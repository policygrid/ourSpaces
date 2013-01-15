<%@ page language="java" import="com.hp.hpl.jena.ontology.*,java.util.Iterator,java.util.*,java.net.*,java.text.SimpleDateFormat,java.util.ArrayList,java.io.*,java.net.*,java.util.Vector,common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="ontology" class="common.OntologyHandler" />
<% 
ParameterHelper parHelp = new ParameterHelper(request, session);
	String liClass = (String)parHelp.getParameter("liClass",  "");
	String liStyle = (String)parHelp.getParameter("liStyle",  "");
	String ulClass = (String)parHelp.getParameter("ulClass",  "");
	String ulStyle = (String)parHelp.getParameter("ulStyle",  "");
	String ulId = (String)parHelp.getParameter("ulId",  "");
	
	//Now display the list of properties.
		%>
 					<ul id="<%=ulId%>" style="<%=ulStyle%>" class="<%=ulClass%>"> 
					
					<%
						Iterator<OntProperty> itProperties = ontology.getPropertiesWithoutClasses("discourse", null);
						while (itProperties.hasNext()) {
							OntProperty prop = (OntProperty) itProperties.next();		
							if(!prop.getNameSpace().equals("http://swan.mindinformatics.org/ontologies/1.2/discourse-relationships/"))
								continue;
							String resType = "resource";
							String range = ontology.getPropertyRange(prop);
							if (range.equals("http://www.w3.org/2001/XMLSchema#string"))  resType = "literal";
							if (range.equals("http://www.w3.org/2006/time#Instant"))  resType = "date";
							if(range.equals("")) range = "http://openprovenance.org/ontology#Artifact";
						%>
					<jsp:include page="liProperty.jsp">
						<jsp:param name="idProperty" value="<%=prop.getURI() %>" />
						<jsp:param name="liClass" value="<%=liClass %>" />
						<jsp:param name="liStyle" value="<%=liStyle %>" />
						<jsp:param name="type" value="<%=resType %>" />
						<jsp:param name="data-range" value="<%=range %>" />
						<jsp:param name="label" value="<%=prop.getLocalName()%>" />
					</jsp:include>
						<%
						} 
						%>
					 </ul>	
 					
					