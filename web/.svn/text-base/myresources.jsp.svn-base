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
<div class="navBarLeft" style="z-index; -1;" >
           <div class="dropdownBox">
              <a class="navBarLeft" href="#" style="float: left; margin-right:5px;"><img src="/ourspaces/icons/001_58.png" align="left" border="0"/>My Resources Space</a> <img src="/ourspaces/icons/dropdown.png" align="right" border="0"/>
              <div class="navBarOptions ui-corner-all" style="z-index; -1; float:left; width:220px;">
			      <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
			              <a class="navBarLeft" href="#" onclick="showUploadDialog({});" style="float:left; margin-right:5px;"><img src="/ourspaces/icons/001_58.png" align="left" border="0"/>Upload New Resource</a> 
			      </div>
		      </div>
           </div>  
           <div class="separator">
              <img src="/ourspaces/icons/separator.png" align="right" style="margin: 0px;" border="0"/> 
           </div>     
       </div>  
<jsp:include page="top_tail.jsp" />
<%



user = (User) session.getAttribute("use");
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));

int id = user.getID();
Vector resources = user.getResources(id);

%>


  
    
    <div class="regContainer">
    
    	<style>
.hidden { display: none; }
.unhidden { display: block; }
</style>

<script language = "javascript">


<script type="text/javascript">
function unhide(divID) {
  var item = document.getElementById(divID);
  if (item) {
    item.className=(item.className=='hidden')?'unhidden':'hidden';
  }
}
</script>

<script language="javascript">
function add(id) {
	
	var tagID = 'tag' + id;
	var resID = 'resID' + id;
	var userID = 'id' + id;
	var tagName = document.getElementById(tagID).value;
	var resource = document.getElementById(resID).value;
	var user = document.getElementById(userID).value;
	
	
	var xmlHttpRequest=init(tagName, user);

 	function init(tag, usr){

		if (window.XMLHttpRequest) 
		{
			return new XMLHttpRequest();
		} 
		else if (window.ActiveXObject)
		{
			return new ActiveXObject("Microsoft.XMLHTTP");
		}
		
	}
		
	var username=document.getElementById("username");
	xmlHttpRequest.open("GET", "/ourspaces/Tagger?tag="+ encodeURIComponent(tagName) + "&resource=" + encodeURIComponent(resource) + "&user=" + encodeURIComponent(user), true);
	xmlHttpRequest.onreadystatechange=processRequest;
	xmlHttpRequest.send(null);
		
	function processRequest(){
		if(xmlHttpRequest.readyState==4){
			if(xmlHttpRequest.status==200){
				processResponse();
			}
		}
	}
		
	function processResponse(){
		document.getElementById(id).innerHTML = '<p>Tag successfully added.</p>';
	}
}
</script>

<script type="text/javascript" language="javascript">
function doComment(res)
{
	jQuery.facebox(function() { 
	  jQuery.get("createcomment.jsp?about="+res+"&namespace=http://www.policygrid.org/provenance-generic.owl", function(data) {
	    jQuery.facebox(data)
	  })
	})	
}

function doMoveFile(file)
{
	jQuery.facebox(function() { 
	  jQuery.get("movefolderUser.jsp?fileID="+file, function(data) {
	    jQuery.facebox(data)
	  })
	})	
}

function doPermissions(resource)
{
	jQuery.facebox(function() { 
	  jQuery.get("permissions/setPermission.jsp?resource="+resource, function(data) {
	    jQuery.facebox(data)
	  })
	})	
}

function doDelete(resource, title)
{
	jQuery.facebox(function() { 
	  jQuery.get("deleteResource.jsp?title="+title+"&resource="+resource+"&page=myresources.jsp", function(data) {
	    jQuery.facebox(data)
	  })
	})	
}


function moveFile(fileID, folder, optSubFolder) {
	window.location='moveFileUser.jsp?fileID='+fileID+'&folder='+folder+'&optSubFolder='+optSubFolder;
}

</script>

<script src="/ourspaces/jqueryFileTree.js" type="text/javascript"></script>
		<link href="/ourspaces/jqueryFileTree.css" rel="stylesheet" type="text/css" media="screen" />
		
		<script type="text/javascript">
			
			$(document).ready( function() {
				
				$('#fileTreeProject').fileTree({ root: '/', script: 'jqueryFileTreeUser.jsp' }, function(file) { 
					
	              jQuery.get("/ourspaces/browseInfo.jsp?resource="+file, function(data) {
	            	  document.getElementById("resInfo").innerHTML = data;
	            	  initClasses();
				    })
				});
			});
		</script>


<div class="homeStatusContainer" style="color:black; position:relative;" >
	<div id="columns" style="position: relative;">
		
		<ul id="column1" class="column" style="width: 650px;">  
			<li class="widget color-orange" id="p1">
		  		<div class="widget-head">
					<h3 class="style3">My Resources</h3>
				</div>
		
		      <div class="widget-content">
		         <div class="widget-top" >
				      <div class="wlink"  align="right" style="float:right;  margin-left: 5px; margin-right: 10px; margin-bottom: 0px; padding: 10px;">
				         <a href="#"  style="" onclick="upload();"><img src="/ourspaces/icons/001_58.png" align="middle" style="float: left; display:inline-block; margin: -2px; border: 0px none;"/><span style="margin-left:8px;">Upload New Resource</span></a>
				      </div>
			     </div>
		      </div>
		      
			 <div style="background-color:white; position:relative; float:left; padding:5px">
		          <!-- project resources -->
			        <div  style="position: relative; float: left;">
			            	<div style="position: relative; float: left; width: 620px;">	                    
			                   
			                    <div style="position: relative; float: left; width: 610px;">		                    	
							  	  	<div id="fileTreeProject" class="demo"></div>	                    	
								</div>
			                </div>
				                
				    </div>
			        <!--  end of project resources -->	
			  </div>
			  </li>
		</ul>
		
		<ul id="column2" class="column" style="width: 310px;">  
			  <li class="widget color-orange" id="p2">
		  		<div class="widget-head">
					<h3 class="style3">Description</h3>
				</div>
				
				<div class="widget-content">
	                <div id="resInfo" style="position: relative; float: left;">	                    		              	
				    </div>
            	</div>
			 </li> 
	    </ul>
		
		
	</div>
</div>


</div>
 
 <jsp:include page="bottom.jsp" />