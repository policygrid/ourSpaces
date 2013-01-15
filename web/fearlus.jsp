
<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
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
    
<jsp:include page="top_head.jsp" />
<!-- menu goes here -->
<jsp:include page="top_tail.jsp" />


<%if (session.isNew()==true){ %>
<jsp:forward page="error.jsp" />
<% } %>

<% 
if( session.getAttribute("use") == null){ %>
<jsp:forward page="error.jsp" />
<% }

user = (User) session.getAttribute("use");
%>

<link type="text/css" rel="stylesheet" media="all" href="/ourspaces/table.css" />
	 
      
      
 <div class="homeStatusContainer" style="color:#FFFFFF; position:relative;" >
 
  
             <div id="columns" style="position:relative;">
                
                <ul id="column1" class="column" style="width: 660px;">
                    <li class="widget color-blue " id="sims1">
                        <div class="widget-head">
                            <h3>Description</h3>
                        </div>
                        <div class="widget-content" style="padding: 5px;">
                        <div class="projectTitle" style="padding: 10px;">
		                    <span>FEARLUS </span>
		            	</div>
		            	     <img src="img/fearlus_upload.jpg" style="position: relative; float: left;"></img>                    
                           <p>
                          FEARLUS (Framework for the Evaluation and Assessment of Regional Land Use Scenarios) was originally conceived as a
                          Scottish Government-funded project involving Nick Gotts, Alistair Law and Gary Polhill, which ran from 1998-2003. 
                          FEARLUS now refers to the agent-based modelling software developed to explore land use change using different assumptions about the
                           motivations of land managers. It is used in various projects involving collaborators from around the world.
                           </p>
                           
                           <!-- 
                           <br />
                           <h2>Usage </h2>
                           
                           <img src="img/FearlusTrends.jpg" style="position: relative; float: left;"></img>
                           -->
                           
                        </div>
                    </li>
                    <li class="widget color-blue " id="sims1a">
                        <div class="widget-head">
                            <h3>Blogs</h3>
                        </div>
                        <div class="widget-content" style="padding: 5px;">
                                    
                          <p>
                          <a href="">Implementation of Enhancements - Author: Gary Polhill</a>
                          </p>
                          
                          <p>
                          <a href="">Experimental Setups - Author: Gary Polhill</a>
                          </p>
                           
                           <!-- 
                           <br />
                           <h2>Usage </h2>
                           
                           <img src="img/FearlusTrends.jpg" style="position: relative; float: left;"></img>
                           -->
                           
                        </div>
                    </li>
                   
                    <li class="widget color-blue" id="sims7">  
                        <div class="widget-head">
                            <h3>Experiments</h3>
                        </div>
                        <div class="widget-content" style="padding: 5px;">
                          <table id="rounded-corner" style="width: 620px;">  
  
							    <!-- Table header -->  
							  
							        <thead>  
							            <tr>  
							                <th scope="col" class="rounded-company" id="e1">Title</th>
							                <th scope="col" class="rounded-q1"id="e2">Creation Date</th>
							                <th scope="col" class="rounded-q1"id="e3">Description</th>
							                <th scope="col" class="rounded-q1"id="e4">Runs</th>             
							                <th scope="col" class="rounded-q4" id="e6">Model Version</th>        
							            </tr>  
							        </thead>  
							  
												
								  <tfoot>  
						            <tr>
						            <td colspan="5" class="rounded-foot-left"><a href="#"><em>Click for more ...</em></a></td>
						            </tr>  
						        </tfoot> 		  
							    <!-- Table body -->  
							  
							        <tbody>  
							            <tr>  
							                <td>Deeside Experiment</td> 
							                <td>12/05/2006</td>
							                <td>Land use experiment based on the Deeside area of Scotland</td>
							                <td>55</td>    
							                <td>0.5.0</td>   
							            </tr>
							             						           

							        </tbody>  
							  
							</table>  
                        </div>
                    </li>
                    
                    <li class="widget color-blue " id="sims113">  
                        <div class="widget-head">
                            <h3>Data</h3>
                        </div>
                        <div class="widget-content" style="padding: 5px;">
                          Parameter Sets
                          <table id="rounded-corner" style="width: 620px;">  
  
							    <!-- Table header -->  
							  
							        <thead>  
							            <tr>  
							                <th scope="col" class="rounded-company" id="e1">Name</th>
							                <th scope="col" class="rounded-q1"id="e2">Creation Date</th>
							                <th scope="col" class="rounded-q1"id="e3">Description</th>           
							                <th scope="col" class="rounded-q4" id="e6">Model Version</th>        
							            </tr>  
							        </thead>  
							  
												
								  <tfoot>  
						            <tr>
						            <td colspan="5" class="rounded-foot-left"><a href="#"><em>Click for more ...</em></a></td>
						            </tr>  
						        </tfoot> 		  
							    <!-- Table body -->  
							  
							        <tbody>  
							            <tr>  
							                <td>Deeside Experiment Set 1</td> 
							                <td>12/05/2010</td>
							                <td>Test Parameter Set</td>
							                <td>0.5.0</td>   
							            </tr>
							             <tr>  
							                <td>Deeside Experiment Set 2</td> 
							                <td>12/05/2010</td>
							                <td>Test Parameter Set</td>  
							                <td>0.5.0</td>   
							            </tr> 
							           

							        </tbody>  
							  
							</table>  
							Reports
                          <table id="rounded-corner" style="width: 620px;">  
  
							    <!-- Table header -->  
							  
							        <thead>  
							            <tr>  
							                <th scope="col" class="rounded-company" id="e1">Name</th>
							                <th scope="col" class="rounded-q1"id="e2">Creation Date</th>
							                <th scope="col" class="rounded-q1"id="e3">Parameret Set</th>            
							                <th scope="col" class="rounded-q4" id="e6">Model Version</th>        
							            </tr>  
							        </thead>  
							  
												
								  <tfoot>  
						            <tr>
						            <td colspan="5" class="rounded-foot-left"><a href="#"><em>Click for more ...</em></a></td>
						            </tr>  
						        </tfoot> 		  
							    <!-- Table body -->  
							  
							        <tbody>  
							            <tr>  
							                <td>Report 1</td> 
							                <td>12/06/2010</td>
							                <td>Deeside Experiment Set 2</td> 
							                <td>0.5.0</td>   
							            </tr>
						                <tr>  
							                <td>Report 2</td> 
							                <td>12/06/2010</td>
							                <td>Deeside Experiment Set 2</td> 
							                <td>0.5.0</td>   
							            </tr>
							         
							       

							        </tbody>  
							  
							</table> 
                        </div>
                    </li>
                     
                </ul>
                      
                <ul id="column3" class="column">
                    <li class="widget color-blue " id="sims75">  
                        <div class="widget-head">
                            <h3>Options</h3>
                        </div>
                        <div class="widget-content" style="padding: 5px;">
                            <a href="#">Upload Model Resources</a> <br>
                            <a href="#">Send Message to Developers</a> <br>
                            <a href="#">Send Message to Users</a> <br>
                        </div>
                    </li>
                    <li class="widget color-blue " id="sims15">  
                        <div class="widget-head">
                            <h3>Information</h3>
                        </div>
                        <div class="widget-content" style="padding: 5px;">
                            <strong>Release Date:</strong> 20/11/2006 <br />
                            <strong>Platform:</strong> Linux, Windows, OS X, Solaris <br />  
                            <strong>Language:</strong> Objective C
                        </div>
                    </li>
                    <li class="widget color-blue " id="sims60">  
                        <div class="widget-head">
                            <h3>Tags</h3>
                        </div>
                        <div class="widget-content" style="padding: 5px;">
                            <jsp:include page="boxes/mytags.jsp" flush="false"/>
                        </div>
                    </li>
                    
                     <li class="widget color-blue " id="widget2">  
                        <div class="widget-head">
                            <h3>Versions</h3>
                        </div>
                        <div class="widget-content" style="padding: 5px;">
                            <table id="rounded-corner" style="width: 290px;">  
  
							    <!-- Table header -->  
							  
							        <thead>  
							            <tr>  
							                <th scope="col" class="rounded-company" id="V1">Version</th>
							                <th scope="col" class="rounded-q1"id="V2">Release Date</th>
							                <th scope="col" class="rounded-q4" id="V3">Platform</th>        
							            </tr>  
							        </thead>  
							  
												
								  <tfoot>  
						            <tr>
						            <td colspan="4" class="rounded-foot-left"><a href="#"><em>Click for more ...</em></a></td>
						            </tr>  
						        </tfoot> 		  
							    <!-- Table body -->  
							  
							        <tbody>  
							            <tr>  
							                <td>0.3.0</td> 
							                <td>12/03/2001</td>
							                <td>Linux</td>   
							            </tr>	            
							            <tr>  
							                <td>0.5.0</td> 
							                <td>12/03/2002</td>
							                <td>Linux</td>   
							            </tr> 
							            

							        </tbody>  
							  
							</table>  
                        </div>
                    </li>
                    <li class="widget color-blue " id="widget10">  
                        <div class="widget-head">
                            <h3>Developers</h3>
                        </div>
                        <div class="widget-content" style="padding: 5px;">
                             <jsp:include page="boxes/fearlusDevelopers.jsp" flush="false"/>
                        </div>
                    </li>
                    <li class="widget color-blue " id="widget11">  
                        <div class="widget-head">
                            <h3>Users</h3>
                        </div>
                        <div class="widget-content" style="padding: 5px;">
                            <jsp:include page="boxes/fearlusUsers.jsp" flush="false"/>
                        </div>
                    </li>
                     <li class="widget color-blue " id="widget12">  
                        <div class="widget-head" >
                            <h3>Related Projects</h3>
                        </div>
                        <div class="widget-content" style="padding: 5px;">
                            <a href="project.jsp?id=aadb9597-6392-42e0-9815-489cbe80cd19">Policy Grid II</a> <br>
                            <a href="project.jsp?id=7b9a187f-fe40-475e-9a90-1f6ba91fe0a9">SwarmClound</a><br>
                        </div>
                    </li>
                    
                </ul>
                
            </div> <!-- end of columns -->
        
            <script type="text/javascript" src="jquery-ui-personalized-1.6rc2.min.js"></script>
            <script type="text/javascript" src="cookie.jquery.js"></script>
            <script type="text/javascript" src="inettuts.jsp?space=simulation"></script>
            
        </div> <!-- end of home status container -->
       
  <jsp:include page="bottom.jsp" />

