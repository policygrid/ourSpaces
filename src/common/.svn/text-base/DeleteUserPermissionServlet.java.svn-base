package common;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.openrdf.OpenRDFException;
import org.xml.sax.SAXException;

public class DeleteUserPermissionServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	
	static final long serialVersionUID = 1L;
	   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
    Connections con = new Connections();
    
    public DeleteUserPermissionServlet() 
	{
		super();
	} 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		PrintWriter out = response.getWriter();
		
		
        // the action element that we'll check for
        String user = request.getParameter("user");
        
        String userid = "http://xmlns.com/foaf/0.1/#" + user;
        
        String resource = request.getParameter("resource");  
        
        String namespace = request.getParameter("namespace");
        
        String resourceID = namespace + "#" + resource;
        
        String cmd = request.getParameter("action");
        if (cmd != null) 
        {
        	 if (cmd.equals("admin")) 
             {
        		 try {
					deletePromotion(userid, resourceID);
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OpenRDFException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
             }
        	 if (cmd.equals("access")) 
             {
        		 
        		 try {
					deleteUser(userid, resourceID);
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OpenRDFException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
             }
        }

        
        response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		
		out.println("<message>");
		out.println("true");
	    out.println("</message>");


	
		out.flush();
		
	}
	
	public void deletePromotion(String userid, String resource) throws ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		User user = new User();
		
		Connections con = new Connections();
		con.connect();
		Statement st = con.getCon().createStatement();
		String qry = "delete from permissions where userID=\""+userid+"\" and resourceID=\""+resource+"\"";

		st.executeUpdate(qry);

		st.close();
		con.disconnect();
		
		AccessControl.setUserPermission("view", userid, resource, true);
		AccessControl.setUserPermission("download", userid, resource, true);
	}
	
	public void deleteUser(String userid, String resource) throws ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		User user = new User();
		
		Connections con = new Connections();
		con.connect();
		Statement st = con.getCon().createStatement();
		
		String qry = "delete from permissions where userID=\""+userid+"\" and resourceID=\""+resource+"\"";
		
		common.Utility.log.debug(qry);
		
		st.executeUpdate(qry);

		st.close();
		con.disconnect();

	}
	
}
