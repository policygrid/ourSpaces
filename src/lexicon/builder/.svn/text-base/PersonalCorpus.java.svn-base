/*
 * 
 */
package lexicon.builder;


import java.io.BufferedReader;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import org.policygrid.ontologies.ProvenanceGeneric;



// TODO: Auto-generated Javadoc
/**
 * The Class PersonalCorpus.
 * This buids the personal corpus of a user
 */
public class PersonalCorpus extends Thread {
	
	/** The foaf. */
	String 		foaf;

	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try
        {            

			Vector<common.Resources> res=new Vector<common.Resources>();
			
			common.RDFi rdf=new common.RDFi();
			//get paths from configurations
	       	String datapath=common.Configuration.getParaemter("pls", "dataFolder"); 
			String txtpath= common.Configuration.getParaemter("pls", "textFolder");
			String parsedpath= common.Configuration.getParaemter("pls", "parsedFolder");
			int totdocs=0;
			Vector<String> txts=new Vector<String>();
			System.out.println("User id "+foaf);	
	        common.User user=new common.User();
	        int usrid=user.getUserIDFromFOAF(foaf);
	        //check valid user and consent for using documents
	        if (usrid!=0 && user.getPLSconsentStatus(usrid)==0){
	        		//get papers reports and documents
	        		res = rdf.getResources(ProvenanceGeneric.Paper.getURI(), user.getUserRDFID( usrid), true);
					res.addAll( rdf.getResources(ProvenanceGeneric.Report.getURI(), user.getUserRDFID( usrid), true));
					res.addAll(rdf.getResources(ProvenanceGeneric.Documentation.getURI(), user.getUserRDFID( usrid), true));
	   		  		System.out.println("RDF USER ID"+user.getUserRDFID( usrid));
	   		  		String foafid=foaf.split("#")[1];
	   		 
	   		  		Vector<String> docs=new Vector<String>();
	   		  	
	   			
	   			
	   		  		for (int k =0;k<res.size();k++){
	   		  			System.out.println(res.get(k).getID());
	   		  			String resname=res.get(k).getURL().replace("http://www.ourspaces.net/file/", "");
	   		  			docs.add(resname);
	   		  		}
	   		 
	     		
	   		  		System.out.println("documents found"+docs.size());
	   	
	   		  		//String[] pdfFiles =getFiles(pdfdir,pdffilter);
	   		  	
	   		  		File f = new File(txtpath+foafid+"/");
	   		  		if (!f.exists())
	   		  			f.mkdir();
	   		  	
	   		  		f = new File(parsedpath+foafid+"/");
	   		  		if (!f.exists())
	   		  			f.mkdir();
	   		  		//convert to text
	   		  		for (int j=0;j<docs.size();j++){
	   		  	
	   		  			System.out.println("reading file "+docs.get(j));
	   		  			File file = new File(datapath+docs.get(j).toString());
	   		  			File txtfile = new File(txtpath+foafid+"/"+docs.get(j).toString());
	   		  			System.out.println("file size: "+txtfile.length());
	   		  			txts.add(foafid+"/"+docs.get(j).toString());
	   		  			if (file.length()>0 && txtfile.length()==0){
	   		  				
	   		  				System.out.println("converting to text.... "+docs.get(j).toString());

	   		  				TextConvertor tc=new TextConvertor(datapath+docs.get(j).toString(), txtpath+foafid+"/"+docs.get(j).toString());
	   		  				totdocs++;
	   				
	   		  			}
	   		  		
	   			
	   			
	   		  		}
	        	 
	   		  	
	   		 
	        	 
		   }
	       	
	       	
	    	System.out.println("Total converted :"+totdocs);
				
			
			System.out.println("Start parsing (using RASP) ......at: "+DateUtils.now()) ;
			System.out.println("Total files: "+txts.size()) ;
			//parsing text documents using RASP....
			for (int i=0; i< txts.size();i++){
				File file = new File(txtpath+txts.get(i));
				File parsedfile = new File(parsedpath+"/"+txts.get(i));
				if (file.length()>0 && parsedfile.length()==0){
					Runtime rt = Runtime.getRuntime();
		            System.out.println("Start processing .....: ("+i+") "+txtpath+txts.get(i)) ;
		           //Process proc = rt.exec("/home/kapila/rasp3os/scripts/test.sh "+txtDir+"/"+txtfiles[i]+"   "+parsedDir+"/"+txtfiles[i]);
		            Process proc = rt.exec("/home/policygrid/rasp3os/scripts/test.sh "+file+"   "+parsedfile);
		            InputStream stderr = proc.getErrorStream();
		            InputStreamReader isr = new InputStreamReader(stderr);
		            BufferedReader br = new BufferedReader(isr);
		            String line = null;
		            
		            while ( (line = br.readLine()) != null)
		            	System.out.println(line);
		           
		            int exitVal = proc.waitFor();
		            System.out.println("RASP exitValue: " + exitVal);
				}
			}
			
			System.out.println("Finished parsing (using RASP) ......at: "+DateUtils.now()) ;
		 

            
        } catch (Throwable t)
          {
            t.printStackTrace();
          }
    }
	
	
	/**
	 * Instantiates a new personal corpus with foaf id of the user.
	 *
	 * @param foafid the foafid
	 */
	public PersonalCorpus(String foafid){
		foaf=foafid;

	}
    
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String args[]) {
       
    }
    
    /** The txtfilter. */
    public static FilenameFilter txtfilter = new FilenameFilter() {
	    @Override
		public boolean accept(File dir, String name) {
	        return name.endsWith("");
	    }
	};
	
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
}
