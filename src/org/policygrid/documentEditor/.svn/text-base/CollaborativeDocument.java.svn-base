package org.policygrid.documentEditor;

import java.util.ArrayList;
import java.util.Hashtable;

public class CollaborativeDocument {

	public String id;
	public String title;
	public ArrayList<String> lines;
	public Hashtable<String,Integer> pointers;

	public CollaborativeDocument() {
		lines = new ArrayList<String>();		
	}
	
	public synchronized void appendLine(String line){
		lines.add(line);
	}
	
	public synchronized void insertLine(int index, String line){
		lines.add(index, line);
	}
	
	public synchronized void removeLine(int index){
		lines.remove(index);
    }
	
	public synchronized String getLine(int index){
	 return lines.get(index);	
	}
	
	public synchronized int size(){
		return lines.size();
	}
	
	
}
