<%@ page contentType="text/html; charset=utf-8" language="java" import="java.util.regex.*, java.sql.*, common.*, java.util.Random, java.util.*" errorPage="" %>

    <jsp:useBean id="fc" class="common.FacetHandler" />
    <jsp:useBean id="rdfi" class="common.RDFi" />
    
<%
	String rdfUserID = (String) request.getParameter("id");

	String label = (String) request.getParameter("label");
	String resourceType = (String) request.getParameter("resourceType");
	String property = (String) request.getParameter("property");
	

	ArrayList resources = fc.constructQueryForUserWithType(label, resourceType, property, rdfUserID);

%>

<%
	for(int i = 0; i < resources.size(); i++)
	{
		Resources res = (Resources) resources.get(i);
		String title = res.getTitle();
		String id = res.getID();
		String type = res.getType();
		%>
		<p><%=title %></p>
		<%
	}	
	%>