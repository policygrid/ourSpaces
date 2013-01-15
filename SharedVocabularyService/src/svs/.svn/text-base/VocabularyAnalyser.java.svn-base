package svs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import bak.GR;





public class VocabularyAnalyser {
	
	private static List <GR>  memmodel,memmodel2;
	public static void main(String[] args) throws NullPointerException{
		VocabularyAnalyser va=new VocabularyAnalyser();
		String root="D:/repository/";
		
		memmodel=va.buildMemModel("D:/repository/") ;
				
		Iterator<GR> iterator = memmodel.iterator();
		
//		while (iterator.hasNext()) {
//			
//				GR g=(GR) iterator.next();
//				
//				memmodel2=va.buildMemModel("D:/repository/") ;
//				Iterator<GR> iterator2 = memmodel2.iterator();
//				
//				while (iterator2.hasNext()) {
//					GR g2=(GR) iterator2.next();
//					
//					System.out.println(g.getLeft()+"="+g2.getLeft());
//				}
//
//			
//			
//		}
		
		Set lwl=getLeftWordList(memmodel, "dobj");
		Set lw2=getLeftWordList(memmodel, "dobj");
		
		Iterator <String> it1=lwl.iterator();
		it1.next();
		while (it1.hasNext()){
			String s1 =it1.next();
			
			Iterator <String> it2=lw2.iterator();
			it2.next();
			while (it2.hasNext()){
				String s2 =it2.next();
				//System.out.println(s1+"="+s2+"=");
				double v=getDS(s1.trim(), s2.trim(), "ncmod", "online");
				if (!Double.isNaN(v))
					System.out.println(s1+"="+s2+"="+v);
				
			}
		}
		
		
		System.out.println(getDS("purchaase", "bu0y", "dobj", "online"));
		System.out.println(getDS("shoap", "buay", "dobj", "online"));
		
		
		
	}
	
	
	public VocabularyAnalyser(){
		String root="/Users/kapila/Documents/repository/";
		buildRdfRepository(root+"pdfs",root+"txts",root+"parsed",root+"rdfs");
		
		
	}

	
	
		
		public static double getDS(String w1, String w2, String r, String t){
			double z=getMI(w1, r, t)+getMI(w2, r, t);
			Set left=getLeftList(t, r);
			double y=0;
			Iterator<String> it = left.iterator();
			while (it.hasNext()) {
				String s =it.next();
				 y=y+getMI(s, r, t);
				
				
			}
			
			
			double x=z/(y+z)*100;

			return x;
			
		}
		

	
	
	
	public static void buildRdfRepository(String pdfDir, String txtDir, String parsedDir, String rdfDir){
		
	}
	
	public final List <GR> buildMemModel(String dir){
		
		  System.out.println("Start building rdfmodel in memory.....at: "+DateUtils.now());
			// Create an empty in-memory model and populate it from the graph
		  ArrayList <GR> t=new ArrayList <GR>();
				
			try {
				
				 String[] parsedFiles =getFiles(dir,txtfilter);
				 
				 
				
				for (int i=0;i<parsedFiles.length;i++){
					System.out.println("Parsing file"+parsedFiles[i]);
					//System.out.println("reading file "+dir+"/"+rdfFiles[i]);
					File file = new File(dir+"/"+parsedFiles[i]);
					
					
					if (file.length()>0 ){
						Text2Mem tm=new Text2Mem(dir+"/"+parsedFiles[i]);
						t.addAll(tm.buildModel("", ""));
						
					}
					
					
					
				}
				 System.out.println("Finish building model in memory.....at: "+DateUtils.now());
				
			} catch (Exception ex ){
			}
			
			return t;
	  }
	
	
	  
	  
	  
	  
	public static String[] getFiles(String dirname,FilenameFilter filter ){
		
		File dir = new File(dirname);

		String[] files = dir.list(filter);
		return files;
	}
	
	public static FilenameFilter pdffilter = new FilenameFilter() {
	    public boolean accept(File dir, String name) {
	        return name.endsWith(".pdf");
	    }
	};
	public static FilenameFilter txtfilter = new FilenameFilter() {
	    public boolean accept(File dir, String name) {
	        return name.endsWith(".txt");
	    }
	};
	public static FilenameFilter rdffilter = new FilenameFilter() {
	    public boolean accept(File dir, String name) {
	        return name.endsWith(".rdf");
	    }
	};
	
	public static int getCount(GR gr){
		int c=0;
		Iterator<GR> iterator = memmodel.iterator();
		int i=0;
		while (iterator.hasNext()) {
			if (iterator.next().isEqual(gr.getLeft(),gr.getGr(),gr.getRight()))
				c++;
			
			
		}
		
		
		return c;
		
		
	}
	public static int getCountGR(String gr){
		int c=0;
		Iterator<GR> iterator = memmodel.iterator();
		int i=0;
		while (iterator.hasNext()) {
			if (iterator.next().isGrEqual(gr))
				c++;
			
			
		}
		
		
		return c;
		
		
	}
	
	public static int getCountLeft(String w1, String gr){
		int c=0;
		Iterator<GR> iterator = memmodel.iterator();
		int i=0;
		while (iterator.hasNext()) {
			if (iterator.next().isLeftEqual(w1)&&iterator.next().isGrEqual(gr))
				c++;
			
			
		}
		
		
		return c;
		
		
	}
	public static int getCountRight(String w2, String gr){
		int c=0;
		Iterator<GR> iterator = memmodel.iterator();
		int i=0;
		while (iterator.hasNext()) {
			GR g = (GR) iterator.next();
			if (g.isRightEqual(w2)&&g.isGrEqual(gr))
				c++;
			
			
		}
		
		
		return c;
		
		
	}
	
	public static Set <String> getLeftList(String w2, String gr){
		Set<String> s=new TreeSet<String>();
		Iterator<GR> iterator = memmodel.iterator();
		
		while (iterator.hasNext()) {
			GR g = (GR) iterator.next();
			if (g.isRightEqual(w2) && g.isGrEqual(gr))
				s.add(g.getLeft());
			
			
		}
		
		
		return s;
		
		
	}
	
	public static Set <String> getLeftWordList(List <GR> mem, String gr){
		Set<String> s=new TreeSet<String>();
		Iterator<GR> iterator = mem.iterator();
		
		while (iterator.hasNext()) {
			GR g = (GR) iterator.next();
			if ( g.isGrEqual(gr))
				s.add(g.getLeft());
			
			
		}
		
		
		return s;
		
		
	}
	
	public static double getMI(String w1, String r, String w2) {
		GR gr= new GR();
		gr.setW1(w1);
		gr.setW2(w2);
		gr.setGR(r);
		double x=getCount(gr)*getCountGR(r);
		double y=getCountLeft(w1, r)*getCountRight(w2, r);
//		if (x!=0)
//				return Math.log10((getCount(gr)*getCountGR(r))/(getCountLeft(w1, r)*getCountRight(w2, r)));
//		else
//			return 0;
		return Math.log10(y/x);
		
	}
	
	
}
