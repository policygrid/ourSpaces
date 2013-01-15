<%@ page language="java" import="common.ParameterHelper,java.util.List,java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.Project" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="blog" class="common.Blog" />

<%
ParameterHelper parHelp = new ParameterHelper(request, session);
if(request.getParameter("limit") == null) { %>
	<jsp:forward page="error.jsp" /> <%
}
User user = (User) session.getAttribute("use");
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));

if(request.getParameter("offset") == null) { %>
	<jsp:forward page="error.jsp" /> <%
}
Integer offset = Integer.parseInt(parHelp.getRequestParameter("offset", new Integer(0)).toString());
if (offset < 0) 
	offset = 0;

Integer limit = Integer.parseInt(parHelp.getRequestParameter("limit", new Integer(5)).toString());
if (limit < 0) 
	limit = 5;
String divId = (String) parHelp.getRequestParameter("divId", "resourcesList");


String linkTo = request.getParameter("link");
String onClick = request.getParameter("onClick");
String paging = request.getParameter("paging");

List<common.Resources> resourcesList = (List<common.Resources>) parHelp.getParameter("resources", new ArrayList());
%>

<div id="<%=divId%>">
	<table border="0" cellspacing="5" cellpadding="3" width="100%">
          <%
          	// ----------- PAGES MIDDLE
        	for(int i = offset; i < resourcesList.size() && i < offset + limit; i++)
        	{
				            // ----------- END OF PAGES MIDDLE
				
								common.Resources res = (common.Resources) resourcesList.get(i);
							/*	if(res.getTitle() == null){
									max++;
									continue;					
								}*/
								//Display one resource
								%><jsp:include page="oneresource.jsp" flush="false">
									<jsp:param name="resource" value="<%=res.getId() %>"/>
									<jsp:param name="i" value="<%=divId + i %>"/>
									<jsp:param name="link" value="<%=linkTo %>"/>
									<jsp:param name="onClick" value="<%=onClick %>"/>
								 </jsp:include><%
        	}
				%>
	</table>  		
<%
  if (offset > 0) {%>
           <div id="previous" style="float: left; width: 100px;">
           <a id="<%=divId%>previousButton" href="#" ><img src="/ourspaces/icons/001_27.png" style="float: left;" border="0"/></a>
           </div>                 
                 <%
                 }
	if ((resourcesList.size() > 0) && (offset + limit <= resourcesList.size()) ) {%>
	           <div id="next" style="float: right; width: 100px;">
	           <a id="<%=divId%>nextButton" href="#" style="float: right;"><img src="/ourspaces/icons/001_25.png" align="right" border="0"/></a>
	           </div><%
	}
%>
</div>
<script>
//$(document).ready(function() {
addLoadMoreContentBehaviour(true, $('#<%=divId%>nextButton'), "<%=paging%>", <%=offset%>, <%=limit%>, <%=limit%>, $("#<%=divId%>"));
addLoadMoreContentBehaviour(false, $('#<%=divId%>previousButton'), "<%=paging%>", <%=offset%>, <%=limit%>, <%=limit%>, $("#<%=divId%>"));
initClasses();
//});
</script>