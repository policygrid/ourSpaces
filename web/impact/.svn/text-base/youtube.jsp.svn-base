<%@ page language="java" import="com.hp.hpl.jena.ontology.OntProperty, common.*, java.net.URLDecoder, java.util.List" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>

<jsp:useBean id="impact" class="common.Impact" scope="request"/>

<script>
	function addYouTube(){
	var newId = $('#youtubeNew').val();
	if(newId.substring(0,7)=="http://"){
		var start = newId.indexOf("v=");
		var end = newId.substring(start).indexOf("&");
		//Skip the v= with +2
		newId = newId.substring(start+2,start+end);
		$('#youtubeNew').val(newId);
	}
	$.post("/ourspaces/impact/impact.jsp",{  uri:"<%=impact.getUri()%>",youtubeId:newId,action:"addYouTube"},
		function(data2) {
		var newId = $('#youtubeNew').val();
		$.post("/ourspaces/forms/pageWidgets/youtube.jsp",{youtubeId:newId}, function(html) {
			$("#youtube").append(html);
	  		var playernew = new YT.Player(newId, {height: '220',width: '265',videoId: newId});
			$('#youtubeNew').val("");
		});
		}
	);
}

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
<div class="resourceInputDiv" style="float:left;width:295px;" id="youtube">
<%
for(String youtubeId : impact.getYouTubeVideos()){
	String text = "<a title=\"Hides or shows the video.\" onclick=\"$('#"+youtubeId+"').toggle();return false;\" href=\"#\" style=\"position: relative; top: 0px;margin: 0 5px;\">Hide/show</a><div id=\""+youtubeId+"\" style=\"padding:5px\"></div>"; %>
    <jsp:include page="../forms/pageWidgets/smallEntity.jsp">
						<jsp:param name="id" value="<%=youtubeId+\"span\"%>"/>
						<jsp:param name="text" value="<%=text%>"/>
						<jsp:param name="style" value="padding: 3px 16px 4px 2px;"/>
	</jsp:include>
	
<%}%>
</div>