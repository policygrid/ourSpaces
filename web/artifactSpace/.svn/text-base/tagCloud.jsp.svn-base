
<%@ page language="java" import="java.util.Iterator,java.util.*,java.net.*,java.text.SimpleDateFormat,java.util.ArrayList,java.io.*,java.net.*,java.util.Vector,common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="tag" class="common.Tag" />
<% 
ParameterHelper parHelp = new ParameterHelper(request, session);
   List tags = (List)parHelp.getParameter("tags", new ArrayList());%>
<div style="padding-left:10px;" class="style3">
         	<%
         		for (int i = 0; i < tags.size(); i++) {

         			tag = (Tag) tags.get(i);
         			String tagName = tag.getTag();
         			int tagSize = tag.getSize();
         			if (tagSize == 1) {
         	%>
                         <span style="font-size:12px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
                    <%
                    	} else if (tagSize == 2) {
                    %>
                         <span style="font-size:15px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
                    <%
                    	} else if (tagSize == 3) {
                    %>
                         <span style="font-size:18px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
                    <%
                    	} else if (tagSize == 4) {
                    %>
                         <span style="font-size:20px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
                    <%
                    	} else if (tagSize == 5) {
                    %>
                         <span style="font-size:23px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
                    <%
                    	} else {
                    %>
                         <span style="font-size:25px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
                    <%
                    	}

                    	}
                    %>
             </div>