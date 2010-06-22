package edu.tum.cup2.test.lr1blowup;

import org.junit.Test;

import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.spec.CUP2Specification;
import edu.tum.cup2.test.Time;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.test.lr1blowup.Spec2NoPrec.NonTerminals.*;
import static edu.tum.cup2.test.lr1blowup.Spec2NoPrec.Terminals.*;

/**
 * Grammar to test LR(1)-blowup.
 * 
 * @author Andreas Wenger
 */
public class Spec2NoPrec
	extends CUP2Specification
{

	
	//terminals
	public enum Terminals implements Terminal
	{
		TERMINAL, LBR, RBR, OP1, OP2
	}
	

	//non-terminals
	public enum NonTerminals implements NonTerminal
	{
		term, brackets, op1, op2
	}

	
	public Spec2NoPrec()
	{
		grammar(
			prod(term,
                    rhs(op1)),
      prod(op1,
                    rhs(op2),
                    rhs(op1, OP1, op2)),
			prod(op2,
				            rhs(brackets),
				            rhs(op2, OP2, brackets)),
			prod(brackets,
					          rhs(TERMINAL),
					          rhs(LBR, term, RBR))	            
		);
	}
	
	
	@Test public void testTime()
	{
		Time.measureTime(new Spec2NoPrec());
	}


}