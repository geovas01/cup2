package edu.tum.cup2.io;

import edu.tum.cup2.generator.LALR1Generator;
import edu.tum.cup2.generator.LR1toLALRGenerator;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.spec.CUP2Specification;
import edu.tum.cup2.test.SpecCalc1;
import edu.tum.cup2.test.SpecCalc4;

import org.junit.Test;


/**
 * JUnit test that tests the dot input generated by the
 * GraphBuilderVisitor class 
 * 
 * @author Michael Hausmann
 *
 */
public class GraphBuilderTest 
{
	/**
	 * The graphTest method
	 * creates an LALR1Generator and an LR1toLALR1Generator
	 * and compares the output delivered by the 
	 * GraphBuilderVisitor and saves the GraphViz input
	 * as well as the automaton graph to file. 
	 */
	private static void graphTest(CUP2Specification spec) 
		throws GeneratorException
	{
		String strSpecName = spec.getClass().getSimpleName();
		if(strSpecName == null)
		{
			strSpecName = "";
		}
		
		//create an LALR1Generator
		LALR1Generator generatorLALR1 = new LALR1Generator(spec);
		
		//visit automaton structure and generate dot input
		GraphBuilderVisitor gbvLALR1 = new GraphBuilderVisitor();
		gbvLALR1.visit(generatorLALR1.getAutomaton());
		gbvLALR1.saveGraphVizInput(strSpecName + ".LALR1.dot");
		
		//create an LR1toLALRGenerator for comparison
		LR1toLALRGenerator generatorLR1toLALR = new LR1toLALRGenerator(spec);
		
		//visit automaton structure and generate dot input
		GraphBuilderVisitor gbvLR1toLALR = new GraphBuilderVisitor();
		gbvLR1toLALR.visit(generatorLR1toLALR.getAutomaton());
		gbvLR1toLALR.saveGraphVizInput(strSpecName + ".LR1toLALR.dot");
	
		//compare GraphViz Input
		//Note: this is a string compare that requires
		//the input to match 100%
		//theoretically also an isomorphic automaton is correct! 
		assert(
				gbvLR1toLALR.buildGraphVizInput()
				.equals(
				gbvLALR1.buildGraphVizInput()
			));
	}
	
	/**
	 * Test automaton for SpecCalc4
	 * @throws Exception
	 */
	@Test public void lalrTestSpecCalc4() throws Exception
	{
		SpecCalc4 spec = new SpecCalc4(); //Spec for Test
		graphTest(spec);
	}
	
	/**
	 * Test automaton for SpecCalc1
	 * @throws Exception
	 */
	@Test public void lalrTestSpecCalc1() throws Exception
	{
		SpecCalc1 spec = new SpecCalc1(); //Spec for Test
		graphTest(spec);
	}
}