<%
String label = "";

if(request.getParameter("label") != null) {
label = request.getParameter("label");
}
%>
<script>
//Overriding default click behaviour in order to store the values.
//click 
osMapF.events.register("click", osMapF, function(e) {
	osMapF.events.clearMouseCache();		
	var xy = osMapF.events.getMousePosition(e);
	var pt = osMapF.getLonLatFromViewPortPx(xy);	
	var lonlat = gridProjection.getLonLatFromMapPoint(pt);
	pos = new OpenSpace.MapPoint(pt.lon,pt.lat);
	
	document.getElementById(fieldid + '_l1').value = lonlat.lon;
	document.getElementById(fieldid + '_l2').value = lonlat.lat;
	
	if (marker != null) osMapF.removeMarker(marker);
	
	size = new OpenLayers.Size(30,39);
	offset = new OpenLayers.Pixel(-15,-36);
	infoWindowAnchor = new OpenLayers.Pixel(16,16);
	icon = new OpenSpace.Icon('http://openspace.ordnancesurvey.co.uk/osmapapi/img_versions/img_1.1.0/OS/images/markers/marker-cross-med-blue.png', size, offset, null, infoWindowAnchor);
	marker = osMapF.createMarker(pos, icon, null, null);

});
</script>
<div class="ui-state-highlight ui-corner-all" style="float:left; margin: 10px; padding: 8px; width: 400px; ">
<label for="nom"><%=label %>: </label>

 <div style="position: relative; float:left; width: 380px; padding:10px;">
    	<div id="<%=label%>_map" style="border: 1px solid black; position: relative; float: left; width:380px; height:300px;"></div>
    </div>
    
    <div style="position: relative; float: left; width:100%;">
    
    		<div style="position:relative; float: left; width:100%; margin-bottom:10px;">
				<div style="width:190px; position:relative; float:left;"><%= label %><span style="color:red;"> *</span></div>				 
		 		<input style="border:1px solid #006699; width:300px; height: 20px; font-size:14px;" onkeydown="unlock()" onchange="changeValue('<%=label %>', this)" value="Name of location.." type="text" id="<%=label%>_locationName"/>
		 	</div> 
    
    		<div style="position:relative; float:left; width:100%; padding-left:200px; ">
		        <input id="<%=label %>_l1" style="position: relative; float: left; border:1px solid #006699; margin-right:10px; background-color:#c2c2c2; font-size:10px; height:14px; width:80px;" onclick="showUpdate('<%=label %>_updateLoc')" onkeydown="unlock()" onchange="changeValue('<%=label %>', this)" value="Longitude" type="text"/>
		        <input id="<%=label %>_l2" style="position: relative; float: left; border:1px solid #006699; margin-right:10px; background-color:#c2c2c2; font-size:10px; height:14px; width:80px;" onclick="showUpdate('<%=label %>_updateLoc')" onkeydown="unlock()" onchange="changeValue('<%=label %>', this)" value="Latitude" type="text"/>
		        <div id="<%=label %>_updateLoc" style="position: relative; float: left; display:none;"><a href="#" onclick="changemarker(); return false;">[Update]</a></div>
		        <div style="display:none;" style="position: relative; float: left;" id="<%=label %>_deleteLoc"><a href="#" onclick="removeNewDiv('<%=label %>'); return false">[Delete field]</a></div>
		    </div>
    	
		
	    
	</div>





</div>