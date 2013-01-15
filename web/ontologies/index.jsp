<%@ page language="java"
	import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*, org.policygrid.ontologies.*"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="login" class="common.Login" />
<jsp:useBean id="projectrdf" class="common.ProjectRDF" />
<jsp:useBean id="projectblog" class="common.ProjectBlogBean" />
<jsp:useBean id="project" class="common.Project" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<%
String uri = request.getRequestURI();
session.setAttribute("container", "");
session.setAttribute("containerType", "");
session.setAttribute("projectID", "");

RDFi rdf = new RDFi();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<!--[if IE 7]>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
<![endif]-->

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>ourSpaces</title>

<link rel="stylesheet" type="text/css" href="/ourspaces/style.css" />
<link rel="stylesheet" type="text/css"
	href="/ourspaces/css/imageFrame.css" />
<link rel="stylesheet" type="text/css" href="/ourspaces/facebox.css"
	media="screen" />
<link rel="stylesheet" type="text/css" href="/ourspaces/inettuts.css" />
<link rel="stylesheet" type="text/css" type="/ourspaces/text/css"
	href="/ourspaces/css/custom-theme/jquery-ui-1.8.12.custom.css" />
<link rel="stylesheet" type="text/css"
	href="/ourspaces/skins/tango/skin.css" />
<link rel="stylesheet" type="text/css"
	href="/ourspaces/javascript/jquery.treeview.css" />
<link rel="stylesheet" type="text/css" href="/ourspaces/css/chat.css"
	media="all" />
<link rel="stylesheet" type="text/css" href="/ourspaces/css/screen.css"
	media="all" />

<link rel="stylesheet" type="text/css"
	href="/ourspaces/css/jquery.ui.ourSpacesTagging.css" media="all" />


<!--[if lte IE 7]>
<link type="text/css" rel="stylesheet" media="all" href="/ourspaces/css/screen_ie.css" />
<![endif]-->


<script type="text/javascript"
	src="http://openspace.ordnancesurvey.co.uk/osmapapi/openspace.js?key=7CCF1E7712317B1BE0405F0AF1604FF3"></script>
<script type="text/javascript"
	src="/ourspaces/javascript/jquery-1.6.2.min.js"></script>
<script type="text/javascript"
	src="/ourspaces/javascript/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/tab.js"></script>
<script type="text/javascript" src="/ourspaces/facebox.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/calendar.js"></script>
<script type="text/javascript" src="/ourspaces/js/richtext.js"></script>
<script type="text/javascript" src="/ourspaces/js/config.js"></script>
<script type="text/javascript"
	src="/ourspaces/javascript/jquery.ifixpng.js"></script>
<script type="text/javascript"
	src="/ourspaces/javascript/jquery.imageFrame.js"></script>
<script type="text/javascript"
	src="/ourspaces/javascript/jquery.jcarousel.js"></script>
<script type="text/javascript"
	src="/ourspaces/javascript/jquery.simpletip-1.3.1.min.js"></script>
<script type="text/javascript"
	src="/ourspaces/javascript/jquery.qtip-1.0.0-rc3.min.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/chat.js"></script>
<script type="text/javascript"
	src="/ourspaces/jscripts/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript"
	src="/ourspaces/javascript/jquery.treeview.js"></script>
<script type="text/javascript" src="/ourspaces/codebase/dhtmlxvault.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/top.js"></script>

<script type="text/javascript"
	src="http://download.skype.com/share/skypebuttons/js/skypeCheck.js"></script>


<script type="text/javascript"
	src="/ourspaces/javascript/jquery.ourSpacesProvenance.js"></script>
<script type="text/javascript"
	src="/ourspaces/javascript/jquery.textchange.js"></script>

<!-- My resources box -->
<script type="text/javascript" src="/ourspaces/boxes/myresources.js"></script>
<!-- Artifact space -->
<script type="text/javascript"
	src="/ourspaces/artifactSpace/artifactSpace.js"></script>

<!-- Upload -->
<script type="text/javascript"
	src="/ourspaces/javascript/multiplemaps.js"></script>
<script type="text/javascript"
	src="/ourspaces/uploadform/ajaxfileupload.js"></script>
<script type="text/javascript" src="/ourspaces/uploadform/upload.js"></script>

<!-- Autocomplete -->
<link rel="stylesheet" type="text/css"
	href="/ourspaces/css/autocomplete/autocomplete.css " media="all" />

</head>
<body>

	<div style="position: relative; width: 1000px; margin: 0px auto;">
		<div id="suggestions"></div>
	</div>

	<div id="main_container">

		<div class="header">
			<div class="headerContainer">
				<div class="headerLogo">
					<a href="/ourspaces/myhome.jsp" id="headerLogoImg">Home</a>
				</div>

				<div class="search">
					<div
						style="position: relative; float: left; margin-bottom: 5px; width: 100%;">
						<form id="searchform">
							<div>
								What are you looking for? <input type="text" name="tag"
									style="width: 230px; height: 16px; border: 1px solid #666666; background-color: #e6e5e9;"
									id="inputString" />
							</div>

						</form>
					</div>
				</div>
			</div>

		</div>

		<div class="navBarBG">
			<div class="navBar">
				<div class="navBarLeft">
					<div class="dropdownBox">
						<a class="navBarLeft" href="#" style="float: left;"><img
							src="/ourspaces/icons/001_07.png" align="left" border="0" />Spaces
						</a> <img src="/ourspaces/icons/dropdown.png" align="right" border="0" />
						<div class="navBarOptions ui-corner-all" style="width: 750px;">
							<div style="float: left; margin-left: 5px; margin-bottom: 10px;">
								<span style="width: 750px; font-weight: 900;">Available
									Spaces:</span>
							</div>
							<div
								style="float: left; width: 750px; margin-left: 5px; margin-bottom: 10px; border-bottom: 1px solid #FF6600"></div>
							<div
								style="float: left; width: 60px; margin-right: 30px; margin-left: 10px;">
								<a href="/ourspaces/myhome.jsp"><img
									src="/ourspaces/images/spaces/Home.png" border="0"
									style="float: left; margin-left: 0px;" /><br /> Home </a>
							</div>

							<div style="float: left; width: 60px; margin-right: 30px;">
								<a href="/ourspaces/projects.jsp"><img
									src="/ourspaces/images/spaces/Projects.png" border="0"
									style="float: left; margin-left: 0px;" /><br /> Projects </a>
							</div>

							<div style="float: left; width: 60px; margin-right: 30px;">
								<a href="/ourspaces/models.jsp"><img
									src="/ourspaces/images/spaces/Models.png" border="0"
									style="float: left; margin-left: 0px;" /><br /> Models </a>
							</div>

							<div style="float: left; width: 60px; margin-right: 30px;">
								<a href="/ourspaces/docSpace/"><img
									src="/ourspaces/images/spaces/Documents.png" border="0"
									style="float: left; margin-left: 0px;" /><br /> Documents </a>
							</div>

							<div style="float: left; width: 60px; margin-right: 30px;">
								<a href="/ourspaces/data/"><img
									src="/ourspaces/images/spaces/Data.png" border="0"
									style="float: left; margin-left: 0px;" /><br /> Data </a>
							</div>

							<div style="float: left; width: 60px; margin-right: 30px;">
								<a href="/ourspaces/maps.jsp"><img
									src="/ourspaces/images/spaces/Maps.png" border="0"
									style="float: left; margin-left: 0px;" /><br /> Maps</a>
							</div>
							<div style="float: left; width: 60px; margin-right: 30px;">
								<a href="/ourspaces/commSpace/"><img
									src="/ourspaces/images/spaces/Communications.png" border="0"
									style="float: left; margin-left: 0px;" /><br /> Communications
								</a>
							</div>

						</div>
					</div>
				</div>


				<div class="navBarLeft">
					<div class="separator">
						<img src="/ourspaces/icons/separator.png" align="right"
							style="margin: 0px;" border="0" />
					</div>
				</div>
				<div id="contextMenu"></div>
				<div class="navBarRight">
					<a class="navBarRight" href="/ourspaces/logout.jsp"
						style="float: left;"><img src="/ourspaces/icons/logout.png"
						align="left" border="0" />Logout </a>
				</div>
			</div>
		</div>
		<div class="container" style="z-index: 1;">
			<div>
				<div class="homeStatusContainer"
					style="color: #FFFFFF; position: relative;">
					<div id="columns" style="position: relative;">
						<ul id="column1" class="column" style="width: 300px;">
							<li class="widget color-orange" id="widget3">
								<div class="widget-head">
									<h3>Ontologies used in ourSpaces</h3>
								</div>
								<div class="widget-content">
								<ul>
									<li><a href="./actionOntology.owl">Action ontology</a></li>
									<li><a href="./foaf.owl">FOAF ontology</a></li>
									<li><a href="./provenance-generic.owl">OurSpaces resource hierarchy</a></li>
									<li><a href="./provenance-policies.spin.owl">Policy ontology</a></li>
									<li><a href="./policies/UKDADocumentationPolicy1.spin.owl">UKDA policy</a></li>
								</ul>
								</div>
							</li>
						</ul>

					</div>
					<!-- end of columns -->
				</div>
				<!-- end of home status container -->



    </div> <!-- end of content -->
    
    <span style="margin-top:5px; overflow:hidden; width:1000px;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
    
   

</div> <!-- end of container -->

<div class="footermain" >
 <div class=container" style="width: 1000px; margin-left: auto; margin-right: auto;">
     
				<div class="footercolumn"> 
				<h3>Latest Resources</h3> 
			   <%
			   Vector resources = rdf.getLatestObjects(7);
					
					
			   for( int j = resources.size() - 1; j >=0; j--)
				{
						resource = (Resources) resources.get(j);
						String resourceID = resource.getID();
						String[] fields2 = resourceID.split("#");
						String resTitle = resource.getTitle();
						String resDate = resource.getDate();
						String owner = rdf.getResourceDepositor(resourceID);
						ArrayList authors = rdf.getResourceAuthor(resourceID);
						String types = rdf.getResourceType(resourceID);
						String[] type = types.split("#");
						
				%>
				<div style="position:relative; float:left; width:300px; margin-top:6px;">
			 	 <a style="color:white;" href="/ourspaces/artifact_new.jsp?id=<%=fields2[1]%>"><%=resTitle%></a>
				</div>
				<%} %>
				
				</div> 
				<div class="footercolumn"> 
				<h3>Spaces</h3> 
				<div style="position:relative; float:left; width:150px; margin-top:0px;">
				<a href="/ourspaces/projects.jsp" style="color:white;"><img src="/ourspaces/images/spaces/Projects.png" border="0" style="position:relative; float:left; margin-left:12px; margin-right:5px;" />Projects</a>
				</div>
                <div style="position:relative; float:left; width:150px; margin-top:0px;">
                <a href="/ourspaces/models.jsp" style="color:white;"><img src="/ourspaces/images/spaces/Models.png" border="0" style="position:relative; float:left; margin-left:12px; margin-right:5px;" />Models</a>
                </div>
                <div style="position:relative; float:left; width:150px; margin-top:6px;">
                <a href="/ourspaces/documents.jsp" style="color:white;"><img src="/ourspaces/images/spaces/Documents.png" border="0" style="position:relative; float:left; margin-left:12px; margin-right:5px;" />Docs</a>
                </div>
                <div style="position:relative; float:left; width:150px; margin-top:6px;">
                <a href="/ourspaces/data/" style="color:white;"><img src="/ourspaces/images/spaces/Data.png" border="0" style="position:relative; float:left; margin-left:12px; margin-right:5px;" />Data</a>
                </div>
                <div style="position:relative; float:left; width:150px; margin-top:6px;">
                <a href="/ourspaces/maps.jsp" style="color:white;"><img src="/ourspaces/images/spaces/Maps.png" border="0" style="position:relative; float:left; margin-left:12px; margin-right:5px;" />Maps</a>
                </div>
				</div> 
				<div class="footercolumn"> 
				<h3>Helpdesk</h3> 
						<form method="post" action="Controller?action=addBug">
						
						    <div style="position:relative; float:left; width: 300px; margin-bottom:5px;">
						      <div style="position:relative; float:left;">
						      
						      <label for="Feedback">Feedback</label>     
						      <input type="radio" id="Feedback" name="type" value="feedback" checked> 
						      <label for="Bug">Bug</label>     
							  <input type="radio" id="Bug" name="type" value="bug"> 
							  <label for="Feature">Feature</label>     
							  <input type="radio" id="Feature" name="type" value="feature">
						        
							  </div>
						    </div>
						
							<div style="position:relative; float:left; width: 300px; margin-bottom:5px;">
						        <div style="position:relative; float:left; width:150px; margin-bottom:2px;">
						            Subject:
						        </div>
						        <div style="position:relative; float:left;">
						           <input type="text" name="subject" style="width:300px; height:17px; padding-top:3px; border: 1px solid #036;" />
						        </div>
						    </div>
						    
							<div style="position:relative; float:left; width: 300px; margin-bottom:5px;">
						        <div style="position:relative; float:left; width:150px; margin-bottom:2px;">
						           Description:
						        </div>
						        <div style="position:relative; float:left;">
						           <textarea name="message" style="width:300px; height:50px; padding-top:3px; border: 1px solid #036;">
						           </textarea>
						        </div>
						    </div>
						    
						    <input type="submit" value="Send Message"></input>
						
						
						
						</form>
				</div> 
				<div class="clear"></div> 
				<div class="footerlinks">
				 <a href="myhome.jsp">HOME</a> <a href="edit.jsp">EDIT</a> <a href="myresources.jsp">RESOURCES</a> <a href="mymessages.jsp">MESSAGES</a> <a href="mycontacts.jsp">CONTACTS</a> <a href="logout.jsp">LOGOUT</a>
				</div>		
				<div class="policygrid">
				 ourSpaces Virtual Research Environment. Developed by <a href="http://www.policygrid.org">PolicyGrid</a>
				</div>
				
				  
	    
	    
 </div>
 

</div>



</div> <!--  end of main container for crappy chat -->






</body>
</html>
