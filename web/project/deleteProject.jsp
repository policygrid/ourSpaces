<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
String projectID = org.policygrid.ontologies.Project.NS.toString() + request.getParameter("projectID"); 
%>
<form id="target" method="post" action="Controller?action=deleteProject" name="delete">
	<h1>Warning! This cannot be undone
	</h1>
	<div>Are you sure you want to delete this project?</div>		
	<input type="hidden" value="<%=projectID %>" name="projectID"></input>
<!-- 	<input type="submit" value="Delete Project"></input> -->
<!-- 	<input class="close_popup" type="reset" value="Cancel"></input> -->
<!-- 	<a class="close_popup" href="#" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" style="padding: 7px">Cancel 2</a>  -->
	
	
<!-- 	<script type="text/javascript"> -->
<!-- 		$( ".close_popup" ).dialog( "option", "buttons", { "Ok": function() { $(this).dialog("close"); } } ); -->
<!-- 	</script> -->
</form>