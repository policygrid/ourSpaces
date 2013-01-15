function unhide(divID) {
  var item = document.getElementById(divID);
  if (item) {
    item.className=(item.className=='hidden')?'unhidden':'hidden';
  }
}

function doMoveFile(file, resource)
{
	var res = encodeURIComponent(resource);
	
	jQuery.facebox(function() { 
	  jQuery.get("movefolder.jsp?fileID="+file+"&resource="+res, function(data) {
	    jQuery.facebox(data);
	  });
	});
}

function moveFile(fileID, folder, optSubFolder) {
	window.location='moveFileUser.jsp?fileID='+fileID+'&folder='+folder+'&optSubFolder='+optSubFolder;
}

function showEmail(id){
	//Delete previous warnings.
	$("#"+id+"email .warningEmail").remove();
	//Show the dialog.
	$( "#"+id+"email" ).dialog({
		width:340,
		height:150,
		modal: true,
		dialogClass : "alertDialog"
	});	
}


function showTag(id){
	//Delete previous warnings.
	//Show the dialog.
	$( "#"+id+"tag" ).dialog({
		width:340,
		height:150,
		modal: true,
		dialogClass : "alertDialog"
	});	
}

function email(id) {
	var email = 'email' + id;
	var resID = 'resID' + id;
	var url = 'url' + id;
	var emailUser = document.getElementById(email).value;
	var resource = document.getElementById(resID).value;
	var URI = document.getElementById(url).value;
	
	
	var xmlHttpRequest=init(emailUser, resource, URI);

 	function init(e, r, u){

		if (window.XMLHttpRequest) 
		{
			return new XMLHttpRequest();
		} 
		else if (window.ActiveXObject)
		{
			return new ActiveXObject("Microsoft.XMLHTTP");
		}
		
	}
		

	xmlHttpRequest.open("GET", "/ourspaces/emailResource?fileID="+ encodeURIComponent(URI) + "&resource=" + encodeURIComponent(resource) + "&email=" + encodeURIComponent(emailUser), true);
	xmlHttpRequest.onreadystatechange=processRequest;
	xmlHttpRequest.send(null);
		
	function processRequest(){
		
		if(xmlHttpRequest.readyState==4){
			if(xmlHttpRequest.status==200){
				processResponse();
			}
			else{
				//	Error, append a message
				$( "#"+id+"email" ).prepend("<div class='warningEmail'>There was an error, please check the email address and try later.</div>");
				return;
			}
		}
	}
		
	function processResponse(){
		$( "#"+id+"email" ).dialog("close");
		return;
	}
}

function add(id) {
	
	var tagID = 'tag' + id;
	var resID = 'resID' + id;
	var userID = 'id' + id;
	var tagName = document.getElementById(tagID).value;
	var resource = document.getElementById(resID).value;
	var user = document.getElementById(userID).value;
	
	
	var xmlHttpRequest=init(tagName, user);

 	function init(tag, usr){

		if (window.XMLHttpRequest) 
		{
			return new XMLHttpRequest();
		} 
		else if (window.ActiveXObject)
		{
			return new ActiveXObject("Microsoft.XMLHTTP");
		}
		
	}
		
	xmlHttpRequest.open("GET", "/ourspaces/Tagger?tag="+ encodeURIComponent(tagName) + "&resource=" + encodeURIComponent(resource) + "&user=" + encodeURIComponent(user), true);
	xmlHttpRequest.onreadystatechange=processRequest;
	xmlHttpRequest.send(null);
		
	function processRequest(){
		
		if(xmlHttpRequest.readyState==4){
			if(xmlHttpRequest.status==200){
			
				processResponse();
		
			}
		}
	}
		
	function processResponse(){
		$( "#"+id+"tag" ).dialog("close");
	}
}

function getNodeTimestamp(node){
	for (var j=0;j<node.properties.length;j++){
        var prop = node.properties[j];
    	if("http://www.policygrid.org/ourspacesVRE.owl#timestamp"!=prop.name)
    		continue;
        return 1.0*prop.value;
    }
	return null;
}    	
function initProvDisplay(){
	//Emphasize main artifact
	$("#"+getLocalName(artifactId)).addClass("focused");//css("border","3px solid black").css("opacity","1");
	//$("#"+getLocalName(artifactId)+" p").css("font-weight","bold");    
	//Append date to each node.	
	for(var x in provVis.core.graph){
			var node = provVis.core.graph[x];
			var t = getNodeTimestamp(node);
			if(t != null){
				var date = new Date(t);
				/*var hours = date.getHours();
				var minutes = date.getMinutes();
				var seconds = date.getSeconds();					
				var year = date.getFullYear();			
				var month = date.getMonth()+1;			
				var day = date.getDate();
				var formattedTime = year + '/' + month + '/' + day + ' '+ hours + ':' + minutes + ':' + seconds;;
				*/
				var formattedTime = date.toLocaleString();
				$("#"+getLocalName(node.id)+" p").attr("title",formattedTime);
				
			}
	}
	//provVis.layout.layout();
	//Do the nlg for icons.
	$(".icon").each(function(){
		if(typeof $(this).data("events") != "undefined"
			&& typeof $(this).data("events").mouseover != "undefined"
			&& $(this).data("events").mouseover.length >= 1){
			for(var x in $(this).data("events").mouseover){
				if($(this).data("events").mouseover[x].namespace.substring(0,4)=="qtip")
					return;
			}
		}
		   $(this).qtip({
	  	    content: {
		      url: '/ourspaces/LiberRestServlet', 
		      data: { resourceID: $(this).attr("rel") },
		      method: 'get'
		   },
		   hide: {
	           fixed: true // Make it fixed so it can be hovered over
	        },
	       position: { adjust: { x: -10, y: -10 } },
		   style: {
	           padding: '10px', // Give it some extra padding
	           //name: 'cream',
	           tip: { color: 'black' } 
	        }
		});
	});

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

	function showProvenance(uploadedResource){
		//Successfull upload
		if(typeof uploadedResource != "undefined" && uploadedResource != null){
			provVis.core.graph = [];
			/*var div = $("<div>");
			div.attr("id","provenanceEditing");
			div.css("overflow","visible");
			div.appendTo("body");

			if(typeof jsPlumb == 'undefined'){
				jsPlumb = window.jsPlumb = new jsPlumbInstance();
			}*/
			window.location = "/ourspaces/testProvenance/provenanceEdit.jsp?resource="+escape(uploadedResource);
			/*var tmpCanvas = jsPlumb.canvas;
			$.get("testProvenance/provenanceEdit.jsp?resource="+escape(uploadedResource), function(data) {
				//var data = data.replace(/^\s+|\s+$/g, '') ;
				$("#provenanceEditing").html(data);
				var width = $("#provenanceEditing  #left-container").width();
				width += $("#provenanceEditing  .center-container").width()+80;
				width = 900;
				//alert(width);
				$( "#provenanceEditing" ).dialog({
					width:width,
					resizable: true,
					dialogClass : "alertDialog",
					open:function(event, ui) {
						$(".center-container").resizable("disable");
						initJsPlumb();
						initProvenance();
						provenanceInit = true;
						
						//Load the provenance of resource from request
						if(resource != ''){
							loadProvenance(resource);
						}
					},
					close: function(event, ui) {
						$("#provenanceEditing").remove();
						jsPlumb.canvas = tmpCanvas;
					}
				});
				$(".ui-dialog").resizable({ alsoResize: '.center-container'});
				$(".center-container").resizable("disable");
				//autoOpen: false,  
				//
				//$("#provenance").dialog('open');
			});*/
		}
	}
	function uploadType(type, div, page){
		showUploadDialog({
			baseClass:type,
			onClose :function(event, ui){
				//Reload the myresources box.
				$.get(page, function(data) {
					//Set the data to the right location
					$(div).html(data);
					//var escId = '4d636a81-8fd5-4be7-b287-840a2c3b3c6d';
					//uploadedResource = 'http://openprovenance.org/ontology#4d636a81-8fd5-4be7-b287-840a2c3b3c6d';
					var escId = getLocalName(uploadedResource);
					//Highlight the first resource						
					$("."+escId+" .resourceinfo").css("border","4px dotted darkGreen");
					//Create a balloon with Add provenance suggestion.
					var balloon = $("<div>");
					balloon.attr("id","balloon");										
					balloon.html("<a href=\"/ourspaces/testProvenance/provenanceEdit.jsp?resource="+escape(uploadedResource)+"\">Add provenance</a>");
					balloon.css("border-radius","10px");
					balloon.css("background-color","wheat");
					balloon.css("float","right");
					balloon.css("height","1.4em");
					balloon.css("padding","1px 8px");
					balloon.css("position","relative");
					balloon.css("right","1px");
					$("."+escId+" .resourceinfo").append(balloon);
					//Reset the global variables for next upload.
					//We can leave resourceType so the next resource uploaded would be the same type.
					uploadedResource = null;
					fileURI = null;
				});
			}
		});
	}
	function upload(){

		/*jQuery.facebox(function() { 
		  jQuery.get("uploadResourceNew.jsp", function(data) {
		    jQuery.facebox(data);
				initUpload();
		  });
		});*/
		showUploadDialog({
			onClose :function(event, ui){
				//Reload the myresources box.
				$.get("/ourspaces/boxes/myresources.jsp", function(data) {
					//Set the data to the right location
					$("#myresources #resourcesList").html(data);
					//var escId = '4d636a81-8fd5-4be7-b287-840a2c3b3c6d';
					//uploadedResource = 'http://openprovenance.org/ontology#4d636a81-8fd5-4be7-b287-840a2c3b3c6d';
					var escId = getLocalName(uploadedResource);
					//Highlight the first resource						
					$("."+escId+" .resourceinfo").css("border","4px dotted red");
					//Create a balloon with Add provenance suggestion.
					var balloon = $("<div>");
					balloon.attr("id","balloon");						
					balloon.html("<a href=\"/ourspaces/testProvenance/provenanceEdit.jsp?resource="+escape(uploadedResource)+"\">Add provenance</a>");
					balloon.css("border-radius","10px");
					balloon.css("background-color","wheat");
					balloon.css("float","right");
					balloon.css("height","1.4em");
					balloon.css("padding","1px 8px");
					balloon.css("position","relative");
					balloon.css("right","1px");
					$("."+escId+" .resourceinfo").append(balloon);
					//Reset the global variables for next upload.
					//We can leave resourceType so the next resource uploaded would be the same type.
					uploadedResource = null;
					fileURI = null;
				});
			}
		});
		/*
		$.get("uploadResourceNew.jsp", function(data) {
			//Trim the data.
			var data = data.replace(/^\s+|\s+$/g, '') ;
			var div = $("<div>");
			div.attr("id","uploadForm");
			div.appendTo("body");
			div.html(data);
			div.width(750);
			$( "#uploadForm" ).dialog({
				width:740,
				resizable: true,
				modal: true,
				autoOpen: false,  
				height: 600,
				dialogClass : "alertDialog",
				close: function(event, ui) {
				}
			});
			$("#uploadForm").dialog('open');
			initUpload();
		});*/
	      /*setTimeout(function()
	      {
	    	  $("."+escId+" .resourceinfo").css('border', 'none');
	    	  $("#balloon").remove();
	      }, 5000);*/
		
	}