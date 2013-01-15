<%@ page language="java" import="com.hp.hpl.jena.ontology.OntProperty, common.*, java.net.URLDecoder, java.util.List" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>

<jsp:useBean id="impact" class="common.Impact" scope="request"/>
              <div class="resourceInputDiv" id="twitter" style="width:98%;">
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