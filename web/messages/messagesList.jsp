<%@page import="java.net.URLDecoder"%>
<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.util.Vector,java.util.*,java.text.*, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.Project" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="blog" class="common.Blog" />



<%
common.User user = (common.User) session.getAttribute("use");
int id = user.getID();
if (user.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));

String firstmsg="";
if(request.getParameter("limit") == null) { %>
	<jsp:forward page="error.jsp" /> <%
}
int limit = Integer.parseInt(request.getParameter("limit"));

if(request.getParameter("offset") == null) { %>
	<jsp:forward page="error.jsp" /> <%
}
int offset = Integer.parseInt(request.getParameter("offset"));
if (offset < 0) offset = 0;

if(request.getParameter("qrytype") == null) { %>
<jsp:forward page="error.jsp" /> <%
}
String qrytype=request.getParameter("qrytype");

if(request.getParameter("delaction") == null) { %>
<jsp:forward page="error.jsp" /> <%
}
String delaction=request.getParameter("delaction");


%>
<div id="msgpanel">
<%
			
			int mesgid=0;
			Vector messages = (Vector) user.getMessagesWithOffset(id,qrytype,offset,limit);
			if (messages.size() > 0) { 
			for(int i = 0; i < messages.size() && i<8; i++)
			{
				Message m= new Message();
				m=(Message) messages.get(i);
				 mesgid=m.getID();
				
				int uname = m.getSender();
				String subject = m.getSubject();
				if(subject == null || subject.equals(""))
						subject = "No subject";
				Date sentDate=m.getSent();
				String sentdate="";
				if(sentDate != null)
				{
					sentdate=new SimpleDateFormat("dd-MMM-yyyy").format(m.getSent());
				}
				else
				{
					sentdate="null";
				}
					
				String sender =user.getName(m.getSender());
				String msgtxt=URLDecoder.decode(m.getContent());
				String recipients="";
				String recipientids="";
				if (!m.getRecipients().isEmpty())
				{
					recipients=m.getRecipients().toString();
					recipientids=m.getRecipientIds().toString();
				}
				else
				{
					recipients=  "None";
				}
				String unread="";
				
				common.Utility.log.debug(m.getRecipients());
		
			if (m.getUnread().equals("X")) {			
				
			%>
			 <div class="msgrow new"  id=<%=mesgid %>>
               
               <%
			}else{
               
               %>  
                <div class="msgrow"  id=<%=mesgid %>  >
                <%
                
			}
                %>
				    <span class="msgsender" style="float:left;width:240px">   
				  		<%=sender%>
				    </span>
				   
				    <span class="msgsent" style="float:left;width:100px">
				  		<%=sentdate%>
				    </span>
				    <br/>
				     <span class="msgsubject" style="float:left;width:300px;display:block;padding-top:5px;">   
				  		<%=subject%>
				    </span>
				    <span>
				  		<span id ="delaction" style="display:none;"><%=delaction %></span>
				  		<span class ="otherrecs" style="display:none;"><%=recipients %></span>
				  		<span class ="otherrecids" style="display:none;"><%=recipientids %></span>
	      				
	      		
				    </span>
				   <%--  <span class="msgtxt" style="display:none;"><%=msgtxt %></span> --%>
				    
				    
                 </div>
                 
                 
                 
                 
				 <% 
				 
					}
			
				} 
			
			
		 	if (offset > 0) {
		 	%>
		 	           <div id="previous" style="float: left; width: 100px;">
		 	           <a id="previousButton" href="#"<%=offset-limit %>" ><img src="/ourspaces/icons/001_27.png" style="float: left;" border="0"/></a>
		 	           </div>
		 	                 
		 	                 <%
		 	                 }
		 	%>



		 	<%
		 	if ((messages.size() > 0) && (limit <= messages.size()) ) {
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
		url: "/ourspaces/messages/messagesList.jsp?offset=<%=(offset + messages.size())%>&limit=<%=limit %>&qrytype=<%=qrytype%>&delaction=<%=delaction%>", 
		dataType : "html",
		async : false,
		success : function(html, errorThrown) {
			$("#next").remove();
			$("#previous").remove();
			$("#msgpanel").html(html);
		 }
	   });
	 loadeventfunctions();
	 selectFirstRow();
 });
 
 $('#previousButton').click(function() {
	 $.ajax({
		type: 'GET',
		url: "/ourspaces/messages/messagesList.jsp?offset=<%=(offset - limit)%>&limit=<%=limit %>&qrytype=<%=qrytype%>&delaction=<%=delaction%>", 
		dataType : "html",
		async : false,
		success : function(html, errorThrown) {
			$("#next").remove();
			$("#previous").remove();
			$("#msgpanel").html(html);
		 }
	   });
	 loadeventfunctions();
	 selectFirstRow();
 });
 
});

</script>
				
				
				