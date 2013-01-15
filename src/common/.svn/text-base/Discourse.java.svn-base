package common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;


import org.openrdf.OpenRDFException;

import org.policygrid.ontologies.SIOC;
import org.policygrid.ontologies.SWAN;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;



public class Discourse {

	Connections con = new Connections();
	
	
public void addDiscourseItem(String sourceID, String propertyID, String destinationID) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, OpenRDFException, IOException {
	RDFi rdf = new RDFi();
	Model model = ModelFactory.createDefaultModel();
	
	common.Utility.log.debug("DISCOURSE -------------------------------- source:"+sourceID);
	common.Utility.log.debug("DISCOURSE -------------------------------- destination:"+destinationID);
	Resource source = model.createResource(sourceID);
	
	Resource destination = model.createResource(destinationID);

	source.addProperty(ResourceFactory.createProperty(SWAN.getURI(),propertyID), destination);
  
	ByteArrayOutputStream out = new ByteArrayOutputStream();	
	model.write(System.out);
	
	model.write(out);
	InputStream in = new ByteArrayInputStream(out.toByteArray());
	rdf.write(in);
	
}
	
}
