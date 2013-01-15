package lexicon.builder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import net.didion.jwnl.JWNLException;
import net.didion.jwnl.util.factory.NameValueParam;
import net.didion.jwnl.util.factory.Param;
import net.didion.jwnl.util.factory.ParamList;
import net.didion.jwnl.util.factory.ValueParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.Configuration;

public class PersonalLexicon {
	private static boolean initialised;
	private static String dictfile;
	public static Map<String, JSONArray> mp;
	
	public PersonalLexicon(){
		initialised=false;
		mp = Collections.synchronizedMap(new HashMap<String, JSONArray>());
		
	}
	
	protected boolean isInitialised(){
		return initialised;
	}
	public  void loadDict(String foaf){
		
		
		dictfile=Configuration.getParaemter("pls","sourceFolder")+foaf;
		//dictfile="/Users/kapila/Documents/repository/lexicon/"+foaf;
		        
		File file = new File(dictfile);
		 
		    try {
		    	Scanner scanner = new Scanner(new FileReader(file));
		    	while (scanner.hasNext()){
		    		String line=scanner.nextLine();
		    		

//		    		JSONObject json = (JSONObject) JSONSerializer.toJSON( line );   
//		    		String word=json.getString("word");
//		    		JSONArray relwords = (JSONArray) JSONSerializer.toJSON( json.get("rt") );    
		    		
		    		
		    		
					JSONObject json = (JSONObject) JSONSerializer.toJSON( line );   
					JSONObject jsons1 = (JSONObject) JSONSerializer.toJSON( json.get("s1") ); 
					JSONObject jsons2 = (JSONObject) JSONSerializer.toJSON( json.get("s2") ); 
		    		String word=jsons1.getString("word");
					
		    		
		    		
//		    		for (int i =0;i<jsonarr.size();i++){
//		    			JSONObject jsnobj=jsonarr.getJSONObject(i);
//		    			System.out.println(jsnobj.getString("word"));
//		    			System.out.println(jsnobj.getString("source"));
//		    		}
		    		
//		    		String rt=line.split(",")[1];
//		    		Vector<JSONArray> relwords=new Vector <JSONArray>();
					JSONArray jsa;
		    		if (mp.containsKey(word)){
		    			 jsa=mp.get(word);
		    		
		    			
		    		}else{
		    			 jsa=new JSONArray();
		    			
		    		}
		    		jsa.add(jsons2);
	    			mp.put(word, jsa);
	    			System.out.println(jsa);
		    	}
		    	initialised=true;
		    }catch (Exception ex){
		    	System.out.println(ex.getMessage());
		    }
		

}
	public  void init(InputStream propertiesStream, String foafid){
		//foafid="3980b9b8-d707-482f-89e5-ed50665540ec";
			// parse the properties file
				Document doc = null;
				try {
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					factory.setValidating(false);
					DocumentBuilder docBuilder = factory.newDocumentBuilder();
					doc = docBuilder.parse(propertiesStream);
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}

				// do this in a separate try/catch since parse can also throw an IOException
				try {
					propertiesStream.close();
				} catch (IOException ex) {
					System.out.println(ex.getMessage());
				}

				org.w3c.dom.Element root = doc.getDocumentElement();
				
				NodeList nl =root.getChildNodes();
				//System.out.println("NODE LIST SIZE"+nl.getLength());
				for (int i=0;i<nl.getLength();i++){
					if (nl.item(i).getNodeName().equals("param")){

						if (getAttribute(nl.item(i), "name").equals("dict_file")){
							System.out.println(getAttribute(nl.item(i), "value"));
							dictfile=getAttribute(nl.item(i), "value")+foafid;
						}
					
					}
				}
				//System.out.println("DICT FILE:"+dictfile);
				initialised=true;
	}
	
	public static void updateLexicon(String foafid, String words){
		String path=Configuration.getParaemter("pls","sourceFolder")+foafid;
		//words="[{s1:{word:'effect',source:'person',id:'3980b9b8-d707-482f-89e5-ed50665540ec'},s2:{word:'influence',source:'project',id:'aadb9597-6392-42e0-9815-489cbe80cd19'}},{s1:{word:'effect',source:'person',id:'3980b9b8-d707-482f-89e5-ed50665540ec'},s2:{word:'impact',source:'project',id:'aadb9597-6392-42e0-9815-489cbe80cd19'}},{s1:{word:'flow',source:'person',id:'3980b9b8-d707-482f-89e5-ed50665540ec'},s2:{word:'flow',source:'project',id:'aadb9597-6392-42e0-9815-489cbe80cd19'}}]";
				
		String decoded = "";
		try{
			decoded = URLDecoder.decode(words, "UTF-8");
		}catch (Exception ex){
			
		}
		System.out.println(decoded);
		JSONArray pairs = (JSONArray) JSONSerializer.toJSON( decoded );    
		simpleNLG snlg=new simpleNLG();
		
		try {
			 
			File file =new File(path);
			 
    		//if file doesnt exists, then create it
    		if(!file.exists()){
    			file.createNewFile();
    			System.out.println("new file");
    		}
    		System.out.println("file already exists:"+path);
    		//true = append file
    		FileWriter fileWritter = new FileWriter(path,true);
    	    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    	        
			
			
			 for (int i=0; i<pairs.size();i++){
				
				 System.out.println("writing...:"+pairs.get(i).toString()+System.getProperty("line.separator"));
				 JSONObject pair = (JSONObject) JSONSerializer.toJSON( pairs.get(i).toString() );
				 String w1=((JSONObject) pair.get("s1")).getString("word");
				 String w2=((JSONObject) pair.get("s2")).getString("word");
				 JSONObject newobj1=new JSONObject();
				 newobj1.put("word", snlg.getPlural(w1));
				 newobj1.put("source", ((JSONObject) pair.get("s1")).getString("source"));
				 newobj1.put("id", ((JSONObject) pair.get("s1")).getString("id"));
				 
				 
				 
				 JSONObject newobj2=new JSONObject();
				 newobj2.put("word", snlg.getPlural(w2));
				 newobj2.put("source", ((JSONObject) pair.get("s2")).getString("source"));
				 newobj2.put("id", ((JSONObject) pair.get("s2")).getString("id"));
				// "{"s1":{"word":"activity","source":"person","id":"3980b9b8-d707-482f-89e5-ed50665540ec"},"s2":{"word":"exchange","source":"project","id":"aadb9597-6392-42e0-9815-489cbe80cd19"}}"
				 
				 JSONObject newobj3=new JSONObject();
				 newobj3.put("s1", newobj1);
				 newobj3.put("s2", newobj2);
				 
	    	    bufferWritter.write(pairs.get(i).toString()+System.getProperty("line.separator"));
	    	    bufferWritter.write( newobj3.toString()+System.getProperty("line.separator"));

			}
			 
			 System.out.println("Done");
			 bufferWritter.close();
			  } catch (IOException e){
			       e.printStackTrace();
			    }
		
		
	}
	
	protected static void pdf2Text(){
		
		
		
		
	}
	
	
	
	
	private static String getAttribute(Node node, String attributeName) {
		NamedNodeMap map = node.getAttributes();
		if (map != null) {
			Node n = map.getNamedItem(attributeName);
			if (n != null) {
				return n.getNodeValue();
			}
		}
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		updateLexicon("","");
		if (! initialised){		
			PersonalLexicon pl=new PersonalLexicon();
			pl.init(LexDefaults.getInputStream(),"");
			String df="/home/policygrid/apache-tomcat-6.0.18/data/lexicons/personal/3980b9b8-d707-482f-89e5-ed50665540ec.csv";
			pl.loadDict(df);
			
			System.out.println("initialised.....");
			System.out.println("Size.....:"+mp.size() );
			System.out.println("word effect.....:"+mp.get("effect") );
			
			
		}
	}

}
