package wn;

import java.io.File;
import java.io.FilenameFilter;

import svs.DateUtils;
import svs.ExecuteRASP;
import svs.Pdf2Text;
import svs.VocabularyAnalyser;


public class Builder {
	
	public Builder(){
		
	}
	
	
	public static void main(String[] args) throws NullPointerException{
		VocabularyAnalyser va=new VocabularyAnalyser();
		String root="/Users/kapila/Documents/repository/";
		buildRepository(root+"pdfs",root+"txts",root+"parsed");
		
	}
	
	
	
	
	
	public static void buildRepository(String pdfDir, String txtDir, String parsedDir){
		
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

}
