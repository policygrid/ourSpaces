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

   <div class="widget-top" >
      <div class="wlink"  align="right" style="float:right;  margin-left: 5px; margin-right: 10px; margin-bottom: 0px; padding: 10px;">
         <a href="mycontacts.jsp"  style=""><img src="/ourspaces/icons/001_57.png" align="middle" style="float: left; display:inline-block; margin: -2px; border: 0px none;"/><span style="margin-left:8px;">Add More Contacts</span></a>
      </div>
   </div>

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


<div class="contacts_area">

           <div style="float: left; padding: 0px; width: 60px">
          <% if(!photo.equals("")) { %>
				 <img style="float: left; margin-top: 5px;"   src="<%=photo%>" alt="" height="40" />
			<% }
			else { %>
				 <img style="float: left; margin-top: 5px;"  src="/ourspaces/images/no.jpg" alt="" height="40" />
			<% } %>
			</div>
	  
		   <label class="name" style="float: left;">
		   <strong><a href="profile.jsp?id=<%=contact.getContactID() %>"><%=user.getName(contact.getContactID()) %></a></strong><br />
		        <%
						String status = user.getStatus(contact.getContactID());
						if (status.length() > 21) status = status.substring(0,20) + "...";
						%>
							<%=status%>
		   </label>
		   <div style="float: right; padding: 3px;">
		   
		   
		   
		   <a href="#" onClick="showNewMessageDialog('<%=contact.getContactID()%>');">
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
		   
		     <%
	         if( !contact.getSkypeID(contact.getContactID()).equals("") ) {
	        	 String skypeID = contact.getSkypeID(contact.getContactID());
	        %>
	        <a href="skype:<%=skypeID%>?call"><img src="http://mystatus.skype.com/smallicon/<%=skypeID%>" style="border: none; padding:0px; margin:0px; margin-top:3px;  margin-right:5px;" width="16" height="16" alt="My status" /></a>
	        <% } %>
		   </div>          
		               
 
 
</div>


<%
	}
%>
