package common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.serialize.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 * Class designed to handle the creation and parsing of XML files.  Each user
 * has an XML created about them holding non-RDF related data.  They are mainly
 * accessed to retrieve resource IDs of users and their addresses.
 * 
 * @author Richard Reid
 * @version 1.2
 *
 */

@Deprecated public class XML 
{
	
	ArrayList left = new ArrayList();
	ArrayList middle = new ArrayList();
	ArrayList right = new ArrayList();
	
	public XML()
	{
		
	}
	
	/**
	 * Handles the creation of new XML files into a particular directory.  Files are
	 * stored based on the user's unique userid.  Each file contains the user's name,
	 * userid and resource IDs.
	 * 
	 * @param userid
	 * @param locator
	 * @param name
	 * @param address
	 * @throws IOException
	 * @throws SAXException
	 */
	public void createFile(String userid, String locator, String name, String address) throws IOException, SAXException
	{
		// Where to create the XML file
		String filename = "/home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/users/" + userid + ".xml";
		
		// Define a new filetype
		File file = new File(filename);
		FileOutputStream fos = new FileOutputStream(filename);
		
		// Set the output of the filetype
		OutputFormat of = new OutputFormat("XML","ISO-8859-1",true);
		of.setIndent(1);
		of.setIndenting(true);

		XMLSerializer serializer = new XMLSerializer(fos,of);

		ContentHandler hd = serializer.asContentHandler();
		hd.startDocument();
		
		AttributesImpl atts = new AttributesImpl();
		
		// Generate XML tags
		hd.startElement("","","user", null);

		  hd.startElement("","","userid",null);
		  hd.characters(userid.toCharArray(),0,userid.length());
		  hd.endElement("","","userid");
		  
		  hd.startElement("","","locator",null);
		  hd.characters(locator.toCharArray(),0,locator.length());
		  hd.endElement("","","locator");
		  
		  hd.startElement("","","name",null);
		  hd.characters(name.toCharArray(),0,name.length());
		  hd.endElement("","","name");
		  
		  hd.startElement("","","address",null);
		  hd.characters(address.toCharArray(),0,address.length());
		  hd.endElement("","","address");
		  
		  hd.startElement("", "", "home", null);
		  	
		    atts.clear();
		    atts.addAttribute("","","name","CDATA","myresources");
		  	atts.addAttribute("","","column","CDATA","2");
		  	atts.addAttribute("", "","status", "CDATA", "1");
		  	atts.addAttribute("", "","position","CDATA","2");
		  	hd.startElement("", "", "box", atts);
		  	hd.endElement("", "", "box");
		  	
		  	atts.clear();
		  	atts.addAttribute("","","name","CDATA","myprojects");
		  	atts.addAttribute("","","column","CDATA","2");
		  	atts.addAttribute("", "","status", "CDATA", "1");
		  	atts.addAttribute("", "","position","CDATA","1");
		  	hd.startElement("", "", "box", atts);
		  	hd.endElement("", "", "box");
		  	
		  	atts.clear();
		  	atts.addAttribute("","","name","CDATA","mytags");
		  	atts.addAttribute("","","column","CDATA","1");
		  	atts.addAttribute("", "","status", "CDATA", "1");
		  	atts.addAttribute("", "","position","CDATA","2");
		  	hd.startElement("", "", "box", atts);
		  	hd.endElement("", "", "box");
		  	
		  	atts.clear();
		  	atts.addAttribute("","","name","CDATA","mycontacts");
		  	atts.addAttribute("","","column","CDATA","1");
		  	atts.addAttribute("", "","status", "CDATA", "1");
		  	atts.addAttribute("", "","position","CDATA","1");
		  	hd.startElement("", "", "box", atts);
		  	hd.endElement("", "", "box");
		  	
		  	atts.clear();
		  	atts.addAttribute("","","name","CDATA","myblog");
		  	atts.addAttribute("","","column","CDATA","3");
		  	atts.addAttribute("", "","status", "CDATA", "1");
		  	atts.addAttribute("", "","position","CDATA","1");
		  	hd.startElement("", "", "box", atts);
		  	hd.endElement("", "", "box");
		  	
		  	atts.clear();
		  	atts.addAttribute("","","name","CDATA","ouractivities");
		  	atts.addAttribute("","","column","CDATA","3");
		  	atts.addAttribute("", "","status", "CDATA", "1");
		  	atts.addAttribute("", "","position","CDATA","2");
		  	hd.startElement("", "", "box", atts);
		  	hd.endElement("", "", "box");
		  	
		  	atts.clear();
		  	atts.addAttribute("","","name","CDATA","myactivities");
		  	atts.addAttribute("","","column","CDATA","3");
		  	atts.addAttribute("", "","status", "CDATA", "1");
		  	atts.addAttribute("", "","position","CDATA","2");
		  	hd.startElement("", "", "box", atts);
		  	hd.endElement("", "", "box");
		  	
		  	
		  	
		  hd.endElement("", "", "home");
		  
		hd.endElement("","","user");
		hd.endDocument();
		
		// Close the file
		fos.close();
	}
	
	/**
	 * Responsible for saving any changes to a home page to the 
	 * user's XML file.  The file is opened and all current tags
	 * are saved to variables.  The file is then re-written with
	 * the new information in place, particularly the boxes and 
	 * their attributes that the user has requested.
	 * 
	 * The information is received in a string from a servlet.  
	 * The string is tokenized and split various times as well
	 * as sorted into the relevant column list.
	 * 
	 * @param id
	 * @param changes
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void updateLayout(int id, String changes) throws ParserConfigurationException, SAXException, IOException
	{
		// Open the correct file
		String filename = "/home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/users/" + id + ".xml"; 
		
		/*
		 * Handle the tokenizing of the string.  Remove the '&' symbol, then split into columns.
		 */
		
		String pattern = "&";
		String newString = changes.substring(0, changes.length()-1);
		String[] fields = newString.split("&");
		
		
		ArrayList lft = new ArrayList();
		ArrayList mid = new ArrayList();
		ArrayList rht = new ArrayList();
		
		
		for(int i=0; i<fields.length; i++){
			if(fields[i].charAt(11) == '0') {
				lft.add(fields[i].split("widget_col_0="));
			}
			if(fields[i].charAt(11) == '1') {
				mid.add(fields[i].split("widget_col_1="));
			}
			if(fields[i].charAt(11) == '2') {
				rht.add(fields[i].split("widget_col_2="));
			}
		}
		
		
		// Initialise the DOM parser
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document dom;
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		// Parse the file
		dom = db.parse(filename);
		Element docEle = dom.getDocumentElement();
		
		// Retrieve the  previous tags from the parsed file so they can be re-added.
		String userid = getTextValue(docEle,"userid");
		String locator = getTextValue(docEle,"locator");
		String name = getTextValue(docEle,"name");
		String address = getTextValue(docEle,"address");
		
		File file = new File(filename);
		FileOutputStream fos = new FileOutputStream(filename);
		
		// Set the output of the filetype
		OutputFormat of = new OutputFormat("XML","ISO-8859-1",true);
		of.setIndent(1);
		of.setIndenting(true);

		XMLSerializer serializer = new XMLSerializer(fos,of);

		ContentHandler hd = serializer.asContentHandler();
		hd.startDocument();
		
		AttributesImpl atts = new AttributesImpl();
		
		// Generate new XML tags
		hd.startElement("","","user", null);

		  hd.startElement("","","userid",null);
		  hd.characters(userid.toCharArray(),0,userid.length());
		  hd.endElement("","","userid");
		  
		  hd.startElement("","","locator",null);
		  hd.characters(locator.toCharArray(),0,locator.length());
		  hd.endElement("","","locator");
		  
		  hd.startElement("","","name",null);
		  hd.characters(name.toCharArray(),0,name.length());
		  hd.endElement("","","name");
		  
		  hd.startElement("","","address",null);
		  hd.characters(address.toCharArray(),0,address.length());
		  hd.endElement("","","address");
		  
		  hd.startElement("", "", "home", null);
		  for(int j=0; j<lft.size(); j++){
				String[] tmp = (String[]) lft.get(j);
				String name2 = (String) tmp[1];
				Integer pos = j+1;
				String position = pos.toString();
				atts.clear();
			    atts.addAttribute("","","name","CDATA",getTagName(name2));
			  	atts.addAttribute("","","column","CDATA","1");
			  	atts.addAttribute("", "","status", "CDATA", "1");
			  	atts.addAttribute("", "","position","CDATA",position);
			  	hd.startElement("", "", "box", atts);
			  	hd.endElement("", "", "box");	
			}
			
			for(int j=0; j<mid.size(); j++){
				String[] tmp = (String[]) mid.get(j);
				String name2 = (String) tmp[1];
				Integer pos = j+1;
				String position = pos.toString();
				atts.clear();
			    atts.addAttribute("","","name","CDATA",getTagName(name2));
			  	atts.addAttribute("","","column","CDATA","2");
			  	atts.addAttribute("", "","status", "CDATA", "1");
			  	atts.addAttribute("", "","position","CDATA",position);
			  	hd.startElement("", "", "box", atts);
			  	hd.endElement("", "", "box");
			}
			
			for(int j=0; j<rht.size(); j++){
				String[] tmp = (String[]) rht.get(j);
				String name2 = (String) tmp[1];
				Integer pos = j+1;
				String position = pos.toString();
				atts.clear();
			    atts.addAttribute("","","name","CDATA",getTagName(name2));
			  	atts.addAttribute("","","column","CDATA","3");
			  	atts.addAttribute("", "","status", "CDATA", "1");
			  	atts.addAttribute("", "","position","CDATA",position);
			  	hd.startElement("", "", "box", atts);
			  	hd.endElement("", "", "box");
			}
		  	
		  hd.endElement("", "", "home");
		  
		hd.endElement("","","user");
		hd.endDocument();
		
		// Close the file
		fos.close();
		 
	}
	
	
	public void deleteBox(int id, String original, String remove) throws ParserConfigurationException, SAXException, IOException
	{
		// Open the correct file
		String filename = "/home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/users/" + id + ".xml"; 
		
		/*
		 * Handle the tokenizing of the string.  Remove the '&' symbol, then split into columns.
		 */
		
		String pattern = "&";
		String newString = original.substring(0, original.length()-1);
		String[] fields = newString.split("&");
		
		ArrayList lft = new ArrayList();
		ArrayList mid = new ArrayList();
		ArrayList rht = new ArrayList();
		
		
		for(int i=0; i<fields.length; i++){
			if(fields[i].charAt(11) == '0') {
				lft.add(fields[i].split("widget_col_0="));
			}
			if(fields[i].charAt(11) == '1') {
				mid.add(fields[i].split("widget_col_1="));
			}
			if(fields[i].charAt(11) == '2') {
				rht.add(fields[i].split("widget_col_2="));
			}
		}
		
		for(int i=0; i<lft.size(); i++){
			String[] tmp = (String[]) lft.get(i);
			String name2 = (String) tmp[1];
			if(name2.equals(remove))
				lft.remove(i);
		}
		
		for(int i=0; i<mid.size(); i++){
			String[] tmp = (String[]) mid.get(i);
			String name2 = (String) tmp[1];
			if(name2.equals(remove))
				mid.remove(i);
		}
		
		for(int i=0; i<rht.size(); i++){
			String[] tmp = (String[]) rht.get(i);
			String name2 = (String) tmp[1];
			if(name2.equals(remove))
				rht.remove(i);
		}
		
		// Initialise the DOM parser
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document dom;
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		// Parse the file
		dom = db.parse(filename);
		Element docEle = dom.getDocumentElement();
		
		// Retrieve the  previous tags from the parsed file so they can be re-added.
		String userid = getTextValue(docEle,"userid");
		String locator = getTextValue(docEle,"locator");
		String name = getTextValue(docEle,"name");
		String address = getTextValue(docEle,"address");
		
		File file = new File(filename);
		FileOutputStream fos = new FileOutputStream(filename);
		
		// Set the output of the filetype
		OutputFormat of = new OutputFormat("XML","ISO-8859-1",true);
		of.setIndent(1);
		of.setIndenting(true);

		XMLSerializer serializer = new XMLSerializer(fos,of);

		ContentHandler hd = serializer.asContentHandler();
		hd.startDocument();
		
		AttributesImpl atts = new AttributesImpl();
		
		// Generate new XML tags
		hd.startElement("","","user", null);

		  hd.startElement("","","userid",null);
		  hd.characters(userid.toCharArray(),0,userid.length());
		  hd.endElement("","","userid");
		  
		  hd.startElement("","","locator",null);
		  hd.characters(locator.toCharArray(),0,locator.length());
		  hd.endElement("","","locator");
		  
		  hd.startElement("","","name",null);
		  hd.characters(name.toCharArray(),0,name.length());
		  hd.endElement("","","name");
		  
		  hd.startElement("","","address",null);
		  hd.characters(address.toCharArray(),0,address.length());
		  hd.endElement("","","address");
		  
		  hd.startElement("", "", "home", null);
		  for(int j=0; j<lft.size(); j++){
				String[] tmp = (String[]) lft.get(j);
				String name2 = (String) tmp[1];
				Integer pos = j+1;
				String position = pos.toString();
				atts.clear();
			    atts.addAttribute("","","name","CDATA",getTagName(name2));
			  	atts.addAttribute("","","column","CDATA","1");
			  	atts.addAttribute("", "","status", "CDATA", "1");
			  	atts.addAttribute("", "","position","CDATA",position);
			  	hd.startElement("", "", "box", atts);
			  	hd.endElement("", "", "box");	
			}
			
			for(int j=0; j<mid.size(); j++){
				String[] tmp = (String[]) mid.get(j);
				String name2 = (String) tmp[1];
				Integer pos = j+1;
				String position = pos.toString();
				atts.clear();
			    atts.addAttribute("","","name","CDATA",getTagName(name2));
			  	atts.addAttribute("","","column","CDATA","2");
			  	atts.addAttribute("", "","status", "CDATA", "1");
			  	atts.addAttribute("", "","position","CDATA",position);
			  	hd.startElement("", "", "box", atts);
			  	hd.endElement("", "", "box");
			}
			
			for(int j=0; j<rht.size(); j++){
				String[] tmp = (String[]) rht.get(j);
				String name2 = (String) tmp[1];
				Integer pos = j+1;
				String position = pos.toString();
				atts.clear();
			    atts.addAttribute("","","name","CDATA",getTagName(name2));
			  	atts.addAttribute("","","column","CDATA","3");
			  	atts.addAttribute("", "","status", "CDATA", "1");
			  	atts.addAttribute("", "","position","CDATA",position);
			  	hd.startElement("", "", "box", atts);
			  	hd.endElement("", "", "box");
			}
		  	
		  hd.endElement("", "", "home");
		  
		hd.endElement("","","user");
		hd.endDocument();
		
		// Close the file
		fos.close();
		 
	}
	
	
	/**
	 * To return an instance of the HomeLayout JavaBean used to 
	 * construct the home page boxes.  Information is retrieved 
	 * from the user's XML file then each tag is converted into 
	 * a Box object.
	 * 
	 * @param id
	 * @return HomeLayout
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public HomeLayout getLayout(int id) throws ParserConfigurationException, SAXException, IOException
	{
		
		// Open the correct file
		String filename = "/home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/users/" + id + ".xml"; 
		
		// Initialise the DOM parser
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document dom;
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		// Parse the file
		dom = db.parse(filename);
		
		//Element docEle = dom.getDocumentElement();
		 
		// get all boxes
		NodeList children = dom.getElementsByTagName("box");
		
		// Iterate through all boxes to get the the attributes
		for(int i = 0; i<children.getLength(); i++){
			Node node = (Node) children.item(i);
			String name = "";
			int column = 0;
			int status = 0;
			int position = 0;
			NamedNodeMap atts = node.getAttributes();
			for(int j = 0; j<atts.getLength(); j++){
				Attr attr = (Attr) atts.item(j);
				if(attr.getNodeName().equals("name"))
					name = attr.getNodeValue();
				if(attr.getNodeName().equals("column"))
					column = Integer.parseInt(attr.getNodeValue());
				if(attr.getNodeName().equals("status"))
					status = Integer.parseInt(attr.getNodeValue());
				if(attr.getNodeName().equals("position"))
					position = Integer.parseInt(attr.getNodeValue());
			}
			
			Box box = new Box(name, column, status, position);
			setColumn(box);
		}
		
		// Sort each list in order of position.
		
		sort(left);
		sort(middle);
		sort(right);
		
		
		HomeLayout home = new HomeLayout(left, middle, right);
		return home;
		
	}
	
	 public void sort(ArrayList<Box> a)
	 {
	  for(int i=1; i<a.size(); i++)
	     insert(a.get(i),a,i);
	 }

	 private void insert(Box n,ArrayList<Box> a,int i)
	 {
	  for(; i>0&&((Comparable<Integer>) a.get(i-1).getPosition()).compareTo(n.getPosition())>0; i--)
	     a.set(i,a.get(i-1));
	  a.set(i,n);
	 }

	/**
	 * Adds a box to the relevant list based on its position
	 * tag.
	 * @param box
	 */
	public void setColumn(Box box){
		if(box.getColumn() == 1)
			left.add(box);
		else if(box.getColumn() == 2)
			middle.add(box);
		else
			right.add(box);
	}
	
	
	
	/**
	 * Returns the resource ID of a particular user.  The ID is retrieved from the
	 * user's XML file by finding the appropriate file and parsing it using DOM.
	 * 
	 * @param id
	 * @return String of the user's resource ID
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public String getResourceID(int id) throws ParserConfigurationException, SAXException, IOException
	{
		// Open the correct file
		String filename = "/home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/users/" + id + ".xml"; 
		
		// Initialise the DOM parser
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document dom;
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		// Parse the file
		dom = db.parse(filename);
		Element docEle = dom.getDocumentElement();
		
		// Retrieve the resource ID from the parsed file
		String resourceID = getTextValue(docEle,"locator");
		return resourceID;
	}
	
	/**
	 * Generates the resource ID for a particular user's address.  The ID is 
	 * retrieved from the user's XML file by finding the appropriate file and 
	 * parsing it using DOM.
	 * 
	 * @param id
	 * @return String containing user's address ID
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public String getAddressID(int id) throws ParserConfigurationException, SAXException, IOException
	{
		// Find the correct file
		String filename = "/home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/users/" + id + ".xml"; 
		
		// Initialise the DOM parser
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document dom;
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		// Parse the file
		dom = db.parse(filename);
		Element docEle = dom.getDocumentElement();
		
		// Retrieve the correct tag from the parsed file
		String addressID = getTextValue(docEle,"address");
		return addressID;
	}
	
	/**
	 * Returns the value of a particular XML tag in a String format.
	 * 
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private String getTextValue(Element ele, String tagName) 
	{
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) 
		{
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}
		return textVal;
	}
	
	/**
	 * Returns the tag name based on the box title
	 * @param name
	 * @return
	 */
	public String getTagName(String name){
		String full = null;
		if(name.equals("My Resources"))
			full = "myresources";
		else if(name.equals("My Tags"))
			full = "mytags";
		else if(name.equals("My Contacts"))
			full = "mycontacts";
		else if(name.equals("My Projects"))
			full = "myprojects";
		else if(name.equals("My Tools"))
			full = "mytools";
		else if(name.equals("My Groups"))
			full = "mygroups";
		else if(name.equals("My Activities"))
			full = "myactivities";
		else if(name.equals("Our Activities"))
			full = "ouractivities";
		else if(name.equals("Our Tags"))
			full = "ourtags";
		else if(name.equals("Resource Timeline"))
			full = "timeline";
		else if(name.equals("My Blog"))
			full = "myblog";
		else
			full = "alltags";
		return full;
	}
	
	/**
	 * Returns a particular XML element based on the tag name.
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private Element getElement(Element ele, String tagName){
		Element el = null;
		NodeList list = ele.getElementsByTagName(tagName);
		for(int i = 0; i<list.getLength(); i++){
			el = (Element) list.item(i);
		}
		return el;
	}
}
