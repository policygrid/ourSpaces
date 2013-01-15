package lexicon.builder;

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
import java.util.Vector;


public class Text2Mem {
	
	private List<GR> model;
	
	public static void main(String [] args){
		
		String root="/home/policygrid/apache-tomcat-6.0.18/data/lexicons/temp/parsed/ec272513-360a-467a-8f43-0e951d1243db/";
		
		String[] parsedFiles =getFiles(root,txtfilter);
		Vector<String> fvector =new Vector<String>();
		for (String s:parsedFiles)
			fvector.add(s);
		System.out.println(fvector.size());
		Text2Mem tr =new Text2Mem(fvector,"");
		List <GR> cul=tr.getAuthorList("lo");
		System.out.println("total "+cul.size());
		System.out.println(cul.size());
		for (GR g:cul){
			System.out.println(g.toString());
			
		}
		
	}
public Text2Mem( Vector<String> v, String author){
	    
	model=buildModel(v, author);
		
	}
	
	public List<GR> getModel(){
		return model;
	
	}
	
	
	
	
	public  List <GR> getAuthorList(String aut){
		List <GR> m=new ArrayList<GR>();
		Iterator <GR> it1=model.iterator();
		while (it1.hasNext()){
		
			GR g=(GR)it1.next();
			if (g.isAuthor(aut)){
				m.add(g);
			}
		}
		
		return m;
	}
	
	
//	public List <GR> buildModel(String path,Vector<String> dir,String author){
//		List <GR> all= new ArrayList <GR>();
//		try {
//			
//			 
//			 
//			 
//			
//			for (int i=0;i<dir.size();i++){
//				//System.out.println("Reading file "+path+dir.get(i) );
//				File file = new File(path+dir.get(i));
//				
//				
//				if (file.length()>0 ){
//					List <GR> tm=readParsed(path+dir.get(i),author);
//					//System.out.println(tm.size());
//					all.addAll(tm);
//				}
//				
//				
//				
//			}
//			 
//		} catch (Exception ex ){
//			System.out.println(ex.getMessage());
//		}
//		
//		
//		return all;
//		
//	}
	
	
	public List <GR> buildModel(Vector<String> fvector,String author){
		List <GR> all= new ArrayList <GR>();
		
		
		
		
   		
		try {
			
			for (int i=0;i<fvector.size();i++){
				
				File file = new File(fvector.get(i));
				
				
				if (file.length()>0 ){
					System.out.println("Reading file "+fvector.get(i) );
					List <GR> tm=readParsed(fvector.get(i),author);
					//System.out.println(tm.size());
					all.addAll(tm);
				}
				
				
				
			}
			 
		} catch (Exception ex ){
			System.out.println(ex.getMessage());
		}
		
		
		return all;
		
	}
	public static List <GR> readParsed(String filename, String auth) throws FileNotFoundException {
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
		        	 
		        	 
		        	  gr1 =new GR(w1.trim(),gr.trim(),w2.trim(),auth.trim());
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
	        	 GR gr2 =new GR(w1.trim(),gr.trim(),w2.trim(),auth.trim());
	        	 if(isWord(w1)&& isWord(w2)){
	        	 
	        	 if((gr1.getGr().equals("iobj"))&&(gr2.getGr().equals("dobj"))){
	        		
	        		 
	        		 GR grnew=new GR(gr1.getHead(),"dobj",gr2.getDep(),auth.trim());
	        		 temp.add(grnew);
	        	 }else if (!(gr2.getGr().equals("iobj"))){
	        	 //System.out.println(gr1.toString()+","+gr2.toString());
	        	 temp.add(gr2);
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
//	public static List <GR> readParsed(String filename, String auth) throws FileNotFoundException {
//	    //Note that FileReader is used, not File, since File is not Closeable
//		List <GR> temp= new ArrayList <GR>();
//		Object[] grObjs=  {"ncsubj","ncmod","dobj","iobj","obj"};
//		   List grList =Arrays.asList(grObjs);	  
//		   
//		   
//	    Scanner scanner = new Scanner(new FileReader(filename));
//	    try {
//	      //first use a Scanner to get each line
//	    	GR gr1=new GR("","","","");;
//	    	while (scanner.hasNextLine()){
//	    		String aLine=scanner.nextLine();
//		    	  //System.out.println(aLine);
//		    	  aLine=aLine.replace('%', 'p');
//		    	  String triple=aLine.replace("| _ |", ",").replace("| |",",").replace("|)", "").replace("(|", "");
//		    	  String[] parts= triple.split(",");
//		    	  
//		    	  
//		    	 if (grList.contains(parts[0].trim())){
//		    		 
//		    		 //System.out.println("*"+aLine);
//		        
//		        	 String w1=parts[1].trim().split(":")[0].toLowerCase();
//		        	 String w2=parts[2].trim().split(":")[0].toLowerCase();
//		        	 String gr=parts[0].trim();
//		        	 //System.out.println("*"+w1+"="+w2);
//		        	 w1=w1.replace("+",":");
//		        	 w1=w1.split(":")[0];
//		        	 w1=w1.replaceAll("[^\\p{L}\\p{N}]", "");
//		        	 
//		        	 
//		        	 w2=w2.replace("+",":");
//		        	 w2=w2.split(":")[0];
//		        	 w2=w2.replaceAll("[^\\p{L}\\p{N}]", "");
//		        	 
//		        	 
//		        	  gr1 =new GR(w1,gr,w2,auth);
//		        	  temp.add(gr1);
//	    	}
//			 
//	    	}
//	      	
//	   
//	      return temp;
//	      
//	      
//	    
//	    }
//	    finally {
//	      //ensure the underlying stream is always closed
//	      //this only has any effect if the item passed to the Scanner
//	      //constructor implements Closeable (which it does in this case).
//	      scanner.close();
//	    }
//	    
//	    
//	  }	
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
		        return name.endsWith("");
		    }
		};
		public static boolean isWord(String str)
		{
		  return str.matches("^[a-zA-Z]+$");
		}
}
