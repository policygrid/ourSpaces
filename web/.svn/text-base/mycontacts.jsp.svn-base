<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="user" class="common.User" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<jsp:include page="top_head.jsp" />
<!-- menu goes here -->
      <div class="navBarLeft">
           <div class="dropdownBox">
              <a class="navBarLeft" href="#" style="float: left;"><img src="/ourspaces/icons/001_57.png" align="left" border="0"/>My Contacts Space </a> <img src="/ourspaces/icons/dropdown.png" align="right" border="0"/>
              <div class="navBarOptions ui-corner-all" style="font-size: 16px; width:220px;">
               <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
		         <span style="font-weight: 900;"> Options:</span>
		         </div>
		         <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px; border-bottom: 1px solid #FF6600"></div> 
		         
		           
<!-- 		           <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;"> -->
<!-- 		             <a href="#" onclick="upload();"><img src="/ourspaces/icons/001_58.png" align="left" border="0"/>Upload New Resource</a> -->
<!-- 		           </div> -->
		           <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
		             <a href="addContact.jsp" class="popup_dialog" title="Add New Contact"><img src="/ourspaces/icons/001_01.png" align="left" border="0"/>Add New Contact</a>
		           </div>
		           
<!-- 		           <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;"> -->
<!-- 		             <a href="createprojectform.jsp" rel="facebox"><img src="/ourspaces/icons/001_34.png" align="left" border="0"/>Create New Project</a> -->
<!-- 		           </div> -->
		      </div>
           </div>       
       </div>

<jsp:include page="top_tail.jsp" />
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

%>



  <script language="javascript" type="text/javascript">
function stopRKey(evt) {
   var evt = (evt) ? evt : ((event) ? event : null);
   var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
   if ((evt.keyCode == 13) && (node.type=="text")) {return false;}
}

document.onkeypress = stopRKey;
</script>

<script language="javascript" type="text/javascript">


function lock()
{
	document.getElementById('submit').disabled = true;
}

function unlock()
{
	document.getElementById('submit').disabled = false;
}

function createDiv(type)
{
	var formID=Math.floor(Math.random()*100000)
	var divTag = document.createElement("div");
	divTag.id = formID;
	divTag.style.position = 'relative';
	document.getElementById(type).appendChild(divTag);
	if(type == 'members') {
		divTag.innerHTML = tag('contacts/contactMember.jsp?label='+formID, formID);;
	}
	else {
		divTag.innerHTML = "<input type=\"text\" name=\"aim\" style=\"width:300px; height:17px; padding-top:3px; margin-top:5px; margin-right:5px; border: 1px solid #CCC;\" />"
	}
	
}

function searchResource(place,keywords,namespace,resourceName, random)
{	
	var fin = place + 'sea';
	document.getElementById(fin).style.display = 'block';
	document.getElementById(place + 'sc').style.display = 'block';
	document.getElementById(place + 'top').style.display = 'block';
	tag('contacts/queryContact.jsp?sid='+place+'&namespace='+namespace+'&resourceName='+resourceName+'&keywords=' + keywords+'&random=' + random,fin);
}

function updateInput(place,value,text, random)
{
	document.getElementById(place+'r').value = random + '_' + value;
	document.getElementById(place+'gah').style.display = 'block';
	document.getElementById(place+'l').style.display = 'block';
	
	document.getElementById(place+'in').style.color = 'green';
	document.getElementById(place+'in').value = text;
	unlock();
	hideResourceSearch(place);
}

function clearValue(place)
{
    document.getElementById(place).value = '';
}

function setDefaultValue(place)
{
    document.getElementById(place).value = 'Search...';
}


function checkSearch(event, place){

    
	document.getElementById(place+'l').innerHTML = e;
	
	characterCode = e.keyCode;
	if(characterCode == 13){
		searchResource(place,document.getElementById(place+'in').value);
	}
	
    return false;
}

function checkForm(e, sid,namespace,resourceName, random) {
	if (e.keyCode == 13) {
		searchResource(sid,document.getElementById(sid+'in').value,namespace,resourceName, random);
	}
	return true;
}

function showForm(sid, namespace, resourceName, random)
{
		tag('forms/add/projectMember.jsp?sid='+sid+'&random='+random, sid+'sea');
}

function hideResourceSearch(resource)
{
	fin = resource + 'sea';
	document.getElementById(fin).style.display = 'none';
	document.getElementById(resource + 'sc').style.display = 'none';
	document.getElementById(fin).innerHTML = '';
	document.getElementById(resource + 'top').style.display = 'none';
	setDefaultValue(resource);
}

function addPerson(place, random)
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
	
</script>



<div class="homeStatusContainer" style="color:#FFFFFF; position:relative;" >
<div id="columns" style="position: relative;">
	
<ul id="column1" class="column" style="width: 1000px;">  
<!-- old search for contacts (now use autocomplete): -->
<!-- 	<li class="widget color-orange" id="p1"> -->
<!--   		<div class="widget-head"> -->
<!-- 			<h3 class="style3">Add Contact</h3> -->
<!-- 		</div> -->
		
<!--     <div class="widget-content" style="padding:5px 5px" > -->
<!--    		<form method="post" action="Controller?action=addContact">     -->
<!-- 	        <a href="#" style="font-size:9px;" onclick="createDiv('members'); return false;">[Add another contact]</a> -->
	        
<%-- 	        <jsp:include page="contacts/contactMember.jsp"> --%>
<%-- 	           	<jsp:param name="label" value="<%=d %>" /> --%>
<%-- 	        </jsp:include> --%>
	            
<%-- 	    	<input type="hidden" name="userid" value="<%=user.getID() %>"></input> --%>
<!-- 	     	<input type="submit" id="submit" class="fg-button ui-state-active fg-button-icon-right ui-corner-all" value="Add Contacts" disabled style="float:left; "></input> -->
<!--     	</form> -->
<!--     </div> -->
<!--     </li> -->
    
	<li class="widget color-orange" id="p2">
  		<div class="widget-head">
			<h3 class="style3">Contacts</h3>
		</div>

      <div class="widget-content">
   				<div class="widget-top">
					<div class="wlink" align="right" style="float: right; margin-left: 5px; margin-right: 10px; margin-bottom: 0px; padding: 10px;">
<!-- 						<a class="popup_dialog" title="Change Password" href="boxes/profile/updatePassword.jsp"> -->
						<a href="addContact.jsp" class="popup_dialog" title="Add New Contact">
							<img align="middle" style="float: left; display: inline-block; margin: -2px; border: 0px none;" src="/ourspaces/icons/001_01.png"> 
							<span style="margin-left: 8px;">Add New Contact</span> 
						</a>
					</div>
				</div>
      
        	<table border="0" cellspacing="5" cellpadding="5" style="width:900px;">
					<%
	  					java.util.Iterator iter = contacts.iterator();
						while(iter.hasNext()){
							contact = (common.User) iter.next();
					  		String uname = user.getUsername(contact.getContactID());
							String photo = user.getPhoto(contact.getContactID());
							String onlineStatus = user.getUserOnlineStatus(contact.getContactID());
					%>
					<tr>
						<td width="65">
							<% if(!photo.equals("")) { %>
									<img src="<%=common.Utility.absoluteURLtoRelative(photo)%>" style="border:1px solid #666666;" width="48" height="50" />
							<% }
							   else { %>
							  		<img src="/ourspaces/images/no.jpg" alt="no picture" width="50" height="50" />
							<% } %>
						</td>
						<td width="350" style="">
							<a href="profile.jsp?id=<%=contact.getContactID() %>"><%=user.getName(contact.getContactID()) %></a> <br />
							<span style="font-size:10px;"><%=user.getStatus(contact.getContactID())%></span><br />
							
							<form id="delete" name="delete" method="post" action="Controller?action=deleteContact">
                                <input type="hidden" value="<%=uname%>" name="contactid" />
                                <input type="hidden" value="<%=id%>" name="userid" />
                                <input type="image" src="images/delete.gif" />
                                <a href="messages/messages.jsp?to=<%=uname%>&sourcePage=mycontacts.jsp" target="page" onClick="window.open('','page','toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,width=600,height=300,left=50,top=50,titlebar=yes')"><img src="images/e-mail_icon1.gif" border="0" /></a>
                                <%
                                if(onlineStatus.equals("active")) {
                                %>
                                	<a href="javascript:void(0)" onclick="javascript:chatWith('<%=contact.getContactID() %>')"><img src="images/chat.png" style="border:none;" /></a>
                                <% } %>
                                <%
                                if(onlineStatus.equals("idle")) {
                                %>
                                	<a href="javascript:void(0)" onclick="javascript:chatWith('<%=contact.getContactID() %>')"><img src="images/chat_idle.png" style="border:none;" /></a>
                                <% } %>
							</form>
						</td>
						<%
						if (iter.hasNext()) {
						contact = (common.User) iter.next();
				  		uname = user.getUsername(contact.getContactID());
						photo = user.getPhoto(contact.getContactID());
						onlineStatus = user.getUserOnlineStatus(contact.getContactID());
						%>
						<td width="65">
							<% if(!photo.equals("")) { %>
									<img src="<%=common.Utility.absoluteURLtoRelative(photo)%>" style="border:1px solid #666666;" width="48" height="50" />
							<% }
							   else { %>
							  		<img src="/ourspaces/images/no.jpg" alt="no picture" width="50" height="50" />
							<% } %>
						</td>
						<td width="350" style="">
							<a href="profile.jsp?id=<%=contact.getContactID() %>"><%=user.getName(contact.getContactID()) %></a> <br />
							<span style="font-size:10px;"><%=user.getStatus(contact.getContactID())%></span><br />
							
							<form id="delete" name="delete" method="post" action="Controller?action=deleteContact">
                                <input type="hidden" value="<%=uname%>" name="contactid" />
                                <input type="hidden" value="<%=id%>" name="userid" />
                                <input type="image" src="images/delete.gif" />
                                <a href="messages/messages.jsp?to=<%=uname%>&sourcePage=mycontacts.jsp" target="page" onClick="window.open('','page','toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,width=600,height=300,left=50,top=50,titlebar=yes')"><img src="images/e-mail_icon1.gif" border="0" /></a>
                                <%
                                if(onlineStatus.equals("active")) {
                                %>
                                	<a href="javascript:void(0)" onclick="javascript:chatWith('<%=contact.getContactID() %>')"><img src="images/chat.png" style="border:none;" /></a>
                                <% } %>
                                <%
                                if(onlineStatus.equals("idle")) {
                                %>
                                	<a href="javascript:void(0)" onclick="javascript:chatWith('<%=contact.getContactID() %>')"><img src="images/chat_idle.png" style="border:none;" /></a>
                                <% } %>
							</form>
						</td>
						
						<%} %>
					</tr>

					<%
						}
					%>

				</table>
				</div>
				
				</li>
				</ul>
        </div>
</div>
<script type="text/javascript" src="cookie.jquery.js"></script>
 <jsp:include page="bottom.jsp" />