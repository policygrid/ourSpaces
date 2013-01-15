<%@ page
	import="java.io.File,java.io.FilenameFilter,java.util.StringTokenizer, common.DirectoryNode, java.util.Arrays,java.util.Iterator, java.util.ArrayList, java.util.Vector, org.policygrid.ontologies.*, common.*"%>
	
	<jsp:useBean id="resource" class="common.Resources" />
	<jsp:useBean id="user" class="common.User" />
	<jsp:useBean id="project" class="common.Project" />
    <jsp:useBean id="file" class="common.FileUtils" />
   
    <% 
    String fileID = (String)request.getParameter("fileID");
    String folder = (String)request.getParameter("folder");
    String optSubFolder = (String)request.getParameter("optSubFolder");
    
    if ((fileID != null) && (!fileID.equals("")) && (folder != null) && (!folder.equals(""))){
    	
    	String path = folder;
    	if ((optSubFolder != null) && (!optSubFolder.equals(""))) { 
    		
    		if (path.equals("/")) { 
    			path = path + optSubFolder;
    		} else {	
    		path = path + "/" + optSubFolder;
    		}
    		
    	}
    	file.moveFilePath(fileID,path);
    	
    	common.Utility.log.debug(""+request.getRequestURI());
    	common.Utility.log.debug(""+request.getRequestURL());
    	

    }
    
	String projectID = (String)session.getAttribute("projectID");
	projectID = projectID.substring(projectID.lastIndexOf("#")+1);
	
	response.sendRedirect("project.jsp?id="+projectID);
    
    %>