# cup2
(http://www2.in.tum.de/cup2)

Welcome to CUP2 beta
This is a development version of the upcoming new CUP release! It will include

LR(0)
canonical LR(1)
LALR(1)
Pager-LR(1)
and a few other parsing kernels. It will feature a pure-Java syntax and supports a modular object oriented architecture, advanced error handling, symbol region information and a more sophisticated ANT-task, and last but not least: Finally Unit-Tests for the components.

The most recent documentation document can be found  here

You can obtain the recent sources via

svn checkout https://www2.in.tum.de/repos/cup2/trunk
CUP2 is  Zlib-licenced

Current status: We managed to implement

LR-Table-Parsing-Driver
LR(0)/LR(1)/LALR(1) engines
Serialisiation of parsetable
JFlex-CUP2 patch
Productive and unreachable nonterminal optimisation
Grammar-Specification API
ANT-task
Token completion
preliminary documentation
random word generator
Implemented context propagation links
Automated conflict examples
Parallelized automaton generation algorithms via threadpool
specialised algorithm for First/Follow?-Sets based on strongly connected components
Still to do:

feed JFlex-patch back
CUP1toCUP2
typeinference instead of declaring SymbolValue?<XXX>-class
Pager-LR(1)
Refactor unary productions automatically
Enhanced Profiling and optimisation of parsing engines
Attribute grammars and attribute evaluation
LL(k) or LL(*) generators
JFlex-API interface
Enjoy! 
