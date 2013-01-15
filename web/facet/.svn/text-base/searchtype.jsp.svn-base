<%@ page contentType="text/html; charset=utf-8" language="java" import="java.util.regex.*, java.sql.*, common.*, java.util.Random, java.util.*" errorPage="" %>

<jsp:useBean id="fc" class="common.FacetHandler" />
<jsp:useBean id="user" class="common.User" />

<%
String label = (String) request.getParameter("label");
String resourceType = (String) request.getParameter("resourceType");
String property = (String) request.getParameter("property");

String[] fields = resourceType.split("#");

%>

<script type="text/javascript" src="/ourspaces/javascript/jquery-1.2.6.min.js"></script>
<p><em><%=fields[1] %></em></p>

	<%
	ArrayList subtypes = fc.getSubclassList(label, resourceType);
	Hashtable types = fc.getTypes(label, resourceType, property, subtypes);
	for(int i = 0; i < subtypes.size(); i++)
	{
		String subtype = (String) subtypes.get(i);
		ArrayList resources = (ArrayList) types.get(subtype);
		
		String className = subtype.substring(subtype.indexOf("#")+1);
        String tNameSpace = subtype.substring(0,subtype.indexOf("#"));        
        String separatedClassName = Utility.splitString(className);
        if(resources.size() > 0) {        
		%>
			<p><a href="#" onclick="getBrowse('<%=label %>', '<%=subtype %>', '<%=property %>'); searchUser('<%=label %>', '<%=subtype %>', 'http://www.policygrid.org/provenance-generic.owl#depositedBy'); return false;"><%=separatedClassName%> - (<%=resources.size() %>)</a></p>
		<%
        }
	}
	%>