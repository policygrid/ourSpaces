<%@ page language="java" import="java.io.File, java.util.ArrayList,java.util.Scanner, java.io.*,java.util.Random, java.net.*, java.util.Vector, common.*,common.Utility,common.User" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="head.jsp" />
<body>
  

<div class="main" style="">
	<h1 style="    text-align: center;;">Examples of Graphical Visualisation Tool</h1>
	
<p class="box" style="margin:20px;width:90%">  
 To train yourself in using graphical visualisation, please spend a little bit of time with the two examples that follow. In each case, there is a graph and two questions.

 Try to find the information in the graph to answer the questions. Click on the question to see if the information you found is correct.<br>
		After you finish exploring the examples, click on Continue button to go to the proper evaluation.
 
</p>
<script src="graph/html5ext.js" type="text/javascript"></script>
<%//d7fcebbf-8c12-4f54-8e62-7d6449fcfa82 %>
	<div style="width:100%;margin:10px 20px;"> 
		<h3>Example of Graphical Visualisation 1:</h3>
            <div style="width:100%; float: left;">
					<div  id="provenanceResource" style="float:left;width: 90%; height: 500px;overflow: hidden;font-size:12px; margin:10px;">
					
										<jsp:include page="../testProvenance/provenanceOne.jsp" flush="false">
											<jsp:param value="http://openprovenance.org/ontology#0013a70e-715d-43d6-9a4c-938deb7dbd76" name="resource"/>
											<jsp:param value="false" name="editable"/>
										</jsp:include>	
					</div>
				</div>
		<div id="q1body" style="" class="questionBody">		
			<h4 style="text-align: left;"><a style="color:white" href="#" onclick="$('#q2answer').toggle();return false;">Who was examining the scan image?</a></h4>
			<p style="display: none;margin:20px;width:90%" id="q2answer">The scan image is an artefact, represented by an orange box. It was used by the process Examination of Brain Scan Image.You can see two agents that were controlling it. These two doctors were examining the image - the correct answer is Doctor Joe Bloggs and  Doctor Melinda Carpenter.</p>
		</div>
		<div id="q1body" style="" class="questionBody">		
			<h4 style="text-align: left;"><a style="color:white" href="#" onclick="$('#q1answer').toggle();return false;">What is the name of the patient involved in the MRI Scan Process?</a></h4>
			<p style="display: none;margin:20px;width:90%" id="q1answer">You can see the scan image and how it was used, but not how it was generated.
			In order to find its provenance, press the plus icon in its top right corner. A new process, named MRI Scan, is displayed.
			 Two agents are connected to it - one via wasControlledBy edge and the other via involved edge. The wasControlledBy edge denotes a controlling agent, in this case a doctor. Involved, on the other hand, denotes a passive agent, a patient. The name of the patient is therefore Lisa Peterson.</p>
		</div>
	  
	  
	</div>
	
	<div style="width:100%;margin:10px;"> 
	
		<h3>Example of Graphical Visualisation 2:</h3>   
            <div style="width:100%;    float: left;">
					<div id="provenanceResource" style="float:left;width: 90%; height: 500px;overflow: hidden;font-size:12px; margin:10px;">
					
										<jsp:include page="../testProvenance/provenanceOne.jsp" flush="false">
											<jsp:param value="http://openprovenance.org/ontology#dd34c9fe-5dc3-4b59-a71e-54328fd62119" name="resource"/>
											<jsp:param value="false" name="editable"/>
										</jsp:include>	
					</div>
			</div>

		<div id="q3body" style="" class="questionBody">		
			<h4 style="text-align: left;"><a style="color:white" href="#" onclick="$('#q3answer').toggle();return false;">Who developed the Weka software?</a></h4>
			<p style="display: none;margin:20px;width:90%" id="q3answer">When you expand the artifact Weka software and the process Weka development, you can see three agents controlling the process. The developers are Eibe Frank, Mark Hall and Len Trigg.</p>
		</div>
						<div id="q4body" style="" class="questionBody">		
			<h4 style="text-align: left;"><a style="color:white" href="#" onclick="$('#q4answer').toggle();return false;">How many artefacts did the process Simulation run use?</a></h4>
			<p style="display: none;margin:20px;width:90%" id="q4answer">After expanding artifact Simulation output, you can see the process Simulation run. After expanding it, there are two artefacts used by it - Configuration 1 and Simulation software.</p>
		</div>
	</div> 
	<form action="send.jsp"  METHOD="POST">
	
    <INPUT name="id" TYPE="hidden" VALUE="graphintro">
    <INPUT name="type" TYPE="hidden" VALUE="-1">
    <INPUT name="dataset" TYPE="hidden" VALUE="examples">
    <INPUT id="time" name="time" TYPE="hidden" VALUE="0">
    <INPUT onclick="var time = new Date().getTime() - start;$('#time').val(time);return true;" style="cursor: pointer;text-decoration: underline;background-color: darkblue;border: 0 none;border-radius: 5px 5px 5px 5px;color: white;float: left;font-family: times;font-size: 14px;padding: 10px;" TYPE="SUBMIT" VALUE="Continue">
    </form> 
	<!-- <a onclick="sendResults();return true;" style="color:white;" href="Task.jsp"><span style="border-radius:5px;padding:10px;background-color:darkblue;display: block; padding: 10px; width: 130px;;">Continue to the task</span></a>
 -->
 </div>
<script>
function sendResults(){
	var jsonOb = new Object();
	jsonOb.time = new Date().getTime() - start;
	jsonOb.id = "graphintro";
	jsonOb.type="-1";
	jsonOb.dataset = "intro";
	var jsonString = JSON.stringify(jsonOb);
	$.post("/ourspaces/provVisEvaluation/send.jsp",{data:jsonString},
			  function(data2) {
			}
	);
	return true;
}
$(document).ready(function(){
	//$("#questions").accordion();
	userID = '<%=session.hashCode()%>';
});
function initProvDisplay(){

	$(".shape").unbind('click.log');
	$(".shape").bind('click.log', function(){
		if(typeof clickedGraph == 'undefined'){
			clickedGraph = true;
			log('log_graphVis(userid,resid, page, actiontype)',userID+',\''+$(this).attr("data-id")+'\',\''+document.URL+'\',\'click\'');
		}
		return true;
	});
	$(".shape .trigger").unbind('click.log');
	$(".shape .trigger").bind('click.log', function(){
		log('log_graphVis(userid,resid, page, actiontype)',userID+',\''+$(this.parentNode).attr("data-id")+'\',\''+document.URL+'\',\'expand\'');		
		return true;
	});
	$(".shape .info").unbind('click.log');
	$(".shape .info").bind('click.log', function(){
		log('log_graphVis(userid,resid, page, actiontype)',userID+',\''+$(this.parentNode).attr("data-id")+'\',\''+document.URL+'\',\'info\'');		
		return true;
	});
	
	
	$(".shape").draggable({
		start: function(event, ui) { 
				if(typeof draggedGraph == 'undefined'){
					draggedGraph = true;
					log('log_graphVis(userid,resid, page, actiontype)',userID+',\''+$(this).attr("data-id")+'\',\''+document.URL+'\',\'drag\'');
				}
				return true;
		   }
	});
	$(".center-container").hover(function(){
		if(typeof logShow == 'undefined'){
			logShow = true;
			log('log_graphVis(userid,resid, page, actiontype)',userID+',\'\',\''+document.URL+'\',\'artifactSpace\'');
		}		
	});
}

$(document).ready(function(){
	jQuery.ajaxSetup({
		async : false
	});
	start = new Date().getTime();
});

</script>
</body>
</html>