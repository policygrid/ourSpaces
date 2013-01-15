
package bak;
import fr.inria.rdfa.RDFaParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
public class rdfaTest {

	
	
public static void main(String [] args){
	
    try {
    	
    	
    	
//    	URL oracle = new URL("http://mrt.esc.abdn.ac.uk:8080/ourspaces/communications/test1001.html");
//    	  BufferedReader aReader = new BufferedReader(
//    	        new InputStreamReader(
//    	        oracle.openStream()));
    	String res="/Users/kapila/Documents/repository/test1001.html";
    	Reader aReader = new FileReader("/Users/kapila/Documents/repository/test1001.html"); // replace this by a reader on the source
        String aBase =res ;  // base or URL of the source
        RDFaParser aParser = new RDFaParser();
    	aParser.parse(aReader, aBase);
    	
    } catch (TransformerConfigurationException e) {
    	e.printStackTrace();
    } catch (FileNotFoundException e) {
    	e.printStackTrace();
    } catch (IOException e) {
    	e.printStackTrace();
    } catch (TransformerException e) {
    	e.printStackTrace();
    }
}
}
