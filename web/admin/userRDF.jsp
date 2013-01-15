<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*, java.util.Random,  com.hp.hpl.jena.ontology.*, com.hp.hpl.jena.rdf.model.*, com.hp.hpl.jena.util.FileManager, com.hp.hpl.jena.vocabulary.*, org.openrdf.repository.*, org.openrdf.repository.http.*, org.openrdf.*, org.openrdf.model.*, org.openrdf.rio.rdfxml.RDFXMLWriter, org.openrdf.rio.*, org.openrdf.model.*, org.openrdf.query.*, org.openrdf.query.resultio.sparqlxml.*, org.policygrid.ontologies.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
 
 
 <%
 String rdfid = (String) request.getParameter("rdfid").toString();
 %>
<table>

<%


Connections con = new Connections();
StringBuffer qry = new StringBuffer(1024);
qry.append("select ?y ?z where { <"+rdfid+"> ?y ?z}");

String resource = "";

String query = qry.toString();

con.repConnect();

TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
TupleQueryResult result = output.evaluate();

while (result.hasNext()) 
{
	   BindingSet bindingSet = result.next();
	   Value value = bindingSet.getValue("y");
	   String y = value.stringValue();
	   
	    value = bindingSet.getValue("z");
	   String z = value.stringValue();
	   
	   %>
	   <tr>
	   <td><a href="removeTR.jsp?su=<%=java.net.URLEncoder.encode(rdfid) %>&pr=<%=java.net.URLEncoder.encode(y) %>&ob=<%=java.net.URLEncoder.encode(z) %>">[Delete]</a>&nbsp;&nbsp;
	   <a href="seeTR.jsp?su=<%=java.net.URLEncoder.encode(rdfid) %>&pr=<%=java.net.URLEncoder.encode(y) %>&ob=<%=java.net.URLEncoder.encode(z) %>">[Edit]</a></td>
	   <td><%=y %></td>
	   <td><%=z %></td>
	   </tr>
	   
	   
	   
	   <%
}
result.close();
con.repDisconnect();
%>
</table> 