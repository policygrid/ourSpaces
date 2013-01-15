<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, org.policygrid.ontologies.*, java.net.*, java.util.Vector, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="blog" class="common.Blog" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="access" class="common.AccessControl" />
<script type="text/javascript" src="/ourspaces/javascript/top.js"></script>

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

String resourceID = (String) request.getParameter("resource").toString();
String namespace = "http://openprovenance.org/ontology#";
String resourceURI = namespace + resourceID;
String resourceURIEncoded = URLEncoder.encode(resourceURI);

String depositor = rdf.getResourceDepositor(resourceURI);
String title = rdf.getResourceTitle(resourceURI);
String url = rdf.getResourceURL(resourceURI);
String fileID = url.substring(url.lastIndexOf("/")+1);
		
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
	view =  AccessControl.checkPermission("view",user.getFOAFID(),resourceURI);
	edit =  AccessControl.checkPermission("edit",user.getFOAFID(),resourceURI);
	download =  AccessControl.checkPermission("download",user.getFOAFID(),resourceURI);
}


boolean check = access.checkPublic(resourceURI);

long time = rdf.getLong(rdf.getResourceTimestamp(resourceURI));
java.util.Date date = new java.util.Date(time);
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
String fulldate = sdf.format(date);
String types = rdf.getResourceType(resourceURI);
ArrayList authors = rdf.getResourceAuthor(resourceURI);

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




	<div id="infores" style="position: relative; margin:10px auto; padding:5px; display: block; margin-bottom:10px; width: 268px; font-family: Verdana, sans-serif; font-size: 11px; border: 1px solid; -moz-border-radius: 5px;  -webkit-border-radius: 5px;  background-color:#F3F3F3;">
		<%
		if(view == true || download == true || check == true || user.getID() == 56) { %>
				<p style="padding-left:5px; padding-bottom:2px;"><strong>Title:</strong> <span style="color:#666666;"> <a style="font-size:12px;" href="/ourspaces/artifact_new.jsp?id=<%=resourceID%>"><%=title %></a></span></p>
				<p style="padding-left:5px; padding-bottom:2px;"><strong>Deposited by:</strong> <a href="profile.jsp?id=<%=user.getUserIDFromRDFID(depositor) %>"><%=user.getName(user.getUserIDFromRDFID(depositor)) %></a></p>
				<p style="padding-left:5px; padding-bottom:2px;"><strong>Uploaded on:</strong> <span style="color:#666666;"><%=fulldate %></span>
				<%if(authors.size() > 0) { %>
				<p style="padding-left:5px; padding-bottom:2px;"><strong>Authors:</strong>
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
				<% }  %>
				
				<p style="padding-left:5px; padding-bottom:5px;"><strong>Resource Type:</strong>
				 <%
				 String[] fields = types.split("#");
				 %>
				 <%=fields[1] %>
				</p>
				

		<% } else { %>
			
			<p>You do not have permission to view this resource.</p>
			
		<% } %>

	<%if(download == true  || check == true || user.getID() == 56) { %>
		<p align="center" style="padding-left:5px; padding-bottom:2px; margin: 0 auto;">
			<a href="<%=common.Utility.absoluteURLtoRelative(url) %>">Download</a> 
			| <a href="/ourspaces/boxes/resources/email.jsp?resource=<%=resourceURIEncoded%>" class="popup_dialog_ajax" title="Email">Email</a>
			| <a href="/ourspaces/movefolderUser.jsp?fileID=<%=fileID%>" class="popup_dialog_simple" title="Move File">Move</a>
			| <a href="/ourspaces/permissions/setPermission.jsp?resource=<%=resourceID%>" class="popup_dialog" title="Privacy settings">Privacy</a>
			| <a href="/ourspaces/createcomment.jsp?namespace=<%=URLEncoder.encode(namespace) %>&about=<%=resourceID %>" class="popup_comment_dialog" title="Comment">Comment</a>
			| <a href="/ourspaces/boxes/resources/tag.jsp?resource=<%=resourceURIEncoded%>" class="popup_dialog_ajax" title="Tag">Tag</a> 
<%-- 			| <a href="#" class="popup_dialog" title="Delete Resource" onclick="javascript:doDelete('<%=resourceID%>','<%=java.net.URLEncoder.encode(title)%>');">Delete</a> --%>
			| <a href="/ourspaces/deleteResource.jsp?title=<%=java.net.URLEncoder.encode(title)%>&resource=<%=resourceID%>&page=myresources.jsp" class="popup_dialog" title="Delete Resource">Delete</a>
		</p>
	<% } %>
	</div>

<!-- /ourspaces/createcomment.jsp?about=3b4e75c7-8634-4365-8721-13fa42e9e936 -->
<script>
initClasses();
</script>
