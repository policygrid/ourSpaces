package common;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.openrdf.OpenRDFException;
import org.xml.sax.SAXException;

public class ChatServlet extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	
	
	public class ChatBox {
		public String name;
		public String date;
	}
	
	static final long serialVersionUID = 1L;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	Connections con = new Connections();

	public ChatServlet() {
		super();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		// the action element that we'll check for
		String cmd;
		
		cmd = request.getParameter("action");
		//common.Utility.log.debug("Invoking chat servlet GET : "+cmd);
		
		
		HttpSession session = request.getSession();
		
		
		if (session.getAttribute("chatHistory") == null) {
			Hashtable ht = new Hashtable();
			session.setAttribute("chatHistory", ht);
			//common.Utility.log.debug("I have set chatHository to an empty Hashtable");
		}
		
		if (session.getAttribute("openChatBoxes") == null) {
			session.setAttribute("openChatBoxes", new ArrayList());
			//common.Utility.log.debug("I have set openChatBoxes to an empty Hashtable");
		}
		

		if (cmd != null) {

			if (cmd.equals("chatheartbeat"))
				try {
					chatheartbeat(request, response);
				} catch (Exception e2) {
					e2.printStackTrace();
				} 
			if (cmd.equals("sendchat"))
				try {
					sendchat(request, response);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			if (cmd.equals("closechat"))
				closechat(request, response);
			if (cmd.equals("startchatsession"))
				try {
					startchatsession(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				} 
		    if (cmd.equals("getname"))
						try {
							getname(request, response);
						} catch (Exception e) {
							e.printStackTrace();
						}

			out.flush();
		} else {
			out.println("Server error.");
		}

	}
	
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		// the action element that we'll check for
		String cmd;
		
		cmd = request.getParameter("action");
		
		
		//common.Utility.log.debug("Invoking chat servlet POST : "+cmd);
	
		HttpSession session = request.getSession();

		
		
				if (session.getAttribute("chatHistory") == null) {
					Hashtable ht = new Hashtable();
					session.setAttribute("chatHistory", ht);
					common.Utility.log.debug("I have set chatHistory to an empty Hashtable");
		}
		
		if (session.getAttribute("openChatBoxes") == null) {
			session.setAttribute("openChatBoxes", new ArrayList());
			common.Utility.log.debug("I have set openChatBoxes to an empty Hashtable");
		}
		

		if (cmd != null) {

			if (cmd.equals("chatheartbeat"))
				try {
					chatheartbeat(request, response);
				} catch (Exception e2) {
					e2.printStackTrace();
				} 
			if (cmd.equals("sendchat"))
				try {
					sendchat(request, response);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			if (cmd.equals("closechat"))
				closechat(request, response);
			if (cmd.equals("startchatsession"))
				try {
					startchatsession(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				} 
		   if (cmd.equals("getname"))
					try {
						getname(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}

			out.flush();
		} else {
			out.println("Server error.");
		}

	}
	
	
	public void getname(HttpServletRequest request,
			HttpServletResponse response) throws ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("use");
		
		int id = Integer.parseInt((String)request.getParameter("id"));
		
		//common.Utility.log.debug("ID: "+(String)request.getParameter("id"));
		
		String userName = userSession.getName(id);
		
		//common.Utility.log.debug("Username: "+userName);
		PrintWriter out = response.getWriter();	
		response.setContentType("application/json");
		out.println("{");
		out.println("\"personname\": \"" + userName + "\",");
		out.println("\"items\": [");
		out.println(" ]");
		out.println("}");
		
		
	}

	private boolean checkChatBox(ArrayList chatBoxes, String name){
		boolean ret = false;
		
		
		for (int i = 0; i < chatBoxes.size(); i++) {
			ChatBox chat = (ChatBox)chatBoxes.get(i);		
			String ch = chat.name;
			if (ch.equals(name)) {
				ret = true;
			}
		}
		
		return ret;
	}
	
	
	private boolean checkChatHistory(Hashtable chatHistory, String name){
		boolean ret = false;
		
			String historyTxt = (String) chatHistory.get(name);	
			if (historyTxt != null) ret = true;
			
		return ret;
	}
	
	
	public String removeSpaces(String str){
		return str.replaceAll(" ", "");
	}
	
	public void chatheartbeat(HttpServletRequest request,
			HttpServletResponse response) throws ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		/*
$sql = "select * from chat where (chat.to = '".mysql_real_escape_string($_DB_SESSION['username'])."' AND recd = 0) order by id ASC";
	$query = mysql_query($sql);
	$items = '';
$chatBoxes = array();      
while ($chat = mysql_fetch_array($query)) {
		(1)
		(2)
		(3)
	(4)
	(5)
		(6)
		(7)
	}
*/
		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("use");
		String user = ""+userSession.getID();
	
			
		Connections con = new Connections();
		con.connect();
		String sql = "select * from chat where (chat.to = '"+user+"' AND recd = 0) order by id ASC";
		Statement st = con.getCon().createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		String items ="";
		ArrayList chatBoxes = new ArrayList();
		
		
		
		while(rs.next())
		{  
						
			String from =  rs.getString("from");	 
			String message =  rs.getString("message");	 

			
			boolean chatBoxExists = checkChatBox((ArrayList)session.getAttribute("openChatBoxes"),from);
			boolean chatHistoryExists= checkChatHistory((Hashtable)session.getAttribute("chatHistory"),from);
		
			if (!chatBoxExists && chatHistoryExists ) {
				Hashtable history = (Hashtable)session.getAttribute("chatHistory");
				//items = (String)history.get(from);
			}
			
			// (2) $chat['message'] = sanitize($chat['message']);
			message = sanitize(message);

		  
			items += " {";
				items += "\"s\": \"0\",";
				items += "\"f\": \""+from+"\",";
			items += "\"m\": \""+message+"\"";
			items += "},";
			
			if (!chatHistoryExists) {
				Hashtable history = (Hashtable)session.getAttribute("chatHistory");
				history.put(from,"");		
				session.setAttribute("chatHistory", history);
			}
		
			
			String cht = "";
			cht += "{";
				cht += "	\"s\": \"0\",";
				cht += "	\"f\": \""+from+"\",";
			cht += "	\"m\": \""+message+"\"";
			cht += " },";
			
		
				Hashtable history = (Hashtable)session.getAttribute("chatHistory");
			
			String cht1 = (String)history.get(from);
			cht1 += cht;
			history.put(from,cht1);
			session.setAttribute("chatHistory", history);

			
			//unset($_DB_SESSION['tsChatBoxes'][$chat['from']]);
			if (session.getAttribute("tsChatBoxes") != null) {
				ArrayList tsChatBoxes = (ArrayList) session.getAttribute("tsChatBoxes");
				for (int i = 0; i < tsChatBoxes.size(); i++) {
					ChatBox chat = (ChatBox)tsChatBoxes.get(i);		
					String ch = chat.name;
					if (ch.equals(from)) tsChatBoxes.remove(i);
				}

			}
			
		   //$_DB_SESSION['openChatBoxes'][$chat['from']] = $chat['sent'];
			if (session.getAttribute("openChatBoxes") != null) {
				ArrayList openChatBoxes = (ArrayList) session.getAttribute("openChatBoxes");
				boolean found = false;
				for (int i = 0; i < openChatBoxes.size(); i++) {
					ChatBox chat = (ChatBox)openChatBoxes.get(i);		
					String ch = chat.name;
					if (ch.equals(from)) {	
						ChatBox newChat = new ChatBox();
						newChat.name = ch; 
						newChat.date =	rs.getString("sent");
					  openChatBoxes.set(i, newChat);
					  found = true;
					}
				}
				if (!found) {
					ChatBox newChat = new ChatBox();
					newChat.name = from;
					newChat.date = "";
					openChatBoxes.add(newChat);
					session.setAttribute("openChatBoxes",openChatBoxes);
				}
				 

			}
			
			

		}
		
		
		rs.close();
		con.disconnect();		
		
	
	
		
		if (session.getAttribute("openChatBoxes") != null) {
			ArrayList openChatBoxes = (ArrayList) session.getAttribute("openChatBoxes");
			for (int i = 0; i < openChatBoxes.size(); i++) {
				ChatBox ch = (ChatBox)openChatBoxes.get(i);
				
				String chatbox = ch.name;
				//common.Utility.log.debug("Opening chatbox "+chatbox);
				String time = ch.date;
				
				  if ((session.getAttribute("tsChatBoxes") != null) && (checkChatBox((ArrayList)session.getAttribute("tsChatBoxes"),chatbox))) {
					  long timeL = Long.parseLong(time);
					  long now = System.currentTimeMillis()-timeL;
					  String message = "Sent at " + time;
					  
					 
					  if (now > 180) {
						items += " {";
						items += "\"s\": \"2\",";
						items += "\"f\": \""+chatbox+"\",";
						items += "\"m\": \""+message+"\"";
						items += "},"; 
					  }
					  

			 
					  if (session.getAttribute("chatHistory") != null) {
					
					    	  
	
					  Object obj = session.getAttribute("chatHistory");
						
						
						Hashtable history = (Hashtable) session.getAttribute("chatHistory");
						String historyTxt = (String) history.get(chatbox);
						if (historyTxt == null) historyTxt = "";
						
						historyTxt += "  {";
						historyTxt += "		\"s\": \"2\",";
						historyTxt += " \"f\": \""+chatbox+"\",";
						historyTxt += "     \"m\": \""+message+"\"";
						historyTxt += " },";

						history.put(chatbox, historyTxt);
						session.setAttribute("chatHistory", history);
					  }
					  
			
					  
					  /*
						if (session.getAttribute("tsChatBoxes") != null) {
							ArrayList tsChatBoxes = (ArrayList) session.getAttribute("tsChatBoxes");
							for (int j = 0; i < tsChatBoxes.size(); j++) {
								ChatBox chat = (ChatBox)tsChatBoxes.get(j);		
								String chh = chat.name;
								if (chh.equals(chatbox)) {
									ChatBox newChat = new ChatBox();												
									newChat.name = chat.name;
									newChat.date = "1";
									tsChatBoxes.set(j, newChat);
								}
							}

						}
						*/
						
						  
				  }
				
				
			}

		}	
		
		
		
		
		/*


	$sql = "update chat set recd = 1 where chat.to = '".mysql_real_escape_string($_DB_SESSION['username'])."' and recd = 0";
	$query = mysql_query($sql);

	if ($items != '') {
		$items = substr($items, 0, -1);
	}
header('Content-type: application/json');
?>
{
		"items": [
			<?php echo $items;?>
        ]
}

<?php

exit(0)
		 
		 
		 */
	

		con.connect();
		String sql1 = "update chat set recd = 1 where chat.to = '"+user+"' and recd = 0";
		Statement st1 = con.getCon().createStatement();
		st1.executeUpdate(sql1);
		con.disconnect();
		
		if (!items.equals("")) {
			items = items.substring(0, items.length() - 1);
		}

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		String res = "";
		res += 
			res += "{";
			res += "\"username\": \"" + user + "\",";
			res += "\"items\": [";
			res += items;
			res += " ]";
			res += "}";
			out.println(res);

		

	}

	public void sendchat(HttpServletRequest request,
			HttpServletResponse response) throws ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("use");
		String user = ""+userSession.getID();
		
		
		Enumeration paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()) 
		{
        	String param = (String)paramNames.nextElement();
		}
		
		
		
		
		
		
		/*
		 $from = $_DB_SESSION['username'];
	     $to = $_POST['to'];
	     $message = $_POST['message'];
		 */
		String from = user;
		String to = (String)request.getParameter("to");
		String message = (String)request.getParameter("message");
		
		
		// $_DB_SESSION['openChatBoxes'][$_POST['to']] = date('Y-m-d H:i:s', time());
		if (session.getAttribute("openChatBoxes") != null) {
			ArrayList openChatBoxes = (ArrayList) session.getAttribute("openChatBoxes");
			boolean found = false;
			for (int i = 0; i < openChatBoxes.size(); i++) {
				ChatBox chat = (ChatBox)openChatBoxes.get(i);		
				String ch = chat.name;
				if (ch.equals(to)) {
					ChatBox newChat = new ChatBox();												
					newChat.name = ch;
					newChat.date = ""+System.currentTimeMillis();
					found = true;
				  openChatBoxes.set(i, newChat);
				}
			}
			if (!found) {
				ChatBox newChat = new ChatBox();												
				newChat.name = to;
				newChat.date = ""+System.currentTimeMillis();
		        openChatBoxes.add(newChat);
				session.setAttribute("openChatBoxes",openChatBoxes);
			}


		}

		
		
		//$messagesan = sanitize($message);
		String messagesan = sanitize(message);
		
		
		/*
		 if (!isset($_DB_SESSION['chatHistory'][$_POST['to']])) {
	   	 $_DB_SESSION['chatHistory'][$_POST['to']] = '';
	     }
		 $_DB_SESSION['chatHistory'][$_POST['to']] .= <<<EOD
					   {
			"s": "1",
			"f": "{$to}",
			"m": "{$messagesan}"
	     },
        EOD
		 */
		if (session.getAttribute("chatHistory") != null) {
			Hashtable history = (Hashtable) session.getAttribute("chatHistory");
			String historyTxt = (String) history.get(to);
			if (historyTxt == null) historyTxt = "";
			historyTxt += "  {";
			historyTxt += "		\"s\": \"1\",";
			historyTxt += " \"f\": \""+to+"\",";
			historyTxt += "     \"m\": \""+messagesan+"\"";
			historyTxt += " },";

			history.put(to, historyTxt);
			
			session.setAttribute("chatHistory", history);
		}
		
		// unset($_DB_SESSION['tsChatBoxes'][$_POST['to']]);
		if (session.getAttribute("tsChatBoxes") != null) {
			ArrayList tsChatBoxes = (ArrayList) session.getAttribute("tsChatBoxes");
			for (int i = 0; i < tsChatBoxes.size(); i++) {
				ChatBox chat = (ChatBox)tsChatBoxes.get(i);		
				String ch = chat.name;
				if (ch.equals(to)) tsChatBoxes.remove(i);
			}

		}
		
		/*
		$sql = "insert into chat (chat.from,chat.to,message,sent) values ('".mysql_real_escape_string($from)."', '".mysql_real_escape_string($to)."','".mysql_real_escape_string($message)."',NOW())";
		$query = mysql_query($sql);
		echo "1";
	    */
		
		Connections con = new Connections();
		con.connect();
		String sql = "insert into chat (chat.from,chat.to,message,sent) values ('"+from+"', '"+to+"','"+message+"',NOW())";
		Statement st = con.getCon().createStatement();
		st.executeUpdate(sql);
		con.disconnect();
		
		PrintWriter out = response.getWriter();
		out.println("1");
	}

	public void closechat(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		    String chatbox = (String)request.getParameter("chatbox");
			HttpSession session = request.getSession();

			if (session.getAttribute("openChatBoxes") != null) {
				ArrayList openChatBoxes = (ArrayList) session.getAttribute("openChatBoxes");
				for (int i = 0; i < openChatBoxes.size(); i++) {
					ChatBox chat = (ChatBox)openChatBoxes.get(i);		
					String ch = chat.name;
					if (ch.equals(chatbox)) openChatBoxes.remove(i);
				}

			}
		
			PrintWriter out = response.getWriter();
			out.println("1");
		


	}

	public String chatBoxSession(String chatbox, HttpServletRequest request) {

		String items = "";

	
		HttpSession session = request.getSession();
		if (session.getAttribute("chatHistory") != null) {
			Hashtable history = (Hashtable) session.getAttribute("chatHistory");
			items = (String) history.get(chatbox);

		}
		return items;
	}

	public void startchatsession(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			ParserConfigurationException, SAXException, ServletException,
			OpenRDFException, InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		
		HttpSession session = request.getSession();

		String items = "";
		User userSession = (User) session.getAttribute("use");

		String user = ""+userSession.getID();

		if (session.getAttribute("openChatBoxes") != null) {
			ArrayList openChatBoxes = (ArrayList) session.getAttribute("openChatBoxes");
			//common.Utility.log.debug("------------------> OPEN CHAT BOXES NOT NULL Size: "+openChatBoxes.size());
			for (int i = 0; i < openChatBoxes.size(); i++) {
				
				
				ChatBox chat = (ChatBox)openChatBoxes.get(i);
				//common.Utility.log.debug("--------- ChatBox name: "+chat.name);
				if (chatBoxSession(chat.name, request) != null ) items += chatBoxSession(chat.name, request);
			}
      
		}
		

		if (!items.equals("")) {
			items = items.substring(0, items.length() - 1);
		}

		String msg = "";
		msg+="{";
		msg+="\"username\": \"" + user + "\",";
		msg+="\"items\": [";
		msg+=items;
		msg+=" ]";
		msg+="}";
		
	

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		//common.Utility.log.debug("----------------------------------------");
		//common.Utility.log.debug(msg);
		//common.Utility.log.debug("----------------------------------------");
		out.println(msg);


	}

	public String sanitize(String text) {
		String res = text;
		res = res.replaceAll("&", "&amp;");
		res = res.replaceAll("\"", "&quot;");
		res = res.replaceAll("'", "&#039;");
		res = res.replaceAll("<", "&lt;");
		res = res.replaceAll(">", "&gt;");

		res = res.replaceAll("\n\r", "\n");
		res = res.replaceAll("\r\n", "\n");
		res = res.replaceAll("\n", "<br>");
		return res;
	}

}
