package edu.tum.cup2.generator.states;

import org.junit.Test;

import edu.tum.cup2.generator.GrammarInfo;
import edu.tum.cup2.generator.items.LR1Item;
import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.test.GrammarAppel_3_26;
import edu.tum.cup2.util.ArrayTools;

import static edu.tum.cup2.grammar.SpecialTerminals.Placeholder;
import static edu.tum.cup2.test.GrammarAppel_3_26.T.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Test cases for the {@link LR1NSEState} class.
 * 
 * @author Andreas Wenger
 */
public class LR1NSEStateTest
{
	
	/**
	 * Tests the closure method, using the grammar 3.26 from Appel's book.
	 * Expected results are read from page 66.
	 */
	@Test public void closureTest()
	{
		Grammar grammar = new GrammarAppel_3_26().getWith$();
		//compute closure of "S' → .S$" (production 0, position 0) as seen on page 66.
		LR1NSEState state = createLR1NSEState(
			new LR1Item(grammar.getProductionAt(0), 0, Placeholder)); //S' → .S$
		LR1NSEState closure = state.closure(new GrammarInfo(grammar));
		//result must have 8 items
		assertEquals(8, closure.getSimpleItemsCount());
		assertTrue(closure.containsSimpleItem(grammar.getProductionAt(0), 0, Placeholder)); //S → .S$ , ?
		assertTrue(closure.containsSimpleItem(grammar.getProductionAt(1), 0, $));  //S → .V=E , $
		assertTrue(closure.containsSimpleItem(grammar.getProductionAt(2), 0, $));  //S → .E   , $
		assertTrue(closure.containsSimpleItem(grammar.getProductionAt(3), 0, $));  //E → .V   , $
		assertTrue(closure.containsSimpleItem(grammar.getProductionAt(4), 0, $));  //V → .x   , $
		assertTrue(closure.containsSimpleItem(grammar.getProductionAt(5), 0, $));  //V → .*E  , $
		assertTrue(closure.containsSimpleItem(grammar.getProductionAt(4), 0, eq)); //V → .x   , =
		assertTrue(closure.containsSimpleItem(grammar.getProductionAt(5), 0, eq)); //S → .*E  , =
	}
	
	
	private LR1NSEState createLR1NSEState(LR1Item... items)
	{
		return new LR1NSEState(ArrayTools.toHashSet(items));
	}
	

}
