package common;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.openrdf.OpenRDFException;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.rio.RDFFormat;
import org.xml.sax.SAXException;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.*;
import com.hp.hpl.jena.ontology.*;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;


import common.Project;
/**
 * Servlet implementation class for Servlet: Controller
 * 
 * Form requests are sent via this servlet and the results are sent to various JavaBeans.
 * 
 * @author Richard Reid
 * @version 1.0
 *
 */
 public class Controller extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet 
 {
	
	public static final String USER = "wysiwym_user";
	public static final String RESOURCE = "wysiwym_resource";
	 
	 /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Controller() 
	{
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	/**
	 * All form requests are sent here and processed onwards to the relevant Model classes,
	 * following the Model-View-Controller architecture.  
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        PrintWriter out = response.getWriter();
        String cmd;
        // Retrieves the "action" parameter and forwards it to the relevant command.
        cmd = request.getParameter("action");
        
        common.Utility.log.debug("Controller ACTION: "+cmd);
        if (cmd != null) 
        {
            if (cmd.equals("home")) 
            {
                goHome(request, response);
            }
            
            // Allowing the user to log in
            else if (cmd.equals("login")) 
            {
				try {
					logIn(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}	
            }
            
            // Upload a photo
            else if (cmd.equals("upload"))
            {
        		try
        		{
					getFile(request, response);
					//response.sendRedirect("/ourspaces/myhome.jsp");
				} 
        		catch (Exception e)
        		{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            
            // For message passing between contacts
            else if (cmd.equals("sendMessage"))
            {
            	try 
            	{
            		sendMessage(request,response);
            	} 
            	catch (Exception e)
            	{
            		e.printStackTrace();
            	}
            }
            
         // For setting password to a new value
            else if (cmd.equals("updatePassword"))
            {
            	try 
            	{
            		updatePassword(request,response);
            	} 
            	catch (Exception e)
            	{
            		e.printStackTrace();
            	}
            }
            
            // For adding comments to activities
            else if (cmd.equals("addComment"))
            {
            	try 
            	{
            		addComment(request,response);
            	} 
            	catch (Exception e)
            	{
            		e.printStackTrace();
            	}
            }
            
            // For adding contacts to profiles
            else if (cmd.equals("addContact"))
            {
            	try 
            	{
            		addContact(request,response);
            	} 
            	catch (Exception e)
            	{
            		e.printStackTrace();
            	}
            }
            
         // For deleting contacts to profiles
            else if (cmd.equals("deleteContact"))
            {
            	try 
            	{
            		deleteContact(request,response);
            	} 
            	catch (Exception e)
            	{
            		e.printStackTrace();
            	}
            }
            
            // Altering the values of profiles
            else if (cmd.equals("editprofile"))
            {
            	try 
            	{
					update(request, response);
				} 
            	catch (Exception e) 
            	{
					e.printStackTrace();
				} 
            }
            
            // Processing the profile registration
            else if (cmd.equals("register")) 
            {
				try 
				{
					register(request, response);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}	
            }
          //editing personal lexicon service settings
            else if (cmd.equals("editplssettings"))
            {
            	try 
            	{
					update(request, response);
				} 
            	catch (Exception e) 
            	{
					e.printStackTrace();
				} 
            }
            // Creation of a new project
            else if (cmd.equals("createProject"))
            {
            	try 
            	{
            		createProject(request, response);
            	} 
            	catch (Exception e)
            	{
            		e.printStackTrace();
            	}
            }
            
            else if (cmd.equals("addProjectMember"))
            {
            	try 
            	{
            		addProjectMember(request, response);
            	} 
            	catch (Exception e)
            	{
            		e.printStackTrace();
            	}
            }
            
         // deletion of  new project
            else if (cmd.equals("deleteProject"))
            {
            	try 
            	{
            		deleteProject(request, response);
            	} 
            	catch (Exception e)
            	{
            		e.printStackTrace();
            	}
            }
            
         // deletion of  new project
            else if (cmd.equals("projectSettings"))
            {
            	try 
            	{
            		projectSettings(request, response);
            	} 
            	catch (Exception e)
            	{
            		e.printStackTrace();
            	}
            }

            
            // Adding an entry to a blog
            else if (cmd.equals("blog"))
            {
            	try 
            	{
            		addBlog(request, response);
            	}
            	catch (Exception e)
            	{
            		e.printStackTrace();
            	}
            }
            
            // Adding an entry to a blog
            else if (cmd.equals("newProjectBlog"))
            {
            	try 
            	{
            		addProjectBlogContainer(request, response);
            	}
            	catch (Exception e)
            	{
            		e.printStackTrace();
            	}
            }
            
            // Updating user status
            else if(cmd.equals("status"))
            {
            	try
            	{
            		updateStatus(request, response);
            	}
            	catch (Exception e)
            	{
            		e.printStackTrace();
            	}
            }
            // Attend an event
            else if(cmd.equals("attendEvent"))
            {
                try
                {
                	attendEvent(request, response);
                }
                catch (Exception e)
                {
                	e.printStackTrace();
                }
            }
            // Delete blog post
            else if(cmd.equals("deleteBlogPost"))
            {
                try
                {
                	deleteBlogPost(request, response);
                }
                catch (Exception e)
                {
                	e.printStackTrace();
                }
            }else if(cmd.equals("confirmTC"))
            {
                try
                {
                	confirmTC(request, response);
                }
                catch (Exception e)
                {
                	e.printStackTrace();
                }
            }else if(cmd.equals("addBug"))
            {
                try
                {
                	addBug(request, response);
                }
                catch (Exception e)
                {
                	e.printStackTrace();
                }
            }
            else 
            {
                // no command set
                out.println("Error: No page set");
            }
            out.flush();
        }
        else 
        {
        	out.println("Invalid request.");
        }
	}

	/** 
	 * Retrieves the request from the servlet and processes the information.
	 * A new instance of the Login class is made which handles the login and 
	 * validation of all users.
	 * 
	 * If the checkDetails(username, password) returns 0, the user does not exist
	 * and thus, is forwarded to an error page.  
	 * 
	 * A session is created for a valid user, allowing them access to various
	 * pages such as their personal home page.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws OpenRDFException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws NoSuchAlgorithmException 
	 * 
	 */
	private void logIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, OpenRDFException, NoSuchAlgorithmException 
	{
		// Retrieving parameters
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String topage = request.getParameter("topage");
		Login login = new Login();
		int i = 0; // Integer for retrieving user ID

		// A value greater than 0 will be returned if the user exists
		i = (int) login.checkDetails(username, password);

		if(i != 0)
		{
			
			
			// Create a User JavaBean to identify each user
			User user = new User();
			user.setID(i);
			user.setRDFID(login.getRDFID(i));
			user.setFOAFID(user.getFOAFID(i));
			
			
			
			// Creates a session, using the User JavaBean
			HttpSession session = request.getSession(true);
			
			//Logging
			Logs.addLog(i, "login", "login", "");
			
			boolean tc = login.checkTC(username);
			if (!tc) {
				session.setAttribute("use", user);
				session.setAttribute("container", "");
				session.setAttribute("containerType", "");
				session.setAttribute("projectID", "");		
				String stringURL = "/ourspaces/TermsAndConditions.jsp";
				response.sendRedirect(stringURL);
				return;
			}
			
			if(session.getAttribute("use") == user) {
				String stringURL = "/myhome.jsp";
				RequestDispatcher rd = request.getRequestDispatcher(stringURL);
				rd.forward(request, response);
			}
			else {
				session.setAttribute("use", user);
				session.setAttribute("container", "");
				session.setAttribute("containerType", "");
				session.setAttribute("projectID", "");
					
			// Forwards the user to their personal home page.
			String stringURL = "/ourspaces/myhome.jsp";
			
			
			if (topage != null) {
				String tp = java.net.URLDecoder.decode(topage);
				if (!tp.equals("null")) stringURL = java.net.URLDecoder.decode(topage);
			}
			
			
			response.sendRedirect(stringURL);
			}
		}
		else 
		{
			String stringURL ="/ourspaces/error.jsp";
			response.sendRedirect(stringURL);
		}
		
		
		
	}
	
	/**
	 * Allows the user to register with ourSpaces.  Firstly, retrieving the paramaters
	 * from the servlet, then creates a session for that user.  
	 * 
	 * Finally, the user's data is passed to the RDF handler so their information can
	 * be entered into the repository.
	 * 
	 * The user is then forwarded to their relevant home page.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 * @throws SAXException
	 * @throws ParserConfigurationException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * 
	 */
	private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, OpenRDFException, SAXException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParserConfigurationException 
	{
		// Receives parameters
		String password = request.getParameter("password");
		String fname = request.getParameter("firstname");
		String lname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String name = fname + " " + lname;
		
		email = email.replaceAll("'", "\\\\'");
		fname = fname.replaceAll("'", "\\\\'");
		lname = lname.replaceAll("'", "\\\\'");
		password = password.replaceAll("'", "\\\\'");
		
		Login login = new Login();
		
		int check = login.checkEmail(email);
		common.Utility.log.debug("check: " + check);
		
		if(check == 0)
		{	
			PersonCreator pc = new PersonCreator();
			String rdfID = pc.createPerson(fname, lname, email);
			
			
			int id = 0; // Integer to store forthcoming userIDs
			try 
			{
				// Creates a new user in the database and returns the newly created ID.
				id = pc.addUser(email, password, rdfID);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			// Creates a new instance of the RDF Handler class.
			
			Activities act = new Activities();
			act.addActivity(id, 11, "", "");
			String stringURL = "/noauth.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(stringURL);
			rd.forward(request, response);
		}
		else
		{
			response.sendRedirect("myhome.jsp");
		}
	}
	
	/**
	 * Allows a user to update their status.
	 */
	private void updateStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InstantiationException, IllegalAccessException,
		ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, OpenRDFException
	{
		String userid = request.getParameter("username");
		String status = request.getParameter("status");
		
		status = status.replaceAll("'", "\\\\'");
		
		User user = new User();
		int userID = user.getUserID(userid); // Gets the userID of the recipient
		
		if(user.getStatus(userID).equals(status) || status.equals(""))
		{
			response.sendRedirect("myhome.jsp");
		}
		else
		{
			user.updateStatus(userID, status);
			Activities act = new Activities();
			act.addActivity(userID, 25, status, "");
			response.sendRedirect("myhome.jsp");
		}
		
	}
	
	/**
	 * Allows a user to send a message to another registered user.  
	 * 
	 * The user is forwarded to a confirmation page when the message
	 * has been successfully sent.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws OpenRDFException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * 
	 */
	private void sendMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InstantiationException, IllegalAccessException,
		ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, OpenRDFException 
	{
		// Receives parameters from servlet
		String rec = request.getParameter("username");
		int sender = Integer.parseInt(request.getParameter("userid"));
		int tid = Integer.parseInt(request.getParameter("tid"));
		String subject = request.getParameter("subject");
		String message = request.getParameter("message");
		String sourcePage = request.getParameter("sourcePage");
		String sent = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
		
		message = message.replaceAll("'", "\\\\'");
		subject = subject.replaceAll("'", "\\\\'");
		
		// Creates a new instance of the User JavaBean
		User user = new User();
		int recipient = user.getUserID(rec); // Gets the userID of the recipient
		//user.sendMessage2(recipient, sender,tid, subject, message,sent);  // Sends the message
		
		String res = user.getUserRDFID(recipient);
		Activities act = new Activities();
		act.addActivity(sender, 6, res, "");
		
		// Forwards the user to the confirmation page
		if (sourcePage != null) {
		response.sendRedirect("/ourspaces/"+sourcePage);
		} else {
			response.sendRedirect("/ourspaces/home.jsp");
		}
	}

	
	/**
	 * Adds a comment to a particular activity
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws OpenRDFException
	 */
	private void addComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InstantiationException, IllegalAccessException, 
	ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, OpenRDFException 
	{
		// Receives parameters
		int actID = Integer.parseInt(request.getParameter("actID"));
		int userID = Integer.parseInt(request.getParameter("userID"));
		String comment = request.getParameter("comment");
		
		comment = comment.replaceAll("'", "\\\\'");
		
		Comment comments = new Comment();
		//comments.addComment(userID, actID, comment);

		
		// Returns the user to the blog home page where they can view the post
		response.sendRedirect("myhome.jsp");
	}
	
	/**
	 * Generic method for adding a blog post. The blog container is passed as a parameter,
	 * as well as the output URL to redirect the user to the correct page.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws OpenRDFException
	 */
	private void addBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InstantiationException, IllegalAccessException, 
	ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, OpenRDFException 
	{
		int id = 0;
		String content = "";
		String title = "";
		String blogContainerID = "";
		ArrayList about = new ArrayList();
		String outputURL = "";
		ArrayList provenanceLinks = new ArrayList();
		ArrayList discourceLinks = new ArrayList();
		
		Enumeration paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()) 
		{
        	String param = (String)paramNames.nextElement();
        	if(param.equals("id"))
        	{
        		String id2[] = request.getParameterValues(param);
        		id = Integer.parseInt(id2[0]);
        	}
        	if(param.equals("content"))
        	{
        		String temp[] = request.getParameterValues(param);
        		content = temp[0];    	
        	}
        	if(param.equals("title"))
        	{
        		String temp[] = request.getParameterValues(param);
        		title = temp[0];    		
        	}
        	if(param.equals("blogContainer"))
        	{
        		String temp[] = request.getParameterValues(param);
        		blogContainerID = temp[0];    		
        	}
        	if(param.equals("output"))
        	{
        		String temp[] = request.getParameterValues(param);
        		outputURL = temp[0];    		
        	}
        	if(param.equals("Name"))
        	{
        		String temp[] = request.getParameterValues(param);
        		for(int i = 0; i < temp.length; i++)
        		{
        			String foafID = temp[i];
        			String[] fields = foafID.split("_");
        			if(fields.length != 1)
        			{
        				about.add(fields[1]);
        			}
        		}
        	}
        	
        	
        	if(param.equals("provenanceLink")) {
        		String temp[] = request.getParameterValues(param);
        		for(int i = 0; i < temp.length; i++)
        		{
        		 common.Utility.log.debug("============ Provenance Link:" + temp[i]);
        		 provenanceLinks.add(temp[i]);
        		}
        	}
        	
        	
        	//discourse
        	if(param.equals("discourse")) {
        		String temp[] = request.getParameterValues(param);
        		for(int i = 0; i < temp.length; i++)
        		{
        		 common.Utility.log.debug("============ Discource Link:" + temp[i]);
        		 discourceLinks.add(temp[i]);
        		}
        	}
        	
		}
		
		
		User user = new User();
		
		String rdfUserID = user.getUserRDFID(id);
		
		//process content
		
			content = content.replaceAll("\\n","<br>");
			//content = content.replaceAll("#","&#35;");
			//content = content.replaceAll("@","&#64;");
			
			for (int i = 0; i < provenanceLinks.size(); i++) {
			String link = (String)provenanceLinks.get(i);
				String[]  links = link.split(",");
				
				
				//http://mrt.esc.abdn.ac.uk:8080/ourspaces/profile.jsp?id=140
				
				
				//TODO - store the provenance in the RDF database as "about" links
				if (links[0].startsWith("@")) {
					int uuid = user.getUserIDFromFOAF(links[1]);
					about.add(links[1]);
					content = content.replaceAll(links[0], "<a class=\"plinks\" href=\"http://mrt.esc.abdn.ac.uk:8080/ourspaces/profile.jsp?id="+uuid+"\">"+links[0]+"</a>&nbsp;<span class=\"nlg\" rel=\""+java.net.URLEncoder.encode(links[1])+"\"></span>");
				}
				
				//http://mrt.esc.abdn.ac.uk:8080/ourspaces/artifact_new.jsp?id=b8b9c7fd-a210-4b1d-8678-030eee9c4698 http://mrt.esc.abdn.ac.uk:8080/ourspaces/browse.jsp?resource=c906f195-53fb-4dbe-b092-1a86805154e5
				
				//TODO - store the provenance in the RDF database as "about" links
				if (links[0].startsWith("#")) {
					FileUtils f = new FileUtils();
					String fid = f.getFileIDFromRDF(links[1]);
					about.add(links[1]);
					content = content.replaceAll(links[0], "<a class=\"plinks\" href=\"http://mrt.esc.abdn.ac.uk:8080/ourspaces/artifact_new.jsp?id="+fid+"\">"+links[0]+"</a>&nbsp;<span class=\"nlg\" rel=\""+java.net.URLEncoder.encode(links[1])+"\"></span>");
				}
				
				
				
				
				
			}
			
	
   	   //get discourse links 
			Discourse disc = new Discourse();
			for (int i = 0; i < discourceLinks.size(); i++) {
				String link = (String)discourceLinks.get(i);
				String[]  links = link.split(",");
				disc.addDiscourseItem(links[0], links[1], links[2]);
			}
			
			//addDiscourseItem
		
			
			
				
			
		Blog blog = new Blog();
		blog.addBlogPost(blogContainerID, title, content, about, id, rdfUserID);
		
		common.Utility.log.debug("adding a new blog post: "+content);
		
		if(outputURL.startsWith("blog.jsp"))
		{
			Activities act = new Activities();
			act.addActivity(id, 14, "", "");
		}
		
		if(outputURL.startsWith("event.jsp"))
		{
			String[] event = outputURL.split("=");
			Activities act = new Activities();
			act.addActivity(id, 28, outputURL, event[1]);
		}
		
		if(outputURL.startsWith("project.jsp"))
		{
			String[] event = outputURL.split("=");
			String projectID = "http://www.policygrid.org/project.owl#" + event[1];
			Activities act = new Activities();
			act.addActivity(id, 28, outputURL, projectID);
		}
		
		// Forwards the user to their blog page to see the results.
		response.sendRedirect(outputURL);
	}
	
	private void addProjectBlogContainer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InstantiationException, IllegalAccessException, 
	ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, OpenRDFException 
	{
		String title = request.getParameter("title");
		String container = request.getParameter("container");
		String outputURL = request.getParameter("output");
		String projectID = request.getParameter("projectID");
		int id = Integer.parseInt(request.getParameter("id"));
		
		Container cont = new Container();
		cont.createProjectBlogContainer(container, title);
		
		Activities act = new Activities();
		act.addActivity(id, 29, title, projectID);
		
		response.sendRedirect(outputURL);
	}
	
	private void projectSettings(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InstantiationException, IllegalAccessException, 
	ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, OpenRDFException 
	{
		String outputURL = request.getParameter("output");
		Project project = new Project();
		project.editProject(request);
		
		// Forwards the user to their blog page to see the results.
		response.sendRedirect(outputURL);
	}
	
	/**
	 * Allows the user to add a contact to their list.
	 * 
	 * The contact's username is retrieved, from there, their userID
	 * is evaluated and forwarded to the relevant method in the User
	 * JavaBean.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws OpenRDFException 
	 * @throws NumberFormatException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * 
	 */
	private void addContact(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InstantiationException, IllegalAccessException, 
		ClassNotFoundException, SQLException, NumberFormatException, OpenRDFException, ParserConfigurationException, SAXException 
	{
		// Receives parameters
		int userid = Integer.parseInt(request.getParameter("userid"));
		
		RDFi rdf = new RDFi();
		User user = new User();
		
		HttpSession session = request.getSession();
    	User userSession = (User) session.getAttribute("use");
		
		Enumeration paramNames = request.getParameterNames();

		while(paramNames.hasMoreElements()) 
		{
	        String param = (String)paramNames.nextElement();
	        if(param.equals("Name")) 
	        {

	        	String members[] = request.getParameterValues(param);
	        	for(int i = 0; i < members.length; i++)
	        	{

	        		String name = members[i];
	        		String[] fields = name.split("_");
	        		if(fields.length != 1)
	        		{
	        				String foafID = fields[1];
	        				int contactID = user.getUserIDFromFOAF(foafID);
	        				String userRdfID = user.getUserRDFID(contactID);
	        				if(contactID != 0 && contactID != userid) {
	        					user.addContact(userid, contactID);
	        					Activities act = new Activities();
	        					act.addActivity(userid, 9, userRdfID, "");
	        					//notify user
	        					 String to = user.getEmail(contactID);
	        			    	 String esubject = "OurSpaces Notification" ;
	        			         String emessage =  userSession.getName(userSession.getID()) + " has added you as a contact in ourSpaces. \n";
	        			    	 Email.send(to, esubject, emessage);
	        				}
	        		}
	        	}
	        }
		} 
		
		// Forwards the user back to their home page where contacts are visible
		response.sendRedirect("mycontacts.jsp");
	}
	
	public void updatePassword(HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchAlgorithmException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		String oldPass = request.getParameter("currentPass");
		String newPass = request.getParameter("confirmPass");
		
		HttpSession session = request.getSession();
    	User userSession = (User) session.getAttribute("use");
		
		if(userSession.updatePassword(oldPass, newPass, userSession.getID()))
			response.sendRedirect("edit.jsp");
		else
			response.sendRedirect("myhome.jsp");
	}
	
	private void deleteBlogPost(HttpServletRequest request, HttpServletResponse response) throws IOException, RepositoryException, MalformedQueryException, QueryEvaluationException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
	{
		// Receives parameters
		
		String rdfBlogID = request.getParameter("rdfBlogID");
		String outputURL = request.getParameter("outputURL");
	
		Blog blog = new Blog();
		blog.deleteBlogPost(rdfBlogID);
		
		response.sendRedirect(outputURL);
	}
	
	private void deleteContact(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InstantiationException, IllegalAccessException, 
	ClassNotFoundException, SQLException, NumberFormatException, OpenRDFException, ParserConfigurationException, SAXException 
	{
		// Receives parameters
		int userid = Integer.parseInt(request.getParameter("userid"));
		String contactid = request.getParameter("contactid");
		
		common.Utility.log.debug("contact parameters");
		
		User user = new User();
		
		int conID = user.getUserID(contactid);
		
		common.Utility.log.debug("contact id: " + conID);
		
		user.deleteContact(userid, conID);
		
		
		// Forwards the user back to their home page where contacts are visible
		response.sendRedirect("myhome.jsp");
	}
	
	/**
	 * Allows a user to update the contents of their profile.
	 * 
	 * The necessary parameters are retrieved, and a new instance of the RDF Handler
	 * class (RDFi) is initialised.  From there, the updatePerson() and updateAddress()
	 * methods are called.
	 * 
	 * This also triggers the Activity class, ensuring updates appear in the user's
	 * activity log.
	 * 
	 * The user is finally forwarded back to their home page.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 * @throws SAXException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParserConfigurationException
	 * 
	 */
	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, OpenRDFException, SAXException, 
		InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParserConfigurationException 
	{
		HttpSession session = request.getSession();
    	User user = (User) session.getAttribute("use");
    	
		//Update database
		if (request.getParameter("notify_new_message") != null) user.changeNotification("notify_new_message", user.getID(), true);
		else user.changeNotification("notify_new_message", user.getID(), false);
		
		if (request.getParameter("notify_new_meeting") != null) user.changeNotification("notify_new_meeting", user.getID(), true);
		else user.changeNotification("notify_new_meeting", user.getID(), false);
		
		if (request.getParameter("notify_new_tag") != null) user.changeNotification("notify_new_tag", user.getID(), true);
		else user.changeNotification("notify_new_tag", user.getID(), false);
		
		if (request.getParameter("daily_digest") != null) user.changeNotification("daily_digest", user.getID(), true);
		else user.changeNotification("daily_digest", user.getID(), false);
		
		if (request.getParameter("opt_in_pls") != null) user.changeNotification("opt_in_pls", user.getID(), true);
		else user.changeNotification("opt_in_pls", user.getID(), false);
		
		
		
		//Update RDF
		PersonCreator pc = new PersonCreator();
		pc.editProfilef(request);
		
		// Forwards the user to their home page
		response.sendRedirect("myhome.jsp");
	}
	
	private void attendEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, OpenRDFException, SAXException, 
	InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParserConfigurationException 
	{
	
		int eventID = Integer.parseInt(request.getParameter("eventID"));
		String foafID = request.getParameter("foafID");
		String status = request.getParameter("status");
		
		Event event = new Event();
		event.updateAttendingStatus(eventID, foafID, status);
		
		// Forwards the user to their home page
		String stringURL = "event.jsp?id=" + eventID;
		response.sendRedirect(stringURL);
	}
	
	private void deleteProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, OpenRDFException, SAXException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParserConfigurationException
	{
		HttpSession session = request.getSession();
    	User user = (User) session.getAttribute("use");
		
		String projectID = request.getParameter("projectID");
		boolean remove =  AccessControl.checkPermission("remove",user.getFOAFID(),projectID);
		
		if(remove == true)
		{
			Project project = new Project();
			project.deleteProject(projectID);
		}
		
		// Forwards the user to their home page
		response.sendRedirect("myhome.jsp");
	}
	
	/**
	 * Allows the creation of Project pages.
	 * 
	 * Necessary parameters are received then forwarded to a class in the 
	 * Project JavaBean where the creation takes place.
	 * 
	 * The user is finally forwarded back to their home page where they can 
	 * see a list of projects (including the one they just made).
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 * @throws SAXException
	 * @throws ParserConfigurationException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * 
	 */
	private void createProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, OpenRDFException, SAXException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParserConfigurationException 
	{
		Project project = new Project();
		String projectID = project.createProject(request);
		
		// Creates an instance of the Activity JavaBean
		HttpSession session = request.getSession();
    	User user2 = (User) session.getAttribute("use");
    	common.Utility.log.debug(user2.getID());
    	int id2 = user2.getID();
    	
		Activities act = new Activities();
		act.addActivity(id2, 17, projectID, "");
		
		// Forwards the user to the home page
		response.sendRedirect("myhome.jsp");
	}
	
	private void addProjectMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, OpenRDFException, SAXException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParserConfigurationException 
	{
		Project project = new Project();
		String[] params = project.addProjectMember(request);
		HttpSession session = request.getSession();
    	User user2 = (User) session.getAttribute("use");
    	common.Utility.log.debug(user2.getID());
    	int id2 = user2.getID();
    	
		//First is projectID
		String projectID = params[0];
		String[] temp = projectID.split("#");
		//The rest are foafIDs
		for (int i = 1; i < params.length; i++) {
			String foafID = params[i];
			// Creates an instance of the Activity JavaBean	    	
	    	AccessControl.setUserPermission("view", foafID, projectID, true);
			AccessControl.setUserPermission("download", foafID, projectID, true);	    	
			Activities act = new Activities();
			act.addActivity(id2, 19, projectID, foafID);			
		}
		
		// Forwards the user to the home page
		String stringURL = "project.jsp?id=" + temp[1];
		response.sendRedirect(stringURL);
	}
	
	/**
	 * Acts as a forwarding method for users who have just created a profile.
	 * 
	 * Will create the necessary session then forward users to their home page.
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws OpenRDFException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * 
	 */
	private void setHome(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException, ParserConfigurationException, SAXException, OpenRDFException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		// new instance of the User JavaBean
		User user = new User();
		user.setID(id); // sets a variable in the JavaBean
		user.setRDFID(user.getUserRDFID(id));
		user.setFOAFID(user.getFOAFID(id));
		
		// Creates the session with the user bean as a parameter
		HttpSession session = request.getSession(true);
		session.setAttribute("use", user);
		session.setAttribute("container", "");
		session.setAttribute("containerType", "");
		session.setAttribute("projectID", "");
		
		// Forwards the user to their home page
		response.sendRedirect("myhome.jsp");
	}
	
	/**
	 * Acts as a forwarding method for users who have just created a profile via
	 * the alternative registration method.
	 * 
	 * Will create the necessary session then forward users to their edit page.
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws OpenRDFException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * 
	 */
	private void setEdit(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException, ParserConfigurationException, SAXException, OpenRDFException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		// Creates a user JavaBean
		User user = new User();
		user.setID(id);
		user.setRDFID(user.getUserRDFID(id));
		user.setFOAFID(user.getFOAFID(id));
		
		// Creates the session with the user bean as a parameter
		HttpSession session = request.getSession(true);
		session.setAttribute("use", user);
		
		// Forwards the user to their edit page
		String stringURL = "/edit.jsp";
		response.sendRedirect(stringURL);
	}
	
	/**
	 * A means of forwarding the user to the site's index page.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * 
	 */
	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.sendRedirect("myhome.jsp");
		
	} 
	
	/*	Removes all characters that Fedora might not accept from the filename
	 */
	private String checkFileName(String name)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < name.length(); i++)
		{
			Character c = name.charAt(i);
			if (c.isLetterOrDigit(c) || c.equals('.'))
				sb.append(c);
		}
		return sb.toString();
	}
	
	
	
//	/*	Takes the posted file and puts it in Fedora.
//	
//
	private void getFile(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
//		common.Utility.log.debug("+++++++++++++++++++++++++++++ START+++++++++++++++++++++++++++");
		HttpSession session = request.getSession();
    	User user2 = (User) session.getAttribute("use");
    	String projectID = (String) session.getAttribute("projectID");
    	common.Utility.log.debug("PROJECT ID: " + projectID);
    	String foafID = user2.getFOAFID();
		
		common.Utility.log.debug("UPLOADING FILE!!!!");
		//response.setContentType("text/plain");    	
		
	
		//some sort of test
		//File stupidFile = new File("/home/policygrid/apache-tomcat-6.0.18/temp/stuff.txt");


     	FileItemFactory factory = new DiskFileItemFactory();
    	ServletFileUpload upload = new ServletFileUpload(factory);
    	
    	try 
    	{
    		List items = upload.parseRequest(request);
    		String user = null;
    		String filename = null;
    		File file = null;
    	
      		
      			Iterator iterator = items.iterator();
      		    
      			while (iterator.hasNext()) {
      				       		
        		FileItem item = (FileItem) iterator.next();

                if (!item.isFormField()) {
                	filename = item.getName();

                   

                    file = new File(Configuration.getParaemter("file","tempFolder")+filename);
                    file.createNewFile();
                    common.Utility.log.debug("@@@@@@@@@@@@@@ SAVING"+file.getAbsolutePath());
                    item.write(file);
                    common.Utility.log.debug("@@@@@@@@@@@@@@ SAVING"+item.getOutputStream().toString());
                                 
                   
                    
                }
        		
        		
        		
        		
	      	}
	      	
	      	if (file != null)
	      	{
	      		common.Utility.log.debug("Almost there");
	      		//response.getWriter().write(storeFileInFedora(user, file, filename, foafID));
	      		
	      		long length = file.length();
		    	common.Utility.log.debug("The picture length is :"+length);
		    	if (length > 50000) {
		    		//if the file is bigger that 100K 
		    		response.getWriter().write("Sorry, I could not upload your file. The file is bigger than 50 Kbytes.");
		    		return;
		    	}
	      		if(!projectID.equals(""))
	      		{
	      				String fileURI = Utility.storeFile(user2.getID(), filename, file, "","","/");
	      				
	      				Project project = new Project();
	      				project.updatePhoto(projectID, fileURI);
	      		}		
	      		else
	      		{
	      			String fileURI = Utility.storeFile(user2.getID(), filename, file, "","","/"); 
	      			common.Utility.log.debug("The uri if the file (PICTURE) we have just uploaded is: "+ fileURI);
		      		RDFi rdf = new RDFi();
		            rdf.updatePhoto(foafID, fileURI);
	      		}
	      		
	      		if (!file.delete())
           			common.Utility.log.debug("file not deleted");
	      		if(projectID.equals(""))
	      		{
	      			Activities act = new Activities();
		      		act.addActivity(user2.getID(), 13, "", "");
	      			response.sendRedirect("/ourspaces/myhome.jsp");
	      		}
	      		else 
	      		{
	      			String[] fields = projectID.split("#");
	      			String uri = "/ourspaces/project.jsp?id="+fields[1];
	      			response.sendRedirect(uri);
	      		}
   	      		return;
	      	}
    	} 
    	catch (Exception e) 
    	{
      		e.printStackTrace();
      		response.getWriter().write("Sorry, I could not upload your file.");
    		return;
    	}
    	
    	response.getWriter().write("Sorry, I could not upload your file.");
  	}

  	 
  	private void setParameters(ClientHttpRequest httpClient) throws Exception
  	{
  		httpClient.setParameter("title","digitalObject"); 
		httpClient.setParameter("creator","PolicyGrid");
		httpClient.setParameter("description","archive");
		httpClient.setParameter("publisher","PolicyGrid"); 
		httpClient.setParameter("date", "today"); 
		httpClient.setParameter("format","whoknows"); 
		httpClient.setParameter("rights","protected"); 
  	}
  	  	
  	@Deprecated public String storeFileInFedora(String userID, File file, String filename, String foafID) throws Exception
  	{
  		String digObjPID = new String();
		try 
		{
  			ClientHttpRequest httpClient = new ClientHttpRequest("http://roc.csd.abdn.ac.uk:8092/fedoraupload/fedoraCreateObjectServlet");
  			setParameters(httpClient);           
			InputStream serverInput = httpClient.post();
			for(int i = serverInput.read(); i != -1; i = serverInput.read()) 
			     digObjPID += (char) i;  		
			digObjPID = normalise(digObjPID);	         
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		
		try 
		{	//Note: avoid the use of spaces and strange characters in the file name. The file name becomes the name of the datastream in fedora. Sometimes fedora complains if the name of the datastream contains invalid characters.
			ClientHttpRequest httpClient = new ClientHttpRequest("http://roc.csd.abdn.ac.uk:8092/fedoraupload/fedoraUploadServlet");
			httpClient.setParameter("username", "user");
			httpClient.setParameter("pid", digObjPID);	 //the one returned from step 1
			httpClient.setParameter("file", file);
			
			common.Utility.log.debug("Looking at the finishing line");
			
			int idx = filename.indexOf(".");
			if (idx > 0)
			{
				String extension = new Extensions().get(filename.substring(idx + 1));
				if (extension == null)
					extension = "text/plain";
				httpClient.setParameter("mimeType", extension);
				//	httpClient.setMimeType(filename.substring(idx + 1));
			}			
			
			InputStream serverInput = httpClient.post();
			String result = new String();
			for (int i = serverInput.read(); i != -1; i = serverInput.read())
            	result += (char) i;	//The buffer return a success/error message from the servlet
            common.Utility.log.debug("FILENAME: " + result);	
            RDFi rdf = new RDFi();
            rdf.updatePhoto(foafID, result);
            
    		return result;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return("Upload failed");
		}		
  	}
  	
  	@Deprecated public String storeFileInFedoraRepository(String userID, File file, String filename, String userid) throws Exception
  	{
  		String digObjPID = new String();
		try 
		{
  			ClientHttpRequest httpClient = new ClientHttpRequest("http://roc.csd.abdn.ac.uk:8092/fedoraupload/fedoraCreateObjectServlet");
  			setParameters(httpClient);           
			InputStream serverInput = httpClient.post();
			for(int i = serverInput.read(); i != -1; i = serverInput.read()) 
			     digObjPID += (char) i;  		
			digObjPID = normalise(digObjPID);	         
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		
		try 
		{	//Note: avoid the use of spaces and strange characters in the file name. The file name becomes the name of the datastream in fedora. Sometimes fedora complains if the name of the datastream contains invalid characters.
			ClientHttpRequest httpClient = new ClientHttpRequest("http://roc.csd.abdn.ac.uk:8092/fedoraupload/fedoraUploadServlet");
			httpClient.setParameter("username", "user");
			httpClient.setParameter("pid", digObjPID);	 //the one returned from step 1
			httpClient.setParameter("file", file);
			
			common.Utility.log.debug("Looking at the finishing line");
			
			int idx = filename.indexOf(".");
			if (idx > 0)
			{
				String extension = new Extensions().get(filename.substring(idx + 1));
				if (extension == null)
					extension = "text/plain";
				httpClient.setParameter("mimeType", extension);
				//	httpClient.setMimeType(filename.substring(idx + 1));
			}			
			
			InputStream serverInput = httpClient.post();
			String result = new String();
			for (int i = serverInput.read(); i != -1; i = serverInput.read())
            	result += (char) i;	//The buffer return a success/error message from the servlet
            common.Utility.log.debug("FILENAME: " + result);	
           // RDFi rdf = new RDFi();
            //rdf.updatePhoto(userid, result);
            
    		return result;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return("Upload failed");
		}		
  	}
  	
  	private String normalise(String str)
  	{
  		str = str.replace("\\r", "");
  		str = str.replace("\\n", "");
  		str = str.replace("\\", "");
  		str = str.replace("\r", "");
  		str = str.replace("\n", "");
  		return str;
  	}
  	
  	/*	Takes the posted file and puts it in Fedora.
	 */
	private void confirmTC(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
    	User user2 = (User) session.getAttribute("use");
    	
    	user2.acceptTC(user2.getID());
    	
        String uri = "/ourspaces/myhome.jsp";
	    response.sendRedirect(uri);

  	}
	
	private void addBug(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
    	User user2 = (User) session.getAttribute("use");
    	
    	String type = request.getParameter("type");
    	String subject = request.getParameter("subject");
    	String message = request.getParameter("message");
    	
    	user2.addBug(user2.getID(),type,subject,message);
    	
        String uri = "/ourspaces/myhome.jsp";
	    response.sendRedirect(uri);

  	}
  	
  	
}
