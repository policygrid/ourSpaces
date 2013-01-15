<%@ page language="java" import="net.sf.json.*,com.hp.hpl.jena.ontology.*,java.util.Iterator,java.util.*,java.net.*,java.text.SimpleDateFormat,java.util.ArrayList,java.io.*,java.net.*,java.util.Vector,common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="ontology" class="common.OntologyHandler" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="resourceBean" class="common.ResourceUploadBean" />

<% 
ParameterHelper parHelp = new ParameterHelper(request, session);
	String format = (String)parHelp.getParameter("format",  "HTML");
	String className = (String)parHelp.getParameter("className",  "");
	String liClass = (String)parHelp.getParameter("liClass",  "ui-draggable uploadLiItem");
	String liStyle = (String)parHelp.getParameter("liStyle",  "");
	String ulClass = (String)parHelp.getParameter("ulClass",  "draggable");
	String ulStyle = (String)parHelp.getParameter("ulStyle",  "");
	String ulId = (String)parHelp.getParameter("ulId",  "");
	String onclick = (String)parHelp.getParameter("onclick",  "");
	String resourceId = (String)parHelp.getParameter("id",  "");
	String ontologyName = (String)parHelp.getParameter("ontologyName",  "all");
	
	ArrayList<common.Properties> resourceProperties = new ArrayList();
	if(!resourceId.equals("")){
		resourceProperties = rdf.getProperties("" + resourceId);
	}
	
	/**Contains properties and description of the restriction on the property.*/
	HashMap<String,String> restrictions = new HashMap<String,String>();	
	HashMap<String,OntProperty> properties = new HashMap<String,OntProperty>();
	//Either properties or commonProperties
	HashMap<String,String> propertiesLocation = new HashMap<String,String>();	

	//No restrictions in HTML
	if(format.equals("HTML")){			
	}
	else{
		%>
					<jsp:include page="getRestrictionsJS.jsp">
						<jsp:param value="<%=className %>" name="className"/>
						<jsp:param value="<%=format %>" name="format"/>	
					</jsp:include>
		<%
	}
	Iterator<Restriction> it = ontology.getRestrictionsOnClass(ontologyName, className);
	int count = 0;	
	while (it != null && it.hasNext()) {
		Restriction rest = it.next();
		String property = rest.getOnProperty().getLocalName();
		restrictions.put(property, null);
	}		
		//Now display the list of properties.
		Iterator<OntProperty> itProperties = ontology.getProperties(ontologyName, className);
		while (itProperties != null && itProperties.hasNext()) {
			OntProperty prop = (OntProperty) itProperties.next();		
			properties.put(prop.getLocalName(), prop);
			propertiesLocation.put(prop.getLocalName(), "properties");
		}
		//Now load the properties of all superclasses.
		OntModel om = ontology.getOntology(ontologyName);
		OntClass oc = ontology.getClass(Utility.getLocalName(className),om);
		Vector<OntClass> superClasses = ontology.getAllSuperClasses(ontologyName, oc);
		for(OntClass superClass : superClasses){
			itProperties = ontology.getProperties(ontologyName, superClass);
			while (itProperties.hasNext()) {
				OntProperty prop = (OntProperty) itProperties.next();
				properties.put(prop.getLocalName(), prop);
				propertiesLocation.put(prop.getLocalName(), "properties");
			}
		}
		//Check the restrictions and possibly add new properties to the list
		for(String property : restrictions.keySet()){
			if(properties.containsKey(property))
				continue;			
			properties.put(property, ontology.getProperty(property));
			propertiesLocation.put(property, "properties");
		}
		
		//Add the properties of edited artifact
		for(common.Properties prop2 : resourceProperties){
			String property = prop2.getProperty();		
			property = Utility.getLocalName(property);
			if(properties.containsKey(property))
				continue;
			OntProperty prop = ontology.getProperty(property, ontology.getOntology(ontologyName));
			if(prop == null)
				prop = ontology.getProperty(property, ontology.getOntology("location"));
			if(prop == null)
				prop = ontology.getProperty(property, ontology.getOntology("discourse"));
			if(prop == null)
				continue;
			properties.put(prop.getLocalName(), prop);
			propertiesLocation.put(prop.getLocalName(), "commonProperties");
		}
		
		if(format.equals("HTML")){
			%><ul id="<%=ulId%>" style="<%=ulStyle%>" class="<%=ulClass%>"> <%				
		}
		else{
			%>function reloadProperties(uploadForm){
		var property;
		//Reset the restrictions for empty array.
		uploadForm.properties = [];<%
		}
			JSONArray array = resourceBean.getPropertiesJSON(className, resourceId, ontologyName);
			for(int i = 0;i< array.size();i++){
				JSONObject p = array.getJSONObject(i);
				session.setAttribute("json", p);
				if(format.equals("HTML")){
					%>
					<jsp:include page="liProperty.jsp">
						<jsp:param name="idProperty" value="<%=p.get(\"idProperty\") %>" />
						<jsp:param name="liClass" value="<%=liClass %>" />
						<jsp:param name="liStyle" value="<%=liStyle %>" />
						<jsp:param name="type" value="<%=p.get(\"type\") %>" />
						<jsp:param name="onclick" value="<%=onclick %>" />
						<jsp:param name="label" value="<%=p.get(\"property\") %>" />
						<jsp:param name="data-range" value="<%=p.get(\"range\") %>" />						
					</jsp:include><%					
				}
				else{
					%>							
				<jsp:include page="getOnePropertyJS.jsp"/><%				
				}
		
		}

		if(format.equals("HTML")){
			%></ul><%	;			
		}
		else{
			%>}<%;
		}%>