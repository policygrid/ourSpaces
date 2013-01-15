<%@ page language="java" import="com.hp.hpl.jena.ontology.*,java.util.Iterator,java.util.*,java.net.*,java.text.SimpleDateFormat,java.util.ArrayList,java.io.*,java.net.*,java.util.Vector,common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="ontology" class="common.OntologyHandler" />
<% 
ParameterHelper parHelp = new ParameterHelper(request, session);
	new ResourceUploadBean();
	String liClass = (String)parHelp.getParameter("liClass",  "");
	String liStyle = (String)parHelp.getParameter("liStyle",  "");
	String ulClass = (String)parHelp.getParameter("ulClass",  "");
	String ulStyle = (String)parHelp.getParameter("ulStyle",  "");
	String ulId = (String)parHelp.getParameter("ulId",  "");
	String onClick = (String)parHelp.getParameter("onClick",  "");
	String innerUlStyle = (String)parHelp.getParameter("innerUlStyle",  "padding-left:15px");
	
	String className = (String)parHelp.getParameter("className",  "http://openprovenance.org/ontology#Process");
	String ontologyName = (String)parHelp.getParameter("ontologyName",  "all");
		
	String allClasses = (String)parHelp.getParameter("allClasses",  "true");			
	Vector<String> subClasses = new Vector<String>();//ontology.getSubclassListNamespace("all",  className);
	subClasses.add(className);
%>
<%!	//Function for recursive loading the ontology tree.
    // There is a lot of parameters, but unfortunatelly, the method doesn't see the jsp variables.
 public String loadTree(String content, String className, common.OntologyHandler ontology, 
		 String liClass, String liStyle, String onClick, String ulClass, String ulStyle, String innerUlStyle, String allClasses, String ontologyName){
	Vector<String> subClasses = ontology.getSubclassListNamespace(ontologyName, className);
	String fullName = className;
	className=Utility.getLocalName(className);
	content += "<li style=\""+liStyle+"\" class=\""+liClass+"\" rel=\"resource\"><a href=\"#\" onClick=\""+onClick.replaceAll("#className", fullName)+"\"style=\"float:left;\" >" + className+"</a><br/>";
	
	if(subClasses.size() > 0){
		content += "<ul style=\""+innerUlStyle+"\">";
		//Adding all children
		for(String subClass : subClasses){
			if("false".equals(allClasses) && ResourceUploadBean.isNoShowClass(subClass))
				continue;
			content += loadTree("", subClass, ontology, liClass, liStyle, onClick, ulClass, ulStyle, innerUlStyle, allClasses, ontologyName);
		}
		// closing ul and li
		content += "</ul>";
	}
	content += "</li>";
	
	return content;
}
%>
		<ul id="<%=ulId%>">
			<%
				for(String subClass : subClasses){
					String content = loadTree("", subClass, ontology, liClass, liStyle, onClick, ulClass, ulStyle, innerUlStyle, allClasses, ontologyName);%>
					<%=content %>
			<%	} %>
		</ul>	
					