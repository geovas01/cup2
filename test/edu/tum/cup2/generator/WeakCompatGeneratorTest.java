package edu.tum.cup2.generator;

import java.io.File;

import org.junit.Test;

import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.tables.LRParsingTableTest;
import edu.tum.cup2.spec.*;
import edu.tum.cup2.spec.CUPSpecification;
import edu.tum.cup2.spec.LR1butNotLALR1;
import edu.tum.cup2.spec.WeakCompatibleLR1TestSpec;
import edu.tum.cup2.test.SpecJava14;
import edu.tum.cup2.test.SpecMiniJava;


/*
 * * Unit Test for WeakCompatGenerator
 * 
 * @author Michael Hausmann
 * @author Daniel Altmann
 */
public class WeakCompatGeneratorTest
{

	/**
	 * Unit Test for WeakCompatGenerator
	 * @throws Exception
	 */
	@Test
	public void testLR1butNotLALR1() throws Exception
	{

		CUPSpecification spec = new LR1butNotLALR1(); //Spec for Test
		String s = "LR1butNotLALR1";
    s = "Weak_Test__" + s;
		plain_lr_Test(spec, s);

	}
	
	/**
	 * Unit Test for WeakCompatGenerator
	 * @throws Exception
	 */
	@Test
	public void test2LR1butNotLALR1() throws Exception
	{

		CUPSpecification spec = new AlteredLR1butNotLALR1(); //Spec for Test
		String s = "AlteredLR1butNotLALR1";
    s = "Weak_Test__" + s;
		plain_lr_Test(spec, s);

	}	

	
	/**
	 * Unit Test for WeakCompatGenerator
	 * @throws Exception
	 */
	@Test
	public void test3LR1butNotLALR1() throws Exception
	{

		CUPSpecification spec = new MiddleSizeLR1butNotLALR1(); //Spec for Test
		String s = "MiddleSizeLR1butNotLALR1";
    s = "Weak_Test__" + s;
		plain_lr_Test(spec, s);

	}	

	/**
	 * Unit Test for WeakCompatGenerator
	 * @throws Exception
	 */
	@Test
	public void test4LR1butNotLALR1() throws Exception
	{

		CUPSpecification spec = new MiddleSize2LR1butNotLALR1(); //Spec for Test
		String s = "MiddleSize2LR1butNotLALR1";
    s = "Weak_Test__" + s;
		plain_lr_Test(spec, s);

	}
	

	/**
	 * Unit Test for WeakCompatGenerator
	 * @throws Exception
	 */
	@Test
	public void testWeakCompatibleLR1TestSpec() throws Exception
	{

		CUPSpecification spec = new WeakCompatibleLR1TestSpec(); //Spec for Test
		String s = "WeakCompatibleLR1TestSpec";
		s = "Weak_Test__" + s;
    lr_lalr_Test(spec, s);		
		
	}


	/**
	 * Unit Test for WeakCompatGenerator
	 * @throws Exception
	 */
	@Test
	public void testJava14() throws Exception
	{

		CUPSpecification spec = new SpecJava14(); //Spec for Test
		String s = "SpecJava14";
		s = "Weak_Test__" + s;
		lr_lalr_Test(spec, s);
		
	}


	/**
	 * Unit Test for WeakCompatGenerator
	 * @throws Exception
	 */
	@Test
	public void testMiniJava() throws Exception
	{

		CUPSpecification spec = new SpecMiniJava(); //Spec for Test
		String s = "MiniJava";
		s = "Weak_Test__" + s;
		lr_lalr_Test(spec, s);

	}

	public void lr_lalr_Test(CUPSpecification spec, String s) throws Exception
	{

		//create a WeakCompatGenerator
		LALR1relatedGenerator weak_generator = new WeakCompatGenerator(spec);
		//get the parse table from the LALR1Generator and create a parser with it
		LRParser weak_parser = new LRParser(weak_generator.getParsingTable());
		//dump the parse table
		LRParsingTableDump.dumpToHTML(weak_generator.getParsingTable(), new File(s
			+ ".weak.test.cup2.html"));
		//create an LALR1Generator
		LALR1Generator lalr_generator = new LALR1Generator(spec);
		//get the parse table from the LALR1Generator and create a parser with it
		LRParser lalr_parser = new LRParser(lalr_generator.getParsingTable());
		//dump the parse table
		LRParsingTableDump.dumpToHTML(lalr_generator.getParsingTable(), new File(s
			+ ".lalr.test.cup2.html"));
		//create an LR1Generator
		LR1Generator lr1_generator = new LR1Generator(spec);
		//get the parse table from the LALR1Generator and create a parser with it
		LRParser lr1_parser = new LRParser(lr1_generator.getParsingTable());
		//dump the parse table
		LRParsingTableDump.dumpToHTML(lr1_generator.getParsingTable(), new File(s
			+ ".lr1.test.cup2.html"));

		if (lr1_generator.getParsingTable().getStatesCount() == weak_generator
			.getParsingTable().getStatesCount())
		{
			LRParsingTableTest.assertEquals(lr1_generator.getParsingTable(), weak_generator
				.getParsingTable());
		}
		if (lalr_generator.getParsingTable().getStatesCount() == weak_generator
			.getParsingTable().getStatesCount())
		{
			LRParsingTableTest.assertEquals(lalr_generator.getParsingTable(), weak_generator
				.getParsingTable());
		}

	}
	
	public void plain_lr_Test(CUPSpecification spec, String s) throws Exception
	{

		//create a WeakCompatGenerator
		LALR1relatedGenerator weak_generator = new WeakCompatGenerator(spec);
		//get the parse table from the LALR1Generator and create a parser with it
		LRParser weak_parser = new LRParser(weak_generator.getParsingTable());
		//dump the parse table
		LRParsingTableDump.dumpToHTML(weak_generator.getParsingTable(), new File(s
			+ ".weak.test.cup2.html"));
		//create an LR1Generator
		LR1Generator lr1_generator = new LR1Generator(spec);
		//get the parse table from the LALR1Generator and create a parser with it
		LRParser lr1_parser = new LRParser(lr1_generator.getParsingTable());
		//dump the parse table
		LRParsingTableDump.dumpToHTML(lr1_generator.getParsingTable(), new File(s
			+ ".lr1.test.cup2.html"));

		if (lr1_generator.getParsingTable().getStatesCount() == weak_generator
			.getParsingTable().getStatesCount())
		{
			LRParsingTableTest.assertEquals(lr1_generator.getParsingTable(), weak_generator
				.getParsingTable());
		}

	}
}
