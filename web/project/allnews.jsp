 
 <%@page import="java.net.URLEncoder"%>
<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="project" class="common.Project" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="blog" class="common.Blog" />
<jsp:useBean id="activity" class="common.Activities" />

<% 
if(request.getParameter("id") == null) { %>
 	<jsp:forward page="error.jsp" /> <%
}
String temp = (String) request.getParameter("id").toString();
if( temp == null ){
	%> <jsp:forward page="error.jsp" /> <%
}

String projectID = org.policygrid.ontologies.Project.NS.toString() + temp;
String namespace = "http://openprovenance.org/ontology#";

%>

 
 <table border="0" cellspacing="5" cellpadding="3" width="600" style="font-size:11px; margin:3px; padding:2px;">
                	<%
						Vector activities = activity.getSpecificProjectsActivities(projectID, 1);
			
						Iterator it = activities.iterator();
						boolean today = false;
						boolean yesterday = false;
						int prevDay = 0;
						int prevMonth = 0;
						int  counter = 0;
						while(it.hasNext() && (counter < 10)){
							counter++;				
							Activities act = (Activities) it.next();
							String message = act.getMessage();
							String date = act.getDate();
							String type = act.getType();
							int actID = act.getActID();
							
							String[] fields2 = date.split(" / ");
							int day = Integer.parseInt(fields2[0]);
							int month = Integer.parseInt(fields2[1]);
							if (month == 0)
								month = 1;
							int year = Integer.parseInt(fields2[2]);
							
							java.util.Calendar c = java.util.Calendar.getInstance();
							int currDay = c.get(java.util.Calendar.DATE);
							int currMonth = c.get(java.util.Calendar.MONTH);
							int currYear = c.get(java.util.Calendar.YEAR);	
					%>
        
	 <tr width="100%" >
                  	<td height="1"; colspan="3">
                  	<%
                        if(type.equals("resources")) {
                        %><img src="images/spaces/Projects_small.png" align="middle"  style="float: left; margin-top: auto; margin-bottom:auto;" /><%
                        }
                        if(type.equals("project")) {
                        %><img src="icons/001_34.png" align="middle" style="float: left; margin-top: auto; margin-bottom:auto;" /><%
                        }
						if(type.equals("blog")) {
                        %><img src="icons/001_50.png" align="middle" style="float: left; margin-top: auto; margin-bottom:auto;" /><%
                        }
                        if(type.equals("messages")) {
                        %><img src="icons/001_13.png" align="middle" style="float: left; margin-top: auto; margin-bottom:auto;" /><%
                        }
                        if(type.equals("profile")) {
                        %><img src="icons/001_56.png" align="middle" style="float: left; margin-top: auto; margin-bottom:auto;" /><%
                        }
                        if(type.equals("status")) {
                        %><img src="icons/001_45.png" align="middle" style="float: left; margin-top: auto; margin-bottom:auto;" /><%
                        }
                        if(type.equals("contacts")) {
                        %><img src="icons/001_57.png" align="middle" style="float: left; margin-top: auto; margin-bottom:auto;" /><%
                        } %>
                        <div style="float: left; width: 500px;">
				  		<%=message%>
				  		</div>
				    </td>
                 </tr>
                 
                 
                 <tr style="margin-top:2px;">
                   <td width="40px"></td>
                 	<td >
                         <span style="font-size:9px;">
                         <a href="/ourspaces/createcomment.jsp?namespace=<%=URLEncoder.encode(namespace) %>&about=<%=actID %>" class="popup_comment_dialog" title="Comment" style="color:#FF6600;">
							 <%if(blog.getCommentCount(namespace+actID) == 0) { %>
                         		Comment
                        	 <% } %>
                        	 <%if(blog.getCommentCount(namespace+actID) == 1) { %>
                         		1 Comment
                         	 <% } %>
                        	 <%if(blog.getCommentCount(namespace+actID) > 1) { 
                         		int count = blog.getCommentCount(namespace+actID);
                        	 %>
                         		<%=count %> Comments
                         	 <% } %>
                        	 </a>
                         </span><br />

					</td>
					
					<td align="right" width="150px">
					  <span style="font-size:9px; color:#FF6600;">
						<%
                        if((day == currDay) && (month == currMonth) && (year == currYear) ){
                        %>
                            Today
                        <% 
                        } 
                        else if((day == currDay -1) && (month == currMonth) && (year == currYear)){
                        %>
                            Yesterday
                        <%
                            yesterday = true;
                         }
                        else {
                            %>
                                <%=day%> / <%=month%> / <%=year %>
                            <%
                             } 
                        prevDay = day;
                        prevMonth = month;
                        %>
                        </span>
                    </td>
				</tr>
				 <% } %>
				</table>      
