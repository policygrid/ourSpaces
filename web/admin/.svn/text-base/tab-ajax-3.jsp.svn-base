



<p>Helpdesk</p>

<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.text.DateFormat, java.sql.Timestamp, java.io.*, java.net.*, java.util.Vector, common.*, java.sql.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="con" class="common.Connections" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>



<%

user = (User) session.getAttribute("use");

if ((user.getID() == 59) || (user.getID() == 56) || (user.getID() == 55) || (user.getID() == 224) || (user.getID() == 225) || (user.getID() == 196)) {
	
} else {
	response.sendRedirect(response.encodeRedirectURL("/ourspaces/error.jsp"));
}

Vector contacts = user.getAllContacts();

ArrayList list = new ArrayList();

int userid = user.getID();
int id = user.getID();


con.connect();
Statement st = con.getCon().createStatement();

Vector messages = new Vector();

StringBuffer qry = new StringBuffer(1024);
qry.append("select * from bugs order by priority desc");

ResultSet rs = st.executeQuery(qry.toString());
%>
<table border="1" cellspacing="0" cellpadding="5" style="width:1200px;">
<tr>
    <td>Type</td>
    <td>Created</td>
    <td>Closed</td>
    <td>Subject</td>
    <td>Message</td>
    <td>Sender</td>
    <td>Status</td>
    <td>Supported By</td>
    <td>Priority</td>
	</tr>
<%

while(rs.next())
{
	
	String created = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(rs.getLong("created"));
	String closed = ""+rs.getLong("closed");
	if (rs.getString("closed") != null)  closed = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(rs.getLong("closed"));
	
	%>
	<tr>
    <td><%=rs.getString("type")%></td>
    <td><%=created%></td>
    <td><%=closed%></td>
    <td><%=rs.getString("object")%></td>
    <td><%=rs.getString("message")%></td>
    <td><%=user.getEmail(rs.getInt("senderID"))%></td>
     <%
     if (rs.getString("status") == null) {
     %>    
    <td>
     Not assigned ...
    </td>
    <%} else { %>
     <td>
    <select id = '<%=rs.getString("id")%>status' name="status">
        <option selected value="<%=rs.getString("status")%>"><%=rs.getString("status")%></option>
		<option value="work in progress">work in progress</option>
		<option value="unresolved">unresolved</option>
		<option value="resolved">resolved</option>	
     </select>
    </td>
    <%} %>
    
    
	
    
    
    
       <%
     if (rs.getString("supportUserID") == null) {
     %>    
    <td>
    <button type="button" onClick="acceptRequest('<%=rs.getString("id")%>')">Accept Request</button>
    </td>
    <%} else { %>
    <td><%=user.getEmail(rs.getInt("supportUserID"))%></td>
    <%} %>
    
    
    
    
    
    
    <%
     if (rs.getString("priority") == null) {
     %>    
    <td>
    <select id = '<%=rs.getString("id")%>priority' name="priority">
        <option selected value="0">Please Select ...</option>
		<option value="1">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
		<option value="5">5</option>	
     </select>
    </td>
    <%} else { %>
        <td>
    <select id = '<%=rs.getString("id")%>priority' name="priority">
        <option selected value="<%=rs.getString("priority")%>"><%=rs.getString("priority")%></option>
		<option value="1">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
		<option value="5">5</option>	
     </select>
    </td>
    <%} %>
	
	<td> <button type="button" onClick="updateRequest('<%=rs.getString("id")%>')">Update</button></td>
	</tr>
	<%
}

rs.close();
st.close();
con.disconnect();

%>
</table>