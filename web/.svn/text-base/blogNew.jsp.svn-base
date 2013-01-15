<%@ page language="java" import="java.util.Iterator, java.util.*, common.*, org.policygrid.ontologies.*, java.net.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="blog" class="common.Blog" />
<jsp:useBean id="rdf" class="common.RDFi" />

<jsp:include page="top_head.jsp" />
<!-- menu goes here -->
<jsp:include page="top_tail.jsp" />

<%
if(request.getParameter("id") == null) { %>
 	<jsp:forward page="error.jsp" /> <%
}
int id = Integer.parseInt(request.getParameter("id"));

if( id == 0 ){
	%> <jsp:forward page="error.jsp" /> <%
}

user = (User) session.getAttribute("use");
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));

	
boolean isOwner = false;
if( session.getAttribute("use") == null){
	//
}
else {
	user = (User) session.getAttribute("use");
	if(user.getID() == id){
		isOwner = true;
	}
}

String rdfUserID = user.getUserRDFID(id);
String profileContainer = rdf.getUserProfileContainer(rdfUserID);
ArrayList blogContainer = rdf.getBlogContainer(profileContainer);



String temp = (String) blogContainer.get(0);
String[] fields = temp.split("#");

ArrayList blogPosts = blog.getBlogPosts((String)blogContainer.get(0));

ArrayList aboutList = new ArrayList();

for(int i = 0; i < blogPosts.size(); i++)
{
	BlogBean bb = (BlogBean) blogPosts.get(i);
	String blogID = bb.getRdfBlogID();
	ArrayList more = blog.getAbout(blogID);
	for(int j = 0; j < more.size(); j++)
	{
		String aboutFoafID = (String) more.get(j);
		if(!aboutList.contains(aboutFoafID))
		{
			aboutList.add(aboutFoafID);
		}
	}
}

String namespace = "http://openprovenance.org/ontology#";
String name = user.getName(id);

String output = "blog.jsp?id="+user.getID();
String out2 = URLEncoder.encode(output);

%>


<script>
$(document).ready(function(){
		$("#newp").click(function () { 
			$("#newpost").show();
		});	
		
		
		
		 
		 $('#txt').ourSpacesProvenanceTagging({
			 provID: "prov",
			 discID: "disc"
		 });
		 
		 
		 $("input:reset").click(function() {
			 
            $("#newpost").hide(); 	
			 
			 
		 });
		
	})
	
	
	
	
</script>
  
<style type="text/css">
<!--
.style1 {
	color: #FF6600;
	font-size: 16px;
}
-->
</style>



<div class="homeStatusContainer" style="color:#FFFFFF; position:relative;" >
   
             <div id="columns" style="position:relative;"> 
             
             <ul id="column1" class="column" style="width: 660px;">
             
             		<%
				for(int i = 0; i < blogPosts.size(); i++)
				{
					BlogBean bb = (BlogBean) blogPosts.get(i);
					String content = bb.getContent();
					String title = bb.getTitle();
					String date = bb.getDate();
					String blogRdfID = bb.getRdfBlogID();

					String[] fields2 = blogRdfID.split("#");
					
				%> 
				
                
                   <li class="widget color-white" id="<%=fields2[1] %>">
                        <div class="widget-head">
                            <h3><%=title%></h3>
                        </div>
                        <div class="widget-content" style="padding: 10px;">  
						<a name="<%=i %>"></a>
						<p><%=content%></p>
						<p style="font-size:9px; text-align:right; padding-top:5px; color:#006666;">Posted on: <%=date%></p>
						<p style="font-size:9px; text-align:right; padding-top:5px; color:#FF6600;">
						
						<a href="createcomment.jsp?namespace=<%=URLEncoder.encode(namespace) %>&about=<%=fields2[1] %>" rel="facebox" style="color:#FF6600;">
						<%int count = blog.getCommentCount(namespace+fields2[1]);
								if(count == 0) { %>
		                         	Comment</a>
		                         <% } %>
		                         <%if(count == 1) { %>
		                         	1 Comment</a>
		                         <% } %>
		                         <%if(count > 1) { 		                         	
		                         %>
		                         	<%=count %> Comments</a>
		                         <% } %>
						</p>
						<%if(isOwner == true) { %>
							<form method="post" name="deleteBlogPost<%=i %>" action="Controller?action=deleteBlogPost">
								<input type="hidden" value="<%=blogRdfID %>" name="rdfBlogID"></input>
								<input type="hidden" name="outputURL" value="blog.jsp?id=<%=user.getID() %>"></input>
								<p style="font-size:9px; text-align:right; padding-top:5px; color:#FF6600;"><a href="#" onclick="document.deleteBlogPost<%=i %>.submit()">Delete Post</a></p>
			        		</form>
		        		
        			    <% } %>				
				</div>
				</li>
				
				<% } %>
             
                       
                </ul>
                     
             
             
             
               
             
             
             
             
             
                <ul id="column3" class="column">
                    <li class="widget color-white" id="p4">  
                        <div class="widget-head">
                            <h3>Recent Posts</h3>
                        </div>
                        <div class="widget-content" style="padding: 0px;">
                        <div class="widget-top" >
							<div class="wlink"  align="right" style="float:right;  margin-left: 5px; margin-right: 10px; margin-bottom: 0px; padding: 10px;">
							   <a class="popup_blog_dialog" title="New Blog Post" href="createblogpost.jsp?container=<%=fields[1]%>&output=<%=output %>"><img src="/ourspaces/icons/001_31.png" align="middle" style="float: left; display:inline-block; margin: -2px; border: 0px none;"/><span style="margin-left:8px;">Create New Blog Post</span></a>     
							 </div>
							 </div>
							 <div style="padding: 10px; margin-top: 40px;">
				            	<%
				            	for(int i = 0; i < blogPosts.size(); i++)
								{
				            		BlogBean bb = (BlogBean) blogPosts.get(i);
									String title = bb.getTitle();
									String date = bb.getDate();
									
								%>
				            	
				            	<p style="padding-bottom:5px; padding-left:10px;"><a style=" color:#000;;" href="#<%=i%>"><%=title%> - <span style="font-size:9px; color:#FF6600;"><%=date%></span></a></p>
				            	
				            	<% } %>
				            </div>	
                        </div>
                    </li>
                </ul>
                
            </div> <!-- end of columns -->

            <script type="text/javascript" src="cookie.jquery.js"></script>
            <script type="text/javascript" src="inettuts.jsp?space=blogs"></script>
            
    </div>  <!-- end of home status container -->        

 <jsp:include page="bottom.jsp" />
