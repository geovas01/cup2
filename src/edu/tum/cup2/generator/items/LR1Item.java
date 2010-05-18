package edu.tum.cup2.generator.items;

import static edu.tum.cup2.grammar.SpecialTerminals.Epsilon;

import java.util.HashSet;
import java.util.Set;

import edu.tum.cup2.generator.FirstSets;
import edu.tum.cup2.generator.NullableSet;
import edu.tum.cup2.grammar.Production;
import edu.tum.cup2.grammar.Symbol;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.util.ArrayTools;


/**
 * An LR(1)-Item is an LR(0)-Item, combined
 * with a set of lookahead terminals.
 * 
 * @author Andreas Wenger
 */
public final class LR1Item
	implements Item
{
	
	private LR0Item kernel;
	private final HashSet<Terminal> lookaheads;
	
	//cache
	private final int kernelSize;
	private HashSet<Terminal> nextLookaheads = null; //compute on demand
	private final int hashCode;
	
	
	/**
	 * Creates a new {@link LR1Item}, using the given {@link Production},
	 * position within this production and set of possible lookahead terminals.
	 */
	public LR1Item(Production production, int position, HashSet<Terminal> lookaheads)
	{
		this.kernel = new LR0Item(production, position);
		this.kernelSize = this.kernel.getProduction().getRHS().size();
		this.lookaheads = lookaheads;
		//compute hashcode
		int lookaheadHashCode = 0;
		for (Terminal la : lookaheads)
			lookaheadHashCode+= la.hashCode();
		this.hashCode = this.kernel.hashCode() + lookaheadHashCode * 100;
	}
	
	
	/**
	 * Creates a new {@link LR1Item}, using the given {@link LR0Item}
	 * and set of possible lookahead terminals.
	 */
	public LR1Item(LR0Item kernel, HashSet<Terminal> lookaheads)
	{
		this.kernel = kernel;
		this.kernelSize = this.kernel.getProduction().getRHS().size();
		this.lookaheads = lookaheads;
		//compute hashcode
		this.hashCode = this.kernel.hashCode() + lookaheads.size() * 100;
	}
	
	
	/**
	 * Creates a new {@link LR1Item}, using the given {@link Production},
	 * position within this production and a lookahead terminal.
	 */
	public LR1Item(Production production, int position, Terminal lookahead)
	{
		this(production, position, ArrayTools.toHashSet(lookahead));
	}
	
	
	public Production getProduction()
	{
		return kernel.getProduction();
	}
	
	
	public int getPosition()
	{
		return kernel.getPosition();
	}
	
	
	/**
	 * Gets the {@link Symbol} right behind the current position,
	 * or <code>null</code> if there is none left.
	 */
	public Symbol getNextSymbol()
	{
		return kernel.getNextSymbol();
	}
	
	
	public boolean isShiftable()
	{
		return kernel.isShiftable();
	}
	
	
	/**
	 * Returns a new {@link LR1Item}, having the same LR(0) kernel as this
	 * one and containing the union of the lookahead symbols of this item
	 * and the given ones. If no new lookaheads are given, the original
	 * LR1Item (<code>this</code>) is returned.
	 */
	public LR1Item createUnion(HashSet<Terminal> lookaheads)
	{
		if (this.lookaheads.containsAll(lookaheads))
		{
			return this;
		}
		else
		{
			@SuppressWarnings("unchecked") HashSet<Terminal> union =
				(HashSet<Terminal>) this.lookaheads.clone();
			union.addAll(lookaheads);
			return new LR1Item(kernel.production, kernel.position, union);
		}
	}
	
	
	/**
	 * Returns this item with the position shifted one symbol further.
	 */
	public LR1Item shift()
	{
		if (kernel.position >= kernelSize)
			throw new RuntimeException( //TODO
				"Shifting not possible: Item already closed: " + kernel.production.toString(kernel.position));
		return new LR1Item(kernel.production, kernel.position + 1, lookaheads);
	}
	
	
	/**
	 * Gets the lookahead terminals assigned to this item.
	 */
	public HashSet<Terminal> getLookaheads()
	{
		return lookaheads;
	}
	
	
	/**
	 * Gets the FIRST set of βa (where this item has the following form:
	 * "A → α.Xβ with lookahead a" with X being the next symbol), using
	 * the given precomputed nullable set and FIRST sets for all symbols.
	 */
	public HashSet<Terminal> getNextLookaheads(FirstSets firstSets, NullableSet nullableSet)
	{
		if (nextLookaheads == null)
		{
			nextLookaheads = computeNextLookaheadSymbols(firstSets, nullableSet);
		}
		return nextLookaheads;
	}
	
	
	/**
	 * Gets the FIRST set of βa (where this item has the following form:
	 * "A → α.Xβ with lookahead a" with X being the next symbol). See
	 * </code>getNextLookaheads</code>.
	 */
	HashSet<Terminal> computeNextLookaheadSymbols(FirstSets firstSets, NullableSet nullableSet)
	{
		HashSet<Terminal> ret = new HashSet<Terminal>();
		//while the symbols of β (if any) are nullable, collect their FIRST sets.
		//when not nullable, collect the symbols and stop.
		for (int i = kernel.position + 1; i < kernel.production.getRHS().size(); i++)
		{
			Symbol symbol = kernel.production.getRHS().get(i);
			ret.addAll(firstSets.get(symbol));
			if (!(symbol == Epsilon || nullableSet.contains(symbol)))
				return ret;
		}
		//since we still did not return, all symbols of β (if any) were nullable,
		//so we add all our lookaheads to the result
		ret.addAll(lookaheads);
		return ret;
	}
	
	
	/**
	 * Merges this item with the given one and returns the result.
	 */
	public LR1Item merge(LR1Item item)
	{
		if (!kernel.equals(item.kernel))
			throw new IllegalArgumentException("Only items with equal LR(0) kernel can be merged!");
		HashSet<Terminal> la = new HashSet<Terminal>(this.lookaheads);
		la.addAll(item.lookaheads);
		return new LR1Item(kernel.production, kernel.position, la);
	}
	
	
	@Override public String toString()
	{
		StringBuilder s = new StringBuilder(kernel.production.toString(kernel.position));
		s.append(" [");
		s.append(lookaheads.toString());
		s.append("]");
		return s.toString();
	}
	
	
	@Override public boolean equals(Object obj)
	{
		if (obj instanceof LR1Item)
		{
			LR1Item l = (LR1Item) obj;
			if (!kernel.equals(l.kernel))
				return false;
			if (!lookaheads.equals(l.lookaheads))
				return false;
			return true;
		}
		return false;
	}
	
	
	public boolean equalsLR0(LR1Item item)
	{
		return kernel.equals(item.kernel);
	}
	
	
	@Override public int hashCode()
	{
		return hashCode;
	}
	
	
	public LR0Item getLR0Kernel()
	{
		return kernel;
	}
	

}
