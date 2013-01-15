<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, java.util.List,  javax.swing.tree.TreePath, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="file" class="common.FileUtils" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<%
Random r = new Random();
int d = r.nextInt();

String tempContainer = request.getParameter("container");
String output = request.getParameter("output");
String blogContainer = "http://www.policygrid.org/ourspacesVRE.owl#" + tempContainer;

user = (User) session.getAttribute("use");
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));

int id = user.getID();



java.util.Vector resources = user.getResources(id);




%>


	<form class="blogPostDialog" method="post" id="target" action="Controller?action=blog">
		<p style="margin-bottom: 5px;">Blog subject:</p>
						<p>
							<input type="text" class="ui-widget ui-corner-all" style="width:600px; margin-bottom:10px; padding:3px; border:1px solid;" name="title">
						</p>
		<p style="width 600px; font-size: 10px; color: grey;"> You can create links to people or artefacts by prefixing names with: <br> @ for people (e.g.@John) or # for artefacts (e.g. #JournalPaper) </p>
		<div style="width: 606px">
						   <div class="ui-widget ui-widget-content ui-corner-all" style="margin-top: 3px; padding: 3px;"> 
						   <textarea id="post1" name="content" rows="6"  cols="80" style="width: 590px; border:none; background-color: transparent;"></textarea>
						   </div>
						<div id="provenancelinks">	
						</div>
						<div id="discourse">
						</div>
						</div>
		
					
		<input type="hidden" value="<%=id%>" name="id" />
		<input type="hidden" value="<%=blogContainer %>" name="blogContainer"></input>
		<input type="hidden" value="<%=output %>" name="output"></input>
     
     <!-- 
   		<p style="padding-top:5px;">
			<input type="submit" value="Submit" onclick="ste.submit();" />
			<input type="reset" value="Clear" />
		</p>
          --> 
    </form>

