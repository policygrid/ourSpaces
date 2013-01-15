<%@ page language="java" import="java.io.File, java.util.ArrayList,java.util.Scanner, java.io.*,java.util.Random, java.net.*, java.util.Vector, common.*,common.Utility,common.User" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:include page="head.jsp" />

<body>
  

<script>
$(document).ready(function(){
	jQuery.ajaxSetup({
		async : false
	});
	start = new Date().getTime();
});
</script> 
<div class="main" style="">
	<h1 style="    text-align: center;">Introductory video about the Natural Language Descriptions</h1>
			<p class="box"><b>Please watch the video below, which will show you how to handle the natural language generation tool. This skill will be necessary in the following tasks. 
			After you finish watching the video, click on the Continue button to proceed to the training examples. </b>
	</p>
		
		<%

Boolean complG =(Boolean)session.getAttribute("completedGraph");
Boolean complN =(Boolean)session.getAttribute("completedNLG");
		if(complG){	
%>
		<div style="position:relative;width:640px;height:480px; margin: 0 auto;"><video controls="controls"  poster="nlgNoIntro/nlgtutorial.jpg" width="640" height="480"  onclick="if(/Android/.test(navigator.userAgent))this.play();">
	<source src="nlgNoIntro/nlgtutorialnointro.mp4" type="video/mp4" />
	<source src="nlgNoIntro/nlgtutorialnointro.webm" type="video/webm" />
	<source src="nlgNoIntro/nlgtutorialnointro.ogv" type="video/ogg" />
	<object type="application/x-shockwave-flash" data="nlgNoIntro/flashfox.swf" width="640" height="480" style="position:relative;">
	<param name="movie" value="nlgNoIntro/flashfox.swf" />
	<param name="allowFullScreen" value="true" />
	<param name="flashVars" value="autoplay=false&amp;controls=true&amp;loop=true&amp;src=nlgtutorialnointro.mp4" />
	 <embed src="nlgNoIntro/flashfox.swf" width="640" height="480" style="position:relative;"  flashVars="autoplay=false&amp;controls=true&amp;loop=true&amp;poster=nlgNoIntro/nlgtutorial.jpg&amp;src=nlgtutorialnointro.mp4"	allowFullScreen="true" wmode="transparent" type="application/x-shockwave-flash" pluginspage="http://www.adobe.com/go/getflashplayer_en" />
	<img alt="Natural Language Generation Tutorial" src="nlgNoIntro/nlgtutorial.jpg" style="position:absolute;left:0;" width="640" height="480" title="Video playback is not supported by your browser" />
	</object>
	</video></div>
	
	
<%
		}
		else {%>
	<div style="position:relative;width:640px;height:480px; margin: 0 auto;"><video controls="controls"  poster="nlg/nlgtutorial.jpg" width="640" height="480"  onclick="if(/Android/.test(navigator.userAgent))this.play();">
<source src="nlg/nlgtutorial.mp4" type="video/mp4" />
<source src="nlg/nlgtutorial.webm" type="video/webm" />
<source src="nlg/nlgtutorial.ogv" type="video/ogg" />
<object type="application/x-shockwave-flash" data="nlg/flashfox.swf" width="640" height="480" style="position:relative;">
<param name="movie" value="nlg/flashfox.swf" />
<param name="allowFullScreen" value="true" />
<param name="flashVars" value="autoplay=false&amp;controls=true&amp;loop=true&amp;src=nlgtutorial.mp4" />
 <embed src="nlg/flashfox.swf" width="640" height="480" style="position:relative;"  flashVars="autoplay=false&amp;controls=true&amp;loop=true&amp;poster=nlg/nlgtutorial.jpg&amp;src=nlgtutorial.mp4"	allowFullScreen="true" wmode="transparent" type="application/x-shockwave-flash" pluginspage="http://www.adobe.com/go/getflashplayer_en" />
<img alt="Natural Language Generation Tutorial" src="nlg/nlgtutorial.jpg" style="position:absolute;left:0;" width="640" height="480" title="Video playback is not supported by your browser" />
</object>
</video></div>
	<%
		}
		%>
	
	<form action="send.jsp"  METHOD="POST">
	
    <INPUT name="id" TYPE="hidden" VALUE="nlgintro">
    <INPUT name="type" TYPE="hidden" VALUE="-1">
    <INPUT name="dataset" TYPE="hidden" VALUE="video">
    <INPUT id="time" name="time" TYPE="hidden" VALUE="0">
    <INPUT onclick="var time = new Date().getTime() - start;$('#time').val(time);return true;" style="cursor: pointer;text-decoration: underline;background-color: darkblue;border: 0 none;border-radius: 5px 5px 5px 5px;color: white;float: left;font-family: times;font-size: 14px;padding: 10px;" TYPE="SUBMIT" VALUE="Continue">
    </form> 
<!--  	<a onclick="sendResults();return true;" style="color:white;" href="Task.jsp"><span style="border-radius:5px;padding:10px;background-color:darkblue;display: block; padding: 10px; width: 130px;;">Continue to the task</span></a>
-->
</div>
</body>
</html>