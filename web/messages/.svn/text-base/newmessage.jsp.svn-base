
<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, java.util.*,java.text.*, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="user" class="common.User" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<%

user = (User) session.getAttribute("use");
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));

Vector contacts = user.getContacts(user.getID());

ArrayList list = new ArrayList();

int userid = user.getID();
int id = user.getID();


Random r = new Random();
int d = r.nextInt();
int sendto=0;
String sendtofoafid="";
if ( request.getParameter("to")!=null){
	sendto=Integer.parseInt(request.getParameter("to").toString());
	sendtofoafid=user.getFOAFID(sendto);
}
%>


  <script type="text/javascript" src="/ourspaces/javascript/tab.js"></script>



  <script language="javascript" type="text/javascript">
function stopRKey(evt) {
   var evt = (evt) ? evt : ((event) ? event : null);
   var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
   if ((evt.keyCode == 13) && (node.type=="text")) {return false;}
}

document.onkeypress = stopRKey;
</script>

<script language="javascript" type="text/javascript">







function listPeople()
{
	var provList = "&";// save provenance links (not for sending message)
	$(document).ready(function() {$('input[name="provenanceLink"]').each(function (i) {

	provList = provList + "prov="+encodeURIComponent(this.value) + "&";
 	});
	});
	provList = provList.substring(0, provList.length-1);
	
	var finalList = "";// recipents list
	$(document).ready(function() {$(".recipient").each(function (i) {

	        	finalList = finalList + "person="+encodeURIComponent(this.id) + "&";
	 });
	 });

	 finalList = finalList.substring(0, finalList.length-1);
	
	sendMessage(finalList,provList);
}

function sendMessage(list1,list2)
{

	
	var title = document.getElementById('title').value;
	var content = document.getElementById('content').value; 
	//alert ('/ourspaces/messagesHandler?action=new&'+list1+list2+'&title='+encodeURIComponent(title)+'&content='+encodeURIComponent(content));
	if (list1.length != 0){
		
	$.ajax({
		
		type:'GET',
		url:'/ourspaces/messagesHandler?action=new&'+list1+list2+'&title='+encodeURIComponent(title)+'&content='+encodeURIComponent(content),
		
		
		dataType: "html",
		success:function (){
			$('#confirm').show();
			$('#confirm').fadeOut(3000);
			$("#newMessage").dialog("close");;
		},
		error:function(){
			alert('ERROR');
		}
		
	});
	}else {
		
		$('#empty').show();
		$('#empty').fadeOut(5000);
	}
	
}

$(document).ready(function() {
		var sendto=$("#sendto").text();
		if (sendto!=null){
			
			$.get("/ourspaces/search/inversesearch.jsp?q="+escape(sendto)+"&output=JSON", function(data) {
                //Trim the data.
                data = data.replace(/^\s+|\s+$/g, '') ;                      
                var json =  eval('(' + data + ')');
               // $('#MyNameinput').val(json[0].label);
                var resource=json[0].label;
                var span = $("<span>").text(resource);
	  			span.attr("class","recipient");  
	  			span.attr("id", sendto);  
	  			var a = $("<a>").addClass("remove").attr({  
	  		        		href: "javascript:",  
	  		        		title: "Remove " + resource  
	  	              	}).text("x").appendTo(span); 
	  	              //Add the resource to the list.  
	  	              span.insertBefore('#MyNameinput'); 
                
                
			});
			
		}
	
	
	 // hides the stuffs as soon as the DOM is ready
	  	$('#confirm').hide();
	  	$('#empty').hide();
	  	$('#content').ourSpacesProvenanceTagging(
	  			{
	  				provID:"prov",
	  				discID:"disc"
	  			}
	  	);
	 
	  	$('#MyNameinput').autocomplete({  						  
	  		//define callback to format results  
	  		source: function(req, add){  					  
	  			//pass request to server  
	  			$.get("/ourspaces/search/quicksearch.jsp?type="+"http%3A%2F%2Fxmlns.com%2Ffoaf%2F0.1%2FPerson"+"&output=JSON&callback=?", 
	  	  req, function(data) {
	  				
	  				var data = data.replace(/^\s+|\s+$/g, '') ;				
	  				var json =  eval('(' + data + ')');
	  		        		//create array for response objects  
	  		        		var suggestions = [];  
	  		        		//process response  
	  		      		$.each(json, function(i, val){  
	  		        			suggestions.push(val);  
	  		    		});  
	  		    		//pass array to callback  
	  		    		add(suggestions);  
	  		    	});  
	  		    
	  	},  
	  		//define select handler  
	  		select: function(e, ui) {  
	  			//Create formatted resource  
	  			var resource = ui.item.label;  
	  			var span = $("<span>").text(resource);
	  			span.attr("class","recipient");  
	  			span.attr("id", ui.item.id);  
	  			var a = $("<a>").addClass("remove").attr({  
	  		        		href: "javascript:",  
	  		        		title: "Remove " + resource  
	  	              	}).text("x").appendTo(span); 
	  	              //Add the resource to the list.  
	  	              span.insertBefore('#MyNameinput'); 
	  	              ui.item.value="";
	  	           
	  		    
	  		},  
	  		//define select handler  
	  		change: function() {  
	  		    //prevent 'to' field being updated and correct position  
	  		    $('#MyNameinput').val("").css("top", 2);  
	  		}  
	  	});
	  	//add live handler for clicks on remove links  
	  	$(".remove", document.getElementById('#MyNameinputDiv')).live("click", function(){  	    	  
	  	//remove current friend  
	  	$(this).parent().remove();  	      
	  		//correct 'to' field position  
	  		if($("#"+property+"inputDiv span").length === 0) {  
	  		    $("#"+property+"input").css("top", 0);  
	  		}  
	  	});
});
	
</script>




<div id="sendto" style="display:none;"><%=sendtofoafid %></div>
<div  class="ui-state-highlight ui-corner-all" style="position:relative; margin-left:-12px; padding:10px;width: 675px; font-size:12px;">
	<div id="recipients">
		<div style="position:relative; float:left; width: 670px; margin-bottom:15px;">
	     
	     	<div style="position:relative; float:left; width:80px;">Recipients : </div>    
	        <div id="MyNameinputDiv" class="resourceInputDiv" style="float:left;width:580px;">
				<input id="MyNameinput" class="resourceInput" style="padding: 2px;  border: none;width:180px" id="MyName" name="MyName" size="40" type="text"/>
			</div>
	 	</div>
	 	
	 	
              
		
        <div style="position:relative; float:left; width:80px;">Subject : </div>    
		
		
			<input type="text" id="title" style="width:590px;border: 1px solid #CCC;" name="title">
		
		
		<p>
			<textarea name="content" id="content" style="width:665px; margin:5px;border: 1px solid #CCC; height:100px;"></textarea>
		</p>
		<div id="prov"></div>
		<div id="disc"></div>
		
	<div   style="padding-top:15px;width:670px;height:30px;float:left">
	
    <a href="#" style="float:right;color:#fff" class="fg-button ui-state-active fg-button-icon-right ui-corner-all" onclick="listPeople();">Send Message</a>
    </div>
    <div id="confirm" style="position: relative; float:left; width:580px;height:20px; display:block; margin:5px;">
			<p align="center" style="color: #FF6600; font-size:16px;">Your message has been successfully sent.</p>
		</div>
		<div id="empty" style="position: relative; float:left; width:580px;height:20px; display:block; margin:5px;">
			<p align="center" style="color: #FF6600; font-size:16px;">Recipients cannot be empty</p>
		</div>
    
    </div>

		
		
		
</div>


