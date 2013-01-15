package svs.ex1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;


public class Rasp2Rdf {
	static String SXMLNS = "http://example.com/sxml#";
	  static  String BASE = "http://example.com/my-base#";
	
	public static void main(String [] args){
		
		String parsedfile="/Users/kapila/Documents/repository/parsed/cullinane-bricksandclicks.txt";
		try{
		Model rdfmodel=readParsed(parsedfile, "cullinane");
		rdfmodel.setNsPrefix("sxml", SXMLNS);
		// Now write out using base as BASE
		rdfmodel.write(System.out, "RDF/XML-ABBREV", BASE);
		//model.write(System.out);
		}
		catch (Exception ex)
		{
			
		}
		//Rasp2Rdf tr =new Rasp2Rdf();
		//List <GR> finalist=tr.buildModel("D:/repository");
		//System.out.println("total "+finalist.size());
		
	}
	
	public Rasp2Rdf(){
	    
		
		
	}
	
	public List <GR> buildModel(String dir){
		List <GR> all= new ArrayList <GR>();
		try {
			
			 String[] parsedFiles =getFiles(dir,txtfilter);
			 
			 
			
			for (int i=0;i<parsedFiles.length;i++){
				//System.out.println("Parsing file"+parsedFiles[i] +"of "+parsedFiles[i].split("-")[0]);
				File file = new File(dir+"/"+parsedFiles[i]);
				
				
				if (file.length()>0 ){
					List <GR> tm=readParsed2(dir+"/"+parsedFiles[i],parsedFiles[i].split("-")[0]);
					//System.out.println(tm.size());
					all.addAll(tm);
				}
				
				
				
			}
			 
		} catch (Exception ex ){
		}
		
		
		return all;
		
	}
	
	public static Model readParsed(String filename, String auth) throws FileNotFoundException {
	    //Note that FileReader is used, not File, since File is not Closeable
		List <GR> temp= new ArrayList <GR>();
		Object[] grObjs=  {"ncsubj","ncmod","dobj","iobj","obj"};
		   List grList =Arrays.asList(grObjs);	
		   
		   
			Resource HEAD = ResourceFactory.createResource(SXMLNS + "head");
			Resource GR = ResourceFactory.createResource(SXMLNS + "gr");
			Resource DEP = ResourceFactory.createResource(SXMLNS + "dep");
			// The base
			

			// Create some data in a model
			Model model = ModelFactory.createDefaultModel();  
		   
	    Scanner scanner = new Scanner(new FileReader(filename));
	    try {
	      //first use a Scanner to get each line
	    	GR gr1=new GR("","","","");
	    	int fre=0;
	    	  while ( scanner.hasNextLine() ){
	    		String aLine=scanner.nextLine();
		    	  //System.out.println(aLine);
		    	  aLine=aLine.replace('%', 'p');
		    	  String triple=aLine.replace("| _ |", ",").replace("| |",",").replace("|)", "").replace("(|", "");
		    	  String[] parts= triple.split(",");
		    	  
		    	  
		    	 if (grList.contains(parts[0].trim())){
		    		 
		    		 //System.out.println("*"+aLine);
		        
		        	 String w1=parts[1].trim().split(":")[0].toLowerCase();
		        	 String w2=parts[2].trim().split(":")[0].toLowerCase();
		        	 String gr=parts[0].trim();
		        	 //System.out.println(gr);
		        	 w1=w1.replace("+",":");
		        	 w1=w1.split(":")[0];
		        	 w1=w1.replaceAll("[^\\p{L}\\p{N}]", "");
		        	 
		        	 
		        	 w2=w2.replace("+",":");
		        	 w2=w2.split(":")[0];
		        	 w2=w2.replaceAll("[^\\p{L}\\p{N}]", "");
		        	 
		        	 if(isWord(w1)&& isWord(w2)){
		        	  gr1 =new GR(w1,gr,w2,auth);
		        	  temp.add(gr1);
		        	  
		        	  //================
		        	  
		        	  	Resource res = model.createResource(BASE + w1, HEAD)
		        	  			.addProperty(model.createProperty(BASE+gr), model.createResource(BASE + w2, DEP));
//		        	  	int f=1;
//		        	  	
//		      			if (model.containsResource(res)){	
//		      				
//		      				Statement st =model.getResource(BASE + w1).getProperty(model.createProperty(BASE+"frequency"));
//		      				if (st!=null){
//		      				f=st.getInt();
//		      				
//		      				f++;
//		      				//res.addProperty(model.createProperty(BASE+"frequency"), String.valueOf(f));
//		      				model.getResource(BASE + w1).getProperty(model.createProperty(BASE+"frequency")).changeObject(String.valueOf(f));
//		      				
//		      				}else
//		      				{
//		      				res.addProperty(model.createProperty(BASE+"frequency"), "1");
//		      				
//		      				}
//		      				
//		      				
//		      				
//		      				
//		      				
//		      				
//		      				
//		      			}
//		      			else{
//		      				
//		      				//res.addProperty(model.createProperty(BASE+"frequency"), "0");
//		      			}
		      			}
		        	  
		        	 }
	    	}
	    
			
	    	
	     
	    	
	   
	      
	      
	      
	    
	    }catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	    finally {
	      //ensure the underlying stream is always closed
	      //this only has any effect if the item passed to the Scanner
	      //constructor implements Closeable (which it does in this case).
	      scanner.close();
	    }
	    return model;
	    
	  }
	
	
	public static List <GR> readParsed2(String filename, String auth) throws FileNotFoundException {
	    //Note that FileReader is used, not File, since File is not Closeable
		List <GR> temp= new ArrayList <GR>();
		Object[] grObjs=  {"ncsubj","ncmod","dobj","iobj","obj"};
		   List grList =Arrays.asList(grObjs);	  
		   
		   
	    Scanner scanner = new Scanner(new FileReader(filename));
	    try {
	      //first use a Scanner to get each line
	    	GR gr1=new GR("","","","");;
	    	if (scanner.hasNextLine()){
	    		String aLine=scanner.nextLine();
		    	  //System.out.println(aLine);
		    	  aLine=aLine.replace('%', 'p');
		    	  String triple=aLine.replace("| _ |", ",").replace("| |",",").replace("|)", "").replace("(|", "");
		    	  String[] parts= triple.split(",");
		    	  
		    	  
		    	 if (grList.contains(parts[0].trim())){
		    		 
		    		 //System.out.println("*"+aLine);
		        
		        	 String w1=parts[1].trim().split(":")[0].toLowerCase();
		        	 String w2=parts[2].trim().split(":")[0].toLowerCase();
		        	 String gr=parts[0].trim();
		        	 //System.out.println("*"+w1+"="+w2);
		        	 w1=w1.replace("+",":");
		        	 w1=w1.split(":")[0];
		        	 w1=w1.replaceAll("[^\\p{L}\\p{N}]", "");
		        	 
		        	 
		        	 w2=w2.replace("+",":");
		        	 w2=w2.split(":")[0];
		        	 w2=w2.replaceAll("[^\\p{L}\\p{N}]", "");
		        	 
		        	 
		        	  gr1 =new GR(w1,gr,w2,auth);
	    	}
			 
	    	}
	      while ( scanner.hasNextLine() ){
	    	
	    	  String aLine2=scanner.nextLine();
	    	  //System.out.println(aLine);
	    	  aLine2=aLine2.replace('%', 'p');
	    	  String triple2=aLine2.replace("| _ |", ",").replace("| |",",").replace("|)", "").replace("(|", "");
	    	  String[] parts2= triple2.split(",");
	    	  
	    	  
	    	 if (grList.contains(parts2[0].trim())){
	    		 
	    		 //System.out.println("*"+aLine);
	        
	        	 String w1=parts2[1].trim().split(":")[0].toLowerCase();
	        	 String w2=parts2[2].trim().split(":")[0].toLowerCase();
	        	 String gr=parts2[0].trim();
	        	 //System.out.println("*"+w1+"="+w2);
	        	 w1=w1.replace("+",":");
	        	 w1=w1.split(":")[0];
	        	 w1=w1.replaceAll("[^\\p{L}\\p{N}]", "");
	        	 
	        	 
	        	 w2=w2.replace("+",":");
	        	 w2=w2.split(":")[0];
	        	 w2=w2.replaceAll("[^\\p{L}\\p{N}]", "");
	        	 GR gr2 =new GR(w1,gr,w2,auth);
	        	 if(isWord(w1)&& isWord(w2)){
	        	 
	        	 if((gr1.getGr().equals("iobj"))&&(gr2.getGr().equals("dobj"))){
	        		
	        		 
	        		 GR grnew=new GR(gr1.getLeft(),"obj",gr2.getRight(),auth);
	        		 temp.add(grnew);
	        	 }else{
	        	 //System.out.println(gr1.toString()+","+gr2.toString());
	        	 temp.add(gr1);
	        	 }
	        	 gr1=gr2;
	        	 }
	        	 
	        	 
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
public static String[] getFiles(String dirname,FilenameFilter filter ){
		
		File dir = new File(dirname);

		String[] files = dir.list(filter);
		return files;
	}
	
	  private static void log(Object aObject){
		    System.out.println(String.valueOf(aObject));
		  }
	  
	  
	  public static FilenameFilter txtfilter = new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".txt");
		    }
		};
		public static boolean isWord(String str)
		{
		  return str.matches("^[a-zA-Z]+$");
		}
}
