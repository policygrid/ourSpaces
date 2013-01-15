<%@ page language="java" import="com.hp.hpl.jena.ontology.OntProperty,common.*, java.net.URLDecoder, java.util.List" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%
String label = "";
String id = "";

ParameterHelper parHelp = new ParameterHelper(request, session);
if(request.getParameter("label") != null) {
	label = request.getParameter("label");
}
if(request.getParameter("id") != null) {
	id = request.getParameter("id");
	id = URLDecoder.decode(id, "UTF-8");
}
String remove = (String)parHelp.getParameter("remove","true");
String display = "none";
if("true".equals(remove)) {
	display = "";
}

String nameUpload = "";
if(request.getParameter("nameUpload") != null && request.getParameter("nameUpload").length()>0) {
	nameUpload = request.getParameter("nameUpload");
}
//Add the nameUpload to the label, in case there is more upload windows opened.
String labelId = nameUpload + label;
String value = (String)request.getParameter("value");
if(value==null)
	value = "";

String labelText = label;
//Transform uppercase to lowercase and a space
labelText = labelText.replaceAll("([A-Z])"," $1");
labelText = labelText.toLowerCase();
//Make first letter uppercase
labelText = labelText.replaceFirst(""+labelText.charAt(0), ""+Character.toUpperCase(labelText.charAt(0)));

RDFi rdf = new RDFi();
String helpText = "";
OntologyHandler o = new OntologyHandler();
OntProperty p = o.getProperty(label);
helpText = p.getComment(null);
/*
public String getPropertyComment(String property){
	List<Properties> l = rdf.getResourceProperties(id, "http://www.w3.org/2000/01/rdf-schema#comment");
	for(Properties p : l){
		helpText += p.getValue();
	}
}*/
String displayHelp = helpText == null || helpText.length() == 0?"none":"";

String noTitle = "";
if(helpText == null || helpText.length() == 0){
	noTitle = "noTitle";
	helpText = "";
}
%>
<div  id="<%=labelId %>" data-class="<%=label %>" class="ui-corner-all upload-element <%=noTitle%>">
<div class="uploadFieldIcon"></div>
<label style="float:left" for="nom" title="<%=helpText%>"><%=labelText %>: 	<!-- <span class="uploadHelpIcon" style="display:<%=displayHelp%>" title="<%=helpText%>">?</span> -->
</label>
	<span class="ui-state-highlight" style="border:0;display:<%=display%>;"><a href="" style="float: right; display:<%=display%>;" class="ui-icon ui-icon-circle-minus" title="Removes the property" onclick="activeDialog.removeProperty('<%=label %>', this.parentNode)"></a></span>
	<div class="uploadFieldValueContainer">