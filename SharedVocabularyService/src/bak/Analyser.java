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

import svs.GR;

import com.sun.org.apache.xpath.internal.axes.SubContextList;



import svs.Text2Mem;



public class Analyser {
	
	private static List <GR>  memmodel;
	private static String [] stopwords={"i","a","A","about","all","an","and","are","as","at","be","but","by","com","each","either","for","from","how","in","is","it","of","on","or","p","that","the","this","to","was","well","which","what","when","where","who","will","with","the","www"};
	private static List <String>swl= Arrays.asList(stopwords);
	
	
	public static void main(String[] args) throws NullPointerException{
		
		
		//String root="D:/repository/";
		String root="/Users/kapila/Documents/repository/parsed";
		Text2Mem t =new Text2Mem(root);
		memmodel=t.buildModel(root);
		
		
		listModel(memmodel);
		
		List <GR>cullinane=getAuthorList(memmodel, "cullinane");
		
		List <GR>dargay=getAuthorList(memmodel,"dargay");

		Set <String> crw=getRightWordList(cullinane, "obj");
		Set <String> common=getRightWordList(cullinane, "obj");
		Set <String> cullunique=getRightWordList(cullinane, "obj");
		Set <String> drw=getRightWordList(dargay, "obj");
		Set <String> dargayunique=getRightWordList(dargay, "obj");
	
		crw.removeAll(swl);
		System.out.println(crw.size());		
		System.out.println(drw.size());	
		
	
		System.out.println(common.size());
		
		common.retainAll(drw);
		System.out.println(common.size());
		
		System.out.println(crw.size());
		cullunique.removeAll(common);
		dargayunique.removeAll(common);
		
		
		Set <String> crwverb0=getLeftWordList(cullinane, "sub");
		Set <String> crwverb1=getLeftWordList(cullinane, "sub");
		Set <String> crwverb2=getLeftWordList(cullinane, "dobj");
		crwverb1.addAll(crwverb2);
		//crwverb1.retainAll(crwverb2);
		//crwverb1.addAll(crwverb0);
		crwverb1.removeAll(swl);
		System.out.println("cul-verbs "+crwverb1.size());
		System.out.println("cul-verbs "+crwverb2.size());
		
		Set <String> drwverb0=getLeftWordList(dargay, "sub");
		Set <String> drwverb1=getLeftWordList(dargay, "sub");
		Set <String> drwverb2=getLeftWordList(dargay, "dobj");
		//drwverb1.retainAll(drwverb2);
		drwverb1.removeAll(swl);
		System.out.println("dar verbs "+drwverb1.size());
		System.out.println("dar verbs "+drwverb2.size());
		

		
		drwverb1.addAll(crwverb2);
		//AnalyseNew(crwverb2, drwverb2);
		Set <String> crwnoun0=getRightWordList(cullinane, "sub");
		
		Set <String> crwnoun1=getRightWordList(cullinane, "sub");
		Set <String> crwnoun2=getRightWordList(cullinane, "dobj");
		//crwnoun1.retainAll(crwverb2);
		crwnoun1.addAll(crwnoun2);
		crwnoun2.removeAll(swl);
		
		Set <String> drwnoun1=getRightWordList(dargay, "sub");
		Set <String> drwnoun2=getRightWordList(dargay, "dobj");
		//drwnoun1.retainAll(drwverb2);
		drwnoun1.addAll(drwnoun2);
		drwnoun1.removeAll(swl);
		System.out.println("cul nouns "+crwnoun1.size());
		//System.out.println("cul nouns "+crwnoun2.size());
		System.out.println("dar nouns "+drwnoun1.size());
		//System.out.println("dar nouns "+drwnoun2.size());
	
		crwnoun0.retainAll(drwnoun1);
		System.out.println("common "+crwnoun0.size());
		
		
		
		
		try {  
			JWNL.initialize(TestDefaults.getInputStream());
			System.out.println("analysis results=" +getDSNoun("analysis", cullinane, "result", dargay));
			
			//get immediate relationships
//			for (String n1:crwnoun1)
//				for (String n2:drwnoun1){
//					IndexWord iw1 = Dictionary.getInstance().lookupIndexWord(POS.NOUN, n1);
//					IndexWord iw2 = Dictionary.getInstance().lookupIndexWord(POS.NOUN, n2);
//					
//					
//					if((iw1!=null)&&(iw2!=null)&&(!n1.equals(n2))){
//						int rel=RelationshipFinder.getInstance().getImmediateRelationship(iw1,iw2);
//						if (rel!=-1){
//							//System.out.println(iw1.getSense(rel).toString());
//							System.out.println(n1+", "+n2+" ="+rel +"DS="+getDSNoun(n1, cullinane, n2, dargay));
//						}
//					}
//				}
			
			
			Set<String> top20=new TreeSet<String>();
			top20.add("analysis");
			top20.add("process");
			top20.add("commission");
			top20.add("statement");
			top20.add("land");
			double maxsim=-1.0;
			for (String n1:crwnoun1){
				//if (top20.contains(n1))
				for (String n2:drwnoun1){
					
					IndexWord iw1 = Dictionary.getInstance().lookupIndexWord(POS.NOUN, n1);
					IndexWord iw2 = Dictionary.getInstance().lookupIndexWord(POS.NOUN, n2);
					if((iw1!=null)&&(iw2!=null)&&(!n1.equals(n2))){
						 maxsim=0;
						for(Synset s1: iw1.getSenses())
							for (Synset s2:iw2.getSenses()){
								RelationshipList rl= RelationshipFinder.getInstance().findRelationships(s1, s2, PointerType.HYPERNYM);
								if (rl.size()>0){
									double sim=1.0/rl.getShallowest().getSize();
									if (sim>=maxsim)
										maxsim=sim;
								}
							}
								
						
							
						
					}//wordnet path length
					double ds=getDSNoun(n1, cullinane, n2, dargay);
//					;
					if (ds>0.8|| maxsim>0.6)
						System.out.println(n1+", "+n2+", "+ds+", "+maxsim);
					
					
				}
			}
			
			
			
//			for (String n1:crwnoun1)
//				for (String n2:drwnoun1){
//					IndexWord iw1 = Dictionary.getInstance().lookupIndexWord(POS.NOUN, n1);
//					IndexWord iw2 = Dictionary.getInstance().lookupIndexWord(POS.NOUN, n2);
//					if((iw1!=null)&&(iw2!=null)&&(!n1.equals(n2))){
//						for(Synset s1: iw1.getSenses())
//							for (Synset s2:iw2.getSenses()){
//							
//							PointerTargetTree ptt1=	PointerUtils.getInstance().getHypernymTree(s1);
//							PointerTargetTree ptt2=	PointerUtils.getInstance().getHypernymTree(s2);
//							if (ptt1.getRootNode().equals(ptt2.getRootNode())){
//								
//								
//								
//								System.out.println(n1+","+n2);
//							}
//					
//							}
//								
//						
//							
//						
//					}
//				}
			
			
		} catch (JWNLException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
	}
	
	public static void listModel(List <GR>l){
		for (GR g:l){
			System.out.println(g.toString());
		}
		
	}
	
	public static void AnalyseNew(Set s1, Set s2){
		List <GR>cullinane=getAuthorList(memmodel, "cullinane");
		
		List <GR>dargay=getAuthorList(memmodel,"dargay");

		Iterator<String> it1 =s1.iterator();
		
		while (it1.hasNext()){		
			String w1=it1.next();
			Iterator<String> it2 =s2.iterator();
			while (it2.hasNext()){
				String w2 =it2.next();
				//getDSNEW(w1, cullinane,w2,dargay);
				
				
			}
		}
	}
	
	public static void AnalyseNouns(Set s1, Set s2){
		List <GR>cullinane=getAuthorList(memmodel, "cullinane");
		
		List <GR>dargay=getAuthorList(memmodel,"dargay");

		Iterator<String> it1 =s1.iterator();
		
		while (it1.hasNext()){	
			
			String w1=it1.next();
			if(w1.contains("mileage")){
			Iterator<String> it2 =s2.iterator();
			while (it2.hasNext()){
				String w2 =it2.next();
				getDSNoun(w1, cullinane,w2,dargay);
				//System.out.println(w1+"="+w2);
				
			}
			}
		}
	}
	
	public static double getDSNEW(String w1,List<GR> mem1, String w2, List<GR> mem2){
		double x=0.0;
		
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
			System.out.println(w1+"-"+"obj"+objf1.toString()+"<=>"+w2+"-"+"obj"+objf2.toString());
//			System.out.println(w1+"-"+"sub"+subf1.toString()+"<=>"+w2+"-"+"sub"+subf2.toString());
//			System.out.println(w1+"-"+"mod"+modf1.toString()+"<=>"+w2+"-"+"mod"+modf2.toString());
//			
			double top=0.0;
			Iterator<String> itcommonobj = objcommon.iterator();
			while (itcommonobj.hasNext()){
				String cw=itcommonobj.next();
				top=top+getMI(w1, "obj", cw, mem1)+getMI(w2, "obj", cw, mem2);
			}
			
			Iterator<String> itcommonsub = subjcommon.iterator();
			while (itcommonsub.hasNext()){
				String cw=itcommonsub.next();
				top=top+getMI(w1, "sub", cw, mem1)+getMI(w2, "sub", cw, mem2);
			}
			
			
			Iterator<String> itcommonmod = modcommon.iterator();
			while (itcommonmod.hasNext()){
				String cw=itcommonmod.next();
				top=top+getMI(w1, "mod", cw, mem1)+getMI(w2, "mod", cw, mem2);
			}
			
			//System.out.println(top);
			
			double bottom=0.0;
			Iterator<String> itobj1 = objf1.iterator();
			while (itobj1.hasNext()){
				String w=itobj1.next();
				bottom=bottom+getMI(w1, "obj", w, mem1);
			}
			Iterator<String> itobj2 = objf2.iterator();
			while (itobj2.hasNext()){
				String w=itobj2.next();
				bottom=bottom+getMI(w2, "obj", w, mem2);
			}
			Iterator<String> itsubj1 = subf1.iterator();
			while (itsubj1.hasNext()){
				String w=itsubj1.next();
				bottom=bottom+getMI(w1, "sub", w, mem1);
			}
			Iterator<String> itsubj2 = subf2.iterator();
			while (itsubj2.hasNext()){
				String w=itsubj2.next();
				bottom=bottom+getMI(w1, "sub", w, mem2);
			}
			Iterator<String> itmod1 = modf1.iterator();
			while (itmod1.hasNext()){
				String w=itmod1.next();
				bottom=bottom+getMI(w1, "mod", w, mem1);
			}
			Iterator<String> itmod2 = modf2.iterator();
			while (itmod2.hasNext()){
				String w=itmod2.next();
				bottom=bottom+getMI(w1, "mod", w, mem2);
			}
			
			
			
			
			double ds=top/bottom*100;
			System.out.println("DS(cullinane-dargay), "+ w1.toUpperCase() + ", "+w2.toUpperCase()+ " ,"+ds);
		}

		return x;
		
	}
	public static double getDSNoun(String w1,List<GR> mem1, String w2, List<GR> mem2)  {
		double ds=0.0;
		try{
			
			
			Set<String> wnCommonObj1=new TreeSet<String>();
			Set<String> wnCommonObj2=new TreeSet<String>();	
			Set<String> wnCommonSub1=new TreeSet<String>();	
			Set<String> wnCommonSub2=new TreeSet<String>();
		Set<String> objf1= getObjFeatureList(mem1, "obj_of", w1);
		Set<String> objcommon= getObjFeatureList(mem1, "obj_of", w1);
		Set<String> objf2= getObjFeatureList(mem2, "obj_of", w2);
		
		
		
		objcommon.retainAll(objf2);
		
		
		
		objf1.removeAll(objcommon);
		objf2.removeAll(objcommon);
		
		//System.out.println(objf1.toString()+"+++++++"+objf2.toString());
		// dice co-efficient
		for (String f1:objf1){
			
			for (String f2:objf2)
			{			
				IndexWord iw1 = Dictionary.getInstance().lookupIndexWord(POS.VERB, f1);
				IndexWord iw2 = Dictionary.getInstance().lookupIndexWord(POS.VERB, f2);
				
				
				
				int w1ns=iw1.getSenses().length;
				int w2ns=iw2.getSenses().length;
				int synscommon =0;
				if (iw1 !=null &&iw2!=null)
					
				for (Synset s1:iw1.getSenses()){
						for (Synset s2:iw2.getSenses()){
							boolean synfound =false;
							for (Word nw1:s1.getWords()){
							//System.out.println("s1"+s1.toString());
							//System.out.println("s2"+s2.toString());
							for (Word nw2:s2.getWords()){
								
								if (nw1.getLemma().equals(nw2.getLemma()))
								{
									//System.out.println(s1.toString()+","+s2.toString());
									wnCommonObj1.add(f1);
									wnCommonObj2.add(f2);
									synfound=true;
									
								}
								
							}
							
						}
							if (synfound)
								synscommon++;
						
					}
					
				}
				double dice =(2*synscommon)/(w1ns+w2ns);
				System.out.println("dice ="+dice);
				
			}
		}
		
		Set<String> subf1= getSubFeatureList(mem1, "sub_of", w1);
		Set<String> subjcommon= getSubFeatureList(mem1, "sub_of", w1);
		Set<String> subf2= getSubFeatureList(mem2, "sub_of", w2);
		
		subjcommon.retainAll(subf2);
		
		subf1.removeAll(subjcommon);
		subf2.removeAll(subjcommon);
		
		
		for (String f1:subf1){
			
			for (String f2:subf2)
			{
				
				IndexWord iw1 = Dictionary.getInstance().lookupIndexWord(POS.VERB, f1);
				IndexWord iw2 = Dictionary.getInstance().lookupIndexWord(POS.VERB, f2);
			
				
				if (iw1 !=null &&iw2!=null)
				for (Synset s1:iw1.getSenses()){
					
					for (Word nw1:s1.getWords()){
						for (Synset s2:iw2.getSenses()){
							
							for (Word nw2:s2.getWords()){
								
								if (nw1.getLemma().equals(nw2.getLemma()))
								{
									//System.out.println(s1.toString()+"  "+f1+"======="+f2+"  "+s2.toString());
									wnCommonSub1.add(f1);
									wnCommonSub2.add(f2);
								}
								
							}
							
						}
						
						
					}
					
				}
				
				
				
			}
		}
		Set<String> modf1= getModFeatureList(mem1, "mod_of", w1);
		Set<String> modcommon= getModFeatureList(mem1, "mod_of", w1);
		Set<String> modf2= getModFeatureList(mem2, "mod_of", w2);

		
		
		
		modcommon.retainAll(modf2);
	
		if (true ){
			//System.out.println(objcommon);
			//System.out.println(w1+"-"+"obj"+wnCommonObj1.toString()+"<=>"+w2+"-"+"obj"+wnCommonObj2.toString());
			//System.out.println(w1+"-"+"subj"+wnCommonSub1.toString()+"<=>"+w2+"-"+"obj"+wnCommonSub2.toString());
			double top=0.0;
			for (String cw:objcommon){
			
				top=top+getMInoun(w1, "obj", cw, mem1)+getMInoun(w2, "obj", cw, mem2);
				
			}
			
			Iterator<String> itcommonobj1 = wnCommonObj1.iterator();
			while (itcommonobj1.hasNext()){
				String cw=itcommonobj1.next();
				top=top+getMInoun(w1, "obj", cw, mem1);
				
			}
			Iterator<String> itcommonobj2 = wnCommonObj2.iterator();
			while (itcommonobj2.hasNext()){
				String cw=itcommonobj2.next();
				top=top+getMInoun(w2, "obj", cw, mem2);
				
			}
			
			for (String cw:subjcommon){
				
				top=top+getMInoun(w1, "sub", cw, mem1)+getMInoun(w2, "sub", cw, mem2);
				
			}
			
			Iterator<String> itcommonsub1 = wnCommonSub1.iterator();
			while (itcommonsub1.hasNext()){
				String cw=itcommonsub1.next();
				top=top+getMInoun(w1, "sub", cw, mem1);
			}
			Iterator<String> itcommonsub2 = wnCommonSub2.iterator();
			while (itcommonsub2.hasNext()){
				String cw=itcommonsub2.next();
				top=top+getMInoun(w2, "sub", cw, mem2);
			}
			
			
			
			Iterator<String> itcommonmod = modcommon.iterator();
			while (itcommonmod.hasNext()){
				String cw=itcommonmod.next();
				top=top+getMInoun(w1, "mod", cw, mem1)+getMInoun(w2, "mod", cw, mem2);
			}
			
			//System.out.println(top);
			
			double bottom=0.0;
			Iterator<String> itobj1 = objf1.iterator();
			while (itobj1.hasNext()){
				String w=itobj1.next();
				bottom=bottom+getMInoun(w1, "obj", w, mem1);
			}
			Iterator<String> itobj2 = objf2.iterator();
			while (itobj2.hasNext()){
				String w=itobj2.next();
				bottom=bottom+getMInoun(w2, "obj", w, mem2);
			}
			Iterator<String> itsubj1 = subf1.iterator();
			while (itsubj1.hasNext()){
				String w=itsubj1.next();
				bottom=bottom+getMInoun(w1, "sub", w, mem1);
			}
			Iterator<String> itsubj2 = subf2.iterator();
			while (itsubj2.hasNext()){
				String w=itsubj2.next();
				bottom=bottom+getMInoun(w2, "sub", w, mem2);
			}
			Iterator<String> itmod1 = modf1.iterator();
			while (itmod1.hasNext()){
				String w=itmod1.next();
				bottom=bottom+getMInoun(w1, "mod", w, mem1);
			}
			Iterator<String> itmod2 = modf2.iterator();
			while (itmod2.hasNext()){
				String w=itmod2.next();
				bottom=bottom+getMInoun(w2, "mod", w, mem2);
			}
			
			
			
			
			ds=top/bottom;

		}
		}
		catch (Exception ex){
			
			System.out.println(ex.getMessage());
		}
		return ds;
		
	}
	
	public static double getMI(String w1, String r, String w2, List <GR> mem) {
		GR gr= new GR(w1,r,w2,"");
		double x=getCount(gr,mem)*getCountGR(r,mem);
		double y=getCountLeft(w1, r,mem)*getCountRight(w2, r,mem);
		if (x!=0)
			return -Math.log10(x/y);
		else
			return 0;
		
		
	}	
	public static double getMInoun(String w1, String r, String w2, List <GR> mem) {
		GR gr= new GR(w2,r,w1,"");
		double x=getCount(gr,mem)*getCountGR(r,mem);
		double y=getCountLeft(w2, r,mem)*getCountRight(w1, r,mem);
		if (x!=0)
			return -Math.log10(x/y);
		else
			return 0;
		
		
	}
	
	
	
	public Analyser(){
//		String root="/Users/kapila/Documents/repository/";
//		buildRdfRepository(root+"pdfs",root+"txts",root+"parsed",root+"rdfs");
		
		
	}

	
	public static void analyse(){
		try{
		JWNL.initialize(TestDefaults.getInputStream());
		List <GR>cullinane=getAuthorList(memmodel, "cullinane");
		
		List <GR>dargay=getAuthorList(memmodel,"dargay");
			
			Set acfl=getObjFeatureList(dargay, "obj", "reduce");
			//acfl.addAll(getObjFeatureList(cullinane, "sub", "reduce"));
			Iterator itac =acfl.iterator();
			while (itac.hasNext()){
				System.out.println(itac.next());
			}
		}catch (Exception ex)
		{
			System.out.println(ex.getMessage());
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
