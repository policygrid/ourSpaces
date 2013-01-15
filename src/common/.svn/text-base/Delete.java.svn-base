package common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


import java.io.*;
import java.sql.SQLException;
import java.util.*;

import org.openrdf.repository.*;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.OpenRDFException;
import org.openrdf.rio.rdfxml.RDFXMLWriter;
import org.openrdf.rio.*;
import org.openrdf.query.*;
import org.openrdf.query.resultio.sparqlxml.SPARQLResultsXMLWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.openrdf.OpenRDFException;
import org.policygrid.ontologies.OurSpacesVRE;
import org.xml.sax.SAXException;

/**
 * Servlet implementation class for Servlet: Check
 *
 * This class is intended to accompany an AJAX script, designed to
 * check whether or not a user already exists in the RDF repository
 * based on the information they provide.
 * 
 * @author Richard Reid
 * @version 1.0
 */
 public class Delete extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
 {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
   
	public Delete()
	{
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();

        String user = request.getParameter("user");
        
        String userid = "http://xmlns.com/foaf/0.1/#" + user;
        
        String project = request.getParameter("project");  
        
        String projectID = "http://www.policygrid.org/project.owl#" + project;
        
        try {
			deleteProjectMember(userid, projectID);
		} catch (OpenRDFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		
		out.println("<message>");
		out.println("true");
	    out.println("</message>");

		out.flush();
	}  
	
	/**
	 * Retrieves the parameters and uses them in a SPARQL query to the RDF repository.
	 * The results of the SPARQL query are displayed in an XML page to be parsed 
	 * by the AJAX script.  
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private void deleteProjectMember(String foafID, String projectID) throws ServletException, IOException, OpenRDFException, ParserConfigurationException, SAXException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		StringBuffer qry = new StringBuffer(1024);
		Connections con = new Connections();
		RDFi rdf = new RDFi();
		Project project = new Project();
		
		String roleID = project.getRoleIDAboutUser(projectID, foafID);
		
		qry.append("construct { ");
		qry.append("<"+projectID+"> <"+org.policygrid.ontologies.Project.hasMemberRole.toString()+"> ?a } where { ");
		qry.append("<"+projectID+"> <"+org.policygrid.ontologies.Project.hasMemberRole.toString()+"> ?a. FILTER (SAMETERM(?a, <"+roleID+">)) } ");

		
		String query = qry.toString();
		
		
		con.repConnect();
		
		GraphQuery q = con.getRepConnection().prepareGraphQuery(QueryLanguage.SPARQL, query);
		GraphQueryResult result = q.evaluate();
		con.getRepConnection().remove(result);
		
		con.repDisconnect();
		
		AccessControl.deleteUserPermission(foafID, projectID);
	}
}
