<%@ page contentType="text/html; charset=utf-8" language="java" import="java.util.regex.*, java.sql.*, common.*, java.util.Random, java.util.*" errorPage="" %>

<jsp:useBean id="fc" class="common.FacetHandler" />
<jsp:useBean id="user" class="common.User" />

<script type="text/javascript" src="/ourspaces/javascript/jquery-1.2.6.min.js"></script>
<script type="text/javascript">

function getBrowse(label, resourceType, property)
{	
	jQuery.get("browsetype.jsp?label="+encodeURIComponent(label)+"&resourceType="+encodeURIComponent(resourceType)+"&property="+encodeURIComponent(property), function(data) {
	    document.getElementById('browse').innerHTML = data;
	  })
}

function searchBrowse(label, resourceType, property)
{	
	  jQuery.get("searchtype.jsp?label="+encodeURIComponent(label)+"&resourceType="+encodeURIComponent(resourceType)+"&property="+encodeURIComponent(property), function(data) {
	    document.getElementById('types').innerHTML = data;
	  })
}

function getUser(label, resourceType, property, id)
{	
	jQuery.get("browseuser.jsp?label="+encodeURIComponent(label)+"&resourceType="+encodeURIComponent(resourceType)+"&property="+encodeURIComponent(property)+"&id="+encodeURIComponent(id), function(data) {
	    document.getElementById('browse').innerHTML = data;
	  })
}

function searchUser(label, resourceType, property)
{	
	jQuery.get("searchuser.jsp?label="+encodeURIComponent(label)+"&resourceType="+encodeURIComponent(resourceType)+"&property="+encodeURIComponent(property), function(data) {
	    document.getElementById('users').innerHTML = data;
	  })
}


</script>

<div style="position: relative; float:left; width: 320px;"> <!--  Left nav bar -->
	
	<div id="types" style="overflow-x: hidden;overflow-y: scroll; height:300px; width: 300px; padding:10px; border:1px solid #000000; margin-bottom:20px;"> <!--  types -->
		<jsp:include page="searchtype.jsp" flush="true">
			<jsp:param name="label" value="inferedGen" />
			<jsp:param name="resourceType" value="http://openprovenance.org/ontology#Artifact" />
			<jsp:param name="property" value="no" />
  		</jsp:include>
	</div>
	
	<div id="users" style="overflow-x: hidden;overflow-y: scroll; height:300px; width: 300px; padding:10px; border:1px solid #000000; margin-bottom:20px;"> <!--  Depositors -->
		<jsp:include page="searchuser.jsp" flush="true">
			<jsp:param name="label" value="inferedGen" />
			<jsp:param name="resourceType" value="http://openprovenance.org/ontology#Artifact" />
			<jsp:param name="property" value="http://www.policygrid.org/provenance-generic.owl#depositedBy" />
  		</jsp:include>
	</div>
	
</div> <!--  end of left nav bar -->

<div id="browse" style="position: relative; float: left; width: 600px; padding: 10px; border:1px solid #000000; margin-left:20px;"> <!--  right nav bar -->

	<jsp:include page="browsetype.jsp" flush="true">
		<jsp:param name="label" value="inferedGen" />
		<jsp:param name="resourceType" value="http://openprovenance.org/ontology#Artifact" />
   </jsp:include>

</div>