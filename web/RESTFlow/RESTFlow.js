

var exampleColor = '#A14202';
var exampleDropOptions = {
		tolerance:'touch',
		hoverClass:'dropHover',
		activeClass:'dragActive'
	};


var _makeOverlay = function() { return new jsPlumb.Overlays.Arrow({foldback:0.7, fillStyle:'#FF6600', location:0.6, width:15}); };

var endpoint1 = {
	endpoint:new jsPlumb.Endpoints.Dot({radius:9}),
	isSource:true,
	maxConnections:10,
	isTarget:true,
	connector : new jsPlumb.Connectors.Bezier(50),
	dropOptions:exampleDropOptions,		
	overlays:[_makeOverlay()],			
	style:{ width:25, height:21, fillStyle:exampleColor },
	connectorStyle : {
		gradient:{stops:[[0, exampleColor], [0.5, '#FF6600'], [1, exampleColor]]},
		lineWidth:2,
		strokeStyle:exampleColor
	}
};

var anchors = [jsPlumb.Anchors.LeftCenter,jsPlumb.Anchors.BottomCenter, jsPlumb.Anchors.RightCenter,jsPlumb.Anchors.TopCenter ];

				//var commonConfig = { anchors:anchors, endpoint:endpoint1 };
				var commonConfig = {endpoint:endpoint1 };

			$(document).ready(function() {

			
		
				// chrome fix.
				document.onselectstart = function () { return false; };
				
				

				
			
				jsPlumb.Defaults.DragOptions = { cursor: 'pointer', zIndex:2000 };
				jsPlumb.Defaults.PaintStyle = { strokeStyle:'#FF6600', lineWidth:2 };
				jsPlumb.Defaults.EndpointStyle = { radius:9, fillStyle:'#FF6600' };

				                 			    
				
				$(".detach").click(function() {
					jsPlumb.detachAll($(this).attr("rel"));
				});

				$("#clear").click(function() { jsPlumb.detachEverything(); showConnections(); });
		});

$(function() {
		  //Counter
	    counter = 0;

			    
		$('#window2a').resizable();
		$( "#catalog li" ).draggable({
			helper: 'clone',
	        containment: 'frame',
	        //When first dragged
	        stop: function (ev, ui) {
	            var pos = $(ui.helper).offset();
	            counter++;
	            /*
	            objName = "#clonediv" + counter
	            $(objName).css({
	                "left": pos.left,
	                "top": pos.top
	            });
	            $(objName).removeClass("drag");
	            counter++;
                var element = $(ui.draggable).clone();
                element.addClass("tempclass");
                $(this).append(element);
                $(".tempclass").attr("id", "clonediv" + counter);
                $("#clonediv" + counter).removeClass("tempclass");
                //Get the dynamically item id
                draggedNumber = ui.helper.attr('id').search(/drag([0-9])/)
                itemDragged = "dragged" + RegExp.$1
                console.log(itemDragged)
                $("#clonediv").replaceWith("<div></div>");
                $("#clonediv" + counter).addClass("window");
                //$("#clonediv" + counter).addClass("quickFlip");
                //$("#clonediv" + counter).addClass("drag");
               // $("#clonediv" + counter).addClass("ui-widget-content");
                */
	            
	            
                var foo = $( document.createElement('div') );
                foo.addClass("mytempdiv");
                
           
                var name = "";
                var height = "";
                var width = "";
                var actorContent = "";
                
                var pt = "5px;"
                
               $.ajax({  
                	   url: '/Constant/GetDescription',  
                	   dataType: 'json',  
                	   async: false,  
                	   success: function(json){  
		                	name = json.name;
		                 	height = json.height;
		                 	width = json.width;    
                	                }  
                	 });  
                
               $.ajax({  
            	   url: '/Constant/Initialise',  
            	   dataType: 'html',  
            	   async: false,  
            	   success: function(html){  
	                	actorContent = ""+html;   
            	                }  
            	 });  
                
            	//alert(name);
                
                $(foo).css({
                	"padding-top" : pt,
                	"padding-bottom" : pt,
	                "left": pos.left,
	                "top": pos.top,
	                "z-index": "20",
	                "height": height,
	                "width": width
	            });
                
                
                
                $("body").append(foo);
                $(".mytempdiv").attr("id", "actor" + counter);
                $("#actor" + counter).removeClass("mytempdiv");
                $("#actor" + counter).addClass("window");
                $("#actor" + counter).append(actorContent)
               

                jsPlumb.draggable("actor" + counter);
                jsPlumb.addEndpoint("actor" + counter, jsPlumb.extend({anchor:jsPlumb.Anchors.TopCenter}, endpoint1)); 
                jsPlumb.addEndpoint("actor" + counter, jsPlumb.extend({anchor:jsPlumb.Anchors.BottomCenter}, endpoint1));
                jsPlumb.addEndpoint("actor" + counter, jsPlumb.extend({anchor:jsPlumb.Anchors.LeftCenter}, endpoint1)); 
                jsPlumb.addEndpoint("actor" + counter, jsPlumb.extend({anchor:jsPlumb.Anchors.RightCenter}, endpoint1)); 
                
               
	        }
		});

		
	});