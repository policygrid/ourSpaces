  <%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="login" class="common.Login" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="projectrdf" class="common.ProjectRDF" />
<jsp:useBean id="projectblog" class="common.ProjectBlogBean" />
<jsp:useBean id="project" class="common.Project" />


<html>
	<head>
		<script type="text/javascript" src="/ourspaces/javascript/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="/ourspaces/javascript/jquery-ui-1.8.16.custom.min.js"></script>
<!-- 		<script type="text/javascript" src="/ourspaces/javascript/top.js"></script> -->
			
		<style> 
		
		.nlg {
			display:inline-block;
			width: 10px;
			height: 10px;
			padding: 0px;
			padding: 0px;
	    	background-image: url(/ourspaces/images/speech.png);
		}

		</style>

		<script type="text/javascript">

		
		<!--Remember to parse hash key (#) in the URI to its hex ASCII code (%23) -->
<!--	http://openprovenance.org/ontology#0c0c480a-1ca3-402c-9086-1e7476083b21  	-->
<!-- paper: http://openprovenance.org/ontology#3fb74c91-8caa-434a-8d98-12e2d9da387a -->
<!--person (ed): http://xmlns.com/foaf/0.1/%230c8d01df-1d7a-4a2c-8298-b5e6fbb1aa9c -->
		
		$(document).ready(function() {
			$("button").click(function(){
				$.ajax({
					type: 'GET',
					url: "/ourspaces/LiberRestServlet?resourceID=http://www.policygrid.org/project.owl%23aadb9597-6392-42e0-9815-489cbe80cd19", 
					dataType : "html",
					async : false,
					success : function(html, errorThrown) {
						$("div#NLG").html(html);
					}
				});
			});
		});
	    
		$(document).ready(function() {
			$("a.liber").live('click',function() {
				var anchor = this;
				var did = $(this).attr("rel");
				$.ajax({
					type: 'GET',
					url: "/ourspaces/LiberRestServlet?resourceID=" + $(this).attr("href"), 
					dataType : "html",
					async : false,
					success : function(html, errorThrown) {
						$("div#"+did).html(html);
					}
				});
				return false;
			});
		});
		
		
		$(function() {
			$( "#tabs" ).tabs();
			$('.liber2').click(function() {
				  $('.nlgDiv').slideToggle('slow', function() {
					    // Animation complete.
					  });
					});
// 			$( ".nlgDiv" ).slideToggle("slow");

		});

		
		function loadXMLDoc()
		{
		var xmlhttp;
		if (window.XMLHttpRequest)
		  {// code for IE7+, Firefox, Chrome, Opera, Safari
		  xmlhttp=new XMLHttpRequest();
		  }
		else
		  {// code for IE6, IE5
		  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		  }
		xmlhttp.onreadystatechange=function()
		  {
		  if (xmlhttp.readyState==4 && xmlhttp.status==200)
		    {
		    document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
		    }
		  }
		xmlhttp.open("GET","/ourspaces/LiberRestServlet?resourceID=http://www.policygrid.org/project.owl%23aadb9597-6392-42e0-9815-489cbe80cd19",true);
		xmlhttp.send();
		}
		
	  	</script>
	</head>
	<body>
	
	<jsp:include page="/top_head.jsp"  flush="true"/>
	
		<div id="NLG"></div>
		<button>Click</button>
		
		<h1> NLG Span:</h1> <span class="nlg" rel="http://www.policygrid.org/project.owl%23aadb9597-6392-42e0-9815-489cbe80cd19"></span>
		
		<a style="text-decoration:underline;" href="/ourspaces/LiberRestServlet?resourceID=http://www.policygrid.org/project.owl%23aadb9597-6392-42e0-9815-489cbe80cd19" rel="anchoredResourceID" class="liber2"> anchorInfoWords </a>
		
		<div class="nlgDiv" id="anchoredResourceID"></div>


		<h1>Call a service from xmlHTTP: </h1>
<!-- 				<div><script type="text/javascript">alert(xmlhttp.open("GET","/ourspaces/LiberRestServlet?resourceID=http://www.policygrid.org/project.owl%23aadb9597-6392-42e0-9815-489cbe80cd19",true);)</script></div> -->
<!-- 		<div id="myDiv"><h2> Load text here </h2></div> -->
		<button type="button" onclick="loadXMLDoc()">Change Content</button>

		<h1>Tabs</h1>

         <div id="tabs">
			<ul>
				<li><a href="#table">Table</a>
				</li>
				<li><a href="#text">Text</a>
				</li>
			</ul>
			<div id="table">
				<p>tab1</p>
			</div>
			<div id="text">
				<p>tab2</p>
			</div>
		</div>


		<h1>Gender:</h1>
		<form>
				<p style="padding-left:10px; color:#999999; padding:3px; font-size:10px">
				  <div style="width:120px; float:left; position:relative;">Gender:</div> 
				  <input type="radio" name="gender" value="male" /> Male &nbsp
				  <input style="text-indent:5px;" type="radio" name="gender" value="female" /> Female
				</p>
		</form>	


</body>
</html>