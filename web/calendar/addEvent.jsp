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
Random r = new Random();
int d = r.nextInt();
String id = request.getParameter("id");
String projectID = org.policygrid.ontologies.Project.NS.toString() + id;
String containerID = project.getProjectContainer(projectID);

%>

<script language="javascript" type="text/javascript">
function stopRKey(evt) {
   var evt = (evt) ? evt : ((event) ? event : null);
   var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
   if ((evt.keyCode == 13) && (node.type=="text")) {return false;}
}

document.onkeypress = stopRKey;
</script>


<p style="color:#999; font-size:24px; margin: 0 auto;" align="center">Create an Event</p>

<div style="position:relative; margin:20px; padding:15px; border:1px solid #FF6600; width: 600px;">
<form method="post" action="AddEvent">


	<div style="position:relative; float:left; width: 600px; margin-bottom:5px;">
        <div style="position:relative; float:left; width:150px;">
            Event title:
        </div>
        <div style="position:relative; float:left;">
           <input type="text" name="title" style="width:300px; height:17px; padding-top:3px; border: 1px solid #036;" />
        </div>
    </div>
    
	<div style="position:relative; float:left; width: 600px; margin-bottom:5px;">
        <div style="position:relative; float:left; width:150px;">
            Location:
        </div>
        <div style="position:relative; float:left;">
           <input type="text" name="location" style="width:300px; height:17px; padding-top:3px; border: 1px solid #036;" />
        </div>
    </div>
    
    <div style="position:relative; float:left; width: 600px; margin-bottom:5px;">
        <div style="position:relative; float:left; width:150px;">
            Event description:
        </div>
        <div style="position:relative; float:left;">
           <textarea name="description" cols="40" rows="5"></textarea>
        </div>
    </div>
    
    <div style="position:relative; float:left; width: 600px; margin-bottom:5px;">
        <div style="position:relative; float:left; width:150px;">
            Start date:
        </div>
        <div style="position:relative; float:left;">
        	<p><span>Day:</span>
           <select name="day1">
           		<%
           		int day = Integer.parseInt(Utility.getDay());           		
           		int month = Integer.parseInt(Utility.getMonth());
           		int year = Integer.parseInt(Utility.getYear());
           		
           		for(int i = 1; i < 32; i++)
           		{
           			int count = i;
           			if(count == day) {
           				%><option value="<%=count %>" selected><%=count %></option><%
           			}
           			else {
           				%><option value="<%=count %>"><%=count %></option><%
           			}
           		} 		
           		%>
           </select>
           
           <span>Month:</span>
           <select name="month1">
           		<%
           		for(int i = 1; i < 13; i++)
           		{
           			int count = i;
           			if(count == month) {
           				%><option value="<%=count %>" selected><%=count %></option><%
           			}
           			else {
           				%><option value="<%=count %>"><%=count %></option><%
           			}
           		} 		
           		%>
           </select>
           
           <span>Year:</span>
           <select name="year1">
           		<%
           		for(int i = 2009; i < 2015; i++)
           		{
           			int count = i;
           			if(count == year) {
           				%><option value="<%=count %>" selected><%=count %></option><%
           			}
           			else {
           				%><option value="<%=count %>"><%=count %></option><%
           			}
           		} 		
           		%>
           </select></p>
           <br />
           <p><span>Hour:</span>
           <select name="hour1">
           		<%
           		for(int i = 1; i < 25; i++)
           		{
           			int count = i;
           			%><option value="<%=count %>"><%=count %></option><%
           		} 		
           		%>
           </select>
           
           <span>Minute:</span>
           <select name="minute1">
				<option value="00">00</option>
				<option value="15">15</option>
				<option value="30">30</option>
				<option value="45">45</option>
           </select></p>
           
        </div>
    </div>
    
    <div style="position:relative; float:left; width: 600px; margin-top:5px; margin-bottom:5px;">
        <div style="position:relative; float:left; width:150px;">
            End date:
        </div>
        <div style="position:relative; float:left;">
        	<p><span>Day:</span>
           <select name="day2">
           		<%
           		
           		for(int i = 1; i < 32; i++)
           		{
           			int count = i;
           			if(count == day) {
           				%><option value="<%=count %>" selected><%=count %></option><%
           			}
           			else {
           				%><option value="<%=count %>"><%=count %></option><%
           			}
           		} 		
           		%>
           </select>
           
           <span>Month:</span>
           <select name="month2">
           		<%
           		for(int i = 1; i < 13; i++)
           		{
           			int count = i;
           			if(count == month) {
           				%><option value="<%=count %>" selected><%=count %></option><%
           			}
           			else {
           				%><option value="<%=count %>"><%=count %></option><%
           			}
           		} 		
           		%>
           </select>
           
           <span>Year:</span>
           <select name="year2">
           		<%
           		for(int i = 2009; i < 2015; i++)
           		{
           			int count = i;
           			if(count == year) {
           				%><option value="<%=count %>" selected><%=count %></option><%
           			}
           			else {
           				%><option value="<%=count %>"><%=count %></option><%
           			}
           		} 		
           		%>
           </select></p>
           <br />
           <p><span>Hour:</span>
           <select name="hour2">
           		<%
           		for(int i = 1; i < 25; i++)
           		{
           			int count = i;
           			%><option value="<%=count %>"><%=count %></option><%
           		} 		
           		%>
           </select>
           
           <span>Minute:</span>
           <select name="minute2">
				<option value="00">00</option>
				<option value="15">15</option>
				<option value="30">30</option>
				<option value="45">45</option>
           </select></p>
           
        </div>
    </div>

	<div style="position:relative; float:left; width: 600px; margin-bottom:5px;">
     
     	<div style="position:relative; float:left; width:150px;">
            Invite Attendees:<br />
        </div>
        
        <div id="members" style="position:relative; width:450px; float:left;">        
            <jsp:include page="../project/addProjectMemberField.jsp" > 
            	<jsp:param name="role" value="false" />
            </jsp:include>
        </div><!-- end of members -->
 	</div>
 	
 	<input type="hidden" value="<%=containerID %>" name="container"></input>
    
    <input type="submit" value="Create Event"></input>



</form>

</div>