<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<jsp:include page="top_head.jsp" />
<!-- menu goes here -->
<jsp:include page="top_tail.jsp" />

<%

user = (User) session.getAttribute("use");
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));
String url = "/ourspaces/users/" + user.getID() + ".xml";
String username = user.getUsername(user.getID());
String resID = user.getRes(user.getID());

int userid = user.getID();

%>

<script language=javascript type=text/javascript>
<!-- Script courtesy of http://www.web-source.net - Your Guide to Professional Web Site Design and Development
function stopRKey(evt) {
   var evt = (evt) ? evt : ((event) ? event : null);
   var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
   if ((evt.keyCode == 13) && (node.type=="text")) {return false;}
}

document.onkeypress = stopRKey;
-->
</script>


<script language="javascript">

  var sect = '';
  var numb = '';
  
  function checkName(section, no){
  
    sect = section;
	numb = no;
	
var xmlHttpRequest=init();

  function init(){

if (window.XMLHttpRequest) {
           return new XMLHttpRequest();
       } else if (window.ActiveXObject) {
           
           return new ActiveXObject("Microsoft.XMLHTTP");
       }

}
var t = sect + numb;
var name = document.getElementById(t).value;


xmlHttpRequest.open("GET", "Check?name="+ encodeURIComponent(name), true);
if(name != '') {
	xmlHttpRequest.onreadystatechange=processRequest;
	xmlHttpRequest.send(null);
}


function processRequest(){
	if(xmlHttpRequest.readyState==4){
	   if(xmlHttpRequest.status==200){
	   
		  processResponse();
	
	   }
	}
}

	function processResponse(){
	  
		if(sect == 'pi')
			sect = 'hidpi';
		if(sect == 'co')
			sect = 'hidcol';
		if(sect == 'res')
			sect = 'hidres';
		if(sect == 'collab')
			sect = 'hidcollab';
		if(sect == 'su')
			sect = 'hidsub';
			
		sect = sect + numb;
	
		var xmlMessage=xmlHttpRequest.responseXML;
		var message = '';
		var message2 = '';
		var p = '';
				
		var root = xmlMessage.getElementsByTagName("user");
		for (var i = 0; i < root.length; i++){
			var id = xmlMessage.getElementsByTagName("id")[i].firstChild.nodeValue;
			var info = xmlMessage.getElementsByTagName("info")[i].firstChild.nodeValue;
			var tokens = info.split('LINEBREAK');
			var userID = id.split('\n');
			p = userID[1];
			var userInfo = '';
			for(var k = 0; k < tokens.length; k++) {
				userInfo = userInfo + ' <br /> ' + tokens[k];
			}
			url = "<a href=\"#two\" onclick=\"getSection(\'"+p+"\')\">here</a>";
			message = message + userInfo + '<div align="right" style="font-size:10px;">Click ' + url + ' if this is the correct person.</div><br />';
		}
		if(!message == '') {
			document.getElementById('two').innerHTML = '<div style="border:1px solid red; padding: 5px;"><p><div align="center" style="color:#660000;">Are you looking for the following:</div></p><br />' + message + '<br /><p align="center" style="font-style:underline;">Click <a href="#two" onclick="turnOn()">here</a> if this person is <strong><em>NOT</em></strong> listed above.</div>';
			
			document.getElementById('pi1').disabled = true;
			document.getElementById('co1').disabled = true;
			document.getElementById('co2').disabled = true;
			document.getElementById('co3').disabled = true;
			document.getElementById('co4').disabled = true;
			document.getElementById('co5').disabled = true;
			document.getElementById('res1').disabled = true;
			document.getElementById('res2').disabled = true;
			document.getElementById('res3').disabled = true;
			document.getElementById('res4').disabled = true;
			document.getElementById('res5').disabled = true;
			document.getElementById('su1').disabled = true;
			document.getElementById('su2').disabled = true;
			document.getElementById('su3').disabled = true;
			document.getElementById('su4').disabled = true;
			document.getElementById('su5').disabled = true;
			document.getElementById('collab1').disabled = true;
			document.getElementById('collab2').disabled = true;
			document.getElementById('collab3').disabled = true;
			document.getElementById('collab4').disabled = true;
			document.getElementById('collab5').disabled = true;
			
		}
		else
			document.getElementById('two').innerHTML = '<div style="border:1px solid red; padding: 5px;"><p><div align="center" style="color:#660000;">This person does not currently exist within ourSpaces but information will be added about them accordingly.</div>';
		
	}
}

function turnOn() {
	document.getElementById('pi1').disabled = false;
	document.getElementById('co1').disabled = false;
	document.getElementById('co2').disabled = false;
	document.getElementById('co3').disabled = false;
	document.getElementById('co4').disabled = false;
	document.getElementById('co5').disabled = false;
	document.getElementById('res1').disabled = false;
	document.getElementById('res2').disabled = false;
	document.getElementById('res3').disabled = false;
	document.getElementById('res4').disabled = false;
	document.getElementById('res5').disabled = false;
	document.getElementById('su1').disabled = false;
	document.getElementById('su2').disabled = false;
	document.getElementById('su3').disabled = false;
	document.getElementById('su4').disabled = false;
	document.getElementById('su5').disabled = false;
	document.getElementById('collab1').disabled = false;
	document.getElementById('collab2').disabled = false;
	document.getElementById('collab3').disabled = false;
	document.getElementById('collab4').disabled = false;
	document.getElementById('collab5').disabled = false;
	document.getElementById('two').innerHTML = '';
}

	function getSection(id) {
		document.getElementById(sect).value = id;
		document.getElementById('two').innerHTML = '';
		document.getElementById('pi1').disabled = false;
		document.getElementById('co1').disabled = false;
		document.getElementById('co2').disabled = false;
		document.getElementById('co3').disabled = false;
		document.getElementById('co4').disabled = false;
		document.getElementById('co5').disabled = false;
		document.getElementById('res1').disabled = false;
		document.getElementById('res2').disabled = false;
		document.getElementById('res3').disabled = false;
		document.getElementById('res4').disabled = false;
		document.getElementById('res5').disabled = false;
		document.getElementById('su1').disabled = false;
		document.getElementById('su2').disabled = false;
		document.getElementById('su3').disabled = false;
		document.getElementById('su4').disabled = false;
		document.getElementById('su5').disabled = false;
		document.getElementById('collab1').disabled = false;
		document.getElementById('collab2').disabled = false;
		document.getElementById('collab3').disabled = false;
		document.getElementById('collab4').disabled = false;
		document.getElementById('collab5').disabled = false;
		sect = '';
	    numb = '';
	}

</script>
  
  <style type="text/css">
<!--
.style1 {color: #660000}
.style2 {
	color: #666666;
	font-style: italic;
}
.style3 {color: #FF0000}
-->
  </style>

    <span style="color:#3399CC; font-size:24px; padding-bottom:10px;">Create a project with ourSpaces</span>
       <p style="padding:10px;">Creating a project within ourSpaces will allow you and your collaborators to effectively communicate and share all resources with the project community.  You can specifiy members of your project here and it will appear accordingly in their ourSpaces profile. Non-registered members can also be added after.</p>
       <p style="color:#CC0000; font-size:20px; padding-bottom:10px;">Creation Form</p>
       
       <div class="regContainer">
       		<p align="right" class="style1" style="padding:10px;"><strong>Project Information</strong>:</p>
            
            <form method="post" action="Controller?action=createProject">
              <div class="regLeftCont" style="width:965px;">
            	<div class="regLeftNames"><p>Project <span class="style1">Title</span>:</p>
            	</div>
            	<input type="text" name="title" class="input" style="width:300px;" />
              </div>
              <div class="regLeftCont" style="width:965px;">
                <div class="regLeftNames"><p>Project <span class="style1">Subtitle</span>:</p>
                </div>
            	<input type="text" name="subtitle" class="input" style="width:550px;" />
              </div>
              <div class="regLeftCont" style="width:965px;">
                <div class="regLeftNames"><p>Project <span class="style1">Aims</span>:</p>
                </div>
            	<input type="text" name="aims1" class="input" style="width:550px; margin-bottom:5px;" /><br />
                <input type="text" name="aims2" class="input" style="width:550px; margin-left:240px; margin-bottom:5px;" /><br />
                <input type="text" name="aims3" class="input" style="width:550px; margin-left:240px; margin-bottom:5px;" /><br />
                <input type="text" name="aims4" class="input" style="width:550px; margin-left:240px; margin-bottom:5px;" /><br />
                <input type="text" name="aims5" class="input" style="width:550px; margin-left:240px;" />
              </div>
              <div class="regLeftCont" style="width:965px;">
                <div class="regLeftNames"><p><span class="style1">Organisations</span> Involved:</p>
                </div>
            	<input type="text" name="org1" class="input" />
                <input type="text" name="org2" class="input" />
                <input type="text" name="org3" class="input" /><br />
                <input type="text" name="org4" class="input" style="margin-left:240px; margin-top:5px;" />
                <input type="text" name="org5" class="input" />
                <input type="text" name="org6" class="input" />
              </div>
              <div class="regLeftCont" style="width:965px;">
                <div class="regLeftNames"><p><span class="style1">Start</span> Date:</p>
                </div>
            	<input type="text" name="startdate" class="input" />
              </div>
              <div class="regLeftCont" style="width:965px;">
                <div class="regLeftNames"><p><span class="style1">End</span> Date:</p>
                </div>
            	<input type="text" name="enddate" class="input" />
              </div>
              <div class="regLeftCont" style="width:965px;">
                <div class="regLeftNames"><p><span class="style1">Grant</span> Number:</p>
                </div>
            	<input type="text" name="grantnumber" class="input" />
              </div>

              <p align="right" class="style1" style="padding:20px;"><strong>Member Information</strong>:
              <br />
              <span class="style2">Please specifiy project members.</span></p>

              <div class="regLeft">
                  <div class="regLeftCont">
                    <div class="regLeftNames">
                      <p><span class="style1">Principal</span> Investigator:</p>
                    </div>
                    <input type="text" name="pi1" class="input" id="pi1" onblur="checkName('pi', '1')" />
                   <br />
                    <input type="hidden" name="hidpi1" id="hidpi1" class="input" />
                  </div>
                  <div class="regLeftCont">
                    <div class="regLeftNames">
                      <p><span class="style1">Co</span> Investigators:</p>
                    </div>
                    <input type="text" name="co1" id="co1" class="input" style="margin-bottom:5px;" onblur="checkName('co', '1')" />
                    <br />
                    <input type="text" name="co2" id="co2" class="input" style="margin-bottom:5px; margin-left:240px;" onblur="checkName('co', '2')" />
                     <br />
                    <input type="text" name="co3" id="co3" class="input" style="margin-bottom:5px; margin-left:240px;" onblur="checkName('co', '3')" />
                     <br />
                    <input type="text" name="co4" id="co4" class="input" style="margin-bottom:5px; margin-left:240px;" onblur="checkName('co', '4')" />
                     <br />
                    <input type="text" name="co5" id="co5" class="input" style="margin-bottom:5px; margin-left:240px;" onblur="checkName('co', '5')" />
                    <br />
                	<input type="hidden" name="hidcol1" id="hidcol1" class="input" />
                    <input type="hidden" name="hidcol2" id="hidcol2" class="input" />
                    <input type="hidden" name="hidcol3" id="hidcol3" class="input" />
                    <input type="hidden" name="hidcol4" id="hidcol4" class="input" />
                    <input type="hidden" name="hidcol5" id="hidcol5" class="input" />
                    
                    
                  </div>
                  <div class="regLeftCont">
                    <div class="regLeftNames">
                      <p><span class="style1">Researchers</span>:</p>
                    </div>
                    <input type="text" name="res1" id="res1" class="input" style="margin-bottom:5px;" onblur="checkName('res', '1')" />
                     <br />
                    <input type="text" name="res2" id="res2" class="input" style="margin-bottom:5px; margin-left:240px;" onblur="checkName('res', '2')" />
                    <br />
                    <input type="text" name="res3" id="res3" class="input" style="margin-bottom:5px; margin-left:240px;" onblur="checkName('res', '3')" />
                    <br />
                    <input type="text" name="res4" id="res4" class="input" style="margin-bottom:5px; margin-left:240px;" onblur="checkName('res', '4')" />
                    <br />
                    <input type="text" name="res5" id="res5" class="input" style="margin-bottom:5px; margin-left:240px;" onblur="checkName('res', '5')" />
                    <br />
                    <input type="hidden" name="hidres1" id="hidres1" class="input" />
                    <input type="hidden" name="hidres2" id="hidres2" class="input" />
                    <input type="hidden" name="hidres3" id="hidres3" class="input" />
                    <input type="hidden" name="hidres4" id="hidres4" class="input" />
                    <input type="hidden" name="hidres5" id="hidres5" class="input" />

                  </div>
                  <div class="regLeftCont">
                    <div class="regLeftNames">
                      <p><span class="style1">Sub-Contractors</span>:</p>
                    </div>
                    <input type="text" name="su1" id="su1" class="input" style="margin-bottom:5px;" onblur="checkName('su', '1')" />
                   <br />
                    <input type="text" name="su2" id="su2" class="input" style="margin-bottom:5px; margin-left:240px;" onblur="checkName('su', '2')" />
                    <br />
                    <input type="text" name="su3" id="su3" class="input" style="margin-bottom:5px; margin-left:240px;" onblur="checkName('su', '3')" />
                    <br />
                    <input type="text" name="su4" id="su4" class="input" style="margin-bottom:5px; margin-left:240px;" onblur="checkName('su', '4')" />
                    <br />
                    <input type="text" name="su5" id="su5" class="input" style="margin-bottom:5px; margin-left:240px;" onblur="checkName('su', '5')" />
                    <br />
                    <input type="hidden" name="hidsub1" id="hidsub1" class="input" />
                    <input type="hidden" name="hidsub2" id="hidsub2" class="input" />
                    <input type="hidden" name="hidsub3" id="hidsub3" class="input" />
                    <input type="hidden" name="hidsub4" id="hidsub4" class="input" />
                    <input type="hidden" name="hidsub5" id="hidsub5" class="input" />
                  </div>
                  <div class="regLeftCont">
                    <div class="regLeftNames">
                      <p><span class="style1">Collaborators</span>:</p>
                    </div>
                    <input type="text" name="collab1" id="collab1" class="input" style="margin-bottom:5px;" onblur="checkName('collab', '1')" />
                    <br />
                    <input type="text" name="collab2" id="collab2" class="input" style="margin-bottom:5px; margin-left:240px;" onblur="checkName('collab', '2')" />
                   <br />
                    <input type="text" name="collab3" id="collab3" class="input" style="margin-bottom:5px; margin-left:240px;" onblur="checkName('collab', '3')" />
                    <br />
                    <input type="text" name="collab4" id="collab4" class="input" style="margin-bottom:5px; margin-left:240px;" onblur="checkName('collab', '4')" />
                    <br />
                    <input type="text" name="collab5" id="collab5" class="input" style="margin-bottom:5px; margin-left:240px;" onblur="checkName('collab', '5')" />
                    <br />
                    <input type="hidden" name="hidcollab1" id="hidcollab1" class="input" />
                    <input type="hidden" name="hidcollab2" id="hidcollab2" class="input" />
                    <input type="hidden" name="hidcollab3" id="hidcollab3" class="input" />
                    <input type="hidden" name="hidcollab4" id="hidcollab4" class="input" />
                    <input type="hidden" name="hidcollab5" id="hidcollab5" class="input" />
                  </div>
                  <div class="regLeftCont">
                  	<input type="hidden" value="<%=resID%>" name="admin" />
                    <input type="submit" value="Create Project" id="submitForm" />
                    <input type="reset" value="Clear" />
                  </div>
              </div>
            </form>
            
            <div class="regRight">

              <div id="two" style="width:421px; float:left; position:relative; padding-top:10px;"></div>
        
       		</div>
       </div>
       
 <jsp:include page="bottom.jsp" />
