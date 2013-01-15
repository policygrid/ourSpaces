<%@ page contentType="text/html; charset=utf-8" language="java" import="java.util.regex.*, java.sql.*, common.*, java.util.Random, java.util.*" errorPage="" %>

    <jsp:useBean id="fc" class="common.FacetHandler" />
    
<%
	String label = (String) request.getParameter("label");
	String resourceType = (String) request.getParameter("resourceType");
	String property = (String) request.getParameter("property");
	

	ArrayList titles = fc.getAllTitles(label, resourceType, property);

%>

<%
	for(int i = 0; i < titles.size(); i++)
	{
		Resources res = (Resources) titles.get(i);
		String title = res.getTitle();
		String id = res.getID();
		String type = res.getType();
		%>
		<p><%=title %></p>
		<%
	}	
	%>