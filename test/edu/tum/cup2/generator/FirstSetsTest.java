package edu.tum.cup2.generator;

import java.util.HashSet;

import org.junit.Test;

import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.test.GrammarASU_4_11;
import edu.tum.cup2.test.GrammarAppel_3_12;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.test.GrammarAppel_3_12.MyNonTerminal.*;
import static edu.tum.cup2.test.GrammarAppel_3_12.MyTerminal.*;
import static edu.tum.cup2.grammar.SpecialTerminals.Epsilon;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;


/**
 * Tests for the {@link FirstSets} class.
 * 
 * @author Andreas Wenger
 */
public class FirstSetsTest
{
	
	/**
	 * Tests with {@link GrammarAppel_3_12}.
	 * Appel ignores epsilons in FIRST sets, but we do not
	 * (according to ASU99).
	 */
	@Test public void test1()
	{
		Grammar grammar = new GrammarAppel_3_12().get();
		FirstSets ret = new FirstSets(grammar, new NullableSet(grammar.getProductions()));
		//3 non-terminals, 3 terminals, 1 epsilon
		assertEquals(3 + 3 + 1, ret.size());
		//X
		HashSet<Terminal> set = ret.get(X);
		assertEquals(3, set.size());
		assertTrue(set.contains(Epsilon));
		assertTrue(set.contains(a));
		assertTrue(set.contains(c));
		//Y
		set = ret.get(Y);
		assertEquals(2, set.size());
		assertTrue(set.contains(Epsilon));
		assertTrue(set.contains(c));
		//Z
		set = ret.get(Z);
		assertEquals(3, set.size());
		assertTrue(set.contains(a));
		assertTrue(set.contains(c));
		assertTrue(set.contains(d));
	}
	
	
	/**
	 * Tests with {@link GrammarASU_4_11}.
	 * See ASU99, page 231 (german edition).
	 */
	@Test public void test2()
	{
		Grammar grammar = new GrammarASU_4_11().get();
		FirstSets ret = new FirstSets(grammar, new NullableSet(grammar.getProductions()));
		//5 non-terminals, 5 terminals, 1 epsilon
		assertEquals(5 + 5 + 1, ret.size());
		//E
		HashSet<Terminal> set = ret.get(GrammarASU_4_11.N.E);
		assertEquals(2, set.size());
		assertTrue(set.contains(GrammarASU_4_11.T.lbr));
		assertTrue(set.contains(GrammarASU_4_11.T.id));
		//T
		set = ret.get(GrammarASU_4_11.N.T);
		assertEquals(2, set.size());
		assertTrue(set.contains(GrammarASU_4_11.T.lbr));
		assertTrue(set.contains(GrammarASU_4_11.T.id));
		//F
		set = ret.get(GrammarASU_4_11.N.F);
		assertEquals(2, set.size());
		assertTrue(set.contains(GrammarASU_4_11.T.lbr));
		assertTrue(set.contains(GrammarASU_4_11.T.id));
		//Epr
		set = ret.get(GrammarASU_4_11.N.Epr);
		assertEquals(2, set.size());
		assertTrue(set.contains(GrammarASU_4_11.T.plus));
		assertTrue(set.contains(Epsilon));
		//Tpr
		set = ret.get(GrammarASU_4_11.N.Tpr);
		assertEquals(2, set.size());
		assertTrue(set.contains(GrammarASU_4_11.T.mult));
		assertTrue(set.contains(Epsilon));
	}

}
