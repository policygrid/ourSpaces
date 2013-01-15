

function compareValues(val1, val2){
	for(var x in val1){
		var p1 = val1[x];
		//Geo property
		if($.isArray(p1)){
			var found = false;
			for(var y in val2){
				var p2 = val2[y];
				if($.isArray(p2)){
					if(p1[0]==p2[0]&&
						p1[1]==p2[1]&&
						p1[2]==p2[2])
						found = true;
						break;
				}
			}
			if(!found)
				return false;
		}
		else if(val2.indexOf(p1)==-1)
			return false;
	}
	for(var x in val2){
		var p2 = val2[x];
		if($.isArray(p2)){
			var found = false;
			for(var y in val1){
				var p1 = val1[y];
				if($.isArray(p1)){
					if(p1[0]==p2[0]&&
							p1[1]==p2[1]&&
							p1[2]==p2[2])
							found = true;
							break;
				}
			}
			if(!found)
				return false;
		}
		else if(val1.indexOf(p2)==-1)
			return false;
	}
	return true;
}
function updateResource() {
	showUploadDialog({
		resourceId : artifactId,
		resourceType : artifactType,
		
		onClose:function(event, ui){
			//If the update was good, lets forward to the new page.
			if (typeof uploadedResource != 'undefined' && uploadedResource != null) {
				//Reload the whole page.
				$.cookie('artifact_changed', getChangedValues());
				window.location.href = "./artifact_new.jsp?id="+escape(getLocalName(uploadedResource));			
				/*properties = copy;
				emphasizeChangedValues();
				
				//$.get("./artifact_new.jsp?id="+escape(uploadedResource), function(data) {
				$.get("./artifactSpace/resourceInformation.jsp?id="+escape(uploadedResource), function(data) {
					//Backup the properties as they will be discarded on loading of the page.
					var copy = copyProperties(properties);
					$("body").html(data);
					properties = copy;
					emphasizeChangedValues();
				});*/
				//
			}		
	}});

	/*
	$.get("./uploadResourceNew.jsp?id="+escape(artifactId), function(data) {
		//Trim the data.
		var data = data.replace(/^\s+|\s+$/g, '');	
		
		div.html(data);
		div.appendTo("body");
		$( "#uploadResource" ).dialog({
			width: 946,
			height: 600,
			modal: true,
			close: function(event, ui) { 	
				$( "#uploadResource" ).remove();
			}
		});
		initUpload();
	}); */

}

function getChangedValues(){
	var cookie = "";
	for(var x in activeDialog.properties){
		var pNew = activeDialog.properties[x];	
		var found = false;
		for(var y in backProperties){
			var pOld = backProperties[y];
			if(pOld.idProperty == pNew.idProperty){
				found = true;
				if(!compareValues(pOld.value,pNew.value)){
					cookie+=pNew.property+"|";
				}
			}
		}
		if(!found){
			cookie+=pNew.property+"|";
		}
	}
	for(var x in activeDialog.commonProperties){
		var pNew = activeDialog.commonProperties[x];	
		var found = false;
		for(var y in backCommonProperties){
			var pOld = backCommonProperties[y];
			if(pOld.idProperty == pNew.idProperty){
				found = true;
				if(!compareValues(pOld.value,pNew.value)){
					cookie+=pNew.property+"|";
				}
			}
		}
		if(!found){
			cookie+=pNew.property+"|";
		}
	}
	return cookie;
}

function emphasizeChangedValues(){
	var cookie = $.cookie('artifact_changed');
	if(typeof cookie == 'undefined' || cookie == null ||cookie == "")
		return;
	var array = cookie.split("|");
	for(var x in array){
		var prop = array[x];
		$("tr#"+prop+"row").css("background","none repeat scroll 0 0 #CCFFCC");
		$("tr#"+prop+"row td").css("border","1px dashed green");
	}
	//Delete the cookie afterwards.
	$.cookie('artifact_changed','');
}

function emailArtifact(artifactID)
{

    var email = document.getElementById(place+'em').value;
    var firstname = document.getElementById(place+'fn').value;
    var lastname = document.getElementById(place+'ln').value;
  
	var xmlHttpRequest=init();

	  function init(){

	if (window.XMLHttpRequest) {
	           return new XMLHttpRequest();
	       } else if (window.ActiveXObject) {
	           
	           return new ActiveXObject("Microsoft.XMLHTTP");
	       }

	}
  	
	xmlHttpRequest.open("GET", "AddPerson?email="+email+"&firstname="+firstname+"&lastname="+lastname, true);
	xmlHttpRequest.onreadystatechange=function() {
		if(xmlHttpRequest.readyState==4){
	    	   if(xmlHttpRequest.status==200){

	    		   var xmlMessage=xmlHttpRequest.responseXML;

	    	    	var personID=xmlMessage.getElementsByTagName("personID")[0].firstChild.nodeValue;
	    	    	var label=xmlMessage.getElementsByTagName("label")[0].firstChild.nodeValue;
//   	    	    	alert(personID+" is alive !");

	    	    	if(personID != ""){

	    	

	    	        	document.getElementById(place+'r').value = random + '_' + personID;
	    	    		document.getElementById(place+'gah').style.display = 'block';
	    	    		document.getElementById(place+'l').style.display = 'block';
	    	        	document.getElementById(place+'in').style.color = 'green';
	    	    		document.getElementById(place+'in').value = label;
	    	        	hideResourceSearch(place);

	    	    		
	    	    	}
    	    	   
	    	   }
		}


	};
	
	xmlHttpRequest.send(null);
}

function edit(id, subject, predicate, object) {
	var newObject = document.getElementById(id).value;
	var xmlHttpRequest = init();
	function init() {
		if (window.XMLHttpRequest) {
			return new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			return new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	xmlHttpRequest.open("GET", "ResourceBrowse?action=replace&subject="
			+ encodeURIComponent(subject) + "&predicate="
			+ encodeURIComponent(predicate) + "&oldobject="
			+ encodeURIComponent(object) + "&newobject="
			+ encodeURIComponent(newObject), true);
	xmlHttpRequest.onreadystatechange = function() {
		if (xmlHttpRequest.readyState == 4) {
			if (xmlHttpRequest.status == 200) {
				var xmlMessage = xmlHttpRequest.responseXML;
				var confirm = xmlMessage.getElementsByTagName("message")[0].firstChild.nodeValue;
				if (confirm != "") {
					document.getElementById(id + "_div").innerHTML = "<p><span style=\"width:300px;\">Saved</span><span style=\"padding-left:400px; padding-top:2px;\"><a href=\"#\" onclick=\"clearSaved("
							+ id
							+ "); return false;\"><img src=\"images/clear.png\" /></a></span></p>";
					document.getElementById(id + "_span").innerHTML = newObject;
				}
			}
		}
	};
	xmlHttpRequest.send(null);
}

function del(id, subject, predicate, object) {
	var xmlHttpRequest = init();
	function init() {
		if (window.XMLHttpRequest) {
			return new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			return new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	xmlHttpRequest.open("GET", "ResourceBrowse?action=delete&subject="
			+ encodeURIComponent(subject) + "&predicate="
			+ encodeURIComponent(predicate) + "&object="
			+ encodeURIComponent(object), true);
	xmlHttpRequest.onreadystatechange = function() {
		if (xmlHttpRequest.readyState == 4) {
			if (xmlHttpRequest.status == 200) {
				var xmlMessage = xmlHttpRequest.responseXML;
				var confirm = xmlMessage.getElementsByTagName("message")[0].firstChild.nodeValue;
				if (confirm != "") {
					//document.getElementById(id+"_div").innerHTML = "<p><span style=\"width:300px;\">Saved</span><span style=\"padding-left:400px; padding-top:2px;\"><a href=\"#\" onclick=\"clearSaved("+id+"); return false;\"><img src=\"images/clear.png\" /></a></span></p>";
					document.getElementById(id + "_span").innerHTML = "<p>Deleted</p>";
				}
			}
		}
	};
	xmlHttpRequest.send(null);
}

function editRes(id, subject, predicate, object, newValue) {
	var newObject = document.getElementById(id).value;
	var xmlHttpRequest = init();
	function init() {
		if (window.XMLHttpRequest) {
			return new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			return new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	xmlHttpRequest.open("GET", "ResourceBrowse?action=replace&subject="
			+ encodeURIComponent(subject) + "&predicate="
			+ encodeURIComponent(predicate) + "&oldobject="
			+ encodeURIComponent(object) + "&newobject="
			+ encodeURIComponent(newObject), true);
	xmlHttpRequest.onreadystatechange = function() {
		if (xmlHttpRequest.readyState == 4) {
			if (xmlHttpRequest.status == 200) {
				var xmlMessage = xmlHttpRequest.responseXML;
				var confirm = xmlMessage.getElementsByTagName("message")[0].firstChild.nodeValue;
				if (confirm != "") {
					document.getElementById(newValue + "_span").innerHTML = document
							.getElementById(newValue + "l").innerHTML;
					document.getElementById(id + "_div").innerHTML = "<p><span style=\"width:300px;\">Saved</span><span style=\"padding-left:400px; padding-top:2px;\"><a href=\"#\" onclick=\"clearSaved('"
							+ id
							+ "'); return false;\"><img src=\"images/clear.png\" /></a></span></p>";
				}
			}
		}
	};
	xmlHttpRequest.send(null);
}

function createDiv(user, content, date) {
	var formID = Math.floor(Math.random() * 100000);
	var divTag = document.createElement("div");
	divTag.id = formID;
	divTag.style.position = 'relative';
	document.getElementById('comments').appendChild(divTag);
	divTag.innerHTML = tag('forms/jsp/newcomment.jsp?content=' + content
			+ '&user=' + user + '&date=' + date, formID);
}

function editResource(namespace, property, resourceNamespace, resourceName, id) {
	document.getElementById("f" + id + "r_div").style.display = 'block';
	jQuery.get("forms/jsp/resource.jsp?edit=true&label="
			+ encodeURIComponent(property) + "&namespace="
			+ encodeURIComponent(namespace) + "&resourceNamespace="
			+ encodeURIComponent(resourceNamespace) + "&resourceName="
			+ encodeURIComponent(resourceName) + "&property="
			+ encodeURIComponent(property) + "&optional=false&id="
			+ encodeURIComponent(id), function(data) {
		document.getElementById(id).innerHTML = data;
	});

}

function doDelete(resource, title, page) {
	jQuery.facebox(function() {
		jQuery.get("deleteResource.jsp?title=" + title + "&resource="
				+ resource + "&page="+page+"&id=<%=temp%>", function(data) {
			jQuery.facebox(data);
		});
	});
}

function showSearch(id) {
	alert(document.getElementById(id).value);

	edit(id, subject, predicate, object);
}

function showbox(id) {
	document.getElementById(id + "_div").style.display = 'block';
}

function clearSaved(id) {
	document.getElementById(id + "_div").style.display = "none";
}