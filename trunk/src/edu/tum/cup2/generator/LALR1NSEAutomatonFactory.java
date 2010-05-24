package edu.tum.cup2.generator;

import static edu.tum.cup2.grammar.SpecialTerminals.EndOfInputStream;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.generator.items.*;
import edu.tum.cup2.generator.states.*;
import edu.tum.cup2.grammar.Symbol;


/**
 * Factory for a LALR(1) automaton, that works without side effects
 * (unlike {@link LALR1AutomatonFactory}).
 * 
 * TIDY: remove hashsets/maps from {@link AutomatonFactory} since each
 * factory works differently... at least I think so (Andi).
 * 
 * @author Andreas Wenger
 * @author Daniel Altmann
 * @author Michael Hausmann
 */
public class LALR1NSEAutomatonFactory extends AutomatonFactory<LR1Item, LR1NSEState>
{
	
	private class NumberedEdge
	{
		public final Integer srcState;
		public final Integer destState; //null means: accept
		public final Symbol symbol;
		public final LR1Item srcItem;
		
		public NumberedEdge(Integer srcState, Integer destState, Symbol symbol, LR1Item srcItem)
		{
			this.srcState = srcState;
			this.destState = destState;
			this.symbol = symbol;
			this.srcItem = srcItem;
		}
		
		@Override public boolean equals(Object obj)
		{
			if (obj instanceof NumberedEdge)
			{
				NumberedEdge e = (NumberedEdge) obj;
				return srcState.equals(e.srcState) && symbol.equals(e.symbol);
			}
			return false;
		}
		
		
		@Override public int hashCode()
		{
			return srcState * 100 + symbol.hashCode();
		}
	}
	
	
	/**
	 * Create an LALR(1)-Automaton.
	 */
	public Automaton<LR1Item, LR1NSEState> createAutomaton(LRGenerator<LR1Item, LR1NSEState> generator, 
		GrammarInfo grammarInfo)
		throws GeneratorException
	{

		this.generator = generator;
		this.grammarInfo = grammarInfo;
		initCreation();
		
		//hashmap which matches a kernel of a state to its complete state
		HashMap<LR0State, LR1NSEState> NEWdfaStates = new HashMap<LR0State, LR1NSEState>();
		
		//hashmap which matches an integer number to a state (and backwards). this allows merging a state
		//with a new one without changing its number, so that we don't need to update the
		//edges each time we merge statesState
		HashMap<Integer, LR1NSEState> numberedStates = new HashMap<Integer, LR1NSEState>();
		HashMap<LR1NSEState, Integer> statesNumbered = new HashMap<LR1NSEState, Integer>();
		
		//but this means also that we need to store the source and destination states
		//of the edges in this format
		HashSet<NumberedEdge> numberedEdges = new HashSet<NumberedEdge>();
		
		//our queue consists of numbers instead of states
		LinkedList<Integer> NEWqueue = new LinkedList<Integer>();
		LR1NSEState state = queue.removeFirst();
		numberedStates.put(0, state);
		statesNumbered.put(state, 0);
		NEWqueue.add(0);
		
		//to find out quickly if a state is already queued, we use an additional hashset
		HashSet<Integer> queuedStateNumbers = new HashSet<Integer>();
		queuedStateNumbers.add(0);
		
		//for all states, find their edges to other (possibly new) states
		while (!NEWqueue.isEmpty())
			//Appel says: "until E and T did not change in this iteration".
			//but: do we really need E here? I ignored it
		{
			//handle next state in queue
			Integer stateNumber = NEWqueue.removeFirst();
			queuedStateNumbers.remove(stateNumber);
			stateKernel = numberedStates.get(stateNumber); //get kernel of the state
		
			//debug messages
			printDebugMessages();
			state = stateKernel.closure(grammarInfo); //unpack state (from kernel to closure)
			
			for (LR1Item item : state.getItems())
			{
				
				if (item.isShiftable())
				{
					Symbol symbol = item.getNextSymbol();
					if (symbol == EndOfInputStream)
					{
						//$-symbol: here we accept
						numberedEdges.add(new NumberedEdge(stateNumber, null, symbol, item));
					}
					else
					{
						//terminal or non-terminal
						
						//shift to other state
						LR1NSEState shiftedState = (LR1NSEState) state.goTo(symbol, grammarInfo);
						LR0State shiftedStateKernel = shiftedState.getLR0Kernel();
						//its number is still unknown. it depends on the existence of a LR(0)-equal state
						Integer shiftedStateNumber;
						
						//we try to find out if there is already some state which has an equal
						//kernel to the shifted state
						LR1NSEState equalStateLR0 = NEWdfaStates.get(shiftedStateKernel);
						boolean foundEqualState = (equalStateLR0 != null);
						
						//add the shifted state to the queue (again)?
						boolean addShiftedStateToQueue = false;
						
						if (foundEqualState)
						{
							//in case they are equal we merge the states
							LR1NSEState mergedState = equalStateLR0.merge(shiftedState);
							shiftedStateNumber = statesNumbered.get(equalStateLR0);
							
							//if merged state is different to the old state, new information
							//was found and we have to add it to the queue again
							if (!mergedState.equals(equalStateLR0))
							{
								//replace state in hashmap. its number stays the same.
								statesNumbered.remove(equalStateLR0);
								numberedStates.put(shiftedStateNumber, mergedState);
								statesNumbered.put(mergedState, shiftedStateNumber);
								NEWdfaStates.put(mergedState.getLR0Kernel(), mergedState);
								addShiftedStateToQueue = true;
							}
						}
						else 
						{
							//create new state with new number
							LR1NSEState newState = shiftedState;
							shiftedStateNumber = numberedStates.size();
							NEWdfaStates.put(newState.getLR0Kernel(), newState);
							numberedStates.put(shiftedStateNumber, newState);
							statesNumbered.put(newState, shiftedStateNumber);
							addShiftedStateToQueue = true;
						}	
						
						//add edge
						numberedEdges.add(new NumberedEdge(stateNumber, shiftedStateNumber, symbol, item));
						
						//queue shifted state if requested, but only if not already in queue
						if (addShiftedStateToQueue && !queuedStateNumbers.contains(shiftedStateNumber))
						{
							NEWqueue.add(shiftedStateNumber);
							queuedStateNumbers.add(shiftedStateNumber);
						}
						
					}
				}
			}
		}
		
		//fill dfaStates
		for (LR1NSEState s : numberedStates.values())
		{
			dfaStates.add(s);
		}
		
		//fill dfaEdges
		for (NumberedEdge e : numberedEdges)
		{
			dfaEdges.add(new Edge(numberedStates.get(e.srcState), e.symbol,
				numberedStates.get(e.destState), e.srcItem));
		}
		
		return ret;
	}
	

}