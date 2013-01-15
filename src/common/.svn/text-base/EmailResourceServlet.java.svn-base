package common;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.openrdf.OpenRDFException;
import org.xml.sax.SAXException;

public class EmailResourceServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	HttpSession session = request.getSession();
	User userSession = (User) session.getAttribute("use");
	
	if ((userSession == null) || (userSession.getID() == 0))
		response.sendRedirect(response.encodeRedirectURL("/ourspaces/error.jsp"));
	
	RDFi rdfi = new RDFi();
	String resource = request.getParameter("resource");
	String destEmail = request.getParameter("email");
	String fileID = request.getParameter("fileID");

	if ((destEmail != null) && (resource != null)) {

	User user = (User) session.getAttribute("use");
	int id = user.getID();


	// Read user name from session
	String sender = null;

		sender = user.getName(id);
	

	String title = null;
	resource = URLDecoder.decode(resource);
	try {
		title = rdfi.getResourceTitle(resource);
	} catch (OpenRDFException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	

	String publicFileID = UUID.randomUUID().toString();
	
	String fileIDHash = fileID.substring(fileID.lastIndexOf("%2F")+3);
	
	
	//common.Utility.log.debug("The FILE ID is: "+fileID);
	//common.Utility.log.debug("The FILE ID hash  is: "+fileIDHash);

	try {
	 Connections con = new Connections();
     con.connect();  
     Statement st = con.getCon().createStatement();    
     
 	 String sqlLook = "select fileID, fileName, mime, publicFileID from files where fileID=\'"+fileIDHash+"\'";
 	 ResultSet rsl= st.executeQuery(sqlLook);
	 
 	 String timestamp = ""+System.currentTimeMillis();
	 rsl.next(); 
	 String sql = "INSERT INTO files (fileID, fileName, userid, timestamp, mime, publicFileID ) VALUES('"+rsl.getString("fileID")+"','"+rsl.getString("fileName")+"', "+ userSession.getID()+",'"+timestamp+"','"+rsl.getString("mime")+"','"+publicFileID+"')";
	 common.Utility.log.debug(sql);
	 st.executeUpdate(sql);
	 
	} catch (Exception e){
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	String link = "http://www.ourspaces.net/file/"+publicFileID;

	String message = "";
	message += "Hello, \n";
	message += sender + " would like to share the following digital resource with you: " + title;
	message += "You can access this resource via the ourSpaces Virtual Research Environment by following this link: (Warning: the resource can only be downloaded once) \n ";
	message += link + "\n";
	message += "\n \n";
	message += "OurSpaces is developed by PolicyGrid a University of Aberdeen research project. For more information please visit www.policygrid.org  \n";
	Email.send(destEmail,"OurSpaces VRE sharing a Digital Resource", message);
	
	
	   }
	}
}
