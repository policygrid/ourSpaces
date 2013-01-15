<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, org.policygrid.ontologies.*, java.net.*, java.util.Vector, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="f" uri="ourSpacesTags" %>

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

String url = rdf.getResourceURL(resourceID);
		
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



ArrayList authors = rdf.getResourceAuthor(resourceID);

String types = rdf.getResourceType(resourceID);

String[] fields = types.split("#");



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

function edit(id, subject, predicate, object)
{

	var newObject = document.getElementById(id).value;
  
	var xmlHttpRequest=init();

	  function init(){

	if (window.XMLHttpRequest) {
	           return new XMLHttpRequest();
	       } else if (window.ActiveXObject) {
	           
	           return new ActiveXObject("Microsoft.XMLHTTP");
	       }

	}
  	
	xmlHttpRequest.open("GET", "ResourceBrowse?action=replace&subject="+encodeURIComponent(subject)+"&predicate="+encodeURIComponent(predicate)+"&oldobject="+encodeURIComponent(object)+"&newobject="+encodeURIComponent(newObject), true);
	xmlHttpRequest.onreadystatechange=function() {
		if(xmlHttpRequest.readyState==4){
	    	   if(xmlHttpRequest.status==200){

	    		   var xmlMessage=xmlHttpRequest.responseXML;

	    	    	var confirm=xmlMessage.getElementsByTagName("message")[0].firstChild.nodeValue;
	    	    	
	    	    	if(confirm != "")
		    	    {
	    	    			
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


<p style="color:#999; font-size:24px; margin: 0 auto;" align="center">Resource Download</p>

<div style="position:relative; margin:20px; padding:15px; border:1px solid #CCC; width: 420px;">

	<div id="comments" style="position: relative; float:left; width:490px; margin-bottom:10px;">
		<%
			if(view == true || download == true || check == true || user.getID() == 56) { 
				
				ArrayList properties = rdf.getProperties(resourceID);
				for(int i = 0; i < properties.size(); i++)
				{
					Properties prop = (Properties) properties.get(i);
					String property = prop.getProperty();
					String value = prop.getValue();
					
					Random r = new Random();
					int random = r.nextInt();
					
					String[] props = property.split("#");
					if(props[1].equals("timestamp"))
					{
						long time = Long.parseLong(value);
						java.util.Date date = new java.util.Date(time);
						java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
						String fulldate = sdf.format(date);
						%>
						<p>Uploaded on: <%=fulldate %></p>
					<%
					}
					else if(!value.contains("#") && !props[1].equals("hasURI") && !props[1].equals("timestamp")) 
					{
					%>
						<p><%=props[1] %>: <%=value %> <input type="text" id="<%=random %>"></input> <a href="#" onclick="edit('<%=random %>', '<%=resourceID %>', '<%=property %>', '<%=value %>'); return false;">Edit</a></p>
						
					<%
					}
					else if(props[1].equals("depositedBy"))
					{
						%><p>Deposited By: <% 
							int id = user.getUserIDFromRDFID(value);
							String name = user.getName(id);
							
						%><a href="profile.jsp?id=<%=id %>"><%=name %></a></p><%
					}
					else if(props[1].equals("hasAuthor"))
					{
						%><p>Has Author: <%
						String firstname = rdf.getPropertyValue(value, FOAF.firstName.toString());
						String lastname = rdf.getPropertyValue(value, FOAF.surname.toString());
						String fullname = firstname + " " + lastname;
						%><%=fullname %></p><%
					}
					else if(value.contains("#"))
					{
						String[] vals = value.split("#");
						if(vals[0].equals("http://www.geonames.org/ontology"))
						{
							common.Utility.log.debug("%%%%%%%%%%%%%%%%%%%%%% " + value);
							ArrayList feature = rdf.getGeoInformation(value);
							for(int j = 0; j < feature.size(); j++)
							{
								Properties p = (Properties) feature.get(j);
								String[] ps = p.getProperty().split("#");
								%><p><%=ps[1] %>: <%=p.getValue() %></p><%
							};
						}
						else
						{
							String newTitle = rdf.getResourceTitle(value);
							String[] fields2 = value.split("#");
							%><p><%=props[1] %>: <a href="browse.jsp?resource=<%=fields2[1]%>" rel="facebox"><%=newTitle %></a></p><%
						}
					}
					
				}
		%>		
				
		<% } else { %>
			
			<p>You do not have permission to view this resource..dd...</p>
			
		<% } %>
		

		

	</div>
	<%if(download == true  || check == true || user.getID() == 56) { 
	    %>
		<p style="padding-bottom:10px;" align="center"><a href="<%=common.Utility.absoluteURLtoRelative(url) %>"><img src="images/download.png" style="border:none;" /></a></p>
	<% } %>
</div>
