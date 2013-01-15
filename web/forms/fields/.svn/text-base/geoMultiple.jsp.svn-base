<%@ page language="java" import="common.*, java.net.URLDecoder" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>

<%
String label = "";
String id = "";
String index = "";
String geonames = "true";

if(request.getParameter("label") != null) {
	label = request.getParameter("label");
}
if(request.getParameter("mapIndex") != null) {
	index = request.getParameter("mapIndex");
}
if(request.getParameter("geonames") != null) {
	geonames = request.getParameter("geonames");
}
if(request.getParameter("id") != null) {
	id = request.getParameter("id");
	id = URLDecoder.decode(id, "UTF-8");
}
String nameUpload = "";
if(request.getParameter("nameUpload") != null && request.getParameter("nameUpload").length()>0) {
	nameUpload = request.getParameter("nameUpload");
}
//Add the nameUpload to the label, in case there is more upload windows opened.
String labelId = nameUpload + label;

String remove = "true";
remove = request.getParameter("remove");
String display = "none";
if("true".equals(remove)) {
	display = "block";
}

String labelText = label;
//Transform uppercase to lowercase and a space
labelText = labelText.replaceAll("([A-Z])"," $1");
labelText = labelText.toLowerCase();
//Make first letter uppercase
labelText = labelText.replaceFirst(""+labelText.charAt(0), ""+Character.toUpperCase(labelText.charAt(0)));

%><jsp:include page="header.jsp" flush="false">
		<jsp:param value="<%=label %>" name="label"/>
		<jsp:param value="<%=id %>" name="id"/>
		<jsp:param value="<%=nameUpload %>" name="nameUpload"/>
</jsp:include>	
	<a href="#" onclick="$('#<%=labelId %>content').fadeToggle('fast', 'linear');return false;" style="float:right;margin-right: 10px;"><h4>Show/hide</h4></a>
	<div id="<%=labelId %>content">
    
		 <div style="position: relative; float:left; width: 380px; padding:10px;">
		 	<div id="<%=labelId %>_map" style="border: 1px solid black; position: relative; float: left; width:380px; height:300px;"></div>
		 </div>
		    
		 <div style="position: relative; float: left; width:100%;">	    
		    		<div style="position:relative; float: left; width:100%; margin-bottom:10px;">
						<div style="width:190px; position:relative; float:left;"><%= label %><span style="color:red;"> *</span></div>				 
				 		<input style="border:1px solid #006699; width:300px; height: 20px; font-size:14px;" onkeydown="unlock()" onchange="changeValue('<%=label %>', this)" value="Name of location.." type="text" id="<%=labelId %>_locationName"/>
				 		<%
				 		if(!geonames.equals("")&&!geonames.equals("false")){%>
				 			<input style="border:1px solid #006699; width:300px; height: 20px; font-size:14px;" id="<%=labelId %>_locationGeoname" name="<%=id%>_locationGeoname" disabled="disabled"></input>
					 		<%
				 		}
				 		%>
				</div>
		    <div style="position:relative; float:right;">
				        <input id="<%=labelId %>_l1" name="<%=id%>_l1" style="position: relative; float: left; border:1px solid #006699; margin-right:10px; background-color:#c2c2c2; font-size:10px; height:14px; width:80px;" onclick="showUpdate('<%=label %>_updateLoc')" onkeydown="unlock()" onchange="activeDialog.changeValue('<%=label %>', this)" value="Longitude" type="text"/>
				        <input id="<%=labelId %>_l2" name="<%=id%>_l2" style="position: relative; float: left; border:1px solid #006699; margin-right:10px; background-color:#c2c2c2; font-size:10px; height:14px; width:80px;" onclick="showUpdate('<%=label %>_updateLoc')" onkeydown="unlock()" onchange="activeDialog.changeValue('<%=label %>', this)" value="Latitude" type="text"/>
				        <div id="<%=labelId %>_updateLoc" style="position: relative; float: left; display:none;"><a href="#" onclick="changemarker(); return false;">[Update]</a></div>
				        <div style="display:none;" style="position: relative; float: left;" id="<%=labelId %>_deleteLoc"><a href="#" onclick="removeNewDiv('<%=label %>'); return false">[Delete field]</a></div>
				</div>
		</div>
		</div>
<jsp:include page="footer.jsp" flush="false">
		<jsp:param value="<%=label %>" name="label"/>
		<jsp:param value="<%=id %>" name="id"/>
		<jsp:param value="<%=nameUpload %>" name="nameUpload"/>
</jsp:include>	