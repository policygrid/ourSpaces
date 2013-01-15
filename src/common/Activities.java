package common;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;

import org.openrdf.OpenRDFException;
import org.policygrid.ontologies.FOAF;
import org.xml.sax.SAXException;

/**
 * 
 * @author Richard Reid
 * @version 1.0
 * 
 * JavaBean class intended to represent a particular Activity.
 * Each activity in the database represents a particular action
 * that a user has done in ourSpaces.
 *
 */

public class Activities 
{
	
	public int activityID;
	public String message;
	public String date;
	public int user;
	public String actType;
	public double timeStamp;
	public Vector comments;
	Connections con = new Connections();
	
	public Activities()
	{
		
	}
	
	/**
	 * Returns the unique ID of the activity.
	 * @return integer containing activity ID
	 */
	public int getActID()
	{
		return activityID;
	}
	
	/**
	 * Returns the ID of the user who the activity belongs to.
	 * @return Integer containing user ID.
	 */
	public int getUserID()
	{
		return user;
	}
	
	/**
	 * Returns the actual content of an activity.
	 * @return String containing message body.
	 */
	public String getMessage()
	{
		return message;
	}
	
	/**
	 * Returns the data that the activity was performed on.
	 * @return String containing activity date.
	 */
	public String getDate()
	{
		return date;
	}
	
	public String getType(){
		return actType;
	}
	
	public double getTimeStamp()
	{
		return timeStamp;
	}
	
	public Vector getComments()
	{
		return comments;
	}
	
	public void setComments(Vector comments)
	{
		this.comments = comments;
	}
	
	public void setTimeStamp(double timeStamp)
	{
		this.timeStamp = timeStamp;
	}
	
	
	/**
	 * Sets the activity unique ID
	 * @param actID
	 */
	public void setActID(int actID)
	{
		this.activityID = actID;
	}
	
	/**
	 * Sets the User ID of the activity
	 * @param id - user ID
	 */
	public void setID(int id)
	{
		this.user = id;
	}
	
	/**
	 * Sets the message of the activity
	 * @param message
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	/**
	 * Sets the date that the activity was created.
	 * @param date
	 */
	public void setDate(String date)
	{
		this.date = date;
	}
	
	public void setType(String type)
	{
		this.actType = type;
	}
	
	
	/**
	 * Adds the activity to the database.  The choice parameter defines
	 * which message will be used.
	 * 
	 * @param choice
	 * @param userid
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
	public void addActivity(int userID, int actionID, String action1, String action2) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, 
		ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException
	{
		con.connect();
		
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DATE);
		int month = c.get(Calendar.MONTH) + 1;
		int year = c.get(Calendar.YEAR);
		String date = day+" / "+month+" / "+year;
		
		String type = "";
		
		if((actionID >= 1) && (actionID <=5))
			type = "resources";
		
		if(actionID == 26)
			type = "resources";
		if(actionID == 27)
			type = "project";
		
		if(actionID == 28)
			type = "project";
		if(actionID == 29)
			type = "project";
		if(actionID == 30)
			type = "project";
		
		if((actionID >=6) && (actionID <=8))
			type = "messages";
		
		if((actionID >=9) && (actionID <=10))
			type = "contacts";
		
		if((actionID >=11) && (actionID <=13))
			type = "profile";
		
		if((actionID >=14) && (actionID <=16))
			type = "blog";
		
		if((actionID >=17) && (actionID <=21))
			type = "project";
		
		if((actionID >= 22) && (actionID <=25))
			type = "status";
		
		PreparedStatement pStmt;
		
			pStmt = con.getCon().prepareStatement("insert into activities (userID, actionID, endActionID1, endActionID2, date, type) values("+userID+","+actionID+",\""+action1+"\",\""+action2+"\",\""+date+"\",\""+type+"\")");
		
		
		pStmt.executeUpdate();
		
		con.disconnect();
	}
	
	/**
	 * returns the full activity description
	 * @param actionID
	 * @return
	 * @throws OpenRDFException 
	 * @throws ServletException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SQLException 
	 */
	public String getDescription(int userID, int actionID, String action1, String action2) throws ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		User user = new User();
		RDFi rdf = new RDFi();
		Project project = new Project();
		String username = user.getUsername(userID);
		String message = "";
		
		/* 
		 * Resource activities getFOAFID()
		 */
		
		// Resource upload
		if(actionID == 1){
			String fields[] = action1.split("#");
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a>&nbsp;<span class=\"nlg\" rel=\""+URLEncoder.encode(user.getFOAFID(userID),"ISO-8859-1")+"\"></span>  has uploaded the resource <a style=\"font-size:11px;\" href=\"/ourspaces/artifact_new.jsp?id=" + fields[1] + "\">" + rdf.getResourceTitle(action1) + "</a>&nbsp;<span class=\"nlg\" rel=\""+URLEncoder.encode(action1,"ISO-8859-1")+"\"></span>.";
		}
		
		// Resource tagged
		if(actionID == 2){
			String local = Utility.getLocalName(action1);
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a>&nbsp;<span class=\"nlg\" rel=\""+URLEncoder.encode(user.getFOAFID(userID),"ISO-8859-1")+"\"></span> has tagged the resource <a style=\"font-size:11px;\" href=\"/ourspaces/artifact_new.jsp?id=" + local + "\">" + rdf.getResourceTitle(action1) + "</a>&nbsp;<span class=\"nlg\" rel=\""+URLEncoder.encode(action1,"ISO-8859-1")+"\"></span>.";
		}
		// Resource Downloaded
		if(actionID == 3){
			String fields[] = action1.split("#");
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a>&nbsp;<span class=\"nlg\" rel=\""+URLEncoder.encode(user.getFOAFID(userID),"ISO-8859-1")+"\"></span> has downloaded the resource <a style=\"font-size:11px;\" href=\"/ourspaces/artifact_new.jsp?id=" + fields[1] + "\">" + rdf.getResourceTitle(action1) + "</a>&nbsp;<span class=\"nlg\" rel=\""+URLEncoder.encode(action1,"ISO-8859-1")+"\"></span>.";
		}
		// Resource described
		if(actionID == 4) {
			String fields[] = action1.split("#");
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a>&nbsp;<span class=\"nlg\" rel=\""+URLEncoder.encode(user.getFOAFID(userID),"ISO-8859-1")+"\"></span> has described the resource <a style=\"font-size:11px;\" href=\"/ourspaces/artifact_new.jsp?id=" + fields[1] + "\">" + rdf.getResourceTitle(action1) + "</a>&nbsp;<span class=\"nlg\" rel=\""+URLEncoder.encode(action1,"ISO-8859-1")+"\"></span>.";
		}
		// Resource description edited
		if(actionID == 5){
			String fields[] = action1.split("#");
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a>&nbsp;<span class=\"nlg\" rel=\""+URLEncoder.encode(user.getFOAFID(userID),"ISO-8859-1")+"\"></span> has edited the description of the resource: <a href=\"/ourspaces/artifact_new.jsp?id=" + fields[1] + "\">" + rdf.getResourceTitle(action1) +"</a>&nbsp;<span class=\"nlg\" rel=\""+URLEncoder.encode(action1,"ISO-8859-1")+"\"></span>.";
		}
		// Resource upload for project
		if(actionID == 26){
			String fields[] = action1.split("#");
			String fields2[] = action2.split("#");
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a>&nbsp;<span class=\"nlg\" rel=\""+URLEncoder.encode(user.getFOAFID(userID),"ISO-8859-1")+"\"></span> has uploaded the resource <a style=\"font-size:11px;\" href=\"/ourspaces/artifact_new.jsp?id=" + fields[1] + "\">" + rdf.getResourceTitle(action1) + "</a>&nbsp;<span class=\"nlg\" rel=\""+URLEncoder.encode(action1,"ISO-8859-1")+"\"></span> for the <a href=\"/ourspaces/project.jsp?id=" + fields2[1] + "\">" + project.getTitle(action2) + "</a>&nbsp;<span class=\"nlg\" rel=\""+URLEncoder.encode(action2,"ISO-8859-1")+"\"></span> project.";
		}
		/*
		 * Message activities
		 */
		
		// Message sent
		if(actionID == 6)
		{
			int otherUser = user.getUserIDFromRDFID(action1);
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a>&nbsp;<span class=\"nlg\" rel=\""+URLEncoder.encode(user.getFOAFID(userID),"ISO-8859-1")+"\"></span> has sent a message to <a href=\"/ourspaces/profile.jsp?id=" + otherUser + "\">" + user.getName(otherUser) + "</a>.";
		}
		
		// Message read
		if(actionID == 7)
		{
			int otherUser = user.getUserIDFromRDFID(action1);
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a>&nbsp;<span class=\"nlg\" rel=\""+URLEncoder.encode(user.getFOAFID(userID),"ISO-8859-1")+"\"></span> has read a message from <a href=\"/ourspaces/profile.jsp?id=" + otherUser + "\">" + user.getName(otherUser) + "</a>.";
		}
		
		// Message deleted
		if(actionID == 8)
		{
			int otherUser = user.getUserIDFromRDFID(action1);
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a>&nbsp;<span class=\"nlg\" rel=\""+URLEncoder.encode(user.getFOAFID(userID),"ISO-8859-1")+"\"></span> has deleted a message from <a href=\"/ourspaces/profile.jsp?id=" + otherUser + "\">" + user.getName(otherUser) + "</a>.";
		}
		
		/*
		 * Contact activities
		 */
		
		// Contact added
		if(actionID == 9)
		{
			int otherUser = user.getUserIDFromRDFID(action1);
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a>&nbsp;<span class=\"nlg\" rel=\""+URLEncoder.encode(user.getFOAFID(userID),"ISO-8859-1")+"\"></span> has added <a href=\"/ourspaces/profile.jsp?id=" + otherUser + "\">" + user.getName(otherUser) + "</a> as a contact.";
		}
		
		// Contact removed
		if(actionID == 10)
		{
			int otherUser = user.getUserIDFromRDFID(action1);
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a>&nbsp;<span class=\"nlg\" rel=\""+URLEncoder.encode(user.getFOAFID(userID),"ISO-8859-1")+"\"></span> has removed <a href=\"/ourspaces/profile.jsp?id=" + otherUser + "\">" + user.getName(otherUser) + "</a> from their contacts.";
		}
		
		/*
		 * Profile activities
		 */
		
		// Profile created
		if(actionID == 11)
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a>&nbsp;<span class=\"nlg\" rel=\""+URLEncoder.encode(user.getFOAFID(userID),"ISO-8859-1")+"\"></span> has registered and created a profile.";
		
		// Profile edited
		if(actionID == 12)
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a>&nbsp;<span class=\"nlg\" rel=\""+URLEncoder.encode(user.getFOAFID(userID),"ISO-8859-1")+"\"></span> has edited their profile.";
		
		// Photo uploaded
		if(actionID == 13)
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a>&nbsp;<span class=\"nlg\" rel=\""+URLEncoder.encode(user.getFOAFID(userID),"ISO-8859-1")+"\"></span> has updated their profile photo.";
		
		/*
		 * User blogs
		 */
		
		// Add blog post
		if(actionID == 14)
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a> has added a new post to their <a href=\"/ourspaces/blog.jsp?id=" + userID + "\">blog</a>.";
		
		// Receive blog comment
		if(actionID == 15)
		{
			int otherUser = user.getUserIDFromRDFID(action1);
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a> has received a comment to their <a href=\"/ourspaces/blog.jsp?id=" + otherUser + "\">blog</a> from <a href=\"/ourspaces/profile.jsp?id=" + otherUser + "\">" + user.getName(otherUser) + "</a>.";
		}
		
		// Make blog comment
		if(actionID == 16)
		{
			int otherUser = user.getUserIDFromRDFID(action1);
			String uname = user.getUsername(otherUser);
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a> has posted a comment on <a href=\"/ourspaces/blog.jsp?id=" + uname + "\">" + user.getName(otherUser) + "\'s blog</a>.";
		}
		
		/*
		 * Project activities
		 */
		
		// Create project
		if(actionID == 17)
		{
			String[] fields = action1.split("#");
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a> has created the project <em> <a href=\"/ourspaces/project.jsp?id=" + fields[1] + "\">" + project.getTitle(action1) + "</a></em>.";
		}
		// Project ended
		if(actionID == 18)
		{
			String[] fields = action1.split("#");
			message = "The project <em><a href=\"/ourspaces/project.jsp?id=" + fields[1] + ">" + project.getTitle(action1) + "</a></em>, created by <a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a>, has ended.";
		}
		// Project member joined
		if(actionID == 19)
		{
			String[] fields = action1.split("#");
			String foafID = action2;
			
			String firstname = rdf.getPropertyValue(foafID, FOAF.firstName.toString());
			String surname = rdf.getPropertyValue(foafID, FOAF.surname.toString());
			String name = firstname + " " + surname;
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a> has added " + name + "</a> to the <em> <a href=\"/ourspaces/project.jsp?id=" + fields[1] + "\">" + project.getTitle(action1) + "</a></em> project.";
		}
		// Project member left
		if(actionID == 20)
		{
			String[] fields = action1.split("#");
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a> has left the project <em> <a href=\"/ourspaces/project.jsp?id=" + fields[1] + "\">" + project.getTitle(action1) + "</a></em>.";
		}
		// Project news post added
		if(actionID == 21)
		{
			String[] fields = action1.split("#");
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a> has posted a message on the <a href=\""+action1+ "\">" + project.getTitle(action2) + "</a> project blog.";
		}
		// subproject created
		if(actionID == 27)
		{
			String[] fields = action1.split("#");
			String[] fields2 = action2.split("#");
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a> has create the sub-project <a href=\"/ourspaces/project.jsp?id=" + fields[1] + "\">" + project.getTitle(action1) + "</a> as part of the <a href=\"/ourspaces/project.jsp?id=" + fields2[1] + "\">" + project.getTitle(action1) + "</a> project.";
		}
		// project blog created
		if(actionID == 29)
		{
			String[] fields1 = action2.split("#");
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a> has created a new blog entitled <em>"+action1+"</em> for the <a href=\"/ourspaces/project.jsp?id=" + fields1[1] + "\">" + project.getTitle(action2) + "</a> project.";
		}
		
		/*
		 * Status activities
		 */
		
		// User Available
		if(actionID == 22)
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a> has changed their status to <strong>Available</strong>.";
		
		// User busy
		if(actionID == 23)
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a> has changed their status to <strong>Busy</strong>.";
		
		// User not at desk
		if(actionID == 24)
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a> has changed their status to <strong>Not at my desk</strong>.";
		
		// User's typed status
		if(actionID == 25)
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a> has changed their status: <br /><span style=\"padding:15px; color:#666666; font-size:10px;\"><em>" + action1 + "</em></span>";
		
		/*
		 * Event activities
		 */
		
		// Add event blog post
		if(actionID == 28)
		{
			Event event = new Event();
			ArrayList details = event.getEventDetails(Integer.parseInt(action2));
			Event e = (Event) details.get(0);
			String name = e.getTitle();
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a> has added a new post to the <a href=\""+action1 + "\">"+name+"</a> event blog.";
		}
		
		// Add event
		if(actionID == 30)
		{
			Event event = new Event();			
			String[] fields = action1.split("=");		
			ArrayList details = event.getEventDetails(Integer.parseInt(fields[1]));
			Event e = (Event) details.get(0);
			
			Project project2 = new Project();
			String projectID = action2;
			String[] fields2 = projectID.split("#");
			
			String name = e.getTitle();
			message = "<a href=\"/ourspaces/profile.jsp?id=" + userID + "\">" + user.getName(userID) + "</a> has created a new event entitled <em><a href=\""+action1 + "\">"+name+"</a></em> for the <a href=\"/ourspaces/project.jsp?id=" + fields2[1] + "\">"+project2.getTitle(projectID)+" project.";
		}
		
		return message;
	}
	
	public String getDescriptionUN(int userID, int actionID, String action1, String action2) throws ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		User user = new User();
		RDFi rdf = new RDFi();
		Project project = new Project();
		String username = user.getUsername(userID);
		String message = "";
		
		/*
		 * Resource activities
		 */
		
		// Resource upload
		if(actionID == 1){
			String fields[] = action1.split("#");
			message =  user.getName(userID) + " has uploaded the resource "+ rdf.getResourceTitle(action1) + ".\n";
		}
		
		// Resource tagged
		if(actionID == 2){
			String fields[] = action1.split("#");
			message = user.getName(userID) + " has tagged the resource " + rdf.getResourceTitle(action1) + ".\n";
		}
		// Resource Downloaded
		if(actionID == 3){
			String fields[] = action1.split("#");
			message = user.getName(userID) + " has downloaded the resource " + rdf.getResourceTitle(action1) + ".\n";
		}
		// Resource described
		if(actionID == 4) {
			String fields[] = action1.split("#");
			message = user.getName(userID) + " has described the resource " + rdf.getResourceTitle(action1) + ".\n";
		}
		// Resource description edited
		if(actionID == 5){
			String fields[] = action1.split("#");
			message = user.getName(userID) + " has edited the description of the resource: " + rdf.getResourceTitle(action1) +".\n";
		}
		// Resource upload for project
		if(actionID == 26){
			String fields[] = action1.split("#");
			String fields2[] = action2.split("#");
			message = user.getName(userID) + " has uploaded the resource " + project.getTitle(action2) + " project. \n";
		}
		/*
		 * Message activities
		 */
		
		// Message sent
		if(actionID == 6)
		{
			int otherUser = user.getUserIDFromRDFID(action1);
			message =  user.getName(userID) + " has sent a message to " + user.getName(otherUser) + ".\n";
		}
		
		// Message read
		if(actionID == 7)
		{
			int otherUser = user.getUserIDFromRDFID(action1);
			message =  user.getName(userID) + " has read a message from " + user.getName(otherUser) + ".\n";
		}
		
		// Message deleted
		if(actionID == 8)
		{
			int otherUser = user.getUserIDFromRDFID(action1);
			message =  user.getName(userID) + " has deleted a message from " + user.getName(otherUser) + ".\n";
		}
		
		/*
		 * Contact activities
		 */
		
		// Contact added
		if(actionID == 9)
		{
			int otherUser = user.getUserIDFromRDFID(action1);
			message = user.getName(userID) + " has added " + user.getName(otherUser) + " as a contact.\n";
		}
		
		// Contact removed
		if(actionID == 10)
		{
			int otherUser = user.getUserIDFromRDFID(action1);
			message = user.getName(userID) + " has removed " + user.getName(otherUser) + " from their contacts.\n";
		}
		
		/*
		 * Profile activities
		 */
		
		// Profile created
		if(actionID == 11)
			message =  user.getName(userID) + " has registered and created a profile.\n";
		
		// Profile edited
		if(actionID == 12)
			message =  user.getName(userID) + " has edited their profile.\n";
		
		// Photo uploaded
		if(actionID == 13)
			message =  user.getName(userID) + " has updated their profile photo.\n";
		
		/*
		 * User blogs
		 */
		
		// Add blog post
		if(actionID == 14)
			message =  user.getName(userID) + " has added a new post to their blog.\n";
		
		// Receive blog comment
		if(actionID == 15)
		{
			int otherUser = user.getUserIDFromRDFID(action1);
			message = user.getName(userID) + " has received a comment to their blog from " + user.getName(otherUser) + ".\n";
		}
		
		// Make blog comment
		if(actionID == 16)
		{
			int otherUser = user.getUserIDFromRDFID(action1);
			String uname = user.getUsername(otherUser);
			message = user.getName(userID) + " has posted a comment on " + user.getName(otherUser) + "'s blog.\n";
		}
		
		/*
		 * Project activities
		 */
		
		// Create project
		if(actionID == 17)
		{
			String[] fields = action1.split("#");
			message =  user.getName(userID) + " has created the project " + project.getTitle(action1) + ".\n";
		}
		// Project ended
		if(actionID == 18)
		{
			String[] fields = action1.split("#");
			message = "The project " + project.getTitle(action1) + ", created by " + user.getName(userID) + ", has ended.\n";
		}
		// Project member joined
		if(actionID == 19)
		{
			String[] fields = action1.split("#");
			String foafID = action2;
			
			String firstname = rdf.getPropertyValue(foafID, FOAF.firstName.toString());
			String surname = rdf.getPropertyValue(foafID, FOAF.surname.toString());
			String name = firstname + " " + surname;
			message = user.getName(userID) + " has added " + name + " to the " + project.getTitle(action1) + " project.\n";
		}
		// Project member left
		if(actionID == 20)
		{
			String[] fields = action1.split("#");
			message = user.getName(userID) + " has left the project " + project.getTitle(action1) + ".\n";
		}
		// Project news post added
		if(actionID == 21)
		{
			String[] fields = action1.split("#");
			message = user.getName(userID) + " has posted a message on the " + project.getTitle(action2) + " project blog.\n";
		}
		// subproject created
		if(actionID == 27)
		{
			String[] fields = action1.split("#");
			String[] fields2 = action2.split("#");
			message =  user.getName(userID) + " has create the sub-project " + project.getTitle(action1) + " as part of the " + project.getTitle(action1) + " project.\n";
		}
		// project blog created
		if(actionID == 29)
		{
			String[] fields1 = action2.split("#");
			message =  user.getName(userID) + " has created a new blog entitled "+action1+" for the " + project.getTitle(action2) + " project.\n";
		}
		
		
		/*
		 * Event activities
		 */
		
		// Add event blog post
		if(actionID == 28)
		{
			Event event = new Event();
			ArrayList details = event.getEventDetails(Integer.parseInt(action2));
			Event e = (Event) details.get(0);
			String name = e.getTitle();
			message = user.getName(userID) + " has added a new post to the "+name+" event blog.\n";
		}
		
		// Add event
		if(actionID == 30)
		{
			Event event = new Event();			
			String[] fields = action1.split("=");		
			ArrayList details = event.getEventDetails(Integer.parseInt(fields[1]));
			Event e = (Event) details.get(0);
			
			Project project2 = new Project();
			String projectID = action2;
			String[] fields2 = projectID.split("#");
			
			String name = e.getTitle();
			message = user.getName(userID) + " has created a new event entitled "+name+" for the "+project2.getTitle(projectID)+" project.\n";
		}
		
		return message;
	}
	
	/**
	 * Returns the activities of a particular user.  Each activity is retrieved 
	 * and stored in a Vector containing multiple Activities JavaBeans.
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
	public Vector getActivities(int userid, int type) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException
	{
		Vector activities = new Vector();
		
		con.connect();
		String message = "";
		Statement st = con.getCon().createStatement();
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select * from activities where userID=\'");
		qry.append(userid);
		qry.append("\' order by activityID DESC");
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		while(rs.next())
		{
			Activities act = new Activities();
			act.setActID(rs.getInt("activityID"));
			act.setID(rs.getInt("userID"));
			act.setType(rs.getString("type"));
			act.setDate(rs.getString("date"));
			String date = rs.getString("date");
			
			String[] fields2 = date.split(" / ");
			int day = Integer.parseInt(fields2[0]);
			int month = Integer.parseInt(fields2[1]);
			if(month == 0)
				month = 1;
			int year = Integer.parseInt(fields2[2]);
			double total = year + month + day;
			act.setTimeStamp(total);
			
			int action = (Integer) rs.getInt("actionID");
			String endAction1 = (String) rs.getString("endActionID1");
			String endAction2 = (String) rs.getString("endActionID2");
			if(type == 1){
				message = act.getDescription(rs.getInt("userID"), action, endAction1, endAction2);
			}
			
			
			act.setMessage(message);
			
			activities.add(act);
		}
		
		rs.close();
		st.close();

		con.disconnect();
		
		return activities;
	}
	
	/**
	 * Returns only 6 activities for a particular user, so they can be displayed
	 * in a user's home page widget.
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
	public Vector getWidgetActivities(int userid, int amount) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException
	{
		Vector activities = new Vector();
		
		con.connect();
		String message = "";
		Statement st = con.getCon().createStatement();
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select * from activities where userID=\'");
		qry.append(userid);
		qry.append("\' order by activityID DESC");
		
		ResultSet rs = st.executeQuery(qry.toString());
		int count = 0;
		while(rs.next())
		{
			if(count == amount)
				break;
			Activities act = new Activities();
			act.setActID(rs.getInt("activityID"));
			act.setID(rs.getInt("userID"));
			act.setType(rs.getString("type"));
			act.setDate(rs.getString("date"));
			String date = rs.getString("date");
			
			String[] fields2 = date.split(" / ");
			int day = Integer.parseInt(fields2[0]);
			int month = Integer.parseInt(fields2[1]);
			if(month == 0)
				month = 1;
			int year = Integer.parseInt(fields2[2]);
			double total = year + month + day;
			act.setTimeStamp(total);
			
			int action = (Integer) rs.getInt("actionID");
			String endAction1 = (String) rs.getString("endActionID1");
			String endAction2 = (String) rs.getString("endActionID2");
			message = act.getDescription(rs.getInt("userID"), action, endAction1, endAction2);
			
			
			act.setMessage(message);
			
			
			activities.add(act);
			count++;
		}
		
		rs.close();
		st.close();

		con.disconnect();
		
		return activities;
	}
	
	/**
	 * For returning activities for the new-style home page without
	 * movable widgets.  
	 * @param userid
	 * @param type
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
	public Vector getNewHomeContactsActivities(int userid, int amount) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException
	{
		Vector activities = new Vector();
		
		con.connect();
		String message = "";
		Statement st = con.getCon().createStatement();
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select activities.activityID, activities.userID, activities.actionID, activities.endActionID1, activities.endActionID2, activities.date, activities.type from activities, contacts where contacts.userid=");
		qry.append(userid);
		qry.append(" and activities.userID = contacts.contactid order by activities.activityID DESC");
		
		ResultSet rs = st.executeQuery(qry.toString());
		int count = 0;
		while(rs.next())
		{
			if(count == amount)
				break;
			Activities act = new Activities();
			
			act.setActID(rs.getInt("activityID"));
			act.setID(rs.getInt("userID"));
			act.setType(rs.getString("type"));
			act.setDate(rs.getString("date"));
			String date = rs.getString("date");
			
			String[] fields2 = date.split(" / ");
			int day = Integer.parseInt(fields2[0]);
			String sDay = "";
			int month = Integer.parseInt(fields2[1]);
			month++;
			String sMonth = "";
			int year = Integer.parseInt(fields2[2]);
			String sYear = "";
			
			if(day < 10)
				sDay = "0" + String.valueOf(day);
			else
				sDay = String.valueOf(day);
			
			if(month < 10)
				sMonth = "0" + String.valueOf(month);
			else
				sMonth = String.valueOf(month);
			
			sYear = String.valueOf(year);
			
			String sDate = sYear + sMonth + sDay;
			
			double total = Double.parseDouble(sDate);
			
			act.setTimeStamp(total);
			
			int action = (Integer) rs.getInt("actionID");
			String endAction1 = (String) rs.getString("endActionID1");
			String endAction2 = (String) rs.getString("endActionID2");
			
			message = act.getDescription(rs.getInt("userID"), action, endAction1, endAction2);
			
			act.setMessage(message);
			
			Comment com = new Comment();
			//act.setComments(com.getComments(rs.getInt("activityID")));
			
			activities.add(act);
			
			count++;
		}
		
		rs.close();
		st.close();

		con.disconnect();
		
		return activities;
	}
	
	
	public Vector getDailyDigestActivities(int userid) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException
	{
		Calendar c = Calendar.getInstance();
		int day1 = c.get(Calendar.DATE);
		int month1 = c.get(Calendar.MONTH) +1;
		int year1 = c.get(Calendar.YEAR);
		String date1 = day1+" / "+month1+" / "+year1;
				
		Vector activities = new Vector();
		
		con.connect();
		String message = "";
		Statement st = con.getCon().createStatement();
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select activities.activityID, activities.userID, activities.actionID, activities.endActionID1, activities.endActionID2, activities.date, activities.type from activities, contacts where contacts.userid=");
		qry.append(userid);
		qry.append(" and activities.userID = contacts.contactid and activities.date = '"+date1+"' order by activities.activityID DESC");
		
		ResultSet rs = st.executeQuery(qry.toString());
		int count = 0;
		while(rs.next())
		{
			
			Activities act = new Activities();
			
			act.setActID(rs.getInt("activityID"));
			act.setID(rs.getInt("userID"));
			act.setType(rs.getString("type"));
			act.setDate(rs.getString("date"));
			String date = rs.getString("date");
			
			String[] fields2 = date.split(" / ");
			int day = Integer.parseInt(fields2[0]);
			String sDay = "";
			int month = Integer.parseInt(fields2[1]);
			month++;
			String sMonth = "";
			int year = Integer.parseInt(fields2[2]);
			String sYear = "";
			
			if(day < 10)
				sDay = "0" + String.valueOf(day);
			else
				sDay = String.valueOf(day);
			
			if(month < 10)
				sMonth = "0" + String.valueOf(month);
			else
				sMonth = String.valueOf(month);
			
			sYear = String.valueOf(year);
			
			String sDate = sYear + sMonth + sDay;
			
			double total = Double.parseDouble(sDate);
			
			act.setTimeStamp(total);
			
			int action = (Integer) rs.getInt("actionID");
			String endAction1 = (String) rs.getString("endActionID1");
			String endAction2 = (String) rs.getString("endActionID2");
			
			message = act.getDescriptionUN(rs.getInt("userID"), action, endAction1, endAction2);
			
			act.setMessage(message);
			
			Comment com = new Comment();
			//act.setComments(com.getComments(rs.getInt("activityID")));
			
			activities.add(act);
			
			count++;
		}
		
		rs.close();
		st.close();

		con.disconnect();
		
		return activities;
	}
	
	
	/**
	 * Returns all activities in the database regardless of user.  Newer activities
	 * are sorted first.  Each activity is stored as a JavaBean Activity Class as
	 * part of a Vector.
	 * 
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Vector getAllActivities() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		Vector activities = new Vector();
		
		con.connect();
		Statement st = con.getCon().createStatement();
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select * from activities order by activityID DESC");
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		while(rs.next())
		{
			Activities act = new Activities();
			act.setActID(rs.getInt("activityID"));
			act.setID(rs.getInt("userID"));
			act.setMessage(rs.getString("messages"));
			act.setDate(rs.getString("date"));
			activities.add(act);
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return activities;
	}
	
	/**
	 * Returns all activities relating to a specific type, e.g. projects.
	 * 
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
	public Vector getSpecificActivities(String actType, int type) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException
	{
		Vector activities = new Vector();
		
		con.connect();
		Statement st = con.getCon().createStatement();
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select * from activities where type=\"" + actType + "\" OR actionID=26 order by activityID DESC");
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		while(rs.next())
		{
			Activities act = new Activities();
			act.setActID(rs.getInt("activityID"));
			act.setID(rs.getInt("userID"));
			act.setType(rs.getString("type"));
			act.setDate(rs.getString("date"));
			
			int action = (Integer) rs.getInt("actionID");
			String endAction1 = (String) rs.getString("endActionID1");
			String endAction2 = (String) rs.getString("endActionID2");
			if(type == 1){
				message = act.getDescription(rs.getInt("userID"), action, endAction1, endAction2);
			}
			
			
			act.setMessage(message);
			
			activities.add(act);
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return activities;
	}
	
	public Vector getSpecificProjectsActivities(String resourceID, int type)throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException
	{
		Vector activities = new Vector();
		
		con.connect();
		Statement st = con.getCon().createStatement();
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select * from activities where endActionID1=\"" + resourceID + "\" OR endActionID2=\"" + resourceID + "\" order by activityID DESC");
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		int i = 0;
		
		while(rs.next())
		{
			if(i == 17)
				break;
			Activities act = new Activities();
			act.setActID(rs.getInt("activityID"));
			act.setID(rs.getInt("userID"));
			act.setType(rs.getString("type"));
			act.setDate(rs.getString("date"));
			
			int action = (Integer) rs.getInt("actionID");
			String endAction1 = (String) rs.getString("endActionID1");
			String endAction2 = (String) rs.getString("endActionID2");
			if(type == 1){
				message = act.getDescription(rs.getInt("userID"), action, endAction1, endAction2);
			}

			
			
			act.setMessage(message);
			
			activities.add(act);
			
			i++;
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return activities;
	}
	
}
