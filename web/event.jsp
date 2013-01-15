
<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="projectBean" class="common.ProjectBean" />

<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="event" class="common.Event" />

<jsp:useBean id="blog" class="common.Blog" />
<jsp:useBean id="project" class="common.Project" />
    
<jsp:include page="top_head.jsp" />
<!-- menu goes here -->
<jsp:include page="top_tail.jsp" />

<% 
User user = (User) session.getAttribute("use");
int eventID = Integer.parseInt(request.getParameter("id"));

ArrayList details = event.getEventDetails(eventID);
ArrayList attendees = event.getEventAttendees(eventID);

Event e = (Event) details.get(0);

String blogContainer = event.getEventBlogContainer(eventID);
ArrayList blogPosts = blog.getBlogPosts(blogContainer);

boolean attendee = event.checkAttendee(eventID, user.getFOAFID());
String projectID = project.getProjectFromContainer(e.getContainer());
String[] fields3 = projectID.split("#");

%>

  
	<div style="position:relative; float:left; width:978px;">
  			<div style="position:relative; float:left; width:788px;">
            	<div class="projectTitle">
                    <span>Event: <%=e.getTitle() %></span>
            	</div>
                <div class="eventInfo">
                	<p>Organiser: <a href="profile.jsp?id=<%=user.getUserIDFromRDFID(e.getOrganiser()) %>"><%=user.getName(user.getUserIDFromRDFID(e.getOrganiser())) %></a></p>
                	<p>Location: <%=e.getLocation() %></p>
                    <p>Start-time:</p>
                    <p>End-time:</p>
                    <p>Event of: <a href="project.jsp?id=<%=fields3[1] %>"><%=project.getTitle(projectID) %></a></p>
                </div>
       		</div>
        	<div style="position:relative; float:left; padding-left:1px; width:175px; color: #666;">
                    <p style="border:1px solid #CCC; padding:3px;">--> <a style="color:#FF6600;" href="#" rel="facebox">Delete This Event</a></p>
                    <div style="border:1px solid #CCC; margin-top:3px; position:relative; padding:10px; float:left; width:153px;">
                    	<%if(attendee == true){ 
                    		String status = event.getStatus(eventID, user.getFOAFID());             	
                    	%>
	                    	<form method="post">
	                    		<input type="hidden" name="eventID" value="<%=eventID %>"></input>
	                    		<input type="hidden" name="foafID" value="<%=user.getFOAFID() %>"></input>
	                    		<%if(status.equals("attending")) { %>
	                        		<p><input type="radio" name="status" value="attending" checked onClick="this.form.action='Controller?action=attendEvent'; this.form.submit()" /> <span>Attending </span></p>
	                        	<% } else { %>
	                        		<p><input type="radio" name="status" value="attending" onClick="this.form.action='Controller?action=attendEvent'; this.form.submit()" /> <span>Attending </span></p>
	                        	<% } %>
	                        	
	                        	<%if(status.equals("maybe")) { %>
	                           		<p><input type="radio" name="status" checked value="maybe" onClick="this.form.action='Controller?action=attendEvent'; this.form.submit()" /> <span>Maybe </span> </p>
	                           	<% } else { %>
	                           		<p><input type="radio" name="status" value="maybe" onClick="this.form.action='Controller?action=attendEvent'; this.form.submit()" /> <span>Maybe </span> </p>
	                           	<% } %>
	                            
	                            <%if(status.equals("not")) { %>
	                            	<p><input type="radio" name="status" checked value="not" onClick="this.form.action='Controller?action=attendEvent'; this.form.submit()" /> <span>Not Attending </span></p>
	                            <% } else { %>
	                            	<p><input type="radio" name="status" value="not" onClick="this.form.action='Controller?action=attendEvent'; this.form.submit()" /> <span>Not Attending </span></p>
	                            <% } %>
	                        </form>
                        <% } else { %>
                        	<p>You cannot join this event.</p>
                        <% } %>
                    </div>
       		</div>
    </div>
    
    <div class="eventLeft">
    	<div class="eventLeftTitle">Event Description</div>
    	<div class="eventLeftContent"><p><%=e.getDescription() %></p></div>
    	
    	<div class="eventLeftTitle">Event Blog</div>
    	<div class="eventLeftContent">
	
			<%
				Iterator iter = blogPosts.iterator();
				while(iter.hasNext()){
					BlogBean bb = (BlogBean) iter.next();
					String content = bb.getContent();
					String title = bb.getTitle();
					String date = bb.getDate();
					
			%>
				<div style="padding:10px; float:left; position:relative; width:940px;">
					<p style="padding-bottom:10px; color:#9AD0E4; font-size:14px;"><%=title%></p>
					<p><%=content%></p>
				  <p style="font-size:9px; text-align:right; padding-top:5px;"><span class="style6"><span class="style6">Posted on:</span> <%=date%></p>
				</div>
			<% } %>  
			
			<form method="post" action="Controller?action=blog">
						<p>Subject:</p>
						<p>
						  <input type="text" style="width:500px; margin-bottom:10px; border:1px solid #ccc;" name="title">
					  	</p>
					  	<p>Content:</p>
						<p><textarea name="content" style="width:500px; height:100px;  border:1px solid #ccc;"></textarea></p>
						<input type="hidden" value="<%=user.getID()%>" name="id" />
						<input type="hidden" value="<%=blogContainer %>" name="blogContainer"></input>
						<input type="hidden" value="event.jsp?id=<%=eventID %>" name="output"></input>
						<p style="padding-top:5px;"><input type="submit" value="Submit" />
						<input type="reset" value="Clear" /></p>
					</form>
		</div>
    	
    </div>
    
    <div class="eventRight">
    	<div class="eventRightTitle">People Attending</div>
    	<div class="eventRightContent">
			
			<%
			for(int i = 0; i < attendees.size(); i++)
			{
				Person p = (Person) attendees.get(i);
				String photo = user.getPhoto(p.getUserID());
				String name = user.getName(p.getUserID());
				%>
				
				<div style="width:72px; position:relative; float:left;">
                	 <% if(!photo.equals("")) { %>
						<img src="<%=photo%>" style="border:1px solid #666666;" width="48" height="50" />
                	 <% } else { %>
                        <img src="/ourspaces/images/no.jpg" align="center" alt="no picture" width="48" height="50" />
                 	<% } %>
	                   <p style="border:0px; padding:0px; margin:0px;font-size:9px;"><a href="profile.jsp?id=<%=contact.getContactID()%>"><%=name%></a></p>
                </div>
				
				<%
			}
			%>
			
		
		</div>

    </div>

    
  	 
<jsp:include page="bottom.jsp" />

