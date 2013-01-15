package lexicon.builder;


import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.openrdf.OpenRDFException;
import org.policygrid.ontologies.ProvenanceGeneric;
import org.policygrid.ontologies.OurSpacesVRE;
import org.xml.sax.SAXException;


import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import common.Configuration;
import common.Message;
import common.Project;


import java.util.*;
import java.text.*;

public class PLSServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
        // the action element that we'll check for
		
	
       
        	   try {
        		    String type="";
        			String word="";
        			String foaf="";
        			String jsn="";
        			String projectid="";
        			String projectmembers="";
        			String vsfoaf="";
        			String personList="";
        			String projectList="";
        		   if (request.getParameter("type") !=null)
        		   		 type = request.getParameter("type").toString().toLowerCase();
        		   if (request.getParameter("word") !=null)
        		   	     word = request.getParameter("word").toString().toLowerCase();
        		   if (request.getParameter("jsn") !=null)
      		   	     	jsn = request.getParameter("jsn").toString();
        		   if (request.getParameter("projectmembers") !=null)
        			   projectmembers = request.getParameter("projectmembers").toString();
        		   if (request.getParameter("projectid") !=null)
        			   projectid = request.getParameter("projectid").toString();
        		   if (request.getParameter("withuser") !=null)
        			   vsfoaf = request.getParameter("vsfoaf").toString();
        		   if (request.getParameter("foaf") !=null)
        			   foaf = request.getParameter("foaf").toString();
        		   if (request.getParameter("personList") !=null)
        			   personList = request.getParameter("personList").toString();
        		   if (request.getParameter("projectList") !=null)
        			   projectList = request.getParameter("projectList").toString();
        		   System.out.println(projectmembers.trim());
        		   
        		   common.Project prj=new common.Project();
   				
        		   HttpSession session = request.getSession(true);
        		   common.User user=new common.User();
        		   	if ((session.getAttribute( "use" )!=null)&&(request.getParameter("foaf") ==null)){
        		   		user = (common.User) session.getAttribute("use");
        		   		foaf=user.getFOAFID().split("#")[1];
            		   	System.out.println(foaf);
            		   	
            		   	
            		   	
        		   	}
        		   		
//        		   int currentuser=user.getID();
//        		   	System.out.println(user.getFOAFID());
        		   
        		   
        		   	
        		   	//get  related terms from the lexicon for a given word
        		   	if (type.equals("schedule")){
        		   		String path=Configuration.getParaemter("pls","scheduleFolder");
        				
        		   		try {
        						File file =new File(path+foaf);
        					 
        		   			
        		   				
        		   				file.createNewFile();
        		   					
        		    			
        		    			FileWriter fileWritter = new FileWriter(path+foaf,false);
        		    			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
        		    			
        		    		//	{"s1":{"word":"information","source":"person","id":"2af78022-8e4e-4c5b-95d5-bef4fe7299d9"},"s2":{"word":"provenance","source":"","id":"aadb9597-6392-42e0-9815-489cbe80cd19"}}
        		    			
        		    			
        		    			String outstr="";
        		    			String persons="'persons':[]";
        		    			String projects="'projects':[]";
        		    			String status="";
        		    			if (projectList.equals("'") && personList.equals("'")){
        		    				status="'status':'empty'";
        		    			}
        		    			if (!projectList.equals("'")){
        		    				projects="'projects':["+projectList.substring(0,projectList.length()-2)+"]";
        		    				status="'status':'ready'";
        		    			}
        		    			if (!personList.equals("'")){
        		    				persons="'persons':["+personList.substring(0,personList.length()-2)+"]";
        		    				status="'status':'ready'";
        		    				
        		    			}        		    			
        		    			
        		    			outstr="{id:'"+foaf+"',"+status+"," +projects+","+persons+"}"+System.getProperty("line.separator");
        		    			
        		    				
        		    	    	bufferWritter.write(outstr);
        		    	    	
        		    	    	
        		    	    	System.out.println("Done");
        		    	    	bufferWritter.close();
        		    	    	
        		    	    	
        		    	    	
        		    	    	
        		    	    	
        		    	    	
        					 } catch (IOException e){
        					       e.printStackTrace();
        					 }
        		   		
        		   		
        		   	}else if (type.equals("notify")){	
        		   		String path=Configuration.getParaemter("pls","scheduleFolder");
        		   		File file = new File(path+foaf);
        				JSONObject json=new JSONObject(); 
        				JSONObject jsonout=new JSONObject(); 
        			    try {
        			    	
        			    	//read the schedule
        			    	String newStr="";
        			    	
        			    	Scanner scanner = new Scanner(new FileReader(file));
        			    	JSONArray jsnarr=new JSONArray();
        			    	while (scanner.hasNext()){
        			    		String htmlcontent="Following Personal lexicon(s) has been created.Please click on each link and validate individually<br> " ;
        			    	
        			    		String links="";		
                		   		
        			    		String line=scanner.nextLine();
        			    		System.out.println("JSON: "+line);
        						json = (JSONObject) JSONSerializer.toJSON( line );   
        						
        			    		String status=json.getString("status");
        			    		if (status.equals("constructed")){
        			    		System.out.println(status);
        			    		JSONArray jsnprjs=json.getJSONArray("projects");
        			    		
        			    		if (!jsnprjs.isEmpty()){
        			    			for (Object o:jsnprjs){
        			    				System.out.println(o.toString());
        			    				
        			    				System.out.println("title:"+prj.getTitle("http://www.policygrid.org/project.owl#"+o.toString()));
        			    				
        			    				links= links+"<br/><a href="+"/ourspaces/Lexicon/validateLexicon.jsp?id="+o.toString()+"&type=project >"+prj.getTitle("http://www.policygrid.org/project.owl#"+o.toString())+"</a>";
        			    				newStr=foaf+"VS"+o.toString();
        			    				jsnarr.add(newStr);
        			    				System.out.println(links);
        			    			}
        			    		}
        			    		JSONArray jsnprsns=json.getJSONArray("persons");
        			    		System.out.println("JSON ARRAY: "+jsnprsns.toString());
        			    		if (!jsnprsns.isEmpty()){
        			    			for (Object o:jsnprsns){
        			    				
        			    				links= links+"<br/><a href="+"/ourspaces/Lexicon/validateLexicon.jsp?id="+o.toString()+"type=person >"+user.getName(user.getUserIDFromFOAF("http://xmlns.com/foaf/0.1/#"+o.toString()))+"</a>";
                			    		
        			    				newStr=foaf+"VS"+o.toString();
        			    				jsnarr.add(newStr);
        			    				System.out.println(links);
        			    			}
        			    			}
        			    		
        			    		//sending the email
        			    		String[] recs=new String[1];
                		   		recs[0]=user.getFOAFID();
                		   		System.out.println("Send notification");
                		   		System.out.println(htmlcontent+links);
                		   		String sent = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
                		   		user.sendMessage(recs, 248,-1, "PLS Notification",htmlcontent+links,sent);
        			    		}
        			    		
        			    		
        			    	}
        			    	scanner.close();
        			    	json.put("status", "emailsent");
        			    	try{
        						BufferedWriter bout = new BufferedWriter(new FileWriter(path+foaf));
        						
        						bout.write(json.toString());
        						bout.flush();
        						bout.close();
        			    	}
        			    	catch (Exception ex){
        			    		System.out.println(ex.getMessage());
        			    	}
        			    	
        			    	
        			    	
        			    	
        			    	
        			    	jsonout.put("lexicons", jsnarr);
        			    	response.setContentType("application/json");
            		   		
            		   		out.print(jsonout);
            		   		
            		   		
            		   		
            		   		
            		   		out.flush();
        			    }catch (IOException e){
 					       e.printStackTrace();
 					 }
        		   	
        		   	}else if (type.equals("rt")){
        		   		long timestart=System.currentTimeMillis();
        		   		PersonalLexicon pl=new PersonalLexicon();
        		   		pl.init(LexDefaults.getInputStream(),foaf);
        		   		pl.loadDict(foaf);
        		   
        		   
        		   		JSONArray js= pl.mp.get(word.toLowerCase());
        		
        		    
        		   	
						
						String newStr="";
						for (int i=0;i<js.size();i++){
							JSONObject jsnobj=(JSONObject) js.get(i);
							String term=jsnobj.getString("word");  //getObjectName(myObject[i].source,myObject[i].id);
							String source=jsnobj.getString("source");
							String id=jsnobj.getString("id");
							newStr=newStr+"<div>Related terms:</div><b><i>"+term+"</i></b> is used in similar context"+getIdentityPhrase(source,id)+"<br/>";
							
						}
        		   		
        		
        		   		System.out.println("similar terms resolved in: "+(timestart-System.currentTimeMillis()));
        		   		
        		   		response.setContentType("application/json");
        		   		
        		   		out.print(newStr);
        		   		out.flush();
        		    
        		   	}
        		   	else if (type.equals("list")){
        		   	
        		   		PersonalLexicon pl=new PersonalLexicon();
        		   		pl.init(LexDefaults.getInputStream(),foaf);
        		   		pl.loadDict(foaf);
        		   
        		   
        		   		String myString = pl.mp.keySet().toString();
        		   		System.out.println(myString);
        		   		
        		   		
        		   		
        		   		response.setContentType("text/xml");
        		
        		
        		   		out.print(myString);
        		   		out.flush();
        		   		
        		   	}	else if (type.equals("update")){
        		   	
        		   		PersonalLexicon pl=new PersonalLexicon();
        		   		
        		   		pl.updateLexicon(foaf, jsn);
        		   		out.print("success");
        		   		out.flush();
        		   	}else if (type.equals("personalcorpus")){
        		   	
        		   		PersonalCorpus pc=new PersonalCorpus(vsfoaf);
        		   		pc.run();
        		   		out.print("success");
        		   		out.flush();
        		   	}
        		   	
//        		   	else if (type.equals("projectcorpus")){
//        		   		System.out.println(projectid);
//        		   		ProjectCorpus bpc=new ProjectCorpus(projectid);
//        		   		bpc.run();
        		   	
//        		   		
//        		   		out.print("success");
//        		   		out.flush();
//        		   	}
//        		   	else if (type.equals("lex-userproject")){
//        		   		System.out.println(projectid);
//        		   		String root=Configuration.getParaemter("pls","parsedFolder");
//        				
//        		   		//String foaf1 ="ec272513-360a-467a-8f43-0e951d1243db";
//        		   		Vector<String> fvector1 =new Vector<String>();
//        		   		Vector<String> fvector2 =new Vector<String>();
//        		   		fvector1.add(root+foaf);
//        		   		
////        				Text2Mem tm=new Text2Mem(root,fvector,"");
////        				
////        				List <GR> auth1=tm.getModel();
////        				
////        				System.out.println("total personal "+auth1.size());
////        				System.out.println(auth1.size());
////        				
//        				
//        				
//        				ArrayList prjmembers= prj.getProjectMembers("http://www.policygrid.org/project.owl#"+projectid);
//        				System.out.println("Project "+projectid+" has "+prjmembers.size());
//        		       
//        				
//        				
//        		       	for (int i=0;i<prjmembers.size();i++){
//        		        	
//        		        	 common.MemberBean mmbr=(common.MemberBean ) prjmembers.get(i);
//        		        	 String foafid=mmbr.getFoafID().split("#")[1];
//        		        	
//        		        	
//        		        	 if (!foafid.equals(foaf)){
//        		        		 fvector2.add(root+foafid);
//        		        	 }
//        		        System.out.println();
//        		        	 
//        		        	 
//        		        	 
//        		        	 
//        		        	 
//        		       	}
//        		       	System.out.println("Vector 1");
//        		       	for (String s:fvector1)
//        		       		System.out.println(s);
//        		       	System.out.println("Vector 2");
//        		       	for (String s:fvector2)
//        		       		System.out.println(s);
//        		       			
//        		       		String outfile=Configuration.getParaemter("pls","lexsFolder")+foaf+"VS"+projectid;
//        		       			
//        		     
//                				
//        		       	(new Thread(new Analyser(fvector1,root,fvector2,root,outfile))).start();		
//
//                				
//                		
//                		
//                		out.print("success");
//                		out.flush();
//                		   		
//                		   		
//        		       			
//
//   				
//        				
//        		   	}	else if (type.equals("lex-useruser")){
//        		   		System.out.println(projectid);
//        		   		String root=Configuration.getParaemter("pls","parsedFolder");
//        				
//        		   		//String foaf1 ="ec272513-360a-467a-8f43-0e951d1243db";
//        		   		Vector<String> fvector1 =new Vector<String>();
//        		   		Vector<String> fvector2 =new Vector<String>();
//        		   		fvector1.add(root+foaf);
//        		   		
//     				
//        				
//        				System.out.println("With User "+withuser);
//        		       
//        				
//        				
//        		       	if (!withuser.equals(foaf)){
//        		        		 fvector2.add(root+withuser);
//        		        	 }
//        		        
//        		       	System.out.println("Vector 1");
//        		       	for (String s:fvector1)
//        		       		System.out.println(s);
//        		       	System.out.println("Vector 2");
//        		       	for (String s:fvector2)
//        		       		System.out.println(s);
//        		       			
//        		       		String outfile=Configuration.getParaemter("pls","lexsFolder")+foaf+"VS"+projectid;
//        		       			
//        		       //	Analyser an=new Analyser(fvector1,root,fvector2,root,outfile);
//                				
//        		       	(new Thread(new Analyser(fvector1,root,fvector2,root,outfile))).start();		
//
//                				
//        		       	
//                		
//                		out.print("success");
//                		out.flush();
//                		   		
//                		   		
//        		   	}
        			
        		   
        		
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
             
        	             
        	response.setContentType("text/xml");
     		response.setHeader("Cache-Control", "no-cache");
     	        	 
        	 out.flush();
        
	}
public static String[] getFiles(String dirname,FilenameFilter filter ){
		
		File dir = new File(dirname);

		String[] files = dir.list(filter);
		return files;
	}
	
	  
	  public static FilenameFilter txtfilter = new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith("");
		    }
		};
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
        // the action element that we'll check for
       

      
	}
	
	String getIdentityPhrase (String source, String id) throws OpenRDFException, IOException, ServletException {
		String phrase="";
		if (source.equals("project")){
			   String idl="http://www.policygrid.org/project.owl#"+id;	
			   System.out.println(id);
			   common.Project pj=new common.Project();
			   phrase="in the "+source+" <a href='/ourspaces/project.jsp?id="+id+"'>"+pj.getTitle(idl)+"</a>";
		}else if (source.equals("person")){
			
		}
		
		
		
		return phrase;
	}

}
