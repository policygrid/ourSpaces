<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="user" class="common.User" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<%

user = (User) session.getAttribute("use");
if ((user.getID() == 59) || (user.getID() == 56) || (user.getID() == 55) || (user.getID() == 224) || (user.getID() == 225) || (user.getID() == 196)) {
	
} else {
	response.sendRedirect(response.encodeRedirectURL("/ourspaces/error.jsp"));
}
%>


<!-- $Id: example-ajax.html,v 1.2 2006/04/27 21:00:38 pat Exp $ -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ajax Tabber Example</title>

<link rel="stylesheet" href="example.css" TYPE="text/css" MEDIA="screen">
<link rel="stylesheet" href="example-print.css" TYPE="text/css" MEDIA="print">


<!--
Load prototype.js
You can get it at http://prototype.conio.net/
-->

<script src="/ourspaces/javascript/prototype.js" type="text/javascript"></script>


<script type="text/javascript">

/* Optional: Temporarily hide the "tabber" class so it does not "flash"
   on the page as plain HTML. After tabber runs, the class is changed
   to "tabberlive" and it will appear. */

document.write('<style type="text/css">.tabber{display:none;}<\/style>');

var tabberOptions = {

  'onClick': function(argsObj) {

    var t = argsObj.tabber; /* Tabber object */
    var i = argsObj.index; /* Which tab was clicked (0..n) */
    var div = this.tabs[i].div; /* The tab content div */

    /* Display a loading message */
    div.innerHTML = "<p>Loading...<\/p>";

    /* Fetch some html depending on which tab was clicked */
    var url = 'tab-ajax-' + i + '.jsp';
    var pars = 'foo=bar&foo2=bar2'; /* just for example */
    var myAjax = new Ajax.Updater(div, url, {method:'get',parameters:pars});
  },

  'onLoad': function(argsObj) {
    /* Load the first tab */
    argsObj.index = 0;
    this.onClick(argsObj);
  },


}


function acceptRequest(rid) {
	

		var i = 3; /* Which tab was clicked (0..n) */
	    var div = document.getElementById('helpdesk'); /* The tab content div */

	    var pars1 = 'rid='+rid; 
	    var myAjax1 = new Ajax.Updater(div, 'acceptRequest.jsp', {method:'get',parameters:pars1});


	    /* Display a loading message */
	    div.innerHTML = "<p>Loading...<\/p>";

	    /* Fetch some html depending on which tab was clicked */
	    var url = 'tab-ajax-' + i + '.jsp';
	    var pars = 'foo=bar&foo2=bar2'; /* just for example */
	    var myAjax = new Ajax.Updater(div, url, {method:'get',parameters:pars});
		 

}

function updateRequest(rid) {
	
	var dropdownIndexS = document.getElementById(rid+'status').selectedIndex;
	var dropdownValueS = document.getElementById(rid+'status')[dropdownIndexS].value;
	var dropdownIndexP = document.getElementById(rid+'priority').selectedIndex;
	var dropdownValueP = document.getElementById(rid+'priority')[dropdownIndexP].value;

	var i = 3; /* Which tab was clicked (0..n) */
    var div = document.getElementById('helpdesk'); /* The tab content div */

    var pars1 = 'rid='+rid+'&status='+dropdownValueS+'&priority='+dropdownValueP; 
    var myAjax1 = new Ajax.Updater(div, 'updateRequest.jsp', {method:'get',parameters:pars1});


    /* Display a loading message */
    div.innerHTML = "<p>Loading...<\/p>";

    /* Fetch some html depending on which tab was clicked */
    var url = 'tab-ajax-' + i + '.jsp';
    var pars = 'foo=bar&foo2=bar2'; /* just for example */
    var myAjax = new Ajax.Updater(div, url, {method:'get',parameters:pars});
	
}


function deleteRes(resource, rid) {

	new Ajax.Request('removeRes.jsp?id='+resource,
			  {
			    method:'get',
			    onSuccess: function(transport){
			      var response = transport.responseText || "no response text";
			      $(rid).remove();
			    },
			    onFailure: function(){ alert('Something went wrong...') }
			  });
}

</script>

<script type="text/javascript" src="tabber.js"></script>

<style type="text/css">
.tabberlive .tabbertab {
}
</style>
</head>
<body>

<h1>ourSpaces Admin Interface</h1>


<div class="tabber">

     <div class="tabbertab">
	  <h2>People</h2>
     </div>


     <div class="tabbertab">
	  <h2>Resources</h2>
     </div>


     <div class="tabbertab">
	  <h2>Projects</h2>
     </div>
     
      <div id = "helpdesk" class="tabbertab">
	  <h2>Helpdesk</h2>
     </div>
     
     <div id = "stats" class="tabbertab">
	  <h2>Stats</h2>
     </div>

</div>

</body>
</html>
