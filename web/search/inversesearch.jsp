<%@ page language="java" import="common.Utility,com.hp.hpl.jena.query.*, net.sf.json.*, java.util.Iterator, org.openrdf.*, org.openrdf.query.*, org.openrdf.model.Value, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*, org.policygrid.ontologies.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="con" class="common.Connections" />
<jsp:useBean id="ontology" class="common.OntologyHandler" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>




<%
	
	String inputString = "";
	if(request.getParameter("queryString")!=null)
		inputString = request.getParameter("queryString");
  if(request.getParameter("q")!=null)
		inputString = request.getParameter("q");
  //Autocomplete
  if(request.getParameter("term")!=null)
		inputString = request.getParameter("term");
  	
	
	String spaces = "true";
	if(request.getParameter("spaces") != null)
		spaces = request.getParameter("spaces").toString();
	
	String outputType = "HTML";
	if(request.getParameter("output") != null)
		outputType = request.getParameter("output").toString();

	String addValue = "false";
	if(request.getParameter("addValue")!=null)
		addValue = request.getParameter("addValue");
	
	int limit = 20;
	if(request.getParameter("limit")!=null)
		limit = Integer.parseInt(request.getParameter("limit"));

	user = (User) session.getAttribute("use");
  	int userId = -1;
	if(user!= null)
		userId = user.getID();
	JSONArray results = new SearchBean().getJSON("", inputString,20, "true".equals(addValue),"true".equals(spaces), true, userId);
	if(outputType.equals("HTML")){
		if(results!= null && results.size()>0){%>
		
		<%=results.getJSONObject(0).getString("label")%><%
		}
	}
	else{
	%><%=results.toString() %>
<%}%>