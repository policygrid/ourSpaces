<%@ page language="java" import="java.util.List,java.util.Iterator,java.util.ArrayList,java.io.*,java.net.*,java.util.Vector,common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.Project" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="blog" class="common.Blog" />
<%
	if (request.getParameter("limit") == null) {
%>
	<jsp:forward page="error.jsp" /> <%
 	}
 	int limit = Integer.parseInt(request.getParameter("limit"));
 	if (request.getParameter("offset") == null) {
 %>
	<jsp:forward page="error.jsp" /> <%
 	}
 	int offset = Integer.parseInt(request.getParameter("offset"));
 	if (offset < 0)
 		offset = 0;

 	User user = (User) session.getAttribute("use");
 	if (user.getID() == 0)
 		response.sendRedirect(response.encodeRedirectURL("/error.jsp"));
 	ParameterHelper parHelp = new ParameterHelper(request, session);
 	List allProjects = (List) parHelp.getParameter("comments", new ArrayList());
 %>
<div id="relatedComments">
	<table border="0" cellspacing="5" cellpadding="3" width="100%">
		<%
			// ----------- PAGES MIDDLE
			for (int i = offset; i < allProjects.size() && i < limit + offset; i++) {
				BlogBean comment = (BlogBean) allProjects.get(i);
				%>
				<tr>
					<td class="date ui-corner-all"><%=comment.getDate()%></td>
					<td class="title ui-corner-all"><div><%=comment.getContent()%></div></td>
					<td class="author ui-corner-all"><a
						href="profile.jsp?id=<%=comment.userid%>"><%=user.getName(comment.userid)%></a></td>
				</tr>
				<%
			}
		%>
	</table>



	<%
		if (offset > 0) {
	%>
           <div id="previous" style="float: left; width: 100px;">
           <a id="previousButton" href="#" ><img src="/ourspaces/icons/001_27.png" style="float: left;" border="0"/></a>
           </div>
                 
 <%} %>
<%
	if ((allProjects.size() > 0)
			&& (offset + limit <= allProjects.size())) {
%>
           <div id="next" style="float: right; width: 100px;">
           <a id="nextButton" href="#" style="float: right;"><img src="/ourspaces/icons/001_25.png" align="right" border="0"/></a>
           </div>
                 
<%} %>
</div>
<script>
$(document).ready(function() {
addLoadMoreContentBehaviour(true, $('#nextButton'), "/ourspaces/artifactSpace/commentsAbout.jsp", <%=offset%>, <%=limit%>, <%=limit%>, $("#relatedComments"));
addLoadMoreContentBehaviour(false, $('#previousButton'), "/ourspaces/artifactSpace/commentsAbout.jsp", <%=offset%>, <%=limit%>, <%=limit%>, $("#relatedComments"));
});
</script>