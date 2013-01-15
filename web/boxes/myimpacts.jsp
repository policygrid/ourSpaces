<%@page import="java.util.Collections, common.ParameterHelper"%>
<jsp:useBean id="blog" class="common.Blog" />
<%@page import="java.net.URLEncoder, common.Utility"%>
  <%
common.User user = (common.User) session.getAttribute("use");
int id = user.getID();
java.util.Vector resources = user.getResourcesByType(id, "http://www.policygrid.org/provenance-impact.owl#Impact");
Collections.reverse(resources);
//"http://www.policygrid.org/ourspacesVRE.owl#"
String namespace = "http://openprovenance.org/ontology#";
ParameterHelper parHelp = new ParameterHelper(request, session);
String offset = (String)parHelp.getRequestParameter("offset", "0");
String limit = (String)parHelp.getRequestParameter("limit", "6");
String divId = (String) parHelp.getRequestParameter("divId", "impactList");
%>  

					<%
	         			  session.setAttribute("resources", resources);		
	         		  %><jsp:include page="resourcesList.jsp" flush="false">
                             <jsp:param value="<%=limit %>" name="limit"  />
                             <jsp:param value="<%=offset %>" name="offset" />
                             <jsp:param value="<%=divId %>" name="divId"  />
                             <jsp:param value="/ourspaces/boxes/myimpacts.jsp" name="paging" />
                  </jsp:include><%
								  session.removeAttribute("resources");
	         	%>