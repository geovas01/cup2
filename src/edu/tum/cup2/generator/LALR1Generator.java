package edu.tum.cup2.generator;

import static edu.tum.cup2.grammar.SpecialTerminals.Placeholder;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.generator.items.LR1Item;
import edu.tum.cup2.generator.states.LR1State;
import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.actions.Reduce;
import edu.tum.cup2.parser.states.LRParserState;
import edu.tum.cup2.parser.tables.LRActionTable;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.precedences.Precedences;
import edu.tum.cup2.spec.CUPSpecification;
import edu.tum.cup2.util.ArrayTools;


/**
 * LALR(1) parser generator.
 * 
 * Given a {@link Grammar}, this class computes the {@link LRParsingTable}
 * that is needed for a {@link LRParser} to parse an input stream.
 * 
 * @author Michael Hausmann
 */
public class LALR1Generator extends LALR1relatedGenerator
{
	/**
	 * Computes a {@link LRParsingTable} for the given LR(1) specification.
	 */
	public LALR1Generator(CUPSpecification spec)
		throws GeneratorException
	{
		this(spec, Verbosity.None);
	}
	
	/**
	 * Computes a {@link LRParsingTable} for the given LR(1) specification.
	 */
	public LALR1Generator(CUPSpecification spec, Verbosity verbosity)
		throws GeneratorException
	{
		super(spec, verbosity, true);
	}
	
	/**
	 * Computes a {@link LRParsingTable} for the given LR(1) specification.
	 * (which is only extended by an auxiliary start production if requested).
	 * The given verbosity tells the generator how many debug messages are requested.
	 */
	public LALR1Generator(CUPSpecification spec, Verbosity verbosity, boolean extendGrammar)
		throws GeneratorException
	{
		super(spec, verbosity, extendGrammar);
	}
	
	/**
	 * Creates the DFA of the parser, consisting of states and edges
	 * (for shift and goto) connecting them.
	 */
	@Override public Automaton<LR1Item, LR1State> createAutomaton()
		throws GeneratorException
	{
		return new LALR1AutomatonFactory().createAutomaton(this, grammarInfo);
	}

}
