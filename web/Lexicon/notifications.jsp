<%@ page language="java" import="java.util.*, java.io.*, java.net.*, java.util.Vector, common.*, com.hp.hpl.jena.*, com.hp.hpl.jena.rdf.model.ModelFactory" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<%
common.User usr= (User) session.getAttribute("use");
%>

			<form style="padding:5px" method="post" action="/ourspaces/Controller?action=editplssettings">
				<p style="padding-left:10px; padding:3px; font-size:11px">
				  <input type="checkbox" <%=Utility.booleanToCheckbox(usr.checkNotification("opt_in_pls",usr.getID()))%> value="Y" name="opt_in_pls">&nbsp;&nbsp;Exclude my documents from PLS Service.
					
				
			  <p style="padding-left:5px"><input type="submit" value="Update" class="fg-button ui-state-active fg-button-icon-right ui-corner-all" style="margin-top:10px; padding-left:10px" /></p>
			  
			</form>
