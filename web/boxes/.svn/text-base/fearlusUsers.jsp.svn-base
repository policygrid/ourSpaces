<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.util.Vector, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="user" class="common.User" />

 <%

user = (User) session.getAttribute("use");
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));

Vector contacts = user.getContacts(user.getID());

ArrayList list = new ArrayList();

int userid = user.getID();
int id = user.getID();


%>




<table border="0" cellspacing="0" cellpadding="0" width="290" style="padding:0px margin: 0px;">
<%                         
	java.util.Iterator iter = contacts.iterator();
	while(iter.hasNext()){
		contact = (common.User) iter.next();
		String uname = user.getUsername(contact.getContactID());
		String photo = user.getPhoto(contact.getContactID());
		photo = Utility.absoluteURLtoRelative(photo);
		String onlineStatus = user.getUserOnlineStatus(contact.getContactID());
		
		String name = user.getName(contact.getContactID());
		
		if (name.equals("Edoardo Pignotti") || name.equals("Richard Reid") || name.equals("Nick Gotts")) {
		
%>
	<tr>
		<td style="width:41px; height:31px; padding:0px margin: 0px;">
			<% if(!photo.equals("")) { %>
				<img align="left" src="<%=photo%>" style="border:1px solid #666666; margin:0px; padding:0px; margin-right:10px;" width="31" height="31" />
			<% }
			else { %>
				<img align="left" src="/ourspaces/images/no.jpg" style="border:1px solid #FFFFFF; margin:0px; padding:0px; margin-right:10px;" alt="no picture" width="31" height="31" />
			<% } %>
		</td>
	
		<td style="width:168px; height:31px; padding:0px; padding-left:4px; border-left: 1px solid #E0E0E0; border-top: 1px solid #E0E0E0; border-bottom: 1px solid #E0E0E0; -moz-border-radius-topleft: 5px;-moz-border-radius-bottomleft: 5px;">
			
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<a href="profile.jsp?id=<%=contact.getContactID() %>"><%=user.getName(contact.getContactID()) %></a>
					</td>
				</tr>
				<tr>
					<td>
						<%
						String status = user.getStatus(contact.getContactID());
						if (status.length() > 36) status = status.substring(0,35) + "...";
						%>
							<span style="font-size:8px;"><%=status%></span><br />
					</td>
				</tr>
			</table>
		
		</td>
		
		<td style="width:58px; height:31px; padding:0px; margin: 0px; border-right: 1px solid #E0E0E0; border-top: 1px solid #E0E0E0; border-bottom: 1px solid #E0E0E0;">
			<a href="messages/messages.jsp?to=<%=uname%>&sourcePage=myhome.jsp" rel="facebox">
	        	<img src="images/e-mail_icon1.gif" border="0" style="border:none; padding:0px; margin:0px; margin-top:6px; margin-right:3px;" />
	        </a>
	        <%
	        if(onlineStatus.equals("active")) {
	        %>
	            <a href="javascript:void(0)" onclick="javascript:chatWith('<%=contact.getContactID() %>')"><img src="images/chat.png" style="border:none; padding:0px; margin:0px; margin-top:3px; margin-right:5px;" /></a>
	        <% } 
	        if(onlineStatus.equals("idle")) {
	        %>
	            <a href="javascript:void(0)" onclick="javascript:chatWith('<%=contact.getContactID() %>')"><img src="images/chat_idle.png" style="border:none;border:none; padding:0px; margin:0px;margin-top:3px; margin-right:5px;" /></a>
	        <% } %>
		</td>
		
	</tr>
	<tr height="7">
	
	</tr>

<% }
	}
%>

 </table>


  
        
