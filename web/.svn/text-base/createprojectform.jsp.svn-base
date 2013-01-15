<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />

<script type="text/javascript" src="/ourspaces/javascript/top.js"></script>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<%
Random r = new Random();
int d = r.nextInt();

%>

<script language="javascript" type="text/javascript">
  
$(function(){
 	$("button#addAim").button();
	$("button#addAim").click(function() {
		$("div#aims").append("<p><input type=\"text\" style=\"width:300px; height:17px; padding-top:3px; margin-right:5px; border: 1px solid #CCC;\" name=\"aim\" /></p>");
		return false;
	});
});

function stopRKey(evt) {
   var evt = (evt) ? evt : ((event) ? event : null);
   var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
   if ((evt.keyCode == 13) && (node.type=="text")) {return false;}
}

document.onkeypress = stopRKey;

</script>


<p style="color:#900; margin-bottom:20px; margin: 0 auto;" align="center">Within ourSpaces, you can create virtual projects enabling you to better collaborate with other users.</p>

<div style="position:relative; margin:20px; padding:10px;  width: 600px;">

<form class="requiredInputs" method="post" id="target" action="Controller?action=createProject">
	<div style="position:relative; float:left; width: 550px; margin-top:10px; margin-bottom:5px;">
        <div style="position:relative; float:left; width:150px;">
            Project Title*:
        </div>
        <div style="position:relative; float:left;">
            <input class="required" type="text" name="title" style="width:300px; height:17px; padding-top:3px; border: 1px solid #036;" />
        </div>
    </div>
    
    <div style="position:relative; float:left; width: 550px; margin-bottom:5px;">
        
        <div style="position:relative; float:left; width:150px;">
            Project Subtitle:
        </div>
        <div style="position:relative; float:left;">
           <input type="text" name="subtitle" style="width:300px; height:17px; padding-top:3px; border: 1px solid #036;" />
        </div>
    </div>
    
     <div style="position:relative; float:left; width: 550px; margin-bottom:5px;">
        
        <div style="position:relative; float:left; width:150px;">
            Project Start-date*:
        </div>
        <div style="position:relative; float:left;">
           <input class="required" type="text" maxlength="2" name="startday" style="width:17px; height:17px; padding-top:3px; border: 1px solid #036;" />
           <input class="required" type="text" maxlength="2" name="startmonth" style="width:17px; height:17px; padding-top:3px; border: 1px solid #036;" />
           <input class="required" type="text" maxlength="4" name="startyear" style="width:30px; height:17px; padding-top:3px; border: 1px solid #036;" />
    		<span>[dd mm yyyy]</span>
        </div>
    </div>
    
     <div style="position:relative; float:left; width: 550px; margin-bottom:5px;">
        
        <div style="position:relative; float:left; width:150px;">
            Project End-date*:
        </div>
        <div style="position:relative; float:left;">
           <input class="required" type="text" maxlength="2" name="endday" style="width:17px; height:17px; padding-top:3px; border: 1px solid #036;" />
           <input class="required" type="text" maxlength="2" name="endmonth" style="width:17px; height:17px; padding-top:3px; border: 1px solid #036;" />
           <input class="required" type="text" maxlength="4" name="endyear" style="width:30px; height:17px; padding-top:3px; border: 1px solid #036;" />
    		<span>[dd mm yyyy]</span>
        </div>
    </div>
    
    
    <div style="position:relative; float:left; width: 550px; margin-bottom:5px;">
        
        <div style="position:relative; float:left; width:150px;">
            &nbsp;
        </div>
        <div style="position:relative; width:450px;">
           <p align="center" style="padding:0; margin:0;">*</p>
        </div>
        
    </div>
    
    
    <div style="position:relative; float:left; width: 550px; margin-bottom:10px;">
        
        <div style="position:relative; float:left; width:150px;">
            Project Aims: <br />
<!--             <a href="#" style="font-size:9px;" onclick="createDiv('aims','aims'); return false;">[Add another aim]</a> -->
        </div>
        <div style="position:relative; float:left;" id="aims">
           <input class="required" type="text" name="aim" style="width:300px; height:17px; padding-top:3px; margin-right:5px; border: 1px solid #CCC;" />
        </div>
    </div>
    <div style="padding-top:10px">
		<button id="addAim">Add Aim</button>
	</div>
    
    
    <div style="position:relative; float:left; width: 550px; margin-bottom:5px;">
        
        <div style="position:relative; float:left; width:150px;">
            &nbsp;
        </div>
        <div style="position:relative; width:450px;">
           <p align="center" style="padding:0; margin:0;">*</p>
        </div>
        
    </div>
    
    
    
    <div style="position:relative; float:left; width: 550px; margin-bottom:5px;">
     
     	<div style="position:relative; float:left; width:150px;">
            Project Members*:<br />
        </div>
        
        <div id="members" style="position:relative; width:400px; float:left;">
        		<jsp:include page="project/addProjectMemberField.jsp" />      
        </div><!-- end of members -->
 	</div>
    
   
    <!-- <input id="submit" type="image" src="images/create.png" style="margin-left:200px; margin-top:15px;" /> -->
            
</form>

</div>