package common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.repository.RepositoryException;
import org.policygrid.ontologies.OPM;
import org.policygrid.ontologies.OurSpacesVRE;
import org.policygrid.ontologies.ProvenanceGeneric;
import org.policygrid.policies.PolicyReasoner;
import org.policygrid.policies.PolicySession;
import org.policygrid.policies.RequiredFieldsSession;
import org.xml.sax.SAXException;
import org.zefer.html.doc.s;

import provenanceService.Edge;
import provenanceService.ProvenanceService;
import provenanceService.ProvenanceServiceImpl;
import provenanceService.RDFProvider;

import com.hp.hpl.jena.iri.IRI;
import com.hp.hpl.jena.iri.IRIFactory;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.Restriction;
import com.hp.hpl.jena.rdf.arp.impl.URIReference;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * Servlet implementation class ResourceUploadServlet
 */
public class ResourceUploadBean extends HttpServlet {
	private static final long serialVersionUID = -241888150958411703L;
	RDFi rdf = new RDFi();
	
	static String[] multiProperties = null;
	long now;
	static OntologyHandler ontology;
	static Map<String, String[]> subProperties = null;
	static List<String> noShowProperties = null;
	static List<String> noShowClasses = null;
	static List<String> addNewProperties = null;
	static List<String> comboBoxProperties = null;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ResourceUploadBean() {
		super();
		try {
			ontology = new OntologyHandler();
			initProps();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void initProps(){
		String s;
		if(multiProperties == null){
			s = Configuration.getParaemter("upload", "multiProperties");
			multiProperties = s.split(",");
			for (int i = 0; i < multiProperties.length; i++) {
				multiProperties[i] = multiProperties[i].trim();
			}			
		}
		if(subProperties == null){
			subProperties = new HashMap<String, String[]>();
			for(String p : multiProperties){
				String[] tmp = Configuration.getParaemter("upload",p).split(",");
				for (int i = 0; i < tmp.length; i++) {
					tmp[i] = tmp[i].trim();
				}
				subProperties.put(p, tmp);
			}
		}

		if(noShowProperties == null){
			noShowProperties = new ArrayList<String>();
			s = Configuration.getParaemter("upload", "noShowProperties");
			String[] tmp = s.split(",");
			for (int i = 0; i < tmp.length; i++) {
				noShowProperties.add(tmp[i].trim());
			}
			noShowProperties.add("");
		}

		if(noShowClasses == null){
			noShowClasses = new ArrayList<String>();
			s = Configuration.getParaemter("upload", "noShowClasses");
			String[] tmp = s.split(",");
			for (int i = 0; i < tmp.length; i++) {
				noShowClasses.add(tmp[i].trim());
			}
			noShowClasses.add("");
		}
		if(addNewProperties == null){
			addNewProperties = new ArrayList<String>();
			s = Configuration.getParaemter("upload", "addNewProperties");
			String[] tmp = s.split(",");
			for (int i = 0; i < tmp.length; i++) {
				addNewProperties.add(tmp[i].trim());
			}		
		}
		if(comboBoxProperties == null){
			comboBoxProperties = new ArrayList<String>();
			s = Configuration.getParaemter("upload", "comboBoxProperties");
			String[] tmp = s.split(",");
			for (int i = 0; i < tmp.length; i++) {
				comboBoxProperties.add(tmp[i].trim());
			}		
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

	}

	protected HashMap<String, String> getRestrictions(String className, String ontologyName){
		HashMap<String, String> restrictions = new HashMap<String, String>();

		Iterator<Restriction> it = ontology.getRestrictionsOnClass(ontologyName, className);
		int count = 0;
		while (it!= null && it.hasNext()) {
			Restriction rest = it.next();
			String property = rest.getOnProperty().getLocalName();
			restrictions.put(property, null);
		}
		return restrictions;
	}
	
	protected void addProperties(String className,HashMap<String, OntProperty> properties, HashMap<String, String> propertiesLocation, String ontologyName){
		Iterator<OntProperty> itProperties = ontology.getProperties(ontologyName, className);
		while (itProperties.hasNext()) {
			OntProperty prop = (OntProperty) itProperties.next();
			if(prop == null || prop.getDomain() == null /*|| prop.getDomain().getURI() == null*/)
				continue;
			if(prop.getDomain().getURI() != null && prop.getDomain().getURI().equals("http://www.w3.org/2002/07/owl#Thing"))
				continue;
			properties.put(prop.getLocalName(), prop);
			propertiesLocation.put(prop.getLocalName(), "uploadForm.properties");
		}
	}
	protected Vector<OntClass> loadAllProperties(String className,HashMap<String, OntProperty> properties, HashMap<String, String> propertiesLocation, String ontologyName){
		OntModel om = ontology.getOntology(ontologyName);
		OntClass oc = om.getOntClass(className);
		//ontology.getClass(className.substring(className.indexOf("#") + 1), om);
		Vector<OntClass> superClasses = ontology.getAllSuperClasses(ontologyName, oc);
		superClasses.add(oc);
		for (OntClass superClass : superClasses) {			
			// We don't want to go deeper than the Artifact class.
			if ("http://openprovenance.org/ontology#Entity".equals(superClass.getURI()) 
				||"http://openprovenance.org/ontology#Node".equals(superClass.getURI()) 
				|| "http://www.w3.org/2000/01/rdf-schema#Resource".equals(superClass.getURI()) 
				//|| "http://purl.org/net/opmv/ns#Artifact".equals(superClass.getURI())
				//|| "http://purl.org/net/opmv/ns#Process".equals(superClass.getURI())
					)
				continue;
			addProperties(superClass.getURI(), properties, propertiesLocation, ontologyName );
		}
		return superClasses;
	}
		
	protected HashMap<String, String> checkRestrictions(String className, HashMap<String, OntProperty> properties, HashMap<String, String> propertiesLocation, String ontologyName){

		HashMap<String, String> restrictions = getRestrictions(className, ontologyName);
		// Check the restrictions and possibly add new properties to the list
		for (String property : restrictions.keySet()) {
			if (properties.containsKey(property))
				continue;
			properties.put(property, ontology.getProperty(property));
			propertiesLocation.put(property, "uploadForm.properties");
		}
		return restrictions;
	}
	
	protected List<Properties> loadResourceProperties(String resourceId, HashMap<String, OntProperty> properties, HashMap<String, String> propertiesLocation, String ontologyName){
		ArrayList<common.Properties> resourceProperties = new ArrayList<Properties>();
		if (resourceId == null || resourceId.equals(""))
			return new ArrayList<Properties>();
		try {
			resourceProperties = rdf.getProperties("" + resourceId);
			// Add the properties of edited artifact
			for (common.Properties prop2 : resourceProperties) {
				String property = prop2.getProperty();
				if (property.contains("#"))
					property = property.substring(property.indexOf("#") + 1);
				else
					property = property.substring(property.lastIndexOf("/") + 1);
				if (properties.containsKey(property))
					continue;
				OntProperty prop = ontology.getProperty(property,
						ontology.getOntology(ontologyName));
				if (prop == null)
					continue;
				properties.put(prop.getLocalName(), prop);
				propertiesLocation.put(prop.getLocalName(), "uploadForm.commonProperties");
			}
			//Add the provenance properties.
			ProvenanceServiceImpl impl = ProvenanceService.getSingleton();
			provenanceService.Node n = impl.getDataProvider().getNode(null, resourceId);
			impl.getDataProvider().getAdjacencies(null,n, 2);
			for(Edge e : n.getAdjacencies()){
				
				String property = e.getType();
				property = Utility.getLocalName(property);
				//lowercase first letter
				property = property.substring(0,1).toLowerCase() + property.substring(1);
				
				common.Properties prop2 = new common.Properties();
				prop2.property = "http://purl.org/net/opmv/ns#" +property;
				//Leave only the provenance starting from the resource
				if(e.getTo().getId().equals(resourceId))
					continue;//prop2.value = e.getFrom().getId();
				else
					prop2.value = e.getTo().getId();
				//OntProperty prop = ontology.getProperty(property,ontology.getOntology(ontologyName));
				//properties.put(prop.getLocalName(), prop);
				//propertiesLocation.put(prop.getLocalName(), "uploadForm.properties");
				resourceProperties.add(prop2);
			}
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OpenRDFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resourceProperties;
	}
	
	/**
	 * 
		property = new Property();	
		//property.idProperty = '<%=URI %>';
		/property.property = '<%=localName %>';
		property.value = [];
		property.restriction = null;
		property.el = null;
		//property.type = '<%=resType %>';
		//property.range = '<%=range %>';
	 * @param prop2
	 * @return
	 */
	protected JSONObject getOnePropertyJSON(String propId, String resType, String range){
		JSONObject obj = new JSONObject();
		obj.put("idProperty", propId);
		obj.put("property", Utility.getLocalName(propId));
		obj.put("type", resType);
		obj.put("range", range);
		JSONArray values = new JSONArray();
		obj.put("value", values);
		return obj;
	}
	protected void addValue(JSONObject obj, Object value){
		JSONArray values = obj.getJSONArray("value");
		values.add(value);
	}
	
	/**
	 * Returns the type of the range of a property - date or literal.
	 * @param range
	 * @return
	 */
	public static String getRangeType(String range, String ontologyName){
		//No range -> literal
				if (range.equals("http://www.w3.org/2001/XMLSchema#string") //|| range.equals("") 
				 || range.equals("http://www.w3.org/2001/XMLSchema#int") 
				 || range.equals("http://www.w3.org/2001/XMLSchema#decimal"))
					return "literal";
				else if(range.equals("http://www.w3.org/2006/time#Instant") || range.equals("http://www.w3.org/2001/XMLSchema#dateTime")) 
					return "date";		
				 
				else{
					OntModel model = ontology.getOntology(ontologyName);
					OntClass r = (model).getOntClass(range);
					if(r != null){
						Vector<OntClass> superClasses = ontology.getAllSuperClasses(ontologyName, r);
						superClasses.add(r);
						if(superClasses.contains((model).getOntClass("http://www.w3.org/2006/time#TemporalEntity"))||
								superClasses.contains((model).getOntClass("http://www.w3.org/2001/XMLSchema#dateTime"))||
								superClasses.contains((model).getOntClass("http://www.w3.org/2001/XMLSchema#date"))
						)
							return "date";
					}
				/*if (range.equals("http://www.w3.org/2006/time#Instant") ||
						range.equals("http://www.w3.org/2001/XMLSchema#dateTime"))*/
				}
				/*else if (prop.getURI().startsWith("http://www.policygrid.org/academic-disciplines#")) 
						resType = "academic";*/
				return null;
	}

	/**
	 * Returns the type of the property - literal, resource,...
	 * @param prop
	 * @return
	 */
	public static String getResType(OntProperty prop, String ontologyName){
		// Geo property		
		if (prop.getURI().startsWith("http://www.policygrid.org/geo-properties.owl#")) 
			return "geoMultiple";	
		// Processes
		else if(prop.getURI().equals("http://www.policygrid.org/provenance-generic.owl#wasGeneratedByInfer")) 
			return "resourceNew";

		String range = ontology.getPropertyRange(prop);
		String type = getRangeType(range, ontologyName);
		if(type != null)
			return type;
		
			for(String s : multiProperties){
				if(s.equals(prop.getURI()))
					return "multiProperties";
			}			
			if(addNewProperties.contains(prop.getURI()) || addNewProperties.contains(prop.getNameSpace())){
				return "resourceNew";
			}
			else if(comboBoxProperties.contains(prop.getURI()) || comboBoxProperties.contains(prop.getNameSpace())){
				return "resourceCombo";
			}
			return "resource";
	}

	protected void addSubProperties(JSONObject p, String propName, String ontologyName){
		JSONArray subProperties = new JSONArray();
		for(String subP : this.subProperties.get(propName)){
			JSONObject j =  getOnePropertyJSON(subP, getResType(ontology.getProperty(Utility.getLocalName(subP)), ontologyName), ontology.getPropertyRange(ontology.getProperty(Utility.getLocalName(subP))));			
			subProperties.add(j);
		}
		p.put("subProperties", subProperties);
	}
	
	public JSONObject addValue(JSONObject j, Properties prop2) {
		Object value = null;		
		// Geo property - we are editing, so get the value
		if (prop2.getProperty().startsWith("http://www.policygrid.org/geo-properties.owl#")) {
			if(prop2 != null && prop2.getValue() != null){
				String featureId = prop2.getValue();
				// Name lat lon
				List<Properties> geo;
				try {
					geo = rdf.getGeoInformation(featureId);
					// Changed to name lon lat
					value = "['" + geo.get(0).getValue().replaceAll("'","\\\\'").replaceAll("\"","\\\"") + "',"
							+ geo.get(2).getValue() + "," + geo.get(1).getValue() + "]";
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OpenRDFException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else if (j.getString("type").equals("multiProperties")) {
			try {
				ArrayList<Properties> subProperties = rdf.getProperties("" + prop2.getValue());
				for (int i = 0; i < j.getJSONArray("subProperties").size(); i++) {
					JSONObject subp = j.getJSONArray("subProperties").getJSONObject(i);
					for(Properties p : subProperties){
						if(p.getProperty().equals(subp.get("idProperty")))
							addValue(subp, p);					
					}
				}
				value = null;
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		/*// Academic discipline, special treatment
		else if (prop2.getProperty().equals("http://www.policygrid.org/ourspacesVRE.owl#hasDisciplineInfo")) {
			if(prop2 != null && prop2.getValue() != null){
				String discInfoId = prop2.getValue();
				// Name lat lon
				List<common.Properties> discInfo;
				try {
					discInfo = rdf.getScientificDiscipline(discInfoId);
					List<common.Properties> lAD = new ArrayList<common.Properties>();
					List<common.Properties> lADT = new ArrayList<common.Properties>();
					for (common.Properties p : discInfo) {
						if (p.getProperty().endsWith("hasDisciplineTag"))
							lADT.add(p);
						else if (p.getProperty().endsWith("hasAcademicDiscipline"))
							lAD.add(p);
					}
					StringBuilder s = new StringBuilder();
					s.append("[ [");
					for (common.Properties p : lAD) {
						s.append("'"+p.getValue()+"',");
					}
					//Trim last ,
					s.setLength(s.length()-1);
					//Close lAD and open lADT
					s.append("],[");
					for (common.Properties p : lADT) {
						s.append("'"+p.getValue()+"',");
					}
					//Trim last ,
					s.setLength(s.length()-1);
					//Close lADT and close value
					s.append("]]");
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OpenRDFException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}*/
		//Normal property
		else if(prop2 != null && prop2.getValue() != null){
			value = prop2.getValue();
		}
		addValue(j, value);
		return j;
	}
	public JSONArray getPropertiesJSON(String className, String resourceId, String ontologyName) {
		JSONArray array = new JSONArray();
		/**
		 * Contains properties and description of the restriction on the
		 * property.
		 */
		HashMap<String, OntProperty> properties = new HashMap<String, OntProperty>();
		// Value in the map is either properties or commonProperties
		HashMap<String, String> propertiesLocation = new HashMap<String, String>();
		// Load the properties of all superclasses.
		loadAllProperties(className, properties, propertiesLocation, ontologyName);
		// Load the properties from restrictions.
		HashMap<String, String> restrictions = checkRestrictions(className, properties, propertiesLocation, ontologyName);
		// Load values of the (possibly) edited artifact
		List<Properties> resourceProperties = loadResourceProperties(resourceId, properties, propertiesLocation, ontologyName);
		int countProp = 0, countCommonProp = 0, count2;

		for(String s : properties.keySet()){
			OntProperty prop = properties.get(s);
			String property = prop.getLocalName();			
			// We don't want system properties manipulated by the user
			if (noShowProperties.contains(property)){
				boolean skip = true;
				//Add geo properties and discourse, even though they are in noShow
				if(prop.getURI().startsWith("http://www.policygrid.org/geo-properties.owl#") ||
						prop.getURI().startsWith("http://swan.mindinformatics.org/ontologies/1.2/discourse-relationships/")){
					for (common.Properties propTmp : resourceProperties) {
						if (propTmp.getProperty().equals(prop.getURI())) {
							skip = false;
						}
					}
				}
				if(skip)
					continue;
			}
			
			String restriction = restrictions.get(s);
			String location = propertiesLocation.get(s);
			if("uploadForm.properties".equals(location)){
				count2 = countProp;
				countProp++;
			}
			else {
				count2 = countCommonProp;
				countCommonProp++;
			}
			if (restriction == null)
				restriction = "";
			JSONObject j =  getOnePropertyJSON(prop.getURI(), getResType(prop, ontologyName), ontology.getPropertyRange(prop));
			j.put("location", location);
			j.put("count", count2);
			if(j.getString("type").equals("multiProperties")){
				addSubProperties(j, prop.getURI(), ontologyName);
			}
			//Adding the value to the array
			for (common.Properties propTmp : resourceProperties) {
				if (propTmp.getProperty().equals(prop.getURI())) {
					addValue(j, propTmp);
					continue;
				}
			}
			array.add(j);
		}
		return array;
	}

	public static String[] getMultiProperties() {
		initProps();
		return multiProperties;
	}

	public static void setMultiProperties(String[] multiProperties) {
		initProps();
		ResourceUploadBean.multiProperties = multiProperties;
	}

	public static Map<String, String[]> getSubProperties() {
		initProps();
		return subProperties;
	}

	public static void setSubProperties(Map<String, String[]> subProperties) {
		initProps();
		ResourceUploadBean.subProperties = subProperties;
	}
	/**
	 * Returns the basic type of the given class
	 * @param type
	 * @return
	 */
	public static String getBasicType(String type) {
		ProvenanceServiceImpl impl = ProvenanceService.getSingleton();
		String basicType = impl.getBasicTypes().get(type);
		if("Process".equals(basicType))
			return "http://openprovenance.org/ontology#Process";
		if("Agent".equals(basicType))
			return "http://openprovenance.org/ontology#Agent";
		if("Artifact".equals(basicType))
			return "http://openprovenance.org/ontology#Artifact";
		return null;
	}
	public static boolean isNoShowClass(String className){
		return noShowClasses.contains(className);
	}
		
	public static boolean isMultiProperty(String prop) {
		initProps();
		for(String s : multiProperties){
			if(s.equals(prop))
				return true;
		}
		return false;
	}

}
