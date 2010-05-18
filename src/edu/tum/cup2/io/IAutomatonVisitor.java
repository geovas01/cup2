package edu.tum.cup2.io;

import edu.tum.cup2.generator.Automaton;
import edu.tum.cup2.generator.Edge;
import edu.tum.cup2.generator.items.Item;
import edu.tum.cup2.generator.states.LR0State;
import edu.tum.cup2.generator.states.LR1State;
import edu.tum.cup2.generator.states.State;

public interface IAutomatonVisitor 
{
	void visit(Automaton a);
	void visit(Edge e);
	void visit(State s);
	
}
