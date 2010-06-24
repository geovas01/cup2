package edu.tum.cup2.spec.scala {

import edu.tum.cup2.grammar.{NonTerminal, Terminal, Symbol, Production}
import edu.tum.cup2.spec.util.{RHSSymbols, RHSItem}
import edu.tum.cup2.util.{Reflection}
import edu.tum.cup2.semantics.{Action, SymbolValueClasses, SymbolValue}
import edu.tum.cup2.spec.exceptions.{IllegalSpecException}

import collection.JavaConversions._


/**
 * Reduce-Actions should be defined as functions. That is done over extending
 * the Action-Class and doing a redirection to the constructor-attribute f.
 * 
 * This parameter have to be a function. Since there is no abstract type for 
 * function, you can provide any class which has an appropriate apply-Method.
 */
class ScalaAction(val f : AnyRef) extends Action {
  
  /**
   * Override init()-Method to apply all operations on function f
   * instead of an inner method a().
   * 
   * @see Action#init
   */
  override def init(myclass : Class[Action]) : Unit = {
	// define local class
	actionSubclass = myclass
	if(myclass == null) return
	
	// search for apply-method in function f, thats the method
	// of this action
	for(m <- f.getClass().getMethods()) {
	  if(m.getName() == "apply") {
	 	// again, define local variable and break, cause everything is init
        this.method = m
        this.paramsCount = m.getParameterTypes().size
	 	this.returnsVoid = m.getReturnType().equals(Void.TYPE)
	 	return
	  }
	}
	
	throw new IllegalSpecException("Action has no function f, respectively an apply()-method")
  }
  
  /**
   * Override doAction() to invoke method on function f with the parameters. They must have been
   * set via setParameters() previously.
   */
  override def doAction(parameters : Array[Object]) : Object = {
    val method = getMethod()
	val ret = method.invoke(f, parameters : _*)
	if (isVoidReturn()) return SymbolValue.NoValue
	else return ret
  }
}

}