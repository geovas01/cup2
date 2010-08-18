package edu.tum.cup2.test;

import static edu.tum.cup2.test.AlteredLR1butNotLALR1.NonTerminals.*;
import static edu.tum.cup2.test.AlteredLR1butNotLALR1.Terminals.*;
import edu.tum.cup2.spec.CUP2Specification;


/**
 * Das ist eine Grammtik, die zwar LR1, aber nicht LALR1 ist.
 * 
 * @author Daniel Altmann
 */
public class AlteredLR1butNotLALR1 extends CUP2Specification
{


	public enum Terminals implements edu.tum.cup2.grammar.Terminal
	{
		a, b, e;
	}
	
	
	public enum NonTerminals implements edu.tum.cup2.grammar.NonTerminal
	{
		S, E, F, E_tick;
	}

	public AlteredLR1butNotLALR1() {
		grammar(
			prod(S, rhs(a, E, a)),
			prod(S, rhs(b, E_tick, b)),
			prod(S, rhs(a, F, b)),
			prod(S, rhs(b, F, a)),
			prod(E_tick, rhs(e)),
			prod(E, rhs(e)),
			prod(F, rhs(e))
		);
	}

}