<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.Project" />
<jsp:useBean id="rdf" class="common.RDFi" />

<%
if(request.getParameter("limit") == null) { %>
	<jsp:forward page="error.jsp" /> <%
}
int limit = Integer.parseInt(request.getParameter("limit"));

if(request.getParameter("offset") == null) { %>
	<jsp:forward page="error.jsp" /> <%
}
int offset = Integer.parseInt(request.getParameter("offset"));
if (offset < 0) offset = 0;


User user = (User) session.getAttribute("use");
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));

ArrayList allProjects = project.getAllProjectsWithSub(offset,limit);

%>




<div id="projects">

         <table border="0" cellspacing="5" cellpadding="3" width="100%">
        	
          <%
          	// ----------- PAGES MIDDLE
             	
        	for(int i = 0; i < allProjects.size(); i++)
        	{
            // ----------- END OF PAGES MIDDLE
        		
        	    ProjectBean pb = (ProjectBean) allProjects.get(i);
        		ArrayList subProjectsList = pb.getSubprojects();
        		String encRes = URLEncoder.encode(pb.getURI());
        	%>
        	
        	
        	                              <tr>
        	                                    <td rowspan="2" style="width: 20px;">
													<img src="images/spaces/Projects_small.png" align="middle"  style="float: left; margin-top: auto; margin-bottom:auto;" />
											    </td>
												<td>
													<a href="project.jsp?id=<%=pb.getURL()%>"><%=pb.getTitle()%></a>&nbsp;<span class="nlg" rel="<%=encRes%>"></span>
												</td>
											</tr>
											<tr>
												<td wodth="100%">
												  <span style="font-size:10px; color:#666;">
												  	<%=pb.getSubtitle()%>
											  	  </span>
											  	  <% if(subProjectsList != null) { %>
												  	  	<br>
												  	  	<span style="font-size:10px;">Subprojects:</span> 
												  	  		<%for(int j = 0; j < subProjectsList.size(); j++) { 
												  	  			ProjectBean spb = (ProjectBean) subProjectsList.get(j);
												  	  			String subProjectTitle = spb.getTitle();
												  	  			String subURL = spb.getURL();
												  	  			if(j == subProjectsList.size()-1) {
												  	  		%>
																	<span style="font-size:10px; color:#666;">
																  		<a href="project.jsp?id=<%=subURL%>"><%=subProjectTitle %></a> 
															  	  	</span>
														  	  	<% } else { %>
														  	  		<span style="font-size:10px; color:#666;">
																  		<a href="project.jsp?id=<%=subURL%>"><%=subProjectTitle %></a> | 
															  	  	</span>
													  	  	<% 	} 
													  	  	} %>
												  	  <% } %>
												</td>
											</tr>
        	       
   			<% }   

        	%>          


                 		</table>


                		
<%
if (offset > 0) {
%>
           <div id="previous" style="float: left; width: 100px;">
           <a id="previousButton" href="#" ><img src="/ourspaces/icons/001_27.png" style="float: left;" border="0"/></a>
           </div>
                 
                 <%
                 }
%>



<%
if ((allProjects.size() > 0) && (limit <= allProjects.size()) ) {
%>
           <div id="next" style="float: right; width: 100px;">
           <a id="nextButton" href="#" style="float: right;"><img src="/ourspaces/icons/001_25.png" align="right" border="0"/></a>
           </div>
                 
                 <%
                 }
%>
</div>





<script>
$(document).ready(function() {
addLoadMoreContentBehaviour(true, $('#nextButton'), "/ourspaces/project/projectsList.jsp", <%=offset%>, <%=limit%>, <%=allProjects.size()%>, $("#projects"));
addLoadMoreContentBehaviour(false, $('#previousButton'), "/ourspaces/project/projectsList.jsp", <%=offset%>, <%=limit%>, <%=allProjects.size()%>, $("#projects"));
});
</script>



                 
                  
              