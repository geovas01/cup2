package edu.tum.cup2.generator.states;

import java.util.LinkedList;
import java.util.List;

import edu.tum.cup2.generator.NullableSet;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Production;
import edu.tum.cup2.grammar.Symbol;
import edu.tum.cup2.grammar.Terminal;

public class LR1Helper {

    NullableSet nSet;

    public LR1Helper(LinkedList<Production> productions) {
        this.nSet = new NullableSet(productions);
    }

    public boolean isNullable(List<Symbol> the_list) {
        boolean ret = true;
        for (Symbol s : the_list) {
            if (s instanceof Terminal) {
                ret = false;
                break;
            } else if (s instanceof NonTerminal) {
                if (nSet.contains(s)) {
                    continue;
                } else {
                    ret = false;
                    break;
                }
            } else {
                throw new IllegalArgumentException(
                        "Symbol should be Terminal or Nonterminal and nothing else.");
            }
        }
        return ret;
    }

}
