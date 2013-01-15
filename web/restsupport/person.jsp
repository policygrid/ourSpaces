<%@ page language="java" import="net.sf.json.*, java.util.Iterator, org.openrdf.*, org.openrdf.query.*, org.openrdf.model.Value, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*, org.policygrid.ontologies.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="con" class="common.Connections" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>




<%
	
	String personID = "";
	if(request.getParameter("personID")!=null)
		personID = "http://xmlns.com/foaf/0.1/#"+request.getParameter("personID");

	//String outputType = "JSON";
	//if(request.getParameter("output") != null)
    //outputType = request.getParameter("output").toString();

	
	

	JSONObject jobject = new JSONObject();
	
	StringBuffer qry = new StringBuffer(1024);
	
	qry.append("SELECT ?name ?surname ?mbox ?website ?photo ?researchInterests ?jobTitle ");
    qry.append("WHERE { ");
    qry.append("<"+personID+"> <http://xmlns.com/foaf/0.1/firstName> ?name . ");
    qry.append("<"+personID+"> <http://xmlns.com/foaf/0.1/surname> ?surname . ");
    qry.append("<"+personID+"> <http://xmlns.com/foaf/0.1/mbox> ?mbox . ");
    qry.append("OPTIONAL { <"+personID+"> <http://www.policygrid.org/ourspacesVRE.owl#hasWebsite> ?website } ");
    qry.append("OPTIONAL { <"+personID+"> <http://www.policygrid.org/ourspacesVRE.owl#hasPhoto> ?photo } ");
    qry.append("OPTIONAL { <"+personID+"> <http://www.policygrid.org/ourspacesVRE.owl#hasResearchInterest> ?researchInterests } ");
    qry.append("OPTIONAL { <"+personID+"> <http://www.policygrid.org/ourspacesVRE.owl#hasJobTitle> ?jobTitle } ");
    qry.append("}");
	
	String query = qry.toString();

	
	con.repConnect();
	
	TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
	TupleQueryResult result = output.evaluate();
	
	while (result.hasNext()) 
	{
		   BindingSet bindingSet = result.next();
		
		   
		   //exceptions
		   
		
		   jobject.put("name", bindingSet.getValue("name").stringValue());
		   jobject.put("surname", bindingSet.getValue("surname").stringValue());
		   jobject.put("mbox", bindingSet.getValue("mbox").stringValue());
		   if (bindingSet.getValue("website") != null) jobject.put("website", bindingSet.getValue("website").stringValue());
		   if (bindingSet.getValue("photo") != null) jobject.put("photo", bindingSet.getValue("photo").stringValue());
		   if (bindingSet.getValue("researchInterests") != null) jobject.put("researchInterests", bindingSet.getValue("researchInterests").stringValue());
		   if (bindingSet.getValue("jobTitle") != null) jobject.put("jobTitle", bindingSet.getValue("jobTitle").stringValue());
		   
		 
	}
	result.close();
	con.repDisconnect();
	jobject.put("id", user.getUserIDFromFOAF(personID));
    %>
    <%=jobject.toString() %>




