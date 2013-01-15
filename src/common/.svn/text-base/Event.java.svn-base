package common;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.openrdf.OpenRDFException;
import org.policygrid.ontologies.FOAF;

public class Event {
	
	Connections con = new Connections();
	User user = new User();
	RDFi rdf = new RDFi();
	
	public int id;
	public String container;
	public String title;
	public String location;
	public String organiser;
	public String description;
	public String startTime;
	public String endTime;
	public String blogID;
	
	public int getID()
	{
		return id;
	}
	
	public String getContainer()
	{
		return container;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getLocation()
	{
		return location;
	}
	
	public String getOrganiser()
	{
		return organiser;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public String getStartTime()
	{
		return startTime;
	}
	
	public String getEndTime()
	{
		return endTime;
	}
	
	public String getBlogID()
	{
		return blogID;
	}
	
	public void setID(int id)
	{
		this.id = id;
	}
	
	public void setContainer(String container)
	{
		this.container = container;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void setLocation(String location)
	{
		this.location = location;
	}
	
	public void setOrganiser(String organiser)
	{
		this.organiser = organiser;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public void setStartTime(long startTime)
	{
		
	}
	
	public void setEndTime(long endTime)
	{

	}
	
	public void setBlogID(String blogID)
	{
		this.blogID = blogID;
	}
	
	public Event()
	{
		
	}
	
	public ArrayList getEventDetails(int eventID) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		ArrayList eventDetails = new ArrayList();
		
		con.connect();
		
		Statement st = con.getCon().createStatement();
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select containerID, title, location, description, startTime, endTime, userID, blogID from events where eventID=");
		qry.append(eventID);
		
		ResultSet rs = st.executeQuery(qry.toString());
		while(rs.next())
		{
			Event event = new Event();
			event.setContainer(rs.getString("containerID"));
			event.setTitle(rs.getString("title"));
			event.setLocation(rs.getString("location"));
			event.setDescription(rs.getString("description"));
			event.setStartTime(rs.getLong("startTime"));
			event.setEndTime(rs.getLong("endTime"));
			event.setOrganiser(rs.getString("userID"));
			event.setBlogID(rs.getString("blogID"));
			eventDetails.add(event);
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return eventDetails;
	}
	
	/**
	 * Returns a list of users who are attending the event and have confirmed their
	 * status.
	 * 
	 * @param eventID
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws OpenRDFException
	 * @throws IOException
	 */
	public ArrayList getEventAttendees(int eventID) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, OpenRDFException, IOException
	{
		ArrayList attendees = new ArrayList();
		
		con.connect();
		
		Statement st = con.getCon().createStatement();
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select attendeeID from attendees where status=\"attending\" and eventID=");
		qry.append(eventID);
		
		ResultSet rs = st.executeQuery(qry.toString());
		while(rs.next())
		{
			Person person = new Person();
			String attendeeFoafID = rs.getString("attendeeID");
			String firstname = rdf.getPropertyValue(attendeeFoafID, FOAF.firstName.toString());
			String surname = rdf.getPropertyValue(attendeeFoafID, FOAF.surname.toString());
			String fullname = firstname + " " + surname;
			person.setName(fullname);
			person.setUserID(user.getUserIDFromFOAF(attendeeFoafID));
			person.setFoafID(attendeeFoafID);
			attendees.add(person);
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return attendees;
	}
	
	/**
	 * Checks to see if a user has been invited to an event. This is used in the event
	 * page to determine if a user can accept/decline an event.
	 * 
	 * @param foafID
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public boolean checkAttendee(int eventID, String foafID) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		boolean attendee = false;
		
		con.connect();
		Statement st = con.getCon().createStatement();
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select status from attendees where eventID="+eventID+" and attendeeID=\""+foafID+"\"");
		
		ResultSet rs = st.executeQuery(qry.toString());
		while(rs.next())
		{
			if(rs.getString("status") != null || !rs.getString("status").equals(""))
				attendee = true;
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return attendee;	
	}
	
	public String getStatus(int eventID, String foafID) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		String status = "";
		
		con.connect();
		Statement st = con.getCon().createStatement();
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select status from attendees where eventID="+eventID+" and attendeeID=\""+foafID+"\"");
		
		ResultSet rs = st.executeQuery(qry.toString());
		while(rs.next())
		{
			status = rs.getString("status");
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return status;	
	}
	
	public String updateAttendingStatus(int eventID, String foafID, String status) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, OpenRDFException, IOException
	{
		con.connect();
		
		Statement st = con.getCon().createStatement();
		
		String sql = "update attendees set status=\""+status+"\" where eventID="+eventID+" and attendeeID=\""+foafID+"\"";
	    st.executeUpdate(sql);

		st.close();
		con.disconnect();
		
		return container;
	}
	
	
	public String getEventContainer(int eventID) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, OpenRDFException, IOException
	{
		String container = "";
		
		con.connect();
		
		Statement st = con.getCon().createStatement();
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select containerID from attendees where eventID=");
		qry.append(eventID);
		
		ResultSet rs = st.executeQuery(qry.toString());
		while(rs.next())
		{
			container = rs.getString("containerID");
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return container;
	}
	
	public String getEventBlogContainer(int eventID) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, OpenRDFException, IOException
	{
		String container = "";
		
		con.connect();
		
		Statement st = con.getCon().createStatement();
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select blogID from events where eventID=");
		qry.append(eventID);
		
		ResultSet rs = st.executeQuery(qry.toString());
		while(rs.next())
		{
			container = rs.getString("blogID");
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return container;
	}

}
