package edu.tum.cup2.parser.states;


/**
 * Missing state within the goto table.
 * 
 * @author Andreas Wenger
 */
public class ErrorState
	extends LRParserState
{

	public ErrorState()
	{
		super(-1); //does not matter
	}
	
	
	@Override public String toString()
	{
		return "";
	}

}
