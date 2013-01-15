package common;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.servlet.ServletException;

import org.openrdf.OpenRDFException;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryException;

/**
 * JavaBean class intended to represent various resources
 * 
 * @author Richard Reid
 * @version 1.0
 */

public class Resources 
{
	
	public String title;
	public String URL;
	public String restriction;
	public ArrayList authors;
	public double timeStamp;
	public String date;
	public String depositor;
	public String depositorID;
	public String id;
	public String type;
    public String lat;
	public String lon;
	public String geop;
	public String previousVersion;
	
	public void init(){
		RDFi rdf = new RDFi();
		long time;
		try {
			this.setTitle(rdf.getResourceTitle(id));
			this.setURL(rdf.getResourceURL(id));
			this.setTimeStamp(rdf.getResourceTimestamp(id));
			time = (long)rdf.getResourceTimestamp(id);
			Date date = new Date(time);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String fulldate = sdf.format(date);
			this.setDate(fulldate);
			this.versionNumber = rdf.getResourceVersion(id);
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OpenRDFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getPreviousVersion() {
		return previousVersion;
	}

	public void setPreviousVersion(String previousVersion) {
		this.previousVersion = previousVersion;
	}

	public String versionNumber;
	
	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}


	public String getDepositorID() {
		return depositorID;
	}

	public void setDepositorID(String depositorID) {
		this.depositorID = depositorID;
	}
	
	public String getGeop() {
		return geop;
	}

	public void setGeop(String geop) {
		this.geop = geop;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}


	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Resources()
	{
		
	}
	
	public String getID()
	{
		return id;
	}
	
	/**
	 * Returns the resource title
	 * @return 
	 */
	public String getTitle()
	{
		if(versionNumber != null && !"".equals(versionNumber) && Integer.parseInt(versionNumber) > 1){
			return title+" (v"+versionNumber+")";
		}
		else{
			return title;
		}
	}
	
	/**
	 * Returns the resource URL
	 * @return
	 */
	public String getURL()
	{
		return URL;
	}
	
	/**
	 * Returns the resource restriction
	 * @return
	 */
	public String getRestriction()
	{
		return restriction;
	}
	
	public String getDepositor()
	{
		return depositor;
	}
	
	public void setID(String id)
	{
		this.id = id;
	}
	
	public void setDepositor(String name){
		depositor = name;
	}
	
	/**
	 * Returns a list containing the resource authors
	 * @return
	 */
	public ArrayList getAuthors()
	{
		return authors;
	}
	
	public double getTimeStamp(){
		return timeStamp;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public void setTimeStamp(double timeStamp)
	{
		this.timeStamp = timeStamp;
	}
	
	/**
	 * Sets the resource's title
	 * @param title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	/**
	 * Sets the resource's URL
	 * @param URL
	 */
	public void setURL(String URL)
	{
		this.URL = URL;
	}
	
	/**
	 * Sets the resource's restriction
	 * @param restriction
	 */
	public void setRestriction(String restriction)
	{
		this.restriction = restriction;
	}
	
	/**
	 * Sets the resource's authors.
	 * @param authors
	 */
	public void setAuthors(ArrayList authors)
	{
		this.authors = authors;
	}
	
	public Vector getComment(int id)throws InstantiationException,
	IllegalAccessException, ClassNotFoundException, SQLException {
		Vector comments=new Vector();
		// Connects to the database
		Connections con = new Connections();
		con.connect();
		Statement st = con.getCon().createStatement();
		StringBuffer qry = new StringBuffer(1024);
		
		qry.append("select * from comment  where id=");
		qry.append(id);
		
		common.Utility.log.debug(qry.toString());
		ResultSet rs = st.executeQuery(qry.toString());
		while (rs.next()) {
			BlogBean bb = new BlogBean();
			bb.setAbout(rs.getString("about"));
			bb.setID(rs.getInt("id"));
			bb.setUserID(rs.getInt("userid"));
			bb.setRdfUserID(rs.getString("rdfUserID"));
			bb.setRdfCommentID(rs.getString("rdfCommentID"));
			bb.setContent(rs.getString("content"));
			bb.setDate(rs.getString("date"));
			comments.add(bb);
		}
		
		return comments;
	}
	
	
	
	public Vector getComments()throws InstantiationException,
	IllegalAccessException, ClassNotFoundException, SQLException {
		Vector comments=new Vector();
		// Connects to the database
		Connections con = new Connections();
		con.connect();
		Statement st = con.getCon().createStatement();
		StringBuffer qry = new StringBuffer(1024);
		
		qry.append("select * from comment  where about=\'");
		qry.append(id);
		qry.append("'");
		common.Utility.log.debug(qry.toString());
		ResultSet rs = st.executeQuery(qry.toString());
		while (rs.next()) {
			BlogBean bb = new BlogBean();
			bb.setAbout(id);
			bb.setID(rs.getInt("id"));
			bb.setUserID(rs.getInt("userid"));
			bb.setRdfUserID(rs.getString("rdfUserID"));
			bb.setRdfCommentID(rs.getString("rdfCommentID"));
			bb.setContent(rs.getString("content"));
			bb.setDate(rs.getString("date"));
			comments.add(bb);
		}
		
		return comments;
	}
	
	/**
	 * Returns the title of the resource.
	 * @param uri
	 * @return
	 */
	/*public String getTitle(){
		String title = "";
		StringBuffer qry = new StringBuffer(1024);
		//previous versions
		qry.append("SELECT ?title ?version ");
	    qry.append("WHERE { ");
	    qry.append("<"+id+"> <http://www.policygrid.org/provenance-generic.owl#title> ?title . ");
	    qry.append("OPTIONAL {<"+id+"> <http://www.policygrid.org/provenance-generic.owl#versionNumber>  ?version} ");
	    qry.append("}");
	    String query = qry.toString();
		try {
			Connections con = new Connections();
			con.repConnect();
			TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
			TupleQueryResult result = output.evaluate();
			while (result.hasNext()) 
			{
				BindingSet bindingSet = result.next();
				title = bindingSet.getValue("title").stringValue();
				if(bindingSet.hasBinding("version")){
					title += " ("+bindingSet.getValue("version").stringValue()+")";					
				}
						
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return title;
	}*/
}



