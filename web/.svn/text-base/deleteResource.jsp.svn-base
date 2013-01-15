<%@ page language="java" import="java.util.Iterator, java.util.*, java.util.Vector, org.policygrid.ontologies.*, java.text.SimpleDateFormat, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="access" class="common.AccessControl" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="project" class="common.Project" />
<jsp:useBean id="pb" class="common.ProjectBean" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<%if (session.isNew()==true){ %>
<jsp:forward page="error.jsp" />
<% } %>

<% 
if( session.getAttribute("use") == null){ %>
<jsp:forward page="error.jsp" />
<% }

Random r = new Random();
int usr = r.nextInt();
int pro = r.nextInt();

User user = (User) session.getAttribute("use");

String resource1 = request.getParameter("resource");
String resourceID = "http://openprovenance.org/ontology#" + resource1;
String pageSource =  request.getParameter("page");
String id = request.getParameter("id");
String title = request.getParameter("title");

String depositor = rdf.getResourceDepositor(resourceID);

boolean check = access.checkPublic(resourceID);

ArrayList users = access.getAccessUsers(resourceID);

if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));


ArrayList projects = project.getAllProjectsAboutUser(user.getFOAFID());
ArrayList accessGroups = access.getAccessGroups(resourceID);

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

function reveal()
{
	document.getElementById('res265').style.display = 'block';
}

function unreveal()
{
	document.getElementById('res265').style.display = 'none';
}


function createDiv(type)
{
	var formID=Math.floor(Math.random()*100000)
	var divTag = document.createElement("div");
	divTag.id = formID;
	divTag.style.position = 'relative';
	document.getElementById(type).appendChild(divTag);
	if(type == 'members') {
		divTag.innerHTML = tag('/ourspaces/permissions/addUser.jsp?label='+formID, formID);;
	}
	if(type == 'projects') {
		divTag.innerHTML = tag('/ourspaces/permissions/addProject.jsp?label='+formID, formID);;
	}
	
}

function searchResource(place,keywords,namespace,resourceName, random)
{	
	var fin = place + 'sea';
	document.getElementById(fin).style.display = 'block';
	document.getElementById(place + 'sc').style.display = 'block';
	document.getElementById(place + 'top').style.display = 'block';
	tag('forms/jsp/queryProjectMember.jsp?sid='+place+'&namespace='+namespace+'&resourceName='+resourceName+'&keywords=' + keywords+'&random=' + random,fin);
}

function updateInput(place,value,text, random)
{
	document.getElementById(place+'r').value = random + '_' + value;
	document.getElementById(place+'gah').style.display = 'block';
	document.getElementById(place+'l').style.display = 'block';
	
	document.getElementById(place+'in').style.color = 'green';
	document.getElementById(place+'in').value = text;
	hideResourceSearch(place);
}

/*
{
	document.getElementById(place+'r').value = value;
	document.getElementById(place+'gah').style.display = 'block';
	document.getElementById(place+'l').style.display = 'block';
	document.getElementById(place+'l').innerHTML = text;
	setDefaultValue(place+'in');
	hideResourceSearch(place);
}
*/

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

	    	    	var personID=xmlMessage.getElementsByTagName("message")[0].firstChild.nodeValue;

	    	    	if(personID == "true"){

	    	

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

<p style="color:#999; font-size:24px; margin: 0 auto;" align="center">Delete Resource</p>

<div style="position:relative; margin:20px; padding:15px; border:1px solid #CCC; width: 600px;">
<%if(depositor.equals(user.getRDFID())) { %>

<p style="font-size:16px; padding-top:10px; padding-bottom:10px;" align="center"><strong>Do you want to delete this resources?</strong></p>
<p style="font-size:16px; padding-top:10px; padding-bottom:10px;" align="center">Title: <%=java.net.URLDecoder.decode(title) %></p>


<form id="target" method="post" action="ResourceAccess?action=delete">

   <%
	Vector resList = rdf.getResourcesPontingTo(resourceID);
    if (resList.size() > 0) {
	%>
	<p>This resource is associated with the following resources:</p>
	<%
	for (int i = 0; i < resList.size(); i++) {
	String res = (String)resList.get(i);
	%>
	<p><%=res %></p>
	<% 
	} 
    }
    %>
    
 	<input type="hidden" name="resourceID" value="<%=resourceID%>"></input>
 	<input type="hidden" name="sourcePage" value="<%=pageSource%>"></input>
 	<% if (id != null) { %>
 	<input type="hidden" name="id" value="<%=id%>"></input>
 	<%} %>
 	<br />
 	<br />
<%--  	<center> --%>
<!--  	<input type="submit" style="margin-top:10px; margin-bottom:10px; margin:0 auto;" value="Confirm Deletion"></input> -->
<%--  	</center> --%>
	
</form>
<%} else {%>
<p style="font-size:16px; padding-top:10px; padding-bottom:10px;" align="center">You do not have permission to delete this resource.</p>
 <%} %>
</div>
