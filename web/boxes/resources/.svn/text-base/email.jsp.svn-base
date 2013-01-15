<jsp:useBean id="blog" class="common.Blog" />
<%@page import="java.net.URLEncoder, common.Utility"%>
<jsp:useBean id="rdf" class="common.RDFi" />
  <%
common.User user = (common.User) session.getAttribute("use");
int id = user.getID();
String resID = request.getParameter("resource");
				String link = rdf.getResourceURL(resID);
				String encRes = "";
				if(resID != null && !resID.equals(""))
					encRes = URLEncoder.encode(resID);
				String encLink = "";
				if(link != null && !link.equals(""))
					encLink = URLEncoder.encode(link);
		%>
		
		
<form method="get" id="target" action="/ourspaces/emailResource">	
										Recipient: <input name="email" style="margin-right:5px;width:250px" type="text" />					
										<input name="fileID" type="hidden" value="<%=encLink%>" />
										<input name="resource" type="hidden" value="<%=encRes%>" />										
</form>