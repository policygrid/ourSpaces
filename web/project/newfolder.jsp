<%@ page
	import="java.io.File,java.io.FilenameFilter,java.util.StringTokenizer, common.DirectoryNode, java.util.Arrays,java.util.Iterator, java.util.ArrayList, java.util.Vector, org.policygrid.ontologies.*, common.*"%>
	
	<jsp:useBean id="resource" class="common.Resources" />
	<jsp:useBean id="user" class="common.User" />
	<jsp:useBean id="project" class="common.Project" />
    <jsp:useBean id="file" class="common.FileUtils" />
    

<script>
function showOptionalSubfolder()
{
	document.getElementById('optSub').style.display='block';
	document.getElementById('optSubSwitch').style.display='none';	
}

function hideOptionalSubfolder()
{
	document.getElementById('optSub').style.display='none';
	document.getElementById('optSubSwitch').style.display='block';	
}

function changeDir(directory)
{
	document.getElementById('directory').value=directory;
	document.getElementById('dirStuff').style.display='block';
}
</script>

<%!
   public String printFolders(ArrayList root, int level)
   {
	
    String ret = "<ul class=\"jqueryFileTree\" style=\"padding-left: 10px;\">\n";
    
    for (int i = 0; i < root.size(); i++) {
    	DirectoryNode dn = (DirectoryNode)root.get(i);
    	ret = ret + "<li class=\"directory collapsed\"><a href=\"#\" onclick=\"changeDir('"+dn.path+"')\">"+dn.directoryName+"</a></li>\n"+printFolders(dn.subDirectories, dn.level); 	
    }
    
    ret = ret + "</ul>\n";
      
	return ret;
   }
%>
    
<%
String projectID = (String)session.getAttribute("projectID");

String fileID = (String)request.getParameter("fileID");

ArrayList folders = file.getFoldersByContainer(projectID);


ArrayList root = new ArrayList();

DirectoryNode nd = new DirectoryNode("/","/",0);
root.add(nd);


ArrayList rt = nd.subDirectories;

for (int i = 0; i < folders.size(); i++)
{
	
rt = nd.subDirectories;
int level = 1;

String path = "";

StringTokenizer st = new StringTokenizer((String)folders.get(i),"/");
while (st.hasMoreTokens()) {
    String dir = st.nextToken();
    common.Utility.log.debug(dir);
    DirectoryNode tmpNode = DirectoryNode.containsDirectory(rt, dir);
    path = path + "/" + dir;
    if (tmpNode != null) {
    	rt = tmpNode.subDirectories;
    	level++;
    } else {
    	DirectoryNode tt = new DirectoryNode(dir, path, level);
    	rt.add(tt);
    	rt = tt.subDirectories;
    	level++;
    }    
}

}

%>
<!-- <p style="color:#999; font-size:24px; margin: 0 auto;" align="center">Create new Folder</p> -->

<div style="position:relative; margin:10px; padding:10px; width: 420px;">

<p style="margin-bottom: 10px;">Select the main folder:</p>
<p style="margin-bottom: 10px;">
<%=printFolders(root,0)%>
</p>
<p style="margin-top: 10px; margin-bottom: 10px;">
            Selected folder:
            </p>

   <div  id = "dirStuff" style="position: relative; float: left; display:block; display:none; margin-bottom: 10px; width:420px;">
     <div style="position: relative; float: left; display:block; width:200px;">
      <input type="text" name="title" id="directory" style="width:200px; border:0px; height:17px; padding-top:3px;" />
	 </div>
	 <div id="optSub" style="position: relative; float: left;  display:block;">
	   	&nbsp;/&nbsp;<input type="text" name="title" id="optSubfolder" style="width:100px; height:17px; padding-top:3px; border: 1px solid #036;" />
	 </div>
   </div>

   
   <a href="#" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" style="padding: 7px" onclick="javascript:createFolder(document.getElementById('directory').value,document.getElementById('optSubfolder').value)">Create Folder</a>
   <a href="#" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" style="padding: 7px" onclick="javascript:removeFolder(document.getElementById('directory').value)">Remove Selected Folder</a> 
</div>
<!-- 
<ul style="padding-left: 20px;">
<li class="directory collapsed">abc1.1</li>
<li class="directory collapsed">abc1.2</li>
<li class="directory collapsed">abc1.3</li>
</ul>

<li class="directory collapsed">abc2</li>
<li class="directory collapsed">abc3</li>
</ul>
-->