package edu.tum.cup2.ant;

import java.io.File;
import java.util.LinkedList;

import org.apache.tools.ant.*;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.taskdefs.Javac;

import edu.tum.cup2.generator.*;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.generator.exceptions.ReduceReduceConflict;
import edu.tum.cup2.generator.exceptions.ShiftReduceConflict;
import edu.tum.cup2.grammar.CheckedGrammar;
import edu.tum.cup2.grammar.IGrammar;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.io.LRParserSerialization;
import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.spec.CUPSpecification;
import edu.tum.cup2.spec.exceptions.IllegalSpecException;

/**
 * Custom Cup2 Compile Task for Ant
 * The Task can be used to create a parser using Cup2 with a 
 * CUPSpecification.
 * Therefore it creates the class files for the parser with
 * the help of the javac task and creates the cup2 file (serialization of the parser)
 * using the LRParserSerializer class.
 * 
 * Required Attributes for this class:
 *  * CUPSpecification
 *  * Path to Spec
 *  * Path of the cup2 class files
 *  
 *  TODOs:
 *  -parser algorithmus als attribut
 *  -classpath als nested elements
 *  -flag zum ausgeben der parsing tabelle
 *  (graphviz)
 *  -cup2 serialisierung nur wenn timestamp von spec neuer (spec sich ge�ndert hat)
 *  -debug modus (verbose = on)
 *  -erreichbarkeits/produktivit�tscheck aufrufen
 *  
 *  TODOs 18.08.2009:
 *  
 *  
 *  
 *  Example with all attributes:
 *  <cup2 Cup2Specification="com.foo.bar.myspec"
 *        SpecSrcDir=".../com/foo/bar" --where is the file with the spec? (without filename)
 *        Cup2classdirectory="..."     --where to get/place the cup2 class files          
 *        Cup2SourcePath="path_to_my_cup2_sources"  --when specified, also (re-)compiled
 *        SpecDestDir="..."            --where to store the spec's class file
 *        Cup2File="xxx.cup2"          --where to store the serialized parser
 *        Algorithm="lr1"|"lr0"|"lalr1"
 *        verbose=on|off
 *        PrintParsingTable="path_to_output_file"
 *  >
 *     <classpath>                     --additional dependencies
 *     		<pathelement path="xxx"/>
 *     </classpath>
 *  </cup2>
 * 
 * @author Michael Hausmann
 *
 */
public class Cup2AntTask extends org.apache.tools.ant.Task
{
	 private String fCup2SpecificationName = null; //specification name; e.g: edu.tum.cup2.test.SpecCalc4
	 private CUPSpecification fCup2Specification = null; //instantiated specification
	 private String fCup2SpecFile = null; //name of the java file of the specification (without path)
	 private String fCup2FilePath = null; //name of the cup2 file that holds the parser serialization
	 private String fCup2ClassDirectory = null; //path to the class files of cup2
	 private String fSpecDestDir = null; //destination directory for the specification class file
	 private Path fSpecSrcDir = null; //source directory for the specification (where to find the java file)
	 private Path fCup2SourcePath = null; //source path of cup2
	 private Path fClassPath = null; 
	 private String fAlgorithm = null; //generator algorithm; e.g: lr1
	 private boolean fVerbose = false; //print a lot of status messages?
	 private String fPrintParsingTable = null; //where to store the parsing table?
	 
	 /**
	  * executing the cup2 task
	  */
	 public void execute() 
	 {
		  //print parameter settings 
		  if (this.fVerbose) this.displayValues();
		  
		  //check if required attribute CupSpecification is set
	      if (fCup2SpecificationName == null || fCup2SpecificationName.length() == 0) {
	    	log("CUPSpecification missing!", Project.MSG_ERR);   
	      }
		  //check if required attribute Cup2ClassPath is set
	      else if (fCup2ClassDirectory == null || fCup2ClassDirectory.length() == 0) {
	    	log("CUP2ClassDirectory is missing!", Project.MSG_ERR);  
	      }
	      //check if required attribute specsrcdir is set
	      else if (fSpecSrcDir == null) {
	    	log("SpecSrcDir is missing!", Project.MSG_ERR);  
	      } 
	      else {
	    	  //check optional attribute classpath
	    	  if (fClassPath == null)
	    		  log("No classpath set! Is this correct?", Project.MSG_WARN);
	    	  
	    	  //if optional attribute cup2SourcePath is set, compile cup2 sources
	    	  if(fCup2SourcePath != null)
	    	  {
	    		  this.compileCup2();
	    	  }	    	  
	    	  
	    	  //compiling the CUPSpecification
	    	  compileSpec();
	    	  if (fVerbose) log(" === Compiling Spec done ===");
	    	  
	    	  //instantiate CUPSpecification
	    	  try
	    	  {
	    		this.createCup2Specification();  
	    	  }
	    	  catch (InstantiationException e) 
	    	  {
	    		  log("InstantiationException when trying to create Cup2Specification");
				  e.printStackTrace();
			  }
	    	  catch (IllegalSpecException e)
	      	  {
	            log("Cup2 Specification invalid : "+e);
	            e.printStackTrace();
	      	  }
	    	  catch (ClassNotFoundException e) 
	    	  {
				  log("ClassNotFoundException when trying to create Cup2Specification");
				  e.printStackTrace();
			  } 
	    	  catch (IllegalAccessException e) 
	    	  {
				  log("IllegalAccessException when trying to create Cup2Specification");
				  e.printStackTrace();
			  }
			  
			  if(this.fCup2Specification == null)
			  {
				  log("Error: could not create CUPSpecification", Project.MSG_ERR);
			  }
			  else
			  {
				  //Productivity and reachability is checked
				  if (fVerbose) log(" === Checking productivity and reachability ===");
		    	  
				  IGrammar orgGram = this.fCup2Specification.getGrammar();
				  CheckedGrammar cg = new CheckedGrammar(orgGram);
				  if (!cg.isReduced())
				  {
					  log("The grammar is not reduced!", Project.MSG_WARN);
					  if (cg.hasNotProductiveNonTerminals())
					  {
						  log("The grammar contains not productive NonTerminals!", Project.MSG_WARN);
						  LinkedList<NonTerminal> notProductiveNonTerminals = cg.getNotProductiveNonTerminals();
						  log("Not productive are: " + notProductiveNonTerminals.toString());
						  
					  }
					  if (cg.hasNotReachableNonTerminals())
					  {
						  log("The grammar contains not reachable NonTerminals!", Project.MSG_WARN);
						  LinkedList<NonTerminal> notReachableNonTerminals = cg.getNotReachableNonTerminals();
						  log("Not reachable are: " + notReachableNonTerminals.toString());
						  
					  }
				  }
				  
		    	  //create serialization
		    	  LRParsingTable table = null;
		    	  if(fCup2FilePath == null || fCup2FilePath.length() == 0)
		    	  {
		    		  log("File name for cup2 serialization is missing! No serialization will be created!", 
		    				  Project.MSG_WARN);
		    	  }
		    	  else
		    	  {
		    		  try 
		    		  {
		    			if (fVerbose) log(" === Creating parsing table ===");
		    			  
		    			//create Parsing Table
		    			LRGenerator generator = createGenerator(this.fCup2Specification);
		    			table = generator.getParsingTable();
		    			
		    			LRParser parser = new LRParser(table);
		    			if (fVerbose) log(" === Serializing parser ===");
						serializeParser(parser);
					  } 
		    		  catch (ShiftReduceConflict e) 
		    		  {
						log("Shift-Reduce-Conflict during parser generation: " + e.getMessage(), Project.MSG_ERR);
					  }
		    		  catch (ReduceReduceConflict e)
		    		  {
		    			  log("Reduc-Reduce-Conflict during parser generation: " + e.getMessage(), Project.MSG_ERR);
		    		  } 
		    		  catch (GeneratorException e) 
		    		  {
		    			  log("GeneratorException occured during parser generation: " + e.getMessage(), Project.MSG_ERR);
					  }
					  catch (IllegalSpecException e)
  	          		  {
  	            		  log("Cup2 Specification invalid : "+e);
  	            		  e.printStackTrace();
  	          		  }
		    	  }
		    	  
		    	  //print parsing table
		    	  if (this.fPrintParsingTable != null)
		    	  {
		    		  printParsingTable(table, this.fPrintParsingTable);
		    	  }
		      } /*end else*/
	      } /*end else*/
	      if (fVerbose) log(" === Cup2 Ant Task ended ===", Project.MSG_INFO);
	 } /*end of execute*/
	 
	 /**
	  * display all field values
	  */
	 private void displayValues()
	 {
		log("cup2 task - attribute settings: \n ========================", Project.MSG_INFO);
		log("Cup2Specification...: " + this.fCup2SpecificationName);
		log("|--> spec file......: " + this.fCup2SpecFile);
		log("Cup2File............: " + this.fCup2FilePath);
		log("Cup2ClassDirectory..: " + this.fCup2ClassDirectory);
		log("SpecDestDir.........: " + this.fSpecDestDir);
		log("SpecSrcDir..........: " + this.fSpecSrcDir);
		log("Cup2SourcePath......: " + this.fCup2SourcePath);
		log("classpath...........: " + this.fClassPath);
		log("Algorithm...........: " + this.fAlgorithm);
		log("Verbose.............: " + this.fVerbose);
		log("PrintParsingTable...: " + this.fPrintParsingTable);
		log(" ========================");
	 }
	 
	 /**
	  * execute compilation of Cup2 Sources itself
	  */
	 private void compileCup2()
	 {
		 log(" === Compiling cup2 sources ===", Project.MSG_INFO);
		  
		  //set parms
		  Javac jcct = new Javac(); //JavaCup2CompileTask
		  jcct.setSourcepath(fCup2SourcePath);
		  jcct.setSrcdir(fCup2SourcePath);
		  File f = new File(this.fCup2ClassDirectory);
		  jcct.setDestdir(f);
		  jcct.setClasspath(fClassPath);
		  
		  jcct.setProject(this.getProject());
		  
		  //compile cup2 sources
		  jcct.execute();
	 }
	 
	 /**
	  * compile the Cup2Specification
	  */
	 private void compileSpec()
	 {
		  //setting parms for the compile task
	   	  log(" === Creating parser  (Specification = " + this.fCup2SpecificationName + " File = " + this.fCup2SpecFile + ")", Project.MSG_INFO);
	   	  Javac jsct = new Javac(); //JavaSpecCompileTaks  
	   	  jsct.setProject(this.getProject());
	   	  jsct.setSrcdir(fSpecSrcDir);
	   	  jsct.setIncludes(fCup2SpecFile);
	   	
	   	  Path specClassPath = new Path(this.getProject(), this.fCup2ClassDirectory);
	   	  if(this.fClassPath != null)
	   		  specClassPath.add(this.fClassPath);
	   	  jsct.setClasspath(specClassPath);
	   	  
	   	  File specDestDir = null;
	   	  //if attribute specdestdir is specified use it,
	   	  //otherwise use cup2classdirectory as fallback
	   	  if(this.fSpecDestDir != null && this.fSpecDestDir.length() > 0)
	   	  {
	   		  specDestDir = new File(this.fSpecDestDir);  
	   	  }
	   	  else
	   	  {
	   		  specDestDir = new File(this.fCup2ClassDirectory);
	   	  }
	   	  jsct.setDestdir(specDestDir);
	   	  	    	  
	   	  //compile the spec	
	   	  jsct.execute();
	 }
	 
	 /**
	  * create generator depending on algorithm attribute
	  * @return
	  */
	 private LRGenerator createGenerator(CUPSpecification spec) 
	 	throws GeneratorException
	 {
		LRGenerator generator = null;
		if (fVerbose) log("algorithm = " + fAlgorithm);
		if (this.fAlgorithm == null || this.fAlgorithm.equals("lr1"))
		{
			if (fVerbose) log("instantiating lr1 generator...");
			generator = new LR1Generator(spec);
		}
		else if (this.fAlgorithm.equals("lr0"))
		{
			if (fVerbose) log("instantiating lr0 generator...");
			generator = new LR0Generator(spec);
		}
		else if (this.fAlgorithm.equals("lalr1"))
		{
			if (fVerbose) log("instantiating lalr1 generator...");
			generator = new LALR1Generator(spec);
		} 
		return generator;
	 }
	 
	 /**
	  * instantiate the cup2spec with reflection and generate parsing table
	  * 
	  * @throws ClassNotFoundException
	  * @throws InstantiationException
	  * @throws IllegalAccessException
	  */
	 private CUPSpecification createCup2Specification()
	 	throws ClassNotFoundException, 
	 	InstantiationException, 
	 	IllegalAccessException
	 {
		Class<CUPSpecification> c = null;
		CUPSpecification spec = null;

		c = (Class<CUPSpecification>) Class.forName(fCup2SpecificationName);
		spec = (CUPSpecification) c.newInstance();
		this.fCup2Specification = spec;
		return spec;
	 }
	 
	 /**
	  * saves the parsing table to a file
	  * (if the related specification is newer than the cup2 file that is to hold the serialization)
	  */
	 private void serializeParser(LRParser parser) 
	 {
		//create the File object for the java file for the CUPSpecification
		File specFile = null;
		String strSpecFilePath = this.fSpecSrcDir.toString();
		if(!strSpecFilePath.endsWith("/") && !strSpecFilePath.endsWith("\\"))
			strSpecFilePath += "/";
		strSpecFilePath += this.fCup2SpecFile;
		System.out.println(strSpecFilePath);
		specFile = new File(strSpecFilePath);
		
		//create the File object for the cup2 file for the serialization
		String strCup2FilePath = this.fCup2FilePath;
		File cup2File = new File(strCup2FilePath);
		
		if (fVerbose) //if verbose = "on" ==> print files and time stamps
		{
			log("Specification Source File   = " + specFile.getAbsolutePath());
			log("Cup2 File for Serialization = " + cup2File.getAbsolutePath());
			log("Specification - lastModified = " + specFile.lastModified());
			log("Cup2 File     - lastModified = " + cup2File.lastModified());
		}
		
		//check if cup2 file is not older than spec (spec has to be newer)
		if (specFile.lastModified() > cup2File.lastModified() || !cup2File.exists())
		{
			//spec file is newer ==> serialize
			
			if (fVerbose && !cup2File.exists()) log("cup2 file for serialization does not yet exist and is created...");
			else if (fVerbose && specFile.lastModified() > cup2File.lastModified()) log("Spec file is newer than cup2 file... creating cup2 file");
			
			//create LRParserSerialization object
			LRParserSerialization serial = new LRParserSerialization(this.fCup2FilePath);
				
			//serialize parser
			serial.saveParser(parser);
		} /*end if*/
		else if (fVerbose) log("Specification is older than cup2 file... NO SERIALIZATION");
	 }
	 
	 /**
	  * output the parsing table of the LRParser to a file
	  * @param table: Parsing Table of an LRParser
	  * @param fileName: filename for the output
	  */
	 private void printParsingTable(LRParsingTable table, String fileName)
	 {
		 if(this.fVerbose)
		 {
			 log("Printing ParsingTable to: " + fileName);
		 }
		 File file = new File(fileName);
		 LRParsingTableDump.dumpToHTML(table, file); 
	 }
	  
	 public void setSpecDestDir(String s)
	 {
		 this.fSpecDestDir = s;
	 }
	 
	 public String getSpecDestDir()
	 {
		 return this.fSpecDestDir;
	 }
	 
	 public void setSpecSrcDir(Path p)
	 {
		 this.fSpecSrcDir = p;
	 }
	 
	 public Path getSpecSrcDir()
	 {
		 return this.fSpecSrcDir;
	 }
	 
	 public void setCup2SourcePath(Path p)
	 {
		 this.fCup2SourcePath = p;
	 }
	 
	 public Path getCup2SourcePath()
	 {
		 return fCup2SourcePath;
	 }
	 
	 public void setCup2ClassDirectory(String p)
	 {
		 this.fCup2ClassDirectory = p;
	 }
	 
	 public String getCup2ClassDirectory()
	 {
		 return fCup2ClassDirectory;
	 }

	 public void setCup2File(String filePath) {
	      this.fCup2FilePath = filePath;
     }

	 public String getCup2File() {
	      return fCup2FilePath;
	 }

	 public void setCup2Specification(String cupSpecification)
	 {
		 fCup2SpecificationName = cupSpecification;
		 int iLastIndex = fCup2SpecificationName.lastIndexOf(".");
		 try
		 {
			 fCup2SpecFile = cupSpecification.substring(0,iLastIndex)+"/"+cupSpecification.substring(iLastIndex + 1);
		 }
		 catch(IndexOutOfBoundsException e)
		 {
			 fCup2SpecFile = fCup2SpecificationName;
		 }
		 fCup2SpecFile = fCup2SpecFile + ".java";
	 }
	 
	 public String getCup2Specification()
	 {
		 return this.fCup2SpecificationName;
	 }
	 
	 public void setAlgorithm(String s)
	 {
		s = s.trim();
		s = s.toLowerCase();
		this.fAlgorithm = s;
	 }
	 
	 public String getAlgorithm()
	 {
		return fAlgorithm;  
	 }
	 
	 public void setPrintParsingTable(String s)
	 {
		 this.fPrintParsingTable = s;
	 }
	 
	 public String getPrintParsingTable()
	 {
		 return fPrintParsingTable;
	 }
	 
	 public void setVerbose(boolean b)
	 {
		this.fVerbose = b;
	 }
	 
	 public boolean getVerbose()
	 {
		 return this.fVerbose;
	 }
	 
	 public void addConfiguredClassPath(Path cp)
	 {
		this.fClassPath = cp; 
	 }

} /*eoc*/
