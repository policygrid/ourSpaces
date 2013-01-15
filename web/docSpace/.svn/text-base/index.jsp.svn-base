<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.Project" />
<jsp:useBean id="rdf" class="common.RDFi" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<jsp:include page="/top_head.jsp" />
<div class="navBarLeft" style="z-index; -1;" >
           <div class="dropdownBox">
              <a class="navBarLeft" href="#" style="float: left; margin-right:5px;"><img src="/ourspaces/images/spaces/Documents_small.png" align="left" border="0"/>Document Space</a> <img src="/ourspaces/icons/dropdown.png" align="right" border="0"/>
              <div class="navBarOptions ui-corner-all" style="z-index; -1; float:left; width:220px;">
			      <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
			              <a class="navBarLeft" href="#" onclick="showUploadDialog({});" style="float:left; margin-right:5px;"><img src="/ourspaces/images/spaces/Documents_small.png" align="left" border="0"/>Upload Document</a> 
			      </div>
		      </div>
           </div>  
           <div class="separator">
              <img src="/ourspaces/icons/separator.png" align="right" style="margin: 0px;" border="0"/> 
           </div>     
       </div>  
<jsp:include page="/top_tail.jsp" />

<%

User user = (User) session.getAttribute("use");
int id =user.getID();
if (id == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));



%>

<div class="homeStatusContainer" style="color:#FFFFFF; position:relative;" >
	<div id="columns" style="position:relative;">
		<ul id="column1" class="column" style="width: 600px;">
                <li class="widget color-white" id="com1">
                        <div class="widget-head">
                            <h3>My Documents</h3>
                        </div>
                        <div class="widget-content" id="myDocuments"  style="padding: 5px;">
                        <%--  <jsp:include page="/docSpace/docList.jsp" flush="false">
                             <jsp:param name="limit"  value="8" />
                             <jsp:param name="offset" value="0" />
                           </jsp:include>    
                         --%>
                         <jsp:include page="/boxes/resourcesByTypeList.jsp" flush="false">
       							<jsp:param value="http://www.policygrid.org/provenance-generic.owl#Paper" name="type" />
       							<jsp:param value="true" name="userOnly" />
       							<jsp:param value="myDocuments" name="divId" />
						</jsp:include>
                        </div>
                    </li>
                    <li class="widget color-white" id="com1">
                        <div class="widget-head">
                            <h3>All Documents</h3>
                        </div>
                        <div class="widget-content" id="allDocuments" style="padding: 5px;">
        				 <jsp:include page="/boxes/resourcesByTypeList.jsp" flush="false">
       							<jsp:param value="http://www.policygrid.org/provenance-generic.owl#Paper" name="type" />
       							<jsp:param value="false" name="userOnly" />
       							<jsp:param value="allDocuments" name="divId" />
						</jsp:include>
                        </div>
                    </li>
                </ul>
                      
                <ul id="column3" class="column" style="width: 400px;">
                                       <li class="widget color-white" id="com3">  
                        <div class="widget-head">
                            <h3>Documents Tags</h3>
                        </div>
                        <div class="widget-content">
                            <jsp:include page="../boxes/tagsByType.jsp" flush="false">
                            	<jsp:param name="type" value="http://www.policygrid.org/provenance-generic.owl#Paper,http://www.policygrid.org/provenance-generic.owl#Report,http://www.policygrid.org/provenance-generic.owl#Documentation,http://www.policygrid.org/provenance-generic.owl#Presentation"/>
                            </jsp:include>
                        </div>
                    </li>
                </ul>
                
            </div> <!-- end of columns -->

           
            
    </div>  <!-- end of home status container -->        






<jsp:include page="/bottom.jsp" />
