<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
  <%

common.User user = (common.User) session.getAttribute("use");
common.User contact = new common.User();
int id = user.getID();
java.util.Vector contacts = user.getContacts(user.getID());
%>


<%
	  					for(int i = 0; i < contacts.size(); i++)
						{
							contact = (common.User) contacts.get(i);
							String photo = user.getPhoto(contact.getContactID());
							String name = user.getName(contact.getContactID());
							String onlineStatus = user.getUserOnlineStatus(contact.getContactID());
							if(!onlineStatus.equals("offline")) {
					%>
                            		<div style="width:72px; position:relative; float:left;">
                                    	<% if(!photo.equals("")) { %>
											<img src="<%=photo%>" style="border:1px solid #666666;" width="48" height="50" />
                                        <% } else { %>
                                        	<img src="/ourspaces/images/no.jpg" alt="no picture" width="48" height="50" />
                                        <% } %>
	                                    <p style="border:0px; padding:0px; margin:0px;font-size:9px;" align="center"><a href="profile.jsp?id=<%=contact.getContactID()%>"><%=name%></a><br />
	                                    <%
		                                if(onlineStatus.equals("active")) {
		                                %>
		                                	<a href="javascript:void(0)" onclick="javascript:chatWith('<%=contact.getContactID() %>')"><img src="images/chat2.png" style="border:none; padding:0px; margin:0px;" /></a>
		                                <% } %>
		                                <%
		                                if(onlineStatus.equals("idle")) {
		                                %>
		                                	<a href="javascript:void(0)" onclick="javascript:chatWith('<%=contact.getContactID() %>')"><img src="images/chat2_idle.png" style="border:none; padding:0px; margin:0px;" /></a>
		                                <% } %>
		                                </p>
                                     </div>
                     <%
							}
						}
					%>