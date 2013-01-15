<%@ page contentType="text/html; charset=utf-8" language="java" import="java.util.regex.*, java.sql.*, common.*, java.util.Random, java.util.*" errorPage="" %>

<jsp:useBean id="fc" class="common.FacetHandler" />
<jsp:useBean id="user" class="common.User" />

<%
String label = (String) request.getParameter("label");
String resourceType = (String) request.getParameter("resourceType");
String property = (String) request.getParameter("property");
%>

<% 
		
		ArrayList users = fc.getPropertyList(label, resourceType, property);
		Hashtable depositors = fc.getDepositor(label, resourceType, property, users);
		
		for(int i = 0; i < users.size(); i++)
		{
			String rdfUserID = (String) users.get(i);
			ArrayList resources = (ArrayList) depositors.get(rdfUserID);
			
			int id = user.getUserIDFromRDFID(rdfUserID);
			if(id != 0)
			{
				String name = user.getName(id);
				if(resources.size() > 0) {        
					%>
						<p><a href="#" onclick="getUser('<%=label %>', '<%=resourceType %>', '<%=property %>','<%=rdfUserID %>'); searchBrowse('<%=label %>', '<%=resourceType %>', '<%=property %>'); return false;"><%=name%> - (<%=resources.size() %>)</a></p>
					<%
			    }
			}
		}
		
		
		%>