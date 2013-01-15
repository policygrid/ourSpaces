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
import org.openrdf.model.Value;
import org.openrdf.query.*;
import org.openrdf.query.resultio.sparqlxml.SPARQLResultsXMLWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openrdf.OpenRDFException;

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
 public class ObjectCheck extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
 {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
    Connections con = new Connections();
   
	public ObjectCheck()
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

        cmd = request.getParameter("objectName");
        
        if (cmd != null) 
        {
                try 
                {
					checkObject(request, response);
				} 
                catch (OpenRDFException e)
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
	 */
	private void checkObject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, OpenRDFException
	{
		PrintWriter out = response.getWriter();
		String objectName;
		String className;
		
		// Parameters
		objectName = request.getParameter("objectName").toString();

		
		// SPARQL query checking for the name of a particular object
		StringBuffer qry = new StringBuffer(1024);
		
		qry.append("PREFIX pg: <http://www.policygrid.org/utility.owl#> ");
		qry.append("SELECT ?name WHERE { ?id pg:Name ?name. filter(regex(?name, \""+objectName+"\", 'i')). ?id a pg:Organisation. } ");
		
	
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		ArrayList list = new ArrayList();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("name");
			   list.add(value.stringValue());
		}
		result.close();
		con.repDisconnect();
		
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		out.println("<info>");
		for(int i = 0; i < list.size(); i++){
			String name = (String) list.get(i);
			out.println("<name>" + name + "</name>");
		}
		out.println("</info>");
	}
}