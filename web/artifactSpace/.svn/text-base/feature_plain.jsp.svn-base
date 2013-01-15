<%@ page contentType="text/html; charset=utf-8" language="java" import="java.util.regex.*, java.sql.*, common.*, java.util.Random, java.util.Vector" errorPage="" %>


	<%

ParameterHelper parHelp = new ParameterHelper(request, session);
	String showControls = (String)parHelp.getParameter("controls", false); 
    String dvid = (String)parHelp.getParameter("id", null);
    String width = (String)parHelp.getParameter("width", "580px");
    String height = (String)parHelp.getParameter("height", "300px");
    String prop = (String)parHelp.getParameter("label", "");
	String separatedClassName = Utility.splitString(prop);
    %>

<style type="text/css">
#columns .widget .widget-content img {
	margin : 0px;
	border : 0px;
}
</style>
<div style="position:relative; float:left; width:100%; border:0px dashed #666;" id="<%=dvid %>_mapContainer">

    <div style="position: relative; float:left; width:<%=width%>;">
    	<div id="<%=dvid %>_map" style="border: 0px solid black; position: relative; float: left; width:100%; height:<%=height%>;"></div>
    </div>
    <%//Hide controls if necessary. 
      if("true".equals(showControls)){ %>
    	<div style="position: relative; float: left; width:100%;">     
	<%} else {%>
     	<div style="position: relative; float: left; width:100%;display:none;">
	<%} %>
    		 <div style="position:relative; float: left; width:100%; margin-bottom:10px;">
				<div style="width:190px; position:relative; float:left;"><%= separatedClassName %><span style="color:red;"> *</span></div>				 
		 		<input style="border:0px solid #006699; width:300px; height: 20px; font-size:14px;" onkeydown="unlock()" value="Name of location.." type="text" id="<%=dvid%>_locationName"/>
		 	</div> 
    
    		<div style="position:relative; float:left; width:100%; padding-left:200px; ">
		        <input id="<%=dvid %>_l1" style="position: relative; float: left; border:0px solid #006699; margin-right:10px; background-color:#c2c2c2; font-size:10px; height:14px; width:80px;" onclick="showUpdate('<%=dvid %>_updateLoc')" onkeydown="unlock()" value="Longitude" type="text"/>
		        <input id="<%=dvid %>_l2" style="position: relative; float: left; border:0px solid #006699; margin-right:10px; background-color:#c2c2c2; font-size:10px; height:14px; width:80px;" onclick="showUpdate('<%=dvid %>_updateLoc')" onkeydown="unlock()" value="Latitude" type="text"/>
		        <div id="<%=dvid %>_updateLoc" style="position: relative; float: left; display:none;"><a href="#" onclick="changemarker(); return false;">[Update]</a></div>
		        <div style="display:none;" style="position: relative; float: left;" id="<%=dvid %>_deleteLoc"><a href="#" onclick="removeNewDiv('<%=dvid %>'); return false">[Delete field]</a></div>
		    </div>
    	
	    
	</div> 	
</div>