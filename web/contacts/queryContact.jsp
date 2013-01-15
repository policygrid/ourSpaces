<%@ page contentType="text/html; charset=utf-8" language="java" import="java.util.regex.*, java.sql.*, java.util.Random, java.util.Vector, java.util.HashMap, java.util.Iterator" errorPage="" %>

<jsp:useBean id="ontologyHandler" class="common.OntologyHandler" />
<jsp:useBean id="rdf" class="common.RDFi" />

<p style="color:#FF6600; font-size:14px; padding-bottom:10px;">Results</p>
<%

String namespace = request.getParameter("namespace");
String resoruceName = request.getParameter("resourceName");
String keywords = request.getParameter("keywords");
String sid = request.getParameter("sid");
String random = request.getParameter("random");

HashMap results = null;

try {
 //results = rdf.getResourcesByKeywordSearch(namespace + "#" +resoruceName, keywords);
 //common.Utility.log.debug("Namespace:-------- "+namespace);
 
 if (namespace.equals("http://xmlns.com/foaf/0.1/")) {

		results = rdf.getRegisteredUsersByKeyword(namespace + resoruceName, keywords);
 } else {
	 
	 results = rdf.getRegisteredUsersByKeyword(namespace + "#" +resoruceName, keywords);
 }

 } catch (Exception e) {
	common.Utility.log.debug(e);
	e.printStackTrace();
}

 Iterator c = results.keySet().iterator();
 while(c.hasNext()) {
   String res = (String)c.next();
   String val = (String)results.get(res);
 %>
 <p style="padding-left:10px;">
 	<%if(!val.equals("")) { %>
  		<img src="icons/001_55.png" align="middle" /> <a style="color: rgb(102, 102, 102); font-size:11px;" onClick="updateInput('<%=sid%>','<%=res%>','<%=val%>','<%=random %>'); return false;"><%=val%></a><br />
  	<%} else { %>
  		No results found.
  	<% } %>
 </p>
 <%  
 }
%>




