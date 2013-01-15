
<%@ page language="java"
	import="java.util.Iterator,java.util.*,java.net.*,java.text.SimpleDateFormat,java.util.ArrayList,java.io.*,java.net.*,java.util.Vector,common.*"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="blog" class="common.Blog" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="access" class="common.AccessControl" />
<% 
ParameterHelper parHelp = new ParameterHelper(request, session);
   ArtifactBean artifact = (ArtifactBean)parHelp.getParameter("artifact", null);
   String id = (String)parHelp.getParameter("id", null);
	String artifactID = org.policygrid.ontologies.OPM.NS.toString() + id;
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
	 <div class="widget-top" >
			 <%if ((check || download) && (artifact.getUrl()!=null && !artifact.getUrl().equals(""))) {
			%>
		      <div class="wlink"  align="right" style="float:left;  margin: 5px; ">         
						<a href="<%=common.Utility.absoluteURLtoRelative(artifact.getUrl())%>">
						  <img src="/ourspaces/icons/001_53.png" style="border:0px;margin: 1px 10px;float:left;" align="left" border="0"/>Download</a>				           	
		      </div><%
		   }
			if ((edit || check) && artifact.lastVersion()) {%>
					<div class="wlink" style="float:left; margin-left: 5px; margin-bottom: 0px; margin-top: 5px;">
            		<a href="#" onclick="updateResource(); return false;">
            		  <img src="/ourspaces/icons/001_45.png" style="border:0px;margin: 1px 10px;float:left;" align="left" border="0"/>Edit</a>
          	</div>
			<%
		   }%>
   </div>
	
<div style="float:left; width: 100%; padding: 10px;"> 
<table id="properties" style="padding-left: 10px; float: left;" width="90%">
	<%
					Random r = new Random();
					int random;
					String property;
					String value;
					String[] props;
					String separatedClassName;
					String versionNum = null;
					//First, process properties that are not geo-properties
					for(common.Properties prop : artifact.getNiceProperties()){
				 		property = prop.getProperty();
				 		value = prop.getValue();
				 		if(property.contains("#"))
							props = property.split("#");
				 		else{
				 			props = new String[2];
				 			props[1] = property.substring(property.lastIndexOf('/')+1);
				 			props[0] = property.substring(0,property.lastIndexOf('/')-1);
				 		}
						separatedClassName = common.Utility.splitString(props[1]);
						//Skip geo-properties and multiproperties
						if(props[0].equals("http://www.policygrid.org/geo-properties.owl")||
								ResourceUploadBean.isMultiProperty(property)
								){
							continue;
						}
						// Else display the normal property
						else {
							random = r.nextInt();							
							if("Type".equals(separatedClassName)){
								//We erase the namespace of type.
								value= Utility.getLocalName(value);					
							} 
							//Do not display URI
							else if("Has U R I".equals(separatedClassName)){
								continue;					
							}							
							//Instead of timestamp, use rather date.
							if("Timestamp".equals(separatedClassName)){
								separatedClassName= "Uploaded on";
								value= rdf.getFormattedDate(Double.parseDouble(value));						
							}
							if("Version Number".equals(separatedClassName)){
								versionNum = value;
							}
							
							%>
	<jsp:include page="/artifactSpace/valueWithEditDelete.jsp"
		flush="false">
		<jsp:param value="<%=separatedClassName %>" name="separatedClassName" />
		<jsp:param value="<%=random %>" name="random" />
		<jsp:param value="<%=value %>" name="value" />
		<jsp:param value="<%=property %>" name="id" />
	</jsp:include>
	<%						
						}
					}

					//Now process only the geo-properties and multiproperties
					for(common.Properties prop : artifact.getNiceProperties()){
				 		property = prop.getProperty();
				 		value = prop.getValue();
				 		if(property.contains("#"))
							props = property.split("#");
				 		else{
				 			props = new String[2];
				 			props[1] = property.substring(property.lastIndexOf('/')+1);
				 			props[0] = property.substring(0,property.lastIndexOf('/')-1);
				 		}
						separatedClassName = common.Utility.splitString(props[1]);
						//Special handling geo-properties, which is a mess
						if(props[0].equals("http://www.policygrid.org/geo-properties.owl")){
							ArrayList feature = rdf.getGeoInformation(value);							
								random = r.nextInt();								
								//Displaying only the name of the location.
								if(feature == null || feature.size() == 0)
									continue;
								common.Properties p = (common.Properties) feature.get(0);
								if(p.getValue() == null || p.getValue().equals(""))
									continue;
								props = p.getProperty().split("#");
							%>
								<jsp:include page="/artifactSpace/valueWithEditDelete.jsp"
									flush="false">
									<jsp:param value="<%=separatedClassName %>" name="separatedClassName" />
									<jsp:param value="<%=random %>" name="random" />
									<jsp:param value="<%=p.getValue() %>" name="value" />
									<jsp:param value="<%=property %>" name="id" />
								</jsp:include>
								<%
						}
						else if(ResourceUploadBean.isMultiProperty(property)){
							String[] subProps = ResourceUploadBean.getSubProperties().get(property);
							/*String discId = rdf.getPropertyValue(value, "http://www.policygrid.org/academic-disciplines#hasAcademicDiscipline");
							String tags = rdf.getPropertyValue(value, "http://www.policygrid.org/academic-disciplines#hasDisciplineTag");*/							
							for(int i = 0; i <subProps.length;i++){
								random = r.nextInt();								
								List<String> subPropValue = rdf.getPropertyValues(value,subProps[i]);
								for(int j=0;j<subPropValue.size();j++){
								%>
									<jsp:include page="/artifactSpace/valueWithEditDelete.jsp"
										flush="false">
										<jsp:param value="<%=Utility.getLocalName(subProps[i])%>" name="separatedClassName" />
										<jsp:param value="<%=random %>" name="random" />
										<jsp:param value="<%=subPropValue.get(j) %>" name="value" />
										<jsp:param value="<%=subProps[i]%>" name="id" />
									</jsp:include>							
							<%}
							}
						}
					}%>
</table>
</div>