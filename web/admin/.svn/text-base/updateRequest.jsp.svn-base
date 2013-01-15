
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
if ((user.getID() == 59) || (user.getID() == 56) || (user.getID() == 55)) {
	
} else {
	response.sendRedirect(response.encodeRedirectURL("/ourspaces/error.jsp"));
}

Vector contacts = user.getAllContacts();

ArrayList list = new ArrayList();

int userid = user.getID();
int id = user.getID();


con.connect();
Statement st = con.getCon().createStatement();


String rid = request.getParameter("rid");
String status = request.getParameter("status");
String priority = request.getParameter("priority");



StringBuffer qry = new StringBuffer(1024);

if (status.equals("resolved")) {
	qry.append("update bugs set closed = "+System.currentTimeMillis()+", status = "+user.getID()+", status = '"+status+"', priority = "+priority+" where closed IS NULL and id = "+rid);	
} else {
qry.append("update bugs set closed = NULL, status = "+user.getID()+", status = '"+status+"', priority = "+priority+" where id = "+rid);
}

st.executeUpdate(qry.toString());

if (status.equals("resolved")) {
	int ridd = Integer.parseInt(rid);
	String email = user.getEmail(user.getBugSenderID(ridd));
	String object = user.getBugObject(ridd);
	common.Email.send(email,"ourSpaces Helpdesk Request ","Your request ("+object+") has now been resolved. ");
}




%>