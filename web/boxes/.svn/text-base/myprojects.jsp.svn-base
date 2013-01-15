<%@ page language="java" import="java.net.URLEncoder, java.util.Iterator, java.util.ArrayList, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="project" class="common.Project" />

<%

common.User user = (common.User) session.getAttribute("use");
ArrayList projectList = project.getAllProjectsAboutUser(user.getFOAFID());
int id = user.getID();
%>
 
     <div class="widget-top" >
      <div class="wlink"  align="right" style="float:right;  margin-left: 5px; margin-right: 10px; margin-bottom: 0px; padding: 10px;">
         <a class="popup_dialog" title="New Project" href="createprojectform.jsp"><img src="images/spaces/Projects_small.png" align="middle" style="float: left; display:inline-block; margin: -2px; border: 0px none;"/><span style="margin-left:8px;">Create Project</span></a>     
      </div>
   </div>
   

<div style="float:left; padding:5px">
	<table border="0" cellspacing="5" cellpadding="3" width="100%">
	<!-- 		<tr style="padding-bottom:10px; background-color:#F5F5F5; border:1px solid #666666;">
			<td align="right" style="border:1px solid #ccc; padding:5px;">
				<a href="createprojectform.jsp" rel="facebox">Create Project</a>
			</td>
		</tr>
		-->
		 <%
	  	for(int i = 0; i < projectList.size(); i++)
	  	{
	  		ProjectBean pb = (ProjectBean) projectList.get(i);
	  		
	  		String separatedClassName = Utility.splitString(pb.getRole());
	  		
	  		String encRes = URLEncoder.encode(pb.getURI());
	  		
		%>
			 <tr>
				<td rowspan="2" style="">
					<img src="images/spaces/Projects_small.png" align="middle"  style="float: left; margin-top: auto; margin-bottom:auto;" />
			    </td>
				<td>
					<a href="project.jsp?id=<%=pb.getURL()%>"><%=pb.getTitle()%></a>&nbsp;<span class="nlg" rel="<%=encRes%>"></span>
				</td>
			</tr>
			<tr>
				<td wodth="100%">
				  <span style="font-size:10px; color:#666;">
				  	<%=pb.getSubtitle()%>
			  	  </span>
				</td>
				<td align="right" style="font-size:10px; color:#FF6600;">
					<%=separatedClassName %>
				</td>
			</tr>
		<% } %>
	</table>
 </div>    