<%@ page language="java" import="java.io.File, java.util.ArrayList,java.util.Scanner, java.io.*,java.util.Random, java.net.*, java.util.Vector, common.*,common.Utility,common.User" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:include page="head.jsp" />
<body>
<%

Integer type =(Integer)session.getAttribute("type");
int dataset = 0;
String uri = "";
if(type==0 || type == 2){
	dataset = 1;
	uri = "http://openprovenance.org/ontology#aec99eb5-38d2-4540-bfac-c36a34b347a2";
}
else {
	dataset = 2;
	uri = "http://openprovenance.org/ontology#7833cce4-a41c-461a-8149-957117519104";
}

%>  

<script>
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
<div class="main" style="">
<h1>Combination of visualisations evaluation</h1>
<p>In this task, you can use both the graphical and textual visualisations of the same provenance record.
<br></p>
	<div style="width:100%;float:left;">
	
		<div style="width:100%;float:left;">
	<div id="provenanceResource" style="float:left;width: 100%; height: 470px;overflow: hidden;font-size:12px; margin:0px;">

					<jsp:include page="../testProvenance/provenanceOne.jsp" flush="false">
						<jsp:param value="<%=uri %>" name="resource"/>
						<jsp:param value="false" name="editable"/>
					</jsp:include>	
	</div>
	
	<div style="float:left;width: 400px; font-size:20px; margin:10px; background-color: white;">
					<div id=nlg style="     color: black;font-size: 16px;padding: 10px;float:left;height:100%;"></div>
	</div>
	
	<div style="float:left;margin:10px;"> 
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
		<a id="next" style="color:white;" href="evaluation.jsp?completedNLG=true" onclick="sendResults();return true;"><span style="border-radius:5px;padding:10px;background-color:darkblue;float:right;">Continue</span></a>     
	</div>    
	</div>     
<script>


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

function sendResults(){
	var jsonOb = new Object();
	jsonOb.time = new Date().getTime() - start;
	jsonOb.id = "combination";
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