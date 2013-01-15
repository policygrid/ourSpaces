package common;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;

import lexicon.LexUtils;
import lexicon.builder.LexDefaults;
import lexicon.builder.PersonalLexicon;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.openrdf.OpenRDFException;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryException;
import org.policygrid.ontologies.ProvenanceGeneric;
import org.xml.sax.SAXException;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 */
public class SearchBean {

	static OntologyHandler ontology = null;
	Connections con = new Connections();
	// Base model containing all necessary ontologies and instances
	public static OntModel baseModel = null;

	// Sesame model linking to sesame repository
	private static Model sesameModel = null;
	ResultSet jenaResults;
	TupleQueryResult sesameResults;
	
	public SearchBean() {
		super();
		
		try {
			if(ontology == null)
				ontology = new OntologyHandler();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Method used to initialise the search bean
	 */
	public static synchronized void init() {

		//if (baseModel != null)
		//	return;

		// Get proxy information from the configuration
		System.setProperty("http.proxyHost",
				Configuration.getParaemter("proxy", "host"));
		System.setProperty("http.proxyPort",
				Configuration.getParaemter("proxy", "port"));
		baseModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

		// Link sesameModel with sesame repository
		/*Connections con = new Connections();
		try {
			con.repConnect();
		} catch (RepositoryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			sesameModel = JenaSesame.createModel(con.getRepConnection());
		} catch (Exception ex) {
			common.Utility.log.debug(ex);
		}*/

		//baseModel.add(sesameModel);
		// Initialise the ontology handler
		try {
			new common.OntologyHandler();
		} catch (IOException e2) {
			e2.printStackTrace();
		} catch (SAXException e2) {
			e2.printStackTrace();
		}
		// Link all the ontologies with the baseModel
		Enumeration<OntModel> e = common.OntologyHandler.hashtable.elements();
		while (e.hasMoreElements()) {
			OntModel mod = (OntModel) e.nextElement();
			baseModel.add(mod);
			//common.Utility.log.debug("Loading the model :" + mod.size());
		}
	}

	protected Map<String, Object> getNextSesameResults(){
		try{
			if (sesameResults.hasNext()) {
				Map<String, Object> obj = new HashMap<String, Object>();
				BindingSet bindingSet = sesameResults.next();
				obj.put("x", bindingSet.getValue("x"));
				obj.put("type", bindingSet.getValue("type"));
				if (bindingSet.getValue("title") != null)
					obj.put("title", bindingSet.getValue("title").stringValue());
				if (bindingSet.getValue("name") != null)
					obj.put("name", bindingSet.getValue("name").stringValue());
				if (bindingSet.getValue("surname") != null)
					obj.put("surname", bindingSet.getValue("surname").stringValue());
				if (bindingSet.getValue("blogtitle") != null)
					obj.put("blogtitle", bindingSet.getValue("blogtitle").stringValue());
				if (bindingSet.getValue("projectTitle") != null)
					obj.put("projectTitle", bindingSet.getValue("projectTitle").stringValue());
				return obj;
			}		
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void closeSesameSearch(){
		try {
			con.repDisconnect();	
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void closeJenaSearch(){
	}
	public void initSesameSearch(String queryString){
		try {
			con.repConnect();
			TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			sesameResults = output.evaluate();			
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void initJenaSearch(String queryString){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();	
		Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, baseModel);
		jenaResults = qe.execSelect();
	}
	/**
	 * Return results for a given query, using both RDF repository and all ontologies.
	 * @param queryString
	 * @return
	 */
	public Map<String, Object>  getNextJenaResults() {

		if (jenaResults.hasNext()) {
			Map<String, Object> obj = new HashMap<String, Object>();
			QuerySolution bindingSet = jenaResults.next();
			String res = bindingSet.get("x").toString();
			obj.put("x", bindingSet.get("x"));
			obj.put("type", bindingSet.get("type"));
			obj.put("title", bindingSet.get("title"));
			obj.put("name", bindingSet.get("name"));
			obj.put("surname", bindingSet.get("surname"));
			obj.put("blogtitle", bindingSet.get("blogtitle"));
			obj.put("projectTitle", bindingSet.get("projectTitle"));
			return obj;
		}
		return null;
	}

	protected static String getInverseQuery(String inputString){

		StringBuffer qry = new StringBuffer(1024);

		qry.append("SELECT DISTINCT ?x ?type ?title ?name ?surname ?blogtitle  ?projectTitle \n");
		qry.append("WHERE { \n");
		qry.append("?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?type. \n");
		qry.append("FILTER regex(str(?x), \""+inputString+"\",\"i\").\n ");
		qry.append("OPTIONAL {?x <http://www.policygrid.org/provenance-generic.owl#title> ?title }\n ");
		qry.append("OPTIONAL {?x <http://xmlns.com/foaf/0.1/firstName> ?name } \n ");
		qry.append("OPTIONAL {?x <http://xmlns.com/foaf/0.1/surname> ?surname } \n ");
		qry.append("OPTIONAL {?x <http://rdfs.org/sioc/ns#title> ?blogtitle } \n");
		qry.append("OPTIONAL {?x <http://www.policygrid.org/project.owl#projectTitle> ?projectTitle  } \n");
		qry.append("} ORDER BY(?type)");
		return qry.toString();
	}
	/**
	 * Returns standard query for given query string.
	 * @param inputString
	 * @param className
	 * @return
	 */
	protected static String getNormalQuery(String inputString, String className){
		Vector<String> classes = ontology.getSubclassListFull("all", className);
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT DISTINCT ?x ?type ?title ?name ?surname ?blogtitle  ?projectTitle \n");
		qry.append("WHERE { \n");
		qry.append("?x ?prop ?val. \n");
		qry.append("?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?type . \n");
		qry.append("FILTER (regex(?val, \"" + inputString+ "\",\"i\") ).\n ");
		qry.append("FILTER (isURI(?x)).\n ");
		qry.append("OPTIONAL { ?any <"+ ProvenanceGeneric.previousVersion.toString()+ "> ?x . } . \n");
		qry.append(" FILTER ( !BOUND(?any) ) .\n ");
		// qry.append("FILTER NOT EXISTS { ?any <"+ProvenanceGeneric.previousVersion.toString()+"> ?x }. ");
		if (className != null && className.length() > 0) {
			qry.append("FILTER ( \n");
			qry.append("(str(?type) = \"" + className + "\")  \n");
			for (int i = 0; i < classes.size(); i++) {
				if (classes.get(i) == null || classes.get(i).length() == 0)
					continue;
				qry.append("|| (str(?type) = \""+ (String) classes.get(i) + "\")  \n");
			}
			qry.append(") \n");
		}

		qry.append("OPTIONAL {?x <http://www.policygrid.org/provenance-generic.owl#title> ?title }\n ");
		qry.append("OPTIONAL {?x <http://xmlns.com/foaf/0.1/firstName> ?name } \n ");
		qry.append("OPTIONAL {?x <http://xmlns.com/foaf/0.1/surname> ?surname } \n ");
		qry.append("OPTIONAL {?x <http://rdfs.org/sioc/ns#title> ?blogtitle } \n");
		qry.append("OPTIONAL {?x <http://www.policygrid.org/project.owl#projectTitle> ?projectTitle  } \n");
		qry.append("} ORDER BY(?title)");
		return  qry.toString();
	}
	/**
	 * Returns expanded query using wordnet.
	 * @param inputString
	 * @param className
	 * @return
	 */
	protected static String getWordNetQuery(String inputString, String className){
		Vector<String> classes = ontology.getSubclassListFull("general", className);
		lexicon.WNInit.init();
		Set<String> simwords=LexUtils.getSimWords(inputString, "noun", "");
		String regcl="";
		
		
		
		for (String s:simwords){
			regcl=regcl+" regex(?val, \""+s+"\",\"i\") ||";
		}
		if (regcl.length()>2)
			regcl=regcl.substring(0, regcl.length()-2);
		else
			regcl="regex(?val, \""+inputString+"\",\"i\")";
		common.Utility.log.debug(regcl);

		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT DISTINCT ?x ?type ?title ?name ?surname ?blogtitle  ?projectTitle \n");
		qry.append("WHERE { \n");
		qry.append("?x ?prop ?val. \n");
		qry.append("?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?type . \n");
		qry.append("FILTER (isURI(?x)).\n ");
		qry.append("FILTER ("+regcl+" ).\n ");//|| regex(str(?x), \""+inputString+"\",\"i\")

		qry.append("OPTIONAL { ?any <"+ProvenanceGeneric.previousVersion.toString()+"> ?x . } . \n");
		qry.append(" FILTER ( !BOUND(?any) ) .\n ");
		//qry.append("FILTER NOT EXISTS { ?any <"+ProvenanceGeneric.previousVersion.toString()+"> ?x }. ");
		if(className!=null && className.length() >0 ){
			qry.append("FILTER ( \n");
			qry.append("regex(str(?type), \""+className+"\")  \n");
			for (int i = 0; i < classes.size(); i++) {
				if(classes.get(i) == null || classes.get(i).length() == 0)
					continue;
				common.Utility.log.debug("------------------ CLASSES "+(String)classes.get(i));
				qry.append("|| regex(str(?type), \""+(String)classes.get(i)+"\")  \n");
			}	
			qry.append(") \n");
		}
		
		qry.append("OPTIONAL {?x <http://www.policygrid.org/provenance-generic.owl#title> ?title }\n ");
		qry.append("OPTIONAL {?x <http://xmlns.com/foaf/0.1/firstName> ?name } \n ");
		qry.append("OPTIONAL {?x <http://xmlns.com/foaf/0.1/surname> ?surname } \n ");
		qry.append("OPTIONAL {?x <http://rdfs.org/sioc/ns#title> ?blogtitle } \n");
		qry.append("OPTIONAL {?x <http://www.policygrid.org/project.owl#projectTitle> ?projectTitle  } \n");
		qry.append("} ORDER BY(?title)");
		return  qry.toString();
	}
	/**
	 * Returns expanded query using wordnet.
	 * @param inputString
	 * @param className
	 * @return
	 */
	protected static String getPLSQuery(String inputString, String className, int userid) throws SQLException, ClassNotFoundException, IllegalAccessException,InstantiationException,OpenRDFException,ServletException,IOException,SAXException,ParserConfigurationException{
		System.out.println("in getPLSQuery");
		common.User usr=new common.User();
		String foaf=usr.getFOAFID(userid).split("#")[1];
		System.out.println("FOAF ID IS: "+foaf);
		Vector<String> classes = ontology.getSubclassListFull("general", className);
		PersonalLexicon pl=new PersonalLexicon();
   		pl.init(LexDefaults.getInputStream(),foaf);
   		pl.loadDict(foaf);
   
   
   		JSONArray js= pl.mp.get(inputString.toLowerCase().trim());

    
   		String regcl="";
		
		String newStr="";
		if (js !=null){
		for (int i=0;i<js.size();i++){
			JSONObject jsnobj=(JSONObject) js.get(i);
			String s=jsnobj.getString("word");  //getObjectName(myObject[i].source,myObject[i].id);
			regcl=regcl+" regex(?val, \""+s+"\",\"i\") ||";
			
		}
		}
		
		
		
		
		if (regcl.length()>2)
			regcl=regcl.substring(0, regcl.length()-2);
		else
			regcl="regex(?val, \""+inputString+"\",\"i\")";
		common.Utility.log.debug(regcl);

		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT DISTINCT ?x ?type ?title ?name ?surname ?blogtitle  ?projectTitle \n");
		qry.append("WHERE { \n");
		qry.append("?x ?prop ?val. \n");
		qry.append("?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?type . \n");
		qry.append("FILTER (isURI(?x)).\n ");
		qry.append("FILTER ("+regcl+" ).\n ");//|| regex(str(?x), \""+inputString+"\",\"i\")

		qry.append("OPTIONAL { ?any <"+ProvenanceGeneric.previousVersion.toString()+"> ?x . } . \n");
		qry.append(" FILTER ( !BOUND(?any) ) .\n ");
		//qry.append("FILTER NOT EXISTS { ?any <"+ProvenanceGeneric.previousVersion.toString()+"> ?x }. ");
		if(className!=null && className.length() >0 ){
			qry.append("FILTER ( \n");
			qry.append("regex(str(?type), \""+className+"\")  \n");
			for (int i = 0; i < classes.size(); i++) {
				if(classes.get(i) == null || classes.get(i).length() == 0)
					continue;
				common.Utility.log.debug("------------------ CLASSES "+(String)classes.get(i));
				qry.append("|| regex(str(?type), \""+(String)classes.get(i)+"\")  \n");
			}	
			qry.append(") \n");
		}
		
		qry.append("OPTIONAL {?x <http://www.policygrid.org/provenance-generic.owl#title> ?title }\n ");
		qry.append("OPTIONAL {?x <http://xmlns.com/foaf/0.1/firstName> ?name } \n ");
		qry.append("OPTIONAL {?x <http://xmlns.com/foaf/0.1/surname> ?surname } \n ");
		qry.append("OPTIONAL {?x <http://rdfs.org/sioc/ns#title> ?blogtitle } \n");
		qry.append("OPTIONAL {?x <http://www.policygrid.org/project.owl#projectTitle> ?projectTitle  } \n");
		qry.append("} ORDER BY(?title)");
		return  qry.toString();
	}

	/**
	 * Runs given query against the repository and ontologies stored in Jena model
	 * @param query The query to be ran
	 * @param method Name of the method. Usually "Default"
	 * @param className Name of the base class
	 * @param inputString Search string
	 * @param size Number of results to get
	 * @param addValue If true, a parameter "value" will be added to the results
	 * @param spaces If true, replace spaces with underscope
	 * @param inverse If true, do inverse search - from URI to label of resource
	 * @param usrid Id of the user.
	 * @return JSONArray with the results to the query
	 */
	protected JSONArray runQuery(String method,String query,  String className, String inputString, int size, boolean addValue, boolean spaces, boolean inverse, int usrid) {
		try {
			JSONArray results = new JSONArray();
			JSONObject oneRes;
			con.repConnect();
			SearchBean.init();
			initJenaSearch(query);
			initSesameSearch(query);
			int count = 0;
			int iJena = 0, iSesame = 0;
			//Initialize queries to both Sesame and ontologies
			Map<String, Object> objJena = getNextJenaResults(), objSesame = getNextSesameResults();
			while ((size < 0 || count < size) && (objJena != null || objSesame != null)) {
				Map<String, Object> obj = null;
				if ((iSesame < iJena || objJena == null) && objSesame != null) {
					obj = objSesame;
					objSesame = getNextSesameResults();
				} else if (objJena != null) {
					obj = objJena;
					objJena = getNextJenaResults();
					iJena++;
				} else {
					break;
				}

				String res = obj.get("x").toString();
				String type = obj.get("type").toString();
				// exceptions
				if (type.equals("http://www.policygrid.org/ourspacesVRE.owl#BlogContainer"))
					continue;
				if (type.equals("http://www.policygrid.org/project.owl#ResearchAim"))
					continue;
				if (type.equals("http://www.policygrid.org/ourspacesVRE.owl#OurSpacesAccount"))
					continue;
				if (type.equals("http://www.w3.org/2000/01/rdf-schema#Class"))
					continue;
				// END OF exceptions
				String title = "";
				String name = "";
				String surname = "";
				String blogtitle = "";
				String projectTitle = "";

				if (obj.containsKey("title") && obj.get("title") != null)
					title = obj.get("title").toString();
				if (obj.containsKey("name") && obj.get("name") != null)
					name = obj.get("name").toString();
				if (obj.containsKey("surname") && obj.get("surname") != null)
					surname = obj.get("surname").toString();
				if (obj.containsKey("blogtitle") && obj.get("blogtitle") != null)
					blogtitle = obj.get("blogtitle").toString();
				if (obj.containsKey("projectTitle") && obj.get("projectTitle") != null)
					projectTitle = obj.get("projectTitle").toString();

				String displayName = "";
				String fields1 = common.Utility.getLocalName(res);
				// Use localname part of uri by default
				if (fields1 != null && !"".equals(fields1))
					displayName = (String) fields1;
				// Use title by default
				if (title != null && !"".equals(title))
					displayName = (String) title;

				if (type.equals("http://xmlns.com/foaf/0.1/Person")) {
					displayName = name + " " + surname;
				} 
				else if (type.equals("http://rdfs.org/sioc/ns#Post")) {
					displayName = blogtitle;
				} else if (type.equals("http://www.policygrid.org/project.owl#Project")) {
					displayName = projectTitle;
				}

				String image = Utility.getImage(type, null);
				String href = Utility.getDetailPage(type, res);

				// When no type specified and we have no link, do not show the
				// result
				// Add json object to the list.
				if (!"".equals(className) || (!displayName.equals("") && !"".equals(href) && "".equals(className))) {
					oneRes = new JSONObject();
					oneRes.put("id", res);
					if (addValue)
						oneRes.put("value", res);
					if (spaces)
						oneRes.put("label", displayName);
					else {
						String lab = displayName.replaceAll("[^A-Za-z0-9]", "_");
						if (lab.length() > 40)
							lab = lab.substring(0, 40) + "_";
						oneRes.put("label", lab);
					}
					oneRes.put("data-class", type);
					oneRes.put("href", href);
					oneRes.put("image", image);
					oneRes.put("method", method);
					results.add(oneRes);
					count++;
					if (size > 0 && count >= size)
						break;
				}
			}
			closeSesameSearch();
			closeJenaSearch();
			return results;
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return null;
	}

	/** Return JSON of results for given query.
	 * 
	 * @param className
	 * @param inputString
	 * @param addValue
	 * @param spaces
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException */
	public JSONArray getJSON(String className, String inputString, int size, boolean addValue, boolean spaces, boolean inverse, int usrid) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException, OpenRDFException, ServletException, IOException, SAXException, ParserConfigurationException {
		try {
			JSONArray results = new JSONArray();
			JSONObject oneRes;
			String query;
			String method;
			//Inverse search
			if (inverse) {
				query = getInverseQuery(inputString);
				results = runQuery("Inverse",query, className, inputString, size, addValue, spaces, inverse, usrid);
			} 
			//Autocomplete - normal search
			else if (!"".equals(className)) {
				query = getNormalQuery(inputString, className);
				results = runQuery("Default",query, className, inputString, size, addValue, spaces, inverse, usrid);
			}
			// Search with results from different methods together
			else {
				query = getNormalQuery(inputString, className);
				JSONArray results1 = runQuery("Default", query, className, inputString, size, addValue, spaces, inverse, usrid);
				query = getPLSQuery(inputString, className, usrid);
				JSONArray results2 = runQuery("PLSExpansion", query, className, inputString, size, addValue, spaces, inverse, usrid);
				query = getPLSQuery(inputString, className, usrid);
				JSONArray results3 = runQuery("WordNet", query, className, inputString, size, addValue, spaces, inverse, usrid);

				//Removing duplicate entries
				for(int i = 0;i<results2.size();i++){
					JSONObject json2 = (JSONObject)results2.get(i);
					String id2 = json2.getString("id");
					for(int j = 0;j<results3.size();j++){
						JSONObject json3 = (JSONObject)results3.get(j);
						String id3 = json3.getString("id");
						if (id2.equals(id3)){
							results3.remove(j);
						}
					}
				}
				
				for(int i = 0;i<results1.size();i++){
					JSONObject json = (JSONObject)results1.get(i);
					String id = json.getString("id");
					for(int j = 0;j<results2.size();j++){
						JSONObject json2 = (JSONObject)results2.get(j);
						String id2 = json2.getString("id");
						if (id.equals(id2)){
							results2.remove(j);
						}
					}
					for(int j = 0;j<results3.size();j++){
						JSONObject json3 = (JSONObject)results3.get(j);
						String id3 = json3.getString("id");
						if (id.equals(id3)){
							results3.remove(j);
						}
					}
				}
				results.addAll(results1);
				results.addAll(results2);
				results.addAll(results3);
			}
			return results;
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return null;
	}
}