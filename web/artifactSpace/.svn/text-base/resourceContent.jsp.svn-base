
<%@ page language="java"
	import="java.util.Iterator,java.util.*,java.net.*,java.text.SimpleDateFormat,java.util.ArrayList,java.io.*,java.net.*,java.util.Vector,common.*"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="blog" class="common.Blog" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="access" class="common.AccessControl" />
<jsp:useBean id="file" class="common.FileUtils" />

<% 
ParameterHelper parHelp = new ParameterHelper(request, session);
   ArtifactBean artifact = (ArtifactBean)parHelp.getParameter("artifact", null);
   String id = (String)parHelp.getParameter("id", null);
   String artifactID = org.policygrid.ontologies.OPM.NS.toString() + id;
   String url = rdf.getResourceURL(artifactID);
   String fileID = url.substring(url.lastIndexOf("/")+1);
   
	if(id!=null && !id.equals("")){
		artifact = new common.ArtifactBean();
		artifact.setArtifactID(artifactID);
	 	artifact.setRdf(rdf);
	 	artifact.loadValues();
	}
	boolean check = access.checkPublic(artifactID);

	user = (User) session.getAttribute("use");
 	boolean edit = AccessControl.checkPermission("edit", user.getFOAFID(), artifactID);
 	boolean download = AccessControl.checkPermission("download", user.getFOAFID(), artifactID);
											//fg-button-center ui-state-active fg-button-icon-right ui-corner-all
									%>
	
<div style="float:left; width: 100%; padding: 10px;"> 

<ul class="jqueryFileTree" style="padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; ">
    
                   <%
					if (!file.isZipFile(fileID)) { %>
					No zip; <%=fileID %>
					<%
					}
					if (file.isZipFile(fileID)) {
						Vector files = file.listZipFile(fileID);
						for (int x = 0; x < files.size(); x++ ) {
							String nam = (String)files.get(x);
							String fi = common.Utility.absoluteURLtoRelative(artifact.getUrl())+"/"+nam; 
							
							
							String fnam = nam.substring(nam.lastIndexOf("/")+1);
							String ext = fnam.substring(fnam.lastIndexOf(".")+1).toLowerCase();
							if (!fnam.equals("")) {
							%>
							<li class="file ext_<%=ext%>" style="padding:0px; margin:0px; font-family: Verdana, sans-serif; font-size: 11px; padding-left: 20px;">
							 <%
							 if ((check || download) && (artifact.getUrl()!=null && !artifact.getUrl().equals(""))) { %>
							   <a class="file_<%=ext%>" href="<%=fi%>"> <%=fnam%> </a>	
							   
							 <%
							 } else { %>
							  <%=fnam%>
							 <%
							 }
							 %></li><%
							}									
						}
					}
					
					%>
</ul>
</div>