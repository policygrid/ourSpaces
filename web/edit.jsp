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

<jsp:include page="/top_head.jsp" />
<jsp:include page="/top_tail.jsp" />

<script language=javascript type=text/javascript>
<!-- Script courtesy of http://www.web-source.net - Your Guide to Professional Web Site Design and Development-->
function stopRKey(evt) {
   var evt = (evt) ? evt : ((event) ? event : null);
   var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
   if ((evt.keyCode == 13) && (node.type=="text")) {return false;}
}

document.onkeypress = stopRKey;

$(document).ready(function() {
	$('#btnSchedule').click(function(){
		var personList = "'";// recipents list
		$(".person").each(function (i) {

			personList = personList + this.id.split('#')[1] + "','";
		 });
		
		var projectList = "'";// recipents list
		$(".project").each(function (i) {

			projectList = projectList +  this.id.split('#')[1] + "','";
		 });
		
		$.ajax({
			type: 'GET',
			url: "/ourspaces/plsservlet?type=schedule"+'&personList='+personList+'&projectList='+projectList , 
			dataType : "html",
			async : false,
			success : function(data, errorThrown) {
				$( "#dialog" ).dialog({ buttons: { "Ok": function() { $(this).dialog("close"); } } });
				$('.project').remove();
				$('.person').remove();
			}
		});
		
		
	});
	
	//addPersonAutocomplete('MyNameinput',false);
	//'http://xmlns.com/foaf/0.1/Person'
	
	addAutocomplete('MyNameinput', 'http://xmlns.com/foaf/0.1/Person', /*select */function(e, ui) { 
			//Create formatted resource  
			var resource = ui.item.label;  
			var span = $("<span>").text(resource);
			span.attr("class","person");  
	        span.addClass("value"); 
			span.attr("id", ui.item.id);  
			var a = $("<a>").addClass("remove").attr({  
		        		href: "javascript:",  
		        		title: "Remove " + resource  
	              	}).text("x").appendTo(span); 
	              //Add the resource to the list.  
	              span.insertBefore('#MyNameinput'); 
	              ui.item.value="";
	}
   );
	
	
	addAutocomplete('MyProjinput', 'http://www.policygrid.org/project.owl#Project', /*select */function(e, ui) { 
			//Create formatted resource  
			var resource = ui.item.label;  
			var span = $("<span>").text(resource);
			span.attr("class","project");  
	        span.addClass("value"); 
			span.attr("id", ui.item.id);  
			var a = $("<a>").addClass("remove").attr({  
		        		href: "javascript:",  
		        		title: "Remove " + resource  
	              	}).text("x").appendTo(span); 
	              //Add the resource to the list.  
	              span.insertBefore('#MyProjinput'); 
	              ui.item.value="";
	}
   );
  	//add live handler for clicks on remove links  
  	$(".remove", document.getElementById('#MyProjinputDiv')).live("click", function(){  	    	  
  	//remove current friend  
  	$(this).parent().remove();  	      
  		//correct 'to' field position  
  		if($("#"+property+"inputDiv span").length === 0) {  
  		    $("#"+property+"input").css("top", 0);  
  		}  
  	});
  	
  	
});

</script>

<div id="dialog" title="Basic dialog" style="display:none">
	<p>Your request for Personal Lexicon update has been scheduled. You will receive a notification once the process is completed.</p>
</div>
<div class="homeStatusContainer" style="color:#FFFFFF; position:relative;" >
	<div id="columns" style="position: relative;">
	
	<ul id="column1" class="column" style="width: 440px;">  
		<li class="widget color-orange" id="widget1">
  			<div class="widget-head">
				<h3 class="style3">Edit Detail</h3>
			</div>
		
    		<div class="widget-content">
					<jsp:include page="/boxes/profile/details.jsp" flush="false"/>
        	</div> <!-- end of Widget1 content -->        	
        </li>
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
    </ul>
    
    <ul id="column2" class="column" style="width: 440px;">
	
    	<li class="widget color-orange" id="widget2">  
            <div class="widget-head">
                <h3>Email Notifications</h3>
            </div>
            <div class="widget-content">
          		<jsp:include page="/boxes/profile/notifications.jsp" flush="false"/>
            </div>
        </li> <!--  end of Widget2 -->
        <li class="widget color-orange" id="widget4">  
            <div class="widget-head">
                <h3>Personal Lexicon Service</h3>
            </div>
            <div class="widget-content" style="padding:5px">
          		<jsp:include page="/Lexicon/notifications.jsp"  flush="false"/>
          		<hr/>
          	<div style="padding-top:5px">Projects to be mapped with my personal lexicon</div>
            <div id="MyProjectinputDiv" class="resourceInputDiv" style="float:left;width:380px;margin-bottom:10px">
				<input id="MyProjinput" class="resourceInput" style="border: none;width:180px" id="MyProj" name="MyProj" size="40" type="text"/>
			</div>
            <div style="margin-top:10px">People to be mapped with my personal lexicon</div>
            <div id="MyNameinputDiv" class="resourceInputDiv" style="float:left;width:380px;margin-bottom:5px">
				<input id="MyNameinput" class="resourceInput" style="border: none;width:180px" id="MyName" name="MyName" size="40" type="text"/>
			</div>
			 <a id ="btnSchedule" class="fg-button ui-state-active fg-button-icon-left ui-corner-all"  >Schedule Lexicon Generation</a>
            </div>
            
        </li> <!--  end of Widget2 -->
</ul>

</div> <!-- end of columns -->
</div> <!-- end of home status container -->
<jsp:include page="/bottom.jsp" />