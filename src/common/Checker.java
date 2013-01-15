package common;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
 public class Checker extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
 {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
    Connections con = new Connections();
   
	public Checker() 
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

        cmd = request.getParameter("username");
        if (cmd != null) 
        {
                checkUser(request, response);
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
	 */
	private void checkUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		int i = 0;
		
		try 
		{
			i = (int) checkDetails(username);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		
		// If the name is taken
		if(i != 0)
		{
			out.println("<valid>false</valid>");
		}
		// If the name is free
		else 
		{
			out.println("<valid>true</valid>");
		}
	}
	
	/**
	 * Selects the username from the database.  If the user exists, an integer
	 * greater than 0 is returned.  0 indicates the username does not exist.
	 * 
	 * @param username
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int checkDetails(String username) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
	{
		con.connect();
		Statement st = con.getCon().createStatement();
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select userid from user where email=\'");
		qry.append(username);
		qry.append("\'");
		
		int i = 0;
		ResultSet rs = st.executeQuery(qry.toString());
		
		while(rs.next())
		{
			i = rs.getInt("userid");
		}
		rs.close();
		st.close();
		con.disconnect();
		return i;
	
	}
		 	  	  	    
}