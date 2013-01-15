
<%@page import="java.util.Collection"%>
<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="projectBean" class="common.ProjectBean" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="rdf" class="common.RDFi" />
 <jsp:useBean id="blog" class="common.Blog" />

<jsp:useBean id="access" class="common.AccessControl" />
<jsp:useBean id="project" class="common.Project" />
    


<%if (session.isNew()==true){ %>
<jsp:forward page="error.jsp" />
<% } %>

<% 
if( session.getAttribute("use") == null){ %>
<jsp:forward page="error.jsp" />
<% }

user = (User) session.getAttribute("use");

if(request.getParameter("id") == null) { %>
 	<jsp:forward page="error.jsp" /> <%
}
String temp = (String) request.getParameter("id").toString();
String projectID = org.policygrid.ontologies.Project.NS.toString() + temp;
if( projectID == null ){
	%> <jsp:forward page="error.jsp" /> <%
}



String title = project.getTitle(projectID);
String subtitle = project.getSubtitle(projectID);
String startdate = project.getStartdate(projectID);
String enddate = project.getEnddate(projectID);
// Vector tags = tag.getProjectTags(projectID);
// Tags for some reason, getProjectTags() doesn't work anymore (problem with files.containerID?) so get every resources of a project and add each resources' tags to the list of project tags:
Vector<Resources> projectResources = project.getResources(projectID);
ArrayList<String> projectResourcesID = new ArrayList<String>();
for (int i = 0; i < projectResources.size(); i++){
	projectResourcesID.add(projectResources.get(i).getId());
}
Vector<Tag> tags = tag.getResourceTags(projectResourcesID);

ArrayList memberList = project.getProjectMembers(projectID);
ArrayList aims = project.getProjectAims(projectID);
ArrayList subProjects = project.getSubProjects(projectID);


String container = project.getProjectContainer(projectID);
String projectPhoto = project.getProjectPhoto(projectID);
String cont = URLEncoder.encode(container);


boolean check = access.checkPublic(projectID);

//Check current project
boolean view = AccessControl.checkPermission("view", user.getFOAFID(), projectID);
boolean edit = AccessControl.checkPermission("edit", user.getFOAFID(), projectID);
boolean download = AccessControl.checkPermission("download", user.getFOAFID(), projectID);
boolean remove = AccessControl.checkPermission("remove", user.getFOAFID(), projectID);


//Check parent project for permissions
String pProjectID = project.getParentProject(projectID);
if (pProjectID != null)
{
if (AccessControl.checkPermission("view", user.getFOAFID(), pProjectID)) view = true;
if (AccessControl.checkPermission("edit", user.getFOAFID(), pProjectID)) edit = true;
if (AccessControl.checkPermission("download", user.getFOAFID(), pProjectID)) download = true;
}



//Super Ed Mode
if (user.getID()==59 || user.getID()==56) {
	check = true;
	view = true;
	edit = true;
	download = true;
	remove = true;
}



String shortTitle = title;
if (title.length() > 15) shortTitle = title.subSequence(0, 15) + "...";


%>

<jsp:include page="top_head.jsp" />



      <div class="navBarLeft">
           <div class="dropdownBox">
              <a class="navBarLeft" href="#" style="float: left;"><img src="/ourspaces/images/spaces/Projects_small.png" align="left" border="0"/><%=shortTitle%> Space </a> <img src="/ourspaces/icons/dropdown.png" align="right" border="0"/>
              <div class="navBarOptions ui-corner-all" style="font-size: 16px; width:220px;">
              <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
		         <span style="font-weight: 900;"> Edit Project:</span>
		         </div>
		         <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px; border-bottom: 1px solid #FF6600"></div> 
              
		           <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
		             <a class="popup_dialog" title="Create new Project" href="createprojectform.jsp" ><img src="/ourspaces/images/spaces/Projects_small.png" align="left" border="0"/>Create New Project</a>
		           </div>

					<%if(remove == true || edit == true) { %>
		           <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
		             <a class="popup_dialog" title="Create new sub-project" href="project/createsubprojectform.jsp?id=<%=temp %>">
		             	<img src="/ourspaces/images/spaces/Projects_small.png" align="left" border="0"/>Create Sub-Project
		             </a>
		           </div>
					<div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
		             <a class="popup_dialog" title="Edit Project Settings" href="settings.jsp?id=<%=temp %>">
		             	<img src="/ourspaces/icons/001_45.png" align="left" border="0"/>Edit Project Settings
		             </a>
<%-- 		           <a href="#" onclick="javascript:doSettings('<%=temp %>');"><img src="/ourspaces/icons/001_45.png" align="left" border="0"/>Edit Project Settings</a> --%>
		           </div>
  		           <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
		             <a class="popup_dialog" title="Delete Project" href="project/deleteProject.jsp?projectID=<%=temp%>">
		             	<img src="/ourspaces/icons/001_29.png" align="left" border="0"/>Delete Project
		             </a>
		           </div>
					<% } %>       
		           
		           
		           <%if(download == true || edit == true) { %>
					<div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
		           		<span style="font-weight: 900;"> Manage Project:</span>
	           		</div>
        	        <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px; border-bottom: 1px solid #FF6600"></div> 
		           
<!-- 		           Removed events functionality for now. Needs to be discussed -->
<!-- 		           <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;"> -->
<%-- 		           		<a href="#" onclick="javascript:addEvent('<%=temp %>');"><img src="/ourspaces/icons/001_44.png" align="left" border="0"/>Add New Event</a> --%>
<!-- 		           </div> -->
		           <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
		           		<a href="#" onclick="javascript:newMessage('<%=temp %>');"><img src="/ourspaces/icons/001_12.png" align="left" border="0"/>Send Message to All Members</a>
		           </div>
		           <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
		           		<a href="#" onclick="javascript:doUpload();"><img src="/ourspaces/icons/001_58.png" align="left" border="0"/>Upload New Resource</a>
		           </div>
				   <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
		           <a class="popup_dialog_simple" title="Manage Folders" href="project/newfolder.jsp">
		             	<img src="/ourspaces/icons/001_43.png" align="left" border="0"/>Add/Remove Folder
		           </a>
		           </div>
		           
		           <% } %>
		      </div>
           </div>       
       </div>


<jsp:include page="top_tail.jsp" />




<%
session.setAttribute("projectID", projectID);
session.setAttribute("containerType", "project");
%>


<script type="text/javascript" language="javascript">
function goTo()
{
	var month = document.getElementById('iMonth').value;
	var year = document.getElementById('iYear').value;
	tag('calendar/monthView.jsp?container=<%=cont%>&iMonth='+month+'&iYear='+year, 'calendar');
}
</script>

<script type="text/javascript" language="javascript">
function doComment(res)
{
	jQuery.facebox(function() { 
	  jQuery.get("createcomment.jsp?about="+res, function(data) {
	    jQuery.facebox(data)
	  })
	})	
}


/**
 * Shows the upload form.
 */
function doUpload()
{
	var p = new Property();
	p.idProperty = "http://www.policygrid.org/provenance-generic.owl#producedInProject";
	p.property = "producedInProject";
	p.el = null;
	p.type = "resource";
	p.range = "http://www.policygrid.org/project.owl#Project";
	p.value = [];
	p.value.push("<%=projectID%>");
	var properties = [];
	properties.push(p);
	showUploadDialog({initProperties : properties});
	//TODO highlight new artifact. 
	//TODO add the project as default.
}


function addEvent(res)
{
	jQuery.facebox(function() { 
	  jQuery.get("calendar/addEvent.jsp?id="+res, function(data) {
	    jQuery.facebox(data)
	  })
	})	
}


function doSettings(res)
{
	jQuery.facebox(function() { 
	  jQuery.get("settings.jsp?id="+res, function(data) {
	    jQuery.facebox(data)
	  })
	})	
}
	
function newMessage(res)
{
	
	
	var query = "/ourspaces/messages/newprojectmessage.jsp?id="+res;
	
	
	$.get(query, function(data) {
		//Trim the data.
		data = data.replace(/^\s+|\s+$/g, '') ;
		var div = $("<div>");
		div.attr("id","newMessage");
		div.appendTo("body");
		div.html(data);
		div.width(700);
		$( "#newMessage" ).dialog({
			width:700,
			position: 'center',
			resizable: true,
			modal: true,
			autoOpen: false,  
			title: 'New Project Message', 
			dialogClass : "alertDialog",
			
			close: function(event, ui) {
				$("#newMessage").remove();
				
				
			}
		});
		$("#newMessage").dialog('open');
		
	});
	/* jQuery.facebox(function() { 
	  jQuery.get("messages/newprojectmessage.jsp?id="+res, function(data) {
	    jQuery.facebox(data)
	  })
	})	 */
}

function createComment1(res)
{
	jQuery.facebox(function() { 
	  jQuery.get("createcomment.jsp?about="+res, function(data) {
	    jQuery.facebox(data)
	  })
	})	
}


function doMoveFile(file, resource)
{
	var res = encodeURIComponent(resource);
	
	jQuery.facebox(function() { 
	  jQuery.get("movefolder.jsp?fileID="+file+"&resource="+res, function(data) {
	    jQuery.facebox(data)
	  })
	})	
}

function doAddFolder()
{
	jQuery.facebox(function() { 
	  jQuery.get("project/newfolder.jsp", function(data) {
	    jQuery.facebox(data)
	  })
	})	
}
function doPermissions(resource)
{
	jQuery.facebox(function() { 
	  jQuery.get("permissions/setPermission.jsp?resource="+resource, function(data) {
	    jQuery.facebox(data)
	  })
	})	
}
</script>

<script language="javascript" type="text/javascript">

function moveFile(fileID, folder, optSubFolder) {
	window.location='moveFile.jsp?fileID='+fileID+'&folder='+folder+'&optSubFolder='+optSubFolder;
}

function createFolder(folder, subFolder) {
	window.location='createFolder.jsp?folder='+folder+'&subFolder='+subFolder;
}

function removeFolder(folder) {
	window.location='deleteFolder.jsp?folder='+folder;
}


function doDelete(resource, title)
{
	jQuery.facebox(function() { 
	  jQuery.get("deleteResource.jsp?title="+title+"&resource="+resource+"&page=project.jsp&id=<%=temp%>", function(data) {
	    jQuery.facebox(data)
	  })
	})	
}

</script>

	

<link href="/ourspaces/jqueryFileTree.css" rel="stylesheet" type="text/css" media="screen" /> 
<!--
<link type="text/css" rel="stylesheet" media="all" href="/ourspaces/table.css" />	 
     --> 
      
 <div class="homeStatusContainer" style="color:#FFFFFF; position:relative;" >
 

      
 
             <div id="columns" style="position:relative;">
                
                <ul id="column1" class="column" style="width: 660px;">
                <li class="widget color-white" id="pr1">
                        <div class="widget-head">
                            <h3>Recent News</h3>
                        </div>
                        <div class="widget-content" style="padding: 10px;">
                          <jsp:include page="project/allnews.jsp?id=<%=temp%>" flush="false"/>
                        </div>
                    </li>
                 <li class="widget color-white" id="pr0">
                        <div class="widget-head">
                            <h3>Calendar</h3>
                        </div>
                        <div class="widget-content" style="padding: 10px;">
                          <jsp:include page="project/calendar.jsp?id=<%=temp%>" flush="false"/>
                        </div>
                    </li>
                    
                    
                    <li class="widget color-white" id="pr2">
                        <div class="widget-head">
                            <h3>New Resources</h3>
                        </div>
                        <div class="widget-content" style="padding: 10px;">
                          <jsp:include page="project/projectresources.jsp?id=<%=temp%>" flush="false"/>
                        </div>
                    </li>
                    
                    <li class="widget color-white" id="pr3">
                        <div class="widget-head">
                            <h3>Project Blogs</h3>
                        </div>
                        <div class="widget-content" style="padding: 10px;">
                          <jsp:include page="project/projectblogs.jsp?id=<%=temp%>" flush="false"/>
                        </div>
                    </li>
                     
                     
                     
                </ul>
                      
                <ul id="column3" class="column">
                    <li class="widget color-white" id="p4">  
                        <div class="widget-head">
                            <h3>Info</h3>
                        </div>
                        <div class="widget-content" style="padding: 10px;">
                          <div>
                          
							<div style="position:relative; float:left; width:300px;">
						      	<div class="projectTitle" style="padding-left:0px; width:300px; margin-bottom: 20px;">
						               <span><%=title%></span>
						      	</div>
						      	<p style="width:300px;"><%=subtitle%></p>
	
						     </div>
	                          <img src="<%=common.Utility.absoluteURLtoRelative(projectPhoto) %>" style="position: relative; float: right;"></img>
                          
						  </div>
                        </div>
                    </li>
                    
                    <!--  Parent Project -->
			<%
	        String parProjectID = project.getParentProject(projectID);
	        if (parProjectID != null)
	        {
	        	String[] splitter = parProjectID.split("#");
	        	String parProjectTitle  = project.getTitle(parProjectID);
	        %>
                    
                     <li class="widget color-white" id="p5">  
                        <div class="widget-head">
                            <h3>Sub-project Of</h3>
                        </div>
                        <div class="widget-content" style="padding: 10px;">
                           <div style="padding-left:10px;">
		                    	<table border="0" cellspacing="5" cellpadding="5">
									<tr><td style="padding-right:5px;"><a href="project.jsp?id=<%=splitter[1] %>"><%=parProjectTitle %></a></td></tr>
								</table>
							</div>
                        </div>
                    </li>
            <% } %> 
                    
                     <li class="widget color-white" id="p6">  
                        <div class="widget-head">
                            <h3>Sub-projects</h3>
                        </div>
                        <div class="widget-content">
                                               
                        
                        <% if(edit == true) { %>
						   <div class="widget-top" >
						   	<div class="wlink" align="right" style="float:right; margin-left: 5px; margin-right: 10px; margin-bottom: 0px; padding: 10px;">
									<a class="popup_dialog" title="Create new sub-project" href="project/createsubprojectform.jsp?id=<%=temp %>">
										<img align="middle" style="float: left; display:inline-block; margin: -2px; border: 0px none;" src="/ourspaces/images/spaces/Projects_small.png">
										<span style="margin-left:8px;">Create new sub-project</span>
									</a>
								</div>
						   </div>
	            		<% } %>
	            		
                        
		                    	<table border="0" cellspacing="5" cellpadding="5" style="width:100%">
									<tr>
										<td>
											<table border="0" cellspacing="3" cellpadding="3" style="font-size:11px;">
											<%
											
											for(int i = 0; i < subProjects.size(); i++)
											{
												ProjectBean pb = (ProjectBean) subProjects.get(i);
												String subProjectTitle = pb.getTitle();
												String subProjectURL = pb.getURL();
											%>
						                    	<tr>
						                      		<td><a href="project.jsp?id=<%=subProjectURL %>"><%=subProjectTitle %></a></td>
						                 		 </tr>
						                 	<%
											}
						                 	%>
						                       	
						                    </table>
										</td>
									</tr>
								</table>
                        </div>
                    </li>
                    
                    
                     <li class="widget color-white" id="p7">  
                        <div class="widget-head">
                            <h3>Resources</h3>
                        </div>
                        <div class="widget-content">
							<%
							if(download == true || edit == true) { %>
							
							 <div class="widget-top" >
							 
										   	<div class="wlink" align="right" style="float:right; margin-left: 5px; margin-right: 10px; margin-bottom: 0px; padding: 10px;">
<!-- 													<a href="#" onclick="javascript:doAddFolder();"> -->
													<a class="popup_dialog_simple" title="Manage Folders" href="project/newfolder.jsp">
														<img align="middle" style="float: left; display:inline-block; margin: -2px; border: 0px none;" src="/ourspaces/icons/001_43.png">
														<span style="margin-left:8px;">Add/Remove Folder</span>
													</a>
												</div>
												
								 <!-- <div style="float: right; padding: 10px; width: 260px;">
									<a href="#" onclick="javascript:doAddFolder();" class="fg-button ui-state-active fg-button-icon-right ui-corner-all">Add/Remove Folder</a>
								 </div> -->
							 </div>
							<% }
							%> 
		                		          		                    	
						    <div style="padding:5px; float: left;">		                    	
						  	  <jsp:include page="project/projectres.jsp"></jsp:include>                   	
							</div>
		                </div>
                    </li>

                     <li class="widget color-white" id="p12">  
                        <div class="widget-head">
                            <h3>Resource Details</h3>
                        </div>
                        <div class="widget-content">
							<div id="resInfo"></div>

		                </div>
                    </li>
                    
                    
                     <li class="widget color-white" id="p8">  
                        <div class="widget-head">
                            <h3>Members</h3>
                        </div>
                        <div class="widget-content">
                        <% if(edit == true) { %>
                        
										   <div class="widget-top" >
											   <div class="wlink" align="right" style="float:right; margin-left: 5px; margin-right: 10px; margin-bottom: 0px; padding: 10px;">
														<a class="popup_dialog" title="Add Member" href="project/addProjectMember.jsp?id=<%=temp %>">
															<img align="middle" style="float: left; display:inline-block; margin: -2px; border: 0px none;" src="/ourspaces/icons/001_55.png">
															<span style="margin-left:8px;">Add a new member</span>
														</a>
													</div>
                       		<!-- <div style="float: right; padding: 10px; width: 260px;">
	            							<a href="project/addProjectMember.jsp?id=<%=temp %>" rel="facebox" class="fg-button ui-state-active fg-button-icon-right ui-corner-all">Add a new member</a>
	            						</div> -->
	            				 </div>
	            							<% } %>
                        <div style="padding-left:0px;">
		                  <jsp:include page="project/members.jsp"></jsp:include>    
                        </div>
                        </div>
                    </li>
                    
                     <li class="widget color-white" id="p9">  
                        <div class="widget-head">
                            <h3>Project Aims</h3>
                        </div>
                        <div class="widget-content">
                        	<div style="padding:10px;">
                        		<ul>
		                    	<%
								for(int i = 0; i < aims.size(); i++)
								{
									String aim = (String) aims.get(i);
									%><li><p style="padding-bottom:10px;"><%=aim %></p></li><%
								}
								%>
								</ul>
							</div>
                        </div>
                    </li>
                    
                     <li class="widget color-white" id="p10">  
                        <div class="widget-head">
                            <h3>Project Information</h3>
                        </div>
                        <div class="widget-content" style="padding: 10px;">
	                    	<table border="0" cellspacing="3" cellpadding="3" style="font-size:11px;">
		                    	<tr>
		                        	<td width="144" style="color:#CC0000;">Start Date: </td>
		                      		<td width="173"><%=startdate %></td>
		                  		</tr>
		                        <tr>
		                        	<td width="144" style="color:#CC0000;">End Date: </td>
		                      		<td width="173"><%=enddate %></td>
		                  		</tr>
		
		                    </table>
                        </div>
                    </li>
                    
                     <li class="widget color-white" id="p11">  
                        <div class="widget-head">
                            <h3>Project Tags</h3>
                        </div>
                        <div class="widget-content" style="padding: 10px;">
                        <div style="padding:10px;">
		                    	<%
		                    	for (int i = 0; i < tags.size(); i++)
		                    	{
		                     
		                                tag = (Tag)tags.get(i);
		                                String tagName = tag.getTag();
		                                int tagSize = tag.getSize();
		                                if(tagSize == 1) { %>
		                                    <span style="font-size:12px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
		                            <%	}
		                                else if(tagSize == 2) { %>
		                                    <span style="font-size:15px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
		                            <%	}
		                                else if(tagSize == 3) { %>
		                                    <span style="font-size:18px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
		                            <%	}
		                                else if(tagSize == 4) { %>
		                                    <span style="font-size:20px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
		                            <%	}
		                                else if(tagSize == 5) { %>
		                                    <span style="font-size:23px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
		                            <%	}
		                                else  { %>
		                                    <span style="font-size:25px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
		                            <%	}     
		              
		                        }
	  
		                      %>
							</div>
                        </div>
                    </li>
                </ul>
                
            </div> <!-- end of columns -->

            <script type="text/javascript" src="cookie.jquery.js"></script>
            <script type="text/javascript" src="inettuts.jsp?space=project-<%=title%>"></script>
            
    </div>  <!-- end of home status container -->         
           
  <jsp:include page="bottom.jsp" />

