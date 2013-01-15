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
import org.openrdf.query.impl.TupleQueryResultBuilder;
import org.openrdf.query.resultio.sparqlxml.SPARQLResultsXMLWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openrdf.OpenRDFException;
import org.policygrid.ontologies.FOAF;


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
 public class Check extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
 {
   static final long serialVersionUID = 1L;
   
	
	Connections con = new Connections();
   
	public Check()
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

        cmd = request.getParameter("name");
        
        if (cmd != null) 
        {
                try 
                {
					checkUser(request, response);
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
        	out.println("Frick!");
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
	private void checkUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, OpenRDFException
	{
		PrintWriter out = response.getWriter();
		String name;
		
		
		
		// Parameters
		name = request.getParameter("name").toString();
		
		name = name + " ";
		
		String[] fields = name.split(" ");

		common.Utility.log.debug("Fields 0: " +fields[0]);
		common.Utility.log.debug("Fields 1: " +fields[1]);
		
		// SPARQL query checking for the name and email of a particular user
		StringBuffer qry = new StringBuffer(1024);
		
	
		/*
		qry.append("prefix pg: <http://www.policygrid.org/utility.owl#>  ");
		qry.append("prefix res: <http://www.policygrid.org/provenance-generic.owl#>  ");
		qry.append("Select ?x ?name ?email ?org ?project ?title ");
		qry.append("Where {  ");
		qry.append("?x a pg:Person.  ");
		qry.append("?x pg:Name ?name.  ");
		qry.append("filter (regex(?name, \""+name+"\", \"i\")) ");
		qry.append("OPTIONAL {{?x pg:Email ?email.} UNION {?x pg:Email ?y. ?y ?z ?email}}  ");
		qry.append("OPTIONAL {{?x pg:EmployeeOf ?organisation.}  ");
		qry.append("?organisation pg:Name ?org.}  ");
		qry.append("OPTIONAL {{?x pg:AuthorOf ?resource.} UNION {?resource res:HasAuthor ?x.}  ");
		qry.append("?resource res:Title ?title.} ");
		qry.append("OPTIONAL {{?x pg:MemberOf ?proj} UNION {?proj pg:HasMember ?x}. ?proj pg:Name ?project } }  ");
		*/
		
		qry.append("prefix foaf: <http://xmlns.com/foaf/0.1/>  ");
		qry.append("Select ?x ?firstname ?surname ?email ");
		qry.append("Where {  ");
		qry.append("?x a foaf:Person.  ");
		qry.append("?x foaf:firstName ?firstname.  ");
		qry.append("?x foaf:surname ?surname.  ");
		qry.append("?x foaf:mbox ?email.  ");
		qry.append("filter (regex(?firstname, \""+fields[0]+"\", \"i\")) ");
		qry.append("filter (regex(?surname, \""+fields[1]+"\", \"i\")) } ");
	
		String query = qry.toString();
		
		common.Utility.log.debug(qry);
		
		con.repConnect();
		
		RDFi rdf = new RDFi();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		ArrayList list = new ArrayList();
		
		while (result.hasNext()) 
		{
			Person person = new Person();
			BindingSet bindingSet = result.next();
			Value id = bindingSet.getValue("x");
			Value firstname = bindingSet.getValue("firstname");
			Value surname = bindingSet.getValue("surname");
			Value email = bindingSet.getValue("email");
			Value org = bindingSet.getValue("org");
			Value project = bindingSet.getValue("project");
			Value resource = bindingSet.getValue("title");
			
			person.setID(rdf.getOurSpacesUserIdFromFOAF(id.stringValue()));
			person.setName(firstname.stringValue() + " " + surname.stringValue());
			person.setEmail(email.stringValue());
			
			common.Utility.log.debug("FOAF ID from Check: " + id.stringValue());
			common.Utility.log.debug("OurSpaces User from Check" + rdf.getOurSpacesUserIdFromFOAF(id.stringValue()));
			
			String personID = rdf.getOurSpacesUserIdFromFOAF(id.stringValue());
			
			boolean repeat = false;
			
			for(int i = 0; i < list.size(); i++)
			{
				Person pp = (Person) list.get(i);
				String tempID = person.getID();
				if(tempID == personID)
					repeat = true;
			}
			
			if(repeat != true)
				list.add(person);
			repeat = false;
		}
		result.close();
		con.repDisconnect();
		
		
		
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		out.println("<root>");
			for(int i = 0; i < list.size(); i++)
			{
				out.println("<user>");
				Person p = (Person) list.get(i);
				String info = p.getName() + "LINEBREAK";
				if(p.getEmail().length() > 0)
					info = info + p.getEmail() + "LINEBREAK";
				String id = p.getID();
					out.println("<info>");
						out.println(info);
					out.println("</info>");
					out.println("<id>");
						out.println(id);
					out.println("</id>");
				out.println("</user>");
			}
		out.println("</root>");

	}
}