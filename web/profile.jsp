
<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="projects" class="common.Project" />
<jsp:useBean id="blog" class="common.Blog" />
<jsp:useBean id="rdf" class="common.RDFi" />

<jsp:include page="top_head.jsp" />
<!-- menu goes here -->
<jsp:include page="top_tail.jsp" />

<% 
if(request.getParameter("id") == null) { %>
 	<jsp:forward page="error.jsp" /> <%
}
String stringID = (String) request.getParameter("id").toString();
if( stringID == null ){
	%> <jsp:forward page="error.jsp" /> <%
}
	
User user = new User();
int id = Integer.parseInt(stringID);

common.User user2 = (common.User) session.getAttribute("use");
int id2 = user2.getID();

String title = user.getTitle(id);
String name = user.getName(id);

String number = user.getHouseNumber(id);
String street = user.getStreet(id);
String town = user.getTown(id);
String country = user.getCountry(id);
String postcode = user.getPostCode(id);

String interests = user.getResearchInterests(id);
String website = user.getWebsite(id);

if(website.startsWith("http://"))
{
	// Do nothing
}
else
{
	website = "http://" + website;
}

String photo = user.getPhoto(id);


ArrayList projectList = projects.getAllProjectsAboutUser(user.getFOAFID(id));


String rdfUserID = user.getUserRDFID(id);
String profileContainer = rdf.getUserProfileContainer(rdfUserID);
ArrayList blogContainer = rdf.getBlogContainer(profileContainer);

String temp = (String) blogContainer.get(0);
String[] fieldsB = temp.split("#");

ArrayList blogPosts = blog.getBlogPosts((String)blogContainer.get(0));

%>
  
  <script type="text/javascript">
function unhide(divID) {
  var item = document.getElementById(divID);
  if (item) {
    item.className=(item.className=='hidden')?'unhidden':'hidden';
  }
}
</script>

<script language="javascript">
function add(id) {
	
	var tagID = 'tag' + id;
	var resID = 'resID' + id;
	var userID = 'id' + id;
	var tagName = document.getElementById(tagID).value;
	var resource = document.getElementById(resID).value;
	var user = document.getElementById(userID).value;
	
	
	var xmlHttpRequest=init(tagName, user);

 	function init(tag, usr){

		if (window.XMLHttpRequest) 
		{
			return new XMLHttpRequest();
		} 
		else if (window.ActiveXObject)
		{
			return new ActiveXObject("Microsoft.XMLHTTP");
		}
		
	}
		
	var username=document.getElementById("username");
	xmlHttpRequest.open("GET", "/ourspaces/Tagger?tag="+ encodeURIComponent(tagName) + "&resource=" + encodeURIComponent(resource) + "&user=" + encodeURIComponent(user), true);
	xmlHttpRequest.onreadystatechange=processRequest;
	xmlHttpRequest.send(null);
		
	function processRequest(){
		
		if(xmlHttpRequest.readyState==4){
			if(xmlHttpRequest.status==200){
			
				processResponse();
		
			}
		}
	}
		
	function processResponse(){
		document.getElementById(id).innerHTML = '<p>Tag successfully added.</p>';
	}
}
</script>
<style>
.hidden { display: none; }
.unhidden { display: block; }
</style>

  
  <style type="text/css">
<!--
.style1 {
	color: #FF6600;
	font-size: 16px;
	padding-bottom: 10px;
}
.style2 {color: #FF6600; font-size: 16px; }
-->
  </style>




 <div class="homeStatusContainer" style="color:#FFFFFF; position:relative;" >
 



<div id="columns" style="position:relative;">

<ul id="column1" class="column">
 					<li class="widget color-yellow" id="pro1">
                        <div class="widget-head" >
                            <h3>Information</h3>
                        </div>
                        <div class="widget-content">
                          <div class="myInfoContent">
		                	<%
							if(photo.equals("")) {
							%> 
		                    <img src="images/no.jpg" style="position:relative; float:left;" />
		                    <% }
							else {
							%>
		                    <img src="<%=common.Utility.absoluteURLtoRelative(photo)%>" width="80" style="border: 1px solid #666666; position:relative; float:left;" />
		                    <% } %>
		                    <div class="address">
		                    <p>
		                     <span style="color:#003366; font-size:16px; font-weight:bold;" ><%=name%></span>  <br />
		                     <span style="font-size:14px; color: black;"><%=title%></span><br/>
		                     <span style="font-size:12px; color: gray;"><%=user.getStatus(id)%> </span> 
		                   	 <br /> <br />                   
		                     <span style="font-size:14px;">Address:</span> <br />
		                    </p>
		                    	<p style="padding-left:10px; padding-top:2px; color:#666666;">
		                        	<%=number%><br />
									<%=street%><br />
									<%=town%><br />
		                            <%=country%><br />
		                            <%=postcode%>
		                        </p>
		                    </div>
		                    <div class="lowerInfo">
		                    	<div style="position: relative; float:left; width:280px;">
		                    	
		                            	<div style="width:110px; position: relative; float:left;"><span style="color:#006699;">Website:</span></div>
		                            	<div style="width:170px; position: relative; float:left;"><a href="<%=website%>">My Homepage</a></div>
								</div>
								<div style="position: relative; float:left; width:280px;">
		                            	<div style="width:110px; position: relative; float:left;"><span style="color:#006699;">Area of Research:</span></div>
		                            	<div style="width:170px; position: relative; float:left;"><%=interests%></div>
		                        </div>
		                    </div>
		                </div>
                        </div>
                    </li>
                    
                    <%
                    common.Utility.log.debug("PROFILE:---------HERE");
                    if (blogPosts.size() > 0) { %>
                    
                     	<li class="widget color-yellow " id="pro11">
                      <div class="widget-head">
                          <h3>Last Blog Entry</h3>
                      </div>
                      <div class="widget-content">
                      
                      <div style="padding: 10px;">
                        <% 
	                      BlogBean bb = (BlogBean) blogPosts.get(0);
						  String blogcontent = bb.getContent();
						  String blogtitle = bb.getTitle();
						  String blogdate = bb.getDate();
						  String blogRdfID = bb.getRdfBlogID();
		   				  String[] fields21 = blogRdfID.split("#");
		   				   String shortContent = blogcontent;
		   				if (blogcontent.length() > 300) { shortContent = blogcontent.substring(1,300) + "..."; }
						%>
					   	<p style="padding-bottom:10px; color:#036; font-size:18px;"><%=blogtitle%></p>
						<p><%=shortContent%> 
						</p>
						<p style="font-size:9px; text-align:right; padding-top:5px; color:#006666;">Posted on: <%=blogdate%></p>
						<p style="font-size:9px; text-align:right; padding-top:5px; color:#FF6600;">
						
						
						<%if(blog.getCommentCount("http://www.policygrid.org/ourspacesVRE.owl#"+fields21[1]) == 0) { %>
		                         	<a href="createcomment.jsp?about=<%=fields21[1] %>" rel="facebox" style="color:#FF6600;">Comment</a>
		                         <% } %>
		                         <%if(blog.getCommentCount("http://www.policygrid.org/ourspacesVRE.owl#"+fields21[1]) == 1) { %>
		                         	<a href="createcomment.jsp?about=<%=fields21[1] %>" rel="facebox" style="color:#FF6600;">1 Comment</a>
		                         <% } %>
		                         <%if(blog.getCommentCount("http://www.policygrid.org/OurSpacesVre.owl#"+fields21[1]) > 1) { 
		                         	int count = blog.getCommentCount("http://www.policygrid.org/ourspacesVRE.owl#"+fields21[1]);
		                         %>
		                         	<a href="createcomment.jsp?about=<%=fields21[1] %>" rel="facebox" style="color:#FF6600;"><%=count %> Comments</a>
		                         <% } %>
						</p>
								
					  <p><a href="blog.jsp?id=<%=stringID%>">More posts by <%=name%>...</a></p>
					  
					  </div>
                      </div>
                      </li>
                    <%} %>
                    
                  	<li class="widget color-yellow " id="pro2">
                      <div class="widget-head">
                          <h3>Projects</h3>
                      </div>
                      <div class="widget-content">
                       <div class="myInfoContent">
			                    <div style="padding-left:10px;"><table border="0" cellspacing="5" cellpadding="5">
			
					  <%
				  	Iterator projectIter = projectList.iterator();
					int proCt = 0;
					while(projectIter.hasNext()){
			
						project = (ProjectBean) projectIter.next();
						String title2 = project.getTitle();
						String subtitle = project.getSubtitle();
						String projectID = project.getURL();
					%>
					 
					 <tr><td style="padding-right:5px;"><a href="project.jsp?id=<%=projectID%>"><%=title2%></a></td><td><%=subtitle%></td></tr>
					  <% } %>
					 </table></div>
			                </div>
			   
                      </div>
                    </li>
                     
                     
                      
                        
                    <li class="widget color-yellow " id="pro3">
                      <div class="widget-head">
                          <h3>Tags</h3>
                      </div>
                      <div class="widget-content">
					                       <div class="myInfoContent">
					                     <%
					                     Vector tags = user.getTags(id);
								Iterator y = tags.iterator();
								int count = 0;
								while(y.hasNext()){
									if (count == 20) 
										break;
										tag = (Tag) y.next();
										String tagName = tag.getTag();
										int tagSize = tag.getSize();
										
										if(tagSize < 2) { %>
										<span style="font-size:10px; padding-top:2px; margin-top:2px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
								<%	}
									else if((tagSize >= 2) && (tagSize < 6)) { %>
										<span style="font-size:12px; padding-top:2px; margin-top:2px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
								<%	}
									else if((tagSize >= 6) && (tagSize < 10)) { %>
										<span style="font-size:14px; padding-top:2px; margin-top:2px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
								<%	}
									else if((tagSize >= 10) && (tagSize < 14)) { %>
										<span style="font-size:16px; padding-top:2px; margin-top:2px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
								<%	}
									else if((tagSize >= 14) && (tagSize < 18)) { %>
										<span style="font-size:18px; padding-top:2px; margin-top:2px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
								<%	}
									else  { %>
										<span style="font-size:20px; padding-top:2px; margin-top:2px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
								<%	}
									count++;
									
								}
								%>
					                </div>
                      </div>
                    </li>
                      
                    
                    
                    
</ul>

<ul id="column2" class="column" style="width: 660px;">


 <%
 Vector resources = user.getResources(id);
 if (resources.size() > 0) { 
 %>

    <li class="widget color-yellow " id="pro4">
                        <div class="widget-head">
                            <h3>Resources</h3>
                        </div>
                        <div class="widget-content">
                          <div style="padding: 10px;">
                            <table border="0" cellspacing="1" cellpadding="3">   
					                <%
					            
						  		int yz = 0;
						  		for(int j = resources.size() - 1; j >= 0; j--)
						  		{
									Resources res = (Resources) resources.get(j);
									String restitle = res.getTitle();
									String link = res.getURL();
									String date = res.getDate();
									String resID = res.getID();
									String[] fields = resID.split("#");
							%>
							 
																		 
								  <tr>
											
											<td width="100%">
											<img src="icons/001_58.png" align="middle"  style="float: left; margin-top: auto; margin-bottom:auto;" />
												<a style="font-size:12px;" href="/ourspaces/artifact_new.jsp?id=<%=fields[1]%>"><%=restitle%></a>
												<br />
												<span style="font-size:10px;"><a href="javascript:unhide('<%=yz%>tag');" style="color:#FF6600;">Tag</a> 
												| 
												
												<%if(blog.getCommentCount("http://www.policygrid.org/ourspacesVRE.owl#"+fields[1]) == 0) { %>
					                         	<a href="createcomment.jsp?about=<%=fields[1] %>" rel="facebox" style="color:#FF6600;">Comment</a>
					                         <% } %>
					                         <%if(blog.getCommentCount("http://www.policygrid.org/ourspacesVRE.owl#"+fields[1]) == 1) { %>
					                         	<a href="createcomment.jsp?about=<%=fields[1] %>" rel="facebox" style="color:#FF6600;">1 Comment</a>
					                         <% } %>
					                         <%if(blog.getCommentCount("http://www.policygrid.org/OurSpacesVre.owl#"+fields[1]) > 1) { 
					                         	int count9 = blog.getCommentCount("http://www.policygrid.org/ourspacesVRE.owl#"+fields[1]);
					                         %>
					                         	<a href="createcomment.jsp?about=<%=fields[1] %>" rel="facebox" style="color:#FF6600;"><%=count9 %> Comments</a>
					                         <% } %>
												
												</span>
											</td>
											<td align="right">
									 		 <span style="font-size:9px; color:#FF6600;">
											  	<%=date%>
											 </span>
										  </td>
								   </tr>
					               <tr>
					               			<td width="100" style="font-size:10px; color:#666666; font-weight:bold;">
												&nbsp;
											</td>
					                    	<td>
					                        	<div id="<%=yz%>tag" class="hidden">
													<div id="<%=yz%>">		
															<input id="tag<%=yz%>" style="margin-right:5px;" type="text" />					
															<input id="id<%=yz%>" type="hidden" value="<%=id2%>" />
															<input id="resID<%=yz%>" type="hidden" value="<%=resID%>" />
															<a href="#" onClick="add(<%=yz%>)">Add</a>
													</div>
												</div>
					                        </td>
					               </tr>
					                
					                <% yz++; } %>
					              </table>
					        </div>
					         </div>
                    </li>
                    
        <% } %>            
                    
                        <li class="widget color-yellow " id="pro5">
                        <div class="widget-head">
                            <h3>Activities</h3>
                        </div>
                        <div class="widget-content">
            <div style="padding: 10px;">
            <table border="0" cellspacing="5" cellpadding="3" width="500" style="font-size:11px;">
	<%
			Vector activities = (Vector) activity.getWidgetActivities(id, 10);
			boolean today = false;
			boolean yesterday = false;
			int prevDay = 0;
			int prevMonth = 0;
			int count2 = 0;
			for(int i = 0; i < activities.size(); i++)
			{

				common.Activities act = (common.Activities) activities.get(i);
				String message = act.getMessage();
				String date = act.getDate();
				String type = act.getType();
				int actID = act.getActID();
				String[] fields2 = date.split(" / ");
				int day = Integer.parseInt(fields2[0]);
				int month = Integer.parseInt(fields2[1]);
				month++;
				int year = Integer.parseInt(fields2[2]);

				
				java.util.Calendar c = java.util.Calendar.getInstance();
				int currDay = c.get(java.util.Calendar.DATE);
				int currMonth = c.get(java.util.Calendar.MONTH);
				int currYear = c.get(java.util.Calendar.YEAR);
				if(currMonth == 0)
					currMonth = 1;
					
		%>
			
			
			 <tr width="100%" >
                  	<td height="1"; colspan="3">
                  	<%
                        if(type.equals("resources")) {
                        %><img src="icons/001_58.png" align="middle"  style="float: left; margin-top: auto; margin-bottom:auto;" /><%
                        }
                        if(type.equals("project")) {
                        %><img src="images/spaces/Projects_small.png" align="middle" style="float: left; margin-top: auto; margin-bottom:auto;" /><%
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
                        
				  		<%=message%>
				    </td>
                 </tr>
                 
                 
                 <tr style="margin-top:2px;">
                   <td width="40px"></td>
                 	<td >
                         <span style="font-size:9px;">
                         <%if(blog.getCommentCount("http://www.policygrid.org/ourspacesVRE.owl#"+actID) == 0) { %>
                         	<a href="createcomment.jsp?about=<%=actID %>" rel="facebox" style="color:#FF6600;">Comment</a>
                         <% } %>
                         <%if(blog.getCommentCount("http://www.policygrid.org/ourspacesVRE.owl#"+actID) == 1) { %>
                         	<a href="createcomment.jsp?about=<%=actID %>" rel="facebox" style="color:#FF6600;">1 Comment</a>
                         <% } %>
                         <%if(blog.getCommentCount("http://www.policygrid.org/OurSpacesVre.owl#"+actID) > 1) { 
                         	int count1 = blog.getCommentCount("http://www.policygrid.org/ourspacesVRE.owl#"+actID);
                         %>
                         	<a href="createcomment.jsp?about=<%=actID %>" rel="facebox" style="color:#FF6600;"><%=count1 %> Comments</a>
                         <% } %>
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
                 </tr>
                 
				 <% } %>
	</table>
	</div>
                        </div>
                    </li>
</ul>

</div> <!--  end of columns -->

            <script type="text/javascript" src="jquery-ui-personalized-1.6rc2.min.js"></script>
            <script type="text/javascript" src="cookie.jquery.js"></script>
            <script type="text/javascript" src="inettuts.jsp?space=profile-<%=id%>"></script>
            

</div> <!--  End of home status -->

  <jsp:include page="bottom.jsp" />
