<%@ page language="java" import="org.policygrid.policies.PolicyReasoner, org.policygrid.policies.PolicySession, org.policygrid.policies.RequiredFieldsSession,  com.hp.hpl.jena.rdf.model.Model, java.util.Iterator, java.util.ArrayList, java.io.*, java.awt.*, java.awt.image.*, javax.imageio.ImageIO, org.policygrid.ontologies.*, java.net.*, java.util.Vector, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>

 <%

String[] policies = new String[]{
		 //"NLGRetractTranscriptPolicy.spin.owl",
"UKDADocumentationPolicy1.spin.owl",
"UKDADocumentationPolicy2.spin.owl",
"UKDADocumentationPolicy3.spin.owl",
"UKDADocumentationPolicy4.spin.owl",
"UKDADocumentationPolicy5.spin.owl",
"UKDADocumentationPolicy6.spin.owl",
"UKDADocumentationPolicy7.spin.owl"};
 
 try{
 //PolicyReasoner.init();
 int numPol = 1, iter = 5;
 for(numPol = 1;numPol<=10000;numPol*=2){
	 Long duration = 0L;
	 Long durationR = 0L;
	 for(int i=0;i<iter;i++){
			Long start = System.currentTimeMillis();
			PolicySession psession = PolicyReasoner.createSession();
			//PolicyReasoner.loadAllPolicies(psession);
			for(int k=0;k<numPol;k++){
				for(int l=0;l<policies.length && k<numPol;l++, k++){
					PolicyReasoner.loadPolicy(policies[l], psession);			 
				}
			}
			Long startReasoning = System.currentTimeMillis();
			PolicyReasoner.run(psession);
			PolicyReasoner.closeSession(psession);
			RequiredFieldsSession fields = new RequiredFieldsSession(psession);
			Long end = System.currentTimeMillis();
			duration += end - start;
			durationR += end - startReasoning;
	 }
	 
	 common.Utility.log.debug("" + numPol+"\t"+(duration/iter)+"\t"+durationR/iter);

	 %>
		<%=numPol%>	<%= duration/iter %>	<%= durationR/iter %><br>
<% 		
		/*if(numPol == 1)
			numPol = 0;*/
 }

 } catch (Exception e){common.Utility.log.debug(e);}
 %>
 
 