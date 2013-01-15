package common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.topbraid.spin.system.SPINModuleRegistry;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.hp.hpl.jena.ontology.ConversionException;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.ontology.Restriction;
import com.hp.hpl.jena.ontology.UnionClass;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.shared.Lock;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;


public class OntologyHandler {

    @Deprecated public OntModel resource;
    @Deprecated public OntModel utility;
    @Deprecated public OntModel task;
    private static boolean initialized = false;
	Connections con = new Connections();
	public static Hashtable<String, OntModel> hashtable = new Hashtable<String, OntModel>();
	public static OntModel allModels;
	private static HashMap<String, List<OntProperty>> properties = new HashMap<String, List<OntProperty>>();

	private static HashMap<String, Vector<String>> subclasses = new HashMap<String, Vector<String>>();
	
	public OntologyHandler() throws IOException, SAXException {
		if(!initialized)
			init();
	}
	
	private synchronized void init() throws IOException, SAXException{
		//OntologyReader();
		//Proxy configuraion
		SPINModuleRegistry.get().init();
		
		System.setProperty("http.proxyHost", Configuration.getParaemter("proxy","host"));
		System.setProperty("http.proxyPort", Configuration.getParaemter("proxy","port"));
		
		//Init provenance service
		//ProvenanceService.initProvenance();
		
		Vector<OntologyBean> ontologies = Configuration.getOntologies();
		if(!hashtable.containsKey("all")){
			allModels = ModelFactory.createOntologyModel();
			hashtable.put("all", allModels);
		}
		for (int i = 0; i < ontologies.size(); i++) {
			   OntologyBean onto = ontologies.get(i); 
			   if (!hashtable.containsKey(onto.getName())) {
				    String path = onto.getPath();   
					FileInputStream in = new FileInputStream(path);
					//OntModel resource = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
					OntModel resource = ModelFactory.createOntologyModel();
					resource = (OntModel) resource.read(in,onto.getNamespace());
					in.close();
					//allModels.addSubModel(resource);
					allModels.add(resource);
					hashtable.put(onto.getName(), resource);
					common.Utility.log.debug("Loading Ontology : "+onto.getName() + ": " + onto.getNamespace()+ ", " + onto.getPath());
			   }
		}
		//Runs the hard queries to initialise OntologyHandler
		//getRestrictionsOnClass("all", "http://openprovenance.org/ontology#Artifact");
	//	getProperties("all", "http://openprovenance.org/ontology#Artifact");
//		getAllSuperClasses("all",getClass("Artifact",getOntology("all")));
		initialized = true;

	}

	/*
	 * METHOD REPLACED BY CONFIGURATION FILE
	 * public void OntologyReader() throws IOException, SAXException {
		
		//Proxy configuraion
		System.setProperty("http.proxyHost", Configuration.getParaemter("proxy","host"));
		System.setProperty("http.proxyPort", Configuration.getParaemter("proxy","port"));
	

		
		String path = con.getResourceOntology();
		FileInputStream in = new FileInputStream(path);
		resource = ModelFactory.createOntologyModel();
		resource = (OntModel) resource.read(in,"http://policygrid.org/resource.owl");
		in.close();

	    String path1 = "/home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/ontologies/provenance-generic.owl";
		FileInputStream in1 = new FileInputStream(path1);
		OntModel resource1 = ModelFactory.createOntologyModel();
		resource1 = (OntModel) resource1.read(in1,"http://www.policygrid.org/provenance-generic.owl");
		in1.close();
		hashtable.put("general", resource1);
		
	    String path2 = "/home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/ontologies/project.owl";
		FileInputStream in2 = new FileInputStream(path2);
		OntModel resource2 = ModelFactory.createOntologyModel();
		resource2 = (OntModel) resource2.read(in2,"http://policygrod.org/project.owl");
		in2.close();
		hashtable.put("project", resource2);
		
		
		
		String path4 = "/home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/ontologies/opm.owl";
		FileInputStream in3 = new FileInputStream(path4);
		OntModel location = ModelFactory.createOntologyModel();
		location = (OntModel) location.read(in3,"http://policygrid.org/opm.owl");
		in3.close();
		
		String path3 = "/home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/ontologies/geo-properties.owl";
		FileInputStream in4 = new FileInputStream(path3);
		OntModel location = ModelFactory.createOntologyModel();
		location = (OntModel) location.read(in4,"http://policygrid.org/geo-properties.owl");
		in4.close();
		
		//location.add(location2);
		
		hashtable.put("location", location);

		String path4 = "/home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/ontologies/discourserelationships.owl";
		FileInputStream in5 = new FileInputStream(path4);
		OntModel discourse = ModelFactory.createOntologyModel();
		discourse = (OntModel) discourse.read(in5,"http://swan.mindinformatics.org/ontologies/1.2/discourse-relationships/");
		in5.close();		
		hashtable.put("discourse", discourse);
		
		path = "/home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/ontologies/actionOntology.owl";
		in = new FileInputStream(path);
		OntModel model = ModelFactory.createOntologyModel();
		model = (OntModel) model.read(in,"http://www.policygrid.org/ourspacesVRE.owl");
		in5.close();		
		hashtable.put("action", model);
		

	}*/
	/**
	 * Returns the OntProperty with given name, using all the ontologies.
	 * @param name
	 * @return
	 */
	public OntProperty getProperty(String name){		
		for(OntModel ont :hashtable.values()){
			OntProperty o = getProperty(name, ont);
			if(o != null)
				return o;
		}
		return null;
	}
	public OntProperty getProperty(String name, OntModel ontology) {
		for (Iterator it = ontology.listOntProperties(); it.hasNext();) {
			OntProperty p = (OntProperty) it.next();
			if (p.getLocalName().equalsIgnoreCase(name))
				return p;
		}
		return null;
	}

	public OntClass getClass(String name, OntModel ontology) {
		for (Iterator it = ontology.listNamedClasses(); it.hasNext();) {
			OntClass c = (OntClass) it.next();
			if (c.getLocalName().equalsIgnoreCase(name))
				return c;
		}
		return null;
	}


	public OntModel getOntology(String label) {
		OntModel temp = null;
		if (hashtable.containsKey(label))
			temp = (OntModel) hashtable.get(label);
		return temp;
	}

	public Vector getSubclassList(String label, String resourceType) {
		// if(getOntology(label) == null)
		// return null;
		OntModel m = getOntology(label);
		OntClass c = m.getOntClass(resourceType);
		Vector classList = new Vector();
		for (Iterator it = c.listSubClasses(true); it.hasNext();) {
			OntClass sc = (OntClass) it.next();
			classList.add(sc.getLocalName().toString());
		}
		return classList;
	}
	
	public Vector getSubclassListNamespace(String label, String resourceType) {
		// if(getOntology(label) == null)
		// return null;
		OntModel m = getOntology(label);
		Vector classList = new Vector();
		OntClass c = m.getOntClass(resourceType);
		if(c == null)
			return classList;
		for (Iterator it = c.listSubClasses(true); it.hasNext();) {
			OntClass sc = (OntClass) it.next();
			classList.add(sc.getNameSpace()+sc.getLocalName());

		}
		return classList;
	}
	

	/**
	 * Gets all subclasses, even subclasses of subclasses.
	 * @param label
	 * @param resourceType
	 * @return List of URI of all subclasses
	 */
	public Vector<String> getSubclassListFull(String label, String resourceType) {
		// if(getOntology(label) == null)
		// return null;
		if(subclasses.containsKey(label+resourceType))
			return (Vector<String>) subclasses.get(label+resourceType).clone();
		OntModel m = getOntology(label);
		Vector<String> classList = new Vector<String>();
		OntClass c = m.getOntClass(resourceType);
		if(c == null)
			return classList;
		try {
			//Do it write because of iterators possible conflict.
			m.enterCriticalSection(Lock.WRITE);
			for (Iterator it = c.listSubClasses(false); it.hasNext();) {
				OntClass sc = (OntClass) it.next();
				classList.add(sc.getURI());
			}
		} finally {
		    m.leaveCriticalSection() ;
		}
		subclasses.put(label+resourceType,classList);
		return classList;
	}
	public Vector<String> getSuperclassesListFull(String label, String resourceType) {
		// if(getOntology(label) == null)
		// return null;
		OntModel m = getOntology(label);
		Vector<String> classList = new Vector<String>();
		//Do it write because of iterators possible conflict.
		m.enterCriticalSection(Lock.WRITE);
		try {
			OntClass c = m.getOntClass(resourceType);
			for (Iterator it = c.listSuperClasses(false); it.hasNext();) {
				OntClass sc = (OntClass) it.next();
				classList.add(sc.getURI());
			}
		} finally {
		    m.leaveCriticalSection() ;
		}
		return classList;
	}


	public Iterator<OntProperty> getProperties(String label, OntClass c)
	{
		if(properties.containsKey(c.getURI()))
			return properties.get(c.getURI()).iterator();
		List<OntClass> classes = new ArrayList<OntClass>();
		classes.add(c);

		List<OntProperty> result = new ArrayList<OntProperty>();
		getOntology(label).enterCriticalSection(Lock.WRITE);
		Iterator<OntProperty> it = getOntology(label).listOntProperties();
		while (it.hasNext())
		{        //Get the domains of all the properties
			OntProperty p = (OntProperty) it.next();
			Iterator i = getDomain(p);
			while (i.hasNext())
			{        //and check whether the classes are in the domains
				if (classes.contains(i.next()))
				{        //if so, add the property to the result
					result.add(p);
					break;
				}
			}
		}
		getOntology(label).leaveCriticalSection();
		properties.put(c.getURI(), result);
		return result.iterator();
	}

	
	public String getPropertyRange(OntProperty pro)
	{
		String range = "";
		com.hp.hpl.jena.util.iterator.ExtendedIterator iter = pro.listRange();
		
		while (iter.hasNext()) {
			OntResource res1 = (OntResource)iter.next();
			range  += res1.toString();
		}
		return range;
			
	}
	
	/**
	 * Returns iterator over all restrictions that are superclasses of given class.
	 * @param label Label of ontology to be searched.
	 * @param oclass Name of the class.
	 * @return Iterator over all restrictions that are superclasses of given class.
	 */
	public Iterator<Restriction> getRestrictionsOnClass(String label, String oclass){
		OntClass c = getOntology(label).getOntClass(oclass);
		if(c == null)
			return null;
		ExtendedIterator<OntClass> l = c.listSuperClasses();
		List<Restriction> restr = new ArrayList<Restriction>();
		common.Utility.log.debug(oclass);
		while(l.hasNext()){
			OntClass sc = l.next();
			//We take only restrictions
			if(sc.isRestriction()){
				Restriction r = sc.asRestriction();
				restr.add(r);
			}
		}		
		return restr.iterator();
	}
	
	public Iterator<OntProperty> getProperties(String label, String oclass)
	{
		OntClass c = getOntology(label).getOntClass(oclass);
		if(c == null)
			return null;
		return getProperties(label, c);
		/*
		List<OntClass> classes = new ArrayList<OntClass>();
		classes.add(c);
		List<OntProperty> result = new ArrayList<OntProperty>();
		Iterator it = getOntology(label).listOntProperties();
		while (it.hasNext())
		{        //Get the domains of all the properties
			OntProperty p = (OntProperty) it.next();
			Iterator i = getDomain(p);
			while (i.hasNext())
			{        //and check whether the classes are in the domains
				if (classes.contains(i.next()))					
				{        //if so, add the property to the result
					result.add(p);
					break;
				}
			}
		}
		return result.iterator();*/
	}

	/**
	 * This returns the domain of the given property. If that domain is unspecified,
	 * it checks the domain of the super properties.
	 * @param p: Property
	 * @return Iterator over classes in the domain of this property or, if necessary, its superproperties 
	 */
	public Iterator getDomain(OntProperty p)
	{
		return getDomainList(p, false).iterator();
	}

	public List<OntClass> getDomainList(OntProperty p, boolean subclasses)
	{
		ArrayList<OntClass> result = new ArrayList<OntClass>();
		Iterator it = p.listDomain();

		while (it.hasNext())
		{        //add all the classes in the domain
			OntResource r = (OntResource) it.next();                               
			addClass(result, r);
		}
		if (result.isEmpty())
		{        //if the domain was empty, check the domain of the superproperties
			Iterator i = p.listSuperProperties(true);
			while (i.hasNext())
			{
				Iterator it2 = getDomain((OntProperty) i.next());
				while (it2.hasNext())
					result.add((OntClass) it2.next());
			}
		}

		return result;
	}

	/**
	 * This takes an OntClass. If it is a unionclass, all classes in
	 * the union are added to the arraylist, otherwise just the class itself
	 * @param result: ArrayList with classes
	 * @param r: resource to be added to the list, either as is or all operands separately
	 */
	private void addClass(List result, OntResource r)
	{
		if (r.isClass())
		{        //check if it's an OntClass
			OntClass c = r.asClass();                                           
			if (c.isUnionClass())
			{        //check if it's a Union class; if so, add the separate classes to result
				UnionClass u = c.asUnionClass();
				ExtendedIterator e = u.listOperands();
				while (e.hasNext())
				{
					OntClass cl = (OntClass) e.next();                                                  
					result.add(cl);
				}
			}
			else
				result.add(c);
		}
	}

	public boolean vectorContaints(Vector<OntClass> v, OntClass sc){

		for (int i = 0; i < v.size(); i++) { 
			if (v.get(i).getURI().equals(sc.getURI())) {
				return true;
			}
		}
		return false;
	}

	public Vector<OntClass> getAllSuperClasses(String label, OntClass c) {
		Vector<OntClass> classList = new Vector();
		for (Iterator it = c.listSuperClasses(); it.hasNext();) {
			OntClass sc = (OntClass) it.next();
			String clName = sc.getLocalName();
			if ((!vectorContaints(classList,sc)) && (sc.getLocalName() != null) && (!clName.equals("Thing"))) 
				classList.add(sc);
			//if (sc.hasSubClass())
			List<OntClass> l = getAllSuperClasses(label,sc);
			for(OntClass scTmp : l){
				if ((!vectorContaints(classList,sc)) && (sc.getLocalName() != null) && (!clName.equals("Thing")))
					classList.add(scTmp);				
			}
		}
		return classList;
	}


	public void writeTextFile(String filename, String content) {

		Writer output = null;
		File file = new File(filename);
		try {
			output = new BufferedWriter(new FileWriter(file));
			output.write(content);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Deprecated
	public String createAllXfrmFromResource(String label, String resourceType) {
		String ret ="";

		Vector classes = getSubclassListFull(label, resourceType);

		for (int i = 0; i < classes.size(); i++) {
			String cl = (String)classes.get(i);
			
			String xfrm = getXfrmForResource(label, cl);
			String filename = cl.substring(cl.indexOf("#")+1);
			
			writeTextFile("/home/policygrid/xfrm/"+filename+".xfrm",xfrm);
			ret = ret + xfrm;
		}

		return ret;
	}

	@Deprecated
	public String getXfrmForResource(String label, String resourceType) {
		String xfrm = "<%@ taglib prefix=\"f\" uri=\"ourSpacesTags\" %>" + "\n";

		xfrm = xfrm + "<f:ourSpacesForm label=\"My Form\" type=\"resource\">" + "\n";

		OntModel m = getOntology(label);
		OntClass c = m.getOntClass(resourceType);

		if (c != null) {
			Vector<OntClass> myClasses = getAllSuperClasses(label,  c);
			myClasses.add(c);

			for (int i = 0; i < myClasses.size(); i++) {
				//common.Utility.log.debug("processing " + myClasses.get(i).getURI());
				for (Iterator it = getProperties(label, myClasses.get(i)); it.hasNext(); ) {
					OntProperty pr = (OntProperty) it.next();

					String range = ""+pr.getRange();


					if (range.endsWith("#string")) {

						if (pr.getLocalName().equals("Title")) {
							xfrm = xfrm + "    <f:literalItem label=\""+ pr.getLocalName() +"\"" + "\n";
							xfrm = xfrm + "                   optional=\"false\"" + "\n";
							xfrm = xfrm + "                   ukda=\"false\"" + "\n";
							xfrm = xfrm + "                   namespace=\""+pr.getNameSpace().replaceFirst("\\#", "") +"\"" + "\n";
							xfrm = xfrm + "                   property=\""+pr.getLocalName() +"\"/>" + "\n";
							xfrm = xfrm + "" + "\n";	
						} else {
						xfrm = xfrm + "    <f:literalItem label=\""+ pr.getLocalName() +"\"" + "\n";
						xfrm = xfrm + "                   optional=\"true\"" + "\n";
						xfrm = xfrm + "                   ukda=\"false\"" + "\n";
						xfrm = xfrm + "                   namespace=\""+pr.getNameSpace().replaceFirst("\\#", "") +"\"" + "\n";
						xfrm = xfrm + "                   property=\""+pr.getLocalName() +"\"/>" + "\n";
						xfrm = xfrm + "" + "\n";
						}
					} 
					else {

						OntResource mypr = pr.getRange(); 
						if ((mypr != null) && (mypr.getNameSpace() != null) && (mypr.getLocalName() != null)) {
							xfrm = xfrm + "    <f:resourceItem label=\""+ pr.getLocalName() +"\"" + "\n";
							xfrm = xfrm + "                   optional=\"true\"" + "\n";
							xfrm = xfrm + "                   ukda=\"false\"" + "\n";
							xfrm = xfrm + "                   resourceNamespace=\""+ mypr.getNameSpace().replaceFirst("\\#", "") +"\"" + "\n";
							xfrm = xfrm + "                   resourceName=\""+ mypr.getLocalName() +"\"" + "\n";
							xfrm = xfrm + "                   namespace=\""+pr.getNameSpace().replaceFirst("\\#", "") +"\"" + "\n";
							xfrm = xfrm + "                   property=\""+pr.getLocalName() +"\"/>" + "\n";
							xfrm = xfrm + "" + "\n";
						}
					}


					//xfrm = xfrm + pr.toString() + " " + pr.getRange() + "<br>";
				}

			}
		}

		xfrm = xfrm + "</f:ourSpacesForm>" + "\n";
		return xfrm;
	}

	/**
	 * Lists all properties in an ontology regardless of classes. If an ontology has classes, duplicate properties
	 * will occur.
	 * 
	 * @param label
	 * @return
	 */
	public Iterator<OntProperty> getPropertiesWithoutClasses(String label, String domain)
	{
		ArrayList<OntProperty> result = new ArrayList<OntProperty>();
		Iterator<OntProperty> it = getOntology(label).listOntProperties();
		while(it.hasNext())
		{
			OntProperty p = (OntProperty) it.next();
			if(domain == null || (p.getDomain() != null && p.getDomain().toString().equals(domain))) {
				result.add(p);
				common.Utility.log.debug(p.toString());
			}
		}
		
		return result.iterator();
	}
	
	public Vector<FormItem>  getLocationFields()
	{
		Vector<FormItem> fields = new Vector<FormItem>();
		OntModel m = getOntology("location");
		//OntClass c = m.getOntClass("http://www.policygrid.org/opm.owl#Artefact");
		for(Iterator<OntProperty> it = getPropertiesWithoutClasses("location", "http://openprovenance.org/ontology#Artifact"); it.hasNext();)
		{
			OntProperty pr = (OntProperty) it.next();
			OntResource mypr = pr.getRange();
			FormItem fm = new FormItem();
			fm.setType("location");
			fm.setNamespace(pr.getNameSpace().replaceFirst("\\#", ""));
			fm.setProperty(pr.getLocalName());
			fm.setLabel(pr.getLocalName());
			fm.setOptionalTrue();
			fm.setResourceNamespace(mypr.getNameSpace().replaceFirst("\\#", ""));
			fm.setResourceName(mypr.getLocalName());
			fields.add(fm);
			//common.Utility.log.debug(pr.getLocalName());
		}
		
		//m.write(System.out, "RDF/XML-ABBREV");
		return fields;
	}


	public Vector getAllSClasses(String label, String resourceType) {
		return getAllSClasses(label, resourceType, 0);
	}

	public Vector getAllSClasses(String label, String resourceType, int level) {
		OntModel m = getOntology(label);
		OntClass c = m.getOntClass(resourceType);
		Vector classList = new Vector();
		for (Iterator it = c.listSubClasses(true); it.hasNext();) {
			OntClass sc = (OntClass) it.next();
			String clName = sc.getNameSpace() + sc.getLocalName().toString(); 
			classList.add(clName);
			if (sc.hasSubClass())
				classList.addAll(getAllSClasses(label, sc.getURI().toString(),
						level + 1));

		}
		return classList;
	}
	
	
	
	
	public Vector getAllClasses(String label, String resourceType) {
		return getAllClasses(label, resourceType, 0);
	}

	public Vector getAllClasses(String label, String resourceType, int level) {
		OntModel m = getOntology(label);
		OntClass c = m.getOntClass(resourceType);
		Vector classList = new Vector();
		for (Iterator it = c.listSubClasses(true); it.hasNext();) {
			OntClass sc = (OntClass) it.next();
			String clName = sc.getLocalName().toString();
			String stTabs = "";
			for (int i = 0; i < level; i++) {
				stTabs = "&nbsp;&nbsp;" + stTabs;
			}
			classList.add(stTabs + clName);
			if (sc.hasSubClass())
				classList.addAll(getAllClasses(label, sc.getURI().toString(),
						level + 1));

		}
		return classList;
	}

	@Deprecated
	public Vector getOptionalFields(String label) throws ParserConfigurationException, SAXException, IOException
	{

		Vector fields = new Vector();

		// Open the correct file
		String filename = "/home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/forms/general/" + label + ".xfrm"; 
		StringBuilder contents = new StringBuilder();
		BufferedReader input =  new BufferedReader(new FileReader(filename));
		String line = null; //not declared within while loop


		while (( line = input.readLine()) != null){
			contents.append(line);
			contents.append(System.getProperty("line.separator"));
		}


		String fullParse = contents.toString();

		String temp = fullParse.substring(fullParse.indexOf("%>") +2, fullParse.length() );


		//common.Utility.log.debug(temp);

		// Initialise the DOM parser
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document dom;
		DocumentBuilder db = dbf.newDocumentBuilder();

		InputSource inStream = new InputSource();
		inStream.setCharacterStream(new StringReader(temp));


		// Parse the file
		dom = db.parse(inStream);

		// RETRIEVE LITERALS
		NodeList literals = dom.getElementsByTagName("f:literalItem");
		for(int i = 0; i<literals.getLength(); i++)
		{
			Node node = (Node) literals.item(i);
			//common.Utility.log.debug(node.getNodeName());
			NamedNodeMap attList = node.getAttributes();
			FormItem fm = new FormItem();
			for(int j = 0; j < attList.getLength(); j++)
			{
				Attr attr = (Attr) attList.item(j);
				fm.setType("literal");
				if(attr.getNodeName().equals("label") && attr.getValue() != null)
					fm.setLabel(attr.getValue());
				if(attr.getNodeName().equals("namespace") && attr.getValue() != null)
					fm.setNamespace(attr.getValue());
				if(attr.getNodeName().equals("optional") && attr.getValue() != null)
				{
					if(attr.getValue().equals("false") && attr.getValue() != null)
						fm.setOptionalFalse();
					else
						fm.setOptionalTrue();
				}
				if(attr.getNodeName().equals("property") && attr.getValue() != null)
					fm.setProperty(attr.getValue());
				if(attr.getNodeName().equals("ukda") && attr.getValue() != null)
				{
					if(attr.getValue().equals("false") && attr.getValue() != null)
						fm.setUKDAFalse();
					else
						fm.setUKDATrue();
				}
			}
			fields.add(fm);
			//fm.printItem();
		}

		// RETRIEVE RESOURCES
		NodeList resources = dom.getElementsByTagName("f:resourceItem");
		for(int i = 0; i<resources.getLength(); i++)
		{
			Node node = (Node) resources.item(i);
			//common.Utility.log.debug(node.getNodeName());
			NamedNodeMap attList = node.getAttributes();
			FormItem fm = new FormItem();
			for(int j = 0; j < attList.getLength(); j++)
			{
				Attr attr = (Attr) attList.item(j);
				fm.setType("resource");
				if(attr.getNodeName().equals("label") && attr.getValue() != null)
					fm.setLabel(attr.getValue());
				if(attr.getNodeName().equals("namespace") && attr.getValue() != null)
					fm.setNamespace(attr.getValue());
				if(attr.getNodeName().equals("optional") && attr.getValue() != null)
				{
					if(attr.getValue().equals("false") && attr.getValue() != null)
						fm.setOptionalFalse();
					else
						fm.setOptionalTrue();
				}
				if(attr.getNodeName().equals("property") && attr.getValue() != null)
					fm.setProperty(attr.getValue());
				if(attr.getNodeName().equals("ukda") && attr.getValue() != null)
				{
					if(attr.getValue().equals("false") && attr.getValue() != null)
						fm.setUKDAFalse();
					else
						fm.setUKDATrue();
				}
				if(attr.getNodeName().equals("resourceNamespace") && attr.getValue() != null)
					fm.setResourceNamespace(attr.getValue());
				if(attr.getNodeName().equals("resourceName") && attr.getValue() != null)
					fm.setResourceName(attr.getValue());
			}
			fields.add(fm);
			//fm.printItem();
		}

		return fields;
	}

}
