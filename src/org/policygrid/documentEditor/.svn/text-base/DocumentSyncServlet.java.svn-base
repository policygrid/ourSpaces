package org.policygrid.documentEditor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DocumentSyncServlet extends HttpServlet {

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ServletContext context = request.getSession().getServletContext();

		if (context.getAttribute("document1") == null) {
			CollaborativeDocument doc1 = new CollaborativeDocument();
			doc1.appendLine("This is a test");
			doc1.appendLine("Does this crap work?");
			doc1.appendLine("We will know this soon");
			
			context.setAttribute("document1", doc1);
		} 
		
		CollaborativeDocument doc = (CollaborativeDocument)context.getAttribute("document1");
		
	
		PrintWriter out = response.getWriter();
		
		
	   //Detect the action
		String cmd;

        cmd = request.getParameter("action");
        
        //Update the document
        if (cmd.equals("show")) {
         for (int i = 0; i < doc.size(); i++)
        	out.println(doc.getLine(i));
        }

	}

}
