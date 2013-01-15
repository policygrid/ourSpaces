<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="blog" class="common.Blog" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<%
Random r = new Random();
int d = r.nextInt();

String tempAbout = request.getParameter("about");
String tempNamespace = request.getParameter("namespace");
common.Utility.log.debug(tempNamespace);
if(tempNamespace == null || tempNamespace.equals(""))
	tempNamespace =  "http://www.policygrid.org/ourspacesVRE.owl#";
String about = tempNamespace + tempAbout;
common.Utility.log.debug("se;ected ns"+tempNamespace);
user = (User) session.getAttribute("use");
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));

int id = user.getID();

ArrayList comments = blog.getComments(about);


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
  if (content === "") {
	alert("Please enter a comment!");
	return;
  }

	var about = document.getElementById('about').value;//<%=about%>;
  
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
<script type="text/javascript" src="/ourspaces/javascript/pls.js"></script> 

<h2>View and Create Comments</h2>

<div style="position:relative; width: 100%;margin-top:10px;">

	<p>Current comments:</p>
	<div id="comments" style="position: relative; float:left; margin-bottom:10px; width: 100%;">
	<%
		for(int i = 0; i < comments.size(); i++)
		{
			BlogBean bb = (BlogBean) comments.get(i);
			int posterID = bb.getUserID();
			String content = bb.getContent();
			String date = bb.getDate();
		%>
		<div style="width:400px; position: relative; float:left; padding:10px; ">
			<p class="plscontent"><%=content %></p>
			<p style="font-size:9px; color: #666;">Posted by: <a href="profile.jsp?id=<%=posterID %>"><%=user.getName(posterID) %></a> :: Posted on: <%=date %></p>
		</div>
		<%
		}
	%>
	</div>
	

				<p style="margin-top:5px; margin-bottom:5px;float:left; width: 100%; ">
					Your comment:
					<textarea name="content" id="content" name="content" style="margin-top:10px; border:1px solid #ccc; background-color:#f2f2f2;  width: 90%; padding:10px; height:50px;"></textarea>
				</p>
				
				<input type="hidden" value="<%=id%>" id="user" />
				<input type="hidden" value="<%=tempAbout %>" id="about"></input>
		   <!--   
		   		<p style="padding-top:5px;">
					<a href="#" onclick="addComment(); return false;">Post Comment</a>
				</p>
		    -->   


</div>