
package org.policygrid.workflow;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.policygrid.workflow.Edge4JSON;
import org.policygrid.workflow.OntClass4JSON;
import org.policygrid.workflow.Property4JSON;

import com.hp.hpl.jena.ontology.AllValuesFromRestriction;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.Restriction;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class OntologyQuery {
	private String ontFilePath;
	
	public OntologyQuery(String ontFilePath) {
		this.ontFilePath = ontFilePath;
	}
	
	/**
	 * Reads ontology and loads into JENA OntModel
	 * 
	 * @return OntModel containing ont structure
	 * @throws FileNotFoundException
	 */
	public OntModel loadOntology() throws FileNotFoundException {
		FileInputStream owlTaskFile = null;
        owlTaskFile = new FileInputStream(this.ontFilePath);
        OntModel base = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM_RULE_INF );       
        base.read(owlTaskFile, null);
        
        return base;
	}
	
	/**
	 * Loads the ontology to retrieve the root
	 * class. From there, subclasses are retrieved.
	 * 
	 * @param className root ontological class
	 * @return OntClass4JSON object
	 * @throws FileNotFoundException
	 */
	public OntClass4JSON getProcessTree(String className) throws FileNotFoundException {
    	OntModel base = loadOntology();
		OntClass processRoot = base.getOntClass(className);
		OntClass4JSON jsonProcessRoot = new OntClass4JSON();
		addSubClassses(jsonProcessRoot, processRoot);
		
		return jsonProcessRoot;
    }
	
	/**
	 * Recursively retrieves all subclasses from a 
	 * supplied root class.
	 * 
	 * @param ontClass4JSON 
	 * @param ontClass
	 */
	private void addSubClassses(OntClass4JSON ontClass4JSON, OntClass ontClass) {
		Iterator<OntClass> subClasses = ontClass.listSubClasses(true);
		
		while(subClasses.hasNext()) {
			OntClass subClass = subClasses.next();
			OntClass4JSON jsonSubClass = new OntClass4JSON();
			if(subClass.getLocalName() == null || subClass.getLocalName().length() < 1) {	
				//ignore process with empty name
				continue;
			}
			jsonSubClass.setLocalName(subClass.getLocalName());
			jsonSubClass.setUri(subClass.getURI());
			ontClass4JSON.addSubClass(jsonSubClass);
			
			/*
			 * Retrieves properties associated with classes
			 */
			Iterator<OntProperty> properties = subClass.listDeclaredProperties(false);
			while(properties.hasNext()) {
				OntProperty property = properties.next();
				if(property.getRange() != null) {
					jsonSubClass.addProperty(new Property4JSON(property.getLocalName(), property.getURI(), property.getRange().getLocalName()));
				}
				else {
					jsonSubClass.addProperty(new Property4JSON(property.getLocalName(), property.getURI()));					
				}
			}
			
			/*
			 * Check if current subclass has further subclasses
			 */
			if(subClass.hasSubClass()) {
				addSubClassses(jsonSubClass, subClass);
			}
		}		
	}
    
	/**
	 * Retrieves all relationships and restrictions
	 * between all subclasses of the specified root
	 * class.
	 * 
	 * @param className root ontological class
	 * @return ArrayList of Edge4JSON
	 * @throws FileNotFoundException
	 */
	public List<Edge4JSON> listEdges(String className) throws FileNotFoundException {
		OntModel base = loadOntology();
        Iterator<Restriction> iteratorRestriction = base.listRestrictions();
        List<Restriction> restrictions = new ArrayList<Restriction>();
        while(iteratorRestriction.hasNext()) {
        	restrictions.add(iteratorRestriction.next());
        }
        
        OntClass edgeRoot = base.getOntClass(className);

        Iterator<OntClass> subEdges = edgeRoot.listSubClasses(false);
		List<Edge4JSON> edges = new ArrayList<Edge4JSON>();
		while(subEdges.hasNext()) {
			OntClass subEdge = subEdges.next();
			Edge4JSON jsonSubEdge = new Edge4JSON(subEdge.getLocalName(), subEdge.getURI());
			Iterator<OntProperty> properties = subEdge.listDeclaredProperties(false);
			while(properties.hasNext()) {
				OntProperty property = properties.next();
				if(property.getRange() != null) {
					jsonSubEdge.addProperty(new Property4JSON(property.getLocalName(), property.getURI(), property.getRange().getRDFType().getLocalName()));
				}
				else {
					jsonSubEdge.addProperty(new Property4JSON(property.getLocalName(), property.getURI()));
				}
			}
			
			for(int i = 0; i < restrictions.size(); i++) {
				Restriction restriction = restrictions.get(i);
				
				if(subEdge.hasSuperClass(restriction) && restriction.isAllValuesFromRestriction()) {
					AllValuesFromRestriction valuesFromRestriction = restriction.asAllValuesFromRestriction();
					if("cause".equals(valuesFromRestriction.getOnProperty().getLocalName())) {
						jsonSubEdge.setCauseType(valuesFromRestriction.getAllValuesFrom().getLocalName());
					}
					if("effect".equals(valuesFromRestriction.getOnProperty().getLocalName())) {
						jsonSubEdge.setEffectType(valuesFromRestriction.getAllValuesFrom().getLocalName());
					}
				}
			}

			if( ( "Artifact".equals(jsonSubEdge.getCauseType()) && "Process".equals(jsonSubEdge.getEffectType()) ) ||
				( "Process".equals(jsonSubEdge.getCauseType()) && "Artifact".equals(jsonSubEdge.getEffectType()) ) ) {
				edges.add(jsonSubEdge);
				
			}
		}
		
		return edges;
	}
	
}
