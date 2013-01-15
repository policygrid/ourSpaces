package common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;

import org.openrdf.OpenRDFException;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.GraphQuery;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryException;
import org.policygrid.ontologies.OurSpacesVRE;
import org.policygrid.ontologies.SIOC;
import org.xml.sax.SAXException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

public class Blog {
	
	Connections con = new Connections();
	
	public Blog()
	{
		
	}
	

	
	/**
	 * Returns blog post with specified id.
	 * 
	 * @param blogID
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public BlogBean getBlogPost(String blogID) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		//Database connection
		con.connect();
		Statement st = con.getCon().createStatement();
				
		//SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select id, container, userid, rdfUserID, date, title, content, rdfBlogID from blog where id=\"");
		qry.append(blogID);
		qry.append("\"");
		
		ResultSet rs = st.executeQuery(qry.toString());
		BlogBean bb = new BlogBean();
		while(rs.next())
		{
			// Creates a new instance of the UserBlogBean JavaBean			
			bb.setContainer(rs.getString("container"));
			bb.setID(rs.getInt("id"));
			bb.setUserID(rs.getInt("userid"));
			bb.setRdfUserID(rs.getString("rdfUserID"));
			bb.setRdfBlogID(rs.getString("rdfBlogID"));
			bb.setContent(rs.getString("content"));
			bb.setTitle(rs.getString("title"));
			bb.setDate(rs.getString("date"));			
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return bb;
	}
	
	/**
	 * Returns all blog posts that are in a particular blog container.
	 * 
	 * @param blogContainerID
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public ArrayList<BlogBean> getBlogPosts(String blogContainerID) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		//Database connection
		con.connect();
		Statement st = con.getCon().createStatement();
		
		// Vector which will contain the blog JavaBeans
		ArrayList<BlogBean> blog = new ArrayList<BlogBean>();
		
		//SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select id, userid, rdfUserID, date, title, content, rdfBlogID from blog where container=\"");
		qry.append(blogContainerID);
		qry.append("\" order by id DESC");
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		while(rs.next())
		{
			// Creates a new instance of the UserBlogBean JavaBean
			BlogBean bb = new BlogBean();
			bb.setContainer(blogContainerID);
			bb.setID(rs.getInt("id"));
			bb.setUserID(rs.getInt("userid"));
			bb.setRdfUserID(rs.getString("rdfUserID"));
			bb.setRdfBlogID(rs.getString("rdfBlogID"));
			bb.setContent(rs.getString("content"));
			bb.setTitle(rs.getString("title"));
			bb.setDate(rs.getString("date"));
			blog.add(bb);
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return blog;
	}
	/**
	 * Returns  blog post for a given tdfblogid.
	 * 
	 * @param postid
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public ArrayList<BlogBean> getBlogPostbyRdfId(String rdfblogid) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		//Database connection
		con.connect();
		Statement st = con.getCon().createStatement();
		
		// Vector which will contain the blog JavaBeans
		ArrayList<BlogBean> blog = new ArrayList<BlogBean>();
		
		//SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select id,userid, rdfBlogID,rdfUserID, date, title, content, container from blog where rdfBlogID=\'");
		qry.append(rdfblogid);
		qry.append("\'");
		common.Utility.log.debug(qry.toString());
		ResultSet rs = st.executeQuery(qry.toString());
		
		while(rs.next())
		{
			// Creates a new instance of the UserBlogBean JavaBean
			BlogBean bb = new BlogBean();
			bb.setContainer(rs.getString("container"));
			bb.setID(rs.getInt("id"));
			bb.setUserID(rs.getInt("userid"));
			bb.setRdfUserID(rs.getString("rdfUserID"));
			bb.setRdfBlogID(rs.getString("rdfBlogID"));
			bb.setContent(rs.getString("content"));
			bb.setTitle(rs.getString("title"));
			bb.setDate(rs.getString("date"));
			blog.add(bb);
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return blog;
	}	
	
	/**
	 * Returns  blog post for a given id.
	 * 
	 * @param postid
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public ArrayList getBlogPost(int postid) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		//Database connection
		con.connect();
		Statement st = con.getCon().createStatement();
		
		// Vector which will contain the blog JavaBeans
		ArrayList blog = new ArrayList<BlogBean>();
		
		//SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select id,userid, rdfUserID, date, title, content, rdfBlogID, container from blog where id=");
		qry.append(postid);
		
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		while(rs.next())
		{
			// Creates a new instance of the UserBlogBean JavaBean
			BlogBean bb = new BlogBean();
			bb.setContainer(rs.getString("container"));
			bb.setID(rs.getInt("id"));
			bb.setUserID(rs.getInt("userid"));
			bb.setRdfUserID(rs.getString("rdfUserID"));
			bb.setRdfBlogID(rs.getString("rdfBlogID"));
			bb.setContent(rs.getString("content"));
			bb.setTitle(rs.getString("title"));
			bb.setDate(rs.getString("date"));
			blog.add(bb);
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return blog;
	}
	/**
	 * Returns all blog posts that are in a particular blog container or about a particular user.
	 * 
	 * @param blogContainerID
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public ArrayList<BlogBean> getBlogPostsabout(String blogContainerID,int userid) throws  ParserConfigurationException, SAXException, SQLException,IOException, InstantiationException, IllegalAccessException,ServletException, ClassNotFoundException, OpenRDFException
	{
		//Database connection
		con.connect();
		Statement st = con.getCon().createStatement();
		
		// Vector which will contain the blog JavaBeans
		ArrayList<BlogBean> blog = new ArrayList<BlogBean>();
		
		//SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select id, userid, rdfUserID, date, title, content, rdfBlogID from blog where container=\"");
		qry.append(blogContainerID);
		qry.append("\" order by id DESC");
		
		ResultSet rs = st.executeQuery(qry.toString());
		ArrayList <Integer> blogsfound=new ArrayList <Integer>();
		while(rs.next())
		{
			// Creates a new instance of the UserBlogBean JavaBean
			BlogBean bb = new BlogBean();
			bb.setContainer(blogContainerID);
			blogsfound.add(rs.getInt("id"));
			bb.setID(rs.getInt("id"));
			bb.setUserID(rs.getInt("userid"));
			bb.setRdfUserID(rs.getString("rdfUserID"));
			bb.setRdfBlogID(rs.getString("rdfBlogID"));
			bb.setContent(rs.getString("content"));
			bb.setTitle(rs.getString("title"));
			bb.setDate(rs.getString("date"));
			blog.add(bb);
		}
		
		rs.close();
		
		User u=new User();
		
		//ArrayList<BlogBean> aboutList = new ArrayList<BlogBean>();
		qry = new StringBuffer(1024);
		qry.append("SELECT ?postid WHERE { ?postid <"+SIOC.about.toString()+"> <"+u.getFOAFID(userid)+"> } ");
		
		String resource = "";
	
		String query = qry.toString();
		//common.Utility.log.debug(query);
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("postid");
			   resource = value.stringValue();
			   BlogBean bb=new BlogBean();
			   bb=(BlogBean)getBlogPostbyRdfId(resource).get(0);
			   if (!blogsfound.contains(bb.getID()))
				   blog.add(bb);
			   else
				   blogsfound.add(bb.getID());
		}
		result.close();
		con.repDisconnect();
		
		
		qry = new StringBuffer(1024);
		qry.append("select c.about as about,b.id as id,b.container as container, b.userid as userid,b.rdfuserid as rdfuserid, b.rdfBlogID as rdfBlogID, b.content as content, b.date as date, b.title as title from comment c, blog b where c.about=b.rdfBlogID and  c.userid=");
		qry.append(userid);
		
		//common.Utility.log.debug(qry.toString());
		 rs = st.executeQuery(qry.toString());
		
		while(rs.next())
		{
			BlogBean bb = new BlogBean();
			bb.setContainer(rs.getString("container"));
			blogsfound.add(rs.getInt("id"));
			bb.setID(rs.getInt("id"));
			bb.setUserID(rs.getInt("userid"));
			bb.setRdfUserID(rs.getString("rdfUserID"));
			bb.setRdfBlogID(rs.getString("rdfBlogID"));
			bb.setContent(rs.getString("content"));
			bb.setTitle(rs.getString("title"));
			bb.setDate(rs.getString("date"));
			blog.add(bb);
		}
		
		
		rs.close();
		st.close();
		con.disconnect();
		
		
		
		
		return blog;
	}
	public ArrayList<String> getAbout(String postID) throws ServletException, IOException, OpenRDFException
	{
		ArrayList<String> aboutList = new ArrayList<String>();
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?about WHERE { <"+postID+"> <"+SIOC.about.toString()+"> ?about } ");
		
		String resource = "";
	
		String query = qry.toString();

		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("about");
			   resource = value.stringValue();
			   aboutList.add(resource);
		}
		result.close();
		con.repDisconnect();

		return aboutList;
	}
	
	public void deleteBlogPost(String rdfBlogID) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, RepositoryException, MalformedQueryException, QueryEvaluationException
	{
		con.connect();
			Statement st = con.getCon().createStatement();
			String sql = "delete from blog where rdfBlogID=\""+rdfBlogID+"\"";
			st.executeUpdate(sql);
		con.disconnect();
		
		common.Utility.log.debug("BLOG ID: " + rdfBlogID);
		
		String query1 = "CONSTRUCT {<" + rdfBlogID + "> ?y ?z.} WHERE {<" + rdfBlogID + "> ?y ?z.}";
		String query2 = "CONSTRUCT {?y ?z <" + rdfBlogID + ">.} WHERE {?y ?z <" + rdfBlogID + ">.}";
		
		con.repConnect();

			GraphQuery queryA = con.getRepConnection().prepareGraphQuery(QueryLanguage.SPARQL, query1);
			GraphQueryResult result1 = queryA.evaluate();
			con.getRepConnection().remove(result1);

			GraphQuery queryB = con.getRepConnection().prepareGraphQuery(QueryLanguage.SPARQL, query2);
			GraphQueryResult result2 = queryB.evaluate();
			con.getRepConnection().remove(result2);
			
		con.repDisconnect();
	}
	

	public void addBlogPost(String containerID, String title, String content, ArrayList<String> aboutList, int userid, String rdfUserID) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, OpenRDFException, IOException
	{
		// Database Connection
		con.connect();
		Statement st = con.getCon().createStatement();
		
		// Creation of the Calender instance to get the current date
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DATE);
		int month = c.get(Calendar.MONTH) + 1;
		int year = c.get(Calendar.YEAR);
		String date = day+" / "+month+" / "+year; // current date
		
			
		String blogPostID = "http://rdfs.org/sioc/ns#" + UUID.randomUUID().toString();
			
		  String sql = "INSERT INTO blog (userid, rdfUserID, container, date, title, content, rdfBlogID) VALUES(?,?,?,?,?,?,?)";
			
		  PreparedStatement pstmt = con.getCon().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
	      pstmt.setInt(1, userid);
	      pstmt.setString(2, rdfUserID);
	      pstmt.setString(3, containerID);
	      pstmt.setString(4,  date);
	      pstmt.setString(5, title);
	      pstmt.setString(6, content);
	      pstmt.setString(7, blogPostID);
	
	      // execute query, and return number of rows created
	      pstmt.executeUpdate();
		
	      StringBuffer qry = new StringBuffer(1024);
			qry.append("select id from blog where rdfBlogID=\'");
			qry.append(blogPostID);
			qry.append("\'");
		
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		int i = 0;
		
		while(rs.next())
		{
			i = rs.getInt("id");
		}
		
		st.close();
		con.disconnect();

		RDFi rdf = new RDFi();
		Model model = ModelFactory.createDefaultModel();
		
		Resource creator = model.createResource(rdfUserID);
		Resource container = model.createResource(containerID);
		Resource blogPost = model.createResource(blogPostID);
		blogPost.addProperty(RDF.type, SIOC.Post);
		blogPost.addProperty(SIOC.content, ""+i);
		blogPost.addProperty(SIOC.title, title);
		
		blogPost.addProperty(SIOC.has_creator, creator);
		creator.addProperty(SIOC.creator_of, blogPost);
		
		common.Utility.log.debug("===================== BLOG CONTAINER ========= "+containerID);
		common.Utility.log.debug("===================== BLOG POST ========= "+blogPost);
		blogPost.addProperty(SIOC.has_container, container);
		container.addProperty(SIOC.container_of, blogPost);
		
		for(int j = 0; j < aboutList.size(); j++)
		{
			String aboutID = (String) aboutList.get(j);
			Resource about = model.createResource(aboutID);
			blogPost.addProperty(SIOC.about, about);
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();	
		common.Utility.log.debug("===================== BLOG RDF ========= "+blogPost);
		model.write(System.out);
		
		model.write(out);
		InputStream in = new ByteArrayInputStream(out.toByteArray());
		rdf.write(in);
	}
	
	public String addComment(String content, String aboutID, int userid, String rdfUserID) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, OpenRDFException, IOException
	{
		// Database Connection
		con.connect();
		Statement st = con.getCon().createStatement();
		
		// Creation of the Calender instance to get the current date
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DATE);
		int month = c.get(Calendar.MONTH);
		int year = c.get(Calendar.YEAR);
		String date = day+" / "+month+" / "+year; // current date
		
		content = content.replaceAll("'", "\\\\'");
		String rdfCommentID = "http://www.policygrid.org/ourspacesVRE.owl#" + UUID.randomUUID().toString();
		
		String sql = "INSERT INTO comment (userid, rdfUserID, about, date, content, rdfCommentID) " +
				"VALUES("+userid+",'"+rdfUserID+"','"+aboutID+"','"+date+"','"+content+"','"+rdfCommentID+"')";
		st.executeUpdate(sql);
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select id from comment where rdfCommentID=\'");
		qry.append(rdfCommentID);
		qry.append("\'");
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		int i = 0;
		
		while(rs.next())
		{
			i = rs.getInt("id");
		}
		
		st.close();
		con.disconnect();

		RDFi rdf = new RDFi();
		Model model = ModelFactory.createDefaultModel();
		
		Resource creator = model.createResource(rdfUserID);
		Resource about = model.createResource(aboutID);
		Resource comment = model.createResource(rdfCommentID);
		comment.addProperty(RDF.type, OurSpacesVRE.Comment);
		comment.addProperty(SIOC.content, ""+i);
		comment.addProperty(SIOC.about, about);
		
		comment.addProperty(SIOC.has_creator, creator);
		creator.addProperty(SIOC.creator_of, comment);
		


		ByteArrayOutputStream out = new ByteArrayOutputStream();	
		model.write(out);
		InputStream in = new ByteArrayInputStream(out.toByteArray());
		rdf.write(in);
		
		return date;
	}

	/**
	 * Returns the comment for given id
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<BlogBean> getComment(int id) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		//Database connection
		con.connect();
		Statement st = con.getCon().createStatement();
		
		// Vector which will contain the blog JavaBeans
		ArrayList<BlogBean> blog = new ArrayList<BlogBean>();
		
		//SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select * from comment where id=");
		qry.append(id);
		
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		while(rs.next())
		{
			// Creates a new instance of the UserBlogBean JavaBean
			BlogBean bb = new BlogBean();
			bb.setAbout(rs.getString("about"));
			bb.setID(rs.getInt("id"));
			bb.setUserID(rs.getInt("userid"));
			bb.setRdfUserID(rs.getString("rdfUserID"));
			bb.setRdfCommentID(rs.getString("rdfCommentID"));
			bb.setContent(rs.getString("content"));
			bb.setDate(rs.getString("date"));
			blog.add(bb);
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return blog;
	}
	/**
	 * Returns all comments about a particular resource
	 * 
	 * @param about
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<BlogBean> getComments(String about) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		//Database connection
		con.connect();
		Statement st = con.getCon().createStatement();
		
		// Vector which will contain the blog JavaBeans
		ArrayList<BlogBean> blog = new ArrayList<BlogBean>();
		
		//SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select * from comment where about=\"");
		qry.append(about);
		qry.append("\" order by id DESC");
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		while(rs.next())
		{
			// Creates a new instance of the UserBlogBean JavaBean
			BlogBean bb = new BlogBean();
			bb.setAbout(about);
			bb.setID(rs.getInt("id"));
			bb.setUserID(rs.getInt("userid"));
			bb.setRdfUserID(rs.getString("rdfUserID"));
			bb.setRdfCommentID(rs.getString("rdfCommentID"));
			bb.setContent(rs.getString("content"));
			bb.setDate(rs.getString("date"));
			blog.add(bb);
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return blog;
	}
	
	/**
	 * Returns the amount of comments a particular resource/activity has.
	 * 
	 * @param about
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public int getCommentCount(String about) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		//Database connection
		con.connect();
		Statement st = con.getCon().createStatement();
		
		// Vector which will contain the blog JavaBeans
		int count = 0;
		
		//SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select COUNT(id) from comment where about=\"");
		qry.append(about);
		qry.append("\"");
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		while(rs.next())
		{
			count = rs.getInt("COUNT(id)");
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return count;
	}

}
