package common;

public class OntologyBean {
public String name;
public String namespace;
public String path;


public OntologyBean(String name, String namespace, String path) {
	super();
	this.name = name;
	this.namespace = namespace;
	this.path = path;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getNamespace() {
	return namespace;
}
public void setNamespace(String namespace) {
	this.namespace = namespace;
}
public String getPath() {
	return path;
}
public void setPath(String path) {
	this.path = path;
}


}
