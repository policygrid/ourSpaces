  <%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="login" class="common.Login" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="projectrdf" class="common.ProjectRDF" />
<jsp:useBean id="projectblog" class="common.ProjectBlogBean" />
<jsp:useBean id="project" class="common.Project" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

		<script type="text/javascript" src="/ourspaces/javascript/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="/ourspaces/javascript/jquery-ui-1.8.16.custom.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>test</title>
</head>
<body>

test
<div id=nlgDiv></div>
<script type="text/javascript">
	$.ajax({
		type: 'GET',
		url: "/ourspaces/LiberRestServlet?resourceID=http%3A%2F%2Fwww.policygrid.org%2FourspacesVRE.owl%2344dec455-620c-4f77-a5c1-69cf0838997c", 
		dataType : "html",
		async : false,
		success : function(html, errorThrown) {
			$("div#nlgDiv").html(html);
		}
	});
</script>
</body>
</html>