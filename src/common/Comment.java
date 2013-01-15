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

public class Comment extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	
	static final long serialVersionUID = 1L;
	   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
    Connections con = new Connections();
    
    public Comment() 
	{
		super();
	} 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		PrintWriter out = response.getWriter();
        // the action element that we'll check for
        int userid = Integer.parseInt(request.getParameter("user"));
        
        String about = request.getParameter("about"); 
        String namespace = request.getParameter("namespace"); 
        if(namespace == null || namespace.equals(""))
        	namespace = "http://openprovenance.org/ontology#";
        //Check if the about is full URI
        if(!about.startsWith("http://"))
        	about = namespace + about;
        
        String content = request.getParameter("content");
        
        String date = "";
        
        try {
			date = addComment(userid, about, content);
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
        
        response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		
		out.println("<message>");
		out.println(date);
	    out.println("</message>");


	
		out.flush();
		
	}
	
	public String addComment(int userid, String about, String content) throws ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		User user = new User();
		
		Blog blog = new Blog();
		
		String date = blog.addComment(content, about, userid, user.getUserRDFID(userid));
		
		return date;
	}
	
}
