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
<script type="text/javascript" src="/ourspaces/javascript/pls.js"></script> 
<div class="navBarLeft" style="z-index; -1;" >
           <div class="dropdownBox">
              <a class="navBarLeft" href="#" style="float: left; margin-right:5px;"><img src="/ourspaces/icons/001_12.png" align="left" border="0"/>My Messages Space</a> <img src="/ourspaces/icons/dropdown.png" align="right" border="0"/>
              <div class="navBarOptions ui-corner-all" style="z-index; -1; float:left; width:220px;">
			      <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
			              <a class="navBarLeft" href="/ourspaces/messages/inbox.jsp" style="float: left; margin-right:5px;"><img src="/ourspaces/icons/001_43.png" align="left" border="0"/>Inbox</a> 
			      </div>
			      <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
			             <a class="navBarLeft" href="/ourspaces/messages/sent.jsp" style="float: left; margin-right:5px;"><img src="/ourspaces/icons/001_43s.png" align="left" border="0"/>Sent</a>
			      </div>    
				  <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
			           	 <a class="navBarLeft" href="/ourspaces/messages/trash.jsp" style="float: left; margin-right:5px;"><img src="/ourspaces/icons/001_49.png" align="left" border="0"/>Trash</a>
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

String firstmsg="";
%>
  

  

<script type="text/javascript">
initPage();
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
 
 
 <div id="dialog" title="Confirm Delete">
	<p>Are you sure you want to delete the message?</p>
</div>

<div id="columns" style="position:relative;">

<ul id="column2" class="column" style="width: 400px;">
<li class="widget color-yellow " id="mail_main">
                        <div class="widget-head">
                            <h3>Inbox</h3>
                        </div>
                        <div class="widget-content">
            <div style="padding: 10px;">
            <div id="msgpanel" style="font-size:11px;width:360px;" >
			<jsp:include page="messagesList.jsp" flush="false">
                						<jsp:param name="limit"  value="8" />
                             			<jsp:param name="offset" value="0" />
                             			<jsp:param name="qrytype" value="inbox" />
                             			<jsp:param name="delaction" value="delete" />
           </jsp:include>
	</div>
	</div>
</div>
</li>
                    
                    
</ul>
<ul id="column1" class="column" style="width:600px">
 					<li class="widget color-yellow " id="mail_message">
                        <div class="widget-head">
                            <h3>Message</h3>
                        </div>
                       
                        <div class="widget-content">
                       <div id="newmessage">
                        <div id="newmesgheader" class="ui-state-highlight ui-corner-all" style="margin:5px;padding:5px;width:555px;">
                          	<div style="width:80px;float:left;font-weight: bold;text-align: right;padding-right:5px">To:</div><div id="newmessagerecipeints"  style="float:left;"></div>
                          	  <br/>
                        	<div style="width:80px;float:left;font-weight: bold;text-align: right;padding-right:5px">Subject:</div><input type="text" id="subjectnew" style="width:400px; border: 1px solid #CCC; margin-bottom:5px;text-align:left;" name="subject">
                        	<br/>
                        </div>
                      
                      
                        <div id="newmesgbody" class="plscontent ui-state-highlight ui-corner-all" style="margin:5px;padding:5px;width:555px;height:200px;min-height:200px;height:auto">
                        	<textarea name="newbodytext" id="newbodytext" style="width:540px; margin:5px;border: 1px solid #CCC; height:180px;"></textarea>
                        	<div id="provenancelinks">	
							</div>
							<div id="discourse">
							</div>
                        	<br/>
                        </div>
                        <div id ="newcontrols" class="ui-state-highlight ui-corner-all"  style="margin:5px;padding:5px;width:555px;height:30px">
                         <a id ="newcancel" class="fg-button ui-state-active fg-button-icon-right ui-corner-all" href="#"   >Cancel</a>
                         <a id ="newsend" class="fg-button ui-state-active fg-button-icon-right ui-corner-all" href="#"  onclick ='listPeople()' >Send</a>
                         <a id ="newsendall" class="fg-button ui-state-active fg-button-icon-right ui-corner-all" href="#"  onclick ='listPeopleAll()' >Send</a>
  
                         </div>
                         </div>
                         
                         
                         
                         
                        <div id="rightmessage">
                        <div id="mesgheader" class="ui-state-highlight ui-corner-all" style="padding:5px;width:555px;min-height:90px;margin:5px 5px 10px 5px">
                        	<div id="messageid"  style="display:none;"></div>
                        	<div id="threadid"  style="display:none;"></div>
                        	<div style="width:80px;float:left;font-weight: bold;text-align: right;padding-right:5px">Sender:</div><div id="messagesender" style="float:left;"></div>
                        	<span id="messagesenderid"  class="senderid" style="display:none;"></span>
                        	<br/>
                        	<div style="float:left;"><div style="width:80px;float:left;font-weight: bold;text-align: right;padding-right:5px">Recipients:</div><div id="messagerecipeints"  style="float:right;width:460px;height:30px;overflow:auto"></div>
                        	</div>
                        	<div id="messagerecipeintsids"  style="display:none;"></div>
                        	<br/>
                        	<div style="width:80px;float:left;font-weight: bold;text-align: right;padding-right:5px">Subject:</div><div id="messagesubject"  style="float:left;width:400px;"></div>
                        	<br/>
                        	
                        	<div style="width:80px;float:left;font-weight: bold;text-align: right;padding-right:5px">Date Sent:</div><div id="messagesent"  style="float:left;"></div>
                        	<br/>
                        </div>
                        <div id="mesgbody" class="ui-state-highlight ui-corner-all" style="margin:5px;padding:5px;width:555px;height:200px;">
                        	<div id="bodytext" class="plscontent ui-corner-all" style="margin:5px;padding:5px;background-color:#fff;width:540px;height:180px;"></div>
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

            <script type="text/javascript" src="/ourspaces/cookie.jquery.js"></script>
            <script type="text/javascript" src="/ourspaces/inettuts.jsp?space=profile-<%=id%>"></script>
            
</div> <!--  End of home status -->
<script>


//call after page loaded
window.onload=nlglistner ; 
</script>
  <jsp:include page="/bottom.jsp" />
