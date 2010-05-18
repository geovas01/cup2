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
 * Unit Test for LALRGenerator
 * This class compared the parsing table created by the LALR1Generator
 * with the parsing table created through the LR1toLALR1Generator
 * 
 * @author Michael Hausmann
 * @author Daniel Altmann
 *
 */
public class LALR1GeneratorTest 
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
	
	/**
	 * The lalrTest method
	 * 
	 * @param spec
	 * @param strFileNameTail
	 * @throws GeneratorException
	 */
	private static void lalrTest(CUPSpecification spec, String strFileNameTail) throws GeneratorException
	{
		String strSpecName = spec.getClass().getSimpleName();
		if(strSpecName == null)
		{
			strSpecName = "";
		}
		String strFileNameLALR = strSpecName + ".LALR" + strFileNameTail;
		String strFileNameWeak = strSpecName + ".Weak" + strFileNameTail;
		String strFileNameLR1  = strSpecName + ".LR1toLALR" + strFileNameTail;
		
		//create an LALR1Generator
		LALR1Generator generator = new LALR1Generator(spec);
		//get the parse table from the LALR1Generator and create a parser with it
		LRParsingTable tblLALR1 = generator.getParsingTable();
		LRParser parser = new LRParser(tblLALR1);
		//dump the parse table
		LRParsingTableDump.dumpToHTML(
				tblLALR1, 
				new File(strFileNameLALR));
		
		//create an WeakCompatGenerator
		WeakCompatGenerator genWeakCompat = new WeakCompatGenerator(spec);
		LRParsingTable tblgenWeakCompat = genWeakCompat.getParsingTable();
		LRParser parserWeakCompat = new LRParser(tblgenWeakCompat);
		LRParsingTableDump.dumpToHTML(
				tblgenWeakCompat, 
				new File(strFileNameWeak));
		
		
		//create an LR1toLALRGenerator for comparison
		LR1toLALRGenerator generatorLR1toLALR = new LR1toLALRGenerator(spec);
		LRParsingTable tblLR1toLALR = generatorLR1toLALR.getParsingTable();
		LRParser parserLR1toLALR = new LRParser(tblLR1toLALR);
		LRParsingTableDump.dumpToHTML(
				tblLR1toLALR, 
				new File(strFileNameLR1));
		
		LRParsingTableTest.assertEquals(tblLR1toLALR, tblLALR1);
		LRParsingTableTest.assertEquals(tblgenWeakCompat, tblLALR1);
			
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
