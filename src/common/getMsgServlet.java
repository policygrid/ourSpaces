package common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.openrdf.OpenRDFException;
import org.policygrid.ontologies.ProvenanceGeneric;
import org.policygrid.ontologies.OurSpacesVRE;
import org.xml.sax.SAXException;


import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;


import java.util.*;
import java.text.*;

public class getMsgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
        // the action element that we'll check for
        

       
        	   try {
        		   	int messageID = Integer.parseInt(request.getParameter("id"));
        		   	common.Utility.log.debug("msgid"+messageID);
        		   	HttpSession session = request.getSession();
			    	User userSession = (User) session.getAttribute("use");
			    	
			    	
        		   
			    	Vector msgs =userSession.getMessage(messageID);
			    	common.Message msg= (common.Message) msgs.get(0);
			    	User sender=new User();
			    	
        		   JSONObject msgJson= new JSONObject();
        		   msgJson.put("id", messageID);
        		   msgJson.put("threadid", msg.getThreadID());
        		   msgJson.put("senderid", msg.getSender());
        		   msgJson.put("senderfoaf",sender.getFOAFID(msg.getSender()));
        		   msgJson.put("sender", sender.getName(msg.getSender()));
        		   msgJson.put("recids", msg.getRecipientIds());
        		   msgJson.put("recs", msg.getRecipients());
        		   msgJson.put("title", msg.getSubject());
        		   msgJson.put("sent", msg.getSent().toString());
        		   msgJson.put("content", msg.getContent());
        		  
        		   String myString = msgJson.toString();
        		// myString is {"JSON": "Hello, World"}
        		    
        		   response.setContentType("application/json");
        		
        		// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
        		   out.print(myString);
        		   out.flush();
        		    
        		    

        			
        			
        			
        		   
        		
					common.Utility.log.debug("Message retrieved");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
             
        	             
        	response.setContentType("text/xml");
     		response.setHeader("Cache-Control", "no-cache");
     	        	 
        	 out.flush();
        
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
        // the action element that we'll check for
       

      
	}
	
	

}
