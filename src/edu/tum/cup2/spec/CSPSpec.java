package edu.tum.cup2.spec;

/*
 * This is the grammar from the CSP parser generator.
 * I think it is likely to be LR(1)
 * see http://sourceforge.net/projects/csparser/
 */

/*

; CSP LR(1) Parser Generator Grammar
; This is the grammar to read in grammars.
; Basically, it specifies how to read in itself.

expr:
  { expr1 } 

expr1:
  EOL
  prodname prodlist

prodname:
  ID _action_ProdName ':' EOL

prodlist:
  prodlist1 { prodlist1 }

prodlist1:
  STL expr2 EOL 

expr2:
  production_list _action_FinalizeProduction
  '@' _action_Epsilon opt_epsilon _action_LinkEpsAction _action_FinalizeProduction

opt_epsilon:
  . '_action_' ID opt_epsilon _action_EpsAction
  @

production_list:
  production_item opt_prod _action_ProdCatenate

opt_prod:
  . production_list _action_ProdMove
  @

production_item:
  '#' [ ID ] '#' [ NUMBER ] '#' regex '#' [ #STRING##!([^\#\\]+|\\.)*#String# ] '#' _action_RegExToken
  '\'' #TOKEN##!([^'\\]+|\\.)*#String# _action_EscapeString _action_StringToken '\'' ; this is a single quoted string.
  PRODACTID _action_ID ; production name or action rountine.
  '{' . production_item . production_item_list '}' _action_ProdCond
  '[' . production_item . production_item_list ']' _action_ProdCond
  '.' _action_Spaces

production_item_list:
  production_item . production_item_list _action_ProdCatenate
  @

regex:
  ###!!([^\#\\]+|\\.)*#Regular Expression# ; regex
  #STRING##!([^\#\\]+|\\.)*#String# _action_EscapeString ; this is a string.

PRODACTID:
  #PRODACTID##!(?:_action_)?[_a-zA-Z][_a-zA-Z0-9]*#Production or action ID#

ID:
  #ID##![_a-zA-Z][_a-zA-Z0-9]*#ID# ; ID only supports _ letters and 

SPACES:
  ###![ \t]+#Space#

EOL:
  #EOL##![ \t]*(;[^\r\n]*)?[\r\n]+#End of line#

STL:
  ###![ \t]*##

NUMBER:
  #NUMBER##![0-9]+#Number#

 */

public class CSPSpec
{

}
