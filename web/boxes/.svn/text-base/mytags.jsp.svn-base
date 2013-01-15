<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="projects" class="common.Project" />


<div style="float:left; padding:10px">
<%
			User user = (User) session.getAttribute("use");
			if (user.getID() == 0)
				response.sendRedirect(response.encodeRedirectURL("/error.jsp"));
			Vector tags = user.getTags(user.getID());
			Iterator y = tags.iterator();
			int count = 0;
			while(y.hasNext()){
				if (count == 30) 
					break;
					tag = (Tag) y.next();
					String tagName = tag.getTag();
					int tagSize = tag.getSize();
					
					if(tagSize < 2) { %>
						<span style="font-size:10px; padding-top:2px; margin-top:2px;"><a class="tag" href="/ourspaces/tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
				<%	}
					else if((tagSize >= 2) && (tagSize < 6)) { %>
						<span style="font-size:12px; padding-top:2px; margin-top:2px;"><a class="tag" href="/ourspaces/tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
				<%	}
					else if((tagSize >= 6) && (tagSize < 10)) { %>
						<span style="font-size:14px; padding-top:2px; margin-top:2px;"><a class="tag" href="/ourspaces/tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
				<%	}
					else if((tagSize >= 10) && (tagSize < 14)) { %>
						<span style="font-size:16px; padding-top:2px; margin-top:2px;"><a class="tag" href="/ourspaces/tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
				<%	}
					else if((tagSize >= 14) && (tagSize < 18)) { %>
						<span style="font-size:18px; padding-top:2px; margin-top:2px;"><a class="tag" href="/ourspaces/tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
				<%	}
					else  { %>
						<span style="font-size:20px; padding-top:2px; margin-top:2px;"><a class="tag" href="/ourspaces/tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
				<%	}
					count++;
				
			}
		  %>
		  
</div>