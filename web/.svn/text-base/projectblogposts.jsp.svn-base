<%@ page language="java" import="java.util.Iterator, java.util.*, common.*,java.net.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.Project" />
<jsp:useBean id="blog" class="common.Blog" />
<jsp:useBean id="rdf" class="common.RDFi" />


<jsp:include page="top_head.jsp" />


<jsp:include page="top_tail.jsp" />

<%
if(request.getParameter("id") == null) { %>
 	<jsp:forward page="error.jsp" /> <%
}
String temp = request.getParameter("id");

if( temp == null ){
	%> <jsp:forward page="/error.jsp" /> <%
}

user = (User) session.getAttribute("use");
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));

if( session.getAttribute("use") == null){
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));
}

String blogContainer = "http://www.policygrid.org/ourspacesVRE.owl#"+temp;

ArrayList blogPosts = blog.getBlogPosts(blogContainer);

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

String title2 = project.getProjectBlogContainerTitle(blogContainer);

String output = "projectblogposts.jsp?id="+temp;
String out2 = URLEncoder.encode(output);

common.Utility.log.debug("output: "+out2);
common.Utility.log.debug("container: "+blogContainer);

%>

<script>	
	
</script>
  
<style type="text/css">
<!--
.style1 {
	color: #FF6600;
	font-size: 16px;
}
-->
</style>

 <div style="width:976px; background-color:white; position:relative; float:left; margin-left: 10px; margin-top: 10px;">
<div style="position:relative; float:left; width:940px; margin-top:10px;">
  			<div style="position:relative; float:left; width:600px;">
            	<div class="projectTitle">
                    <span><%=title2%></span>
            	</div>
       		</div>
      </div>




	<div class="blogContainer">
    <div class="wlink"  align="right" style="float:left;  margin-left: 5px; margin-right: 10px; margin-bottom: 0px; padding: 10px;">
         <a class="popup_blog_dialog fg-button ui-state-active fg-button-icon-right ui-corner-all" style="float: left; margin-left: 15px;" title="New Blog Post" href="createblogpost.jsp?output=<%=out2 %>&container=<%=temp %>&output=<%=out2 %>"><img src="/ourspaces/icons/001_31.png" align="middle" style="float: left; display:inline-block; margin: -2px; border: 0px none;"/><span style="margin-left:8px;">Create New Blog Post</span></a>     
      </div>
  <!--<a href="#" id="newp" class="fg-button ui-state-active fg-button-icon-right ui-corner-all" style="float: left; margin-left: 15px;" >New Blog Post</a>     	
      -->  
        <div class="blogContentContainer">
        	<div class="blogContentLeft">
        	<!--
        	<div id="newpost" style="display: none; margin:10px;">
              		  <form method="post" action="Controller?action=blog">
						<p>Subject:</p>
						<p>
							<input type="text" class="ui-widget ui-corner-all" style="width:610px; margin-bottom:5px; margin-top:5px; height: 22px; border:1px solid;" name="title">
						</p>
				
						<p>Post:</p>						
						<div style="width: 610px">
						   <div class="ui-widget ui-widget-content ui-corner-all" style="margin-top: 3px; padding: 3px;"> 
						     <textarea id="txt" name="content" rows="10"  cols="80" style="width: 600px; border:none; background-color: transparent;"></textarea>
						   </div>
						   <div id="prov">	
						   </div>
						   <div id="disc">
						   </div>
								
						</div>
																
						<input type="hidden" value="<%=user.getID()%>" name="id" />
						<input type="hidden" value="<%=temp %>" name="blogContainer"></input>
						<input type="hidden" value="<%=out2 %>" name="output"></input>
				   
				   		<p style="padding-top:5px;">
							<input type="submit" value="Submit" onclick="ste.submit();" />
							<input type="reset" value="Clear" />
						</p>
				           
				      </form>
             </div>-->
        	
        	
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
				<div style="position:relative; float:left; border:1px solid #066; padding: 10px; margin-bottom: 10px; width:610px;" class="ui-corner-all">
				<a name="<%=i %>"></a>
        		<p style="padding-bottom:10px; color:#036; font-size:18px;"><%=title%></p>
				<p><%=content%></p>
				<p style="font-size:9px; text-align:right; padding-top:5px; color:#006666;">Posted on: <%=date%></p>
				<p style="font-size:9px; text-align:right; padding-top:5px; color:#FF6600;"><a href="createcomment.jsp?about=<%=fields2[1] %>" rel="facebox">Comment</a></p>
				</div>
				<% } %>
        		
        	</div>
            <div class="blogContentRight" class="ui-corner-all">
            
            	<p style="font-size:16px; color: #FFF; padding-bottom:10px;">Recent Posts</p>
            	<%
            	for(int i = 0; i < blogPosts.size(); i++)
				{
            		BlogBean bb = (BlogBean) blogPosts.get(i);
					String title = bb.getTitle();
					String date = bb.getDate();
					
				%>
            	
            	<p style="padding-bottom:5px; padding-left:10px;"><a style=" color:#ccc;;" href="#<%=i%>"><%=title%> - <span style="font-size:9px; color:#FF6600;"><%=date%></span></a></p>
            	
            	<% } %>
            	
            	
            </div>
        </div>
    
    </div>
</div>
 
 <jsp:include page="/bottom.jsp" />
