<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="blog" class="common.Blog" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>


<%if (session.isNew()==true){ %>
<jsp:forward page="error.jsp" />
<% } %>

<% 
if( session.getAttribute("use") == null){ %>
<jsp:forward page="error.jsp" />
<% }

User user = (User) session.getAttribute("use");


int id = user.getID();

String rdfUserID = user.getUserRDFID(id);
String profileContainer = rdf.getUserProfileContainer(rdfUserID);
ArrayList blogContainer = rdf.getBlogContainer(profileContainer);

String blogContainerID = (String) blogContainer.get(0);
String[] fields = blogContainerID.split("#");

ArrayList blogPosts = blog.getBlogPosts((String)blogContainer.get(0));
String output = "blog.jsp?id="+user.getID();
String out2 = URLEncoder.encode(output);
%>

   
    <div class="widget-top" >
      <div class="wlink"  align="right" style="float:right;  margin-left: 5px; margin-right: 10px; margin-bottom: 0px; padding: 10px;">
         <a class="popup_blog_dialog" title="New Blog Post" href="createblogpost.jsp?container=<%=fields[1] %>&output=<%=out2 %>"><img src="/ourspaces/icons/001_31.png" align="middle" style="float: left; display:inline-block; margin: -2px; border: 0px none;"/><span style="margin-left:8px;">Create New Blog Post</span></a>     
      </div>
   </div>
   
   

<div style="float:left; padding:5px">	
	<table border="0" cellspacing="0" cellpadding="0" width="295px" style="margin:3px; padding:2px; margin-right:10px;">	
		 <%
		 for(int i = 0; i < blogPosts.size(); i++)
		 {
			BlogBean bb = (BlogBean) blogPosts.get(i);
		 	String title = bb.getTitle();
		 	String date = bb.getDate();
		 					
		 %>
			<tr>
				<td width="16">
					<img src="icons/001_31.png" />
				</td>
				<td width="100%">
					<a href="blog.jsp?id=<%=id%>#<%=i%>"><%=title%></a>
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td align="right">
				  <span style="font-size:9px; color:#FF6600; margin-right: 10px;">
				  	<%=date%>
			  	  </span>
				</td>
			</tr>
		<% 
			if(i == 4)
				break;	 
		 } %>
	</table>

</div>


