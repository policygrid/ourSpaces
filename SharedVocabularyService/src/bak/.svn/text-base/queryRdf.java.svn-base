package bak;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.rdf.model.Model;

public class  queryRdf{
	Model model;
	
	
	public queryRdf(Model m){
		model=m;
		
	}
	
	
	public int getTotal(String s, String p, String o){
	// Create a new query
		int count=0;
	String queryString = "prefix gr:<http://rasp/rule:> " +
			"prefix c:<http://cullinane/word:>" +
			"prefix d:<http://dargay/word:>" +
						"SELECT (count(*) as ?no) " +
						"WHERE { "+s+" "+p+" "+o+" .  }";


	Query query = QueryFactory.create(queryString,Syntax.syntaxARQ);

	// Execute the query and obtain results
	QueryExecution qe = QueryExecutionFactory.create(query, model);
	ResultSet results = qe.execSelect();
	//System.out.println(results.hasNext());
	QuerySolution qs;
	// Output query results	
	String scount="";
	while (results.hasNext()){
		qs=results.nextSolution();
		//System.out.println(qs.get("no"));
		scount=qs.get("no").toString().trim();
	}
	scount=scount.split("http:")[0].replace('^', ' ').trim();
	// Important - free up resources used running the query
	qe.close();
	return Integer.parseInt( scount);
	
	
	}
	
	public void query(String s, String p, String o){
		// Create a new query
			int count=0;
		String queryString = "prefix gr:<http://rasp/rule:>" +
							"prefix c:<http://cullinane/word:>" +
							"prefix d:<http://dargay/word:>" +
							"SELECT ?s ?p ?o " +
							"WHERE { "+s+" "+p+" "+o+" .  }";


		Query query = QueryFactory.create(queryString,Syntax.syntaxARQ);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		//System.out.println(results.hasNext());
		QuerySolution qs;
		// Output query results	
		String scount="";
		while (results.hasNext()){
			qs=results.nextSolution();
			System.out.println(qs.get("s").toString()+"****" + qs.get("o"));
			
		}
		
		// Important - free up resources used running the query
		qe.close();
		
		
		
		}
	
}
