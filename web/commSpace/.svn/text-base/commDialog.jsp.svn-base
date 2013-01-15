
<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.util.Vector,java.util.*,java.text.*, java.net.URLEncoder,common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="projects" class="common.Project" />
<jsp:useBean id="blog" class="common.Blog" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="message" class="common.Message"/>
<jsp:useBean id="msg" class="common.Message" />
<%-- <jsp:include page="/top_head.jsp" />
<jsp:include page="/top_tail.jsp" /> --%>
<link rel="stylesheet" type="text/css" href="/ourspaces/commSpace/comms.css" />
<style>
.ui-icon{background-image: url(/ourspaces/css/custom-theme/images/ui-icons_a83300_256x240.png) !important;}

#makeMeDraggable { width: 300px; height: 100px; background: grey; }
#draggableHelper { width: 300px; height: 100px; background: grey; }
#makeMeDroppable { float: right; width: 300px; height: 300px; border: 1px solid #999; }
.msgimg{margin:0px;border:none;float:right;padding:0pxwidth:16px;height:16px;}
.active{border: dashed medium #000000; border-radius:5px;}
.collector{border: dashed medium #999; border-radius:5px;}
.deleted{border: dashed medium #ff0000; border-radius:5px;}
.hidden {display:none}
.hilite {background-color: #999;}
.artifactcomments{float:left;border-bottom: 1px solid #999;margin-bottom:2px; with:540px}
.blogcomments{float:left;border-bottom: 1px solid #999;margin-bottom:2px; with:540px}
#feedback { font-size: 1.4em; }
	#selectable .ui-selecting { background: #FECA40; }
	#selectable .ui-selected { background: #F39814; color: white; }
	#selectable { list-style-type: none; margin: 0; padding: 0; width: 60%; }
	#selectable li { margin: 3px; padding: 0.4em; font-size: 1.4em; height: 18px; }
	
	.comitem.ui-draggable-dragging { background: #999; }
	</style>
<script type="text/javascript">
 
$(document).ready(function(){
	var resid = $("#resuri").html();
	var fileuri = $("#fileuri").html();
	var title = $("#title").html();
	if( typeof fileuri != 'undefined' && fileuri != null){
		$("#savebutton").hide();
		loadItems(fileuri,title);
	}else{
		$("#editbutton").hide();
	}
	
	
	
	
	
	$("#cancelbutton").click(function(){
		
		$('.objectSortable').each(function(index) {
			removeItem( this.id,this);
			
		    
		});
		$("#selectedCommstitle").html("Selected Communication Items");
		
	});
	
	
	

	
	$("#savebutton").click(function(){
		
		var jsonStr=getIDs();
		
		$.post('/ourspaces/commspaceHandler?action=save&comids='+jsonStr, function(data) {
			$('.objectSortable').each(function(index) {
				removeItem( this.id,this);
	    
			});
			$("#selectedCommstitle").html("Selected Communication Items");
          	showUploadDialog({
          		resourceType : "Communication", 
          		fileURI :data
          	});
          	$("#commDialog").remove();
			});
	
	});
	
$("#editbutton").click(function(){
		
		var jsonStr=getIDs();
		
		$.post('/ourspaces/commspaceHandler?action=save&comids='+jsonStr, function(data) {
			$('.objectSortable').each(function(index) {
				removeItem( this.id,this);
	    
			});
			$("#selectedCommstitle").html("Selected Communication Items");
			/* recId="http://openprovenance.org/ontology#"+$("#fileUri").html();	 */
          	showUploadDialog({
          		resourceType : "Communication", 
          		fileURI :data,
    			resourceId: resid
          	});
          	$("#commDialog").remove();
			});
	
	});
	
	

	
	
	$(".comitem").dblclick(function (){
		//alert(this.id);
		 dropItem(null,this,"true");
		
	});
	$(".comitem").hover(function () {
	      $(this).addClass("hilite");
	    }, function () {
	      $(this).removeClass("hilite");
	});
	$(function() {
		$( "#accordion" ).accordion({
			fillSpace: true
		});
	});
	$(function() {
		$( "#accordionResizer" ).resizable({
			minHeight: 400,
			resize: function() {
				$( "#accordion" ).accordion( "resize" );
			}
		});
	});
    $(".comitem").draggable({
    		helper:'clone',
    		appendTo: 'body',
    		drag:function(){
    			 $("#selectedComms").removeClass("collector");
    			 $("#selectedComms").addClass("active");
    		},
    		stop:function(){
    			
   			 $("#selectedComms").removeClass("active");
   			 $("#selectedComms").addClass("collector");
   		}
    		
    	});  

    $("#selectedComms").droppable({
            accept: ".comitem",
            drop: dropItem
        
    });

   
    
});


function loadItems(currentId,title){
	

	$("#selectedCommstitle").html(title);
	$('.objectSortable').each(function(index) {
		removeItem( this.id,this);
		
	    
	});
	
	

	$.get('/ourspaces/commspaceHandler?action=read&artifactid='+currentId,
		function (data){
			//alert(data);
			
			var json = eval('('+data+')');
			$(".comitem").removeClass("hilite");
			$(".deleted").addClass("hidden");
    		$(".deleted").removeClass("deleted");
			for (var i=0;i<json.length;i++){
				var found=false;
				$('.comitem').each(function(index) {
					if (this.id=="msgID-"+json[i].messageid){
						
						dropItem(null,this,"true");
						found=true;
					}else if  (this.id=="imID-"+json[i].imid){							
						dropItem(null,this,"true");
						found=true;
					}else if  (this.id=="blogID-"+json[i].postid){							
						dropItem(null,this,"true");
						found=true;
					}else if  (this.id=="blogcommID-"+json[i].commentid){							
						dropItem(null,this,"true");
						found=true;
					}else if  (this.id=="ArtefactcommID-"+json[i].artefactcommentid){							
						dropItem(null,this,"true");
						found=true;
					}
					
				});
				if (!found){
					var el;
					if (json[i].messageid !=null)
						 el='<div id="'+json[i].messageid+'" class="comitem  ui-draggable" style="float: left;width:440px; padding-bottom:5px; padding-top:5px; border:dashed thin #ff0000;margin-bottom:5px "><div style="float:left;width:80px"><img border="0" height="10px" src="/ourspaces/images/e-mail_icon1.gif" style="border:none; padding:px; margin:0px; margin-left:6px; margin-right:3px;float:left"><div style="width:80px;float:left; font-size: x-small;">'+json[i].SentDate+'</div></div><div style="position: relative; float:left;width:280px"><div style="width:270px;float:left;margin-left:5px ;font-weight:bold;font-size:x-small ">--Deleted item--</div><div class="msgbody">--Deleted item--</div></div><div style="width:75px;float:left; font-size:x-small">'+json[i].Sender+'</div></div>';
					else if (json[i].imid !=null)
						 el='<div id="'+json[i].imid+'" class="comitem  ui-draggable" style="float: left;width:440px; padding-bottom:5px; padding-top:5px; border:dashed thin #ff0000;margin-bottom:5px "><div style="float:left;width:80px"><img border="0" height="10px" src="/ourspaces/images/chat.png" style="border:none; padding:px; margin:0px; margin-left:6px; margin-right:3px;float:left"><div style="width:80px;float:left; font-size: x-small;">'+json[i].SentDate+'</div></div><div style="position: relative; float:left;width:280px"><div class="msgbody">--Deleted item--</div></div><div style="width:75px;float:left; font-size:x-small">'+json[i].Sender+'</div></div>';
					else if (json[i].postid !=null)
							 el='<div id="'+json[i].postid+'" class="comitem  ui-draggable" style="float: left;width:440px; padding-bottom:5px; padding-top:5px; border:dashed thin #ff0000;margin-bottom:5px "><div style="float:left;width:90px"><img border="0" height="10px" src="/ourspaces/icons/001_31.png" style="border:none; padding:px; margin:0px; margin-left:6px; margin-right:3px;float:left"><div style="width:80px;float:left; font-size: x-small;">'+json[i].SentDate+'</div><div style="width:80px;float:left; font-size:x-small">'+json[i].Sender+'</div></div><div style="position: relative; float:left;width:280px"><div style="width:270px;float:left;margin-left:5px ;font-weight:bold;font-size:x-small ">--Deleted item--</div><div class="msgbody">--Deleted item--</div></div></div>';
					else if (json[i].commentid !=null)
							el='<div id="'+json[i].commentid+'" class="comitem  ui-draggable" style="float: left;width:440px; padding-bottom:5px; padding-top:5px; border:dashed thin #ff0000;margin-bottom:5px "><div style="float:left;width:80px"><img border="0" height="10px" src="/ourspaces/icons/001_58.png" style="border:none; padding:px; margin:0px; margin-left:6px; margin-right:3px;float:left"><div style="width:80px;float:left; font-size: x-small;">'+json[i].SentDate+'</div></div><div style="position: relative; float:left;width:280px"><div style="width:270px;float:left;margin-left:5px ;font-weight:bold;font-size:x-small ">--Deleted item--</div><div class="msgbody">--Deleted item--</div></div><div style="width:75px;float:left; font-size:x-small">'+json[i].Sender+'</div></div>';
					else if (json[i].artefactcommentid !=null)
							el='<div id="'+json[i].artefactcommentid+'" class="comitem  ui-draggable" style="float: left;width:440px; padding-bottom:5px; padding-top:5px; border:dashed thin #ff0000;margin-bottom:5px "><div style="float:left;width:80px"><img border="0" height="10px" src="/ourspaces/icons/001_58.png" style="border:none; padding:px; margin:0px; margin-left:6px; margin-right:3px;float:left"><div style="width:80px;float:left; font-size: x-small;">'+json[i].SentDate+'</div></div><div style="position: relative; float:left;width:280px"><div style="width:270px;float:left;margin-left:5px ;font-weight:bold;font-size:x-small ">--Deleted item--</div><div class="msgbody">--Deleted item--</div></div><div style="width:75px;float:left; font-size:x-small">'+json[i].Sender+'</div></div>';

					
					
					dropItem(null,el,"true");
					
				}
					
				
				
			}  
		 			
	});
}
function dropItem(event,ui,op){
	var el;
	if(typeof ui.draggable != "undefined" && ui.draggable != false){
		el = ui.draggable;
		op="true";
	}
	else // if was clicked onto
	{
		
		el =$(ui);
	}
    console.log("Item was Dropped");
    var clone=$(el).clone();
    clone.find(".artifactcomments").removeClass("hidden");
    clone.find(".blogcomments").removeClass("hidden");
    clone.removeClass("comitem");
    clone.addClass("objectSortable");
    clone.removeClass("hilite");
    if (op=="true")
    	clone.append('<span onclick="removeItem('+"'"+clone.attr("id")+"'"+',this.parentNode)" class="removeitem ui-icon ui-icon-circle-minus" style="float: right; display:block;"></span>');
    $("#selectedComms").append(clone);
    $(el).draggable( 'disable' );
  
    
    
}
function getIDs()
{
	var commIDs = "";
	
	
	$('.objectSortable').each(function(index) {
		commIDs=commIDs+ "{"+$(this).attr("id").split("-")[0]+":"+$(this).attr("id").split("-")[1] +"},";
	});
	var newStr = commIDs.substring(0, commIDs.length-1);
	newStr="["+newStr+"]";
	/* var myObject = eval('(' + newStr + ')');
	
	 alert( myObject[0].msgID); */
	 return newStr;
	
}
function removeItem( itemid,element){
	
	
	$( element ).remove();
	 $("#"+itemid).draggable( 'enable' );
	
}

</script>
	<script>
	$(function() {
		$( "#selectedComms" ).sortable({
			helper:'original'
		});
		$( "#selectedComms" ).disableSelection();
	});
	 
	$(function() {
		$( "#selectable" ).selectable();
	});
	
	
	</script>
<% 



common.User user = (common.User) session.getAttribute("use");
int id = user.getID();
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));
	common.Utility.log.debug("Messages....");
	long start = System.currentTimeMillis();
	Vector messageList = user.getMessagesSent_Received(user.getID(),"threadid");
	long elapsedTimeMillis = System.currentTimeMillis()-start;
	common.Utility.log.debug(elapsedTimeMillis);
	common.Utility.log.debug("Instant Messages....");
	ArrayList instantMessages=user.getInstantMessages(user.getID());
	elapsedTimeMillis = System.currentTimeMillis()-start;
	common.Utility.log.debug(elapsedTimeMillis);
	String rdfUserID = user.getUserRDFID(id);
	
	String profileContainer = rdf.getUserProfileContainer(rdfUserID);
	ArrayList blogContainer = rdf.getBlogContainer(profileContainer);
	common.Utility.log.debug("blogs....");
	String temp = (String) blogContainer.get(0);
	String[] fieldsB = temp.split("#");

	ArrayList blogPosts = blog.getBlogPostsabout((String)blogContainer.get(0),id);
	elapsedTimeMillis = System.currentTimeMillis()-start;
	common.Utility.log.debug(elapsedTimeMillis);
	common.Utility.log.debug("resourcess....");
	java.util.Vector resources = user.getResources( user.getID());
	elapsedTimeMillis = System.currentTimeMillis()-start;
	common.Utility.log.debug(elapsedTimeMillis);
	common.Utility.log.debug("com resources....");
	//Vector myComres = user.getResourcesByType(user.getID(),"http://www.policygrid.org/provenance-generic.owl#Communication");
%>
 

    
 <div class="homeStatusContainer" style="color:#FFFFFF; position:relative;" >
<div id="columns" style="position:relative;">

<ul id="column2" class="column" style="width: 500px;height:600px">
	<li class="widget color-yellow " id="mail_main">
        
       		 <div class="widget-content">


<div id="accordion" >
	<h3><a href="#">Messages<img border="0" style="border:none; padding:px; margin:0px; margin-left:6px; margin-right:3px;float:right" src="/ourspaces/images/e-mail_icon1.gif"></a></h3>
	<div style ="padding:5px;with:490px;height:650px">
		   				
				
				
				 
		<%
		//messages
		int currtid=0;
 		if (messageList.size() > 0) { 
				msg = (Message) messageList.get(0);
				currtid=msg.getThreadID();
 		}		
	  			for(int i = 0; i < messageList.size(); i++)
	  			{
	  				common.Utility.log.debug(msg.getID());
	  				msg = (Message) messageList.get(i);
					int uname = msg.getSender();
					int tid=msg.getThreadID();
					String sendername = user.getUsername( uname);
					String subject = msg.getSubject();
					String content= msg.getContent();
					Date sentDate=msg.getSent();
					String sentTime="null";
					if (msg.getSentTime()!=null)
						sentTime= msg.getSentTime().toString();
					String sent="";
					if(sentDate != null)
						sent=new SimpleDateFormat("dd-MMM-yyyy").format(msg.getSent());
						
					else
						sent="null";	
					if(subject == null || subject.equals(""))
							subject = "No subject";	
					if (currtid !=tid){
		      			currtid =tid;
		      			%>
		      			
		      			<div  id =<%="msgID-"+msg.getID() %> class ="comitem" style="float: left;width:440px;  padding-bottom:5px; padding-top:5px; border:solid thin #666; margin-top:5px">
		      			<% 
		      		}else{
					
%>
		      			
		      			<div  id =<%="msgID-"+msg.getID() %> class ="comitem" style="float: left;width:440px;  padding-bottom:5px; padding-top:5px; border:solid thin #666; ">
		      			<% 
		      			
		      		}
						%>
						
						
	
	      		<div style="float:left;width:80px">
	      			<img border="0" style="border:none; padding:px; margin:0px; margin-left:6px; margin-right:3px;float:left" height="10px" src="/ourspaces/images/e-mail_icon1.gif">
	      			<div style="width:80px;float:left; font-size: x-small;"><%=sent +" "+ sentTime%></div>
	      		</div>
      	
      			<div style="position: relative; float:left;width:280px">
      					<div  style="width:270px;float:left;margin-left:5px ;font-weight:bold;font-size:x-small "><%=subject%></div>
      					<div  class="msgbody"><%=content%></div>
      				
				</div>
				<div style="width:75px;float:left; font-size:x-small"><%=user.getName(uname)%></div>
      			
      			
      		</div>
      		
      		
      		<% 
      		
      		
      		
		}
	%>

	</div>
	<h3><a href="#">Instant Messages<img border="0" style="border:none; padding:px; margin:0px; margin-left:6px; margin-right:3px;float:right" src="/ourspaces/images/chat.png"></a></h3>
	<div style ="padding:5px;with:490px;height:650px">
	<%
	if (instantMessages.size()>0){
		   Vector participants=new Vector();
		  
	   		String imgpath="";
	   		
	   			InstantMessage   	im =(InstantMessage)  instantMessages.get(0);
	   			String tof=user.getName( im.getToUserId()).trim(); 
	   			String fromf=String.valueOf(  im.getFromUserId());//user.getName( im.getFromUserId()).trim(); 
	            String contentf=	im.getMessage();
	            String sentf=im.getSentDate();
	            if (im.getRecieved()==0)
	            	imgpath="/ourspaces/images/cross.png";
	            else
	            	imgpath="/ourspaces/images/tic.png";
	            
	            participants.add(tof);
	            participants.add(fromf);
	           
	   		
	   			
		   for(int i = 1; i < instantMessages.size(); i++)
				{
	          	im =(InstantMessage)  instantMessages.get(i);
	          	
	           	String fromc=user.getName( im.getFromUserId()).trim(); 
	           	String to=user.getName( im.getToUserId()).trim(); 
	            String content=	im.getMessage();
	            String sent=im.getSentDate();
	           
	            if (im.getRecieved()==0)
	            	imgpath="/ourspaces/images/cross.png";
	            else
	            	imgpath="/ourspaces/images/tic.png";
	            %>
	            
	            <%
	            
	            if (participants.contains(to)&&participants.contains(fromc)){
	            %>
	            <div id =<%="imID-"+im.getIMid() %> class ="comitem" style="float: left; width:440px;margin:2px;margin-top:-5px">
	            <%
	            }else{
            	 %>
 	            <div id =<%="imID-"+im.getIMid() %> class ="comitem" style="float: left; width:440px;margin:2px">
 	            <%
 	           	participants.clear();
            	participants.add(to);
                participants.add(fromc);
	            }
	            if (id==im.getFromUserId()){           
	            %>
	            <img border="0" style="border:none; padding:px; margin:0px;  margin-right:2px;float:left" height="12px" src="/ourspaces/images/chat.png">
	            <div style="float:left;width:80px;font-size:x-small"><%=sent %></div>
				<div style="float:left;width:80px;font-size:x-small"><%=fromc %></div>
				<div><img style="margin:0px;border:none;float:left;padding:0px;width:16px;height:16px;" src=<%="'/ourspaces/images/leftspeacharrow.png'" %>/><div  class="msgbody" style="width:120px"><%=content%><img style="margin:0px;border:none;float:right;padding:0px;width:16px;height:16px;" src=<%="'"+imgpath+"'" %>/></div></div>
				
			<%-- 	<div  class="chatbody" style="width:130px; background-image:url('/ourspaces/images/speechbubble.png');background-repeat:no-repeat;"><%=content%><img style="margin:0px;border:none;float:right;padding:0px;width:16px;height:16px;" src=<%="'"+imgpath+"'" %>/></div> --%>
				<div style="float:left;width:80px;font-size:x-small;margin-left:18px"><%=to %></div>
      			<%
	            }else if(id==im.getToUserId()) {
      			%>
      			<img border="0" style="border:none; padding:px; margin:0px;  margin-right:2px;float:left" height="12px" src="/ourspaces/images/chat.png">
      			<div style="float:left;width:80px;font-size:x-small"><%=sent %></div>
      			 <div style="float:left;width:80px;font-size:x-small"><%=to %></div>
      			  
      			<div><div  class="msgbody" style="width:120px;margin-left:18px"><%=content%><img style="margin:0px;border:none;float:right;padding:0px;width:16px;height:16px;" src=<%="'"+imgpath+"'" %>/></div><img style="margin:0px;border:none;float:left;padding:0px;width:16px;height:16px;" src=<%="'/ourspaces/images/rightspeacharrow.png'" %>/></div>
      			 <div style="float:left;width:80px;font-size:x-small;"><%=fromc %></div>
      			  	         		
      			<%
	            }
	            %>
				</div>
	            <%	
				}   
	              	
	            
	   	}
	            
	   
	
	%>	
		
		
	</div>
	<h3><a href="#">Blogs<img border="0" style="border:none; padding:px; margin:0px; margin-left:6px; margin-right:3px;float:right" src="/ourspaces/icons/001_31.png"></a></h3>
	<div style ="padding:5px;with:490px;height:650px">
			<% 
		//blogs	
   if (blogPosts.size()>0){
	   
	   for(int i = 0; i < blogPosts.size(); i++)
			{
          	BlogBean   	post =(BlogBean)  blogPosts.get(i);
           	String author=user.getName( post.getUserID()); 
            String title=	post.getTitle();
            String postcontent=post.getContent();
            String date =post.getDate();
            String about = "http://openprovenance.org/ontology#" + post.getRdfBlogID().split("#")[1];
            ArrayList comments = blog.getComments(about);
            
            
	%>
	
      		
      		<div id=<%="blogID-"+post.blogID %> class ="comitem" style="float: left; width:440px;margin:4px;">
      		
      		<div style="float:left;width:110px">
      			<img border="0" style="border:none; padding:px; margin:0px;  margin-right:2px;float:left" height="12px" src="/ourspaces/icons/001_31.png">
      			<div style="width:100px;float:left;font-size:x-small"><%=date %></div>
      			<div style="width:100px;float:left;font-size:x-small"><%=author%></div>
      		</div>
      		<div style="position: relative; float:left;width:320px">
      					<div  style="width:320px;float:left;margin-left:5px ;font-weight:bold;font-size:x-small "><%=title%></div>
      					<div  style="width:320px;" class="msgbody"><%=postcontent%></div>
      				
				</div>
				
  		 </div>
      		
      			
      			<div class="comments">
      				<div style="position: relative; float:left; width:100%;">
      				
      					<%
      					for(int j = 0; j < comments.size(); j++)
      					{
      						BlogBean comm =(BlogBean) comments.get(j);
      						String commauthor=user.getName( comm.getUserID()); 
      						//String dateposted=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date.parse( comm.getDate()));
      						%>
      						
      						<div id=<%="blogcommID-"+comm.blogID %> class ="comitem" style="float: left; width:440px;margin:4px;margin-top:-8px">
      						<div class="blogcomments hidden">
			      				<img border="0" style="border:none; padding:px; margin:0px;  margin-right:2px;float:left" height="12px" src="/ourspaces/icons/001_31.png">
			      				<div style="width:90px;float:left;font-size:x-small"><%=date %></div>
			      				<div  style="width:240px;float:left;margin-left:5px;font-weight:bold ;font-size:x-small"><%=title%><span class="nlg" rel="<%=about%>"></span></div>
			      				</div>
      						<div style="float:left;width:110px">
      							<img border="0" style="border:none; padding:px; margin:0px;  margin-right:2px;float:left" height="12px" src="/ourspaces/icons/001_58.png">
      							<div style="float:left;width:100px;font-size:x-small"><%=comm.getDate()%></div>
      							<div style="width:100px;float:left;padding-left:10px;font-size:x-small"><%=commauthor %></div>
      						</div>
      						<div style="position: relative; float:left;width:300px;margin-left:30px">				
      							<div class="msgbody" style="float:left;width:280px;"><%=comm.getContent() %></div>
      						</div>
      						</div>
      						<br/>
      						<% 
      						
      					}
      					
      					%>
      				</div>
      			</div>
      	
      	
      		
      		<% 
            	
            	
            	
            	
   }
            
          
                    
   }
			%>
	</div>
	<h3><a href="#">Comments<img border="0" style="border:none;  margin:0px; margin-left:6px; margin-right:3px;float:right" src="/ourspaces/icons/001_58.png"></a></h3>
 <div style ="padding:5px;with:490px;height:650px">
          
             <%
                    
                    
                    if (resources.size()>0){
                    	 common.Utility.log.debug("FOUND Comments"+resources.size());
                 	   
                       for (int k =0;k<resources.size();k++){
                    	   common.Resources res = (common.Resources) resources.get(k);
                    	   Vector comments = res.getComments();
                    	   common.Utility.log.debug(res.getID());
                    	   String encRes = URLEncoder.encode(res.getID());
                    	   if (comments.size()>0){
                    	  %>
             		<div  id=<%="artifact"+res.getID().split("#")[1] %>  class="comitemstatic" style="float: left;  padding-bottom:5px; padding-top:5px;margin-top:5px;width:440px">
             		<img border="0" style="border:none; padding:px; margin:0px;  margin-right:2px;float:left" height="12px" src="/ourspaces/icons/001_58.png">
               		<div style="width:90px;float:left;font-size:x-small"><%=res.getDate() %></div>
   					<div  style="width:240px;float:left;margin-left:5px;font-weight:bold ;font-size:x-small"><%=res.getTitle()%><span class="nlg" rel="<%=encRes%>"></span></div>
 					<div style="width:90px;float:left;font-size:x-small"></div>
					</div>			
							<% 
							for (int n=0;n<comments.size();n++){
								BlogBean comm =(BlogBean) comments.get(n);
							%>	
								
							
			      		<div  id=<%="ArtefactcommID-"+comm.getID() %>  rel=<%=comm.getAbout().split("#")[1] %> class ="comitem" style="float: left;  padding-bottom:5px; padding-top:5px;width:440px">
			      				<div class="artifactcomments hidden">
			      				<img border="0" style="border:none; padding:px; margin:0px;  margin-right:2px;float:left" height="12px" src="/ourspaces/icons/001_58.png">
			      				<div style="width:90px;float:left;font-size:x-small"><%=res.getDate() %></div>
			      				<div  style="width:240px;float:left;margin-left:5px;font-weight:bold ;font-size:x-small"><%=res.getTitle()%><span class="nlg" rel="<%=encRes%>"></span></div>
			      				</div>
			      				<div style="float:left;width:110px;">
			      				<img border="0" style="border:none; padding:px; margin:0px;  margin-right:2px;float:left" height="12px" src="/ourspaces/icons/001_58.png">
			      					<div style="float:left;width:90px;font-size:x-small"><%=comm.getDate() %></div>
			      					<div style="width:90px;float:right;width:100px;font-size:x-small"> <%=user.getName(comm.getUserID())%></div>
			      					
			      				</div>	
			      		<div class="msgbody" style="float:left;width:280px;"><%=comm.getContent()%></div>
			      		</div>
			      			
			      			<% 	
							}
								
							
							%>
      				
                    	  
                    	  
                    	  
                    	  
                    	  <% 
                       }   
                       
                    
                       }
                   
                    }
                   %>   
                   
                   </div> 
</div>
       		 

	
        	</div>
   </li>
                    
</ul>
<ul id="column1" class="column">
 	<li class="widget color-yellow " id="communication_items">
    	<div class="widget-head"><h3 id="selectedCommstitle">Selected Communication Items</h3></div>
       		<div class="widget-content">
        	 <div id="selectedComms" class="sortable collector" style=" height:705px; margin:2px;padding:2px;overflow:auto"></div>
        	 
        	
        	<div style="float:left;background-color:#fff;padding:5px;width:450px;margin:5px" class="ui-state-highlight ui-corner-all">
        	 <a id ="editbutton" class="fg-button ui-state-active fg-button-icon-right ui-corner-all"  >Save New Version</a>
        	 <a id ="savebutton" class="fg-button ui-state-active fg-button-icon-right ui-corner-all"  >Save as Communication Artefact</a>
        	 <a id ="cancelbutton" class="fg-button ui-state-active fg-button-icon-right ui-corner-all"  >Cancel</a>
        	 
        	 </div> 
        	 </div> 
 </li>
     	
</ul>



</div> <!--  end of columns -->

            
            
</div> <!--  End of home status -->

<%--   <jsp:include page="/bottom.jsp" /> --%>
