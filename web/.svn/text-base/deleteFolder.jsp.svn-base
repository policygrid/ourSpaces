<%@ page
	import="java.io.File,java.io.FilenameFilter,java.util.StringTokenizer, common.DirectoryNode, java.util.Arrays,java.util.Iterator, java.util.ArrayList, java.util.Vector, org.policygrid.ontologies.*, common.*"%>
	
	<jsp:useBean id="resource" class="common.Resources" />
	<jsp:useBean id="user" class="common.User" />
	<jsp:useBean id="project" class="common.Project" />
    <jsp:useBean id="file" class="common.FileUtils" />
   
    <% 
   
    String folder = (String)request.getParameter("folder");
    
	String projectID = (String)session.getAttribute("projectID");

    
    if ((folder != null) && (!folder.equals(""))){
    	  
   
    	file.deleteFolder(projectID, folder);
    	

    }
    
	projectID = projectID.substring(projectID.lastIndexOf("#")+1);
	
	response.sendRedirect("project.jsp?id="+projectID);
    
    %>