package common;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;

import org.openrdf.OpenRDFException;
import org.policygrid.ontologies.FOAF;
import org.xml.sax.SAXException;

/**
 * @author EP
 * @author RR
 * @version 1.0
 */
public class AccessControl {

	public static boolean checkPermission(String ptype, String user, String resource) throws ServletException, IOException, OpenRDFException, ParserConfigurationException, SAXException {
		// Read the database
		boolean permission = false;

		try {
			Connections con = new Connections();

			con.connect();

			String qry = "select * from permissions where (groupID <> '' OR groupID IS NOT NULL) and resourceID=?";

			PreparedStatement pstmt = con.getCon().prepareStatement(qry);
			pstmt.setString(1, resource);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String groupID = rs.getString("groupID");
	            ArrayList users = getUsersFromGroup(groupID); 		
				if (users.contains(user)) permission = rs.getBoolean(ptype);
			}

			rs.close();
			pstmt.close();
			con.disconnect();

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Connections con = new Connections();

			con.connect();

			String qry = "select * from permissions where userID=? and resourceID=?";

			PreparedStatement pstmt = con.getCon().prepareStatement(qry);
			pstmt.setString(1, user);
			pstmt.setString(2, resource);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				permission = rs.getBoolean(ptype);
			}

			rs.close();
			pstmt.close();
			con.disconnect();

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		
		return permission;
	}
	
	/**
	 * Adds an individual user permission to a particular RDF resource. The resource an be anything
	 * in RDF.
	 * 
	 * ptype must be either view, edit or download.
	 * 
	 * @param ptype
	 * @param user
	 * @param resource
	 * @param value
	 */
	public static void setUserPermission(String ptype, String user, String resource, boolean value) {

		try {
			if (userPermissionExists(user, resource)) {
				
				Connections con = new Connections();
				con.connect();

				String qry = "UPDATE permissions SET "+ptype+"=? WHERE userID = ? and resourceID = ?";

				PreparedStatement pstmt = con.getCon().prepareStatement(qry);
				pstmt.setBoolean(1, value);
				pstmt.setString(2, user);
				pstmt.setString(3, resource);

				int  rs = pstmt.executeUpdate();

				pstmt.close();
				con.disconnect();

			} else {

				Connections con = new Connections();
				con.connect();

				String qry = "INSERT INTO permissions(userID,resourceID,"+ptype+") values (?,?,?)";

				PreparedStatement pstmt = con.getCon().prepareStatement(qry);
				pstmt.setString(1, user);
				pstmt.setString(2, resource);
				pstmt.setBoolean(3, value);

				int  rs = pstmt.executeUpdate();

				pstmt.close();
				con.disconnect();
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList getUsersFromGroup(String groupID) throws ServletException, IOException, OpenRDFException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParserConfigurationException, SAXException {
		ArrayList users = new ArrayList();
		RDFi rdf = new RDFi();
		String type = rdf.getSingleResourceType(groupID);
		
		if (type.equals("OurSpacesAccount")) {
			User user = new User();
			int userID = user.getUserIDFromRDFID(groupID);
			Vector contacts = user.getContacts(userID);
			for (int i = 0; i < contacts.size(); i++) {
			  User contact = (User)contacts.get(i);
			  String foafID = user.getFOAFID(contact.getContactID());
			  if (!foafID.equals("")) users.add(foafID);
			  //common.Utility.log.debug("User in group (CONTACT): "+foafID);
			}
		}
		
       if (type.equals("Project")) {
    	   Project pr = new Project();
    	   ArrayList members = pr.getProjectMembers(groupID);
    	   User user = new User();
    	   for(int i = 0; i < members.size(); i++)
			{ 
				MemberBean mb = (MemberBean) members.get(i);
				int userid = mb.getUserID();
				String foafID = user.getFOAFID(userid);
				if (!foafID.equals("")) users.add(foafID);
				//common.Utility.log.debug("User in group (PR. MEMBER): "+user.getFOAFID(userid));
			}
		}
        
        return users;
	}
	
	
	/**
	 * Adds a Group permission to a particular RDF resource. The resource an be anything
	 * in RDF.
	 * 
	 * ptype must be either view, edit or download.
	 * 
	 * @param ptype
	 * @param user
	 * @param resource
	 * @param value
	 * @throws OpenRDFException 
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static void setGroupPermission(String ptype, String groupID, String resource, boolean value) throws ServletException, IOException, OpenRDFException, ParserConfigurationException, SAXException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

      
      
		try {
			if (groupPermissionExists(groupID, resource)) {
				
				Connections con = new Connections();
				con.connect();

				String qry = "UPDATE permissions SET "+ptype+"=? WHERE groupID = ? and resourceID = ?";

				PreparedStatement pstmt = con.getCon().prepareStatement(qry);
				pstmt.setBoolean(1, value);
				pstmt.setString(2, groupID);
				pstmt.setString(3, resource);

				int  rs = pstmt.executeUpdate();

				pstmt.close();
				con.disconnect();

			} else {

				Connections con = new Connections();
				con.connect();

				String qry = "INSERT INTO permissions(userID,groupID,resourceID,"+ptype+") values ('',?,?,?)";

				PreparedStatement pstmt = con.getCon().prepareStatement(qry);
				pstmt.setString(1, groupID);
				pstmt.setString(2, resource);
				pstmt.setBoolean(3, value);

				int  rs = pstmt.executeUpdate();

				pstmt.close();
				con.disconnect();
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteUserPermission(String foafID, String resource) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		User user = new User();
		
		Connections con = new Connections();
		con.connect();
		Statement st = con.getCon().createStatement();
		String qry = "delete from permissions where userID=\""+foafID+"\" and resourceID=\""+resource+"\"";

		st.executeUpdate(qry);

		st.close();
		con.disconnect();	
	}
	
	public static void deleteGroupPermission(String groupID, String resource) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		User user = new User();
		
		Connections con = new Connections();
		con.connect();
		Statement st = con.getCon().createStatement();
		String qry = "delete from permissions where groupID=\""+groupID+"\" and resourceID=\""+resource+"\"";
		
		common.Utility.log.debug("Delete GROUP :"+qry);

		st.executeUpdate(qry);

		st.close();
		con.disconnect();	
	}
	
	public static void setPublic(String resource) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{

				Connections con = new Connections();
				con.connect();

				String qry = "INSERT INTO public(resourceID) values (?)";

				PreparedStatement pstmt = con.getCon().prepareStatement(qry);
				pstmt.setString(1, resource);


				int  rs = pstmt.executeUpdate();

				pstmt.close();
				con.disconnect();

	}
	
	public static void deletePublic(String resource) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{

				Connections con = new Connections();
				con.connect();
				Statement st = con.getCon().createStatement();
				String qry = "delete from public where resourceID=\""+resource+"\"";

				st.executeUpdate(qry);

				st.close();
				con.disconnect();

	}
	
	public boolean checkPublic(String resource) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		boolean check = false;
				Connections con = new Connections();
				con.connect();
				Statement st = con.getCon().createStatement();
				
				String qry = "select resourceID from public where resourceID=?";

				PreparedStatement pstmt = con.getCon().prepareStatement(qry);
				pstmt.setString(1, resource);

				ResultSet rs = pstmt.executeQuery();
				while(rs.next())
				{
					check = true;
				}

				st.close();
				con.disconnect();
				
		return check;
	}
	
	/**
	 * Checks if a permission already exists for a particular resource.
	 * s
	 * @param user
	 * @param resource
	 * @return
	 */
	public static boolean userPermissionExists(String user, String resource) {
		// Read the database
		boolean permissionExists = false;

		try {
			Connections con = new Connections();

			con.connect();

			String qry = "select * from permissions where userID=? and resourceID=?";

			PreparedStatement pstmt = con.getCon().prepareStatement(qry);
			pstmt.setString(1, user);
			pstmt.setString(2, resource);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				permissionExists = true;
			}

			rs.close();
			pstmt.close();
			con.disconnect();

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return permissionExists;
	}
	
	/**
	 * Checks if a permission already exists for a particular resource.
	 * s
	 * @param user
	 * @param resource
	 * @return
	 */
	public static boolean groupPermissionExists(String groupID, String resource) {
		// Read the database
		boolean permissionExists = false;

		try {
			Connections con = new Connections();

			con.connect();

			String qry = "select * from permissions where groupID=? and resourceID=?";

			PreparedStatement pstmt = con.getCon().prepareStatement(qry);
			pstmt.setString(1, groupID);
			pstmt.setString(2, resource);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				permissionExists = true;
			}

			rs.close();
			pstmt.close();
			con.disconnect();

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return permissionExists;
	}
	
	public ArrayList getAccessUsers(String resourceID) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, OpenRDFException, IOException
	{
		Connections con = new Connections();
		con.connect();
		
		ArrayList people = new ArrayList();
		
		String qry = "select userID from permissions where userID <> '' AND resourceID=?";

		PreparedStatement pstmt = con.getCon().prepareStatement(qry);
		pstmt.setString(1, resourceID);
		
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			String foafID = rs.getString("userID");
			RDFi rdf = new RDFi();
			User user = new User();
			
			String firstname = rdf.getPropertyValue(foafID, FOAF.firstName.toString());
			String surname = rdf.getPropertyValue(foafID, FOAF.surname.toString());
			String fullname = firstname + " " + surname;
			String email = rdf.getPropertyValue(foafID, FOAF.mbox.toString());
			int userid = user.getUserIDFromFOAF(foafID);
			Person person = new Person();
			person.setEmail(email);
			person.setName(fullname);
			person.setUserID(userid);
			person.setFoafID(foafID);
			people.add(person);
		}
		
		
		return people;
		
	}
	
	public ArrayList getAccessGroups(String resourceID) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, OpenRDFException, IOException, ServletException
	{
		Connections con = new Connections();
		con.connect();
		
		ArrayList groups = new ArrayList();
		
		String qry = "select groupID from permissions where resourceID=?";

		PreparedStatement pstmt = con.getCon().prepareStatement(qry);
		pstmt.setString(1, resourceID);
		
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			String groupID = rs.getString("groupID");
			groups.add(groupID);
		}
		
		
		return groups;
		
	}
	
	/**
	 * Returns a list of users who have admin rights on a project. This entails they can edit but NOT delete
	 * information.
	 * 
	 * @param resourceID
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws OpenRDFException
	 * @throws IOException
	 */
	public ArrayList getProjectAdministrators(String resourceID) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, OpenRDFException, IOException
	{
		Connections con = new Connections();
		con.connect();
		
		ArrayList people = new ArrayList();
		
		String qry = "select userID from permissions where edit=1 and resourceID=?";

		PreparedStatement pstmt = con.getCon().prepareStatement(qry);
		pstmt.setString(1, resourceID);
		
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			String foafID = rs.getString("userID");
			RDFi rdf = new RDFi();
			User user = new User();
			
			String firstname = rdf.getPropertyValue(foafID, FOAF.firstName.toString());
			String surname = rdf.getPropertyValue(foafID, FOAF.surname.toString());
			String fullname = firstname + " " + surname;
			String email = rdf.getPropertyValue(foafID, FOAF.mbox.toString());
			int userid = user.getUserIDFromFOAF(foafID);
			Person person = new Person();
			person.setEmail(email);
			person.setName(fullname);
			person.setUserID(userid);
			person.setFoafID(foafID);
			people.add(person);
		}
		
		return people;
		
	}

}
