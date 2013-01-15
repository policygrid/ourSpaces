package svs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.Vector;



public class ReadWithScanner {

  public static void main(String args[]) throws FileNotFoundException {
	  //int option =Integer.parseInt(args[0]);
	  
//	  ReadWithScanner parser = new ReadWithScanner("/Users/kapila/Documents/repository/parsed/cullinanebricksandclicks.txt");
//	  parser.processLineByLine();
//	  
	  ReadWithScanner parser = new ReadWithScanner("/Users/kapila/Documents/repository/rdfs/culliane.rdf");
	  
	
	  
	  //	  if (option==1){
//		  parser.processLineByLine();
//		  log("Done.");
//	  }
//		  else if (option==2){
//			  parser.buildCollection();
//		  } else {
//			  parser.queryRDF();
//		  }
		  
  }
  
  /**
   Constructor.
   @param aFileName full name of an existing, readable file.
  */
  public ReadWithScanner(String aFileName){
    fFile = new File(aFileName);  
  }
  
  /** Template method that calls {@link #processLine(String)}.  */
  public final void processLineByLine() throws FileNotFoundException {
    //Note that FileReader is used, not File, since File is not Closeable
	   Object[] grObjs=  {"ncsubj","xcomp","ccomp","ncsubj","ncmod","aux","dobj","det","ncmod","dobj","iobj","dobj","det","iobj","dobj","passive","ncsubj","xmod","iobj","dobj","ncmod","conj","conj","conj","conj","det","ncmod","ccomp","ncsubj","ccomp","ncsubj","ncmod","ncmod","dobj","ncmod","ncmod","ncmod","dobj","ta","ncmod","ncmod","dobj","ncmod"};
	   List grList =Arrays.asList(grObjs);
	
	  
	   
	   
    Scanner scanner = new Scanner(new FileReader(fFile));
    try {
      //first use a Scanner to get each line
    	 String wordURI    = "http://rasp/word:";	
		 String ruleURI    = "http://rasp/rule:";	
		 OutputStream out = new FileOutputStream("/Users/kapila/Documents/repository/rdfs/culliane" + ".rdf");
			 int i=0;
		
      while ( scanner.hasNextLine() ){
    	  try {
    	  String aLine=scanner.nextLine();
    	  aLine=aLine.replace('%', 'p');
    	  String triple=aLine.replace("| |",",").replace("|)", "").replace("(|", "");
    	  String[] parts= triple.split(",");
    	  
    	  //&& ( aLine.contains("VV0") ||aLine.contains("_II"))
    	 if (grList.contains(parts[0])){
    		 
        	  log( scanner.nextLine() );
        
        	 String w1=parts[1].trim().replace(':','-');
        	 String w2=parts[2].trim().replace(':','-');
    	  
    	    	  
    	  
    	  i++;
    	  }
    	  }
    	  catch (Exception e)
    	  { 
    		  
    		  
    	  }
      }
    	
         
    }
    finally {
      //ensure the underlying stream is always closed
      //this only has any effect i§f the item passed to the Scanner
      //constructor implements Closeable (which it does in this case).
      scanner.close();
    }
  }
  
  
   
  /** 

  */
  protected void processLine(String aLine){
    //use a second Scanner to parse the content of each line 
    Scanner scanner = new Scanner(aLine);
    scanner.useDelimiter("| |");
    if ( scanner.hasNext() ){
      String name = scanner.next();
      String value = scanner.next();
      log("Name is : " + quote(name.trim()) + ", and Value is : " + quote(value.trim()) );
    }
    else {
      log("Empty or invalid line. Unable to process.");
    }
    //no need to call scanner.close(), since the source is a String
  }
  
  // PRIVATE 
  private final File fFile;
  
  private static void log(Object aObject){
    System.out.println(String.valueOf(aObject));
  }
  
  private String quote(String aText){
    String QUOTE = "'";
    return QUOTE + aText + QUOTE;
  }
} 

