<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<%

String mainProjectID = org.policygrid.ontologies.Project.NS + (String) request.getParameter("id").toString();

Random r = new Random();
int d = r.nextInt();

%>

  <script language="javascript" type="text/javascript">
function stopRKey(evt) {
   var evt = (evt) ? evt : ((event) ? event : null);
   var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
   if ((evt.keyCode == 13) && (node.type=="text")) {return false;}
}

document.onkeypress = stopRKey;
</script>

<script language="javascript" type="text/javascript">

$(function(){
 	$("button#addAim").button();
	$("button#addAim").click(function() {
		$("div#aims").append("<p><input type=\"text\" style=\"width:300px; height:17px; padding-top:3px; margin-right:5px; border: 1px solid #CCC;\" name=\"aim\" /></p>");
		return false;
	});
});

	function lock()
	{
		document.getElementById('target').disabled = true;
	}
	
	function unlock()
	{
		document.getElementById('target').disabled = false;
	}

	function createDiv(type)
	{
		var formID=Math.floor(Math.random()*100000)
		var divTag = document.createElement("div");
		divTag.id = formID;
		divTag.style.position = 'relative';
		document.getElementById(type).appendChild(divTag);
		if(type == 'members') {
			divTag.innerHTML = tag('forms/jsp/projectmember.jsp?label='+formID, formID);;
		}
		else {
			divTag.innerHTML = "<input type=\"text\" name=\"aim\" style=\"width:300px; height:17px; padding-top:3px; margin-top:5px; margin-right:5px; border: 1px solid #CCC;\" />"
		}
		
	}
	
	function searchResource(place,keywords,namespace,resourceName, random)
    {	
		var fin = place + 'sea';
		document.getElementById(fin).style.display = 'block';
		document.getElementById(place + 'sc').style.display = 'block';
		document.getElementById(place + 'top').style.display = 'block';
    	tag('forms/jsp/queryProjectMember.jsp?sid='+place+'&namespace='+namespace+'&resourceName='+resourceName+'&keywords=' + keywords+'&random=' + random,fin);
    }
	
	function updateInput(place,value,text, random)
    {
		document.getElementById(place+'r').value = random + '_' + value;
		document.getElementById(place+'gah').style.display = 'block';
		document.getElementById(place+'l').style.display = 'block';
		document.getElementById(place+'role').style.display = 'block';
		
    	document.getElementById(place+'in').style.color = 'green';
		document.getElementById(place+'in').value = text;
    	hideResourceSearch(place);
    }
	
    /*
	{
    	document.getElementById(place+'r').value = value;
		document.getElementById(place+'gah').style.display = 'block';
		document.getElementById(place+'l').style.display = 'block';
    	document.getElementById(place+'l').innerHTML = text;
    	setDefaultValue(place+'in');
    	hideResourceSearch(place);
    }
	*/
	
	function clearValue(place)
    {
        document.getElementById(place).value = '';
    }
    
    function setDefaultValue(place)
    {
        document.getElementById(place).value = 'Search...';
    }


    function checkSearch(event, place){

        
    	document.getElementById(place+'l').innerHTML = e;
    	
    	characterCode = e.keyCode;
    	if(characterCode == 13){
    		searchResource(place,document.getElementById(place+'in').value);
    	}
    	
        return false;
    }

    function checkForm(e, sid,namespace,resourceName, random) {
    	if (e.keyCode == 13) {
    		searchResource(sid,document.getElementById(sid+'in').value,namespace,resourceName, random);
    	}
    	return true;
    }

    function showForm(sid, namespace, resourceName, random)
    {

    		tag('forms/add/projectMember.jsp?sid='+sid+'&random='+random, sid+'sea');
    	
    }
	
	function hideResourceSearch(resource)
	{
		fin = resource + 'sea';
		document.getElementById(fin).style.display = 'none';
		document.getElementById(resource + 'sc').style.display = 'none';
		document.getElementById(fin).innerHTML = '';
		document.getElementById(resource + 'top').style.display = 'none';
		
	}

	
	
</script>

<!-- <p style="color:#999; font-size:24px; margin: 0 auto;" align="center">Create a Sub-project</p> -->

<p style="color:#900; margin-bottom:20px; margin: 0 auto;" align="center">Create a sub-project to help organise your research.</p>

<div style="position:relative; margin:20px; padding:15px; width: 600px;">

<form method="post" id="target" action="Controller?action=createProject">
	<div style="position:relative; float:left; width: 550px; margin-top:10px; margin-bottom:5px;">
        <div style="position:relative; float:left; width:150px;">
            Project Title*:
        </div>
        <div style="position:relative; float:left;">
            <input class="required" type="text" name="title" style="width:300px; height:17px; padding-top:3px; border: 1px solid #036; position:relative; float:left;" />
        </div>
    </div>
    
    <div style="position:relative; float:left; width: 550px; margin-bottom:5px;">
        
        <div style="position:relative; float:left; width:150px;">
            Project Subtitle:
        </div>
        <div style="position:relative; float:left;">
           <input class="required" type="text" name="subtitle" style="width:300px; height:17px; padding-top:3px; border: 1px solid #036;" />
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
        <div style="position:relative; width:400px;">
           <p align="center" style="padding:0; margin:0;">*</p>
        </div>
        
    </div>
    
    
    
    <div style="position:relative; float:left; width: 550px; margin-bottom:5px;">
     
     	<div style="position:relative; float:left; width:150px;">
            Project Members*:<br />
      </div>
        
        <div id="members" style="position:relative; width:400px; float:left;">
        
        	<jsp:include page="addProjectMemberField.jsp"/>
            
    
        </div><!-- end of members -->
 	</div>
    
    <input type="hidden" name="projectOf" value="<%=mainProjectID %>"></input>
    
</form>
</div>
<script>
initClasses();
</script>