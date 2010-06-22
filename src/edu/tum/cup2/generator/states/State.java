package edu.tum.cup2.generator.states;

import java.util.Collection;

import edu.tum.cup2.generator.GrammarInfo;
import edu.tum.cup2.generator.items.Item;
import edu.tum.cup2.grammar.Symbol;
import edu.tum.cup2.io.IAutomatonVisitor;
import edu.tum.cup2.io.IVisitedElement;
import edu.tum.cup2.util.It;

/**
 * State, consisting of a list of {@link Item}s
 * of the given type.
 * 
 * @author Andreas Wenger
 * 
 */
public abstract class State<T extends Item>
	implements IVisitedElement
{
	
	protected Collection<T> items;
	
	public State(Collection<T> items)
	{
		this.items = items;
	}
	
	
	public It<T> getItems()
	{
		return new It<T>(items);
	}
	
	
	public T getFirstItem()
	{
		return items.iterator().next();
	}
	
	
	public Collection<T> getItemsAsCollection()
	{
		return items;
	}
	
	
	public int getItemsCount()
	{
		return items.size();
	}
	
	
	/**
	 * Returns the closure of this {@link State} as another
	 * {@link State}.
	 * For a description of this algorithm, see Appel's book, page 62.
	 */
	public abstract State<T> closure(GrammarInfo grammarInfo);
	
	
	/**
	 * Moves the position if the items one step further when
	 * the given symbol follows and returns them as a new state
	 * (only the kernel, without closure).
	 * For a description of this algorithm, see Appel's book, page 62.
	 */
	public abstract State<T> goTo(Symbol symbol);

		
	@Override public String toString()
	{
		StringBuilder s = new StringBuilder("State:\n");
		for (Item item : items)
		{
			s.append("  ");
			s.append(item.toString());
			s.append("\n");
		}
		return s.toString();
	}
	
    /**
	 * implements the visitor pattern
	 */
	public void visited(IAutomatonVisitor visitor) {
		visitor.visit(this);
		
	}
	
}
