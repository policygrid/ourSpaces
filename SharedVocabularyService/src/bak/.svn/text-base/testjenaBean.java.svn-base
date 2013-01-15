package bak;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import thewebsemantic.Bean2RDF;

public class testjenaBean {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		Book bk = new Book("11111");
		bk.setTitle("Simple Java");
		Model myModel = ModelFactory.createDefaultModel();
		Bean2RDF writer = new Bean2RDF(myModel );
		writer.save(bk);
		myModel.setNsPrefix("xsd", "http://www.w3.org/2001/XMLSchema#");
		myModel.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		myModel.setNsPrefix("", "http://example.org/");
		myModel.setNsPrefix("the", "http://thewebsemantic.com/");
		
		
		myModel.write(System.out);
		
	}

}
