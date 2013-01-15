package bak;

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


import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFWriter;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.sun.tools.example.debug.bdi.SessionListener; 
import com.hp.hpl.jena.tdb.TDBFactory;


import org.openrdf.repository.*;
import org.openrdf.rio.RDFFormat;
import org.openrdf.*;

import org.openrdf.repository.Repository;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.sail.memory.MemoryStore;



import org.openrdf.repository.RepositoryConnection ;

import org.openjena.jenasesame.JenaSesame ;



public class VocabularyAnalyser {
	
	private static List <GR>  memmodel;
	public static void main(String[] args) throws NullPointerException{
		VocabularyAnalyser va=new VocabularyAnalyser();
		String root="/Users/kapila/Documents/repository/";
		
		memmodel=va.buildMemModel(root+"parsed") ;
		
		Iterator<GR> iterator = memmodel.iterator();
		int i=0;
		while (iterator.hasNext()) {
			try{
			i++;
			//GR g= (GR) iterator.next();
//			System.out.print(i);
//			System.out.print(" ");
			//System.out.println( iterator.next().getLeft());
			
				GR g=(GR) iterator.next();
				if (g.isRightEqual("online") && g.isGrEqual("dobj"))
					System.out.println(g.toString());
			
			
			}catch (NullPointerException e)
			{
				System.out.println(e.getMessage());
			}
		}
//		
//		GR g=new GR();
//		g.setW1("purchase");
//		g.setGR("dobj");
//		g.setW2("online");
//		System.out.println(getCount(g));
//		System.out.println(getCountGR("dobj"));
//		System.out.println(getCountLeft("purchase","dobj"));
//		System.out.println(getCountLeft("online","dobj"));
//		System.out.println(getMI("purchase","dobj","online"));
//		
		
		
		
		
		GR g2=new GR();
		g2.setW1("buy");
		g2.setGR("dobj");
		g2.setW2("online");
		System.out.println(getCount(g2));
		System.out.println(getCountGR("dobj"));
		System.out.println(getCountLeft("buy","dobj"));
		System.out.println(getCountLeft("online","dobj"));
		System.out.println(getMI("buy","dobj","online"));
		
		Set left=getLeftList("online", "dobj");
		Iterator<String> it = left.iterator();
		while (it.hasNext()) {
			String s =it.next();
		System.out.println(s+"="+getMI(s,"dobj","online"));

		}
		
		
		
		
		
//		Model rdfmodel=va.buildRdfModel(root+"rdfs");
//		rdfmodel.write(System.out);
//		
//		queryRdf q=new queryRdf(rdfmodel);
		//<http://rasp/rule:dobj>
		
//		q.getTotal("?s","?p","?o");
//		System.out.println("total-dobj");
		
//		q.query("?s","gr:ncmod","?o");
//		System.out.println("***");
		
		
		
//		System.out.println("***");
//		System.out.println(q.getTotal("?s","?p","?o"));
//		System.out.println(q.getTotal("c:buy","gr:dobj","?o"));
//		System.out.println(q.getTotal("c:purchase","gr:dobj","?o"));
//		System.out.println(q.getTotal("?s","gr:dobj","c:online"));
//		System.out.println(q.getTotal("c:buy","gr:dobj","c:online"));
//		System.out.println(q.getTotal("c:purchase","gr:dobj","c:online"));
	}
	
	
	public VocabularyAnalyser(){
		String root="/Users/kapila/Documents/repository/";
		buildRdfRepository(root+"pdfs",root+"txts",root+"parsed",root+"rdfs");
		
		
	}

	
	
		
		
		

	
	
	
	public static void buildRdfRepository(String pdfDir, String txtDir, String parsedDir, String rdfDir){
		
		System.out.println("Start converting pdfs......at: "+DateUtils.now()) ;
		String[] pdffiles=getFiles(pdfDir,pdffilter);
		for (int i=0; i< pdffiles.length;i++){
			
			System.out.println(pdfDir+"/"+pdffiles[i]);
			Pdf2Text pdf =new Pdf2Text(pdfDir+"/"+pdffiles[i]);
			pdf.saveToTextPreProssesd(txtDir+"/"+pdffiles[i].split(".pdf")[0]+".txt");
			
			
		}
		System.out.println("Finish converting pdfs......at: "+DateUtils.now()) ;
		System.out.println("Start parsing (using RASP) ......at: "+DateUtils.now()) ;
		String[] txtfiles=getFiles(txtDir,txtfilter);
		for (int i=0; i< txtfiles.length;i++){
			File file = new File(txtDir+"/"+txtfiles[i]);
				if (file.length()>0){
			
						System.out.println(txtDir+"/"+txtfiles[i]);
						System.out.println(parsedDir+"/"+txtfiles[i]);
						ExecuteRASP r =new ExecuteRASP(txtDir+"/"+txtfiles[i], parsedDir+"/"+txtfiles[i]);
						r.callRASP();
				}
			
		}
		System.out.println("Finish parsing (using RASP) ......at: "+DateUtils.now()) ;
		System.out.println("Start converting to RDF ......at: "+DateUtils.now()) ;
		String[] parsedFiles=getFiles(parsedDir,txtfilter);
		for (int i=0;i<parsedFiles.length;i++){
			File file = new File(parsedDir+"/"+parsedFiles[i]);
			if (file.length()>0){
					String auth=parsedFiles[i].split("-")[0];
//					System.out.println("auther is .........."+auth);
//					System.out.println(rdfDir+"/"+parsedFiles[i].split(".txt")[0]+".rdf");
					Text2RDF t =new Text2RDF(parsedDir+"/"+parsedFiles[i]);
					try {
					t.saveToRdf(rdfDir+"/"+parsedFiles[i].split(".txt")[0]+".rdf",auth);
					}
					catch (Exception e)
					{System.out.println(e.getMessage()) ;}
			}
			
			
			
			
		}
		System.out.println("Finish converting to RDF ......at: "+DateUtils.now()) ;
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
	
	
	  public final Model buildRdfModel(String dir){
			
		  System.out.println("Start building rdfmodel in memory.....at: "+DateUtils.now());
			// Create an empty in-memory model and populate it from the graph
				Model model = ModelFactory.createMemModelMaker().createDefaultModel();
				
			try {
				
				 String[] rdfFiles =getFiles(dir,rdffilter);
				 System.out.println(rdfFiles.length);
				 InputStream in ;
				 in = new FileInputStream(new File(dir+"/"+rdfFiles[0]));
				 model.read(in,null);
				for (int i=0;i<rdfFiles.length;i++){
					//System.out.println("reading file "+dir+"/"+rdfFiles[i]);
					File file = new File(dir+"/"+rdfFiles[i]);
					Model modelt = ModelFactory.createMemModelMaker().createDefaultModel();
					
					if (file.length()>0 ){
						in = new FileInputStream(file);	 
				
						modelt.read(in,null); // null base URI, since model URIs are absolute
						//model.commit();
				 
						in.close();
					
						
						//modelt.write(System.out);
						model.add(modelt);
						
					}
					
					
					
				}
				 System.out.println("Finish building rdfmodel in memory.....at: "+DateUtils.now());
				
			} catch (Exception ex ){
			}
			
			return model;
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
			if (g.isRightEqual("online") && g.isGrEqual("dobj"))
				s.add(g.getLeft());
			
			
		}
		
		
		return s;
		
		
	}
	
	public static double getMI(String w1, String r, String w2) {
		GR gr= new GR();
		gr.setW1(w1);
		gr.setW2(w2);
		gr.setGR(r);
		
		return Math.log10((getCount(gr)*getCountGR(r))/(getCountLeft(w1, r)*getCountRight(w2, r)));
		
		
	}
	
	
}
