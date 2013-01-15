<jsp:useBean id="blog" class="common.Blog" />
<%@page import="java.net.URLEncoder, common.Utility"%>
<jsp:useBean id="rdf" class="common.RDFi" />
  <%
common.User user = (common.User) session.getAttribute("use");
int id = user.getID();
String resID = URLEncoder.encode(request.getParameter("resource"));
		%>
<form method="get" id="target" action="/ourspaces/Tagger">	
									Tag: <input name="tag" style="margin-right:5px;" type="text" value=""/>					
										<input name="user" type="hidden" value="<%=id%>" />
										<input name="resource" type="hidden" value="<%=resID%>" />
									<!-- 	<a href="#" onclick="add('')"  style="background: none repeat scroll 0 0 green;display: block;float: none;margin-left: auto;margin-right: auto; width: 120px;" class="fg-button ui-state-active  ui-corner-all">Add tag</a>
									 -->
</form>