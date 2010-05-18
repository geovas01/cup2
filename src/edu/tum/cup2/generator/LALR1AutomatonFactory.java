package edu.tum.cup2.generator;

import static edu.tum.cup2.grammar.SpecialTerminals.EndOfInputStream;

import java.util.HashMap;

import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.generator.items.*;
import edu.tum.cup2.generator.states.*;
import edu.tum.cup2.grammar.Symbol;

/**
 * AutomatonFactory is a Factory for an Automaton that creates
 * an LALR1Automaton
 * 
 * @author Daniel Altmann
 * @author Michael Hausmann
 * 
 */
public class LALR1AutomatonFactory extends AutomatonFactory<LR1Item, LR1State>
{
	//hashmap which matches a kernel of a state to its complete state
	HashMap<LR0State, LR1State> tempStates = new HashMap<LR0State, LR1State>();
	
	/**
	 * create an LALR1-Automaton
	 */
	public Automaton<LR1Item, LR1State> createAutomaton(
			LRGenerator<LR1Item, LR1State> generator, 
			GrammarInfo grammarInfo)
	throws GeneratorException
	{
		
		this.generator = generator;
		this.grammarInfo = grammarInfo;
		initCreation();
		
		//for all states, find their edges to other (possibly new) states
		while (!queue.isEmpty())
			//Appel says: "until E and T did not change in this iteration".
			//but: do we really need E here? I ignored it
		{
			//handle next state in queue
			stateKernel = queue.removeFirst(); //extracts the next stateKernel (compressed) from the queue
		
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
						dfaEdges.add(Edge.createAcceptEdge(stateKernel, symbol));
					}
					else
					{
						//terminal or non-terminal
						LR1State shiftedState = (LR1State) state.goTo(symbol, grammarInfo);
						
						// We try to find out if there already
						// exists some state which has an equal
						// kernel to the shifted state
						LR1State equalStateLR0 = tempStates.get(shiftedState.getLR0Kernel());
						
						boolean foundEqualState = (equalStateLR0 != null);
						
						if (foundEqualState)
						{
							// in case they are equal we merge the states
							LR1State tempMergedState = shiftedState.mergeWithSideEffects(equalStateLR0);
							
							//new merged state?
							if (!tempStates.containsValue(tempMergedState))
							{
								//hashCode possibly changes in mergeWithSideEffects 
								//==> readd equalStateLR0 to states HashSet
								tempStates.remove(equalStateLR0.getLR0Kernel());
								
								equalStateLR0.initFromOtherState(tempMergedState);
								LR1State mergedState = equalStateLR0;
								tempStates.put(mergedState.getLR0Kernel(), mergedState);
								
								//To make sure that the algorithm terminates, 
								//the merged state may only be once in the queue
								queue.remove(mergedState); //equalState might already be contained in queue - double containedness leads to endless loop
								queue.add(mergedState); //add the state (again) - state is exactly contained once in queue
								
								dfaEdges.add(new Edge(stateKernel, symbol, mergedState, item));
							}
							else {
								LR1State existingMergedState = tempStates.get(tempMergedState.getLR0Kernel());
								dfaEdges.add(new Edge(stateKernel, symbol, existingMergedState, item));
							}
						}
						else { //if (!foundEqualState)
							//new state?
							if (!tempStates.containsValue(shiftedState)) 
							{
								tempStates.put(shiftedState.getLR0Kernel(), shiftedState);
								queue.add(shiftedState);
							}
							//add the edge
							dfaEdges.add(new Edge(stateKernel, symbol, shiftedState, item));
						}	
					} /*end else*/
				} /*end if*/
			} /*end for*/
		} /*end while*/
		
		//fill dfaStates
		for (LR1State s : tempStates.values())
		{
			dfaStates.add(s);
		}
		
		printDebugResult();
		
		return ret;
	}
	
} /*end of AutomatonFactory*/
