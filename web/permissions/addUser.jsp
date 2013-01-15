<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
String role = (String) request.getParameter("role");
boolean showRole = true;
if(role != null && role.equals("false"))
	showRole = false;

%>
<script language="javascript" type="text/javascript">
addPersonAutocomplete('membersinput');
</script>

            <div id="membersinputDiv" class="resourceInputDiv" style="float: left;width:100%;">
							<input id="membersinput" class="resourceInput" style="padding: 2px;  border: 0px solid #000000;" size="40" type="text" value="">
						</div>
            