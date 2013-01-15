<%@ page language="java" import="java.io.File, java.util.ArrayList,java.util.Scanner, java.io.*,java.util.Random, java.net.*, java.util.Vector, common.*,common.Utility,common.User" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:include page="head.jsp" />
<body>
<%
Integer type =(Integer)session.getAttribute("type");
Boolean complG =(Boolean)session.getAttribute("completedGraph");
Boolean complN =(Boolean)session.getAttribute("completedNLG");
boolean showNLG = false, showG = false;
	//NLG first
	if(type==0 || type == 1){
		if(!complN){
			showNLG = true;
		}
		else{
			showNLG = true;
			showG = true;
		}
	}
	//Graph first
	else if(type==2 || type == 3){
		if(!complG){
			showG = true;
		}
		else{
			showNLG = true;
			showG = true;
		}
	}
	String method = "";
if(showNLG && showG)
	method = "Combination";
else if( showG)
	method = "Graph";
else if(showNLG )
	method = "NLG";


int dataset = 0;
String uri = "";
//The first round - type 0 and 2 get 1st dataset
if((!complG && !complN && (type == 0 || type == 2))
		||
// The second round - type 1 and 3 get 1st dataset
((complG || complN) && (type == 1 || type == 3))		
		){
	dataset = 1;
	uri = "http://openprovenance.org/ontology#aec99eb5-38d2-4540-bfac-c36a34b347a2";
}
//The rest gets 2nd dataset.
else {
	dataset = 2;
	uri = "http://openprovenance.org/ontology#7833cce4-a41c-461a-8149-957117519104";
}

%>  

<div class="main" style="">
<%
if(showG && showNLG){
	%><h1>Combination of visualisations evaluation</h1>
<p class="box">Thank you for performing the task using one of the visualisation tools.
 In this section, you are presented with both graphical and natural language visualisation tools. 
You can use either graphical or NLG tools or both to obtain the necessary information to answer the questions.

<br></p>
	<%
}
else if(showG){
	%><h1>Graphical visualisation evaluation</h1>
	<p class="box">We will now begin the evaluation of the Graphical visualisation tool. Please answer the questions on the bottom of the page.
	 All information required to answer the questions can be found using the graph below.
<br></p><%
}
else if(showNLG){
	%><h1>Natural language generation evaluation</h1>
	<p class="box">We will now begin the evaluation of the Natural language generation tool. Please answer the questions on the right hand side of the page.
	 All information required to answer the questions is in the natural language description, which is on the left side of the page.
<br></p><%
}
%>
	<div style="width:100%;float:left;">	
		<div style="width:100%;float:left;">
<%if(showG){
	%>
	<script>
	userID = '<%=session.hashCode()%>';
function initProvDisplay(provVis){

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
	provVis.layout.layout();
}
</script>
	<div id="provenanceResource" style="float:left;width: 100%; height: 470px;overflow: hidden;font-size:12px; margin:0px;">
	<jsp:include page="../testProvenance/provenanceOne.jsp" flush="false">
						<jsp:param value="<%=uri %>" name="resource"/>
						<jsp:param value="false" name="editable"/>
					</jsp:include>		
	</div>
					<%
}
%>
				
	
<%if(showNLG){
	%>
	<script>
	userID = '<%=session.hashCode()%>';
$(document).ready(function(){
	$(".nlg").each(function(){
		var nlgitem=this;
		//Check if the NLG is already bound

		if(typeof $(nlgitem).data("events") != "undefined"
			&& typeof $(nlgitem).data("events").mouseover != "undefined"
			&& $(nlgitem).data("events").mouseover.length >= 1){
			for(var x in $(nlgitem).data("events").mouseover){
				if($(nlgitem).data("events").mouseover[x].namespace.substring(0,4)=="qtip")
					return;
			}
		}
		/*if(typeof $(nlgitem).data("events") != "undefined"
			&& typeof $(nlgitem).data("events").mouseover != "undefined"
			&& $(nlgitem).data("events").mouseover.length >= 2
			&& $(nlgitem).data("events").mouseover[1].namespace.substring(0,4)=="qtip")
			return;*/

	   $(this).qtip({
		   content: 'Some basic content for the tooltip',
	   hide: {
           fixed: true // Make it fixed so it can be hovered over
        },
       position: { adjust: { x: -10, y: -10 } },
	   style: {
           padding: '10px', // Give it some extra padding
           //name: 'cream',
           tip: { color: 'black' } 
        },
        api: {
	         // Retrieve the content when tooltip is first rendered
	         onRender: function()
	         {
	        	
	        	
	        	
	        	var self = this;
            $.ajax({
				type: 'GET',
				url: '/ourspaces/LiberRestServlet', 
				data: { resourceID: $(nlgitem).attr("rel") },
				dataType : "html",
				async : false,
				success : function(str, errorThrown) {
					
					
					self.updateContent(str);
				}
			});
            annotateTerms();
	         }
	         }
	});
	   var y = 0;
	   
});

	$("a.liber2").live('click',function() {
		var anchor = this;
		var did = $(this).attr("rel");

		// if the div containing the text hasn't been set yet:
		if( $("div#"+did).is(':empty') ) {

			// call LiberRestServlet to obtain the textual description of the resource:
			$.ajax({
				type: 'GET',
				url: $(this).attr("href"), 
				dataType : "html",
				async : false,
				success : function(html, errorThrown) {
					$("div#"+did).html(html);
					$("div#"+did).hide();
					return false;
				}
			});
		}
		
		// show/hide the text:
		$("div#"+did).slideToggle("fast");
		return false;
	});
});

$(document).ready(function(){
	$.ajax({
		type: 'GET',
		url: "/ourspaces/LiberRestServlet?resourceID=" + "<%=URLEncoder.encode(uri,"UTF-8")%>&Property=provenance", 
		dataType : "html",
		async : false,
		success : function(html, errorThrown) {
			$("div#nlg").html(html);
		}
	});
});
</script>
	
	<div style="float:left;width: 400px; font-size:20px; margin:10px; background-color: white;">
					<div id=nlg style="     color: black;font-size: 16px;padding: 10px;float:left;height:100%;"></div>
	</div>	<%
}
%>
	<div style="float:left;margin:10px;">
	<form ACTION="evaluation.jsp"  METHOD="POST">
	<div id="questions" style="margin: 0 0 10px;width: 430px;height: 100%;display:block">
	  <%
	  if(dataset==1){
		  %><jsp:include page="dataset1.jsp" /><%
	  }
	  else if (dataset ==2){
		  %><jsp:include page="dataset2.jsp" /><%		  
	  }		  
	  %>
	 </div>
	 
	 
    <INPUT name="<%=method %>" TYPE="hidden" VALUE="true">
    <INPUT name="id" TYPE="hidden" VALUE="<%=method %>">
    <INPUT name="type" TYPE="hidden" VALUE="<%=type %>">
    <INPUT name="dataset" TYPE="hidden" VALUE="<%=dataset %>">
    <INPUT id="time" name="time" TYPE="hidden" VALUE="0">
    <INPUT onclick="var time = new Date().getTime() - start;$('#time').val(time);return true;" style="cursor: pointer;text-decoration: underline;background-color: darkblue;border: 0 none;border-radius: 5px 5px 5px 5px;color: white;float: right;font-family: times;font-size: 14px;padding: 10px;" TYPE="SUBMIT" VALUE="Continue">
    </form> 
		<!-- <a id="next" style="color:white;" href="evaluation.jsp?<%=method %>=true" onclick="sendResults();return true;"><span style="border-radius:5px;padding:10px;background-color:darkblue;float:right;">Continue</span></a>     
	 -->
	 
	 </div>    
	</div>     
<script>
function sendResults(){
	var jsonOb = new Object();
	jsonOb.time = new Date().getTime() - start;
	jsonOb.id = "<%=method%>"; 
	jsonOb.type="<%=type%>";
	jsonOb.q1 = $('input:radio[name=q1]:checked').val();
	jsonOb.q2 = $('input:radio[name=q2]:checked').val();
	jsonOb.q3 = $('input:radio[name=q3]:checked').val();
	jsonOb.q4 = $('input:radio[name=q4]:checked').val();
	jsonOb.dataset = "<%=dataset%>";
	var jsonString = JSON.stringify(jsonOb);
	$.post("/ourspaces/provVisEvaluation/send.jsp",{data:jsonString},
			  function(data2) {
			}
	);
	return true;
}
$(document).ready(function(){
	jQuery.ajaxSetup({
		async : false
	});
	start = new Date().getTime();
});

</script> 
</div>    
</body>
</html>