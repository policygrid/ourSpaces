package common;
import java.io.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.openrdf.repository.*;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.OpenRDFException;
import org.openrdf.model.Literal;
import org.openrdf.rio.rdfxml.RDFXMLWriter;
import org.openrdf.rio.*;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.query.*;
import org.openrdf.query.resultio.sparqlxml.SPARQLResultsXMLWriter;
import org.xml.sax.SAXException;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;
import org.policygrid.ontologies.*;

/**
 * Servlet implementation class ResourceUploadServlet
 */
public class AuthUserServlet extends HttpServlet {
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		try {
      
        String authKey = request.getPathInfo().substring(1);
     
        
        //Read the database
        Connections con = new Connections();
        con.connect();
        
        int userID = -1;
        
        Statement st = con.getCon().createStatement();
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select userid from user where authKey=\'");
		qry.append(authKey);
		qry.append("\'");
		ResultSet rs = st.executeQuery(qry.toString());
		
		while(rs.next())
		{  
		   //Locate the user 
		   userID = rs.getInt("userid");

		}
			
		rs.close();
		
		
		if (userID != -1) {
			
			
			String sql = "update user set confirmed=true, authKey='' where userid="+userID;
			st.executeUpdate(sql);
			
			 User user = new User();
		        user.setID(userID); // sets a variable in the JavaBean
		        user.setRDFID(user.getUserRDFID(userID));
		        user.setFOAFID(user.getFOAFID(userID));
		        
		        // Creates the session with the user bean as a parameter
		        HttpSession session = request.getSession(true);
		        session.setAttribute("use", user);
				session.setAttribute("container", "");
				session.setAttribute("containerType", "");
				session.setAttribute("projectID", "");
		        
		        // Forwards the user to their home page
		        response.sendRedirect("/ourspaces/myhome.jsp");			
			
		}
		
		
		st.close();
		con.disconnect();
		
		
        //create session, redirect user
	

		} catch (Exception e) {e.printStackTrace();}
	}


}
