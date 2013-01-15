package lexicon;

import java.awt.Rectangle;
import java.io.*;
import java.text.BreakIterator;
import java.util.List;
import java.util.Locale;

import org.apache.pdfbox.exceptions.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.PDFTextStripperByArea;



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

 public static void main(String[] args) throws Exception{
	 String fn1="/Users/kapila/Documents/repository/experiment5/pdfs/1-s2.0-S1366554512000658-main.pdf";
	 String fn2="/Users/kapila/Documents/repository/experiment5/pdfs/1-s2.0-S1366554512000658-main.txt";
	 Pdf2Text pdft=new Pdf2Text(fn1);
	 //pdft.saveToText(fn2);
	 pdft.saveToTextPreProssesd3( fn2 );
	 
	 

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
//         stripper.setParagraphStart("&*&"); 
//         stripper.setLineSeparator("#%#");
//         stripper.setPageSeparator("#%#");
//        
         String fulltxt=stripper.getText(pd) ;
//         String paras[]=fulltxt.split("&*&");
         
         
         File file = new File(filename);
        	try {
        	    BufferedWriter out = new BufferedWriter(new FileWriter(file));
        	   
//        	    int i=0;
//        	    while (i<paras.length) {
//        	       if (paras[i].length()>0){
//        	    	   String para=paras[i].replace("#%#", " ");
        	    	   
        	        out.write(fulltxt);
//        	    }
//        	        i++;
//        	    }
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
 public  void saveToTextPreProssesd3(String filename  ) throws Exception
 {
    PDDocument document = null;
         try
         {
             document = pd;
             if( document.isEncrypted() )
             {
                 try
                 {
                     document.decrypt( "" );
                 }
                 catch( InvalidPasswordException e )
                 {
                     System.err.println( "Error: Document is encrypted with a password." );
                     System.exit( 1 );
                 }
             }
             
             File file = new File(filename);
      
         	    BufferedWriter out = new BufferedWriter(new FileWriter(file));

              stripper = new PDFTextStripper();
           stripper.setParagraphStart("&*&"); 
           stripper.setLineSeparator("£@£");
           stripper.setPageSeparator("#%#");

      

             String fulltxt =stripper.getText(pd);
             String paras[]=fulltxt.split("&*&");
             
             int i=0;
             while (i<paras.length) {
      	       if (paras[i].length()>200){
      	    	   String para=paras[i];
      	    	   para=para.replace("-£@£", "");
      	    	   para=para.replace("£@£", " ");
      	    	   
      	    	 BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
      	    	 String source = para;
      	    	 iterator.setText(source);
      	    	int start = iterator.first();
      	    	String proccessedPara="";
      	    	for (int end = iterator.next();
      	    	    end != BreakIterator.DONE;
      	    	    start = end, end = iterator.next()) {
      	    		String line=source.substring(start,end).trim();
      	    		char firstchr = line.charAt(0);
      	    		char lastchr=line.charAt(line.length()-1);
      	    		if (Character.isUpperCase( firstchr)&& lastchr=='.' && line.length()>20){
      	    			proccessedPara=proccessedPara+line +" ";
      	    		}
      	    	}
      	    	if (proccessedPara.length()>0)   {
      	    	 // System.out.println (proccessedPara) ;
      	    	 	out.write(proccessedPara);
      	    	 
      	    	}
      	    }
      	        i++;
      	    }
            
             out.close();
         }
         finally
         {
             if( document != null )
             {
                 document.close();
             }
         }
     
 }
 public void saveToTextPreProssesd2(String filename){
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
        	       if (paras[i].length()>0){
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


