
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

<jsp:useBean id="list" class="java.util.ArrayList" scope="request"/>

<jsp:useBean id="impact" class="common.Impact" scope="request"/>
    
<%if (session.isNew()==true){ %>
<jsp:forward page="/ourspaces/error.jsp" />
<% } %>

<% 
if( session.getAttribute("use") == null){ %>
<jsp:forward page="/ourspaces/error.jsp" />
<% }

user = (User) session.getAttribute("use");
String temp = (String) request.getParameter("id").toString();
String impactID = org.policygrid.ontologies.OPM.NS.toString() + temp;

impact.setUri(impactID);
impact.loadProperties();
%>
<jsp:include page="top_head.jsp" />
<!-- menu goes here -->
<div class="navBarLeft" style="z-index; -1;" >
           <div class="dropdownBox">
              <a class="navBarLeft" href="#" style="float: left; margin-right:5px;"><img src="/ourspaces/icons/001_12.png" align="left" border="0"/>My Impact Space</a> <img src="/ourspaces/icons/dropdown.png" align="right" border="0"/>
              <div class="navBarOptions ui-corner-all" style="z-index; -1; float:left; width:220px;">
			      <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
			              <a class="navBarLeft" href="/ourspaces/impact/exportImpact.jsp?id=<%=temp %>" style="float: left; margin-right:5px;"><img src="/ourspaces/icons/001_43.png" align="left" border="0"/>Export impact</a> 
			      </div>
		      </div>
           </div>  
           <div class="separator">
              <img src="/ourspaces/icons/separator.png" align="right" style="margin: 0px;" border="0"/> 
           </div>     
       </div>  


       <div class="navBarLeft" style="z-index; -1;" >
           <div class="dropdownBoxFixed">
           <a onClick="updateImpact();return false;" href=""><img src="/ourspaces/icons/001_45.png" align="left" border="0"/>Save changes</a>
            </div>
           <div class="separator">
              <img src="/ourspaces/icons/separator.png" align="right" style="margin: 0px;" border="0"/> 
           </div>       
           <div class="dropdownBoxFixed">
           <a class="" href="/ourspaces/impact/exportImpact.jsp?id=<%=temp %>" target="_blank"><img src="/ourspaces/icons/001_45.png" align="left" border="0"/>Export impact</a>
            </div>   
       </div> 
<jsp:include page="top_tail.jsp" />



<link type="text/css" rel="stylesheet" media="all" href="/ourspaces/table.css" />
	 
<script>
function addTwitter(){
	var newId = $('#twitterNew').val();
	$.post("/ourspaces/impact/impact.jsp",{  uri:"<%=impactID%>",twitterId:newId,action:"addTwitter"},
		function(data2) {
			var newId = $('#twitterNew').val();
			$.post("/ourspaces/forms/pageWidgets/tweet.jsp",{tweetUrl:newId}, function(html) {
				$("#twitter").append(html);
				$('#twitterNew').val("");
			});
		}
	);
}
function updateImpact(){
	var json = new Object();
	json.uri = "<%=impactID%>";
	json.title = $("#title").val();
	json.summary = $("#summary").val();
	json.insights = $("#insights").val();
	json.details = $("#details").val();

	json.researchers = [];
	json.references = [];
	json.corroboratingSource = [];
	json.youTubeVideos = [];
	json.twitterPosts = [];
	json.projects = [];
	
	$("#hasResearcherinputContent span").each(function(i, el){
		json.researchers.push(el.id);
	});
	$("#hasReferenceinputContent span").each(function(i, el){
		json.references.push(el.id);
	});
	$("#hasCorroboratingSourceinputContent span").each(function(i, el){
		json.corroboratingSource.push(el.id);
	});
	$("#youtube iframe").each(function(i, el){
		json.youTubeVideos.push(el.id);
	});
	$("#twitter span").each(function(i, el){
		json.twitterPosts.push(el.id);
	});
	$("#projects span").each(function(i, el){
		json.projects.push(el.id);
	});
	var jsonString = JSON.stringify(json);
	$.post("/ourspaces/impact/impact.jsp",{  json:jsonString,action:"update"},
			  function(data2) {
			}
	);
}
</script>
      
<div class="homeStatusContainer" style="color:#FFFFFF; position:relative;" >
  <div id="columns" style="position:relative;">
     <ul id="column1" class="column" style="width: 660px;">
         <li class="widget color-blue " id="imp1">
             <div class="widget-head">
                 <h3>General</h3>
             </div >
             <div class="widget-content" style="padding: 5px;">
	              <div style="float: left; color: black; margin-bottom: 10px; margin-top: 10px; width:630px;">
	              	<h3 style="color: black;">Title of the case study:</h3>
	              </div>
	              <div style="padding: 5px; margin-bottom: 15px; width:630px;">
	                <input class="inputValue" style="float: left; padding: 2px;  border: 1px solid #000000; width:610px; " id="title" name="" type="text" value="<%=impact.getTitle()%>">
	              </div>
	              <div style="float: left; color: black; margin-bottom: 10px; margin-top: 10px; width:630px;">
	              	<h3 style="color: black;">Summary of the impact:</h3>
	              </div>
	              <div style="padding: 5px; margin-bottom: 15px; width:630px;">
					<textarea class="inputValue" style="float: left; padding: 2px;  border: 1px solid #000000; width:610px; height: 80px;" id="summary" name=""><%=impact.getSummary() %></textarea>
	              </div>   
	               <% list.addAll(impact.getProjects()); %>
					<jsp:include page="forms/pageWidgets/resource.jsp">
						<jsp:param name="id" value="http://www.policygrid.org/provenance-generic.owl#producedInProject"/>
						<jsp:param name="label" value="Projects involved:"/>
					</jsp:include>				  
		              <% list.clear();%>           
             </div>
         </li>
         <li class="widget color-blue " id="imp2">
             <div class="widget-head">
                 <h3>Research</h3>
             </div>
             <div class="widget-content" style="padding: 5px;">
	             <div style="float: left; color: black; margin-bottom: 10px; margin-top: 10px; width:630px;">
	              	<h3 style="color: black;">Research insights:</h3>
	              </div>
	              <div style="padding: 5px; margin-bottom: 15px; width:630px;">
	                <textarea class="inputValue" style="float: left; padding: 2px;  border: 1px solid #000000; width:610px; height: 80px;" id="insights" name=""><%=impact.getInsights()%></textarea>
	              </div>
	              
		              <% list.addAll(impact.getResearchers()); %>
					<jsp:include page="forms/pageWidgets/resource.jsp">
						<jsp:param name="id" value="http://www.policygrid.org/provenance-impact.owl#hasResearcher"/>
						<jsp:param name="label" value="Key Researchers:"/>
					</jsp:include>
				  
		              <% list.clear();
		              	list.addAll(impact.getReferences()); %>
					<jsp:include page="forms/pageWidgets/resource.jsp">
						<jsp:param name="id" value="http://www.policygrid.org/provenance-impact.owl#hasReference"/>
						<jsp:param name="label" value="References to the research:"/>
					</jsp:include>
		              <% list.clear(); %>
                 </div>
             </li>
            
             <li class="widget color-blue" id="imp3">  
                 <div class="widget-head">
                     <h3>Impact</h3>
                 </div>
                 <div class="widget-content" style="padding: 5px;">
             
              <div style="float: left; color: black; margin-bottom: 10px; margin-top: 10px; width:630px;">
              <h3 style="color: black;">Details of the impact:</h3>
              </div>
              <div style="padding: 5px; margin-bottom: 15px; width:630px;">
                <textarea class="inputValue" style="float: left; padding: 2px;  border: 1px solid #000000; width:610px; height: 80px;" id="details" name=""><%=impact.getDetails()%></textarea>
              </div>
              
		              <% list.clear();
		              
		              	list.addAll(impact.getCorroboratingSource()); %>
					<jsp:include page="forms/pageWidgets/resource.jsp">
						<jsp:param name="id" value="http://www.policygrid.org/provenance-impact.owl#hasCorroboratingSource"/>
						<jsp:param name="label" value="Sources to corroborate the impact:"/>
					</jsp:include>
		              <% list.clear(); %>
                 
                 </div>
             </li>

         </ul>
               
         <ul id="column3" class="column">
             <li class="widget color-blue " id="imp7">  
                 <div class="widget-head">
                     <h3>Suggested Evidence</h3>
                 </div>
                 <div class="widget-content" style="padding: 5px;">

              <div style="float: left; color: black; margin-bottom: 10px; margin-top: 10px; width:630px;">
              <h3 style="color: black;">ourSpaces artefacts:</h3>
              </div>             
             </li>
             <li class="widget color-blue " id="imp7">  
                 <div class="widget-head">
                     <h3>Other evidence</h3>
                 </div>
                 <div class="widget-content" style="padding: 5px;">
             
              <div style="float: left; color: black; margin-bottom: 10px; margin-top: 10px; width:630px;">
              <h3 style="color: black;">Youtube videos:</h3>
              </div>
					<jsp:include page="impact/youtube.jsp"/>
              <div> 
                 <h3 style="color: black;">Add new YouTube video ID or URL:</h3>
              </div>
              <div style="padding: 5px; margin-bottom: 15px; width:95%;">
                <input class="inputValue" style="float: left; padding: 2px;  border: 1px solid #000000; width:90%; " id="youtubeNew" name="" type="text" value=""><button onclick="addYouTube()">Add</button>
              </div>
 
                 
              <div style="float: left; color: black; margin-bottom: 10px; margin-top: 10px; width:630px;">
              <h3 style="color: black;">Twitter posts:</h3>
              </div>                   
					<jsp:include page="impact/twitter.jsp"/>
              <div> 
                 <h3 style="color: black;">Add new Twitter URL:</h3>
              </div>
              <div style="padding: 5px; margin-bottom: 15px; width:95%;">
                <input class="inputValue" style="float: left; padding: 2px;  border: 1px solid #000000; width:90%; " id="twitterNew" name="" type="text" value=""><button onclick="addTwitter()">Add</button>
              </div>
                 
                 
                 
             </div>
         </li>
         
     </ul>
     
 </div> <!-- end of columns -->

    <script type="text/javascript" src="cookie.jquery.js"></script>
    <script type="text/javascript" src="inettuts.jsp?space=impact123"></script>
</div> <!-- end of home status container -->

<jsp:include page="bottom.jsp" />

