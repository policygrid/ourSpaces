<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.util.Vector, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project1" class="common.ProjectBean" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="project" class="common.Project" />

 <%

user = (User) session.getAttribute("use");
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));

Vector contacts = user.getContacts(user.getID());

ArrayList list = new ArrayList();

int userid = user.getID();
int id = user.getID();
String projectID = (String)session.getAttribute("projectID");

ArrayList memberList = project.getProjectMembers(projectID);

common.Utility.log.debug("Project ID: "+ projectID);
%>




<table border="0" cellspacing="0" cellpadding="0" width="290" style="padding:0px margin: 0px;">
<%                        

    for(int i = 0; i < memberList.size(); i++) {
	//java.util.Iterator iter = contacts.iterator();
	//while(iter.hasNext()){
		MemberBean mb = (MemberBean) memberList.get(i);
		String role = mb.getRole();
	    String separatedClassName = Utility.splitString(role);
	    String name = mb.getName();
        int uid = mb.getUserID();

        common.Utility.log.debug("UID: "+ uid); 
		String uname = user.getUsername(uid);
		
		String photo = "";
		String onlineStatus = "";
		if (uid != 0) {
		  photo = user.getPhoto(uid);
		  photo = Utility.absoluteURLtoRelative(photo);
		  onlineStatus = user.getUserOnlineStatus(uid);
		}
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
					<%if (uid != 0) { %>
						<a href="profile.jsp?id=<%=uid %>"><%=name %></a>
				    <% } else {%>
				    <%=name %>
				    <%} %>
					</td>
				</tr>
				<tr>
					<td>
						
							<span style="font-size:10px;"><%=separatedClassName%></span><br />
					</td>
				</tr>
			</table>
		
		</td>
		
		<td style="width:58px; height:31px; padding:0px; margin: 0px; border-right: 1px solid #E0E0E0; border-top: 1px solid #E0E0E0; border-bottom: 1px solid #E0E0E0;">
			<% if (uid != 0) {%>
			<a href="messages/messages.jsp?to=<%=uname%>&sourcePage=myhome.jsp" rel="facebox"><img src="/ourspaces/images/e-mail_icon1.gif" border="0" style="border:none; padding:0px; margin:0px; margin-top:6px; margin-right:3px;" /></a>
	        <%
	        if(onlineStatus.equals("active")) {
	        %>
	            <a href="javascript:void(0)" onclick="javascript:chatWith('<%=uid %>')"><img src="/ourspaces/images/chat.png" style="border:none; padding:0px; margin:0px; margin-top:3px; margin-right:5px;" /></a>
	        <% } 
	        if(onlineStatus.equals("idle")) {
	        %>
	            <a href="javascript:void(0)" onclick="javascript:chatWith('<%=uid %>')"><img src="/ourspaces/images/chat_idle.png" style="border:none;border:none; padding:0px; margin:0px;margin-top:3px; margin-right:5px;" /></a>
	        <% } 
	        
	        } else {
	        %>
	        &nbsp;
	        <%} %>
		</td>
		
	</tr>
	<tr height="7">
	
	</tr>

<%
	}
%>

 </table>


  
        
