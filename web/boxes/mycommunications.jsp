<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="blog" class="common.Blog" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<script type="text/javascript">
$(document).ready(function(){
	
	$(".comlink").click(function(e) {
		var currentId = $(this).attr('id');
		var title = $(this).text();
		showCommDialog(currentId,title);
   
	
  });
	
});






</script>

<%if (session.isNew()==true){ %>
<jsp:forward page="error.jsp" />
<% } %>

<% 
if( session.getAttribute("use") == null){ %>
<jsp:forward page="error.jsp" />
<% }

User user = (User) session.getAttribute("use");


int id = user.getID();

Vector myComres = user.getResourcesByType(id,"http://www.policygrid.org/provenance-generic.owl#Communication",0,5);

%>

   
    <div class="widget-top" >
      <div class="wlink"  align="right" style="float:right;  margin-left: 5px; margin-right: 10px; margin-bottom: 0px; padding: 10px;">
         <a class="popup_comm_dialog" title="New Communication Artefact" href="#" onclick="showCommDialog();"><img src="/ourspaces/images/spaces/Communications_small.png" align="middle" style="float: left; display:inline-block; margin: -2px; border: 0px none;"/><span style="margin-left:8px;">Create New Communication Collection</span></a>     
      </div>
   </div>
   
   

<div style="float:left; padding:5px">	
	<table border="0" cellspacing="0" cellpadding="0" width="295px" style="margin:3px; padding:2px; margin-right:10px;">	
		 <%
		 for (int i=0;i<myComres.size();i++){
         	Resources res= (Resources) myComres.get(i);
        	String url = rdf.getResourceURL(res.getID());
        	String fileID = url.substring(url.lastIndexOf("/")+1);
        	common.Utility.log.debug("Res URL is:"+fileID);
		 					
		 %>
			<tr>
				<td width="16">
					<img src="/ourspaces/images/spaces/Communications_small.png" />
				</td>
				<td width="100%">
					<a href="#" class="comlink" id= <%=fileID %> ><%=res.title %></a>
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td align="right">
				  <span style="font-size:9px; color:#FF6600; margin-right: 10px;">
				  	<%=res.getDate()%>
			  	  </span>
				</td>
			</tr>
		<% 
			if(i == 4)
				break;	 
		 } %>
	</table>

</div>


