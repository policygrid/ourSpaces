<%@ page language="java" import="java.net.URLEncoder, java.util.Iterator, java.util.ArrayList, java.io.*, org.policygrid.ontologies.*, java.net.*, java.util.Vector, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="blog" class="common.Blog" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="access" class="common.AccessControl" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<%if (session.isNew()==true){ %>
<jsp:forward page="error.jsp" />
<% } %>

<% 
if( session.getAttribute("use") == null){ %>
<jsp:forward page="error.jsp" />
<% }

user = (User) session.getAttribute("use");

String resource1 = (String) request.getParameter("resource").toString();
String resourceID = "http://openprovenance.org/ontology#" + resource1;

String depositor = rdf.getResourceDepositor(resourceID);
String title = rdf.getResourceTitle(resourceID);
String url = rdf.getResourceURL(resourceID);
String lat = request.getParameter("lat").toString();
String lon = request.getParameter("lon").toString();
		
boolean view = false;
boolean edit = false;
boolean download = false;

if(depositor.equals(user.getRDFID()))
{
	view = true;
	edit = true;
	download = true;
}
else
{
	view =  AccessControl.checkPermission("view",user.getFOAFID(),resourceID);
	edit =  AccessControl.checkPermission("edit",user.getFOAFID(),resourceID);
	download =  AccessControl.checkPermission("download",user.getFOAFID(),resourceID);
}

boolean check = access.checkPublic(resourceID);

long time = rdf.getLong(rdf.getResourceTimestamp(resourceID));
java.util.Date date = new java.util.Date(time);
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
String fulldate = sdf.format(date);

ArrayList authors = rdf.getResourceAuthor(resourceID);

String types = rdf.getResourceType(resourceID);

if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));

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

function addComment()
{
  var user = document.getElementById('user').value;
  var content = document.getElementById('content').value;
  var about = document.getElementById('about').value;
  
	var xmlHttpRequest=init();

	  function init(){

	if (window.XMLHttpRequest) {
	           return new XMLHttpRequest();
	       } else if (window.ActiveXObject) {
	           
	           return new ActiveXObject("Microsoft.XMLHTTP");
	       }

	}
  	
	xmlHttpRequest.open("GET", "Comment?user="+user+"&content="+content+"&about="+about, true);
	xmlHttpRequest.onreadystatechange=function() {
		if(xmlHttpRequest.readyState==4){
	    	   if(xmlHttpRequest.status==200){

	    		   var xmlMessage=xmlHttpRequest.responseXML;

	    	    	var confirm=xmlMessage.getElementsByTagName("message")[0].firstChild.nodeValue;
	    	    	
	    	    	if(confirm != ""){

	    				createDiv(user, content, confirm);
						document.getElementById('content').value = '';

	    	    		
	    	    	}
    	    	   
	    	   }
		}


	};
	
	xmlHttpRequest.send(null);
}

function createDiv(user, content, date)
{
	var formID = Math.floor(Math.random()*100000)
	var divTag = document.createElement("div");
	divTag.id = formID;
	divTag.style.position = 'relative';
	document.getElementById('comments').appendChild(divTag);
	divTag.innerHTML = tag('forms/jsp/newcomment.jsp?content='+content+'&user='+user+'&date='+date, formID);
}

</script>
	<%if(download == true  || check == true || user.getID() == 56) { 
	    %>
	    <div id="de" style="position: relative; margin-top: 10px; float:left;">
		<a class="fg-button ui-state-active fg-button-icon-right ui-corner-all" href="<%=common.Utility.absoluteURLtoRelative(url) %>">Download</a>
		</div>
		
	<% } %>

	<div id="comments" style="position: relative; float:left; width:250px;">
		<%
			if(view == true || download == true || check == true || user.getID() == 56) { 
				String encRes = URLEncoder.encode(resourceID, "UTF-8");
			%>
				<p><a onMouseOver="addNLGlistener();" style="text-decoration:underline;" href="/ourspaces/LiberRestServlet?resourceID=<%=encRes%>" rel="<%=resource1%>NLG" class="liber2">Textual Description</a></p>
				<div style="color:black;" id="<%=resource1%>NLG"></div>
				
				<div style="color:black;" id="nlg"></div>
				
				<script type="text/javascript">
					alert("Ajax call");
// 							$.ajax({
// 								type: 'GET',
// 								url: $(this).attr("href"), 
// 								dataType : "html",
// 								async : false,
// 								success : function(html) {
// 									$("div#"+resource1+"NLG").html(html);
// 									alert("Ajax call successfull");
//									$("div#"+did).hide();
// 								}
// 							});
				</script>
				<!-- can't call javascript from here so use onMouseOver -->
			
<%-- 				<p style="padding-bottom:5px;">Title: <span style="color:#666666;"><%=title %></span> <span onclick="NLGtest();" class="nlg" rel="<%=encRes%>"></span></p> --%>
<%-- 				<div id="<%=encRes%>"></div> --%>
				<br>
				<p style="padding-bottom:5px;">Deposited by: <a href="profile.jsp?id=<%=user.getUserIDFromRDFID(depositor) %>"><%=user.getName(user.getUserIDFromRDFID(depositor)) %></a></p>
				<p style="padding-bottom:5px;">Uploaded on: <span style="color:#666666;"><%=fulldate %></span>
				<%if(authors.size() > 0) { %>
				<p style="padding-bottom:10px;">Authors:
					<%
					if(authors.size() > 0) {
					for(int i = 0; i < authors.size(); i++)
					{
						String authorID = (String) authors.get(i);
						if(authorID != null || !authorID.equals("")) {
							String firstname = rdf.getPropertyValue(authorID, FOAF.firstName.toString());
							String lastname = rdf.getPropertyValue(authorID, FOAF.surname.toString());
							String fullname = firstname + " " + lastname;
							%><span style="color:#666666;"><%=fullname %></span> <%
							}
						}
					}
					%>
				</p>
				<% } %>
				<p style="padding-bottom:5px;">Resource Type:
				 <%
							String[] fields = types.split("#");
				%>
				<%=fields[1] %>
				</p>
				<p style="padding-bottom:5px;">Lat: <span style="color:#666666;"><%=lat %></span>
				<p style="padding-bottom:5px;">Lon: <span style="color:#666666;"><%=lon %></span>
		<% } else { %>
			
			<p>You do not have permission to view this resource.</p>
			
		<% } %>		
	</div>
	
<!-- 	<script type="text/javascript" src="/ourspaces/javascript/top.js"> -->
<!-- 		addNLGdivListener(); -->
<!-- 		alert("Script loaded and executed from browseMaps.jsp"); -->
<!-- 	</script> -->
	
