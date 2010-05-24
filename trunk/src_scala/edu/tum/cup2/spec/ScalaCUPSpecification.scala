package edu.tum.cup2.spec {

import edu.tum.cup2.grammar.{NonTerminal, Terminal, Symbol, Production}
import edu.tum.cup2.spec.util.{RHSSymbols, RHSItem}
import edu.tum.cup2.util.{Reflection}
import edu.tum.cup2.semantics.{Action}

import collection.JavaConversions._


/**
 * Scala extension for CUPSpecification to allow grammar definitions in scala.
 * 
 * The specification will be in a the way of the ll parser combinators which are
 * included in the standard scala library. But in combination with the cup2
 * framework it will be possible to create a grammar for lr parsing, which allows
 * more language grammars to be parsed and therefore is mightier.
 * 
 * To use it, just mix this trait in your spec-class which already inherits from
 * CUPSpecification. 
 * 
 * @author matthias
 */
trait ScalaCUPSpecification { self : CUPSpecification =>

  /** Define the enum with Terminals-Symbols */
  val terminals : SymbolEnum
  /** Define the enum with NonTerminals-Symbols */
  val nonTerminals : SymbolEnum
  
  /** Make it easy to access terminals as Array */
  def terminalsArr : Array[Terminal] = Array[Terminal]() ++ (terminals.values map ( _.asInstanceOf[Terminal] ))
  /** Make it easy to access nonterminals as Array */
  def nonTerminalsArr = Array[NonTerminal]() ++ (nonTerminals.values map ( _.asInstanceOf[NonTerminal] ))
      
  
  /**
   * Override this method of super-class to get rid of the in scala non-available
   * enums as well as the somewhat nasty reflection usage.
   */
  override def init() = {
    println("INIT")
    if (! isInit) {
      symbolValueClasses = Reflection.getSymbolValueClasses(terminalsArr, nonTerminalsArr)
      //inited
      isInit = true
    }
  }
  
  /**
   * Just a redirect so that you can use scala collection type Seq for definition.
   */
  def production(nt : NonTerminal, items : Seq[RHSItem]) = self.prod(nt, items : _*)
  
  
  /**
   * Override Method getTerminals() from CUPSpecification to avoid terminal-determination
   * over reflection.
   */
  override def getTerminals() : Array[Terminal] = terminalsArr
  /**
   * Override Method getNonTerminals() from CUPSpecification to avoid nonterminal-determination
   * over reflection.
   */
  override def getNonTerminals() : Array[NonTerminal] = nonTerminalsArr
  

  
  implicit def noterm2prod(nt : NonTerminal) = new { def ->(l : Seq[RHSItem]) = production(nt, l) }
  
  implicit def item2items(s : Seq[RHSItem]) = new ParserCombinator(s)
  implicit def sym2syms(s : RHSSymbols) = new RHSSymbolsExt(s)
  implicit def sym2syms(s : Symbol) = new RHSSymbolsExt(new RHSSymbols(s))
  
  /**
   * Make it possible that every Symbol can be part of a parser combinator. That
   * means that it can be used to construct a production for a grammar.
   */
  class ParserCombinator(i : Seq[RHSItem]) {
    def | (o : Seq[RHSItem]) : Seq[RHSItem]       = i ++ o
  }

  /**
   * Extends RHSSymbols class to be combinable.
   */
  class RHSSymbolsExt(i : RHSSymbols) {
    def ~ (s : Symbol) : RHSSymbols               = new RHSSymbols( (i.getSymbols() :+ s) : _* )
    //def <~ (s : Symbol) : RHSSymbols               = i
    //def ~> (s : Symbol) : RHSSymbols               = new RHSSymbols(s)
    
    def ^^ (a : Action) : Seq[RHSItem]            = Seq(i, a)
//    def ^^[A,X](f : A => X) : Seq[RHSItem]        = ^^(new { def a(a) = f(a) } with Action)
//    def ^^[A,B,X](f : (A,B) => X) : Seq[RHSItem]        = ^^(new Action { def a = f })
    
    def ^^[A,X](f : A => X) : Seq[RHSItem]        = ^^(new ScalaAction[A,X](f)) 
    def ^^[A,B,X](f : (A,B) => X) : Seq[RHSItem]  = ^^(new Scala2Action[A,B,X](f))
    
//    def ^^(f : _ => _) : Seq[RHSItem]        = ^^(new Action() { def a = f(_) }) 
//    def ^^[A,B,X](f : (_,_) => _) : Seq[RHSItem]  = ^^(new Action() { def a = f(_,_) })
  
    def ^^*(f : Int => Int) : Seq[RHSItem]        = ^^(new ScalaIntAction(f)) 
    def ^^**(f : (Int,Int) => Int) : Seq[RHSItem] = ^^(new ScalaInt2Action(f))
  }
  
  
  /**
   * Reduce-Actions should be defined as functions, but at the moment thats
   * impossible. Thats because of type erasure and reflection-method search
   * within cup2.
   */
  class ScalaIntAction(f : Int => Int) extends Action { def a(a : Integer) : Integer = f(a.intValue) }
  class ScalaInt2Action(f : (Int,Int) => Int) extends Action { def a(a : Integer, b : Integer) : Integer = f(a.intValue,b.intValue) }
  
  class ScalaSmartAction[A,X](f : A => X)(implicit m1 : ClassManifest[A], implicit m2 : ClassManifest[X]) 
      extends Action {
    // implement a smart way to get the A and X at runtime
	// ...
  }
  
  
//  class ScalaAction[A,X](f : A => X) extends Action { def a = f(_) }
//  class Scala2Action[A,B,X](f : (A,B) => X) extends Action { def a = f(_,_) }
  
  // try specialized annotation
  class ScalaAction[A,X](f : A => X) extends Action { def a(a : A) = f(a) }
  class Scala2Action[A,B,X](f : (A,B) => X) extends Action { def a(a : A, b : B) = f(a, b) }
  
}

}