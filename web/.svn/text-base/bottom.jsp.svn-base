<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*, org.policygrid.ontologies.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="rdf" class="common.RDFi" />


<%

user = (User) session.getAttribute("use");
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));

%>




    </div> <!-- end of content -->
    
    <span style="margin-top:5px; overflow:hidden; width:1000px;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
    
   

</div> <!-- end of container -->

<div class="footermain" >
 <div class=container" style="width: 1000px; margin-left: auto; margin-right: auto;">
     
				<div class="footercolumn"> 
				<h3>Latest Resources</h3> 
			   <%
			   Vector resources = rdf.getLatestObjects(7);
					
					
			   for( int j = resources.size() - 1; j >=0; j--)
				{
						resource = (Resources) resources.get(j);
						String resourceID = resource.getID();
						String[] fields2 = resourceID.split("#");
						String resTitle = resource.getTitle();
						String resDate = resource.getDate();
						String owner = rdf.getResourceDepositor(resourceID);
						ArrayList authors = rdf.getResourceAuthor(resourceID);
						String types = rdf.getResourceType(resourceID);
						String[] type = types.split("#");
						
				%>
				<div style="position:relative; float:left; width:300px; margin-top:6px;">
			 	 <a style="color:white;" href="/ourspaces/artifact_new.jsp?id=<%=fields2[1]%>"><%=resTitle%></a>
				</div>
				<%} %>
				
				</div> 
				<div class="footercolumn"> 
				<h3>Spaces</h3> 
				<div style="position:relative; float:left; width:150px; margin-top:0px;">
				<a href="/ourspaces/projects.jsp" style="color:white;"><img src="/ourspaces/images/spaces/Projects.png" border="0" style="position:relative; float:left; margin-left:12px; margin-right:5px;" />Projects</a>
				</div>
                <div style="position:relative; float:left; width:150px; margin-top:0px;">
                <a href="/ourspaces/models.jsp" style="color:white;"><img src="/ourspaces/images/spaces/Models.png" border="0" style="position:relative; float:left; margin-left:12px; margin-right:5px;" />Models</a>
                </div>
                <div style="position:relative; float:left; width:150px; margin-top:6px;">
                <a href="/ourspaces/documents.jsp" style="color:white;"><img src="/ourspaces/images/spaces/Documents.png" border="0" style="position:relative; float:left; margin-left:12px; margin-right:5px;" />Docs</a>
                </div>
                <div style="position:relative; float:left; width:150px; margin-top:6px;">
                <a href="/ourspaces/data/" style="color:white;"><img src="/ourspaces/images/spaces/Data.png" border="0" style="position:relative; float:left; margin-left:12px; margin-right:5px;" />Data</a>
                </div>
                <div style="position:relative; float:left; width:150px; margin-top:6px;">
                <a href="/ourspaces/maps.jsp" style="color:white;"><img src="/ourspaces/images/spaces/Maps.png" border="0" style="position:relative; float:left; margin-left:12px; margin-right:5px;" />Maps</a>
                </div>
                <div style="position:relative; float:left; width:150px; margin-top:6px;">
                <a href="/ourspaces/blog.jsp?id=<%=user.getID() %>" style="color:white;"><img src="/ourspaces/images/spaces/Blogs.png" border="0" style="position:relative; float:left; margin-left:12px; margin-right:5px;" />Blogs</a>
                </div>
				</div> 
				<div class="footercolumn"> 
				<h3>Helpdesk</h3> 
				<!-- Controller?action=addBug -->
						<form method="post" action="">
												    					
							<div style="position:relative; float:left; width: 300px; margin-bottom:5px;">
						        <div style="position:relative; float:left; width:150px; margin-bottom:2px;">
						            Subject:
						        </div>
						        <div style="position:relative; float:left;">
						           <input type="text" name="subject" id="subject" style="width:300px; height:17px; padding-top:3px; border: 1px solid #036;" />
						        </div>
						    </div>
						    
							<div style="position:relative; float:left; width: 300px; margin-bottom:5px;">
						        <div style="position:relative; float:left; width:150px; margin-bottom:2px;">
						           Description:
						        </div>
						        <div style="position:relative; float:left;">
						           <textarea name="message" id="message" style="width:300px; height:50px; padding-top:3px; border: 1px solid #036;"></textarea>
						        </div>
						    </div>
						    
						    <input type="button" id="bugSubmit" onclick="myBugSubmit();" value="Send Message"></input>
						
						
						
						</form>
				</div> 
				<div class="clear"></div> 
				<div class="footerlinks">
				 <a href="myhome.jsp">HOME</a> <a href="edit.jsp">EDIT</a> <a href="myresources.jsp">RESOURCES</a> <a href="mymessages.jsp">MESSAGES</a> <a href="mycontacts.jsp">CONTACTS</a> <a href="logout.jsp">LOGOUT</a>
				</div>		
				<div class="policygrid">
				 ourSpaces Virtual Research Environment. Developed by <a href="http://www.policygrid.org">PolicyGrid</a>
				</div>
				
				  
	    
	    
 </div>
 

</div>



</div> <!--  end of main container for crappy chat -->






</body>
</html>
