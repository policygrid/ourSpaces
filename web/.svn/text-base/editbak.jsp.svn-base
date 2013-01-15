<%@page import="org.policygrid.ontologies.AcademicDiscipline"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="org.policygrid.ontologies.OurSpacesVRE"%>
<%@page import="org.policygrid.ontologies.FOAF"%>
<%@ page language="java" import="java.util.*,java.io.*,java.net.*,java.util.Vector,common.*,com.hp.hpl.jena.*,com.hp.hpl.jena.rdf.model.ModelFactory,com.hp.hpl.jena.ontology.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="user" class="common.User" />
<%
	String userID = Integer.toString(user.getID());
%>


<script language=javascript type=text/javascript>
<!-- Script courtesy of http://www.web-source.net - Your Guide to Professional Web Site Design and Development-->
function stopRKey(evt) {
   var evt = (evt) ? evt : ((event) ? event : null);
   var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
   if ((evt.keyCode == 13) && (node.type=="text")) {return false;}
}

document.onkeypress = stopRKey;

</script>
<jsp:include page="top_head.jsp" />
<jsp:include page="top_tail.jsp" />


<div class="homeStatusContainer" style="color:#FFFFFF; position:relative;" >
	<div id="columns" style="position: relative;">
	
	<ul id="column1" class="column" style="width: 440px;">  
		<li class="widget color-orange" id="widget1">
  			<div class="widget-head">
				<h3 class="style3">Edit Detail</h3>
			</div>
		
    		<div class="widget-content">
					<jsp:include page="boxes/profile/details.jsp" flush="false"/>
        	</div> <!-- end of Widget1 content -->        	
        </li>
    </ul>
    
    <ul id="column2" class="column" style="width: 440px;">
	<li class="widget color-orange" id="widget3">  
        <div class="widget-head">
            <h3>Change Profile Picture</h3>
        </div>
        <div class="widget-content">
          <div align="center" style="position:relative; float:left; width:400px; padding:10px">  
            <form ENCTYPE="multipart/form-data" method="post" action="Controller?action=upload">
            	<!--  <input type="hidden" name="userid" id="userid" value="<%=userID%>" /> -->
            	<input type="file" class="fg-button ui-state-active fg-button-icon-right ui-corner-all" name="upload" />
            	<div style="padding:5px"> (50 KB max)</div>
                <input type="submit" class="fg-button ui-state-active fg-button-icon-right ui-corner-all" value="Upload Photo" />
            </form>
         </div>
        </div>
    </li>
    	<li class="widget color-orange" id="widget2">  
            <div class="widget-head">
                <h3>Notifications</h3>
            </div>
            <div class="widget-content">
          		<jsp:include page="boxes/profile/notifications.jsp" flush="false"/>
            </div>
        </li> <!--  end of Widget2 -->
</ul>

</div> <!-- end of columns -->
</div> <!-- end of home status container -->
<jsp:include page="bottom.jsp" />