package vocabulary;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public class parseToRDF {
	
	public parseToRDF(){
		
	}
	public static void Process(){
		
		// some definitions
		 String docURI    = "http://somewhere/doc1";
		 String grURI    = "http://somewhere/gr";
		 String wordURI    = "http://somewhere/word";	
		 String ruleURI    = "http://somewhere/rule";	
		// create an empty Model
		Model model = ModelFactory.createDefaultModel();

		// create the resource
		Resource docnode = model.createResource(docURI);
		
		Resource wordnode = model.createResource(wordURI);

		// add the property
		//docnode.addProperty(VCARD.FN, fullName);
		
	}

}
