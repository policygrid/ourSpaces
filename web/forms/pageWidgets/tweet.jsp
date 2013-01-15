<%@page import="java.net.URLEncoder"%>
<%@ page language="java" import="twitter4j.*,java.util.Iterator, java.util.ArrayList, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <jsp:include page="smallEntity.jsp">
						<jsp:param name="id" value="<%=request.getParameter(\"tweetUrl\")%>"/>
						<jsp:param name="text" value="<%=TwitterBridge.getTwitterStatus(request.getParameter(\"tweetUrl\"))%>"/>
	</jsp:include>