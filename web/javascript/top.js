	
		/**
		 * Returns the part of the URI without the namespace
		 * @param uri
		 * @returns
		 */
		function getLocalName(uri){
			if(uri == null || uri == "")
				return "";
			if(uri.indexOf('#')>0)
				return uri.substring(uri.indexOf('#')+1);
			else if(uri.indexOf('/')>0)
				return uri.substring(uri.lastIndexOf('/')+1);
			else
				return uri;
		}
		
		/**
		 * Returns the namespace part of the URI
		 * @param uri
		 * @returns
		 */
		function getNamespace(uri){
			if(uri == null || uri == "")
				return "";
			if(uri.indexOf('#')>0)
				return uri.substring(0, uri.indexOf('#')-1);
			else if(uri.indexOf('/')>0)
				return uri.substring(0, uri.indexOf('/')-1);
		}
		
		var NLGInitialised = false;
		// Add listener that will call the NLG servlet when a user clicks on a anchored resource
		// (in some pages .live doesn't work and .bind() should be used instead (e.g. on map space))
		   var addNLGdivListener = function() {

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
				
				if(NLGInitialised)
				   return;
			   NLGInitialised = true;
				   
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
			
//				$(document).delegate("a.liber2", "click", function(){
//					var anchor = this;
//					var did = $(this).attr("rel");
//
//					// if the div containing the text hasn't been set yet:
//					if( $("div#"+did).is(':empty') ) {
//
//						// call LiberRestServlet to obtain the textual description of the resource:
//						$.ajax({
//							type: 'GET',
//							url: $(this).attr("href"), 
//							dataType : "html",
//							async : false,
//							success : function(html, errorThrown) {
//								$("div#"+did).html(html);
//								$("div#"+did).hide();
//							}
//						});
//					}
//					// show/hide the text:
//					$("div#"+did).slideToggle("fast");
//					return false;
//				});
		   }

		/**
		 * Initializes all css classes with functionality.
		 */
		var initClasses = function(){
			var in_panel = false;
			var in_field = false;

			addNLGdivListener();


			$("a.liber").unbind('click');
			$("a.liber").bind('click',function() {
				var anchor = this;
				var did = $(this).attr("rel");
				$.ajax({
					type: 'GET',
					url: "/ourspaces/LiberRestServlet?resourceID=" + $(this).attr("href"), 
					dataType : "html",
					async : false,
					success : function(html, errorThrown) {
						$("div#"+did).html(html);
					}
				});
				return false;
			});
			
			
			$(".file_png, .file_jpeg, .file_gif, .file_jpg, .file_bmp").imgPreview({
			    imgCSS: { width: 300 },
			    preloadImages : false
			});
			
			//Log the collapsing of boxes.
			$(".collapse").unbind('click');
			$(".collapse").click(function(){	
				var action = 'expand';
				//If it is now collapsed, it was open before
				if($(this).parents().parents().hasClass("collapsed"))
					action = 'collapse';
				log('log_collapsing(userid,page,boxTitle, action)',userID+',\''+document.URL+'\',\''+$(this).parents().children("h3").text()+'\',\''+action+'\'');		
				return true;
			});
			if(typeof iNettuts != "undefined" && iNettuts != null)
				iNettuts.initButtonsCollapse();
			
			//Check that required fields of popup forms are not null
			$("#target").submit(function(){
			    var isFormValid = true;
			    $("input.required").each(function(){
			        if ($.trim($(this).val()).length == 0){
			            $(this).addClass("highlight");
			            isFormValid = false;
			        }
			        else{
			            $(this).removeClass("highlight");
			        }
			    });

			    //special case for autocomplete: check that the user selected a result from the search
			    if ($("div#membersinputDiv > span").length == 0) {
			    	isFormValid = false;
			    	$("input#membersinput").addClass("highlight");
				} else {
					$("input#membersinput").removeClass("highlight");
				}
			    
			    if (!isFormValid) alert("Please fill in all the required fields");

			    return isFormValid;
			});

			
			//dropdown menus
			 $(".dropdownBox").unbind('mouseover');
			 $(".dropdownBox").unbind('mouseout');
			 $(".dropdownBox").mouseover(function() {
				    $(this).css("background-color","#333333");		    
					$(this).find('a:link').css("color","white");
				    $(this).find('a:visited').css("color","white");
				    $(this).find('a:hover').css("color","white");
				    $(this).find('div.navBarOptions').show();
			  }).mouseout(function(){
					  if(!in_panel) {
						    $(this).css("background-color","");
						    $(this).find('a:link').css("color","#272626");
						    $(this).find('a:visited').css("color","#272626");
						    $(this).find('a:hover').css("color","#272626");
						    $(this).find('div.navBarOptions').hide();
					  }
				  });

			 $(".navBarOptions").unbind('mouseover');
			 $(".navBarOptions").mouseover(function(){
				 $(".navBarOptions").focus();
				    in_panel = true;
			    }).mouseout(function(){
			    	in_panel = false;
			    	   /*	$(this).parent().css("background-color","");
					    $(this).parent().find('a:link').css("color","#272626");
					    $(this).parent().find('a:visited').css("color","#272626");
					    $(this).parent().find('a:hover').css("color","#272626");
					    $(this).parent().find('div.navBarOptions').hide();
			    	*/
			    });	 
			 
			 $(".dropdownBoxFixed").unbind('mouseover');
			 $(".dropdownBoxFixed").unbind('mouseout');
			 $(".dropdownBoxFixed").mouseover(function() {
				    $(this).css("background-color","#333333");		    
					$(this).find('a:link').css("color","white");
				    $(this).find('a:visited').css("color","white");
				    $(this).find('a:hover').css("color","white");
			  }).mouseout(function(){
						    $(this).css("background-color","");
						    $(this).find('a:link').css("color","#272626");
						    $(this).find('a:visited').css("color","#272626");
						    $(this).find('a:hover').css("color","#272626");
				  });

			

			 $(".navBarOptions").find('a').unbind('click');
			 $(".navBarOptions").find('a').click(function() {
				    //alert("click" + $(this));
				    $(".dropdownBox").css("background-color","");
				    $(".dropdownBox").find('a:link').css("color","#272626");
				    $(".dropdownBox").find('a:visited').css("color","#272626");
				    $(".dropdownBox").find('a:hover').css("color","#272626");
				    $(".dropdownBox").find('div.navBarOptions').hide();
			  });
			 
			 
				
			 
			 

			 $(".popup_blog_dialog").unbind('click');
			 $(".popup_blog_dialog").click(function() {
				    var $link = $(this);
					var $dialog = $('<div></div>')
						.load($link.attr('href'))
						.dialog({
							autoOpen: false,
							title: $link.attr('title'),
							modal: true,
							height: 'auto',
				            width: 'auto' ,
				            position: [(($(window).width()/2)-300),50],
				            open: function(event, ui) {
				            	setTimeout(
				      "$('#post1').ourSpacesProvenanceTagging({provID: 'provenancelinks', discID: 'discourse'})"
				            			,100);
				            	       	
				            },
				            create:function(event, ui){
					   			/* $("#post1").ourSpacesProvenanceTagging({
					   				 provID: "prov",
					   				 discID: "disc"
					   			 });*/
				            },
						    buttons: {
						        "Submit": function() {
						          $("#target").submit();
						  	    }
						    }//END OF BUTTONS
						
						});    			    
					$dialog.dialog('open');
					return false;
			});

			 $(".popup_dialog").unbind('click');
			 $(".popup_dialog").click(function() {
				    var $link = $(this);
					var $dialog = $('<div></div>')
						.load($link.attr('href'))
						.dialog({
							autoOpen: false,
							title: $link.attr('title'),
							modal: true,
							height: 'auto',
				            width: 'auto' ,
				            position: [(($(window).width()/2)-300),50],
				            open: function(event, ui) {
				            	$('#post1').ourSpacesProvenanceTagging();
				            },
						    buttons: {
						        "Submit": function() {
						          $("#target").submit();
						  	    }
						    },//END OF BUTTONS
				            close:function(event, ui) {
						          $(this).remove();				            	
				            }
						
						});   			    
					$dialog.dialog('open');
					return false;
			});
			 $(".popup_dialog_ajax").unbind('click');
			 $(".popup_dialog_ajax").click(function() {
				    var $link = $(this);
					var $dialog = $('<div></div>')
						.load($link.attr('href'))
						.dialog({
							autoOpen: false,
							title: $link.attr('title'),
							modal: true,
							height: 'auto',
				            width: 'auto' ,
				            position: [(($(window).width()/2)-300),50],
				            open: function(event, ui) {
				            	$('#post1').ourSpacesProvenanceTagging();
				            },
						    buttons: {
						        "Submit": function() {
						        	$.ajax({
						                type: $("#target").attr("method"),
						                url: $("#target").attr("action"),
						                data: $("#target").serializeArray(),
						                success: function() {
						                }
						          });
						        	$dialog.dialog("close");
						        	$dialog.remove();
						          //$("#target").submit();
						  	    }
						    }//END OF BUTTONS
						
						});   			    
					$dialog.dialog('open');
					return false;
			});

			 $(".popup_dialog_simple").unbind('click');
			 $(".popup_dialog_simple").click(function() {
				    var $link = $(this);
					var $dialog = $('<div></div>')
						.load($link.attr('href'))
						.dialog({
							autoOpen: false,
							title: $link.attr('title'),
							modal: true,
							height: 'auto',
				            width: 'auto' ,
				            position: [(($(window).width()/2)-300),50],
				            open: function(event, ui) {
				            	$('#post1').ourSpacesProvenanceTagging();
				            },
						    buttons: {
						    }//END OF BUTTONS
						
						});   			    
					$dialog.dialog('open');
					return false;
			});

			 $(".popup_comment_dialog").unbind('click');
			 $(".popup_comment_dialog").click(function() {
				    var $link = $(this);
					var $dialog = $('<div></div>')
						.load($link.attr('href'))
						.dialog({
							autoOpen: false,
							title: $link.attr('title'),
							modal: true,
							height: 'auto',
				            width: 'auto' ,
				            position: [(($(window).width()/2)-300),50],
				            open: function(event, ui) {
				            	$('#post1').ourSpacesProvenanceTagging();        	
				            },
						    buttons: {
						        "Add comment": function() {
						        	addComment();
						  	    }
						    }//END OF BUTTONS
						
						});  			    
					//$dialog.dialog('open').css('left',($(window).width()-$(this).outerWidth())/ 2 + 'px');
					$dialog.dialog('open');
					return false;
			});
		}
		
		
		jQuery(document).ready(function($) {			
			$("#inputString").keydown(function() {
				
				clearTimeout( this.searching );
				this.searching = setTimeout(function() {
						$('#suggestions').fadeOut(); // Hide the suggestions box

					if($("#inputString").val().length > 0) {						
						$.post("/ourspaces/search/quicksearch.jsp", {queryString: ""+$("#inputString").val()+""}, function(data) { 
								if($(data).get(0).innerHTML != $("#inputString").val())
									return;
								$('#suggestions').fadeIn(); // Show the suggestions box
								$('#suggestions').html(data); // Fill the suggestions box							
						});
					}
					/*if ( this.term != this.element.val() ) {
						this.selectedItem = null;
						this.search( null, event );
					}*/
				}, 350 );
			}).blur(fadeOutSearchBox);
			//onkeyup="lookup(this.value);"
			
		  $('a[rel*=facebox]').facebox();
		  jQuery('img.photo').imageFrame('soft');
		  jQuery('#spacescarousel').css("display","block");
		  jQuery('#spacescarousel').jcarousel({
		  	wrap: 'circular'
		  });

   
/*
  $(".nlg").simpletip({
      position: 'absolute',
	  content: "<img src=\"/ourspaces/images/ajax-loader.gif\" />",
	  offset: [-200,-200],
	  boundryCheck: false,
	  fixed: true,
	  onBeforeShow: function(){
	      // Note this refers to the API in the callback function
	      // this.load();
	      rel = this.getParent().attr("rel");
	      this.load("/ourspaces/LiberRestServlet?resourceID="+rel);
      }
  
  	});
*/
	
	
	
	
		  
 /*$(".nlg").each(function(){
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
		
		  
		  */
	// NLGlog: Logs when a user hover on top of a speech bubble for the first time
	$(".nlg").one("mouseenter", function() {
		//created a var in top.jsp containing the user database ID
		log('log_nlgRequest(userid,resid,nlgtype,depth)', userID + ',\'' + decodeURIComponent($(this).attr("rel")) + '\',' + "\'metadata\', 0");
	});
	

	   
	
	   jQuery(document).ready(function($) {
		//addNLGdivListener();
		
	
		initClasses();
		
		 
	});
	
	
	
	
	
	
//	$(document).ready(function() {
//		$("a.liber2").bind('click',function() {
//			var anchor = this;
//			var did = $(this).attr("rel");
//
//			// if the div containing the text hasn't been set yet:
//			if( $("div#"+did).is(':empty') ) {
//
//				// call LiberRestServlet to obtain the textual description of the resource:
//				$.ajax({
//					type: 'GET',
//					url: $(this).attr("href"), 
//					dataType : "html",
//					async : false,
//					success : function(html, errorThrown) {
//						$("div#"+did).html(html);
//						$("div#"+did).hide();
//					}
//				});
//			}
//			// show/hid the text:
//			$("div#"+did).slideToggle("fast");
//			return false;
//		});
//	});

  
});


	
var vault = null;    


function myBugSubmit(){
	var subject = $("#subject").val();
	var message = $("#message").val();
	var dataString = 'action=addBug&subject='+ subject + '&message=' + message;
 
	if (message!='') {	
		$('#bugSubmit').attr("disabled", true);
	$.ajax({
		type: "POST",
		url: "Controller",
		data: dataString,
		success: function(){
		  alert("Your feedback has been submitted");
		  $("#subject").val('');
		  $("#message").val('');
		  $('#bugSubmit').attr("disabled", false);
		}
	});
	} else {
		alert("Please provide a description")
	}
	
}


function doUploadWidget(location) {
     vault = new dhtmlXVaultObject();             
     vault.setImagePath("/ourspaces/codebase/imgs/");
     vault.setServerHandlers("/ourspaces/UploadHandler.jsp", "/ourspaces/GetInfoHandler.jsp", "/ourspaces/GetIdHandler.jsp");
     vault.onAddFile = function(fileName) {        
          document.getElementById(location+"File").value = fileName;
          return true; 
          };

     vault.setFilesLimit(1); 
     vault.create(location);            
}


function lookup(inputString) {
}

function fadeOutSearchBox() {
	$('#suggestions').fadeOut();
}

function deleteStatus()
{
  
	var xmlHttpRequest=init();

	function init(){

		if (window.XMLHttpRequest) {
	           return new XMLHttpRequest();
	    } else if (window.ActiveXObject) {	           
	           return new ActiveXObject("Microsoft.XMLHTTP");	       
	    }
	}  	
	xmlHttpRequest.open("GET", "DeleteStatus", true);
	xmlHttpRequest.onreadystatechange=function() {
		if(xmlHttpRequest.readyState==4){
	    	   if(xmlHttpRequest.status==200){
	    		   var xmlMessage=xmlHttpRequest.responseXML;
	    	    	var confirm=xmlMessage.getElementsByTagName("message")[0].firstChild.nodeValue;	    	    	
	    	    	if(confirm != ""){
						document.getElementById('status').value = '';	    	    		
	    	    	}    	    	   
	    	   }
		}


	};
	
	xmlHttpRequest.send(null);
}
function addPerson(place, showRole, onClick)
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
	if(email == "" || firstname == "" || lastname == "")
		return;
	xmlHttpRequest.open("GET", "AddPerson?email="+email+"&firstname="+firstname+"&lastname="+lastname, true);
	xmlHttpRequest.onreadystatechange=function() {
		if(xmlHttpRequest.readyState==4){
	    	   if(xmlHttpRequest.status==200){
	    		   var xmlMessage=xmlHttpRequest.responseXML;
	    	    	var personID=xmlMessage.getElementsByTagName("personID")[0].firstChild.nodeValue;
	    	    	var label=xmlMessage.getElementsByTagName("label")[0].firstChild.nodeValue;
	    	    	if(personID != ""){
	    	    		ui = new Object();
	    	    		ui.item = new Object();
	    	    		ui.item.label = label;
	    	    		ui.item.id = personID;
	    	    		eval(onClick);
	    	    		createPersonDiv(ui, place, showRole);
	    	    	}    	    	   
	    	   }
		}
	};
	
	xmlHttpRequest.send(null);
}

var marker;
var fieldid;

function changemarker() {
	if (marker != null) osMapF.removeMarker(marker);
	
    var gridProjection = new OpenSpace.GridProjection();
    
    var lonlat = new OpenLayers.LonLat();
    lonlat.lon = document.getElementById(fieldid + '_l1').value;
    lonlat.lat = document.getElementById(fieldid + '_l2').value;
    
    var pt = gridProjection.getMapPointFromLonLat(lonlat);

	pos = new OpenSpace.MapPoint(pt.lon,pt.lat);
	size = new OpenLayers.Size(30,39);
	offset = new OpenLayers.Pixel(-15,-36);
	infoWindowAnchor = new OpenLayers.Pixel(16,16);
	icon = new OpenSpace.Icon('http://openspace.ordnancesurvey.co.uk/osmapapi/img_versions/img_1.1.0/OS/images/markers/marker-cross-med-blue.png', size, offset, null, infoWindowAnchor);
	marker = osMapF.createMarker(pos, icon, null, null);
	osMapF.setCenter(pos,7);

}

var osMapF, mapOVF, postcodeServiceF;

//Variables for postcode/gazetteer searches
var inputStr, sectorFlag, globalGazArray, locationFound, zoomVal, eastVal, eastValstr, o, da;

//Start of Functions required for postcode/gazetteer searches
//clear search box when clicked on
function clearTextF(event){
	document.getElementById("searchArea").value = "";
	//Stop propagation, so that the marker does not appear under the text box.
	if (!event) var event = window.event;
	event.cancelBubble = true;
	if (event.stopPropagation) 
		event.stopPropagation();
}

function searchPostcodeF(id)
{

	//hide and clear list box
	document.getElementById('selectGaz').style.display='none';
	da = document.getElementById("selectGaz");
	
	document.getElementById
	da.options.length = 0;
	locationFound = 0;
	eastValstr = "";
	
	//clear menu if already populated
	da.options.length = 0;
	sectorFlag = 0;
	
	var query = document.getElementById("searchArea");
	document.getElementById(id+"_locationName").value = query.value;
	inputStr = query.value;
	//document.getElementById("markersCheckBox").checked = false;
	document.getElementById("searchArea").value = "enter a place/postcode";
	
	//ascertain if postcode sector or full postcode
	if (inputStr.length < 5)
	{
	sectorFlag = 1;
	}
	
	//search postcode service
	postcodeServiceF.getLonLat(inputStr, onResultF);
	return;
}

//result of search postcode is passed here
function onResultF(mapPoint)
{
	//set zoom level depending on sector or full postcode
	if  (sectorFlag == 0)
	{zoomVal = 9;}
	else {zoomVal = 5}
	
	//if not a valid PostCode, pass to gazetteer search
	//an eastValStr of length three indicates no match found for postcode
	if (mapPoint != null)
	{
		eastVal = mapPoint.getEasting();
		eastValstr = eastVal.toString();
	}
	
	//no postcode match, so search gazetteer
	if (mapPoint == null || eastValstr.length == 3 )
	{
		var osGaz = new OpenSpace.Gazetteer;
		var gazArray = osGaz.getLocations(inputStr, gazOptionsF)
		
	}
	
	//zoom to postcode
	if (mapPoint != null && eastValstr.length > 3)
	{
	
		var gridProjection = new OpenSpace.GridProjection();
	
		var lonlat = gridProjection.getLonLatFromMapPoint(mapPoint);
		document.getElementById(fieldid + '_l1').value = lonlat.lon;
		document.getElementById(fieldid + '_l2').value = lonlat.lat;
	
		if (marker != null) osMapF.removeMarker(marker);
	
		size = new OpenLayers.Size(30,39);
		offset = new OpenLayers.Pixel(-15,-36);
		infoWindowAnchor = new OpenLayers.Pixel(16,16);
		icon = new OpenSpace.Icon('http://openspace.ordnancesurvey.co.uk/osmapapi/img_versions/img_1.1.0/OS/images/markers/marker-cross-med-blue.png', size, offset, null, infoWindowAnchor);
		marker = osMapF.createMarker(mapPoint, icon, null, null);
		
		osMapF.setCenter(mapPoint, zoomVal);
		locationFound = 1;
		
		document.getElementById("postcode").value = "";
	}
	return false;
}

function gazOptionsF(searchVal)
{
	//if one match found
	if (searchVal.length == 1)
	{
		osMapF.setCenter(searchVal[0].location, 7);
		var gridProjection = new OpenSpace.GridProjection();
		
		var lonlat = gridProjection.getLonLatFromMapPoint(searchVal[0].location);
		document.getElementById(fieldid + '_l1').value = lonlat.lon;
		document.getElementById(fieldid + '_l2').value = lonlat.lat;
		
		if (marker != null) osMapF.removeMarker(marker);
		
		size = new OpenLayers.Size(30,39);
		offset = new OpenLayers.Pixel(-15,-36);
		infoWindowAnchor = new OpenLayers.Pixel(16,16);
		icon = new OpenSpace.Icon('http://openspace.ordnancesurvey.co.uk/osmapapi/img_versions/img_1.1.0/OS/images/markers/marker-cross-med-blue.png', size, offset, null, infoWindowAnchor);
		marker = osMapF.createMarker(searchVal[0].location, icon, null, null);
		
		
		
		locationFound = 1;
	}
	
	//if several choices, create a list box
	if (searchVal != null && searchVal.length > 1)
	{
		locationFound = 1;
		globalGazArray = searchVal;
		o = document.createElement("OPTION");
		o.text= "Select a place";
		da.options.add(o);
		
		//build list box
		for (var i=0 ; i < searchVal.length; i++)
		{
			o = document.createElement("OPTION");
			o.text= searchVal[i].name + ", " + searchVal[i].county;
			da.options.add(o);
		}
		
		//make list box visible
		document.getElementById('selectGaz').style.display='block';
	}
		
	if (locationFound == 0)
	{
		alert("neither postcode or place found");
	}
}

//zoom to item selected from list box
function zoomGazSelF(selObj)
{
osMapF.setCenter(globalGazArray[selObj.selectedIndex-1].location, 7);  

var gridProjection = new OpenSpace.GridProjection();

var lonlat = gridProjection.getLonLatFromMapPoint(globalGazArray[selObj.selectedIndex-1].location);
document.getElementById(fieldid + '_l1').value = lonlat.lon;
document.getElementById(fieldid + '_l2').value = lonlat.lat;

if (marker != null) osMapF.removeMarker(marker);

size = new OpenLayers.Size(30,39);
offset = new OpenLayers.Pixel(-15,-36);
infoWindowAnchor = new OpenLayers.Pixel(16,16);
icon = new OpenSpace.Icon('http://openspace.ordnancesurvey.co.uk/osmapapi/img_versions/img_1.1.0/OS/images/markers/marker-cross-med-blue.png', size, offset, null, infoWindowAnchor);
marker = osMapF.createMarker(globalGazArray[selObj.selectedIndex-1].location, icon, null, null);


//hide list box
document.getElementById('selectGaz').style.display='none';

//clear text field
document.getElementById("searchArea").value = "enter a place/postcode";
}

//End of Functions required for postcode/gazetteer searches
function initmapbuilderNew(id, showMarker)
{
	fieldid = id;
	if(typeof showMarker == 'undefined' || showMarker == null)
		showMarker = true;
	//Creating the Openspace map and the postcode service
	osMapF = new OpenSpace.Map(fieldid+'_map');
	postcodeServiceF = new OpenSpace.Postcode();
	
	//Adding the map overview
	mapOVF = new OpenSpace.Control.OverviewMap();
	osMapF.addControl(mapOVF);
	//fix to put copyright on top of overview map: Needs api fix for later version
	var ccControl = osMapF.getControlsByClass("OpenSpace.Control.CopyrightCollection")
	osMapF.removeControl(ccControl[0]);
	ccControl = new OpenSpace.Control.CopyrightCollection();
	osMapF.addControl(ccControl);
	ccControl.activate();
	//end of fix
	//Turning the overview map off
	mapOVF.minimizeControl();
	
	//Adding the postcode/gazetteer search box
	//define an overlay for search box
	searchBox = new OpenSpace.Layer.ScreenOverlay("search");
	//set its position
	searchBox.setPosition(new OpenLayers.Pixel(80, 0));
	//and add to the map
	osMapF.addLayer(searchBox);
	searchBox.setHTML("<div id=\"OpenSpace.Layer.ScreenOverlay_132\" style=\"position: absolute; width: 200px; height: 100%; z-index: 340; left: 0px; top: 3px;\" class=\"olLayerDiv\">" + 
	"<div id=\"div1\" style=\"z-index:999; padding-left: 0px; font-size: 14px;\">" + 
	"<form name=\"searchForm\" onsubmit=\"return false;\">" + 
	"<input type=\"text\" name=\"searchArea\" id=\"searchArea\" onclick=\"clearTextF(event)\" value=\"enter a place/postcode\"/><input type=\"button\" onclick=\"searchPostcodeF('"+id+"');\" value=\"Find\" title=\"find place by postcode or 1:50,000 gazetteer\"></button>" + 
	"<select name=\"select\" id=\"selectGaz\" onchange=\"zoomGazSelF(this.form.select)\" style=\"display: block\">" + 
	"<option>Select a place</option><option></option></select></form></div></div>");
	//hide list box select
	document.getElementById('selectGaz').style.display='none';
	searchBox.events.register("mouseover", searchBox, function(){
		//de-activate keyboard and navigation controls
		osMapF.controls[0].deactivate();
		osMapF.controls[1].deactivate();
	});
	searchBox.events.register("mouseout", searchBox, function(){
		//activate keyboard and navigation controls
		osMapF.controls[0].activate();
		osMapF.controls[1].activate();
	});

	//click
	var gridProjection = new OpenSpace.GridProjection();
	if(showMarker)
		osMapF.events.register("click", osMapF, function(e) {
			osMapF.events.clearMouseCache();	
			var xy = osMapF.events.getMousePosition(e);
			var pt = osMapF.getLonLatFromViewPortPx(xy);
			var lonlat = gridProjection.getLonLatFromMapPoint(pt);
			pos = new OpenSpace.MapPoint(pt.lon,pt.lat);
			
			document.getElementById(fieldid + '_l1').value = lonlat.lon;
			document.getElementById(fieldid + '_l2').value = lonlat.lat;
			
			if (marker != null) osMapF.removeMarker(marker);
			
			size = new OpenLayers.Size(30,39);
			offset = new OpenLayers.Pixel(-15,-36);
			infoWindowAnchor = new OpenLayers.Pixel(16,16);
			icon = new OpenSpace.Icon('http://openspace.ordnancesurvey.co.uk/osmapapi/img_versions/img_1.1.0/OS/images/markers/marker-cross-med-blue.png', size, offset, null, infoWindowAnchor);
			marker = osMapF.createMarker(pos, icon, null, null);
		
		});

	//Defining the center of the map and the zoom level
	osMapF.setCenter(new OpenSpace.MapPoint(300000,200000),7);
} // End of initmapbuilder.



function showVersions(id, atitle){
	
	//if( $("#"+container).is(":visible") ) {
	//	 $("#"+container).hide();		
	//} else {

	$.ajax({
        type: "get",
        cache: false,
        url: "/ourspaces/rest/artifact/"+id+"/versions",
        success: function(data){
            //response(data);
            //$("#"+container).html(data);
        	
            var text = "";
        	$.each(data, function(i, item) {
        		var res = data[i].resource;
        		var fields = res.split("#");
        	    text = "" + text + "Version: "+data[i].version +" ("+data[i].date+") - <a href = \"/ourspaces/artifact_new.jsp?id="+fields[1]+"\">"+ data[i].title + "</a> <br/>";
        	});
	
            //$("#"+container).html(text);
            //$("#"+container).show();
            
        	var $dialog = $('<div><p>Other Versions:</p></div>')
           .append(text)
   	       .dialog({
   			autoOpen: false,
   			title: atitle,
   			modal: true,
   			height: 'auto',
            width: 'auto' 
   		});  
        	
        	$dialog.dialog('open');
            

           },
        error: function (xhr, ajaxOptions, thrownError){
                           alert(xhr.status);
                           alert(thrownError);
                       },    
        dataType: "json"
    });
 }
//}

/**
 * Logs given things into database. 
 * @param table name of the table, where the things should be inserted.
 * @param insert comma separated list of values. The string will be inserted as such into SQL INSERT statement.
 */
function log(table, insert){
	$.ajax({
        type: "post",
        cache: false,
        url: "/ourspaces/LoggerSearch",
        data: {table:table, insert:insert}
    });
}

function createPersonDiv(ui, id, showRole){
	var resource = ui.item.label; 
			//Create formatted resource  
	var span = $("<span>").text(resource);
	span.css("width", "90%");  
    span.addClass("value"); 
	span.attr("id", ui.item.id);
	var random = Math.floor(Math.random()*10000);
	var input = $("<input>").attr("type","hidden").attr("name","Name").val(random+"_"+ui.item.id)
	.appendTo(span);
	
	var a = $("<a>").addClass("remove").attr({  
	href: "#",                
	title: "Remove " + name  
	}).text("x").appendTo(span);
	if(showRole){
	 $.get("/ourspaces/forms/jsp/projectmemberroles.jsp?random="+random+"&id="+escape(ui.item.id), function(data) {	
		 //Trim the data.
			data = data.replace(/^\s+|\s+$/g, '');
			span.append(data);
		});
	}
	//Add the resource to the list.  
	span.insertBefore('#'+id);  
	//Empty the value so that the edit box isn't updated with the URI
	ui.item.value = "";
}
function addPersonAutocomplete(id, showRole){
	if(typeof showRole == 'undefined' || showRole == null)
		showRole = false;
	addAutocomplete(id, 'http://xmlns.com/foaf/0.1/Person', /*select */function(e, ui) { 
		createPersonDiv(ui, id, showRole);
	},
   /* change:*/ function() {  
        //prevent 'to' field being updated and correct position  
        $('#'+id).css("top", 2);  
    
    }
   );
    //add live handler for clicks on remove links  
    $(".remove", document.getElementById('#'+id+'inputDiv')).live("click", function(){
        //remove current friend  
        $(this).parent().remove();  
        //correct 'to' field position  
        if($("#"+id+"inputDiv span").length === 0) {  
            $("#"+id+"input").css("top", 0);  
        }  
    });
}
/**
 * Adds the autocomplete functionality to the specified id.
 * @param id id of edit box
 * @param range range to query - artifact, person, process, ...
 * @param select function what to do with the selected element of the form function(e, ui) { var x = ui.item.id  }
 * @param change function on change. Usually used to clear the edit box.
 */
function addAutocomplete(id, range, select, change){		
		//attach autocomplete  
        $('#'+id).autocomplete({  
        	delay:500,
        	//minLength:2,
            //define callback to format results  
            source: function(req, add){
    		    $(".noResults").remove();  					
			    if(range == null)
			    	range = "http://openprovenance.org/ontology#Artifact";   
			    $.ajax({
					type: 'GET',
					url: "/ourspaces/search/quicksearch.jsp?type="+escape(range)+"&output=JSON",
					data: req,
					dataType : "html",
					async : true,
					success : function(data, errorThrown) {
						data = data.replace(/^\s+|\s+$/g, '') ;				
						var json =  eval('(' + data + ')');
	                    //create array for response objects  
	                    var suggestions = [];  
	                    //process response  
	                  	$.each(json, function(i, val){  
	                    	suggestions.push(val);  
	                		});  
	                  	if(suggestions.length == 0){
	                  		var d = $("<span>").addClass("noResults ui-corner-all ui-widget").html("No match for: \""+$('#'+id).val()+"\"");//.attr("style","background-color: #CCDDDD;border: 1px solid black;color: black;display: block;padding:3px;float: right;font-size: 13px;height: 18px; position: absolute; width: 234px;");
	                  		
	                  		$("#"+id).parent().parent().append(d);
	                  		$(".noResults").css("top",($("#"+id).position().top+21)+"px");
	                  		$(".noResults").css("left",($("#"+id).position().left)+"px");
	                  	}                  		
	                	//pass array to callback  
	                	add(suggestions);  
					 }
				   });
			    
                //pass request to server  
                /*$.get("/ourspaces/search/quicksearch.jsp?type="+escape(range)+"&output=JSON", req, function(data) {
					//Trim the data.
					data = data.replace(/^\s+|\s+$/g, '') ;				
					var json =  eval('(' + data + ')');
                    //create array for response objects  
                    var suggestions = [];  
                    //process response  
                  	$.each(json, function(i, val){  
                    	suggestions.push(val);  
                		});  
                  	if(suggestions.length == 0){
                  		var d = $("<span>").addClass("noResults ui-corner-all ui-widget").html("No match for: \""+$('#'+id).val()+"\"");//.attr("style","background-color: #CCDDDD;border: 1px solid black;color: black;display: block;padding:3px;float: right;font-size: 13px;height: 18px; position: absolute; width: 234px;");
                  		
                  		$("#"+id).parent().parent().append(d);
                  		$(".noResults").css("top",($("#"+id).position().top+21)+"px");
                  		$(".noResults").css("left",($("#"+id).position().left)+"px");
                  	}                  		
                	//pass array to callback  
                	add(suggestions);  
                });*/
                
        },  
        create: function(e, ui) {  
			$('.ui-autocomplete.ui-menu ').css("z-index","2000");
        },
        open: function(event, ui) { 
			$('.ui-autocomplete.ui-menu ').css("z-index","2000");
		},
        //define select handler  
        select: select,
        close:function(e, ui) { 
        	if($('#'+id).val().length == 0){
			  	//Empty the edit box
			    $('#'+id).val("");
			    $(".noResults").remove();
        	}
        },
	    change : function() {  
	    	change();
		    $('#'+id).val("");
		    $(".noResults").remove();
	    }
     });
}

function addCloseButton(){
	$(document).ready(function() {
		$( ".close_popup" ).dialog( "option", "buttons", { "Ok": function() { $(this).dialog("close"); } } );
	});
}


/**
 * Add behaviour to a button to dynamically load more content.
 * (see projects.jsp or data.jsp for an example)
 * @param isNext boolean to indicate wheather to load next or previous content
 * @param button button where the behaviour must be attached (must be in a #next div)
 * @param url URL of the jsp page loading the content
 * @param offset
 * @param limit max number of items displayed per page
 * @param maxSize actual number of items currenly being displayed
 * @param div div where the new content must be displayed
 */
function addLoadMoreContentBehaviour(isNext, button, url, offset, limit, maxSize, div){		
	$(document).ready(function() {
		 button.click(function() {
			 var fullURL;
			 if(url.indexOf('?')>-1)
				 fullURL = url+"&";
			 else
				 fullURL = url+"?";
			 if (isNext) {
				 fullURL += "offset=" + (offset + maxSize) + "&limit=" + limit;
			} else {
				fullURL += "offset=" + (offset - limit) + "&limit=" + limit;
			}
			 //TODO: add loading gif instead of button here
			 $.ajax({
				type: 'GET',
				url: fullURL, 
				dataType : "html",
				async : false,
				success : function(html, errorThrown) {
//					nextButton.remove();
//					$("#previous").remove();
					div.html(html);
				 }
			   });
		 });
	});
	}

/**
 * Shows the form for creating a new person
 * @param sid id of the div. E.g. for "test" div id should be "testsea".
 * @param showRole Show the role? Useful for project member.
 * @param onClick Javascript what to do when Add clicked.
 */
function showForm(sid, showRole, onClick)
{
			 jQuery.get("/ourspaces/forms/add/Person.jsp?sid="+sid+"&showRole="+showRole+"&onClick="+encodeURIComponent(onClick), function(data1) {
				  document.getElementById(sid+"sea").innerHTML = ''+data1;	
			 },'html');
}

function showCommDialog(fileuri,resid,title){
	var divmsg = $("<div>");
	divmsg.attr("id","dialog");
	divmsg.text("Loading..");
	divmsg.appendTo("body");
	
	$( "#dialog" ).dialog({
		
		modal: true,
		
		autoOpen: false,  
		
		
		close: function(event, ui) {
			
			$("#dialog").remove();
			
		}
	});

	 $("#dialog").dialog("open");
	 $.get("/ourspaces/commSpace/commDialog.jsp", function(data) {
		if( typeof fileuri != 'undefined' && fileuri != null){
			
			var divruri = $("<div>");
			divruri.attr("id","resuri");
			divruri.html(resid);	
			divruri.appendTo("body");
			var divart = $("<div>");
			divart.attr("id","fileuri");
			divart.html(fileuri);	
			divart.appendTo("body");
			var divt = $("<span>");
			divt.attr("id","title");
			divt.html(title);	
			divt.appendTo("body");
			
		}
		var popuptitle="Create New Communication Artefact";
		if( typeof title != 'undefined' && title != null){
			popuptitle="View/Edit Communication Artefact";
		}
		
		var div = $("<div>");
		div.attr("id","commDialog");
		
		div.appendTo("body");
		div.html(data);
		div.width(1020);
		$( "#commDialog" ).dialog({
			width:1020,
			resizable: true,
			modal: true,
			title:popuptitle,
			autoOpen: false,  
			height: 870,
			dialogClass : "alertDialog",
			close: function(event, ui) {
				$("#fileuri").remove();
				$("#resuri").remove();
				
				$("#title").remove();
				$("#commDialog").remove();
				
				
			}
		});
		 $("#dialog").dialog("close");
		$("#commDialog").dialog('open'); 
		
	});
	
}

//Ajax waiting picture settings
var ajaxWaitOpts = {
		  lines: 13, // The number of lines to draw
		  length: 14, // The length of each line
		  width: 9, // The line thickness
		  radius: 14, // The radius of the inner circle
		  rotate: 0, // The rotation offset
		  color: '#000', // #rgb or #rrggbb
		  speed: 0.7, // Rounds per second
		  trail: 36, // Afterglow percentage
		  shadow: false, // Whether to render a shadow
		  hwaccel: false, // Whether to use hardware acceleration
		  className: 'spinner', // The CSS class to assign to the spinner
		  zIndex: 2e9, // The z-index (defaults to 2000000000)
		  top: 'auto', // Top position relative to parent in px
		  left: 'auto' // Left position relative to parent in px
		};
function displayWaiting(div){
	if($(".spinOverlay").size() > 0)
		return;
	if(typeof div == 'undefined' || div == null)
		div = 'main_container';
	var target = document.getElementById(div);
	spinner = new Spinner(ajaxWaitOpts).spin(target);
	var overlay = $("<div>").addClass("ui-widget-overlay ui-corner-all spinOverlay").css("z-index","100000")
	.css("left",($(".spinner").position().left-110)+"px")
	.css("top",($(".spinner").position().top-100)+"px")
	.css("width",220+"px")
	.css("height",200+"px")
	.css("position","absolute")
	.appendTo("body");
	var overlay1 = $("<div>").addClass("spinOverlay")
	.css("left","0px")
	.css("top","0px")
	.css("width","100%")
	.css("height","100%")
	.css("position","absolute").css("z-index","100001")
	.appendTo("body");
}
function hideWaiting(){
	spinner.stop();
	$(".spinOverlay").remove();
}