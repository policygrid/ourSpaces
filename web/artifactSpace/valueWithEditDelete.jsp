<%@page import="java.net.URLEncoder"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.util.regex.*, java.sql.*, common.*, java.util.Random, java.util.Vector" errorPage="" %>
<%

ParameterHelper parHelp = new ParameterHelper(request, session);
String separatedClassName = (String)parHelp.getParameter("separatedClassName","");
Integer random = Integer.parseInt(parHelp.getParameter("random","").toString());
String value = parHelp.getParameter("value","").toString();
String id = parHelp.getParameter("id","").toString();
%>
<tr id="<%=Utility.getLocalName(id) %>row">
	<td class="title"><%=separatedClassName %>:</td>
	<td class="value" id="<%=random%>_span">
	<jsp:include page="resourceWithLink.jsp" flush="false">
		<jsp:param value="<%=value %>" name="value"/>
	</jsp:include>
	</td>								
</tr>								