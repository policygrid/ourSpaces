/*!
 * jQuery ourSpacesd Provenance Plugin
 * Edoardo Pignotti
 * Policy Grid II Digital Social Research Node
 */
(function ($) {
	
	$.widget("ui.ourSpacesProvenanceTagging", {
	    // default options
		options: {
	        provID : "provenancelinks",
	        discID : "discourse"
	        },
	        
	    _create: function() {
	        var self = this;     
	        
	        var discourseProperties = {
	          "refers to":"refersTo",
	          "related to":"relatedTo",
	          "relates to":"relatedTo",
	          "in response to":"inResponseTo",
	          "agrees with":"agreesWith",
	          "arises from":"arisesFrom",
	          "cites":"cites",
	          "discusses":"discusses",
	          "motivated by":"motivatedBy",
	          "inconsistent with":"inconsistentWith",
	          "alternative to":"alternativeTo",
	          "disagreesWith":"disagreesWith",
	          "does not agree with":"disagreesWith",
	          "doesn\'t agree with":"disagreesWith",
	          "relevant to":"relevantTo"
	          };
	        
	        var globalAtTags = {};
	        var globalHashTags = {};
	        var deletedDiscourse = [];
	        
	        var provIDtag = "#"+this.options.provID;
	        var discIDtag = "#"+this.options.discID;
	        
	        var newTag = function($tags, $global) {
	            var rnewTags = new Array();
			    
			    for (tag in $tags) {
			     var found = false;
			     
			     $.each($global, function(key, value) {
			      if (key == $tags[tag]) found = true;
			      });
			      
			     if (!found) rnewTags.push($tags[tag]);
			    }
			    
			    return rnewTags;  
	        };
	       
	        
	        var getCurrentOpenTag = function($txt, $symbol ,$global) {
	        	var text = $txt;
	        	var tags = [];
	        	if ($symbol == "@") tags = text.match(/@\w+/g);
	        	if ($symbol == "#") tags = text.match(/#\w+/g);
	        	
	        	//console.log("getCurrentOpenTags--text: "+text);
	        	//console.log("getCurrentOpenTags--tags: "+tags);
	        	
	        	var newTags = newTag(tags, $global);
	        	if (newTags.length > 0) {
	        	return newTags[0]; } else { return []; }
	        }
	        
	        var removeObjectDiscourse = function() {
           	      $(this).parent().removeClass("ui-state-highlight");
	        	  $(this).parent().addClass("ui-state-disabled");
	        	  $(this).addClass("ui-icon-arrowreturnthick-1-n");
	        	  $(this).addClass("redoObjectDiscourse");
	        	  $(this).removeClass("ui-icon-circle-close");
	        	  $(this).removeClass("removableObjectDiscourse");
	        	  var prop = $(this).siblings("input").val();
	              deletedDiscourse.push(prop);
	              $(this).unbind("click");
	              $(this).bind("click", redoObjectDiscourse);
	              $(this).siblings("input").attr("name","DISABLEDdiscourse");
                }
  
	        
	        var redoObjectDiscourse = function() {
       	   	  $(this).parent().removeClass("ui-state-disabled");
       	   	  $(this).parent().addClass("ui-state-highlight");
          	  $(this).removeClass("ui-icon-arrowreturnthick-1-n");
          	  $(this).removeClass("redoObjectDiscourse");
          	  $(this).addClass("ui-icon-circle-close");
          	  $(this).addClass("removableObjectDiscourse");	
          	  var prop = $(this).siblings("input").val();
          	  deletedDiscourse[$.inArray(prop, deletedDiscourse)] = "";
           	  $(this).unbind("click");
              $(this).bind("click", removeObjectDiscourse);
              $(this).siblings("input").attr("name","discourse");
                }
	        
	        var removeObject = function() {
      	      $(this).parent().removeClass("ui-state-highlight");
    	 	  $(this).parent().addClass("ui-state-disabled");
    	 	  $(this).addClass("ui-icon-arrowreturnthick-1-n");
    	 	  $(this).addClass("redoObject");
    	 	  $(this).removeClass("ui-icon-circle-close");
    	 	  $(this).removeClass("removableObject");
              $(this).unbind("click");
              $(this).bind("click", redoObject);
              $(this).siblings("input").attr("name","DISABLEDprovenanceLink");
    	
	           }
	        
	       var redoObject = function() {
			   	  $(this).parent().addClass("ui-state-highlight");
	         	  $(this).parent().removeClass("ui-state-disabled");
	         	  $(this).addClass("ui-icon-circle-close");
	         	  $(this).addClass("removableObject");
	         	  $(this).removeClass("ui-icon-arrowreturnthick-1-n");
	         	  $(this).removeClass("redoObject");
	              $(this).unbind("click");
	              $(this).bind("click", removeObject);
	              $(this).siblings("input").attr("name","provenanceLink");
	           }
	        
	        var updateInterface = function() {
	        
	          $('.removableObjectDiscourse').unbind("click");
	          $('.redoObjectDiscourse').unbind("click");   	  
	          $('.removableObject').unbind("click");	       
	          $('.redoObject').unbind("click");
	          
	          $('.removableObjectDiscourse').bind("click", removeObjectDiscourse);
	          $('.redoObjectDiscourse').bind("click", redoObjectDiscourse);   	  
	          $('.removableObject').bind("click", removeObject);	       
	          $('.redoObject').bind("click", redoObject);
	     
	        }
	        
	        
	        var clearAll = function() {
	        	 $(discIDtag).empty();
	        	 $(provIDtag).empty();
	        	 console.log("CLEARING ALL DATA");
	        	 globalAtTags = {};
	 	         globalHashTags = {};
	 	         deletedDiscourse = [];
	        }
	        
	        
	        var discourseDetection = function($txt) {          
	            $(discIDtag).html("");
	        
	            console.log(discIDtag);
	          $.each(discourseProperties, function(key, value) {
	        
	           var pattern = new RegExp("#\\w+ (\\w+ )*" + key + " (\\w+ )*#\\w+", "igm");  
	           
	           var text = $txt;
	           var discourse = text.match(pattern);
	        
	           //console.log("DISCOURSE tags:" + text);
	           
	           if (discourse) {
	             console.log("DISCOURSE text:" + text + ", patterm: "+pattern + " = " + discourse);
	             for (item in discourse) {
	             console.log("DISCOURSE ITEM:" + item);
	               var tags = discourse[item].match(/#\w+/g);
	               
	               var property = globalHashTags[tags[0]]+","+value+","+globalHashTags[tags[1]];
	               
	               console.log("DISCOURSE tags:" + tags);
	               
	               console.log(property+" DDDD: "+deletedDiscourse+" method "+  $.inArray(property, deletedDiscourse));
	               
	              if ( $.inArray(property, deletedDiscourse) < 0) {	               
	                $(discIDtag).append("<div class=\"ui-state-highlight ui-corner-all\" style=\"margin-top: 3px; padding: 3px;\">"+tags[0] + " <b>"+value+"</b> " + tags[1]+"<span class=\"ui-icon ui-icon-circle-close removableObjectDiscourse\" style=\"curson: pointer; float: right; margin-left: .3em;\"></span><input type=\"hidden\" name=\"discourse\" value=\""+property+"\" /></div>");
	                             
	                } else {
	                $(discIDtag).append("<div class=\"ui-state-disabled ui-corner-all\" style=\"margin-top: 3px; padding: 3px;\">"+tags[0] + " <b>"+value+"</b> " + tags[1]+"<span class=\"ui-icon ui-icon-arrowreturnthick-1-n redoObjectDiscourse\" style=\"curson: pointer; float: right; margin-left: .3em;\"></span><input type=\"hidden\" name=\"discourse\" value=\""+property+"\" /></div>");
	                }
        
	              }
	            }  	        
	           
	           });
	          
	         	updateInterface();	         	          
	           
	        }
	        
	        this.element.bind('textchange', function (event, previousText) {
	                   
	           var text = $(this).val();
	           
	           var atTags = text.match(/@\w+/g);
	           var hashTags = text.match(/#\w+/g);
      
	           discourseDetection(text);          
	           	  
	        });
	        
	        $("input:reset").click(function() {
	        	clearAll();
	        });
	        
	        this.element.bind("keydown", function(event) {
	            if (event.keyCode === $.ui.keyCode.TAB && $(this).data("autocomplete").menu.active) {
	               event.preventDefault();
	            }
	            }).autocomplete({
	            minLength: 0,
	            source: function(request, response) {
	                var term = request.term, results = [];
	                console.log("The term is: "+term);
	                
	                var newAtTags = getCurrentOpenTag(term, "@", globalAtTags);
	                  //console.log("New At Tags: "+newAtTags);
	                var newHashTags = getCurrentOpenTag(term, "#", globalHashTags);
	                  //console.log("New Hash Tags: "+newHashTags);  
	                                 
	                if (newAtTags.length > 0) {
	                                //console.log("Stuff to search :"+newAtTags.substr(1));
	                  $.ajax({
	                      type: "post",
	                      url: "/ourspaces/search/quicksearch.jsp?type=http%3A%2F%2Fxmlns.com%2Ffoaf%2F0.1%2FPerson&term="+newAtTags.substr(1)+"&output=JSON&addValue=true&spaces=false",
	                      //data: { term: request.term, json: JSON.stringify(names) },
	                      success: function(data){
	                          //response(data);
	                          response(data);	              
	                          //alert(request.term);
	                         },
	                      error: function (xhr, ajaxOptions, thrownError){
	                                         alert(xhr.status);
	                                         alert(thrownError);
	                                     },    
	                      dataType: "json"
	                  });          
	                 }
	                 
	                 if (newHashTags.length > 0) {
	                   $.ajax({
	                       type: "post",
	                       url: "/ourspaces/search/quicksearch.jsp?type=http%3A%2F%2Fopenprovenance.org%2Fontology%23Artifact&term="+newHashTags.substr(1)+"&output=JSON&addValue=true&spaces=false",
	                       //data: { term: request.term, json: JSON.stringify(names) },
	                       success: function(data){
	                           //response(data);
	                           response(data);	              
	                           //alert(request.term);
	                          },
	                       error: function (xhr, ajaxOptions, thrownError){
	                                          alert(xhr.status);
	                                          alert(thrownError);
	                                      },    
	                       dataType: "json"
	                   });          
	                  }
	                 
	                 
	                 
	            },
	            focus: function() {
	                // prevent value inserted on focus
	                return false;
	            },
	           
	            select: function(event, ui) {
	            
	                console.log("The value is "+this.value);
	                
	                if (getCurrentOpenTag(this.value,"@",globalAtTags).length > 0) {
	                 var tag = getCurrentOpenTag(this.value,"@",globalAtTags); 
	                 //console.log("Current open tag" + tag);
	                 this.value = this.value.replace(tag, "@"+ui.item.label+" ");
	                 globalAtTags["@"+ui.item.label] = ui.item.value;
	                } else if (getCurrentOpenTag(this.value,"#",globalHashTags).length > 0) {
	                 var tag = getCurrentOpenTag(this.value,"#",globalHashTags); 
	                 //console.log("Current open tag" + tag);
	                 this.value = this.value.replace(tag, "#"+ui.item.label+" ");
	                 globalHashTags["#"+ui.item.label] = ui.item.value;
	                }
	                
	                                
	                $(provIDtag).html("");
	                console.log(provIDtag);
	                $.each(globalAtTags, function(key, value) {
	                
	                var personRDFID = value;
	                var personID = personRDFID.replace("http://xmlns.com/foaf/0.1/#","");
	                var personJSON;
	                
	                //do some magic with ajax to get some stuff from the repository
	                $.ajax({
	                    type: "get",
	                    url: "/ourspaces/rest/person/"+personID,
	                    //data: { term: request.term, json: JSON.stringify(names) },
	                    success: function(data){
	                        //response(data);
	                        personJSON = data;	              
	                        //alert(request.term);
	                       },
	                    error: function (xhr, ajaxOptions, thrownError){
	                                       alert(xhr.status);
	                                       alert(thrownError);
	                                   },    
	                    dataType: "json",
	                    async: false
	                });
	                
	                  var img = "http://mrt.esc.abdn.ac.uk:8080/ourspaces/images/no.jpg";
	                  if (personJSON.photo) img = personJSON.photo;
	                  var jobTitle = "";
	                  if (personJSON.jobTitle) jobTitle = "("+personJSON.jobTitle+")" ; 
	                  
	                  img = img.replace("http://www.ourspaces.net/file/","/ourspaces/file/");
	                  
	                  $(provIDtag).append("<div class=\"ui-state-highlight ui-corner-all\" style=\"margin-top: 3px; padding: 3px;\"> " + key + " <strong>is</strong> <img align=\"middle\" src=\""+img+"\" style=\"border:1px solid #FFFFFF; margin:0px; padding:0px; margin-right:10px;\" alt=\"no picture\" width=\"31\" height=\"31\">" + personJSON.name + " " +personJSON.surname + jobTitle +" <span class=\"ui-icon ui-icon-circle-close removableObject\" style=\"curson: pointer; float: right; margin-left: .3em;\"></span><input type=\"hidden\" name=\"provenanceLink\" value=\""+key+","+value+"\" /></div>");
	              	                  
	                 });
	                $.each(globalHashTags, function(key, value) {
	                
			              var artifactRDFID = value;
			              var artifactID = artifactRDFID.replace("http://openprovenance.org/ontology#","");
			              var artifactJSON;
			              
			              //do some magic with ajax to get some stuff from the repository
			              $.ajax({
			                  type: "get",
			                  url: "/ourspaces/rest/artifact/"+artifactID,
			                  //data: { term: request.term, json: JSON.stringify(names) },
			                  success: function(data){
			                      //response(data);
			                      artifactJSON = data;	              
			                      //alert(request.term);
			                     },
			                  error: function (xhr, ajaxOptions, thrownError){
			                                     alert(xhr.status);
			                                     alert(thrownError);
			                                 },    
			                  dataType: "json",
			                  async: false
			              });
			              
			              var type = artifactJSON.type.split("#");                   
	                    $(provIDtag).append("<div class=\"ui-state-highlight ui-corner-all\" style=\"margin-top: 3px; padding: 3px;\"> " + key + " <strong>is</strong>  a "+type[1]+" named " +artifactJSON.title+" <span class=\"ui-icon ui-icon-circle-close removableObject\" style=\"curson: pointer; float: right; margin-left: .3em;\"></span><input type=\"hidden\" name=\"provenanceLink\" value=\""+key+","+value+"\" /></div>");
	                    
	                   
	                   
	                    
	                  });
	                  
	                updateInterface();  	                  
	                
	                return false;
	            }
	        });	
	        
	        
	        
	        
	         
	        
	     } //enf of _create function
         
         }); //end of widget
	
})(jQuery); //end of functions