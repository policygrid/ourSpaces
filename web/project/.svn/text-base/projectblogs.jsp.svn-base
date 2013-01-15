<%@ page language="java" import="java.util.Iterator, java.util.ArrayList,  java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="container" class="common.Container" />
<jsp:useBean id="project" class="common.Project" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="blog" class="common.Blog" />

<% 
if(request.getParameter("id") == null) { %>
 	<jsp:forward page="error.jsp" /> <%
}
String tempID = (String) request.getParameter("id").toString();
if( tempID == null ){
	%> <jsp:forward page="error.jsp" /> <%
}


if(session.getAttribute("use") != null){
	user = (User) session.getAttribute("use");

}

String projectID = "http://www.policygrid.org/project.owl#"+tempID;
String projectContainer = project.getProjectContainer(projectID);
ArrayList projectBlogContainer = project.getProjectBlogContainers(projectContainer);


%>


<form method="post" action="Controller?action=newProjectBlog">
	<input type="hidden" name="container" value="<%=projectContainer %>"></input>
	<input type="hidden" name="id" value="<%=user.getID() %>"></input>
	<input type="hidden" name="projectID" value="<%=projectID %>"></input>
	<input type="hidden" name="output" value="project.jsp?id=<%=tempID %>"></input>
	<p style="padding-top:10px; padding-bottom:10px; font-size:16px;">Create New Blog Topic: </p>
	<input class="shadow ui-corner-all"  style="width:250px; height:21px; border:1px solid #666;" type="text" name="title"></input> <input type="submit" class="fg-button ui-state-active fg-button-icon-right ui-corner-all" value="Create New Blog"></input>
</form>




<p style="padding-top:10px; padding-bottom:10px; font-size:16px;">Current Blogs:</p>

<table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin:3px; padding:2px;">	
<%
				for(int i = 0; i < projectBlogContainer.size(); i++)
				{
					ProjectBlogBean pb = (ProjectBlogBean) projectBlogContainer.get(i);
					String blogContainer = pb.getBlogContainer();
					String[] fields = blogContainer.split("#");
					if (!pb.getTitle().equals("")) {
					
				%>
        		      		
        		<tr>
				<td width="16">
					<img src="icons/001_31.png"/>
				</td>
				<td width="100%">
					<a href="projectblogposts.jsp?id=<%=fields[1]%>" ><%=pb.getTitle()%></a>
				</td>
			   </tr>
				
				<% } 
					
				} %>
</table>
