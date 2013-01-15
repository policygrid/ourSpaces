<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.Project" />
<jsp:useBean id="rdf" class="common.RDFi" />
<script type="text/javascript">
$(document).ready(function(){


	$(".comlink").click(function(e) {
		var currentId = $(this).attr('id');
		var title = $(this).text();
		showCommDialog(currentId,title);
   
	
  });
	
});


</script>
<%
User user = (User) session.getAttribute("use");
int id =user.getID();
if (id == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));

if(request.getParameter("limit") == null) { %>
	<jsp:forward page="error.jsp" /> <%
}
int limit = Integer.parseInt(request.getParameter("limit"));

if(request.getParameter("offset") == null) { %>
	<jsp:forward page="error.jsp" /> <%
}
int offset = Integer.parseInt(request.getParameter("offset"));
if (offset < 0) offset = 0;




Vector myComres = user.getResourcesByType(id,"http://www.policygrid.org/provenance-generic.owl#Communication",offset,limit);

%>



<div id="comms">


        <div style="float:left">
        				<%
        				int count=0;
                            for (int i=0;i<myComres.size();i++){
                            	Resources res= (Resources) myComres.get(i);
                            	String url = rdf.getResourceURL(res.getID());
                            	String fileURI = url.substring(url.lastIndexOf("/")+1);
                            	int oid=0;
                            	String name="";
                            	String owner = rdf.getResourceDepositor(res.getID());
                            	if(!owner.equals(""))
    							{
    								oid = user.getUserIDFromRDFID(owner);
    								name = user.getName(oid);
    								
    							}
                           		
                            	//common.Resources res = (common.Resources) resources.get(j);
                				String resID = res.getID();
                				
                				
                				String jsfunc="showCommDialog('"+fileURI+"','"+resID+"','"+res.getTitle()+"');";
                            	%>
                            	
                            	<div style="float: left;width:540px;padding-left:10px;" class ="comres">	<jsp:include page="/boxes/oneresource.jsp" flush="false">
                						<jsp:param value="<%=jsfunc%>" name="onClick"/>
                						<jsp:param value="#" name="link"/>
                						
                						<jsp:param value="<%=resID%>" name="resource"/>
                						
                						<jsp:param value="<%=count%>" name="i"/>
                				</jsp:include>	
                				<%count++;
                            	%>
                					</div>	
                            	
                            	<% 
                            }
                            
                            %>
						</div>  


                		
<%
if (offset > 0) {
%>
           <div id="previous" style="float: left; width: 100px;">
           <a id="previousButton" href="#" ><img src="/ourspaces/icons/001_27.png" style="float: left;" border="0"/></a>
           </div>
                 
                 <%
                 }
%>



<%
if ((myComres.size() > 0) && (limit <= myComres.size()) ) {
%>
           <div id="next" style="float: right; width: 100px;">
           <a id="nextButton" href="#" style="float: right;"><img src="/ourspaces/icons/001_25.png" align="right" border="0"/></a>
           </div>
                 
                 <%
                 }
%>
</div>





<script>
<%-- $(document).ready(function() {
 $('#nextButton').click(function() {
	 $.ajax({
		type: 'GET',
		url: "/ourspaces/commSpace/commList.jsp?offset=<%=(offset + myComres.size())%>&limit=<%=limit %>", 
		dataType : "html",
		async : false,
		success : function(html, errorThrown) {
			$("#next").remove();
			$("#previous").remove();
			$("#comms").html(html);
		 }
	   });
 });
 
 $('#previousButton').click(function() {
	 $.ajax({
		type: 'GET',
		url: "/ourspaces/commSpace/commList.jsp?offset=<%=(offset - limit)%>&limit=<%=limit %>", 
		dataType : "html",
		async : false,
		success : function(html, errorThrown) {
			$("#next").remove();
			$("#previous").remove();
			$("#comms").html(html);
		 }
	   });
 });
 
}); --%>
$(document).ready(function() {
addLoadMoreContentBehaviour(true, $('#nextButton'), "/ourspaces/commSpace/commList.jsp", <%=offset%>, <%=limit%>, <%=myComres.size()%>, $("#comms"));
addLoadMoreContentBehaviour(false, $('#previousButton'), "/ourspaces/commSpace/commList.jsp", <%=offset%>, <%=limit%>, <%=myComres.size()%>, $("#comms"));
});
</script>



                 
                  
              