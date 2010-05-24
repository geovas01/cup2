package edu.tum.cup2.generator;

import static edu.tum.cup2.grammar.SpecialTerminals.Placeholder;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.generator.items.LR1Item;
import edu.tum.cup2.generator.states.LR1NSEState;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.parser.actions.Reduce;
import edu.tum.cup2.parser.states.LRParserState;
import edu.tum.cup2.parser.tables.LRActionTable;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.spec.CUPSpecification;
import edu.tum.cup2.util.ArrayTools;


/**
 * LALR(1) parser generator, that works without side effects
 * (other than {@link LALR1Generator}).
 * 
 * @author Andreas Wenger
 */
public class LALR1NSEGenerator
	extends LRGenerator<LR1Item, LR1NSEState>
{
	
	
	/**
	 * Computes a {@link LRParsingTable} for the given specification.
	 */
	public LALR1NSEGenerator(CUPSpecification spec)
		throws GeneratorException
	{
		this(spec, Verbosity.None);
	}
	
	
	/**
	 * Computes a {@link LRParsingTable} for the given specification.
	 */
	public LALR1NSEGenerator(CUPSpecification spec, Verbosity verbosity)
		throws GeneratorException
	{
		super(spec, verbosity, true);
	}
	
	
	
	/**
	 * Computes a {@link LRParsingTable} for the given specification.
	 * (which is only extended by an auxiliary start production if requested).
	 * The given verbosity tells the generator how many debug messages are requested.
	 */
	public LALR1NSEGenerator(CUPSpecification spec, Verbosity verbosity, boolean extendGrammar)
		throws GeneratorException
	{
		super(spec, verbosity, extendGrammar);
	}
	
	
	/**
	 * Creates and returns the start state.
	 * This is the item, which consists of the start production at position 0.
	 */
	@Override protected LR1NSEState createStartState()
	{
		return new LR1NSEState(ArrayTools.toHashSet(new LR1Item(grammar.getStartProduction(), 0, Placeholder)));
	}
	
	
	/**
	 * Fills the given {@link LRActionTable} with reduce actions, using the
	 * given non-shiftable item and its parent parser state.
	 */
	@Override protected void createReduceActions(LRActionTable actionTable, LR1Item item, LRParserState state)
		throws GeneratorException
	{
		//in LR(1), reduce the production for each lookahead terminal
		Reduce reduce = new Reduce(item.getProduction());
		for (Terminal lookahead : item.getLookaheads())
		{
			setReduceAction(actionTable, reduce, state, lookahead);
		}
	}
	
	/**
	 * Creates the DFA of the parser, consisting of states and edges
	 * (for shift and goto) connecting them.
	 */
	@Override public Automaton<LR1Item, LR1NSEState> createAutomaton()
		throws GeneratorException
	{
		return new LALR1NSEAutomatonFactory().createAutomaton(this, grammarInfo);
	}

}
