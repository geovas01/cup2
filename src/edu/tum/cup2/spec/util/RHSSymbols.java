package edu.tum.cup2.spec.util;

import java.util.Arrays;
import java.util.List;

import edu.tum.cup2.grammar.SpecialTerminals;
import edu.tum.cup2.grammar.Symbol;


/**
 * {@link RHSItem}-compatible wrapper around a {@link List}
 * of {@link Symbol}s.
 * 
 * @author Andreas Wenger
 */
public final class RHSSymbols
	implements RHSItem
{
	
	private final List<Symbol> symbols;
	
	
	public RHSSymbols(Symbol... symbols)
	{
		if (symbols.length == 0)
		{
			//empty RHS means epsilon
			symbols = new Symbol[]{SpecialTerminals.Epsilon};
		}
		else
		{
			//check, that there is no epsilon in a RHS
			for (Symbol symbol : symbols)
			{
				if (symbol == SpecialTerminals.Epsilon)
				{
					throw new IllegalArgumentException("Epsilon may not be used in a RHS!");
				}
			}
		}
		this.symbols = Arrays.asList(symbols);
	}
	
	
	public List<Symbol> getSymbols()
	{
		return symbols;
	}
	

}
