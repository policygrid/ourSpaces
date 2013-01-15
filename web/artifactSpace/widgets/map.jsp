
<%@ page language="java"
	import="java.util.Iterator,java.util.*,java.net.*,java.text.SimpleDateFormat,java.util.ArrayList,java.io.*,java.net.*,java.util.Vector,common.*"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="blog" class="common.Blog" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="access" class="common.AccessControl" />
<div class="widget-head">
					<h3 class="style3">Location</h3>
				</div>
				<div id="location" class="widget-content"
					style="border: 0px solid black; width: 100%; height: 100%;">
					
					<jsp:include page="../feature_plain.jsp"
						flush="false">
						<jsp:param value="location" name="id"/>
						<jsp:param value="false" name="controls"/>
					</jsp:include>	
				</div>
				<script>	
				var dvid = 'location';
				var divTag = document.getElementById('location');				
				initmapbuilderNew(dvid, false); 				
				<%ArrayList gr = (ArrayList)session.getAttribute("features");
				for (int i = 0; i < gr.size(); i++) {
					ArrayList feature = (ArrayList) gr.get(i);
					if(feature == null || feature.size()<5)
						continue;
				%>
					var lonlat<%=i%> = new OpenLayers.LonLat();
					lonlat<%=i%>.lon = <%=feature.get(4)%>;
					lonlat<%=i%>.lat = <%=feature.get(3)%>;
	
					var gridProjection = new OpenSpace.GridProjection();
					var pt<%=i%> = gridProjection.getMapPointFromLonLat(lonlat<%=i%>);
					var size<%=i%> = new OpenLayers.Size(80,32);
					var offset<%=i%> = new OpenLayers.Pixel(-15,-36);
					var infoWindowAnchor<%=i%> = new OpenLayers.Pixel(16,16);
					var icon<%=i%> = new OpenSpace.Icon('/ourspaces/img/<%=feature.get(0)%>.png', size<%=i%>, offset<%=i%>, null, infoWindowAnchor<%=i%>);
					var mapPoint<%=i%> = new OpenSpace.MapPoint(pt<%=i%>.lon,pt<%=i%>.lat);
	
					var mydata<%=i%>;
					$.ajax({
						url: "browseMap.jsp?resource=<%=Utility.getLocalName(session.getAttribute("artifactID").toString())%>&lat=<%=feature.get(3)%>&lon=<%=feature.get(4)%>",
						async: false,
						cache: false,
						dataType: "html",
						success: function(data) 
						{
							mydata<%=i%> = data;
						}
					});
					var popUpSize<%=i%> = new OpenLayers.Size(300, 220)
					var marker<%=i%> = osMapF.createMarker(mapPoint<%=i%>, icon<%=i%>,null, null);				    
					osMapF.setCenter(mapPoint<%=i%>,7);
			   <%}//End of loop
				if(gr.size()>0) {%>
						osMapF.setCenter(mapPoint0,7);
			  <%}%>
			</script> 			