<%@ page language="java" import="java.util.List,java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.Project" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="blog" class="common.Blog" />

<%


ParameterHelper parHelp = new ParameterHelper(request, session);
int limit = Integer.parseInt(parHelp.getParameter("limit", new Integer(5)).toString());
int offset = Integer.parseInt(parHelp.getParameter("offset", new Integer(0)).toString());

if (offset < 0) offset = 0;


User user = (User) session.getAttribute("use");
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));
List allProjects = (List)parHelp.getParameter("blogPosts",  new ArrayList());
%>




<div id="blogPostsInner">

         <table border="0" cellspacing="5" cellpadding="3" width="100%">
        	
          <%
          	// ----------- PAGES MIDDLE
             	
        	for(int i = offset; i < allProjects.size() && i < limit + offset; i++)
        	{
            // ----------- END OF PAGES MIDDLE

				BlogBean post = (BlogBean) allProjects.get(i);
				ArrayList<BlogBean> comments = blog.getComments(post.rdfBlogID);
				String postAuthor = "";
				try{
					postAuthor = user.getName(post.userid);
				}
				catch(Exception e){
					//Skip the blog post.
					continue;
				}
		%>
		<tr>
			<td class="date ui-corner-all"><%=post.getDate()%></td>
			<td class="title ui-corner-all"><div class="posttitle"><h4><%=post.getTitle()%></h4></div>
											<div class="postcontent"><%=post.getContent()%></div></td>
			<td class="author ui-corner-all"><a href="profile.jsp?id=<%=post.userid %>"><%=postAuthor%></a></td>
		</tr>
		<%		
				if(comments.size()>0){
					%><tr><td colspan="3" style="border:0px"><table class="commentsOfPost"><%
					for(BlogBean comment: comments){
							String commentAuthor = "";
							try{
								commentAuthor = user.getName(comment.getUserID());
							}
							catch(Exception e){
								//Skip the comment.
								continue;
							}%>
							<td width="3%" style="border:0px"></td>
							<td width="18%" class="date ui-corner-all"><%=comment.getDate()%></td>
							<td width="50%"class="title ui-corner-all"><div><%=comment.getContent() %></div></td>
							<td class="author ui-corner-all"><a href="profile.jsp?id=<%=comment.getUserID() %>"><%=commentAuthor%></a></td>
						</tr>
				<%	}
					%></table></td></tr><% 
				}
			}
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
if ((allProjects.size() > 0) && (offset + limit <= allProjects.size()) ) {
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
addLoadMoreContentBehaviour(true, $('#nextButton'), "/ourspaces/artifactSpace/blogsAbout.jsp", <%=offset%>, <%=limit%>, <%=limit%>, $("#blogPostsInner"));
addLoadMoreContentBehaviour(false, $('#previousButton'), "/ourspaces/artifactSpace/blogsAbout.jsp", <%=offset%>, <%=limit%>, <%=limit%>, $("#blogPostsInner"));
});
</script>