<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page import="java.util.*, NLGService.WYSIWYM.testclasses.TextGeneratorUtils, com.hp.hpl.jena.ontology.OntModel, com.hp.hpl.jena.rdf.model.Statement, com.hp.hpl.jena.rdf.model.impl.StatementImpl, com.hp.hpl.jena.rdf.model.impl.ResourceImpl, com.hp.hpl.jena.rdf.model.RDFNode,
com.hp.hpl.jena.graph.Node, com.hp.hpl.jena.rdf.model.RDFVisitor, com.hp.hpl.jena.rdf.model.impl.PropertyImpl, com.hp.hpl.jena.rdf.model.Model, com.hp.hpl.jena.rdf.model.StmtIterator,
NLGService.WYSIWYM.ontology.OntologyReader, NLGService.WYSIWYM.ontology.SesameReader, NLGService.liber.AnchorInfo" %>
<html>
 <body>
  Hello, world The time is now <%= new Date() %><BR>
<% 
String[] resourceIDs = {"http://openprovenance.org/ontology#b346b4ba-cc5e-43e4-810d-d492c561b879", "http://xmlns.com/foaf/0.1/#7771aed1-c59c-488f-b9b7-324f72be6642", "http://xmlns.com/foaf/0.1/#76b69445-b2b1-4c1e-a28d-bbbd028993a3"};

  TextGeneratorUtils feedbackTextGeneratorUtils = new TextGeneratorUtils();
%>
<TABLE BORDER="2">
<%
    for (String resourceID : resourceIDs ) {
        %>
        <TR>
        <TD><%= resourceID%></TD>
        <TD><%= feedbackTextGeneratorUtils.getTextualDescription(resourceID)%></TD>
        </TR>
        <%
    }

%>
</TABLE>
<BR> Text after modifying the model: <BR>
<TABLE BORDER="2">
<%

	for (String resourceID : resourceIDs ) {
		OntModel jenaModel = feedbackTextGeneratorUtils.getJenaModel(resourceID);
		
		//new statement:
		Statement jobTitleStatement = new StatementImpl(new ResourceImpl(), new PropertyImpl("http://www.policygrid.org/project.owl#roleOf"), new RDFNode() {
			
			public Node asNode() {
				return null;
			}
			public Object visitWith(RDFVisitor rv) {
				return null;
			}
			public boolean isURIResource() {
				return false;
			}
			public boolean isResource() {
				return false;
			}
			public boolean isLiteral() {
				return false;
			}
			public boolean isAnon() {
				return false;
			}
			public com.hp.hpl.jena.rdf.model.RDFNode inModel(Model m) {
				return null;
			}
			public <T extends com.hp.hpl.jena.rdf.model.RDFNode> boolean canAs(
					Class<T> view) {
				return false;
			}
			public <T extends com.hp.hpl.jena.rdf.model.RDFNode> T as(Class<T> view) {
				return null;
			}
		});

		StmtIterator listStatements = jenaModel.listStatements();
		int i = 1;

		while (listStatements.hasNext()) {
			Statement statement = (Statement) listStatements.next();
//			common.Utility.log.debug("Statement " + i + " : " + statement.toString());
			i++;
			if (statement.getPredicate().toString().contains("hasJobTitle")) {
				jobTitleStatement = statement;
			}
		}
		if (!jobTitleStatement.getPredicate().getURI().contains("roleOf")) {
			//add statement to model works:
//			jobTitleStatement.changeLiteralObject('A');
			jenaModel.remove(jobTitleStatement);
			common.Utility.log.debug("removed statement: " + jobTitleStatement.toString());
		}
		
        %>
        <TR>
        <TD><%= resourceID%></TD>
        <TD><%= feedbackTextGeneratorUtils.getTextualDescription(jenaModel, resourceID)%></TD>
        </TR>
        <%
	}
String anchorFeedbackText = "didn't work :(";
%>
</TABLE>
<BR> Testing getAnchorFeedbackText(): <BR>
<%
try{
	OntologyReader ontReader = new OntologyReader();
	SesameReader sesameReader = new SesameReader(false);
	OntModel jenaModel = feedbackTextGeneratorUtils.getJenaModel(resourceIDs[1]);
	AnchorInfo[] anchorInfo = TextGeneratorUtils.getAnchorFeedbackText(ontReader, sesameReader, jenaModel, resourceIDs[1]);
	for(AnchorInfo singleAnchInfo : anchorInfo){
		anchorFeedbackText += singleAnchInfo.getWords();	
	}
	
} catch (Exception e){
	e.printStackTrace();
}

%>
<BR><%= anchorFeedbackText%><BR>


</BODY>
</HTML>

