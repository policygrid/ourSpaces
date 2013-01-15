<%@ page language="java" import="org.mindswap.pellet.jena.*, com.hp.hpl.jena.ontology.*,com.hp.hpl.jena.rdf.model.*, com.hp.hpl.jena.util.iterator.*,   java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="rdf" class="common.RDFi" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>


<jsp:include page="top_head.jsp" />
<!-- menu goes here -->
<jsp:include page="top_tail.jsp" />


<%

String ont = "http://mrt.esc.abdn.ac.uk:8080/ourspaces/ontologies/rules/dl-safe.owl";

OntModel model = ModelFactory.createOntologyModel( PelletReasonerFactory.THE_SPEC, null );
model.read(ont );

ObjectProperty sibling = model.getObjectProperty( ont + "#sibling" );

OntClass BadChild = model.getOntClass( ont + "#BadChild" );
OntClass Child = model.getOntClass( ont + "#Child" );

Individual Abel = model.getIndividual( ont + "#Abel" );
Individual Cain = model.getIndividual( ont + "#Cain" );
Individual Remus = model.getIndividual( ont + "#Remus" );
Individual Romulus = model.getIndividual( ont + "#Romulus" );

model.prepare();

%>
<%=model.write(System.out) %>
<jsp:include page="bottom.jsp" />
