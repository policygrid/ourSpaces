
<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.util.Vector,java.util.*,java.text.*, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<script type="text/javascript" src="msgfunctions.js"></script>

<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="projects" class="common.Project" />
<jsp:useBean id="blog" class="common.Blog" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="message" class="common.Message"/>
<jsp:useBean id="msg" class="common.Message" />
<jsp:include page="/top_head.jsp" />

<div class="navBarLeft" style="z-index; -1;" >
           <div class="dropdownBox">
              <a class="navBarLeft" href="#" style="float: left; margin-right:5px;"><img src="/ourspaces/icons/001_12.png" align="left" border="0"/>My Messages Space</a> <img src="/ourspaces/icons/dropdown.png" align="right" border="0"/>
              <div class="navBarOptions ui-corner-all" style="z-index; -1; float:left; width:220px;">
			      <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
			              <a class="navBarLeft" href="/ourspaces/messages/mymessagesNew.jsp" style="float: left; margin-right:5px;"><img src="/ourspaces/icons/001_43.png" align="left" border="0"/>Inbox</a> 
			      </div>
			      <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
			             <a class="navBarLeft" href="/ourspaces/messages/mysentmessagesNew.jsp" style="float: left; margin-right:5px;"><img src="/ourspaces/icons/001_43s.png" align="left" border="0"/>Sent</a>
			      </div>    
				  <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
			           	 <a class="navBarLeft" href="/ourspaces/messages/mydeletedmessages.jsp" style="float: left; margin-right:5px;"><img src="/ourspaces/icons/001_49.png" align="left" border="0"/>Trash</a>
					</div>    
			      
			          
		      </div>
           </div>  
           <div class="separator">
              <img src="/ourspaces/icons/separator.png" align="right" style="margin: 0px;" border="0"/> 
           </div>     
       </div>  


       <div class="navBarLeft" style="z-index; -1;" >
           <div class="dropdownBoxFixed">
           <a onClick="showNewMessageDialog();"><img src="/ourspaces/icons/001_45.png" align="left" border="0"/>New Message</a>
            </div>
           <div class="separator">
              <img src="/ourspaces/icons/separator.png" align="right" style="margin: 0px;" border="0"/> 
           </div>          
       </div> 
       
        <div class="navBarLeft" style="z-index; -1;" >
           <div class="dropdownBoxFixed">
            <a href="#"  id="Delete"><img src="/ourspaces/icons/001_29.png" align="left" border="0"/>Delete Message</a>
           </div>
           <div class="separator">
              <img src="/ourspaces/icons/separator.png" align="right" style="margin: 0px;" border="0"/> 
           </div>          
       </div> 
       
        

     

<jsp:include page="/top_tail.jsp" />

<% 



common.User user = (common.User) session.getAttribute("use");
int id = user.getID();
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));
String title = user.getTitle(id);
String name = user.getName(id);

String number = user.getHouseNumber(id);
String street = user.getStreet(id);
String town = user.getTown(id);
String country = user.getCountry(id);
String postcode = user.getPostCode(id);

String interests = user.getResearchInterests(id);
String website = user.getWebsite(id);

if(website.startsWith("http://"))
{
	// Do nothing
}
else
{
	website = "http://" + website;
}

String photo = user.getPhoto(id);


ArrayList projectList = projects.getAllProjectsAboutUser(user.getFOAFID(id));


String rdfUserID = user.getUserRDFID(id);
String profileContainer = rdf.getUserProfileContainer(rdfUserID);
ArrayList blogContainer = rdf.getBlogContainer(profileContainer);

String temp = (String) blogContainer.get(0);
String[] fieldsB = temp.split("#");

ArrayList blogPosts = blog.getBlogPosts((String)blogContainer.get(0));

String firstmsg="";
%>
  

<script type="text/javascript">
$(document).ready(function(){
	
	var res;
	
	$("#dialog").dialog({
	      autoOpen: false,
	      modal: true
	    });

	  $("#Delete").click(function(e) {
	    e.preventDefault();
	    var targetUrl = $(this).attr("href");

	    res= $("#dialog").dialog('option', 'buttons', {
            "Confirm" : function() {
            	deleteMessage();
            	 $(this).dialog("close");
                },
            "Cancel" : function() {
                $(this).dialog("close");
                
                }
            });

	    $("#dialog").dialog("open");
		
	  });

	

	
	
	
	
	loadeventfunctions();
	$('.loadmore').click(function(){
		var userid=$("#userid").text();
		var currentId = $(this).attr('id');

		$.get('/ourspaces/commspaceLoader?action=sent&user='+userid+'&start='+currentId,
			function (data){
			//alert(data);
				var json = eval('('+data+')');
				var lstmsg;
				var counter=0;
				for (i=0;i<json.length;i++){
					
					
					
					$("#msgpanel").append( "<div class='msgrow'  id="+json[i].id+"><span style='float:left;width:240px' class='msgsender'>"+json[i].sender+"</span><span class='msgsent' style='float:left;width:100px'>"+json[i].sentdate+"</span><br/><span style='float:left;width:300px;display:block;padding-top:5px;' class='msgsubject'>"+json[i].subject+"</span><span class='msgtxt' style='display:none;'>"+json[i].content+"</span><span style='display:none;' class='otherrecs'>"+json[i].recipients+"</span><span class='otherrecids' style='display:none;'>"+json[i].otherrecids+"</span></div>");
					
					lastmsg=json[i].id;
					counter++;
				}   
				//alert(lastmsg);
				//$(this).removeAttr('id');
				$('.loadmore').attr('id',lastmsg);	
				if (counter<8)
					$('.loadmore').hide();	
				
				loadeventfunctions();
		});
		
	});
	
	
	
  function loadeventfunctions(){
	  $(".msgrow").click(function () { 
	$('#mail_message').show('slow');
	$('#rightmessage').show('slow');
	$('#contentnew').val('');
	$('#contentnew').hide();
	$('#reply').show();
	
	$('#replyall').show();
	$('#send').hide();
	$('#sendall').hide();
	$('#replyheader').hide();
	$('#replyheader2').hide();
	$('#firstmsg').hide();
	$(".msgrow").removeClass("selected");
	 $(this).addClass("selected");
	 var selmsgid=$(this).find(".msgid").html();
	 getMessage(selmsgid);	 
	 updateReadStatus(selmsgid);
	 
    });
    
    
    $(".msgrow").hover(function () {
      $(this).addClass("hilite");
    }, function () {
      $(this).removeClass("hilite");
    });
  
 
}  
  
  
    
});




function updateReadStatus(msgid)
{

	
	var xmlHttpRequest=init();

	  function init(){

	if (window.XMLHttpRequest) {
	           return new XMLHttpRequest();
	       } else if (window.ActiveXObject) {
	           
	           return new ActiveXObject("Microsoft.XMLHTTP");
	       }

	}

	  /* var msgid = document.getElementById("selmsgid").innerHTML;
		var recid = document.getElementById("seluserid").innerHTML; */
	//alert("/ourspaces/messagesHandler?action=read&id="+msgid+"&recipient="+recid);
		var userid=$("#userid").text();
	xmlHttpRequest.open("GET", "/ourspaces/messagesHandler?action=read&id="+msgid+"&recipient="+userid, true);
	xmlHttpRequest.onreadystatechange=function() {
		if(xmlHttpRequest.readyState==4){
	    	   if(xmlHttpRequest.status==200){

	    		   var xmlMessage=xmlHttpRequest.responseXML;

	    		  
						
    	    	   
	    	   }
		}


	};
	
	xmlHttpRequest.send(null);
}


function getMessage(msgid)
{

	
	var xmlHttpRequest=init();

	  function init(){

	if (window.XMLHttpRequest) {
	           return new XMLHttpRequest();
	       } else if (window.ActiveXObject) {
	           
	           return new ActiveXObject("Microsoft.XMLHTTP");
	       }

	}
		//var msgid=238;
	  //var msgid = document.getElementById("selmsgid").innerHTML;
	//	var recid = document.getElementById("seluserid").innerHTML;
	//alert("/ourspaces/messagesHandler?action=read&id="+msgid+"&recipient="+recid);
	xmlHttpRequest.open("GET", "/ourspaces/getmsgservlet?id="+msgid, true);
	xmlHttpRequest.onreadystatechange=function() {
		if(xmlHttpRequest.readyState==4){
	    	   if(xmlHttpRequest.status==200){

	    		   var xmlMessage=xmlHttpRequest.responseText;
					//alert(xmlMessage);
					var JSONdata = eval('(' + xmlMessage + ')');
					
					document.getElementById("messagesender").innerHTML=	JSONdata.sender;
					document.getElementById("messagesenderid").innerHTML=	JSONdata.senderid;
					 var recipients="";
					 recipients=JSONdata.recs;
					 var recipientarray=String(recipients).split(",");
					
					 var recnames="<div style='float:left;padding-right:5px;background-color:#fff' class='ui-corner-all'>";
					 var readstatus="";
					 for (i=0;i< recipientarray.length;i++){
						 if (recipientarray[i].contains("(R)")){
							 readstatus="<img class='imgmsg' style='margin:0px;border:none;' src='/ourspaces/images/tic.png'/ width='12px'/>";

						 }else{
							 readstatus="<img class='imgmsg' style='margin:0px;border:none;' src='/ourspaces/images/cross.png'/ width='12px'/>";

						 }
						 recnames=recnames+recipientarray[i].substring(0, recipientarray[i].length-3)+readstatus+"</div><div style='float:left;padding-right:5px;background-color:#fff;' class='ui-corner-all'>";
						 
					 }
					
					 recnames=recnames.substring(0, recnames.length-5);
					
					 
					document.getElementById("messagerecipeints").innerHTML=	recnames;
					document.getElementById("messageid").innerHTML=	JSONdata.id;
					var recids=String(JSONdata.recids).split(",");
					var otherrecipientidtags="";
					for (i=0;i< recids.length;i++){
						 
						 otherrecipientidtags=otherrecipientidtags+'<span style="display:none;"class="othrecids">'+recids[i].trim() +"</span>";
						 
					 }
					document.getElementById("messagerecipeintsids").innerHTML=otherrecipientidtags	;
					document.getElementById("messagesent").innerHTML=	JSONdata.sent;
					document.getElementById("messagesubject").innerHTML=	JSONdata.title;
					document.getElementById("bodytext").innerHTML=	JSONdata.content;
	    	   }
		}


	};
	
	xmlHttpRequest.send(null);
}

function replyall(){
	document.getElementById("newmessagerecipeints").innerHTML=	document.getElementById("messagerecipeints").innerHTML;
	document.getElementById("subjectnew").value=	document.getElementById("messagesubject").innerHTML;
	
}
function reply(){
	document.getElementById("newmessagerecipeints").innerHTML=	document.getElementById("messagesender").innerHTML;
	document.getElementById("subjectnew").value=	document.getElementById("messagesubject").innerHTML;
	
}

</script>
<script type="text/javascript">
$(document).ready(function() {
	 // hides the stuffs as soon as the DOM is ready
	  	$('#newbodytext').ourSpacesProvenanceTagging();
		  $('#newmesgheader').hide();
		  $('#newmesgbody').hide();
		  $('#newcontrols').hide();
		
	  
	  
	  $('.msgrow:first').addClass("selected");
	  //===========first row
	  var msgid= $('.msgrow:first').attr("id");
	  getMessage(msgid);
		
	  //========
	 // shows the stuffs 
	  $('#reply').click(function() {
		 
		  $('#controls').hide();
		  
		  $('#newmesgheader').show();
		  $('#newmesgbody').show();
		  $('#newcontrols').show();
		  $('#newsend').show();
		  $('#newsendall').hide();
		  
	  });
	  $('#replyall').click(function() {
		  $('#controls').hide();
		  $('#newsend').hide();
		  $('#newmesgheader').show();
		  $('#newmesgbody').show();
		  $('#newcontrols').show();
		  $('#newsendall').show();
		  
		  
		  
	  });
	  
	  $('#newcancel').click(function() {
		  $('#controls').show();
		  $('#newmesgheader').hide();
		  $('#newmesgbody').hide();
		  $('#newcontrols').hide();
		  
		  
		  
	  });
	  $('#newsend').click(function() {
		  $('#controls').show();
		  $('#newmesgheader').hide();
		  $('#newmesgbody').hide();
		  $('#newcontrols').hide();
		  
		  
		  
	  });
	  $('#newsendall').click(function() {
		  $('#controls').show();
		  $('#newmesgheader').hide();
		  $('#newmesgbody').hide();
		  $('#newcontrols').hide();
		  
		  
		  
	  });
	 
});
</script>

<script type="text/javascript">



function listPeopleAll()
{
	var finalList = "";
	var list = document.getElementsByTagName("span");
	
	for( var i = 0; i < list.length; i++)
	{
		if(list[i].className == "othrecids")
		{
			//alert(list[i].innerHTML);
			if(i < list.length - 1)
			{
				finalList = finalList + "person="+encodeURIComponent(list[i].innerHTML) + "&";
			}
			else
			{
				finalList = finalList + "person="+encodeURIComponent(list[i].innerHTML);
			}
		}	
		
	}
	sendMessage(finalList);
	
}





function listPeople()
{
	var finalList = "";
	var list = document.getElementsByTagName("span");
	
	for( var i = 0; i < list.length; i++)
	{
		if(list[i].className == "senderid")
		{
			//alert(list[i].innerHTML);
			if(i < list.length - 1)
			{
				finalList = finalList + "person="+encodeURIComponent(list[i].innerHTML) + "&";
			}
			else
			{
				finalList = finalList + "person="+encodeURIComponent(list[i].innerHTML);
			}
		}	
		
	}
	sendMessage(finalList);
	
}

function sendMessage(list)
{

  
	var xmlHttpRequest=init();

	  function init(){

	if (window.XMLHttpRequest) {
	           return new XMLHttpRequest();
	       } else if (window.ActiveXObject) {
	           
	           return new ActiveXObject("Microsoft.XMLHTTP");
	       }

	}
	var msgid = document.getElementById("messageid").innerHTML;
	var title = document.getElementById("subjectnew").value;
	var content = document.getElementById("newbodytext").value; 
	//alert("/ourspaces/messagesHandler?action=reply&"+list+"&title="+encodeURIComponent(title)+"&content="+encodeURIComponent(content), true)+"&threadid="+msgid);
	xmlHttpRequest.open("GET", "/ourspaces/messagesHandler?action=reply&"+list+"&title="+encodeURIComponent(title)+"&content="+encodeURIComponent(content)+"&threadid="+msgid, true);
	xmlHttpRequest.onreadystatechange=function() {
		if(xmlHttpRequest.readyState==4){
	    	   if(xmlHttpRequest.status==200){

	    		   var xmlMessage=xmlHttpRequest.responseXML;


						document.getElementById('confirmreply').style.display = 'block';
						document.getElementById('recipients').style.display = 'none';
    	    	   
	    	   }
		}


	};
	
	xmlHttpRequest.send(null);
}


function deleteMessage()
{

	
	var xmlHttpRequest=init();

	  function init(){

	if (window.XMLHttpRequest) {
	           return new XMLHttpRequest();
	       } else if (window.ActiveXObject) {
	           
	           return new ActiveXObject("Microsoft.XMLHTTP");
	       }

	}

	  var msgid = document.getElementById("messageid").innerHTML;
		//var recid = document.getElementById("seluserid").innerHTML;
	//alert("/ourspaces/messagesHandler?action=delete&id="+msgid+"&recipient="+recid);
	var conf=confirm('Are you sure you want to delete the message?');
	if (conf){
	xmlHttpRequest.open("GET", "/ourspaces/messagesHandler?action=delete&id="+msgid, true);
	xmlHttpRequest.onreadystatechange=function() {
		if(xmlHttpRequest.readyState==4){
	    	   if(xmlHttpRequest.status==200){

	    		   var xmlMessage=xmlHttpRequest.responseXML;

	    		   document.getElementById(msgid).style.display = 'none';
						/*document.getElementById('confirmdel').style.display = 'block';
						document.getElementById('message').style.display = 'none'; */
    	    	   
	    	   }
		}


	};
	}
	xmlHttpRequest.send(null);
}
function hideSent()
{

	
	var xmlHttpRequest=init();

	  function init(){

	if (window.XMLHttpRequest) {
	           return new XMLHttpRequest();
	       } else if (window.ActiveXObject) {
	           
	           return new ActiveXObject("Microsoft.XMLHTTP");
	       }

	}

	  var msgid = document.getElementById("messageid").innerHTML;
		//var recid = document.getElementById("seluserid").innerHTML;
	//alert("/ourspaces/messagesHandler?action=delete&id="+msgid+"&recipient="+recid);
	/* var conf=confirm('Are you sure you want to delete the message?');
	if (conf){ */
	xmlHttpRequest.open("GET", "/ourspaces/messagesHandler?action=hidesent&id="+msgid, true);
	xmlHttpRequest.onreadystatechange=function() {
		if(xmlHttpRequest.readyState==4){
	    	   if(xmlHttpRequest.status==200){

	    		   var xmlMessage=xmlHttpRequest.responseXML;

	    		   document.getElementById(msgid).style.display = 'none';
						/*document.getElementById('confirmdel').style.display = 'block';
						document.getElementById('message').style.display = 'none'; */
    	    	   
	    	   }
		}


	};
	/* } */
	xmlHttpRequest.send(null);
}
</script>
<style>
.hidden { display: none; }
.unhidden { display: block; }
.hilite {background-color: #999;}
.new {font-style: italic;font-weight:bold;}
.selected {background-color: #ccc;}
.msgrow{width:100%;height:40px;float:left;position:relative; border-bottom-style: dotted;border-bottom-color: #666;border-bottom-width:1px;}
.msgimg{margin:0px;border:none;float:left:}
</style>

  
  <style type="text/css">
<!--
.style1 {
	color: #FF6600;
	font-size: 16px;
	padding-bottom: 10px;
}
.style2 {color: #FF6600; font-size: 16px; }
-->
  </style>




 <div class="homeStatusContainer" style="color:#FFFFFF; position:relative;" >
  <div id="userid" style="display:none"><%=id %></div>
  
<!--  <div class="ui-state-highlight ui-corner-all" style="margin-left:10px;margin-right:10px;margin-top:10px;height:20px;padding:10px">
<div style="padding-left:10px;">

			<span class="ui-icon ui-icon-folder-open" style="display: block; float: left; margin-right:3px;"></span><a style="display: block; float: left;" href="/ourspaces/messages/mymessagesNew.jsp">Inbox</a>
		<span class="ui-icon ui-icon-arrowthick-1-nw" style="display: block; float: left; margin-right:3px; margin-left: 20px;"></span> <a style="display: block; float: left;" href="/ourspaces/messages/mysentmessagesNew.jsp">Sent</a>
		<span class="ui-icon ui-icon-trash" style="display: block; float: left; margin-right:3px;margin-left: 20px;"></span><a style="display: block; float: left;" href="/ourspaces/messages/mydeletedmessages.jsp">Trash</a>
		<span class="ui-icon ui-icon-mail-closed" style="display: block; float: left; margin-right:3px; margin-left: 20px;"></span> <a style="display: block; float: left;" href="newmessage.jsp" rel="facebox">New Message</a>
		<span class="ui-icon ui-icon-closethick" style="display: block; float: left; margin-right:3px; margin-left: 20px;"></span><a style="display: block; float: left;" id="Delete"  href="#" style="margin:10px" >Delete Message</a>
</div>
</div> -->

<div id="columns" style="position:relative;">

<ul id="column2" class="column" style="width: 400px;">


 <%
 Vector messages = (Vector) user.getSentMessages(id,0,0);
 if (messages.size() > 0) { 
 %>

    
                    
        <% } %>            
                    
                        <li class="widget color-yellow " id="mail_main">
                        <div class="widget-head">
                            <h3>Sent</h3>
                        </div>
                        <div class="widget-content">
            <div style="padding: 10px;">
            <div id="msgpanel" style="font-size:11px;width:360px;" >
	<%
			
			int mesgid=0;
			for(int i = 0; i < messages.size()&& i<8; i++)
			{
				Message m= new Message();
				m=(Message) messages.get(i);
				 mesgid=m.getID();
				
				int uname = m.getSender();
				String subject = m.getSubject();
				if(subject == null || subject.equals(""))
						subject = "No subject";
				Date sentDate=m.getSent();
				String sentdate="";
				if(sentDate != null)
				{
					sentdate=new SimpleDateFormat("dd-MMM-yyyy").format(m.getSent());
				}
				else
				{
					sentdate="null";
				}
					
				String sender =user.getName(m.getSender());
				String msgtxt=m.getContent();
				String recipients="";
				String recipientids="";
				if (!m.getRecipients().isEmpty())
				{
					recipients=m.getRecipients().toString();
					recipientids=m.getRecipientIds().toString();
				}
				else
				{
					recipients=  "None";
				}
				String unread="";
				
				common.Utility.log.debug(m.getRecipients());
				%>
			
                <div class="msgrow"  id=<%=mesgid %>>
               
				    <span class="msgsender" style="float:left;width:240px">   
				  		<%=sender%>
				    </span>
				   
				    <span class="msgsent" style="float:left;width:100px">
				  		<%=sentdate%>
				    </span>
				    <br/>
				     <span class="msgsubject" style="float:left;width:300px;display:block;padding-top:5px;">   
				  		<%=subject%>
				    </span>
				    <span>
				  		<span class ="msgid" style="display:none;"><%=mesgid%></span>
				  		<span class ="otherrecs" style="display:none;"><%=recipients %></span>
				  		<span class ="otherrecids" style="display:none;"><%=recipientids %></span>
	      				<span class ="userid" style="display:none;"><%=uname%></span> 
	      		
				    </span>
				    <span class="msgtxt" style="display:none;"><%=msgtxt %></span>
				    
				    
                 </div>
                 
                 
                 
                 
				 <% } %>
	</div>
	</div>
	<div style="float:right"><a class="loadmore" id=<%= mesgid%> href="#">load more...</a></div>
                        </div>
                        
                    </li>
                    
</ul>
<ul id="column1" class="column">
 					<li class="widget color-yellow " id="mail_message">
                        <div class="widget-head">
                            <h3>Message</h3>
                        </div>
                       
                        <div class="widget-content">
                       <div id="newmessage">
                        <div id="newmesgheader" class="ui-state-highlight ui-corner-all" style="margin:5px;padding:5px;width:555px;">
                          	<div style="width:80px;float:left;">To</div><div id="newmessagerecipeints"  style="float:left;"></div>
                          	  <br/>
                        	<div style="width:80px;float:left;">Subject</div><input type="text" id="subjectnew" style="width:400px; border: 1px solid #CCC; margin-bottom:5px;text-align:left;" name="subject">
                        	<br/>
                        </div>
                      
                      
                        <div id="newmesgbody" class="ui-state-highlight ui-corner-all" style="margin:5px;padding:5px;width:555px;height:200px;">
                        	<textarea name="newbodytext" id="newbodytext" style="width:540px; margin:5px;border: 1px solid #CCC; height:180px;"></textarea>
                        	<br/>
                        </div>
                        <div id ="newcontrols" class="ui-state-highlight ui-corner-all"  style="margin:5px;padding:5px;width:555px;height:30px">
                         <a id ="newcancel" class="fg-button ui-state-active fg-button-icon-right ui-corner-all" href="#"  onclick ='getMessage(238)' >Cancel</a>
                         <a id ="newsend" class="fg-button ui-state-active fg-button-icon-right ui-corner-all" href="#"  onclick ='listPeople()' >Send</a>
                         <a id ="newsendall" class="fg-button ui-state-active fg-button-icon-right ui-corner-all" href="#"  onclick ='listPeopleAll()' >Send</a>
  
                         </div>
                         </div>
                         
                         
                         
                         
                        <div id="rightmessage">
                        <div id="mesgheader" class="ui-state-highlight ui-corner-all" style="margin:5px;padding:5px;width:555px;">
                        	<div id="messageid"  style="display:none;"></div>
                        	<div style="width:80px;float:left;">Sender</div><div id="messagesender" style="float:left;"></div>
                        	<span id="messagesenderid"  class="senderid" style="display:none;"></span>
                        	<br/>
                        	<div style="width:80px;float:left;">Recipients</div><div id="messagerecipeints"  style="float:left;"></div>
                        	<div id="messagerecipeintsids"  style="display:none;"></div>
                        	<br/>
                        	<div style="width:80px;float:left;">Subject</div><div id="messagesubject"  style="float:left;"></div>
                        	<br/>
                        	<div style="width:80px;float:left;">Date Sent</div><div id="messagesent"  style="float:left;"></div>
                        	<br/>
                        </div>
                        <div id="mesgbody" class="ui-state-highlight ui-corner-all" style="margin:5px;padding:5px;width:555px;height:200px;">
                        	<div id="bodytext" class="ui-corner-all" style="margin:5px;padding:5px;background-color:#fff;width:540px;height:180px;"></div>
                        	<br/>
                        </div>
                        <div id ="controls" class="ui-state-highlight ui-corner-all"  style="margin:5px;padding:5px;width:555px;height:30px">
                         <a id ="reply" class="fg-button ui-state-active fg-button-icon-right ui-corner-all" href="#"  onclick ='reply()' >Reply</a>
                         <a id ="replyall" class="fg-button ui-state-active fg-button-icon-right ui-corner-all" href="#"  onclick ='replyall()' >Reply All</a>
                         </div>
                         </div>
                         
                         
                         
                    
                       
                       
                      </div>  
                    </li>
                    
                    
                    
                  	
                     
             
                      
                    
                    
                    
</ul>



</div> <!--  end of columns -->
<!-- 
            <script type="text/javascript" src="/ourspaces/jquery-ui-personalized-1.6rc2.min.js"></script>
            <script type="text/javascript" src="/ourspaces/cookie.jquery.js"></script>
            <script type="text/javascript" src="/ourspaces/inettuts.jsp?space=profile-<%=id%>"></script>
            
-->
</div> <!--  End of home status -->

  <jsp:include page="/bottom.jsp" />
