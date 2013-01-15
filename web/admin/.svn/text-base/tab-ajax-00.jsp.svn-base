
<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="user" class="common.User" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>



<%

user = (User) session.getAttribute("use");
if ((user.getID() == 59) || (user.getID() == 56) || (user.getID() == 55)) {
	
} else {
	response.sendRedirect(response.encodeRedirectURL("/ourspaces/error.jsp"));
}

Vector contacts = user.getAllContacts();

ArrayList list = new ArrayList();

int userid = user.getID();
int id = user.getID();


Random r = new Random();
int d = r.nextInt();

%>

					<%
	  					java.util.Iterator iter = contacts.iterator();
						while(iter.hasNext()){
							contact = (common.User) iter.next();
					  		String uname = user.getUsername(contact.getContactID());
							String photo = user.getPhoto(contact.getContactID());
							String onlineStatus = user.getUserOnlineStatus(contact.getContactID());
					%>
					
					<%=user.getName(contact.getContactID()) %>; <%=user.getEmail(contact.getContactID()) %> <br>
					

					<%
						}
					%>

				</table>



  
 
