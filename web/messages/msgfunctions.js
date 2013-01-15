function initPage(){


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

		
	    
		 // hides the stuffs as soon as the DOM is ready
		  	
		 
		  	$('#newbodytext').ourSpacesProvenanceTagging(
		  			{
		  				provID:"provenancelinks",
		  				discID:"discourse"
		  			}
		  	);
			  $('#newmesgheader').hide();
			  $('#newmesgbody').hide();
			  $('#newcontrols').hide();
			
		  
		  
		
			
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
			  $('#provenancelinks').empty();
			  $('#discourse').empty();
			  
			  
			  
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
		  
		  loadeventfunctions();
		  
		  $('.msgrow:first').addClass("selected");
		  //===========first row
		  var msgid= $('.msgrow:first').attr("id");
		  getMessage(msgid);
		  
		  
			
			
		 
	});
	
	
	
}


/* loads new message dialog */
function nlglistner(){
	
	$(".nlg").each(function(){
 	  
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
	
}

function showNewMessageDialog(uname){
	
	var query;
	if (uname !=null)
		query = "/ourspaces/messages/newmessage.jsp?to="+uname;
	else
		query = "/ourspaces/messages/newmessage.jsp";
	
	
	//Get the content of the dialog.
	$.get(query, function(data) {
		//Trim the data.
		data = data.replace(/^\s+|\s+$/g, '') ;
		var div = $("<div>");
		div.attr("id","newMessage");
		div.appendTo("body");
		div.html(data);
		div.width(700);
		$( "#newMessage" ).dialog({
			width:700,
			position: 'center',
			resizable: true,
			modal: true,
			autoOpen: false,  
			title: 'New Message', 
			dialogClass : "alertDialog",
			
			close: function(event, ui) {
				$("#newMessage").remove();
				
				
			}
		});
		$("#newMessage").dialog('open');
		
	});
}


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
		 var selmsgid=$(this).attr("id");
		 getMessage(selmsgid);	 
		 updateReadStatus(selmsgid);
		 //add NLG Listner
	       
		 
		 
		 
});

	 $(".msgrow").hover(function () {
	      $(this).addClass("hilite");
	    }, function () {
	      $(this).removeClass("hilite");
});

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

	xmlHttpRequest.open("GET", "/ourspaces/getmsgservlet?id="+msgid, true);
	xmlHttpRequest.onreadystatechange=function() {
		if(xmlHttpRequest.readyState==4){
	    	   if(xmlHttpRequest.status==200){

	    		   var xmlMessage=xmlHttpRequest.responseText;
					//alert(xmlMessage);
					var JSONdata = eval('(' + xmlMessage + ')');
					
					document.getElementById("messagesender").innerHTML=	JSONdata.sender;
					document.getElementById("messagesenderid").innerHTML=	JSONdata.senderfoaf;
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
					document.getElementById("threadid").innerHTML=	JSONdata.threadid;
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
		annotateTerms();
		nlglistner();

	};
	
	
	xmlHttpRequest.send(null);
	
}



function selectFirstRow(){
	
	$('.msgrow:first').addClass("selected");
	  //===========first row
	  var msgid= $('.msgrow:first').attr("id");
	  getMessage(msgid);
	
}



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

	  /* var msgid = document.getElementById("selmsgid").innerHTML;*/
		var userid = document.getElementById("userid").innerHTML; 
	//alert("/ourspaces/messagesHandler?action=read&id="+msgid+"&recipient="+recid);
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




function replyall(){
	document.getElementById("newmessagerecipeints").innerHTML=	document.getElementById("messagerecipeints").innerHTML;
	document.getElementById("subjectnew").value="re:"+	document.getElementById("messagesubject").innerHTML;
	
}
function reply(){
	document.getElementById("newmessagerecipeints").innerHTML=	document.getElementById("messagesender").innerHTML;
	document.getElementById("subjectnew").value="re:"+	document.getElementById("messagesubject").innerHTML;
	
}

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
	var provList = "&";
	$(document).ready(function() {$('input[name="provenanceLink"]').each(function (i) {

	provList = provList + "prov="+encodeURIComponent(this.value) + "&";
 	});
	});
	provList = provList.substring(0, provList.length-1);
	
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
	
	sendMessage(finalList,provList);
}

function sendMessage(list1,list2)

{

  
	var xmlHttpRequest=init();

	  function init(){

	if (window.XMLHttpRequest) {
	           return new XMLHttpRequest();
	       } else if (window.ActiveXObject) {
	           
	           return new ActiveXObject("Microsoft.XMLHTTP");
	       }

	}
	var threadid = document.getElementById("threadid").innerHTML;
	var title = document.getElementById("subjectnew").value;
	var content = document.getElementById("newbodytext").value; 
	
	$.ajax({
		
		type:'GET',
		url:'/ourspaces/messagesHandler?action=reply&'+list1+list2+'&title='+encodeURIComponent(title)+'&content='+encodeURIComponent(content)+"&threadid="+threadid,
		
		
		dataType: "html",
		success:function (){
			$('#confirm').show();
			
		},
		error:function(){
			alert('ERROR');
		}
		
	});
	
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
	  var delaction = document.getElementById("delaction").innerHTML;
	  var msgid = document.getElementById("messageid").innerHTML;
		//var recid = document.getElementById("seluserid").innerHTML;
	//alert("/ourspaces/messagesHandler?action=delete&id="+msgid+"&recipient="+recid);
	/* var conf=confirm('Are you sure you want to delete the message?');
	if (conf){ */
	xmlHttpRequest.open("GET", "/ourspaces/messagesHandler?action="+delaction+"&id="+msgid, true);
	xmlHttpRequest.onreadystatechange=function() {
		if(xmlHttpRequest.readyState==4){
	    	   if(xmlHttpRequest.status==200){

	    		   var xmlMessage=xmlHttpRequest.responseXML;

	    		   document.getElementById(msgid).style.display = 'none';
	    		   selectFirstRow();
						/*document.getElementById('confirmdel').style.display = 'block';
						document.getElementById('message').style.display = 'none'; */
    	    	   
	    	   }
		}


	};
	/*}*/
	xmlHttpRequest.send(null);
}
