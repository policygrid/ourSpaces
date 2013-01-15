
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






        	<table border="0" cellspacing="5" cellpadding="5" style="width:400px;">
        	<tr><td></td><td><h2>Registered People</h2></td></tr>
					<%
	  					java.util.Iterator iter = contacts.iterator();
						while(iter.hasNext()){
							contact = (common.User) iter.next();
					  		String uname = user.getUsername(contact.getContactID());
							String photo = user.getPhoto(contact.getContactID());
							String onlineStatus = user.getUserOnlineStatus(contact.getContactID());
					%>
					<tr>
						<td width="65">
							<% if(!photo.equals("")) { %>
									<img src="<%=common.Utility.absoluteURLtoRelative(photo)%>" style="border:1px solid #666666;" width="48" height="50" />
							<% }
							   else { %>
							  		<img src="/ourspaces/images/no.jpg" alt="no picture" width="50" height="50" />
							<% } %>
						</td>
						<td width="300" style="border-bottom:1px solid #C7F0FE;">
							<a href="/ourspaces/profile.jsp?id=<%=contact.getContactID() %>"><%=user.getName(contact.getContactID()) %></a>&nbsp;&nbsp;
							<a href="/ourspaces/admin/userRDF.jsp?rdfid=<%=java.net.URLEncoder.encode(contact.getFOAFID(contact.getContactID())) %>">RDF</a><br />
							<span style="font-size:10px;"><%=user.getStatus(contact.getContactID())%></span><br />
					
                                <input type="hidden" value="<%=uname%>" name="contactid" />
                                <input type="hidden" value="<%=id%>" name="userid" />       
                                <%
                                if(onlineStatus.equals("active")) {
                                %>
                                	<img src="/ourspaces/images/chat.png" style="border:none;" />
                                <% } %>
                                <%
                                if(onlineStatus.equals("idle")) {
                                %>
                                	<img src="/ourspaces/images/chat_idle.png" style="border:none;" />
                                <% } %>				
						</td>
					</tr>

					<%
						}
					%>

				</table>



  
 
