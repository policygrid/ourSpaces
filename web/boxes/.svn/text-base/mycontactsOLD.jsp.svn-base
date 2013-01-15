<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

  <%

common.User user = (common.User) session.getAttribute("use");
common.User contact = new common.User();
int id = user.getID();
java.util.Vector contacts = user.getContacts(user.getID());
%>

<style>
.hidden { display: none; }
.unhidden { display: block; }
</style>

<script type="text/javascript" src="/ourspaces/javascript/tab.js"></script>

				<div style="position:relative; float:left; width:290px;">
				
				<a href="javascript:tag('boxes/allcontacts.jsp', 'res');">All Contacts</a> |
				
				<a href="javascript:tag('boxes/onlinecontacts.jsp', 'res');">Online Contacts</a>
				
				<div id="res">
				
                	<%
	  					for(int i = 0; i < contacts.size(); i++)
						{
							contact = (common.User) contacts.get(i);
							String photo = user.getPhoto(contact.getContactID());
							String name = user.getName(contact.getContactID());
							String onlineStatus = user.getUserOnlineStatus(contact.getContactID());
					%>
                            		<div style="width:72px; position:relative; float:left;">
                                    	<% if(!photo.equals("")) { %>
											<img src="<%=photo%>" style="border:1px solid #666666;" width="48" height="50" />
                                        <% } else { %>
                                        	<img src="/ourspaces/images/no.jpg" alt="no picture" width="48" height="50" />
                                        <% } %>
	                                    <p style="border:0px; padding:0px; margin:0px;font-size:9px;" align="center"><a href="profile.jsp?id=<%=contact.getContactID()%>"><%=name%></a>
	                                    
		                                </p>
                                     </div>
                     <%
						}
					%>
					
				</div><!-- res -->
				
                </div>
                
                