<%@page import="java.net.URLEncoder"%>
<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <jsp:useBean id="blog" class="common.Blog" />

  <%
  String id = (String) request.getParameter("id");
  String text = (String) request.getParameter("text");
  String style = (String) request.getParameter("style");
  if(style==null)
	  style="";
  if(text == null || "".equals(text)){%>
	  <span class="value" id="<%=id%>"><jsp:include page="../../search/inversesearch.jsp" flush="false">
		<jsp:param value="<%=id %>" name="q"/>
		<jsp:param value="HTML" name="output"/>
		<jsp:param value="1" name="limit"/>
	</jsp:include><a class="remove" href="#" title="Remove">x</a></span><%
  }
  else{
	  %><span class="value" style="<%=style %>" id="<%=id%>"><%=text %><a class="remove" href="#" title="Remove">x</a></span><%
  }
%>