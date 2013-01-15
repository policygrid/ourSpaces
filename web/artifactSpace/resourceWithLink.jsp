<%@page import="java.net.URLEncoder"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.util.regex.*, java.sql.*, common.*, java.util.Random, java.util.Vector" errorPage="" %>
<%
ParameterHelper parHelp = new ParameterHelper(request, session);
String value = parHelp.getParameter("value","").toString();
String style = parHelp.getParameter("style","position: relative; float: left; color: #666666;").toString();
%><span style="<%=style%>"><%if(value.startsWith("http")) {
			RDFi rdf = new RDFi();
			String type=rdf.getResourceType(value);
			String href = Utility.getDetailPage(type, value);
			if(href != null && !href.equals("")){
				%><a href="<%=href%>"><%
			}
				%><jsp:include page="../search/inversesearch.jsp">
							<jsp:param value="<%=value %>" name="q"/>
							<jsp:param value="HTML" name="output"/>
							<jsp:param value="1" name="limit"/>
						</jsp:include><%
			if(href != null && !href.equals("")){
				%></a><%
			}
		}
		else {%><%=value %><%
		}%></span>