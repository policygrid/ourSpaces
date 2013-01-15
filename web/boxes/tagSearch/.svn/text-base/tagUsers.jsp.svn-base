<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="common.User"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Vector"%>
<%@page import="common.Tag"%>
<%@page import="common.Utility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="rdf" class="common.RDFi" />
    
<% 
if(request.getParameter("tag") == null) { %>
 	<jsp:forward page="error.jsp" /> <%
}
String tagWord = (String) request.getParameter("tag").toString();
if( tag == null ){
	%> <jsp:forward page="error.jsp" /> <%
}

if(session.getAttribute("use") != null){
	user = (User) session.getAttribute("use");
}

		Collection<String> userList2 = new ArrayList<String>();
		userList2 = tag.getUsersOfTag(tagWord);

		for (Iterator<String> iterator = userList2.iterator(); iterator.hasNext();) {
			String foafID = (String) iterator.next();
			
			int id = user.getUserIDFromFOAF(foafID);
			if (id != 0) {
				String realName = user.getName(id);
				%>
				<p style="padding:5px; padding-left:20px;"><a href="profile.jsp?id=<%=id%>"><%=realName%></a></p>
				<% 
			}
		}
		%>