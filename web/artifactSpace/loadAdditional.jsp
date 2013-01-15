<%@ page contentType="text/html; charset=utf-8" language="java" import="java.util.regex.*, java.sql.*, common.*, java.util.Random, java.util.Vector" errorPage="" %>
<%
ParameterHelper parHelp = new ParameterHelper(request, session);
String divId = (String)parHelp.getParameter("divId","");
String link = (String)parHelp.getParameter("link","");
String linkText = (String)parHelp.getParameter("linkText","Load additional items...");
String itemsCount = (String)parHelp.getParameter("itemsCount","3");
String offset = (String)parHelp.getParameter("offset","0");
%>
		<script>
			function loadAdditionalItems<%=divId%>(){
				var table = document.getElementById("<%=divId%>");									
				jQuery.get("/ourspaces/artifactSpace/<%=link%>?count=<%=itemsCount%>&offset=<%=offset%>", function(data) {
					var oldHTML = table.innerHTML;
					var newHTML = oldHTML + data;
					table.innerHTML = newHTML;
		  		});
      				}
		</script>
		<span style="float: left;text-align: center;width: 100%;"><a href="#" onclick="loadAdditionalItems<%=divId%>();return false;" ><%=linkText %></a></span>
					