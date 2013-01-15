<%@ page language="java" import="java.util.*, java.io.*, java.net.*, java.util.Vector, common.*, com.hp.hpl.jena.*, com.hp.hpl.jena.rdf.model.ModelFactory" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
common.User usr= (User) session.getAttribute("use");
%>


			<form style="padding:5px" method="post" action="Controller?action=editprofile">
				<p style="padding-left:10px; padding:3px; font-size:11px">
				  <input type="checkbox" <%=Utility.booleanToCheckbox(usr.checkNotification("notify_new_message",usr.getID()))%> value="Y" name="notify_new_message">&nbsp;&nbsp;Email me when a new message is received.
				<p style="padding-left:10px; padding:3px; font-size:11px">
				  <input type="checkbox" <%=Utility.booleanToCheckbox(usr.checkNotification("notify_new_meeting",usr.getID()))%> value="Y" name="notify_new_meeting">&nbsp;&nbsp;Email me when I am invited to a meeting.
				<p style="padding-left:10px; padding:3px; font-size:11px">
				  <input type="checkbox" <%=Utility.booleanToCheckbox(usr.checkNotification("notify_new_tag",usr.getID()))%> value="Y" name="notify_new_tag">&nbsp;&nbsp;Email me when one of my resources is tagged.
				<p style="padding-left:10px; padding:3px; font-size:11px; border-top:1px solid #666">
				  <input type="checkbox" <%=Utility.booleanToCheckbox(usr.checkNotification("daily_digest",usr.getID()))%> value="Y" name="daily_digest">&nbsp;&nbsp;Send daily digests (list of daily activities of your network).
				
				
			  <p style="padding-left:5px"><input type="submit" value="Update" class="fg-button ui-state-active fg-button-icon-right ui-corner-all" style="margin-top:10px; padding-left:10px" /></p>
			  
			</form>
