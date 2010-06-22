package edu.tum.cup2.parser.exceptions;

import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.parser.states.LRParserState;
import edu.tum.cup2.scanner.ScannerToken;

/**
 * 
 * 	This exception indicates, that an error was encountered in the input
 *  but error-recovery failed due to missing error-recovery productions.
 *  
 **/

public class MissingErrorRecoveryException extends LRParserException {
	
	private final LRParserState state;
	private final ScannerToken<? extends Object> token;
	
	/**
	 * Creates a new {@link MissingErrorRecoveryException}.
	 * @param message   a message.
	 * @param state     the current state of the parser
	 * @param terminal  the terminal that was read
	 */
	public MissingErrorRecoveryException(String message, LRParserState state, ScannerToken<? extends Object> token)
	{
		super(message+" State: " + state.getID() + ", Token: " + token);
		this.state = state;
		this.token = token;
	}
	
}