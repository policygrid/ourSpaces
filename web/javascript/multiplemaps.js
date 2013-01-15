

//This function probably has to be global. It uses global var activeMap
function gazOptionsF(searchVal) {
		//if one match found
		if (searchVal.length == 1)
		{
			maps[activeMap].osMapF.setCenter(searchVal[0].location, 7);
			var gridProjection = new OpenSpace.GridProjection();
			
			var lonlat = gridProjection.getLonLatFromMapPoint(searchVal[0].location);
			maps[activeMap].setLon(lonlat.lon);
			maps[activeMap].setLat(lonlat.lat);
			
			if (maps[activeMap].marker != null) 
				maps[activeMap].osMapF.removeMarker(maps[activeMap].marker);
			
			maps[activeMap].setMarker(searchVal[0].location, false);
			
			maps[activeMap].locationFound = 1;
		}
		
		//if several choices, create a list box
		if (searchVal != null && searchVal.length > 1)
		{
			maps[activeMap].locationFound = 1;
			maps[activeMap].globalGazArray = searchVal;
			var o = document.createElement("OPTION");
			o.text= "Select a place";
			maps[activeMap].da.options.add(o);
			
			//build list box
			for (var i=0 ; i < searchVal.length; i++)
			{
				o = document.createElement("OPTION");
				o.text= searchVal[i].name + ", " + searchVal[i].county;
				maps[activeMap].da.options.add(o);
			}
			
			//make list box visible
			$("#"+maps[activeMap].div_id+" #selectGaz").css("display", "block");
		}
			
		if (locationFound == 0)
		{
			alert("neither postcode or place found");
		}
	}


//result of search postcode is passed here
function onResultF (mapPoint)	{
	//set zoom level depending on sector or full postcode
	if  (sectorFlag == 0){
		zoomVal = 9;
	}
	else {
		zoomVal = 5;
	}
	
	//if not a valid PostCode, pass to gazetteer search
	//an eastValStr of length three indicates no match found for postcode
	if (mapPoint != null)
	{
		eastVal = mapPoint.getEasting();
		eastValstr = eastVal.toString();
	}
	
	//no postcode match, so search gazetteer
	if ( mapPoint == null || eastValstr.length == 3)
	{
		var osGaz = new OpenSpace.Gazetteer;
		var gazArray = osGaz.getLocations(inputStr, gazOptionsF)
		
	}
	
	//zoom to postcode
	if (mapPoint != null && eastValstr.length > 3)
	{
	
		var gridProjection = new OpenSpace.GridProjection();
	
		var lonlat = gridProjection.getLonLatFromMapPoint(mapPoint);
		maps[activeMap].setLon(lonlat.lon);
		maps[activeMap].setLat(lonlat.lat);
	
		if (maps[activeMap].marker != null) 
			maps[activeMap].osMapF.removeMarker(maps[activeMap].marker);
		maps[activeMap].setMarker(mapPoint, true);
		maps[activeMap].locationFound = 1;
		
		$("#"+maps[activeMap].div_id+" #postcode").val("");
	}
	return false;
}
/**
 * This is a class for a map. All functions and attributes are like in a normal map, except that wrapped in a Map class.
 */
function Map() {
	
	this.marker = null;
	this.div_id = null;
	//Index in variable maps 
	this.index;

	this.osMapF;
	this.mapOVF;
	this.postcodeServiceF;
	this.searchBox;
	
	//Variables for postcode/gazetteer searches
	this.inputStr;
	this.sectorFlag;
	this.globalGazArray;
	this.locationFound;
	this.zoomVal;
	this.eastVal;
	this.eastValstr;
	this.da;
	
	this.setLon = function(lon) {
		$("#" + this.div_id + "_l1").val(lon);
	}
	this.setLat = function(lat) {
		$("#" +this.div_id + "_l2").val(lat);
	}
	
	this.getLon = function() {
		return $("#" + this.div_id + "_l1").val();
	}
	this.getLat = function() {
		return $("#" + this.div_id + "_l2").val();
	}
	
	this.setMarker = function(mapPoint, center){
		this.size = new OpenLayers.Size(30,39);
		this.offset = new OpenLayers.Pixel(-15,-36);
		this.infoWindowAnchor = new OpenLayers.Pixel(16,16);
		this.icon = new OpenSpace.Icon('http://openspace.ordnancesurvey.co.uk/osmapapi/img_versions/img_1.1.0/OS/images/markers/marker-cross-med-blue.png', this.size, this.offset, null, this.infoWindowAnchor);
		this.marker = this.osMapF.createMarker(mapPoint, this.icon, null, null);
		if(center == true)
			this.osMapF.setCenter(mapPoint, this.zoomVal);
	}
	
	this.changemarker = function() {
		if (this.marker != null) 
			this.osMapF.removeMarker(this.marker);
		
	    var gridProjection = new OpenSpace.GridProjection();
	    
	    var lonlat = new OpenLayers.LonLat();
	    lonlat.lon = this.getLon();
	    lonlat.lat = this.getLat();
	    
	    var pt = gridProjection.getMapPointFromLonLat(lonlat);
		pos = new OpenSpace.MapPoint(pt.lon,pt.lat);
		this.setMarker(pos, true);
	    //Load the geoname of the location
		var query = "http://api.geonames.org/findNearbyJSON?lat="+lonlat.lat+"&lng="+lonlat.lon+"&username=alan";
		$.get(query, function(data) {
				//Trim the data.
				var data = data.replace(/^\s+|\s+$/g, '') ;				
				var json =  eval('(' + data + ')');
				$("#" +this.div_id + "_locationGeoname").val(json.geonames[0].name);				
		});	    
	}
	
	//Start of Functions required for postcode/gazetteer searches
	//clear search box when clicked on	
	this.clearTextF = function(event) {
		$("#"+this.div_id+"  #searchArea").val("");	
		
		if (!event) var event = window.event;
		event.cancelBubble = true;
		if (event.stopPropagation) 
			event.stopPropagation();
	}
	
	this.searchPostcodeF = function(id) {
		//hide and clear list box
		$('#'+this.div_id+'  #selectGaz').css("display", "none"); 
		this.da = $("#"+this.div_id+"_map  #selectGaz").get(0);
		
		this.da.options.length = 0;
		this.locationFound = 0;
		this.eastValstr = "";
		
		//clear menu if already populated
		this.da.options.length = 0;
		this.sectorFlag = 0;
		
		var query = $("#"+this.div_id+"  #searchArea").get(0);
		$("#"+this.div_id+"  #"+id+"_locationName").val(query.value);
		this.inputStr = query.value;
		//document.getElementById("markersCheckBox").checked = false;
		$("#"+this.div_id+" #searchArea").val("enter a place/postcode");
		
		//ascertain if postcode sector or full postcode
		if (this.inputStr.length < 5)
		{
			sectorFlag = 1;
		}
		
		inputStr = this.inputStr;
		activeMap = this.div_id;
		//search postcode service
		var res = this.postcodeServiceF.getLonLat(this.inputStr, onResultF);
		return;
	}
	
	
	
	//zoom to item selected from list box
	this.zoomGazSelF = function(selObj) {
		this.osMapF.setCenter(this.globalGazArray[selObj.selectedIndex-1].location, 7);  
		
		var gridProjection = new OpenSpace.GridProjection();
		
		var lonlat = gridProjection.getLonLatFromMapPoint(this.globalGazArray[selObj.selectedIndex-1].location);
		this.setLon(lonlat.lon);
		this.setLat(lonlat.lat);
		
		if (this.marker != null) 
			this.osMapF.removeMarker(this.marker);
		
		this.setMarker(this.globalGazArray[selObj.selectedIndex-1].location,false);
		
		//hide list box
		$("#"+this.div_id+" #selectGaz").css("display", "none"); 
		this.changemarker();
		//clear text field
		$("#"+this.div_id+" #searchArea").val("enter a place/postcode");
	}
	
	//End of Functions required for postcode/gazetteer searches
	this.initmapbuilderNew = function(id, index) {
		
		this.div_id = id;
		this.index = index;
		//Creating the Openspace map and the postcode service
		this.osMapF = new OpenSpace.Map(this.div_id+'_map');
		this.postcodeServiceF = new OpenSpace.Postcode();
		
		//Adding the map overview
		this.mapOVF = new OpenSpace.Control.OverviewMap();
		this.osMapF.addControl(this.mapOVF);
		//fix to put copyright on top of overview map: Needs api fix for later version
		var ccControl = this.osMapF.getControlsByClass("OpenSpace.Control.CopyrightCollection")
		this.osMapF.removeControl(ccControl[0]);
		ccControl = new OpenSpace.Control.CopyrightCollection();
		this.osMapF.addControl(ccControl);
		ccControl.activate();
		//end of fix
		//Turning the overview map off
		this.mapOVF.minimizeControl();
		
		//Adding the postcode/gazetteer search box
		//define an overlay for search box
		this.searchBox = new OpenSpace.Layer.ScreenOverlay("search");
		//set its position
		this.searchBox.setPosition(new OpenLayers.Pixel(80, 0));
		//and add to the map
		this.osMapF.addLayer(this.searchBox);
		this.searchBox.setHTML("<div id=\"OpenSpace.Layer.ScreenOverlay_132\" style=\"position: absolute; width: 200px; height: 100%; z-index: 9999; left: 0px; top: 3px;\" class=\"olLayerDiv\">" + 
		"<div id=\"div1\" style=\"z-index:9999; padding-left: 0px; font-size: 14px;\">" + 
		"<form name=\"searchForm\" onsubmit=\"return false;\">" + 
		"<input type=\"text\" name=\"searchArea\" id=\"searchArea\" onclick=\"maps['"+id+"'].clearTextF(event)\" value=\"enter a place/postcode\"/><input type=\"button\" onclick=\"maps['"+id+"'].searchPostcodeF('"+id+"');return false;\" value=\"Find\" title=\"find place by postcode or 1:50,000 gazetteer\"></button>" + 
		"<select name=\"select\" id=\"selectGaz\" onchange=\"maps['"+id+"'].zoomGazSelF(this.form.select)\" style=\"display: block\">" + 
		"<option>Select a place</option><option></option></select></form></div></div>");
		//hide list box select
		$("#"+this.div_id+" #selectGaz").css("display", "none"); 
		this.searchBox.events.register("mouseover", this.searchBox, function(e){
			//de-activate keyboard and navigation controls
			//Crazy, but works. Finding the top div element and its id.
			var id = this.div.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.id;			
			maps[id].osMapF.controls[0].deactivate();
			maps[id].osMapF.controls[1].deactivate();
		});
		this.searchBox.events.register("mouseout", this.searchBox, function(e){
			//activate keyboard and navigation controls
			//Crazy, but works. Finding the top div element and its id.
			var id = this.div.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.id;
			maps[id].osMapF.controls[0].activate();
			maps[id].osMapF.controls[1].activate();	
		});	
	
		//Defining the center of the map and the zoom level
		this.osMapF.setCenter(new OpenSpace.MapPoint(300000,200000),7);
	} // End of initmapbuilder.

}
/**Variable containing all the maps on the current page*/
var maps = [];