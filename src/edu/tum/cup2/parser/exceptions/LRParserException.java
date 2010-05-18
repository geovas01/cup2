package edu.tum.cup2.parser.exceptions;


/**
 * This exception is thrown when an error
 * occurs while parsing.
 * 
 * @author Andreas Wenger
 */
public class LRParserException
	extends Exception
{
	
	public LRParserException(String message)
	{
		super(message);
	}

}
