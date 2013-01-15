<%@ page language="java"
	import="net.sf.json.JSONArray, common.ParameterHelper,provenanceService.*,com.hp.hpl.jena.rdf.model.*,com.hp.hpl.jena.ontology.*,java.util.Iterator,java.util.*,java.net.*,java.text.SimpleDateFormat,java.util.ArrayList,java.io.*,java.net.*,java.util.Vector"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<!--[if IE 7]>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
<![endif]-->  

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<title>ourSpaces</title>

<link rel="stylesheet" type="text/css" href="/ourspaces/style.css" />
<link rel="stylesheet" type="text/css" href="/ourspaces/css/imageFrame.css"  />
<link rel="stylesheet" type="text/css" href="/ourspaces/facebox.css" media="screen" />
<link rel="stylesheet" type="text/css" href="/ourspaces/inettuts.css" />
<link rel="stylesheet" type="text/css" type="/ourspaces/text/css" href="/ourspaces/css/custom-theme/jquery-ui-1.8.12.custom.css"  />
<link rel="stylesheet" type="text/css" href="/ourspaces/skins/tango/skin.css" />
<link rel="stylesheet" type="text/css" href="/ourspaces/javascript/jquery.treeview.css" />
<link rel="stylesheet" type="text/css" href="/ourspaces/css/chat.css" media="all" />
<link rel="stylesheet" type="text/css" href="/ourspaces/css/screen.css" media="all" />

<link rel="stylesheet" type="text/css" href="/ourspaces/css/jquery.ui.ourSpacesTagging.css" media="all" />


<!--[if lte IE 7]>
<link type="text/css" rel="stylesheet" media="all" href="/ourspaces/css/screen_ie.css" />
<![endif]-->  


<script type="text/javascript" src="http://openspace.ordnancesurvey.co.uk/osmapapi/openspace.js?key=7CCF1E7712317B1BE0405F0AF1604FF3"></script>  
<script type="text/javascript" src="/ourspaces/javascript/jquery-1.6.2.min.js"></script> 
<script type="text/javascript" src="/ourspaces/javascript/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/tab.js"></script>
<script type="text/javascript" src="/ourspaces/facebox.js" ></script>
<script type="text/javascript" src="/ourspaces/javascript/calendar.js"></script>
<script type="text/javascript" src="/ourspaces/js/richtext.js"></script>
<script type="text/javascript" src="/ourspaces/js/config.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/jquery.ifixpng.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/jquery.imageFrame.js"></script> 
<script type="text/javascript" src="/ourspaces/javascript/jquery.jcarousel.js"></script> 
<script type="text/javascript" src="/ourspaces/javascript/jquery.simpletip-1.3.1.min.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/jquery.qtip-1.0.0-rc3.min.js"></script> 
<script type="text/javascript" src="/ourspaces/javascript/chat.js"></script>
<script type="text/javascript" src="/ourspaces/jscripts/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/jquery.treeview.js"></script>
<script type="text/javascript" src="/ourspaces/codebase/dhtmlxvault.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/pls.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/top.js"></script>
<script type="text/javascript" src="/ourspaces/messages/msgfunctions.js"></script>
<script type="text/javascript" src="http://download.skype.com/share/skypebuttons/js/skypeCheck.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/imgpreview.full.jquery.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/spin.min.js"></script>

<script type="text/javascript" src="/ourspaces/javascript/jquery.ourSpacesProvenance.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/jquery.textchange.js"></script>    
    
<!-- My resources box -->
<script type="text/javascript" src="/ourspaces/boxes/myresources.js"></script>
<!-- Artifact space -->
<script type="text/javascript"	src="/ourspaces/artifactSpace/artifactSpace.js"></script>
    
<!-- Upload -->
<script type="text/javascript" src="/ourspaces/javascript/multiplemaps.js"></script>
<script type="text/javascript" src="/ourspaces/uploadform/ajaxfileupload.js"></script>
<script type="text/javascript" src="/ourspaces/uploadform/upload.js"></script>

<script type="text/javascript"	src="/ourspaces/javascript/jquery.jsPlumb-1.3.8-all-min.js"></script>


<!-- Autocomplete -->
<link rel="stylesheet" type="text/css" href="/ourspaces/css/autocomplete/autocomplete.css " media="all" />
    
</head>
<body>
<!-- CSS Files -->
<link type="text/css"	href="/ProvenanceService/css/JsPlumb.css" rel="stylesheet" />
<link type="text/css"	href="/ProvenanceService/css/ForceDirected.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="./css/jquery.treeview.css" />
<!-- <link type="text/css"	href="./css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />-->

<!-- <script type="text/javascript"	src="/ProvenanceService/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript"	src="/ProvenanceService/js/jquery.qtip-1.0.0-rc3.min.js"></script>-->
<script type="text/javascript"	src="/ProvenanceService/js/jquery.jsPlumb-1.3.3-all.js"></script>

<!-- Custom files -->
<script type="text/javascript"	src="/ProvenanceService/js/seedrandom.js"></script>
<script type="text/javascript"	src="/ProvenanceService/js/init.js"></script>
<script type="text/javascript"	src="/ProvenanceService/js/graphOperations.js"></script>
<script type="text/javascript"	src="/ProvenanceService/js/customJsPlumb.js"></script>
<script type="text/javascript"	src="/ProvenanceService/js/customJsPlumbSpring.js"></script>
<script type="text/javascript"	src="/ProvenanceService/js/customJsPlumbForce.js"></script>
<script type="text/javascript"  src="/ProvenanceService/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="/ProvenanceService/js/jquery.treeview.js"></script>

<!-- <script type="text/javascript" src="/ourspaces/javascript/top.js"></script> -->

<%
ParameterHelper parHelp = new ParameterHelper(request, session);

	String resource = (String) parHelp.getParameter("resource","");
	String editable = (String) parHelp.getParameter("editable","false");
	String escId;
	if(resource.contains("#"))
		escId = resource.substring(resource.indexOf("#")+1);
	else
		escId = resource.substring(resource.indexOf("/")+1);
	String fileName = (String) parHelp.getParameter("fileName","SimpleWERCM.owl");
	String namespace = (String) parHelp.getParameter("namespace","http://www.local-fp7.com/ontologies/WERCM-0.0/runs/SimpleWERCM-0.0-run1234-provenance.owl#");
	FileInputStream in = new FileInputStream(fileName);
	//OntModel resource = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
	OntModel model = ModelFactory.createOntologyModel();
	
	
	model = (OntModel) model.read(in,namespace);
	ProvenanceServiceImpl ps = new ProvenanceServiceImpl();
	DataProvider dp = new DataProvider();
	dp.setOntologies(model);
	ps.setDataProvider(dp);
	
	ps.initEdges();
	ps.initNodes();
	String provSession = ps.startSession();
	ps.addRDFGraph(provSession, model);
	session.setAttribute("simulationProv",ps);
	ProvenanceServiceImpl impl = ProvenanceService.getSingleton();
	JSONArray jsonGraph = impl.getJSONGraph(provSession);
	//json = '<%=jsonGraph';
%>
<script>
	//json = eval(json);
	sessionId = '<%=provSession%>';
	artifactId = '<%=resource%>';
	json = [];
	var provenanceEditable = <%=editable%>;
	var superclasses = new Object();
	var subclasses = new Object();
	serverVisual = '/ourspaces/simulation/';
	jsPlumb.width = 495, jsPlumb.height = 165, jsPlumb.offsetX=500, jsPlumb.offsetY=500;
	jsPlumb.canvas = "infovis";
	<jsp:include page="js/edges.jsp"	flush="false">
		<jsp:param value="location" name="id"/>
		<jsp:param value="false" name="controls"/>
	</jsp:include>	
	<jsp:include page="getSuperclasses.jsp"	flush="false">
		<jsp:param value="http://openprovenance.org/ontology#Artifact" name="className"/>
	</jsp:include>	
	<jsp:include page="getSuperclasses.jsp"	flush="false">
		<jsp:param value="http://openprovenance.org/ontology#Agent" name="className"/>
	</jsp:include>	
	<jsp:include page="getSuperclasses.jsp"	flush="false">
		<jsp:param value="http://openprovenance.org/ontology#Process" name="className"/>
	</jsp:include>	


	
	
	function enlargeProvenance(){
		var newEl = $("<div>");
		newEl.attr("id","dialogProvenance");
		newEl.appendTo('body');
		//Pull the provenance to the dialog
		var content = $('#provenance<%=escId%>').detach();
		content.appendTo('#dialogProvenance');
		$("#center-container").css("width","100%");
		$("#center-container").css("height","95%");
		$( "#dialogProvenance" ).dialog({
			width: 946,
			height: 600,
			modal: true,
			open: function(event, ui) { 
				jsPlumb.width = $("#center-container").width()-115, 
				jsPlumb.height = $("#center-container").height()-40;
				layout();
			},
			//Push the provenance back to place.
			close: function(event, ui) { 	
				var content = $('#provenance<%=escId%>').detach();
				content.prependTo('#provenanceResource');
				$("#center-container").css("width","100%");
				$("#center-container").css("height","250px");
				jsPlumb.width = $("#center-container").width()-115;
				jsPlumb.height = $("#center-container").height()-40;
				content.css("z-index","1");
				$( "#dialogProvenance" ).remove();
				provVis.layout.layout();

				//jsPlumb.width = $("#center-container").width()-30, 
				//jsPlumb.height = $("#center-container").height()-30;
			}
		});
	}
	
	$(document).ready(function() { 
		$("#center-container").css("width","100%");
		$("#center-container").css("height","250px");

		$("#provenance<%=escId%>").css("width","100%");
		$("#provenance<%=escId%>").css("height","100%");
		
		jsPlumb.width = $("#center-container").width()-115, 
		jsPlumb.height = $("#center-container").height()-40;
		loadProperties();
	
		   loadSuperclassesProcess();
		   loadSuperclassesArtifact();
		   loadSuperclassesAgent();
		   //Now fill the subclasses.
		   var x,y;
		   for(x in superclasses){
			   var subclass = x;
			   var xsuperclasses = superclasses[x];
			   for(y in superclasses){
				   var superclass = y;
				   if(subclasses[y] == null){
					   subclasses[y] = [];
				   }
				   subclasses[y].push(x);
			   }
		   }
		provVis.core.loadProvenance('<%=resource%>', '<%=provSession %>');
		});
	</script>
<div  style="float:left" id="provenance<%=escId%>">
	
	
	<div id="center-container">
		<!-- <div id="timeArrow" style="float: left;top: 165px;position: relative;opacity: 0.6;">
			<div id="arrowText" style="color: black;font-size: large;font-weight: bold;left: 200px;position: relative;top: 17px;width: 50px; z-index: 999;">Time</div>
			<div id="arrowBody" style="background-color: darkgrey;float: left;height: 6px;position: relative;top: 7px;width: 550px;"></div>
			<div id="arrowHead" style="border-bottom: 10px inset white;border-left: 20px inset white;border-top: 10px outset white;float: right;"></div>
		</div> -->
		<div id="infovis" class="infovis"></div>
	</div>
	
	<div style="background-color: #222222;float: left; width: 100%;">
							<a style="float:left" href="#" onclick="enlargeProvenance();return false;"><img src="/ourspaces/icons/001_38.png" style="border:0"></img></a>
							<a style="float:left" href="#" onclick="layout();return false;"><img src="/ourspaces/icons/001_39.png" style="border:0"></img></a>
						</div>
</div>