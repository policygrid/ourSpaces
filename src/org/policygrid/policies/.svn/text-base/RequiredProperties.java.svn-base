package org.policygrid.policies;

import common.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryException;

public class RequiredProperties {
	
	public HashMap resources;
	
	Connections con = new Connections();
	
	public RequiredProperties()
	{
		
	}
	
	/**
	 * Retrieves properties from database based on 
	 * policyID
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public ArrayList<Property> getProperties(String policyID) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		ArrayList<Property> reqProperties = new ArrayList<Property>();
		con.connect();
		Statement st = con.getCon().createStatement();
		
		String sql = "select * from policies where policyID = \""+policyID+"\"";
		
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next())
		{
			Property prop = new Property(rs.getString("reqProperty"), rs.getString("class"));
			reqProperties.add(prop);
		}
		rs.close();
		st.close();
		con.disconnect();
		return reqProperties;
	}
	
	public ArrayList<String> getProjectResourcesByUser(String projectID, String rdfUserID) throws RepositoryException, MalformedQueryException, QueryEvaluationException
	{

		StringBuffer str = new StringBuffer(1024);
		str.append("select ?resources where { ");
		str.append("?resources <http://www.policygrid.org/opm-resource.owl#depositedBy> <" + rdfUserID + "> . ");
		str.append("?resources <http://www.policygrid.org/opm-resource.owl#producedInProject> <" + projectID + "> } ");
		String query = str.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		ArrayList<String> projectResources = new ArrayList<String>();
		
		while(result.hasNext())
		{
			BindingSet bindingSet = result.next();	   
		    String resourceID = bindingSet.getValue("resources").stringValue();
		    projectResources.add(resourceID);
		}
		
		con.repDisconnect();
		
		return projectResources;
	}
	
	public ArrayList<String> getAllPropertiesForResources(String resourceID) throws RepositoryException, MalformedQueryException, QueryEvaluationException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		StringBuffer str = new StringBuffer(1024);
		str.append("select ?property ?value where { ");
		str.append("<"+resourceID+"> ?property ?value } ");
		String query = str.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate(); 
		
		ArrayList<String> givenProperties = new ArrayList<String>();
		ArrayList<String> missingProperties = new ArrayList<String>();
		
		String classType = "";
		
		while(result.hasNext())
		{
			BindingSet bindingSet = result.next();	   
		    String className = bindingSet.getValue("value").stringValue();
		    String property = bindingSet.getValue("property").stringValue();
		    if(property.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"))
		    {
		    	classType = className;
		    	break;
		    }
		}
		
		while(result.hasNext())
		{
			BindingSet bindingSet = result.next();	   
		    String property = bindingSet.getValue("property").stringValue();
		    if(!property.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"))
		    {
		    	givenProperties.add(property);
		    }
		}
		
		ArrayList<Property> dbProperties = getProperties("ukda");
		for(int i = 0; i < dbProperties.size(); i++)
		{
			Property p = dbProperties.get(i);
			if(p.getClassName().equals("http://openprovenance.org/ontology#Artifact") || p.getClassName().equals(classType))
			{
				if(!givenProperties.contains(p.getPropertyName()) && !missingProperties.contains(p.getPropertyName()))
				{
					missingProperties.add(p.getPropertyName());
				}
			}
		}
		
		
		return missingProperties;
	}
	
}
