
<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="user" class="common.User" />

<%
user = (User) session.getAttribute("use");

//Logging
// Logs.addLog(user.getID(), "logout", "logout", "");

user.pingUserOut(user.getID());

session.invalidate();
%>

<jsp:forward page="myhome.jsp" />