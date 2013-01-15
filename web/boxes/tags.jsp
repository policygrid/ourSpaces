<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<jsp:useBean id="tags" class="java.util.Vector" scope="request"/>
<div style="float:left; padding:10px">
<%
			//Vector tags = (Vector) session.getAttribute("tags");
			Iterator y = tags.iterator();
			int count = 0;
			while(y.hasNext()){
				if (count == 30) 
					break;
					Tag tag = (Tag) y.next();
					String tagName = tag.getTag();
					int tagSize = tag.getSize();
					int fontSize = 10;
					if(tagSize < 2) {
						fontSize = 10;	
					}
					else if((tagSize >= 2) && (tagSize < 6)) { 
						fontSize = 12;
					}
					else if((tagSize >= 6) && (tagSize < 10)) { 
						fontSize = 14;
					}
					else if((tagSize >= 10) && (tagSize < 14)) {
						fontSize = 16;
						}
					else if((tagSize >= 14) && (tagSize < 18)) { 
						fontSize = 18;}
					else  { 	
						fontSize = 20;
					}
					%>
					<span style="font-size:<%=fontSize%>px; padding-top:2px; margin-top:2px;"><a class="tag" href="/ourspaces/tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
			<%
					count++;
				
			}
		  %>
		  
</div>