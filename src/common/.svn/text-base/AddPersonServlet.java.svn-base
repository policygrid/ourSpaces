package common;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.openrdf.OpenRDFException;
import org.policygrid.ontologies.FOAF;
import org.policygrid.ontologies.OurSpacesVRE;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

/**
 * 
 * @author Ed
 * @version 1.0
 *
 */
 public class AddPersonServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
 {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
    Connections con = new Connections();
   
	public AddPersonServlet() 
	{
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
        // the action element that we'll check for
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		
		HttpSession session = request.getSession();
    	User userSession = (User) session.getAttribute("use");
	
		
		Model model = ModelFactory.createDefaultModel();
		RDFi rdf = new RDFi();
		
		String foafResourceID = "http://xmlns.com/foaf/0.1/#" + UUID.randomUUID().toString();
		
		
		
		Resource myResource  = model.createResource(foafResourceID);
		myResource.addProperty(RDF.type, FOAF.Person);       
        myResource.addProperty(FOAF.firstName,firstname);
        myResource.addProperty(FOAF.surname,lastname);
        myResource.addProperty(FOAF.mbox,email);
        myResource.addProperty(RDFS.label, firstname + " " + lastname + " : " + email);
        
        ByteArrayOutputStream outp = new ByteArrayOutputStream();
		
		model.write(outp);
		InputStream in = new ByteArrayInputStream(outp.toByteArray());
		try {
			rdf.write(in);
		} catch (OpenRDFException e) {
			e.printStackTrace();
		}
		
		try {
			String to = email;
	   	 	String esubject = userSession.getName(userSession.getID()) + " has provided some information about you in ourSpaces";
	        String emessage = "Dear " + firstname + ", \n\n " + userSession.getName(userSession.getID()) + " has provided some information about you in ourSpaces.\n\n ourSpaces is a Virtual Research Environment developed by the University of Aberdeen to aid researchers in managing their work digitally. To view the information provided about you, make changes or remove it, please visit www.ourspaces.net and register a profile with this email address.\n\n";
	   	 	Email.send(to, esubject, emessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		out.println("<message>");
	    out.println("<personID>"+foafResourceID+"</personID>");
	    out.println("<label>"+firstname + " " + lastname + " : " + email+"</label>");
	    out.println("</message>");


	
		out.flush();
       
	}
	
	

		 	  	  	    
}