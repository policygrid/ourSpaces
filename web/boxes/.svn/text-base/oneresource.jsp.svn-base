<jsp:useBean id="blog" class="common.Blog" />
<%@page import="java.net.URLEncoder, common.Utility"%>
<jsp:useBean id="rdf" class="common.RDFi" />
  <%
common.User user = (common.User) session.getAttribute("use");
int id = user.getID();
//"http://www.policygrid.org/ourspacesVRE.owl#"
String namespace = "http://openprovenance.org/ontology#";

String resID = request.getParameter("resource");
String type = rdf.getResourceType(resID);
String i = request.getParameter("i");
/**
In linkTo, use #artId for local part of id, i.e. "2efd3b2c-3127-43f8-9d73-48b5830e41d3" and #fullId for full URI.
*/
String linkTo = "/ourspaces/artifact_new.jsp?id=#artId";
if(request.getParameter("link")!=null && !"null".equals(request.getParameter("link"))){
	linkTo=request.getParameter("link");
}
else{
	linkTo=Utility.getDetailPage(type, resID);
}
String onClick = "";
if(request.getParameter("onClick")!=null && !"null".equals(request.getParameter("onClick"))){
	onClick=request.getParameter("onClick");
}
String icon = "/ourspaces/icons/001_58.png";
if(request.getParameter("icon")!=null && !"null".equals(request.getParameter("icon"))){
	icon=request.getParameter("icon");
}

icon = Utility.getImage(type, null);
String title = rdf.getResourceTitle(resID);
common.Resources res = new common.Resources();
res.setId(resID);
res.init();
				String link = res.getURL();
				String date = res.getDate();
				String local=Utility.getLocalName(resID);
				String access = res.getRestriction();
				String encRes = "";
				if(resID != null && !resID.equals(""))
					encRes = URLEncoder.encode(resID);
				String encLink = "";
				
				if(link != null && !link.equals(""))
					encLink = URLEncoder.encode(link);
				
				String versionNumber = "";
				if (res.getVersionNumber() != null ) versionNumber = res.getVersionNumber();
				
				//common.Utility.log.debug("The version number is :"+res.getVersionNumber() );
				
		%>
		
		
		<table>
		<tr class="<%=local%>">
					<td class="icon" rowspan="2" style="">					
					<a style="font-size:12px;" href="<%=linkTo.replaceAll("#artId", local).replaceAll("#fullId", resID) %>" onclick="<%=onClick%>">
						<img src="<%=icon %>" align="middle"  style="float: left; margin-top: auto; margin-bottom:auto;" />
					</a>
							
					</td>
						<td class="resourceinfo" width="100%" colspan="2" style="">
							<a style="font-size:12px;" href="<%=linkTo.replaceAll("#artId", local).replaceAll("#fullId", resID) %>" onclick="<%=onClick%>"><%=title%> </a> <span class="nlg" rel="<%=encRes%>"></span>
							<% if (!versionNumber.equals("")) { %>
							<br> <span style="font-size:10px; margin-left:0px; float: left;">Version: <%=versionNumber %> </span> <a id="vlink<%=i%>" onclick="javascript:showVersions('<%=local %>','<%=title%>')"><img style="float: left; margin:0; margin-left: 5px;" src="/ourspaces/img/plus.gif"/></a> 
							<% } %>						
							<div id="ver<%=i%>" style=" float: left; font-size:10px; margin-left:20px; display: none;">
							</div>
						</td>
		</tr>
		<tr class="<%=resID%>">
						
						<td width="100%" >	
						<!-- TODO pass the namespace &namespace=http://www.policygrid.org/provenance-generic.owl%23 to the createcomment.jsp--> 
							<span style="font-size:9px;">
							<%if(!"".equals(encLink)){ %><a href="/ourspaces/boxes/resources/email.jsp?resource=<%=encRes%>" class="popup_dialog_ajax" title="Email" style="color:#FF6600;">Email</a><%} %> 
							| <a href="/ourspaces/boxes/resources/tag.jsp?resource=<%=encRes%>" class="popup_dialog_ajax" title="Tag" style="color:#FF6600;">Tag</a> 
							| <a href="/ourspaces/permissions/setPermission.jsp?resource=<%=local%>" class="popup_dialog" title="Privacy settings" style="color:#FF6600;">Privacy</a>
							| <a href="/ourspaces/createcomment.jsp?namespace=<%=URLEncoder.encode(namespace) %>&about=<%=local %>" class="popup_comment_dialog" title="Comment" style="color:#FF6600;">
							 <%if(blog.getCommentCount(namespace+local) == 0) { %>
                         		Comment
                        	 <% } %>
                        	 <%if(blog.getCommentCount(namespace+local) == 1) { %>
                         		1 Comment
                         	 <% } %>
                        	 <%if(blog.getCommentCount(namespace+local) > 1) { 
                         		int count = blog.getCommentCount(namespace+local);
                        	 %>
                         		<%=count %> Comments
                         	 <% } %>
                         	 </a>	
                         
                         	 <%
                         	int did=0;
                         	String name="";
                         	String depositer = rdf.getResourceDepositor(res.getID());
                         	if(!depositer.equals(""))
 							{
 								did = user.getUserIDFromRDFID(depositer);
 								name = user.getName(did);
 								if (id!=did){
 									%>
 									| Deposited By <a href="/ourspaces/profile.jsp?id=<%=did %>"><%=name %></a><span class="nlg" rel="<%=depositer%>"></span>
 									<% 
 								}
 								
 							}
                         	 
                         	 %>
                         						
						</td>
						<td align="right">
				 		 <span style="font-size:9px; color:#FF6600;">
						  	<%=date%>
					  	  </span>
						</td>
					</tr>
					
					<tr>
						<td >
							<div id="<%=i%>tag" class="hidden">
								<div id="<%=i%>">		
										<input id="tag<%=i%>" style="margin-right:5px;" type="text" />					
										<input id="id<%=i%>" type="hidden" value="<%=id%>" />
										<input id="resID<%=i%>" type="hidden" value="<%=resID%>" />
										<a href="#" onclick="add('<%=i%>')"  style="background: none repeat scroll 0 0 green;display: block;float: none;margin-left: auto;margin-right: auto; width: 120px;" class="fg-button ui-state-active  ui-corner-all">Add tag</a>
								</div>
							</div>
						</td>
					</tr>
					
					<tr>
					 <td >
							<div id="<%=i%>email" title="Email dialog" class="hidden">
								<div id="<%=i%>_email">		
										Recipient: <input id="email<%=i%>" style="margin-right:5px;width:250px" type="text" />					
										<input id="url<%=i%>" type="hidden" value="<%=encLink%>" />
										<input id="resID<%=i%>" type="hidden" value="<%=encRes%>" />
										<a href="#" onclick="email('<%=i%>')" style="background: none repeat scroll 0 0 green;display: block;float: none;margin-left: auto;margin-right: auto; width: 120px;" class="fg-button ui-state-active  ui-corner-all">Send email</a>
								</div>
							</div>
						</td>
					</tr>
					
	</table>