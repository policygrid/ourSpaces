<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.Project" />
<jsp:useBean id="rdf" class="common.RDFi" />

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




Vector <common.Resources>  mydocRes = user.getResourcesByType(id,"http://www.policygrid.org/provenance-generic.owl#Paper",offset,limit);
Vector <common.Resources> reps = user.getResourcesByType(id,"http://www.policygrid.org/provenance-generic.owl#Report",offset,limit);
Vector <common.Resources> docs = user.getResourcesByType(id,"http://www.policygrid.org/provenance-generic.owl#Documentation",offset,limit);
Vector <common.Resources> pres = user.getResourcesByType(id,"http://www.policygrid.org/provenance-generic.owl#Presentation",offset,limit);



 mydocRes.addAll(reps);
 mydocRes.addAll(docs);
mydocRes.addAll(pres);  



%>



<div id="docs">


        <div style="float:left">
        				<%
                            for (int i=mydocRes.size()-1;i>=0;i--){
                            	Resources res= (Resources) mydocRes.get(i);
                            	String url = rdf.getResourceURL(res.getID());
                            	String fileID = url.substring(url.lastIndexOf("/")+1);
                            	common.Utility.log.debug("Res URL is:"+fileID);
                            	%>
                            	<div style="float: left;width:540px;padding-left:10px;" class ="comres">
                            	<div style="float: left;width:30px"><img align="top" style="margin:5px" src="/ourspaces/icons/001_58.png"></div>
                            	<div style="float: left;width:230px;padding-top:10px"><a href="#" class="comlink" id= <%=fileID %> ><%=res.title %></a></div>
                            	<div style="float: left;font-size: x-small;">
                            		<%-- <span style="padding-right:5px"><a href="#">Email</a></span>|--%>
                            		<span style="padding-right:5px"><a href="#">Tag</a></span>|
                            		<span style="padding-right:5px"><a href="#">Privacy</a></span>|
                            		<span style="padding-right:5px"><a href="#">Comment</a></span>|
                            		<span style="float:right;display:block;padding-left:10px"><%=res.getDate() %></span>
                            	</div>
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
if ((mydocRes.size() > 0) && (limit <= mydocRes.size()) ) {
%>
           <div id="next" style="float: right; width: 100px;">
           <a id="nextButton" href="#" style="float: right;"><img src="/ourspaces/icons/001_25.png" align="right" border="0"/></a>
           </div>
                 
                 <%
                 }
%>
</div>





<script>
$(document).ready(function() {
 $('#nextButton').click(function() {
	 $.ajax({
		type: 'GET',
		url: "/ourspaces/communications/commList.jsp?offset=<%=(offset + mydocRes.size())%>&limit=<%=limit %>", 
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
		url: "/ourspaces/communications/commList.jsp?offset=<%=(offset - limit)%>&limit=<%=limit %>", 
		dataType : "html",
		async : false,
		success : function(html, errorThrown) {
			$("#next").remove();
			$("#previous").remove();
			$("#comms").html(html);
		 }
	   });
 });
 
});


</script>



                 
                  
              