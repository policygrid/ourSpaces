package common;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;

import org.openrdf.OpenRDFException;
import org.openrdf.model.Literal;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.query.BindingSet;
import org.openrdf.query.GraphQuery;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFFormat;
import org.policygrid.ontologies.AcademicDiscipline;
import org.policygrid.ontologies.FOAF;
import org.policygrid.ontologies.OurSpacesVRE;
import org.policygrid.ontologies.ProvenanceGeneric;
import org.policygrid.ontologies.SIOC;
import org.xml.sax.SAXException;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;


/**
 * RDF Initialiser sds   sadfa Class used to handle the interfacing between the user and the
 * RDF repository.  Generally used to generate data related to user Profile pages.
 * 
 * @author Richard Reid
 * @version 1.2 ed
 */

public class RDFi 
{
	
	@Deprecated public OntModel ontology;
	Connections con = new Connections();
	

	
	public RDFi()
	{
		
	}
	
	/**
	 * Reads in the Utility Ontology. The path is retrieved
	 * from the Connections class.
	 * 
	 * @throws IOException
	 * @throws SAXException
	 */
	@Deprecated public void OntologyReader() throws IOException, SAXException 
	{
		String path2 = con.getUtilityOntology();
	    FileInputStream in = new FileInputStream(path2);
	    ontology = ModelFactory.createOntologyModel();
	    ontology = (OntModel) ontology.read(in, "http://www.policygrid.org/utility.owl");
	    in.close();
	}


	@Deprecated public OntProperty getProperty(String name)
		{
			for (Iterator<OntProperty> it = ontology.listOntProperties(); it.hasNext(); )
			{
				OntProperty p = (OntProperty) it.next();
				if (p.getLocalName().equalsIgnoreCase(name))
					return p;
			}
			return null;
		}
	
	@Deprecated public OntClass getClass (String name){
		for(Iterator<OntClass> it = ontology.listNamedClasses(); it.hasNext();){
			OntClass c =(OntClass) it.next();
			if(c.getLocalName().equalsIgnoreCase(name))
				return c;
		}
		return null;
	}
	
	
	/**
	 * Returns FOAF properties by querying with a user's ourSpaces RDF ID.
	 * 
	 * @param uniqueOurSpacesID
	 * @param namespace
	 * @return
	 * @throws OpenRDFException
	 * @throws IOException
	 */
	public String getFoafProperty(String uniqueOurSpacesID, String namespace) throws OpenRDFException, IOException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?y where { ?x <"+namespace+"> ?y . ");
		qry.append("?x <http://xmlns.com/foaf/0.1/holdsAccount> <"+uniqueOurSpacesID+"> } ");
		
		//TODO: extract the code to execute the SPARQL query in a method
		return executeSparqlQuery(qry);
	}

	private String executeSparqlQuery(StringBuffer qry) throws RepositoryException,
			MalformedQueryException, QueryEvaluationException {
		String resource = "";
	
		String query = qry.toString();

		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("y");
			   resource = value.stringValue();
		}
		result.close();
		con.repDisconnect();

		return resource;
	}

	private List<String> executeSparqlQueryMultiple(StringBuffer qry)
			throws RepositoryException, MalformedQueryException,
			QueryEvaluationException {
		List<String> res= new ArrayList<String>();

		String query = qry.toString();

		con.repConnect();

		TupleQuery output = con.getRepConnection().prepareTupleQuery(
				QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();

		while (result.hasNext()) {
			BindingSet bindingSet = result.next();
			Value value = bindingSet.getValue("y");
			res.add(value.stringValue());
		}
		result.close();
		con.repDisconnect();

		return res;
	}
	
		
	/**
	 * Returns an ourSpaces User Account RDF ID using a FOAF ID.
	 * 
	 * @param uniqueFOAFID
	 * @param namespace
	 * @return
	 * @throws OpenRDFException
	 * @throws IOException
	 */
	public String getOurSpacesUserIdFromFOAF(String uniqueFOAFID) throws OpenRDFException, IOException
	{
		return getPropertyValue(uniqueFOAFID, FOAF.holdsAccount.toString());
	}

	
	/**
	 * Returns an ourSpaces User Account RDF ID using a FOAF ID.
	 * 
	 * @param uniqueFOAFID
	 * @param namespace
	 * @return
	 * @throws OpenRDFException
	 * @throws IOException
	 */
	public String getFOAFIDFromOurSpacesUser(String uniqueOurSpacesID) throws OpenRDFException, IOException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?y where { ?y <"+FOAF.holdsAccount.toString()+"> <"+uniqueOurSpacesID+"> } ");
		
		return executeSparqlQuery(qry);
	}
	
	/**
	 * Returns data for the browse JSP. Returns all known properties of a resource.
	 * @throws RepositoryException 
	 * @throws MalformedQueryException 
	 * @throws QueryEvaluationException 
	 * @throws UnsupportedEncodingException 
	 */
	public ArrayList<Properties> getProperties(String resourceID) throws RepositoryException, MalformedQueryException, QueryEvaluationException, UnsupportedEncodingException
	{
		ArrayList<Properties> res = new ArrayList<Properties>();
		String query1 = "CONSTRUCT {<" + resourceID + "> ?y ?z.} WHERE {<" + resourceID + "> ?y ?z.}";
		
		common.Utility.log.debug(query1.toString());
		
		con.repConnect();
		GraphQuery queryA = con.getRepConnection().prepareGraphQuery(QueryLanguage.SPARQL, query1);
		GraphQueryResult result1 = queryA.evaluate();
		con.repDisconnect();
		
		
		/*byte[] bytes = result.getBytes("UTF-8");
		InputStream input = new ByteArrayInputStream(bytes);
		
		Model m = ModelFactory.createDefaultModel();
		m = (Model) m.read(input, "http://policygrod.org/opm-resource.owl");
		
		common.Utility.log.debug(m);
		
		Resource r = m.getResource(resourceID);
		StmtIterator iter = (StmtIterator) r.listProperties();
		while(iter.hasNext())
		{
			Statement st = iter.nextStatement();
			common.Utility.log.debug(st);
			String value = (String) st.getString();
			Property property = (Property) st.getPredicate();
			String namespace = property.getNameSpace();
			String propertyName = property.getLocalName();
			Properties prop = new Properties();
			prop.setProperty(namespace + propertyName);
			prop.setValue(value);
			if(isResource(value))
			{
				prop.isResource = true;
			}
			else
			{
				prop.isResource = false;
			}
			
			common.Utility.log.debug(" ----------------- " + namespace + propertyName + " ==== " + value);
			
			res.add(prop);
		} */
		
		while(result1.hasNext())
		{
			org.openrdf.model.Statement st = (org.openrdf.model.Statement) result1.next();
			org.openrdf.model.URI uri = st.getPredicate();
			org.openrdf.model.Value val = st.getObject();
			
			String namespace = uri.getNamespace();
			String property = uri.getLocalName();
			String value = val.stringValue();
			
			common.Utility.log.debug(" ---------HERE WE GO-------- " + namespace + property + " ==== " + value);
			
			Properties prop = new Properties();
			prop.setProperty(namespace + property);
			prop.setValue(value);
			if(isResource(value))
			{
				prop.isResource = true;
			}
			else
			{
				prop.isResource = false;
			}
			res.add(prop);
		}
		
		return res;
	}
	
	public boolean isResource(String res)
    {
 
    	if (res.contains("#")) {
    		return true;
    	}
 
    	return false;
    }
	
	
	
	/**
	 * Returns a list of all blog container IDs found in the parent container.
	 * 
	 * @param parentContainerID
	 * @return
	 * @throws RepositoryException
	 * @throws QueryEvaluationException
	 * @throws MalformedQueryException
	 */
	public ArrayList<String> getBlogContainer(String parentContainerID) throws RepositoryException, QueryEvaluationException, MalformedQueryException
	{
		ArrayList<String> containers = new ArrayList<String>();
		String blogContainerID = "";
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?y where { ?y a <"+OurSpacesVRE.BlogContainer.toString()+">. ");
		qry.append("<"+parentContainerID+"> <"+SIOC.parent_of.toString()+"> ?y } ");
		
		String query = qry.toString();

		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("y");
			   blogContainerID = value.stringValue();
			   containers.add(blogContainerID);
		}
		result.close();
		con.repDisconnect();
		
		return containers;
	}
	
	public String getUserProfileContainer(String rdfUserID) throws RepositoryException, QueryEvaluationException, MalformedQueryException
	{
		String userContainerID = "";
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?y where { ?y a <"+OurSpacesVRE.UserProfileContainer.toString()+">. ");
		qry.append("?y <"+SIOC.has_owner.toString()+"> <"+rdfUserID+"> } ");
		
		String query = qry.toString();

		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("y");
			   userContainerID = value.stringValue();
		}
		result.close();
		con.repDisconnect();
		
		return userContainerID;
	}
	
	
	public void deletePhoto(String foafID) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("construct { ");
		qry.append("<"+foafID+"> <"+OurSpacesVRE.hasPhoto.toString()+"> ?a. } where { ");
		qry.append("<"+foafID+"> <"+OurSpacesVRE.hasPhoto.toString()+"> ?a. } ");

		
		String query = qry.toString();
		
		con.repConnect();
		
		GraphQuery q = con.getRepConnection().prepareGraphQuery(QueryLanguage.SPARQL, query);
		GraphQueryResult result = q.evaluate();
		con.getRepConnection().remove(result);
		
		con.repDisconnect();
	}
	
	public String getObjectID(String objectName, String className) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("PREFIX pg: <http://www.policygrid.org/utility.owl#> ");
		qry.append("SELECT ?id WHERE { ?id pg:Name ?name. filter(regex(?name, \"");
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
	
	/**
	 * Returns an RDF id based on some data entered.
	 * @param propertyValue
	 * @param propertyName
	 * @param className
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public String getResourceIDFromRDF(String propertyValue, String propertyName, String className) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select DISTINCT ?x where { ?x <"+propertyName+"> ?y .");
		qry.append("filter(regex(?y, \""+propertyValue+"\", 'i')). ?id a <"+className+"> } ");
		
		String resource = "";
	
		String query = qry.toString();

		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("x");
			   resource = value.stringValue();
		}
		result.close();
		con.repDisconnect();

		return resource;
	}
	
	
	public void updatePhoto(String foafID, String photo) throws OpenRDFException, IOException, ServletException, ParserConfigurationException, SAXException
	{
		deletePhoto(foafID);
				
		Model model = ModelFactory.createDefaultModel();
		
		Resource person = model.createResource(foafID);
		person.addProperty(OurSpacesVRE.hasPhoto, photo);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		model.write(out);
		InputStream in = new ByteArrayInputStream(out.toByteArray());
		write(in);
	}

	
	/**
	 * Writes the current InputStream into the repository.  The InputStream must
	 * contain applicable RDF metadata.
	 * 
	 * @param in
	 * @throws OpenRDFException
	 * @throws IOException
	 */
	public void write(InputStream in) throws OpenRDFException, IOException
	{	
		con.repConnect();
		con.getRepConnection().add(in, con.getBaseURI(), RDFFormat.RDFXML);
		con.repDisconnect();
	}
	
	public void writeRDF(InputStream in) throws OpenRDFException, IOException
	{	
		con.repConnect();
		con.getRepConnection().add(in, con.getBaseURI(), RDFFormat.RDFXML);
		con.repDisconnect();
	}

	
	/**
	 * Generates the organisation a user may belong to based on the unique resource ID.
	 * 
	 * @param resourceID
	 * @return String containing a user's organisation
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public HashMap<String, String> getResourcesByKeywordSearch( String resourceType, String keywords) throws ServletException, IOException, OpenRDFException 
	{
		StringBuffer qry = new StringBuffer(1024);
		OntologyHandler o;
		try {
			o = new OntologyHandler();qry.append("SELECT DISTINCT ?res ?lab ?title ");
			qry.append("WHERE "); 
			qry.append("{ "); 
			qry.append("?res <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>  ?ty . ");
			//qry.append("?res <"+RDF.type+"> <"+resourceType+">. ");
		    //Additional types
			
			qry.append("OPTIONAL { ?res <"+RDFS.label+"> ?lab } . ");
			qry.append("OPTIONAL { ?res <"+ProvenanceGeneric.BASE_URI+"#title> ?title } . ");
			qry.append("?res ?pr ?lit. ");
			
			qry.append("filter(regex(?lit, \""+keywords+"\", 'i')"); 

			//FacetHandler ft = new FacetHandler();
			Vector<String> subclasses = o.getSubclassListFull("general", resourceType);//getSubclassList("inferedGen", resourceType);
			
			if (subclasses.size() > 0) {
				qry.append(" && (");
			for(int i = 0; i < subclasses.size(); i++)
			{
				String name = (String) subclasses.get(i);
				if(i < subclasses.size() - 1)
				{
					qry.append("?ty = <"+ name +"> || ");
				}
				else
				{
					qry.append("?ty = <"+ name +"> )  ).");
				}
			}
			} else {
				qry.append(" && (?ty = <"+ resourceType +"> )  ).");		
			}
			
			
			qry.append(" }");
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				

        common.Utility.log.debug("query: "+qry.toString());
		
		String query = qry.toString();

		con.repConnect();
		
		HashMap<String, String> ret = new HashMap<String, String>();

		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();

		while (result.hasNext()) 
		{
			BindingSet bindingSet = result.next();
			Value resourceURI = bindingSet.getValue("res");
		
			Value resourceLiteral = null;
			if (bindingSet.getValue("lab") != null)  resourceLiteral = bindingSet.getValue("lab");
			
			Value titleLiteral = null;
			if (bindingSet.getValue("title") != null) titleLiteral = bindingSet.getValue("title");
			
			if (resourceLiteral != null) {
				ret.put(resourceURI.stringValue(), resourceLiteral.stringValue());
			} else {

				if (titleLiteral != null) ret.put(resourceURI.stringValue(), titleLiteral.stringValue());
			}
			
		}
		result.close();
		con.repDisconnect();
		return ret;
	}
	
	public HashMap<String, String> getRegisteredUsersByKeyword( String resourceType, String keywords) throws ServletException, IOException, OpenRDFException 
	{
		StringBuffer qry = new StringBuffer(1024);
	
		qry.append("SELECT DISTINCT ?res ?lab ");
		qry.append("WHERE "); 
		qry.append("{ "); 
		qry.append("?res <"+RDF.type+"> <"+resourceType+">. ");
		qry.append("?res <"+RDFS.label+"> ?lab. ");
		qry.append("?res <"+FOAF.holdsAccount+"> ?acc. ");
		qry.append("?acc <"+RDF.type+"> <"+OurSpacesVRE.OurSpacesAccount+">. ");
		qry.append("?res ?pr ?lit. ");
		qry.append("filter(regex(?lit, \""+keywords+"\", 'i')). "); 
		qry.append("} ");

common.Utility.log.debug("query: "+qry.toString());
		
		String query = qry.toString();

		con.repConnect();
		
		HashMap<String, String> ret = new HashMap<String, String>();

		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();

		while (result.hasNext()) 
		{
			BindingSet bindingSet = result.next();
			Value resourceURI = bindingSet.getValue("res");
			Value resourceLiteral = bindingSet.getValue("lab");
			ret.put(resourceURI.stringValue(), resourceLiteral.stringValue());
			
		}
		result.close();
		con.repDisconnect();
		return ret;
	}
	
	/**
	 * Generates the organisation a user may belong to based on the unique resource ID.
	 * 
	 * @param resourceID
	 * @return String containing a user's organisation
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public String getOrganisation(String resourceID) throws ServletException, IOException, OpenRDFException 
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?organisation WHERE { ");
		qry.append("<");
		qry.append(resourceID);
		qry.append("> ");
		qry.append("<http://www.policygrid.org/utility.owl#EmployeeOf> ?organisation");
		qry.append(" } ");

		
		String organisation = "";
	
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("organisation");
			   organisation = value.stringValue();
		}
		result.close();
		con.repDisconnect();
		return organisation;
	}

	
	public String getAddressID(String foafID) throws ServletException, IOException, OpenRDFException
	{
		return getPropertyValue(foafID, org.policygrid.ontologies.Utility.hasAddress.toString());
	}

	/**
	 * Retrieve the value of the given property, for the given resource. If this resource has more than one of those property, only one will be returned.
	 * @param resourceID
	 * @param property
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public String getPropertyValue(String resourceID, String property) throws IOException, OpenRDFException
	{
		if (Utility.isNotNull(resourceID) && Utility.isNotNull(property)) {
			StringBuffer qry = new StringBuffer(1024);
			qry.append("SELECT ?y WHERE { <"+resourceID+"> <"+property+"> ?y } ");

			return executeSparqlQuery(qry);
		} else {
			return "";
		}
	}

	/**
	 * Retrieve the values of the given property, for the given resource. 
	 * @param resourceID
	 * @param property
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public List<String> getPropertyValues(String resourceID, String property) throws IOException, OpenRDFException
	{
		if (Utility.isNotNull(resourceID) && Utility.isNotNull(property)) {
			StringBuffer qry = new StringBuffer(1024);
			qry.append("SELECT ?y WHERE { <"+resourceID+"> <"+property+"> ?y } ");

			return executeSparqlQueryMultiple(qry);
		} else {
			return new ArrayList<String>();
		}
	}
	
	/**
	 * Returns all resources belonging to a particular user based on their resource ID.  The
	 * resources are returned in a Vector containing multiple Resource JavaBeans.
	 * 
	 * @param resourceID
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public Vector<Resources> getResources(String ourSpacesUserAccount) throws ServletException, IOException, OpenRDFException
	{
		// SPARQL query to retrieve resource ID of resources
		StringBuffer qry = new StringBuffer(1024);
		//qry.append("SELECT ?y WHERE { ?y <"+OpmResource.depositedBy.toString()+"> <"+ourSpacesUserAccount+"> . ?x <"+OurSpacesVRE.timestamp.toString()+"> ?time } ORDER BY DESC(?time) ");
	
		//qry.append("SELECT ?y WHERE { ?y <"+OpmResource.depositedBy.toString()+"> <"+ourSpacesUserAccount+">  }");

		//FacetHandler ft = new FacetHandler();
		OntologyHandler o;
		try {
			o = new OntologyHandler();
			Vector<String> subclasses = o.getSubclassListFull("all", "http://openprovenance.org/ontology#Artifact");
			subclasses.add("http://openprovenance.org/ontology#Artifact");
			
			qry.append("SELECT distinct ?y  ?time ?title ?uri ?pVersion ?vNumber ");
			qry.append("WHERE { ?y <"+ProvenanceGeneric.depositedBy.toString()+"> <"+ourSpacesUserAccount+"> . ");
			qry.append("        ?y <"+OurSpacesVRE.timestamp.toString()+"> ?time . ");
		    qry.append("   		?y  a ?ty . ");
		    qry.append("   		FILTER ( ");
				for(int i = 0; i < subclasses.size(); i++)
				{
					String name = (String) subclasses.get(i);
					if(i < subclasses.size() - 1)
					{
						qry.append("?ty = <"+ name +"> || ");
					}
					else
					{
						qry.append("?ty = <"+ name +">   ");
					}
				}
		    qry.append("   		).");	    
			qry.append("        FILTER   isURI(?y) . ");		
			qry.append("        FILTER NOT EXISTS { ?any <"+ProvenanceGeneric.previousVersion.toString()+"> ?y } ");
			qry.append("OPTIONAL { ?y <"+ProvenanceGeneric.previousVersion.toString()+"> ?pVersion .  }");
			qry.append("OPTIONAL { ?y <"+ProvenanceGeneric.versionNumber.toString()+"> ?vNumber .  }");
		    qry.append("OPTIONAL { ");
		    qry.append("   ?y <"+ProvenanceGeneric.title.toString()+"> ?title . ");
		    qry.append("   ?y  <"+ProvenanceGeneric.hasURI.toString()+"> ?uri . ");
		    qry.append(" } ");
		    qry.append("} ORDER BY ASC(?time)");

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query = qry.toString();
		//common.Utility.log.debug(query);
		
		// Connection
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		Vector<Resources> allResources = new Vector<Resources>();
		
		
		while (result.hasNext()) 
		{ 
			   BindingSet bindingSet = result.next();
					   
		      String resID = bindingSet.getValue("y").stringValue();
				Resources res = new Resources();
				res.setID(resID);
				if(bindingSet.hasBinding("title"))
					res.setTitle(bindingSet.getValue("title").stringValue());
				if(bindingSet.hasBinding("uri"))
					res.setURL(bindingSet.getValue("uri").stringValue());
				//common.Utility.log.debug("VERSION : "+bindingSet.getValue("vNumber"));
				
				if(bindingSet.hasBinding("pVersion"))
					res.setPreviousVersion(bindingSet.getValue("pVersion").stringValue());
				
				if(bindingSet.hasBinding("vNumber"))
					res.setVersionNumber(bindingSet.getValue("vNumber").stringValue());
				
				Value  tvalue = bindingSet.getValue("time");
				double timestamp = Double.parseDouble(tvalue.stringValue());
				
				res.setTimeStamp(timestamp);
				long time = getLong(timestamp);
				Date date = new Date(time);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String fulldate = sdf.format(date);
				res.setDate(fulldate);
				allResources.add(res);
			   
			   
		}
		result.close();
		con.repDisconnect();
		
		//sort(allResources);
		return allResources;
	}
	

	/**
	 * Returns all processes belonging to a particular user based on their resource ID.  The
	 * resources are returned in a Vector containing multiple Resource JavaBeans.
	 * 
	 * @param resourceID
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public Vector<Resources> getProcesses(String ourSpacesUserAccount) throws ServletException, IOException, OpenRDFException
	{
		// SPARQL query to retrieve resource ID of resources
		StringBuffer qry = new StringBuffer(1024);
		//qry.append("SELECT ?y WHERE { ?y <"+OpmResource.depositedBy.toString()+"> <"+ourSpacesUserAccount+"> . ?x <"+OurSpacesVRE.timestamp.toString()+"> ?time } ORDER BY DESC(?time) ");
	
		//qry.append("SELECT ?y WHERE { ?y <"+OpmResource.depositedBy.toString()+"> <"+ourSpacesUserAccount+">  }");

		OntologyHandler o;
		try {
			o = new OntologyHandler();
			Vector<String> subclasses = o.getSubclassListFull("all", "http://openprovenance.org/ontology#Process");		
			subclasses.add("http://openprovenance.org/ontology#Process");
			qry.append("SELECT DISTINCT ?y  ?time ?title ?uri ?pVersion ?vNumber ");
			qry.append("WHERE { ?y <"+ProvenanceGeneric.depositedBy.toString()+"> <"+ourSpacesUserAccount+"> . ");
			qry.append("        ?y <"+OurSpacesVRE.timestamp.toString()+"> ?time . ");
		    qry.append("   		?y  a ?ty . ");
		    qry.append("   		FILTER ( ");	 
				for(int i = 0; i < subclasses.size(); i++)
				{
					String name = (String) subclasses.get(i);
					if(i < subclasses.size() - 1)
					{
						qry.append("?ty = <"+ name +"> || ");
					}
					else
					{
						qry.append("?ty = <"+ name +"> ");
					}
				}
		    qry.append("   		). ");	    
			qry.append("        FILTER   isURI(?y) . ");		
			qry.append("        FILTER NOT EXISTS { ?any <"+ProvenanceGeneric.previousVersion.toString()+"> ?y } ");
			qry.append("OPTIONAL { ?y <"+ProvenanceGeneric.previousVersion.toString()+"> ?pVersion .  }");
			qry.append("OPTIONAL { ?y <"+ProvenanceGeneric.versionNumber.toString()+"> ?vNumber .  }");
		    qry.append("OPTIONAL { ");
		    qry.append("   ?y <"+ProvenanceGeneric.title.toString()+"> ?title . ");
		    qry.append("   ?y  <"+ProvenanceGeneric.hasURI.toString()+"> ?uri . ");
		    qry.append(" } ");
		    qry.append("} ORDER BY ASC(?time)");


		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String query = qry.toString();
		//common.Utility.log.debug(query);
		
		// Connection
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		Vector<Resources> allResources = new Vector<Resources>();
		
		
		while (result.hasNext()) 
		{ 
			   BindingSet bindingSet = result.next();
					   
		      String resID = bindingSet.getValue("y").stringValue();
				Resources res = new Resources();
				res.setID(resID);
				if(bindingSet.hasBinding("title"))
					res.setTitle(bindingSet.getValue("title").stringValue());
				if(bindingSet.hasBinding("uri"))
					res.setURL(bindingSet.getValue("uri").stringValue());
				//common.Utility.log.debug("VERSION : "+bindingSet.getValue("vNumber"));
				
				if(bindingSet.hasBinding("pVersion"))
					res.setPreviousVersion(bindingSet.getValue("pVersion").stringValue());
				
				if(bindingSet.hasBinding("vNumber"))
					res.setVersionNumber(bindingSet.getValue("vNumber").stringValue());
				
				Value  tvalue = bindingSet.getValue("time");
				double timestamp = Double.parseDouble(tvalue.stringValue());
				
				res.setTimeStamp(timestamp);
				long time = getLong(timestamp);
				Date date = new Date(time);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String fulldate = sdf.format(date);
				res.setDate(fulldate);
				allResources.add(res);
			   
			   
		}
		result.close();
		con.repDisconnect();
		
		//sort(allResources);
		return allResources;
	}

	
	public Vector<Resources> getResourcesByType(String ourSpacesUserAccount, String resType, int offset,int limit) throws ServletException, IOException, OpenRDFException
	{
		// SPARQL query to retrieve resource ID of resources
		StringBuffer qry = new StringBuffer(1024);
		//qry.append("SELECT ?y WHERE { ?y <"+OpmResource.depositedBy.toString()+"> <"+ourSpacesUserAccount+"> . ?x <"+OurSpacesVRE.timestamp.toString()+"> ?time } ORDER BY DESC(?time) ");
	
		//qry.append("SELECT ?y WHERE { ?y <"+OpmResource.depositedBy.toString()+"> <"+ourSpacesUserAccount+">  }");

		
		qry.append("SELECT ?y  ?time ?title ?uri");
		qry.append("WHERE { ?y <"+ProvenanceGeneric.depositedBy.toString()+"> <"+ourSpacesUserAccount+"> . ");
		qry.append("        ?y <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <"+resType+">  . ");
		qry.append("        FILTER   isURI(?y) . ");		
		qry.append("        ?y <"+OurSpacesVRE.timestamp.toString()+"> ?time . ");
		qry.append("        FILTER NOT EXISTS { ?any <"+ProvenanceGeneric.previousVersion.toString()+"> ?y } ");
		qry.append("OPTIONAL { ?y <"+ProvenanceGeneric.previousVersion.toString()+"> ?pVersion .  }");
		qry.append("OPTIONAL { ?y <"+ProvenanceGeneric.versionNumber.toString()+"> ?vNumber .  }");
	    qry.append("OPTIONAL { ");
	    qry.append("   ?y <"+ProvenanceGeneric.title.toString()+"> ?title . ");
	    qry.append("   ?y  <"+ProvenanceGeneric.hasURI.toString()+"> ?uri . ");
	    qry.append(" } ");
	    qry.append("} ORDER BY DESC(?time)");
	    qry.append("		LIMIT  " + limit + " ");
	    qry.append("		OFFSET  " + offset + " ");
		

	    
	    
	    
	    
		String query = qry.toString();
		//common.Utility.log.debug("Get res by type "+query);
		
		// Connection
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		Vector<Resources> allResources = new Vector<Resources>();
		
		
		while (result.hasNext()) 
		{ 
			   BindingSet bindingSet = result.next();
					   
		      String resID = bindingSet.getValue("y").stringValue();
				Resources res = new Resources();
				res.setID(resID);
				if(bindingSet.hasBinding("title"))
					res.setTitle(bindingSet.getValue("title").stringValue());
				if(bindingSet.hasBinding("uri"))
					res.setURL(bindingSet.getValue("uri").stringValue());
				
				
				if(bindingSet.hasBinding("pVersion"))
					res.setPreviousVersion(bindingSet.getValue("pVersion").stringValue());
				
				if(bindingSet.hasBinding("vNumber"))
					res.setPreviousVersion(bindingSet.getValue("vNumber").stringValue());
				
				Value  tvalue = bindingSet.getValue("time");
				double timestamp = Double.parseDouble(tvalue.stringValue());
				
				res.setTimeStamp(timestamp);
				long time = getLong(timestamp);
				Date date = new Date(time);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String fulldate = sdf.format(date);
				res.setDate(fulldate);
				allResources.add(res);
			   
			   
		}
		result.close();
		con.repDisconnect();
		
		//sort(allResources);
		return allResources;
	}
	
	public Vector<Resources> getOthersResourcesByType(String ourSpacesUserAccount, String resType, int offset,int limit) throws ServletException, IOException, OpenRDFException
	{
		// SPARQL query to retrieve resource ID of resources
		StringBuffer qry = new StringBuffer(1024);
		//qry.append("SELECT ?y WHERE { ?y <"+OpmResource.depositedBy.toString()+"> <"+ourSpacesUserAccount+"> . ?x <"+OurSpacesVRE.timestamp.toString()+"> ?time } ORDER BY DESC(?time) ");
	
		//qry.append("SELECT ?y WHERE { ?y <"+OpmResource.depositedBy.toString()+"> <"+ourSpacesUserAccount+">  }");

		
		qry.append("SELECT ?y  ?time ?title ?uri");
		qry.append("WHERE { ?y <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <"+resType+">  . ");
		qry.append("        FILTER   isURI(?y) . ");		
		qry.append("        ?y <"+OurSpacesVRE.timestamp.toString()+"> ?time . ");
		qry.append("        FILTER NOT EXISTS { ?any <"+ProvenanceGeneric.previousVersion.toString()+"> ?y } ");
		qry.append("        FILTER   NOT EXISTS{ ?y <"+ProvenanceGeneric.depositedBy.toString()+"> <"+ourSpacesUserAccount+">} . ");	
		qry.append("OPTIONAL { ?y <"+ProvenanceGeneric.previousVersion.toString()+"> ?pVersion .  }");
		qry.append("OPTIONAL { ?y <"+ProvenanceGeneric.versionNumber.toString()+"> ?vNumber .  }");
	    qry.append("OPTIONAL { ");
	    qry.append("   ?y <"+ProvenanceGeneric.title.toString()+"> ?title . ");
	    qry.append("   ?y  <"+ProvenanceGeneric.hasURI.toString()+"> ?uri . ");
	    qry.append(" } ");
	    qry.append("} ORDER BY DESC(?time)");
	    qry.append("		LIMIT  " + limit + " ");
	    qry.append("		OFFSET  " + offset + " ");
		

	    
	    
	    
	    
		String query = qry.toString();
		//common.Utility.log.debug("Get res by type "+query);
		
		// Connection
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		Vector<Resources> allResources = new Vector<Resources>();
		
		
		while (result.hasNext()) 
		{ 
			   BindingSet bindingSet = result.next();
					   
		      String resID = bindingSet.getValue("y").stringValue();
				Resources res = new Resources();
				res.setID(resID);
				if(bindingSet.hasBinding("title"))
					res.setTitle(bindingSet.getValue("title").stringValue());
				if(bindingSet.hasBinding("uri"))
					res.setURL(bindingSet.getValue("uri").stringValue());
				
				
				if(bindingSet.hasBinding("pVersion"))
					res.setPreviousVersion(bindingSet.getValue("pVersion").stringValue());
				
				if(bindingSet.hasBinding("vNumber"))
					res.setPreviousVersion(bindingSet.getValue("vNumber").stringValue());
				
				Value  tvalue = bindingSet.getValue("time");
				double timestamp = Double.parseDouble(tvalue.stringValue());
				
				res.setTimeStamp(timestamp);
				long time = getLong(timestamp);
				Date date = new Date(time);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String fulldate = sdf.format(date);
				res.setDate(fulldate);
				allResources.add(res);
			   
			   
		}
		result.close();
		con.repDisconnect();
		
		//sort(allResources);
		return allResources;
	}
	public Vector<Resources> getDocResources(String ourSpacesUserAccount,  int offset,int limit) throws ServletException, IOException, OpenRDFException
	{
		// SPARQL query to retrieve resource ID of resources
		StringBuffer qry = new StringBuffer(1024);
		//qry.append("SELECT ?y WHERE { ?y <"+OpmResource.depositedBy.toString()+"> <"+ourSpacesUserAccount+"> . ?x <"+OurSpacesVRE.timestamp.toString()+"> ?time } ORDER BY DESC(?time) ");
	
		//qry.append("SELECT ?y WHERE { ?y <"+OpmResource.depositedBy.toString()+"> <"+ourSpacesUserAccount+">  }");

		
		qry.append("SELECT ?y  ?time ?title ?uri");
		qry.append("WHERE { ?y <"+ProvenanceGeneric.depositedBy.toString()+"> <"+ourSpacesUserAccount+"> . ");
		qry.append("      { ?y <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.policygrid.org/provenance-generic.owl#Paper> } " +
				"			union {?y <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.policygrid.org/provenance-generic.owl#Report> } "+
				"			union {?y <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.policygrid.org/provenance-generic.owl#Documentation> } "+
				"			union {?y <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.policygrid.org/provenance-generic.owl#Presentation> } . ");
		qry.append("        FILTER   isURI(?y) . ");		
		qry.append("        ?y <"+OurSpacesVRE.timestamp.toString()+"> ?time . ");
		qry.append("        FILTER NOT EXISTS { ?any <"+ProvenanceGeneric.previousVersion.toString()+"> ?y } ");
		qry.append("OPTIONAL { ?y <"+ProvenanceGeneric.previousVersion.toString()+"> ?pVersion .  }");
		qry.append("OPTIONAL { ?y <"+ProvenanceGeneric.versionNumber.toString()+"> ?vNumber .  }");
	    qry.append("OPTIONAL { ");
	    qry.append("   ?y <"+ProvenanceGeneric.title.toString()+"> ?title . ");
	    qry.append("   ?y  <"+ProvenanceGeneric.hasURI.toString()+"> ?uri . ");
	    qry.append(" } ");
	    qry.append("} ORDER BY DESC(?time)");
	    qry.append("		LIMIT  " + limit + " ");
	    qry.append("		OFFSET  " + offset + " ");
		

	    
	    
	    
	    
		String query = qry.toString();
		//common.Utility.log.debug("Get res by type "+query);
		
		// Connection
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		Vector<Resources> allResources = new Vector<Resources>();
		
		
		while (result.hasNext()) 
		{ 
			   BindingSet bindingSet = result.next();
					   
		      String resID = bindingSet.getValue("y").stringValue();
				Resources res = new Resources();
				res.setID(resID);
				if(bindingSet.hasBinding("title"))
					res.setTitle(bindingSet.getValue("title").stringValue());
				if(bindingSet.hasBinding("uri"))
					res.setURL(bindingSet.getValue("uri").stringValue());
				
				
				if(bindingSet.hasBinding("pVersion"))
					res.setPreviousVersion(bindingSet.getValue("pVersion").stringValue());
				
				if(bindingSet.hasBinding("vNumber"))
					res.setPreviousVersion(bindingSet.getValue("vNumber").stringValue());
				
				Value  tvalue = bindingSet.getValue("time");
				double timestamp = Double.parseDouble(tvalue.stringValue());
				
				res.setTimeStamp(timestamp);
				long time = getLong(timestamp);
				Date date = new Date(time);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String fulldate = sdf.format(date);
				res.setDate(fulldate);
				allResources.add(res);
			   
			   
		}
		result.close();
		con.repDisconnect();
		
		//sort(allResources);
		return allResources;
	}
	
	
	
	public Vector<Resources> getOtherUsersDocResources(String ourSpacesUserAccount,  int offset,int limit) throws ServletException, IOException, OpenRDFException
	{
		// SPARQL query to retrieve resource ID of resources
		StringBuffer qry = new StringBuffer(1024);
		//qry.append("SELECT ?y WHERE { ?y <"+OpmResource.depositedBy.toString()+"> <"+ourSpacesUserAccount+"> . ?x <"+OurSpacesVRE.timestamp.toString()+"> ?time } ORDER BY DESC(?time) ");
	
		//qry.append("SELECT ?y WHERE { ?y <"+OpmResource.depositedBy.toString()+"> <"+ourSpacesUserAccount+">  }");

		
		qry.append("SELECT ?y  ?time ?title ?uri");
		qry.append("WHERE {{ ?y <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.policygrid.org/provenance-generic.owl#Paper> } " +
				"			union {?y <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.policygrid.org/provenance-generic.owl#Report> } "+
				"			union {?y <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.policygrid.org/provenance-generic.owl#Documentation> } "+
				"			union {?y <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.policygrid.org/provenance-generic.owl#Presentation> } . ");
		qry.append("        FILTER   isURI(?y) . ");		
		qry.append("        FILTER   NOT EXISTS{ ?y <"+ProvenanceGeneric.depositedBy.toString()+"> <"+ourSpacesUserAccount+">} . ");		
		qry.append("        ?y <"+OurSpacesVRE.timestamp.toString()+"> ?time . ");
		qry.append("        FILTER NOT EXISTS { ?any <"+ProvenanceGeneric.previousVersion.toString()+"> ?y } ");
		qry.append("OPTIONAL { ?y <"+ProvenanceGeneric.previousVersion.toString()+"> ?pVersion .  }");
		qry.append("OPTIONAL { ?y <"+ProvenanceGeneric.versionNumber.toString()+"> ?vNumber .  }");
	    qry.append("OPTIONAL { ");
	    qry.append("   ?y <"+ProvenanceGeneric.title.toString()+"> ?title . ");
	    qry.append("   ?y  <"+ProvenanceGeneric.hasURI.toString()+"> ?uri . ");
	    qry.append(" } ");
	    qry.append("} ORDER BY DESC(?time)");
	    qry.append("		LIMIT  " + limit + " ");
	    qry.append("		OFFSET  " + offset + " ");
		

	    
	    
	    
	    
		String query = qry.toString();
		//common.Utility.log.debug("Get res by type "+query);
		
		// Connection
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		Vector<Resources> allResources = new Vector<Resources>();
		
		
		while (result.hasNext()) 
		{ 
			   BindingSet bindingSet = result.next();
					   
		      String resID = bindingSet.getValue("y").stringValue();
				Resources res = new Resources();
				res.setID(resID);
				if(bindingSet.hasBinding("title"))
					res.setTitle(bindingSet.getValue("title").stringValue());
				if(bindingSet.hasBinding("uri"))
					res.setURL(bindingSet.getValue("uri").stringValue());
				
				
				if(bindingSet.hasBinding("pVersion"))
					res.setPreviousVersion(bindingSet.getValue("pVersion").stringValue());
				
				if(bindingSet.hasBinding("vNumber"))
					res.setPreviousVersion(bindingSet.getValue("vNumber").stringValue());
				
				Value  tvalue = bindingSet.getValue("time");
				double timestamp = Double.parseDouble(tvalue.stringValue());
				
				res.setTimeStamp(timestamp);
				long time = getLong(timestamp);
				Date date = new Date(time);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String fulldate = sdf.format(date);
				res.setDate(fulldate);
				allResources.add(res);
			   
			   
		}
		result.close();
		con.repDisconnect();
		
		//sort(allResources);
		return allResources;
	}
	public Vector<Resources> getResourcesByType(String ourSpacesUserAccount, String resType) throws ServletException, IOException, OpenRDFException
	{
		// SPARQL query to retrieve resource ID of resources
		StringBuffer qry = new StringBuffer(1024);
		//qry.append("SELECT ?y WHERE { ?y <"+OpmResource.depositedBy.toString()+"> <"+ourSpacesUserAccount+"> . ?x <"+OurSpacesVRE.timestamp.toString()+"> ?time } ORDER BY DESC(?time) ");
	
		//qry.append("SELECT ?y WHERE { ?y <"+OpmResource.depositedBy.toString()+"> <"+ourSpacesUserAccount+">  }");

		
		qry.append("SELECT ?y  ?time ?title ?uri");
		qry.append("WHERE { ?y <"+ProvenanceGeneric.depositedBy.toString()+"> <"+ourSpacesUserAccount+"> . ");
		qry.append("        ?y <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <"+resType+">  . ");
		qry.append("        FILTER   isURI(?y) . ");		
		qry.append("        ?y <"+OurSpacesVRE.timestamp.toString()+"> ?time . ");
		qry.append("        FILTER NOT EXISTS { ?any <"+ProvenanceGeneric.previousVersion.toString()+"> ?y } ");
		qry.append("OPTIONAL { ?y <"+ProvenanceGeneric.previousVersion.toString()+"> ?pVersion .  }");
		qry.append("OPTIONAL { ?y <"+ProvenanceGeneric.versionNumber.toString()+"> ?vNumber .  }");
	    qry.append("OPTIONAL { ");
	    qry.append("   ?y <"+ProvenanceGeneric.title.toString()+"> ?title . ");
	    qry.append("   ?y  <"+ProvenanceGeneric.hasURI.toString()+"> ?uri . ");
	    qry.append(" } ");
	    qry.append("} ORDER BY ASC(?time)");

		

	    
	    
	    
	    
		String query = qry.toString();
		//common.Utility.log.debug("Get res by type "+query);
		
		// Connection
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		Vector<Resources> allResources = new Vector<Resources>();
		
		
		while (result.hasNext()) 
		{ 
			   BindingSet bindingSet = result.next();
					   
		      String resID = bindingSet.getValue("y").stringValue();
				Resources res = new Resources();
				res.setID(resID);
				if(bindingSet.hasBinding("title"))
					res.setTitle(bindingSet.getValue("title").stringValue());
				if(bindingSet.hasBinding("uri"))
					res.setURL(bindingSet.getValue("uri").stringValue());
				
				
				if(bindingSet.hasBinding("pVersion"))
					res.setPreviousVersion(bindingSet.getValue("pVersion").stringValue());
				
				if(bindingSet.hasBinding("vNumber"))
					res.setPreviousVersion(bindingSet.getValue("vNumber").stringValue());
				
				Value  tvalue = bindingSet.getValue("time");
				double timestamp = Double.parseDouble(tvalue.stringValue());
				
				res.setTimeStamp(timestamp);
				long time = getLong(timestamp);
				Date date = new Date(time);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String fulldate = sdf.format(date);
				res.setDate(fulldate);
				allResources.add(res);
			   
			   
		}
		result.close();
		con.repDisconnect();
		
		//sort(allResources);
		return allResources;
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
	 * Returns the author(s) of a particular Resource based on the resource ID provied.  Authors
	 * are returned in an ArrayList of Strings.
	 * 
	 * @param resourceID
	 * @return ArrayList of Strings of Authors
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public ArrayList<String> getResourceAuthor(String resourceID) throws ServletException, IOException, OpenRDFException
	{
		// SPARQL query to return authors
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select ?y where { <"+resourceID+"> <"+ProvenanceGeneric.hasAuthor.toString()+"> ?y } ");

		ArrayList<String> authorList = new ArrayList<String>();
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		
		// Authors for this resource are added to the list
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value1 = bindingSet.getValue("y");
			   String author = value1.stringValue();
			   authorList.add(author);
		}

		result.close();
		con.repDisconnect();
		return authorList;
	}
	
	public void addPropertyValue(String subject, String property, String newvalue) throws RepositoryException, MalformedQueryException, QueryEvaluationException
	{
				
		con.repConnect();
		
		ValueFactory f = con.getRepConnection().getRepository().getValueFactory();
			
		
		   
		   String sub = subject;
		   String pre = property;
		   String obj = newvalue;
	
		
		   org.openrdf.model.URI usub = f.createURI(sub);
		   org.openrdf.model.URI upre = f.createURI(pre);


		   org.openrdf.model.URI uobj = null;
		   org.openrdf.model.Literal lobj = null;

		   if (obj.startsWith("http://")) {
		   	uobj = f.createURI(obj);
		   } else {
		       lobj = f.createLiteral(obj);
		   }
		   
		   if (obj.startsWith("http://")) {
		   	 con.getRepConnection().add(usub, upre, uobj);
		   	} else {
		   	 con.getRepConnection().add(usub, upre, lobj);	
		   	}			 
		}
		
	
	public void replacePropertyValue(String subject, String property, String newvalue) throws RepositoryException, MalformedQueryException, QueryEvaluationException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select ?v where { <"+subject+"> <"+property+"> ?v}");

		String query = qry.toString();
		
		con.repConnect();
		
		ValueFactory f = con.getRepConnection().getRepository().getValueFactory();
			
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		   
		   String sub = subject;
		   String pre = property;
		   String obj = newvalue;
		   
		if (result.hasNext()) {   
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();	 
			   Value value = bindingSet.getValue("v");
			   String v = value.stringValue();
			   
			   String su = subject;
			   String pr = property;
			   String ob = v;
			 
			   

			   org.openrdf.model.URI usu = f.createURI(su);
			   org.openrdf.model.URI upr = f.createURI(pr);


			   org.openrdf.model.URI uob = null;
			   org.openrdf.model.Literal lob = null;

			   if (ob.startsWith("http://")) {
			   	uob = f.createURI(ob);
			   } else {
			       lob = f.createLiteral(ob);
			   }


			   org.openrdf.model.URI usub = f.createURI(sub);
			   org.openrdf.model.URI upre = f.createURI(pre);


			   org.openrdf.model.URI uobj = null;
			   org.openrdf.model.Literal lobj = null;

			   if (obj.startsWith("http://")) {
			   	uobj = f.createURI(obj);
			   } else {
			       lobj = f.createLiteral(obj);
			   }


			   if (ob.startsWith("http://")) {
			    con.getRepConnection().remove(usu, upr, uob);
			   } else {
			    con.getRepConnection().remove(usu, upr, lob);	
			   }

			   if (obj.startsWith("http://")) {
			   	 con.getRepConnection().add(usub, upre, uobj);
			   	} else {
			   	 con.getRepConnection().add(usub, upre, lobj);	
			   	}			   
			   
		}
		result.close();
		} else {
		
		   org.openrdf.model.URI usub = f.createURI(sub);
		   org.openrdf.model.URI upre = f.createURI(pre);


		   org.openrdf.model.URI uobj = null;
		   org.openrdf.model.Literal lobj = null;

		   if (obj.startsWith("http://")) {
		   	uobj = f.createURI(obj);
		   } else {
		       lobj = f.createLiteral(obj);
		   }
		   
		   if (obj.startsWith("http://")) {
		   	 con.getRepConnection().add(usub, upre, uobj);
		   	} else {
		   	 con.getRepConnection().add(usub, upre, lobj);	
		   	}			 
		}
		
	}
	
	public void replacePropertyValue(String subject, String property, String oldvalue, String newvalue) throws RepositoryException, MalformedQueryException, QueryEvaluationException
	{
		
		con.repConnect();
		
		ValueFactory f = con.getRepConnection().getRepository().getValueFactory();
		
		
			   String su = subject;
			   String pr = property;
			   String ob = oldvalue;
			 
			   String sub = subject;
			   String pre = property;
			   String obj = newvalue;
			   

			   org.openrdf.model.URI usu = f.createURI(su);
			   org.openrdf.model.URI upr = f.createURI(pr);


			   org.openrdf.model.URI uob = null;
			   org.openrdf.model.Literal lob = null;

			   if (ob.startsWith("http://")) {
			   	uob = f.createURI(ob);
			   } else {
			       lob = f.createLiteral(ob);
			   }


			   org.openrdf.model.URI usub = f.createURI(sub);
			   org.openrdf.model.URI upre = f.createURI(pre);


			   org.openrdf.model.URI uobj = null;
			   org.openrdf.model.Literal lobj = null;

			   if (obj.startsWith("http://")) {
			   	uobj = f.createURI(obj);
			   } else {
			       lobj = f.createLiteral(obj);
			   }


			   if (ob.startsWith("http://")) {
			    con.getRepConnection().remove(usu, upr, uob);
			   } else {
			    con.getRepConnection().remove(usu, upr, lob);	
			   }

			   if (obj.startsWith("http://")) {
			   	 con.getRepConnection().add(usub, upre, uobj);
			   	} else {
			   	 con.getRepConnection().add(usub, upre, lobj);	
			   	}			   
			   
		
	}
	
	public void deleteProperty(String subject, String property, String value) throws RepositoryException, MalformedQueryException, QueryEvaluationException
	{
		
		con.repConnect();
		
		ValueFactory f = con.getRepConnection().getRepository().getValueFactory();
		
		
			   String su = subject;
			   String pr = property;
			   String ob = value;
			 
			  
			   org.openrdf.model.URI usu = f.createURI(su);
			   org.openrdf.model.URI upr = f.createURI(pr);


			   org.openrdf.model.URI uob = null;
			   org.openrdf.model.Literal lob = null;

			   if (ob.startsWith("http://")) {
			   	uob = f.createURI(ob);
			   } else {
			       lob = f.createLiteral(ob);
			   }

			   if (ob.startsWith("http://")) {
			    con.getRepConnection().remove(usu, upr, uob);
			   } else {
			    con.getRepConnection().remove(usu, upr, lob);	
			   }
		
	}
	
	
	public void deleteActivity(String resourceID) throws  InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		con.connect();
		String qry = "DELETE FROM activities WHERE endActionID1=?";
		PreparedStatement pstmt = con.getCon().prepareStatement(qry);
		pstmt.setString(1, resourceID);
		int  rs = pstmt.executeUpdate();
		pstmt.close();
		con.disconnect();
	}

	public void deleteTags(String resourceID) throws  InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		con.connect();
		String qry = "DELETE FROM tags WHERE resource=?";
		PreparedStatement pstmt = con.getCon().prepareStatement(qry);
		pstmt.setString(1, resourceID);
		int  rs = pstmt.executeUpdate();
		pstmt.close();
		con.disconnect();
	}
	public void deleteResource(String resourceID, boolean addBNode) throws RepositoryException, MalformedQueryException, QueryEvaluationException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select ?p ?o where { <"+resourceID+"> ?p ?o}");

		String query = qry.toString();
		
		con.repConnect();
		
		ValueFactory f = con.getRepConnection().getRepository().getValueFactory();
			
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		org.openrdf.model.BNode b = f.createBNode();
		// Authors for this resource are added to the list
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();	 
			   Value value = bindingSet.getValue("p");
			   String p = value.stringValue();
			   
			   value = bindingSet.getValue("o");
			   String o = value.stringValue();
			   
			   
			   org.openrdf.model.URI usu = f.createURI(resourceID);
			   org.openrdf.model.URI upr = f.createURI(p);
			   
			   org.openrdf.model.URI uob = null;
			   org.openrdf.model.Literal lob = null;

			   if ((o.startsWith("http://")) && (!o.startsWith("http://www.ourspaces.net/file/"))) {
			   	uob = f.createURI(o);
			   } else {
			       lob = f.createLiteral(o);
			   }

			   common.Utility.log.debug(usu);
			   common.Utility.log.debug(upr);
			   common.Utility.log.debug(uob);
			   common.Utility.log.debug(lob);

			   if (o.startsWith("http://")) {
			    con.getRepConnection().remove(usu, upr, uob);
			   } else {
			    con.getRepConnection().remove(usu, upr, lob);	
			   }
			   //Add the connections to the blankNode.
			   if(addBNode){
				   if (o.startsWith("http://")) {
					    con.getRepConnection().add(b, upr, uob);
					   } else {
					    con.getRepConnection().add(b, upr, lob);	
					  }
			   }
		}
		//Do the same thing for the reverse directions
		if(addBNode){
			qry = new StringBuffer(1024);
			qry.append("select ?p ?o where { ?o ?p <"+resourceID+"> }");
			query = qry.toString();
			con.repConnect();
				
			output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
			result = output.evaluate();
			// Authors for this resource are added to the list
			while (result.hasNext()) 
			{
				   BindingSet bindingSet = result.next();	 
				   Value value = bindingSet.getValue("p");
				   String p = value.stringValue();				   
				   value = bindingSet.getValue("o");
				   String o = value.stringValue();
				   org.openrdf.model.URI usu = f.createURI(resourceID);
				   org.openrdf.model.URI upr = f.createURI(p);
				   org.openrdf.model.URI uob = f.createURI(o);				   
				   common.Utility.log.debug(usu);
				   common.Utility.log.debug(upr);
				   common.Utility.log.debug(uob);
				   con.getRepConnection().remove( uob, upr, usu);
				   con.getRepConnection().add(uob, upr , b);
			}
		}
		result.close();
		con.repDisconnect();
		try {
			deleteActivity(resourceID);
			deleteTags(resourceID);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Vector<String> getResourcesPontingTo(String resourceID) throws RepositoryException, MalformedQueryException, QueryEvaluationException
	{
		Vector<String> list = new Vector<String>();
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?x WHERE { ");
		qry.append("?x ?y <");
		qry.append(resourceID);
		qry.append("> ");
		qry.append(" } ");
		
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		// Authors for this resource are added to the list
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("x");
			   list.add(value.stringValue());
			   
		}
		result.close();
		con.repDisconnect();
		return list;
	}
	
	public double getResourceTimestamp(String resourceID) throws RepositoryException, MalformedQueryException, QueryEvaluationException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?time WHERE { ");
		qry.append("<");
		qry.append(resourceID);
		qry.append("> ");
		qry.append("<"+OurSpacesVRE.timestamp.toString()+"> ?time");
		qry.append(" } ");
	
		double timestamp = 0;
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		// Authors for this resource are added to the list
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("time");
			   timestamp = Double.parseDouble(value.stringValue());
			   
		}
		result.close();
		con.repDisconnect();
		return timestamp;
	}
	
	/**
	 * Returns the title or name of a particular resource based on its unique resource ID.
	 * 
	 * @param resourceID
	 * @return String containing Resource Title
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public String getResourceTitle(String resourceID) throws ServletException, IOException, OpenRDFException 
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?title ?version WHERE { ");
		qry.append("<"+resourceID+">  <"+ProvenanceGeneric.title.toString()+"> ?title. ");
	    qry.append("OPTIONAL {<"+resourceID+"> <http://www.policygrid.org/provenance-generic.owl#versionNumber>  ?version} ");
		qry.append(" } ");
	
		String title = "";
		
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();

		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   title = bindingSet.getValue("title").stringValue();
			   if(bindingSet.hasBinding("version")){
					title += " (v"+bindingSet.getValue("version").stringValue()+")";					
			   }

		}
		result.close();
		con.repDisconnect();
		return title;
	}
	
	/**
	 * Returns the title or name of a particular resource based on its unique resource ID.
	 * 
	 * @param resourceID
	 * @return String containing Resource Title
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public String getResourceVersion(String resourceID) throws ServletException, IOException, OpenRDFException 
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?version WHERE { ");
		qry.append("<");
		qry.append(resourceID);
		qry.append("> ");
		qry.append("<"+ProvenanceGeneric.versionNumber.toString()+"> ?version");
		qry.append(" } ");
	
		String title = "";
		
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();

		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   

			   Value value = bindingSet.getValue("version");
			   Literal value2 =  (Literal) value;

			   title = value2.getLabel();

		}
		result.close();
		con.repDisconnect();
		return title;
	}
	
	
	/**
	 * Returns the label of a particular resource based on its unique resource ID.
	 * 
	 * @param resourceID
	 * @return String containing Resource Title
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public String getResourceLabel(String resourceID) throws ServletException, IOException, OpenRDFException 
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?label WHERE { ");
		qry.append("<");
		qry.append(resourceID);
		qry.append("> ");
		qry.append("<"+RDFS.label.toString()+"> ?label");
		qry.append(" } ");
	
		String title = "";
		
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();

		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   

			   Value value = bindingSet.getValue("label");
			   Literal value2 =  (Literal) value;

			   title = value2.getLabel();

		}
		result.close();
		con.repDisconnect();
		return title;
	}
	
	/**
	 * Returns the full URL of the resource held in the repository.  Resources are
	 * identified by their Resource ID.
	 * 
	 * @param resourceID
	 * @return String containing the resource URL
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public String getResourceURL(String resourceID) throws ServletException, IOException, OpenRDFException 
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?url WHERE { ");
		qry.append("<");
		qry.append(resourceID);
		qry.append("> ");
		qry.append("<"+ProvenanceGeneric.hasURI.toString()+"> ?url");
		qry.append(" } ");
	
		String url = "";
		
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext())
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("url");
			   url = value.stringValue();
		}
		result.close();
		con.repDisconnect();
		return url;
	}
	
	/**
	 * Returns the depositor of a resource based on the ourSpaces User RDF ID.
	 * 
	 * @param resourceID
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public String getResourceDepositor(String resourceID) throws ServletException, IOException, OpenRDFException 
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?restriction WHERE { ");
		qry.append("<");
		qry.append(resourceID);
		qry.append("> ");
		qry.append("<"+ProvenanceGeneric.depositedBy.toString()+"> ?restriction");
		qry.append(" } ");
	
		String restriction = "";
		
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext())
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("restriction");
			   restriction = value.stringValue();
		}
		result.close();
		con.repDisconnect();
		return restriction;
	}
	
	public String getResourceType(String resourceID) throws ServletException, IOException, OpenRDFException 
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?type WHERE { ");
		qry.append("<");
		qry.append(resourceID);
		qry.append("> ");
		qry.append("<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?type");
		qry.append(" } ");
	
		String types = "";
	
		
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext())
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("type");
			   types = value.stringValue();
			  
		}
		result.close();
		con.repDisconnect();
		return types;
	}
	
	/**
	 * 
	 * @param resourceID Id of the resource
	 * @param regex Regular expression to filter the properties
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public List<Properties> getResourceProperties(String resourceID, String regex) throws ServletException, IOException, OpenRDFException 
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?p ?o WHERE { ");
		qry.append("<");
		qry.append(resourceID);
		qry.append("> ");
		qry.append("?p ?o. filter(regex(str(?p), \""+regex+"\")) ");
		qry.append(" } ");		
		String query = qry.toString();		
		con.repConnect();		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		List<Properties> prop = new ArrayList<Properties>();
		while (result.hasNext())
		{
			   BindingSet bindingSet = result.next();
			   Properties pr = new Properties();
			   String name = bindingSet.getValue("p").stringValue();
			   String value = bindingSet.getValue("o").stringValue();
			   pr.setProperty(name);
			   pr.setValue(value);
			   prop.add(pr);
			  
		}
		result.close();
		con.repDisconnect();
		return prop;
	}

	/**
	 * Properties, where given resourse is the object
	 * @param resourceID Id of the resource
	 * @param regex Regular expression to filter the properties
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public List<Properties> getResourcePropertiesObject(String resourceID, String regex) throws ServletException, IOException, OpenRDFException 
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?o ?p WHERE { ");
		qry.append(" ?o ?p<");
		qry.append(resourceID);
		qry.append("> ");
		qry.append(". filter(regex(str(?p), \""+regex+"\")) ");
		qry.append(" } ");		
		String query = qry.toString();		
		con.repConnect();		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		List<Properties> prop = new ArrayList<Properties>();
		while (result.hasNext())
		{
			   BindingSet bindingSet = result.next();
			   Properties pr = new Properties();
			   String name = bindingSet.getValue("p").stringValue();
			   String value = bindingSet.getValue("o").stringValue();
			   pr.setProperty(name);
			   pr.setValue(value);
			   prop.add(pr);
			  
		}
		result.close();
		con.repDisconnect();
		return prop;
	}
	public String getSingleResourceType(String resourceID) throws ServletException, IOException, OpenRDFException 
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?type WHERE { ");
		qry.append("<");
		qry.append(resourceID);
		qry.append("> ");
		qry.append("<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?type");
		qry.append(" } ");
	
		String types = "";
		String restriction = "";
		
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext())
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("type");
			   restriction = value.stringValue();
			   if(!restriction.equals(""+ProvenanceGeneric.BASE_URI+"#Resource"))
			   {
				   String[] fields = restriction.split("#");
				   String property = (String) fields[1];
				   types=property;
			   }
		}
		result.close();
		con.repDisconnect();
		return types;
	}
	
	/**
	 * Returns the resource ID of the project that a particular resource belongs to.  The 
	 * resource is identified by it's unique Resource ID.
	 * 
	 * @param resourceID
	 * @return String containing the project URI
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public String getProject(String resourceID) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?project WHERE { ");
		qry.append("<");
		qry.append(resourceID);
		qry.append("> ");
		qry.append("<"+ProvenanceGeneric.BASE_URI+"#producedInProject> ?project");
		qry.append(" } ");

		
		String project = "";
	
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("project");
			   project = value.stringValue();
		}
		result.close();
		con.repDisconnect();
		return project;
	}
	
	/**
	 * returns the geo information of a resource based on a feature id. This is used in browse.jsp
	 * @param featureID
	 * @return
	 */
	public ArrayList<common.Properties> getGeoInformation(String featureID) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?name ?lat ?lon ");
	    qry.append("WHERE ");
		qry.append("{ ");
		qry.append("<"+featureID+"> <http://www.geonames.org/ontology#name> ?name. ");
		qry.append("<"+featureID+"> <http://www.w3.org/2003/01/geo/wgs84_pos#lat> ?lat. ");
		qry.append("<"+featureID+"> <http://www.w3.org/2003/01/geo/wgs84_pos#long> ?lon ");
		qry.append("} ");
		
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		ArrayList<Properties> prop = new ArrayList<Properties>();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Properties pr1 = new Properties();
			   String name = bindingSet.getValue("name").stringValue();
			   pr1.setValue(name);
			   pr1.setProperty("http://www.geonames.org/ontology#name");
			   prop.add(pr1);
			   
			   Properties pr2 = new Properties();
			   String lat = bindingSet.getValue("lat").stringValue();
			   pr2.setValue(lat);
			   pr2.setProperty("http://www.w3.org/2003/01/geo/wgs84_pos#lat");
			   prop.add(pr2);
			   
			   Properties pr3 = new Properties();
			   String lon = bindingSet.getValue("lon").stringValue();
			   pr3.setValue(lon);
			   pr3.setProperty("http://www.w3.org/2003/01/geo/wgs84_pos#long");
			   prop.add(pr3);
			   
		}
		result.close();
		con.repDisconnect();
		return prop;
	}
	
	
	public List<common.Properties> getScientificDiscipline(String discInfoID) throws ServletException, IOException, OpenRDFException
	{
		List<Properties> l = getResourceProperties(discInfoID, "http://www.policygrid.org/academic-disciplines#hasAcademicDiscipline");
		List<Properties> l2 = getResourceProperties(discInfoID, "http://www.policygrid.org/academic-disciplines#hasDisciplineTag");
		l.addAll(l2);
		return l;
	}
	
	/**
	 * Returns the resource ID of the project that a particular resource belongs to.  The 
	 * resource is identified by it's unique Resource ID.
	 * 
	 * @param resourceID
	 * @return String containing the project URI
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public Vector<Resources> getAllGeoResurces() throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?timestamp ?uri ?ar ?title ?gp ?lat ?lon ");
	    qry.append("WHERE ");
		qry.append("{ ");
		qry.append("?f <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.geonames.org/ontology#Feature> . ");
		qry.append("?f <http://www.w3.org/2003/01/geo/wgs84_pos#long> ?lon . ");
		qry.append("?f <http://www.w3.org/2003/01/geo/wgs84_pos#lat> ?lat . ");
		qry.append("?ar ?gp ?f . ");
		qry.append("?ar <"+ProvenanceGeneric.BASE_URI+"#title> ?title . ");		
		qry.append("?ar <"+ProvenanceGeneric.BASE_URI+"#hasURI> ?uri . ");
		qry.append("?ar <http://www.policygrid.org/ourspacesVRE.owl#timestamp> ?timestamp ");
		qry.append("} ");

	
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		Vector<Resources> allResources = new Vector<Resources>();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();	   
			    String resID = bindingSet.getValue("ar").stringValue();
				Resources res = new Resources();
				res.setID(resID);
				res.setTitle(bindingSet.getValue("title").stringValue());
				res.setURL(bindingSet.getValue("uri").stringValue());
				//res.setRestriction(getResourceRestriction(resID));
				res.setTimeStamp(Long.parseLong(bindingSet.getValue("timestamp").stringValue()));
				long time = Long.parseLong(bindingSet.getValue("timestamp").stringValue());
				Date date = new Date(time);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String fulldate = sdf.format(date);
				res.setDate(fulldate);
				res.setLat(bindingSet.getValue("lat").stringValue());
				res.setLon(bindingSet.getValue("lon").stringValue());		
				
				String gp = bindingSet.getValue("gp").stringValue();
				gp = gp.substring(gp.indexOf("#")+1);
				res.setGeop(gp);
				allResources.add(res); 
			   
			   
		}
		result.close();
		con.repDisconnect();
		return allResources;
	}
	
	/**
	 * Returns all resources that are of a specific type.. i.e papers, data objects, etc.
	 * 
	 * @param objectName
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public Vector<Resources> getLatestObjects(int limit) throws ServletException, IOException, OpenRDFException
	{
		
		/*
		 * 
		 * qry.append("SELECT ?y  ?time ?title ?uri ");
		qry.append("WHERE { ?y <"+OpmResource.depositedBy.toString()+"> <"+ourSpacesUserAccount+"> . ");
		qry.append("        ?y <"+OurSpacesVRE.timestamp.toString()+"> ?time . ");
	    qry.append("OPTIONAL { ");
	    qry.append("   ?y <"+OpmResource.title.toString()+"> ?title . ");
	    qry.append("   ?y  <"+OpmResource.hasURI.toString()+"> ?uri . ");
	    qry.append(" } ");
	    qry.append("} ORDER BY ASC(?time)");

		
		
		String query = qry.toString();
		common.Utility.log.debug(query);
		
		// Connection
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		Vector allResources = new Vector();
		
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
					   
		      String resID = bindingSet.getValue("y").stringValue();
				Resources res = new Resources();
				res.setID(resID);
				res.setTitle(bindingSet.getValue("title").stringValue());
				res.setURL(bindingSet.getValue("uri").stringValue());
				
				
				Value  tvalue = bindingSet.getValue("time");
				double timestamp = Double.parseDouble(tvalue.stringValue());
				
				res.setTimeStamp(timestamp);
				long time = getLong(timestamp);
				Date date = new Date(time);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String fulldate = sdf.format(date);
				res.setDate(fulldate);
				allResources.add(res);
			   
			   
		}
		result.close();
		con.repDisconnect();
		 */
		
		
		
		
		StringBuffer qry = new StringBuffer(1024);
		
		qry.append("SELECT ?y  ?time ?title ?uri ");
		qry.append("WHERE { ");
		qry.append("   ?y <"+ProvenanceGeneric.depositedBy.toString()+"> ?x . ");
		qry.append("   ?y <"+OurSpacesVRE.timestamp.toString()+"> ?time . ");
	    qry.append("   ?y <"+ProvenanceGeneric.title.toString()+"> ?title . ");
	    qry.append(" FILTER isURI(?y). ");
		qry.append(" FILTER NOT EXISTS { ?any <"+ProvenanceGeneric.previousVersion.toString()+"> ?y } ");
	    qry.append(" OPTIONAL { ");
	    qry.append("   ?y  <"+ProvenanceGeneric.hasURI.toString()+"> ?uri . ");
	    qry.append(" } ");
	    qry.append(" } ORDER BY DESC(?time) LIMIT "+limit+" ");
			
	
		String query = qry.toString();
		
		common.Utility.log.debug("Latest res");
		common.Utility.log.debug(query);
		
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		

		Vector<Resources> allResources = new Vector<Resources>();
		
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();				   
		       String resID = bindingSet.getValue("y").stringValue();
				Resources res = new Resources();
				res.setID(resID);
				res.setTitle(bindingSet.getValue("title").stringValue());
				if(bindingSet.getValue("uri") != null)
					res.setURL(bindingSet.getValue("uri").stringValue());
				
				
				Value  tvalue = bindingSet.getValue("time");
				double timestamp = Double.parseDouble(tvalue.stringValue());
				
				res.setTimeStamp(timestamp);
				long time = getLong(timestamp);
				Date date = new Date(time);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String fulldate = sdf.format(date);
				res.setDate(fulldate);
				allResources.add(res);
			   
			   
		}
		sort(allResources);
		return allResources;
	}
	
	
	public Vector<Resources> getCertainObject(String objectName) throws ServletException, IOException, OpenRDFException, SAXException
	{
		return getResources(objectName, null, false);
	}
	
	/**
	 * Returns all resources that are of a specific type, and optionally from its sub-types.
	 * Optional: only retrieve resources deposited by a particular user by specifying ourSpaces account.
	 * 
	 * @param resourceType full URI of the type of resources retrieved
 	 * @param ourSpacesAccountID the OurSpacesAccount ID of the user that deposited those resources (optional parameter. if null, every resources will be retrieved)
 	 * @param retrieveSubTypes indicate whether or no to retrieve the subclasses' resources 
	 * @return List of resources of a specific type
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public Vector<Resources> getResources(String resourceType, String ourSpacesAccountID, boolean retrieveSubTypes) throws ServletException, IOException, OpenRDFException, SAXException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select ?x where { ");

		if (Utility.isNotNull(ourSpacesAccountID)) {
			qry.append("?x <" + ProvenanceGeneric.depositedBy.toString() + "> <" + ourSpacesAccountID + "> .");
		}

		qry.append("?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?type .");

		qry.append(" FILTER ( str(?type) = \"" + resourceType + "\"  \n ");	
		
		if (retrieveSubTypes) {
			Vector<String> objectSubtypes = new OntologyHandler().getSubclassListFull("general", resourceType);
			for (String subclass : objectSubtypes) {
				qry.append("|| str(?type) = \""+ subclass+"\"  \n");
			}
		}
		qry.append(" ). "); // end of Filter
		qry.append(" FILTER   isURI(?x) ." +//?x is not a blank node, so x is not deleted
				"} "); // end of WHERE
		//TODO: Order resources by date deposited?

		Vector<Resources> allResources = processGetCertainObjectQuery(qry);
		return allResources;
	}

	/**
	 * Returns all resources that are of a specific type, and optionally from its sub-types.
	 * Optional: only retrieve resources deposited by a particular user by specifying ourSpaces account.
	 * 
	 * @param resourceType full URI of the type of resources retrieved
 	 * @param ourSpacesAccountID the OurSpacesAccount ID of the user that deposited those resources (optional parameter. if null, every resources will be retrieved)
 	 * @param retrieveSubTypes indicate whether or no to retrieve the subclasses' resources 
	 * @return List of resources of a specific type
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	
	private Vector<Resources> processGetCertainObjectQuery(StringBuffer qry)
			throws RepositoryException, MalformedQueryException,
			QueryEvaluationException, ServletException, IOException,
			OpenRDFException {
		ArrayList<String> list = new ArrayList<String>();
		Vector<Resources> allResources = new Vector<Resources>();
		
		String object = "";
	
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("x");
			   if (value != null) {
				   object = value.stringValue();
				   list.add(object);
			}
		}
		result.close();
		con.repDisconnect();
		for(int i = 0; i < list.size(); i++)
		{
			String resID = (String)list.get(i);
			Resources res = new Resources();
			res.setID(resID);
			res.setTitle(getResourceTitle(resID));
			res.setURL(getResourceURL(resID));
			//res.setRestriction(getResourceRestriction(resID));
			res.setTimeStamp(getResourceTimestamp(resID));
			res.setDepositor(getResourceDepositor(resID));
			long time = getLong(getResourceTimestamp(resID));
			Date date = new Date(time);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String fulldate = sdf.format(date);
			res.setDate(fulldate);
			allResources.add(res);
		}
		sort(allResources);
		return allResources;
	}
	
	/**
	 * Return string representation of the date of given timestamp.
	 * @param timestamp
	 * @return
	 */
	public String getFormattedDate(double timestamp){
		long time = getLong(timestamp);
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date);
	}
	
	public Vector<Resources> getObjectsByType(String type) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select ?x where { ");
		qry.append("?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>");
		qry.append("<"+ProvenanceGeneric.BASE_URI+"#" + type + "> } ");
		
		Vector<Resources> allResources = processGetCertainObjectQuery(qry);
		return allResources;
	}

	
	
	
	/**
	 * Returns a certain class of resource belonging to a particular user,
	 * i.e all Papers or Reports, etc.
	 */
	public Vector<Resources> getCertainObjectForUser(String resourceID, String objectName) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select ?x where { ");
		qry.append("?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <"+ProvenanceGeneric.BASE_URI+"#" + objectName + "> . ");
		qry.append("?x <"+ProvenanceGeneric.depositedBy.toString()+"> <"+resourceID+"> . ");
		qry.append("FILTER   isURI(?x) . ");		
		qry.append(" } ");
		
		Vector<Resources> allResources = processGetCertainObjectQuery(qry);
		return allResources;
	}
	
	public Vector<Resources> sortingResources(Vector<Resources> resources)
	{
		sort(resources);
		return resources;
	}
	
	/**
	 * list the instances of academic disciplines
	 * @return list of academic disciplines
	 */
	public Collection<OntResource> getAcademicDisciplines(){
		// Read ontology model:
		OntModel disciplineOnto = getDisciplinesModel();

		Collection<OntResource> academicDisciplinesList = new ArrayList<OntResource>();
		
		ExtendedIterator<? extends OntResource> instances = disciplineOnto.getOntClass(AcademicDiscipline.AcademicDiscipline.toString()).listInstances();
		while (instances.hasNext()) {
			OntResource ontResource = (OntResource) instances.next();
			academicDisciplinesList.add(ontResource);
		}
		
		return academicDisciplinesList;
	}

	private OntModel getDisciplinesModel(){
		OntModel ont;
		try {
			ont = new OntologyHandler().getOntology("discipline");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ont;
	}
	
	/**
	 * Get the proper label of an academic discipline from it's URI
	 * @param disciplineURI
	 * @return
	 */
	public String getDisciplineLabel(String disciplineURI){
		OntModel disciplinesModel = getDisciplinesModel();
		return disciplinesModel.getIndividual(disciplineURI).getLabel(null);
	}
	
	/**
	 * List the main disciplines contained in the ontology
	 * @return
	 */
	public Collection<OntResource> getMainDisciplines(){
		Collection<OntResource> mainDisciplines = new ArrayList<OntResource>();
		
		Collection<OntResource> academicDisciplines = getAcademicDisciplines();
		for (OntResource ontResource : academicDisciplines) {
			Property broaderProp = getDisciplinesModel().getProperty("http://www.w3.org/2004/02/skos/core#broader");
			// add the discipline if it doesn't have a broader one:
			if (!ontResource.hasProperty(broaderProp)) {
				mainDisciplines.add(ontResource);
			}
		}
		
//		OntModel disciplinesModel = getDisciplinesModel();
//		QueryExecution qexec = QueryExecutionFactory.create(getMainDisciplinesQuery() , disciplinesModel);
//		ResultSet resultSet = qexec.execSelect();
//		while(resultSet.hasNext()) {
//			QuerySolution row = (QuerySolution)resultSet.next();
//			RDFNode nextDiscipline = row.get("discipline");
//			mainDisciplines.add(nextDiscipline.toString());
//		}
//		qexec.close();
		
		return mainDisciplines;
	}
	
	/**
	 * list the sub discipline of a main discipline contained in the ontology file
	 * @param mainDiscipline
	 * @return
	 */
	public Collection<OntResource> getSubDisciplines(OntResource mainDiscipline){
		Collection<OntResource> subDisciplines = new ArrayList<OntResource>();
		
		Collection<OntResource> academicDisciplines = getAcademicDisciplines();
		Property broaderProp = getDisciplinesModel().getProperty("http://www.w3.org/2004/02/skos/core#broader");

		for (OntResource ontResource : academicDisciplines) {
			if (ontResource.hasProperty(broaderProp) && ontResource.getPropertyValue(broaderProp).asNode().getURI().equals(mainDiscipline.getURI())) {
				subDisciplines.add(ontResource);
			}
		}

		// or with narrower property: (quicker but need a narrower property)
//		NodeIterator listPropertyValues = mainDiscipline.listPropertyValues(getDisciplinesModel().getProperty("http://www.w3.org/2004/02/skos/core#narrower"));
//		OntModel disciplinesModel = getDisciplinesModel();
//		
//		while (listPropertyValues.hasNext()) {
//			RDFNode rdfNode = (RDFNode) listPropertyValues.next();
//			subDisciplines.add(disciplinesModel.getIndividual(rdfNode.asNode().getURI()));
//		}

		return subDisciplines;
	}

	private String getMainDisciplinesQuery() {
		StringBuffer query = new StringBuffer();
		query.append("SELECT ?discipline ");
		query.append("WHERE {");
		query.append("   ?discipline a <http://www.policygrid.org/AcademicDisciplines#academicDiscipline>.");
		query.append("	 NOT EXISTS{ ");
		query.append("		?x skos:broader ?discipline.");
		query.append("	}");
		query.append("}");
		return query.toString();
	}
	
}