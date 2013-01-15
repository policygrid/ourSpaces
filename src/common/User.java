package common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;

import org.openrdf.OpenRDFException;
import org.openrdf.query.GraphQuery;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.QueryLanguage;
import org.policygrid.ontologies.FOAF;
import org.policygrid.ontologies.OurSpacesVRE;
import org.policygrid.policies.PolicyReasoner;
import org.policygrid.policies.PolicySession;
import org.policygrid.policies.RequiredFieldsSession;
import org.xml.sax.SAXException;

import provenanceService.ProvenanceService;
import provenanceService.ProvenanceServiceException;
import provenanceService.ProvenanceServiceImpl;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * 
 * @author Richard Reid
 * @version 1.0
 * 
 *          User JavaBean Class
 * 
 *          All requests from the servlet and JSP pages related to generating
 *          user information is initialised in this class.
 * 
 */

public class User {
	public int userid;
	public int contactid;
	public String rdfID;
	public String foafID;

	Connections con = new Connections();

	public User() {
		super();
	}

	/**
	 * Returns the user's ID
	 * 
	 * @return
	 */
	public int getID() {
		return userid;
	}

	/**
	 * Sets the user's ID
	 * 
	 * @param id
	 */
	public void setID(int id) {
		userid = id;
	}

	/**
	 * @return the OurSpacesAccount ID of the user
	 */
	public String getRDFID() {
		return rdfID;
	}

	public void setRDFID(String rdfID) {
		this.rdfID = rdfID;
	}

	public String getFOAFID() {
		return foafID;
	}

	public void setFOAFID(String foafID) {
		this.foafID = foafID;
	}

	/**
	 * returns the contact's ID
	 * 
	 * @return
	 */
	public int getContactID() {
		return contactid;
	}

	/**
	 * Sets the contact's ID
	 * 
	 * @param id
	 */
	public void setContactID(int id) {
		contactid = id;
	}

	/**
	 * Returns a user's widget layout configuration in the form of a HomeLayout
	 * javabean.
	 * 
	 * @param id
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public HomeLayout getHomeLayout(int id)
			throws ParserConfigurationException, SAXException, IOException {
		XML xml = new XML();
		HomeLayout home = xml.getLayout(id);
		return home;
	}


	/**
	 * Returns the username of the user based on their userID
	 * 
	 * @param id
	 *            - the id of the user
	 * @return username
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String getUsername(int id) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		// Database Connection
		con.connect();
		Statement st = con.getCon().createStatement();

		// SQL Query
		StringBuffer qry = new StringBuffer(1024);
		String i = String.valueOf(id);
		qry.append("select email from user where userid=\'");
		qry.append(i);
		qry.append("\'");

		ResultSet rs = st.executeQuery(qry.toString());

		String name = "";
		while (rs.next()) {
			name = rs.getString("email");
		}

		rs.close();
		st.close();
		con.disconnect();

		return name;
	}

	/**
	 * Returns the unique ID of the user based on their email
	 * 
	 * @param username
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int getUserID(String username) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		// Database connection
		con.connect();
		Statement st = con.getCon().createStatement();

		// SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select userid from user where email=\'");
		qry.append(username);
		qry.append("\'");

		ResultSet rs = st.executeQuery(qry.toString());

		int i = 0;
		while (rs.next()) {
			i = rs.getInt("userid");
		}

		rs.close();
		st.close();
		con.disconnect();

		return i;
	}

	/**
	 * Returns the unique ID of the user based on their ourSpaces User Account
	 * RDF ID.
	 * 
	 * @param username
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int getUserIDFromRDFID(String rdf) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		// Database connection
		con.connect();
		Statement st = con.getCon().createStatement();

		// SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select userid from user where rdf=\'");
		qry.append(rdf);
		qry.append("\'");

		ResultSet rs = st.executeQuery(qry.toString());

		int i = 0;
		while (rs.next()) {
			i = rs.getInt("userid");
		}

		rs.close();
		st.close();
		con.disconnect();

		return i;
	}

	/**
	 * Update the user ping timestamp
	 * 
	 * @param username
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void pingUser(int userID) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		// Database connection
		con.connect();
		Statement st = con.getCon().createStatement();

		// SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("update user set ping=" + System.currentTimeMillis()
				+ " where userid=");
		qry.append(userID);
		qry.append("");

		st.executeUpdate(qry.toString());

		st.close();
		con.disconnect();
	}

	/**
	 * Update the user ping timestamp
	 * 
	 * @param username
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void pingUserOut(int userID) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		// Database connection
		con.connect();
		Statement st = con.getCon().createStatement();

		// SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("update user set ping=0 where userid=");
		qry.append(userID);
		qry.append("");

		st.executeUpdate(qry.toString());

		st.close();
		con.disconnect();
	}
	/**
	 * Returns the preferred visualisation method. Either graphical, textual or a table.
	 * @param artifact Artifact which has been accessed.
	 * @return
	 */
	public Vector<String> getPreferredVisualisations(ArtifactBean artifact){
		//Check which display method is preferred
		ProvenanceServiceImpl impl = ProvenanceService.getSingleton();
	 	String provSession = impl.startSession();
	 	try{
		 	String process = impl.addNode(provSession, "http://www.policygrid.org/ourspacesVRE.owl#BrowseResource");
		 	impl.addExistingResource(provSession, artifact.getArtifactID(), artifact.getType(), artifact.getTitle());
		 	impl.addExistingResource(provSession, getFOAFID(), FOAF.Person.toString(), getName(getID()));
		 	impl.addCausalRelationship(provSession, "http://openprovenance.org/ontology#WasControlledBy", getFOAFID(), process);
		 	impl.addCausalRelationship(provSession, "http://openprovenance.org/ontology#Used", process, artifact.getArtifactID());
	 	} catch (ProvenanceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	Model m = impl.getModel(provSession);
	 	m.write(System.out);
	 	try {
			PolicySession psession = PolicyReasoner.createSession();
			psession.addContext(m);
			OntologyHandler ontoHandle = new OntologyHandler();
			psession.addContext(ontoHandle.getOntology("discipline"));
			//PolicyReasoner.loadAllPolicies(psession);
			PolicyReasoner.loadPolicy("VisualPreferencePolicy.spin.owl", psession);
			PolicyReasoner.run(psession);
			PolicyReasoner.closeSession(psession);
			RequiredFieldsSession fields = new RequiredFieldsSession(psession);
			Vector<String> preferedVisualTypes = fields.listPreferedVisualisationTypes(artifact.getArtifactID());
//			impl.addRDFGraph(provSession, psession.getInferences());
//			impl.addRDFGraph(provSession, ontoHandle.getOntology("discipline"));
//			impl.getModel(provSession).write(System.out);
			impl.rollback(provSession);
			return preferedVisualTypes;
		} catch (Exception e) {
			common.Utility.log.debug(e);
		} finally {
			impl.rollback(provSession);
		}
	 	return null;
	}
	
	/**
	 * Check is the user is online
	 * 
	 * @param username
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String getUserOnlineStatus(int userID)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		// Database connection
		con.connect();
		Statement st = con.getCon().createStatement();

		// SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select userid, ping from user where userid=");
		qry.append(userID);

		ResultSet rs = st.executeQuery(qry.toString());

		long ping = 0;

		while (rs.next()) {
			ping = rs.getLong("ping");
		}

		rs.close();
		st.close();
		con.disconnect();
		String res = "offline";

		long dif = (System.currentTimeMillis() - ping);

		if (dif < 600000)
			res = "active";
		if ((dif > 600000) && (dif < 7200000))
			res = "idle";

		return res;
	}
	
	public boolean updatePassword(String oldPass, String newPass, int userID) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NoSuchAlgorithmException
	{
		boolean success = false;
		con.connect();
		Statement st = con.getCon().createStatement();
		ResultSet rs;
		PersonCreator pc = new PersonCreator();
		String encryptpass = pc.encrypt(oldPass);
		
		String query = "select password from user where userid = " + userID;
		rs = st.executeQuery(query);
		while(rs.next())
		{
			if(rs.getString("password").equals(encryptpass))
			{
				success = true;
				
			}
		}
		rs.close();
		
		if(success) 
		{
			String newEncrypt = pc.encrypt(newPass);
			String query2 = "update user set password = \"" + newEncrypt + "\" where userid = " + userID;
			st.executeUpdate(query2);
		}
		
		st.close();
		
		con.disconnect();
		return success;
	}

	/**
	 * Checks the notifications option for a given notification type and user id
	 * 
	 * @param username
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void changeNotification(String notificationType, int id,
			boolean value) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		boolean check = false;
		// Database connection
		con.connect();
		Statement st = con.getCon().createStatement();

		// SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("update user set " + notificationType + "=" + value
				+ " where userid=");
		qry.append(id);
		qry.append("");

		st.executeUpdate(qry.toString());

		st.close();
		con.disconnect();
	}

	/**
	 * Checks the notifications option for a given notification type and user id
	 * 
	 * @param username
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean checkNotification(String notificationType, int id)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		boolean check = false;
		// Database connection
		con.connect();
		Statement st = con.getCon().createStatement();

		// SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select " + notificationType + " from user where userid=\'");
		qry.append(id);
		qry.append("\'");

		ResultSet rs = st.executeQuery(qry.toString());

		int i = 0;
		while (rs.next()) {
			check = rs.getBoolean(notificationType);
		}

		rs.close();
		st.close();
		con.disconnect();

		return check;
	}

	/**
	 * Returns the userid (int) based on the RDF FOAF ID.
	 * 
	 * @param foaf
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int getUserIDFromFOAF(String foaf) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		// Database connection
		con.connect();
		Statement st = con.getCon().createStatement();

		// SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select userid from user where foaf=\'");
		qry.append(foaf);
		qry.append("\'");

		ResultSet rs = st.executeQuery(qry.toString());

		int i = 0;
		while (rs.next()) {
			i = rs.getInt("userid");
		}

		rs.close();
		st.close();
		con.disconnect();

		return i;
	}

	/**
	 * Returns a String containing the RDF ourSpaces User Account ID of a
	 * particular user based on the ID entered.
	 * 
	 * @param id
	 *            Unique ID of the user
	 * @return String containing the RDF resource ID
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public String getUserRDFID(int userid) {
		String id = "";
		// Database Connection
		try {
			con.connect();
			Statement st = con.getCon().createStatement();
			// SQL Query
			StringBuffer qry = new StringBuffer(1024);
			qry.append("select rdf from user where userid=");
			qry.append(userid);
			ResultSet rs = st.executeQuery(qry.toString());
			while (rs.next()) {
				id = rs.getString("rdf");
			}
			rs.close();
			st.close();
			con.disconnect();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id;
	}

	/**
	 * Returns a String containing the RDF FOAF Account ID of a particular user
	 * based on the ID entered.
	 * 
	 * @param id
	 *            Unique ID of the user
	 * @return String containing the RDF resource ID
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public String getFOAFID(int userid) throws ParserConfigurationException,
			SAXException, IOException, ServletException, OpenRDFException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		// Database Connection
		con.connect();
		Statement st = con.getCon().createStatement();

		// SQL Query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select foaf from user where userid=");
		qry.append(userid);

		ResultSet rs = st.executeQuery(qry.toString());

		String id = "";

		while (rs.next()) {
			id = rs.getString("foaf");
		}

		rs.close();
		st.close();
		con.disconnect();

		return id;
	}

	/**
	 * Returns a vector containing a user's blog. The vector is comprised of
	 * multiple UserBlogBean classes.
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public Vector getUserBlog(int id) throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		// Database connection
		con.connect();
		Statement st = con.getCon().createStatement();

		// Vector which will contain the blog JavaBeans
		Vector blog = new Vector<UserBlogBean>();

		// SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry
				.append("select id, userid, title, content, date from userblog where userid=");
		qry.append(id);
		qry.append(" order by id DESC");

		ResultSet rs = st.executeQuery(qry.toString());

		while (rs.next()) {
			// Creates a new instance of the UserBlogBean JavaBean
			UserBlogBean ubb = new UserBlogBean();
			ubb.setContent(rs.getString("content"));
			ubb.setTitle(rs.getString("title"));
			ubb.setDate(rs.getString("date"));
			blog.add(ubb);
		}

		rs.close();
		st.close();
		con.disconnect();

		return blog;
	}

	/**
	 * Retrieves all the tags that belong to a particular user, based on their
	 * user resource ID.
	 * 
	 * Tags are returned in a vector containing multiple Tag JavaBeans.
	 * 
	 * @param id
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws OpenRDFException
	 * @throws ServletException
	 */
	public Vector getTags(int id) throws ParserConfigurationException,
			SAXException, IOException, SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, ServletException,
			OpenRDFException {
		// Retrieves the resource ID of a particular
		String foafID = getFOAFID(id);

		// Returns the tags in a vector
		Vector tags = new Vector();
		Tag tag = new Tag();
		tags = tag.getTags(foafID);

		return tags;
	}

	/**
	 * Allows the user to send a message by adding a message to the Messages
	 * database table with the appropriate 'Unread' marker.
	 * 
	 * @param recipient
	 * @param sender
	 * @param subject
	 * @param message
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws OpenRDFException
	 * @throws ServletException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	//added parameters threadid and sent by Kapila 11/09/11
	public void sendMessage(String[] recipient, int sender,int threadid, String subject,
			String message, String sent) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException,
			ParserConfigurationException, SAXException, IOException,
			ServletException, OpenRDFException {
		con.connect();
		Statement st = con.getCon().createStatement();
		common.Utility.log.debug("User.SendMessage");
		int tempID = 0;//No recipient is recoded with the message
		String foafIDs="";
		//comma separated recipient list is saved to be written with a trigger to separate table
		for (int i = 0; i < recipient.length; i++) {
			int tempID2 = getUserIDFromFOAF(recipient[i]);
			 foafIDs = foafIDs+","+Integer.toString(tempID2);
		}
			// The 'X' marks the post as Unread
		foafIDs.trim();
		try{
			String sql = "INSERT INTO messages (recipient, sender,threadid, subject, content,sent, unread, hide_sent, hide_received) VALUES("
					+ tempID
					+ ","
					+ sender
					+ ","
					+threadid 
					+ ",'"
					+ subject
					+ "','"
					+ message
					+ "','"
					+sent
					+ "','X',false, false)";
			
			common.Utility.log.debug(sql);
			st.executeUpdate(sql);
			
			//get the last inserted message id for the session
			sql="select LAST_INSERT_ID() FROM messages";
			ResultSet rs = st.executeQuery(sql);
			if (rs.first()) {
				
				int lastmsgid=rs.getInt(1);
				for (int i = 0; i < recipient.length; i++) {
				int tempID2 = getUserIDFromFOAF(recipient[i]);
				if (tempID2!=0){
				 sql = "INSERT INTO messagerecipients (id,recipient, unread,hide_sent, hide_received) VALUES("
						+ lastmsgid
						+ ","
						+ tempID2
						+ ",'X',false, false)";
				 common.Utility.log.debug(sql);
				 st.executeUpdate(sql);
				}
			}
			}
			
			

			
			if (checkNotification("notify_new_message", tempID)) {
				String to = getEmail(tempID);
				String esubject = "You have a new message in ourSpaces from "
						+ getName(sender);
				String emessage = "If you want to see the message please log into ourSpaces at http://www.ourspaces.net \n";
				Email.send(to, esubject, emessage);
			}
		}catch(Exception ex){
			common.Utility.log.debug("Error: "+ex.getMessage());
		}
		

		st.close();
		con.disconnect();
	}
	public void addBug(int sender, String type, String object, String message)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException, ParserConfigurationException,
			SAXException, IOException, ServletException, OpenRDFException {
		con.connect();
		Statement st = con.getCon().createStatement();

		String qry = "INSERT INTO bugs (created, type, object, message, senderID) VALUES(?,?,?,?,?)";

		PreparedStatement pstmt = con.getCon().prepareStatement(qry);
		pstmt.setLong(1, System.currentTimeMillis());
		pstmt.setString(2, type);
		pstmt.setString(3, object);
		pstmt.setString(4, message);
		pstmt.setInt(5, sender);

		pstmt.executeUpdate();

		String to = getEmail(sender);
		String esubject = "ourSpaces Helpdesk Request ";
		String emessage = "Your request ("
				+ object
				+ ") has been submitted to the ourSpaces helpdesk. \n You will be notified by email when the issue has been resolved.";
		Email.send(to, esubject, emessage);

		st.close();
		con.disconnect();
	}

	public int getBugSenderID(int rid) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {

		int sid = 0;
		con.connect();
		Statement st = con.getCon().createStatement();

		Vector blog = new Vector<UserBlogBean>();

		// SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select id, senderID, object from bugs where id=");
		qry.append(rid);

		ResultSet rs = st.executeQuery(qry.toString());

		while (rs.next()) {
			sid = rs.getInt("senderID");
		}

		rs.close();
		st.close();
		con.disconnect();

		return sid;
	}

	public String getBugObject(int rid) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {

		String object = "";

		con.connect();
		Statement st = con.getCon().createStatement();

		Vector blog = new Vector<UserBlogBean>();

		// SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select id, senderID, object from bugs where id=");
		qry.append(rid);

		ResultSet rs = st.executeQuery(qry.toString());

		while (rs.next()) {
			object = rs.getString("object");
		}

		rs.close();
		st.close();
		con.disconnect();

		return object;
	}

	public int getNewBugs() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {

		int msg = 0;

		con.connect();
		Statement st = con.getCon().createStatement();

		// SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select id from bugs where status is null");

		ResultSet rs = st.executeQuery(qry.toString());

		while (rs.next()) {
			msg++;
		}

		rs.close();
		st.close();
		con.disconnect();

		return msg;
	}

	/**
	 * Performs a check on whether a user has unread messages or not.
	 * 
	 * Messages containing an 'X' are unread for that particular user.
	 * 
	 * @param id
	 * @return Integer
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	//change to reflect changes in tables
	public int checkMessages(int id) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		// Connects to the database
		con.connect();
		Statement st = con.getCon().createStatement();

		// SQL Query
		StringBuffer qry = new StringBuffer(1024);
//		qry.append("select id from messages where recipient=\'");
//		qry.append(id);
//		qry.append("\' and unread='X'");

		 
		
		qry.append("select COUNT(*) as no from messages m,messagerecipients r  where m.id=r.id and r.recipient=\'");
		qry.append(id);
		qry.append("\'  and r.unread='X' and r.hide_received=false;");

		ResultSet rs = st.executeQuery(qry.toString());

		int i = 0;
		// if i is greater than 0, there are unread messages.
		while (rs.next()) {
			i = rs.getInt("no");
		}

		rs.close();
		st.close();
		con.disconnect();

		return i;
	}

	/**
	 * Returns a Vector of all messages belonging to a particular used based on
	 * their userID entered. The Vector contains multiple instances of the
	 * Message JavaBean used to store each message.
	 * 
	 * Newer messages appear first.
	 * 
	 * @param userid
	 * @return Vector
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws OpenRDFException 
	 * @throws ServletException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public Vector getDeletedMessages(int userid, int offset, int limit) throws SQLException,
	InstantiationException, IllegalAccessException,
	ClassNotFoundException, ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException {
	con.connect();
	Statement st = con.getCon().createStatement();
	
	Vector messages = new Vector();
	
	StringBuffer qry = new StringBuffer(1024);
		qry.append("select m.id,threadid, sender, subject, content, sent,r.unread from messages m,messagerecipients r  where m.id=r.id and r.recipient=\'");
		qry.append(userid);
		qry.append("\'  and r.hide_received = true order by m.id DESC, m.threadid DESC limit "+offset+", "+limit);	
	
	ResultSet rs = st.executeQuery(qry.toString());
	//common.Utility.log.debug(qry.toString());
	while (rs.next()) {
		Message message = new Message();
		message.setID(rs.getInt("id"));
		message.setSender(rs.getInt("sender"));
		message.setThreadID(rs.getInt("threadid"));
		message.setSubject(rs.getString("subject"));
		message.setContent(rs.getString("content"));
		message.setSent(rs.getDate("sent"));
		message.setSentTime(rs.getTime("sent"));
		
		message.setUnread(rs.getString("unread"));
		Statement st2 = con.getCon().createStatement();
		StringBuffer qry2 = new StringBuffer(1024);
		qry2.append("select recipient,unread from messagerecipients where id=\'");
		qry2.append(rs.getInt("id"));
		qry2.append("\' order by recipient");
		//common.Utility.log.debug(qry2.toString());
		ResultSet rs2 = st2.executeQuery(qry2.toString());
		Vector rec = new Vector();
		Vector recids = new Vector();
		while (rs2.next()) {
			int uid=rs2.getInt("recipient");
			String unread=rs2.getString("unread");
			User u=new User();
			u.setID(uid);
			//common.Utility.log.debug(u.getName(uid).toString());
			recids.add(uid);
			rec.add(u.getName(uid).toString()+"("+unread+")");
			
		}
		message.setRecipients(rec);
		message.setRecipientIds(recids);
		messages.add(message);
	}
	
	rs.close();
	st.close();
	con.disconnect();
	
	return messages;
}

	/**
	 * Returns a Vector of all messages belonging to a particular used based on
	 * their userID entered. The Vector contains multiple instances of the
	 * Message JavaBean used to store each message.
	 * 
	 * Newer messages appear first.
	 * 
	 * @param userid
	 * @return Vector
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws OpenRDFException 
	 * @throws ServletException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public Vector getMessages(int userid,String orderby, int start, int limit) throws SQLException,
	InstantiationException, IllegalAccessException,
	ClassNotFoundException, ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException {
	con.connect();
	Statement st = con.getCon().createStatement();
	
	Vector messages = new Vector();
	
	StringBuffer qry = new StringBuffer(1024);
	
	if (start==0 && limit ==0){
		qry.append("select m.id,threadid, sender, subject, content, sent,r.unread from messages m,messagerecipients r  where m.id=r.id and r.recipient=\'");
		qry.append(userid);
		if (orderby.equals("threadid"))
			qry.append("\'  and r.hide_received = false order by m.threadid DESC, m.id DESC");
		else
			qry.append("\'  and r.hide_received = false order by m.id DESC, m.threadid DESC");
		
	}else{
		qry.append("select m.id,threadid, sender, subject, content, sent,r.unread from messages m,messagerecipients r  where m.id=r.id and r.recipient=\'");
		qry.append(userid);
		qry.append("\'  and m.id<\'");
		qry.append(start);
		qry.append("\'  and r.hide_received = false order by m.id DESC, m.threadid DESC limit "+limit);	
	
	}
	
	
	
	
	ResultSet rs = st.executeQuery(qry.toString());
	//common.Utility.log.debug(qry.toString());
	while (rs.next()) {
		Message message = new Message();
		message.setID(rs.getInt("id"));
		message.setSender(rs.getInt("sender"));
		message.setThreadID(rs.getInt("threadid"));
		message.setSubject(rs.getString("subject"));
		message.setContent(rs.getString("content"));
		message.setSent(rs.getDate("sent"));
		message.setSentTime(rs.getTime("sent"));
		
		message.setUnread(rs.getString("unread"));
		Statement st2 = con.getCon().createStatement();
		StringBuffer qry2 = new StringBuffer(1024);
		qry2.append("select recipient,unread from messagerecipients where id=\'");
		qry2.append(rs.getInt("id"));
		qry2.append("\' order by recipient");
		//common.Utility.log.debug(qry2.toString());
		ResultSet rs2 = st2.executeQuery(qry2.toString());
		Vector rec = new Vector();
		Vector recids = new Vector();
		while (rs2.next()) {
			int uid=rs2.getInt("recipient");
			String unread=rs2.getString("unread");
			User u=new User();
			u.setID(uid);
			//common.Utility.log.debug(u.getName(uid).toString());
			recids.add(uid);
			rec.add(u.getName(uid).toString()+"("+unread+")");
			
		}
		message.setRecipients(rec);
		message.setRecipientIds(recids);
		messages.add(message);
	}
	
	rs.close();
	st.close();
	con.disconnect();
	
	return messages;
}
	
	
	public Vector <Message> getMessagesWithOffset(int userid,String qrytype, int offset, int limit) throws SQLException,
	InstantiationException, IllegalAccessException,
	ClassNotFoundException, ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException {
	con.connect();
	Statement st = con.getCon().createStatement();
	
	Vector <Message> messages = new Vector<Message>();
	
	StringBuffer qry = new StringBuffer(1024);
	if (qrytype.equals("inbox")){
		qry.append("select distinct m.id,threadid, sender, subject, content, sent,r.unread from messages m,messagerecipients r  where m.id=r.id and r.recipient=\'");
		qry.append(userid);
		qry.append("\'  and r.hide_received = false order by m.id DESC, m.threadid DESC limit "+offset+", "+limit);
	}
	else if (qrytype.equals("sent")){
		qry.append("select distinct m.id,threadid, sender, subject, content, sent,r.unread from messages m,messagerecipients r  where m.id=r.id and m.sender=\'");
		qry.append(userid);
		qry.append("\'  and m.hide_sent = false order by m.id DESC, m.threadid DESC limit "+offset+", "+limit);
	}
	else if (qrytype.equals("trash")){
		qry.append("select distinct m.id,threadid, sender, subject, content, sent,r.unread from messages m,messagerecipients r  where m.id=r.id and r.recipient=\'");
		qry.append(userid);
		qry.append("\'  and (r.hide_received = true || m.hide_sent=true) order by m.id DESC, m.threadid DESC limit "+offset+", "+limit);
	}
		
		


	ResultSet rs = st.executeQuery(qry.toString());
	//common.Utility.log.debug(qry.toString());
	while (rs.next()) {
		Message message = new Message();
		message.setID(rs.getInt("id"));
		message.setSender(rs.getInt("sender"));
		message.setThreadID(rs.getInt("threadid"));
		message.setSubject(rs.getString("subject"));
		message.setContent(rs.getString("content"));
		message.setSent(rs.getDate("sent"));
		message.setSentTime(rs.getTime("sent"));
		
		message.setUnread(rs.getString("unread"));
		Statement st2 = con.getCon().createStatement();
		StringBuffer qry2 = new StringBuffer(1024);
		qry2.append("select recipient,unread from messagerecipients where id=\'");
		qry2.append(rs.getInt("id"));
		qry2.append("\' order by recipient");
		//common.Utility.log.debug(qry2.toString());
		ResultSet rs2 = st2.executeQuery(qry2.toString());
		Vector rec = new Vector();
		Vector recids = new Vector();
		while (rs2.next()) {
			int uid=rs2.getInt("recipient");
			String unread=rs2.getString("unread");
			User u=new User();
			u.setID(uid);
			//common.Utility.log.debug(u.getName(uid).toString());
			recids.add(uid);
			rec.add(u.getName(uid).toString()+"("+unread+")");
			
		}
		message.setRecipients(rec);
		message.setRecipientIds(recids);
		messages.add(message);
	}
	
	rs.close();
	st.close();
	con.disconnect();
	
	return messages;
}
	/**
	 * Returns a Vector of all messages belonging to a particular used based on
	 * their userID entered. The Vector contains multiple instances of the
	 * Message JavaBean used to store each message.
	 * 
	 * Newer messages appear first.
	 * 
	 * @param userid
	 * @return Vector
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws OpenRDFException 
	 * @throws ServletException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public Vector getMessagesSent_Received(int userid,String orderby) throws SQLException,
	InstantiationException, IllegalAccessException,
	ClassNotFoundException, ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException {
	con.connect();
	Statement st = con.getCon().createStatement();
	
	Vector messages = new Vector();
	
	StringBuffer qry = new StringBuffer(1024);
	//select m.id,threadid, sender, subject, content, sent,r.unread from messages m,messagerecipients r  where (m.id=r.id and r.recipient='225'  and r.hide_received = false) or (m.id=r.id and m.sender=225 and r.hide_received = false) order by m.threadid DESC, m.id DESC
	qry.append("select distinct m.id,threadid, sender, subject, content, sent,r.unread from messages m,messagerecipients r  where (m.id=r.id and r.recipient='"+userid+"'  and r.hide_received = false) or (m.id=r.id and m.sender="+userid+" and m.hide_sent = false) ");
	if (orderby.equals("threadid"))
		qry.append("   order by m.threadid DESC, m.id DESC");
	else
		qry.append("  order by m.id DESC, m.threadid DESC");
		
	//common.Utility.log.debug("QUERY "+qry.toString());
	ResultSet rs = st.executeQuery(qry.toString());
	//common.Utility.log.debug(qry.toString());
	while (rs.next()) {
		Message message = new Message();
		message.setID(rs.getInt("id"));
		message.setSender(rs.getInt("sender"));
		message.setThreadID(rs.getInt("threadid"));
		message.setSubject(rs.getString("subject"));
		message.setContent(rs.getString("content"));
		message.setSent(rs.getDate("sent"));
		message.setSentTime(rs.getTime("sent"));
		
		message.setUnread(rs.getString("unread"));
		Statement st2 = con.getCon().createStatement();
		StringBuffer qry2 = new StringBuffer(1024);
		qry2.append("select recipient,unread from messagerecipients where id=\'");
		qry2.append(rs.getInt("id"));
		qry2.append("\' order by recipient");
		//common.Utility.log.debug(qry2.toString());
		try{
		ResultSet rs2 = st2.executeQuery(qry2.toString());
		Vector rec = new Vector();
		Vector recids = new Vector();
		while (rs2.next()) {
			int uid=rs2.getInt("recipient");
			if (uid!=0){
			String unread=rs2.getString("unread");
			User u=new User();
			
			u.setID(uid);
			//common.Utility.log.debug(u.getName(uid).toString());
			recids.add(uid);
			rec.add(u.getName(uid).toString()+"("+unread+")");
			}
		}
		message.setRecipients(rec);
		message.setRecipientIds(recids);
		}catch (Exception ex){
			//common.Utility.log.debug(ex.getMessage());
		}
		messages.add(message);
	}
	
	rs.close();
	st.close();
	con.disconnect();
	//common.Utility.log.debug("returned successfully");
	return messages;
}
	
//	/**
//	 * Returns a Vector of all messages belonging to a particular used based on
//	 * their userID entered. The Vector contains multiple instances of the
//	 * Message JavaBean used to store each message.
//	 * 
//	 * Newer messages appear first.
//	 * 
//	 * @param userid
//	 * @return Vector
//	 * @throws SQLException
//	 * @throws InstantiationException
//	 * @throws IllegalAccessException
//	 * @throws ClassNotFoundException
//	 * @throws OpenRDFException 
//	 * @throws ServletException 
//	 * @throws IOException 
//	 * @throws SAXException 
//	 * @throws ParserConfigurationException 
//	 */
//	public Vector get8SentMessagesFrom(int userid,int start) throws SQLException,
//	InstantiationException, IllegalAccessException,
//	ClassNotFoundException, ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException {
//	con.connect();
//	Statement st = con.getCon().createStatement();
//	
//	Vector messages = new Vector();
//	
//	StringBuffer qry = new StringBuffer(1024);
//	
//	qry.append("select m.id,threadid, sender, subject, content, sent,r.unread from messages m,messagerecipients r  where m.id=r.id and m.sender=\'");
//	qry.append(userid);
//	qry.append("\'  and m.id<\'");
//	qry.append(start);
//	qry.append("\'  and r.hide_received = false order by m.id DESC, m.threadid DESC limit 8");	
//	
//	ResultSet rs = st.executeQuery(qry.toString());
//	//common.Utility.log.debug(qry.toString());
//	while (rs.next()) {
//		Message message = new Message();
//		message.setID(rs.getInt("id"));
//		message.setSender(rs.getInt("sender"));
//		message.setThreadID(rs.getInt("threadid"));
//		message.setSubject(rs.getString("subject"));
//		message.setContent(rs.getString("content"));
//		message.setSent(rs.getDate("sent"));
//		message.setSentTime(rs.getTime("sent"));
//		
//		message.setUnread(rs.getString("unread"));
//		Statement st2 = con.getCon().createStatement();
//		StringBuffer qry2 = new StringBuffer(1024);
//		qry2.append("select recipient,unread from messagerecipients where id=\'");
//		qry2.append(rs.getInt("id"));
//		qry2.append("\' order by recipient");
//		//common.Utility.log.debug(qry2.toString());
//		ResultSet rs2 = st2.executeQuery(qry2.toString());
//		Vector rec = new Vector();
//		Vector recids = new Vector();
//		while (rs2.next()) {
//			int uid=rs2.getInt("recipient");
//			String unread=rs2.getString("unread");
//			User u=new User();
//			u.setID(uid);
//			//common.Utility.log.debug(u.getName(uid).toString());
//			recids.add(uid);
//			rec.add(u.getName(uid).toString()+"("+unread+")");
//			
//		}
//		message.setRecipients(rec);
//		message.setRecipientIds(recids);
//		messages.add(message);
//	}
//	
//	rs.close();
//	st.close();
//	con.disconnect();
//	
//	return messages;
//}
	
	
//	/**
//	 * Returns a Vector of all messages belonging to a particular used based on
//	 * their userID entered. The Vector contains multiple instances of the
//	 * Message JavaBean used to store each message.
//	 * 
//	 * Newer messages appear first.
//	 * 
//	 * @param userid
//	 * @return Vector
//	 * @throws SQLException
//	 * @throws InstantiationException
//	 * @throws IllegalAccessException
//	 * @throws ClassNotFoundException
//	 * @throws OpenRDFException 
//	 * @throws ServletException 
//	 * @throws IOException 
//	 * @throws SAXException 
//	 * @throws ParserConfigurationException 
//	 */
//	public Vector get8DeletedMessagesFrom(int userid,int start) throws SQLException,
//	InstantiationException, IllegalAccessException,
//	ClassNotFoundException, ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException {
//	con.connect();
//	Statement st = con.getCon().createStatement();
//	
//	Vector messages = new Vector();
//	
//	StringBuffer qry = new StringBuffer(1024);
//	
//	qry.append("select m.id,threadid, sender, subject, content, sent,r.unread from messages m,messagerecipients r  where m.id=r.id and r.recipient=\'");
//	qry.append(userid);
//	qry.append("\'  and m.id<\'");
//	qry.append(start);
//	qry.append("\'  and r.hide_received = true order by m.id DESC, m.threadid DESC limit 8");	
//	
//	ResultSet rs = st.executeQuery(qry.toString());
//	//common.Utility.log.debug(qry.toString());
//	while (rs.next()) {
//		Message message = new Message();
//		message.setID(rs.getInt("id"));
//		message.setSender(rs.getInt("sender"));
//		message.setThreadID(rs.getInt("threadid"));
//		message.setSubject(rs.getString("subject"));
//		message.setContent(rs.getString("content"));
//		message.setSent(rs.getDate("sent"));
//		message.setSentTime(rs.getTime("sent"));
//		
//		message.setUnread(rs.getString("unread"));
//		Statement st2 = con.getCon().createStatement();
//		StringBuffer qry2 = new StringBuffer(1024);
//		qry2.append("select recipient,unread from messagerecipients where id=\'");
//		qry2.append(rs.getInt("id"));
//		qry2.append("\' order by recipient");
//		//common.Utility.log.debug(qry2.toString());
//		ResultSet rs2 = st2.executeQuery(qry2.toString());
//		Vector rec = new Vector();
//		Vector recids = new Vector();
//		while (rs2.next()) {
//			int uid=rs2.getInt("recipient");
//			String unread=rs2.getString("unread");
//			User u=new User();
//			u.setID(uid);
//			//common.Utility.log.debug(u.getName(uid).toString());
//			recids.add(uid);
//			rec.add(u.getName(uid).toString()+"("+unread+")");
//			
//		}
//		message.setRecipients(rec);
//		message.setRecipientIds(recids);
//		messages.add(message);
//	}
//	
//	rs.close();
//	st.close();
//	con.disconnect();
//	
//	return messages;
//}
	
//	/**
//	 * Returns a Vector of all messages belonging to a particular used based on
//	 * their userID entered. The Vector contains multiple instances of the
//	 * Message JavaBean used to store each message.
//	 * 
//	 * Newer messages appear first.
//	 * 
//	 * @param userid
//	 * @return Vector
//	 * @throws SQLException
//	 * @throws InstantiationException
//	 * @throws IllegalAccessException
//	 * @throws ClassNotFoundException
//	 * @throws OpenRDFException 
//	 * @throws ServletException 
//	 * @throws IOException 
//	 * @throws SAXException 
//	 * @throws ParserConfigurationException 
//	 */
//	public Vector getNMessagesFrom(int userid,int start,int limit) throws SQLException,
//	InstantiationException, IllegalAccessException,
//	ClassNotFoundException, ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException {
//	con.connect();
//	Statement st = con.getCon().createStatement();
//	
//	Vector messages = new Vector();
//	
//	StringBuffer qry = new StringBuffer(1024);
//	
//	qry.append("select m.id,threadid, sender, subject, content, sent,r.unread from messages m,messagerecipients r  where m.id=r.id and r.recipient=\'");
//	qry.append(userid);
//	qry.append("\'  and m.id<\'");
//	qry.append(start);
//	qry.append("\'  and r.hide_received = false order by m.id DESC, m.threadid DESC limit "+limit);	
//	
//	ResultSet rs = st.executeQuery(qry.toString());
//	//common.Utility.log.debug(qry.toString());
//	while (rs.next()) {
//		Message message = new Message();
//		message.setID(rs.getInt("id"));
//		message.setSender(rs.getInt("sender"));
//		message.setThreadID(rs.getInt("threadid"));
//		message.setSubject(rs.getString("subject"));
//		message.setContent(rs.getString("content"));
//		message.setSent(rs.getDate("sent"));
//		message.setSentTime(rs.getTime("sent"));
//		
//		message.setUnread(rs.getString("unread"));
//		Statement st2 = con.getCon().createStatement();
//		StringBuffer qry2 = new StringBuffer(1024);
//		qry2.append("select recipient,unread from messagerecipients where id=\'");
//		qry2.append(rs.getInt("id"));
//		qry2.append("\' order by recipient");
//		//common.Utility.log.debug(qry2.toString());
//		ResultSet rs2 = st2.executeQuery(qry2.toString());
//		Vector rec = new Vector();
//		Vector recids = new Vector();
//		while (rs2.next()) {
//			int uid=rs2.getInt("recipient");
//			String unread=rs2.getString("unread");
//			User u=new User();
//			u.setID(uid);
//			//common.Utility.log.debug(u.getName(uid).toString());
//			recids.add(uid);
//			rec.add(u.getName(uid).toString()+"("+unread+")");
//			
//		}
//		message.setRecipients(rec);
//		message.setRecipientIds(recids);
//		messages.add(message);
//	}
//	
//	rs.close();
//	st.close();
//	con.disconnect();
//	
//	return messages;
//}
	/**
	 * Returns a Vector of all instant messages sent by a particular user
	 *  The Vector contains multiple instances ims
	 * 
	 * Newer messages appear first.
	 * 
	 
	 */
	public ArrayList getInstantMessages(int userid) throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException {
		con.connect();
		Statement st = con.getCon().createStatement();

		ArrayList ims = new ArrayList();

		StringBuffer qry = new StringBuffer(1024);
	
		qry.append("select chat.id,chat.from, chat.to, chat.message, chat.sent, chat.recd from chat where chat.from=\'");
		qry.append(userid);
		qry.append("' or chat.to='");
		qry.append(userid);
		qry.append("' order by chat.id DESC");
			

		ResultSet rs = st.executeQuery(qry.toString());
		//common.Utility.log.debug(qry.toString());
		
		while (rs.next()) {
			InstantMessage im =new InstantMessage();
			im.setIMid(rs.getInt("id"));
			im.setFromUserId(Integer.parseInt(rs.getString("from").trim()));
			im.setToUserId(Integer.parseInt(rs.getString("to").trim()));
			im.setMessage(rs.getString("message").trim());
			im.setSentDate(rs.getDate("sent").toString());
			im.setRecieved(rs.getInt("recd"));
			ims.add(im);
		}

		rs.close();
		st.close();
		con.disconnect();

		return ims;
	}
	
	
	/**
	 * Returns the instant messages of a given chatid
	 * 
	 */
	public ArrayList getInstantMessage(int chatid) throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException {
		con.connect();
		Statement st = con.getCon().createStatement();

		ArrayList ims = new ArrayList();

		StringBuffer qry = new StringBuffer(1024);
	
		qry.append("select chat.id,chat.from, chat.to, chat.message, chat.sent, chat.recd from chat where chat.id=");
		qry.append(chatid);
		
			

		ResultSet rs = st.executeQuery(qry.toString());
		//common.Utility.log.debug(qry.toString());
		
		while (rs.next()) {
			InstantMessage im =new InstantMessage();
			im.setIMid(rs.getInt("id"));
			im.setFromUserId(Integer.parseInt(rs.getString("from").trim()));
			im.setToUserId(Integer.parseInt(rs.getString("to").trim()));
			im.setMessage(rs.getString("message").trim());
			im.setSentDate(rs.getDate("sent").toString());
			im.setRecieved(rs.getInt("recd"));
			ims.add(im);
		}

		rs.close();
		st.close();
		con.disconnect();

		return ims;
	}
	/**
	 * Returns a Vector of all messages belonging to a particular used based on
	 * their userID entered. The Vector contains multiple instances of the
	 * Message JavaBean used to store each message.
	 * 
	 * Newer messages appear first.
	 * 
	 * @param userid
	 * @return Vector
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws OpenRDFException 
	 * @throws ServletException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public Vector getSentMessages(int userid,int offset, int limit) throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException {
		con.connect();
		Statement st = con.getCon().createStatement();
		
		Vector messages = new Vector();

		StringBuffer qry = new StringBuffer(1024);
			qry.append("select distinct m.id,threadid, sender, subject, content, sent,r.unread from messages m,messagerecipients r  where m.id=r.id and m.sender=\'");
			qry.append(userid);
			qry.append("\'  and m.hide_sent = false order by m.id DESC limit "+offset+", "+limit);	
	
		ResultSet rs = st.executeQuery(qry.toString());
		common.Utility.log.debug(qry.toString());
		while (rs.next()) {
			Message message = new Message();
			message.setID(rs.getInt("id"));
			message.setSender(rs.getInt("sender"));
			message.setThreadID(rs.getInt("threadid"));
			message.setSubject(rs.getString("subject"));
			message.setContent(rs.getString("content"));
			message.setSent(rs.getDate("sent"));
			message.setSentTime(rs.getTime("sent"));
			
			message.setUnread(rs.getString("unread"));
			Statement st2 = con.getCon().createStatement();
			StringBuffer qry2 = new StringBuffer(1024);
			qry2.append("select recipient,unread from messagerecipients where id=\'");
			qry2.append(rs.getInt("id"));
			qry2.append("\' order by recipient");
			//common.Utility.log.debug(qry2.toString());
			ResultSet rs2 = st2.executeQuery(qry2.toString());
			Vector rec = new Vector();
			Vector recids = new Vector();
			while (rs2.next()) {
				int uid=rs2.getInt("recipient");
				String unread=rs2.getString("unread");
				User u=new User();
				u.setID(uid);
				//common.Utility.log.debug(u.getName(uid).toString());
				recids.add(uid);
				rec.add(u.getName(uid).toString()+"("+unread+")");
				
			}
			message.setRecipients(rec);
			message.setRecipientIds(recids);
			messages.add(message);
		}

		rs.close();
		st.close();
		con.disconnect();

		return messages;
	}
	/**
	 * Returns a Vector of all messages belonging to a particular used based on
	 * their userID entered. The Vector contains multiple instances of the
	 * Message JavaBean used to store each message.
	 * 
	 * Newer messages appear first.
	 * 
	 * @param userid
	 * @return Vector
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
//	public Vector getSentMessages(int userid) throws SQLException,
//			InstantiationException, IllegalAccessException,
//			ClassNotFoundException {
//		con.connect();
//		Statement st = con.getCon().createStatement();
//
//		Vector messages = new Vector();
//
//		StringBuffer qry = new StringBuffer(1024);
//		qry
//				.append("SELECT min(id) AS id, max(id) AS idmax, sender, subject, content from messages where sender=\'");
//		qry.append(userid);
//		qry
//				.append("\' and hide_sent = false GROUP BY sender, subject, content ORDER BY id DESC");
//
//		ResultSet rs = st.executeQuery(qry.toString());
//
//		while (rs.next()) {
//			Message message = new Message();
//			message.setID(rs.getInt("id"));
//			message.setIDmin(rs.getInt("id"));
//			message.setIDmax(rs.getInt("idmax"));
//			message.setSender(rs.getInt("sender"));
//			message.setSubject(rs.getString("subject"));
//			message.setContent(rs.getString("content"));
//			messages.add(message);
//		}
//
//		rs.close();
//		st.close();
//		con.disconnect();
//
//		return messages;
//	}

	/**
	 * Returns a Vector containing a particular message based on the message ID
	 * requested. The Vector contains one Message JavaBean.
	 * 
	 * @param id
	 * @return Vector
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws OpenRDFException 
	 * @throws ServletException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public Vector getMessage(int id) throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException {
		con.connect();
		Statement st = con.getCon().createStatement();

		Vector messages = new Vector();

		StringBuffer qry = new StringBuffer(1024);
		qry.append("select id, threadid,sender, subject, content,sent, unread from messages where id=\'");
		qry.append(id);
		qry.append("\'");

		ResultSet rs = st.executeQuery(qry.toString());

		while (rs.next()) {
			Message message = new Message();
			message.setID(rs.getInt("id"));
			message.setThreadID(rs.getInt("threadid"));
			message.setSender(rs.getInt("sender"));
			message.setSubject(rs.getString("subject"));
			message.setContent(rs.getString("content"));
			message.setSent(rs.getDate("sent"));
			message.setUnread(rs.getString("unread"));
			
			StringBuffer qryRec = new StringBuffer(1024);
			qryRec.append("select recipient,unread from messagerecipients where id=\'");
			qryRec.append(id);
			qryRec.append("\'");
			Statement stRec = con.getCon().createStatement();

			ResultSet rsRec = stRec.executeQuery(qryRec.toString());
			Vector recipients =new Vector();
			Vector recipientIds =new Vector();
			while (rsRec.next()){
				int recid=rsRec.getInt("recipient");
				
				
				String unread=rsRec.getString("unread");
				User u=new User();
				u.setID(recid);
				String foafid=u.getFOAFID(recid);
				recipientIds.add(foafid);
				//adding all recipients of the message
				//if (rs.getInt("sender")!=recid){
					//common.Utility.log.debug(u.getName(recid).toString());
					recipients.add(u.getName(recid)+"("+unread+")");
					//recipientIds.add(Integer.toString( recid ));
				//}
				
			}
			message.setRecipientIds(recipientIds);
			message.setRecipients(recipients);
			//message.setRecipientIds(recipientIds);
			messages.add(message);
		}

		updateUnread(id);
		rs.close();
		st.close();
		con.disconnect();

		return messages;
	}

	/**
	 * Updates the state of a particular message, indicating that the message
	 * has now been read. The message in question is identified by the entered
	 * message ID.
	 * 
	 * A 'P' is used to indicate this.
	 * 
	 * @param id
	 *            Unique ID of the message
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	//no longer in use replaced by update read status in message class
	public void updateUnread(int id) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		con.connect();
		Statement st = con.getCon().createStatement();

		String sql = "update messages set unread='P' where id="
				+ String.valueOf(id);
		st.executeUpdate(sql);

		st.close();
		con.disconnect();
	}

	public void hideMessageFromSent(int idmin, int idmax)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		con.connect();
		Statement st = con.getCon().createStatement();

		String sql = "UPDATE messages SET hide_sent = true WHERE id >="
				+ String.valueOf(idmin) + " AND id <= " + String.valueOf(idmax);
		st.executeUpdate(sql);

		st.close();
		con.disconnect();
	}

	/**
	 * Adds a contact to the user's contact list.
	 * 
	 * @param userid
	 * @param contactID
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ServletException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public void addContact(int userid, int contactID)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException, ParserConfigurationException,
			SAXException, IOException, ServletException, OpenRDFException {
		con.connect();
		Statement st = con.getCon().createStatement();

		String sql = "INSERT INTO contacts (userid, contactid) VALUES('"
				+ userid + "','" + contactID + "')";
		st.executeUpdate(sql);

		st.close();
		con.disconnect();

		RDFi rdf = new RDFi();
		// Create empty Ontology Model
		Model model = ModelFactory.createDefaultModel();

		String rdfUserID = getUserRDFID(userid);
		String rdfContactID = getUserRDFID(contactID);

		Resource ourSpacesContactAccount = model.createResource(rdfContactID);
		Resource ourSpacesUserAccount = model.createResource(rdfUserID);
		ourSpacesUserAccount.addProperty(OurSpacesVRE.hasContact,
				ourSpacesContactAccount);

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		model.write(out);
		InputStream in = new ByteArrayInputStream(out.toByteArray());
		rdf.write(in);
	}

	/**
	 * deletes a contact from the user's contact list.
	 * 
	 * @param userid
	 * @param contactID
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws OpenRDFException
	 * @throws ServletException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public void deleteContact(int userid, int contactID)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException, ParserConfigurationException,
			SAXException, IOException, ServletException, OpenRDFException {
		con.connect();
		Statement st = con.getCon().createStatement();

		String sql = "delete from contacts where userid=" + userid
				+ " and contactid=" + contactID;
		st.executeUpdate(sql);

		st.close();
		con.disconnect();

		StringBuffer qry = new StringBuffer(1024);

		String rdfUserID = getUserRDFID(userid);
		String rdfContactID = getUserRDFID(contactID);

		qry.append("construct { ");
		qry.append("<" + rdfUserID + "> <" + OurSpacesVRE.hasContact.toString()
				+ "> ?a } where { ");
		qry.append("<" + rdfUserID + "> <" + OurSpacesVRE.hasContact.toString()
				+ "> ?a. FILTER (SAMETERM(?a, <" + rdfContactID + ">)) } ");

		String query = qry.toString();

		con.repConnect();

		GraphQuery q = con.getRepConnection().prepareGraphQuery(
				QueryLanguage.SPARQL, query);
		GraphQueryResult result = q.evaluate();
		con.getRepConnection().remove(result);

		con.repDisconnect();
	}

	/**
	 * Returns a vector containing all contacts belonging to a particular user
	 * based on the userid entered. The Vector contains multiple instances of
	 * the User JavaBean to store contacts.
	 * 
	 * @param userid
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public Vector getContacts(int userid) throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		// Database Connection
		con.connect();
		Statement st = con.getCon().createStatement();

		// Initialising the contacts Vector
		Vector contacts = new Vector();

		// SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry
				.append("select contacts.userid, contacts.contactid, user.ping from contacts, user  where contacts.userid=\'");
		qry.append(userid);
		qry
				.append("\' and (contacts.contactid = user.userid) order by user.ping DESC ");

		ResultSet rs = st.executeQuery(qry.toString());

		while (rs.next()) {
			// User JaveBean to store contacts
			User user = new User();
			user.setID(rs.getInt("contacts.userid"));
			user.setContactID(rs.getInt("contacts.contactid"));
			contacts.add(user);

		}

		rs.close();
		st.close();
		con.disconnect();

		return contacts;
	}

	/**
	 * Returns a vector containing all contacts belonging to a particular user
	 * based on the userid entered. The Vector contains multiple instances of
	 * the User JavaBean to store contacts.
	 * 
	 * @param userid
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public Vector getAllContacts() throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		// Database Connection
		con.connect();
		Statement st = con.getCon().createStatement();

		// Initialising the contacts Vector
		Vector users = new Vector();

		// SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select userid from user order by user.ping DESC ");

		ResultSet rs = st.executeQuery(qry.toString());

		while (rs.next()) {
			// User JaveBean to store contacts
			User user = new User();
			user.setID(rs.getInt("userid"));
			user.setContactID(rs.getInt("userid"));
			users.add(user);

		}

		rs.close();
		st.close();
		con.disconnect();

		return users;
	}



	/**
	 * Updates a user's personal status.
	 * 
	 * @param userid
	 * @param status
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public void updateStatus(int userid, String status) throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		con.connect();
		Statement st = con.getCon().createStatement();

		String sql = "DELETE FROM status where userid=" + userid;
		st.executeUpdate(sql);

		String sql2 = "INSERT INTO status (userid, status) values (" + userid
				+ ",'" + status + "')";
		st.executeUpdate(sql2);

		st.close();
		con.disconnect();
	}

	/**
	 * Deletes a user's personal status.
	 * 
	 * @param userid
	 * @param status
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public void deleteStatus(int userid) throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		con.connect();
		Statement st = con.getCon().createStatement();

		String sql = "DELETE FROM status where userid=" + userid;
		st.executeUpdate(sql);

		st.close();
		con.disconnect();
	}

	/**
	 * Returns a user's current status.
	 * 
	 * @param userid
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public String getStatus(int userid) throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		// Database Connection
		con.connect();
		Statement st = con.getCon().createStatement();

		// SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select status from status where userid=");
		qry.append(userid);

		String status = "";

		ResultSet rs = st.executeQuery(qry.toString());

		while (rs.next()) {
			status = rs.getString("status");
		}

		rs.close();
		st.close();
		con.disconnect();

		return status;
	}

	
	
	public String getSkypeID(int userid) throws SQLException,
	InstantiationException, IllegalAccessException,
	ClassNotFoundException {
		// Database Connection
		con.connect();
		Statement st = con.getCon().createStatement();
		
		// SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select skypeId from user where userid=");
		qry.append(userid);
		
		String skypeID = "";
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		while (rs.next()) {
			skypeID = rs.getString("skypeID");
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		if (skypeID == null) skypeID ="";
		
		return skypeID;
		}
	
	
	
	/**
	 * Returns a Vector containing all activities from a particular user based
	 * on the userid provided. An Activities JavaBean stores the information for
	 * each activity.
	 * 
	 * @param userid
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws OpenRDFException
	 * @throws ServletException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public Vector getActivities(int userid) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException,
			ParserConfigurationException, SAXException, IOException,
			ServletException, OpenRDFException {
		Activities act = new Activities();
		Vector activities = (Vector) act.getActivities(userid, 1);

		return activities;
	}


	/**
	 * Returns a Vector containing all activities from a particular user based
	 * on the userid provided. An Activities JavaBean stores the information for
	 * each activity.
	 * 
	 * This will only return 6 activities.
	 * 
	 * @param userid
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws OpenRDFException
	 * @throws ServletException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public Vector getWidgetActivities(int userid)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException, ParserConfigurationException,
			SAXException, IOException, ServletException, OpenRDFException {
		Activities act = new Activities();
		Vector activities = (Vector) act.getWidgetActivities(userid, 8);

		return activities;
	}

	/**
	 * designed for a user's home page widget
	 * 
	 * @param userid
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 */
	public Vector getWidgetContactsActivities(int userid)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException, ParserConfigurationException,
			SAXException, IOException, ServletException, OpenRDFException {
		Activities act = new Activities();
		Vector activities = (Vector) act
				.getNewHomeContactsActivities(userid, 8);
		// sort(activities);
		return activities;
	}

	public void sendDailyDigest(int userid) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException,
			ParserConfigurationException, SAXException, IOException,
			ServletException, OpenRDFException {
		Activities act1 = new Activities();
		Vector activities = (Vector) act1.getDailyDigestActivities(userid);
		// sort(activities);

		if (activities.size() > 0) {
			// Show activities
			String subject = "ourSpaces Daily Digest";
			String emailText = "Recent news from ourspaces: \n\n";

			int k = 0;
			boolean today = false;
			boolean yesterday = false;
			int prevDay = 0;
			int prevMonth = 0;
			for (int i = 0; i < activities.size(); i++) {
				Activities act = (Activities) activities.get(i);
				boolean comments = false;
				if (act.getComments() != null)
					comments = true;
				String message = act.getMessage();
				String date = act.getDate();
				String type = act.getType();
				int actID = act.getActID();

				String[] fields2 = date.split(" / ");
				int day = Integer.parseInt(fields2[0]);
				int month = Integer.parseInt(fields2[1]);
				month++;
				int year = Integer.parseInt(fields2[2]);
				emailText += day + "/" + month + "/" + year + " - " + message
						+ "\n";
			}

			common.Utility.log.debug(emailText);
		
			//send email
			common.Email.send(getEmail(userid), subject, emailText);
			
			// Update database
			con.connect();
			Statement st = con.getCon().createStatement();

			Calendar c = Calendar.getInstance();
			int day1 = c.get(Calendar.DATE);
			int month1 = c.get(Calendar.MONTH) + 1;
			int year1 = c.get(Calendar.YEAR);

			StringBuffer qry = new StringBuffer(1024);
			qry.append("update user set last_digest = '" + day1 + "/" + month1
					+ "/" + year1 + "' where userid=" + userid);

			st.executeUpdate(qry.toString());

			st.close();
			con.disconnect();
		}

	}

	/**
	 * An insertion sort algorithm for activities.
	 * 
	 * @param a
	 */
	public void sort(Vector<Activities> a) {
		for (int i = 1; i < a.size(); i++)
			insert(a.get(i), a, i);
	}

	private void insert(Activities n, Vector<Activities> a, int i) {
		for (; i > 0
				&& ((Comparable<Double>) a.get(i - 1).getTimeStamp())
						.compareTo(n.getTimeStamp()) > 0; i--)
			a.set(i, a.get(i - 1));
		a.set(i, n);
	}

	/**
	 * Returns a Vector containing all resources belonging to a particular user
	 * based on the userid provided. The resourceID is extracted from the
	 * getUserRDFID() method and forwarded to a method in the RDF Handler class
	 * (RDFi).
	 * 
	 * @param id
	 *            Unique ID of the user
	 * @return Vector of resources
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public Vector getResources(int id) throws ParserConfigurationException,
			SAXException, IOException, ServletException, OpenRDFException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		String resID = getUserRDFID(id);

		RDFi rdf = new RDFi();
		Vector resources = (Vector) rdf.getResources(resID);

		return resources;
	}
	

	/**
	 * Returns a Vector containing all processes belonging to a particular user
	 * based on the userid provided. The resourceID is extracted from the
	 * getUserRDFID() method and forwarded to a method in the RDF Handler class
	 * (RDFi).
	 * 
	 * @param id
	 *            Unique ID of the user
	 * @return Vector of resources
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public Vector getProcesses(int id) throws ParserConfigurationException,
			SAXException, IOException, ServletException, OpenRDFException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		String resID = getUserRDFID(id);

		RDFi rdf = new RDFi();
		Vector resources = (Vector) rdf.getProcesses(resID);

		return resources;
	}
	
	/**
	 * Returns a Vector containing all document resources not belonging to a particular user
	 * based on the userid provided. The resourceID is extracted from the
	 * getUserRDFID() method and forwarded to a method in the RDF Handler class
	 * (RDFi).*/
	public Vector getOthersDocResources(int id,int offset,int limit) throws ParserConfigurationException,
	SAXException, IOException, ServletException, OpenRDFException,
	InstantiationException, IllegalAccessException,
	ClassNotFoundException, SQLException {
		String resID = getUserRDFID(id);

		RDFi rdf = new RDFi();
		Vector resources = (Vector) rdf.getOtherUsersDocResources(resID,offset,limit);

		return resources;
	}	
	
	/**
	 * Returns a Vector containing all document resources not belonging to a particular user
	 * based on the userid provided. The resourceID is extracted from the
	 * getUserRDFID() method and forwarded to a method in the RDF Handler class
	 * (RDFi).*/
	public Vector getOthersResourcesByType(int id,String recType,int offset,int limit) throws ParserConfigurationException,
	SAXException, IOException, ServletException, OpenRDFException,
	InstantiationException, IllegalAccessException,
	ClassNotFoundException, SQLException {
		String resID = getUserRDFID(id);

		RDFi rdf = new RDFi();
		Vector resources = (Vector) rdf.getOthersResourcesByType(resID,recType,offset,limit);

		return resources;
	}	
	/**
	 * Returns a Vector containing all document resources  belonging to a particular user
	 * based on the userid provided. The resourceID is extracted from the
	 * getUserRDFID() method and forwarded to a method in the RDF Handler class
	 * (RDFi).*/
	public Vector getDocResources(int id,int offset,int limit) throws ParserConfigurationException,
	SAXException, IOException, ServletException, OpenRDFException,
	InstantiationException, IllegalAccessException,
	ClassNotFoundException, SQLException {
		String resID = getUserRDFID(id);

		RDFi rdf = new RDFi();
		Vector resources = (Vector) rdf.getDocResources(resID,offset,limit);

		return resources;
	}
	/**
	 * Returns a Vector containing all resources belonging to a particular user
	 * based on the userid provided. The resourceID is extracted from the
	 * getUserRDFID() method and forwarded to a method in the RDF Handler class
	 * (RDFi).
	 * 
	
	 */
	public Vector getResourcesByType(int id,String rectype) throws ParserConfigurationException,
			SAXException, IOException, ServletException, OpenRDFException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		String resID = getUserRDFID(id);

		RDFi rdf = new RDFi();
		Vector resources = (Vector) rdf.getResourcesByType(resID,rectype);

		return resources;
	}
	/**
	 * Returns a Vector containing all resources belonging to a particular user
	 * based on the userid provided. The resourceID is extracted from the
	 * getUserRDFID() method and forwarded to a method in the RDF Handler class
	 * (RDFi).
	 * 
	 * @param id
	 *            Unique ID of the user
	 * @return Vector of resources
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	
	public Vector getResourcesByType(int id,String rectype,int offset,int limit) throws ParserConfigurationException,
	SAXException, IOException, ServletException, OpenRDFException,
	InstantiationException, IllegalAccessException,
	ClassNotFoundException, SQLException {
String resID = getUserRDFID(id);

RDFi rdf = new RDFi();
Vector resources = (Vector) rdf.getResourcesByType(resID,rectype,offset,limit);

return resources;
}

	/**
	 * Returns the real name of the user based on the ID provided.
	 * 
	 * @param id
	 * @return String containing user's real name
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public String getName(int id) {
		String resID = getUserRDFID(id);
		String name = "";
		RDFi rdf = new RDFi();
		try {
			String firstname;
			firstname = rdf.getFoafProperty(resID, FOAF.firstName.toString());
			String surname = rdf.getFoafProperty(resID, FOAF.surname.toString());
			name = firstname + " " + surname;
		} catch (OpenRDFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return name;
	}
	
	/**
	 * Returns the real name of the user based on the ID provided.
	 * 
	 * @param id
	 * @return String containing user's real name
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public String getFirstName(int id) throws ParserConfigurationException,
			SAXException, IOException, ServletException, OpenRDFException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		String resID = getUserRDFID(id);

		RDFi rdf = new RDFi();
		String firstname = rdf.getFoafProperty(resID, FOAF.firstName.toString());
	
		String name = firstname;

		return name;
	}
	

	/**
	 * Returns the job title of the particular user based on the ID provided.
	 * 
	 * @param id
	 * @return String containing the user's title.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public String getTitle(int id) throws ParserConfigurationException,
			SAXException, IOException, ServletException, OpenRDFException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		String resID = getUserRDFID(id);

		RDFi rdf = new RDFi();
		String title = rdf.getFoafProperty(resID, OurSpacesVRE.hasJobTitle
				.toString());

		return title;
	}
	
	/**
	 * Returns a user's main topic of research.
	 * 
	 * @param id
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public String getResearchInterests(int id)
			throws ParserConfigurationException, SAXException, IOException,
			ServletException, OpenRDFException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		String resID = getUserRDFID(id);

		RDFi rdf = new RDFi();
		String interests = rdf.getFoafProperty(resID,
				OurSpacesVRE.hasResearchInterest.toString());

		return interests;
	}
	
	/**
	 * Returns the value of the specified property, for the particular user
	 * @param id user's ourSpaces RDF ID
	 * @param property
	 * @return The value of the specified property
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String getPropertyValue(int id, String property)
			throws ParserConfigurationException, SAXException, IOException,
			ServletException, OpenRDFException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		String resID = getUserRDFID(id);

		RDFi rdf = new RDFi();
		String title = rdf.getFoafProperty(resID, property);

		return title;
	}

	/**
	 * Returns the user's email address based on the ID provided.
	 * 
	 * @param id
	 * @return String containing the user's email.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public String getEmail(int id) throws ParserConfigurationException,
			SAXException, IOException, ServletException, OpenRDFException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		return getUsername(id);
	}

	/**
	 * Returns the organisation a user may belong to based on the ID of the user
	 * provided
	 * 
	 * @param id
	 * @return String containing the user's organisation.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public String getOrganisation(int id) throws ParserConfigurationException,
			SAXException, IOException, ServletException, OpenRDFException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		String resID = getUserRDFID(id);

		RDFi rdf = new RDFi();
		String organisation = "";

		if (rdf.getOrganisation(resID) == null)
			organisation = rdf.getPropertyValue(resID,
					OurSpacesVRE.hasOrganisation.toString());

		return organisation;
	}

	/**
	 * Returns a website that the user owns or is a part of based on the ID
	 * provided.
	 * 
	 * @param id
	 * @return String containing the user's website.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public String getWebsite(int id) throws ParserConfigurationException,
			SAXException, IOException, ServletException, OpenRDFException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		String resID = getUserRDFID(id);

		RDFi rdf = new RDFi();
		String website = rdf.getFoafProperty(resID, OurSpacesVRE.hasWebsite
				.toString());

		return website;
	}

	/**
	 * Returns the URL for a user's person photo.
	 * 
	 * @param id
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public String getPhoto(int id) throws ParserConfigurationException,
			SAXException, IOException, ServletException, OpenRDFException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		String resID = getFOAFID(id);

		RDFi rdf = new RDFi();
		String photo = rdf.getPropertyValue(resID,
				OurSpacesVRE.hasPhoto.toString());

		return photo;
	}

	/**
	 * Returns the first line of the user's address based on the ID provided.
	 * This is either the house name or number.
	 * 
	 * @param id
	 * @return String containing the name or number of the user's address
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public String getHouseNumber(int id) throws ParserConfigurationException,
			SAXException, IOException, ServletException, OpenRDFException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		String foafID = getFOAFID(id);
		RDFi rdf = new RDFi();
		String addressID = rdf.getAddressID(foafID);
		return rdf.getPropertyValue(addressID,
				org.policygrid.ontologies.Utility.houseNumber.toString());
	}

	/**
	 * Returns the user's street as part of their address, based on the ID
	 * provided.
	 * 
	 * @param id
	 * @return String containing the user's street.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public String getStreet(int id) throws ParserConfigurationException,
			SAXException, IOException, ServletException, OpenRDFException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		String foafID = getFOAFID(id);
		RDFi rdf = new RDFi();
		String addressID = rdf.getAddressID(foafID);
		return rdf.getPropertyValue(addressID,
				org.policygrid.ontologies.Utility.street.toString());
	}

	/**
	 * Returns the user's home town based on the ID provided.
	 * 
	 * @param id
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public String getTown(int id) throws ParserConfigurationException,
			SAXException, IOException, ServletException, OpenRDFException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		String foafID = getFOAFID(id);
		RDFi rdf = new RDFi();
		String addressID = rdf.getAddressID(foafID);
		return rdf.getPropertyValue(addressID,
				org.policygrid.ontologies.Utility.place.toString());
	}

	/**
	 * Returns the user's Country based on the ID provided
	 * 
	 * @param id
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public String getCountry(int id) throws ParserConfigurationException,
			SAXException, IOException, ServletException, OpenRDFException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		String foafID = getFOAFID(id);
		RDFi rdf = new RDFi();
		String addressID = rdf.getAddressID(foafID);
		return rdf.getPropertyValue(addressID,
				org.policygrid.ontologies.Utility.country.toString());

	}

	/**
	 * Returns the user's postcode based on the ID provided.
	 * 
	 * @param id
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public String getPostCode(int id) throws ParserConfigurationException,
			SAXException, IOException, ServletException, OpenRDFException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		String foafID = getFOAFID(id);
		RDFi rdf = new RDFi();
		String addressID = rdf.getAddressID(foafID);
		return rdf.getPropertyValue(addressID,
				org.policygrid.ontologies.Utility.postcode.toString());

	}

	/**
	 * Retrieves the unique RDF resourceID of a particular user.
	 * 
	 * @param id
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public String getRes(int id) throws ParserConfigurationException,
			SAXException, IOException, ServletException, OpenRDFException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		return getUserRDFID(id);
	}

	public void acceptTC(int id) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		con.connect();
		Statement st = con.getCon().createStatement();

		StringBuffer qry = new StringBuffer(1024);
		qry.append("update user set terms_and_conditions = true where userid="
				+ id);

		st.executeUpdate(qry.toString());

		st.close();
		con.disconnect();
	}
	
	public int getPLSconsentStatus(int userid) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		//check consent for PLS
				// Database connection
				con.connect();
				Statement st = con.getCon().createStatement();

				// SQL query
				StringBuffer sqlqry = new StringBuffer(1024);
				sqlqry.append("select opt_in_pls from user where userid=");
				sqlqry.append(userid);
				sqlqry.append("");

				ResultSet rs = st.executeQuery(sqlqry.toString());

				int i = 0;
				while (rs.next()) {
					i = rs.getInt("opt_in_pls");
				}

				rs.close();
				st.close();
				con.disconnect();
				
				return i;
	}

}
