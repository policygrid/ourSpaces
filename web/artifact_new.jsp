<%@page import="org.policygrid.ontologies.FOAF"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="javax.swing.event.ListSelectionEvent"%>
<%@page import="java.net.*,org.eclipse.jdt.internal.compiler.ast.ForeachStatement"%>
<%@ page language="java" import="com.hp.hpl.jena.rdf.model.Model, java.util.Iterator,java.util.*,java.text.SimpleDateFormat,java.util.ArrayList,java.io.*,java.net.*,java.util.Vector,common.*,provenanceService.ProvenanceService, org.policygrid.policies.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="projectBean" class="common.ProjectBean" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="blog" class="common.Blog" />

<jsp:useBean id="access" class="common.AccessControl" />
<jsp:useBean id="project" class="common.Project" />
<jsp:useBean id="artifact" class="common.ArtifactBean" />
<jsp:useBean id="file" class="common.FileUtils" />
<%
	if (session.isNew() == true) {
%>
<jsp:forward page="error.jsp" />
<%
	}
	if (session.getAttribute("use") == null) {
%>
<jsp:forward page="error.jsp" />
<%
	}

	user = (User) session.getAttribute("use");

	if (request.getParameter("id") == null) {
%>
 	<jsp:forward page="error.jsp" /> <%
 	}
 	//Artifact ID
 	String temp = (String) request.getParameter("id").toString();
 	String artifactID = org.policygrid.ontologies.OPM.NS.toString() + temp;
 	// No ArtifactID or OPM namespace
 	if (temp == null) {
 %> <jsp:forward page="error.jsp" /> <%
 	}

ParameterHelper parHelp = new ParameterHelper(request, session);
 	session.setAttribute("artifactID", artifactID);
 	session.setAttribute("containerType", "artifact");
 	artifact.setArtifactID(artifactID);
 	artifact.setRdf(rdf);
 	String title = rdf.getResourceTitle(artifactID);
	String url = rdf.getResourceURL(artifactID);
	String fileID = url.substring(url.lastIndexOf("/")+1);

 	artifact.loadValues();
	if(artifact.getType()==null ||artifact.getType().equals("")){
		//Error
		//TODO Fix propert error warning.
		%>
<jsp:include page="top_head.jsp" />
<jsp:include page="top_tail.jsp" />
		Error! Wrong artifact id.
  <jsp:include page="bottom.jsp" /><%
		return;
	}
	String link = artifact.getUrl();
	String encLink = "";
	if(link != null && !link.equals(""))
		encLink = URLEncoder.encode(link);
	
 	String encodedArtifactID = URLEncoder.encode(artifactID,"UTF-8");

 	parHelp.setSessionParameter("artifact", artifact);
	
	
	Vector<String> preferredVis = user.getPreferredVisualisations(artifact);

 	//Check rights of current user
	boolean check = access.checkPublic(artifactID);
 	
 	boolean view = AccessControl.checkPermission("view",
 			user.getFOAFID(), artifactID);
 	boolean edit = AccessControl.checkPermission("edit",
 			user.getFOAFID(), artifactID);
 	boolean download = AccessControl.checkPermission("download",
 			user.getFOAFID(), artifactID);
 	boolean remove = AccessControl.checkPermission("remove",
 			user.getFOAFID(), artifactID);
 	
 %>

<jsp:include page="top_head.jsp" />
<!-- menu goes here -->





<%
		//Hide non preferred visualisations, if any
		if(preferredVis != null && preferredVis.size() > 0){ %>
					<script>
					$(document).ready(function(){
						<%
						if(!preferredVis.contains("http://www.policygrid.org/academic-disciplines#Graph")){
							%>$("#liProvenance").addClass('collapsed');
							<%
						}
						if(!preferredVis.contains("http://www.policygrid.org/academic-disciplines#Table")){
							%>$("#resourceInfo").addClass('collapsed');<%
						}
						if(!preferredVis.contains("http://www.policygrid.org/academic-disciplines#Text")){
							%>
// 							$("#textProvenance").addClass('collapsed');
							  $("#textArtifact").addClass('collapsed');<%
						}
						%>	
					});
					</script>
		<%
		}
		//No options for previous versions
		if (artifact.lastVersion()) {	 %>
      <div class="navBarLeft">
           <div class="dropdownBox">
              <a class="navBarLeft" href="#" style="float: left;"><img src="/ourspaces/icons/001_58.png" align="left" border="0"/>Resource Space </a> <img src="/ourspaces/icons/dropdown.png" align="right" border="0"/>
              <div class="navBarOptions ui-corner-all" style="font-size: 16px; width:220px;">
               <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
		         <span style="font-weight: 900;"> Share:</span>
		         </div>
		         <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px; border-bottom: 1px solid #FF6600"></div>
		           
											<!-- Email -->
											
										<div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
				             		<a href="" onclick="showEmail('X');return false;">
				             		<img src="/ourspaces/icons/001_12.png" align="left" border="0"/>Email artifact</a>
				           	</div>
												<%
										if (edit || check) {
									%>
										<div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
				             		<a href="" onclick="showTag('X');return false;">
				             		<img src="/ourspaces/icons/001_35.png" align="left" border="0"/>Tag</a>
				           	</div>
										<div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
				             		<a href="createcomment.jsp?namespace=http%3A%2F%2Fopenprovenance.org%2Fontology%23&about=<%=temp%>" title="Comment" class="popup_comment_dialog" >
				             		<img src="/ourspaces/icons/001_50.png" align="left" border="0"/>Comment</a>
				           	</div>
				           	
									<%} %>
						        <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
							        <span style="font-weight: 900;"> Actions:</span>
							      </div>
							      <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px; border-bottom: 1px solid #FF6600"></div>
							         <%
									//TODO - replace with proper check
										
										if ((check || download) && (artifact.getUrl()!=null && !artifact.getUrl().equals(""))) {
											//fg-button-center ui-state-active fg-button-icon-right ui-corner-all
									%>
										<div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
				             		<a href="<%=common.Utility.absoluteURLtoRelative(artifact.getUrl())%>"><img src="/ourspaces/icons/001_53.png" align="left" border="0"/>Download</a>
				           	</div>
											<%
									    }
									//Edit - check rights and if it is the last version.
											if (edit || check ) {	
												%>
												
										<div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
				             		<a href="#" onclick="updateResource(); return false;">
				             		<img src="/ourspaces/icons/001_45.png" align="left" border="0"/>Edit</a>
				           	</div>
										<div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
				             		<a href="/ourspaces/permissions/setPermission.jsp?resource=<%=temp%>" class="popup_dialog" title="Privacy settings">
				             		<img src="/ourspaces/icons/001_42.png" align="left" border="0"/>Edit privacy</a>
				           	</div>
				           	
										<div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
				             		<a href="/ourspaces/movefolderUser.jsp?fileID=<%=fileID%>" class="popup_dialog_simple" title="Move File">
				             		<img src="/ourspaces/icons/001_60.png" align="left" border="0"/>Move resource</a>
				           	</div>
				           	
										<div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
				             		<a href="testProvenance/provenanceEdit.jsp?resource=<%=encodedArtifactID %>" onclick="log('log_graphVis(userid,resid, page, actiontype)','<%=user.getID() %>,\'<%=artifactID %>\',\''+document.URL+'\',\'edit\'');return true;">
				             		<img src="/ourspaces/icons/001_45.png" align="left" border="0"/>Edit provenance</a>
				           	</div>
												<%
											}
											if (remove || check) {
												%>
												<div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">												
						             		<a class="popup_dialog" href="deleteResource.jsp?title=<%=URLEncoder.encode(title)%>&resource=<%=temp%>&page=myhome.jsp" >
						             		<img src="/ourspaces/icons/001_05.png" align="left" border="0"/>Delete</a>
						           	</div>
													<%
												}
											
									%>
		      </div>
           </div>       
       </div>
       
		<% }	 %>

<jsp:include page="top_tail.jsp" />

<!-- Email dialog -->
							<div id="Xemail" title="Email dialog" class="hidden" style="display:none">
								<div id="X_email">		
										Recipient: <input id="emailX" style="margin-right:5px;width:250px" type="text" />					
										<input id="urlX" type="hidden" value="<%=encLink%>" />
										<input id="resIDX" type="hidden" value="<%=encodedArtifactID%>" /><br><br>
										<a href="#" onclick="email('X')" style="background: none repeat scroll 0 0 green;display: block;float: none;margin-left: auto;margin-right: auto; width: 120px;" class="fg-button ui-state-active  ui-corner-all">Send email</a>
								</div>
							</div>
<!-- Tag dialog -->
							<div id="Xtag" class="hidden" style="display:none">
								<div id="X">		
										<input id="tagX" style="margin-right:5px;" type="text" />					
										<input id="idX" type="hidden" value="<%=user.getID()%>" />
										<input id="resIDX" type="hidden" value="<%=artifactID%>" />
										<a href="#" onclick="add('X')" style="background: none repeat scroll 0 0 green;display: block;float: none;margin-left: auto;margin-right: auto; width: 120px;" class="fg-button ui-state-active  ui-corner-all">Add tag</a>
								</div>
							</div>
							
<script language="javascript" type="text/javascript">
artifactId = '<%=artifactID%>';
artifactType = '<%=artifact.getType()%>';
</script>

	


<link type="text/css" rel="stylesheet" media="all" href="/ourspaces/table.css" />
<link href="/ourspaces/jqueryFileTree.css" rel="stylesheet" type="text/css" media="screen" />


<script language="javascript" type="text/javascript">
$(document).ready(function() {
	//provVis.core.initJsPlumb();
	emphasizeChangedValues();
});
</script>



<div style="width: 946px; position: relative; float: left; margin-left: 10px; margin-top: 0px; padding: 15px; padding-top: 0px">
	<div id="columns" style="position: relative;">

		<ul id="column1" class="column" style="width: 65%">
			<!--Resources of artifact-->
			<li class="widget color-orange" id="resourceInfo">
								<jsp:include page="artifactSpace/widgets/informationTable.jsp" flush="false"/>
			</li>
			<%
			if (file.isZipFile(fileID)) {
						%>
					<li class="widget color-orange" id="resourceContent">
						<div class="widget-head">
							<h3 class="style3">Resource content</h3>
						</div>
						<div class="widget-content">
							<div class="style3">
								<jsp:include page="artifactSpace/resourceContent.jsp" flush="false"/>
							</div>
							<div>
							
							</div>
						</div>
					</li>
			 <%} %>
				<!-- Previous versions of the artifact -->
			<%if(artifact.getPreviousVersions() != null && artifact.getPreviousVersions().size() >0) {%>				
				<li class="widget color-orange" id="previousVersions">
							<jsp:include page="artifactSpace/widgets/versions.jsp" flush="false"/>	
					
				</li>
				<%}//End if - displaying blog posts %>
				
				
				
			<!-- Blogs and comments for artifact -->
			<li class="widget color-orange" id="p2">
			<%if(artifact.getBlogPosts() != null && artifact.getBlogPosts().size() >0) {%>
				<div class="widget-head">
					<h3 class="style3">Artifact blog posts</h3>
				</div>
				<div class="widget-content" id="postsandcomments">
					<div style="padding: 10px;" class="style3">
                           <jsp:include page="artifactSpace/blogsAbout.jsp" flush="false">
                             <jsp:param name="limit"  value="5" />
                             <jsp:param name="offset" value="0" />
                           </jsp:include>
                           
						<% 
						parHelp.setSessionParameter("blogPosts", artifact.getBlogPosts());
						parHelp.setSessionParameter("blogsCount", 1);
						%>
						<jsp:include page="artifactSpace/blogsAbout.jsp" flush="false">
							<jsp:param value="3" name="maxItems"/>
						</jsp:include>	
						<jsp:include page="artifactSpace/loadAdditional.jsp" flush="false">
							<jsp:param value="relatedBlogs" name="divId"/>
							<jsp:param value="getMoreRelatedBlogs.jsp" name="link"/>
						</jsp:include>						
					</div>
				</div>
				<%}//End if - displaying blog posts %>
				</li>
				
				
				<li class="widget color-orange" id="p2b">
				<div class="widget-head">
					<h3 class="style3">Artifact comments</h3>
				</div>
				<div class="widget-content" id="postsandcomments">
				<div class="widget-top" >
					      <div class="wlink"  align="right" style="float:left;  margin: 5px; ">         
									<a href="createcomment.jsp?namespace=http%3A%2F%2Fopenprovenance.org%2Fontology%23&about=<%=temp%>" title="Comment" class="popup_comment_dialog"><img src="/ourspaces/icons/001_50.png" style="float: left; border: 0pt none; margin: 0pt 5px;"/>Comment artifact </a>				           	
					      </div>					  
			   </div>
				<div style="padding: 10px;    float: left;width:90%" class="style3">
					 <% 
					 parHelp.setSessionParameter("comments", artifact.getComments());	
					 parHelp.setSessionParameter("commentsCount", 1);						
						%>						
            <jsp:include page="artifactSpace/commentsAbout.jsp" flush="false">
              <jsp:param name="limit"  value="3" />
              <jsp:param name="offset" value="0" />
            </jsp:include>						
				</div>
				</div>
				
			</li>
		


			<!-- Provenance timeline for artifact -->
			<li class="widget color-orange" id="liProvenance">
							<jsp:include page="artifactSpace/widgets/provenance.jsp" flush="false"/>	
					</li>
		</ul>
		<ul id="column2" class="column" style="width: 35%">

			<!-- Textual description of the artifact -->
			<li class="widget color-orange" id="textArtifact">
							<jsp:include page="artifactSpace/widgets/informationNLG.jsp" flush="false"/>
			</li>


			<!-- Related resources of the artifact -->
			<li class="widget color-orange" id="relatedResources">
							<jsp:include page="artifactSpace/widgets/related.jsp" flush="false"/>
				</li>

			<!-- Textual description of the provenance of the artifact -->
			<li class="widget color-orange" id="textProvenance">
							<jsp:include page="artifactSpace/widgets/provenanceNLG.jsp" flush="false"/>
				</li>

			<!-- Tag cloud for artifact -->
			<li class="widget color-orange" id="p4">
					<%	
           		session.setAttribute("tags", artifact.getTags());
	         	%>
					<jsp:include page="artifactSpace/widgets/tags.jsp" flush="false" />
					<%
     			session.setAttribute("tags", null);	         		
	         	%></li>

			<!-- Map with location of the artifact -->
			<li class="widget color-orange" id="p5">
				<%
        	if (artifact.getFeatures() != null && artifact.getFeatures().size() > 0) {
        		session.setAttribute("width", "100%");	
        		session.setAttribute("features",artifact.features);
        %>
					<jsp:include page="artifactSpace/widgets/map.jsp" flush="false" />
				<%
        		session.setAttribute("features",null);
			}//End of if- display map or not.%>
			</li>

			
			
			
</ul>

         

        </div>    <!-- end of columns -->            

          <script type="text/javascript" src="cookie.jquery.js"></script>
          <script type="text/javascript" src="inettuts.jsp?space=artifact<%=temp%>"></script>

        </div> <!-- end of home status container -->
       

  <jsp:include page="bottom.jsp" />

