package edu.tum.cup2.generator;

import static edu.tum.cup2.grammar.SpecialTerminals.Epsilon;

import java.util.HashMap;
import java.util.HashSet;

import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Production;
import edu.tum.cup2.grammar.Symbol;
import edu.tum.cup2.grammar.Terminal;


/**
 * The FIRST sets of all symbols.
 * This is the set of all terminals that may appear at the beginning
 * of a string derived from the given symbol.
 * 
 * @author Andreas Wenger
 */
public class FirstSets
	extends HashMap<Symbol, HashSet<Terminal>>
{
	
	
	/**
	 * Computes the FIRST sets of all symbols of the
	 * given grammar, using the precomputed set of nullable non-terminals.
	 * This algorithm is inspired by ASU99, page 230 (german edition) (rules)
	 * and Appel's book, page 51 (rule 3 implementation).
	 */
	public FirstSets(Grammar grammar, NullableSet nullableSet)
	{
		
		//initialize map for all symbols
		for (Terminal terminal : grammar.getTerminals())
		{
			this.put(terminal, new HashSet<Terminal>());
		}
		for (NonTerminal nonTerminal : grammar.getNonTerminals())
		{
			this.put(nonTerminal, new HashSet<Terminal>());
		}
		
		//rule 1: if X is a terminal, then FIRST(X) = {X}
		for (Terminal terminal : grammar.getTerminals())
		{
			HashSet<Terminal> set = new HashSet<Terminal>();
			set.add(terminal);
			this.put(terminal, set);
		}
		HashSet<Terminal> set = new HashSet<Terminal>();
		set.add(Epsilon);
		this.put(Epsilon, set);
		
		//rule 2: if X → ɛ is a production, add ɛ to FIRST(X)
		for (NonTerminal nullableNonTerminal : nullableSet)
		{
			this.get(nullableNonTerminal).add(Epsilon);
		}
		
		//rule 3: if X is a non-terminal and X → Y1 Y2 ... Yk is
		//a production, then add a to FIRST(X), if a is in FIRST(Yi) for
		//any i and FIRST(Y1) ... FIRST(Yi-1) are all nullable. if all
		//FIRST(Y1) ... FIRST(Yk) are nullable, add ɛ to FIRST(X)
		//(Appel ignores the epsilon, but we do not, according to ASU99)
		boolean changed;
		do
		{
			changed = false;
			for (Production production : grammar.getProductions())
			{
				NonTerminal lhs = production.getLHS();
				//find first not nullable rhs symbol. up to this
				//symbol, add all FIRST-symbols of these rhs symbols to
				//the FIRST-set of the current lhs symbol
				Symbol firstNotNullableSymbol = null;
				for (Symbol symbol : production.getRHS())
				{
					if (!(symbol == Epsilon || nullableSet.contains(symbol)))
					{
						//terminal or not nullable non-terminal, stop
						firstNotNullableSymbol = symbol;
						break;
					}
					else
					{
						//epsilon or nullable non-terminal, so go on, but
						//add their FIRST values
						changed |= add(this.get(lhs), this.get(symbol));
					}
				}
				//first not nullable symbol found? than add its FIRST values.
				//otherwise add epsilon
				if (firstNotNullableSymbol != null)
				{
					changed |= add(this.get(lhs), this.get(firstNotNullableSymbol));
				}
				else
				{
					changed |= this.get(lhs).add(Epsilon);
				}
			}
		}
		while (changed);
		
	}
	
	
	/**
	 * Adds the given terminals (src) without epsilon to the other given
	 * set of terminals (dest), returning true if at least one new
	 * terminal was given, otherwise false.
	 */
	private boolean add(HashSet<Terminal> dest, HashSet<Terminal> src)
	{
		boolean foundNew = false;
		for (Terminal terminal : src)
		{
			try
			{
				if (terminal != Epsilon)
					foundNew |= dest.add(terminal);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return foundNew;
	}
	

}
