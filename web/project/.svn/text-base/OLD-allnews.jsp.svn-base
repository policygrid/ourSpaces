 
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



%>

 
 <table border="0" cellspacing="3" cellpadding="1" width="620" style="font-size:11px;">
 
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
        

					<tr>
						<td width="67" style="font-size:10px; color:#666666; font-weight:bold;">
						<%
                        if((day == currDay) && (month == currMonth) && (year == currYear) && (today == false)){
                        %>
                            Today
                        <% 
                            today = true;
                        } 
                        else if((day == currDay) && (month == currMonth) && (year == currYear) && (today == true)){
                        %>
                            
                        <% 
                        }
                        else if((day == currDay -1) && (month == currMonth) && (year == currYear) && (yesterday == false)){
                        %>
                            Yesterday
                        <%
                            yesterday = true;
                         }
                        else if((day == currDay -1) && (month == currMonth) && (year == currYear) && (yesterday == true)){
                        %>
                            
                        <% 
                        } 
                        else {
                            if((day == prevDay) && (month == prevMonth)){ 
                            %>
                                
                            <%
                            }
                            else { 
                            %>
                                <%=day%> / <%=month%>
                            <%
                            }
                        } 
                        prevDay = day;
                        prevMonth = month;
                        %>
                    </td>
				</tr>
				<tr>
					<td style="padding-left:20px;">
						<%
                        if(type.equals("resources")) {
                        %><img src="images/resource.gif" alt="resource"/><%
                        }
                        if(type.equals("project")) {
                        %><img src="images/project.gif" alt="project"/><%
                        }
                        if(type.equals("messages")) {
                        %><img src="images/message.gif" alt="message"/><%
                        }
                        if(type.equals("profile")) {
                        %><img src="images/edit.gif" alt="edit"/><%
                        }
                        if(type.equals("status")) {
                        %><img src="images/resource.gif" alt="status"/><%
                        }
                        if(type.equals("contacts")) {
                        %><img src="images/user.gif" alt="user"/><%
                        } %>
					</td>
                  	<td style="border-bottom:1px solid #C7F0FE;">
				  		<%=message%>
                        <br /><span style="font-size:10px;">
                        <%if(blog.getCommentCount("http://www.policygrid.org/ourspacesVRE.owl#"+actID) == 0) { %>
                         	<a href="#" onclick="javascript:createComment1('<%=actID %>');" style="color:#FF6600;">Comment</a>
                         <% } %>
                         <%if(blog.getCommentCount("http://www.policygrid.org/ourspacesVRE.owl#"+actID) == 1) { %>
                         	<a href="#" onclick="javascript:createComment1('<%=actID %>');"  style="color:#FF6600;">1 Comment</a>
                         <% } %>
                         <%if(blog.getCommentCount("http://www.policygrid.org/OurSpacesVre.owl#"+actID) > 1) { 
                         	int count = blog.getCommentCount("http://www.policygrid.org/ourspacesVRE.owl#"+actID);
                         %>
                         	<a href="#" onclick="javascript:createComment1('<%=actID %>');" style="color:#FF6600;"><%=count %> Comments</a>
                         <% } %>
                        
                        </span>
					</td>
                 </tr>
				 <% } %>
				</table>      
