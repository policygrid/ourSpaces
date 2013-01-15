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
 	
 %>

<jsp:include page="top_head.jsp" />
<!-- menu goes here -->

<div class="navBarLeft" style="z-index; -1;" >
           <div class="dropdownBox">
              <a class="navBarLeft" href="#" style="float: left; margin-right:5px;"><img src="/ourspaces/images/spaces/Models_small.png" align="left" border="0"/>Simulation Space</a> <img src="/ourspaces/icons/dropdown.png" align="right" border="0"/>
              <div class="navBarOptions ui-corner-all" style="z-index; -1; float:left; width:220px;">
			      <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
			              <a class="navBarLeft" href="#" onclick="uploadTypeOnto('http://www.policygrid.org/provenance-simulation.owl#SimulationModel', 'simulation'); return false;" style="float:left; margin-right:5px;"><img src="/ourspaces/images/spaces/Models_small.png" align="left" border="0"/>
			              Upload new simulation model</a> 
			      </div>
			      <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
			              <a class="navBarLeft" href="#" onclick="uploadTypeOnto('http://www.policygrid.org/provenance-simulation.owl#SimulationModelInstance', 'simulation'); return false;" style="float:left; margin-right:5px;"><img src="/ourspaces/images/spaces/Models_small.png" align="left" border="0"/>
			              Upload new simulation model instance</a> 
			      </div>
			      <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
			              <a class="navBarLeft" href="#" onclick="uploadTypeOnto('http://www.policygrid.org/provenance-simulation.owl#SimulationInput', 'simulation'); return false;" style="float:left; margin-right:5px;"><img src="/ourspaces/images/spaces/Models_small.png" align="left" border="0"/>
			              Upload new simulation input</a> 
			      </div>
			      <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
			              <a class="navBarLeft" href="#" onclick="uploadTypeOnto('http://www.policygrid.org/provenance-simulation.owl#SimulationOutput', 'simulation'); return false;" style="float:left; margin-right:5px;"><img src="/ourspaces/images/spaces/Models_small.png" align="left" border="0"/>
			              Upload new simulation output</a> 
			      </div>
			      <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
			              <a class="navBarLeft" href="#" onclick="uploadTypeOnto('http://openprovenance.org/ontology#Artifact', 'simulation'); return false;" style="float:left; margin-right:5px;"><img src="/ourspaces/images/spaces/Models_small.png" align="left" border="0"/>
			              Upload new simulation resource</a> 
			      </div>
		      </div>
           </div>  
           <div class="separator">
              <img src="/ourspaces/icons/separator.png" align="right" style="margin: 0px;" border="0"/> 
           </div>     
       </div>  
<jsp:include page="top_tail.jsp" />

<link type="text/css" rel="stylesheet" media="all" href="/ourspaces/table.css" />
<link href="/ourspaces/jqueryFileTree.css" rel="stylesheet" type="text/css" media="screen" />

<div class="homeStatusContainer" style="color:#FFFFFF; position:relative;" >
	<div id="columns" style="position:relative;">
		<ul id="column1" class="column" style="width: 65%">
			<li class="widget color-orange" id="mySimulationModels">
                        <div class="widget-head">
                            <h3>My Simulations Models</h3>
                        </div>                  
							<div class="widget-top" >									
                            	<div class="wlink" style="float:left; margin-left: 5px; margin-bottom: 0px; margin-top: 5px;">
		            				<a href="#" onclick=" uploadTypeOnto('http://www.policygrid.org/provenance-simulation.owl#SimulationModel', 'simulation'); return false;">
		            		  			<img src="/ourspaces/icons/001_45.png" style="border:0px;margin: 1px 10px;float:left;" align="left" border="0"/>Upload new simulation model
		            		 		</a>
            		 			</div>
							</div>
                        <div class="widget-content" style="padding: 5px;">
	                         <jsp:include page="/boxes/resourcesByTypeList.jsp" flush="false">
	       							<jsp:param value="http://www.policygrid.org/provenance-simulation.owl#SimulationModel" name="type" />
	       							<jsp:param value="true" name="userOnly" />
       								<jsp:param value="myModels" name="divId" />
							</jsp:include>
						</div>
			</li>
			<li class="widget color-orange" id="allSimulationModels">
                        <div class="widget-head">
                            <h3>All Simulations Models</h3>
                        </div>
							<div class="widget-top" >			
							</div>
                        <div class="widget-content" style="padding: 5px;">
                         <jsp:include page="/boxes/resourcesByTypeList.jsp" flush="false">
       							<jsp:param value="http://www.policygrid.org/provenance-simulation.owl#SimulationModel" name="type" />
       							<jsp:param value="false" name="userOnly" />
       							<jsp:param value="allModels" name="divId" />
						</jsp:include>
						</div>
			</li>
			<li class="widget color-orange" id="simulationModelInstances">
                      
                        <div class="widget-head">
                            <h3>My Simulations Model Instances</h3>
                        </div>                 
							<div class="widget-top" >									
                            	<div class="wlink" style="float:left; margin-left: 5px; margin-bottom: 0px; margin-top: 5px;">
		            				<a href="#" onclick=" uploadTypeOnto('http://www.policygrid.org/provenance-simulation.owl#SimulationModelInstance', 'simulation'); return false;">
		            		  			<img src="/ourspaces/icons/001_45.png" style="border:0px;margin: 1px 10px;float:left;" align="left" border="0"/>Upload new simulation model instance
		            		 		</a>
            		 			</div>
							</div>
                        <div class="widget-content" style="padding: 5px;">
	                         <jsp:include page="/boxes/resourcesByTypeList.jsp" flush="false">
	       							<jsp:param value="http://www.policygrid.org/provenance-simulation.owl#SimulationModelInstance" name="type" />
	       							<jsp:param value="true" name="userOnly" />
       							<jsp:param value="myModelInstances" name="divId" />
							</jsp:include>
						</div>
			</li>
			<li class="widget color-orange" id="allSimulationModelInstances">
                        <div class="widget-head">
                            <h3>All Simulations Model Instances</h3>
                        </div>
							<div class="widget-top" >			
							</div>
                        <div class="widget-content" style="padding: 5px;">
                         <jsp:include page="/boxes/resourcesByTypeList.jsp" flush="false">
       							<jsp:param value="http://www.policygrid.org/provenance-simulation.owl#SimulationModelInstance" name="type" />
       							<jsp:param value="false" name="userOnly" />
       							<jsp:param value="allModelInstances" name="divId" />
						</jsp:include>
						</div>
			</li>
		</ul>
		<ul id="column2" class="column" style="width: 35%">
		
			<li class="widget color-orange" id="mySimulationResources">
                        <div class="widget-head">
                            <h3>My Simulation resources</h3>
                        </div>
							<div class="widget-top" >									
                            	<div class="wlink" style="float:left; margin-left: 5px; margin-bottom: 0px; margin-top: 5px;">
		            				<a href="#" onclick=" uploadTypeOnto('http://www.policygrid.org/provenance-simulation.owl#SimulationDataResource', 'simulation'); return false;">
		            		  			<img src="/ourspaces/icons/001_45.png" style="border:0px;margin: 1px 10px;float:left;" align="left" border="0"/>Upload new simulation resource
		            		 		</a>
            		 			</div>
							</div>
                        <div class="widget-content" style="padding: 5px;">
							Inputs:
                         <jsp:include page="/boxes/resourcesByTypeList.jsp" flush="false">
       							<jsp:param value="http://www.policygrid.org/provenance-simulation.owl#SimulationInput" name="type" />
       							<jsp:param value="true" name="userOnly" />
       							<jsp:param value="mySimulationInputs" name="divId" />
						</jsp:include>
						</div>
                        <div class="widget-content" style="padding: 5px;">
						Outputs:
                         <jsp:include page="/boxes/resourcesByTypeList.jsp" flush="false">
       							<jsp:param value="http://www.policygrid.org/provenance-simulation.owl#SimulationOutput" name="type" />
       							<jsp:param value="true" name="userOnly" />
       							<jsp:param value="mySimulationOutputs" name="divId" />
						</jsp:include>
						</div>
			</li>
			<li class="widget color-orange" id="allSimulationInputs">
                        <div class="widget-head">
                            <h3>All Simulation resources</h3>
                        </div>
							<div class="widget-top" >			
							</div>
                        <div class="widget-content" style="padding: 5px;">
							Inputs:
                         <jsp:include page="/boxes/resourcesByTypeList.jsp" flush="false">
       							<jsp:param value="http://www.policygrid.org/provenance-simulation.owl#SimulationInput" name="type" />
       							<jsp:param value="false" name="userOnly" />
       							<jsp:param value="allSimulationInputs" name="divId" />
						</jsp:include>
						</div>
                        <div class="widget-content" style="padding: 5px;">
						Outputs:
                         <jsp:include page="/boxes/resourcesByTypeList.jsp" flush="false">
       							<jsp:param value="http://www.policygrid.org/provenance-simulation.owl#SimulationOutput" name="type" />
       							<jsp:param value="false" name="userOnly" />
       							<jsp:param value="allSimulationOutputs" name="divId" />
						</jsp:include>
						</div>
			</li>
			<li class="widget color-white" id="tags">  
                        <div class="widget-head">
                            <h3>Simulation Tags</h3>
                        </div>
                        <div class="widget-content">
                            <jsp:include page="/simulation/simulationtags.jsp" flush="false"/>
                        </div>
                        
                    </li>
		</ul>

         

	</div>    <!-- end of columns -->            

<script type="text/javascript" src="cookie.jquery.js"></script>
<script type="text/javascript" src="inettuts.jsp?space=simulation"></script>

</div> <!-- end of home status container -->
       

  <jsp:include page="bottom.jsp" />

