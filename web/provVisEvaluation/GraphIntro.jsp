<%@ page language="java" import="java.io.File, java.util.ArrayList,java.util.Scanner, java.io.*,java.util.Random, java.net.*, java.util.Vector, common.*,common.Utility,common.User" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="head.jsp" />
<body>
  

<div class="main" style="">
	<h1 style="    text-align: center;;">Introductory video about Graphical Visualisation Tool</h1>
	<p class="box" style=""><b>Please watch the video below, which will show you how to handle the graphical visualisation tool. This skill will be necessary in the following tasks. After you finish watching the video, click on the Continue button to proceed to the training examples. </b>
	</p>
	<%

Boolean complG =(Boolean)session.getAttribute("completedGraph");
Boolean complN =(Boolean)session.getAttribute("completedNLG");
		if(complN){	
%>
	<div style="position:relative;width:640px;height:480px; margin: 0 auto;"><video controls="controls"  poster="graphNoIntro/GraphVis.jpg" width="640" height="480"  onclick="if(/Android/.test(navigator.userAgent))this.play();">
<source src="graphNoIntro/GraphVisNoIntro.mp4" type="video/mp4" />
<source src="graphNoIntro/GraphVisNoIntro.webm" type="video/webm" />
<source src="graphNoIntro/GraphVisNoIntro.ogv" type="video/ogg" />
<object type="application/x-shockwave-flash" data="graphNoIntro/flashfox.swf" width="640" height="480" style="position:relative;">
<param name="movie" value="graphNoIntro/flashfox.swf" />
<param name="allowFullScreen" value="true" />
<param name="flashVars" value="autoplay=false&amp;controls=true&amp;loop=true&amp;src=GraphVisNoIntro.mp4" />
 <embed src="graphNoIntro/flashfox.swf" width="640" height="480" style="position:relative;"  flashVars="autoplay=false&amp;controls=true&amp;loop=true&amp;poster=graphNoIntro/GraphVis.jpg&amp;src=GraphVisNoIntro.mp4"	allowFullScreen="true" wmode="transparent" type="application/x-shockwave-flash" pluginspage="http://www.adobe.com/go/getflashplayer_en" />
<img alt="Graphical Visualisation Tutorial" src="graphNoIntro/GraphVis.jpg" style="position:absolute;left:0;" width="640" height="480" title="Video playback is not supported by your browser" />
</object>
</video>
</div>	

<%
		}
		else {%>
		
	<div style="position:relative;width:640px;height:480px; margin: 0 auto;"><video controls="controls"  poster="graph/GraphVis.jpg" width="640" height="480"  onclick="if(/Android/.test(navigator.userAgent))this.play();">
<source src="graph/GraphVis.mp4" type="video/mp4" />
<source src="graph/GraphVis.webm" type="video/webm" />
<source src="graph/GraphVis.ogv" type="video/ogg" />
<object type="application/x-shockwave-flash" data="graph/flashfox.swf" width="640" height="480" style="position:relative;">
<param name="movie" value="graph/flashfox.swf" />
<param name="allowFullScreen" value="true" />
<param name="flashVars" value="autoplay=false&amp;controls=true&amp;loop=true&amp;src=GraphVis.mp4" />
 <embed src="graph/flashfox.swf" width="640" height="480" style="position:relative;"  flashVars="autoplay=false&amp;controls=true&amp;loop=true&amp;poster=graph/GraphVis.jpg&amp;src=GraphVis.mp4"	allowFullScreen="true" wmode="transparent" type="application/x-shockwave-flash" pluginspage="http://www.adobe.com/go/getflashplayer_en" />
<img alt="Graphical Visualisation Tutorial" src="graph/GraphVis.jpg" style="position:absolute;left:0;" width="640" height="480" title="Video playback is not supported by your browser" />
</object>
</video>
</div>	
<%
		}%>
	<form action="send.jsp"  METHOD="POST">
	
    <INPUT name="id" TYPE="hidden" VALUE="graphintro">
    <INPUT name="type" TYPE="hidden" VALUE="-1">
    <INPUT name="dataset" TYPE="hidden" VALUE="video">
    <INPUT id="time" name="time" TYPE="hidden" VALUE="0">
    <INPUT onclick="var time = new Date().getTime() - start;$('#time').val(time);return true;" style="cursor: pointer;text-decoration: underline;background-color: darkblue;border: 0 none;border-radius: 5px 5px 5px 5px;color: white;float: left;font-family: times;font-size: 14px;padding: 10px;" TYPE="SUBMIT" VALUE="Continue">
    </form> 
	<!-- <a onclick="sendResults();return true;" style="color:white;" href="Task.jsp"><span style="border-radius:5px;padding:10px;background-color:darkblue;display: block; padding: 10px; width: 130px;;">Continue to the task</span></a>
 -->
 </div>
<script>
$(document).ready(function(){
	//$("#questions").accordion();
	userID = '<%=session.hashCode()%>';
});
$(document).ready(function(){
	jQuery.ajaxSetup({
		async : false
	});
	start = new Date().getTime();
});

</script>
</body>
</html>