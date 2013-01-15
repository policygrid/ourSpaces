<%@ page language="java" import="com.hp.hpl.jena.ontology.OntProperty, common.*, java.net.URLDecoder, java.util.List" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<% String youtubeId = (String) request.getParameter("youtubeId");%>
	<div id="<%=youtubeId%>"></div>
	<script>	  
	  // Replace the 'ytplayer' element with an <iframe> and
	  // YouTube player after the API code downloads.
	  var player;
	  function onYouTubePlayerAPIReady() {
	    player = new YT.Player('<%=youtubeId%>', {
	      height: '190',
	      width: '240',
	      videoId: '<%=youtubeId%>'
	    });
	  }
	</script>