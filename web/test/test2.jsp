
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd"><html><head>
<title>OpenSpace Tutorial - Example 15</title>
<script type="text/javascript" src="http://openspace.ordnancesurvey.co.uk/osmapapi/openspace.js?key=7CCF1E7712317B1BE0405F0AF1604FF3"></script>
<script type="text/javascript">
 var osMap, markersLayer, clusterControl, dragControl;
 function init(){
    osMap = new OpenSpace.Map('map');
    osMap.setCenter(new OpenSpace.MapPoint(300000, 200000), 1);
    osMap.createMarker(new OpenSpace.MapPoint(20000, 15000));
    osMap.createMarker(new OpenSpace.MapPoint(43000, 26000));
    osMap.createMarker(new OpenSpace.MapPoint(30000, 17000));
    osMap.createMarker(new OpenSpace.MapPoint(31000, 22000));
    osMap.createMarker(new OpenSpace.MapPoint(34000, 22000));
    osMap.createMarker(new OpenSpace.MapPoint(100000, 25200));
    osMap.createMarker(new OpenSpace.MapPoint(314758.86,391221.15));
    osMap.createMarker(new OpenSpace.MapPoint(432833.86,384671.15));
    osMap.createMarker(new OpenSpace.MapPoint(434283.86,385096.15));
    osMap.createMarker(new OpenSpace.MapPoint(445108.86,392871.15));
    osMap.createMarker(new OpenSpace.MapPoint(435733.00,387546.15));
    osMap.createMarker(new OpenSpace.MapPoint(345908.4,352278.3));
    osMap.createMarker(new OpenSpace.MapPoint(341084.58,345753.78));
    osMap.createMarker(new OpenSpace.MapPoint(345815.63,341672.08));
    osMap.createMarker(new OpenSpace.MapPoint(361214.73,339662.15));
    osMap.createMarker(new OpenSpace.MapPoint(361091.05,351072.35));
    osMap.createMarker(new OpenSpace.MapPoint(411091.56,423460.42));
    osMap.createMarker(new OpenSpace.MapPoint(356669.14,251782.05));
    osMap.createMarker(new OpenSpace.MapPoint(288393.71,236939.5));
    osMap.createMarker(new OpenSpace.MapPoint(297793.87,292846.37));
    osMap.createMarker(new OpenSpace.MapPoint(302741.43,296804.36));
    osMap.createMarker(new OpenSpace.MapPoint(300762.35,284435.58));
    osMap.createMarker(new OpenSpace.MapPoint(293835.97,297299.1));
    osMap.createMarker(new OpenSpace.MapPoint(411586.61,171137.65));
    osMap.createMarker(new OpenSpace.MapPoint(364585.28,146894.85));
    osMap.createMarker(new OpenSpace.MapPoint(351721.66,231002.52));
    osMap.createMarker(new OpenSpace.MapPoint(406144.34,231991.99));
    osMap.createMarker(new OpenSpace.MapPoint(333416.04,220117.95));
    osMap.createMarker(new OpenSpace.MapPoint(334900.16,225065.49));
    osMap.createMarker(new OpenSpace.MapPoint(340837.13,222591.72));
    osMap.createMarker(new OpenSpace.MapPoint(336384.53,221602.25));
    osMap.createMarker(new OpenSpace.MapPoint(416534.18,341331.89));
    osMap.createMarker(new OpenSpace.MapPoint(426429.04,323026.09));
    osMap.createMarker(new OpenSpace.MapPoint(600000,100000));
    osMap.createMarker(new OpenSpace.MapPoint(609000,115000));
    clusterControl = new OpenSpace.Control.ClusterManager();
    osMap.addControl(clusterControl);
    clusterControl.activate();
    markersLayer = osMap.getMarkerLayer();
    dragControl = new OpenSpace.Control.DragMarkers(markersLayer);
    osMap.addControl(dragControl);
    clusterControl.deactivate();
    osMap.createMarker(new OpenSpace.MapPoint(437300,113300));
    clusterControl = new OpenSpace.Control.ClusterManager();
    osMap.addControl(clusterControl);
    clusterControl.activate();
 }
 function dragStart()
 {
  dragControl.activate();
  markersLayer.setDragMode(true);
 }
  function dragStop()
 {
  markersLayer.setDragMode(false);
  dragControl.deactivate();
  osMap.zoomIn(0);
  osMap.zoomOut(0);
 }
</script>
</head><body onload="init()">
<form name="form1" method="post" action="">
  <input type="button" name="Submit2" onClick="dragStart()" return="false" value="start drag markers">
  <input type="button" name="Button" onClick="dragStop()" return="false" value="stop drag markers">
</form>
<div id="map" style="width: 750px; height: 420px; border: 1px solid black;"></div>
</body></html>
		
