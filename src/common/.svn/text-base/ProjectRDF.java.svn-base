package common;


import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.*;
import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.openrdf.repository.*;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.OpenRDFException;
import org.openrdf.rio.rdfxml.RDFXMLWriter;
import org.openrdf.rio.*;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.query.*;
import org.openrdf.query.resultio.sparqlxml.SPARQLResultsXMLWriter;
import org.xml.sax.SAXException;


import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.ontology.*;

/**
 * Handles all RDF transactions for Project pages.  Information is 
 * retrieved from the Sesame RDF Repository and returned to the Project
 * Model Class.  
 * 
 * @author Richard Reid
 * @version 1.2
 */

public class ProjectRDF
{
	
	
	public OntModel ontology;
	Connections con = new Connections();
	
	public ProjectRDF()
	{
		
	}
	
	
	public void OntologyReader() throws IOException, SAXException 
	{
		String path2 = con.getUtilityOntology();
	    FileInputStream in = new FileInputStream(path2);
	    ontology = ModelFactory.createOntologyModel();
	    ontology = (OntModel) ontology.read(in, "http://www.policygrid.org/utility.owl");
	    in.close();
	}


	public OntProperty getProperty(String name)
		{
			for (Iterator it = ontology.listOntProperties(); it.hasNext(); )
			{
				OntProperty p = (OntProperty) it.next();
				if (p.getLocalName().equalsIgnoreCase(name))
					return p;
			}
			return null;
		}
	
	public OntClass getClass (String name){
		for(Iterator it = ontology.listNamedClasses(); it.hasNext();){
			OntClass c =(OntClass) it.next();
			if(c.getLocalName().equalsIgnoreCase(name))
				return c;
		}
		return null;
	}
	
	public String getObjectID(String property, String objectName, String className) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("PREFIX pg: <http://www.policygrid.org/utility.owl#> ");
		qry.append("SELECT ?id WHERE { ?id pg:"+property+" ?name. filter(regex(?name, \"");
		qry.append(objectName);
		qry.append("\", 'i')). ?id a pg:"+className+". } ");
		
		String resource = "";
	
		String query = qry.toString();

		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("id");
			   resource = value.stringValue();
		}
		result.close();
		con.repDisconnect();

		return resource;
	}
	
	public int checkPerson(String name)
	{
		int i = 0;
		char a = name.charAt(0);
		char b = name.charAt(3);
		
		if(a == 'h' && b == 'p')
			i = 1;
		return i;
	}
	
	
	
	public void deleteProject(String id) throws ServletException, IOException, OpenRDFException
	{
		String query1 = "CONSTRUCT {<" + id + "> ?y ?z.} WHERE {<" + id + "> ?y ?z.}";
		String query2 = "CONSTRUCT {?y ?z <" + id + ">.} WHERE {?y ?z <" + id + ">.}";
		
		con.repConnect();

			GraphQuery queryA = con.getRepConnection().prepareGraphQuery(QueryLanguage.SPARQL, query1);
			GraphQueryResult result1 = queryA.evaluate();
			con.getRepConnection().remove(result1);

			GraphQuery queryB = con.getRepConnection().prepareGraphQuery(QueryLanguage.SPARQL, query2);
			GraphQueryResult result2 = queryB.evaluate();
			con.getRepConnection().remove(result2);
	}
	
	
	
	/**
	 * Returns an ArrayList of all resources related to a particular project.  The 
	 * ArrayList contains multiple RDF resourceIDs pointing to the location of a 
	 * resource in the repository.
	 * 
	 * @param resourceID
	 * @return ArrayList of resourceIDs
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public ArrayList getResources(String resourceID) throws ServletException, IOException, OpenRDFException 
	{
		//Forms the SPARQL query to retrieve the results of the appropriate resources.
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?uri WHERE { ");
		qry.append("?uri <http://www.policygrid.org/provenance-generic.owl#producedInProject> <");
		qry.append(resourceID);
		qry.append("> } ");

		//common.Utility.log.debug(qry);
		
		ArrayList resources = new ArrayList();
	
		String query = qry.toString();
		
		// Connects to the repository
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		// Any relevant results are added to the resources ArrayList
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   // retrieves the resourceID of the resource
			   Value value = bindingSet.getValue("uri");
			   String url = value.stringValue();
			   resources.add(url);
		}
		result.close();
		con.getRepConnection().close();
		return resources;
	}
	
	/**
	 * Retrieves the necessary information that describes each resource including the Title,
	 * URL and the authors.  
	 * 
	 * A Resource JavaBean is created to store the information about each resource and is 
	 * returned in a Vector.
	 * 
	 * @param resources
	 * @return Vector of Resource JavaBeans
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public Vector getAllResources(ArrayList resources) throws ServletException, IOException, OpenRDFException 
	{
		// Initialises the Vector
		Vector resourcesBean = new Vector();
		RDFi rdf = new RDFi();
		for(int i = 0; i<resources.size(); i++)
		{
			Resources res = new Resources();
			String resourceID = (String) resources.get(i);
			res.setID(resourceID);
			res.setTitle(rdf.getResourceTitle(resourceID));
			res.setURL(rdf.getResourceURL(resourceID));
			res.setTimeStamp(rdf.getResourceTimestamp(resourceID));
			long time = getLong(rdf.getResourceTimestamp(resourceID));
			Date date = new Date(time);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String fulldate = sdf.format(date);
			res.setDate(fulldate);
			resourcesBean.add(res);
		}
		sort(resourcesBean);
		return resourcesBean;
	}
	
	public long getLong(double d){
		return (long) d;
	}
	
	public void sort(Vector<Resources> a)
	 {
	  for(int i=1; i<a.size(); i++)
	     insert(a.get(i),a,i);
	 }

	 private void insert(Resources n,Vector<Resources> a,int i)
	 {
	  for(; i>0&&((Comparable<Double>) a.get(i-1).getTimeStamp()).compareTo(n.getTimeStamp())>0; i--)
	     a.set(i,a.get(i-1));
	  a.set(i,n);
	 }
	
	/**
	 * Retrieves the project title based on the project Resource ID provided.
	 * 
	 * @param resourceID
	 * @return String containing project title
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public String getTitle(String resourceID) throws ServletException, IOException, OpenRDFException 
	{
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?title WHERE { ");
		qry.append("<");
		qry.append(resourceID);
		qry.append("> ");
		qry.append("<http://www.policygrid.org/utility.owl#Name> ?title");
		qry.append(" } ");

		
		
		
		String title = "";
	
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("title");
			   title = value.stringValue();
		}
		result.close();
		con.getRepConnection().close();
		return title;
	}
	
	/**
	 * Returns the project subtitle based on the resource ID provided
	 * @param resourceID
	 * @return String containing the project description
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public String getSubtitle(String resourceID) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?description WHERE { ");
		qry.append("<");
		qry.append(resourceID);
		qry.append("> ");
		qry.append("<http://www.policygrid.org/utility.owl#subtitle> ?description");
		qry.append(" } ");

		
		String description = "";
	
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext())
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("description");
			   description = value.stringValue();
		}
		result.close();
		con.getRepConnection().close();
		return description;
	}

	public String getStartdate(String resourceID) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?startdate WHERE { ");
		qry.append("<");
		qry.append(resourceID);
		qry.append("> ");
		qry.append("<http://www.policygrid.org/utility.owl#Startdate> ?startdate");
		qry.append(" } ");

		
		String description = "";
	
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext())
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("startdate");
			   description = value.stringValue();
		}
		result.close();
		con.getRepConnection().close();
		return description;
	}
	
	public String getEnddate(String resourceID) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?enddate WHERE { ");
		qry.append("<");
		qry.append(resourceID);
		qry.append("> ");
		qry.append("<http://www.policygrid.org/utility.owl#Enddate> ?enddate");
		qry.append(" } ");

		
		String description = "";
	
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext())
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("enddate");
			   description = value.stringValue();
		}
		result.close();
		con.getRepConnection().close();
		return description;
	}
	
	public String getGrantNumber(String resourceID) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?grantnumber WHERE { ");
		qry.append("<");
		qry.append(resourceID);
		qry.append("> ");
		qry.append("<http://www.policygrid.org/utility.owl#GrantNumber> ?grantnumber");
		qry.append(" } ");

		
		String description = "";
	
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext())
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("grantnumber");
			   description = value.stringValue();
		}
		result.close();
		con.getRepConnection().close();
		return description;
	}
	
	public ArrayList getOrganisations(String resourceID) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?org WHERE { ");
		qry.append("<");
		qry.append(resourceID);
		qry.append("> ");
		qry.append("<http://www.policygrid.org/utility.owl#OrganisationOrDepartment> ?org");
		qry.append(" } ");

		
		String description = "";
	
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		ArrayList list = new ArrayList();
		while (result.hasNext())
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("org");
			   description = value.stringValue();
			   list.add(description);
		}
		result.close();
		con.getRepConnection().close();
		return list;
	}
	
	public String getAims(String resourceID) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?aims WHERE { ");
		qry.append("<");
		qry.append(resourceID);
		qry.append("> ");
		qry.append("<http://www.policygrid.org/utility.owl#HasResearchAims> ?aims");
		qry.append(" } ");

		
		String description = "";
	
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext())
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("aims");
			   description = value.stringValue();
		}
		result.close();
		con.getRepConnection().close();
		return description;
	}
	
	public ArrayList getEachAim(String resourceID) throws ServletException, IOException, OpenRDFException
	{
		ArrayList list = new ArrayList();
		if((!resourceID.equals("")) && (resourceID != null))
		{
			StringBuffer qry = new StringBuffer(1024);
			qry.append("SELECT ?aim WHERE { ");
			qry.append("<");
			qry.append(resourceID);
			qry.append("> ");
			qry.append("<http://www.policygrid.org/utility.owl#HasDescription> ?aim");
			qry.append(" } ");
	
			
			String description = "";
		
			String query = qry.toString();
			
			con.repConnect();
			
			TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
			TupleQueryResult result = output.evaluate();
			
			while (result.hasNext())
			{
				   BindingSet bindingSet = result.next();
				   Value value = bindingSet.getValue("aim");
				   description = value.stringValue();
				   list.add(description);
			}
			result.close();
			con.getRepConnection().close();
			return list;
		}
		else
			return list;
	}
	
	/**
	 * Returns a vector with the resource IDs of all projects
	 * in the repository.
	 * 
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public Vector getAllProjectsInOurSpaces() throws ServletException, IOException, OpenRDFException
	{
		Vector projects = new Vector();
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select ?x where { ");
		qry.append("?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ");
		qry.append("<http://www.policygrid.org/utility.owl#Project> } ");
		
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext())
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("x");
			   String uri = value.stringValue();
			   projects.add(uri);
		}
		result.close();
		con.getRepConnection().close();
		
		return projects;
	}
	
	public ArrayList getProjects() throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("prefix pg: <http://www.policygrid.org/project.owl#> "); 
		qry.append("Select ?x ");
		qry.append("Where {   ");
		qry.append("?x a pg:Project.  ");
		qry.append("?x pg:projectTitle ?name.  ");
		qry.append("}  ");
		
		
		common.Utility.log.debug(qry);
		String uri = "";
		
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		ArrayList list = new ArrayList();
		while (result.hasNext())
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("x");
			   uri = value.stringValue();
			   list.add(uri);
		}
		result.close();
		con.getRepConnection().close();
		return list;
	}
	
	public ArrayList getResourcesFromStage(String stage, String projectID) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		
		qry.append("select ?y ?z where { ");
		qry.append("?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ");
		qry.append("<http://www.policygrid.org/utility.owl#"+stage+">. ");
		qry.append("?x <http://www.policygrid.org/task.owl#HasOutput> ?y. ");
		qry.append("?y <http://www.policygrid.org/provenance-generic.owl#ProducedInProject> ?z. ");
		qry.append("?z <http://www.policygrid.org/utility.owl#Name> ?name ");
		qry.append("} ");
		
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		// list of all resources in particular stage
		ArrayList allResources = new ArrayList();
		
		// List of resources only related to a project
		ArrayList projectResources = new ArrayList();
		
		while (result.hasNext())
		{
			   BindingSet bindingSet = result.next();
			   Value y = bindingSet.getValue("y");
			   Value z = bindingSet.getValue("z");
			   String y2 = y.stringValue();
			   String z2 = z.stringValue();
			   Stage resStage = new Stage();
			   resStage.setResourceID(y2);
			   resStage.setProjectID(z2);
			   allResources.add(resStage);
		}
		result.close();
		con.getRepConnection().close();
		for(int i = 0; i < allResources.size(); i++)
		{
			Stage resStage = (Stage) allResources.get(i);
			if(resStage.getProjectID().equals(projectID))
			{
				projectResources.add(resStage.getResourceID());
			}
		}
		return projectResources;
	}
	
	/**
	 * Writes what's contained in an InputStream to the RDF Repository.
	 * 
	 * @param in
	 * @throws OpenRDFException
	 * @throws IOException
	 */
	public void write(InputStream in) throws OpenRDFException, IOException 
	{	
		con.repConnect();
		con.getRepConnection().add(in, con.getBaseURI(), RDFFormat.RDFXML);
		con.getRepConnection().close();
	}
}
