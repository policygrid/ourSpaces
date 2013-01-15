package common;

import java.util.*;


public class MapFeature {
	   private int id;
	   private String name;  
	   private String resourceID; 
	   private ArrayList points;
	   private double numericalValue;
	   private String textValue;
	   
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
	public ArrayList getPoints() {
		return points;
	}
	public void setPoints(ArrayList points) {
		this.points = points;
	}
	public double getNumericalValue() {
		return numericalValue;
	}
	public void setNumericalValue(double numericalValue) {
		this.numericalValue = numericalValue;
	}
	public String getTextValue() {
		return textValue;
	}
	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}

	  
	  
}
