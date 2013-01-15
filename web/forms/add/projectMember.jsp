<%@ page contentType="text/html; charset=utf-8" language="java" import="common.*, java.util.regex.*, java.sql.*, java.util.Random, java.util.Vector" errorPage="" %>

<jsp:useBean id="ontologyHandler" class="common.OntologyHandler" />
<jsp:useBean id="configuration" class="common.Configuration" />

<%
String sid = request.getParameter("sid");
String random = request.getParameter("random");
%>

<p style="font-size:16px; color: #666; padding-bottom:15px;">Add a new person</p>

<div style="position: relative; float: left; width:350px;">
	<div style="position: relative; float: left; width:100px; padding-bottom:10px;">
		<p>Email:</p>
	</div>
	<div style="position: relative; float: left; width:250px; padding-bottom:10px;">
		<input type="text" style="1px solid #ccc;" name="email" id="<%=sid%>em" />
	</div>
</div>

<div style="position: relative; float: left; width:350px;">
	
	<div style="position: relative; float: left; width:100px; padding-bottom:10px;">
		<p>First name:</p>
	</div>
	<div style="position: relative; float: left; width:250px; padding-bottom:10px;">
		<input type="text" style="1px solid #ccc;" name="firstname" id="<%=sid%>fn" />
	</div>
</div>

<div style="position: relative; float: left; width:350px;">
	<div style="position: relative; float: left; width:100px; padding-bottom:10px;">
		<p>Last name:</p>
	</div>
	<div style="position: relative; float: left; width:250px; padding-bottom:10px;">
		<input type="text" style="1px solid #ccc;" name="lastname" id="<%=sid%>ln" />
	</div>
</div>
	
	<p><a href="#" OnClick="addPerson('<%=sid%>', '<%=random %>');return false;">Add</a></p>

