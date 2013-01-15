package org.policygrid.workflow;

import java.util.ArrayList;
import java.util.List;

public class Edge4JSON {
	private String localName;
	private String uri;
	private List<Edge4JSON> subEdges;
	private String causeType;
	private String effectType;
	private List<Property4JSON> properties;
	
	public Edge4JSON(String localName, String uri) {
		this.localName = localName;
		this.uri = uri;
		subEdges = new ArrayList<Edge4JSON>();
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
	public List<Edge4JSON> getSubEdges() {
		return subEdges;
	}
	public void addSubEdge(Edge4JSON jsonSubEdge) {
		this.subEdges.add(jsonSubEdge);
	}

	public String getCauseType() {
		return causeType;
	}

	public void setCauseType(String causeType) {
		this.causeType = causeType;
	}

	public String getEffectType() {
		return effectType;
	}

	public void setEffectType(String effectType) {
		this.effectType = effectType;
	}
	public void addProperty(Property4JSON jsonProperty) {
		this.properties.add(jsonProperty);
	}
	
	public List<Property4JSON> getProperties() {
		return properties;
	}
}
