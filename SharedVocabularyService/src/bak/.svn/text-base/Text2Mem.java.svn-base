package bak;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFWriter;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.shared.PrefixMapping;

public class Text2Mem {
	File fFile;
	
	public static void main(String [] args){
		
		String rdffile="/Users/kapila/Documents/repository/rdfs/cullinanebricksandclicks.rdf";
		Text2Mem tr =new Text2Mem("/Users/kapila/Documents/repository/parsed/cullinane-parsed.txt");
		try{
		List l=tr.buildModel(rdffile,"cullinane");
		
		Iterator<GR> iterator = l.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		
		System.out.println(l.size());
		}catch (Exception e)
		{System.out.println(e.getMessage());}
	}
	
	public Text2Mem(String aFileName){
	    fFile = new File(aFileName);  
		
		
	}
	
	
	public final List <GR> buildModel(String outFile,String auth) throws FileNotFoundException {
	    //Note that FileReader is used, not File, since File is not Closeable
		List <GR> temp= new ArrayList <GR>();
		Object[] grObjs=  {"ncsubj","ncmod","dobj"};
		   List grList =Arrays.asList(grObjs);	  
		   
		   
	    Scanner scanner = new Scanner(new FileReader(fFile));
	    try {
	      //first use a Scanner to get each line
	    	
			 
			
	      while ( scanner.hasNextLine() ){
	    	
	    	  String aLine=scanner.nextLine();
	    	  //System.out.println(aLine);
	    	  aLine=aLine.replace('%', 'p');
	    	  String triple=aLine.replace("| _ |", ",").replace("| |",",").replace("|)", "").replace("(|", "");
	    	  String[] parts= triple.split(",");
	    	  
	    	  
	    	 if (grList.contains(parts[0].trim())){
	    		 
	    		 //System.out.println("*"+aLine);
	        
	        	 String w1=parts[1].trim().split(":")[0];
	        	 String w2=parts[2].trim().split(":")[0];
	        	 String gr=parts[0].trim();
	        	 //System.out.println("*"+w1+"="+w2);
	        	 w1=w1.replace("+",":");
	        	 w1=w1.split(":")[0];
	        	 w1=w1.replaceAll("[^\\p{L}\\p{N}]", "");
	        	 
	        	 
	        	 w2=w2.replace("+",":");
	        	 w2=w2.split(":")[0];
	        	 w2=w2.replaceAll("[^\\p{L}\\p{N}]", "");
	        	 
	        	 
	        	 GR gr1 =new GR();
		    	  gr1.setW1(w1);
		    	  gr1.setGR(gr);
		    	  gr1.setW2(w2);
	        	 temp.add(gr1);
	        	 //System.out.println(gr1.toString());
	    	 }
	      }
	    	
	   
	      return temp;
	      
	      
	    
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
