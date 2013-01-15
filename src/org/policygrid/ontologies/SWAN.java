package org.policygrid.ontologies;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

/**
 * Vocabulary for http://swan.mindinformatics.org/ontologies/1.2/discourse-relationships/
 *
 * Automatically generated with TopBraid Composer.
 */
public class SWAN {

    public final static String BASE_URI = "http://swan.mindinformatics.org/ontologies/1.2/discourse-relationships/";

    public final static String NS = BASE_URI;


    public final static Resource __ = ResourceFactory.createResource(NS + "");

    public final static Property agreesWith = ResourceFactory.createProperty(NS + "agreesWith");

    public final static Property alternativeTo = ResourceFactory.createProperty(NS + "alternativeTo");

    public final static Property arisesFrom = ResourceFactory.createProperty(NS + "arisesFrom");

    public final static Property cites = ResourceFactory.createProperty(NS + "cites");

    public final static Property consistentWith = ResourceFactory.createProperty(NS + "consistentWith");

    public final static Property disagreesWith = ResourceFactory.createProperty(NS + "disagreesWith");

    public final static Property discusses = ResourceFactory.createProperty(NS + "discusses");

    public final static Property inResponseTo = ResourceFactory.createProperty(NS + "inResponseTo");

    public final static Property inconsistentWith = ResourceFactory.createProperty(NS + "inconsistentWith");

    public final static Property motivatedBy = ResourceFactory.createProperty(NS + "motivatedBy");

    public final static Property refersTo = ResourceFactory.createProperty(NS + "refersTo");

    public final static Property relatedTo = ResourceFactory.createProperty(NS + "relatedTo");

    public final static Property relevantTo = ResourceFactory.createProperty(NS + "relevantTo");


    public static String getURI() {
        return NS;
    }
}
