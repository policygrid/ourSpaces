package common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


import java.io.*;
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
 public class Save extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
 {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
   
	public Save()
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
        String cmd;

        cmd = request.getParameter("userid");
        
        if (cmd != null) 
        {
                try 
                {
					saveSettings(request, response);
				} 
                catch (Exception e)
                {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            out.flush();
        }
        else 
        {
        	out.println("Gah!");
        }
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
	 */
	private void saveSettings(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, OpenRDFException, ParserConfigurationException, SAXException
	{
		PrintWriter out = response.getWriter();
		int userID = Integer.parseInt(request.getParameter("userid"));
		String changes = request.getParameter("string");
		XML xml = new XML();
		xml.updateLayout(userID, changes);
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		out.println("<xml>Saved</xml>");
	}
}