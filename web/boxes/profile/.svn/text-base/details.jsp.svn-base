<%@page import="org.policygrid.ontologies.AcademicDiscipline"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="org.policygrid.ontologies.OurSpacesVRE"%>
<%@page import="org.policygrid.ontologies.FOAF"%>
<%@ page language="java" import="java.util.*, java.io.*, java.net.*, java.util.Vector, common.*, com.hp.hpl.jena.*, com.hp.hpl.jena.rdf.model.ModelFactory, com.hp.hpl.jena.ontology.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="users" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="rdf" class="common.RDFi" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<%if (session.isNew()==true){ %>
<jsp:forward page="error.jsp" />
<% } %>

<% 
if( session.getAttribute("use") == null){ %>
<jsp:forward page="error.jsp" />
<%
	}

user = (User) session.getAttribute("use");
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));
	
String userID = Integer.toString(user.getID());

String username = user.getUsername(user.getID());
String resID = user.getRes(user.getID());

String fullName = users.getName(user.getID());
String email = users.getEmail(user.getID());
String title = users.getTitle(user.getID());
String organisation = users.getOrganisation(user.getID());
String researchInterest = users.getResearchInterests(user.getID());

String disciplineInfoID = users.getPropertyValue(user.getID(), OurSpacesVRE.hasDisciplineInfo.toString());
String academicDisciplineURI = rdf.getPropertyValue(disciplineInfoID, AcademicDiscipline.hasAcademicDiscipline.toString());
String disciplineTag = rdf.getPropertyValue(disciplineInfoID, AcademicDiscipline.hasDisciplineTag.toString());

%> <script language="javascript" type="text/javascript">
	$(document).ready(function() {
		$("[name=academicDiscipline]").val("<%=academicDisciplineURI%>");
<%-- 	$("select.academicDiscipline").val("<%=academicDisciplineURI%>"); --%>
	});
</script> <%

// Could use getPropertyValue() instead and remove all those useless methods (getResearchInterests(), getWebsite(),	...) 
// String researchInterest = users.getPropertyValue(user.getID(), OurSpacesVRE.hasResearchInterest.toString());
String website = users.getWebsite(user.getID());
String gender = users.getPropertyValue(user.getID(), FOAF.gender.toString());

if ("male".equalsIgnoreCase(gender)){
	%> <script language=javascript type=text/javascript>
	$(document).ready(function() {
		$('input[type="radio"][value="male"]').prop('checked', true);
	});
	</script> <%
} else if ("female".equalsIgnoreCase(gender)){
	%> <script language=javascript type=text/javascript>
	$(document).ready(function() {
		$('input[type="radio"][value="female"]').prop('checked', true);
	});
	</script> <%
}

String number = users.getHouseNumber(user.getID());
String street = users.getStreet(user.getID());
String town = users.getTown(user.getID());
String country = users.getCountry(user.getID());
String postcode = users.getPostCode(user.getID());
String skypeID = users.getSkypeID(user.getID());

int userid = user.getID();
%>

				<div class="widget-top">
					<div class="wlink" align="right" style="float: right; margin-left: 5px; margin-right: 10px; margin-bottom: 0px; padding: 10px;">
						<a class="popup_dialog" title="Change Password" href="boxes/profile/updatePassword.jsp">
							<img align="middle" style="float: left; display: inline-block; margin: -2px; border: 0px none;" src="/ourspaces/icons/001_41.png"> 
							<span style="margin-left: 8px;">Change Password</span> </a>
					</div>
				</div>


<form method="post" action="Controller?action=editprofile" style="padding:5px 5px">
<div style="float:left; padding:5px">
  <div style="width:130px; float:left; position:relative;">Name:</div> 
  <input type="text" style="border: 1px solid #000000; width:250px;" name="name" value="<%=fullName%>" />
<p style="padding-left:10px; color:#999999; padding:3px; font-size:10px">
  <div style="width:130px; float:left; position:relative;">Gender:</div> 
  <input type="radio" name="gender" value="male" /> Male &nbsp
  <input type="radio" name="gender" value="female" /> Female
<p style="padding-left:10px; color:#999999; padding:3px; font-size:10px">
  <div style="width:130px; float:left; position:relative;">Research Interest:</div>
  <input type="text" style="border: 1px solid #000000; width:250px;" name="researchInterest" value="<%=researchInterest%>" />
<p style="padding-left:10px; color:#999999; padding:3px; font-size:10px">
  <div style="width:130px; float:left; position:relative;">Academic Discipline:</div>
  <select name="academicDiscipline" style="width:250px;">
  <%
  Utility.getSortedResources(rdf.getMainDisciplines());
  for(OntResource mainDiscipline : Utility.getSortedResources(rdf.getMainDisciplines())){
	  %>
	  <option value="<%=mainDiscipline.getURI()%>" style="font-weight: bold"><%= mainDiscipline.getLabel(null)%></option>
	  <%
	  for(OntResource discipline : Utility.getSortedResources(rdf.getSubDisciplines(mainDiscipline))){
		  %>
		  <option value="<%=discipline.getURI()%>"> - <%= discipline.getLabel(null)%></option>
		  <%						  
	  }
  }
  %>
  </select>
<p style="padding-left:10px; color:#999999; padding:3px; font-size:10px">
  <div style="width:130px; float:left; position:relative;">Specific Discipline:</div>
  <input type="text" style="border: 1px solid #000000; width:250px;" name="disciplineTag" value="<%= disciplineTag%>" />
<p style="padding-left:10px; color:#999999; padding:3px; font-size:10px">
  <div style="width:130px; float:left; position:relative;">Job Title:</div>
  <input type="text" style="border: 1px solid #000000; width:250px;" name="title" value="<%=title%>" />
<p style="padding-left:10px; color:#999999; padding:3px; font-size:10px">
  <div style="width:130px; float:left; position:relative;">Website:</div>
  <input type="text" style="border: 1px solid #000000; width:250px;" name="website" value="<%=website%>" />
<p style="padding-left:10px; color:#999999; padding:3px; font-size:10px">
  <div style="width:130px; float:left; position:relative;">Number/Name:</div>
  <input type="text" style="border: 1px solid #000000; width:250px;" name="number" value="<%=number%>"  />
<p style="padding-left:10px; color:#999999; padding:3px; font-size:10px">
  <div style="width:130px; float:left; position:relative;">Street: </div>
  <input type="text" style="border: 1px solid #000000; width:250px;" name="street" value="<%=street%>" />
<p style="padding-left:10px; color:#999999; padding:3px; font-size:10px">
  <div style="width:130px; float:left; position:relative;">Town/City: </div>
  <input type="text" style="border: 1px solid #000000; width:250px;" name="town" value="<%=town%>" />
<p style="padding-left:10px; color:#999999; padding:3px; font-size:10px">
  <div style="width:130px; float:left; position:relative;">Country: </div>
  <input type="text" style="border: 1px solid #000000; width:250px;" name="country" value="<%=country%>" />
<p style="padding-left:10px; color:#999999; padding:3px; font-size:10px">
  <div style="width:130px; float:left; position:relative;">Post Code: </div>
  <input type="text" style="border: 1px solid #000000; width:250px;" name="postcode" value="<%=postcode%>" />
  <input type="hidden" value="<%=userid%>" name="id" />
<p style="padding-left:10px; color:#999999; padding:3px; font-size:10px">
  <div style="width:130px; float:left; position:relative;">Skype ID: </div>
  <input type="text" style="border: 1px solid #000000; width:250px;" name="skypeID" value="<%=skypeID%>" />
  <input type="hidden" value="<%=userid%>" name="id" />
			
<p><input type="submit" value="Update" class="fg-button ui-state-active fg-button-icon-right ui-corner-all" style="margin-top:10px;" /></p>
</div>
</form>

