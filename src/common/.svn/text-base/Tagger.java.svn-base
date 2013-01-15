package common;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.*;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.openrdf.OpenRDFException;
import org.xml.sax.SAXException;

/**
 * Servlet implementation class for Servlet: Checker
 * 
 * Servlet class that handles the AJAX request during registration.  
 * A check is done to see if a username is already taken by another
 * user, thus, not available.
 * 
 * @author Richard Reid
 * @version 1.0
 *
 */
 public class Tagger extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
 {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	
    Connections con = new Connections();
   
	public Tagger() 
	{
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
        // the action element that we'll check for
        String cmd;

        cmd = request.getParameter("tag");
        if (cmd != null) 
        {
                try {
					addTag(request, response);
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
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OpenRDFException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            out.flush();
        }
        else
        {
        	out.println("Server error.");
        }
	}
	
	/**
	 * Takes a request, retrieves the username parameter and calls the
	 * appropriate steps to check if a username is free.  If a name is taken,
	 * an XML page is rendered displaying false.  If the username is free,
	 * true is printed instead.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SQLException 
	 * @throws OpenRDFException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws NumberFormatException 
	 */
	private void addTag(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NumberFormatException, ParserConfigurationException, SAXException, OpenRDFException 
	{
		PrintWriter out = response.getWriter();
		String tag = request.getParameter("tag");
		String resource = request.getParameter("resource");
		resource = URLDecoder.decode(resource);
		String user = request.getParameter("user");
		int i = 0;
		
		int id = Integer.parseInt(user);
		
		HttpSession session = request.getSession();
    	User user2 = (User) session.getAttribute("use");
    	String userID = user2.getFOAFID(user2.getID());
		
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DATE);
		int month = c.get(Calendar.MONTH) + 1;
		int year = c.get(Calendar.YEAR);
		String date = day+" / "+month+" / "+year;
		
			
		if(!tag.equals(""))
		{
			con.connect();
			
			PreparedStatement pStmt;
			
			pStmt = con.getCon().prepareStatement("insert into tags (tag, resource, user, time) values(\""+tag+"\",\""+resource+"\",\""+userID+"\",\""+date+"\")");
		
		
			pStmt.executeUpdate();
			
			Activities act = new Activities();
			act.addActivity(id, 2, resource, "");

		}
		//response.sendRedirect("/ourspaces/artifact_new.jsp?id="+Utility.getLocalName(resource));
	}
		
}