package edu.tum.cup2.generator;

import java.io.File;

import org.junit.Test;

import edu.tum.cup2.spec.CUPSpecification;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.parser.tables.*;
import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.test.*;


/**
 * Unit tests for {@link LALR1NSEGenerator}
 * 
 * This class compares the parsing tables created by the {@link LALR1NSEGenerator}
 * with the parsing tables created by the {@link LR1toLALRGenerator}.
 * 
 * @author Michael Hausmann
 * @author Daniel Altmann
 * @author Andreas Wenger
 *
 */
public class LALR1NSEGeneratorTest 
{
	/**
	 * The main method of the LALR1GeneratorTest class
	 * can be called from the command line and gets passed any
	 * CUPSpecification name that is used for the comparison test.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		//make sure there is a class Name passed
		if(args.length != 1)
		{
			System.err.println("This program requires exactly one argument. "
					+ "This must be the name of a CUPSpecification which is going to be used to compare the parsing tables created with the LALR1Generator with the LR1toLALRGenerator.");
			return;
		}
		String strSpecName = args[0];
		
		//create Spec with Reflection
		CUPSpecification spec = createSpec(strSpecName);
		if(spec == null) return;
		
		try{
			lalrTest(spec, ".html");
		}
		catch(Exception e)
		{
			System.err.println("An exception of type " 
					+ e.getClass().getSimpleName() + " occured: " 
					+ e.getMessage() + "; " + e.getCause());
		}
		
	}
	
	/**
	 * createSpec 
	 * creates a CUPSpecification with Reflection
	 * @param specName Name of the Specification to create
	 * @return CUPSpecification
	 */
	private static CUPSpecification createSpec(String specName)
	{
		Class<CUPSpecification> specClass = null;
		//get the class object for the specName
		try {
			Class aClass = Class.forName(specName);
			specClass = aClass.asSubclass(CUPSpecification.class);
		} catch(ClassNotFoundException e) {
			System.err.println("The class for the passed CUPSpecification could not be found.");
		} catch(Exception e)
		{
			System.err.println("The argument was not a valid CUPSpecification name!");
		}
		//having found the class, try to instantiate it
		try {
			CUPSpecification spec = specClass.newInstance();
			return spec;
		} catch(IllegalAccessException e) {
			System.err.println("The default construction of " + specName + " could not be accessed.");
		} catch(InstantiationException e) {
			System.err.println(specName + " could not be instantiated!");
		}
		return null;
	}

	private static void lalrTest(CUPSpecification spec, String strFileNameTail) throws GeneratorException
	{
		String strFileName1 = spec.getClass().getSimpleName();
		String strFileName2 = spec.getClass().getSimpleName();
		if(strFileName1 == null)
		{
			strFileName1 = "";
			strFileName2 = "";
		}
		strFileName1 += ".table1" + strFileNameTail;
		strFileName2 += ".table2" + strFileNameTail;
		
		//create an LALR1NSEGenerator
		long timeStart = System.currentTimeMillis();
		LALR1NSEGenerator generator = new LALR1NSEGenerator(spec);
		long timeStop = System.currentTimeMillis();
		System.out.println(spec.getClass().getSimpleName() + " - LALR1NSEGenerator: " + (timeStop - timeStart) + " ms");
		//get the parse table from the LALR1NSEGenerator and create a parser with it
		LRParsingTable tblLALR1 = generator.getParsingTable();
		new LRParser(tblLALR1);
		//dump the parse table
		LRParsingTableDump.dumpToHTML(
				tblLALR1, 
				new File(strFileName1));
		
		//create an LALR1Generator for comparison
		timeStart = System.currentTimeMillis();
		LALR1Generator generator2 = new LALR1Generator(spec);
		timeStop = System.currentTimeMillis();
		System.out.println(spec.getClass().getSimpleName() + " - LALR1Generator: " + (timeStop - timeStart) + " ms");
		LRParsingTable tbl_NSE_LALR1 = generator2.getParsingTable();
		new LRParser(tbl_NSE_LALR1);
		LRParsingTableDump.dumpToHTML(
				tbl_NSE_LALR1, 
				new File(strFileName2));
		
		LRParsingTableTest.assertEquals(tbl_NSE_LALR1, tblLALR1);
			
	}
	
	private static void lalrTest(CUPSpecification spec) throws GeneratorException
	{
		lalrTest(spec, ".html");
	}
	
	/**
	 * Unit Test for LALRGenerator
	 * @throws Exception
	 */
	@Test public void lalrTestSpecCalc4() throws Exception
	{
		SpecCalc4 spec = new SpecCalc4(); //Spec for Test
		lalrTest(spec);
	}
	
	/**
	 * Unit Test for LALRGenerator
	 * @throws Exception
	 */
	@Test public void lalrTestSpecCalc1() throws Exception
	{
		SpecCalc1 spec = new SpecCalc1(); //Spec for Test
		lalrTest(spec);
	}
	
	/**
	 * Unit Test for LALRGenerator using the grammar for C
	 * @throws Exception
	 */
	@Test public void lalrTestSpecC() throws Exception
	{
		CUPSpecification spec = new TranslatedCSpec2(); //Spec for Test
		lalrTest(spec);
	}
	
	/**
	 * Unit Test for LALRGenerator with the PHP Spec
	 * @throws Exception
	 */
	@Test public void lalrTestSpecPHP() throws Exception
	{
		CUPSpecification spec = new TranslatedPHPSpec(); //Spec for Test
		lalrTest(spec);
	}
	
}
