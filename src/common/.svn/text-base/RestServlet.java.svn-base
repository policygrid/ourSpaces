package common;
import java.io.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;
import org.policygrid.ontologies.*;

/**
 * Servlet implementation class ResourceUploadServlet
 */
public class RestServlet extends HttpServlet {
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//HttpSession session = request.getSession();
    	//User userSession = (User) session.getAttribute("use");
		
	
    	
    	String path = request.getPathInfo();
    	
       common.Utility.log.debug("------------- PATH :"+path);
       common.Utility.log.debug(request.getPathTranslated());
    	
    	
    	if (path.startsWith("/person/")) {  	
    		common.Utility.log.debug("REST SERVICE ---- Requets person with ID: "+path.substring(8));
    		//response.sendRedirect("/ourspaces/restsupport/person.jsp?personID="+path.substring(8));
        	getServletConfig().getServletContext().getRequestDispatcher("/restsupport/person.jsp?personID="+path.substring(8)).forward(request,response);
    	}
    	
    	if (path.startsWith("/artifact/")) {  	
    		common.Utility.log.debug("REST SERVICE ---- Requets artifact with ID: "+path.substring(10));
    		//response.sendRedirect("/ourspaces/restsupport/person.jsp?personID="+path.substring(8));
        	getServletConfig().getServletContext().getRequestDispatcher("/restsupport/artifact.jsp?artifactID="+path.substring(10)).forward(request,response);
    	}
    	if (path.startsWith("/project/")) {  	
    		common.Utility.log.debug("REST SERVICE ---- Requets artifact with ID: "+path.substring(10));
    		//response.sendRedirect("/ourspaces/restsupport/person.jsp?personID="+path.substring(8));
        	getServletConfig().getServletContext().getRequestDispatcher("/restsupport/project.jsp?projectID="+path.substring(10)).forward(request,response);
    	}
    	
    	
    		      
	}


}
