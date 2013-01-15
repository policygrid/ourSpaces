<jsp:useBean id="blog" class="common.Blog" />
<%@ page language="java" import="java.util.Iterator,java.util.*,java.net.*,java.text.SimpleDateFormat,java.util.ArrayList,java.io.*,java.net.*,java.util.Vector,common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<% 
ParameterHelper parHelp = new ParameterHelper(request, session);
common.User user = (common.User) session.getAttribute("use");
int id = user.getID();
/**  How many items to show in the resource box.*/
Integer count = Integer.parseInt(parHelp.getParameter("count", 3).toString());
Integer offset = Integer.parseInt(parHelp.getParameter("offset", 0).toString());

/** Resources to be displayed */
List blogPosts = (List) parHelp.getParameter("blogPosts", new ArrayList());
		for (int i = offset; i + count < blogPosts.size() && i < count; i++) {
			BlogBean post = (BlogBean) blogPosts.get(i+count);
			ArrayList<BlogBean> comments = blog.getComments(post.rdfBlogID);
			%>
			<tr>
				<td class="date ui-corner-all"><%=post.getDate()%></td>
				<td class="title ui-corner-all"><div class="posttitle"><h4><%=post.getTitle()%></h4></div>
												<div class="postcontent"><%=post.getContent()%></div></td>
				<td class="author ui-corner-all"><a href="profile.jsp?id=<%=post.userid %>"><%=user.getName(post.userid)%></a></td>
			</tr>
			<%		
			if(comments.size()>0){
				%><tr><td colspan="3" style="border:0px"><table class="commentsOfPost"><%
				for(BlogBean comment: comments){%>					
						<td width="3%" style="border:0px"></td>
						<td width="18%" class="date ui-corner-all"><%=comment.getDate()%></td>
						<td width="50%"class="title ui-corner-all"><div><%=comment.getContent() %></div></td>
						<td class="author ui-corner-all"><a href="profile.jsp?id=<%=comment.getUserID() %>"><%=user.getName(comment.getUserID()) %></a></td>
					</tr>
			<%	}
				%></table></td></tr><% 
			}
			session.setAttribute("commentsCount", 1 + i + count);
		} %>