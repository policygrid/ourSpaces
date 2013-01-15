package common;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class DeleteStatus extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	
	static final long serialVersionUID = 1L;
	   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
    Connections con = new Connections();
    
    public DeleteStatus() 
	{
		super();
	} 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
    	User user = (User) session.getAttribute("use");
        
        try {
			user.deleteStatus(user.getID());
		} catch (Exception e) {
			e.printStackTrace();
		} 
        
        response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		
		out.println("<message>");
		out.println("done");
	    out.println("</message>");


	
		out.flush();
		
	}

	
}
