package common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.openrdf.OpenRDFException;
import org.openrdf.query.Update;
import org.policygrid.ontologies.OPM;
import org.policygrid.ontologies.OurSpacesVRE;
import org.policygrid.ontologies.ProvenanceGeneric;
import org.policygrid.policies.PolicyReasoner;
import org.policygrid.policies.PolicySession;
import org.policygrid.policies.RequiredFieldsSession;
import org.xml.sax.SAXException;

import provenanceService.Edge;
import provenanceService.Graph;
import provenanceService.ProvenanceService;
import provenanceService.ProvenanceServiceException;
import provenanceService.ProvenanceServiceImpl;
import provenanceService.RDFProvider;

import com.hp.hpl.jena.iri.IRI;
import com.hp.hpl.jena.iri.IRIFactory;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.arp.impl.URIReference;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import common.Properties;

/**
 * Servlet implementation class ResourceUploadServlet
 */
public class ResourceUploadServletNew extends HttpServlet {
	private static final long serialVersionUID = -241888150958411703L;
	RDFi rdf = new RDFi();
	long now;
	OntologyHandler ontology;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ResourceUploadServletNew() {
		super();
		 try {
			ontology = new OntologyHandler();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {

			response.setContentType("text/plain");
			if(request.getParameter("getURI") != null && "true".equals(request.getParameter("getURI"))){
				String uniqueResourceID = "http://openprovenance.org/ontology#"	+ UUID.randomUUID().toString();
				PrintWriter writer = response.getWriter();
				writer.write(uniqueResourceID);
				writer.flush();
				writer.close();
				return;
			}
			if(request.getParameter("log") != null){
				HttpSession session = request.getSession();
				String uniqueResourceID = request.getParameter("URI");
				String resourceType = request.getParameter("resourceType");
				log(session, uniqueResourceID, resourceType);
				return;
			}
						if (!ServletFileUpload.isMultipartContent(request)) {
							createRDF(request, response);
						}
						else{
							ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
							PrintWriter writer = response.getWriter();
							try {
								java.util.List<FileItem> items = uploadHandler.parseRequest(request);
								for (FileItem item : items) {
									if (!item.isFormField()) {									
							      		String fileName = item.getName();
					          			if(fileName != null) {
							        		//write the image to a file
							        		File theFile = new File(Configuration.getParaemter("file", "tempFolder")+fileName);
							        		item.write(theFile);
					           			}
					          			String folder = "/";
					    				if(request.getParameter("folder") != null)
					    					folder =  request.getParameter("folder");
					          			
										String uri = "";
										HttpSession session = request.getSession();
										User userSession = (User) session.getAttribute("use");
										String projectID = (String) session.getAttribute("projectID");
										String uniqueResourceID = "http://openprovenance.org/ontology#" + UUID.randomUUID().toString();
										uri = Utility.storeFile(userSession.getID(), item.getName(), new File(Configuration.getParaemter("file", "tempFolder")+fileName), uniqueResourceID, projectID,folder);
										//Returns the uri of the file.
										writer.write(uri);
										writer.flush();										
										break; // assume we only get one file at a time
									}
								}
							} catch (FileUploadException e) {
								e.printStackTrace();
								throw new RuntimeException(e);
							} catch (Exception e) {
								e.printStackTrace();
								throw new RuntimeException(e);
							} finally {
								writer.close();
							}
						}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create link from previous version of the resource to the current one. Create also the version number of current version.
	 * @param resourceId if null, the newId is the first version
	 * @param newId URI of new artifact.
	 * @param model
	 */
	protected void createVersionOf(String resourceId, String newId, Model model){		
		Resource newResource = model.createResource(newId);
		if(resourceId == null){
			//Do not add any version number until necessary.
			//newResource.addProperty(OpmResource.versionNumber,"1");
			return;
		}
		Resource oldResource = model.createResource(resourceId);	
		//l should contain only one property
		List<Properties> l = null;
		try {
			l = rdf.getResourceProperties(resourceId, ProvenanceGeneric.versionNumber.getLocalName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//No previous version number, assuming the first version
		if(l == null || l.size()==0){
			oldResource.addProperty(ProvenanceGeneric.versionNumber,"1");		
			newResource.addProperty(ProvenanceGeneric.versionNumber,"2");			
		}
		else{
			int version = Integer.parseInt(l.get(0).getValue());
			newResource.addProperty(ProvenanceGeneric.versionNumber,""+(version+1));	
		}
		newResource.addProperty(ProvenanceGeneric.previousVersion,oldResource);
	}
	
	/**
	 * Extracts tags from the title
	 * @param title Title to extracts tags from
	 * @param uniqueResourceID Resource id
	 * @param userSession User session
	 */
	protected void extractTags(String title, String uniqueResourceID, User userSession){
		//Extract tags from the title                 	   
    	try {
        	Vector<String> titleSplit = Utility.getKeywordsFromString(title);
        	Tag tag = new Tag();
        	String taggerID = userSession.getFOAFID();
        	//To fix add stopwords               	   
        	for(int k = 0; k < titleSplit.size(); k++)
        	{
        		tag.addTag((String)titleSplit.get(k), uniqueResourceID, taggerID);
        	}
        	Activities act = new Activities();
       		act.addActivity(userSession.getID(), 2, uniqueResourceID, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds the access rights to the resource depending on the project and privacy.
	 * @param projectID
	 * @param privateRes
	 * @param model
	 * @param uniqueResourceID
	 * @param userSession
	 * @return
	 */
	protected String addAccessRights(String projectID, boolean privateRes, Model model, String uniqueResourceID, User userSession){
		try {
			if (!privateRes) {
				AccessControl.setPublic(uniqueResourceID);
				AccessControl.setUserPermission("view", ""+userSession.getFOAFID(), uniqueResourceID, true);
				AccessControl.setUserPermission("edit", ""+userSession.getFOAFID(), uniqueResourceID, true);
				AccessControl.setUserPermission("download", ""+userSession.getFOAFID(), uniqueResourceID, true);
				AccessControl.setUserPermission("remove", ""+userSession.getFOAFID(), uniqueResourceID, true);
			}
			else{
				AccessControl.setUserPermission("view", ""+userSession.getFOAFID(), uniqueResourceID, true);
				AccessControl.setUserPermission("edit", ""+userSession.getFOAFID(), uniqueResourceID, true);
				AccessControl.setUserPermission("download", ""+userSession.getFOAFID(), uniqueResourceID, true);
				AccessControl.setUserPermission("remove", ""+userSession.getFOAFID(), uniqueResourceID, true);
				}
			//Handle project access rights
			if(projectID != null && !projectID.equals("")) {
				if (privateRes) {
					AccessControl.setGroupPermission("view", projectID, uniqueResourceID, true);
					AccessControl.setGroupPermission("download", projectID, uniqueResourceID, true);
					 
					common.Project project = new common.Project();
					String pProjectID = project.getParentProject(projectID);
					common.Utility.log.debug("Setting permission "+ uniqueResourceID +" for parent project :" + pProjectID);
					if (pProjectID != null) {
						AccessControl.setGroupPermission("view", pProjectID, uniqueResourceID, true);
						AccessControl.setGroupPermission("download", pProjectID, uniqueResourceID, true); 
					}
				 
				} 
				else {
					 AccessControl.setPublic(uniqueResourceID);
				}
				String[] fields = projectID.split("#");
				
				Resource project = model.createResource(projectID);
				Resource myResource = model.createResource(uniqueResourceID);
				myResource.addProperty(ProvenanceGeneric.producedInProject,project);
				Activities act = new Activities();
				act.addActivity(userSession.getID(), 26, uniqueResourceID, projectID);
				return "project.jsp?id="+fields[1];
			 }
			Activities act = new Activities();
			act.addActivity(userSession.getID(), 1, uniqueResourceID, "");
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "myhome.jsp";		
	}
	
	/**
	 * Runs policy reasoner to find which policies were triggered for this resource. Then adds link wasInfluencedBy from the event to the policy.
	 * @param model
	 * @param resourceId
	 */
	protected void addWasInfluencedBy(Model model, String resourceId){
		
		PolicySession psession = PolicyReasoner.createSession();
		psession.addContext(model);
		PolicyReasoner.loadAllPolicies(psession,"UP");
		PolicyReasoner.run(psession);
		PolicyReasoner.closeSession(psession);
		RequiredFieldsSession fields = new RequiredFieldsSession(psession);
		
		//List all processes that generated the artifact
		String queryString = 
		"SELECT ?process  " +
		"WHERE {" +
		"      ?x a <http://openprovenance.org/ontology#WasGeneratedBy> . " +
		"      ?x <http://openprovenance.org/ontology#effect> <"+resourceId+"> . " +
		"      ?x <http://openprovenance.org/ontology#cause> ?process . " +
		"      }";

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(queryString, model);
		ResultSet results = qe.execSelect();
		Map<String,List<String>> policies = new HashMap<String, List<String>>();
		while (results.hasNext()) {
			QuerySolution soln = results.next();
			RDFNode n = soln.get("process"); 
			if (n.isResource()) {
				Resource r = (Resource) n;
				if (!r.isAnon()) {
					String event = r.getURI();
						Vector<String> activations = fields.listPolicyActivations(r.getURI());
						for(String policy: activations){
							if(!policies.containsKey(event))
								policies.put(event, new ArrayList<String>());
							policies.get(event).add(policy);
						}
				}
			}
		}		
		for(String event : policies.keySet()){
			for(String policy : policies.get(event)){
				//Add the wasInfluencedBy link
				String wasInfluencedByID = "http://openprovenance.org/ontology#"	+ UUID.randomUUID().toString();
				model.add(model.getResource(wasInfluencedByID), RDF.type, model.getResource("http://www.policygrid.org/provenance-policies#WasInfluencedBy"));
				model.add(model.getResource(wasInfluencedByID), model.getProperty("http://openprovenance.org/ontology#effect"), model.getResource(event));
				model.add(model.getResource(wasInfluencedByID), model.getProperty("http://openprovenance.org/ontology#cause"), model.getResource(policy));			
			}
		}

	}
	
	/**
	 * 
	 * Test the policies and returns the required properties.
	 * @param model
	 * @return The list required properties.
	 */
	protected String testPolicies(Model model, String resourceId) {
		try {
			PolicySession psession = PolicyReasoner.createSession();
			psession.addContext(model);
			PolicyReasoner.loadAllPolicies(psession,"UP");
			PolicyReasoner.run(psession);
			PolicyReasoner.closeSession(psession);
			RequiredFieldsSession fields = new RequiredFieldsSession(psession);
			JSONArray policies = new JSONArray();
			ResIterator it = model.listSubjects();
			/**List of all resources from policy inferences*/
			List<Resource> list = new ArrayList<Resource>();
			while(it.hasNext()){
				Resource r = it.next();
				if(!list.contains(r))
					list.add(r);
			}
			NodeIterator nit = model.listObjects();
			while(nit.hasNext()){
				RDFNode r = nit.next();
				if(!r.isResource())
					continue;
				if(!list.contains(r.as(Resource.class)))
					list.add(r.as(Resource.class));
			}
			for(Resource r : list){
				if(r.getURI().equals(resourceId))
					continue;
				Vector<String> mandatory = fields.listMandatoryFields(r.getURI());
				if(mandatory != null && mandatory.size()>0){
					for(String s : mandatory){						
						String title = fields.getPolicyTitleForRequiredField(r.getURI(), mandatory.get(0));
						String policyURI = fields.getActivePolicy(r.getURI(), mandatory.get(0));
						JSONObject policy = new JSONObject();
						policy.put("resource", r.getURI());
						policy.put("property", s);
						policy.put("title", title);
						policy.put("policy", policyURI);
						policy.put("min", 1);
						policies.add(policy);
					}
				}
			}
			Vector<String> mandatory = fields.listMandatoryFields(resourceId);
			for(String s : mandatory){
				String title = fields.getPolicyTitleForRequiredField(resourceId, s);
				String policyURI = fields.getActivePolicy(resourceId, s);
				JSONObject policy = new JSONObject();
				policy.put("property", s);
				policy.put("title", title);
				policy.put("policy", policyURI);
				policy.put("min", 1);
				Vector<String> types = fields.listFieldTypes(resourceId, s);
				if(types.size() > 0)
					policy.put("type", types.get(0));
				policies.add(policy);				
			}
			return policies.toString();
		} catch (Exception e) {
			common.Utility.log.debug(e);
		}
		return "";
	}

	/**
	 * Adds the provenance to the model.
	 * @param model
	 * @param uniqueResourceID
	 * @param userSession
	 */
	protected void addProvenance(Model model, String uniqueResourceID, String previousVersion, User userSession, String type) throws SAXException{

		Resource myResource = model.createResource(uniqueResourceID);
		Resource user = model.createResource(userSession.getFOAFID());
		ProvenanceServiceImpl impl = ProvenanceService.getSingleton();
		String session = impl.startSession();
		String processId;

		impl.addRDFGraph(session, model);
		try {
			if(previousVersion == null){
				//If uploaded resource is an artifact, choose uploadResource, otherwise createAction
				if(ontology.getSubclassListFull("general", "http://openprovenance.org/ontology#Artifact").contains(type)){
					processId = impl.addNode(session, "http://www.policygrid.org/ourspacesVRE.owl#UploadResource");			
					impl.addTitle(session, processId, Utility.getLocalName(type) + " upload");
				}
				else{
					processId = impl.addNode(session, "http://www.policygrid.org/ourspacesVRE.owl#CreateAction");
					impl.addTitle(session, processId, Utility.getLocalName(type) + " creation");
				}
			}
			else{
					processId = impl.addNode(session, "http://www.policygrid.org/ourspacesVRE.owl#EditResource");
				
				impl.addTitle(session, processId, Utility.getLocalName(type)+" editing");
				//Edge to the old artifact
				String edge = impl.addCausalRelationship(session, OPM.Used.getURI(), processId, previousVersion);
				impl.addCustomProperty(session, edge, OurSpacesVRE.timestamp.getURI(),""+now);		
				
				//Edge was derived from
				String edge2 = impl.addCausalRelationship(session, OPM.WasDerivedFrom.getURI(), myResource.getURI(), previousVersion);
				impl.addCustomProperty(session, edge2, OurSpacesVRE.timestamp.getURI(),""+now);		
				
			}
			impl.addCustomProperty(session, processId, OurSpacesVRE.timestamp.getURI(),""+now);
			//Edge to agent
			String edgeAgent = impl.addCausalRelationship(session, OPM.WasControlledBy.getURI(), processId, user.getURI());
			impl.addCustomProperty(session, edgeAgent, OurSpacesVRE.timestamp.getURI(),""+now);		
					
			//Edge to the new artifact
			String edgeResource = impl.addCausalRelationship(session, OPM.WasGeneratedBy.getURI(), myResource.getURI(), processId);
			impl.addCustomProperty(session, edgeResource, OurSpacesVRE.timestamp.getURI(),""+now);
			impl.clean(session);
		} catch (ProvenanceServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.add(impl.getModel(session));
		impl.rollback(session);
		
	}
	protected void addProperty(Model model, Resource myResource, JSONObject prop){

		String idProperty = prop.get("idProperty").toString();
		String type = prop.get("type").toString();
		JSONArray value = (JSONArray) prop.get("value");
		if (value == null || value.size() == 0)
			return;
		//Processes treated differently - conversion to OPM from OPMV
		if (idProperty.startsWith("http://purl.org/net/opmv/ns#")) {
			String edgeName = Utility.getLocalName(idProperty);
			//Capitalize first letter - opmv has lowercase, opm has uppercase.
			edgeName = String.format( "%s%s",Character.toUpperCase(edgeName.charAt(0)),edgeName.substring(1) );
			String edgeType = "http://openprovenance.org/ontology#"+edgeName;

			
			ProvenanceServiceImpl impl = ProvenanceService.getSingleton();
			String session = impl.startSession();
			try {
				for (int j = 0; j < value.size(); j++) {
					String uri = value.get(j).toString();
					Resource r = model.getResource(uri);
					/* if(r.getProperty(RDF.type) != null)
					 * impl.addExistingResource(session,
					 * r.getURI(),
					 * r.getProperty(RDF.type).getResource().getURI(),"");
					 * else
					 * impl.addExistingResource(session,
					 * value.get(j).toString()); */
					// Cause is the value, effect is the myResource.
					String edgeId = impl.addCausalRelationship(session, edgeType, myResource.getURI(), value.get(j).toString());
					impl.addTitle(session, edgeId, Utility.getLocalName(idProperty));
				}
				impl.clean(session);
			} catch (ProvenanceServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//model.add(impl.getModel(session));
			Graph g = impl.getGraph(session);
			model.add(impl.getProvProvider().getRDFProvider().getGraphModel(g));
			impl.rollback(session);
		}
		//Geo location
		else if ("geoMultiple".equals(type)) {
			addGeoProperty(myResource, idProperty, value, model);
		} 
		//Multiproperties
		else if ("multiProperties".equals(type)) {
			addMultiProperty(myResource, prop, model);
		}

		/*if(idProperty.equals("http://www.policygrid.org/academic-disciplines#hasAcademicDiscipline") ||
			
				idProperty.equals("http://www.policygrid.org/academic-disciplines#hasDisciplineTag") 
				){
			addAcademicDiscipline(myResource, idProperty, value, model);
		}*/
		//Literal
		else if ("literal".equals(type)) {			
			for (int j = 0; j < value.size(); j++) {
				String val = value.get(j).toString();
				myResource.addProperty(model.createProperty(idProperty),val.toString());
			}

		} //Resource
		else if ("resource".equals(type) || "resourceNew".equals(type)) {
			for (int j = 0; j < value.size(); j++) {
				String val = value.get(j).toString();
				myResource.addProperty(model.createProperty(idProperty),model.createResource(val.toString()));
			}
		} // Date - same as literal 
		else if ("date".equals(type)) {
			for (int j = 0; j < value.size(); j++) {
				String val = value.get(j).toString();
				myResource.addProperty(model.createProperty(idProperty),val.toString());
			}
		}
		
	}

	protected boolean requiresNewVersion(JSONObject res){
		List<String> sub = ontology.getSubclassListFull("general", "http://openprovenance.org/ontology#Artifact");
		sub.add("http://openprovenance.org/ontology#Artifact");
		return sub.contains(res.get("type").toString()) && res.containsKey("file") && !res.get("file").toString().equals("null") && !res.get("file").toString().equals("");
	}
	
	protected Model getJSONModel(JSONObject res, User userSession, boolean commit, String projectID) throws ServletException, IOException, OpenRDFException, SAXException{

		// Create empty Ontology Model
		Model model = ModelFactory.createDefaultModel();
		Resource person = model.createResource(userSession.getRDFID());	
		String uniqueResourceID = res.getString("URI");//"http://openprovenance.org/ontology#"	+ UUID.randomUUID().toString();
		Resource myResource = model.createResource(uniqueResourceID);
		/**Old version id*/
		String previousVersionId = null;
		if(res.containsKey("resourceId")){
			previousVersionId= res.get("resourceId").toString();
		}
		//No file and no previous version
		//We do not care any more - for processess there is no file
		/*if((!res.containsKey("file") || res.get("file").toString().equals(""))&& previousVersionId==null){
			writer.write("File is missing");
			writer.flush();
			return;
		}*/	
		boolean privateRes = Boolean.parseBoolean(res.get("private").toString());
		
		String fileURI = null;
		//Get the file from the previous version.
		if((!res.containsKey("file") || res.get("file").toString().equals("") || res.get("file").toString().equals("null")) && previousVersionId != null){
			List<Properties> l = rdf.getResourceProperties(previousVersionId, ProvenanceGeneric.hasURI.getLocalName());
			if(l != null && l.size()>0)
				fileURI = l.get(0).getValue();
		}
		else if(res.containsKey("file")){
			fileURI = res.get("file").toString();
		}

		
		//Add properties
		myResource.addProperty(model.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "type"),	model.createResource(res.get("type").toString()));
		myResource.addProperty(ProvenanceGeneric.depositedBy,person);
		myResource.addProperty(OurSpacesVRE.timestamp,""+now);
		if(fileURI != null && !fileURI.equals("null"))
			myResource.addProperty(ProvenanceGeneric.hasURI,fileURI);
		

		boolean wasGeneratedByAdded = false;
		boolean accessRightsAdded = false;
		//Fill the properties of the artifact
		JSONArray properties = res.getJSONArray("properties");
		for (int i = 0; i < properties.size(); i++) {
			JSONObject prop = (JSONObject) properties.get(i);
			addProperty(model, myResource, prop);

			String idProperty = prop.get("idProperty").toString();
			JSONArray value = (JSONArray) prop.get("value");
			if(idProperty.equals("http://purl.org/net/opmv/ns#wasGeneratedBy")){
				wasGeneratedByAdded = true;
			}
			//Extract tags from the title
			if(commit && "http://www.policygrid.org/provenance-generic.owl#title".equals(idProperty))
				extractTags(value.get(0).toString(),uniqueResourceID, userSession);
			if(idProperty.equals("http://www.policygrid.org/provenance-generic.owl#producedInProject")){
				//Add access rights
				for (int j = 0; j < value.size(); j++) {
					accessRightsAdded = true;
					//myResource.addProperty(model.createProperty(idProperty),value.get(j).toString());
					if(commit)
						addAccessRights(value.get(j).toString(), privateRes, model, uniqueResourceID, userSession);
				}
				//continue;
			}
			
		}
		if(commit && !accessRightsAdded){
			addAccessRights(projectID, privateRes, model, uniqueResourceID, userSession);					
		}
		//Add the previous version only if it is an artifact and a file or URI is specified.
		if(requiresNewVersion(res)){		
			createVersionOf(previousVersionId, uniqueResourceID, model);
		}
		//Add the provenance, in case of a new version or the user didn't specified wasGeneratedBy property
		if(!commit || !wasGeneratedByAdded || requiresNewVersion(res))
			addProvenance(model, uniqueResourceID, previousVersionId, userSession, res.get("type").toString());
		return model;
		
	}
	/**
	 * Creates RDF about resource
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public void createRDF(HttpServletRequest request,
			HttpServletResponse response) throws ParserConfigurationException,
			SAXException, IOException {
		now = System.currentTimeMillis();

		try {
			String commit = request.getParameter("commit");
			PrintWriter writer = response.getWriter();
			HttpSession session = request.getSession();


			
			String projectID = (String) session.getAttribute("projectID");
			User userSession = (User) session.getAttribute("use");

			// Create empty Ontology Model
			JSONObject res = (JSONObject) JSONSerializer.toJSON(request.getParameter("resource"));
			boolean previousVersion = false;
			if(!requiresNewVersion(res) && "true".equals(commit) && res.get("resourceId") != null){	
					//TODO rename the resource to old version and delete the resource
					res.put("URI", res.get("resourceId").toString());			
					previousVersion = true;
					res.remove("resourceId");
			}
				//Create a new version
			Model model = getJSONModel(res, userSession, commit.equals("true"), projectID);
			String uniqueResourceID = res.getString("URI");

			
			//Write the model to RDF database
			if("true".equals(commit)){
				
				//TODO add wasInfluencedBy property to the upload process.
				if(!requiresNewVersion(res) && previousVersion){
					//Delete the previous version
					common.ResourceUploadBean rub = new common.ResourceUploadBean();
					HashMap<String, OntProperty> properties = new HashMap<String, OntProperty> ();
					HashMap<String, String> propertiesLocation = new HashMap<String, String>();
					List<common.Properties> props = rub.loadResourceProperties(uniqueResourceID, properties, propertiesLocation, "all"	);
					StringBuilder deleteQuery = new StringBuilder();
					deleteQuery.append("DELETE DATA{");
					deleteQuery.append("<"+uniqueResourceID+"> a <"+rdf.getResourceType(uniqueResourceID)+">.");
					for (common.Properties prop : props) {
						if(prop.getProperty().startsWith("http://purl.org/net/opmv/ns#")){
							//Skip provenance, add it separately later
							continue;
						}
						//We want to delete geo and discourse properties and timestamp and depositedBy
						if(ResourceUploadBean.noShowProperties.contains(Utility.getLocalName(prop.getProperty()))&& 
								!(prop.getProperty().equals("http://www.policygrid.org/ourspacesVRE.owl#timestamp") ||
								  prop.getProperty().startsWith("http://www.policygrid.org/provenance-generic.owl#depositedBy") ||
								  prop.getProperty().startsWith("http://www.policygrid.org/geo-properties.owl#") ||
										prop.getProperty().startsWith("http://swan.mindinformatics.org/ontologies/1.2/discourse-relationships/"))){
							continue;
						}
						
						if(provenanceService.Utility.isURI(prop.value))
							deleteQuery.append("<"+uniqueResourceID+"> <"+prop.getProperty()+"> <"+prop.value+">.");
						else
							deleteQuery.append("<"+uniqueResourceID+"> <"+prop.getProperty()+"> \""+prop.value+"\".");
					}
					//Add the provenance
					ProvenanceServiceImpl impl = ProvenanceService.getSingleton();
					provenanceService.Node n = impl.getDataProvider().getNode(null, uniqueResourceID);
					impl.getDataProvider().getAdjacencies(null,n, 2);
					for(Edge e : n.getAdjacencies()){						
						String property = e.getType();
						property = Utility.getLocalName(property);
						//lowercase first letter
						property = property.substring(0,1).toLowerCase() + property.substring(1);						
						//Leave only the provenance starting from the resource
						if(e.getTo().getId().equals(uniqueResourceID))
							continue;//prop2.value = e.getFrom().getId();
						
						deleteQuery.append("<"+e.getId()+"> <"+OPM.effect.getURI()+"> <"+e.getFrom().getId()+">.");
						deleteQuery.append("<"+e.getId()+"> <"+OPM.cause.getURI()+"> <"+e.getTo().getId()+">.");
						deleteQuery.append("<"+e.getId()+"> <"+RDF.type.getURI()+"> <"+e.getType()+">.");
					}
					deleteQuery.append("}");
					Connections con = new Connections();
					con.repConnect();
					//Delete previous version.
					Update up = con.getRepConnection().prepareUpdate(org.openrdf.query.QueryLanguage.SPARQL, deleteQuery.toString());
					up.execute();
					//ResourceAccess.removeResource(uniqueResourceID, userSession, false); 		
					//TODO rename the resource to old version and delete the resource			
				}
				addWasInfluencedBy(model, uniqueResourceID);
				//Create a new version
				ByteArrayOutputStream outp = new ByteArrayOutputStream();
				RDFi rdfi = new RDFi();
				model.write(outp);
				InputStream in = new ByteArrayInputStream(outp.toByteArray());
				rdfi.write(in);
				
				log( session, uniqueResourceID, res.get("type").toString());
				
				//Send the uri of the new resource back.
				writer.write(uniqueResourceID);
				writer.flush();
			}
			else{
				// Add the properties of possible other open upload dialog
				// This is in case that there are policies affecting connection of two entities linked together, e.g. artefact and process
				Model modelPolicies = ModelFactory.createDefaultModel();
				modelPolicies.add(model);
				if(request.getParameter("otherDialogs") != null && request.getParameter("otherDialogs").length()>0 && !request.getParameter("otherDialogs").equals("null")){
					JSONArray otherDialogs = (JSONArray) JSONSerializer.toJSON(request.getParameter("otherDialogs"));
					//First initialize the otherResource
					for (int i = 0; i < otherDialogs.size(); i++) {
						JSONObject dial = (JSONObject) otherDialogs.get(i);
						Model modelDial = getJSONModel(dial, userSession, false, projectID);
						modelPolicies.add(modelDial);
					}					
				}
				//Debug - lets see what we are writing to the repository
				//modelPolicies.write(System.out);
				//Send the results of executing the policies.
				writer.write(testPolicies(modelPolicies, uniqueResourceID));
				writer.flush();
			}
		} catch (Exception e) {
			common.Utility.log.debug(e);
			e.printStackTrace();
			
		}

	}
	/**
	 * Adds geo property to the resource.
	 * @param myResource
	 * @param idProperty
	 * @param value
	 * @param model
	 */
	protected void addGeoProperty(Resource myResource,String idProperty, JSONArray value, Model model){
		for (int j = 0; j < value.size(); j++) {
			//Data are in the array name, lon, lat
			JSONArray val = (JSONArray) value.get(j);
			String name = val.get(0).toString();
			String lon = val.get(1).toString();
			String lat = val.get(2).toString();
			
			String locationID = "http://www.geonames.org/ontology#" + UUID.randomUUID().toString();
			
			Resource feature = model.createResource(locationID);
			feature.addProperty(model.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#","type"), model.createResource("http://www.geonames.org/ontology#Feature"));
			feature.addProperty(model.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#", "lat"), lat);
			feature.addProperty(model.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#", "long"), lon);
			feature.addProperty(model.createProperty("http://www.geonames.org/ontology#", "name"), name);							
			myResource.addProperty(model.createProperty(idProperty),feature);
		}
	}
	
	protected void addMultiProperty(Resource myResource, JSONObject property, Model model){
		Resource middleNode;
		String propertyName = property.getString("idProperty");
		//Resource already has the discipline info, just add the information, don't create a new discipline info resource
		if(myResource.hasProperty(model.createProperty(propertyName))){
			middleNode = myResource.getProperty(model.createProperty(propertyName)).getResource();
		}
		else{
			String middleNodeID = "http://www.policygrid.org/ourspacesVRE.owl#" + UUID.randomUUID().toString();
			middleNode = model.createResource(middleNodeID);
			middleNode.addProperty(RDF.type, model.createResource(property.getString("range")));
			myResource.addProperty(model.createProperty(propertyName),middleNode);

		}
		
		
		JSONArray subProperties = property.getJSONArray("subProperties");
		for (int j = 0; j < subProperties.size(); j++) {
			JSONObject subproperty = subProperties.getJSONObject(j);
			addProperty(model, middleNode, subproperty);

			/*
			//Check if the value is URI or literal
			IRIFactory iriFactory = IRIFactory.semanticWebImplementation();
			boolean includeWarnings = false;
			IRI iri;
			iri = iriFactory.create(name); // always works
			if (iri.hasViolation(includeWarnings)) 
				disciplineInfo.addProperty(model.createProperty(idProperty), name);
			else
				disciplineInfo.addProperty(model.createProperty(idProperty), model.createResource(name));
			
			myResource.addProperty(model.createProperty("http://www.policygrid.org/ourspacesVRE.owl#hasDisciplineInfo"),disciplineInfo);*/
		}
	}
	
	
	protected void log(HttpSession session, String uniqueResourceID, String resourceType){		
		long end = System.currentTimeMillis();
		long start = (Long)session.getAttribute("uploadstart");		
		long time = end-start;		
		User userSession = (User) session.getAttribute("use");
		try {
			Logs.addLogUpload(userSession.userid, session.getId(), uniqueResourceID, resourceType, time);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OpenRDFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
