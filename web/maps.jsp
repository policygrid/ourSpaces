<%@ page language="java" import="java.util.Iterator,jxl.*,java.io.File,java.util.ArrayList,java.io.*,java.net.*,java.util.Vector,common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="access" class="common.AccessControl" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<%
	if (session.getAttribute("use") == null) {

		String topage = "";
		if (request.getQueryString() != null) {
			topage = java.net.URLEncoder.encode(request.getRequestURI()
					+ "?" + request.getQueryString());
		} else {
			topage = request.getRequestURI();
		}
%>
	
	<jsp:forward page="error.jsp" >
	<jsp:param name="topage" value="<%=topage%>"/>
	</jsp:forward>
<%
	}

	user = (User) session.getAttribute("use");

	if (user.getID() == 0) {

		String topage = "";
		if (request.getQueryString() != null) {
			topage = java.net.URLEncoder.encode(request.getRequestURI()
					+ "?" + request.getQueryString());
		} else {
			topage = request.getRequestURI();
		}
%>
		
		<jsp:forward page="error.jsp" >
		<jsp:param name="topage" value="<%=topage%>"/>
		</jsp:forward>
	<%
		}

		String url = "/ourspaces/users/" + user.getID() + ".xml";
		String username = user.getUsername(user.getID());
		String resID = user.getRes(user.getID());

		XML xml = new XML();
		Project projects = new Project();

		int userid = user.getID();
		String width = "970px";
		String height = "550px";
	%>


<jsp:include page="top_head.jsp" flush="true"/>
<jsp:include page="top_tail.jsp" flush="true"/>

<div class="homeStatusContainer" style="color:#FFFFFF; position:relative;" >

	<div id="columns" style="position: relative;">

		<ul id="column1" class="column" style="width: 1000px;">
			<li class="widget color-orange" id="pr1">
				<div class="widget-head">
					<h3>Map</h3>
				</div>
				<div class="widget-content" style="padding: 5px;">

					<div id="map" class="widget-content"
						style="border: 0px solid black; width: <%=width%>; height: <%=height%>;">
						<jsp:include page="artifactSpace/feature_plain.jsp" flush="false">
							<jsp:param value="map" name="id" />
							<jsp:param value="false" name="controls" />
							<jsp:param value="Location:" name="label" />
							<jsp:param value="<%=width %>" name="width" />
							<jsp:param value="<%=height %>" name="height" />
							<jsp:param value="true" name="optional" />
						</jsp:include>
					</div>
				</div>
			</li>
		</ul>
	</div>
	<!-- end of columns -->

	<script type="text/javascript" src="cookie.jquery.js"></script>
	<script type="text/javascript" src="inettuts.jsp?space=map"></script>

</div>  <!-- end of home status container -->        
   
<script type="text/javascript">	
	var dvid = 'map';	
	//Init the map and controls.
	 initmapbuilderNew(dvid);
				
	//Display all flags.
	<%Vector gr = rdf.getAllGeoResurces();

	for (int i = 0; i < gr.size(); i++) {
		Resources re = (Resources) gr.get(i);
		common.Utility.log.debug(re.getTitle());
		common.Utility.log.debug(re.getLat());
		common.Utility.log.debug(re.getLon());
		String resourceURI = re.getID();
		String res = re.getID();
		String encodedURI = java.net.URLEncoder.encode(re.getID());
		res = res.substring(res.indexOf("#") + 1);%>
		var lonlat<%=i%> = new OpenLayers.LonLat();
		lonlat<%=i%>.lon = <%=re.getLon()%>;
		lonlat<%=i%>.lat = <%=re.getLat()%>;

		var gridProjection = new OpenSpace.GridProjection();
		var pt<%=i%> = gridProjection.getMapPointFromLonLat(lonlat<%=i%>);
		size<%=i%> = new OpenLayers.Size(80,32);
		offset<%=i%> = new OpenLayers.Pixel(-15,-36);
		infoWindowAnchor<%=i%> = new OpenLayers.Pixel(16,16);
		icon<%=i%> = new OpenSpace.Icon('/ourspaces/img/<%=re.getGeop()%>.png', size<%=i%>, offset<%=i%>, null, infoWindowAnchor<%=i%>);

		var mapPoint<%=i%> = new OpenSpace.MapPoint(pt<%=i%>.lon,pt<%=i%>.lat);

		// Old code to retrieve info of geolocation (use NLG instead)
// 		$.ajax({
<%-- 		url: "browseMap.jsp?resource=<%=res%>&lat=<%=re.getLat()%>&lon=<%=re.getLon()%>", --%>
// 			async: false,
// 			cache: false,
// 			dataType: "html",
// 			success: function(data) 
// 			{
<%-- 			mydata<%=i%> = data; --%>
// 			}
// 					});		
		
		// create content of popup:
		var mydata<%=i%> = '';
		mydata<%=i%> += '<div style="position: relative; float:left; width:250px; color:black;" id="nlg"></div>';

		//Need to check download permissions:
		<% 
		if (AccessControl.checkPermission("download",user.getFOAFID(),re.getID())) { %>
			mydata<%=i%> += '<div id="download"></div>';
		<%} else { %>
			mydata<%=i%> += "You don't have permission to download this artifact";
		<%}  %>
		

		var popUpSize<%=i%> = new OpenLayers.Size(300, 220);
		var marker<%=i%> = osMapF.createMarker(mapPoint<%=i%>, icon<%=i%>,mydata<%=i%>, popUpSize<%=i%>);
					
		//add an mousedown event to the maker that will make an ajax call to set the textual description:
		marker<%=i%>.events.register("mousedown", marker<%=i%>, function(evt) {
			$("div#download").html('<div id="de" style="position: relative; margin-top: 10px; float:left;"> <a class="fg-button ui-state-active fg-button-icon-right ui-corner-all" href="<%=common.Utility.absoluteURLtoRelative(re.getURL())%>">Download</a> </div> <br> ');
						
		   	//Call NLG service and set the text in the NLG div:
			$.ajax({
				type: 'GET',
				url: "/ourspaces/LiberRestServlet?resourceID=" + "<%=encodedURI%>", 
				dataType : "html",
				async : false,
				success : function(html, errorThrown) {
					$("div#nlg").html(html);
					addNLGlistener();
				}
			});
		});
					
<%-- 	var control = new OpenLayers.Control.SelectFeature(marker<%=i%>); --%>
// 		osMapF.addControl(control);
// 		control.activate();
					
		<%}
		if (gr.size() > 0) {%>
				osMapF.setCenter(mapPoint0,7);
		<%}%>		
			  
		// Should use top.js.addNLGdivListener but it's using live()
		 var addNLGlistener = function() {
		  // Should use live() or delegate() instead of .bind() but those methods don't work...
		$("a.liber2").bind('click',function() {
				var anchor = this;
				var did = $(this).attr("rel");
		
				// if the div containing the text hasn't been set yet:
				if( $("div#"+did).is(':empty') ) {
		
					// call LiberRestServlet to obtain the textual description of the resource:
					$.ajax({
						type: 'GET',
						url: $(this).attr("href"), 
						dataType : "html",
						async : false,
						success : function(html, errorThrown) {
							$("div#"+did).html(html);
							addNLGlistener();
						}
					});
				}
				// show/hid the text:
// 					$("div#"+did).slideToggle("fast");
				return false;
			});
		 }
		</script>

 <!-- 
     <a href="#" onclick="getData(1)">Cattle</a> 
     <a href="#" onclick="getData(2)">Sheep</a> 
 -->
 <jsp:include page="bottom.jsp" />