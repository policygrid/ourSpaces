package common;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;

import org.openrdf.OpenRDFException;
import org.xml.sax.SAXException;

/**
 * JavaBean class intended to represent a single Tag when displayed on a page.
 * 
 * @author Richard Reid
 * @version 1.0
 */

public class Tag
{
	
	public String tag;
	public int size;
	public String user;
	public String resource;
	public String property;
	Connections con = new Connections();
	
	public Tag()
	{
		
	}
	
	/**
	 * Returns the actual Tag
	 * @return
	 */
	public String getTag()
	{
		return tag;
	}
	
	/**
	 * Returns the frequency of the tag
	 * @return
	 */
	public int getSize()
	{
		return size;
	}
	
	/**
	 * Returns the resource ID of the user
	 * @return
	 */
	public String getUser()
	{
		return user;
	}
	
	/**
	 * Returns the resource a tag is linked with
	 * @return
	 */
	public String getResource()
	{
		return resource;
	}
	
	/**
	 * Returns the property a tag is linked with
	 * @return
	 */
	public String getProperty()
	{
		return property;
	}
	
	/**
	 * Sets the tag's user
	 * @param user
	 */
	public void setUser(String user)
	{
		this.user = user;
	}
	
	/**
	 * Sets the tag's resource
	 * @param resource
	 */
	public void setResource(String resource)
	{
		this.resource = resource;
	}
	
	/**
	 * Sets the tags's property
	 * @param property
	 */
	public void setProperty(String property)
	{
		this.property = property;
	}
	
	/**
	 * Set's the actual tag
	 * @param tag
	 */
	public void setTag(String tag)
	{
		this.tag = tag;
	}
	
	/**
	 * Sets the tag's frequency
	 * @param size
	 */
	public void setSize(int size)
	{
		this.size = size;
	}
	
	/**
	 * 
	 * Connect to the database
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void connect() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		con.connect();
	}
	
	/**
	 * 
	 * Disconnecting from the database
	 * 
	 * @throws SQLException
	 */
	public void disconnect() throws SQLException
	{
		con.disconnect();
	}
	
	public Vector search(String tag1) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		Vector tags = new Vector();
		
		connect();
		
		Statement st = con.getCon().createStatement();
		
		String qry = "select tag, COUNT(tag) from tags where tag like '%"+tag1+"%' group by tag";
		ResultSet rs = st.executeQuery(qry);
		
		while(rs.next())
		{
			Tag tag = new Tag();
			tag.setTag(rs.getString("tag"));
			tag.setSize(rs.getInt("COUNT(tag)"));
			tags.add(tag);
		}
		
		rs.close();
		st.close();
		disconnect();
		
		return tags;
	}
	
	
	/**
	 * Retrieves all the tags from the database based on a user's resource ID.  This returns
	 * all tags that belong to a particular user.
	 * 
	 * Tags are returned in a Vector containing multiple Tag JavaBeans.
	 * 
	 * @param resourceID
	 * @return Vector of Tags
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public Vector getProjectTags(String projectID) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		Vector tags = new Vector();
		
		connect();
		
		Statement st = con.getCon().createStatement();
		
		//String qry = "select tag, COUNT(tag) as frequency  FROM tags WHERE tag IN (select tag, COUNT(tag) as frequency from tags where user='"+resourceID+"' group by tag order by COUNT(tag) DESC LIMIT 0, 50) order by tag";
		String qry = "";
		qry = qry + "select main.tag, count(main.tag) as frequency from tags as main " +
		            "INNER JOIN (select tag, COUNT(tag) as frequency from tags, files where tags.resource = files.resourceID AND files.containerID = '"+projectID+"' group by tag order by COUNT(tag) DESC LIMIT 50) AS sub "+
		            "ON main.tag = sub.tag GROUP BY main.tag ORDER BY main.tag;";
		
		ResultSet rs = st.executeQuery(qry);
		int count = 0;
		while(rs.next())
		{
			
			Tag tag = new Tag();
			tag.setTag(rs.getString("tag"));
			tag.setSize(rs.getInt("frequency"));
			tags.add(tag);
			
		}
		
		rs.close();
		st.close();
		disconnect();
		
		return tags;
	}
	
	/**
	 * Retrieves all the tags from the database based on a user's resource ID.  This returns
	 * all tags that belong to a particular user.
	 * 
	 * Tags are returned in a Vector containing multiple Tag JavaBeans.
	 * 
	 * @param resourceID
	 * @return Vector of Tags
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public Vector getTags(String resourceID) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		Vector tags = new Vector();
		
		connect();
		
		Statement st = con.getCon().createStatement();
		
		//String qry = "select tag, COUNT(tag) as frequency  FROM tags WHERE tag IN (select tag, COUNT(tag) as frequency from tags where user='"+resourceID+"' group by tag order by COUNT(tag) DESC LIMIT 0, 50) order by tag";
		String qry = "";
		qry = qry + "select main.tag, count(main.tag) as frequency from tags as main " +
		            "INNER JOIN (select tag, COUNT(tag) as frequency from tags where user='"+resourceID+"' group by tag order by COUNT(tag) DESC LIMIT 50) AS sub "+
		            "ON main.tag = sub.tag GROUP BY main.tag ORDER BY main.tag;";
		
		ResultSet rs = st.executeQuery(qry);
		int count = 0;
		while(rs.next())
		{
			
			Tag tag = new Tag();
			tag.setTag(rs.getString("tag"));
			tag.setSize(rs.getInt("frequency"));
			tags.add(tag);
			
		}
		
		rs.close();
		st.close();
		disconnect();
		
		return tags;
	}
	
	public void addTag(String tag, String resource, String foafID) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException
	{
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DATE);
		int month = c.get(Calendar.MONTH) + 1;
		int year = c.get(Calendar.YEAR);
		String date = day+" / "+month+" / "+year;
		
		User user = new User();
		int id = user.getUserIDFromFOAF(foafID);
		
		con.connect();
		
		PreparedStatement pStmt;
		
		pStmt = con.getCon().prepareStatement("insert into tags (tag, resource, user, time) values(\""+tag.toLowerCase()+"\",\""+resource+"\",\""+foafID+"\",\""+date+"\")");
	
	
		pStmt.executeUpdate();
		
	}
	
	
	/**
	 * Retrieves all tags from the database regardless of ownership.
	 * 
	 * @return Vector of Tags
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public Vector getAllTags() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		Vector tags = new Vector();
		
		connect();
		
		Statement st = con.getCon().createStatement();
		
		String qry = "select tag, COUNT(tag) from tags group by tag order by tag";
		ResultSet rs = st.executeQuery(qry);
		
		while(rs.next())
		{
			Tag tag = new Tag();
			tag.setTag(rs.getString("tag"));
			tag.setSize(rs.getInt("COUNT(tag)"));
			tags.add(tag);
		}
		
		rs.close();
		st.close();
		disconnect();
		
		return tags;
	}
	
	/**
	 * Clears the temporary tag table.
	 * 
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public void clearTable() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		connect();
		
		Statement st = con.getCon().createStatement();
		String sql = "delete from temp";
		st.execute(sql);
		st.close();
		disconnect();
	}

	
	/**
	 * Returns all tags belonging to a particular resource.  Resource tags are added to a
	 * temporary list.  That list will comprise of all resources that make up a project,
	 * allowing tags to be generated and counted based on an entire project's resources.
	 * 
	 * The temp table acts as a cache.
	 * 
	 * @param resources
	 * @return Vector of Tags
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public Vector<Tag> getResourceTags(ArrayList<String> resources, int limit) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		Vector<Tag> tags = new Vector<Tag>();
		if(resources == null || resources.size() == 0)
			return tags;
		
		connect();
		
		Statement st = con.getCon().createStatement();
		String recIDs="'";
		ResultSet rs = null;
		for(int i = 0; i < resources.size(); i++)
		{
			String resourceID = (String) resources.get(i);
			recIDs=recIDs+resourceID+"','";
		}
		recIDs=recIDs.substring(0, recIDs.length()-2);
		//common.Utility.log.debug(recIDs);
		String qry = "select tag, COUNT(tag) from tags where resource in ("+recIDs+") group by tag order by COUNT(tag) DESC Limit "+limit;
		//common.Utility.log.debug(qry);
		rs = st.executeQuery(qry);
		while(rs.next())
		{
			Tag tag = new Tag();
			tag.setTag(rs.getString("tag"));
			tag.setSize(rs.getInt("COUNT(tag)"));
			tags.add(tag);
		}
		rs.close();
		st.close();
		disconnect();
		
		Collections.shuffle(tags);
		return tags;
	}
	
	public Vector<Tag> getResourceTags(ArrayList<String> resources) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		return getResourceTags(resources,9999);
	}
	
	/**
	 * Retrieves information about a particular tag specified.  The information is
	 * added to a Vector containing an instance(s) of a Tag JavaBean.
	 * 
	 * @param tag
	 * @return Vector of a Tag
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public Vector<Tag> getTagInfo(String tag) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		Vector<Tag> tagInfo = new Vector<Tag>();
		
		connect();
		
		Statement st = con.getCon().createStatement();
		
		String qry = "select * from tags where tag='"+tag+"' AND resource IS NOT NULL";
		ResultSet rs = st.executeQuery(qry);
		
		while(rs.next())
		{
			Tag info = new Tag();
				info.setResource(rs.getString("resource"));
				info.setProperty(rs.getString("property"));
				info.setUser(rs.getString("user"));
				tagInfo.add(info);
		}
		
		rs.close();
		st.close();
		disconnect();
		
		return tagInfo;
	}
	
	/**
	 * Finds the list of users that used a particular tag to describe a resource
	 * @param tagName
	 * @return list of FOAF id
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public Collection<String> getUsersOfTag(String tagName) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		Collection<String> tagUsers = new ArrayList<String>();
		
		connect();
		Statement st = con.getCon().createStatement();
		
		String qry = "select distinct user from tags where tag='"+tagName+"' AND resource IS NOT NULL";
		ResultSet rs = st.executeQuery(qry);
		
		while(rs.next()){
			String userID = rs.getString("user");
			if (Utility.isNotNull(userID) && !tagUsers.contains(userID)) {
				tagUsers.add(rs.getString("user"));
			}
		}
		
		rs.close();
		st.close();
		disconnect();
		
		return tagUsers;
	}
}
