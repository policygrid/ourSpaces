package org.policygrid.ontologies;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

/**
 * Vocabulary for http://xmlns.com/foaf/0.1/
 *
 * Automatically generated with TopBraid Composer.
 */
public class FOAF {

    public final static String BASE_URI = "http://xmlns.com/foaf/0.1/";

    public final static String NS = BASE_URI;

    public final static String PREFIX = "foaf";


    public final static Resource __ = ResourceFactory.createResource(NS + "");

    public final static Resource Agent = ResourceFactory.createResource(NS + "Agent");

    public final static Resource Document = ResourceFactory.createResource(NS + "Document");

    public final static Resource Group = ResourceFactory.createResource(NS + "Group");

    public final static Resource Image = ResourceFactory.createResource(NS + "Image");

    public final static Resource OnlineAccount = ResourceFactory.createResource(NS + "OnlineAccount");

    public final static Resource OnlineChatAccount = ResourceFactory.createResource(NS + "OnlineChatAccount");

    public final static Resource OnlineEcommerceAccount = ResourceFactory.createResource(NS + "OnlineEcommerceAccount");

    public final static Resource OnlineGamingAccount = ResourceFactory.createResource(NS + "OnlineGamingAccount");

    public final static Resource Organization = ResourceFactory.createResource(NS + "Organization");

    public final static Resource Person = ResourceFactory.createResource(NS + "Person");

    public final static Resource PersonalProfileDocument = ResourceFactory.createResource(NS + "PersonalProfileDocument");

    public final static Resource Project = ResourceFactory.createResource(NS + "Project");

    public final static Property accountName = ResourceFactory.createProperty(NS + "accountName");

    public final static Property accountServiceHomepage = ResourceFactory.createProperty(NS + "accountServiceHomepage");

    public final static Property age = ResourceFactory.createProperty(NS + "age");

    public final static Property aimChatID = ResourceFactory.createProperty(NS + "aimChatID");

    public final static Property based_near = ResourceFactory.createProperty(NS + "based_near");

    public final static Property birthday = ResourceFactory.createProperty(NS + "birthday");

    public final static Property currentProject = ResourceFactory.createProperty(NS + "currentProject");

    public final static Property depiction = ResourceFactory.createProperty(NS + "depiction");

    public final static Property depicts = ResourceFactory.createProperty(NS + "depicts");

    public final static Property dnaChecksum = ResourceFactory.createProperty(NS + "dnaChecksum");

    public final static Property family_name = ResourceFactory.createProperty(NS + "family_name");

    public final static Property firstName = ResourceFactory.createProperty(NS + "firstName");

    public final static Property fundedBy = ResourceFactory.createProperty(NS + "fundedBy");

    public final static Property geekcode = ResourceFactory.createProperty(NS + "geekcode");

    public final static Property gender = ResourceFactory.createProperty(NS + "gender");

    public final static Property givenname = ResourceFactory.createProperty(NS + "givenname");

    public final static Property holdsAccount = ResourceFactory.createProperty(NS + "holdsAccount");

    public final static Property homepage = ResourceFactory.createProperty(NS + "homepage");

    public final static Property icqChatID = ResourceFactory.createProperty(NS + "icqChatID");

    public final static Property img = ResourceFactory.createProperty(NS + "img");

    public final static Property interest = ResourceFactory.createProperty(NS + "interest");

    public final static Property isPrimaryTopicOf = ResourceFactory.createProperty(NS + "isPrimaryTopicOf");

    public final static Property jabberID = ResourceFactory.createProperty(NS + "jabberID");

    public final static Property knows = ResourceFactory.createProperty(NS + "knows");

    public final static Property logo = ResourceFactory.createProperty(NS + "logo");

    public final static Property made = ResourceFactory.createProperty(NS + "made");

    public final static Property maker = ResourceFactory.createProperty(NS + "maker");

    public final static Property mbox = ResourceFactory.createProperty(NS + "mbox");

    public final static Property mbox_sha1sum = ResourceFactory.createProperty(NS + "mbox_sha1sum");

    public final static Property member = ResourceFactory.createProperty(NS + "member");

    public final static Property membershipClass = ResourceFactory.createProperty(NS + "membershipClass");

    public final static Property msnChatID = ResourceFactory.createProperty(NS + "msnChatID");

    public final static Property myersBriggs = ResourceFactory.createProperty(NS + "myersBriggs");

    public final static Property name = ResourceFactory.createProperty(NS + "name");

    public final static Property nick = ResourceFactory.createProperty(NS + "nick");

    public final static Property page = ResourceFactory.createProperty(NS + "page");

    public final static Property pastProject = ResourceFactory.createProperty(NS + "pastProject");

    public final static Property phone = ResourceFactory.createProperty(NS + "phone");

    public final static Property plan = ResourceFactory.createProperty(NS + "plan");

    public final static Property primaryTopic = ResourceFactory.createProperty(NS + "primaryTopic");

    public final static Property publications = ResourceFactory.createProperty(NS + "publications");

    public final static Property schoolHomepage = ResourceFactory.createProperty(NS + "schoolHomepage");

    public final static Property sha1 = ResourceFactory.createProperty(NS + "sha1");

    public final static Property surname = ResourceFactory.createProperty(NS + "surname");

    public final static Property theme = ResourceFactory.createProperty(NS + "theme");

    public final static Property thumbnail = ResourceFactory.createProperty(NS + "thumbnail");

    public final static Property tipjar = ResourceFactory.createProperty(NS + "tipjar");

    public final static Property title = ResourceFactory.createProperty(NS + "title");

    public final static Property topic = ResourceFactory.createProperty(NS + "topic");

    public final static Property topic_interest = ResourceFactory.createProperty(NS + "topic_interest");

    public final static Property weblog = ResourceFactory.createProperty(NS + "weblog");

    public final static Property workInfoHomepage = ResourceFactory.createProperty(NS + "workInfoHomepage");

    public final static Property workplaceHomepage = ResourceFactory.createProperty(NS + "workplaceHomepage");

    public final static Property yahooChatID = ResourceFactory.createProperty(NS + "yahooChatID");


    public static String getURI() {
        return NS;
    }
}
