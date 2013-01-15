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

String resource1 = (String) request.getParameter("resource").toString();
String resourceID = "http://openprovenance.org/ontology#" + resource1;

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


function deleteUser(place, user, resource, namespace)
{

	var xmlHttpRequest=init();

	  function init(){

	if (window.XMLHttpRequest) {
	           return new XMLHttpRequest();
	       } else if (window.ActiveXObject) {
	           
	           return new ActiveXObject("Microsoft.XMLHTTP");
	       }

	}
  	
	xmlHttpRequest.open("GET", "DeleteUser?action=access&user="+user+"&resource="+resource+"&namespace="+namespace, true);
	xmlHttpRequest.onreadystatechange=function() {
		if(xmlHttpRequest.readyState==4){
	    	   if(xmlHttpRequest.status==200){

	    		   var xmlMessage=xmlHttpRequest.responseXML;

	    	    	var personID=xmlMessage.getElementsByTagName("message")[0].firstChild.nodeValue;
	    	    	
	    	    	if(personID != ""){

	    				document.getElementById(place).style.display = 'none'


	    	    		
	    	    	}
    	    	   
	    	   }
		}


	};
	
	xmlHttpRequest.send(null);
}


</script>

<p style="color:#999; font-size:24px; margin: 0 auto;" align="center">Resource Permissions</p>

<div style="position:relative; margin:20px; padding:15px; border:1px solid #CCC; width: 600px;">

<%if( (user.getID()==59) || user.getID()==56 || (depositor.equals(user.getRDFID()))) { %>

<p style="font-size:16px; padding-top:10px; padding-bottom:10px;" align="center"><strong>You can specify the access control settings for your resources.</strong></p>

<form id="target" method="post" action="ResourceAccess?action=add">

	<%if(check == true) { %>
		<p><input type="radio" onclick="unreveal()" checked name="type" value="public"></input> <span style="color:blue;">Public</span> (all ourSpaces users can download)</p>
		<p><input type="radio" onclick="reveal()" name="type" value="private"></input> <span style="color:red;">Private</span> (accessible by the resource owner)</p>
	<% } else { %>
		<p><input type="radio" onclick="unreveal()" name="type" value="public"></input> <span style="color:blue;">Public</span> (all ourSpaces users can download)</p>
		<p><input type="radio" onclick="reveal()" checked name="type" value="private"></input> <span style="color:red;">Private</span> (accessible by the resource owner)</p>
	<% } %>
	
	<div id="res265" style="position: relative; float:left;">
	
		<p style="padding-top:10px; padding-bottom:10px; font-size:16px;">Exceptions</p>
		
		<div style="position: relative; float: left; width: 600px; margin:bottom:5px;">
			<p style="padding-bottom:10px; color: #FF6600; font-size:14px;">Groups</p>
			<%
			if(accessGroups.contains(user.rdfID)) { %>
				<p style="font-size:10px; color:#069; padding-left:10px;"><input type="checkbox" checked name="group" value="<%=user.rdfID %>" /> My Contacts</p>
			<% } else { %>
				<p style="font-size:10px; color:#069; padding-left:10px;"><input type="checkbox" name="group" value="<%=user.rdfID %>" /> My Contacts</p>
			<% } %>
			
			
            <div style="position:relative; float:left; width:410px; margin-top:10px; padding-left:10px;">
            	<%
            	for(int i = 0; i < projects.size(); i++)
            	{
            		pb = (ProjectBean) projects.get(i);
            		String title = pb.getTitle();
            		String projectID = pb.getURI();
            	%>
            		<div style="position: relative; float: left; width: 180px; padding-right:10px;">
            		<%if(accessGroups.contains(projectID)) { %>
            			<p style="font-size:10px;"><input type="checkbox" checked name="group" value="<%=projectID %>" /> <%=title %></p>
            		<% } else { %>
            			<p style="font-size:10px;"><input type="checkbox" name="group" value="<%=projectID %>" /> <%=title %></p>
            		<% } %>
            		</div>
            	<%
            	}
            	%>
            </div>
		</div>
		
		<div style="position: relative; float: left; width: 590px; margin:bottom:5px; margin-top:10px;">
			<p style="padding-bottom:10px; color: #FF6600; font-size:14px;">Users</p>
			<%
			for(int i = 0; i < users.size(); i++)
			{
				Person person = (Person) users.get(i);
				String name = person.getName();
				String foaf = person.getFoafID();
				String[] fields = foaf.split("#");
				%><p style="padding-left:10px; font-size:10px;" id="<%=fields[1] %>"><%=name %> - <a href="#" onclick="deleteUser('<%=fields[1] %>','<%=fields[1] %>','<%=resource1 %>', 'http://openprovenance.org/ontology'); return false;">[Remove Access]</a> <div style="display: none;" id="del"></div></p><%
			}
			%>
		</div>
		
		<div style="position:relative; float:left; width: 600px; margin-bottom:5px; margin-top:10px; padding-left:10px;"> <!--  users -->
	     
	     	<div style="position:relative; float:left; width:150px;">
	            Add users with access:<br />
	        </div>
	        
	        <div id="members" style="position:relative; width:450px; float:left;"> <!--  members -->
	        
	        	<jsp:include page="/permissions/addUser.jsp">
	            	<jsp:param name="label" value="<%=usr %>" />
	            </jsp:include>
	            
	    
	        </div><!-- end of members -->
	 	</div> <!--  end of users -->
	 	
	 </div>
 	
 	<input type="hidden" name="resourceID" value="<%=resourceID %>"></input>
 	<br />
 	<br />
<!--  	<input type="submit" style="margin-top:10px; margin-bottom:10px; margin:0 auto;" value="Confirm Permissions"></input> -->
	
</form>



<% } else { %>
<p>You do not have permission to modify the settings of this resource.</p>
<% } %>
</div>
