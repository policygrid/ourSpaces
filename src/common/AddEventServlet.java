package common;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.UUID;

import org.openrdf.OpenRDFException;
import org.policygrid.ontologies.FOAF;
import org.policygrid.ontologies.OurSpacesVRE;
import org.xml.sax.SAXException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

/**
 * 
 * @author Ed
 * @version 1.0
 *
 */
 public class AddEventServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
 {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
    Connections con = new Connections();
   
	public AddEventServlet() 
	{
		super();
	}   	
	
	protected String getStart(HttpServletRequest request){

    	//Assemble start time
    	String day1 = ""; 
    	if (request.getParameter("day1") != null) day1 = request.getParameter("day1");
    	String month1 = ""; 
    	if (request.getParameter("month1") != null) month1 = request.getParameter("month1");
    	String year1 = ""; 
    	if (request.getParameter("year1") != null) year1 = request.getParameter("year1");
    	String hour1 = ""; 
    	if (request.getParameter("hour1") != null) hour1 = request.getParameter("hour1");
    	String minute1 = ""; 
    	if (request.getParameter("minute1") != null) minute1 = request.getParameter("minute1");

        //Fix the number format;
     	if (day1.length() == 1) day1 = "0"+day1;
     	if (month1.length() == 1) month1 = "0"+month1;
     	if (year1.length() == 1) year1 = "0"+year1;
     	if (hour1.length() == 1) hour1 = "0"+hour1;
     	if (minute1.length() == 1) minute1 = "0"+minute1;
 	    String start = year1+"-"+month1+"-"+day1+" "+hour1+":"+minute1+":00";
 	    return start;
	}
	protected String getEnd(HttpServletRequest request){

    	String day2 = ""; 
    	if (request.getParameter("day2") != null) day2 = request.getParameter("day2");
    	String month2 = ""; 
    	if (request.getParameter("month2") != null) month2 = request.getParameter("month2");
    	String year2 = ""; 
    	if (request.getParameter("year2") != null) year2 = request.getParameter("year2");
    	String hour2 = ""; 
    	if (request.getParameter("hour2") != null) hour2 = request.getParameter("hour2");
    	String minute2 = ""; 
    	if (request.getParameter("minute2") != null) minute2 = request.getParameter("minute2");

        //Fix the number format;
    	if (day2.length() == 1) day2 = "0"+day2;
    	if (month2.length() == 1) month2 = "0"+month2;
    	if (year2.length() == 1) year2 = "0"+year2;
    	if (hour2.length() == 1) hour2 = "0"+hour2;
    	if (minute2.length() == 1) minute2 = "0"+minute2;

 	    String end = year2+"-"+month2+"-"+day2+" "+hour2+":"+minute2+":00";
 	    return end;
	}
	protected int insertEvent(HttpServletRequest request){


		int eventID = 0;
    	//Add event to the database here
		//--------------------------
		String start = getStart(request);
		String end = getEnd(request);
    	String title = "";
    	if (request.getParameter("title") != null) title = request.getParameter("title");
        
    	String location = ""; 
    	if (request.getParameter("location") != null) 
    		location = request.getParameter("location");
        
    	String description = ""; 
    	if (request.getParameter("description") != null) 
    		description = request.getParameter("description");

    	String container = ""; 
    	if (request.getParameter("container") != null) 
    		container = request.getParameter("container");  
   
    	
    	
        HttpSession session = request.getSession();
    	Container cont = new Container();
    	User userSession = (User) session.getAttribute("use");

        String userID = userSession.getRDFID();
        String blogID = "";
		try {
			blogID = cont.createBlogContainer("");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Connections con = new Connections();
    	
    	
 	    

    	Timestamp startTm = Timestamp.valueOf(start);
    	Timestamp endTm = Timestamp.valueOf(end);
    	    	
    	
		try {
			con.connect();

		
		  String sql = "INSERT INTO events (containerID, title, location, description, startTime, endTime, userID, blogID) VALUES(?,?,?,?,?,?,?,?)";
		
		  PreparedStatement pstmt = con.getCon().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
	      pstmt.setString(1, container);
	      pstmt.setString(2, title);
	      pstmt.setString(3, location);
	      pstmt.setString(4, description);
	      pstmt.setLong(5, startTm.getTime());
	      pstmt.setLong(6, endTm.getTime());

	      pstmt.setString(7, userID);
	      pstmt.setString(8, blogID);
	      // execute query, and return number of rows created
	      int rowCount = pstmt.executeUpdate();
		
		ResultSet rs = pstmt.getGeneratedKeys();
		   if(rs.next()){
			   common.Utility.log.debug("The index returned is:"+ rs.getInt(1));
			   eventID = rs.getInt(1);
			   return eventID;
		   }
			  
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		return 0;
	}
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
      
		
        //Get the session
        HttpSession session = request.getSession();
    	User userSession = (User) session.getAttribute("use");
    	
    	if ((userSession == null) || (userSession.getID() == 0))
    		response.sendRedirect(response.encodeRedirectURL("/ourspaces/error.jsp"));
    	
    	String userFoaf = userSession.getFOAFID();
    	
    	int eventID = insertEvent(request);
    
    	
    	//common.Utility.log.debug("-------------------" +start);
    	//common.Utility.log.debug("-------------------" +end);

		String start = getStart(request);
		String end = getEnd(request);
    	Timestamp startTm = Timestamp.valueOf(start);
    	Timestamp endTm = Timestamp.valueOf(end);
    	    	
		
		//Add attendees
		//--------------------------

    	String container = ""; 
    	if (request.getParameter("container") != null) 
    		container = request.getParameter("container");  
    	String title = "";
    	if (request.getParameter("title") != null) title = request.getParameter("title");

		try {
			con.connect();
			String[] paramValues = request.getParameterValues("Name");
	        for (int i = 0; i < paramValues.length; i++) 
	        {
	        	if(paramValues.length != 0)
	        	{
	        	
		        	String name = paramValues[i];
		        	String[] splittedName = name.split("_"); 
		        	if(splittedName.length > 1)
		        	{
			        	String foafID = splittedName[1];
			        	
			        	if(!foafID.equals(userFoaf))
			        	{
			        	    	        		
							Statement st;
							try {
								st = con.getCon().createStatement();	
							    String sql = "INSERT INTO attendees (eventID, attendeeID, status) VALUES("+eventID+",'"+foafID+"', 'unknown')";
							    st.executeUpdate(sql);
				        	} catch (SQLException e) {
								e.printStackTrace();
							}		        	
			        	
				        	int tempID;
							try {
								tempID = userSession.getUserIDFromFOAF(foafID);
								//send a notification
					        	if (userSession.checkNotification("notify_new_meeting",tempID)) 
						    	{
						         String to = userSession.getEmail(tempID);
						    	 String esubject = "You have been invited by " + userSession.getName(userSession.getID()) + " to attend the event: " + title + ".\n\n";
						         String emessage = "For more information regarding this event, please log into ourSpaces at http://www.ourspaces.net. \n The direct link to the event is <a href=\"http://ourspaces.net/event.jsp?id="+eventID+"\">http://localhost:8080/ourspaces/event.jsp?id="+eventID+"</a>.\n\n";
						    	 Email.send(to, esubject, emessage);
						    	 MessagesHandlerServlet.newMessage(-1, userSession.getID(), "New event invitation", esubject + emessage, new String[]{userSession.getFOAFID()}, null);
						    	}
				        	} catch (Exception e) {
								e.printStackTrace();
							} 
			        	}
		        	}
	        	}
	        	
	        }
	        
	        //Add user as attendee
	        Statement st;
			try {
				st = con.getCon().createStatement();	
			    String sql = "INSERT INTO attendees (eventID, attendeeID, status) VALUES("+eventID+",'"+userSession.getFOAFID()+"', 'unknown')";
			    st.executeUpdate(sql);
	    	} catch (SQLException e) {
				e.printStackTrace();
			}
	        
	        Activities act = new Activities();
	        String ev = String.valueOf(eventID);
	        Project pro = new Project();
			try {
				act.addActivity(userSession.getID(), 30, "event.jsp?id="+eventID, pro.getProjectFromContainer(container));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally{
		    try {
				con.getCon().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		response.sendRedirect("/ourspaces/event.jsp?id=" + eventID);
       
	}
	
	

		 	  	  	    
}