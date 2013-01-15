package org.policygrid.extraction;

import java.io.File;
import org.apache.lucene.document.Document;
import org.apache.pdfbox.*;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;


import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

public class ExtractPDF {
    
    PDFParser parser;
    String parsedText;
    PDFTextStripper pdfStripper;
    PDDocument pdDoc;
    COSDocument cosDoc;
    PDDocumentInformation pdDocInfo;
    
    // PDFTextParser Constructor 
    public ExtractPDF() {
    }
    
    // Extract text from PDF Document
    String pdftoText(String fileName) {
        
        common.Utility.log.debug("Parsing text from PDF file " + fileName + "....");
        File f = new File(fileName);
        
        if (!f.isFile()) {
            common.Utility.log.debug("File " + fileName + " does not exist.");
            return null;
        }
        
        try {
            parser = new PDFParser(new FileInputStream(f));
        } catch (Exception e) {
            common.Utility.log.debug("Unable to open PDF Parser.");
            return null;
        }
        
        try {
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc); 
        } catch (Exception e) {
            common.Utility.log.debug("An exception occured in parsing the PDF Document.");
            e.printStackTrace();
            try {
                   if (cosDoc != null) cosDoc.close();
                   if (pdDoc != null) pdDoc.close();
               } catch (Exception e1) {
               e.printStackTrace();
            }
            return null;
        }      
        common.Utility.log.debug("Done.");
        return parsedText;
    }
    
    // Write the parsed text from PDF to a file
    void writeTexttoFile(String pdfText, String fileName) {
    	
    	common.Utility.log.debug("\nWriting PDF text to output text file " + fileName + "....");
    	try {
    		PrintWriter pw = new PrintWriter(fileName);
    		pw.print(pdfText);
    		pw.close();    	
    	} catch (Exception e) {
    		common.Utility.log.debug("An exception occured in writing the pdf text to file.");
    		e.printStackTrace();
    	}
    	common.Utility.log.debug("Done.");
    }
    
    //Extracts text from a PDF Document and writes it to a text file
    public static void main(String args[]) {
    	
    	if (args.length != 2) {
        	common.Utility.log.debug("Usage: java PDFTextParser <InputPDFFilename> <OutputTextFile>");
        	System.exit(1);
        }
        
        ExtractPDF pdfTextParserObj = new ExtractPDF();
        String pdfToText = pdfTextParserObj.pdftoText(args[0]);
        
        if (pdfToText == null) {
        	common.Utility.log.debug("PDF to Text Conversion failed.");
        }
        else {
        	common.Utility.log.debug("\nThe text parsed from the PDF Document....\n" + pdfToText);
        	pdfTextParserObj.writeTexttoFile(pdfToText, args[1]);
        }
    }  
}