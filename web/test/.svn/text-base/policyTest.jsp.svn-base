<%@ page language="java" import="org.policygrid.policies.PolicyReasoner, org.policygrid.policies.PolicySession, org.policygrid.policies.RequiredFieldsSession,  com.hp.hpl.jena.rdf.model.Model, java.util.Iterator, java.util.ArrayList, java.io.*, java.awt.*, java.awt.image.*, javax.imageio.ImageIO, org.policygrid.ontologies.*, java.net.*, java.util.Vector, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>

 <%
 
 try{
 //PolicyReasoner.init();
 
 PolicySession psession = PolicyReasoner.createSession();
 
 PolicyReasoner.loadAllPolicies(psession);
 
// PolicyReasoner.loadPolicy("UKDADocumentationPolicy1.spin.owl", psession);
 
 PolicyReasoner.run(psession);
 
 PolicyReasoner.closeSession(psession);
 
 
 //PolicyReasoner.test();
 
 RequiredFieldsSession fields = new RequiredFieldsSession(psession);

 %>
 
 <%= fields.listMandatoryFields("http://openprovenance.org/ontology#3afc93ce-5b09-49c0-bb11-2067366d4792") %>
 
 
 <%
 } catch (Exception e){common.Utility.log.debug(e);}
 %>
 