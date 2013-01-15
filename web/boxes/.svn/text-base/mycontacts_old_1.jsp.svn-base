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
    int i = 0;
	while(iter.hasNext() && i <= 5){
		i++;
		contact = (common.User) iter.next();
		String uname = user.getUsername(contact.getContactID());
		String photo = user.getPhoto(contact.getContactID());
		photo = Utility.absoluteURLtoRelative(photo);
		String onlineStatus = user.getUserOnlineStatus(contact.getContactID());
%>
	<tr style="border-bottom: 1px solid #E0E0E0; ">
		<td style="width:41px; height:41px; padding:0px margin: 0px;">
			<% if(!photo.equals("")) { %>
				<img align="left" class="photo" src="<%=photo%>" style="margin:0px; padding:0px; margin-right:0px;" width="31" height="31" />
			<% }
			else { %>
				<img align="left" class="photo" src="/ourspaces/images/no.jpg" style="margin:0px; padding:0px; margin-right:0px;" alt="no picture" width="41" height="41" />
			<% } %>
		</td>
	
		<td style="width:168px; height:41px; padding:0px; padding-left:0px; margin:0px; margin-left:20px; border-bottom: 1px solid #E0E0E0;">
			
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
		
		<td style="width:58px; height:31px; padding:0px; margin: 0px; border-bottom: 1px solid #E0E0E0;">
			<a href="messages/messages.jsp?to=<%=uname%>&sourcePage=myhome.jsp" rel="facebox">
	        	<img src="images/e-mail_icon1.gif" border="0" style="border:none; padding:0px; margin:0px; margin-top:8px; margin-right:3px;" />
	        </a>
	        <%
	        if(onlineStatus.equals("active")) {
	        %>
	            <a href="javascript:void(0)" onclick="javascript:chatWith('<%=contact.getContactID() %>')"><img src="images/chat.png" style="border:none; padding:0px; margin:0px; margin-top:5px; margin-right:5px;" /></a>
	        <% } 
	        if(onlineStatus.equals("idle")) {
	        %>
	            <a href="javascript:void(0)" onclick="javascript:chatWith('<%=contact.getContactID() %>')"><img src="images/chat_idle.png" style="border:none;border:none; padding:0px; margin:0px;margin-top:5px; margin-right:5px;" /></a>
	        <% } %>
		</td>
		
	</tr>
	<tr height="7">
	
	</tr>

<%
	}
%>

 </table>
 
 
 
 
 
 


  
        
