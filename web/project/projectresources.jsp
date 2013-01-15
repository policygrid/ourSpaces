<%@ page language="java" import="java.util.List,java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.Project" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="blog" class="common.Blog" />

<%
String temp = (String) request.getParameter("id").toString();
if( temp == null ){
	%> <jsp:forward page="error.jsp" /> <%
}
String projectID = org.policygrid.ontologies.Project.NS.toString() + temp;


Vector<Resources> resources = project.getResources(projectID);

String[] fields = projectID.split("#");
String projectURL = (String) fields[0];
String projectURLNumbers = (String) fields[1];

List<Resources> tmp = new ArrayList<Resources>(resources);
java.util.Collections.reverse(tmp);
session.setAttribute("resources", tmp);			
String offset = request.getParameter("offset");
String limit = request.getParameter("limit");
%>         			  				
	         		  <jsp:include page="../boxes/resourcesList.jsp" flush="false">
                             <jsp:param value="<%=limit %>" name="limit"  />
                             <jsp:param value="<%=offset %>" name="offset" />
                             <jsp:param value="projectResources" name="divId" />
                             <jsp:param value="<%=\"/ourspaces/project/projectresources.jsp?id=\"+temp%>" name="paging" />
                  </jsp:include><%
session.removeAttribute("resources");%>