package edu.tum.cup2.generator.states;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import edu.tum.cup2.generator.FirstSets;
import edu.tum.cup2.generator.GrammarInfo;
import edu.tum.cup2.generator.NullableSet;
import edu.tum.cup2.generator.items.LR0Item;
import edu.tum.cup2.generator.items.LR1Item;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Production;
import edu.tum.cup2.grammar.SpecialTerminals;
import edu.tum.cup2.grammar.Symbol;
import edu.tum.cup2.grammar.Terminal;

/**
 * LR(1) state, consisting of a list of LR(1) items.
 * 
 * @author Andreas Wenger
 * @author Daniel Altmann
 * @author Michael Hausmann
 * 
 */

public class LR1State extends State<LR1Item> 
{

	//The hashCode of the LR1State is depending on the hashCodes of the contained items.
	//I.e. the hashCode has to be recomputed when the item set changes.
	//(hashCode is computed in constructor and mergeWithSideEffects)
    private int hashCode;
    
    private LR0State kernel;
    private HashMap<LR0Item, LR1Item> itemsWithKernels;
    
    //closure cache
    private HashMap<LR0Item, LR1Item> closureItems;
    
    public LR1State(Collection<LR1Item> items) {
        super(items);
        computeHashcode();
        this.nucleusStateHasChanged = true;
        //create LR(0) kernels
        this.createLR0Kernel();
    }
    
    public void initFromOtherState(LR1State o)
    {
    	this.items = o.items;
    	computeHashcode();
    	this.nucleusStateHasChanged = true;
    	//copy cache as state has changed
    	this.closureItems = o.closureItems;
    	//create LR(0) kernels
    	this.createLR0Kernel();
    }
    
    private int computeHashcode()
    {
    	int sum = 0;
        for (LR1Item item : items) {
            sum += item.hashCode();
        }
        this.hashCode = sum;
        return this.hashCode;
    }
    
    private void createLR0Kernel()
    {
    	//create LR(0) kernels
		HashSet<LR0Item> kernelItems = new HashSet<LR0Item>();
		for (LR1Item item : items)
		{
			kernelItems.add(item.getLR0Kernel());
		}
		kernel = new LR0State(kernelItems);
		//create map with LR(0) kernels as key
		itemsWithKernels = new HashMap<LR0Item, LR1Item>();
		for (LR1Item item : items)
		{
			itemsWithKernels.put(item.getLR0Kernel(), item);
		}
    }

    /**
     * Returns the closure of this {@link LR1State} as another {@link LR1State}.
     */
    @Override
    public LR1State closure(GrammarInfo grammarInfo) {
        return new LR1State(closureItems(grammarInfo));
    }
    
	/**
	 * Returns the closure of the {@link LR1Item}s of this state as set of
	 * {@link LR1Item}s. For a description of this algorithm, see Appel's book,
	 * page 63.
	 */
	private Collection<LR1Item> closureItems(GrammarInfo grammarInfo)
	{
		//first check if closure is already computed
   	if (this.closureItems != null)
   		return this.closureItems.values();
		
		//the closure contains all items of the source...
		//(we use a map with the LR(0)-part as the key for very fast access,
		//since we will probably often add new lookahead symbols to existing LR1Items)
		HashMap<LR0Item, HashSet<Terminal>> items = new HashMap<LR0Item, HashSet<Terminal>>();
		for (LR1Item item : this.items)
		{
			items.put(item.getLR0Kernel(), new HashSet<Terminal>(item.getLookaheads()));
		}
		// ... and the following ones: for any item "A → α.Xβ with lookahead z",
		// any "X → γ" and any w ∈ FIRST(βz), add "X → .γ with lookahead w"
		LinkedList<LR1Item> queue = new LinkedList<LR1Item>();
		queue.addAll(this.items);
		FirstSets firstSets = grammarInfo.getFirstSets();
		NullableSet nullableSet = grammarInfo.getNullableSet();
		while (!queue.isEmpty())
		{
			LR1Item item = queue.removeFirst(); //item is A → α.Xβ with lookahead z"
			Symbol nextSymbol = item.getNextSymbol();
			if (nextSymbol instanceof NonTerminal) //nextSymbol is "X"
			{
				HashSet<Terminal> firstSet = item.getNextLookaheads(firstSets, nullableSet); //all "w"s
				for (Production p : grammarInfo.getProductionsFrom((NonTerminal) nextSymbol)) //p is each "X → γ"
				{
					LR0Item newItemLR0 = new LR0Item(p, 0);
					//look, if there is already a LR(1) item with that LR(0) kernel. if so, add the
					//new lookahead symbols there. If not, add the LR(1) item to the result set.
					HashSet<Terminal> sameKernelItemLookaheads = items.get(newItemLR0);
					if (sameKernelItemLookaheads != null)
					{
						//add to existing LR1Item
						int sizeBefore = sameKernelItemLookaheads.size();
						sameKernelItemLookaheads.addAll(firstSet);
						//if new lookahead was found, add again to queue
						if (sizeBefore < sameKernelItemLookaheads.size())
						{
							queue.add(new LR1Item(newItemLR0, sameKernelItemLookaheads));
						}
					}
					else
					{
						//new item
						items.put(newItemLR0, (HashSet<Terminal>) firstSet.clone());
						queue.add(new LR1Item(newItemLR0, firstSet));
					}
				}
			}
		}
		//collect resulting LR(1) items
		HashMap<LR0Item, LR1Item> ret = new HashMap<LR0Item, LR1Item>();
		for (LR0Item itemLR0 : items.keySet())
		{
			ret.put(itemLR0, new LR1Item(itemLR0.getProduction(), itemLR0.getPosition(), items.get(itemLR0)));
		}
		this.closureItems = ret;
		return ret.values();
	}

    /**
     * Moves the position of the items one step further when the given symbol
     * follows and returns them as a new state (only the kernel, without
     * closure). For a description of this algorithm, see Appel's book, page 62.
     */
    @Override
    public LR1State goTo(Symbol symbol, GrammarInfo grammarInfo) {
        HashSet<LR1Item> ret = new HashSet<LR1Item>();
        // find all items where the given symbol follows and add them shifted
        for (LR1Item item : items) {
            if (item.getNextSymbol() == symbol) {
                ret.add(item.shift());
            }
        }
        return new LR1State(ret);
    }

    /**
     * Gets the number of items, when each item has only one lookahead symbol.
     * Useful for debugging and testing.
     */
    int getSimpleItemsCount() {
        int ret = 0;
        for (LR1Item item : items) {
            ret += item.getLookaheads().size();
        }
        return ret;
    }

    /**
     * Returns true, if this state contains an item with the given production,
     * position and lookhead symbol. Useful for debugging and testing.
     */
    boolean containsSimpleItem(Production production, int position,
            Terminal lookahead) {
        for (LR1Item item : items) {
            if (item.getProduction() == production
                    && item.getPosition() == position
                    && item.getLookaheads().contains(lookahead))
                return true;
        }
        return false;
    }
    
	/**
	 * Gets the item of this state which has the given LR(0) kernel,
	 * or <code>null</code>, if there is no such item.
	 */
	public LR1Item getItemByLR0Kernel(LR0Item kernel)
	{
		return itemsWithKernels.get(kernel);
	}
	  
   /**
	 * Merges this state with the given one and returns the result.
	 * Only works, if both states have equal LR(0) kernels.
	 */
	public LR1State merge(LR1State state) 
	{
		HashSet<LR1Item> items = new HashSet<LR1Item>();
		for (LR1Item item1 : this.items)
		{
			LR1Item newItem = item1;
			
			//find lr0-equal item and merge them
			LR1Item item2 = state.getItemByLR0Kernel(item1.getLR0Kernel());
			newItem = newItem.merge(item2);
			
			items.add(newItem);
		}
		LR1State ret = new LR1State(items);		
		return ret;
	}
	
    /**
	 * Merges this state with the given one and returns the result.
	 * Only works, if both states have equal LR(0) kernels.
	 */
//TODO LR1Items_WithSideEffects	public LR1State mergeWithSideEffects(LR1State state) 
//	{
//		for (LR1Item item1 : this.items)
//		{			
//			//find lr0-equal item and merge them
//			LR1Item item2 = state.getItemByLR0Kernel(item1.getLR0Kernel());
//			item1.mergeWithSideEffects(item2);
//		}
//		return this;
//	}
	
	public LR1State mergeWithSideEffects(LR1State other) 
	{
		LR1State merged = this.merge(other);
    	initFromOtherState(merged);   
		return this;
	}

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LR1State) {
            LR1State s = (LR1State) obj;
            if (items.size() != s.items.size())
                return false;
            for (LR1Item item : items) {
                if (!s.items.contains(item))
                    return false;
            }
            return true;
        }
        return false;
    }

    public boolean equalsLR0(Object obj) {
        if (obj instanceof LR1State) {
        	LR1State s = (LR1State) obj;
            if (items.size() != s.items.size())
                return false;
            for (LR1Item item : items) {
                // find item where LR(0) part is equal
                boolean found = false;
                for (LR1Item sItem : s.items) {
                    if (item.equalsLR0(sItem)) {
                        found = true;
                        break;
                    }
                }
                if (!found)
                    return false;
            }
            return true;
        }
        return false;
    }
    
    /**
	 * Gets the LR(0) kernel of this state.
	 */
	public LR0State getLR0Kernel()
	{
		return kernel;
	}

    @Override
    public int hashCode() {
    	return hashCode;
    }

    /*
     * 
     * Hier kommt etwas Funktionalität dazu...
     */

    /*
     * Attribute - Getter - Setter
     */
    
    private Map<LR0Item, Set<Terminal>> nucleus = Collections.EMPTY_MAP;

    private boolean nucleusStateHasChanged = true;

    public Map<LR0Item, Set<Terminal>> getNucleus() {
        if (true == this.nucleusStateHasChanged) {
            computeNucleus();
        }
        return Collections.unmodifiableMap(this.nucleus);
    }

    /*
     * public Methoden
     */

    /**
     * Die sogennante "weak compatibility" besagt (bei einem Return-Wert von
     * "true"), daß 2 LR1States Konfliktfrei "ge-merge-d" werden können. Es kann
     * allerdings sein, daß bei einem Rückgabewert von "false" trotzdem
     * konfliktfrei gemerged werden kann. Motto: Sicher ist sicher.
     * 
     * WICHTIG WICHTIG WICHTIG: Zwei States sind genau dann "weak compatible",
     * wenn ihre "nuclei" es sind.
     * 
     * @author Daniel Altmann
     */
    public boolean isWeakCompatible(LR1State other) {

        if (!this.equalsLR0(other)) {
            return false;
        }

        /*
         * Die Rolle dieser Maps ist es die Kontexte der States zu speichern,
         * zumal diese hier öfter gebraucht werden
         */

        Map<LR0Item, Set<Terminal>> me = this.getNucleus();

        if (me.isEmpty()) {
            return true;
        }

        Map<LR0Item, Set<Terminal>> you = other.getNucleus();

        boolean ret = true;

        outer: for (LR1Item i_elem : this.items) {
            LR0Item i = i_elem.getLR0Kernel();
            inner: for (LR1Item j_elem : this.items) {
                LR0Item j = j_elem.getLR0Kernel();
                if (i.equals(j))
                    continue inner;
                if (Collections.disjoint(me.get(i), you.get(j))
                        && Collections.disjoint(me.get(j), you.get(i))) {
                    // ret ist immer noch wahr
                    continue inner;
                } else if (!Collections.disjoint(me.get(i), me.get(j))) {
                    // ret ist immer noch wahr
                    continue inner;
                } else if (!Collections.disjoint(you.get(i), you.get(j))) {
                    // ret ist immer noch wahr
                    continue inner;
                } else {
                    ret = false;
                    break outer;

                }
            }
        }

        return ret;

    }

    private boolean isNucleusItem(LR1Item item) {
        int pos = item.getPosition();
        return (!(pos == 0 || (pos == 1 && item.getProduction().getRHS().get(0)
                .equals(SpecialTerminals.Epsilon))));
    }

    /*
     * private-Methoden
     */

    /*
     * Der "nucleus" einer LR1Item-Menge, sind die Items, bei denen der Punkt
     * nicht ganz links steht.
     */
    private void computeNucleus() {

        if (false == this.nucleusStateHasChanged) {
            return;
        }

        Map<LR0Item, Set<Terminal>> newNuke = new HashMap<LR0Item, Set<Terminal>>();

        for (LR1Item elem : this.items) {
            if (this.isNucleusItem(elem)) {
                newNuke.put(elem.getLR0Kernel(), elem.getLookaheads());
            }
        }

        this.nucleus = newNuke;
        this.nucleusStateHasChanged = false;

    }

}
