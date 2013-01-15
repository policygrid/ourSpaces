
<%@ page language="java"
	import="java.util.Iterator,java.util.*,java.net.*,java.text.SimpleDateFormat,java.util.ArrayList,java.io.*,java.net.*,java.util.Vector,common.*"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="blog" class="common.Blog" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="rdf" class="common.RDFi" />
<% 
ParameterHelper parHelp = new ParameterHelper(request, session);
   ArtifactBean artifact = (ArtifactBean)parHelp.getParameter("artifact", null);
%>
<table id="previousVersionList" width="90%">
	<%
	for(String uri : artifact.getPreviousVersions()){
		Random r = new Random();
		int random = r.nextInt();				
		String property = "Title";
		String value = "<a href=\"/ourspaces/artifact_new.jsp?id="+Utility.getLocalName(uri)+"\">"+rdf.getResourceTitle(uri)+"</a>";
		String separatedClassName = "Title";
		//String version = rdf.getResourceVersion(uri);
		%>
	<jsp:include page="/artifactSpace/valueWithEditDelete.jsp"
		flush="false">
		<jsp:param value="<%=separatedClassName %>" name="separatedClassName" />
		<jsp:param value="<%=random %>" name="random" />
		<jsp:param value="<%=value %>" name="value" />
		<jsp:param value="<%=property %>" name="id" />
	</jsp:include>
	<%					
	}
	%>										
</table>