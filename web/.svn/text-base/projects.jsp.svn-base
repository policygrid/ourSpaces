<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.Project" />
<jsp:useBean id="rdf" class="common.RDFi" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<jsp:include page="top_head.jsp" />
<div class="navBarLeft" style="z-index; -1;" >
           <div class="dropdownBox">
              <a class="navBarLeft" href="#" style="float: left; margin-right:5px;"><img src="/ourspaces/images/spaces/Projects_small.png" align="left" border="0"/>Projects Space</a> <img src="/ourspaces/icons/dropdown.png" align="right" border="0"/>
              <div class="navBarOptions ui-corner-all" style="z-index; -1; float:left; width:220px;">
		           <div style="float:left; width: 220px;  margin-left: 5px; margin-bottom: 10px;">
		             <a class="popup_dialog" title="Create new Project" href="createprojectform.jsp" ><img src="/ourspaces/images/spaces/Projects_small.png" align="left" border="0"/>Create New Project</a>
		           </div>
		      </div>
           </div>  
           <div class="separator">
              <img src="/ourspaces/icons/separator.png" align="right" style="margin: 0px;" border="0"/> 
           </div>     
       </div>  
<jsp:include page="top_tail.jsp" />

<%

User user = (User) session.getAttribute("use");
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));

ArrayList projects = project.getAllProjectsAboutUser(user.getFOAFID());

ArrayList allProjects = project.getAllProjectsWithSub();

Vector tags = project.getAllProjectTags();



// ----------- PAGES TOP JSP    
String pp = request.getParameter("page");

int spage = 1;

if (pp != null) {
spage = Integer.parseInt(request.getParameter("page"));
} 

int itemsPerPage = 10;
int totalPages = 0;

int items = allProjects.size() +1;
//----------- END OF PAGES TOP JSP    

%>

<div class="homeStatusContainer" style="color:#FFFFFF; position:relative;" >
 

      
 
             <div id="columns" style="position:relative;">
                
                <ul id="column1" class="column" style="width: 660px;">
                <li class="widget color-white" id="pr1">
                        <div class="widget-head">
                            <h3>My Projects</h3>
                        </div>
                        <div class="widget-content" style="padding: 5px;">
                        
                        
                        
                        
                        <div style="float:left; padding:5px">
									<table border="0" cellspacing="5" cellpadding="3" width="100%">
										 <%
										 for(int i = 0; i < projects.size(); i++)
							  				{
							  					ProjectBean pb = (ProjectBean) projects.get(i);
							  					String separatedClassName = Utility.splitString(pb.getRole());
							  					String encRes = URLEncoder.encode(pb.getURI());
									  		
										%>
											 <tr>
												<td rowspan="2" style="">
													<img src="images/spaces/Projects_small.png" align="middle"  style="float: left; margin-top: auto; margin-bottom:auto;" />
											    </td>
												<td>
													<a href="project.jsp?id=<%=pb.getURL()%>"><%=pb.getTitle()%></a>&nbsp;<span class="nlg" rel="<%=encRes%>"></span>
												</td>
											</tr>
											<tr>
												<td wodth="100%">
												  <span style="font-size:10px; color:#666;">
												  	<%=pb.getSubtitle()%>
											  	  </span>
												</td>
												<td align="right" style="font-size:10px; color:#FF6600;">
													<%=separatedClassName %>
												</td>
											</tr>
										<% } %>
									</table>
					</div>    
                        
                                                                   
                          
                        </div>
                    </li>
                 <li class="widget color-white" id="pr0">
                        <div class="widget-head">
                            <h3>All Projects</h3>
                        </div>
                        <div class="widget-content" style="padding: 10px;">
                           <jsp:include page="project/projectsList.jsp" flush="false">
                             <jsp:param name="limit"  value="10" />
                             <jsp:param name="offset" value="0" />
                           </jsp:include>                                
                        </div>
                    </li>       
                </ul>
                      
                <ul id="column3" class="column">
                    <li class="widget color-white" id="p4">  
                        <div class="widget-head">
                            <h3>Tag CLoud</h3>
                        </div>
                        <div class="widget-content" style="padding: 10px;">
                          
                          <%
              		Iterator y = tags.iterator();
        			int count = 0;
        			while(y.hasNext()){
        				if (count == 300) 
        					break;
        					tag = (Tag) y.next();
        					String tagName = tag.getTag();
        					int tagSize = tag.getSize();
        					
        					if(tagSize < 2) { %>
        						<span style="font-size:10px; padding-top:2px; margin-top:2px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
        				<%	}
        					else if((tagSize >= 2) && (tagSize < 6)) { %>
        						<span style="font-size:12px; padding-top:2px; margin-top:2px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
        				<%	}
        					else if((tagSize >= 6) && (tagSize < 10)) { %>
        						<span style="font-size:14px; padding-top:2px; margin-top:2px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
        				<%	}
        					else if((tagSize >= 10) && (tagSize < 14)) { %>
        						<span style="font-size:16px; padding-top:2px; margin-top:2px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
        				<%	}
        					else if((tagSize >= 14) && (tagSize < 18)) { %>
        						<span style="font-size:18px; padding-top:2px; margin-top:2px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
        				<%	}
        					else  { %>
        						<span style="font-size:20px; padding-top:2px; margin-top:2px;"><a class="tag" href="tagsearch.jsp?tag=<%=tagName%>"><%=tagName%></a></span>
        				<%	}
        					count++;
        				
        			}
        		  %>
                          
                        </div>
                    </li>
                </ul>
                
            </div> <!-- end of columns -->

            <script type="text/javascript" src="cookie.jquery.js"></script>
            <script type="text/javascript" src="inettuts.jsp?space=projects"></script>
            
    </div>  <!-- end of home status container -->        






<jsp:include page="bottom.jsp" />
