package common;

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.OWL;

import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;

import org.openrdf.OpenRDFException;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryException;
import org.xml.sax.SAXException;

@Deprecated public class FacetHandler {
	
	public static Hashtable hashtable = new Hashtable();
	public Hashtable types = new Hashtable();
	
	
	public FacetHandler() throws IOException
	{
		String path1 = "/home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/ontologies/opm-Resource.owl";
		FileInputStream in1 = new FileInputStream(path1);
		OntModel resource1 = ModelFactory.createOntologyModel();
		resource1 = (OntModel) resource1.read(in1,"http://policygrod.org/opm-resource.owl");
		OntModel resource2 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, resource1);
		in1.close();
		hashtable.put("general", resource1);
		hashtable.put("inferedGen", resource2);
		
		//resource2.write(System.out, "RDF/XML-ABBREV");
		
		//constructQuery("inferedGen", "http://www.policygrid.org/opm.owl#Artifact");
		
		
		
	}
	
	public class byTitle implements Comparator
	{
	      public int compare(Object o1, Object o2)
	      {
	    	  Resources u1 = (Resources) o1;
	    	  Resources u2 = (Resources) o2;
	    	  return u1.getTitle().compareTo(u2.getTitle());
	      }
	}
	
	public class byName implements Comparator
	{
	      public int compare(Object o1, Object o2)
	      {
	    	  Resources u1 = (Resources) o1;
	    	  Resources u2 = (Resources) o2;
	    	  return u1.getDepositor().compareTo(u2.getDepositor());
	      }
	}
	
	public class byType implements Comparator
	{
	      public int compare(Object o1, Object o2)
	      {
	    	  String u1 = (String) o1;
	    	  String u2 = (String) o2;
	    	  return u1.compareTo(u2);
	      }
	}

	
	public String constructQuery(String label, String resourceType, String property) throws QueryEvaluationException, RepositoryException, MalformedQueryException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?x ?ty ?title ?p WHERE { ");
				qry.append("?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>  ?ty . "); 
				qry.append("?x <http://www.policygrid.org/opm-resource.owl#title> ?title . ");  
				if(property != null && !property.equals("") && !property.equals("no"))
					qry.append("?x <" + property + "> ?p ");
				qry.append("FILTER ( ");
				qry.append("?ty = <"+ resourceType +"> || ");
				
		ArrayList subclasses = getSubclassList(label, resourceType);
		for(int i = 0; i < subclasses.size(); i++)
		{
			String name = (String) subclasses.get(i);
			if(i < subclasses.size() - 1)
			{
				qry.append("?ty = <"+ name +"> || ");
			}
			else
			{
				qry.append("?ty = <"+ name +"> )  }  ");
			}
		}
		String query = qry.toString();
		
		return query;
	}
	
	/**
	 * Queries for resources of a particular type belonging to a particular user.
	 * @param label
	 * @param resourceType
	 * @param property
	 * @return
	 * @throws QueryEvaluationException
	 * @throws RepositoryException
	 * @throws MalformedQueryException
	 */
	public ArrayList constructQueryForUserWithType(String label, String resourceType, String property, String propertyValue) throws QueryEvaluationException, RepositoryException, MalformedQueryException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?x ?title WHERE { ");
				qry.append("?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <"+resourceType+"> . "); 
				qry.append("?x <http://www.policygrid.org/opm-resource.owl#title> ?title . ");  
				qry.append("?x <" + property + "> <"+propertyValue+"> } ");
				
				
		String query = qry.toString();
		
		Connections con = new Connections();
		con.repConnect();

		ArrayList resources = new ArrayList();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			BindingSet bindingSet = result.next();
			String resourceID = bindingSet.getValue("x").stringValue();
			String title = bindingSet.getValue("title").stringValue();
			Resources res = new Resources();
			res.setID(resourceID);
			res.setTitle(title);
			res.setDepositorID(propertyValue);
			resources.add(res);
		}
		result.close();
		con.repDisconnect();
		
		return resources;
	}
	
	/**
	 * Queries for resources of any literal
	 * @param label
	 * @param resourceType
	 * @param property
	 * @return
	 * @throws QueryEvaluationException
	 * @throws RepositoryException
	 * @throws MalformedQueryException
	 */
	/*public ArrayList constructQueryForLiterals(String label, String resourceType, String property) throws QueryEvaluationException, RepositoryException, MalformedQueryException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?x ?y WHERE { ");
				qry.append("?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <"+resourceType+"> . "); 
				qry.append("?x <" + property + "> ?y } ");
		
		String query = qry.toString();
		
		Connections con = new Connections();
		con.repConnect();

		ArrayList resources = new ArrayList();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			BindingSet bindingSet = result.next();
			String resourceID = bindingSet.getValue("x").stringValue();
			String title = bindingSet.getValue("title").stringValue();
			Resources res = new Resources();
			res.setID(resourceID);
			res.setTitle(title);
			res.setDepositorID(propertyValue);
			resources.add(res);
		}
		result.close();
		con.repDisconnect();
		
		return resources;
	} */
	
	public ArrayList getPropertyList(String label, String resourceType, String property) throws QueryEvaluationException, RepositoryException, MalformedQueryException
	{
		Connections con = new Connections();
		con.repConnect();
		
		String query = constructQuery(label, resourceType, property);
		ArrayList properties = new ArrayList();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			BindingSet bindingSet = result.next();
			String p = bindingSet.getValue("p").stringValue();
			if(!properties.contains(p))
			{
				properties.add(p);

			}
		}
		result.close();
		con.repDisconnect();
		
		
		return properties;
	}
	
	public Hashtable getDepositor(String label, String resourceType, String property, ArrayList list) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException
	{
		Hashtable users = new Hashtable();
		for(int i = 0; i < list.size(); i++)
		{
				ArrayList GOD = new ArrayList();
				users.put(list.get(i), GOD);

		}
		
		ArrayList titles = getAllDepositors(label, resourceType, property);
		


		
		for(int i = 0; i < titles.size(); i++)
		{
			Resources res = (Resources) titles.get(i);
			String depositor = res.getDepositorID();
			
			ArrayList resourceList = new ArrayList();
			resourceList = (ArrayList) users.get(depositor);
			
			
			if(resourceList != null) {

				resourceList.add(res);
				users.put(depositor, resourceList);
			}
		}
		
		return users;
	}
	
	public ArrayList getAllTitles(String label, String resourceType, String property) throws QueryEvaluationException, RepositoryException, MalformedQueryException
	{
		Connections con = new Connections();
		con.repConnect();
		
		String query = constructQuery(label, resourceType, property);
		ArrayList titles = new ArrayList();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			Resources res = new Resources();   
			BindingSet bindingSet = result.next();
			String id = bindingSet.getValue("x").stringValue();
			String type = bindingSet.getValue("ty").stringValue();
			String title = bindingSet.getValue("title").stringValue();
			res.setID(id);
			res.setType(type);
			res.setTitle(title);
			titles.add(res);
		}
		result.close();
		con.repDisconnect();
		
		Collections.sort(titles, new byTitle());
		
		return titles;
	}
	
	public ArrayList getAllDepositors(String label, String resourceType, String property) throws QueryEvaluationException, RepositoryException, MalformedQueryException
	{
		Connections con = new Connections();
		con.repConnect();
		
		String query = constructQuery(label, resourceType, property);
		ArrayList titles = new ArrayList();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			Resources res = new Resources();   
			BindingSet bindingSet = result.next();
			String id = bindingSet.getValue("x").stringValue();
			String type = bindingSet.getValue("ty").stringValue();
			String title = bindingSet.getValue("title").stringValue();
			String prop = bindingSet.getValue("p").stringValue();
			res.setID(id);
			res.setType(type);
			res.setTitle(title);
			res.setDepositorID(prop);
			titles.add(res);
		}
		result.close();
		con.repDisconnect();

		return titles;
	}
	
	public Hashtable getTypes(String label, String resourceType, String property, ArrayList list) throws QueryEvaluationException, RepositoryException, MalformedQueryException
	{		
		
		for(int i = 0; i < list.size(); i++)
		{
			ArrayList resources = new ArrayList();
			types.put(list.get(i), resources);
		}
		
		ArrayList titles = getAllTitles(label, resourceType, property);

		
		for(int i = 0; i < titles.size(); i++)
		{
			Resources res = (Resources) titles.get(i);
			String type = res.getType();
			
			ArrayList resourceList = (ArrayList) types.get(type);
			if(resourceList != null) {
				resourceList.add(res);
				types.put(type, resourceList);
			}
		}
		
		return types;
	}
	
	public ArrayList getSubclassList(String label, String resourceType) {
		OntModel m = getOntology(label);
		OntClass c = m.getOntClass(resourceType);
		ArrayList classList = new ArrayList();
		if(c == null)
			return classList;
		for (Iterator it = c.listSubClasses(false); it.hasNext();) {
			OntClass sc = (OntClass) it.next();
			classList.add(sc.getNameSpace() + sc.getLocalName().toString());
		}
		Collections.sort(classList, new byType());
		return classList;
	}
	
	public ArrayList getSubclassListWithLocalNames(String label, String resourceType) {
		OntModel m = getOntology(label);
		OntClass c = m.getOntClass(resourceType);
		ArrayList classList = new ArrayList();
		for (Iterator it = c.listSubClasses(false); it.hasNext();) {
			OntClass sc = (OntClass) it.next();
			classList.add(sc.getLocalName().toString());
		}
		return classList;
	}
	
	public OntModel getOntology(String label) {
		OntModel temp = null;
		if (hashtable.containsKey(label))
			temp = (OntModel) hashtable.get(label);
		return temp;
	}
	
}
