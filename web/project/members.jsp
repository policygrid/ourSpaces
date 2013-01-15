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
%>




<%                        

    for(int i = 0; i < memberList.size(); i++) {
	//java.util.Iterator iter = contacts.iterator();
	//while(iter.hasNext()){
		MemberBean mb = (MemberBean) memberList.get(i);
		String role = mb.getRole();
	    String separatedClassName = Utility.splitString(role);
	    String name = mb.getName();
        int uid = mb.getUserID();

        //common.Utility.log.debug("UID: "+ uid); 
		String uname = user.getUsername(uid);
		
		String photo = "";
		String onlineStatus = "";
		if (uid != 0) {
		  photo = user.getPhoto(uid);
		  photo = Utility.absoluteURLtoRelative(photo);
		  onlineStatus = user.getUserOnlineStatus(uid);
		}
%>


<div class="contacts_area" style="width: 300px">


<% if(!photo.equals("")) { %>
				 <img style="float: left; margin-top: 5px;"   src="<%=photo%>" alt="" height="40" />
			<% }
			else { %>
				 <img style="float: left; margin-top: 5px;"  src="/ourspaces/images/no.jpg" alt="" height="40" />
			<% } %>
	  
		   <label class="name" style="float: left;">
		   
		    <strong>
		   <%if (uid != 0) { %>
						<a href="profile.jsp?id=<%=uid %>"><%=name %></a>
				    <% } else {%>
				    <%=name %>
				    <%} %>
		   </strong><br />
		   
		   
		 
		        <%
						String status = user.getStatus(uid);
						if (status.length() > 21) status = status.substring(0,20) + "...";
						%>
							<%=status%>
		   </label>
		   <div style="float: right; padding: 3px;">
		   
		   <a href="#" onClick="showNewMessageDialog('<%=uid%>');">
	        	<img src="images/e-mail_icon1.gif" border="0" style="border:none; padding:0px; margin:0px; margin-top:6px; margin-right:3px;" />
	        </a>
	        <%
	        if(onlineStatus.equals("active")) {
	        %>
	            <a href="javascript:void(0)" onclick="javascript:chatWith('<%=uid %>')"><img src="images/chat.png" style="border:none; padding:0px; margin:0px; margin-top:3px; margin-right:5px;" /></a>
	        <% } 
	        if(onlineStatus.equals("idle")) {
	        %>
	            <a href="javascript:void(0)" onclick="javascript:chatWith('<%=uid %>')"><img src="images/chat_idle.png" style="border:none;border:none; padding:0px; margin:0px;margin-top:3px; margin-right:5px;" /></a>
	        <% } %>
		   </div>  
		           
		    <%
	         if( !user.getSkypeID(uid).equals("") ) {
	        	 String skypeID = user.getSkypeID(uid);
	        %>
	        <a href="skype:<%=skypeID%>?call"><img src="http://mystatus.skype.com/smallicon/<%=skypeID%>" style="border: none; padding:0px; margin:0px; margin-top:3px;  margin-right:5px;" width="16" height="16" alt="My status" /></a>
	        <% } %>           
 
 
</div>



<%
	}
%>

 </table>


  
        
