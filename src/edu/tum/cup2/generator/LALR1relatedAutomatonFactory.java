package edu.tum.cup2.generator;

import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.generator.items.*;
import edu.tum.cup2.generator.states.*;

/**
 * LALR1relatedAutomatonFactory is a common base class for factories 
 * for an automaton that create
 * an LALR1Automaton
 * 
 * @author Daniel Altmann
 * @author Michael Hausmann
 * 
 */
public abstract class LALR1relatedAutomatonFactory 
	extends AutomatonFactory<LR1Item, LR1State>
{
	/**
	 * create an LALR1-Automaton
	 */
	public abstract Automaton<LR1Item, LR1State> createAutomaton(
			LRGenerator<LR1Item, LR1State> generator, 
			GrammarInfo grammarInfo)
	throws GeneratorException;
}
