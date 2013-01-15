<%@ page language="java" import="java.io.File, java.util.ArrayList,java.util.Scanner, java.io.*,java.util.Random, java.net.*, java.util.Vector, common.*,common.Utility,common.User" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="impact" class="common.Impact" scope="request"/>
<%
String temp = (String) request.getParameter("id").toString();
String impactID = org.policygrid.ontologies.OPM.NS.toString() + temp;
impact.setUri(impactID);
impact.loadProperties();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/ourspaces/javascript/jquery-1.6.2.min.js"></script> 
<script type="text/javascript" src="/ourspaces/javascript/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/jquery.qtip-1.0.0-rc3.min.js"></script>

<link rel="stylesheet" type="text/css" type="text/css" href="/ourspaces/impact/style.css"  />
<link rel="stylesheet" type="text/css" type="text/css" href="/ourspaces/css/custom-theme/jquery-ui-1.8.12.custom.css"  />
<link rel="stylesheet" type="text/css" type="text/css" href="/ourspaces/css/autocomplete/autocomplete.css"  />
<title><%=impact.getTitle() %></title>
</head>
<body style="background-color: white;    line-height: 2em;">

<div class="" style="padding:0 10px;border-radius:10px; background-color: lightblue;color: black;  margin: 0 auto; overflow: hidden; padding: 5px 15px; width: 915px;">
<h1 style ="    text-align: center;"><%=impact.getTitle() %></h1>

<h2>Summary of the impact:</h2>
<p><%=impact.getSummary() %></p>

<h2>Research insights:</h2>
<p><%=impact.getInsights() %></p>
<p>Involved researchers: 
<%
for(String id : impact.getResearchers()){
%>
	<jsp:include page="../artifactSpace/resourceWithLink.jsp">
		<jsp:param value="<%=id %>" name="value" />
		<jsp:param value="" name="style" />
	</jsp:include><%=impact.getResearchers().size()-1 != impact.getResearchers().indexOf(id)?", ":"" %><%
}
%>

</p>

<p>References to the research: <%
for(String id : impact.getReferences()){%>
	<jsp:include page="../artifactSpace/resourceWithLink.jsp">
		<jsp:param value="<%=id %>" name="value" />
		<jsp:param value="" name="style" />
	</jsp:include><%=impact.getReferences().size()-1 != impact.getReferences().indexOf(id)?", ":"" %><%
}
%></p>

<h2>Details of the impact:</h2>
<p><%=impact.getDetails() %></p>
<p>Sources to corroborate the impact: <%
for(String id : impact.getCorroboratingSource()){%>
	<jsp:include page="../artifactSpace/resourceWithLink.jsp">
		<jsp:param value="<%=id %>" name="value" />
		<jsp:param value="" name="style" />
	</jsp:include><%=impact.getCorroboratingSource().size()-1 != impact.getCorroboratingSource().indexOf(id)?", ":"" %><%
}
%></p>
<!-- Youtube -->
<%if(impact.getYouTubeVideos().size() > 0) { %>
	<h2>Promotion videos:</h2>
	<script>
		  // Load the IFrame Player API code asynchronously.
		  var tag = document.createElement('script');
		  tag.src = "https://www.youtube.com/player_api";
		  var firstScriptTag = document.getElementsByTagName('script')[0];
		  firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
		  
	
		  <%for(String youtubeId : impact.getYouTubeVideos()){ %>
		  		var player<%=youtubeId%>;
		  <%}%>
	
		  function onYouTubePlayerAPIReady() {
			  <%for(String youtubeId : impact.getYouTubeVideos()){ %>
			    player<%=youtubeId%> = new YT.Player('<%=youtubeId%>', {height: '220',width: '265',videoId: '<%=youtubeId%>'});
			  <%}%>
		  }
		  $(document).ready(function(){
				//add live handler for clicks on remove links  
				$("#youtube .remove", document.getElementById('#youtube')).live("click", function(){
				    //remove current friend  
				    $(this).parent().remove();  
				    //correct 'to' field position  
				    if($("#youtube span").length === 0) {  
				        $("#youtube").css("top", 0);  
				    }
				});
			});
	</script>
	<div class="resourceInputDiv" style="border-radius: 5px;float:left;width:98%;background-color:#ADD8E6;margin-bottom: 30px;" id="youtube">
	<%
	for(String youtubeId : impact.getYouTubeVideos()){
		String text = "<div id=\""+youtubeId+"\" style=\"padding:5px\"></div>"; %>
	    <jsp:include page="../forms/pageWidgets/smallEntity.jsp">
							<jsp:param name="id" value="<%=youtubeId+\"span\"%>"/>
							<jsp:param name="text" value="<%=text%>"/>
							<jsp:param name="style" value="padding: 3px 16px 4px 2px;"/>
		</jsp:include>
		
	<%}%>
	</div>
<%}%>

<!-- Twitter -->
<%if(impact.getTwitterPosts().size() > 0) { %>
<h2>Twitter posts:</h2>
              <div class="resourceInputDiv" id="twitter" style="border-radius: 5px;background-color:#ADD8E6;width:98%;    float: none;overflow:auto;">
<%for(String twitterId : impact.getTwitterPosts()){ %>
                 <jsp:include page="../forms/pageWidgets/tweet.jsp"><jsp:param  name="tweetUrl" value="<%=twitterId%>"/>
                 </jsp:include>
<%}%>
<script>
$(document).ready(function(){
	//add live handler for clicks on remove links  
	$("#twitter .remove", document.getElementById('#twitter')).live("click", function(){
	    //remove current friend  
	    $(this).parent().remove();  
	    //correct 'to' field position  
	    if($("#twitter span").length === 0) {  
	        $("#twitter").css("top", 0);  
	    }
	});
});</script>
</div>
<%}%>

</div>
</body>
</html>