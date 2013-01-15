<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*, java.util.Random,  com.hp.hpl.jena.ontology.*, com.hp.hpl.jena.rdf.model.*, com.hp.hpl.jena.util.FileManager, com.hp.hpl.jena.vocabulary.*, org.openrdf.repository.*, org.openrdf.repository.http.*, org.openrdf.*, org.openrdf.model.*, org.openrdf.rio.rdfxml.RDFXMLWriter, org.openrdf.rio.*, org.openrdf.model.*, org.openrdf.query.*, org.openrdf.query.resultio.sparqlxml.*, org.policygrid.ontologies.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
 <%
 String su = java.net.URLDecoder.decode((String) request.getParameter("su").toString());
 String pr = java.net.URLDecoder.decode((String) request.getParameter("pr").toString());
 String ob = java.net.URLDecoder.decode((String) request.getParameter("ob").toString());
 %>
<table>


<%
Connections con = new Connections();
con.repConnect();

ValueFactory f =con.getRepConnection().getRepository().getValueFactory();

org.openrdf.model.URI usu = f.createURI(su);
org.openrdf.model.URI upr = f.createURI(pr);


org.openrdf.model.URI uob = null;
org.openrdf.model.Literal lob = null;

if (ob.startsWith("http://")) {
	uob = f.createURI(ob);
} else {
    lob = f.createLiteral(ob);
}

common.Utility.log.debug(usu);
common.Utility.log.debug(upr);
common.Utility.log.debug(uob);
common.Utility.log.debug(lob);

if (ob.startsWith("http://")) {
 con.getRepConnection().remove(usu, upr, uob);
} else {
 con.getRepConnection().remove(usu, upr, lob);	
}

%>

<%

//String query = qry.toString();

//con.repConnect();

//GraphQuery q = con.getRepConnection().prepareGraphQuery(QueryLanguage.SPARQL, query);
//GraphQueryResult result = q.evaluate();
//con.getRepConnection().remove(result);

//con.repDisconnect();


%>

<h1>Deleted Triple</h1>
<%=su%><br>
<%=pr%><br>
<%=ob%><br>
