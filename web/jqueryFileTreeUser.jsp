<%@ page
	import="java.io.File,java.io.FilenameFilter,java.util.Arrays,java.util.Iterator, java.util.ArrayList, java.util.Vector, org.policygrid.ontologies.*, common.*"%>
	
	<jsp:useBean id="resource" class="common.Resources" />
	<jsp:useBean id="user" class="common.User" />
	<jsp:useBean id="project" class="common.Project" />
    <jsp:useBean id="file" class="common.FileUtils" />
    
<%
/**
  * jQuery File Tree JSP Connector
  * Version 1.0
  * Copyright 2008 Joshua Gould
  * 21 April 2008
*/	
    String dir = request.getParameter("dir");

			if (dir == null) {
				return;
			}
   
	dir = java.net.URLDecoder.decode(dir);
			 
    user = (User)session.getAttribute("use");
    
	Vector resources = user.getResources(user.getID());
			
	
    out.print("<ul class=\"jqueryFileTree\" style=\"display: none;\">");
    
    ArrayList folders = file.getFoldersByUserID(user.getID());
    
    String previous = "";
	
    ArrayList printedFolders = new ArrayList();
	
	for (int j = 0; j < folders.size(); j++) {
		String folder = (String)folders.get(j);
		if(folder == null)
			continue;
		
		String relativeFolder = dir; 
		
		if (dir.endsWith("/") && (dir.length() > 1)) dir = dir.substring(0,dir.length()-1);
		
		//if the folder begins with the same dir
		if (folder.startsWith(dir)) {
			String tmp = folder.substring(dir.length());
			if (tmp.startsWith("/")) tmp = tmp.substring(1);
			
			if (tmp.indexOf("/") > -1) tmp = tmp.substring(0,tmp.indexOf("/"));
			
			//common.Utility.log.debug("The TMP string is "+tmp);
	    
        
				if ((!folder.equals(dir)) && (!printedFolders.contains(tmp))) {
				  String folderName = folder.substring(folder.lastIndexOf('/')+1);
				  printedFolders.add(tmp);
				  String projectID = file.getContainerIDofFolder(user.getID(),folder);
				  if (!projectID.equals("")){
				    out.print("<li class=\"directory collapsed\"><a href=\"#\" rel=\""+folder+"/\">("+project.getTitle(projectID)+") "+folderName+"</a></li>");
				  } else {
					  out.print("<li class=\"directory collapsed\"><a href=\"#\" rel=\""+folder+"/\">"+folderName+"</a></li>");
				  }
				}
		
		}
		
		
		previous = relativeFolder;
		
		
	}
    
    for (int i = 0; i < resources.size(); i++) {
    	Resources res = (Resources)resources.get(i);
    	
    	String url = res.getID();
    	String id = url.substring(url.lastIndexOf('#')+1);
    	
    	String url1 = res.getURL();
    	//If there is no URL, continue.
    	if(url1 == null)
    		continue;
    	String id1 = url1.substring(url1.lastIndexOf('/')+1);
    	//common.Utility.log.debug("The ID is: "+id1);
    	
    	String filepath = file.getFilePath(id1);
    	//common.Utility.log.debug("The file "+res.getTitle()+" has path "+filepath);
    	
    	if (filepath.equals(dir)) {
    	 String name = file.getFileName(id1);	
    	 String ext = "txt";
    	 if (name.contains(".")) {
    		ext = name.substring(name.lastIndexOf(".")+1); 
    	 }
    	 
    	 //common.Utility.log.debug("File ID1 "+id1);
    	 
    	 String projectID = file.getContainerIDofFile(id1);
    	 //common.Utility.log.debug("Project ID "+projectID);
		  if (!projectID.equals("")){
		    out.print("<li class=\"file ext_"+ext+"\"><a href=\"#\" rel=\""+id+"\">("+project.getTitle(projectID)+") "+res.getTitle()+"</a></li>");
		  } else {
    	 
    	 out.print("<li class=\"file ext_"+ext+"\"><a href=\"#\" rel=\""+id+"\">"+res.getTitle()+"</a></li>");
		  }
    	}
    }
    
    /*
    //Dirs
    out.print("<li class=\"directory collapsed\"><a href=\"#\" rel=\"/test/a/\">test1</a></li>");
    out.print("<li class=\"directory collapsed\"><a href=\"#\" rel=\"/test/b/\">test2</a></li>");
    out.print("<li class=\"directory collapsed\"><a href=\"#\" rel=\"/test/c/\">test3</a></li>");
    
    //Files
    out.print("<li class=\"file ext_pdf\"><a href=\"#\" rel=\"/test/ab\">testabc</a></li>");
    	
    */
    
    out.print("</ul>");
    
  
	
    /*
	if (dir.charAt(dir.length()-1) == '\\') {
    	dir = dir.substring(0, dir.length()-1) + "/";
	} else if (dir.charAt(dir.length()-1) != '/') {
	    dir += "/";
	}
    if (new File(dir).exists()) {
		String[] files = new File(dir).list(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
				return name.charAt(0) != '.';
		    }
		});
		Arrays.sort(files, String.CASE_INSENSITIVE_ORDER);
		out.print("<ul class=\"jqueryFileTree\" style=\"display: none;\">");
		// All dirs
		for (String file : files) {
		    if (new File(dir, file).isDirectory()) {
				out.print("<li class=\"directory collapsed\"><a href=\"#\" rel=\"" + dir + file + "/\">"
					+ file + "</a></li>");
		    }
		}
		// All files
		for (String file : files) {
		    if (!new File(dir, file).isDirectory()) {
				int dotIndex = file.lastIndexOf('.');
				String ext = dotIndex > 0 ? file.substring(dotIndex + 1) : "";
				out.print("<li class=\"file ext_" + ext + "\"><a href=\"#\" rel=\"" + dir + file + "\">"
					+ file + "</a></li>");
		    	}
		}
		out.print("</ul>");
		
    }
    */
%>