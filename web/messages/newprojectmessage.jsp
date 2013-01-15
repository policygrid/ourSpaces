<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.Project" />
<jsp:useBean id="user" class="common.User" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<%

user = (User) session.getAttribute("use");
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));

String temp = request.getParameter("id");
String projectID = "http://www.policygrid.org/project.owl#" + temp;


ArrayList list = new ArrayList();

int userid = user.getID();
int id = user.getID();



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

function sendMessage()
{

  
	var xmlHttpRequest=init();

	  function init(){

	if (window.XMLHttpRequest) {
	           return new XMLHttpRequest();
	       } else if (window.ActiveXObject) {
	           
	           return new ActiveXObject("Microsoft.XMLHTTP");
	       }

	}

	var title = document.getElementById('title').value;
	var content = document.getElementById('content').value; 
	var projectID = document.getElementById('projectID').value;	
	xmlHttpRequest.open("GET", "/ourspaces/messagesHandler?action=project&id="+encodeURIComponent(projectID)+"&title="+encodeURIComponent(title)+"&content="+encodeURIComponent(content), true);
	xmlHttpRequest.onreadystatechange=function() {
		if(xmlHttpRequest.readyState==4){
	    	   if(xmlHttpRequest.status==200){

	    		   var xmlMessage=xmlHttpRequest.responseXML;


						document.getElementById('confirm').style.display = 'block';
						document.getElementById('recipients').style.display = 'none';
    	    	   
	    	   }
		}


	};
	
	xmlHttpRequest.send(null);
}
	
</script>




<div  class="ui-state-highlight ui-corner-all" style="position:relative; margin-left:-12px; padding:10px;width: 675px; font-size:12px;">
	<div id="recipients">
<div style="position:relative; float:left; width: 670px; margin-bottom:15px;">
	     
	     	<div style="position:relative; float:left; width:500px;">Recipients : Members of project<em><%=project.getTitle(projectID) %></em></div>    
	       <input type="hidden" value="<%=projectID %>" id="projectID"></input>
	       
	 	</div>


		<div style="position:relative; float:left; width:80px;">Subject : </div>    
		
		
			<input type="text" id="title" style="width:590px;border: 1px solid #CCC;" name="title">
		
			
		<p style ="margin-top:10px">
			<textarea name="content" id="content" style="width:670px; border: 1px solid #CCC; height:100px;"></textarea>
		</p>

    <div   style="padding-top:15px;width:670px;height:30px;float:left"><a class="fg-button ui-state-active fg-button-icon-right ui-corner-all" href="#" style="color: #FFF;" onclick="sendMessage(); return false;">Send Message</a></div>
    
    </div>

		
		<div id="confirm" style="position: relative; float:left; width:550px; display:none; margin-bottom:10px;">
			<p align="center" style="color: #FF6600; font-size:16px;">Your message has been successfully sent.</p>
		</div>
		
</div>


