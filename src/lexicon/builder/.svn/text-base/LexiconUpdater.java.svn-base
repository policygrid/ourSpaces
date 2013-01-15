package lexicon.builder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;



import org.openrdf.OpenRDFException;
import org.policygrid.ontologies.ProvenanceGeneric;
import org.xml.sax.SAXException;

import common.Configuration;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;


// TODO: Auto-generated Javadoc
/**
 * The Class LexiconUpdater.
 */
public class LexiconUpdater implements Runnable{

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		

		int userdocs=0;
		int projectdocs=0;
		int foafdocs=0;
		String path=Configuration.getParaemter("pls","scheduleFolder");
		//get all files (foafs)
		String[] foafarr=getFiles(path, txtfilter);
		System.out.println("all foafs :"+foafarr.length);		
		
		// iterate through available schedules based on foaf
		for (String foaf:foafarr){
			System.out.println(path+foaf);
			common.User usr=new common.User();
			
			int userid=0;
			File file = new File(path+foaf);
			JSONObject json=new JSONObject(); 
		    try {
		    	userid=usr.getUserIDFromFOAF("http://xmlns.com/foaf/0.1/#"+foaf);
		    	userdocs=buildPersonalCorpus("http://xmlns.com/foaf/0.1/#"+foaf);
		    	//read the schedule
		    	Scanner scanner = new Scanner(new FileReader(file));
		    	while (scanner.hasNext()){
		    		String line=scanner.nextLine();
		    		System.out.println("JSON: "+line);
					 json = (JSONObject) JSONSerializer.toJSON( line );   
					
		    		String status=json.getString("status");
		    		if (status.equals("ready")){
		    		System.out.println(status);
		    		JSONArray jsnprjs=json.getJSONArray("projects");
		    		String root=Configuration.getParaemter("pls","parsedFolder");
		    		Vector<String> fvector1 =new Vector<String>();
    		   		Vector<String> fvector2 =new Vector<String>();
		    		Vector<String> filevector1 =new Vector<String>();
    		   		Vector<String> filevector2 =new Vector<String>();
		    		if (!jsnprjs.isEmpty()){
		    			for (Object o:jsnprjs){
		    				String projectid=o.toString();
		    				
		    			   //user's folder
	       		   		   fvector1.add(root+foaf);
	        		   		

	        				//get project members
	        				common.Project prj=new common.Project();
	        				ArrayList prjmembers= prj.getProjectMembers("http://www.policygrid.org/project.owl#"+projectid);
	        				System.out.println("Project "+projectid+" has "+prjmembers.size());
	        				
	        				projectdocs=buildProjectCorpus(projectid);
	        		 		
	        		       	for (int i=0;i<prjmembers.size();i++){
	        		        	
	        		        	 common.MemberBean mmbr=(common.MemberBean ) prjmembers.get(i);
	        		        	 String foafid=mmbr.getFoafID().split("#")[1];
	        		        	
	        		        	
	        		        	 if (!foafid.equals(foaf)){
	        		        		 //each project member's folder (except the user)
	        		        		 fvector2.add(root+foafid);
	        		        	 }
	        		      	        		        	 
	        		       	}
	        		       	//extract all files from respective dirs
	        		   		filevector1=getFilesfromDIRlist(fvector1, txtfilter);
	        		   		filevector2=getFilesfromDIRlist(fvector2, txtfilter);
	        		       	System.out.println("File Vector 1: " +filevector1.size());
	        		       
	        		       	System.out.println("File Vector 2: "+filevector2.size());
	        		       
	        		       			
	        		        String outfile=Configuration.getParaemter("pls","lexsFolder")+foaf+"VS"+projectid;
	        		       	// call the Lexiconconstructor
	        		       	constructLexicon(filevector1,filevector2,outfile); 
	        		    	
	        		       	common.LoggerSearch ls= new common.LoggerSearch();
	         		       	ls.addLog("log_svsUpdate(userid,project_user_id,type,newdocuments)", userid+",'"+projectid+"','project',"+projectdocs);	

		    				
		    			}
		    			json.put("status", "constructed");
		    		}
		    		JSONArray jsnprsns=json.getJSONArray("persons");
		    		System.out.println("JSON ARRAY: "+jsnprsns.toString());
		    		if (!jsnprsns.isEmpty()){
		    			for (Object o:jsnprsns){
		    				
		    				System.out.println("PERSON:"+o.toString());
		    				
		    				/////
		    				//get the individual's foaf
		    				String personid=o.toString();
		    				
		    				foafdocs=buildPersonalCorpus("http://xmlns.com/foaf/0.1/#"+personid);
		    				//user's folder
	        		   		fvector1.add(root+foaf);
	        		   	   //individual's folder
	        		   		fvector2.add(root+personid);

	        		   	   //extract all files from respective dirs  	
	        		   		filevector1=getFilesfromDIRlist(fvector1, txtfilter);
	        		   		filevector2=getFilesfromDIRlist(fvector2, txtfilter);
	        		   		System.out.println("File Vector 1: " +filevector1.size());
		        		       
	        		       	System.out.println("File Vector 2: "+filevector2.size());
	        		       			
	        		        String outfile=Configuration.getParaemter("pls","lexsFolder")+foaf+"VS"+personid;
	        		       	
	        		       	// call the Lexiconconstructor
	        		       //	constructLexicon(filevector1,filevector2,outfile);
	        		    	common.LoggerSearch ls= new common.LoggerSearch();
	         		       	ls.addLog("log_svsUpdate(userid,project_user_id,type,newdocuments)", userid+",'"+personid+"','person',"+foafdocs);	

		    				
		    				/////
		    				
		    			}
		    			json.put("status", "constructed");
		    		}
		    		
		    	}
		    	
		    	}//end while
		    	scanner.close();
		    	try{
					BufferedWriter out = new BufferedWriter(new FileWriter(path+foaf));
					
					out.write(json.toString());
					out.flush();
					out.close();
		    	}
		    	catch (Exception ex){
		    		System.out.println(ex.getMessage());
		    	}
		    }catch(Exception ex){
		    	System.out.println(ex.getMessage());	
		    }
		}
		
		
		
		

	}
	
	/**
	 * Construct lexicon.
	 *
	 * @param filevector1 the filevector1
	 * @param filevector2 the filevector2
	 * @param outfile the outfile
	 */
	public static void constructLexicon(Vector<String> filevector1,	Vector<String> filevector2, String outfile){

			
			
			System.out.println("files of dir1 :"+filevector1.size());		
			
			
			Text2Mem t =new Text2Mem(filevector1,"");
			
			List <GR>corpus1=t.getModel();
			
			
			System.out.println("files of dir2 :"+filevector2.size());			
			t =new Text2Mem(filevector2,"");
			
	     	List <GR>corpus2=t.getModel();
	     	
			
	     	
			
			Set <String> corp1Nouns=getDepWordList(corpus1, "obj");
			corp1Nouns.addAll(getDepWordList(corpus1, "sub"));
			corp1Nouns.addAll(getHeadWordList(corpus1, "mod"));
			Set <String> corp2Nouns=getDepWordList(corpus2, "obj");
			corp2Nouns.addAll(getDepWordList(corpus2, "sub"));
			corp2Nouns.addAll(getHeadWordList(corpus2, "mod"));
					
			corp1Nouns.removeAll(swl);
			corp2Nouns.removeAll(swl);
					
			System.out.println("corpus1 Nouns:"+corp1Nouns.size());		
			System.out.println("corpus2 Nouns :"+corp2Nouns.size());	
					
					
			//System.out.println("corpus1 triples:"+corpus1.size());		
			//System.out.println("corpus2 triples :"+corpus2.size());	
			try{
				BufferedWriter out = new BufferedWriter(new FileWriter(outfile));
				 for (String w1:corp1Nouns){

						
						for(String w2:corp2Nouns){
					
							double ds=getDSNoun(w1, corpus1,w2,corpus2);
							
							
							if ((ds>3.0)){
								
								//System.out.println(w1+", "+w2+", "+ds[0]+", "+", "+ds[1]+", "+ds[2]);
								System.out.println(w1+", "+w2+", "+ds);
							 	out.write(w1+", "+w2+", "+ds+System.getProperty("line.separator"));  
						        out.flush();
							}
							}
						}
		
				  
				  
				  
				  
				  
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
		//
		LexiconUpdater lu=new LexiconUpdater();
		lu.run();

		
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
			//System.out.println("dir:"+dir);
			String[] parsedFiles =getFiles(dir,f);
			
			File fl = new File(dir);
		  	if (fl.exists()){
		  		//System.out.println("Directory exists");
			for (String s:parsedFiles)
				files.add(dir+"/"+s);
			}
			
		}
		
		
		
		
		return files;
	}
	

	
	
	/**
	 * Builds the project corpus.
	 *
	 * @param projectid the projectid
	 * @return the int
	 */
	public static int buildProjectCorpus(String projectid){
		int totdocs=0;
		try{
		
		//projectid="aadb9597-6392-42e0-9815-489cbe80cd19";//http://xmlns.com/foaf/0.1/#
		
		common.Project prj=new common.Project();
       	ArrayList prjmembers= prj.getProjectMembers("http://www.policygrid.org/project.owl#"+projectid);
       	System.out.println("Project "+projectid+" has "+prjmembers.size());
       	
		
		
       	for (int i=0;i<prjmembers.size();i++){
        	//  userid=Integer.parseInt( prjmembers.get(i).toString());
        	 common.MemberBean mmbr=(common.MemberBean ) prjmembers.get(i);
        	 String foaf=mmbr.getFoafID();
        	// System.out.println("User id "+foaf);	
        	int docs= buildPersonalCorpus(foaf);
        	totdocs+=docs;
       	
       	}
              
    } catch (Throwable t)
      {
        t.printStackTrace();
      }
		return totdocs; 
	}
	
	/**
	 * Builds the personal corpus.
	 *
	 * @param foaf the foaf
	 * @return the int
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the sQL exception
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws OpenRDFException the open rdf exception
	 * @throws SAXException the sAX exception
	 * @throws InterruptedException the interrupted exception
	 */
	public static int buildPersonalCorpus(String foaf) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ServletException, IOException, OpenRDFException, SAXException, InterruptedException{
		Vector<common.Resources> res=new Vector<common.Resources>();
		common.RDFi rdf=new common.RDFi();
		 common.User user=new common.User();
		 int totdocs=0;
    	 int usrid=user.getUserIDFromFOAF(foaf);
    	 if (usrid!=0 ){
    		
    		 res = rdf.getResources(ProvenanceGeneric.Paper.getURI(), user.getUserRDFID( usrid), true);
			 res.addAll( rdf.getResources(ProvenanceGeneric.Report.getURI(), user.getUserRDFID( usrid), true));
			 res.addAll(rdf.getResources(ProvenanceGeneric.Documentation.getURI(), user.getUserRDFID( usrid), true));
		  		System.out.println("Building Personal Corpus for user: "+user.getUserRDFID( usrid));
		  		String foafid=foaf.split("#")[1];
		 
		  		Vector<String> pdfs=new Vector<String>();
		  	
			
			
		  		for (int k =0;k<res.size();k++){
		  			//System.out.println(res.get(k).getID());
		  			String resname=res.get(k).getURL().replace("http://www.ourspaces.net/file/", "");
		  			pdfs.add(resname);
		  		}
		 
 		
		  		System.out.println("Documents found for "+user.getUserRDFID( usrid)+": "+pdfs.size());
	
		  		//String[] pdfFiles =getFiles(pdfdir,pdffilter);
		  		String datapath=common.Configuration.getParaemter("pls", "dataFolder"); 
				String txtpath= common.Configuration.getParaemter("pls", "textFolder");
				String parsedpath= common.Configuration.getParaemter("pls", "parsedFolder");
		  		File f = new File(txtpath+foafid+"/");
		  		if (!f.exists())
		  			f.mkdir();
		  	
		  		f = new File(parsedpath+foafid+"/");
		  		if (!f.exists())
		  			f.mkdir();
		  		Vector<String> txts=new Vector<String>();
		  		for (int j=0;j<pdfs.size();j++){
		  	
		  			//System.out.println("reading file "+pdfs.get(j));
		  			File file = new File(datapath+pdfs.get(j).toString());
		  			File txtfile = new File(txtpath+foafid+"/"+pdfs.get(j).toString());
		  			//System.out.println("file size: "+txtfile.length());
		  			
		  			txts.add(foafid+"/"+pdfs.get(j).toString());
		  			if (file.length()>0 && txtfile.length()==0){
		  				
		  				System.out.println("converting to text.... "+pdfs.get(j).toString());

		  				TextConvertor tc=new TextConvertor(datapath+pdfs.get(j).toString(), txtpath+foafid+"/"+pdfs.get(j).toString());
		  				
				
		  			}
		  		
			
			
		  		}
		  		System.out.println("Total converted to text:"+totdocs);
		  		System.out.println("Start parsing (using RASP) ......at: "+DateUtils.now()) ;
				//System.out.println("Total files found: "+txts.size()) ;

				for (int i=0; i< txts.size();i++){
					File file = new File(txtpath+txts.get(i));
					File parsedfile = new File(parsedpath+"/"+txts.get(i));
					if (file.length()>0 && parsedfile.length()==0){
						///home/kapila/rasp3os/scripts/rasp.sh -m < /Users/kapila/Documents/repository/textdocs/cullinanebricksandclicks.txt > /Users/kapila/Documents/repository/parsed/cullinanebricksandclicks.txt
			            Runtime rt = Runtime.getRuntime();
			           // System.out.println("Start processing .....: ("+i+") "+txtpath+txts.get(i)) ;
			           //Process proc = rt.exec("/home/kapila/rasp3os/scripts/test.sh "+txtDir+"/"+txtfiles[i]+"   "+parsedDir+"/"+txtfiles[i]);
			            Process proc = rt.exec("/home/policygrid/rasp3os/scripts/test.sh "+file+"   "+parsedfile);
			            InputStream stderr = proc.getErrorStream();
			            InputStreamReader isr = new InputStreamReader(stderr);
			            BufferedReader br = new BufferedReader(isr);
			            String line = null;
			            //common.Utility.log.debug("<ERROR>");
			            while ( (line = br.readLine()) != null)
			            	System.out.println(line);
			            //common.Utility.log.debug("</ERROR>");
			            int exitVal = proc.waitFor();
			            System.out.println("RASP exitValue: " + exitVal);
			            if (exitVal==0){
			            	totdocs++;
			            }
					}
				}
				System.out.println("Total files converted: "+totdocs) ;
				System.out.println("Finished parsing (using RASP) ......at: "+DateUtils.now()) ;
			 
		 
    	 
   		}//
    	 return totdocs;
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
	public static double getDSNoun(String w1,List<GR> mem1, String w2, List<GR> mem2){
		
		
		Set<String> objf1= getObjFeatureList(mem1, "obj_of", w1);
		Set<String> objcommon= objf1;
		Set<String> objf2= getObjFeatureList(mem2, "obj_of", w2);
		
		Set<String> subf1= getSubFeatureList(mem1, "sub_of", w1);
		Set<String> subjcommon= subf1;
		Set<String> subf2= getSubFeatureList(mem2, "sub_of", w2);
		
		
		Set<String> modf1= getModFeatureList(mem1, "mod", w1);
		Set<String> modcommon= modf1;
		Set<String> modf2= getModFeatureList(mem2, "mod", w2);

		
		objcommon.retainAll(objf2);
		subjcommon.retainAll(subf2);
		modcommon.retainAll(modf2);
		double ds=0.0;
//		double[]  dsarr=new double[3];
//		System.out.println(w1+"-"+"obj"+objf1.toString()+"<=>"+w2+"-"+"obj"+objf2.toString());
//		System.out.println(w1+"-"+"sub"+subf1.toString()+"<=>"+w2+"-"+"sub"+subf2.toString());
//		System.out.println(w1+"-"+"mod"+modf1.toString()+"<=>"+w2+"-"+"mod"+modf2.toString());
		//if ((objcommon.size()>0 && subjcommon.size()>0)&&((objf1.size()>1 && objf2.size()>1) || (subf1.size()>1 && subf2.size() >1)) ){
		if ((objcommon.size()>0 && subjcommon.size()>0)&&((objf1.size()>1 && objf2.size()>1) || (subf1.size()>1 && subf2.size() >1)) ){
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
			
			
			
			
			//ds=(topo+tops+topm)/devisor*100;
			double p=pt/pb;
			double r=rt/rb;
			//double dscocr=  (2*p*r)/(p+r);
			 ds=(0.25*topo+0.25*tops+0.5*topm)/devisor*100;

//			
//			dsarr[0]=ds;
//			dsarr[1]=dscocr*100;
//			dsarr[2]=dsweighted;
		}

		return ds;
		
	}
	
	/**
	 * Instantiates a new lexicon updater.
	 */
	public LexiconUpdater(){
		
		
		
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
	/**
 * Gets the dep word list.
 *
 * @param mem the mem
 * @param gr the gr
 * @return the dep word list
 */
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
	
	/**
	 * Gets the author list.
	 *
	 * @param mem the mem
	 * @param aut the aut
	 * @return the author list
	 */
	public static  List <GR> getAuthorList(List <GR> mem,String aut){
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
	/**
	 * Gets the head word list.
	 *
	 * @param mem the mem
	 * @param gr the gr
	 * @return the head word list
	 */
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
	
	/**
	 * Gets the obj feature list.
	 *
	 * @param mem the mem
	 * @param gr the gr
	 * @param w the w
	 * @return the obj feature list
	 */
	public static  Set <String> getObjFeatureList(List <GR> mem, String gr, String w){
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
	
	/**
	 * Gets the sub feature list.
	 *
	 * @param mem the mem
	 * @param gr the gr
	 * @param w the w
	 * @return the sub feature list
	 */
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
	
	/**
	 * Gets the mod feature list.
	 *
	 * @param mem the mem
	 * @param gr the gr
	 * @param w the w
	 * @return the mod feature list
	 */
	public static  Set <String> getModFeatureList(List <GR> mem, String gr, String w){
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
