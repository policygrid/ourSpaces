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
	new SearchBean();

	user = (User) session.getAttribute("use");
  int userId = -1;
	if(user!= null)
		userId = user.getID();
	String inputString = "";
	if(request.getParameter("queryString")!=null)
		inputString = request.getParameter("queryString");
  if(request.getParameter("q")!=null)
		inputString = request.getParameter("q");
  //Autocomplete
  if(request.getParameter("term")!=null)
		inputString = request.getParameter("term");
  	
	String queryType = "";
	if(request.getParameter("type") != null)
		queryType = request.getParameter("type").toString();
	
	String spaces = "true";
	if(request.getParameter("spaces") != null)
		spaces = request.getParameter("spaces").toString();
	
	String outputType = "HTML";
	if(request.getParameter("output") != null)
		outputType = request.getParameter("output").toString();

	String addValue = "false";
	if(request.getParameter("addValue")!=null)
		addValue = request.getParameter("addValue");
	
	
	if("HTML".equals(outputType)){
		%><div id="queryString" style="display:hidden"><%=inputString %></div><ul style="padding: 5px;width: auto;" class="ui-autocomplete ui-menu ui-widget ui-widget-content ui-corner-all"><%		
	}
	/*else{
		%>[<%	
	}*/

	JSONArray results = new SearchBean().getJSON(queryType, inputString, 20, "true".equals(addValue),"true".equals(spaces), false,userId);
	int size = results.size();
	for(int i = 0;i<size;i++){
		JSONObject json = (JSONObject)results.get(i);
		String href = json.getString("href");
		String id = json.getString("id");
		if("HTML".equals(outputType) && "".equals(queryType) && !"".equals(href)){%>
		<li><div id="<%=id %>" data-class="<%=json.getString("data-class") %>" title="<%=json.getString("label") %>" class="dragElement">
	   		<a onclick="log('log_searchaction(userid,searchalgo,selectedrank,searchstring, resultscount, clickedresource)', '<%=userId %>,\'<%=json.getString("method") %>\',<%=i %>,\'<%=inputString%>\',<%=size %>, \'<%=id %>\'');return true;" href="<%=json.getString("href") %>"><img src="<%=json.getString("image") %>" align="middle"><%=json.getString("label") %></a>
	   	</div>
	    </li>
			<%		
		}
	}
	
	if("HTML".equals(outputType)){
		%></ul><%		
	}
	else{		
		%><%=results.toString() %><%
	}%>





