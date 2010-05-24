package edu.tum.cup2.generator.exceptions;


/**
 * Base class for all exceptions thrown by a
 * parser generator.
 * 
 * @author Andreas Wenger
 */
public class GeneratorException
	extends Exception
{
	
	public GeneratorException()
	{
	}
	
	
	public GeneratorException(String message)
	{
		super(message);
	}	

}
