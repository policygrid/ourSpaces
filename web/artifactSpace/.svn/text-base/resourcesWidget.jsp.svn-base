<%@ page language="java" import="java.util.List,java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.Project" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="blog" class="common.Blog" />

<%
User user = (User) session.getAttribute("use");
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));
String offset = request.getParameter("offset");
String limit = request.getParameter("limit");
common.ArtifactBean artifact = (common.ArtifactBean)session.getAttribute("artifact");
session.setAttribute("resources", artifact.getRelatedByTags());			
%>         			  				
	         		  <jsp:include page="../boxes/resourcesList.jsp" flush="false">
                             <jsp:param value="<%=limit %>" name="limit"  />
                             <jsp:param value="<%=offset %>" name="offset" />
                             <jsp:param value="relatedResourcesInner" name="divId" />
                             <jsp:param value="/ourspaces/artifactSpace/resourcesWidget.jsp" name="paging" />
                  </jsp:include><%
session.removeAttribute("resources");%>