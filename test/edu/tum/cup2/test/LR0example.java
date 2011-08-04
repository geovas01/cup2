package edu.tum.cup2.test;

import edu.tum.cup2.generator.LR0Generator;
import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.generator.exceptions.ShiftReduceConflict;
import edu.tum.cup2.io.GraphBuilderVisitor;
import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.tables.LRParsingTable;
import static edu.tum.cup2.test.LR0example.NonTerminals.*;
import static edu.tum.cup2.test.LR0example.Terminals.*;
import edu.tum.cup2.spec.CUP2Specification;
import java.io.File;
import org.junit.Test;
import static junit.framework.Assert.fail;


/**
 * Das ist eine Grammtik, die zwar LR1, aber nicht LALR1 ist.
 * 
 * @author Daniel Altmann
 */
public class LR0example extends CUP2Specification
{


	public enum Terminals implements edu.tum.cup2.grammar.Terminal
	{
		star, id;
	}
	
	
	public enum NonTerminals implements edu.tum.cup2.grammar.NonTerminal
	{
		S, E, L, R;
	}

	public LR0example() {
		grammar(prod(S,rhs(E,E)),
                        prod(E,rhs( L,L )),
			prod(L, rhs(star,L),rhs(id))
		);
	}
      @Test public void testLR1()
	{
		try
		{
                        LR1Generator gen = new LR1Generator(this);
			LRParsingTable table = gen.getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File(this.getClass().getName()+"-LR1.html"));
                        GraphBuilderVisitor gbvLALR1 = new GraphBuilderVisitor();
                        gbvLALR1.visit(gen.getAutomaton());
                        gbvLALR1.saveGraphVizInput(this.getClass().getName()+"-LR1.dot");

		}
		catch (ShiftReduceConflict ex)
		{
			fail("No Shift/reduce conflict expected!");
                        //ok
		}
		catch (GeneratorException ex)
		{
			fail("Wrong exception");
		}
	}	
	      @Test public void testLR0()
	{
		try
		{
                        LR0Generator gen = new LR0Generator(this);
			LRParsingTable table = gen.getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File(this.getClass().getName()+"-LR0.html"));
                        GraphBuilderVisitor gbvLALR1 = new GraphBuilderVisitor();
                        gbvLALR1.visit(gen.getAutomaton());
                        gbvLALR1.saveGraphVizInput(this.getClass().getName()+"-LR0.dot");

		}
		catch (ShiftReduceConflict ex)
		{
			fail("No Shift/reduce conflict expected!");
                        //ok
		}
		catch (GeneratorException ex)
		{
			fail("Wrong exception");
		}
	}	

}
