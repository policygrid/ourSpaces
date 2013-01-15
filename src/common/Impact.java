package common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.Update;
import org.openrdf.repository.RepositoryException;

import com.hp.hpl.jena.iri.IRI;
import com.hp.hpl.jena.iri.IRIFactory;
import com.hp.hpl.jena.vocabulary.RDF;

public class Impact {

	Connections con = new Connections();
	public RDFi rdf = new RDFi();
	
	public String uri;
	
	public String title = "";
	public String type = "";
	public String depositedBy = "";
	public String uploadedOn = "";
	public String summary = "";
	public String insights = "";
	public String details = "";

	public ArrayList<String> researchers = new ArrayList<String>();
	public ArrayList<String> references = new ArrayList<String>();
	public ArrayList<String> corroboratingSource = new ArrayList<String>();
	public ArrayList<String> youTubeVideos = new ArrayList<String>();
	public ArrayList<String> twitterPosts = new ArrayList<String>();
	public ArrayList<String> projects = new ArrayList<String>();
	 		
	public int userid;
	public String rdfUserID;
	
	public Impact()
	{
		
	}
	
	public void loadFromJson(String json){

		JSONObject res = (JSONObject) JSONSerializer.toJSON(json);
		uri = res.getString("uri");
		title = res.getString("title");
		summary = res.getString("summary");
		insights = res.getString("insights");
		details = res.getString("details");
		
		JSONArray arr;
		arr = res.getJSONArray("researchers");
		researchers.addAll(arr);
		arr = res.getJSONArray("references");
		references.addAll(arr);
		arr = res.getJSONArray("corroboratingSource");
		corroboratingSource.addAll(arr);
		arr = res.getJSONArray("youTubeVideos");
		youTubeVideos.addAll(arr);
		arr = res.getJSONArray("twitterPosts");
		twitterPosts.addAll(arr);
		arr = res.getJSONArray("projects");
		projects.addAll(arr);
	}
	protected void setOneProperty(Properties prop){
		String property = prop.getProperty();
		String value = prop.getValue();
		try {
			if (property.equals("http://www.policygrid.org/provenance-generic.owl#title")) {
			title = value;
			}
			else if (property.equals(RDF.type.getURI())) {
				type = value;
			}
			else if (property.equals("http://www.policygrid.org/provenance-generic.owl#depositedBy")) {
				depositedBy = value;
			} else if (property.equals("uploadedOn")) {
			 	//Get the natural representation on timestamp
			 	double timestamp;
				timestamp = rdf.getResourceTimestamp(uri);
				long time = rdf.getLong(timestamp);
			 	Date date = new Date(time);
			 	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			 	uploadedOn = sdf.format(date);
			}
			else if (property.equals("http://www.policygrid.org/provenance-generic.owl#producedInProject")) {
				projects.add(value);
			} else if (property.equals("http://www.policygrid.org/provenance-impact.owl#hasCorroboratingSource")) {
				corroboratingSource.add(value);
			} else if (property.equals("http://www.policygrid.org/provenance-impact.owl#hasReference")) {
				references.add(value);
			}else if (property.equals("http://www.policygrid.org/provenance-impact.owl#hasResearcher")) {
				researchers.add(value);
			}else if (property.equals("http://www.policygrid.org/provenance-impact.owl#hasTwitterPosts")) {
				twitterPosts.add(value);
			}else if (property.equals("http://www.policygrid.org/provenance-impact.owl#hasYouTubeVideo")) {
				youTubeVideos.add(value);
			}else if (property.equals("http://www.policygrid.org/provenance-impact.owl#hasDetails")) {
				details = value;
			}else if (property.equals("http://www.policygrid.org/provenance-impact.owl#hasInsights")) {
				insights = value;
			}else if (property.equals("http://www.policygrid.org/provenance-impact.owl#hasSummary")) {
				summary = value;
			}
		} catch (RepositoryException e) {
			e.printStackTrace();
		} catch (MalformedQueryException e) {
			e.printStackTrace();
		} catch (QueryEvaluationException e) {
			e.printStackTrace();
		}
	 
	}
		
	/**
	 * Loads properties of current artifact and fills the variables. Geo properties are also treated.
	 */
	public void loadProperties() {
		try {

			ArrayList<Properties> properties = rdf.getProperties("" + uri);
			for (int i = 0; i < properties.size(); i++) {
				Properties prop = properties.get(i);
				String value = prop.getValue();
				if (value == null || value.length() == 0)
					continue;
				setOneProperty(prop);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void deleteProperties() {
		try {
			con.repConnect();
			String q = "DELETE DATA{"+getSPARQLData()+"}";
			Update up = con.getRepConnection().prepareUpdate(org.openrdf.query.QueryLanguage.SPARQL, q);
			up.execute();
			con.repDisconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void writeProperties() {
		try {
			con.repConnect();
			String q = "INSERT DATA{"+getSPARQLData()+"}";
			Update up = con.getRepConnection().prepareUpdate(org.openrdf.query.QueryLanguage.SPARQL, q);
			up.execute();
			con.repDisconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	private static String addBrackets(String s){
		return "<"+s+"> ";
	}
	private static String getTriple(String s1, String s2, String s3){
		IRIFactory iriFactory = IRIFactory.semanticWebImplementation();
		boolean includeWarnings = false;
		IRI iri;
		iri = iriFactory.create(s3); 
		//Literal
		if (iri.hasViolation(includeWarnings)) 
			return addBrackets(s1)+addBrackets(s2)+"\""+s3+"\".\n";
		//Resource
		else
			return addBrackets(s1)+addBrackets(s2)+ addBrackets(s3)+".\n";
	}
	
	
	public String getSPARQLData() {
		String q = "";
		q+=getTriple(uri,"http://www.policygrid.org/provenance-generic.owl#title" ,title);
		q+=getTriple(uri,"http://www.policygrid.org/provenance-impact.owl#hasSummary" ,summary);
		q+=getTriple(uri,"http://www.policygrid.org/provenance-impact.owl#hasInsights" ,insights);
		q+=getTriple(uri,"http://www.policygrid.org/provenance-impact.owl#hasDetails" ,details);
		
		for(String s : researchers){
			q+=getTriple(uri,"http://www.policygrid.org/provenance-impact.owl#hasResearcher" ,s);
		}
		for(String s : references){
			q+=getTriple(uri,"http://www.policygrid.org/provenance-impact.owl#hasReference" ,s);
		}
		for(String s : corroboratingSource){
			q+=getTriple(uri,"http://www.policygrid.org/provenance-impact.owl#hasCorroboratingSource" ,s);
		}
		for(String s : projects){
			q+=getTriple(uri,"http://www.policygrid.org/provenance-generic.owl#producedInProject" ,s);
		}
		for(String s : youTubeVideos){
			q+=getTriple(uri,"http://www.policygrid.org/provenance-impact.owl#hasYouTubeVideo" ,s);
		}
		for(String s : twitterPosts){
			q+=getTriple(uri,"http://www.policygrid.org/provenance-impact.owl#hasTwitterPosts" ,s);
		}
		return q;
	}
			
	/**
	 * Returns the artifact's title
	 * @return
	 */
	public String getTitle()
	{
		return title;
	}
	
	
	
	/**
	 * Sets the artifact's title
	 * @param title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getUploadedOn() {
		return uploadedOn;
	}

	public void setUploadedOn(String uploadedOn) {
		this.uploadedOn = uploadedOn;
	}

	public String getDepositedBy() {
		return depositedBy;
	}

	public void setDepositedBy(String depositedBy) {
		this.depositedBy = depositedBy;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getInsights() {
		return insights;
	}

	public void setInsights(String insights) {
		this.insights = insights;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public ArrayList<String> getResearchers() {
		return researchers;
	}

	public void setResearchers(ArrayList<String> researchers) {
		this.researchers = researchers;
	}

	public ArrayList<String> getReferences() {
		return references;
	}

	public void setReferences(ArrayList<String> references) {
		this.references = references;
	}

	public ArrayList<String> getCorroboratingSource() {
		return corroboratingSource;
	}

	public void setCorroboratingSource(ArrayList<String> corroboratingSource) {
		this.corroboratingSource = corroboratingSource;
	}

	public ArrayList<String> getYouTubeVideos() {
		return youTubeVideos;
	}

	public void setYouTubeVideos(ArrayList<String> youTubeVideos) {
		this.youTubeVideos = youTubeVideos;
	}

	public ArrayList<String> getTwitterPosts() {
		return twitterPosts;
	}

	public void setTwitterPosts(ArrayList<String> twitterPosts) {
		this.twitterPosts = twitterPosts;
	}	
	public ArrayList<String> getProjects() {
		return projects;
	}

	public void setProjects(ArrayList<String> projects) {
		this.projects = projects;
	}

}
