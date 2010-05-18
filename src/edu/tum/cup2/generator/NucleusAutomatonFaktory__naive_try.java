package edu.tum.cup2.generator;

import static edu.tum.cup2.grammar.SpecialTerminals.EndOfInputStream;

import java.util.Collection;
import java.util.HashMap;

import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.generator.items.LR1Item;
import edu.tum.cup2.generator.states.LR0State;
import edu.tum.cup2.generator.states.LR1State;
import edu.tum.cup2.grammar.Symbol;

public class NucleusAutomatonFaktory__naive_try 
	extends LALR1relatedAutomatonFactory 
{

    protected LR1State findStateWithEqualKernel(LR1State orgState) {
        for (LR1State s : getAllStates()) {
            if (orgState.isWeakCompatible(s)) {
                return s;
            }
        }
        return null;
    }
    
    private LR1State findEqualLR1State(LR1State orgState) {
    	for (LR1State s : getAllStates()) {
    		if (orgState.equals(s))
    			return s;
    	}
    	return null;
    }
    
    private Collection<LR1State> getAllStates()
    {
    	return dfaStates;
    }

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
				LR1State shiftedState = state.goTo(symbol, grammarInfo);
				
				// We try to find out if there already
				// exists some state which has an equal
				// kernel to the shifted state
				LR1State equalStateLR0 = findStateWithEqualKernel(shiftedState);
				boolean foundEqualState = (equalStateLR0 != null);
				
				if (foundEqualState)
				{
					// in case they are equal we merge the states
					LR1State tempState = equalStateLR0.merge(shiftedState);
					
					//new merged state?
					if (!dfaStates.contains(tempState))
					{
						//hashCode possibly changes in mergeWithSideEffects 
						//==> readd equalStateLR0 to states HashSet
						this.dfaStates.remove(equalStateLR0);
						equalStateLR0.mergeWithSideEffects(shiftedState);
						LR1State mergedState = equalStateLR0;
						this.dfaStates.add(mergedState);
						
						//To make sure that the algorithm terminates, 
						//the merged state may only be once in the queue
						queue.remove(mergedState); //equalState might already be contained in queue - double containedness leads to endless loop
						queue.add(mergedState); //add the state (again) - state is excatly contained once in queue
						
						dfaEdges.add(new Edge(stateKernel, symbol, mergedState, item));
					}
					else {
						LR1State existingMergedState =  findEqualLR1State(tempState);
						dfaEdges.add(new Edge(stateKernel, symbol, existingMergedState, item));
					}
				}
				else { //if (!foundEqualState)
					//new state?
					if (!dfaStates.contains(shiftedState)) 
					{
						dfaStates.add(shiftedState);
						queue.add(shiftedState);
					}
					//add the edge
					dfaEdges.add(new Edge(stateKernel, symbol, shiftedState, item));
				}	
			} /*end else*/
		} /*end if*/
	} /*end for*/
	} /*end while*/
	
	printDebugResult();
	
	return ret;
	}

}

