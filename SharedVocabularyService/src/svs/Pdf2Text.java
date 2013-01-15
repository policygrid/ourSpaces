package svs;

import java.io.*;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.util.*;

public class Pdf2Text {
	PDDocument pd;
	BufferedWriter wr;
	PDFTextStripper stripper;
	public Pdf2Text(String filename){
		 try {
		         File input = new File(filename);  // The PDF file from where you would like to extract
		         pd = PDDocument.load(input);	         
		 } catch (Exception e){
		         e.printStackTrace();
		 } 
	}

 public static void main(String[] args){
	 String fn1="/Users/kapila/Documents/repository/experiment1/pdfs/science8.pdf";
	 String fn2="/Users/kapila/Documents/repository/experiment1/txts/science8.txt";
	 Pdf2Text pdft=new Pdf2Text(fn1);
	 pdft.saveToTextPreProssesd(fn2);
	 

}
 
 public void saveToText(String filename){
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
 
 
 
 
 public void saveToTextPreProssesd(String filename){
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
 
 
}


