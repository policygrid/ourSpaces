
<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="projectBean" class="common.ProjectBean" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="rdf" class="common.RDFi" />
 <jsp:useBean id="blog" class="common.Blog" />

<jsp:useBean id="access" class="common.AccessControl" />
<jsp:useBean id="project" class="common.Project" />
    
<jsp:include page="top_head.jsp" />
<!-- menu goes here -->
<jsp:include page="top_tail.jsp" />


<%if (session.isNew()==true){ %>
<jsp:forward page="error.jsp" />
<% } %>

<% 
if( session.getAttribute("use") == null){ %>
<jsp:forward page="error.jsp" />
<% }

user = (User) session.getAttribute("use");
%>




<script type="text/javascript" language="javascript">
function doComment(res)
{
	jQuery.facebox(function() { 
	  jQuery.get("createcomment.jsp?about="+res, function(data) {
	    jQuery.facebox(data)
	  })
	})	
}

function doMoveFile(file, resource)
{
	var res = encodeURIComponent(resource);
	
	jQuery.facebox(function() { 
	  jQuery.get("movefolder.jsp?fileID="+file+"&resource="+res, function(data) {
	    jQuery.facebox(data)
	  })
	})	
}

function doAddFolder()
{
	jQuery.facebox(function() { 
	  jQuery.get("project/newfolder.jsp", function(data) {
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
</script>

<script language="javascript" type="text/javascript">

function moveFile(fileID, folder, optSubFolder) {
	window.location='moveFile.jsp?fileID='+fileID+'&folder='+folder+'&optSubFolder='+optSubFolder;
}

function createFolder(folder, subFolder) {
	window.location='createFolder.jsp?folder='+folder+'&subFolder='+subFolder;
}

function removeFolder(folder) {
	window.location='deleteFolder.jsp?folder='+folder;
}



</script>

<script src="/ourspaces/jqueryFileTree.js" type="text/javascript"></script>
		<link href="/ourspaces/jqueryFileTree.css" rel="stylesheet" type="text/css" media="screen" />
		
		<script type="text/javascript">
			
			$(document).ready( function() {
				
				$('#fileTreeProject').fileTree({ root: '/', script: 'jqueryFileTree.jsp' }, function(file) { 

	              jQuery.get("/ourspaces/browseInfo.jsp?resource="+file, function(data) {
	            	  document.getElementById("resInfo").innerHTML = data;
				    })
						
				});
				
				
			});
		</script>


<script type="text/javascript" src="/ourspaces/javascript/excanvas.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/jquery-ui-1.8.5.min.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/jquery.jsPlumb-1.2.2-all.js"></script>
  	 
      
      

 
  <div style="position:relative; float:left; width:978px; margin-bottom:5px;">
  			<div style="position:relative; float:left; width:600px;">
            	<div class="projectTitle">
                    <span>FEARLUS Space</span>
            	</div>
            	<div class="projectSubTitle">
                    <span>FEARLUS is a part of the Land Use Change Modelling project within the Land Use Change research programme at the Macaulay Land Use Research Institute.</span>
            	</div>
       		</div>
        	<div style="position:relative; float:left; padding-left:78px; width:285px; color: #666;">

  	  			<img src="../img/fearlus_upload.jpg" style="position: relative; float: left;"></img>

        	
        			
       		</div>
      </div>
 

	
	
<link rel="stylesheet" type="text/css" href="/ourspaces/css/demos.css" />	
<link type="text/css" href="/ourspaces/themes/base/jquery.ui.all.css" rel="stylesheet" />
	<style type="text/css">
	.column { width: 170px; float: left; padding-bottom: 100px; }
	.portlet { margin: 0 1em 1em 0; }
	.portlet-header { margin: 0.3em; padding-bottom: 4px; padding-left: 0.2em; }
	.portlet-header .ui-icon { float: right; }
	.portlet-content { padding: 0.4em; }
	.ui-sortable-placeholder { border: 1px dotted black; visibility: visible !important; height: 50px !important; }
	.ui-sortable-placeholder * { visibility: hidden; }
	</style>
	<script type="text/javascript">
	$(function() {
		$(".column").sortable({
			connectWith: '.column'
		});

		$(".portlet").addClass("ui-widget ui-widget-content ui-helper-clearfix ui-corner-all")
			.find(".portlet-header")
				.addClass("ui-widget-header ui-corner-all")
				.prepend('<span class="ui-icon ui-icon-minusthick"></span>')
				.end()
			.find(".portlet-content");

		$(".portlet-header .ui-icon").click(function() {
			$(this).toggleClass("ui-icon-minusthick").toggleClass("ui-icon-plusthick");
			$(this).parents(".portlet:first").find(".portlet-content").toggle();
		});

		$(".column").disableSelection();
	});
	</script>


<div class="demo">

<div class="column" style="width: 470px;">

	<div class="portlet">
		<div class="portlet-header">Description</div>
		<div class="portlet-content">
		<img src="img/fearlus_upload.jpg" style="position: relative; float: left;"></img>
		 <p>
                           FEARLUS is a part of the Land Use Change Modelling project within the Land Use Change research programme at the Macaulay Land Use Research Institute. 
                           Land use change is a vital aspect of the development of coupled socio-ecosystems at a range of scales from the local to the global. 
                           FEARLUS is a response to the difficulties of understanding land use change, and hence of forseeing the likely consequences of possible changes 
                           in the complex network of processes that produce it. These include local biophysical factors, climatic and economic conditions, technical innovation, 
                           social, cultural and institutional influences, and policy-makers' decisions. Moreover, changes in land use can themselves affect some of these constraints 
                           and influences on future change. 
        </p>
				
		</div>
	</div>
	
	<div class="portlet">
		<div class="portlet-header">News</div>
		<div class="portlet-content">Lorem ipsum dolor sit amet, consectetuer adipiscing elit</div>
	</div>

</div>

<div class="column">

	<div class="portlet">
		<div class="portlet-header">Shopping</div>
		<div class="portlet-content">Lorem ipsum dolor sit amet, consectetuer adipiscing elit</div>
	</div>

</div>

<div class="column">

	<div class="portlet">
		<div class="portlet-header">Links</div>
		<div class="portlet-content">Lorem ipsum dolor sit amet, consectetuer adipiscing elit</div>
	</div>
	
	<div class="portlet">
		<div class="portlet-header">Images</div>
		<div class="portlet-content">Lorem ipsum dolor sit amet, consectetuer adipiscing elit</div>
	</div>

</div>

</div><!-- End demo -->
 
 
 
 </div> 
  <jsp:include page="bottom.jsp" />

