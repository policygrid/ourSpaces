<%@ page language="java" import="java.util.Iterator, com.hp.hpl.jena.ontology.*, java.util.Random, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />

<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.Project" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="ontology" class="common.OntologyHandler" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%

user = (User) session.getAttribute("use");
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));


ArrayList projectList = project.getAllProjectsAboutUser(user.getFOAFID());

Random r = new Random();
int basic = r.nextInt();
int advanced = r.nextInt();
String label = "No label";
String namespace = "";
String property = "";
String uri = "";

if(request.getParameter("label") == null) { %>
<%
}
else
{
	label = "Type";
	namespace = "http://openprovenance.org/ontology";
	property = "Artifact";
	uri = namespace + "#" + property;
	common.Utility.log.debug("URI::: " + uri);
}

String baseClass = "http://openprovenance.org/ontology#Artifact";
if(request.getParameter("baseClass") != null && request.getParameter("baseClass").length()>0) {
	baseClass = request.getParameter("baseClass");
}

String nameUpload = "";
if(request.getParameter("nameUpload") != null && request.getParameter("nameUpload").length()>0) {
	nameUpload = request.getParameter("nameUpload");
}

String allowFile = "";
if(request.getParameter("allowFile") != null && request.getParameter("allowFile").length()>0) {
	allowFile = request.getParameter("allowFile");
}
String ontologyName = "all";
if(request.getParameter("ontologyName") != null && request.getParameter("ontologyName").length()>0) {
	ontologyName = request.getParameter("ontologyName");
}

String projectID = (String)session.getAttribute("projectID");
String resourceId = (String)request.getParameter("id");
String buttonText = "Upload resource";

//Find the baseClass when the resourceId is specified.
//Make resourceId global in javascript
if(resourceId != null){
	String baseClassRes = ResourceUploadBean.getBasicType(rdf.getResourceType(resourceId));
	if(baseClassRes != null)
		baseClass = baseClassRes;
	%>
<script language="javascript" type="text/javascript">
			$(document).ready(function(){
				activeDialog.configuration.resourceId = '<%=resourceId%>';
				activeDialog.configuration.resourceType = '<%=rdf.getResourceType(resourceId) %>';
				activeDialog.configuration.previousFile = '<%=rdf.getResourceURL(resourceId) %>';
				activeDialog.configuration.baseClass = '<%=baseClass %>';
				<%
				if(!baseClass.equals("http://openprovenance.org/ontology#Artifact")){
					allowFile = "false";
					%>activeDialog.configuration.allowFile = false;<%
				}
				%>
			});
</script><%
buttonText = "Apply changes";
}
else{
	%><script language="javascript" type="text/javascript">activeDialog.configuration.resourceId = null;</script><%
}

%>
 
 <div id="<%=nameUpload %>breadcrumb" class="ui-corner-all" style="background-color: #6E6E6E;overflow: hidden;z-index:-1;width:700px;height:30px; position:fixed; clear:both; margin: 0px; padding:0px">
  </div>
 <div id="<%=nameUpload %>main"style="overflow-x: hidden;overflow-y: scroll;z-index:-1;width:100%; position:relative; clear:both; margin: 2px auto;margin-top:31px; padding:0px">
  
  	<div style="width:210px;position: fixed; float:left;"> <!-- left div -->
  	<div id="<%=nameUpload %>accordion" style="height:390px;display:none">
	<h3 id="<%=nameUpload %>propertiesHeader" style="border: 0px"><a href="#">Properties of (Artifact?)</a></h3>
	<div id = "<%=nameUpload %>propertiesDiv" style="height:100%; padding: 5px; background-color:white;  border: 0px; margin-bottom:5px;">		
		<h3 style="text-align: center;">Loading...</h3>
	</div>
	

	
	<h3 style="border: 0px"><a href="#">Scientific Discourse</a></h3> <!-- Swan ontology -->
	<div style="padding: 5px; background-color:white;  border: 0px; margin-bottom:5px;">
	<p>
	
						<jsp:include page="uploadform/getDiscourseProperties.jsp" flush="false">
							<jsp:param value="ui-draggable uploadLiItem" name="liClass"/>
							<jsp:param value="margin: 3px;" name="liStyle"/>
							<jsp:param value="padding: 5px;" name="ulStyle"/>
							<jsp:param value="draggable" name="ulClass"/>
							<jsp:param value="discouseProperties" name="ulId"/>
							
						</jsp:include>						
		</p>
	</div>
	<h3 style="border: 0px"><a href="#">Geo Properties</a></h3>
	<div style="padding: 5px; background-color:white;  border: 0px; margin-bottom:5px;">
	<p>
	<ul class="draggable" style="padding: 5px;">
	<%
	
	Vector<FormItem> location = (Vector<FormItem>)ontology.getLocationFields();

	for(int i = 0; i < location.size(); i++)
	{

		FormItem fm = (FormItem) location.get(i);
		String type = fm.getType();
		String name = fm.getNamespace();
		String prop = fm.getProperty();
		
		String resourceNamespace = "";
		String resourceName = "";
		
		if(type.equals("location"))
		{
			resourceNamespace = fm.getResourceNamespace();
			resourceName = fm.getResourceName();
		}
		String separatedClassName = Utility.splitString(prop);
		   	
		%>
					<jsp:include page="uploadform/liProperty.jsp">
						<jsp:param name="idProperty" value="<%=name+\"#\"+prop%>" />
						<jsp:param name="liStyle" value="margin: 3px;" />
						<jsp:param value="ui-draggable uploadLiItem" name="liClass"/>
						<jsp:param name="type" value="geoMultiple" />
						<jsp:param name="data-range" value="http://www.geonames.org/ontology#Feature" />
						<jsp:param name="label" value="<%=prop%>" />
					</jsp:include>
		<%		           
	}
    %>
	 </ul>
		</p>
	</div>
		
</div>
        
      
        
    </div> <!-- end of left -->
<!-- Get rid of the scrollbar height:608px; overflow:auto; -->
<div id="<%=nameUpload %>droppable" style="width:530px; margin-left: 215px; position:relative; background-color:white; float:left; border: 0px;" class="ui-widget-content ui-corner-all">
<!--<p style="color:#999; font-size:24px; margin: 0 auto;" align="center">Upload a resource</p>-->
		
	<%
	if("true".equals(allowFile)){
		session.setAttribute("uploadstart", System.currentTimeMillis());
		%>
			<div id="<%=nameUpload %>upfileformDiv" class="ui-state-highlight ui-corner-all  upload-element">
				<form id="<%=nameUpload %>upfileform" action="/ourspaces/ResourceUploadNew?action=upload" method="POST" enctype="multipart/form-data">
				<div class="fileupload-buttonbar">
		            <label class="fileinput-button">
		                <span>Upload file:</span>
		                <input  style="float:right" id="<%=nameUpload %>upfile" type="file" name="files[]" multiple>
		            </label><br>
		        </div>
		    </form>
		    		<div class="URI-buttonbar">
		            <label style="float: left;" class="URIinput-button">
		                <span>or external URL:</span>
		            </label>     
		            <input class="inputValue" style="float: right; padding: 2px;  border: 1px solid #000000;" id="<%=nameUpload %>fileURL" name="fileURL" size="30" type="text" value="">			                
		           
		        </div>
		  </div>			 
		
				<div class="ui-state-highlight ui-corner-all upload-element">
		             <label for="<%=nameUpload %>folder">Upload to the folder:</label>
		           <select style="float: right;" id="<%=nameUpload %>folder" name="<%=nameUpload %>folder">
		           <%
		           		FileUtils f = new FileUtils();
		           		ArrayList<String> folders = f.getFoldersByUserID(user.getID());
		           		if(!folders.contains("/"))
		           			folders.add("/");
		           		for(String s : folders){
		           			if(s == null)
		           				continue;
		           			%><option value="<%=s%>"><%=s %></option><%	
		           		}
		           %>
					  
					</select> 
				</div>  <% 
	}
	%>   
			<!--   <div class="ui-state-highlight ui-corner-all" style="float:left; margin: 10px; padding: 8px; width: 400px; ">
				
							<label for="typeField1">Project:</label>
				     <select name="Progect" id="<%=nameUpload %>typeField1" style="width:250px; float: right;">
			       
			
			         <option value="" selected="selected" style="font-weight:bold;">Personal Resource (Home Space)</option>
			
			        <%
					  	for(int i = 0; i < projectList.size(); i++)
					  	{
					  		ProjectBean pb = (ProjectBean) projectList.get(i);
					  		
					  		String separatedClassName = Utility.splitString(pb.getRole());
					  		//No need to encode, data are sent as JSON.
					  		String encRes = pb.getURI();
					  		
					%>
							        
			       
			                <option value="<%=encRes%>"><%=pb.getTitle()%></option>
			    
			        <%
			    
			        }
			        
			        
			        %>
			      </select>
				</div> -->
				
				
				<div class="ui-state-highlight ui-corner-all upload-element">
				  <% 
				  		String expl = "Check, whether the resource should be visilbe only to ";
		           if ((projectID == null) || (projectID.equals(""))) {
		        	   expl+="you.";
		           %>
		             <label title="<%=expl %>" for="pri">The resource is PRIVATE</label>
		           <%} else { 
		        	   expl+=" the members of the selected project.";%>
		             <label title="<%=expl %>" for="pri">The resource is visible ONLY to project members.</label>
		           <% } %>
		           <input title="<%=expl %>" type="checkbox" checked="true" style="float: right;" id="<%=nameUpload %>pri" name="private">
				</div>
			
				
				<div class="ui-state-highlight ui-corner-all upload-element">
				<div id="<%=nameUpload %>resourceTypes" style=""><a href="#" onclick="$('#<%=nameUpload %>navigationType').toggle();return false;">Resource Type:</a><br/></div>
				<div style="width: 95%; float:left;">	
		              <jsp:include page="uploadform/getResourcesTypes.jsp" >
		              	<jsp:param value="false" name="allClasses"/>
		              	<jsp:param value="<%=baseClass %>" name="className"/>
		              	<jsp:param value="activeDialog.changeType('#className');" name="onClick"/>
		              	<jsp:param value="<%=nameUpload+\"navigationType\" %>" name="ulId"/>	
		              	<jsp:param value="<%=ontologyName %>" name="ontologyName"/>	
		              		              	
		              </jsp:include>
	            </div>
	 
	            </div>
				
				
<div id="<%=nameUpload %>allFields" class="upload-element">
<p id="<%=nameUpload %>mandatory" style="float:left;width: 100%;"><b>Mandatory Fields for:</b></p>

<div id="<%=nameUpload %>mandatoryFields" class="requiredFields" style="float:left;width: 100%;">
</div>

	<div id="<%=nameUpload %>policyFields" style="float:left;width: 100%;">
	</div>

<div>
<div id="<%=nameUpload %>add" class="upload-element" style="border: dotted 1px #00ff00;">
	<div id="<%=nameUpload %>newFields" style="float:left;width: 100%;">
	<p><b>Optional Fields</b></p></br>
	</div>
	<p>Drag &amp; drop additional fields here...</p>
</div>
</div>
</div><!-- End of allFields. -->

<!-- <div id="<%=nameUpload %>submit" type="image"  onclick="sendData()" style="margin-left: auto; margin-right: auto; position: relative; width: 250px; margin-top:10px;" >
	<a href="#" style="background: none repeat scroll 0 0 green;margin-bottom: 20px;" class="fg-button ui-state-active  ui-corner-all"><h2><%=buttonText %></h2></a>
</div> -->

  </div>
 </div> 
