<%@ page language="java" import="net.sf.json.*, java.util.Iterator, org.openrdf.*, org.openrdf.query.*, org.openrdf.model.Value, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*, org.policygrid.ontologies.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="ontology" class="common.OntologyHandler" />
<jsp:useBean id="con" class="common.Connections" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>




<%
	String projectID = "";
	if(request.getParameter("projectID")!=null)
		projectID = "http://www.policygrid.org/project.owl#"+request.getParameter("projectID");
	JSONObject jobject = new JSONObject();
	StringBuffer qry = new StringBuffer(1024);
	qry.append("SELECT ?predicate ?value ");
    qry.append("WHERE { ");
    qry.append("<"+projectID+"> ?predicate ?value . ");
    qry.append("}");
	
	String query = qry.toString();
	con.repConnect();
	TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
	TupleQueryResult result = output.evaluate();
	
	while (result.hasNext()) 
	{
		   BindingSet bindingSet = result.next();
		
		   
	       String predicate = bindingSet.getValue("predicate").stringValue();   
		   String value = bindingSet.getValue("value").stringValue();
		   
		   String[] pred = predicate.split("#");
		   
		   jobject.put(pred[1], value);
		 
	}
	result.close();
	con.repDisconnect();

    %>
    

    
    <%=jobject.toString() %>




