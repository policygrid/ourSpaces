package org.policygrid.ontologies;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

/**
 * Vocabulary for http://rdfs.org/sioc/ns
 *
 * Automatically generated with TopBraid Composer.
 */
public class SIOC {

    public final static String BASE_URI = "http://rdfs.org/sioc/ns";

    public final static String NS = BASE_URI + "#";

    public final static String PREFIX = "sioc";


    public final static Resource __ = ResourceFactory.createResource(NS + "");

    public final static Resource Community = ResourceFactory.createResource(NS + "Community");

    public final static Resource Container = ResourceFactory.createResource(NS + "Container");

    public final static Resource Forum = ResourceFactory.createResource(NS + "Forum");

    public final static Resource Item = ResourceFactory.createResource(NS + "Item");

    public final static Resource Post = ResourceFactory.createResource(NS + "Post");

    public final static Resource Role = ResourceFactory.createResource(NS + "Role");

    public final static Resource Site = ResourceFactory.createResource(NS + "Site");

    public final static Resource Space = ResourceFactory.createResource(NS + "Space");

    public final static Resource Thread = ResourceFactory.createResource(NS + "Thread");

    public final static Resource User = ResourceFactory.createResource(NS + "User");

    public final static Resource Usergroup = ResourceFactory.createResource(NS + "Usergroup");

    public final static Property about = ResourceFactory.createProperty(NS + "about");

    public final static Property account_of = ResourceFactory.createProperty(NS + "account_of");

    public final static Property administrator_of = ResourceFactory.createProperty(NS + "administrator_of");

    public final static Property attachment = ResourceFactory.createProperty(NS + "attachment");

    public final static Property avatar = ResourceFactory.createProperty(NS + "avatar");

    public final static Property container_of = ResourceFactory.createProperty(NS + "container_of");

    public final static Property content = ResourceFactory.createProperty(NS + "content");

    public final static Property content_encoded = ResourceFactory.createProperty(NS + "content_encoded");

    public final static Property created_at = ResourceFactory.createProperty(NS + "created_at");

    public final static Property creator_of = ResourceFactory.createProperty(NS + "creator_of");

    public final static Property description = ResourceFactory.createProperty(NS + "description");

    public final static Property earlier_version = ResourceFactory.createProperty(NS + "earlier_version");

    public final static Property email = ResourceFactory.createProperty(NS + "email");

    public final static Property email_sha1 = ResourceFactory.createProperty(NS + "email_sha1");

    public final static Property feed = ResourceFactory.createProperty(NS + "feed");

    public final static Property first_name = ResourceFactory.createProperty(NS + "first_name");

    public final static Property follows = ResourceFactory.createProperty(NS + "follows");

    public final static Property function_of = ResourceFactory.createProperty(NS + "function_of");

    public final static Property group_of = ResourceFactory.createProperty(NS + "group_of");

    public final static Property has_administrator = ResourceFactory.createProperty(NS + "has_administrator");

    public final static Property has_container = ResourceFactory.createProperty(NS + "has_container");

    public final static Property has_creator = ResourceFactory.createProperty(NS + "has_creator");

    public final static Property has_discussion = ResourceFactory.createProperty(NS + "has_discussion");

    public final static Property has_function = ResourceFactory.createProperty(NS + "has_function");

    public final static Property has_group = ResourceFactory.createProperty(NS + "has_group");

    public final static Property has_host = ResourceFactory.createProperty(NS + "has_host");

    public final static Property has_member = ResourceFactory.createProperty(NS + "has_member");

    public final static Property has_moderator = ResourceFactory.createProperty(NS + "has_moderator");

    public final static Property has_modifier = ResourceFactory.createProperty(NS + "has_modifier");

    public final static Property has_owner = ResourceFactory.createProperty(NS + "has_owner");

    public final static Property has_parent = ResourceFactory.createProperty(NS + "has_parent");

    public final static Property has_part = ResourceFactory.createProperty(NS + "has_part");

    public final static Property has_reply = ResourceFactory.createProperty(NS + "has_reply");

    public final static Property has_scope = ResourceFactory.createProperty(NS + "has_scope");

    public final static Property has_space = ResourceFactory.createProperty(NS + "has_space");

    public final static Property has_subscriber = ResourceFactory.createProperty(NS + "has_subscriber");

    public final static Property has_usergroup = ResourceFactory.createProperty(NS + "has_usergroup");

    public final static Property host_of = ResourceFactory.createProperty(NS + "host_of");

    public final static Property id = ResourceFactory.createProperty(NS + "id");

    public final static Property ip_address = ResourceFactory.createProperty(NS + "ip_address");

    public final static Property last_name = ResourceFactory.createProperty(NS + "last_name");

    public final static Property later_version = ResourceFactory.createProperty(NS + "later_version");

    public final static Property latest_version = ResourceFactory.createProperty(NS + "latest_version");

    public final static Property link = ResourceFactory.createProperty(NS + "link");

    public final static Property links_to = ResourceFactory.createProperty(NS + "links_to");

    public final static Property member_of = ResourceFactory.createProperty(NS + "member_of");

    public final static Property moderator_of = ResourceFactory.createProperty(NS + "moderator_of");

    public final static Property modified_at = ResourceFactory.createProperty(NS + "modified_at");

    public final static Property modifier_of = ResourceFactory.createProperty(NS + "modifier_of");

    public final static Property name = ResourceFactory.createProperty(NS + "name");

    public final static Property next_by_date = ResourceFactory.createProperty(NS + "next_by_date");

    public final static Property next_version = ResourceFactory.createProperty(NS + "next_version");

    public final static Property note = ResourceFactory.createProperty(NS + "note");

    public final static Property num_replies = ResourceFactory.createProperty(NS + "num_replies");

    public final static Property num_views = ResourceFactory.createProperty(NS + "num_views");

    public final static Property owner_of = ResourceFactory.createProperty(NS + "owner_of");

    public final static Property parent_of = ResourceFactory.createProperty(NS + "parent_of");

    public final static Property part_of = ResourceFactory.createProperty(NS + "part_of");

    public final static Property previous_by_date = ResourceFactory.createProperty(NS + "previous_by_date");

    public final static Property previous_version = ResourceFactory.createProperty(NS + "previous_version");

    public final static Property reference = ResourceFactory.createProperty(NS + "reference");

    public final static Property related_to = ResourceFactory.createProperty(NS + "related_to");

    public final static Property reply_of = ResourceFactory.createProperty(NS + "reply_of");

    public final static Property scope_of = ResourceFactory.createProperty(NS + "scope_of");

    public final static Property sibling = ResourceFactory.createProperty(NS + "sibling");

    public final static Property space_of = ResourceFactory.createProperty(NS + "space_of");

    public final static Property subject = ResourceFactory.createProperty(NS + "subject");

    public final static Property subscriber_of = ResourceFactory.createProperty(NS + "subscriber_of");

    public final static Property title = ResourceFactory.createProperty(NS + "title");

    public final static Property topic = ResourceFactory.createProperty(NS + "topic");

    public final static Property usergroup_of = ResourceFactory.createProperty(NS + "usergroup_of");


    public static String getURI() {
        return NS;
    }
}
