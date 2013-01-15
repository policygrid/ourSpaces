<%@ page language="java" import="common.*, java.net.URLDecoder, java.util.List" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%
String label = "";
String id = "";

ParameterHelper parHelp = new ParameterHelper(request, session);
if(request.getParameter("label") != null) {
	label = request.getParameter("label");
}
//Add the nameUpload to the label, in case there is more upload windows opened.
String labelId = label;
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
List<String> list = (List<String>)session.getAttribute("values");
%><jsp:include page="header.jsp" flush="false">
		<jsp:param value="<%=label %>" name="label"/>
</jsp:include>	
<div id="<%=labelId %>inputDiv" class="resourceInputDiv" style="float: left; width: 100%;">
						<div id="<%=labelId %>inputContent" style="float: left; width: 100%;">
						<%for(String reference: list){
						%><jsp:include page="smallEntity.jsp">
						<jsp:param name="id" value="<%=reference%>"/>
						</jsp:include><%
						}
						%>
						</div>
						<div style="width:100%">
						<input id="<%=labelId %>input" class="resourceInput" style=" border: 1px solid #000000;" id="<%=labelId %>" name="" size="60" type="text" value="">
						<button id="<%=labelId %>NewDiv"  style="float: left;margin:3px; display: inline-block; background: none repeat scroll 0 0 OliveDrab;font-size: 11px;font-weight: normal;">Create new </button>
						</div>
</div>
<jsp:include page="footer.jsp" flush="false">
		<jsp:param value="<%=label %>" name="label"/>
</jsp:include>	