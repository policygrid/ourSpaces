package org.policygrid.ontologies;

import java.util.UUID;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * Vocabulary for http://www.policygrid.org/academic-disciplines
 *
 * Automatically generated with TopBraid Composer.
 */
public class AcademicDiscipline {

    public final static String BASE_URI = "http://www.policygrid.org/academic-disciplines";

    public final static String NS = BASE_URI + "#";


    public final static Resource AcademicDiscipline = ResourceFactory.createResource(NS + "AcademicDiscipline");

    public final static Resource ChemicalEngineering = ResourceFactory.createResource(NS + "ChemicalEngineering");

    public final static Resource Chemistry = ResourceFactory.createResource(NS + "Chemistry");

    public final static Resource ComputerScience = ResourceFactory.createResource(NS + "ComputerScience");

    public final static Resource DisciplineInfo = ResourceFactory.createResource(NS + "DisciplineInfo");

    public final static Resource PhysicalSciencesAndEngineering = ResourceFactory.createResource(NS + "PhysicalSciencesAndEngineering");

    public final static Resource Psychology = ResourceFactory.createResource(NS + "Psychology");

    public final static Resource SocialSciences = ResourceFactory.createResource(NS + "SocialSciences");

    public final static Resource SocialSciencesAndHumanities = ResourceFactory.createResource(NS + "SocialSciencesAndHumanities");

    public final static Property hasAcademicDiscipline = ResourceFactory.createProperty(NS + "hasAcademicDiscipline");

    public final static Property hasDisciplineTag = ResourceFactory.createProperty(NS + "hasDisciplineTag");


    public static String getURI() {
        return NS;
    }
    
    public static Resource getNewDisciplineInfo(){
    	Model model = ModelFactory.createDefaultModel();
    		
		return getNewDisciplineInfo(model);
    }
    
    public static Resource getNewDisciplineInfo(Model model){

		String disciplineInfoURI = DisciplineInfo.toString() + "-" + UUID.randomUUID().toString();
		Resource disciplineInfoArtifact = model.createResource(disciplineInfoURI);
		disciplineInfoArtifact.addProperty(RDF.type, DisciplineInfo);
		
		return disciplineInfoArtifact;
    }

}
