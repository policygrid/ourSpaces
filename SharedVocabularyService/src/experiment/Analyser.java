package experiment;

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

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.PointerType;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.relationship.RelationshipFinder;
import net.didion.jwnl.data.relationship.RelationshipList;
import net.didion.jwnl.dictionary.Dictionary;
import wn.TestDefaults;





public class Analyser {
	
	private static List <GR>  memmodel;
	private static String [] stopwords={"i","a","all","and","it","p","that","the","but","they","each","these","between","itself","both","or","this","so","to","vmt","was","well","much","which","what","when","where","who","will","with","those","the","www"};
	private static List <String>swl= Arrays.asList(stopwords);
	public static void main(String[] args) throws NullPointerException{
		
		
		//String root="D:/repository/parsed";
		//String root="/Users/kapila/Documents/repository/parsed/";//"
		String root="/Users/kapila/Documents/repository/experiment2/parsed/";
				
		Text2Mem t =new Text2Mem(root);
		memmodel=t.buildModel(root) ;
		List <GR>corpus1=t.getAuthorList( "cullinane1");
		List <GR>corpus2=t.getAuthorList("cullinane2");

		//get Nouns
		Set <String> corp1Nouns=getDepWordList(corpus1, "obj");
		corp1Nouns.addAll(getDepWordList(corpus1, "sub"));
		corp1Nouns.addAll(getHeadWordList(corpus1, "mod"));
		Set <String> corp2Nouns=getDepWordList(corpus2, "obj");
		corp2Nouns.addAll(getDepWordList(corpus2, "sub"));
		corp2Nouns.addAll(getHeadWordList(corpus2, "mod"));
		
		corp1Nouns.removeAll(swl);
		corp2Nouns.removeAll(swl);
		
		System.out.println("corpus1 Nouns:"+corp1Nouns.size());		
		System.out.println("corpus2 Nouns:"+corp2Nouns.size());	
		//get verbs
		
		
		Set <String> corp1Verbs=getHeadWordList(corpus1, "obj");
		corp1Verbs.addAll(getHeadWordList(corpus1, "sub"));
		corp1Verbs.addAll(getHeadWordList(corpus1, "mod"));
		Set <String> corp2Verbs=getHeadWordList(corpus2, "obj");
		corp2Verbs.addAll(getHeadWordList(corpus2, "sub"));
		corp2Verbs.addAll(getHeadWordList(corpus2, "mod"));
		
		corp1Verbs.removeAll(swl);
		corp2Verbs.removeAll(swl);
		
		System.out.println("corpus1 Verbs:"+corp1Verbs.size());		
		System.out.println("corpus2 verbs:"+corp2Verbs.size());
	
		//AnalyseVerbs(corp1Verbs,corp2Verbs);
		AnalyseNouns(corp1Nouns,corp2Nouns);
		//ReAnalyseNounsTop100(corp1Nouns,corp2Nouns);
		
	}
	
	public static void ReAnalyseNounsTop100(Set<String> s1, Set<String> s2){
		List <GR>cullinane=getAuthorList(memmodel, "cullinane");
		
		List <GR>dargay=getAuthorList(memmodel,"dargay");
		BufferedWriter out;
		try{
		 
				JWNL.initialize(TestDefaults.getInputStream());
			 out = new BufferedWriter(new FileWriter("/Users/kapila/Documents/repository/experiment1/results/c1c2.csv"));
			 Text2Set t2s=new Text2Set();
			 Set<String> top200=t2s.text2Set("/Users/kapila/Documents/repository/experiment1/results/top100.csv");
			 for (String w1:top200){
			 //if(w1.equals("proportion")){
					
					for(String w2:s2){
					//	if(w2.equals("agreement")){
						double[] ds=getDSNoun(w1, cullinane,w2,dargay);
						double pl=getPathLength(w1, w2);
						
						if ((ds[0]>10)){

							System.out.println(w1+", "+w2+", "+ds[0]+", "+", "+ds[1]+", "+ds[2]+","+pl);
						 	out.write(w1+","+w2+","+ds[0]+","+ds[1]+","+ds[2]+","+pl+System.getProperty("line.separator"));  
					        out.flush();
						}
						}
//					}
//					}
				}
			  
			  
			  
			  
			  
			 out.close();
			  
	
		}catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
				
		
	}
	public static double getPathLength(String n1, String n2){
		double maxsim=-1.0;
		try {  
			

			

					IndexWord iw1 = Dictionary.getInstance().lookupIndexWord(POS.NOUN, n1);
					IndexWord iw2 = Dictionary.getInstance().lookupIndexWord(POS.NOUN, n2);
					if((iw1!=null)&&(iw2!=null)){
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
					
					
			
		} catch (JWNLException e) {
			e.printStackTrace();
			
		}
		return maxsim;
	
	}
	public static void AnalyseNouns(Set<String> s1, Set<String> s2){
		List <GR>cullinane=getAuthorList(memmodel, "cullinane1");
		
		List <GR>dargay=getAuthorList(memmodel,"cullinane2");
		BufferedWriter out;
		try{
			 out = new BufferedWriter(new FileWriter("/Users/kapila/Documents/repository/experiment2/results/cullinane1Vscullinane2.csv"));
			 for (String w1:s1){
//				 if(w1.equals("land")){
					
					for(String w2:s2){
				//		if(w2.equals("agreement")){
						double[] ds=getDSNoun(w1, cullinane,w2,dargay);
						
						
						if ((ds[0]>5)){
//							 out.write(w1+", "+w2+", "+ds);
							System.out.println(w1+", "+w2+", "+ds[0]+", "+", "+ds[1]+", "+ds[2]);
						 	out.write(w1+", "+w2+", "+ds[0]+", "+ds[1]+", "+ds[2]+System.getProperty("line.separator"));  
					        out.flush();
						}
						}
					}
				//	}
	//			}
			  
			  
			  
			  
			  
			 out.close();
			  
	
		}catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
				
		
	}
	
	
	public static void AnalyseVerbs(Set<String> s1, Set<String> s2){
		List <GR>cullinane=getAuthorList(memmodel, "cullinane1");
		
		List <GR>dargay=getAuthorList(memmodel,"cullinane2");
		BufferedWriter out;
		try{
			 out = new BufferedWriter(new FileWriter("/Users/kapila/Documents/repository/experiment2/results/cullinane1Vscullinane2(verbs)060212.csv"));
			 for (String w1:s1){
//				 if(w1.equals("land")){
					
					for(String w2:s2){
				//		if(w2.equals("agreement")){
						double[] ds=getDSVerb(w1, cullinane,w2,dargay);
						
						
						if ((ds[0]>5)){
//							 out.write(w1+", "+w2+", "+ds);
							System.out.println(w1+", "+w2+", "+ds[0]+", "+", "+ds[1]+", "+ds[2]);
						 	out.write(w1+", "+w2+", "+ds[0]+", "+ds[1]+", "+ds[2]+System.getProperty("line.separator"));  
					        out.flush();
						}
						}
					}
				//	}
	//			}
			  
			  
			  
			  
			  
			 out.close();
			  
	
		}catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
				
		
	}
	public static double[] getDSVerb(String w1,List<GR> mem1, String w2, List<GR> mem2){
		
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
		double ds=0.0;
		double[]  dsarr=new double[3];
		if ((objcommon.size()>0 && subjcommon.size()>0)&&((objf1.size()>1 && objf2.size()>1) || (subf1.size()>1 && subf2.size() >1)) ){
//			System.out.println(w1+"-"+"obj"+objf1.toString()+"<=>"+w2+"-"+"obj"+objf2.toString());
//			System.out.println(w1+"-"+"sub"+subf1.toString()+"<=>"+w2+"-"+"sub"+subf2.toString());
//			System.out.println(w1+"-"+"mod"+modf1.toString()+"<=>"+w2+"-"+"mod"+modf2.toString());
//			
//			
//			System.out.println(objcommon.toString());
//			System.out.println(subjcommon.toString());
//			System.out.println(modcommon.toString());

			double tops=0.0;
			double topo=0.0;
			double topm=0.0;
			double t1i=0.0;double t2i=0.0;
			double pt=0.0,rt=0.0;
			for(String cw:objcommon){
				
				GR t1=new GR(w1,"obj",cw,"");
				GR t2=new GR(w2,"obj",cw,"");
			    t1i=t1.getMI(mem1);
				t2i=t2.getMI(mem2);
				topo=topo+t1i+t2i;
				pt=pt+t1i;
				rt=rt+t2i;
				
			}
			
			for(String cw:subjcommon){
				GR t1=new GR(w1,"sub",cw,"");
				GR t2=new GR(w2,"sub",cw,"");
				t1i=t1.getMI(mem1);
				t2i=t2.getMI(mem2);
				tops=tops+t1i+t2i;
				pt=pt+t1i;
				rt=rt+t2i;
			}
			
			
			for(String cw:modcommon){
				GR t1=new GR(w1,"mod",cw,"");
				GR t2=new GR(w2,"mod",cw,"");
				t1i=t1.getMI(mem1);
				t2i=t2.getMI(mem2);
				topm=topm+t1i+t2i;
				pt=pt+t1i;
				rt=rt+t2i;
				
			}
			
			double pb=0.0,rb=0.0;
			double devisor=0.0;
			for(String w:objf1){
				GR t1=new GR(w1,"obj",w,"");
				t1i=t1.getMI( mem1);
				devisor=devisor+t1i;
				pb=pb+t1i;
			}
			for(String w:objf2){
				GR t2=new GR(w2,"obj",w,"");
				t2i=t2.getMI( mem2);
				devisor=devisor+t2i;
				rb=rb+t2i;
			}
			for(String w:subf1){
				GR t1=new GR(w1,"sub",w,"");
				t1i=t1.getMI( mem1);
				devisor=devisor+t1i;
				pb=pb+t1i;
			}
			for(String w:subf2){
				GR t2=new GR(w2,"sub",w,"");
				t2i=t2.getMI( mem2);
				devisor=devisor+t2i;
				rb=rb+t2i;
			}
			for(String w:modf1){
				GR t1=new GR(w1,"mod",w,"");
				t1i=t1.getMI( mem1);
				devisor=devisor+t1i;
				pb=pb+t1i;
				
			}
			for(String w:modf2){
				GR t2=new GR(w2,"mod",w,"");
				t2i=t2.getMI(mem2);
				devisor=devisor+t2i;
				rb=rb+t2i;
				
			}
			
			
			
			
			ds=(topo+tops+topm)/devisor*100;
			double p=pt/pb;
			double r=rt/rb;
			double dscocr=  (2*p*r)/(p+r);
			double dsweighted=(0.5*topo+0.25*tops+0.25*topm)/devisor*100;
//			System.out.println("DS(cullinane-dargay), "+ w1.toUpperCase() + ", "+w2.toUpperCase()+ " ,"+ds);
//			System.out.println("obj_of1"+objf1.toString());
//			System.out.println("obj_of2"+objf2.toString());
//			System.out.println("sub_of1"+subf1.toString());
//			System.out.println("sub_of2"+subf2.toString());
//			System.out.println("===============");
			
			dsarr[0]=ds;
			dsarr[1]=dscocr*100;
			dsarr[2]=dsweighted;
		}

		return dsarr;
		
	}
	public static double[] getDSNoun(String w1,List<GR> mem1, String w2, List<GR> mem2){
		
		
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
		double[]  dsarr=new double[3];
		//if ((objcommon.size()>0 && subjcommon.size()>0)&&((objf1.size()>1 && objf2.size()>1) || (subf1.size()>1 && subf2.size() >1)) ){
		if ((objcommon.size()>0 && subjcommon.size()>0)&&((objf1.size()>1 && objf2.size()>1) || (subf1.size()>1 && subf2.size() >1)) ){
		//if ((objcommon.size()>0 && subjcommon.size()>0)&&((objf1.size()>2 && objf2.size()>2) && (subf1.size()>2 && subf2.size() >2)) ){
//			System.out.println(w1+"-"+"obj"+objf1.toString()+"<=>"+w2+"-"+"obj"+objf2.toString());
//			System.out.println(w1+"-"+"sub"+subf1.toString()+"<=>"+w2+"-"+"sub"+subf2.toString());
//			System.out.println(w1+"-"+"mod"+modf1.toString()+"<=>"+w2+"-"+"mod"+modf2.toString());
//			
//			System.out.println(objcommon.toString());
//			System.out.println(subjcommon.toString());
//			System.out.println(modcommon.toString());

			
			double tops=0.0;
			double topo=0.0;
			double topm=0.0;
			double t1i=0.0;double t2i=0.0;
			double pt=0.0,rt=0.0;
			for(String cw:objcommon){
				
				GR t1=new GR(cw,"obj",w1,"");
				GR t2=new GR(cw,"obj",w2,"");
			    t1i=t1.getMI(mem1);
				t2i=t2.getMI(mem2);
				topo=topo+t1i+t2i;
				pt=pt+t1i;
				rt=rt+t2i;
				
			}
			
			for(String cw:subjcommon){
				GR t1=new GR(cw,"sub",w1,"");
				GR t2=new GR(cw,"sub",w2,"");
				t1i=t1.getMI(mem1);
				t2i=t2.getMI(mem2);
				tops=tops+t1i+t2i;
				pt=pt+t1i;
				rt=rt+t2i;
			}
			
			
			for(String cw:modcommon){
				GR t1=new GR(w1,"mod",cw,"");
				GR t2=new GR(w2,"mod",cw,"");
				t1i=t1.getMI(mem1);
				t2i=t2.getMI(mem2);
				topm=topm+t1i+t2i;
				pt=pt+t1i;
				rt=rt+t2i;
				
			}
			
			double pb=0.0,rb=0.0;
			double devisor=0.0;
			for(String w:objf1){
				GR t1=new GR(w,"obj",w1,"");
				t1i=t1.getMI( mem1);
				devisor=devisor+t1i;
				pb=pb+t1i;
			}
			for(String w:objf2){
				GR t2=new GR(w,"obj",w2,"");
				t2i=t2.getMI( mem2);
				devisor=devisor+t2i;
				rb=rb+t2i;
			}
			for(String w:subf1){
				GR t1=new GR(w,"sub",w1,"");
				t1i=t1.getMI( mem1);
				devisor=devisor+t1i;
				pb=pb+t1i;
			}
			for(String w:subf2){
				GR t2=new GR(w,"sub",w2,"");
				t2i=t2.getMI( mem2);
				devisor=devisor+t2i;
				rb=rb+t2i;
			}
			for(String w:modf1){
				GR t1=new GR(w1,"mod",w,"");
				t1i=t1.getMI( mem1);
				devisor=devisor+t1i;
				pb=pb+t1i;
				
			}
			for(String w:modf2){
				GR t2=new GR(w2,"mod",w,"");
				t2i=t2.getMI(mem2);
				devisor=devisor+t2i;
				rb=rb+t2i;
				
			}
			
			
			
			
			ds=(topo+tops+topm)/devisor*100;
			double p=pt/pb;
			double r=rt/rb;
			double dscocr=  (2*p*r)/(p+r);
			double dsweighted=(0.25*topo+0.25*tops+0.5*topm)/devisor*100;
//			System.out.println("DS(cullinane-dargay), "+ w1.toUpperCase() + ", "+w2.toUpperCase()+ " ,"+ds);
//			System.out.println("obj_of1"+objf1.toString());
//			System.out.println("obj_of2"+objf2.toString());
//			System.out.println("sub_of1"+subf1.toString());
//			System.out.println("sub_of2"+subf2.toString());
//			System.out.println("===============");
			
			dsarr[0]=ds;
			dsarr[1]=dscocr*100;
			dsarr[2]=dsweighted;
		}

		return dsarr;
		
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
		if (((objf1.size()>2 && objf2.size()>2) || (subf1.size()>2 && subf2.size() >2)) ){
//			System.out.println(w1+"-"+"obj"+objf1.toString()+"<=>"+w2+"-"+"obj"+objf2.toString());
//			System.out.println(w1+"-"+"sub"+subf1.toString()+"<=>"+w2+"-"+"sub"+subf2.toString());
//			System.out.println(w1+"-"+"mod"+modf1.toString()+"<=>"+w2+"-"+"mod"+modf2.toString());
//			
			double pt=0.0,rt=0.0;
			for(String cw:objcommon){
				GR t1=new GR(cw,"obj",w1,"");
				GR t2=new GR(cw,"obj",w2,"");
				pt=pt+t1.getMI( mem1);
				rt=rt+t2.getMI( mem2);
				//top=top+t1.getMI( mem1);
			}
			
			for(String cw:subjcommon){
				GR t1=new GR(cw,"sub",w1,"");
				GR t2=new GR(cw,"sub",w2,"");
				pt=pt+t1.getMI(mem1);
				rt=rt	+t2.getMI( mem2);
			}
			
			
			for(String cw:modcommon){
				GR t1=new GR(w1,"mod",cw,"");
				GR t2=new GR(w2,"mod",cw,"");
				pt=pt+t1.getMI(mem1);
				rt=rt+t2.getMI( mem2);
			}
			
			//System.out.println(top);
			
			double pb=0.0,rb=0.0;
			for(String w:objf1){
				GR t1=new GR(w,"obj",w1,"");
				pb=pb+t1.getMI( mem1);
			}
			for(String w:objf2){
				GR t2=new GR(w,"obj",w2,"");
				rb=rb+t2.getMI( mem2);
			}
			for(String w:subf1){
				GR t1=new GR(w,"sub",w1,"");
				pb=pb+t1.getMI( mem1);
			}
			for(String w:subf2){
				GR t2=new GR(w,"sub",w2,"");
				rb=rb+t2.getMI( mem2);
			}
			for(String w:modf1){
				GR t1=new GR(w1,"mod",w,"");
				pb=pb+t1.getMI( mem1);
			}
			for(String w:modf2){
				GR t2=new GR(w2,"mod",w,"");
				rb=rb+t2.getMI(mem2);
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
		
		
		
	
	
	
	public static void buildRdfRepository(String pdfDir, String txtDir, String parsedDir, String rdfDir){
		
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
	
	
		
		

	
	
	
	public static Set <String> getRightList(String w, String gr, List<GR> mem){
		Set<String> s=new TreeSet<String>();
		Iterator<GR> iterator = mem.iterator();
		
		while (iterator.hasNext()) {
			GR g = (GR) iterator.next();
			if (g.isDepEqual(w) && g.isGrEqual(gr))
				s.add(g.getHead());
			
			
		}
		
		
		return s;
		
		
	}
	
//	
//	
	public static Set <String> getDepWordList(List <GR> mem, String gr){
		Set<String> s=new TreeSet<String>();
		Iterator<GR> iterator = mem.iterator();
		
		while (iterator.hasNext()) {
			GR g = (GR) iterator.next();
			if ( g.isGrEqual(gr))
				s.add(g.getDep());
			
			
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
	public static Set <String> getHeadWordList(List <GR> mem, String gr){
		Set<String> s=new TreeSet<String>();
		Iterator<GR> iterator = mem.iterator();
		
		while (iterator.hasNext()) {
			GR g = (GR) iterator.next();
			if ( g.isGrEqual(gr))
				s.add(g.getHead());
			
			
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
	
	public static void printGrList(List <GR> mem){
		for(GR g:mem)
			System.out.println(g.toString());
		
		
	}
	
	
	public static void printStrList(Set <String> mem){
		for(String g:mem)
			System.out.println(g);
		
		
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
