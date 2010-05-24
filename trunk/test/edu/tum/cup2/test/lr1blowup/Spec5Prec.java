package edu.tum.cup2.test.lr1blowup;

import org.junit.Test;

import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.spec.CUPSpecification;
import edu.tum.cup2.test.Time;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.test.lr1blowup.Spec5Prec.NonTerminals.*;
import static edu.tum.cup2.test.lr1blowup.Spec5Prec.Terminals.*;


/**
 * Grammar to test LR(1)-blowup.
 * 
 * @author Andreas Wenger
 */
public class Spec5Prec
	extends CUPSpecification
{

	
	//terminals
	public enum Terminals implements Terminal
	{
		TERMINAL, LBR, RBR, OP1, OP2, OP3, OP4, OP5
	}
	

	//non-terminals
	public enum NonTerminals implements NonTerminal
	{
		term
	}

	
	public Spec5Prec()
	{
		precedences(left(OP5, OP4, OP3, OP2, OP1));
		grammar(
			prod(term,
					          rhs(TERMINAL),
					          rhs(LBR, term, RBR),
					          rhs(term, OP1, term),
					          rhs(term, OP2, term),
					          rhs(term, OP3, term),
					          rhs(term, OP4, term),
					          rhs(term, OP5, term))	            
		);
	}
	
	
	@Test public void testTime()
	{
		Time.measureTime(new Spec5Prec());
	}


}