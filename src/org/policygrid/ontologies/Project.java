package org.policygrid.ontologies;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

/**
 * Vocabulary for http://www.policygrid.org/project.owl
 *
 * Automatically generated with TopBraid Composer.
 */
public class Project {

    public final static String BASE_URI = "http://www.policygrid.org/project.owl";

    public final static String NS = BASE_URI + "#";


    public final static Resource CoInvestigator = ResourceFactory.createResource(NS + "CoInvestigator");

    public final static Resource Collaborator = ResourceFactory.createResource(NS + "Collaborator");

    public final static Resource Member = ResourceFactory.createResource(NS + "Member");

    public final static Resource PrincipalInvestigator = ResourceFactory.createResource(NS + "PrincipalInvestigator");

    public final static Resource Project = ResourceFactory.createResource(NS + "Project");

    public final static Resource ProjectRole = ResourceFactory.createResource(NS + "ProjectRole");

    public final static Resource ResearchAim = ResourceFactory.createResource(NS + "ResearchAim");

    public final static Resource ResearchQuestion = ResourceFactory.createResource(NS + "ResearchQuestion");

    public final static Resource Researcher = ResourceFactory.createResource(NS + "Researcher");

    public final static Resource Subcontractor = ResourceFactory.createResource(NS + "Subcontractor");

    public final static Property endDate = ResourceFactory.createProperty(NS + "endDate");

    public final static Property hasDescription = ResourceFactory.createProperty(NS + "hasDescription");
    
    public final static Property hasProjectPhoto = ResourceFactory.createProperty(NS + "hasProjectPhoto");

    public final static Property hasMemberRole = ResourceFactory.createProperty(NS + "hasMemberRole");

    public final static Property hasResearchAims = ResourceFactory.createProperty(NS + "hasResearchAims");

    public final static Property hasResearchQuestions = ResourceFactory.createProperty(NS + "hasResearchQuestions");

    public final static Property hasSubProject = ResourceFactory.createProperty(NS + "hasSubProject");

    public final static Property organisationOrDepartment = ResourceFactory.createProperty(NS + "organisationOrDepartment");

    public final static Property projectGrantNumber = ResourceFactory.createProperty(NS + "projectGrantNumber");

    public final static Property projectSubtitle = ResourceFactory.createProperty(NS + "projectSubtitle");

    public final static Property projectTitle = ResourceFactory.createProperty(NS + "projectTitle");

    public final static Property projectWebsite = ResourceFactory.createProperty(NS + "projectWebsite");

    public final static Property roleOf = ResourceFactory.createProperty(NS + "roleOf");

    public final static Property startDate = ResourceFactory.createProperty(NS + "startDate");


    public static String getURI() {
        return NS;
    }
}
