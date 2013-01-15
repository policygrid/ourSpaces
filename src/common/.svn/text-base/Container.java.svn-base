package common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.openrdf.OpenRDFException;
import org.policygrid.ontologies.OurSpacesVRE;
import org.policygrid.ontologies.SIOC;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

public class Container {
	
	public Container()
	{
		
	}
	
	public String createUserContainer(String ourSpacesUserID) throws OpenRDFException, IOException
	{
		
		RDFi rdf = new RDFi();
		// Create empty Ontology Model
		Model model = ModelFactory.createDefaultModel();
		
		String userProfileContainerID = "http://www.policygrid.org/ourspacesVRE.owl#" + UUID.randomUUID().toString();
		
		Resource userProfileContainer = model.createResource(userProfileContainerID);
			userProfileContainer.addProperty(RDF.type, OurSpacesVRE.UserProfileContainer);
		
		Resource ourSpacesUser = model.createResource(ourSpacesUserID);
			ourSpacesUser.addProperty(SIOC.owner_of, userProfileContainer);
		
			userProfileContainer.addProperty(SIOC.has_owner, ourSpacesUser);
        
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		model.write(out);
		InputStream in = new ByteArrayInputStream(out.toByteArray());
		rdf.write(in);
		
		return userProfileContainerID;
	}
	
	
	
	public String createBlogContainer(String parentContainerID) throws OpenRDFException, IOException
	{
		RDFi rdf = new RDFi();
		Model model = ModelFactory.createDefaultModel();
		
		String blogContainerID = "http://www.policygrid.org/ourspacesVRE.owl#" + UUID.randomUUID().toString();
		
		Resource blogContainer = model.createResource(blogContainerID);
			blogContainer.addProperty(RDF.type, OurSpacesVRE.BlogContainer);
			
		if(!parentContainerID.equals(""))
		{
			Resource parentContainer = model.createResource(parentContainerID);
				blogContainer.addProperty(SIOC.has_parent, parentContainer);
				parentContainer.addProperty(SIOC.parent_of, blogContainer);
		}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();	
		model.write(out);
		//updateParentContainer(blogContainerID, parentContainerID);
		InputStream in = new ByteArrayInputStream(out.toByteArray());
		rdf.write(in);
		return blogContainerID;
	}
	
	public String createProjectBlogContainer(String parentContainerID, String title) throws OpenRDFException, IOException
	{
		RDFi rdf = new RDFi();
		Model model = ModelFactory.createDefaultModel();
		
		String blogContainerID = "http://www.policygrid.org/ourspacesVRE.owl#" + UUID.randomUUID().toString();
		
		Resource blogContainer = model.createResource(blogContainerID);
			blogContainer.addProperty(RDF.type, OurSpacesVRE.BlogContainer);
			blogContainer.addProperty(OurSpacesVRE.BlogTitle, title);
			
		if(!parentContainerID.equals(""))
		{
			Resource parentContainer = model.createResource(parentContainerID);
				blogContainer.addProperty(SIOC.has_parent, parentContainer);
				parentContainer.addProperty(SIOC.parent_of, blogContainer);
		}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();	
		model.write(out);
		//updateParentContainer(blogContainerID, parentContainerID);
		InputStream in = new ByteArrayInputStream(out.toByteArray());
		rdf.write(in);
		return blogContainerID;
	}
	
	public void updateParentContainer(String currentContainerID, String parentContainerID) throws OpenRDFException, IOException
	{
		RDFi rdf = new RDFi();
		Model model = ModelFactory.createDefaultModel();
		
		common.Utility.log.debug("current Con: " + currentContainerID);
		common.Utility.log.debug("parent Con: " + parentContainerID);
		common.Utility.log.debug("SIOC: " + SIOC.parent_of.toString());
		
		Resource currentContainer = model.createResource(currentContainerID);
		
		Resource parentContainer = model.createResource(parentContainerID);
			parentContainer.addProperty(SIOC.parent_of, currentContainer);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();	
		model.write(out);
		InputStream in = new ByteArrayInputStream(out.toByteArray());
		rdf.write(in);
	}
	

}
