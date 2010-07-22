package edu.tum.cup2.generator;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import edu.tum.cup2.generator.items.Item;
import edu.tum.cup2.generator.states.State;
import edu.tum.cup2.io.IAutomatonVisitor;
import edu.tum.cup2.io.IVisitedElement;


/**
 * An automaton consists of states and edges
 * connecting the states.
 * 
 * @author Andreas Wenger
 */
public class Automaton<I extends Item, S extends State<I>>
implements IVisitedElement
{
	
	private HashSet<S> states = new HashSet<S>();
	private HashSet<Edge> edges = new HashSet<Edge>();
	private S startState = null;
	
	
	public Automaton(S startState)
	{
		states.add(startState);
		this.startState = startState;
	}
	
	
	public HashSet<S> getStates()
	{
		return states;
	}
	
	
	public HashSet<Edge> getEdges()
	{
		return edges;
	}
	
	
	public S getStartState()
	{
		return startState;
	}
	
	public void visited(IAutomatonVisitor visitor)
	{
		visitor.visit(this);
	}
	
	
	/**
	 * Gets all edges starting at the given state.
	 * This implementation is not optimized and runs in O(n).
	 */
	public LinkedList<Edge> getEdgesFrom(S state)
	{
		LinkedList<Edge> ret = new LinkedList<Edge>();
		for (Edge edge : edges)
		{
			if (edge.getSrc().equals(state))
				ret.add(edge);
		}
		return ret;
	}
	
	/** 
	 * Gets the edge arriving in the given state.
	 * This implementation is not optimized and runs in O(n).
	 */
	public List<Edge> getEdgeTo(State<?> state)
	{
		LinkedList<Edge> ret = new LinkedList<Edge>();
		for (Edge edge : edges)
		{
			State<?> dest = edge.getDest();
			if (dest!=null && dest.equals(state))
				ret.add(edge);
		}
		return ret;
	}
	

}
