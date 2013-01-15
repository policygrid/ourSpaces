<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="blog" class="common.Blog" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>


<%if (session.isNew()==true){ %>
<jsp:forward page="error.jsp" />
<% } %>

<% 
if( session.getAttribute("use") == null){ %>
<jsp:forward page="error.jsp" />
<% }

User user = (User) session.getAttribute("use");


int id = user.getID();

String rdfUserID = user.getUserRDFID(id);
String profileContainer = rdf.getUserProfileContainer(rdfUserID);
ArrayList blogContainer = rdf.getBlogContainer(profileContainer);

String blogContainerID = (String) blogContainer.get(0);
String[] fields = blogContainerID.split("#");

ArrayList blogPosts = blog.getBlogPosts((String)blogContainer.get(0));
String output = "blog.jsp?id="+user.getID();
String out2 = URLEncoder.encode(output);
%>


<jsp:include page="top_head.jsp" />
<script type="text/javascript">

$.ajax({
	type: 'GET',
	url: "/ourspaces/plsservlet?type=notify", 
	dataType : "html",
	async : false,
	success : function(html, errorThrown) {
		
	}
});

</script>
      <div class="navBarLeft">
           <div class="dropdownBox">
              <a class="navBarLeft" href="#" style="float: left;"><img src="/ourspaces/images/spaces/Home_small.png" align="left" border="0"/>My Home Space </a> <img src="/ourspaces/icons/dropdown.png" align="right" border="0"/>
              <div class="navBarOptions ui-corner-all" style="font-size: 16px; width:220px;">
               <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
		         <span style="font-weight: 900;"> Options:</span>
		         </div>
		         <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px; border-bottom: 1px solid #FF6600"></div> 
		         
		           
		           <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
		             <a href="#" onclick="upload();"><img src="/ourspaces/icons/001_58.png" align="left" border="0"/>Upload New Resource</a>
		           </div>
		           <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
		             <a class="popup_dialog" title="Create new Project" href="createprojectform.jsp" ><img src="/ourspaces/images/spaces/Projects_small.png" align="left" border="0"/>Create New Project</a>
		           </div>
		           <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
		             <a class="popup_blog_dialog" title="New Blog Post" href="createblogpost.jsp?container=<%=fields[1] %>&output=<%=out2 %>"><img src="/ourspaces/icons/001_31.png" align="middle" style="float: left; display:inline-block; border: 0px none;"/><span style="margin-left:8px;">Create New Blog Post</span></a>                  
		           </div>
                   <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
        				<a href="mycontacts.jsp"  style=""><img src="/ourspaces/icons/001_57.png" align="middle" style="float: left; display:inline-block; border: 0px none;"/><span style="margin-left:8px;">Add More Contacts</span></a>
			      </div>
		      </div>
           </div>       
       </div>


<jsp:include page="top_tail.jsp" />



        <div class="homeStatusContainer" style="color:#FFFFFF; position:relative;" >
             <div id="columns" style="position:relative;">
                
                <ul id="column1" class="column">
                    <li class="widget color-orange" id="myresources">
                        <div class="widget-head">
                            <h3>My Resources</h3>
                        </div>
                        <div class="widget-content">                        
												   <div class="widget-top" >
												      <div class="wlink"  align="right" style="float:right;  margin-left: 5px; margin-right: 10px; margin-bottom: 0px; padding: 10px;">
												         <a href="#"  style="" onclick="upload();"><img src="/ourspaces/icons/001_58.png" align="middle" style="float: left; display:inline-block; margin: -2px; border: 0px none;"/><span style="margin-left:8px;">Upload New Resource</span></a>
												      </div>
												   </div>
                           <jsp:include page="boxes/myresources.jsp" flush="false"/>
                        </div>
                    </li>
                    <li class="widget color-orange" id="myprocesses">
                        <div class="widget-head">
                            <h3>My Processes</h3>
                        </div>
                        <div class="widget-content">                        
												   <div class="widget-top" >
												      <div class="wlink"  align="right" style="float:right;  margin-left: 5px; margin-right: 10px; margin-bottom: 0px; padding: 10px;">
												        <a href="#"  style="" onclick="uploadType('http://openprovenance.org/ontology#Process','#myprocesses #processesList','/ourspaces/boxes/myprocesses.jsp' );"><img src="/ourspaces/icons/process.png" align="middle" style="float: left; display:inline-block; margin: -2px; border: 0px none;"/><span style="margin-left:8px;">Create New Process</span></a>
												       </div>
												   </div>
                           <jsp:include page="boxes/myprocesses.jsp" flush="false"/>
                        </div>
                    </li>
                    <li class="widget color-orange" id="widget2">  
                        <div class="widget-head">
                            <h3>My Tags</h3>
                        </div>
                        <div class="widget-content">
                            <jsp:include page="boxes/mytags.jsp" flush="false"/>
                        </div>
                    </li>
                    <li class="widget color-orange" id="widget7">  
                        <div class="widget-head">
                            <h3>My Blog</h3>
                        </div>
                        <div class="widget-content">
                           <jsp:include page="boxes/myblog.jsp" flush="false"/>
                        </div>
                    </li>
                      <li class="widget color-orange" id="widgetCom">  
                        <div class="widget-head">
                            <h3>My Communications</h3>
                        </div>
                        <div class="widget-content">
                           <jsp:include page="boxes/mycommunications.jsp" flush="false"/>
                        </div>
                    </li>
                </ul>
        
                <ul id="column2" class="column">
                	<li class="widget color-orange" id="widget4">  
                        <div class="widget-head">
                            <h3>My Projects</h3>
                        </div>
                        <div class="widget-content">
                          <jsp:include page="boxes/myprojects.jsp" flush="false"/>
                        </div>
                    </li>
                    <li class="widget color-orange" id="widget3">  
                        <div class="widget-head">
                            <h3>My Activities</h3>
                        </div>
                        <div class="widget-content">
                          <jsp:include page="boxes/myactivities.jsp" flush="false"/>
                        </div>
                    </li>
                </ul>
                
                <ul id="column3" class="column">
                    <li class="widget color-orange" id="widget5">  
                        <div class="widget-head">
                            <h3>My Contacts</h3>
                        </div>
                        <div class="widget-content">
                           <jsp:include page="boxes/mycontacts.jsp" flush="false"/>
                        </div>
                    </li>
                      <li class="widget color-orange" id="widgetImpact">  
                        <div class="widget-head">
                            <h3>My Impacts</h3>
                        </div>
                        <div class="widget-content">              
												   <div class="widget-top" >
												      <div class="wlink"  align="right" style="float:right;  margin-left: 5px; margin-right: 10px; margin-bottom: 0px; padding: 10px;">
												         <a href="#"  style="" onclick="uploadType('http://www.policygrid.org/provenance-impact.owl#Impact','#impactList','/ourspaces/boxes/myimpacts.jsp' );"><img src="/ourspaces/images/spaces/Impact_small.png" align="middle" style="float: left; display:inline-block; margin: -2px; border: 0px none;"/><span style="margin-left:8px;">Create New Impact</span></a>
												      </div>
												   </div>
                           <jsp:include page="boxes/myimpacts.jsp" flush="false"/>
                        </div>
                    </li>
                    <li class="widget color-orange" id="widget6">  
                        <div class="widget-head">
                            <h3>Our Activities</h3>
                        </div>
                        <div class="widget-content">
                            <jsp:include page="boxes/ouractivities.jsp" flush="false"/>
                        </div>
                    </li>
                    
                </ul>
                
            </div> <!-- end of columns -->
           <!--  <script type="text/javascript" src="jquery-ui-personalized-1.6rc2.min.js"></script> -->
            <script type="text/javascript" src="cookie.jquery.js"></script>
            <script type="text/javascript" src="inettuts.jsp?space=home"></script>
            
        </div> <!-- end of home status container -->
    
<jsp:include page="bottom.jsp" />