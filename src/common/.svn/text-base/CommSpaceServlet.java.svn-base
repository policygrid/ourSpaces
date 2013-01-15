package common;

import java.io.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.Vector;
import java.io.File;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;




import org.openrdf.repository.*;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.OpenRDFException;
import org.openrdf.model.Literal;
import org.openrdf.rio.rdfxml.RDFXMLWriter;
import org.openrdf.rio.*;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.query.*;
import org.openrdf.query.resultio.sparqlxml.SPARQLResultsXMLWriter;
import org.xml.sax.SAXException;
import org.zkforge.json.simple.parser.JSONParser;

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.sparql.lib.org.json.JSONML;
import com.hp.hpl.jena.sparql.resultset.JSONOutput;
import com.hp.hpl.jena.util.FileManager;
import com.lowagie.text.List;

import org.policygrid.ontologies.*;
public class CommSpaceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	  
	
	public String fileNameToUpload;
	public File fileResource;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request,response);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
        // the action element that we'll check for
        String cmd;
        common.Utility.log.debug("Found commspace servlet!");
        cmd = request.getParameter("action");
        String comids = request.getParameter("comids");
        response.setContentType("text/plain");
        common.Utility.log.debug(cmd);
        if (cmd != null) 
        {
        	 if (cmd.equals("save")) 
             {
        		try {
        			
        			//response.getWriter().write("success");
        			
        			createRDF(request, response);
        			
//				} catch (ParserConfigurationException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (SAXException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
				}
        		catch (Exception ex){
        			
        			common.Utility.log.debug(ex.getMessage());
        		}
             }
        	 if (cmd.equals("read")) 
             {
        		try {
        			//createComArtifact(request, response);
        			//doSomething(request, response);
        			getComArtifact(request, response);
        			
//				} catch (ParserConfigurationException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (SAXException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		 //out.write("testdata");
             }
            //out.flush();
            out.close();
        }
        else
        {
        	out.println("Server error.");
        }
	}
	/**
	 * Creates RDF about resource
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public void getComArtifact(HttpServletRequest request,
			HttpServletResponse response) throws ParserConfigurationException,
			SAXException, IOException {

		

		try {
			PrintWriter writer = response.getWriter();
			HttpSession session = request.getSession();
			User userSession = (User) session.getAttribute("use");
			String artifactid=request.getParameter("artifactid");
			common.Utility.log.debug("the artefact id: "+artifactid);
			String fileoutput =Utility.readTextFile(artifactid);
			common.Utility.log.debug(fileoutput);
			XMLSerializer xmlSerializer = new XMLSerializer(); 
            JSON json2 = xmlSerializer.read( fileoutput.trim() ); 
            common.Utility.log.debug("JSON from XML: "+ json2 );
            String msgstr="";
            if (json2.isArray()){
            	JSONArray jsnarr=(JSONArray) json2;
            	common.Utility.log.debug( jsnarr.get(0).toString() );
                
                for (int i =0; i<jsnarr.size();i++){
                	JSONObject msgobj=(JSONObject) jsnarr.get(i);
                	
                	msgstr=msgstr+constrJson(msgobj, userSession);
                }
                
            }
            else {
            	JSONObject  msgobj=(JSONObject) ((JSONObject) json2).get("e");
            	common.Utility.log.debug( "JSON OBJ "+msgobj.get("e") );
            	msgstr=constrJson(msgobj, userSession);
            	
            }
            
            
            
            msgstr=msgstr.substring(0,msgstr.length()-1);
            msgstr="["+msgstr+"]";
            common.Utility.log.debug(msgstr);
			writer.write(msgstr );
			
		}catch (Exception ex){
			common.Utility.log.debug(ex.getMessage());
		}
		
	
	}

	
	public String constrJson(JSONObject msgobj, User usr){
		String str="";
		if (msgobj.containsKey("MessageID")){
    		String sender=msgobj.getString("Sender");
    		String sendername=usr.getName(Integer.parseInt(sender)) ;
    		str=str+"{messageid:"+msgobj.getString("MessageID")+",SentDate:'"+msgobj.getString("SentDate")+"',Sender:'"+sendername+"'},";
    	}
    	else if (msgobj.containsKey("ImID")){
    		
    		String sender=msgobj.getString("Sender");
    		String sendername=usr.getName(Integer.parseInt(sender)) ;
    		str=str+"{imid:"+msgobj.getString("ImID")+",SentDate:'"+msgobj.getString("SentDate")+"',Sender:'"+sendername+"'},";

    	}
    	else if (msgobj.containsKey("PostID")){
    		String sender=msgobj.getString("author");
    		String sendername=usr.getName(Integer.parseInt(sender)) ;
    		str=str+"{postid:"+msgobj.getString("PostID")+",SentDate:'"+msgobj.getString("Date")+"',Sender:'"+sendername+"'},";
    	}
    	else if (msgobj.containsKey("CommentID")){
    		
    		str=str+"{commentid:"+msgobj.getString("CommentID")+",SentDate:'"+msgobj.getString("Date")+"',Sender:'"+msgobj.getString("author")+"'},";
    	}
    	else if (msgobj.containsKey("ArtefactcommID")){
   
    		str=str+"{artefactcommentid:"+msgobj.getString("ArtefactcommID")+",SentDate:'"+msgobj.getString("Date")+"',Sender:'"+msgobj.getString("author")+"'},";
    	}
		
		return str;
		
	}

	/**
	 * Creates RDF about resource
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public void createRDF(HttpServletRequest request,
			HttpServletResponse response) throws ParserConfigurationException,
			SAXException, IOException {

		

		try {
			PrintWriter writer = response.getWriter();
			HttpSession session = request.getSession();

			

			// Create empty Ontology Model
			Model model = ModelFactory.createDefaultModel();
			
			String projectID = (String) session.getAttribute("projectID");
			User userSession = (User) session.getAttribute("use");
			Resource person = model.createResource(userSession.getRDFID());	


			String uniqueResourceID = "http://openprovenance.org/ontology#"	+ UUID.randomUUID().toString();
			Resource myResource = model.createResource(uniqueResourceID);

			boolean privateRes = Boolean.parseBoolean(request.getParameter("pvt"));
			
			String fileURI = null;
			
			String title="test";//request.getParameter("title");
			
			if (request.getParameter("project")!=null)
				projectID=request.getParameter("project");
			
				
			
			fileNameToUpload = uniqueResourceID.split("#")[1]+".xml";
			//write the selected communication items as an artefact
			File file = new File("/home/policygrid/apache-tomcat-6.0.18/temp/"+fileNameToUpload);
			Writer output = new BufferedWriter(new FileWriter(file));
			output.write(createCommArtefact(request, response));
			output.close();
			
			String uri = Utility.storeFile(userSession.getID(), fileNameToUpload, new File("/home/policygrid/apache-tomcat-6.0.18/temp/"+fileNameToUpload), uniqueResourceID, projectID,"/");
			
			
			//Returns the uri of the file.
			

//			String type="http://www.policygrid.org/provenance-generic.owl#Communication";
//			myResource.addProperty(model.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "type"),	model.createResource(type));
//			String readdatetime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
//			myResource.addProperty(ProvenanceGeneric.depositedBy,person);
//			
//			myResource.addProperty(ProvenanceGeneric.title,title);
//			extractTags(title,uniqueResourceID, userSession);
//			if (projectID.length()!=0)
//				myResource.addProperty(ProvenanceGeneric.producedInProject,	model.createResource(projectID));
//			myResource.addProperty(ProvenanceGeneric.dateOfDeposit, readdatetime);
//			myResource.addProperty(ProvenanceGeneric.hasURI,uri);
//			myResource.addProperty(OurSpacesVRE.timestamp,""+System.currentTimeMillis());
//	
//			
//			
//				//Debug - lets see what we are writing to the repository
//				common.Utility.log.debug("================================="+request.getParameter("pvt"));
//				model.write(System.out);
//				//Write the model to RDF database
//				ByteArrayOutputStream outp = new ByteArrayOutputStream();
//				RDFi rdfi = new RDFi();
//				model.write(outp);
//				InputStream in = new ByteArrayInputStream(outp.toByteArray());
//				rdfi.write(in);
			common.Utility.log.debug(uri);
				writer.write(uri);
				
		} catch (Exception e) {
			common.Utility.log.debug(e.getMessage());
			e.printStackTrace();
		}
		

		//response.sendRedirect(outputURL);
	}
	
	/**
	 * Extracts tags from the title
	 * @param title Title to extracts tags from
	 * @param uniqueResourceID Resource id
	 * @param userSession User session
	 */
	protected void extractTags(String title, String uniqueResourceID, User userSession){
		//Extract tags from the title                 	   
    	try {
        	Vector<String> titleSplit = Utility.getKeywordsFromString(title);
        	Tag tag = new Tag();
        	String taggerID = userSession.getFOAFID();
        	//To fix add stopwords               	   
        	for(int k = 0; k < titleSplit.size(); k++)
        	{
        		tag.addTag((String)titleSplit.get(k), uniqueResourceID, taggerID);
        	}
        	Activities act = new Activities();
       		act.addActivity(userSession.getID(), 2, uniqueResourceID, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the access rights to the resource depending on the project and privacy.
	 * @param projectID
	 * @param privateRes
	 * @param model
	 * @param uniqueResourceID
	 * @param userSession
	 * @return
	 */
	protected String addAccessRights(String projectID, boolean privateRes, Model model, String uniqueResourceID, User userSession){
		try {
			//Handle access rights
			if(projectID != null && !projectID.equals("")) {
				if (privateRes) {
					AccessControl.setGroupPermission("view", projectID, uniqueResourceID, true);
					AccessControl.setGroupPermission("download", projectID, uniqueResourceID, true);
					 
					common.Project project = new common.Project();
					String pProjectID = project.getParentProject(projectID);
					common.Utility.log.debug("Setting permission "+ uniqueResourceID +" for parent project :" + pProjectID);
					if (pProjectID != null) {
						AccessControl.setGroupPermission("view", pProjectID, uniqueResourceID, true);
						AccessControl.setGroupPermission("download", pProjectID, uniqueResourceID, true); 
					}
				 
				} 
				else {
					 AccessControl.setPublic(uniqueResourceID);
				}
				String[] fields = projectID.split("#");
				
				Resource project = model.createResource(projectID);
				Resource myResource = model.createResource(uniqueResourceID);
				myResource.addProperty(ProvenanceGeneric.producedInProject,project);
				Activities act = new Activities();
				act.addActivity(userSession.getID(), 26, uniqueResourceID, projectID);
				return "project.jsp?id="+fields[1];
			 }
			 else
			 {
				if (!privateRes) 
					AccessControl.setPublic(uniqueResourceID);
				Activities act = new Activities();
				act.addActivity(userSession.getID(), 1, uniqueResourceID, "");
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "myhome.jsp";		
	}
	
	public String createCommArtefact(HttpServletRequest request, HttpServletResponse response) throws ParserConfigurationException, SAXException, IOException
	{
		String xml="";
		try {
		
  	    
  		HttpSession session = request.getSession();
  		
    	User userSession = (User) session.getAttribute("use");
    	//userSession.setID(userID);
    	
    	common.Utility.log.debug(userSession.getID());
    	common.Utility.log.debug(userSession.getRDFID());
 	    
    	//JSONObject res = (JSONObject) JSONSerializer.toJSON(request.getParameter("resource"));
    	
    	String json = (String) request.getParameter("comids");
    	//Use the JSON-Java binding library to create a JSON object in Java JSONObject jsonObject = null;
    	
    	JSONArray commArray = (JSONArray) JSONSerializer.toJSON(json);
    	
    	common.Utility.log.debug("JSON Array"+commArray.toString());
    	JSONArray jsonmsgs=new JSONArray();	
    	for (int i=0;i<commArray.size();i++){
    		JSONObject commitem =(JSONObject) commArray.get(i);
    		common.Utility.log.debug("JSON obj"+commitem.toString());
    		
    		if (commitem.toString().contains("msgID")){
    			Message msg=(Message) userSession.getMessage(commitem.getInt("msgID")).get(0);
         		common.Utility.log.debug("msg id "+msg.getID());
         		JSONObject jsonmsg=new JSONObject();
         		jsonmsg.put("MessageID", msg.getID());
         		jsonmsg.put("Sender", msg.getSender());
         		jsonmsg.put("SentDate", msg.getSent().toString());
         		//jsonmsg.put("Content", msg.getContent().replaceAll("\r\n", ""));
         		//jsonmsg.put("Recipients", msg.getRecipients());
         		jsonmsgs.add(jsonmsg);
    		}else if (commitem.toString().contains("imID")){
    			InstantMessage im=(InstantMessage) userSession.getInstantMessage(commitem.getInt("imID")).get(0);
         		common.Utility.log.debug("imID"+im.getIMid());
         		JSONObject jsonmsg=new JSONObject();
         		jsonmsg.put("ImID", im.getIMid());
         		jsonmsg.put("Sender", im.getFromUserId());
         		jsonmsg.put("SentDate", im.getSentDate().toString());
         		//jsonmsg.put("Recipients", msg.getRecipients());
         		jsonmsgs.add(jsonmsg);
    		}else if (commitem.toString().contains("blogID")){
    			Blog b=new Blog();
    			BlogBean blogpost=(BlogBean) b.getBlogPost(commitem.getInt("blogID")).get(0);
         		common.Utility.log.debug("blogID"+blogpost.getID());
         		JSONObject jsonmsg=new JSONObject();
         		jsonmsg.put("PostID", blogpost.getID());
         		jsonmsg.put("author", blogpost.getUserID());
         		jsonmsg.put("title", blogpost.getTitle());
         		jsonmsg.put("Date", blogpost.getDate());
         		jsonmsgs.add(jsonmsg);
    		}else if (commitem.toString().contains("blogcommID")){
    			Blog b=new Blog();
    			BlogBean comment=(BlogBean) b.getComment(commitem.getInt("blogcommID")).get(0);
         		common.Utility.log.debug("blogcommentID"+comment.getID());
         		JSONObject jsonmsg=new JSONObject();
         		jsonmsg.put("CommentID", comment.getID());
         		jsonmsg.put("author", userSession.getName( comment.getUserID()));
         		jsonmsg.put("Date", comment.getDate());
         		jsonmsgs.add(jsonmsg);
    		}else if (commitem.toString().contains("ArtefactcommID")){
    			common.Utility.log.debug(commitem.getInt("ArtefactcommID"));
    			Resources res=new Resources();
    			BlogBean comment=(BlogBean) res.getComment(commitem.getInt("ArtefactcommID")).get(0);
         		common.Utility.log.debug("ArtefactcommID"+comment.getID());
         		JSONObject jsonmsg=new JSONObject();
         		jsonmsg.put("ArtefactcommID", comment.getID());
         		jsonmsg.put("author", userSession.getName( comment.getUserID()));
         		jsonmsg.put("Date", comment.getDate());
         		jsonmsgs.add(jsonmsg);
    		}
    		
    	}
    	
    	
    	common.Utility.log.debug("JSON String...");
    	common.Utility.log.debug(jsonmsgs.toString());
    	
    	String jsonData = jsonmsgs.toString();
		XMLSerializer serializer = new XMLSerializer(); 
    	JSON jsonstr = JSONSerializer.toJSON( jsonData ); 
    	serializer.setRootName("CommArtifact");
    	serializer.setTypeHintsEnabled(false);
    	xml = serializer.write( jsonstr );  
    	common.Utility.log.debug(xml);   
    	}catch(Exception ex)
    	{
    		common.Utility.log.debug(ex.getMessage());
    	}
		return xml;
	}
	
//	
//	public String createComArtifact(HttpServletRequest request, HttpServletResponse response) throws ParserConfigurationException, SAXException, IOException
//	{
//		String xml="";
//		try {
//		
//  	    
//  		HttpSession session = request.getSession();
//  		
//    	User userSession = (User) session.getAttribute("use");
//    	//userSession.setID(userID);
//    	
//    	common.Utility.log.debug(userSession.getID());
//    	common.Utility.log.debug(userSession.getRDFID());
// 	    
//    	//JSONObject res = (JSONObject) JSONSerializer.toJSON(request.getParameter("resource"));
//    	
//    	String json = (String) request.getParameter("comids");
//    	//Use the JSON-Java binding library to create a JSON object in Java JSONObject jsonObject = null;
//    	try {
//    	JSONObject commObject = (JSONObject) JSONSerializer.toJSON(json);
//    	
//    	common.Utility.log.debug("JSON Object"+commObject.toString());
//    	
//    	JSONArray comids= commObject.getJSONArray("comIDs");
//    	JSONObject msgids =(JSONObject) comids.get(0);
//    	JSONArray msids =msgids.getJSONArray("msgIDs");
//    	//JSONArray blogids =msgids.getJSONArray("blogIDs");
//    	
//    	
//
//    	
//    	common.Utility.log.debug(msids.get(0));
//    	
//    	JSONArray jsonmsgs=new JSONArray();	
//    	for (int i=0;i<msids.size();i++){
//    		
//    		JSONObject mid=msids.getJSONObject(i);
//    		
//     		common.Utility.log.debug(mid.getInt("msgID"));
//     		Message msg=(Message) userSession.getMessage(mid.getInt("msgID")).get(0);
//     		common.Utility.log.debug("msg id "+msg.getID());
//     		JSONObject jsonmsg=new JSONObject();
//     		jsonmsg.put("MessageID", msg.getID());
//     		jsonmsg.put("Sender", msg.getSender());
//     		jsonmsg.put("SentDate", msg.getSent().toString());
//     		jsonmsg.put("Content", msg.getContent().replaceAll("\r\n", ""));
//     		//jsonmsg.put("Recipients", msg.getRecipients());
//     		jsonmsgs.add(jsonmsg);
//     		
//    	}
//    	common.Utility.log.debug("JSON String...");
//    	common.Utility.log.debug(jsonmsgs.toString());
//    	
//    	String jsonData = jsonmsgs.toString();
//    			//"[{'MessageID':277,'Sender':185,'SentDate':'2011-11-08','Content':'Hi Kapila Sending you a real time message from afar for your SICSA demo! Best wishes Kate'},{'MessageID':274,'Sender':196,'SentDate':'2011-11-08','Content':'test'},{'MessageID':276,'Sender':185,'SentDate':'2011-11-08','Content':'OK!'},{'MessageID':275,'Sender':185,'SentDate':'2011-11-08','Content':'OK!'}]";  
//        XMLSerializer serializer = new XMLSerializer(); 
//        JSON jsonstr = JSONSerializer.toJSON( jsonData ); 
//        serializer.setRootName("CommArtifact");
//        serializer.setTypeHintsEnabled(false);
//         xml = serializer.write( jsonstr );  
//        common.Utility.log.debug(xml);   
//    	
////        XMLSerializer xmlSerializer = new XMLSerializer(); 
////        JSON json2 = xmlSerializer.read( xml );  
////        common.Utility.log.debug( json2.toString(2) );
//    	
//        	
//    	}
//    	catch(Exception pe) { 
//    		common.Utility.log.debug(pe.getMessage());
//    	}
//    	
//    	
//    	JSONObject x =new JSONObject();
//    	
//    
//    	
//    	
//    	
//
//		
//		
//		}catch (Exception ex){
//			common.Utility.log.debug(ex.getMessage());
//			
//		}
//		return xml;
//			//response.sendRedirect(outputURL);
//	}
//	
//	
	
	public void createSiocPost(String id, String type){
		
		String sioc    = "http://rdfs.org/sioc/ns#";	
		String rdf    = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";	
		String dcterms="http://purl.org/dc/terms#";
		String ourspaces="http://ourspaces.org#";
		Model model = ModelFactory.createDefaultModel();
		
		 
		Resource post = model.createResource(sioc+"post");
		try{
			
			
			if (type.equals("blog")){
				Blog b=new Blog();
				BlogBean   	blogpost =(BlogBean)  b.getBlogPost(id);
						
				
				Property about = model.createProperty(rdf,"about");
				post.addProperty(about,ourspaces+blogpost.getRdfBlogID());
				Property title = model.createProperty(dcterms,"title");
				post.addProperty(title, blogpost.getTitle());
				//writer.write(model,System.out,"http://example.org/");
				// Statement statement = model.createStatement(post,title,blogpost.getTitle());
				// model.add(statement);
				 //writer.write(model, System.out,"http://example.org/") ;
			     model.write(System.out);
			     common.Utility.log.debug(model.size());
				
			}
			
			
			
			
		}catch (Exception ex){
			
			
			
		}
		
		//return post;
		
	}
	public String removeSpaces(String s) {
		  StringTokenizer st = new StringTokenizer(s," ",false);
		  String t="";
		  while (st.hasMoreElements()) t += st.nextElement();
		  return t;
		}

		
  public String getNamespace(String res)
  {
  	String myres = res;

	    if (myres.indexOf('#') != -1) {
	    	myres = myres.substring(0,myres.indexOf('#')+1);
	    }

	    return myres;
  }

  public String getLabel(String res)
  {
  	String myres = res;

  	if (myres.indexOf('#') != -1) {
  		myres = myres.substring(myres.indexOf('#')+1,myres.length());
  	}

  	return myres;
  }


  public boolean isResource(String res)
  {
  	String myres = res;

  	if (res.indexOf('#') != -1) {
  		return true;
  	}

  	return false;
  }
  public void doSomething(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   try{
	  String  items ="messages";
	    common.Utility.log.debug(items);
	    request.setAttribute("items", items);
	    request.getRequestDispatcher("/ourspaces/communications/commspacs.jsp").forward(request, response);
	   }catch (Exception ex){
		   common.Utility.log.debug(ex.getMessage());
	   }
	}
}
