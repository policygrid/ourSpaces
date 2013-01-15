package experiment;

import java.io.*;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.util.*;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.model.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.*;;


public class TextConvertor {
	PDDocument pd;
	XWPFDocument dx;
	HWPFDocument dc;
	BufferedWriter wr;
	PDFTextStripper stripper;
	String filetype;
	
	
	/**
	 * Constructor sets the input and output file and convert the pdf, docx and doc files to text .
	 * @param infile,outfile
	 * @return
	 */
	public TextConvertor(String infile, String outfile){
		 try {
		         File input = new File(infile);  // The file from where you would like to extract
		         FileInputStream fis=new FileInputStream(input.getAbsolutePath());
		         int x=fis.read();
		         int y=fis.read();
		         fis=new FileInputStream(input.getAbsolutePath());
		         if (x==37 && y==80){
		        	 filetype="pdf";
		        	 pd = PDDocument.load(input);
		        	 PDF2Text( outfile);
		         }else if (x==80 && y==75){
		        	 filetype="docx";
		        	 
		        	  dx =new XWPFDocument(fis);
		        	  DOCX2Text( outfile);
		         }else if (x==208 && y==207){
		        	 filetype="doc";
		        	  dc=new HWPFDocument(fis);
		        	  DOC2Text( outfile);
		         }
		         	         
		 } catch (Exception e){
		         e.printStackTrace();
		 } 
	}


 /**
	 * save the converted text (without any processing) to the given file.
	 * @param filename
	 * @return
	 */
public void DOCX2Text(String filename){
	 try {
       File output = new File(filename); // The text file where you are going to store the extracted data
      
       XWPFWordExtractor ex =new XWPFWordExtractor(dx);
		
       String fileData = ex.getText();
       
       
      wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
      wr.write(fileData);
     
      
      wr.close();
	 } catch (Exception e){
      e.printStackTrace();
     } 
}
/**
 * save the converted text (without any processing) to the given file.
 * @param filename
 * @return
 */
public void DOC2Text(String filename){
 try {
   File output = new File(filename); // The text file where you are going to store the extracted data
   wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
   WordExtractor extractor = new WordExtractor(dc);
	String [] fileData = extractor.getParagraphText();
	for(int i=0;i<fileData.length;i++){
	if(fileData[i] != null)
		 wr.write(fileData[i]);
	}
   
  wr.close();
  
 } catch (Exception e){
  e.printStackTrace();
 } 
}
 /**
	 * save the converted text (without any processing) to the given file.
	 * @param filename
	 * @return
	 */
 public void PDF2Text(String filename){
	 try {
          File output = new File(filename); // The text file where you are going to store the extracted data
         
          stripper = new PDFTextStripper();
          
          
         wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
         
         stripper.writeText(pd, wr);
         
         if (pd != null) {
             pd.close();
         }
 } catch (Exception e){
         e.printStackTrace();
        } 
	 
	 
	 
	 
 }
 public void PDF2TextPreProssesd(String filename){
	 try {
               
         stripper = new PDFTextStripper();
         stripper.setParagraphStart("&*&"); 
         stripper.setLineSeparator("#%#");
         stripper.setPageSeparator("#%#");
         String fulltxt=stripper.getText(pd) ;
         String paras[]=fulltxt.split("&*&");
         
         
         File file = new File(filename);
        	try {
        	    BufferedWriter out = new BufferedWriter(new FileWriter(file));
        	   
        	    int i=0;
        	    while (i<paras.length) {
        	       if (paras[i].length()>200){
        	    	   String para=paras[i].replace("#%#", " ");
        	    	   
        	        out.write(para+"\r\n");
        	    }
        	        i++;
        	    }
        	    out.close();
        	     
        	} catch (IOException ex) {
        	    ex.printStackTrace();
        	}
         
         
         
         
         if (pd != null) {
             pd.close();
         }
 } catch (Exception e){
         e.printStackTrace();
        } 
	 
	 
	 
	 
 }
 
 public static void main(String[] args){
	 String fn1="/Users/kapila/Documents/repository/experiment3/test/swan_project.doc";
	 String fn2="/Users/kapila/Documents/repository/experiment3/test/swan_project.txt";
	 
	 TextConvertor tc =new TextConvertor(fn1,fn2);
	 
	 

//	 String txtDir="/Users/kapila/Documents/repository/experiment3/txts/author4/";
//	 String pdfDir="/Users/kapila/Documents/repository/experiment3/pdfs/author4/";
//	 String[] pdffiles=getFiles(pdfDir,pdffilter);
//		for (int i=0; i< pdffiles.length;i++){
//			 System.out.println("Converting file ..."+pdffiles[i]);
//			 TextConvertor pdft=new TextConvertor(pdfDir+pdffiles[i]);
//			 pdft.saveToTextPreProssesd(txtDir+pdffiles[i].replace(".pdf", ".txt"));
//					
//		}
	 
	
	 
	 
	 

}
 
 public static FilenameFilter pdffilter = new FilenameFilter() {
	    public boolean accept(File dir, String name) {
	        return name.endsWith(".pdf");
	    }
	};
	public static String[] getFiles(String dirname,FilenameFilter filter ){
		
		File dir = new File(dirname);

		String[] files = dir.list(filter);
		return files;
	}	
}


