package edu.tum.cup2.spec {

import edu.tum.cup2.grammar.{NonTerminal, Terminal, Symbol, Production}
import edu.tum.cup2.spec.util.{RHSSymbols, RHSItem}
import edu.tum.cup2.semantics.{Action, SymbolValue}


class ScalaSampleSpec extends CUPSpecification with ScalaCUPSpecification {
  
  /**
   * Enum workaround in Scala for Terminal-Definition
   */
  object Terminals extends SymbolEnum {
    val SEMI, PLUS, TIMES, LPAREN, RPAREN, NUMBER = TerminalEnum
  }
  
  /**
   * Enum workaround in Scala for NonTerminal-Definition
   */
  object NonTerminals extends SymbolEnum {
    val expr, res = NonTerminalEnum
  }
  
  // make the enum-Values available
  import NonTerminals._, Terminals._
  
  
  // tell parent class what (non)terminals exist
  val terminals = Terminals
  val nonTerminals = NonTerminals
  
  
  //symbols with values
  class NUMBER extends SymbolValue[Int]{ }
  class expr   extends SymbolValue[Int]{ }
  class res    extends SymbolValue[Int]{ }
  
  
  //precedences
  precedences(left(TIMES), left(PLUS))
  
  // specify grammar
  grammar(
    // wanted
    production(res, expr ~ SEMI ^^ { (x : Int) => x }),
    production(expr, NUMBER ^^ { (x : Int) => x } |
		     expr ~ PLUS ~ expr ^^ { (x : Int, y : Int) => x + y} |
		     expr ~ TIMES ~ expr ^^ { (x : Int, y : Int) => x * y} |
		     LPAREN ~ expr ~ RPAREN ^^ { (x : Int) => x }

    
    // CUP2-like - works
//    production(res, expr ~ SEMI ^^ { (x : Int) => x }),
//    production(expr, NUMBER ^^ new Action { def a(x : Int) = x } |
//    		     expr ~ PLUS ~ expr ^^ new Action { def a(x : Int, y : Int) = x + y} |
//    		     expr ~ TIMES ~ expr ^^ new Action { def a(x : Int, y : Int) = x * y} |
//    		     LPAREN ~ expr ~ RPAREN ^^ new Action { def a(x : Int) = x }
    
    // Workaround - works
//    production(res, expr ~ SEMI ^^ { (x : Int) => x }),
//    production(expr, NUMBER ^^* ((x : Int) => x) |
//    		     expr ~ PLUS ~ expr ^^** ( (x : Int, y : Int) => x + y) |
//    		     expr ~ TIMES ~ expr ^^** ( (x : Int, y : Int) => x * y) |
//    		     LPAREN ~ expr ~ RPAREN ^^* ( (x : Int) => x )
    )
  )
}

}