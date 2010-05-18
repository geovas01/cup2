package edu.tum.cup2.generator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import org.junit.Test;

import edu.tum.cup2.generator.items.Item;
import edu.tum.cup2.generator.items.LR1Item;
import edu.tum.cup2.generator.states.LR1NSEState;
import edu.tum.cup2.generator.states.LR1State;
import edu.tum.cup2.generator.states.State;
import edu.tum.cup2.spec.CUPSpecification;
import edu.tum.cup2.test.SpecMiniJava;

import static org.junit.Assert.*;


/**
 * Test class for {@link Automaton}.
 * 
 * @author Andreas Wenger
 */
public class AutomatonTest
{
	
	
	/**
	 * Asserts that that the two given {@link Automaton}s behave equally.
	 * If not, the test fails and the first differing state is printed.
	 */
	@SuppressWarnings("unchecked")
	public static void testEquals(Automaton a1, Automaton a2)
	{
		//we could test for equal number of states first, but we want to
		//find the first state that is different, so we don't do that test here.
		
		//equal start state?
		assertEquals(a1.getStartState(), a2.getStartState());
		
		//create map between states of a1 and a2
		HashMap<State, State> statesMap = new HashMap<State, State>();
		statesMap.put(a1.getStartState(), a2.getStartState());
		
		//queue: begin with start state of a1
		LinkedList<State> queue = new LinkedList<State>();
		queue.add(a1.getStartState());
		
		//set of already checked states
		HashSet<State> finished = new HashSet<State>();
		
		//for each state in queue: find equal state in a2. then, look at all edges and
		//find their equivalents. target states are added to the queue and are suspected to be equal
		//(this is is tested as soon as the states are dequeued).
		while (!queue.isEmpty())
		{
			State s1 = queue.getFirst();
			
			//find equal state in a2
			State s2 = null;
			for (Object s : a2.getStates())
			{
				if (s.equals(s1))
				{
					s2 = (State) s;
					break;
				}
			}
			if (s2 == null)
			{
				fail("No equivalent state in for: " + s1);
			}
			finished.add(s1);
			
			//number of outgoing edges must be equal
			LinkedList<Edge> e1l = a1.getEdgesFrom(s1);
			LinkedList<Edge> e2l = a1.getEdgesFrom(s2);
			if (e1l.size() != e2l.size())
			{
				fail("Number of outgoing edges is different. State: " + s1);
			}
			
			//follow edges
			for (Edge e1 : e1l)
			{
				//find equivalent edge in e2
				Edge e2 = null;
				for (Edge e : e2l)
				{
					if (e.getSymbol() == e1.getSymbol())
					{
						e2 = e;
						break;
					}
				}
				if (e2 == null)
				{
					fail("No edge with symbol " + e1.getSymbol() + " at state: " + s2);
				}
				
				//target states must be equal. if still unknown, queue.
				State ts1 = e1.getDest();
				State ts2 = e2.getDest();
				if (!ts1.equals(ts2))
				{
					fail("Edge with symbol " + e1.getSymbol() + " leads to different states at state: " + s1);
				}
				else if (!queue.contains(ts1) && finished.contains(ts1))
				{
					queue.add(ts1);
				}
			}
			
		}
		
	}
	
	
	@Test public void testTestEquals()
		throws Exception
	{
		/* doesn't work yet because of side effects in automaton factory...
		CUPSpecification spec = new SpecMiniJava();
		GrammarInfo grammarInfo = new GrammarInfo(spec.getGrammar());
		//we know that LALR1NSEAutomatonFactory and LALR1AutomatonFactory produce the same automatons
		LALR1NSEAutomatonFactory f1 = new LALR1NSEAutomatonFactory();
		LALR1NSEGenerator g1 = new LALR1NSEGenerator(spec);
		Automaton<LR1Item, LR1NSEState> a1 = f1.createAutomaton(g1, grammarInfo);
		LALR1AutomatonFactory f2 = new LALR1AutomatonFactory();
		LALR1Generator g2 = new LALR1Generator(spec);
		Automaton<LR1Item, LR1State> a2 = f2.createAutomaton(g2, grammarInfo);
		*/
		//testEquals(a1, a2);
	}
	

}
