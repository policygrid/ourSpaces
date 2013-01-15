package bak;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFWriter;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.shared.PrefixMapping;

public class Text2RDFNew {
	File fFile;
	
	public static void main(String [] args){
		
		String rdffile="/Users/kapila/Documents/repository/rdfs/cullinanebricksandclicks.rdf";
		Text2RDFNew tr =new Text2RDFNew("/Users/kapila/Documents/repository/parsed/cullinane-parsed.txt");
		try{
		tr.saveToRdf(rdffile,"cullinane");
		}catch (Exception e)
		{System.out.println(e.getMessage());}
	}
	
	public Text2RDFNew(String aFileName){
	    fFile = new File(aFileName);  
		
		
	}
	
	
	public final void saveToRdf(String outFile,String auth) throws FileNotFoundException {
	    //Note that FileReader is used, not File, since File is not Closeable
		   Object[] grObjs=  {"ncsubj","xcomp","ccomp","ncmod","aux","dobj","det","iobj","dobj","det","iobj","dobj","passive","ncsubj","xmod","iobj","dobj","ncmod","conj","det","ncmod","ccomp","ccomp","xmod","ncmod","dobj","ta"};
		   List grList =Arrays.asList(grObjs);
		
		  
		   
		   
	    Scanner scanner = new Scanner(new FileReader(fFile));
	    try {
	      //first use a Scanner to get each line
	    	 String wordURI    = "http://"+auth+"/word#";	
			 String ruleURI    = "http://rasp/rule:";	
			 			 
			 
			 String SXMLNS = "http://"+auth+"/word#";
				
			
				// The base
			String BASE = "http://example.com/my-base#";
			Model model = ModelFactory.createDefaultModel();
			
			 
			 RDFWriter writer = model.getWriter();
			 OutputStream out = new FileOutputStream(outFile);
			 PrefixMapping  pf =model.setNsPrefix("gr",ruleURI);
			 PrefixMapping  pf2 =model.setNsPrefix("word",wordURI);
			 
			int id=0;
	      while ( scanner.hasNextLine() ){
	    	  id++;
	    	  try {
	    	  String aLine=scanner.nextLine();
	    	  //System.out.println(aLine);
	    	  aLine=aLine.replace('%', 'p');
	    	  String triple=aLine.replace("| _ |", ",").replace("| |",",").replace("|)", "").replace("(|", "");
	    	  String[] parts= triple.split(",");
	    	  
	    	  //&& ( aLine.contains("VV0") ||aLine.contains("_II"))
	    	 if (grList.contains(parts[0].trim())){
	    		 
	    		 //System.out.println("*"+aLine);
	        
	        	 String w1=parts[1].trim().split(":")[0];
	        	 String w2=parts[2].trim().split(":")[0];
	        	 //System.out.println("*"+w1+"="+w2);
	        	 w1=w1.replace("+",":");
	        	 w1=w1.split(":")[0];
	        	 w1=w1.replaceAll("[^\\p{L}\\p{N}]", "");
	        	 
	        	 
	        	 w2=w2.replace("+",":");
	        	 w2=w2.split(":")[0];
	        	 w2=w2.replaceAll("[^\\p{L}\\p{N}]", "");
	        	 //System.out.println("*"+w1+"+"+w2);
	        	 
	        	 Resource NODETYPE = ResourceFactory.createResource(SXMLNS + w1);
	        	 Resource NODETYPE2 = ResourceFactory.createResource(SXMLNS + w2);
	        	 String strid=String.valueOf(id);
	    	  Resource wordnode = model.createResource(BASE + strid, NODETYPE);
	    	  Resource wordnode2 = model.createResource(BASE + strid, NODETYPE2);
	    	  wordnode.addProperty(model.createProperty(pf.getNsPrefixURI("gr")+parts[0].trim()), wordnode2);
	    	  
	    	  
	    	 
	    	  }
	    	  }
	    	  catch (Exception e)
	    	  { 
	    		  
	    		  
	    	  }
	      }
	    	
	      writer.write(model,out,"http://example.org/");
	      
	      
	      
	      //model.write(System.out);
	      model.write(System.out, "RDF/XML-ABBREV", BASE);
	      
	    }
	    finally {
	      //ensure the underlying stream is always closed
	      //this only has any effect if the item passed to the Scanner
	      //constructor implements Closeable (which it does in this case).
	      scanner.close();
	    }
	  }
	
	
	
	  private static void log(Object aObject){
		    System.out.println(String.valueOf(aObject));
		  }
}
