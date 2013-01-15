<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*, java.util.Random,  com.hp.hpl.jena.ontology.*, com.hp.hpl.jena.rdf.model.*, com.hp.hpl.jena.util.FileManager, com.hp.hpl.jena.vocabulary.*, org.openrdf.repository.*, org.openrdf.repository.http.*, org.openrdf.*, org.openrdf.model.*, org.openrdf.rio.rdfxml.RDFXMLWriter, org.openrdf.rio.*, org.openrdf.model.*, org.openrdf.query.*, org.openrdf.query.resultio.sparqlxml.*, org.policygrid.ontologies.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
 <%
 String id = java.net.URLDecoder.decode((String) request.getParameter("id").toString());
 %>

<%
Connections con = new Connections();
con.repConnect();

StringBuffer qry = new StringBuffer(1024);
qry.append("DELETE {?x ?y ?z} WHERE { ?x ?y ?z . FILTER (?x = <"+id+">) . }");
		
String query = qry.toString();

Update up = con.getRepConnection().prepareUpdate(QueryLanguage.SPARQL, query);
up.execute();

%>
