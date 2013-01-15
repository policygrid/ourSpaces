package svs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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





public class Analyser {
	
	private static List <GR>  memmodel;
	private static String [] stopwords={"i","a","and","it","p","that","the","this","to","was","well","which","what","when","where","who","will","with","the","www"};
	private static List <String>swl= Arrays.asList(stopwords);
	public static void main(String[] args) throws NullPointerException{
		
		
		//String root="D:/repository/parsed";
		String root="/Users/kapila/Documents/repository/parsed/";
				
		Text2Mem t =new Text2Mem(root);
		memmodel=t.buildModel(root) ;
		List <GR>cullinane=t.getAuthorList( "cullinane");
		List <GR>dargay=t.getAuthorList("dargay");

		//get verbs
		Set <String> crw=getRightWordList(cullinane, "obj");
		Set <String> common=getRightWordList(cullinane, "obj");
		Set <String> cullunique=getRightWordList(cullinane, "obj");
		Set <String> drw=getRightWordList(dargay, "obj");
		Set <String> dargayunique=getRightWordList(dargay, "obj");
	
		crw.removeAll(swl);
		drw.removeAll(swl);
		System.out.println(crw.size());		
		System.out.println(drw.size());	
		
	
		System.out.println(common.size());
		
		common.retainAll(drw);
		System.out.println(common.size());
		
		System.out.println(crw.size());
		cullunique.removeAll(common);
		dargayunique.removeAll(common);
		
		
		Set <String> crwverbs=new TreeSet<String>();
		Set <String> commonverbs=new TreeSet<String>();
		Set <String> crwverb1=getLeftWordList(cullinane, "sub");
		Set <String> crwverb2=getLeftWordList(cullinane, "dobj");
		crwverb1.addAll(crwverb2);
		
		crwverbs.addAll(crwverb1);
		crwverbs.retainAll(crwverb2);
		
		crwverbs.removeAll(swl);
		commonverbs=crwverbs;
		
		System.out.println("cul-verbs, "+crwverbs.size());
		
		
		Set <String> drwverbs=new TreeSet<String>();
		Set <String> drwverb1=getLeftWordList(dargay, "sub");
		Set <String> drwverb2=getLeftWordList(dargay, "obj");
		drwverbs.addAll(drwverb1);
		drwverbs.retainAll(drwverb2);
		drwverbs.removeAll(swl);
		
		System.out.println("dar-verbs, "+drwverbs.size());
		
		
		commonverbs.retainAll(drwverbs);
		System.out.println("common verbs, "+commonverbs.size());

		
		
		//AnalyseNew(crwverb2, drwverb2);
		Set <String> crwnouns=new TreeSet<String>();
		Set <String> commonnouns=new TreeSet<String>();
		
		Set <String> crwnoun1=getRightWordList(cullinane, "sub");
		Set <String> crwnoun2=getRightWordList(cullinane, "obj");
		//crwnoun1.retainAll(crwverb2);
		crwnouns.addAll(crwnoun1);
		crwnouns.addAll(crwnoun2);
		
		crwnouns.removeAll(swl);
		
		Set <String> drwnouns=new TreeSet<String>();
		
		Set <String> drwnoun1=getRightWordList(dargay, "sub");
		Set <String> drwnoun2=getRightWordList(dargay, "obj");
		//drwnoun1.retainAll(drwverb2);
		drwnouns.addAll(drwnoun1);
		drwnouns.addAll(drwnoun2);
		drwnouns.removeAll(swl);
		
		System.out.println("cul nouns1, "+crwnouns.size());
		//System.out.println("cul nouns "+crwnoun2.size());
		System.out.println("dar nouns2, "+drwnouns.size());
		//System.out.println("dar nouns "+drwnoun2.size());
		commonnouns=crwnouns;
		commonnouns.retainAll(drwnouns);
		System.out.println("common nouns, "+commonnouns.size());
		
		Iterator itx =drwnouns.iterator();

//		while (itx.hasNext()){
//			System.out.println(itx.next());
//			
//		}		
		
		
		//AnalyseNew(crwverbs, drwverbs);
		//
		
		AnalyseNouns(crwnouns, drwnouns);
		
		
		
		
		
		//analyse();
		//crwv1.retainAll(drwv1);
		
		//Iterator it =getModFeatureList(cullinane,"mod_of","deliver").iterator();
		
//		Iterator it =crwv1.iterator();
//		
//		
//		
//		int i=0;
//		while(it.hasNext()){
//			//GR g=(GR) it.next();
//			String w=(String )it.next();
//			
//			System.out.println(w);
//			i++;
//			
//		}
//		System.out.println(i);
		
		
		
	}
	
	public static void AnalyseNew(Set s1, Set s2){
		List <GR>cullinane=getAuthorList(memmodel, "cullinane");
		
		List <GR>dargay=getAuthorList(memmodel,"dargay");

		Iterator<String> it1 =s1.iterator();
		
		while (it1.hasNext()){	
			System.out.println(it1.next());
//			String w1=it1.next();
//			Iterator<String> it2 =s2.iterator();
//			while (it2.hasNext()){
//				String w2 =it2.next();
//				double ds=0.0;//getDSNEW(w1, cullinane,w2,dargay);
//				//if (ds>5)
//					System.out.println("DS(cullinane-dargay), "+ w1.toUpperCase() + ", "+w2.toUpperCase()+ " ,"+ds);
//		
//				
//				
//			}
		}
	}
	
	public static void AnalyseNouns(Set s1, Set s2){
		List <GR>cullinane=getAuthorList(memmodel, "cullinane");
		
		List <GR>dargay=getAuthorList(memmodel,"dargay");

		Iterator<String> it1 =s1.iterator();
		try{
			  // Create file 
//			  FileWriter fstream = new FileWriter("/Users/kapila/Documents/repository/results/res.csv");
//			  BufferedWriter out = new BufferedWriter(fstream);
			 
			 
			  while (it1.hasNext()){	
					
					String w1=it1.next();
					//System.out.println(getModFeatureList(cullinane, "mod_of", w1).toString());
					//if(w1.contains("car")){
					Iterator<String> it2 =s2.iterator();
					while (it2.hasNext()){
						String w2 =it2.next();
						//if(w2.contains("elasticity")){
						double ds=getDSNoun(w1, cullinane,w2,dargay);
						//double dscooc=getDSNounCoOc(w1, cullinane,w2,dargay);
						
						if (ds>10)
//							 out.write(w1+", "+w2+", "+ds);
						 System.out.println(w1+", "+w2+", "+ds);
						}
//					}
					//}
				}
			  
			  
			  
			  
			  
			  
			  
//			  out.close();
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
		
	}
	
	public static double getDSNEW(String w1,List<GR> mem1, String w2, List<GR> mem2){
		double ds=0.0;
		
		Set<String> objf1= getObjFeatureList(mem1, "obj", w1);
		Set<String> objcommon= getObjFeatureList(mem1, "obj", w1);
		Set<String> objf2= getObjFeatureList(mem2, "obj", w2);
		
		Set<String> subf1= getSubFeatureList(mem1, "sub", w1);
		Set<String> subjcommon= getSubFeatureList(mem1, "sub", w1);
		Set<String> subf2= getSubFeatureList(mem2, "sub", w2);
		
		
		Set<String> modf1= getModFeatureList(mem1, "mod", w1);
		Set<String> modcommon= getModFeatureList(mem1, "mod", w1);
		Set<String> modf2= getModFeatureList(mem2, "mod", w2);

		
		objcommon.retainAll(objf2);
		subjcommon.retainAll(subf2);
		modcommon.retainAll(modf2);
		
		if ((objcommon.size()>0) ){
//			System.out.println(w1+"-"+"obj"+objf1.toString()+"<=>"+w2+"-"+"obj"+objf2.toString());
//			System.out.println(w1+"-"+"sub"+subf1.toString()+"<=>"+w2+"-"+"sub"+subf2.toString());
//			System.out.println(w1+"-"+"mod"+modf1.toString()+"<=>"+w2+"-"+"mod"+modf2.toString());
//			
			double top=0.0;
			Iterator<String> itcommonobj = objcommon.iterator();
			while (itcommonobj.hasNext()){
				String cw=itcommonobj.next();
				GR t1=new GR(w1,"obj",cw.trim(),"");
				GR t2=new GR(w2,"obj",cw.trim(),"");
				top=top+t1.getMIVerb(mem1)+t2.getMIVerb(mem2);
				
			}
			
			Iterator<String> itcommonsub = subjcommon.iterator();
			while (itcommonsub.hasNext()){
				String cw=itcommonsub.next();
				GR t1=new GR(w1,"sub",cw.trim(),"");
				GR t2=new GR(w2,"sub",cw.trim(),"");
				top=top+t1.getMIVerb( mem1)+t2.getMIVerb( mem2);
			}
			
			
			Iterator<String> itcommonmod = modcommon.iterator();
			while (itcommonmod.hasNext()){
				String cw=itcommonmod.next();
				GR t1=new GR(w1,"mod",cw.trim(),"");
				GR t2=new GR(w2,"mod",cw.trim(),"");
				top=top+t1.getMIVerb( mem1)+t2.getMIVerb( mem2);
			}
			
			//System.out.println(top);
			
			double bottom=0.0;
			Iterator<String> itobj1 = objf1.iterator();
			while (itobj1.hasNext()){
				String w=itobj1.next();
				GR t1=new GR(w1,"obj",w,"");
				bottom=bottom+t1.getMIVerb(mem1);
			}
			Iterator<String> itobj2 = objf2.iterator();
			while (itobj2.hasNext()){
				String w=itobj2.next();
				GR t2=new GR(w2,"obj",w,"");
				bottom=bottom+t2.getMIVerb(mem2);
			}
			Iterator<String> itsubj1 = subf1.iterator();
			while (itsubj1.hasNext()){
				String w=itsubj1.next();
				GR t1=new GR(w1,"sub",w,"");
				bottom=bottom+t1.getMIVerb(mem1);
			}
			Iterator<String> itsubj2 = subf2.iterator();
			while (itsubj2.hasNext()){
				String w=itsubj2.next();
				GR t2=new GR(w2,"sub",w,"");
				bottom=bottom+t2.getMIVerb(mem2);
			}
			Iterator<String> itmod1 = modf1.iterator();
			while (itmod1.hasNext()){
				String w=itmod1.next();
				GR t1=new GR(w1,"mod",w,"");
				bottom=bottom+t1.getMIVerb(mem1);
			}
			Iterator<String> itmod2 = modf2.iterator();
			while (itmod2.hasNext()){
				String w=itmod2.next();
				GR t2=new GR(w2,"mod",w,"");
				bottom=bottom+t2.getMIVerb(mem2);
			}
			
			
			
			
			 ds=top/bottom*100;
			//System.out.println("DS(cullinane-dargay), "+ w1.toUpperCase() + ", "+w2.toUpperCase()+ " ,"+ds);
		}

		return ds;
		
	}
	public static double getDSNoun(String w1,List<GR> mem1, String w2, List<GR> mem2){
		double x=0.0;
		
		Set<String> objf1= getObjFeatureList(mem1, "obj_of", w1);
		Set<String> objcommon= getObjFeatureList(mem1, "obj_of", w1);
		Set<String> objf2= getObjFeatureList(mem2, "obj_of", w2);
		
		Set<String> subf1= getSubFeatureList(mem1, "sub_of", w1);
		Set<String> subjcommon= getSubFeatureList(mem1, "sub_of", w1);
		Set<String> subf2= getSubFeatureList(mem2, "sub_of", w2);
		
		
		Set<String> modf1= getModFeatureList(mem1, "mod", w1);
		Set<String> modcommon= getModFeatureList(mem1, "mod", w1);
		Set<String> modf2= getModFeatureList(mem2, "mod", w2);

		
		objcommon.retainAll(objf2);
		subjcommon.retainAll(subf2);
		modcommon.retainAll(modf2);
		double ds=0.0;
		
		if (((objf1.size()>2 || objf2.size()>2 || subf1.size()>2 ||subf2.size() >2)) ){
//			System.out.println(w1+"-"+"obj"+objf1.toString()+"<=>"+w2+"-"+"obj"+objf2.toString());
//			System.out.println(w1+"-"+"sub"+subf1.toString()+"<=>"+w2+"-"+"sub"+subf2.toString());
//			System.out.println(w1+"-"+"mod"+modf1.toString()+"<=>"+w2+"-"+"mod"+modf2.toString());
//			
			double tops=0.0;
			double topo=0.0;
			double topm=0.0;
			Iterator<String> itcommonobj = objcommon.iterator();
			while (itcommonobj.hasNext()){
				String cw=itcommonobj.next();
				GR t1=new GR(cw.trim(),"obj",w1,"");
				GR t2=new GR(cw.trim(),"obj",w2,"");
				topo=topo+t1.getMINoun(mem1)+t2.getMINoun(mem2);
				
				//top=top+t1.getMINoun( mem1);
			}
			
			Iterator<String> itcommonsub = subjcommon.iterator();
			while (itcommonsub.hasNext()){
				String cw=itcommonsub.next();
				GR t1=new GR(cw.trim(),"sub",w1,"");
				GR t2=new GR(cw.trim(),"sub",w2,"");
				tops=tops+t1.getMINoun( mem1)+t2.getMINoun( mem2);
				
			}
			
			
			Iterator<String> itcommonmod = modcommon.iterator();
			while (itcommonmod.hasNext()){
				String cw=itcommonmod.next();
				GR t1=new GR(w1,"mod",cw,"");
				GR t2=new GR(w2,"mod",cw,"");
				topm=topm+t1.getMINoun( mem1)+t2.getMINoun( mem2);
				
			}
			
			//System.out.println(top);
			
			double bottom=0.0;
			Iterator<String> itobj1 = objf1.iterator();
			while (itobj1.hasNext()){
				String w=itobj1.next();
				GR t1=new GR(w,"obj",w1,"");
				bottom=bottom+t1.getMINoun( mem1);
				
				
			}
			Iterator<String> itobj2 = objf2.iterator();
			while (itobj2.hasNext()){
				String w=itobj2.next();
				GR t1=new GR(w,"obj",w2,"");
				bottom=bottom+t1.getMINoun( mem2);
			}
			Iterator<String> itsubj1 = subf1.iterator();
			while (itsubj1.hasNext()){
				String w=itsubj1.next();
				GR t1=new GR(w,"sub",w1,"");
				bottom=bottom+t1.getMINoun( mem1);
			}
			Iterator<String> itsubj2 = subf2.iterator();
			while (itsubj2.hasNext()){
				String w=itsubj2.next();
				GR t1=new GR(w,"sub",w2,"");
				bottom=bottom+t1.getMINoun( mem2);
			}
			Iterator<String> itmod1 = modf1.iterator();
			while (itmod1.hasNext()){
				String w=itmod1.next();
				GR t1=new GR(w1,"mod",w,"");
				bottom=bottom+t1.getMINoun( mem1);
				
			}
			Iterator<String> itmod2 = modf2.iterator();
			while (itmod2.hasNext()){
				String w=itmod2.next();
				GR t1=new GR(w2,"mod",w,"");
				bottom=bottom+t1.getMINoun(mem2);
				
			}
			
			
			
			
			ds=(topo+tops+topm*3)/bottom*100;
//			System.out.println("DS(cullinane-dargay), "+ w1.toUpperCase() + ", "+w2.toUpperCase()+ " ,"+ds);
//			System.out.println("obj_of1"+objf1.toString());
//			System.out.println("obj_of2"+objf2.toString());
//			System.out.println("sub_of1"+subf1.toString());
//			System.out.println("sub_of2"+subf2.toString());
//			System.out.println("===============");
		}

		return ds;
		
	}
	public static double getDSNounCoOc(String w1,List<GR> mem1, String w2, List<GR> mem2){
		double x=0.0;
		
		Set<String> objf1= getObjFeatureList(mem1, "obj_of", w1);
		Set<String> objcommon= getObjFeatureList(mem1, "obj_of", w1);
		Set<String> objf2= getObjFeatureList(mem2, "obj_of", w2);
		
		Set<String> subf1= getSubFeatureList(mem1, "sub_of", w1);
		Set<String> subjcommon= getSubFeatureList(mem1, "sub_of", w1);
		Set<String> subf2= getSubFeatureList(mem2, "sub_of", w2);
		
		
		Set<String> modf1= getModFeatureList(mem1, "mod_of", w1);
		Set<String> modcommon= getModFeatureList(mem1, "mod_of", w1);
		Set<String> modf2= getModFeatureList(mem2, "mod_of", w2);

		
		objcommon.retainAll(objf2);
		subjcommon.retainAll(subf2);
		modcommon.retainAll(modf2);
		double ds=0.0;
		if ((objcommon.size()>1&&subjcommon.size()>1)&& objf1.size()>2 ){
//			System.out.println(w1+"-"+"obj"+objf1.toString()+"<=>"+w2+"-"+"obj"+objf2.toString());
//			System.out.println(w1+"-"+"sub"+subf1.toString()+"<=>"+w2+"-"+"sub"+subf2.toString());
//			System.out.println(w1+"-"+"mod"+modf1.toString()+"<=>"+w2+"-"+"mod"+modf2.toString());
//			
			double pt=0.0,rt=0.0;
			Iterator<String> itcommonobj = objcommon.iterator();
			while (itcommonobj.hasNext()){
				String cw=itcommonobj.next();
				GR t1=new GR(cw.trim(),"obj",w1,"");
				GR t2=new GR(cw.trim(),"obj",w2,"");
				pt=pt+t1.getMINoun( mem1);
				rt=rt+t2.getMINoun( mem2);
				//top=top+t1.getMINoun( mem1);
			}
			
			Iterator<String> itcommonsub = subjcommon.iterator();
			while (itcommonsub.hasNext()){
				String cw=itcommonsub.next();
				GR t1=new GR(cw.trim(),"sub",w1,"");
				GR t2=new GR(cw.trim(),"sub",w2,"");
				pt=pt+t1.getMINoun(mem1);
				rt=rt	+t2.getMINoun( mem2);
			}
			
			
			Iterator<String> itcommonmod = modcommon.iterator();
			while (itcommonmod.hasNext()){
				String cw=itcommonmod.next();
				GR t1=new GR(w1,"mod",cw,"");
				GR t2=new GR(w2,"mod",cw,"");
				pt=pt+t1.getMINoun(mem1);
				rt=rt+t2.getMINoun( mem2);
			}
			
			//System.out.println(top);
			
			double pb=0.0,rb=0.0;
			Iterator<String> itobj1 = objf1.iterator();
			while (itobj1.hasNext()){
				String w=itobj1.next();
				GR t1=new GR(w,"obj",w1,"");
				pb=pb+t1.getMINoun( mem1);
			}
			Iterator<String> itobj2 = objf2.iterator();
			while (itobj2.hasNext()){
				String w=itobj2.next();
				GR t2=new GR(w,"obj",w2,"");
				rb=rb+t2.getMINoun( mem2);
			}
			Iterator<String> itsubj1 = subf1.iterator();
			while (itsubj1.hasNext()){
				String w=itsubj1.next();
				GR t1=new GR(w,"sub",w1,"");
				pb=pb+t1.getMINoun( mem1);
			}
			Iterator<String> itsubj2 = subf2.iterator();
			while (itsubj2.hasNext()){
				String w=itsubj2.next();
				GR t2=new GR(w,"sub",w2,"");
				rb=rb+t2.getMINoun( mem2);
			}
			Iterator<String> itmod1 = modf1.iterator();
			while (itmod1.hasNext()){
				String w=itmod1.next();
				GR t1=new GR(w1,"mod",w,"");
				pb=pb+t1.getMINoun( mem1);
			}
			Iterator<String> itmod2 = modf2.iterator();
			while (itmod2.hasNext()){
				String w=itmod2.next();
				GR t2=new GR(w2,"mod",w,"");
				rb=rb+t2.getMINoun(mem2);
			}
			
			
			
			
			double p=pt/pb;
			double r=rt/rb;
			ds=  (2*p*r)/(p+r);
					
//			System.out.println("DS(cullinane-dargay), "+ w1.toUpperCase() + ", "+w2.toUpperCase()+ " ,"+ds);
//			System.out.println("obj_of1"+objf1.toString());
//			System.out.println("obj_of2"+objf2.toString());
//			System.out.println("sub_of1"+subf1.toString());
//			System.out.println("sub_of2"+subf2.toString());
//			System.out.println("===============");
		}

		return ds;
		
	}
	public Analyser(){
//		String root="/Users/kapila/Documents/repository/";
//		buildRdfRepository(root+"pdfs",root+"txts",root+"parsed",root+"rdfs");
		
		
	}

	
	public static void analyse(){
		List <GR>cullinane=getAuthorList(memmodel, "cullinane");
		
		List <GR>dargay=getAuthorList(memmodel,"dargay");
			
			Set acfl=getObjFeatureList(cullinane, "obj_of", "impact");
			//acfl.addAll(getObjFeatureList(cullinane, "sub", "reduce"));
			Iterator itac =acfl.iterator();
			while (itac.hasNext()){
				System.out.println(itac.next());
			}
		
		
	}
		
		public static double getDS(String w1, String w2, String r, String t,List<GR> mem){
			double z=getMI(w1, r, t,mem)+getMI(w2, r, t,mem);
			Set right1=getRightList(w1, r,mem);
			double y=0;
			Iterator<String> it1 = right1.iterator();
			while (it1.hasNext()) {
				String s =it1.next();
				 y=y+getMI(w1, r, t,mem);
				
				
			}
			Set right2=getRightList(w2,r,mem);
			Iterator <String> it2=right2.iterator();
			while (it2.hasNext()){
				String s= it2.next();
				y=y+getMI(w2,r,s,mem);
			}
			double x=z/(y)*100;

			return x;
			
		}
		
		public static double getDS2(String w1,List<GR> mem1, String w2,List<GR> mem2, String r, String t){
			double z=getMI(w1, r, t,mem1)+getMI(w2, r, t,mem2);
			Set right1=getRightList(w1, r,mem1);
			double y=0;
			Iterator<String> it1 = right1.iterator();
			while (it1.hasNext()) {
				String s =it1.next();
				 y=y+getMI(w1, r, t,mem1);
				
				
			}
			Set right2=getRightList(w2,r,mem2);
			Iterator <String> it2=right2.iterator();
			while (it2.hasNext()){
				String s= it2.next();
				y=y+getMI(w2,r,s,mem2);
			}
			double x=z/(y)*100;

			return x;
			
		}
	
	
	
	public static void buildRdfRepository(String pdfDir, String txtDir, String parsedDir, String rdfDir){
		
	}
	
//	public final List <GR> buildMemModel(String dir){
//		
//		  System.out.println("Start building rdfmodel in memory.....at: "+DateUtils.now());
//			// Create an empty in-memory model and populate it from the graph
//		  ArrayList <GR> t=new ArrayList <GR>();
//				
//			try {
//				
//				 String[] parsedFiles =getFiles(dir,txtfilter);
//				 
//				 
//				
//				for (int i=0;i<parsedFiles.length;i++){
//					System.out.println("Parsing file"+parsedFiles[i]);
//					//System.out.println("reading file "+dir+"/"+rdfFiles[i]);
//					File file = new File(dir+"/"+parsedFiles[i]);
//					
//					
//					if (file.length()>0 ){
//						Text2Mem tm=new Text2Mem(dir+"/"+parsedFiles[i]);
//						t.addAll(tm.buildModel("", ""));
//						
//					}
//					
//					
//					
//				}
//				 System.out.println("Finish building model in memory.....at: "+DateUtils.now());
//				
//			} catch (Exception ex ){
//			}
//			
//			return t;
//	  }
	
	
	  
	  
	  
	  
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
	
	public static int getCount(GR gr, List<GR> mem){
		int c=0;
		Iterator<GR> iterator = mem.iterator();
		int i=0;
		while (iterator.hasNext()) {
			if (iterator.next().isEqual(gr.getLeft(),gr.getGr(),gr.getRight()))
				c++;
			
			
		}
		
		
		return c;
		
		
	}
	public static int getCountGR(String gr,List <GR> mem){
		int c=0;
		Iterator<GR> iterator = mem.iterator();
		int i=0;
		while (iterator.hasNext()) {
			if (iterator.next().isGrEqual(gr))
				c++;		
		}
		
		
		return c;
		
		
	}
	
	public static int getCountLeft(String w1, String gr,List<GR> mem){
		int c=0;
		Iterator<GR> iterator = mem.iterator();
		
		while (iterator.hasNext()) {
			GR t=iterator.next();
			if (t.isLeftEqual(w1)&& t.isGrEqual(gr))
				c++;
			
			
		}
		
		
		return c;
		
		
	}
	public static int getCountRight(String w2, String gr,List<GR> mem){
		int c=0;
		Iterator<GR> iterator = mem.iterator();
		int i=0;
		while (iterator.hasNext()) {
			GR g = (GR) iterator.next();
			if (g.isRightEqual(w2)&&g.isGrEqual(gr))
				c++;
			
			
		}
		
		
		return c;
		
		
	}
	
	public static Set <String> getRightList(String w, String gr, List<GR> mem){
		Set<String> s=new TreeSet<String>();
		Iterator<GR> iterator = mem.iterator();
		
		while (iterator.hasNext()) {
			GR g = (GR) iterator.next();
			if (g.isLeftEqual(w) && g.isGrEqual(gr))
				s.add(g.getRight());
			
			
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
	public static List <GR> getAuthorList(List <GR> mem,String aut){
		List <GR> m=new ArrayList<GR>();
		Iterator <GR> it1=mem.iterator();
		while (it1.hasNext()){
		
			GR g=(GR)it1.next();
			if (g.isAuthor(aut)){
				m.add(g);
			}
		}
		
		return m;
	}
	//getRightWordList
	public static Set <String> getRightWordList(List <GR> mem, String gr){
		Set<String> s=new TreeSet<String>();
		Iterator<GR> iterator = mem.iterator();
		
		while (iterator.hasNext()) {
			GR g = (GR) iterator.next();
			if ( g.isGrEqual(gr))
				s.add(g.getRight());
			
			
		}
		
		
		return s;
		
		
	}
	
	public static Set <String> getObjFeatureList(List <GR> mem, String gr, String w){
		Set<String> s=new TreeSet<String>();
		Iterator<GR> iterator = mem.iterator();
		String obj="";
		while (iterator.hasNext()) {
			GR g = (GR) iterator.next();
			if (gr.equals("obj")){
				obj=g.obj(w);
			}else if (gr.equals("obj_of")){
				obj=g.obj_of(w);
			}
			if ( obj.length()>0 && !(isStopWord(obj)))
				s.add(obj);
		}
		
		
		return s;
		
		
	}
	
	public static Set <String> getSubFeatureList(List <GR> mem, String gr, String w){
		Set<String> s=new TreeSet<String>();
		Iterator<GR> iterator = mem.iterator();
		String obj="";
		while (iterator.hasNext()) {
			GR g = (GR) iterator.next();
			if (gr.equals("sub")){
				obj=g.sub(w);
			}else if (gr.equals("sub_of")){
				obj=g.sub_of(w);
			}
			if ( obj.length()>0 && !(isStopWord(obj)))
				s.add(obj);
		}
		
		
		return s;
		
		
	}
	public static Set <String> getModFeatureList(List <GR> mem, String gr, String w){
		Set<String> s=new TreeSet<String>();
		Iterator<GR> iterator = mem.iterator();
		String obj="";
		while (iterator.hasNext()) {
			GR g = (GR) iterator.next();
			if (gr.equals("mod")){
				obj=g.mod(w);
			}else if (gr.equals("mod_of")){
				obj=g.mod_of(w);
			}
			if ( obj.length()>0 && !(isStopWord(obj)))
				s.add(obj);
		}
		
		
		return s;
		
		
	}
	
	
	
	
	public static int getTotal(List <GR> mem){
		return mem.size();
	}
	
	public static boolean isStopWord(String w){
		Iterator it =swl.iterator();
		boolean res=false;
		while (it.hasNext()){
			String s=(String)it.next();
			if (w.equals(s)){
				res= true;
				break;
			}
			
		}
		return res;
	}
}
