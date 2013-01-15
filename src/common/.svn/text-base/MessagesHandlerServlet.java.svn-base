package common;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.openrdf.OpenRDFException;
import org.xml.sax.SAXException;

import java.util.*;
import java.text.*;

public class MessagesHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	protected static void readMessage(int messageId, int userId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		//added by kapila 
		Message msg = new Message();
		//int userID = Integer.parseInt(request.getParameter("recipient"));
		String readdatetime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
		msg.updateReadStatus(userId,messageId,readdatetime);
		common.Utility.log.debug("Read Status Updated");
	}
	
	protected static void newMessage(int threadId, int userID, String title, String content, String[] recipients, String[] provlist) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException{
		User user = new User(); 
		ArrayList<String> about = new ArrayList<String>();
		String sent = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
				.format(new Date());
			
		ArrayList<String> provenanceLinks = new ArrayList<String>();
		if(provlist != null){
			for(int i = 0; i < provlist.length; i++)
			{
			 common.Utility.log.debug("============ Provenance Link:" + provlist[i]);
			 provenanceLinks.add(provlist[i]);
			}	        	
		}
		for (int i = 0; i < provenanceLinks.size(); i++) {
			String link = (String) provenanceLinks.get(i);
			String[] links = link.split(",");
			// http://mrt.esc.abdn.ac.uk:8080/ourspaces/profile.jsp?id=140
			// TODO - store the provenance in the RDF database as "about" links
			if (links[0].startsWith("@")) {
				int uuid = user.getUserIDFromFOAF(links[1]);
				about.add(links[1]);
				content = content.replaceAll(links[0],
								"<a class=\"plinks\" href=\"http://mrt.esc.abdn.ac.uk:8080/ourspaces/profile.jsp?id="
										+ uuid
										+ "\">"
										+ links[0]
										+ "</a>&nbsp;<span class=\"nlg\" rel=\""
										+ java.net.URLEncoder.encode(links[1])
										+ "\"></span>");
			}
			// http://mrt.esc.abdn.ac.uk:8080/ourspaces/artifact_new.jsp?id=b8b9c7fd-a210-4b1d-8678-030eee9c4698
			// http://mrt.esc.abdn.ac.uk:8080/ourspaces/artifact_new.jsp?id=c906f195-53fb-4dbe-b092-1a86805154e5

			// TODO - store the provenance in the RDF database as "about" links
			if (links[0].startsWith("#")) {
				FileUtils f = new FileUtils();
				String fid = f.getFileIDFromRDF(links[1]);
				about.add(links[1]);
				content = content
						.replaceAll(
								links[0],
								"<a class=\"plinks\" href=\"http://mrt.esc.abdn.ac.uk:8080/ourspaces/artifact_new.jsp?id="
										+ fid
										+ "\">"
										+ links[0]
										+ "</a>&nbsp;<span class=\"nlg\" rel=\""
										+ java.net.URLEncoder.encode(links[1])
										+ "\"></span>");
			}
		}
		user.sendMessage(recipients, userID, threadId,title, content,sent);	
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList discourceLinks = new ArrayList();
		ArrayList about = new ArrayList();
		String outputURL = "";
		PrintWriter out = response.getWriter();
        // the action element that we'll check for
        String cmd;
        common.Utility.log.debug("found mssagehandler servlet");
        cmd = request.getParameter("action");
        if (cmd != null) 
        {
        	 if (cmd.equals("read")) 
             {
        	   try {
        			int messageID = Integer.parseInt(request.getParameter("id"));
        			HttpSession session = request.getSession();
        			User userSession = (User) session.getAttribute("use");
        	 	
        			int userID = userSession.getID();
        			readMessage(messageID, userID);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
             }else if (cmd.equals("delete")) 
             {
        	   try {
        		   //change by kapila 
        		Message msg = new Message();
        		int messageID = Integer.parseInt(request.getParameter("id"));
        		HttpSession session = request.getSession();
		    	User userSession = (User) session.getAttribute("use");
		    	
		    	int userid = userSession.getID();
					msg.deleteMessage(userid,messageID);
					common.Utility.log.debug("Deleted");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
             }
             else if (cmd.equals("deleteperm")) 
             {
        	   try {
        		   //change by kapila 
        		Message msg = new Message();
        		int messageID = Integer.parseInt(request.getParameter("id"));
        		HttpSession session = request.getSession();
		    	User userSession = (User) session.getAttribute("use");
		    	
		    	int userid = userSession.getID();
					msg.deleteMessagePerm(userid,messageID);
					common.Utility.log.debug("Deleted permenantly");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
             }
             else if (cmd.equals("hidesent")) 
             {
        	   try {
        		   //change by kapila 
        		   common.Utility.log.debug("Hide sent");
        		Message msg = new Message();
        		int messageID = Integer.parseInt(request.getParameter("id"));
        		HttpSession session = request.getSession();
		    	User userSession = (User) session.getAttribute("use");
		    	
		    	int userid = userSession.getID();
					msg.updatehideSent(userid,messageID);
					common.Utility.log.debug("Hide sent");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
             }
             else if (cmd.equals("hide")) 
             {
        	   try {
        		User user = new User();
        		int messageIDmin = Integer.parseInt(request.getParameter("idmin"));
        		int messageIDmax = Integer.parseInt(request.getParameter("idmax"));
					user.hideMessageFromSent(messageIDmin,messageIDmax);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
             }
             else if (cmd.equals("new")) 
             {
        	   try {
        		    String[] list = request.getParameterValues("person");
		       	    //int threadid =Integer.parseInt(request.getParameter("threadid")) ;
		       	    String title = request.getParameter("title");
		       		String content = request.getParameter("content");
		       			
		       		HttpSession session = request.getSession();
		           	User userSession = (User) session.getAttribute("use");
		           	common.Utility.log.debug("LIST: " + list);		           	
		        	String[] provlist = request.getParameterValues("prov");
		        	//for new messages intial thread id is -1 this will be updated to the message id by trigger trg_setthreadid
		           	newMessage(-1, userSession.getID(),title, content, list,provlist);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
             }
             else if (cmd.equals("reply")) 
             {
        	   try {
        		   // added threadid and sent parameters by kapila

        			ArrayList<String> provenanceLinks = new ArrayList<String>();
        		   //String list = request.getParameter("list");
        		    String[] list = request.getParameterValues("person");
        		    int threadid =Integer.parseInt(request.getParameter("threadid")) ;
        		    
        		    ///int threadid =-1; //for new messages intial thread id is -1 this will be updated to the message id by trigger trg_setthreadid
					String title = request.getParameter("title");
					String content = request.getParameter("content");
					common.Utility.log.debug("FOUND SERVLET!"+threadid);
					HttpSession session = request.getSession();
			    	User userSession = (User) session.getAttribute("use");
			    	String[] provlist = request.getParameterValues("prov");
			    	common.Utility.log.debug("call newmessage");
			    	newMessage(threadid, userSession.getID(),title, content, list,provlist);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.append("Error occured");
					e.printStackTrace();
				}
             }
             else if (cmd.equals("project")) 
             {
        	   try {
        		   	
        		   	String projectID = request.getParameter("id");
        		   	int threadid =0 ;
					String title = request.getParameter("title");
					String content = request.getParameter("content");
					String sent = request.getParameter("sent");
					HttpSession session = request.getSession();
			    	User userSession = (User) session.getAttribute("use");
			    	//Add the current datetime.
			    	if(sent == null){
			    		sent = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
						
			    	}
			    	int id = userSession.getID();
			    	
			    	String[] list = getProjectMembers(projectID);
			    	for (int i =0; i<list.length; i++)
			    		common.Utility.log.debug(list[i]);
			    	userSession.sendMessage(list, id, threadid,title, content,sent);
			    	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
             }
            
        	response.setContentType("text/xml");
     		response.setHeader("Cache-Control", "no-cache");
     		
     		out.println("<message>");
     		out.println("true");
     	    out.println("</message>");

        	 
        	 out.flush();
        }
        else
        {
        	out.println("Server error.");
        }
	}

	public String[] getProjectMembers(String projectID) throws ServletException, IOException, OpenRDFException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		Project project = new Project();
		ArrayList membersList = project.getProjectMembers(projectID);
		String[] list = new String[membersList.size()];
		for(int i = 0; i < membersList.size(); i++)
		{
			MemberBean mb = (MemberBean) membersList.get(i);
			String foaf = mb.getFoafID();
			list[i] = foaf;
		}
		return list;
	}

}
