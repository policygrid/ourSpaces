package common;

import java.util.*;


public class MapDataSeries {
	   private int id;
	   private String name;  
	   private String resourceID; 
	   private String stype;
	   private String ftype;
	   private ArrayList features;
	   
	public String getStype() {
		return stype;
	}
	public void setStype(String stype) {
		this.stype = stype;
	}
	public String getFtype() {
		return ftype;
	}
	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResourceID() {
		return resourceID;
	}
	public void setResourceID(String resourceID) {
		this.resourceID = resourceID;
	}
	public ArrayList getFeatures() {
		return features;
	}
	public void setFeatures(ArrayList features) {
		this.features = features;
	}	  
}
