package edu.tum.cup2.generator.exceptions;

import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.parser.actions.Reduce;
import edu.tum.cup2.parser.actions.Shift;

/**
 * ConsecutiveNonAssocException
 * Exception that is raised when there are two consecutive 
 * equal precedence terminals 
 * 
 * @author Michael Hausmann
 */
public class ConsecutiveNonAssocException extends ShiftReduceConflict
{

	public ConsecutiveNonAssocException(Shift shift, Reduce reduce, Terminal terminal)
	{
		super(shift, reduce, terminal);
	}
	
	@Override public String getMessage()
	{
		String msg = super.getMessage();
		msg = "The existance of two or more consecutive terminals with equal precedence that are declared \"nonassoc\" lead to: " + msg;
		return msg;
	}
	
}
