<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*, java.util.Random,  com.hp.hpl.jena.ontology.*, com.hp.hpl.jena.rdf.model.*, com.hp.hpl.jena.util.FileManager, com.hp.hpl.jena.vocabulary.*, org.openrdf.repository.*, org.openrdf.repository.http.*, org.openrdf.*, org.openrdf.model.*, org.openrdf.rio.rdfxml.RDFXMLWriter, org.openrdf.rio.*, org.openrdf.model.*, org.openrdf.query.*, org.openrdf.query.resultio.sparqlxml.*, org.policygrid.ontologies.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="user" class="common.User" />


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>


<%

user = (User) session.getAttribute("use");
if ((user.getID() == 59) || (user.getID() == 56) || (user.getID() == 55) || (user.getID() == 224) || (user.getID() == 225) || (user.getID() == 196)) {

	
} else {
	response.sendRedirect(response.encodeRedirectURL("/ourspaces/error.jsp"));
}
%>

<table border="1">
<%


Connections con = new Connections();
StringBuffer qry = new StringBuffer(1024);
qry.append("select ?id (MIN(?type) as ?theType)  (GROUP_CONCAT (?cont ; separator = \" <br> \") as ?content) where { ?id <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?type . ?id ?prop ?cont . FILTER isLiteral(?cont) .  } GROUP BY (?id)");

String res = "";

String query = qry.toString();

con.repConnect();

TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
TupleQueryResult result = output.evaluate();
int i = 0;
while (result.hasNext()) 
{
	   BindingSet bindingSet = result.next();
	   Value value = bindingSet.getValue("id");
	   String id = value.stringValue();
	   
	   value = bindingSet.getValue("theType");
	   String type = value.stringValue();
	   
	   value = bindingSet.getValue("content");
	   String content = value.stringValue();
	   
	   %>
	   <tr  name="r<%=i%>" id = "r<%=i%>">
	   <td><%=i%></td>
	   <td><%=id %></td>
	   <td><%=type %></td>
	   <td><%=content %> </td>
	   <td> <a href="#r<%=(i-1)%>" onclick="deleteRes('<%=java.net.URLEncoder.encode(id) %>', 'r<%=i%>')" >[Delete]</a>
	   <!-- <a href="removeRes.jsp?id=<%=java.net.URLEncoder.encode(id) %>">[Delete]</a> -->
	   </tr>
	   
	   
	   
	   <%
	   
	   i++;
}
result.close();
con.repDisconnect();
%>
</table>