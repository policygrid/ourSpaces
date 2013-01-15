package common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.policygrid.ontologies.FOAF;
import org.policygrid.ontologies.OurSpacesVRE;
import org.policygrid.ontologies.ProvenanceGeneric;
import org.policygrid.ontologies.SIOC;
import org.policygrid.policies.PolicyReasoner;
import org.policygrid.policies.PolicySession;
import org.policygrid.policies.RequiredFieldsSession;

import provenanceService.ProvenanceService;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.vocabulary.RDF;

public class ArtifactBean {

	Connections con = new Connections();
	public RDFi rdf;
	
	public String artifactID;
	
	public String title = "";
	public String type = "";
	public String depositedBy = "";
	public String uploadedOn = "";
	public String producedInProject = "";
	public String featureId = "";
	public String url;

	public ArrayList<Properties> niceProperties;
	public ArrayList<Properties> properties;
	public ArrayList<BlogBean> blogPosts;
	public ArrayList<String> previousVersions;
 	
 	/**
 	 * Tags of the artifact. List of class Tag.
 	 */
 	public ArrayList<Tag> tags;
	/**
	 * List of BlogBeans
	 */
	public ArrayList<BlogBean> comments;
 	/**
 	 * Contains list of:0-property linking artifact to feature, 1-featureId, 2-name,3-lattitude, 4-longitude 
 	 */
	public ArrayList<ArrayList<String>> features;
	

	/**
	 * List of related artifacts that have similar tags as the current one.
	 */
	public ArrayList<Resources> relatedByTags;

	/**
	 * List of related artifacts that appear in the provenance of the current one.
	 */
	public ArrayList<Resources> relatedByProvenance;

	
	public int userid;
	public String rdfUserID;
	
	public ArtifactBean()
	{
		
	}
	
	public void loadValues() {
		try {
			features = new ArrayList<ArrayList<String>>();
			blogPosts = new ArrayList<BlogBean>();
			comments = new ArrayList<BlogBean>();
			previousVersions = new ArrayList<String>();
			tags = new ArrayList<Tag>();
			properties = new ArrayList<Properties>();
			relatedByTags = new ArrayList<Resources>();
			relatedByProvenance = new ArrayList<Resources>();

			loadPreviousVersions();
			loadTags();	
			loadProperties();
			loadBlogs();
			loadRelatedByTags();
			loadRelatedByProvenance();
		 	//Get the url of artifact
		 	url = rdf.getResourceURL(artifactID);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public boolean lastVersion(){

		StringBuffer qry = new StringBuffer(1024);
		//previous versions
		qry.append("SELECT ?resource ");
	    qry.append("WHERE { ");
	    qry.append("?resource <http://www.policygrid.org/provenance-generic.owl#previousVersion> <"+artifactID+"> . ");
	    qry.append("}");
	    String query = qry.toString();
		try {
			con.repConnect();
			TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
			TupleQueryResult result = output.evaluate();

			while (result.hasNext()) 
			{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * Loads tags of current artifact.
	 */
	public void loadTags() {
	 	ArrayList<String> l = new ArrayList<String>();
	 	l.add(artifactID);
	 	Tag tag = new Tag();
		try {
			tags.addAll(tag.getResourceTags(l));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads properties of current artifact and fills the variables. Geo properties are also treated.
	 */
	public void loadProperties() {
		try {

			properties = rdf.getProperties("" + artifactID);
			niceProperties = new ArrayList<common.Properties>();
			for (int i = 0; i < properties.size(); i++) {
				common.Properties prop = properties.get(i);
				common.Properties niceProp = new common.Properties();
				niceProperties.add(niceProp);
				String property = prop.getProperty();
				String value = prop.getValue();
				niceProp.setProperty(property);
				niceProp.setValue(value);
				if (value == null || value.length() == 0)
					continue;
				if (property.equals("http://www.policygrid.org/provenance-generic.owl#title")) {
					title = value;
				}
				else if (property.equals(RDF.type.getURI())) {
					type = value;
				}
				else if (property.equals("http://www.policygrid.org/provenance-generic.owl#depositedBy")) {
					User user = new User();
					int id = user.getUserIDFromRDFID(value);
					String name = user.getName(id);
					depositedBy = name;
					niceProp.setValue(name);
				} else if (property.equals("uploadedOn")) {
				 	//Get the natural representation on timestamp
				 	double timestamp = rdf.getResourceTimestamp(artifactID);
				 	long time = rdf.getLong(timestamp);
				 	Date date = new Date(time);
				 	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				 	uploadedOn = sdf.format(date);
					niceProp.setValue(uploadedOn);

				} else if (property.equals("producedInProject")) {
					producedInProject = value;
				} else if (property.startsWith("http://www.policygrid.org/geo-properties.owl#")) {
					// Loading geo-features of artifact
					String name = property
							.substring("http://www.policygrid.org/geo-properties.owl#"
									.length());
					if (value == null || value.length() == 0)
						continue;
					ArrayList<String> v = new ArrayList<String>();
					v.add(name);
					v.add(value);
					// Getting geo-locations and getting only the values
					ArrayList<common.Properties> geo = rdf.getGeoInformation(value);
					for (int j = 0; j < geo.size(); j++) {
						v.add(geo.get(j).value);
					}
					features.add(v);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads the comments on and the blog posts about the current artifact 
	 */
	public void loadBlogs() {
		blogPosts = new ArrayList<BlogBean>();
		comments = new ArrayList<BlogBean>();
		Blog blog = new Blog();
		try {
			// Load comments. That was the easy part.
			comments = blog.getComments(artifactID);

			// Find blog posts in RDF with about=artifactID
			StringBuffer qry = new StringBuffer(1024);
			qry.append("SELECT ?postRdfId ?postDBid "
					+ "WHERE { ?postRdfId <" + SIOC.about.toString() + "> <"+ artifactID + ">. " + 
					" ?postRdfId <"+ SIOC.content.toString() + "> ?postDBid. " +
					" ?postRdfId <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://rdfs.org/sioc/ns#Post> "+
					"} ");
			common.Utility.log.debug(qry.toString());
			String query = qry.toString();
			Connections con = new Connections();
			con.repConnect();

			TupleQuery output = con.getRepConnection().prepareTupleQuery(
					QueryLanguage.SPARQL, query);
			TupleQueryResult result = output.evaluate();
			// For every post found, fetch the data from database
			while (result.hasNext()) {
				BindingSet bindingSet = result.next();
				BlogBean bb = blog.getBlogPost(bindingSet.getValue("postDBid")
						.stringValue());
				blogPosts.add(bb);
			}
			result.close();
			con.repDisconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void loadPreviousVersions(){
		StringBuffer qry = new StringBuffer(1024);
		String query = null;
		TupleQuery output = null;
		TupleQueryResult result = null;
		//previous versions
		qry.append(" SELECT ?resource ");
	    qry.append(" WHERE { ");
	    qry.append(" { <"+artifactID+"> <http://www.policygrid.org/provenance-generic.owl#previousVersion>* ?resource } ");
	    qry.append("  UNION ");	     
	    qry.append(" { ?resource <http://www.policygrid.org/provenance-generic.owl#previousVersion>* <"+artifactID+"> }.");
	    qry.append(" ?resource <http://www.policygrid.org/provenance-generic.owl#versionNumber>+ ?number. ");
	    qry.append(" FILTER (?resource != <"+artifactID+"> ) ");
	    qry.append(" }");
	    qry.append(" ORDER BY <http://www.w3.org/2001/XMLSchema#decimal>(?number)");
		query = qry.toString();
		try {
			con.repConnect();
			output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
			result = output.evaluate();
			while (result.hasNext()) 
			{
				   BindingSet bindingSet = result.next();	
				   previousVersions.add(bindingSet.getValue("resource").stringValue());
			}
			result.close();
			con.repDisconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Loads artifacts related by provenance.
	 */
	public void loadRelatedByProvenance(){
		
	}
	
	/**
	 * Loads artifacts with similar tags.
	 */
	public void loadRelatedByTags() {
		try {
			con.connect();
			Statement st = con.getCon().createStatement();
			
			ResultSet rs = null;
			String qry = 
				"select t2.resource, count(*) as count from tags t1 "+
				"join tags t2 on (t1.tag=t2.tag) "+
				"where " +
				//TODO hack for namespaces
				"t1.resource like '%"+Utility.getLocalName(artifactID)+"' and "+
				"t1.resource <> t2.resource and "+
				"t2.resource is not null and "+
				"t2.resource <> '' "+ 
				"group by t1.resource, t2.resource " +
				"order by count(*) desc";

			
			rs = st.executeQuery(qry);
			con.repConnect();

			TupleQuery output;
			TupleQueryResult result = null;
			while(rs.next())
			{
				String resourceId =  rs.getString("resource");
				qry = "SELECT ?time ?title ?uri "
						+ "WHERE { " +
							"<"+resourceId+"> <"+OurSpacesVRE.timestamp.toString()+"> ?time . "+
							" <"+resourceId+"> <"+ProvenanceGeneric.title.toString()+"> ?title . "+
						    //"OPTIONAL { "+
							" <"+resourceId+"> <"+ProvenanceGeneric.hasURI.toString()+"> ?uri "+
							//" } "+
						"} ";
				if(previousVersions.contains(resourceId))
					continue;
				output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, qry);
				result = output.evaluate();

				Resources res = new Resources();
				res.setID(resourceId);
				// get the title of resource
				if (result.hasNext()) {
					BindingSet bindingSet = result.next();					
					res.setTitle(bindingSet.getValue("title").stringValue());
					res.setURL(bindingSet.getValue("uri").stringValue());
					Value  tvalue = bindingSet.getValue("time");
					double timestamp = Double.parseDouble(tvalue.stringValue());					
					res.setTimeStamp(timestamp);
					res.setDate(rdf.getFormattedDate(timestamp));
					//We add only those resources that exist in RDF repository
					relatedByTags.add(res);
				}				
			}
			if(result != null)
				result.close();
			con.repDisconnect();
			rs.close();
			st.close();
			con.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public RDFi getRdf() {
		return rdf;
	}

	public void setRdf(RDFi rdf) {
		this.rdf = rdf;
	}

	/**
	 * returns the blog's database numerical ID
	 * @return
	 */
	public String getArtifactID()
	{
		return artifactID;
	}
	
	public int getUserID()
	{
		return userid;
	}

	public void setUserID(int userid)
	{
		this.userid = userid;
	}
	
	public String getRDFUserID()
	{
		return rdfUserID;
	}
	
	public void setRdfUserID(String rdfUserID)
	{
		this.rdfUserID = rdfUserID;
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
	 * Sets the artifact's ID
	 * @param id
	 */
	public void setArtifactID(String artifactID)
	{
		this.artifactID = artifactID;
	}
	
	
	/**
	 * Sets the artifact's title
	 * @param title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	public ArrayList<common.Properties> getNiceProperties() {
		return niceProperties;
	}

	public void setNiceProperties(ArrayList<common.Properties> niceProperties) {
		this.niceProperties = niceProperties;
	}

	public String getUploadedOn() {
		return uploadedOn;
	}

	public void setUploadedOn(String uploadedOn) {
		this.uploadedOn = uploadedOn;
	}

	public ArrayList<?> getTags() {
		return tags;
	}

	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDepositedBy() {
		return depositedBy;
	}

	public void setDepositedBy(String depositedBy) {
		this.depositedBy = depositedBy;
	}

	public String getProducedInProject() {
		return producedInProject;
	}

	public void setProducedInProject(String producedInProject) {
		this.producedInProject = producedInProject;
	}

	public ArrayList<BlogBean> getBlogPosts() {
		return blogPosts;
	}

	public void setBlogPosts(ArrayList<BlogBean> blogPosts) {
		this.blogPosts = blogPosts;
	}

	public ArrayList<BlogBean> getComments() {
		return comments;
	}

	public void setComments(ArrayList<BlogBean> comments) {
		this.comments = comments;
	}

	public ArrayList<ArrayList<String>> getFeatures() {
		return features;
	}

	public void setFeatures(ArrayList<ArrayList<String>> features) {
		this.features = features;
	}

	public ArrayList<Resources> getRelatedByTags() {
		return relatedByTags;
	}

	public void setRelatedByTags(ArrayList<Resources> relatedByTags) {
		this.relatedByTags = relatedByTags;
	}

	public ArrayList<Resources> getRelatedByProvenance() {
		return relatedByProvenance;
	}

	public void setRelatedByProvenance(ArrayList<Resources> relatedByProvenance) {
		this.relatedByProvenance = relatedByProvenance;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getRdfUserID() {
		return rdfUserID;
	}

	public ArrayList<String> getPreviousVersions() {
		return previousVersions;
	}

	public void setPreviousVersions(ArrayList<String> previousVersions) {
		this.previousVersions = previousVersions;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

	/**
	 * Returns the resource ID of the project that a particular resource belongs to.  The 
	 * resource is identified by it's unique Resource ID.
	 * 
	 * @param resourceID
	 * @return String containing the project URI
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	/*
	public String getProject(String resourceID) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?project WHERE { ");
		qry.append("<");
		qry.append(resourceID);
		qry.append("> ");
		qry.append("<http://www.policygrid.org/opm-resource.owl#producedInProject> ?project");
		qry.append(" } ");

		
		String project = "";
	
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("project");
			   project = value.stringValue();
		}
		result.close();
		con.repDisconnect();
		return project;
	}*/
	
}
