package lexicon.builder;

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
import java.util.Vector;





// TODO: Auto-generated Javadoc
/**
 * The Class Analyser.
 */
public class Analyser implements Runnable{
	
	/** The s1 dirs. */
	private Vector <String>s1Dirs;
	
	/** The source1. */
	private String source1;
	
	/** The s2 dirs. */
	private Vector <String>s2Dirs;
	
	/** The source2. */
	private String source2;
	
	/** The file. */
	private String file;
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		BufferedWriter out;
		//get Nouns
		Analyser an=new Analyser(s1Dirs,source1,s2Dirs,source2,file);
		
		Vector<String> fvector=an.getFilesfromDIRlist(s1Dirs, txtfilter);
		System.out.println("files of dir1 :"+fvector.size());		
		
		
		Text2Mem t =new Text2Mem(fvector,"");
		
		List <GR>corpus1=t.getModel();
		
		fvector=an.getFilesfromDIRlist(s2Dirs,txtfilter);
		System.out.println("files of dir2 :"+fvector.size());			
		t =new Text2Mem(fvector,"");
		
     	List <GR>corpus2=t.getModel();
     	
		
     	System.out.println("corpus1 :"+corpus1.size());		
		System.out.println("corpus2  :"+corpus2.size());	
		
		
		Set <String> corp1Nouns=an.getDepWordList(corpus1, "obj");
		corp1Nouns.addAll(an.getDepWordList(corpus1, "sub"));
		corp1Nouns.addAll(an.getHeadWordList(corpus1, "mod"));
		Set <String> corp2Nouns=an.getDepWordList(corpus2, "obj");
		corp2Nouns.addAll(an.getDepWordList(corpus2, "sub"));
		corp2Nouns.addAll(an.getHeadWordList(corpus2, "mod"));
				
		corp1Nouns.removeAll(swl);
		corp2Nouns.removeAll(swl);
				
		System.out.println("corpus1 Nouns:"+corp1Nouns.size());		
		System.out.println("corpus2 Nouns :"+corp2Nouns.size());	
				
				
		System.out.println("corpus1 triples:"+corpus1.size());		
		System.out.println("corpus2 triples xx:"+corpus2.size());	
		try{
			System.out.println("in try catch");
			 out = new BufferedWriter(new FileWriter(file));
			 int count=0;
			 for (String w1:corp1Nouns){
				 System.out.println(w1);
				 	//if (w1.equals("heuristic")){
					
					for(String w2:corp2Nouns){
						
						//if (w2.equals("study")){
						double[] ds=getDSNoun(w1, corpus1,w2,corpus2);
						System.out.println(w1+"==="+w2);
//						if (w1.equals(w2))
//							count++;
						
						if ((ds[2]>2.5)){
							
							System.out.println(w1+", "+w2+", "+ds[0]+", "+", "+ds[1]+", "+ds[2]);
						 	out.write(w1+", "+w2+", "+ds[0]+", "+ds[1]+", "+ds[2]+System.getProperty("line.separator"));  
					        out.flush();
						}
						//}
					//	}
				 	}
					}
	
			  System.out.println("Total :"+count);
			  
			  
			  
			  
			 out.close();
			  
	
		}catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
	}
	
	/** The stopwords. */
	private static String [] stopwords={"i","a","all","and","it","p","that","the","but","they","each","these","between","itself","both","or","this","so","to","vmt","was","well","much","which","what","when","where","who","will","with","those","the","www"};
	
	/** The swl. */
	private static List <String>swl= Arrays.asList(stopwords);
	
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws NullPointerException the null pointer exception
	 */
	public static void main(String[] args) throws NullPointerException{
			
		Vector<String> s1Dirs =new Vector<String>();
		Vector<String> s2Dirs =new Vector<String>();
		String source1="/Users/kapila/Documents/repository/experiment4/computing/parsed/part2/";
		String source2="/Users/kapila/Documents/repository/experiment4/environment/parsed/part1/";
		s1Dirs.add(source1);
		s2Dirs.add(source2);
		
//		String[] s1 = getFiles(source1, txtfilter);
//		String[] s2 = getFiles(source2, txtfilter);
//		
//		for (String f:s1){
//			s1Dirs.add(f);
//		}
//		for (String f:s2){
//			s2Dirs.add(f);
//		}
//		
//		System.out.println("Count1 "+s1Dirs.size());
//		System.out.println("Count2 "+s2Dirs.size());
		
		String outfile="/Users/kapila/Documents/repository/experiment4/results/compVSenv.csv";
		
		Analyser an=new Analyser(s1Dirs,source1,s2Dirs,source2, outfile);
		
		

		
		
		an.run();;

		
	}
	
	//returns the vector of files (full path)  from vector of dirs
	/**
	 * Gets the filesfrom di rlist.
	 *
	 * @param pathvector the pathvector
	 * @param f the f
	 * @return the filesfrom di rlist
	 */
	public Vector<String > getFilesfromDIRlist(Vector<String > pathvector,FilenameFilter f ){
		Vector<String > files=new Vector<String >();
		
		for (String dir:pathvector){
			System.out.println("dir:"+dir);
			String[] parsedFiles =getFiles(dir,f);
			
			File fl = new File(dir);
		  	if (fl.exists()){
		  		System.out.println("Directory exists");
			for (String s:parsedFiles)
				files.add(dir+"/"+s);
			}
			
		}
		
		
		
		
		return files;
	}
	
	
	
	
	/**
 * Gets the dS noun.
 *
 * @param w1 the w1
 * @param mem1 the mem1
 * @param w2 the w2
 * @param mem2 the mem2
 * @return the dS noun
 */
public  double[] getDSNoun(String w1,List<GR> mem1, String w2, List<GR> mem2){
		
		
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
		if ((objcommon.size()>1 && subjcommon.size()>1)&&((objf1.size()>2 && objf2.size()>2) && (subf1.size()>2 && subf2.size() >2) ) ){
			
		//if ((objcommon.size()>0 && subjcommon.size()>0)&&((objf1.size()>2 && objf2.size()>2) && (subf1.size()>2 && subf2.size() >2)) ){
//			System.out.println(w1+"-"+"obj"+objf1.toString()+"<=>"+w2+"-"+"obj"+objf2.toString());
//			System.out.println(w1+"-"+"sub"+subf1.toString()+"<=>"+w2+"-"+"sub"+subf2.toString());
//			System.out.println(w1+"-"+"mod"+modf1.toString()+"<=>"+w2+"-"+"mod"+modf2.toString());
//			
//			System.out.println(objf2.toString());
//			System.out.println(subf2.toString());
//			System.out.println(modf2.toString());

			
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
//			if (dsweighted>3.0){
//				System.out.println(w1+"-"+"obj"+objf1.toString());
//				System.out.println(w2+"-"+"obj"+objf2.toString());
//				System.out.println(w1+"-"+"sub"+subf1.toString());
//				System.out.println(w2+"-"+"sub"+subf2.toString());
//				System.out.println(w1+"-"+"mod"+modf1.toString());
//				System.out.println(w2+"-"+"mod"+modf2.toString());
//			}
			
			dsarr[0]=ds;
			dsarr[1]=dscocr*100;
			dsarr[2]=dsweighted;
		}

		return dsarr;
		
	}
	
	/**
	 * Instantiates a new analyser.
	 *
	 * @param Dirs1 the dirs1
	 * @param s1 the s1
	 * @param Dirs2 the dirs2
	 * @param s2 the s2
	 * @param f the f
	 */
	public Analyser(Vector <String>Dirs1,String s1, Vector <String>Dirs2,String s2, String f){
		s1Dirs=Dirs1;
		source1=s1;
		s2Dirs=Dirs2;
		source2=s2;
		file=f;
		
		
	}

	
	
		
		
		
	
	
	  
	  
	  
	/**
	 * Gets the files.
	 *
	 * @param dirname the dirname
	 * @param filter the filter
	 * @return the files
	 */
	public static String[] getFiles(String dirname,FilenameFilter filter ){
		
		File dir = new File(dirname);

		String[] files = dir.list(filter);
		return files;
	}
	
	/** The pdffilter. */
	public static FilenameFilter pdffilter = new FilenameFilter() {
	    public boolean accept(File dir, String name) {
	        return name.endsWith(".pdf");
	    }
	};
	
	/** The txtfilter. */
	public static FilenameFilter txtfilter = new FilenameFilter() {
	    public boolean accept(File dir, String name) {
	        return name.endsWith("");
	    }
	};
	
	/** The txtfilter2. */
	public static FilenameFilter txtfilter2 = new FilenameFilter() {
	    public boolean accept(File dir, String name) {
	        return name.endsWith(".txt");
	    }
	};
	
	/** The rdffilter. */
	public static FilenameFilter rdffilter = new FilenameFilter() {
	    public boolean accept(File dir, String name) {
	        return name.endsWith(".rdf");
	    }
	};
	
	
		
		

	
	
	
	/**
	 * Gets the right list.
	 *
	 * @param w the w
	 * @param gr the gr
	 * @param mem the mem
	 * @return the right list
	 */
	public static Set <String> getRightList(String w, String gr, List<GR> mem){
		Set<String> s=new TreeSet<String>();
	
		
		for (GR g:mem){
			if (g.isDepEqual(w) && g.isGrEqual(gr))
				s.add(g.getHead());
			
			
		}
		
		
		return s;
		
		
	}
	
//	
//	
	/**
 * Gets the dep word list.
 *
 * @param mem the mem
 * @param gr the gr
 * @return the dep word list
 */
public  Set <String> getDepWordList(List <GR> mem, String gr){
		Set<String> s=new TreeSet<String>();
		
		
		for (GR g:mem){
			if ( g.isGrEqual(gr))
				s.add(g.getDep());
			
			
		}
		
		
		return s;
		
		
	}
	
//	/**
//	 * Gets the author list.
//	 *
//	 * @param mem the mem
//	 * @param aut the aut
//	 * @return the author list
//	 */
//	public  List <GR> getAuthorList(List <GR> mem,String aut){
//		List <GR> m=new ArrayList<GR>();
//		Iterator <GR> it1=mem.iterator();
//		while (it1.hasNext()){
//		
//			GR g=(GR)it1.next();
//			if (g.isAuthor(aut)){
//				m.add(g);
//			}
//		}
//		
//		return m;
//	}
	//getRightWordList
	/**
	 * Gets the head word list.
	 *
	 * @param mem the mem
	 * @param gr the gr
	 * @return the head word list
	 */
	public  Set <String> getHeadWordList(List <GR> mem, String gr){
		Set<String> s=new TreeSet<String>();
		
		
		for (GR g:mem){
			if ( g.isGrEqual(gr))
				s.add(g.getHead());
			
			
		}
		
		
		return s;
		
		
	}
	
	/**
	 * Gets the obj feature list.
	 *
	 * @param mem the mem
	 * @param gr the gr
	 * @param w the w
	 * @return the obj feature list
	 */
	public  Set <String> getObjFeatureList(List <GR> mem, String gr, String w){
		Set<String> s=new TreeSet<String>();
		String obj="";
		for (GR g:mem){
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
	
	/**
	 * Gets the sub feature list.
	 *
	 * @param mem the mem
	 * @param gr the gr
	 * @param w the w
	 * @return the sub feature list
	 */
	public  Set <String> getSubFeatureList(List <GR> mem, String gr, String w){
		Set<String> s=new TreeSet<String>();
		String obj="";
		for (GR g:mem){
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
	
	/**
	 * Gets the mod feature list.
	 *
	 * @param mem the mem
	 * @param gr the gr
	 * @param w the w
	 * @return the mod feature list
	 */
	public  Set <String> getModFeatureList(List <GR> mem, String gr, String w){
		Set<String> s=new TreeSet<String>();
		
		String obj="";
		
		for (GR g:mem){
			
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
	
	
	
	
	/**
	 * Gets the total.
	 *
	 * @param mem the mem
	 * @return the total
	 */
	public  int getTotal(List <GR> mem){
		return mem.size();
	}
	
	/**
	 * Prints the gr list.
	 *
	 * @param mem the mem
	 */
	public  void printGrList(List <GR> mem){
		for(GR g:mem)
			System.out.println(g.toString());
		
		
	}
	
	
	/**
	 * Prints the str list.
	 *
	 * @param mem the mem
	 */
	public  void printStrList(Set <String> mem){
		for(String g:mem)
			System.out.println(g);
		
		
	}
	
	/**
	 * Checks if is stop word.
	 *
	 * @param w the w
	 * @return true, if is stop word
	 */
	public  boolean isStopWord(String w){
		
		boolean res=false;
		for(String s:swl){
			
			if (w.equals(s)){
				res= true;
				break;
			}
			
		}
		return res;
	}
}
