package svs.ex1;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.DC;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String SXMLNS = "http://example.com/sxml#";
		Resource HEAD = ResourceFactory.createResource(SXMLNS + "head");
		Resource GR = ResourceFactory.createResource(SXMLNS + "gr");
		Resource DEP = ResourceFactory.createResource(SXMLNS + "dep");
		// The base
		String BASE = "http://example.com/my-base#";

		// Create some data in a model
		Model model = ModelFactory.createDefaultModel();

		// NOTE use of BASE here
		Resource res = model.createResource(BASE + "Buy", HEAD);
		res.addProperty(model.createProperty(BASE+"frequency"), "1");
		
		Resource res2 = model.createResource(BASE + "Car", DEP);		
		res2.addProperty(model.createProperty(BASE+"frequency"), "5");
		Resource res3 = model.createResource(BASE + "Van", DEP);		
		res3.addProperty(model.createProperty(BASE+"frequency"), "4");
		
		Resource gr1 = model.createResource(BASE + "ncsub", GR);		
		gr1.addProperty(model.createProperty(BASE+"frequency"), "3");
		
		gr1.addProperty(model.createProperty(BASE+"has"),res2);
	
		
		
		res.addProperty(model.createProperty(BASE+"has"),gr1);
		
		
	
		gr1.addProperty(model.createProperty(BASE+"has"),res3);
		
		
		Resource res0 = model.createResource(BASE + "kjhdfg", GR);
		
		
		
		model.getResource("Buy").addProperty(model.createProperty(BASE+"has"), res0);
		
		model.setNsPrefix("sxml", SXMLNS);
		// Now write out using base as BASE
		model.write(System.out, "RDF/XML-ABBREV", BASE);
		//model.write(System.out);
	}

}
