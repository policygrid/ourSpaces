package org.policygrid.policies;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;

public class NLGStatementsSession extends PolicySession {

	public static final String NLG_RETRACTION = "http://www.policygrid.org/provenance-policies#NLGRetractionRequest";
	public static final String NLG_ASSERTION = "http://www.policygrid.org/provenance-policies#NLGAssertionRequest";
	
	public NLGStatementsSession(OntModel policies, Model inferences,
			Model context) {
		super(policies, inferences, context);
	}

	public NLGStatementsSession(PolicySession session) {			
		super(session.policies, session.inferences, session.context);
	}

	/**
	 * Retrieve the retraction statements by querying the model inferred from the policies
	 * @return model containing the statements to remove from the NLG model 
	 */
	public Model getRetractionModel() {
		return queryInferances(NLG_RETRACTION);
	}

	/**
	 * Retrieve the assertion statements by querying the model inferred from the policies
	 * @return model containing the statements to add to the NLG model 
	 */
	public Model getAssertionModel() {
		return queryInferances(NLG_ASSERTION);
	}
	
	private Model queryInferances(String requestType) {
		//TODO: if there is several NLGRetractionRequest, will the statements be constructed properly?
		String queryString = 
		"CONSTRUCT{?s ?p ?o }" + 
		"WHERE{" + 
		"	?NLGRetract a <" + requestType + "> ." +   
		"	?NLGRetract <http://www.policygrid.org/provenance-policies#requireSubject> ?s . " +  
		"	?NLGRetract <http://www.policygrid.org/provenance-policies#requirePredicate> ?p ." + 
		"	?NLGRetract <http://www.policygrid.org/provenance-policies#requireObject> ?o . " + 
		"}";
		
		Query query = QueryFactory.create(queryString);
		
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, inferences);
		Model inferedModel = qe.execConstruct();

		return inferedModel;
	}
}
