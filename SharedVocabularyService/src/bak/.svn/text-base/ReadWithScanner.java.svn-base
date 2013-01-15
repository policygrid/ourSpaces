package bak;

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


import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFWriter;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.util.FileManager;

import com.ibm.icu.text.SimpleDateFormat;


public class ReadWithScanner {

  public static void main(String args[]) throws FileNotFoundException {
	  //int option =Integer.parseInt(args[0]);
	  
//	  ReadWithScanner parser = new ReadWithScanner("/Users/kapila/Documents/repository/parsed/cullinanebricksandclicks.txt");
//	  parser.processLineByLine();
//	  
	  ReadWithScanner parser = new ReadWithScanner("/Users/kapila/Documents/repository/rdfs/culliane.rdf");
	  parser.queryRDF();
	  
	
	  
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
		 Model model = ModelFactory.createDefaultModel();
		 RDFWriter writer = model.getWriter();
		 OutputStream out = new FileOutputStream("/Users/kapila/Documents/repository/rdfs/culliane" + ".rdf");
		 PrefixMapping  pf =model.setNsPrefix("gr",ruleURI);
		 PrefixMapping  pf2 =model.setNsPrefix("word",wordURI);
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
    	  
    	  Resource wordnode = model.createResource(pf2.getNsPrefixURI("word")+w1);
    	  Resource wordnode2 = model.createResource(pf2.getNsPrefixURI("word")+w2);
    	  wordnode.addProperty(model.createProperty(pf.getNsPrefixURI("gr")+parts[0].trim()), wordnode2);
    	  
    	  
    	  i++;
    	  }
    	  }
    	  catch (Exception e)
    	  { 
    		  
    		  
    	  }
      }
    	
      writer.write(model,out,"http://example.org/");
      
      
      
      model.write(System.out);
      
    }
    finally {
      //ensure the underlying stream is always closed
      //this only has any effect i§f the item passed to the Scanner
      //constructor implements Closeable (which it does in this case).
      scanner.close();
    }
  }
  
  protected void buildCollection() throws FileNotFoundException {
	  //Note that FileReader is used, not File, since File is not Closeable
	    Scanner scanner = new Scanner(new FileReader(fFile));
	    try {
	      //first use a Scanner to get each line
	    	 String wordURI    = "http://rasp/word:";	
			 String ruleURI    = "http://rasp/rule:";	
			 Model model = ModelFactory.createDefaultModel();
		int i=0;
			 ArrayList <GR> doc=new ArrayList();
	      while ( scanner.hasNextLine() ){
	    	  try {
	    	  String aLine=scanner.nextLine();
	    	  aLine=aLine.replace('%', 'p');
	    	  //&& ( aLine.contains("VV0") ||aLine.contains("_II"))
	    	 //if ((aLine.contains("obj"))&& ( aLine.contains("VV0") ||aLine.contains("_II"))){
	    		 if ((aLine.contains("obj")||(aLine.contains("conj")) ||(aLine.contains("aux")) ||(aLine.contains("det")) ||(aLine.contains("mod")) ||(aLine.contains("sub")) ||(aLine.contains("comp")))) {
	    		// processLine( scanner.nextLine() );
	        	  log( scanner.nextLine() );
	        	  //buildVector(scanner.nextLine() );
	        	 
	    	  String triple=aLine.replace("| |",",").replace("|)", "").replace("(|", "");
	    	  String[] parts= triple.split(",");
	    	  
	    	  GR gr =new GR();
	    	  gr.setW1(parts[1].trim().split(":")[0]);
	    	  gr.setGR(parts[0].trim());
	    	  gr.setW2(parts[2].trim().split(":")[0]);
	    	 doc.add(gr);
	    	      	  
	    	  
	    	  i++;
	    	  }
	    	  }
	    	  catch (Exception e)
	    	  { 
	    		  
	    		  
	    	  }
	      }
	      Iterator it=doc.iterator();
	      int countw1gr=0;
	      int countgrw2=0;
	      int countgrgoods=0;
	      int countgr=0;
	      int countw1grw2=0;
	      while (it.hasNext()) {
	    	  GR gr =(GR)it.next();
	    	 if (gr.getLeft().contains("buy") && gr.getGr().contains("obj") && !(gr.getRight().contains("and")||gr.getRight().contains("at")||gr.getRight().contains("it"))){
	    		 System.out.println(gr.getRight());
	    	  countw1gr++;
	    	   ;
	    	  }
	      if (gr.getRight().contains("online") && gr.getGr().contains("obj")){
	    	  countgrw2++;
	    	   ;
	    	  }
	      if (gr.getRight().contains("goods") && gr.getGr().contains("obj")){
	    	  countgrgoods++;
	    	   ;
	    	  }
	      if ( gr.getGr().contains("obj")){
	    	  countgr++;
	    	   ;
	    	  }
	      
	      if (gr.getLeft().contains("buy") && gr.getGr().contains("obj")&& gr.getRight().contains("online")){
	    	  countw1grw2++;
	    	   ;
	    	  }
	      
	    }
	      System.out.println();
	      System.out.println("*********************");
	      System.out.println("|| buy , obj, * ||-"+countw1gr);
	      System.out.println("||*, obj online ||-"+countgrw2);
	      System.out.println("||*, obj goods ||-"+countgrgoods);
	      System.out.println("|| *, obj, * ||-"+countgr);
	      System.out.println("|| buy, obj ,online ||-"+countw1grw2);
	      double x= Math.log10((countw1grw2*countgr)/(countw1gr*countgrw2));
	      System.out.println(x);
	      System.out.println("====================");
	     
	      countw1gr=0;
	      countgrw2=0;
	      int countgrfood=0;
	      countgr=0;
	      countw1grw2=0;
	      it=doc.iterator();
	      while (it.hasNext()) {
	    	  GR gr =(GR)it.next();
	    	 if (gr.getLeft().contains("purchase") && gr.getGr().contains("obj") && !(gr.getRight().contains("and")||gr.getRight().contains("at")||gr.getRight().contains("of")||gr.getRight().contains("it")||gr.getRight().contains("p"))){
	    		 System.out.println(gr.getRight());
	    	  countw1gr++;
	    	   ;
	    	  }
	      if (gr.getRight().contains("online") && gr.getGr().contains("obj")){
	    	  countgrw2++;
	    	   ;
	    	  }
	      if (gr.getRight().contains("Food") && gr.getGr().contains("obj")){
	    	  countgrfood++;
	    	   
	    	  }
	      if ( gr.getGr().contains("obj")){
	    	  countgr++;
	    	   ;
	    	  }
	      
	      if (gr.getLeft().contains("purchase") && gr.getGr().contains("obj")&& gr.getRight().contains("online")){
	    	  countw1grw2++;
	    	   ;
	    	  }
	      
	    }
	      System.out.println();
	      System.out.println("*********************");
	      System.out.println("|| purchase , obj, * ||-"+countw1gr);
	      System.out.println("||*, obj online ||-"+countgrw2);
	      System.out.println("||*, obj food ||-"+countgrfood);
	      System.out.println("|| *, obj, * ||-"+countgr);
	      System.out.println("|| purchase, obj ,online ||-"+countw1grw2);
	       x= Math.log10((countw1grw2*countgr)/(countw1gr*countgrw2));
	      System.out.println(x);
	      
	      
	    }
	    finally {
	      //ensure the underlying stream is always closed
	      //this only has any effect i§f the item passed to the Scanner
	      //constructor implements Closeable (which it does in this case).
	      scanner.close();
	    }
	  
  
  }
  
  public final void ReadRDFModel(){
		
	// create an empty model
	 
	  String strFileName="/Users/kapila/Documents/repository/rdfs/culliane.rdf";
	  Model model = ModelFactory.createDefaultModel();
	  // use the FileManager to find the input file
	  InputStream in = FileManager.get().open( strFileName );
	 if (in == null) {
	     throw new IllegalArgumentException(
	                                  "File: " + strFileName + " not found");
	 }

	 // read the RDF/XML file
	 model.read(in, null);

	 // write it to standard out
	 model.write(System.out);
		
	}
  
  
  public final Model buildRdfModel(String dir, String[] rdfFiles){
		
		
		// Create an empty in-memory model and populate it from the graph
			Model model = ModelFactory.createMemModelMaker().createDefaultModel();
			
		try {
			for (int i=0;i<rdfFiles.length;i++){
			InputStream in = new FileInputStream(new File(dir+"/"+rdfFiles[i]));
			model.read(in,null); // null base URI, since model URIs are absolute
		

		
			in.close();
			}
			
		} catch (Exception ex ){
		}
		return model;
  }
public final void queryRDF(){
	// Open the bloggers RDF graph from the filesystem
	String strFileName="/Users/kapila/Documents/repository/rdfs/culliane.rdf";
	// Create an empty in-memory model and populate it from the graph
		Model model = ModelFactory.createMemModelMaker().createDefaultModel();
		
	try {
		InputStream in = new FileInputStream(new File(strFileName));
		model.read(in,null); // null base URI, since model URIs are absolute
	

	
	in.close();
	} catch (Exception ex ){
	}
	// Create a new query
	String queryString = "prefix gr: <http://rasp/rule>" +
						"SELECT (count(*) as ?no) " +
						"WHERE { ?s <http://rasp/rule:dobj> ?o .  }";

	
	
		

		
		

	Query query = QueryFactory.create(queryString,Syntax.syntaxARQ);

	// Execute the query and obtain results
	QueryExecution qe = QueryExecutionFactory.create(query, model);
	ResultSet results = qe.execSelect();
	System.out.println(results.hasNext());
	QuerySolution qs;
	// Output query results	
	while (results.hasNext()){
		qs=results.nextSolution();
		System.out.println(qs.get("no"));
	}
	// Important - free up resources used running the query
	qe.close();
	
	
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

