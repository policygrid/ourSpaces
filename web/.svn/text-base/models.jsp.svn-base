<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />

<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="rdf" class="common.RDFi" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<jsp:include page="top_head.jsp" />
<!-- menu goes here -->
<jsp:include page="top_tail.jsp" />

<%

user = (User) session.getAttribute("use");
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));

%>


  <style type="text/css">
<!--
.style3 {
	font-size: 16px;
	color: #006699;
}
.style4 {font-size: 16px;color: #CC0000}
.style5 {
	font-size: 16px;
	color: #FF6600;
	padding-bottom:10px;
}
-->
  </style>
  
 <div style="width:946px; background-color:white; position:relative; float:left; margin-left: 10px; margin-top: 10px; padding:15px">
  
  	<div style="width:650px; position:relative; float:left;"> <!-- left div -->
  	
		<img src="images/spaces_models2_03.jpg" alt="Models Space" />
        
      <p style="padding:30px;"><a href="fearlus.jsp">FEARLUS Model</a></p>
      <p style="padding:30px;"><a href="simulation.jsp">New simulation space</a></p>
        
        
    </div> <!-- end of left -->

  </div>  
  <jsp:include page="bottom.jsp" />
