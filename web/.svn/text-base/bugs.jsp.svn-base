<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.Project" />



<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<%
String projectID = (String)session.getAttribute("projectID");
user = (User)session.getAttribute("use");
%>

<p style="color:#999; font-size:24px; margin: 0 auto;" align="center">ourSpaces Helpdesk</p>

<div style="position:relative; margin:20px; padding:15px; border:1px solid #FF6600; width: 600px;">
<form method="post" action="Controller?action=addBug">

    <div style="position:relative; float:left; width: 600px; margin-bottom:5px;">
        <div style="position:relative; float:left; width:150px;">
           Message Type:
        </div>
        <div style="position:relative; float:left;">
           
      
        <select name="type" id="Select1" size="4" >

		    <option value="feedback">Feedback</option>
		
		    <option value="bug">Bug Report</option>
		
		    <option value="feature">Feature Request</option>
		
		</select>
	  </div>
    </div>

	<div style="position:relative; float:left; width: 600px; margin-bottom:5px;">
        <div style="position:relative; float:left; width:150px;">
            Subject:
        </div>
        <div style="position:relative; float:left;">
           <input type="text" name="subject" style="width:450px; height:17px; padding-top:3px; border: 1px solid #036;" />
        </div>
    </div>
    
	<div style="position:relative; float:left; width: 600px; margin-bottom:5px;">
        <div style="position:relative; float:left; width:150px;">
           Description:
        </div>
        <div style="position:relative; float:left;">
           <textarea name="message" style="width:450px; height:50px; padding-top:3px; border: 1px solid #036;" />
        </div>
    </div>
    
    <input type="submit" value="Send Message"></input>



</form>

</div>