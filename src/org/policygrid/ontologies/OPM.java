package org.policygrid.ontologies;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

/**
 * Vocabulary for http://openprovenance.org/ontology
 *
 * Automatically generated with TopBraid Composer.
 */
public class OPM {

    public final static String BASE_URI = "http://openprovenance.org/ontology";

    public final static String NS = BASE_URI + "#";

    public final static String PREFIX = "opm";


    public final static Resource AValue = ResourceFactory.createResource(NS + "AValue");

    public final static Resource Account = ResourceFactory.createResource(NS + "Account");

    public final static Resource Agent = ResourceFactory.createResource(NS + "Agent");

    public final static Resource Annotable = ResourceFactory.createResource(NS + "Annotable");

    public final static Resource Annotation = ResourceFactory.createResource(NS + "Annotation");

    public final static Resource Artifact = ResourceFactory.createResource(NS + "Artifact");

    public final static Resource Edge = ResourceFactory.createResource(NS + "Edge");

    public final static Resource Entity = ResourceFactory.createResource(NS + "Entity");

    public final static Resource EventEdge = ResourceFactory.createResource(NS + "EventEdge");

    public final static Resource Node = ResourceFactory.createResource(NS + "Node");

    public final static Resource OPMGraph = ResourceFactory.createResource(NS + "OPMGraph");

    public final static Resource OTime = ResourceFactory.createResource(NS + "OTime");

    public final static Resource Process = ResourceFactory.createResource(NS + "Process");

    public final static Resource Property = ResourceFactory.createResource(NS + "Property");

    public final static Resource Role = ResourceFactory.createResource(NS + "Role");

    public final static Resource Used = ResourceFactory.createResource(NS + "Used");

    public final static Resource WasControlledBy = ResourceFactory.createResource(NS + "WasControlledBy");

    public final static Resource WasDerivedFrom = ResourceFactory.createResource(NS + "WasDerivedFrom");

    public final static Resource WasGeneratedBy = ResourceFactory.createResource(NS + "WasGeneratedBy");

    public final static Resource WasTriggeredBy = ResourceFactory.createResource(NS + "WasTriggeredBy");

    public final static Property account = ResourceFactory.createProperty(NS + "account");

    public final static Property annotation = ResourceFactory.createProperty(NS + "annotation");

    public final static Property avalue = ResourceFactory.createProperty(NS + "avalue");

    public final static Property cause = ResourceFactory.createProperty(NS + "cause");

    public final static Property content = ResourceFactory.createProperty(NS + "content");

    public final static Property effect = ResourceFactory.createProperty(NS + "effect");

    public final static Property encoding = ResourceFactory.createProperty(NS + "encoding");

    public final static Property endTime = ResourceFactory.createProperty(NS + "endTime");

    public final static Property exactlyAt = ResourceFactory.createProperty(NS + "exactlyAt");

    public final static Property hasAccount = ResourceFactory.createProperty(NS + "hasAccount");

    public final static Property hasAgent = ResourceFactory.createProperty(NS + "hasAgent");

    public final static Property hasArtifact = ResourceFactory.createProperty(NS + "hasArtifact");

    public final static Property hasDependency = ResourceFactory.createProperty(NS + "hasDependency");

    public final static Property hasProcess = ResourceFactory.createProperty(NS + "hasProcess");

    public final static Property label = ResourceFactory.createProperty(NS + "label");

    public final static Property noEarlierThan = ResourceFactory.createProperty(NS + "noEarlierThan");

    public final static Property noLaterThan = ResourceFactory.createProperty(NS + "noLaterThan");

    public final static Property pname = ResourceFactory.createProperty(NS + "pname");

    public final static Property profile = ResourceFactory.createProperty(NS + "profile");

    public final static Property property = ResourceFactory.createProperty(NS + "property");

    public final static Property role = ResourceFactory.createProperty(NS + "role");

    public final static Property startTime = ResourceFactory.createProperty(NS + "startTime");

    public final static Property time = ResourceFactory.createProperty(NS + "time");

    public final static Property type = ResourceFactory.createProperty(NS + "type");

    public final static Property uri = ResourceFactory.createProperty(NS + "uri");

    public final static Property value = ResourceFactory.createProperty(NS + "value");


    public static String getURI() {
        return NS;
    }
}
