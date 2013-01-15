package org.policygrid.policies;

import java.util.Vector;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

public class RequiredFieldsSession extends PolicySession {

	public RequiredFieldsSession(OntModel policies, Model inferences,
			Model context) {
		super(policies, inferences,context);
		// TODO Auto-generated constructor stub
	}

	public RequiredFieldsSession(PolicySession session) {			
		super(session.policies, session.inferences, session.context);
	}
    
	
	public Vector<String> listFields(String resourceURI, String type, String property){

		Vector<String> properties = new Vector<String>();
	   
		String queryString = 
			"SELECT ?x ?property " +
			"WHERE {" +
			"      ?x <http://www.policygrid.org/provenance-policies#requestAboutResource> <"+ resourceURI +"> . " +
			"      ?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <"+ type +"> ." +
			"      ?x <"+ property +"> ?property . " +
			"      }";

		Query query = QueryFactory.create(queryString);
	
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, inferences);
		ResultSet results = qe.execSelect();
	
		while (results.hasNext()) {
			String prop = "";
			QuerySolution soln = results.next();

			RDFNode n = soln.get("property"); // "x" is a variable in the query

			if (n.isLiteral()) {
				prop = "" + ((Literal) n).getLexicalForm();
			}
			if (n.isResource()) {
				Resource r = (Resource) n;
				if (!r.isAnon()) {
					prop = "" + r.getURI();
				}
			}
			if (!prop.equals(""))
				properties.add(prop);
		}

		qe.close();
		return properties;
	}

	public Vector<String> listFieldTypes(String resourceURI, String property) {
		Vector<String> properties = new Vector<String>();
	   
		String queryString = 
			"SELECT ?x ?class " +
			"WHERE {" +
			"      ?x <http://www.policygrid.org/provenance-policies#requestAboutResource> <"+ resourceURI +"> . " +
			"      ?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.policygrid.org/provenance-policies#InformationRequest> ." +
			"      ?x <http://www.policygrid.org/provenance-policies#requireInstanceOfClass> ?class . " +
			"      ?x <http://www.policygrid.org/provenance-policies#requireProperty> <"+property +"> . " +
			"      }";

		Query query = QueryFactory.create(queryString);
	
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, inferences);
		ResultSet results = qe.execSelect();	
		while (results.hasNext()) {
			String prop = "";
			QuerySolution soln = results.next();
			RDFNode n = soln.get("class"); // "x" is a variable in the query
			if (n.isLiteral()) {
				prop = "" + ((Literal) n).getLexicalForm();
			}
			if (n.isResource()) {
				Resource r = (Resource) n;
				if (!r.isAnon()) {
					prop = "" + r.getURI();
				}
			}
			if (!prop.equals(""))
				properties.add(prop);
		}		
		qe.close();
		return properties;	
	}
	
	public Vector<String> listMandatoryFields(String resourceURI) {
		return listFields(
				resourceURI,
				"http://www.policygrid.org/provenance-policies#InformationRequest",
				"http://www.policygrid.org/provenance-policies#requireProperty");
	}

	public Vector<String> listPreferedVisualisationTypes(String resourceURI) {
		return listFields(
				resourceURI,
				"http://www.policygrid.org/provenance-policies#PreferenceVisualisationRequest",
				"http://www.policygrid.org/provenance-policies#hasPreferedVisualisationType");
	}
	

	public String getActivePolicy(String resourceURI, String field){
		String policy = "";
	   
		String queryString = 
			"SELECT ?pol " +
			"WHERE {" +
			"      ?x <http://www.policygrid.org/provenance-policies#requestAboutResource> <"+ resourceURI +"> . " +
			"      ?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.policygrid.org/provenance-policies#InformationRequest> ." +
			"      ?x <http://www.policygrid.org/provenance-policies#requireProperty> <"+field+"> . " +
		    "      ?x <http://www.policygrid.org/provenance-policies#activePolicy> ?pol . " +
			"      }";

		Query query = QueryFactory.create(queryString);
	
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, inferences);
		ResultSet results = qe.execSelect();
	
		while (results.hasNext()) {
			String prop = "";
			QuerySolution soln = results.next();

			RDFNode n = soln.get("pol"); // "x" is a variable in the query

			if (n.isLiteral()) {
				prop = "" + ((Literal) n).getLexicalForm();
			}
			if (n.isResource()) {
				Resource r = (Resource) n;
				if (!r.isAnon()) {
					prop = "" + r.getURI();
				}
			}
			if (!prop.equals(""))
				policy = prop;
		}
		
		qe.close();
		return policy;
	}
	
	public String getPolicyTitleForRequiredField(String resourceURI, String field){
		String title = "";
	   
		String queryString = 
			"SELECT ?title " +
			"WHERE {" +
			"      ?x <http://www.policygrid.org/provenance-policies#requestAboutResource> <"+ resourceURI +"> . " +
			"      ?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.policygrid.org/provenance-policies#InformationRequest> ." +
			"      ?x <http://www.policygrid.org/provenance-policies#requireProperty> <"+field+"> . " +
		    "      ?x <http://www.policygrid.org/provenance-policies#activePolicy> ?pol . " +
		    "      ?pol <http://www.policygrid.org/provenance-policies#policyTitle> ?title . " +
			"      }";

		Query query = QueryFactory.create(queryString);
	
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, inferences);
		ResultSet results = qe.execSelect();
	
		while (results.hasNext()) {
			String prop = "";
			QuerySolution soln = results.next();

			RDFNode n = soln.get("title"); // "x" is a variable in the query

			if (n.isLiteral()) {
				prop = "" + ((Literal) n).getLexicalForm();
			}
			if (n.isResource()) {
				Resource r = (Resource) n;
				if (!r.isAnon()) {
					prop = "" + r.getURI();
				}
			}
			if (!prop.equals(""))
				title = prop;
		}
		
		qe.close();
		return title;
	}
	
}
