<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*, org.policygrid.ontologies.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<jsp:include page="/top_head.jsp" />
<div class="navBarLeft" style="z-index; -1;" >
           <div class="dropdownBox">
              <a class="navBarLeft" href="#" style="float: left; margin-right:5px;"><img src="/ourspaces/images/spaces/Data_small.png" align="left" border="0"/>Data Space</a> <img src="/ourspaces/icons/dropdown.png" align="right" border="0"/>
              <div class="navBarOptions ui-corner-all" style="z-index; -1; float:left; width:220px;">
			      <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
			              <a class="navBarLeft" href="#" onclick="showUploadDialog({baseClass : '<%= ProvenanceGeneric.Data.getURI() %>'});" style="float:left; margin-right:5px;"><img src="/ourspaces/images/spaces/Data_small.png" align="left" border="0"/>Upload New Data</a> 
			      </div>
		      </div>
           </div>  
           <div class="separator">
              <img src="/ourspaces/icons/separator.png" align="right" style="margin: 0px;" border="0"/> 
           </div>     
       </div>  
<jsp:include page="/top_tail.jsp" />


  <style type="text/css">
<!--
.style3 {
	font-size: 16px;
	color: #006699;
}
.style4 {font-size: 16px;color: #CC0000}
.style5 {
	font-size: 16px;
	color: #FF6600;
	padding-bottom:10px;
}
-->
  </style>

<div class="homeStatusContainer" style="color:#FFFFFF; position:relative;" >
	<div id="columns" style="position: relative;">
	
	<ul id="column1" class="column" style="width: 600px;">  
		<li class="widget color-orange" id="widget1">
  			<div class="widget-head">
				<h3 class="style3">My Data</h3>
			</div>
		
    		<div class="widget-content">
				<jsp:include page="/boxes/resourcesByTypeList.jsp" flush="false">
				      <jsp:param value="<%= ProvenanceGeneric.Data.getURI() %>" name="type" />
				      <jsp:param value="true" name="userOnly" />
				      <jsp:param value="myDatadiv" name="divId" />
				</jsp:include>
        	</div> <!-- end of Widget1 content -->        	
        </li>
        
		<li class="widget color-orange" id="widget2">
  			<div class="widget-head">
				<h3 class="style3">All Data</h3>
			</div>
		
    		<div class="widget-content">
				<jsp:include page="/boxes/resourcesByTypeList.jsp" flush="false">
				      <jsp:param value="<%= ProvenanceGeneric.Data.getURI() %>" name="type" />
				      <jsp:param value="false" name="userOnly" />
				      <jsp:param value="allDatadiv" name="divId" />
				      <jsp:param value="10" name="limit"/>
				</jsp:include>
        	</div> <!-- end of Widget2 content -->        	
        </li>
    </ul>
    
    <ul id="column2" class="column" style="width: 300px;">
	<li class="widget color-orange" id="widget3">  
        <div class="widget-head">
            <h3>Data Tag</h3>
        </div>
        <div class="widget-content">
            <jsp:include page="../boxes/tagsByType.jsp" flush="false">
            	<jsp:param name="type" value="<%=ProvenanceGeneric.Data.getURI() %>"/>
            </jsp:include>
        </div>
    </li>
</ul>

</div> <!-- end of columns -->
</div> <!-- end of home status container -->
  
 
<jsp:include page="/bottom.jsp" />