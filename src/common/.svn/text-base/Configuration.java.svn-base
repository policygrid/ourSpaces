package common;
import java.io.File;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.w3c.dom.*;

public class Configuration {
	
private static String baseFolder;
private static boolean initialised = false;

private static Vector<OntologyBean> ontologies = null;


    public static synchronized void init(String base) {
    	baseFolder = base;
    	//servletContext.getRealPath("/") 
    	
    	ontologies = new Vector<OntologyBean>();
    	
    	try {
			  File file =  new File(baseFolder + "/WEB-INF/ourspaces.xml");
			  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			  DocumentBuilder db = dbf.newDocumentBuilder();
			  Document doc = db.parse(file);
			  doc.getDocumentElement().normalize();
			  common.Utility.log.debug("Root element " + doc.getDocumentElement().getNodeName());
			  NodeList nodeLst = doc.getElementsByTagName("ontologies");
			  common.Utility.log.debug("Information of all parameters");
      
			  for (int s = 0; s < nodeLst.getLength(); s++) {
				 			 
			    Node fstNode = (Node)nodeLst.item(s);
			    
			    if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
			  
			      NodeList fstNm = fstNode.getChildNodes();
			      for (int i = 0; i < fstNm.getLength(); i++) {	
			    	  
			      Node par = (Node)fstNm.item(i);
			      if (par.getNodeType() == Node.ELEMENT_NODE) {
			    	  
			      //common.Utility.log.debug("Parameter: " +par.getAttributes().getNamedItem("name")+" Value: " +  par.getFirstChild().getNodeValue());
			      
			      OntologyBean ont = new OntologyBean(par.getAttributes().getNamedItem("name").getNodeValue().toString(),par.getAttributes().getNamedItem("namespace").getNodeValue().toString(),par.getFirstChild().getNodeValue());
			      
			      ontologies.add(ont);
			  			      
			      //common.Utility.log.debug("Parameter: "  + par.getAttributes().getNamedItem("name").getTextContent());
			      //par.toString() common.Utility.log.debug("Parameter: Value: "  + par.getTextContent()); 
			      }
			      }
			    }

			  }
			  } catch (Exception e) {
			    e.printStackTrace();
			    
			  }
  	
    	
    	
    	

    	
    	initialised = true;
       	Configuration.class.notifyAll(); 	
    	common.Utility.log.debug("Ourspaces initialised");
    }
    
    
    public static synchronized Vector getOntologies(){
    	   	
    	while(!initialised) {
            try {
            	Configuration.class.wait();
            } catch (InterruptedException e) {}
        }

    	return ontologies;
    }
    
    
    public static synchronized String getParaemter(String category, String name) {
    	
    	while(!initialised) {
            try {
            	Configuration.class.wait();
            } catch (InterruptedException e) {}
        }
    	
    	try {
			  File file =  new File(baseFolder + "/WEB-INF/ourspaces.xml");
			  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			  DocumentBuilder db = dbf.newDocumentBuilder();
			  Document doc = db.parse(file);
			  doc.getDocumentElement().normalize();
			  //common.Utility.log.debug("Root element " + doc.getDocumentElement().getNodeName());
			  NodeList nodeLst = doc.getElementsByTagName(category);
			  //common.Utility.log.debug("Information of all parameters");
    
			  for (int s = 0; s < nodeLst.getLength(); s++) {
				 			 
			    Node fstNode = (Node)nodeLst.item(s);
			    
			    if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
			  
			      NodeList fstNm = fstNode.getChildNodes();
			      for (int i = 0; i < fstNm.getLength(); i++) {	
			    	  
			      Node par = (Node)fstNm.item(i);
			      if (par.getNodeType() == Node.ELEMENT_NODE) {
			    	  
			      //common.Utility.log.debug("Parameter: " +par.getAttributes().getNamedItem("name").getNodeValue()+" Value: " +  par.getFirstChild().getNodeValue());
			      
			      String parameterName = par.getAttributes().getNamedItem("name").getNodeValue().toString();
			      
			      if (parameterName.equals(name)) return par.getFirstChild().getNodeValue().toString();
			  			      
			      }
			      }
			    }		   

			  }
			  } catch (Exception e) {
			    e.printStackTrace();
			  }
    	
    	return null;
    }
    
}


