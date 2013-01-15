<%@ page language="java" import="common.*, java.net.URLDecoder" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
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
	display = "block";
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

%><jsp:include page="header.jsp" flush="false">
		<jsp:param value="<%=label %>" name="label"/>
		<jsp:param value="<%=id %>" name="id"/>
		<jsp:param value="<%=nameUpload %>" name="nameUpload"/>
</jsp:include>	
<input class="datepicker inputValue" onchange="var p = activeDialog.findPropertyEverywhere('<%=label %>');activeDialog.changeValue(p, this);" style="float: right; padding: 2px;  border: 1px solid #000000; width: 200px;" id="<%=labelId %>value" name="<%=id %>" size="45" type="text" value="<%=value%>">
<jsp:include page="footer.jsp" flush="false">
		<jsp:param value="<%=label %>" name="label"/>
		<jsp:param value="<%=id %>" name="id"/>
		<jsp:param value="<%=nameUpload %>" name="nameUpload"/>
</jsp:include>	