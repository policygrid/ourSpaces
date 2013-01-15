<%@ page language="java" import="net.sf.json.JSONArray,net.sf.json.JSONObject,com.hp.hpl.jena.ontology.OntProperty,java.util.Vector,common.*, java.net.URLDecoder" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%
String label = "";
String id = "";
ParameterHelper parHelp = new ParameterHelper(request, session);
if(request.getParameter("label") != null) {
label = request.getParameter("label");
}
String nameUpload = "";
if(request.getParameter("nameUpload") != null && request.getParameter("nameUpload").length()>0) {
	nameUpload = request.getParameter("nameUpload");
}
//Add the nameUpload to the label, in case there is more upload windows opened.
String labelId = nameUpload + label;
if(request.getParameter("id") != null) {
	id = request.getParameter("id");
	id = URLDecoder.decode(id, "UTF-8");
}

id = URLDecoder.decode(id, "UTF-8");
String remove = (String)parHelp.getParameter("remove","true");
String display = "none";
if("true".equals(remove)) {
	display = "block";
}
String value = (String)request.getParameter("value");
if(value==null)
	value = "";

String labelText = label;
//Transform uppercase to lowercase and a space
labelText = labelText.replaceAll("([A-Z])"," $1");
labelText = labelText.toLowerCase();
//Make first letter uppercase
labelText = labelText.replaceFirst(""+labelText.charAt(0), ""+Character.toUpperCase(labelText.charAt(0)));
OntologyHandler onto  = new OntologyHandler();
OntProperty prop = onto.getProperty(label);
RDFi rdf = new RDFi();

//Vector<Resources> values
JSONArray values = new SearchBean().getJSON(prop.getRange().getURI(),  "", -1, true ,true, false, 0);
//rdf.getCertainObject();

%><jsp:include page="header.jsp" flush="false">
		<jsp:param value="<%=label %>" name="label"/>
		<jsp:param value="<%=id %>" name="id"/>
		<jsp:param value="<%=nameUpload %>" name="nameUpload"/>
</jsp:include>	
	
					<select name="<%=label %>" id="<%=labelId %>" style="width:250px; float: right;" onchange="var p = activeDialog.findPropertyEverywhere('<%=label %>'); p.value.length = 0;if($(this).val() != null && $(this).val().length > 0) p.value.push($(this).val());activeDialog.changeValue(p, this);">
			       
					<option value=""></option>
					 <%
					  	for(int i = 0; i < values.size(); i++)
					  	{
					  		JSONObject pb = (JSONObject) values.get(i);					  		
					  		//No need to encode, data are sent as JSON.
					  		String encRes = pb.getString("value");
					  		//JSONArray results = new SearchBean().getJSON("JSON",  pb.getID(), false,true, true);
					  		String title = pb.getString("label");
					%>
					<option value="<%=encRes%>"><%=title%></option>
			        <%
			        }
			        %>
			      </select>
<jsp:include page="footer.jsp" flush="false">
		<jsp:param value="<%=label %>" name="label"/>
		<jsp:param value="<%=id %>" name="id"/>
		<jsp:param value="<%=nameUpload %>" name="nameUpload"/>
</jsp:include>	