var activeDialog;
var dialogStack = [];
var maxDepth = 4;
/**
 * This is a class for containing the restrictions on properties.
 */
function Restriction() {
	/** Points to the class Property */
	this.property = null;
	this.min = -1;
	this.max = -1;
	this.exact = -1;
	/**Can be changed using the policies. This alters the range of the property.*/
	this.type = null;
};
/**
 * This is a class for containing the properties.
 */
function Property() {
	/**Subject of the properties*/
	this.URI = null;
	
	this.idProperty = 'prop';
	this.property = 'prop';
	/** 
	 * Array of either one text value or ids of a resource.
	 * This has to be done like this because there can be more resources added
	 * to one property.
	 */
	this.value = [];
	/** Potential restriction on property. */
	this.restriction = null;
	/** Restriction based on policy reasoning. */
	this.policyRestriction = null;
	/** Div element containing the properties details. Useful for manipulation. */
	this.el = null;
	/** date, literal, resource, geoMultiple, resourceNew */
	this.type = 'literal';
	/** Range of the property. Required for the check at drag'n'drop. */
	this.range;
};

function UploadForm() {
	//this.baseClass = "http://openprovenance.org/ontology#Artifact";
	/**Configuration of the upload form */
	this.configuration = {
			/**Event that executes on closing the dialog*/
			onClose : null,
			/**Properties that will be filled in in the beginning of the upload dialog*/
			initProperties : null,
			/**Name of the upload form. This is used when multiple upload forms are stacked above.*/			
			nameUpload : "",
			/**Base class, from which is derived the hierarchy of classes.*/
			baseClass : "http://openprovenance.org/ontology#Artifact",
			/** Either name of ontology in OntologyHandler*/
			ontologyName : "all",
			resourceId : null,
			resourceType : null,
			previousFile : null,
			/**URL of the file that should be used with this artifact*/
			fileURI : null,
			/**Text on the button*/
			confirmText : "Upload",
			/**Whether the file upload is permitted or not*/
			allowFile:true,
			/**In case of new dialog, this is the property that newly created resource would be linked through to the previous resource. E.g. opm:wasGeneratedBy*/
			property:null
	};
	//this.name = "";
	/**Variable containing all the restrictions for current type of objects */
	this.restrictions = [];
	
	/**URI of the new artefact to be uploaded.*/
	this.URI;
	
	/** All properties available for current type of object. Those with value != null are in drag and drop area, those with restrictions exact,min are in mandatory fields area.*/
	this.properties = [];
	
	/**Properties dragged to the area that are common for all types - geo, scientific discourse, tasks. 
	 * These properties stay when the type changes.
	 */
	this.commonProperties = [];
	
	/** Currently selected type*/
	this.typeSelected = "";
	
	/** "hashmap" for storing maps*/
	this.maps = new Array();
	
	this.policyFieldsMissingOK = false;
	
	/**Whether to log about the form was sent already. When successful upload, we don't want to send the cancelled log as well.*/
	this.logSent = false;
	
	/**
	 * Returns the id prefixed with the name of the upload form.
	 */
	this.getId = function(id){
		return this.configuration.nameUpload+id;
	};	
	this.initDragDrop =  function(){
			$( ".draggable li" ).draggable({ 
				appendTo: "body",
				helper: "clone",
				scope: "fields",
				scroll: false,
				zIndex:2700
					});
	
			//Disable required properties.
			for (x in this.properties)
			{
				var newProp = this.properties[x];
				//Disable dragging of the property - one property on the list is enough.
				//Either mandatory properties or filled in ones.
				if(this.isMandatory(newProp)|| newProp.el != null)
					$(".ui-draggable > a:contains('" + newProp.property + "')")
						.parent().draggable("disable");
			}
		};
	    
		/**
		 * 
		 * @param property Name of the property
		 * @param properties Array of properties
		 * @returns Object Property
		 */
		this.findProperty = function(property, properties){
			var p;
			for(var x in properties){
				p = properties[x];
				//Property is about a different resource
				if(typeof p.resource != "undefined" && p.resource != null && p.resource != activeDialog.URI)
					continue;
				if(p.property == property)
					return p;
				else if(p.subProperties != null){
					var tmp = this.findProperty(property, p.subProperties);
					if(tmp != null)
						return tmp;
				}
			}
			return null;
		};
		
		this.findPropertyEverywhere = function(property){
			var p = this.findProperty(property, this.properties);
			if(p == null){
				p = this.findProperty(property, this.commonProperties);
			}
			if(p == null){
				p = this.findProperty(property, this.commonProperties);
			}
			return p;
		};
	
		/**
		Initialization of fields.
		*/
		this.initFields = function(){
			$("#"+activeDialog.getId("uploadForm")+" textarea.inputValue").autoGrow();
				//Datepickers
			$("#"+activeDialog.getId("uploadForm")+" .datepicker" ).datepicker({

					changeMonth: true,
					changeYear: true,
					dateFormat:"dd.mm.yy",			
				});
		};
	
		/**
			Removes a property. El is the parent div of the property.
		*/
		this.removeProperty = function (property, el){
			var checkPolicies = false;
			//Empty the javascript properties.
			var p = this.findProperty(property, this.properties);
			if(p == null){
				//Search in commonProperties
				for(var x in this.commonProperties){
					p = this.commonProperties[x];
					//Remove the property from commonProperties
					if(p.property == property){
						this.commonProperties.splice(x,1);
						break;
					}
					
				}
			}
			else {
				if(p.value.length > 0)
					checkPolicies = true;
				//Empty the property contents, but the property itself stays there.
				p.value = [];
				p.el = null;
				if(p.subProperties != null){
					for(var x in p.subProperties){
						var p2 = p.subProperties[x];
						this.removeProperty(p2.property, p2.el);
					}
				}
			}
			if(p.type == "geoMultiple"){
				activeDialog.maps[activeDialog.getId(property)] = null;
			}
			//Remove the dom element.
			$(el).remove();		
			$("[data-class='"+p.property+"']").remove();	
			//Enable dragging of the property.
			$(".ui-draggable > a:contains('" + property + "')")
					.parent().draggable("enable");
			//Check the policies again.
			if(checkPolicies)
				this.sendRDF(null, false);
		};
		this.colorAllMandatory = function(){	
			//Remove all classes first
			$("#"+this.getId("allFields")+" div").removeClass("filled");
			//Now add them only to the ones that are filled and mandatory.
			for(var x in this.properties){
				this.colorMandatory(this.properties[x]);
			}
			for(var x in this.commonProperties){
				this.colorMandatory(this.commonProperties[x]);
			}
		};
		this.colorMandatory = function(p){
			if(this.isMandatory(p) && p.value.length > 0 && (p.type != 'literal' || p.value[0].length > 0))
				$("#"+this.getId("allFields")+" [data-class="+p.property+"]").addClass("filled");
			else
				$("#"+this.getId("allFields")+" [data-class="+p.property+"]").removeClass("filled");
		
		};
		/**
			Removes value of a property. El is the parent div of the value.
			This function applies only for resources.
		*/
		this.removeValue = function(p, el){
			/*var p = findProperty(property, properties);
			if(p == null){
				p = findProperty(property, commonProperties);
			}*/
			//We delete the given resource
			if (p.type == "resource" || p.type == "resourceNew"){	
				var id = $(el).attr("id");
				var val;
				for(x in p.value){
					val = p.value[x];
					//Remove the value from p.values
					if(val == id){
						p.value.splice(x,1);
						//Remove the dom element.
						$(el).remove();
						break;
					}				
				}
			}
			//Check the policies again.
			this.sendRDF(null, false);
			this.colorMandatory(p);			
		};
		/**
		Change the value of given property, depending on the type.
		*/
		this.changeValue = function(p, el) {
			/*var p = findProperty(property, properties);
			//Maybe it is a common property - geo, scientific discourse or task
			if(p == null){
				p = findProperty(property, commonProperties);
			}*/
			//Wew didn't find the property, create an skeleton of a property.
			if(p == null){
				return;
			}
			checkPolicies = true;
			//For literals, update the value
			if (p.type == "literal" || p.type == "date"){
				if(p.value.length > 0 && p.value[0] == el.value)
					checkPolicies = false;				
				p.value[0] = el.value;
			}
			//Geo properties
			else if(p.type == "geoMultiple"){
				//Geo properties is triple - name, l1, l2 <=> name, lat, lon
				if(p.value[0]==null || typeof p.value[0] == 'undefined'){
					p.value[0] = [null,null,null];
				}
				//Check if we need to check policies
				else if((el == null &&
						p.value[0][1] == $("#"+this.getId(p.property+"_l1")).val() &&
						p.value[0][2] == $("#"+this.getId(p.property+"_l2")).val())
						|| (el != null && p.value[0][0] == el.value)){
					checkPolicies = false;
				}
				//El is null, changing lat and lon.
				if(el == null){
					p.value[0][1] = $("#"+this.getId(p.property+"_l1")).val();
					p.value[0][2] = $("#"+this.getId(p.property+"_l2")).val();
				}
				//El is not null, changing the name of the location
				else{
					p.value[0][0] = el.value;						
				}
			}
			//Check policies for further constraints.
			if(checkPolicies){
				this.sendRDF(null, false);
				this.colorMandatory(p);
			}
		};
		
		this.dropProperty = function(event, ui){
			//Extracting values from the dragged element.
			var el;
			//If was dragged
			if(typeof ui.draggable != "undefined" && ui.draggable != false)
				el = ui.draggable;
			else // if was clicked onto
				el = $(ui);
			var ftype = el.attr('rel');
			var id = el.attr('id');
			var property = el.children("a").text();
			//Find the property
			var p = activeDialog.findProperty(property, activeDialog.properties);
			if (p != null) {
				//The property is already in the upload dialog.
				if(p.el != null)
					return;
				p.el = el;
			}
			//If it was common property, add it to common properties
			else {
				//Update var properties, adding property to the array
				p = new Property();
				p.idProperty = id;
				p.property = property;
				p.el = el;
				p.range = el.attr("data-range");
				p.type = ftype;
				activeDialog.commonProperties[activeDialog.commonProperties.length] = p;
			}
	
			activeDialog.addNewProperty(p, "newFields", "true");
		};
		
		this.addExistingResource = function(prop,id, add){
			if(add == true)
				prop.value.push(id);
			$.get("/ourspaces/search/inversesearch.jsp?q="+escape(id)+"&output=JSON", function(data) {
					//Trim the data.
					data = data.replace(/^\s+|\s+$/g, '') ;				
					var json =  eval('(' + data + ')');
					activeDialog.addResource(prop,id, json[0].label);
					activeDialog.colorMandatory(prop);
	        }); 
		};
		this.addResource = function(prop,id,name){
			 //Create formatted resource  
	        var span = $("<span>").text(name);
	        span.attr("id", id);  
	        span.addClass("value");  
	        var a = $("<a>").addClass("remove").attr({  
	                href: "#",                
	                title: "Remove " + name  
	      }).text("x").appendTo(span); 
	      a.click(function(){ 
	    	  activeDialog.removeValue(prop,this.parentNode);
	    	  return false;
	      });
	      //Add the resource to the list.  
	      $('#'+this.getId(prop.property+'inputContent')).prepend(span);  
		};
		/**	
		* Initializes the given property - resource and geo need special treatment by javascript.
		*@param p Property to be initialized
		*@param property Name of the property
		*/
		this.initProperty =  function(p, property){				
			var range = p.range;
			if(p.range == null || p.range == "")
				range = "http://openprovenance.org/ontology#Artifact";
			//If there is restriction to the class of the property, use it.
			// TODO - what if there is multiple conflicting restrictions???
			if(p.policyRestriction != null){
					if(p.policyRestriction.type != null)
						range = p.policyRestriction.type;				
			}
			//Load basic type of the range - artifact,process or agent
			var query = "/ourspaces/uploadform/getImage.jsp?type="+escape(p.range)+"&property="+escape(p.idProperty);
			$.get(query, function(data) {
					//Trim the data.
					data = data.replace(/^\s+|\s+$/g, '') ;
					$("#"+activeDialog.getId(property)+" .uploadFieldIcon").css("background-image","url(\""+data+"\")");
			});
			if (p.type == 'resource' || p.type == 'resourceNew'){
				//attach autocomplete  
				addAutocomplete(this.getId(property+'input'), range, /*select */function(e, ui) { 
		            var resource = ui.item.label;
		            var p = activeDialog.findPropertyEverywhere(property);
		        	activeDialog.addResource(p, ui.item.id, resource);
		            //Fill the value of the property.
		            p.value.push(ui.item.id);
		            //Call changeValue just for checking the policies.
		            activeDialog.changeValue(p, null);
		            //Empty the value so that the edit box isn't updated with the URI
		            ui.item.value = "";
		            $('#'+activeDialog.getId(p.property+'input')).val("");
		        },  
		       /* change:*/ function() {  
		            //prevent 'to' field being updated and correct position  
		            $('#'+activeDialog.getId(property+'input')).val("").css("top", 2);  
		        
		        }
		       );
		        //add live handler for clicks on remove links  
		        $(".remove", document.getElementById('#'+activeDialog.getId(property+'inputDiv'))).live("click", function(){
		            //remove current friend  
		            $(this).parent().remove();  
		            //correct 'to' field position  
		            if($("#"+activeDialog.getId(property+"inputDiv span")).length === 0) {  
		                $("#"+activeDialog.getId(property+"input")).css("top", 0);  
		            }  
		        }); 
		        //Add the range to the title of the property
		       var result = property.replace( /([A-Z])/g, " $1" );
		       var arr = result.split(' ');
		       result = "";
		       for (var x=0; x<arr.length; x++)
		           result+=arr[x].substring(0,1).toLowerCase()+arr[x].substring(1)+' ';
		       result = result.substring(0,result.length-1);
		       var finalResult = result.charAt(0).toUpperCase() + result.slice(1); // capitalize the first letter - as an example.

		       //var text = $("#"+this.getId(property)+" label").text();
		       //text = text.substring(0,text.length-7);
		       finalResult += ":<br>("+getLocalName(range)+")";
		       $("#"+this.getId(property)+" label").html(finalResult);
			}

			if (p.type == 'resourceNew'){
				$("#"+activeDialog.getId(p.property+"NewDiv")).text("Create new "+getLocalName(p.range));
				$("#"+activeDialog.getId(p.property+"NewDiv")).button({
		            icons: {
		                primary: "ui-icon-circle-plus"
		            }
		        });
				if(dialogStack.length >= maxDepth)
					$('#'+activeDialog.getId(property+'NewDiv')).hide();
				else {
					$('#'+activeDialog.getId(property+'NewDiv')).unbind("click");
					$('#'+activeDialog.getId(property+'NewDiv')).click(function(){
			        	//Store the previous dialog in a variable and on close reassign it back.
						//dialogStack.push(activeDialog);
						var tmp = activeDialog;
			        	showUploadDialog({
			        		onClose: function(){
						            //Load the dialog underneath.
						            activeDialog = dialogStack[dialogStack.length-1];
						        	maps = activeDialog.maps;
									$("#"+activeDialog.getId("uploadForm")).parent().show();
									$("#"+activeDialog.getId("helpWindow")).parent().show();
						            if(typeof uploadedResource != 'undefined' && uploadedResource != null&& uploadedResource != ""){
						            	var val = uploadedResource;
						            	uploadedResource = null;
						            	activeDialog.addExistingResource(p, val, true);
						            }		            
					            	activeDialog.sendRDF(null, false);
						        }, 
						        nameUpload:activeDialog.getId(p.property),
						        baseClass: range,
						        confirmText: "Confirm new",
						        allowFile:false,
						        property:p.idProperty
			        	});	
						//Hide the previous dialog.
						$("#"+tmp.getId("uploadForm")).parent().hide();
						$("#"+tmp.getId("helpWindow")).parent().hide();
				    });
				}			
			}
			
			//Initialize new map and store it in the global variable.
			if (p.type == 'geoMultiple'){
				//Check, if the property wasn't initialized already.
				if(typeof activeDialog.maps[activeDialog.getId(property)] != 'undefined' && activeDialog.maps[activeDialog.getId(property)] != null){
					return;
				}
				var m = new Map();
				m.initmapbuilderNew(activeDialog.getId(property), activeDialog.maps.length);
				activeDialog.maps[activeDialog.getId(property)] = m;
				//Overriding default click behaviour in order to store the values.
				//click 
				activeDialog.maps[activeDialog.getId(property)].osMapF.events.register("click", activeDialog.maps[activeDialog.getId(property)].osMapF, function(e) {
					activeDialog.maps[activeDialog.getId(property)].osMapF.events.clearMouseCache();				
					var xy = activeDialog.maps[activeDialog.getId(property)].osMapF.events.getMousePosition(e);
					var pt = activeDialog.maps[activeDialog.getId(property)].osMapF.getLonLatFromViewPortPx(xy);
					var gridProjection = new OpenSpace.GridProjection();
					var lonlat = gridProjection.getLonLatFromMapPoint(pt);
					pos = new OpenSpace.MapPoint(pt.lon,pt.lat);
					activeDialog.maps[activeDialog.getId(property)].setLon(lonlat.lon);
					activeDialog.maps[activeDialog.getId(property)].setLat(lonlat.lat);
					
					if (activeDialog.maps[activeDialog.getId(property)].marker != null) 
						activeDialog.maps[activeDialog.getId(property)].osMapF.removeMarker(activeDialog.maps[activeDialog.getId(property)].marker);
					
					var size = new OpenLayers.Size(30,39);
					var offset = new OpenLayers.Pixel(-15,-36);
					var infoWindowAnchor = new OpenLayers.Pixel(16,16);
					var icon = new OpenSpace.Icon('http://openspace.ordnancesurvey.co.uk/osmapapi/img_versions/img_1.1.0/OS/images/markers/marker-cross-med-blue.png', size, offset, null, infoWindowAnchor);
					activeDialog.changeValue(p, null);
					activeDialog.maps[activeDialog.getId(property)].marker = activeDialog.maps[activeDialog.getId(property)].osMapF.createMarker(pos, icon, null, null);
	
				    //Load the geoname of the location
					var query = "/ourspaces/uploadform/getGeonames.jsp?lat="+lonlat.lat+"&lon="+lonlat.lon;
					$.get(query, function(data) {
							//Trim the data.
							if(typeof data.geonames == "undefined"){
								data = data.replace(/^\s+|\s+$/g, '') ;
								data =  eval('(' + data + ')');
							}
							$("#"+activeDialog.maps[activeDialog.getId(property)].div_id + "_locationGeoname").val(data.geonames[0].name);
							//Fill the value to the property
							p.value[0][3] = data.geonames[0].name; 
					});
				});
			}
			if (p.type == 'literal'){
				//Fill the default empty value.
				if(p.value.length == 0)
					p.value.push("");
				//Integer
				if(p.range != null && p.range == "http://www.w3.org/2001/XMLSchema#int"){
					$("#"+activeDialog.getId(p.property+"value")).keyup(function () {
				        this.value = this.value.replace(/[^0-9\-]/g,'');
				        if(this.value.search("-") > 0){
					        this.value = this.value.replace(/[^0-9]/g,'');				        	
				        }
					});
					$("#"+activeDialog.getId(p.property+"value")).keypress(function(event) {
						  // Backspace, tab, enter, end, home, left, right
						  // We don't support the del key in Opera because del == . == 46.
						  var controlKeys = [8, 9, 13, 35, 36, 37, 39];
						  // IE doesn't support indexOf
						  var isControlKey = controlKeys.join(",").match(new RegExp(event.which));
						  // Some browsers just don't raise events for control keys. Easy.
						  // e.g. Safari backspace.
						  if (!event.which || // Control keys in most browsers. e.g. Firefox tab is 0
						      (49 <= event.which && event.which <= 57) || // Always 1 through 9
						      (48 == event.which && $(this).attr("value")) || // No 0 first digit
						      isControlKey) { // Opera assigns values for control keys.
						    return;
						  }else if ('-' == String.fromCharCode(event.which) && $(this).attr("value").search("-") == -1) {
							  return;
						  }
						  else {
						    event.preventDefault();
						  }
						});
				}
				//Decimal
				if(p.range != null && p.range == "http://www.w3.org/2001/XMLSchema#decimal"){
					$("#"+activeDialog.getId(p.property+"value")).keyup(function () {
				        this.value = this.value.replace(/[^0-9\.\-]/g,'');
				        if(this.value.search("-") > 0){
					        this.value = this.value.replace(/[^0-9\.]/g,'');				        	
				        }
					});
					$("#"+activeDialog.getId(p.property+"value")).keypress(function(event) {
						  // Backspace, tab, enter, end, home, left, right
						  // We don't support the del key in Opera because del == . == 46.
						  var controlKeys = [8, 9, 13, 35, 36, 37, 39];
						  // IE doesn't support indexOf
						  var isControlKey = controlKeys.join(",").match(new RegExp(event.which));
						  // Some browsers just don't raise events for control keys. Easy.
						  // e.g. Safari backspace.
						  if (!event.which || // Control keys in most browsers. e.g. Firefox tab is 0
						      (49 <= event.which && event.which <= 57) || // Always 1 through 9
						      (48 == event.which && $(this).attr("value")) || // No 0 first digit
						      isControlKey) { // Opera assigns values for control keys.
						    return;
						  }else if ('-' == String.fromCharCode(event.which) && $(this).attr("value").search("-") == -1) {
							  return;
						  }//Decimal dot 
						  else if ('.' == String.fromCharCode(event.which) && $(this).attr("value").search("\\.") == -1) {
							  return;
						  }
						  else {
						    event.preventDefault();
						  }
						});
				}
				
				if(p.range != null && p.range == "http://www.w3.org/2001/XMLSchema#int"){
					$("#"+activeDialog.getId(p.property+"value")).keypress(function(event) {
						  // Backspace, tab, enter, end, home, left, right
						  // We don't support the del key in Opera because del == . == 46.
						  var controlKeys = [8, 9, 13, 35, 36, 37, 39];
						  // IE doesn't support indexOf
						  var isControlKey = controlKeys.join(",").match(new RegExp(event.which));
						  // Some browsers just don't raise events for control keys. Easy.
						  // e.g. Safari backspace.
						  if (!event.which || // Control keys in most browsers. e.g. Firefox tab is 0
						      (49 <= event.which && event.which <= 57) || // Always 1 through 9
						      (48 == event.which && $(this).attr("value")) || // No 0 first digit
						      isControlKey) { // Opera assigns values for control keys.
						    return;
						  } else {
						    event.preventDefault();
						  }
						});
				}
				//Initialize onblur event using jQuery.
				// Do not check editing the literal values.
				$('#'+this.getId(p.property+"value")).blur(function() {
					t = setTimeout(function(){clearTimeout(t); activeDialog.changeValue(p, $('#'+activeDialog.getId(p.property+"value")).get(0));},1);
					return false;
				});
			}
			if (p.type == 'multiProperties'){
				p.value.push("New middle node");
				for(var y in p.subProperties){
					var subProp = p.subProperties[y];
					this.addNewProperty(subProp, p.property+"subPropsId",false);
					/*initProperty(subProp, subProp.property);
					for(var y in newProp.subProperties){
						var subProp = newProp.subProperties[y];*/
					//}
					
				}
				
			}	
			$(".dragElement").draggable({
				appendTo : "body",
				helper : "clone",
				addClasses : true,
				scope : "elemets"
			});
		};
	
		/**
			Shows the dialog about losing a property due to changing to a different type.
		*/
		this.showAlert = function(property, tempProperties, tempRestrictions, type){
	
			var typeText = getLocalName(type);
			//Append dialog to HTML.
			$('body').append('<div id="'+this.getId("dialogconfirm")+'" title="Change the type and lose property '+property+'?">'+
					'<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>'+
					'The selected type ('+typeText+') doesn\'t support property '+property+', which has been filled in. Are you sure to change the type and lose the property '+property+'?'+
					'</p></div>');
			//Run the dialog.
			$( "#"+this.getId("dialogconfirm")).dialog({
				resizable: true,
				height:200,
				width:500,
				modal: true,
				closeOnEscape: false,
				autoOpen: true,  
				dialogClass : "alertDialog",
				buttons: {
					"Change the type and lose the property": function() {
						$( this ).dialog( "close" );
						//Destroy the dialog.
						$( this ).remove();
						//User pressed ok, lets change the page.
						activeDialog.changePage(tempProperties, tempRestrictions, type);
						return 1;
					},
					Cancel: function() {
						$( this ).dialog( "close" );
						//Destroy the dialog.
						$( this ).remove();
	
						//User pressed cancel
						activeDialog.properties = tempProperties;
						activeDialog.restrictions = tempRestrictions;
					}
				}
			});
	
	       // $("#"+this.getId("dialogconfirm")).dialog('open');
	
		};
		
		/**
		 * Checks the restriction
		 * @param r
		 * @returns {Boolean}
		 */
		this.isMandatoryRestr = function(r){
			if(r == null)
				return false;
			if (typeof r == 'undefined')
				return false;
			if(r.exact > 0 || r.min > 0)
				return true;
			return false;
		};
		
		/**
		 * Returns true if the property is mandatory
		 * @param prop
		 * @returns {Boolean}
		 */
		this.isMandatory = function(prop){
			if(this.isMandatoryRestr(prop.restriction) == false)
				return false;//return this.isMandatoryRestr(prop.policyRestriction);
			return true;
		};
		
		/**
		 * Returns true if the property is mandatory because of the policy restriction
		 * @param prop
		 * @returns {Boolean}
		 */
		this.isPolicyMandatory = function(prop){
			return this.isMandatoryRestr(prop.policyRestriction) && !this.isMandatoryRestr(prop.restriction);
		};
		
		/**
		 * Moves the property from one field to another
		 * @param p Property to be moved
		 * @param from id of element
		 * @param to id of element
		 * @param minus Boolean whether to display the minus button
		 */
		this.moveProperty = function(p, from, to, minus){
			 var oldPropDataPara = $("#"+this.getId(from)+" [data-class='"+p.property+"']").detach();						 
			 oldPropDataPara.appendTo('#'+this.getId(to));
			 if(minus)
				 $(oldPropDataPara).children().children(".ui-icon-circle-minus").show();
			 //Disable the minus icon
			 else
				 $(oldPropDataPara).children().children(".ui-icon-circle-minus").hide();
			 p.el = $(oldPropDataPara);
		};
		/**
		 * Manipulates dom tree so that the new properties comply to new restrictions.
		 * If a property existed before, it is only transferred to right category - mandatory <-> optional.	
		 * @param tempProperties
		 * @param tempRestrictions
		 */
		this.moveProperties = function (tempProperties, tempRestrictions){
			//Searching for common properties with changed restrictions
			for (y in tempProperties)
			{
				var oldProp = tempProperties[y];
				//found - if we have found a corresponding new property. Otherwise delete.
				var found = false;
				for (x in this.properties)
				{
					var newProp = this.properties[x];
					if(oldProp.idProperty != newProp.idProperty)
						continue;
					found = true;
					//In all cases, we want the new property to point to the same dom element as the old one
					 newProp.el = oldProp.el;
					//And retain the value
					 newProp.value = oldProp.value;
					
					// Here, we only move the property, creating new ones is bellow.
					// New restriction is mandatory
					if(!this.isMandatory(oldProp) && this.isMandatory(newProp)){
						if(oldProp.el != null){
							this.moveProperty(oldProp, 'allfields', 'mandatoryFields', false);
						}
					}				
					// New property is optional
					if(this.isMandatory(oldProp) && !this.isMandatory(newProp)){
						// Move, if previously filled in
						if(oldProp.el != null && oldProp.value.length > 0){
							this.moveProperty(oldProp, 'allFields', 'newFields', true);
						}
						//Remove if element exists but no value
						else if(oldProp.el != null) {
							$("#"+this.getId("allFields [data-class='"+oldProp.property+"']")).remove();
							oldProp.el = null;
							newProp.el = null;
						}
					}		
				}
				//Delete if corresponding new property not found.
				if(found == false && oldProp.el != null){
					$("#"+this.getId("allFields  [data-class='"+oldProp.property+"']")).remove();
					oldProp.el = null;
				}
			}
			//Creating new properties.
			for (x in this.properties)
			{
				var newProp = this.properties[x];
				if(newProp.el != null || !this.isMandatory(newProp))
					continue;
				this.addNewProperty(newProp, "mandatoryFields","false");
			}
		};
		
		/**
		 * Adds new property to the middle column.
		 * @param newProp
		 */
		this.addNewProperty = function(newProp, divId, remove){
			//Create a new row for the property	in mandatory fields.	
			var query = "/ourspaces/forms/fields/"+newProp.type+".jsp?label="+newProp.property+"&remove="+remove+"&nameUpload="+activeDialog.getId("")+"&id="+escape(newProp.idProperty);

			jQuery.ajaxSetup({
				async : false
			});
			$("<div>").load(query, function() {
		       	$("#"+activeDialog.getId(divId)).append($(this).html());
		       	newProp.el = this;
		       	activeDialog.initFields();	     
				//Disable dragging of the property - one property on the list is enough.
				$(".ui-draggable > a:contains('" + newProp.property + "')")
					.parent().draggable("disable");
				//Init property - used for geo and resource properties
				activeDialog.initProperty(newProp, newProp.property);

				//TODO hack for adding a new person. Not nice at all
				if (newProp.property == "hasAuthor"){
					newPropGlobal = newProp;
					var plus = $("<div>").attr("style","background:url(/ourspaces/icons/001_01.png) no-repeat;float: left;height: 24px; width: 24px;margin-right: 3px;");
					plus.click(function(){
						$("<div>").attr("id", activeDialog.getId("addPersonAuthorsea")).dialog({
							modal:true,
							width:400,
							close:function(){
								$(this).remove();
							}
							});
						showForm(activeDialog.getId("addPersonAuthor"), false, 'activeDialog.addExistingResource(newPropGlobal,ui.item.id, true);');
						
					});
					$("#"+activeDialog.getId("hasAuthorinput")).before(plus);
					$("#"+activeDialog.getId("hasAuthorinput")).css("width","220px");
				}
				//Add the values - used when editing a resource
				if (newProp.value.length>0){
					//Resource
					if(newProp.type == 'resource' || newProp.type == 'resourceNew'){
						for(var y in newProp.value){
							var val = newProp.value[y];
							activeDialog.addExistingResource(newProp, val, false);
						}
					}				
					//Geo - name, lon, lat
					else if(newProp.type == 'geoMultiple'){
						$("#"+activeDialog.getId(newProp.property+"_l1")).val(newProp.value[0][1]);
						$("#"+activeDialog.getId(newProp.property+"_l2")).val(newProp.value[0][2]);
						$("#"+activeDialog.getId(newProp.property+"_locationName")).val(newProp.value[0][0]);
					}
					//Literal and date
					else{
						$("#"+activeDialog.getId(newProp.property)+"value").val(newProp.value[0]);
					}
				}
						
	       });		
			jQuery.ajaxSetup({
				async : true
			});
		};
		/**
		 * Updates the page so that it reflects newly selected type.
		 * @param tempProperties
		 * @param tempRestrictions
		 * @param type
		 */
		this.changePage = function(tempProperties, tempRestrictions ,type){
			//User confirmed the change of type, lets do it.
			//First change the heading.
			var typeText = getLocalName(type);
			document.getElementById(this.getId('mandatory')).innerHTML = '<b>Mandatory Fields for: '+typeText+'</b>';
			$("#"+this.getId("propertiesHeader a")).text('Properties of '+typeText);
			$("#"+this.getId("resourceTypes a")).text('Resource type: '+typeText+". Click to change type.");
			//Change the title of the dialog
			$("#ui-dialog-title-"+this.getId("uploadForm")).text("Upload "+typeText);
			//Change the confirm button
			$("#"+this.getId("uploadForm")).siblings(".ui-dialog-buttonpane").children().children().children().text(this.configuration.confirmText+" "+typeText);
			//Change the breadcrumb label
			 
			$("#"+this.getId(getLocalName(this.URI))).html(this.getBreadcrumbDiv(this.getId(getLocalName(this.URI))).html());
			
			var el = document.getElementById(this.getId('propertiesDiv'));  
		    typeText =  typeText.split(' ').join('');	
			//Replace the content of the div.
		    //liClass=ui-draggable&liStyle="+escape("margin:3px;width:300px;")+"&ulStyle=padding: 5px;&ulClass=draggable&
		    //&ontologyName="+escape(this.configuration.ontologyName)+"
			jQuery.get("/ourspaces/uploadform/getProperties.jsp?format=HTML&ulId=properties&onclick=activeDialog.dropProperty(null, this.parentNode)&className="+escape(type), function(data) {
				el.innerHTML = data;
				activeDialog.initDragDrop();	
				activeDialog.initFields();
				});
		
			//Moves properties so that they retain data and comply to new restrictions.
			this.moveProperties(tempProperties, tempRestrictions);
	
	
			//Check the policies just in case.
			this.sendRDF(null, false);
		    $("#"+this.getId("accordion")).accordion("destroy"); 
			$("#"+this.getId("accordion")).css("display","block");
			$("#"+this.getId("accordion")).accordion({ fillSpace: true});
			
			$("#"+this.getId('propertiesDiv')).css("overflow-x","hidden");
			$("#"+this.getId('propertiesDiv')).css("height","100%");
			
			this.initDragDrop();	
			this.initFields();
			//Collapse the type tree
			$("#"+this.getId("navigationType")).hide();
		};
		
		/**
		 * 
			Check properties and restrictions of the previous type and the new selected type.
			If there is a filled in property that would dissapear, alert the user.		
		 * @param tempProperties Previous properties.
		 * @param tempRestrictions Previous restrictions.
		 * @param type New type.
		 */
		this.checkProperties = function(tempProperties, tempRestrictions, type){
			var x = null,y = null;
			// Storing names of properties for display in the dialog.
			var propertiesNames = "";
			//Look for old ones with filled value with no corresponding new ones
			for (y in tempProperties)
			{
				var oldProp = tempProperties[y];
				var found = false;
				if(oldProp.value.length == 0)
					continue;
				for (x in this.properties)
				{
					var newProp = this.properties[x];
					//Found the same - break and continue with other properties.
					if(newProp.idProperty == oldProp.idProperty){
							found = true;
							break;
					}
				}
				if(found)
					continue;
				if(propertiesNames == "")
					propertiesNames += oldProp.property;
				else
					propertiesNames += ', ' + oldProp.property;			 
			}
			//There is a conflict, lets show the dialog.
			if(propertiesNames != ""){			
				this.showAlert(propertiesNames, tempProperties, tempRestrictions, type);
			}	
			//There are no conflicts, lets change the page!
			else{
				this.changePage(tempProperties, tempRestrictions, type);
			}
		};
		
		/**
		 * Change the type of uploaded resource. Restrictions and properties have to be loaded again.
		 * @param type
		 */
		this.changeType = function(type)
		{
			if(type == getLocalName(type)){
				type = "http://www.policygrid.org/provenance-generic.owl#"+type;
			}
			this.typeSelected = type;
			var query = "/ourspaces/uploadform/getProperties.jsp?format=JS&className="+escape(type);
			if(typeof this.configuration.resourceId != 'undefined' && this.configuration.resourceId != null)
				query += "&id="+escape(this.configuration.resourceId);
					
			//Load javascript with restrictions on properties and properties.
			$.getScript(query, function(data, textStatus){
				   //console.log(data); //data returned
				   //console.log(textStatus); //success
				   //console.log('Load was performed.');
				// Storing current properties and restrictions for further use.
				var tempProperties = activeDialog.properties;
				var tempRestrictions = activeDialog.restrictions;
				//These two functions are defined in getProperties.
				reloadProperties(activeDialog);
				reloadRestrictions(activeDialog);
				var copy = activeDialog.properties.concat(activeDialog.commonProperties);
				for(var x in copy){
					var p = copy[x];
					p.URI = activeDialog.URI;
				}
				//Now check the current properties and restrictions with the previous ones. 
				//If user doesn't want to change the type, cancel changes.
				activeDialog.checkProperties(tempProperties, tempRestrictions, type);
				
				// The change of the page is done from the dialog showAlert.
				// Because of javascript nature, it is not possible to wait for the closing of the dialog and the function to proceed has to be an event of the dialog.
			
			});
		};
	
		/**
		 * Returns copy of given property
		 * @param properties
		 */
		this.copyProperty = function(prop){
			var prop2 = new Property();
			prop2.idProperty = prop.idProperty;
			prop2.property = prop.property;
			prop2.value = [];
			for(var z in prop.value){
				if($.isArray(prop.value[z])){
					prop2.value[z] = [];
					for(var a in prop.value[z])
						prop2.value[z].push(prop.value[z][a]);
				}
				else if(prop.value[z].length > 0){
					prop2.value.push(prop.value[z]);					
				}
			}
			if(prop.subProperties != null){
				prop2.subProperties = [];
				for(var z in prop.subProperties){
						var subprop = prop.subProperties[z];
						prop2.subProperties.push(this.copyProperty(subprop));		
				}
			}
			prop2.range = prop.range;
		    prop2.restriction = prop.restriction;
		    prop2.policyRestriction = prop.policyRestriction;
		    prop2.el = prop.el;
		    prop2.type = prop.type;
		    prop2.URI = prop.URI;
		    return prop2;
		};
	
		this.copyProperties = function (properties){
			var copy = [];
			for(var x in properties){
				var prop = properties[x];
				var prop2 = this.copyProperty(prop);
				copy.push(prop2); 		
			}
			return copy;
		};
		
		/**
		 * Applies the policies to the upload form
		 * @param json Policies.
		 */
		this.applyPolicies = function (json){
			var copy = this.properties.concat(this.commonProperties);
			//Reset previous policy restrictions
			for(var x in copy){
				var p = copy[x];
				var restr = this.findProperty(p.idProperty, json);
				//There was restriction, but now it isn't
				if(restr == null && p.policyRestriction != null){
					p.policyRestriction = null;
					if(!this.isMandatory(p) && p.value.length == 0)
						this.removeProperty(p.property, p.el);
					else if(this.isMandatory(p) )
						this.moveProperty(p, "policyFields", "mandatoryFields", false);
					else
						this.moveProperty(p, "policyFields", "newFields", true);
					this.colorMandatory(p);
				}
			}
			$(".editIcon").remove();

			if(json.length == 0)
				$("."+this.getId("policyDiv")).hide();
			else
				$("."+this.getId("policyDiv")).show();
			//Add new policy restrictions
			for(var x in json){
				var restr = json[x];
				//Restriction on a resource
				if(typeof restr.resource != 'undefined'){
					var res = restr.resource;
					//Find the property, which contains the resource
					for(var x2 in copy){
						var p = copy[x2];
						for(var v in p.value){
							var val = p.value[v];
							if(val==res){
								//Skip if there is already editIcon
								if($("#"+activeDialog.getId(p.property)+"inputDiv [id='"+res+"'] a .editIcon").size() > 0)
									continue;
								// Else add the editIcon
								var el = $("#"+activeDialog.getId(p.property)+"inputDiv [id='"+res+"']");
								var img = $("<img>").attr("src","/ourspaces/icons/001_11.png").attr("class","editIcon");
								var a = $("<a>").attr("href","#").attr("data-id",res).attr("prop-id",p.idProperty).css("right","0px").css("margin-left","4px").css("position","relative").css("float","right").append(img);
								a.click(function(){
									//dialogStack.push(activeDialog);
									
						        	showUploadDialog({
						        		onClose: function(){
								        		var property = activeDialog.configuration.property;
								        		var resId = activeDialog.configuration.resourceId;
									            //Load the dialog underneath.
									            activeDialog = dialogStack[dialogStack.length-1];
									        	maps = activeDialog.maps;
									            if(typeof uploadedResource != 'undefined' && uploadedResource != null){
									            	var val = uploadedResource;
									            	uploadedResource = null;
									            	var p = activeDialog.findPropertyEverywhere(getLocalName(property));
									            	activeDialog.removeValue(p,$("[id='"+resId+"']")); 									            	
									            	activeDialog.addExistingResource(p, val, true);
									            }		            
								            	activeDialog.sendRDF(null, false);
									        }, 
									        nameUpload:activeDialog.getId(getLocalName($(this).attr("data-id"))),
									        confirmText: "Confirm edits",
									        allowFile:false,
									        resourceId:$(this).attr("data-id"),
									        property:$(this).attr("prop-id")
						        	});
						        	return false;
								});
								el.append(a);
							}
						}
					}					
					continue;
				}
				var p = this.findProperty(getLocalName(restr.property), this.properties.concat(this.commonProperties));

				this.createPolicyDiv(restr.policy, restr.title);
				//Property is a common property, add it to the array
				if(p == null){
					//$("#"+this.getId("policyFieldsHeader")).show();
					this.dropProperty(null, $("#"+this.getId("uploadForm")+" .ui-draggable > a:contains('" + getLocalName(restr.property) + "')").parent().get());
					p = this.findProperty(getLocalName(restr.property), this.properties.concat(this.commonProperties));
					this.moveProperty(p, "allFields", getLocalName(restr.policy)+"Fields", false);
				}
				//If the property is not in the mandatory fields list, add it
				if(p.el == null){
					//$("#"+this.getId("policyFieldsHeader")).show();
					this.addNewProperty(p, getLocalName(restr.policy)+"Fields","false");				
				}
				//Move the property to the policy required list, if it is not there already, but only in case the property is not required itself.
				else if(!this.isMandatoryRestr(p.restriction) && $("#"+this.getId(getLocalName(restr.policy))+"Fields  [id="+this.getId(p.property)+"]").size() == 0){
					this.moveProperty(p, "allFields", getLocalName(restr.policy)+"Fields", false);
				}
				p.policyRestriction = restr;
				//Init the property - used when the range of the class changed because of the policy
				this.initProperty(p, p.property);
				this.colorMandatory(p);
			}
			// Hide the policy field, if it has no more fields in it
			$("."+this.getId("policyDiv")).each(function(){
				if($(this).children(".policyFields").children("div").size() == 0)
					$(this).hide();
			});
		};
		
		this.getDialogJSON = function (){

			function getProperties(upload){
				var copy = upload.copyProperties(upload.properties);
				for(var x in copy){
					var prop = copy[x];
				    prop.restriction = null;
				    prop.policyRestriction = null;
				    prop.el = null;	
				}
				var copy2 = upload.copyProperties(upload.commonProperties);
				for(var x in copy2){
					var prop = copy2[x];
				    prop.restriction = null;
				    prop.policyRestriction = null;
				    prop.el = null;	
					//Fill the geoNames value instead of empty one.
					if("geoMultiple"==prop.type && prop.value.length > 0 &&
						prop.value[0][0] == null){
						prop.value[0][0] = prop.value[0][3];
					}
				}
				copy = copy.concat(copy2);
				//Delete el from subproperties as well.
				for(var x in copy){
					var prop = copy[x];
					if(prop.subProperties != null){
						for(var z in prop.subProperties){
								var subprop = prop.subProperties[z];
								subprop.restriction = null;
								subprop.policyRestriction = null;
								subprop.el = null;			
						}
					}
				}

				for(var x=0;x<copy.length;x++){
					var prop = copy[x];
					if(prop.value != null && prop.value.length == 0 && prop.subProperties == null){
						copy.splice(x,1);
						x--;
						continue;
					}
					if(prop.subProperties != null){
						var foundTrue = false;
						for(var z in prop.subProperties){
									var subprop = prop.subProperties[z];
									if(subprop.value.length >0) 
										foundTrue = true;		
						}					
						if(foundTrue == false){
							copy.splice(x,1);
							x--;
							continue;
						}
					}
				}
				
				return copy;
			}
			var propertiesCopy = getProperties(this);

			var JSONResource = new Object;
			JSONResource.type = ""+this.typeSelected;
			JSONResource.URI = ""+this.URI;
			JSONResource.folder = $("#"+this.getId("folder")).val();		
			if($("#"+this.getId("pri")).attr("checked")=="checked")
				JSONResource.private = "true";
			else
				JSONResource.private = "false";
			if(this.configuration.allowFile == true)
				JSONResource.file = this.configuration.fileURI;
			//If the URI of the dialog is the same as the previous version, don't send the previous version - we're not creating a new version of the resource.
			if(typeof this.configuration.resourceId != 'undefined' && this.configuration.resourceId != null/* && activeDialog.URI != this.configuration.resourceId*/){
				JSONResource.resourceId = this.configuration.resourceId;
			}
			JSONResource.properties = propertiesCopy;
			return JSONResource;
		};
		/**
		 * Sends the properties of the uploaded resource.
		 * @param fileURI
		 * @param commit whether to commit or not
		 */
		this.sendRDF = function (fileURI, commit){
			var otherDialogs = [];
			//In case this is second dialog, add the properties of the old one as well.
			displayWaiting();			
			/*
			//If file is specified, we should get a new URI for the resouce, to create a new version
			var file = $("#"+this.getId("fileURL")).val();
			if(file == null || file == "")
				file = $("#"+this.getId("upfile")).val();
			if(file != null && file != "" && activeDialog.URI == this.configuration.resourceId){				
				//Get new URI
				$.post("/ourspaces/ResourceUploadNew",{  getURI:'true'},function(data){
					//Trim the data.
					data = data.replace(/^\s+|\s+$/g, '') ;
					activeDialog.URI = data;
				});
			}*/
			
			
			for(var x = dialogStack.length-2; x>=0;x--){
				var dial = dialogStack[x];
				//Add the link between the previous dialog and the actual one.
				var p = null;
				if(typeof dialogStack[x+1].configuration.property != 'null' && dialogStack[x+1].configuration.property != null){
					p = dial.findPropertyEverywhere(getLocalName(dialogStack[x+1].configuration.property));
					p.value.push(dialogStack[x+1].URI);						
				}
				//Add the type property to the list.
				/*var property;
				property = new Property();	
				property.idProperty = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type';
				property.property = 'type';
				property.value = [dial.typeSelected];
				property.type = 'resource';
				property.range = '';
				dial.properties.push(property);			*/	
				otherDialogs.push(dial.getDialogJSON()); 
				//Remove the link again
				if(p != null){
					p.value.length = p.value.length - 1;		
				}
				//Remove the type property
				//dial.properties.length = dial.properties.length - 1;

			}
			var JSONResourcestring = JSON.stringify(this.getDialogJSON()); 
			//Test the policies
			//typeof this.configuration.fileURI == "undefined" || this.configuration.fileURI == null || this.configuration.fileURI == ""
			if(commit == false){
				$.post("/ourspaces/ResourceUploadNew",{  resource:JSONResourcestring,otherDialogs:JSON.stringify(otherDialogs), commit:"false"},
						  function(data2) {
							hideWaiting();
							data2 = data2.replace(/^\s+|\s+$/g, '');
							var json =  eval( data2 );
							activeDialog.applyPolicies(json);
						}
				);
			}
			//Upload the data
			else{
            	activeDialog.logSent = true;
				$.post("/ourspaces/ResourceUploadNew",{  resource:JSONResourcestring, commit:"true"},
				  function(data2) {
						hideWaiting();
						data2 = data2.replace(/^\s+|\s+$/g, '');
						//Making a new global variable uploadedResource.
						uploadedResource = data2;
						$('#'+activeDialog.getId('uploadForm')).dialog("close");
						$('#'+activeDialog.getId('helpWindow')).dialog("close");
						//activeDialog = null;
				  }
				);
			}
		};
		
		/**
		 * Checks whether the given property is mandatory and filled in.
		 * @param prop Property to be checked.
		 * @returns {Boolean} True if everything is ok, false otherwise.
		 */
		this.checkOneProperty = function (prop){
			if(!this.isMandatory(prop)){
				return true;
			}
			if(prop.value.length == 0 ||
					(prop.type == "literal" && prop.value[0].length == 0)){
				alert("Required property "+prop.property+" is not filled in!");
				return false;
			}
			return true;
		};
		
		/**
		 * Check if all the mandatory fields are filled in
		 * @returns {Boolean} True if everything is ok, false otherwise.
		 */
		this.checkMandatoryFields = function (){
			var copy = this.properties.concat(this.commonProperties);
			for(var x in copy){
				var prop = copy[x];
				if(!this.checkOneProperty(prop))
					return false;
			}

			if(activeDialog.policyFieldsMissingOK == true)
				return true;
			//Check if the policyfields are filled in or not
			for(var x in copy){
				var prop = copy[x];
				if(this.isPolicyMandatory(prop) && (prop.value.length == 0 ||
						(prop.type == "literal" && prop.value[0].length == 0))){
					//Append dialog to HTML.
					$('body').append('<div id="'+this.getId("dialogconfirm")+'" title="Suggested properties missing">'+
							'<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>'+
							'Some of the properties suggested by the policies are not filled in. Do you want to upload the '+getLocalName(this.typeSelected)+' anyway?'+
							'</p></div>');
					//Run the dialog.
					$( "#"+this.getId("dialogconfirm")).dialog({
						resizable: true,
						height:200,
						width:500,
						modal: true,
						closeOnEscape: false,
						autoOpen: true,  
						dialogClass : "alertDialog",
						buttons: {
							"Upload without the properties": function() {
								$( this ).dialog( "close" );
								//Destroy the dialog.
								$( this ).remove();
								activeDialog.policyFieldsMissingOK = true;
								activeDialog.sendData();
								return true;
							},
							Cancel: function() {
								$( this ).dialog( "close" );
								//Destroy the dialog.
								$( this ).remove();
							}
						}
					});
					return false;
				}
			}
			return true;
		};
		/**
		 * Sends the data to the server. First the file is uploaded, then the properties.
		 */
		this.sendData = function (){
			//Fill in the literal values
			/*for(var x in this.properties){
				var p = this.properties[x];
				if(p.el != null)
					this.changeValue(p, $('#'+this.getId(p.property)).get(0));
				if(p.type == "multiProperties" && p.value.length > 0){
					for(var y in p.subProperties){
						var subP = p.subProperties[y];
						if(subP.el != null)
							this.changeValue(subP, $('#'+this.getId(subP.property)).get(0));				
					}
				}
			}*/
			//If not everything is filled in, do not upload anything.
			if(!this.checkMandatoryFields())
				return;
			// The URI was filled in by the user manually
			if(this.configuration.allowFile == true && $("#"+this.getId("fileURL")).val().length>0){
				this.configuration.fileURI = $("#"+this.getId("fileURL")).val();
			}
			//Check if the file wasn't already uploaded or we do not need the file at all
			if((typeof(this.configuration.fileURI) != 'undefined' && this.configuration.fileURI != null && this.configuration.fileURI != "")
					||
					this.configuration.allowFile == false
				){
				this.sendRDF(this.configuration.fileURI, true);		
				return;		
			}
			//Check if the resourceId is not null and the file is null - editing a resource and not sending a new file
			if(typeof(this.configuration.resourceId) != 'undefined' && this.configuration.resourceId != null && this.configuration.resourceId != "" &&
					typeof(this.configuration.previousFile) != 'undefined' && this.configuration.previousFile != null && this.configuration.previousFile != ""){
				this.sendRDF(this.configuration.previousFile, true);	
				return;			
			}
			//Check if the file is filled in.
			if(this.configuration.allowFile == true && $("#"+this.getId("upfile")).val()==""){
				alert("Please choose the file to upload.");
	    		return;			
			}
			//Uploads file using ajaxFileUpload plugin only if allowFile == true.		
	        if(this.configuration.allowFile == true){
	        	$.ajaxFileUpload(
	    	            {
	    	                url:'/ourspaces/ResourceUploadNew?action=upload&folder='+escape($("#"+activeDialog.getId("folder")).val()),
	    	                secureuri:false,
	    	                fileElementId:this.getId("upfile"),
	    	                dataType: 'xml',
	    	                success: function (data, status)
	    	                {
	    	                    if(typeof(data.error) != 'undefined')
	    	                    {
	    	                        if(data.error != '')
	    	                        {
	    	                            alert(data.error);
	    	                        }else
	    	                        {
	    	                            alert(data.msg);
	    	                        }
	    	                    }
	    	                    else{
	    	                    	//Success, lets upload the metadata
	    	                    	activeDialog.configuration.fileURI = $(data.body.innerHTML+"pre").html();
	    	                    	if(activeDialog.configuration.fileURI == "")
	    	                    		alert("Please choose the file to upload.");
	    	                    	else
	    	                    		activeDialog.sendRDF(activeDialog.configuration.fileURI, true);
	    	                    }
	    	                },
	    	                error: function (data, status, e)
	    	                {
	    	                    alert(e);
	    	                }
	    	            }
	    	        );
	        }	           
	        return false;		
		};
		
		this.resizeDialog = function(width, height){
			$('#'+activeDialog.getId("accordion")).height(1.0*height-140);
			$('#'+activeDialog.getId("droppable")).height(1.0*height-30);
			$('#'+activeDialog.getId("droppable")).width(1.0*width-265);
			$('#'+activeDialog.getId("breadcrumb")).width(1.0*width);		
		};
		/**
		 * Initialization at the start of the page
		 * @param initProperties Optional array of properties with prefilled values.
		 */
		this.initUpload = function () {
			//Clear variables for upload.
			this.restrictions = [];
			this.properties = [];
			this.commonProperties = [];
			this.typeSelected = "";
			
			//Load using synchronous loading.
			jQuery.ajaxSetup({
				async : false
			});
			
			//If uploaded file is specified, hide the form for finding the file
			if(typeof this.configuration.fileURI != 'undefined' && this.configuration.fileURI != null && this.configuration.fileURI != ""){
				$("#"+this.getId("upfileformDiv")).hide();
			}

			//Start with the base class.
			var typeText = getLocalName(this.configuration.baseClass);
			//Start with the type of the resource.
			if(typeof this.configuration.resourceType != 'undefined' && this.configuration.resourceType != null && this.configuration.resourceType != ""){
				this.changeType(this.configuration.resourceType);
				typeText = getLocalName(this.configuration.resourceType);
			}
			else{
				this.changeType(this.configuration.baseClass);
				//Show the tree for the first time
				$("#"+this.getId("navigationType")).show();
				$("#"+this.getId("resourceTypes a")).text('Resource type: '+typeText+".");
			}
	
			this.initDragDrop();
	
			$("#"+this.getId("droppable")+" #"+this.getId("add")).droppable(
					{
						activeClass : "ui-state-default",
						hoverClass : "ui-state-hover",
						scope : "fields",
						/**New property dropped to the area*/
						drop : this.dropProperty 
					});
	
			$("#"+this.getId("navigationType")).treeview({collapsed : true});
			//Unfold the first level of resources types.
			$("#"+this.getId("navigationType")+" div.hitarea:visible").click();
			
			//In case we are editing an existing resource, lets create a filled in properties.
			if( typeof this.configuration.resourceId != 'undefined' && this.configuration.resourceId != null){
				//Make backup of the properties
				backProperties = this.copyProperties(this.properties);
				backCommonProperties = this.copyProperties(this.commonProperties);
				
				for (var x in this.properties)
				{
					var prop = this.properties[x];
					//Properties that have filled .el were already filled in during addNewProperty.
					if(prop.value.length > 0 && prop.el == null){
						if(this.isMandatory(prop))
							this.addNewProperty(prop, "mandatoryFields","false");
						else
							this.addNewProperty(prop, "newFields","true");
					}
				}
				for (var x in this.commonProperties)
				{
					var prop = this.commonProperties[x];
					if(prop.value.length > 0 && prop.el == null){
						if(this.isMandatory(prop))
							this.addNewProperty(prop, "mandatoryFields","false");
						else
							this.addNewProperty(prop, "newFields","true");
					
						//Place the marker
						if(prop.type == "geoMultiple"){
							var pos = new OpenSpace.MapPoint(prop.value[0][1],prop.value[0][2]);
							this.maps[this.getId(prop.property)].setLon(prop.value[0][1]);		
							this.maps[this.getId(prop.property)].setLat(prop.value[0][2]);			
							var size = new OpenLayers.Size(30,39);
							var offset = new OpenLayers.Pixel(-15,-36);
							var infoWindowAnchor = new OpenLayers.Pixel(16,16);
							var icon = new OpenSpace.Icon('http://openspace.ordnancesurvey.co.uk/osmapapi/img_versions/img_1.1.0/OS/images/markers/marker-cross-med-blue.png', size, offset, null, infoWindowAnchor);
							this.changeValue(prop, null);
							this.maps[this.getId(prop.property)].marker = this.maps[this.getId(prop.property)].osMapF.createMarker(pos, icon, null, null);
						    var lonlat = new OpenLayers.LonLat(prop.value[0][1],prop.value[0][2]);
						    var gridProjection = new OpenSpace.GridProjection();
						    pos = gridProjection.getMapPointFromLonLat(lonlat);
						    this.maps[this.getId(prop.property)].osMapF.setCenter(pos,7);
						}
					}
				}
			}
			for (var x in this.configuration.initProperties)
			{
				var prop = this.configuration.initProperties[x];
				var p = this.findProperty(prop.property, this.properties);
				if(p == null)
					p = this.findProperty(prop.property, this.commonProperties);
				if(p == null)
					continue;
				p.value = p.value.concat(prop.value);
				if(p.value.length > 0 && p.el == null){
					if(this.isMandatory(p))
						this.addNewProperty(p, "mandatoryFields","false");
					else
						this.addNewProperty(p, "newFields","true");
				}
				//Check the policies just in case.
				this.sendRDF(null, false);
			}
			this.colorAllMandatory();
			var breadcrumb = $("<div>");
			if(typeof jsPlumb == 'undefined'){
				jsPlumb = window.jsPlumb = new jsPlumbInstance();
			}
			var offset = 5;
			for(var x = 0; x<dialogStack.length;x++){
				var dial = dialogStack[x];
				var div = dial.getBreadcrumbDiv(this.getId(getLocalName(dial.URI)));
				div.css("left",offset+"px");
				offset += 200;
				breadcrumb.append(div);
			}
			//breadcrumb.append(this.getBreadcrumbDiv(this.getId(getLocalName(this.URI))).css("left",offset+"px"));
			$("#"+this.getId("breadcrumb")).append(breadcrumb);

			//Emphasize active dialog.
			$("#"+this.getId(getLocalName(activeDialog.URI))).css("font-weight","bold").css("border","2px solid black");
			
			$("#"+this.getId("breadcrumb")).css("z-index","");			
			//$("#"+this.getId("loading")).css("display","none");	
			$("#"+this.getId("main")).css("z-index","");
			
			// Fix the offsets
			for(var x = 1; x<dialogStack.length;x++){
				var dial = dialogStack[x];
				var prev = dialogStack[x-1];
				var w = $("#"+this.getId(getLocalName(prev.URI))).width();
				var l = $("#"+this.getId(getLocalName(prev.URI))).position().left;
				$("#"+this.getId(getLocalName(dial.URI))).css("left",l+w+165);
			}
			var anchors = ["RightMiddle","LeftMiddle"];
			for(var x = dialogStack.length-2; x>=0;x--){
				var prev = dialogStack[x+1];
				var dial = dialogStack[x];
				try{
					jsPlumb.connect({
						source:this.getId(getLocalName(dial.URI)),
						target:this.getId(getLocalName(prev.URI)),
						anchors:anchors,	
						connector:[ "Straight" ],//[ "Straight"], ["Flowchart"]
						overlays:[ 
						   [ "Arrow", { width:15, length:15, location:1, cssClass:"arrow" }], 
						   [ "Label",  {
							   			//id:idTrim,
							   			label:getLocalName(prev.configuration.property), 
							   			location: 0.5,
							   			cssClass : "labelUpload",
							   			labelStyle:{ color : "white"}} ]
						],paintStyle:{lineWidth:3, strokeStyle : "#000"},
						endpoint:["Rectangle",{ width:1, height:1, isSource:false, isTarget:false}]
					});
				}
				catch(err)
				  {
				  	alert(err);
				  }
			}
			this.resizeDialog($( "#"+activeDialog.getId("uploadForm")).width(), $( "#"+activeDialog.getId("uploadForm")).height());		
			
			//Set back to asynchronous because of the waiting spinner - it doesnt work in Chrome when synchronous
			jQuery.ajaxSetup({
				async : true
			});
		};
		
		this.createPolicyDiv = function(id, title){
			if($("#"+this.getId(getLocalName(id))).size() > 0)
				return;
			var policyField = $("<div>").attr("id",this.getId(getLocalName(id))).addClass(this.getId("policyDiv")).attr("style","float:left;width: 100%;");
			policyField.html('<b>Fields required by <span id="'+this.getId(getLocalName(id))+'Title" class="policyTitle">'+title+'</span>:</b><div class="policyFields" id="'+this.getId(getLocalName(id))+'Fields" style="float:left;width: 100%;"></div>');
			policyField.appendTo("#"+this.getId("policyFields"));
			return;
			
		}
		this.getBreadcrumbDiv = function(id){
			var breadcrumb = $("<div>").attr("id",id);
			breadcrumb.html(""+getLocalName(this.typeSelected)).attr("class","ui-corner-all overviewUpload");//.css("width","150px"
			return breadcrumb;
		};
		/**
		 * Shows the upload form. Uses the global variable "resourceId" for editing.
		 * @param onClose Optional function to perform on closing the dialog.
		 * @param initProperties Optional array of properties with prefilled values.
		 */
		this.showUploadDialog = function(configuration){
			if(typeof configuration.onClose != 'undefined'){
				this.configuration.onClose = configuration.onClose;
			}
			if(typeof configuration.initProperties != 'undefined'){
				this.configuration.initProperties = configuration.initProperties;
			}
			if(typeof configuration.nameUpload != 'undefined'){
				this.configuration.nameUpload = configuration.nameUpload;
			}
			if(typeof configuration.baseClass != 'undefined'){
				this.configuration.baseClass = configuration.baseClass;
			}
			if(typeof configuration.resourceId != 'undefined'){
				this.configuration.resourceId = configuration.resourceId;
			}
			if(typeof configuration.resourceType != 'undefined'){
				this.configuration.resourceType = configuration.resourceType;
			}
			if(typeof configuration.previousFile != 'undefined'){
				this.configuration.previousFile = configuration.previousFile;
			}
			if(typeof configuration.fileURI != 'undefined'){
				this.configuration.fileURI = configuration.fileURI;
			}
			if(typeof configuration.confirmText != 'undefined'){
				this.configuration.confirmText = configuration.confirmText;
			}
			if(typeof configuration.allowFile != 'undefined'){
				this.configuration.allowFile = configuration.allowFile;
			}
			if(typeof configuration.property != 'undefined'){
				this.configuration.property = configuration.property;
			}
			if(typeof configuration.ontologyName != 'undefined'){
				this.configuration.ontologyName = configuration.ontologyName;
			}
			
			
			
			
			var query = "/ourspaces/uploadResourceNew.jsp?a=1";
			//Button - either upload resource or apply changes, depending if we are editing or uploading
			var button = {"Upload resource": function() {
				activeDialog.sendData();
			}};
			if( typeof this.configuration.resourceId != 'undefined' && this.configuration.resourceId != null){
				query += "&id="+escape(this.configuration.resourceId);
				if( typeof buttonText == 'undefined' || buttonText == null){
					button = {"Apply changes": function() {
						activeDialog.sendData();
					}};
				}
			}
			if( typeof this.configuration.baseClass != 'undefined' && this.configuration.baseClass != null){
				query += "&baseClass="+escape(this.configuration.baseClass);
			}
			if( typeof this.configuration.nameUpload != 'undefined' && this.configuration.nameUpload != null){
				query += "&nameUpload="+escape(this.configuration.nameUpload);
			}
			if( typeof this.configuration.allowFile != 'undefined' && this.configuration.allowFile != null){
				query += "&allowFile="+escape(this.configuration.allowFile);
			}
			if( typeof this.configuration.ontologyName != 'undefined' && this.configuration.ontologyName != null){
				query += "&ontologyName="+escape(this.configuration.ontologyName);
			}
			
			
			
			//TODO DELETE the commented code - it seems that the upload of different version will be handled on the server side, leaving the front-end intact.
			//If we are editing something, let's assign activeDialog.URI to the edited version.
			//If it is artifact, the URI is changed to new URI when the new file is specified
			/*if( typeof this.configuration.resourceId != 'undefined' && this.configuration.resourceId != null){
				activeDialog.URI = this.configuration.resourceId;
			}
			//Creation of a new resource, let's get a new URI
			else{*/
				//Get new URI
				$.post("/ourspaces/ResourceUploadNew",{  getURI:'true'},function(data){
					//Trim the data.
					data = data.replace(/^\s+|\s+$/g, '') ;
					activeDialog.URI = data;
				});
			/*}*/
			
			//Get the content of the dialog.
			$.get(query,function(data){
				//Trim the data.
				data = data.replace(/^\s+|\s+$/g, '') ;
				var div = $("<div>");
				div.attr("id",activeDialog.getId("uploadForm"));
				div.appendTo("body");
				div.html(data);
				div.width(750);
				div.css("padding","0").css("overflow","hidden");
				$( "#"+activeDialog.getId("uploadForm")).dialog({
					width:760,
					resizable: true,
					closeOnEscape: false,
					resizeStop : function(event, ui) {
						activeDialog.resizeDialog($( "#"+activeDialog.getId("uploadForm")).width(), $( "#"+activeDialog.getId("uploadForm")).height());//ui.size.width, ui.size.height
					},
					modal: true,
					autoOpen: false,  
					height: 600,
					minWidth:600,
					title: 'Upload resource', 
					dialogClass : "alertDialog",
					buttons:button,
					close: function(event, ui) {
						$("#"+activeDialog.getId("uploadForm")).remove();
						$("#"+activeDialog.getId("helpWindow")).remove();	
						//properties = [];
						//commonProperties = [];
        				//Remove the last dialog
			            dialogStack.pop();
			            //Log the attempt to upload
			            if(activeDialog.logSent == false){
			            	activeDialog.logSent = true;
			            	$.post("/ourspaces/ResourceUploadNew",{ log:"true", URI:"cancelled", resourceType:activeDialog.typeSelected });
			            }
			            	
						if(typeof activeDialog.configuration.onClose != "undefined" && activeDialog.configuration.onClose != null){						
							activeDialog.configuration.onClose(event,ui);
						}
					}
				});
				$("#"+activeDialog.getId("uploadForm")).dialog('open');
				//Add the question mark symbol to titlebar.
				var help = $("<a>").attr("href","").attr("id",activeDialog.getId("helpButton")).css("color","black").addClass("helpUpload ui-corner-all").html("?");
				help.click(function(){
					
					if($( "#"+activeDialog.getId("helpWindow")).size()>0)
						$( "#"+activeDialog.getId("helpWindow")).remove();
					else
						$.get("/ourspaces/uploadform/help.jsp",function(data){
							//Trim the data.
							data = data.replace(/^\s+|\s+$/g, '') ;
							var help = $("<div>").attr("id",activeDialog.getId("helpWindow")).html(data);		
							help.appendTo("#"+activeDialog.getId("uploadForm"));		
							var x = $("#"+activeDialog.getId("uploadForm")).offset().left+$("#"+activeDialog.getId("uploadForm")).width();
							var y = $("#"+activeDialog.getId("uploadForm")).offset().top;
							$( "#"+activeDialog.getId("helpWindow")).dialog({
								width:260,
								resizable: true,
								closeOnEscape: true,
								modal: false,
								autoOpen: true,  
								height: 600,
								minWidth:200,
								title: 'Help', 
								position :[x,y],
								dialogClass : "alertDialog",
								close: function(event, ui) {
									$("#"+activeDialog.getId("helpWindow")).remove();								
								}
							});
						});
					return false;
				});
				$("#"+activeDialog.getId("uploadForm")).parent().children(".ui-dialog-titlebar").append(help);
				
				//Hiding of choose file and URL fields
				$("#"+activeDialog.getId("upfile")).change(function() { 
					if($("#"+activeDialog.getId("upfile")).val().length > 0){
						$("#"+activeDialog.getId("upfileformDiv")+" div.URI-buttonbar").hide();
						$("<a>").css("float","left").attr("href","").html("Choose URL instead").click(function(){
							$("#"+activeDialog.getId("upfileformDiv")+" div").show();
							$(this).remove();
						}).appendTo("#"+activeDialog.getId("upfileformDiv"));
					}
				});
				$("#"+activeDialog.getId("fileURL")).blur(function() {
					if($("#"+activeDialog.getId("fileURL")).val().length > 0){
						$("#"+activeDialog.getId("upfileformDiv")+" div.fileupload-buttonbar").hide();
						$("<a>").css("float","left").attr("href","").html("Choose file instead").click(function(){
							$("#"+activeDialog.getId("upfileformDiv")+" div").show();
							$(this).remove();
						}).appendTo("#"+activeDialog.getId("upfileformDiv"));
						
					}
				});

				//$("#"+activeDialog.getId("uploadForm")).parent().resizable({ alsoResize: '#'+activeDialog.getId("accordion")});
				activeDialog.initUpload();
			});
		};
};
var showUploadDialog = function(configuration){
	displayWaiting();
	var upload = new UploadForm();
	dialogStack.push(upload);
	activeDialog = upload;
	if(typeof configuration.nameUpload == "undefined" || configuration.nameUpload == null)
		configuration.nameUpload = "Upload";
	maps = upload.maps;
	upload.showUploadDialog(configuration);
};