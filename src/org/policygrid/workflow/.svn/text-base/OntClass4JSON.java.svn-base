package org.policygrid.workflow;

import java.util.ArrayList;
import java.util.List;

public class OntClass4JSON {
	private String localName;
	private String uri;
	private List<OntClass4JSON> subClasses;
	private List<Property4JSON> properties;
	
	public OntClass4JSON() {
		subClasses = new ArrayList<OntClass4JSON>();
		properties = new ArrayList<Property4JSON>();
	}
	
	public String getLocalName() {
		return localName;
	}
	public void setLocalName(String localName) {
		this.localName = localName;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public void addSubClass(OntClass4JSON ontClass4JSON) {
		this.subClasses.add(ontClass4JSON);
	}

	public List<OntClass4JSON> getSubClasses() {
		return subClasses;
	}
	
	public void addProperty(Property4JSON property4JSON) {
		this.properties.add(property4JSON);
	}

	public List<Property4JSON> getProperties() {
		return properties;
	}
}
