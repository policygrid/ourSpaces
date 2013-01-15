<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<%@page import="java.util.Collections, common.ParameterHelper, common.Resources, common.RDFi, java.util.Vector"%>
<jsp:useBean id="blog" class="common.Blog" />
<jsp:useBean id="tags" class="java.util.Vector" scope="request"/>

<%@page import="java.net.URLEncoder, common.Utility"%>
  <%
common.User user = (common.User) session.getAttribute("use");
int id = user.getID();
RDFi rdf = new RDFi();
//"http://www.policygrid.org/ourspacesVRE.owl#"
String namespace = "http://openprovenance.org/ontology#";
ParameterHelper parHelp = new ParameterHelper(request, session);
String type =  (String)parHelp.getRequestParameter("type", "");
String userOnly =  (String)parHelp.getRequestParameter("userOnly", "true");
ArrayList<String> resources = new ArrayList<String>();
String divId = (String) parHelp.getRequestParameter("divId", "resourcesList");
String [] types;
if(type.contains(",")){
	types = type.split(",");
}
else{
	types = new String[]{type};
}
for(String s : types){
	Vector<Resources> tmp;
	if("true".equals(userOnly)) 
		tmp = rdf.getResources(s, user.getRDFID(), true);
	else
		tmp = rdf.getResources(s, null, true);
	for(Resources r : tmp){
		if(!resources.contains(r.getId()))
			resources.add(r.getId());
	}
}
Tag tag = new Tag();
if( resources.size() > 0)
	tags.addAll(tag.getResourceTags( resources));
%>
<jsp:include page="../boxes/tags.jsp"></jsp:include>