package edu.tum.cup2.test.lr1blowup;

import org.junit.Test;

import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.spec.CUP2Specification;
import edu.tum.cup2.test.Time;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.test.lr1blowup.Spec3Prec.NonTerminals.*;
import static edu.tum.cup2.test.lr1blowup.Spec3Prec.Terminals.*;


/**
 * Grammar to test LR(1)-blowup.
 * 
 * @author Andreas Wenger
 */
public class Spec3Prec
	extends CUP2Specification
{

	
	//terminals
	public enum Terminals implements Terminal
	{
		TERMINAL, LBR, RBR, OP1, OP2, OP3
	}
	

	//non-terminals
	public enum NonTerminals implements NonTerminal
	{
		term
	}

	
	public Spec3Prec()
	{
		precedences(left(OP3, OP2, OP1));
		grammar(
			prod(term,
					          rhs(TERMINAL),
					          rhs(LBR, term, RBR),
					          rhs(term, OP1, term),
					          rhs(term, OP2, term),
					          rhs(term, OP3, term))	            
		);
	}
	
	
	@Test public void testTime()
	{
		Time.measureTime(new Spec3Prec());
	}


}