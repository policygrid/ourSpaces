<%@ page language="java" import="java.util.Iterator, java.util.Collection, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="projectBean" class="common.ProjectBean" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="projectblog" class="common.ProjectBlogBean" />
<jsp:useBean id="project" class="common.Project" />
    
<jsp:include page="top_head.jsp" />
<!-- menu goes here -->

      <div class="navBarLeft">
           <div class="dropdownBox">
              <a class="navBarLeft" href="#" style="float: left;"><img src="/ourspaces/icons/001_34.png" align="left" border="0"/> Tag Search </a> 
              <img src="/ourspaces/icons/dropdown.png" align="right" border="0"/>
              <div class="navBarOptions ui-corner-all" style="font-size: 16px; width:220px;">
               <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
		         <span style="font-weight: 900;"> Your Tags:</span>
		         </div>
		         <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px; border-bottom: 1px solid #FF6600"></div> 
		         
		           <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
						<jsp:include page="mytags.jsp"></jsp:include>
		           </div>
		           
		      </div>
           </div>       
       </div>


<jsp:include page="top_tail.jsp" />
<% 
if(request.getParameter("tag") == null) { %>
 	<jsp:forward page="error.jsp" /> <%
}
String tagWord = (String) request.getParameter("tag").toString();
if( tag == null ){
	%> <jsp:forward page="error.jsp" /> <%
}

if(session.getAttribute("use") != null){
	user = (User) session.getAttribute("use");

}

Vector info = tag.getTagInfo(tagWord);
Iterator iter;

%>

<div class="homeStatusContainer" style="color:black; position:relative;" >

	<div id="columns" style="position: relative;">

		<ul id="column1" class="column" style="width: 1000px;">
		

		
		<%
		iter = info.iterator();
		String temp = "";
		%>
			<li class="widget color-orange" id="tag1">
				<div class="widget-head">
					<h3  class="style3">
						<span style="font-weight:bold; color:#FF6600;"><%=tagWord%></span> has been used to describe the following <strong>resources</strong>
					</h3>
				</div>
				<div class="widget-content" style="padding: 5px;">
					<div id="map" class="widget-content" style="border: 0px solid black;">
					<%
				    ArrayList<String> resourcesAdded = new ArrayList<String>(); 
					while(iter.hasNext()){
						tag = (Tag) iter.next();
						if((tag.getResource() != null) && (!tag.getResource().equals(""))) {
							String resourceID = tag.getResource();
							String resTitle = rdf.getResourceTitle(resourceID);
							String[] fields = resourceID.split("#");
							if(!resourceID.equals(temp) && Utility.isNotNull(resTitle) && !resourcesAdded.contains(resourceID)){
					%>
				   				<p style="padding:5px; padding-left:20px;"><a style="font-size:12px;" href="/ourspaces/artifact_new.jsp?id=<%=fields[1]%>"><%=resTitle%></a></p>
					<%		 
								resourcesAdded.add(resourceID);
							}
							temp = resourceID;
						} 
					} %>
					</div>
				</div>
			</li>
			
			
				
				
			<li class="widget color-orange" id="tag2">
				<div class="widget-head">
					<h3  class="style3">
						<span style="font-weight:bold; color:#FF6600;"><%=tagWord%></span> has been featured in the following <strong>projects</strong>
					</h3>
				</div>
				<div class="widget-content" style="padding: 5px;">
					<div id="map" class="widget-content" style="border: 0px solid black;">
				    <%
					iter = info.iterator();
				    ArrayList<String> projectsAdded = new ArrayList<String>(); 
					while(iter.hasNext()){
						tag = (Tag) iter.next();
						if( Utility.isNotNull(tag.getResource()) ) {
							String resourceID = tag.getResource();
							String projectID = rdf.getProject(resourceID);
							
							if( Utility.isNotNull(projectID) && !projectsAdded.contains(projectID))
							{
						    	String projectTitle = project.getTitle(projectID);
						    	String[] fields = projectID.split("#");
						%>
								<p style="padding:5px; padding-left:20px;"><a href="project.jsp?id=<%=fields[1] %>"><%=projectTitle%></a></p>
						<% 
								projectsAdded.add(projectID);
							}
						}
					}
					%>
					</div>
				</div>
			</li>
		
			<li class="widget color-orange" id="tag3">
				<div class="widget-head">
					<h3  class="style3">
						<span style="font-weight:bold; padding-top:10px; color:#FF6600;"><%=tagWord%></span> has been used by the following <strong>users.</strong>
					</h3>
				</div>
				<div class="widget-content" style="padding: 5px;">
					<div id="map" class="widget-content" style="border: 0px solid black;">
		
						<jsp:include page="/boxes/tagSearch/tagUsers.jsp">
							<jsp:param value="<%= tagWord %>" name="tag"/>
						</jsp:include>
						
					</div>
				</div>
			</li>
		
		</ul>
	</div>
	<!-- end of columns -->

	<script type="text/javascript" src="cookie.jquery.js"></script>

</div>  <!-- end of home status container -->        
<jsp:include page="bottom.jsp" />
