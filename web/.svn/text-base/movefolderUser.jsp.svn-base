<%@ page
	import="java.io.File,java.io.FilenameFilter,java.util.StringTokenizer, common.DirectoryNode, java.util.Arrays,java.util.Iterator, java.util.ArrayList, java.util.Vector, org.policygrid.ontologies.*, common.*"%>
	
	<jsp:useBean id="resource" class="common.Resources" />
	<jsp:useBean id="user" class="common.User" />
	<jsp:useBean id="project" class="common.Project" />
    <jsp:useBean id="file" class="common.FileUtils" />
    

<script>
$(function(){
	$("a#moveResource").button();
});
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
//String projectID = (String)session.getAttribute("projectID");
user = (User)session.getAttribute("use");

String fileID = (String)request.getParameter("fileID");

ArrayList folders = file.getFoldersByUserID(user.getID());


ArrayList root = new ArrayList();

DirectoryNode nd = new DirectoryNode("/","/",0);
root.add(nd);


ArrayList rt = nd.subDirectories;

for (int i = 0; i < folders.size(); i++)
{
	if(folders.get(i) == null)
		continue;
rt = nd.subDirectories;
int level = 1;

String path = "";

String fo =(String)folders.get(i);
String projectName = "";
String projectID = file.getContainerIDofFolder(user.getID(),fo);
if (!projectID.equals("")){
	projectName = "("+project.getTitle(projectID)+") ";
}

StringTokenizer st = new StringTokenizer((String)folders.get(i),"/");
while (st.hasMoreTokens()) {
    String dir = st.nextToken();
    common.Utility.log.debug(dir);
    path = path + "/" + dir;
    dir = projectName + dir;    	
    DirectoryNode tmpNode = DirectoryNode.containsDirectory(rt, dir);
    
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
<p style="color:#999; font-size:24px; margin: 0 auto;" align="center">Move Resource to new Folder</p>

<div style="position:relative; margin:20px; padding:15px; border:1px solid #CCC; width: 420px;">

<p style="margin-bottom: 10px;">Select the folder:</p>
<p style="margin-bottom: 10px;">
<%=printFolders(root,0)%>
</p>
<p style="margin-top: 10px; margin-bottom: 10px;">
            Selected folder:
            </p>

   <div  id = "dirStuff" style="position: relative; float: left; display:block; display:none; margin-bottom: 10px; width:420px;">
     <div style="position: relative; float: left; display:block; width:200px;">
      <input type="text" name="title" id="directory" disabled="disabled" style="width:200px; border:0px; height:17px; padding-top:3px;" />
	 </div>
	   <div id="optSubSwitch" style="position: relative; float: left; display:block">
	   	 &nbsp;<a href="#" onclick="showOptionalSubfolder()">add sub-folder</a>
	   </div>
	   
	   <div id="optSub" style="position: relative; float: left;  display:none;">
	   	&nbsp;/&nbsp;<input type="text" name="title" id="optSubfolder" style="width:100px; height:17px; padding-top:3px; border: 1px solid #036;" /><a href="#" onclick="hideOptionalSubfolder()">&nbsp;hide</a>
	   </div>
	   
   </div>

   
   <a id="moveResource" href="#" onclick="javascript:moveFile('<%=fileID %>',document.getElementById('directory').value,document.getElementById('optSubfolder').value)">Move Resource</a> 


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