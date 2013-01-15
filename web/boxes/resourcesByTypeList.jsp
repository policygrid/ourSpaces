<%@page import="java.util.Collections, common.ParameterHelper, common.Resources, common.RDFi, java.util.Vector"%>
<jsp:useBean id="blog" class="common.Blog" />
<%@page import="java.net.URLEncoder, common.Utility"%>
  <%
common.User user = (common.User) session.getAttribute("use");
int id = user.getID();
RDFi rdf = new RDFi();
//"http://www.policygrid.org/ourspacesVRE.owl#"
String namespace = "http://openprovenance.org/ontology#";
ParameterHelper parHelp = new ParameterHelper(request, session);
String offset = (String)parHelp.getRequestParameter("offset", "0");
String limit = (String)parHelp.getRequestParameter("limit", "6");
String type =  (String)parHelp.getRequestParameter("type", "");
String userOnly =  (String)parHelp.getRequestParameter("userOnly", "true");
Vector<Resources> resources = new Vector<Resources>();
String divId = (String) parHelp.getRequestParameter("divId", "resourcesList");
String [] types;
if(type.contains(",")){
	types = type.split(",");
}
else{
	types = new String[]{type};
}
for(String s : types){
	Vector<Resources> tmp;
	if("true".equals(userOnly)) 
		tmp = rdf.getResources(s, user.getRDFID(), true);
	else
		tmp = rdf.getResources(s, null, true);
	for(Resources r : tmp){
		if(!resources.contains(r))
			resources.add(r);
	}
}
	         			  session.setAttribute("resources", resources);		
	         		  %><jsp:include page="resourcesList.jsp" flush="false">
                             <jsp:param value="<%=limit %>" name="limit"  />
                             <jsp:param value="<%=offset %>" name="offset" />
                             <jsp:param value="<%=divId %>" name="divId"  />
                             <jsp:param value="<%=\"/ourspaces/boxes/resourcesByTypeList.jsp?type=\"+java.net.URLEncoder.encode(type)+\"&userOnly=\"+userOnly+\"&divId=\"+divId %>" name="paging" />
                  </jsp:include><%
								  session.removeAttribute("resources");
	         	%>