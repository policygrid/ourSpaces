
<%@ page language="java" import="java.util.Iterator,java.util.*,java.net.*,java.text.SimpleDateFormat,java.util.ArrayList,java.io.*,java.net.*,java.util.Vector,common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <jsp:useBean id="blog" class="common.Blog" />
<jsp:useBean id="user" class="common.User" />
<% 
ParameterHelper parHelp = new ParameterHelper(request, session);
   List timeline = (List)parHelp.getParameter("timeline",  new ArrayList());%>
	<table border="0" cellspacing="5" cellpadding="3">
						<tr>
						<%
						   // Sorting resources by time.
							Comparator comp = new Comparator(){
								public int compare(Object o, Object p){
									common.Resources r1 = (common.Resources)o;
									common.Resources r2 = (common.Resources)p;
									if(r1.timeStamp<r2.timeStamp)
										return -1;
									if(r1.timeStamp>r2.timeStamp)
										return 1;
									return 0;
								}
							};
							List tempTimeline = new ArrayList();
							tempTimeline.addAll(timeline);
							Collections.sort(tempTimeline, comp);
							for(int i = 0; i < tempTimeline.size(); i++)
							{
								common.Resources res = (common.Resources) tempTimeline.get(i);
								String resTitle = "";
								if(res ==null || res.getTitle() == null)
									resTitle = "";
								else if( res.getTitle().length() < 40)
									resTitle = res.getTitle();
								else
									resTitle = res.getTitle().substring(0, 40)+"...";
								%><td><%=resTitle %></td><%
						    } %>  
						</tr>
						<tr>
						<%
						   // Sorting resources by time.
							for(int i = 0; i < tempTimeline.size(); i++)
							{
								common.Resources res = (common.Resources) tempTimeline.get(i);
								%><td><%=res.getDate() %></td><%
						    } %>  
						</tr>
					</table>