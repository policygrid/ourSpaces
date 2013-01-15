<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*, java.util.Random,  com.hp.hpl.jena.ontology.*, com.hp.hpl.jena.rdf.model.*, com.hp.hpl.jena.util.FileManager, com.hp.hpl.jena.vocabulary.*, org.openrdf.repository.*, org.openrdf.repository.http.*, org.openrdf.*, org.openrdf.model.*, org.openrdf.rio.rdfxml.RDFXMLWriter, org.openrdf.rio.*, org.openrdf.model.*, org.openrdf.query.*, org.openrdf.query.resultio.sparqlxml.*, org.policygrid.ontologies.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
 <%
 String su = java.net.URLDecoder.decode((String) request.getParameter("su").toString());
 String pr = java.net.URLDecoder.decode((String) request.getParameter("pr").toString());
 String ob = java.net.URLDecoder.decode((String) request.getParameter("ob").toString());
 %>


<%

//String query = qry.toString();

//con.repConnect();

//GraphQuery q = con.getRepConnection().prepareGraphQuery(QueryLanguage.SPARQL, query);
//GraphQueryResult result = q.evaluate();
//con.getRepConnection().remove(result);

//con.repDisconnect();


%>

<h1>Edit Triple</h1>

 <form method="post" style="border:none;" action="editTR.jsp">
          <div class="regLeftCont">
                <div class="regLeftNames"><p>Subject:</p></div>
            	<input type="text" name="sub" id=""  value="<%=su%>" class="input" />
              </div>
              <div class="regLeftCont">
                <div class="regLeftNames"><p>Predicate:</p></div>
            	<input type="text" name="pre" id=""  value="<%=pr%>" class="input" />
              </div>
              <div class="regLeftCont">
                <div class="regLeftNames"><p>Object:</p></div>
            	<input type="text" name="obj" id=""  value="<%=ob%>" class="input" />
              </div>
             
              <div class="regLeftCont">
              	<input type="submit" value="Confirm" id="submitForm" />
                <input type="reset" value="Clear" />
              </div>
              
              
              <input type="hidden" name="su" value="<%=su%>" id="" />
              <input type="hidden" name="pr" value="<%=pr%>" id="" />
              <input type="hidden" name="ob" value="<%=ob%>" id="" />
            </form>




