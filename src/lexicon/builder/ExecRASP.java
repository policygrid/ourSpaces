package lexicon.builder;


import java.io.BufferedReader;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;







public class ExecRASP extends Thread {
	String parsedpath;
	String textpath;
	Vector<String> txts=new Vector<String>();
	
	public void run() {
		try
        {            
//			String txtDir= "/home/policygrid/apache-tomcat-6.0.18/data/lexicons/temp/txt/";
//			String parsedDir= "/home/policygrid/apache-tomcat-6.0.18/data/lexicons/temp/parsed/";
			System.out.println("Start parsing (using RASP) ......at: "+DateUtils.now()) ;


			for (int i=0; i< txts.size();i++){
				File file = new File(textpath+txts.get(i));
				File parsedfile = new File(parsedpath+"/"+txts.get(i));
				if (file.length()>0 && parsedfile.length()==0){
					///home/kapila/rasp3os/scripts/rasp.sh -m < /Users/kapila/Documents/repository/textdocs/cullinanebricksandclicks.txt > /Users/kapila/Documents/repository/parsed/cullinanebricksandclicks.txt
		            Runtime rt = Runtime.getRuntime();
		            System.out.println("Start processing .....: "+textpath+txts.get(i)) ;
		            Process proc = rt.exec("/home/kapila/rasp3os/scripts/test.sh "+textpath+"/"+txts.get(i)+"   "+parsedpath+"/"+txts.get(i));
		            //Process proc = rt.exec("/home/policygrid/rasp3os/scripts/test.sh "+file+"   "+parsedfile);
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
				}
			}
			
			System.out.println("Finished parsing (using RASP) ......at: "+DateUtils.now()) ;
		 

            
        } catch (Throwable t)
          {
            t.printStackTrace();
          }
    }
	
	
	public ExecRASP(String txtPath,String parsedPath, Vector<String> txtfiles){
		textpath=txtPath;
		parsedpath=parsedPath;
		txts=txtfiles;
	}
    public static void main(String args[]) {
    	Vector<String> files =new Vector<String>();
    	String[] txtfiles=getFiles("/Users/kapila/Documents/repository/experiment4/computing/txts/selected/",txtfilter);
 		for (int i=0; i< txtfiles.length;i++){
 			files.add(txtfiles[i]);
 			 
 					
 		}
    	
 		ExecRASP ex=new ExecRASP("/Users/kapila/Documents/repository/experiment4/computing/txts/selected/","/Users/kapila/Documents/repository/experiment4/computing/parsed/part1/", files);
 		ex.run();
    }
    
    public static FilenameFilter txtfilter = new FilenameFilter() {
	    public boolean accept(File dir, String name) {
	        return name.endsWith(".txt");
	    }
	};
	public static String[] getFiles(String dirname,FilenameFilter filter ){
		
		File dir = new File(dirname);

		String[] files = dir.list(filter);
		return files;
	}	
}
