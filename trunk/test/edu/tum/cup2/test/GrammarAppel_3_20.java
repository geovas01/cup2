package edu.tum.cup2.test;

import java.util.LinkedList;

import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Production;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.util.ArrayTools;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.test.GrammarAppel_3_20.MyNonTerminal.L;
import static edu.tum.cup2.test.GrammarAppel_3_20.MyNonTerminal.S;
import static edu.tum.cup2.test.GrammarAppel_3_20.MyNonTerminal.S_primed;
import static edu.tum.cup2.test.GrammarAppel_3_20.MyTerminal.$;
import static edu.tum.cup2.test.GrammarAppel_3_20.MyTerminal.comma;
import static edu.tum.cup2.test.GrammarAppel_3_20.MyTerminal.leftbr;
import static edu.tum.cup2.test.GrammarAppel_3_20.MyTerminal.rightbr;
import static edu.tum.cup2.test.GrammarAppel_3_20.MyTerminal.x;

/**
 * Grammar 3.20 (LR(0)) from Appel's book, page 60.
 * 
 * @author Andreas Wenger
 */
public class GrammarAppel_3_20
{

	public enum MyTerminal
		implements Terminal
	{
		leftbr,
		rightbr,
		x,
		comma,
		$;
	}


	public enum MyNonTerminal
		implements NonTerminal
	{
		S_primed,
		S,
		L;
	}


	public Grammar getWith$()
	{
		return new Grammar(MyTerminal.values(), MyNonTerminal.values(), new Production[]{
			new Production(0, S_primed, S, $),
			new Production(1, S, leftbr, L, rightbr),
			new Production(2, S, x),
			new Production(3, L, S),
			new Production(4, L, L, comma, S)});
	}
	
	
	public Grammar get()
	{
		//remove $
		LinkedList<Terminal> terminals = new ArrayTools<Terminal>().toLinkedList(MyTerminal.values());
		terminals.remove($);
		LinkedList<NonTerminal> nonTerminals = new ArrayTools<NonTerminal>().toLinkedList(MyNonTerminal.values());
		return new Grammar(terminals, nonTerminals, new ArrayTools<Production>().toLinkedList(
			new Production(1, S, leftbr, L, rightbr),
			new Production(2, S, x),
			new Production(3, L, S),
			new Production(4, L, L, comma, S)));
	}

}
