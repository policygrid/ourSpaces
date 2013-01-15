package common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;


import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.openrdf.repository.*;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.OpenRDFException;
import org.openrdf.rio.rdfxml.RDFXMLWriter;
import org.openrdf.rio.*;
import org.openrdf.model.Value;
import org.openrdf.query.*;
import org.openrdf.query.impl.TupleQueryResultBuilder;
import org.openrdf.query.resultio.sparqlxml.SPARQLResultsXMLWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.openrdf.OpenRDFException;
import org.policygrid.ontologies.FOAF;
import org.policygrid.ontologies.OPM;
import org.policygrid.ontologies.OurSpacesVRE;
import org.policygrid.ontologies.ProvenanceGeneric;
import org.xml.sax.SAXException;

import provenanceService.ProvenanceService;
import provenanceService.ProvenanceServiceImpl;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.ontology.*;



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
 public class ResourceAccess extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
 {
   static final long serialVersionUID = 1L;

   	AccessControl access = new AccessControl();
   	Project pro = new Project();
   	Connections con = new Connections();
   
	public ResourceAccess()
	{
		super();
	}  
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
        String cmd;
        // Retrieves the "action" parameter and forwards it to the relevant command.
        cmd = request.getParameter("action");
        if (cmd != null) 
        {
        	if (cmd.equals("delete")) 
            {
        		try {
					removeResource(request, response);
				} catch (SQLException e) {
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
				} catch (OpenRDFException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            } else 
        	if (cmd.equals("add")) 
            {
                try {
					addRestriction(request, response);
				} catch (RepositoryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedQueryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (QueryEvaluationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
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
				} catch (OpenRDFException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
		
        }
		
	}
	
	public static void addProvenance(String resourceID, String userID){
		try {
			ProvenanceServiceImpl impl = ProvenanceService.getSingleton();
			String id = impl.startSession();
			String process = impl.addNode(id, "http://www.policygrid.org/ourspacesVRE.owl#RemoveResource");		
			impl.addExistingResource(id, resourceID);
			impl.addExistingResource(id, userID);
			impl.addCausalRelationship(id, OPM.Used.getURI(), resourceID,  process);
			impl.commit(id);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	

	/**
	 * Removes the resource. The resource URI is transformed to the blank node and the provenance is stored.
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public static void removeResource(String resourceID, User user, boolean removeFile) throws ServletException, IOException, OpenRDFException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		RDFi rdf = new RDFi();
		String depositor = rdf.getResourceDepositor(resourceID);
		if(depositor.equals(user.getRDFID())) {		
			Vector resList = rdf.getResourcesPontingTo(resourceID);
			//Add the provenance first, before loosing the URI of the resource.
			addProvenance(resourceID, user.getRDFID());
		    FileUtils file = new FileUtils();
			if (resList.size() > 0) {
				//TODO: The resource need to be anonymised because there are other things linking to it
	
				try {
					rdf.deleteResource(resourceID, true);
				} catch (RepositoryException e) {
					common.Utility.log.debug(e);
					return;
				}
			} else {
				
				String url =rdf.getResourceURL(resourceID);
				//remove the file
				
				String fileID = url.substring(url.lastIndexOf("/")+1);
				
				if(removeFile){
					common.Utility.log.debug("Removing the file: "+url+" ID:"+ fileID);
					file.removeFile(fileID);
				}
				rdf.deleteResource(resourceID, false);
				
			}
		}
	}
	/**
	 * Removes the resource. The resource URI is transformed to the blank node and the provenance is stored.
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public void removeResource(HttpServletRequest request, HttpServletResponse response) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, ServletException, IOException, OpenRDFException, ParserConfigurationException, SAXException
	{
		String resourceID = request.getParameter("resourceID");	
		String sourcePage = request.getParameter("sourcePage");
		String id = request.getParameter("id");
		
		String type = request.getParameter("type");
		
		HttpSession session = request.getSession();
    	User user = (User) session.getAttribute("use");
		
    	removeResource(resourceID, user, true);
		forwardPage(request, response, sourcePage, id);
	}


	private void forwardPage(HttpServletRequest request,
			HttpServletResponse response, String sourcePage, String id)
			throws ServletException, IOException {
		String stringURL = "";
		if (id != null) {
	    stringURL = "/"+sourcePage+"?id="+id;	
		} else {
		stringURL = "/"+sourcePage;
		}
		RequestDispatcher rd = request.getRequestDispatcher(stringURL);
		rd.forward(request, response);
	}
	
	public void addRestriction(HttpServletRequest request, HttpServletResponse response) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, ServletException, IOException, OpenRDFException, ParserConfigurationException, SAXException
	{
		String resourceID = request.getParameter("resourceID");
		String type = request.getParameter("type");
		
		AccessControl ac = new AccessControl();
		ArrayList accessGroups = ac.getAccessGroups(resourceID);
		for(int i = 0; i < accessGroups.size(); i++)
		{
			String groupID = (String) accessGroups.get(i);
			AccessControl.deleteGroupPermission(groupID, resourceID);
		}
		
		if(type.equals("private"))
		{
			AccessControl.deletePublic(resourceID);
			Enumeration paramNames = request.getParameterNames();

			while(paramNames.hasMoreElements()) 
			{
	        	String param = (String)paramNames.nextElement();
	        	if(param.equals("Name")) 
	        	{

	        		String members[] = request.getParameterValues(param);
	        		for(int i = 0; i < members.length; i++)
	        		{

	        			String name = members[i];
	        			String[] fields = name.split("_");
	        			if(fields.length != 1)
	        			{
	        				String foafID = fields[1];
	        				AccessControl.setUserPermission("view", foafID, resourceID, true);
	        				AccessControl.setUserPermission("download", foafID, resourceID, true);
	        			}
	        		}
	        	}
	        	/*if(param.equals("project"))
	        	{
	        		String projects[] = request.getParameterValues(param);
	        		for(int i = 0; i < projects.length; i++)
	        		{
	        			String project = projects[i];
	        			String[] fields = project.split("_");
	        			if(fields.length != 1)
	        			{
		        			String projectID = fields[1];
		        			
		        			StringBuffer qry = new StringBuffer(1024);
		        			qry.append("select ?person where { ");
		        			qry.append("<"+projectID+"> <"+org.policygrid.ontologies.Project.hasMemberRole.toString()+"> ?r . ");
		        			qry.append("?r <"+RDF.type+"> ?role . "); 
		        			qry.append("?r <"+org.policygrid.ontologies.Project.roleOf.toString()+"> ?person } ");
		        			
		        			String query = qry.toString();
		        			
		        			con.repConnect();
		        			
		        			TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		        			TupleQueryResult result = output.evaluate();
		        			
		        			while (result.hasNext()) 
		        			{
		        					        				
		        				BindingSet bindingSet = result.next();
		        				Value value1 = bindingSet.getValue("person");	        				
		        				String personFoafID = value1.stringValue();
		        				AccessControl.setPermission("download", personFoafID, resourceID, true);
		        			}
		        			result.close();
		        			con.getRepConnection().close();
		        			
	        			}
	        		}
	        	} */
	        	if(param.equals("group"))
	        	{
	        		String groups[] = request.getParameterValues(param);
	        		for(int i = 0; i < groups.length; i++)
	        		{
	        			String group = groups[i];
	        			if(group != null)
	        			{
	        				AccessControl.setGroupPermission("view", group, resourceID, true);
	        				AccessControl.setGroupPermission("download", group, resourceID, true);
	        			}
	        		}
	        	}
			} 
			
		}
		else
		{
			AccessControl.setPublic(resourceID);
		}
	
		String stringURL = "/myhome.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(stringURL);
		rd.forward(request, response);
	}
	
}