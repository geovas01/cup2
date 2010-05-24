package edu.tum.cup2.generator;

import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.generator.items.LR1Item;
import edu.tum.cup2.generator.states.LR1State;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.spec.CUPSpecification;

/**
 * 
 * Mein naiver Ansatz den LALRGenerator zu einem WeakCompatGenerator
 * zu erweitern. Die einzige Methode, die sich (neben den Konstruktoren)
 * jetzt anders verhalten sollte ist createAutomaton()
 * 
 * @author Daniel Altmann
 */
public class WeakCompatGenerator extends LALR1relatedGenerator
{

	/**
	 * Computes a {@link LRParsingTable} for the given LR(1) specification.
	 */
	public WeakCompatGenerator(CUPSpecification spec) throws GeneratorException
	{
		this(spec, Verbosity.None);
	}


	/**
	 * Computes a {@link LRParsingTable} for the given LR(1) specification.
	 */
	public WeakCompatGenerator(CUPSpecification spec, Verbosity verbosity)
		throws GeneratorException
	{
		super(spec, verbosity, true);
	}

	/**
	 * Creates the DFA of the parser, consisting of states and edges
	 * (for shift and goto) connecting them.
	 */
	@Override public Automaton<LR1Item, LR1State> createAutomaton()
		throws GeneratorException
	{
		return new NucleusAutomatonFaktory__naive_try().createAutomaton(this, grammarInfo);
	}
	
}
