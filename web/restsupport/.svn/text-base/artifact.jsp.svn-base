<%@ page language="java" import="net.sf.json.*, java.util.Iterator, org.openrdf.*, java.text.SimpleDateFormat, org.openrdf.query.*, org.openrdf.model.Value, java.util.ArrayList, java.io.*, java.net.*, java.util.*, common.*, org.policygrid.ontologies.*" contentType="text/html; charset=ISO-8859-1"
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
	
    //Vector vv = ontology.getAllSClasses("general","http://openprovenance.org/ontology#Artifact");

    //for (int i = 0; i < vv.size(); i++) {
    //	common.Utility.log.debug((String)vv.get(i));
    //}
    
    boolean versions = false;

	String artifactID = "";
	if(request.getParameter("artifactID")!=null) {
	   common.Utility.log.debug(request.getParameter("artifactID"));
	   String artifactIDstr = request.getParameter("artifactID");
	   
	   if (artifactIDstr.endsWith("/versions")) {
		   versions = true;
		   artifactID = "http://openprovenance.org/ontology#"+artifactIDstr.substring(0, artifactIDstr.length() - 9);
	   } else {
	   artifactID = "http://openprovenance.org/ontology#"+request.getParameter("artifactID");
	   }
	   
	   common.Utility.log.debug(artifactID);
	}
	//String outputType = "JSON";
	//if(request.getParameter("output") != null)
    //outputType = request.getParameter("output").toString();

	
	

	if (versions) { 
		
		JSONArray results = new JSONArray();
		JSONObject oneRes = new JSONObject();
		StringBuffer qry = new StringBuffer(1024);
		String query = null;
		
		TupleQuery output = null;
		TupleQueryResult result = null;
		
		
		
		//previous versions
		qry.append("SELECT ?resource ?title ?version ?uri ?timestamp ");
	    qry.append("WHERE { ");
	    qry.append("<"+artifactID+"> <http://www.policygrid.org/provenance-generic.owl#previousVersion>* ?resource . ");
	    qry.append("OPTIONAL { ?resource <http://www.policygrid.org/provenance-generic.owl#title> ?title } ");
	    qry.append("OPTIONAL { ?resource <http://www.policygrid.org/provenance-generic.owl#versionNumber> ?version } ");
	    qry.append("OPTIONAL { ?resource <http://www.policygrid.org/ourspacesVRE.owl#timestamp> ?timestamp . } ");
	    qry.append("OPTIONAL { ?resource <http://www.policygrid.org/provenance-generic.owl#hasURI> ?uri } ");
	    qry.append("FILTER (?resource != <"+artifactID+"> ) ");
	    qry.append("}");
		
		query = qry.toString();
		
		con.repConnect();
		
		 output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		 result = output.evaluate();

		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();	
			   
			   oneRes = new JSONObject();
			   
			   oneRes.put("resource", bindingSet.getValue("resource").stringValue());
			   if (bindingSet.getValue("title") != null) oneRes.put("title", bindingSet.getValue("title").stringValue());
			   if (bindingSet.getValue("version") != null) oneRes.put("version", bindingSet.getValue("version").stringValue());
			   if (bindingSet.getValue("uri") != null) oneRes.put("uri", bindingSet.getValue("uri").stringValue());
			   
			   if (bindingSet.getValue("timestamp") != null) {	   
					double timestamp = Double.parseDouble(bindingSet.getValue("timestamp").stringValue());  
				    long time = (long)timestamp;
					Date date = new Date(time);
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String fulldate = sdf.format(date);
					oneRes.put("date", fulldate);		
				   }
			   
			   results.add(oneRes);
		}
		result.close();
		con.repDisconnect();
		
		
		//later versions
		qry = new StringBuffer(1024);
		qry.append("SELECT ?resource ?title ?version ?uri ?timestamp ");
	    qry.append("WHERE { ");
	    qry.append("<"+artifactID+"> ^<http://www.policygrid.org/provenance-generic.owl#previousVersion>* ?resource . ");   
	    qry.append("OPTIONAL { ?resource <http://www.policygrid.org/provenance-generic.owl#title> ?title } ");
	    qry.append("OPTIONAL { ?resource <http://www.policygrid.org/provenance-generic.owl#versionNumber> ?version } ");   
	    qry.append("OPTIONAL { ?resource <http://www.policygrid.org/ourspacesVRE.owl#timestamp> ?timestamp . } ");
	    qry.append("OPTIONAL { ?resource <http://www.policygrid.org/provenance-generic.owl#hasURI> ?uri } ");
	    qry.append("FILTER (?resource != <"+artifactID+"> ) ");
	    qry.append("}");
		
		query = qry.toString();
		
		con.repConnect();
		
		 output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		 result = output.evaluate();

		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();	
			   
			   oneRes = new JSONObject();
			   
			   oneRes.put("resource", bindingSet.getValue("resource").stringValue());
			   if (bindingSet.getValue("title") != null) oneRes.put("title", bindingSet.getValue("title").stringValue());
			   if (bindingSet.getValue("version") != null) oneRes.put("version", bindingSet.getValue("version").stringValue());
			   if (bindingSet.getValue("uri") != null) oneRes.put("uri", bindingSet.getValue("uri").stringValue());
			   
			   if (bindingSet.getValue("timestamp") != null) {	   
				double timestamp = Double.parseDouble(bindingSet.getValue("timestamp").stringValue());  
			    long time = (long)timestamp;
				Date date = new Date(time);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String fulldate = sdf.format(date);
				oneRes.put("date", fulldate);		
			   }
			   
			   results.add(oneRes);
		}
		result.close();
		con.repDisconnect();
		
		%>
		 
	    <%=results.toString() %>
	<%
		
	} else {
		JSONObject jobject = new JSONObject();
		StringBuffer qry = new StringBuffer(1024);
		
		qry.append("SELECT ?predicate ?value ");
	    qry.append("WHERE { ");
	    qry.append("<"+artifactID+"> ?predicate ?value . ");
	    qry.append("}");
		
		String query = qry.toString();
	 
		//common.Utility.log.debug(query);
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			
			   
		       String predicate = bindingSet.getValue("predicate").stringValue();   
			   String value = bindingSet.getValue("value").stringValue();
			   
			   String[] pred = predicate.split("#");
			   if(pred.length > 1 &&pred[1]!=null)
			   	jobject.put(pred[1], value);
			   else if(pred.length > 0 &&pred[0]!=null)
				   	jobject.put(pred[0], value);
		}
		result.close();
		con.repDisconnect();
		%>
	 
	    <%=jobject.toString() %>
	<%
	}
	
    %>
    

    





