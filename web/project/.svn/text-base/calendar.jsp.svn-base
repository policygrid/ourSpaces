<%@ page language="java" import=" java.util.*, java.text.*, common.*, java.sql.*, java.util.Iterator, java.util.ArrayList, org.policygrid.ontologies.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="project" class="common.Project" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="user" class="common.User" />

<% 
if(request.getParameter("id") == null) { %>
 	<jsp:forward page="error.jsp" /> <%
}
String temp = (String) request.getParameter("id").toString();
if( temp == null ){
	%> <jsp:forward page="error.jsp" /> <%
}

String projectID = org.policygrid.ontologies.Project.NS.toString() + temp;

String container = project.getProjectContainer(projectID);

%>


<div style="position: relative; float:left;" id="calendar">

<jsp:include page="/calendar/monthView.jsp" flush="true">
	<jsp:param name="container" value="<%=container %>" />

</jsp:include>


</div>