
package org.policygrid.workflow;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.openrdf.OpenRDFException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import common.*;

public class ProvenanceGenerator {
	private String baseOntFilePath;
	
	public ProvenanceGenerator(String baseOntFilePath) {
		this.baseOntFilePath = baseOntFilePath;
	}
	
	/**
	 * Generates RDF from a JSON String and inserts into 
	 * the RDF repository.
	 * 
	 * @param toServerJSON :: JSON String
	 * @throws OpenRDFException
	 * @throws IOException
	 */
	public void generateRDF(String toServerJSON) throws OpenRDFException, IOException {	
		
		/*
		 * Specify ontology prefix
		 */
		
		String SOURCE = "http://openprovenance.org/ontology";
        String NS = SOURCE + "#";
        
        /*
         * Load ontology into a Jena Model. This model will 
         * be used to check for logical inconsistencies. 
         */
        
		FileInputStream owlTaskFile = null;
        owlTaskFile = new FileInputStream(baseOntFilePath);   
        OntModel base = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM_RDFS_INF);
        base.read(owlTaskFile, null);
        OntProperty cause = base.getOntProperty(NS + "cause");
		OntProperty effect = base.getOntProperty(NS + "effect");
        
		/*
		 * Create empty default JENA model, used for 
		 * creating new RDF.
		 */
		
        OntModel graph = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM_RDFS_INF);
        graph.setNsPrefix("policygrid", "http://policygrid.org/");
        graph.setNsPrefix("opm", "http://openprovenance.org/ontology#");
        
        /*
         * Extracts ontological Process classes and their 
         * according properties from the JSON String.
         */
        JSONObject jsonObject = JSONObject.fromObject(toServerJSON);
		Map<String, Individual> processMap = new HashMap<String, Individual>();
        JSONArray jsonProcesses = jsonObject.getJSONArray("processInstances");
        for(int i = 0; i < jsonProcesses.size(); i++) {
        	
        	//Retrieve OntClass from ontology
        	JSONObject jsonProcess = jsonProcesses.getJSONObject(i);        	
    		OntClass processClass = base.getOntClass(jsonProcess.getString("classUri")); 
    		//Create new OntClass for default model
    		Individual processIndividual = graph.createIndividual(processClass);
    		
    		JSONArray jsonProperties = jsonProcess.getJSONArray("properties");
    		for(int j = 0; j < jsonProperties.size(); j++) {
    			JSONObject jsonProperty = jsonProperties.getJSONObject(j);
    			OntProperty ontProperty = base.getOntProperty(jsonProperty.getString("uri"));
    			processIndividual.addLiteral(ontProperty, jsonProperty.get("value"));
    		}
    		
    		processMap.put(jsonProcess.getString("nodeId"), processIndividual);			
        }
        
        /*
         * Extracts the causal relationships between classes. 
         * Classes are temporarily stored in processMap; links 
         * cannot be made until all classes have been extracted 
         * from JSON.
         */
        JSONArray jsonEdges = jsonObject.getJSONArray("edgeInstances");
        for(int i = 0; i < jsonEdges.size(); i++) {
        	JSONObject jsonEdge = jsonEdges.getJSONObject(i);
        	OntClass edgeClass = base.getOntClass(jsonEdge.getString("edgeClassUri"));
			Individual edgeIndividual = graph.createIndividual(edgeClass);
			Resource causeIndividual;
			Resource effectIndividual;
			causeIndividual = jsonEdge.containsKey("causeUri") ? graph.createResource(jsonEdge.getString("causeUri")) : processMap.get(jsonEdge.getString("causeNodeId"));
			edgeIndividual.addProperty(cause, causeIndividual);
							
			
			effectIndividual = jsonEdge.containsKey("effectUri") ? graph.createResource(jsonEdge.getString("effectUri")) : processMap.get(jsonEdge.getString("effectNodeId"));	
			edgeIndividual.addProperty(effect, effectIndividual);			
        }
		
        /*
         * Write the newly created OntModel to the RDF repository.
         */

	}
}
