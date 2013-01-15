<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" import="java.util.Iterator, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<% 
if((request.getParameter("username") == null) || (request.getParameter("password") == null)) { %>
 	<jsp:forward page="register.jsp" /> <%
}

String username = request.getParameter("username");
String password = request.getParameter("password");
String name = request.getParameter("name");
String email = request.getParameter("email");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>OurSpaces</title>
<link rel="stylesheet" href="styles.css" type="text/css" />

<style type="text/css">
<!--
.style1 {color: #990000}
-->
</style>
</head>

<body>

<div class="containter">
  <img src="images/design1_02.jpg" alt="OurSpaces Banenr" align="absbottom" />
  <div class="loginbox"> 
  	<img src="images/design1_05.jpg" alt="box" class="loginicon1" />
	<div class="logintext">
		<span><a href="index.jsp">Login</a> | <a href="register.jsp">Register</a> | <a href="index.jsp">Search</a></span>	</div>
	<img src="images/design1_05.jpg" alt="box" class="loginicon2" />  </div>
  
  <div class="main">
	
	<div class="register">
		<div class="regintrotext">
			<p class="regtext">Confirm your Registration </p>
			<p>In many cases, users can create profiles on behalf of other users. This is what's occured for you. 
			<p>            
			<p>Please confirm the details below and your account details will be linked with the information already supplied about you. 
		</div>
		<img src="images/logo.jpg" alt="OurSpaces!" />
	</div>
	<div class="regcontent">
		<form method="post" action="Controller?action=otherReg">
	
	 		<p>Your chosen username: <%=username%></p>
			<input type="hidden" value="<%=username%>" name="username" />
			<p>Your chosen password: <%=password%></p>
			<input type="hidden" value="<%=password%>" name="password" />
			<input type="hidden" value="<%=name%>" name="name" />
			<input type="hidden" value="<%=email%>" name="email" />
			<input type="submit" value="Register" id="submitForm" style="background-color:#9AD0E4; border:1px solid #FFFFFF; margin-top:10px;" /> <input type="reset"  value="Clear" style="background-color:#9AD0E4; border:1px solid #FFFFFF; margin-top:10px;" />
	 	</form>
	</div>
  </div><!-- Main -->
	
  <div class="bottombox">
  	<p style="text-align:center; padding-top:5px; padding-bottom:5px;">Richard Reid @ Policy Grid</p>
  </div>
	
</div><!-- Container -->

</body>
</html>


