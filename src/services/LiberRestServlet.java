package services;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.policygrid.ontologies.OPM;
import org.policygrid.ontologies.ProvenanceGeneric;
import org.policygrid.policies.NLGStatementsSession;
import org.policygrid.policies.PolicyReasoner;
import org.policygrid.policies.PolicySession;

import NLGService.WYSIWYM.model.Anchor;
import NLGService.WYSIWYM.model.SGNode;
import NLGService.WYSIWYM.model.SummationAnchor;
import NLGService.WYSIWYM.ontology.OntologyReader;
import NLGService.WYSIWYM.ontology.OntologyWriter;
import NLGService.WYSIWYM.ontology.SesameReader;
import NLGService.WYSIWYM.services.OntologyInitThread;
import NLGService.WYSIWYM.testclasses.TextTypesGenerator;
import NLGService.WYSIWYM.transformer.AutomaticGenerator;
import NLGService.WYSIWYM.transformer.SemanticGraphTransformer;
import NLGService.WYSIWYM.util.OntologyInputException;
import NLGService.WYSIWYM.util.SesameException;
import NLGService.liber.AnchorInfo;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import common.User;
import common.Utility;

public class LiberRestServlet extends HttpServlet {
	
	public static final String servletLocation = "/ourspaces/LiberRestServlet";
	
	public void init(ServletConfig config) throws ServletException {
		common.Utility.log.debug("Initialising Ontology and Sesame Readers during server's startup");
		ServletContext context = config.getServletContext();
		
		OntologyInitThread ontologyInitThread = new OntologyInitThread(context);
		ontologyInitThread.start();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		// For evalutating time perfomrances:
		long timeBeforeNLG = System.currentTimeMillis();
		
		HttpSession session = request.getSession();
		ServletContext context = request.getSession().getServletContext();
		
		// Get ID of ourSpaces account from the session:
		User userSession = (User) session.getAttribute("use");
		int userDbID = userSession.getID();
		String foafID;
		try {
			 foafID = userSession.getFOAFID(userDbID);
		} catch (Exception e) {
			// Prevent unidentified users to access the NLG service:
			common.Utility.log.debug("Couldn't retrieve the FOAF ID associated with the current ourSpaces account");
			response.getWriter().print("Access denied: Unidentified user");
			return;
		}

		// Retrieve ontology and sesame reader if they are already created, otherwise create them:
		OntologyReader ontReader = (OntologyReader) context.getAttribute("OntologyReader");
		if (ontReader == null) {
			try {
				ontReader = new OntologyReader();
				context.setAttribute("OntologyReader", ontReader);
			} catch (OntologyInputException e) {
				e.printStackTrace();
			} 
		}
		SesameReader sesameReader = (SesameReader) context.getAttribute("SesameReader");
		if (sesameReader == null) {
			try {
				sesameReader = new SesameReader(false);
				context.setAttribute("SesameReader", sesameReader);
			} catch (SesameException e) {
				e.printStackTrace();
			}
		}
		
		
		/* Retrieve parameters */
		// Retrieve the resource ID from the parameters:
		String resourceID = request.getParameter("resourceID");
		resourceID = URLDecoder.decode(resourceID, "ISO-8859-1");
		
		// Retrieve the Property parameter, specifying which property should be retrieved from the repository to generate a description only of that property
		String propertyToNLG = request.getParameter("Property");
		String aggregationThreshold = request.getParameter("AggregationThreshold");

		// Save model in /home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/NLGTest (for testing in TopBraid)
//		File jenaModelFile = new File("../webapps/ourspaces/NLG/JenaModel.xml");
//		FileOutputStream fop = new FileOutputStream(jenaModelFile);
//		RDFWriter w = jenaModel.getWriter("RDF/XML-ABBREV");
//		w.setProperty("xmlbase","http://www.policygrid.org/NLGTest");
//		w.write(jenaModel, fop, "http://www.policygrid.org/NLGTest");
//		fop.flush();
//      fop.close();

		// Generate the Feedback text with anchors:
		// Storing AutomaticGenerator in session (quicker to run NLG service as we only read from class spec files once, but can't have several context-dependent class spec files) 
		AutomaticGenerator autoGenerator = (AutomaticGenerator) context.getAttribute("AutomaticGenerator");
		if (autoGenerator == null) {
			autoGenerator = new AutomaticGenerator(ontReader, sesameReader);
			context.setAttribute("AutomaticGenerator", autoGenerator);
		}
		

		
		boolean nlgProvenance = false;


		// Determine which type of information should be retrieved from the repository:
		String sparqlQuery = "";
		if ("literal".equalsIgnoreCase(propertyToNLG)) {
			sparqlQuery = OntologyWriter.getLiteralValuesQuery(resourceID);
		} else if ("provenance".equalsIgnoreCase(propertyToNLG)) {
			sparqlQuery = OntologyWriter.getInferredProvenanceQuery(resourceID);
			nlgProvenance = true;
		} else if (propertyToNLG != null && !propertyToNLG.equals("")) {
			sparqlQuery = OntologyWriter.getDescriptionAndTypesQuery(resourceID, propertyToNLG);
		} else {
		// Otherwise retrieve every direct metadata available:
			propertyToNLG = "metadata";
			sparqlQuery = OntologyWriter.getDescriptionAndTypesQuery(resourceID);
		}
		
		// Generate Jena model from sparql query:
		OntModel jenaModel = sesameReader.getJenaModelFromSparqlQuery(sparqlQuery);
		
		
		/* Generate the NLG process that will be detected by the policy framework: */
		// Create empty Ontology Model
		Model nlgProcessModel = ModelFactory.createDefaultModel();
		
		//TODO: if we want to record the text generated, we need to create a new Resource with uniqueResourceID 
		String nlgArtifactID = "http://openprovenance.org/ontology#"	+ UUID.randomUUID().toString();
		Resource nlgArtifact = nlgProcessModel.createResource(nlgArtifactID);

		// Record other properties of the NLG process:
		//TODO: send OurSpacesAccount instead of foafID ?
		Resource nlgProcess = createNLGProcess(nlgProcessModel, nlgArtifactID, resourceID, foafID);
		nlgProcess.addProperty(ProvenanceGeneric.nlgType, propertyToNLG);
		if (aggregationThreshold != null) {
			nlgProcess.addProperty(ProvenanceGeneric.aggregationThreshold, aggregationThreshold);
		}
		
		// Print out the NLG Process model:
//		common.Utility.log.debug("nlgProcessModel: ");
//		nlgProcessModel.write(System.out);

		// Apply policies to adapt Jena model depending on context
		//TODO: don't apply policies for testing
//		jenaModel = applyNLGpolicy(jenaModel, nlgProcessModel);
		
		// Generate the liber model (used for NLG) from the jena model:
		SemanticGraphTransformer sgt = autoGenerator.buildGraphFromJenaModel(jenaModel, resourceID);
//		sgt = autoGenerator.buildGraphFromSesameRepo(foafID, resourceID);
		
		// Build the text generator from the model
		TextTypesGenerator generator;
		try {
			generator = new TextTypesGenerator(sgt, ontReader, sesameReader, foafID);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		// Set the aggregation threshold to limit the miximum nb of similar resources in the text
		if (aggregationThreshold != null) {
			try {
				generator.setAggregationThreshold(Integer.parseInt(aggregationThreshold));
			} catch (NumberFormatException e) {
				common.Utility.log.debug("The parameter AggregationThreshold must be an integer. Default value will be used (4)");
			}
		}
		
		AnchorInfo[] anchorText;
		try {
			anchorText = generator.getSurfaceText();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		
		// wrap the generated text with HTML:
		//TODO: adapt to aggregation (getProperty is null?)
		String resourceType = "";
		try {
			resourceType = jenaModel.getResource(resourceID).getProperty(jenaModel.getProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")).getObject().toString();
		} catch (Exception e) {
		}
		String htmlText = prepareHtmlResponse(timeBeforeNLG, resourceID, resourceType, userDbID, generator,
				anchorText, nlgProvenance, propertyToNLG);
		
		// Send the response:
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(htmlText);

		common.Utility.log.debug("HTML feedback text: " + htmlText);
		out.flush();
	}

	/**
	 * Apply the policies to the jena model by running SPIN inferances on the model and removing/adding retraction/assertion statements.
	 * @param jenaModel
	 * @param nlgProcessModel
	 * @return
	 */
	private OntModel applyNLGpolicy(OntModel jenaModel, Model nlgProcessModel) {
		// create model with repository, policies and processes to reason on everything:
		PolicySession psession = PolicyReasoner.createSession();
		psession.addContext(nlgProcessModel);

		// Generate inferances from the policies
		PolicyReasoner.loadPolicy("NLGRetractTranscriptPolicy.spin.owl", psession);
//		PolicyReasoner.loadPolicy("NLGAssertionArtifactPolicy.spin.owl", psession);
		PolicyReasoner.run(psession);
		PolicyReasoner.closeSession(psession);

		/* Process infered statements */
		// Remove statements from Jena Model so that those information aren't included in the text:
		NLGStatementsSession nlgSession = new NLGStatementsSession(psession);
		jenaModel.remove(nlgSession.getRetractionModel());

		// add infered statements to Jena Model:
		jenaModel.add(nlgSession.getAssertionModel());
		
		return jenaModel;
	}

	/**TODO Remove when policy framework implemented (hardcoded policy just for demo) **/
	private void removeStatement(String foafID, String resourceID, OntModel jenaModel) {
		if (foafID.equals("http://xmlns.com/foaf/0.1/#403ffd9e-7f79-417a-8445-a607d50c612a") 
				&& resourceID.equals("http://openprovenance.org/ontology#539166d0-6dba-4224-9cc4-61a0581b0bf2")) {

			OntModel jenaModelCopy = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
			jenaModelCopy.add(jenaModel);
			StmtIterator it = jenaModelCopy.listStatements(null, ResourceFactory.createProperty("http://www.policygrid.org/provenance-generic.owl#", "transcribedBy"), (RDFNode) null);
			while (it.hasNext()) {
				Statement transcriptedByStmnt = (Statement) it.next();
				jenaModel.remove(transcriptedByStmnt);
				common.Utility.log.debug("removed statement: " + transcriptedByStmnt.toString());
			}
		}
	}

	/**
	 * Process the anchorString generated by the NLG service and wrap the text with HTML code so that this text is formated and has anchors to related resources
	 * (to allow users to expand the description)
	 * @param timeBeforeNLG
	 * @param resourceID
	 * @param resourceType 
	 * @param userDbID user ID containted in the database (not foaf ID)
	 * @param generator
	 * @param anchorFeedbackText
	 * @param nlgProvenance
	 * @param propertyToNLG 
	 * @return the text in HTML
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private String prepareHtmlResponse(long timeBeforeNLG,
			String resourceID, String resourceType, int userDbID, TextTypesGenerator generator,
			AnchorInfo[] anchorFeedbackText, boolean nlgProvenance, String propertyToNLG)
			throws IOException, UnsupportedEncodingException {

		// Textual description wrapped in HTML code:
		StringBuffer htmlFeedbackText = new StringBuffer();
		htmlFeedbackText.append("<div id=\"" + resourceID +"\" text-decoration:none> <p class='plscontent'>");

		// Counter for related resources
		int relatedResourcesNb = 0;
		
		//Create a string containing a list of <div> where the text will be added when a user expand the text about a resource:
		StringBuffer expandableTextDivs = new StringBuffer();
		
		// onclick to log when a user expends a textual description (i.e. makes a deeper nlg request)
		String onclickLogger = buildOnclickString(resourceID, userDbID, propertyToNLG);

		// Iterate all parts of the feedback text:
		for (AnchorInfo anchorInfo : anchorFeedbackText) {
			String anchorInfoWords = anchorInfo.getWords();
			anchorInfoWords = formatSpecialRelatedResourcesLabels(anchorInfoWords);
			
			// If this anchor contains informations:
			if (anchorInfoWords != null && !"".equals(anchorInfoWords) && !"The x:".equals(anchorInfoWords)) {
				
				// If this part of the text refers to another resource, create a link to that resource:
				if (anchorInfo.isAnchor()) {
					
					Anchor anchor = generator.getText().getAnchor(anchorInfo.getID());

					//TODO: problem with ourSpacesVRE.owl#hasContact property: anchor.getNode() is null so can't create links to those other resources
					if (anchorInfo.getID() == null || "".equals(anchorInfo.getID()) || anchor.getNode() == null) {
						common.Utility.log.debug("No ID for the node " + anchor.getID());

						if (anchor instanceof SummationAnchor && ((SummationAnchor) anchor).getAggregatedProperty() != null && ((SummationAnchor) anchor).getAggregatedProperty() != "") {
							// Get the property summarized by this anchor :
							String aggregatedProperty = ((SummationAnchor) anchor).getAggregatedProperty();
							String aggregatedPropertyEncoded = URLEncoder.encode(aggregatedProperty, "ISO-8859-1");
							
							String anchoredResourceURI = URLEncoder.encode(resourceID, "ISO-8859-1");
//							String anchoredResourceID = System.currentTimeMillis() + anchoredResourceURI.substring(anchoredResourceURI.indexOf("%23") + 3);
							String anchoredResourceID = anchoredResourceURI.substring(anchoredResourceURI.indexOf("%23") + 3) + aggregatedPropertyEncoded.substring(aggregatedPropertyEncoded.indexOf("%23") + 3) + System.currentTimeMillis() ;

							
							// onclick to log when a user request a description of another resource:
//							onclickLogger = buildOnclickString(resourceID, userDbID, aggregatedProperty);
							if (nlgProvenance) {
								// if the user is exploring the provenance graph, the link to un-aggregate the property will display all the provenance, as we can't specify only one property (because the of the way provenance is represented in the repository)
								htmlFeedbackText.append("<a "+ onclickLogger +" style=\"text-decoration:underline;\" href=\"" + servletLocation + "?resourceID=" 
										+ anchoredResourceURI + "&Property=provenance&AggregationThreshold=50\" rel=\"" + anchoredResourceID + "\" class=\"liber2\">" + anchorInfoWords + "</a>");
							} else {
								htmlFeedbackText.append("<a "+ buildOnclickString(resourceID, userDbID, aggregatedProperty) +" style=\"text-decoration:underline;\" href=\"" + servletLocation + "?resourceID=" 
										+ anchoredResourceURI + "&Property=" + aggregatedPropertyEncoded + "&AggregationThreshold=50\" rel=\"" + anchoredResourceID + "\" class=\"liber2\">" + anchorInfoWords + "</a>");
							}

							expandableTextDivs.append("<div id=\"" + anchoredResourceID + "\"></div>");
							
						} else {
							// Otherwise simply add the text (no anchor)
							htmlFeedbackText.append(anchorInfoWords);
						}

						continue;
					}

					// Retrieve the URI of this resource:
					String anchoredResourceURI = ((SGNode) generator.getGraph().getNode(anchor.getNode().getID())).getUniqueID();
					
					// If that resource is the resource we're already describing, don't create an anchor:
					if (anchoredResourceURI.contains(resourceID)) {
						//TODO: replace nlgLinkTable by the SQL table logging when a user follows a link after a NLG
						// and add the different values to insert in that table as the 2nd argument of log()
						String resourcePageLink = " <a onclick=\"log('log_nlgLink(userid,resid)', '" + userDbID + ",\\'" + resourceID + "\\'');return true;\" href=\"" + Utility.getDetailPage(resourceType, resourceID) + "\"> <span class=\"pageLink\"></span></a>";
						htmlFeedbackText.append("<strong>" + anchorInfoWords + "</strong>" + resourcePageLink);
//								" <a href=\"" + Utility.getDetailPage(resourceType, resourceID) + "\"> resource's page</a>");
						
					// Otherwise, create an anchor to that other resource:
					} else {
						// get onclick to that related resource:
						String onclickRelatedResource = buildOnclickString(anchoredResourceURI, userDbID, propertyToNLG);
						
						// Convert # to its ASCII code (%23) so it can be sent as a parameter with a GET (use URLConverter instead?)
						anchoredResourceURI = URLEncoder.encode(anchoredResourceURI, "ISO-8859-1");
						
						// Create an ID for the related resource: System current time + related resource ID (see BUG: can't expend twice on the same resource):
						String anchoredResourceID = /*resourceID.substring(resourceID.indexOf("#") + 1)*/ System.currentTimeMillis() + anchoredResourceURI.substring(anchoredResourceURI.indexOf("%23") + 3);
//						htmlFeedbackText += "<a style=\"text-decoration:underline;\" href=\"" + anchoredResourceURI + "\" rel=\"" + anchoredResourceID + "\" class=\"liber\">" + anchorInfoWords + "</a>";
						htmlFeedbackText.append("<a " + onclickRelatedResource + "style=\"text-decoration:underline;\" href=\"" + servletLocation + "?resourceID=" + anchoredResourceURI);
						
						// Explore the provenance graph of related resources:
						if (nlgProvenance) {
							htmlFeedbackText.append("&Property=provenance");
						}
						
						htmlFeedbackText.append("\" rel=\"" + anchoredResourceID + "\" class=\"liber2\">" + anchorInfoWords + "</a>");
						
						// add a div where the text about this linked resource will be added
						expandableTextDivs.append("<div id=\"" + anchoredResourceID + "\"></div>");
						
						relatedResourcesNb++;
					}
					
				} else {
					//Otherwise, just add the text without anchor:
					htmlFeedbackText.append(anchorInfoWords);
				}
			}
		}
		
		// add the expandable divs at the end of the text to allow user to explore related resoruces:
		htmlFeedbackText.append("</p>" + expandableTextDivs.toString() + "</div>");
		
		// if no info about the resource, display a message instead of just the name of the resource
		if (anchorFeedbackText.length <= 7 ) {
			htmlFeedbackText.append("no information available.");
		} 		

		// Write performances file:
		writeTimeStats(timeBeforeNLG, resourceID, relatedResourcesNb);
	
		return htmlFeedbackText.toString();
	}

	/**
	 * Deal with special resources types that needs to be formated (e.g. academic discipline)
	 * @param anchorInfoWords
	 * @return
	 */
	private String formatSpecialRelatedResourcesLabels(String anchorInfoWords) {
		if (anchorInfoWords != null && anchorInfoWords.contains("http://www.policygrid.org/academic-disciplines")) {
			anchorInfoWords = " " + anchorInfoWords.substring(anchorInfoWords.indexOf("#")+1);
			
			StringBuffer formattedWordsBuffer = new StringBuffer();
			for (String word : anchorInfoWords.split("(?=\\p{Upper})")) {
				formattedWordsBuffer.append(" " + word);
			}
			
			anchorInfoWords = formattedWordsBuffer.toString();
		}
		return anchorInfoWords;
	}

	private String buildOnclickString(String resourceID, int userDbID,
			String propertyToNLG) {
		return "onclick=\"log('log_nlgRequest(userid,resid,nlgtype,depth)', '" + userDbID + ",\\'" + resourceID + "\\',\\'" + propertyToNLG + "\\', 1');return true;\" ";
	}

	/**
	 * Writes how long the NLG service took to execute in a stat file
	 * @param timeBeforeNLG
	 * @param resourceID
	 * @param relatedResourcesNb
	 */
	private void writeTimeStats(long timeBeforeNLG, String resourceID,
			int relatedResourcesNb) {
		common.Utility.log.debug("Time spent to NLG resource " + resourceID +
				"\n with " + relatedResourcesNb + " related ressources :\n" + (System.currentTimeMillis() - timeBeforeNLG) + " ms");
		
		// Writing time statistic file: (in /home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/NLG/timeStat.txt)
		try {
			FileWriter timeStatFile = new FileWriter("../webapps/ourspaces/NLG/timeStat.txt", true);
			timeStatFile.append(resourceID + ";" + relatedResourcesNb + ";" + (System.currentTimeMillis() - timeBeforeNLG) + "\n");
			timeStatFile.close();
		} catch (Exception e) {
			common.Utility.log.debug("Failed to write NLG Time statistic file ");
		}
	}
	
	/**
	 * Adds the provenance to the model.
	 * @param model
	 * @param uniqueResourceID
	 * @param userSession
	 * @return the NLG process
	 */
	protected Resource createNLGProcess(Model model, String uniqueResourceID, String resourceDescribedID, String userID){

		Resource myResource = model.createResource(uniqueResourceID);
		Resource user = model.createResource(userID);
		String processId = "http://www.policygrid.org/ourspacesVRE.owl#"	+ UUID.randomUUID().toString();
		
		// NLGAction process
		Resource nlgProcess = model.createResource(processId);
		nlgProcess.addProperty(model.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "type"),	
						   model.createResource("http://www.policygrid.org/ourspacesVRE.owl#NLGAction"));
		nlgProcess.addProperty(ProvenanceGeneric.title, "Generate textual description of a resource");
		
		//Edge to resource described
		Resource usedEdgeResource = model.createResource("http://www.policygrid.org/ourspacesVRE.owl#"	+ UUID.randomUUID().toString());		
		usedEdgeResource.addProperty(model.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "type"), OPM.Used);
		usedEdgeResource.addProperty(OPM.cause, model.createResource(resourceDescribedID));
		usedEdgeResource.addProperty(OPM.effect, nlgProcess);
		
		//Edge to agent that requested the text
		Resource wasControlledByEdge = model.createResource("http://www.policygrid.org/ourspacesVRE.owl#"	+ UUID.randomUUID().toString());
		wasControlledByEdge.addProperty(model.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "type"),	
				   OPM.WasControlledBy);
		wasControlledByEdge.addProperty(OPM.cause, user);
		wasControlledByEdge.addProperty(OPM.effect, nlgProcess);
		
		//Edge to the new artifact 
		//TODO: only add if we want to record the NLG artificat (i.e. the text generated)
		Resource wasGeneratedByEdge = model.createResource("http://www.policygrid.org/ourspacesVRE.owl#"	+ UUID.randomUUID().toString());		
		wasGeneratedByEdge.addProperty(model.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "type"),	
				   OPM.WasGeneratedBy);
		wasGeneratedByEdge.addProperty(OPM.cause, nlgProcess);
		wasGeneratedByEdge.addProperty(OPM.effect, myResource);
		
		return nlgProcess;
	}

}
