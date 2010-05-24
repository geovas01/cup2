package edu.tum.cup2.generator.exceptions;


/**
 * Exception for a reduce/reduce conflict.
 * 
 * @author Andreas Wenger
 */
public class ReduceReduceConflict
	extends GeneratorException
{
	
	String word = null;
	private String msg;
	
	public ReduceReduceConflict() {}
	
	
	public ReduceReduceConflict(String message) {
		this.msg = message;
	}


	public void setWord(String word) {
		this.word = word;
	}	
	
	@Override
	public String getMessage() {
		return (msg==null?"":msg+" -- ")+(word==null?"":"Example word: " + word);
	}
	

}
