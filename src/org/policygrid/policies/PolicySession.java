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

public class PolicySession {

	public OntModel policies = null;
	public Model inferences = null;
	public Model context = null;


	public PolicySession(OntModel policies, Model inferences, Model context) {
		super();
		this.policies = policies;
		this.inferences = inferences;
		this.context = context;
	}
	
	
	public Model getContext() {
		return context;
	}

	public void addContext(Model context) {
		//We shouldn't set the context, as it is contained in the baseModel
		//Rather we fill it with new triples.
		this.context.add(context);
	}

	public OntModel getPolicies() {
		return policies;
	}
	public void setPolicies(OntModel policies) {
		this.policies = policies;
	}
	public Model getInferences() {
		return inferences;
	}
	public void setInferences(Model inferences) {
		this.inferences = inferences;
	}
	
	
	public boolean hasActionRequests(){
		
		String queryString = 
		"SELECT ?x ?action " +
		"WHERE {" +
		"      ?x <http://www.policygrid.org/provenance-policies#requestAboutResource> ?res . " +
		"      ?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?action . " +
		"      }";

		Query query = QueryFactory.create(queryString);
	
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, inferences);
		ResultSet results = qe.execSelect();
		boolean hasResults = results.hasNext();
		// Important - free up resources used running the query
		qe.close();
  	    return  hasResults;
		
	}
	
	public String listActionRequests(){
			
		String queryString = 
		"SELECT ?x ?action " +
		"WHERE {" +
		"      ?x <http://www.policygrid.org/provenance-policies#requestAboutResource> ?res . " +
		"      ?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?action . " +
		"      }";

		Query query = QueryFactory.create(queryString);
	
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, inferences);
		ResultSet results = qe.execSelect();
	
		// Output query results	
		//ResultSetFormatter.out(System.out, results, query);
		String txt = ResultSetFormatter.asXMLString(results);
		

		// Important - free up resources used running the query
		qe.close();
  	    return  txt;
		
	}
	
	public Vector<String> listPolicyActivations(String event){
		Vector<String> activations = new Vector<String>();
		   
		String queryString = 
		"SELECT ?policy  " +
		"WHERE {" +
		"      ?x a <http://www.policygrid.org/provenance-policies#PolicyActivation> . " +
		"      ?x <http://www.policygrid.org/provenance-policies#activePolicy> ?policy . " +
		"      ?x <http://www.policygrid.org/provenance-policies#basedOnEvent> <"+event+"> . " +
		"      }";

		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, inferences);
		ResultSet results = qe.execSelect();
		
		while (results.hasNext()) {
			String prop = "";
			QuerySolution soln = results.next();

			RDFNode n = soln.get("policy"); // "x" is a variable in the query

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
				activations.add(prop);
		}

		qe.close();
		return activations;		
	}
	
	/**
	 * Gets the event that triggered the policy.
	 * @param policy
	 * @return URI of the event.
	 */
	public String getBasedOnEvent(String policy){			
		String queryString = 
		"SELECT ?event " +
		"WHERE {" +
		"      <"+policy+"> <http://www.policygrid.org/provenance-policies#basedOnEvent> ?event . " +
		"      }";

		Query query = QueryFactory.create(queryString);
		
		// Output query results	
		//ResultSetFormatter.out(System.out, results, query);
		String event = "";

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, inferences);
		ResultSet results = qe.execSelect();
	
		while (results.hasNext()) {
			String prop = "";
			QuerySolution soln = results.next();
			RDFNode n = soln.get("event"); // "x" is a variable in the query
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
				event = prop;
		}		
		qe.close();
		return event;	
	}
	
}
