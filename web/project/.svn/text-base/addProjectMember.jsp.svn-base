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
Random r = new Random();
int d = r.nextInt();
String mainProjectID = org.policygrid.ontologies.Project.NS + (String) request.getParameter("id").toString();


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

	function lock()
	{
		document.getElementById('submit').disabled = true;
	}

	function unlock()
	{
		document.getElementById('submit').disabled = false;
	}

	

    function checkForm(e, sid,namespace,resourceName, random) {
    	if (e.keyCode == 13) {
    		searchResource(sid,document.getElementById(sid+'in').value,namespace,resourceName, random);
    	}
    	return true;
    }

	
	
</script>

<!-- <p style="color:#999; font-size:24px; margin: 0 auto;" align="center">Add Member</p> -->

<!-- <div style="position:relative; margin:20px; padding:15px; border:1px solid #CCC; width: 600px;"> -->

<p style="font-size:16px; padding-top:10px; padding-bottom:10px;" align="center">Add other people to the project.</p>

<form method="post" id="target" action="Controller?action=addProjectMember">
	
    <div style="position:relative; float:left; width: 600px; margin-bottom:5px;">
     
     	<div style="position:relative; float:left; width:150px;">
            Search for Members:<br />
            
    
        
             <!-- <a href="#" style="font-size:9px;" onclick="createDiv('members'); return false;">[Add another member]</a> -->
        </div>
        
        <div id="member" style="position:relative; width:450px; float:left;">
        
        	<jsp:include page="addProjectMemberField.jsp">
            	<jsp:param name="role" value="true" />
        	</jsp:include>            
    
        </div>
        <a href="#" onclick="showForm('membersinput', true)">Create new member</a>
        <div id="membersinputsea"></div>
        <!-- end of members -->
 	</div>
    
    <input type="hidden" name="projectOf" value="<%=mainProjectID %>"></input>
    
<!--     <input type="submit" id="submit" value="Confirm"></input> -->
            
</form>

</div>