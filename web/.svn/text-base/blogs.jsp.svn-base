<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="blog" class="common.UserBlogBean" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<jsp:include page="top_head.jsp" />
<!-- menu goes here -->
<jsp:include page="top_tail.jsp" />

<%
if (session.isNew() == true) {
%>
<jsp:forward page="error.jsp" />
<%
}
if (session.getAttribute("use") == null) {
%>
<jsp:forward page="error.jsp" />
<%
}

user = (User) session.getAttribute("use");
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));
String url = "/ourspaces/users/" + user.getID() + ".xml";
String username = user.getUsername(user.getID());
String resID = user.getRes(user.getID());

XML xml = new XML();



int id = user.getID();

Vector blogs = user.getUserBlog(id);

%>



   <p style="color:#FF6600; font-size:24px; padding-bottom:10px;">My Blog</p>
    
    <div class="regContainer">
    
    	<div class="contactsboxes">
			<p class="style13">&nbsp;</p>
			<%
				Iterator iter = blogs.iterator();
				while(iter.hasNext()){
					blog = (UserBlogBean) iter.next();
					String content = blog.getContent();
					String title = blog.getTitle();
					String date = blog.getDate();
					
			%>
				<div style="padding:10px; float:left; position:relative; width:940px;">
					<p style="padding-bottom:10px; color:#9AD0E4; font-size:14px;"><%=title%></p>
					<p><%=content%></p>
				  <p style="font-size:9px; text-align:right; padding-top:5px;"><span class="style6"><span class="style6">Posted on:</span> <%=date%></p>
				</div>
			<% } %>  
	
				
		  <div class="projres">
			  <p class="style4 style1">Add to Blog:</p>
				<div style="padding:10px; float:left; position:relative;">
					<form method="post" action="Controller?action=userBlog">
						<p>Blog subject:</p>
						<p>
						  <input type="text" style="width:600px; margin-bottom:10px;" name="title">
					  </p>
						<p><textarea name="content" style="width:600px; height:100px;"></textarea></p>
						<input type="hidden" value="<%=id%>" name="id" />
						<p style="padding-top:5px;"><input type="submit" value="Submit" />
						<input type="reset" value="Clear" /></p>
					</form>
				</div>
		  </div>
			
		</div>
    
    </div>
 
  <jsp:include page="bottom.jsp" />