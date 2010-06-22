package compiler;

import edu.tum.cup2.grammar.*;
import edu.tum.cup2.scanner.*;

import static compiler.MiniJavaSpec.NonTerminals.*;
import static compiler.MiniJavaSpec.Terminals.*;

import static minijava.Constants.*;


/**
 * This class is a scanner for the {@link MiniJavaSpec} grammar.
 * It was generated by JFlex, using the file MiniJavaSpec.jflex.
 *
 * @author Andreas Wenger
 */
%%

%class MiniJavaLexer
%cup2

%{
  private void error(String message)
  {
    System.out.println("Error at line "+(yyline+1)+", column "+(yycolumn+1)+" : "+message);
  }
%} 

Ident = [a-zA-Z$_] [a-zA-Z0-9$_]*

IntLiteral = 0 | [1-9][0-9]*

BoolLiteral = true | false

new_line = \r|\n|\r\n;

white_space = {new_line} | [ \t\f]

%%


/* keywords */
"int"             { return token(TYPE, new Integer(INTTYPE)); }
"if"              { return token(IF); }
"else"            { return token(ELSE); }
"while"           { return token(WHILE); }
"read"            { return token(READ); }
"write"           { return token(WRITE); }
  
/* string literals */

/* char literal */

/* bool literal */
{BoolLiteral} { return token(BOOLCONST, new Boolean(Boolean.parseBoolean(yytext()))); }

/* literals */
{IntLiteral} { return token(INTCONST, new Integer(Integer.parseInt(yytext()))); }

/* names */
{Ident}           { return token(IDENT, yytext()); }



/* separators */
";"               { return token(SEMICOLON); }
","               { return token(COMMA); }
"("               { return token(LPAR); }
")"               { return token(RPAR); }
"{"               { return token(BEGIN); }
"}"               { return token(END); }
"="               { return token(ASSIGN); }
"+"               { return token(BINOP,  new Integer(PLUS)); }
"-"               { return token(BINOP,  new Integer(MINUS)); }
"*"               { return token(BINOP,  new Integer(MULT)); }
"/"               { return token(BINOP,  new Integer(DIV)); }
"%"               { return token(BINOP,  new Integer(MOD)); }
"<="              { return token(COMP,   new Integer(LEQ)); }
">="              { return token(COMP,   new Integer(GTQ)); }
"=="              { return token(COMP,   new Integer(EQ)); }
"!="              { return token(COMP,   new Integer(NEQ)); }
"<"               { return token(COMP,   new Integer(LE)); }
">"               { return token(COMP,   new Integer(GT)); }
"&&"              { return token(BBINOP, new Integer(AND)); }
"||"              { return token(BBINOP, new Integer(OR )); }
"!"               { return token(BUNOP); }



{white_space}     { /* ignore */ }


/* error fallback */
.|\n              {  /* throw new Error("Illegal character <"+ yytext()+">");*/
		    error("Illegal character <"+ yytext()+">");
                  }