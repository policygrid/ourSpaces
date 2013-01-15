<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.Project" />
<jsp:useBean id="access" class="common.AccessControl" />
<jsp:useBean id="ontologyHandler" class="common.OntologyHandler" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<%
Random r = new Random();
int d = r.nextInt();

String temp = request.getParameter("id");
String output = "project.jsp?id="+temp;
String projectID = "http://www.policygrid.org/project.owl#" + temp;

user = (User) session.getAttribute("use");
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));


boolean view =  AccessControl.checkPermission("view",user.getFOAFID(),projectID);
boolean edit =  AccessControl.checkPermission("edit",user.getFOAFID(),projectID);
boolean download =  AccessControl.checkPermission("download",user.getFOAFID(),projectID);
boolean remove =  AccessControl.checkPermission("remove",user.getFOAFID(),projectID);

ArrayList users = access.getProjectAdministrators(projectID);

boolean check = access.checkPublic(projectID);

String title = project.getTitle(projectID);
String subtitle = project.getSubtitle(projectID);
String startdate = project.getStartdate(projectID);
String enddate = project.getEnddate(projectID);
ArrayList memberList = project.getProjectMembers(projectID);
ArrayList aims = project.getProjectAims(projectID);
Vector list = ontologyHandler.getSubclassList("project", "http://www.policygrid.org/project.owl#ProjectRole");
String projectPhoto = project.getProjectPhoto(projectID);


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

$(function(){
 	$("button#addAim").button();
	$("button#addAim").click(function() {
		$("div#aims").append("<p style=\"padding-left:10px; color:#999999; padding:3px; font-size:10px\"><div style=\"width:120px; float:left; position:relative;\">Aim:</div> <input type=\"text\" style=\"width:250px;\" name=\"aim\" /></p>");
		return false;
	});
});

function reveal()
{
	document.getElementById('res').style.display = 'block';
}

function unreveal()
{
	document.getElementById('res').style.display = 'none';
}


function createDiv(type)
{
	var formID=Math.floor(Math.random()*100000)
	var divTag = document.createElement("div");
	divTag.id = formID;
	divTag.style.position = 'relative';
	document.getElementById(type).appendChild(divTag);
	if(type == 'members') {
		divTag.innerHTML = tag('permissions/addUser.jsp?label='+formID, formID);;
	}
	if(type == 'projects') {
		divTag.innerHTML = tag('permissions/addProject.jsp?label='+formID, formID);;
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
	//Delete administrator rights only if at least another user have administration right
//   	if (access.getProjectAdministrators(projectID).size() > 1) {
  function init(){
	
		if (window.XMLHttpRequest) {
		           return new XMLHttpRequest();
		       } else if (window.ActiveXObject) {
		           
		           return new ActiveXObject("Microsoft.XMLHTTP");
		       }
	
		}

	
	if ($('p.projectAdmin').length > 1) {
		var xmlHttpRequest=init();
	
		xmlHttpRequest.open("GET", "DeleteUser?action=admin&user="+user+"&resource="+resource+"&namespace="+namespace, true);
		xmlHttpRequest.onreadystatechange=function() {
			if(xmlHttpRequest.readyState==4){
	    	   if(xmlHttpRequest.status==200){
	      		    var xmlMessage=xmlHttpRequest.responseXML;
	    	    	var personID=xmlMessage.getElementsByTagName("message")[0].firstChild.nodeValue;
	    	    	
	    	    	if(personID != ""){
	    				document.getElementById(place).style.display = 'none';
	    	    	}
	    	    	$('p#' + user).remove();
	    	   }
			}
		};
		
		xmlHttpRequest.send(null);
	} else {
		alert("Only one user has administration access. This user cannot be removed.");
	}
}

function deleteMember(user, project, place)
{
  
	var xmlHttpRequest=init();

	  function init(){

	if (window.XMLHttpRequest) {
	           return new XMLHttpRequest();
	       } else if (window.ActiveXObject) {
	           
	           return new ActiveXObject("Microsoft.XMLHTTP");
	       }

	}
  	
	if ($('div.projectMember').length > 1) {
		xmlHttpRequest.open("GET", "Delete?user="+user+"&project="+project, true);
		xmlHttpRequest.onreadystatechange=function() {
			if(xmlHttpRequest.readyState==4){
		    	   if(xmlHttpRequest.status==200){
	
		    		   var xmlMessage=xmlHttpRequest.responseXML;
	
		    	    	var personID=xmlMessage.getElementsByTagName("message")[0].firstChild.nodeValue;
		    	    	
		    	    	if(personID != ""){
	
		    				document.getElementById(place).style.display = 'none'
							document.getElementById(place).innerHTML = '';
		    	    	}
		    	    	$('div#' + place).remove();	    	    	   
		    	   }
			}
		};
		
		xmlHttpRequest.send(null);
	} else {
		alert("The project only has one member. This user cannot be removed.");
	}

}

</script>


<div style="position:relative; padding:15px; width: 650px;">

	<div style="position:relative; float:left; width:650px; padding-bottom:15px;">
		<p style="color:#FF6600; font-size:18px; padding-bottom:10px; padding-top:15px;">Project Logo</p>
		<img src="<%=common.Utility.absoluteURLtoRelative(projectPhoto) %>" style="position: relative; float: right;"></img>
		<form ENCTYPE="multipart/form-data" method="post" action="Controller?action=upload">
			<input type="hidden" name="userid" id="userid" value="<%=user.getID()%>" />
            <input type="file" name="upload" /><br /><br /> (50 Kbytes max)
            <input type="submit" value="upload photo" />
		</form>
	</div>

	<p style="color:#FF6600; font-size:18px; padding-bottom:10px; padding-top:10px;">Administrator Options</p>
	
	<p style="padding-left:10px; padding-bottom:15px;">You can add other administrators to this project. These users can create sub-projects and add members. 
	They also have full access to all resources.</p>
	
	<form method="post" id="target" action="Controller?action=projectSettings">
	
	<div style="position:relative; float:left; width: 620px; padding-left:30px; margin-bottom:15px;">
     
     	<div style="position:relative; float:left; width:150px;">
            Promote user:<br />
            <!--  <a href="#" style="font-size:9px;" onclick="createDiv('members'); return false;">[Add another user]</a> -->
        </div>
        
        <div id="members" style="position:relative; width:450px; float:left;">
        
            <jsp:include page="project/addProjectMemberField.jsp" > 
            	<jsp:param name="role" value="false" />
            </jsp:include>
            
    
        </div><!-- end of members -->
        
        <p style="padding-bottom:10px; font-size:14px;">Current Administrators:</p>

			<%
			for(int i = 0; i < users.size(); i++)
			{
				Person person = (Person) users.get(i);
				String name = person.getName();
				String foaf = person.getFoafID();
				String[] fields = foaf.split("#");
				
				boolean remove2 =  AccessControl.checkPermission("remove",fields[1],projectID);
				if(remove2 == true) 
				{
					%><p style="padding-left:20px;" id="<%=fields[1] %>"><%=name %> - Cannot be removed*</p><%
				}
				else
				{
					%><p style="padding-left:20px;" class="projectAdmin" id="<%=fields[1] %>"><%=name %> - <a href="#" onclick="deleteUser('<%=fields[1] %>','<%=fields[1] %>','<%=temp %>', 'http://www.policygrid.org/project.owl'); return false;">[Remove Admin Access]</a> <div style="display: none;" id="del"></div></p><%
				}
			}
			%>	
	</div>

	<p style="color:#FF6600; font-size:18px; padding-bottom:10px; padding-top:10px;">Public Settings:</p>
	
	<%if(check == true) { %>
		<p style="padding-left:10px;"><input type="radio" onclick="unreveal()" checked name="type" value="public"></input> <span style="color:blue;">Public</span> (all ourSpaces users can view this project)</p>
		<p style="padding-left:10px;"><input type="radio" onclick="reveal()" name="type" value="private"></input> <span style="color:red;">Private</span> (only project members can view this project)</p>
	<% } else { %>
		<p style="padding-left:10px;"><input type="radio" onclick="unreveal()" name="type" value="public"></input> <span style="color:blue;">Public</span> (all ourSpaces users can view this project)</p>
		<p style="padding-left:10px;"><input type="radio" onclick="reveal()" checked name="type" value="private"></input> <span style="color:red;">Private</span> (only project members can view this project)</p>
	<% } %>
	
	<input type="hidden" name="output" value="<%=output %>"></input>
	<input type="hidden" name="projectID" value="<%=projectID %>"></input>
	
	<div style="position:relative; float:left; width:500px;">
		<p style="color:#FF6600; font-size:18px; padding-bottom:10px; padding-top:15px;">Edit details </p>
	  	<p>&nbsp;</p>
                <p style="padding-left:10px; color:#999999; padding:3px; font-size:10px">
				  <div style="width:120px; float:left; position:relative;">Title:</div> 
				  <input type="text" style="width:250px;" name="title" value="<%=title%>" />
				</p>
                <p style="padding-left:10px; color:#999999; padding:3px; font-size:10px">
				  <div style="width:120px; float:left; position:relative;">Sub-title:</div>
				  <input type="text" style="width:250px;" name="subtitle" value="<%=subtitle%>" />
				</p>
				
				<div id="aims">
				<%
				for(int i = 0; i < aims.size(); i++)
				{
					String aim = (String) aims.get(i);
					%>
					<p style="padding-left:10px; color:#999999; padding:3px; font-size:10px">
				  		<div style="width:120px; float:left; position:relative;">Aim <%=i + 1 %>:</div>
				  		<input type="text" style="width:250px;" name="aim" value="<%=aim%>" />
					</p>
					<%
				}
				%>
				</div>
				<div style="padding-top:10px">
					<button id="addAim">Add Aim</button>
				</div>
	</div>
	
	<div style="position:relative; float:left; width:500px;">
	
		<p style="color:#FF6600; font-size:18px; padding-bottom:10px; padding-top:15px;">Members</p>
	
		<%
		for(int i = 0; i < memberList.size(); i++)
		{
			MemberBean mb = (MemberBean) memberList.get(i);
			String role = mb.getRole();
			String separatedClassName = Utility.splitString(role);
			String name = mb.getName();
			String memberFoafID = mb.getFoafID();
			
			String[] tempUser = memberFoafID.split("#");
			
			int userid = mb.getUserID();
		%>
			<div class="projectMember" style="position: relative; float: left; width:600px;" id="<%=i %>">
				<div style="position: relative; float: left; width:150px;">
					<p><%=name %></p>
				</div>
				<div style="position: relative; float: left; width:200px;">
					<select name="role" style="width:160px; position:relative; float:left;">
		                <%      
							for(int j = 0; j < list.size(); j++)
							{
							     String className = (String) list.get(j); 
							     String separatedClassName2 = common.Utility.splitString(className);
							     if(separatedClassName.equals(separatedClassName2))
							     {
						%>
							 		 <option selected value="<%=memberFoafID+"_"+className%>">&nbsp;&nbsp;&nbsp;<%=separatedClassName2%></option>				    
						<%				    
							     }
							     else
							     {
							    	 %><option value="<%=memberFoafID+"_"+className%>">&nbsp;&nbsp;&nbsp;<%=separatedClassName2%></option><%
							     }
							 }				        					        
						%>
		             </select>
		          </div>
		          <div style="position:relative; float:left; width:150px;">
	         		<p><a href="#" onclick="deleteMember('<%=tempUser[1] %>','<%=temp %>','<%=i %>'); return false;">[Delete Member]</a></p>
	        	  </div>
	         </div>
			
		<%
		}
		%>
		
	</div>
				
	</form>
</div>