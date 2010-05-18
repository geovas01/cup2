package edu.tum.cup2.generator;

import static edu.tum.cup2.generator.Verbosity.Detailled;
import static edu.tum.cup2.generator.Verbosity.None;
import static edu.tum.cup2.generator.Verbosity.Sparse;
import static edu.tum.cup2.generator.Verbosity.Verbose;
import edu.tum.cup2.grammar.Grammar;

import java.util.HashSet;
import java.util.LinkedList;
import java.io.PrintStream;

import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.generator.items.*;
import edu.tum.cup2.generator.states.*;

/**
 * AutomatonFactory is a Factory for an Automaton that creates
 * an Automaton (used in an LRGenerator)
 * 
 * @author Daniel Altmann
 * @author Michael Hausmann
 * 
 */
public abstract class AutomatonFactory<I extends Item, S extends State<I>>
	implements IAutomatonFactory<I,S>
{
	Automaton<I, S> ret = null; //Automaton that is going to be returned by createAutomaton
	GrammarInfo grammarInfo = null; //grammarInfo used during creation
	Verbosity verbosity = null;
	PrintStream debugOut = null;
	Grammar grammar = null;
	LRGenerator<I,S> generator = null;
	LinkedList<S> queue = null;
	S state = null; //current state during creation of the automaton
	S stateKernel = null; //current state kernel 
	HashSet<Edge> dfaEdges = null;
	HashSet<S> dfaStates = null;
	int statesCounterMsgStep;
	int statesCounterMsgNext;
	int iterationsCounter;
	boolean debug = false;
	
	/**
	 * Method for creating an Automaton - to be overridden in subclasses
	 */
	public abstract Automaton<I, S> createAutomaton ( 
			LRGenerator<I, S> generator, 
			GrammarInfo grammarInfo)
	throws GeneratorException;
	
	/**
	 * init variables
	 */
	protected void initCreation()
	{
		//Debug Output
		verbosity = generator.getVerbosity();
		debugOut = generator.getDebugOut();
		grammar = generator.getGrammar();
		
		//start state
		S state0 = (S) generator.createStartState();
		
		queue = new LinkedList<S>();
		queue.addLast(state0);
		
		//set of DFA states and edges created so far
		ret = new Automaton<I, S>(state0);
		dfaStates = ret.getStates(); //shortcut
		dfaEdges = ret.getEdges(); //shortcut
		
		//debug messages
		debug = (verbosity != None); //flag if no debugging is requested
		if (debug && verbosity.ordinal() >= Sparse.ordinal())
		{
			debugOut.println("Terminals:    " + grammar.getTerminals().size());
			debugOut.println("NonTerminals: " + grammar.getNonTerminals().size());
			debugOut.println("Productions:  " + grammar.getProductionCount());
		}
		statesCounterMsgStep = verbosity.getStatesCounterStep();
		statesCounterMsgNext = statesCounterMsgStep;
		iterationsCounter = 0;	
	}
	
	/**
	 * print debug information
	 */
	protected final void printDebugMessages()
	{
		//debug messages
		iterationsCounter++;
		if (debug)
		{
			if (verbosity.getStatesCounterStep() > 0 && dfaStates.size() >= statesCounterMsgNext)
			{
				debugOut.println("Number of states >= " + statesCounterMsgNext);
				if (verbosity.ordinal() >= Verbose.ordinal())
				{
					debugOut.println("  Queue: " + queue.size() + 1 + ", DFA states: " + dfaStates.size() + ", DFA edges: " + dfaEdges.size() +
						", Iterations: " + iterationsCounter);
				}
				statesCounterMsgNext += statesCounterMsgStep;
			}
			if (verbosity == Detailled)
			{
				debugOut.println(stateKernel);
			}
			S state = (S) stateKernel.closure(grammarInfo); //unpack state (from kernel to closure)
			//find all shiftable items and shift them
			if (verbosity == Detailled)
			{
				debugOut.println("  Number of kernel items in current state:      " + stateKernel.getItemsCount());
				debugOut.println("  Number of items in closure of current state:  " + state.getItemsCount());
			}
		}
	}
	
	/**
	 * print debug information after automaton creation
	 */
	protected void printDebugResult()
	{
		if (verbosity != Verbosity.None)
		{
			debugOut.println("Automaton: DFA states: " + dfaStates.size() + ", DFA edges: " + dfaEdges.size() +
				", Iterations: " + iterationsCounter);
		}
	}

} /*end of AutomatonFactory*/
