<%@ page language="java" import="com.hp.hpl.jena.ontology.OntProperty, common.*, java.net.URLDecoder, java.util.List, net.sf.json.*" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>


<jsp:useBean id="impact" class="common.Impact" scope="request"/>
<%
String action = (String)request.getParameter("action");
if("addYouTube".equals(action)){
	String uri = (String)request.getParameter("uri");
	String youtubeId = (String)request.getParameter("youtubeId");
	impact.setUri(uri);
	impact.getYouTubeVideos().add(youtubeId);
	impact.writeProperties();
}
if("addTwitter".equals(action)){
	String uri = (String)request.getParameter("uri");
	String twitterId = (String)request.getParameter("twitterId");
	impact.setUri(uri);
	impact.getTwitterPosts().add(twitterId);
	impact.writeProperties();
}
if("update".equals(action)){
	String json = (String)request.getParameter("json");
	impact.setUri(((JSONObject) JSONSerializer.toJSON(json)).getString("uri"));
	impact.loadProperties();
	impact.deleteProperties();
	impact = new Impact();
	impact.loadFromJson(json);
	impact.writeProperties();
}
/*for(String youtubeId : impact.getYouTubeVideos()){ 
	
}*/
%>