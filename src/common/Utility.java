package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.ServletException;

import org.apache.catalina.core.StandardService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openrdf.OpenRDFException;
import org.xml.sax.SAXException;

import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.ibm.icu.util.StringTokenizer;

public class Utility {

	public static Log log = LogFactory.getLog(Utility.class);
	
	/**
	 * Sort a list of resources alphabetically from their labels
	 * @param unsortedResources list of resources to sort
	 * @return array of sorted resources
	 */
	public static OntResource[] getSortedResources(Collection<OntResource> unsortedResources){
		OntResource[] sortedResources = (OntResource[]) unsortedResources.toArray(new OntResource[0]);
		Arrays.sort(sortedResources, new OntResourceLabelComparator());
		return sortedResources;
	}

	/**
	 * Returns the (full) current date in a word-based format for the ourSpaces
	 * header bar.
	 * 
	 * @return
	 */
	public static String getDate() {
		long time = System.currentTimeMillis();
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMMM yyyy");
		String fulldate = sdf.format(date).toString();
		return fulldate;		
	}

	public static String getDay() {
		long time = System.currentTimeMillis();
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		String fulldate = sdf.format(date).toString();
		return fulldate;
	}

	public static String getMonth() {
		long time = System.currentTimeMillis();
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		String fulldate = sdf.format(date).toString();
		return fulldate;
	}

	public static String getYear() {
		long time = System.currentTimeMillis();
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String fulldate = sdf.format(date).toString();
		return fulldate;
	}
	public static String splitString(String str){
		
		String res = "";
		
		for (int i = 0; i < str.length(); i++){
			
	
		
		 if (Character.isUpperCase(str.charAt(i)) && (i > 0)) {		 
			 res = res + " " + str.charAt(i);
		 } else 
			 
			 if (i == 0) {
				 res = res + Character.toUpperCase(str.charAt(i));
			 } else {
	           res = res + str.charAt(i);
			 }
		}
		
		return res;
		
	}
	
	/**
	 * Checks is a String is not null and not empty
	 * @param stringToCheck
	 * @return true is the String is not null and not empty
	 */
	public static boolean isNotNull(String stringToCheck){
		if (stringToCheck != null && !"".equals(stringToCheck)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String booleanToCheckbox(boolean test){
		if (test) return "checked=\"true\"";
		else return "";
	}
	
	
	private static void copyfile(File srFile, String dtFile){
	    try{
	      File f1 = srFile;
	      File f2 = new File(dtFile);
	      common.Utility.log.debug("Copying : "+srFile+" to "+dtFile);
	      InputStream in = new FileInputStream(f1);
	      
	      //For Append the file.
//	      OutputStream out = new FileOutputStream(f2,true);

	      //For Overwrite the file.
	      OutputStream out = new FileOutputStream(f2);

	      byte[] buf = new byte[1024];
	      int len;
	      while ((len = in.read(buf)) > 0){
	        out.write(buf, 0, len);
	      }
	      in.close();
	      out.close();
	      common.Utility.log.debug("File copied.");
	      f1.delete(); //delete the file after the copy
	    }
	    catch(FileNotFoundException ex){
	      common.Utility.log.debug(ex.getMessage() + " in the specified directory.");
	      //System.exit(0);
	    }
	    catch(IOException e){
	      common.Utility.log.debug(e.getMessage());      
	    }
	  }
	
	public static String getFileID(String filename ) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
	    Connections con = new Connections();
	    con.connect();
		Statement st = con.getCon().createStatement();
		
		
		//String fileID = UUID.randomUUID().toString();
		//String fineName = filename;
		//String userid = userSession.getRDFID();
		//String timestamp = ""+System.currentTimeMillis();
		//String mime = "application/octet-stream";
	
		String sql = "Select fileID From files where  fileName ='"+filename+"'";
		common.Utility.log.debug(sql);
		ResultSet rs = st.executeQuery(sql);

		String fileid = "";
		while (rs.next()) {
			fileid= rs.getString("fileID");
		}
		
		
		return fileid;
	}
	

	public static String storeFile(int userID, String filename, File file, String uniqueResourceID, String projectID, String path ) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
	
	    Connections con = new Connections();
	    con.connect();
		Statement st = con.getCon().createStatement();
		
		
		String fileID = UUID.randomUUID().toString();
		String fineName = filename;
		//String userid = userSession.getRDFID();
		String timestamp = ""+System.currentTimeMillis();
		String mime = "application/octet-stream";
	
		String sql = "INSERT INTO files (fileID, fileName, userid, timestamp, mime, publicFileID, resourceID, containerID, path ) VALUES('"+fileID+"','"+fineName+"', "+ userID+",'"+timestamp+"','"+mime+"','','"+uniqueResourceID+"','"+projectID+"','"+path+"')";
		st.executeUpdate(sql);
		
		st.close();
		con.disconnect();
		
		common.Utility.log.debug("===================================About to store the file :"+file);
		copyfile(file,Configuration.getParaemter("file","dataFolder")+fileID);			
		
		String uri = "http://www.ourspaces.net/file/"+fileID;
		
		return uri;
	}
	
	public static String readTextFile(String fileid) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ServletException, OpenRDFException{
		common.Utility.log.debug("In utility");	
		RDFi r=new RDFi();
		
		StringBuffer sb = new StringBuffer(1024);
		String filename=fileid;
		common.Utility.log.debug("file name= "+filename);
		try{
		BufferedReader reader = new BufferedReader(new FileReader(Configuration.getParaemter("file","dataFolder")+filename));
			
		char[] chars = new char[1024];
		while( (reader.read(chars)) > -1){
			sb.append(String.valueOf(chars));	
		}

		reader.close();
		}catch(Exception ex){
			common.Utility.log.debug(ex);
			
		}
		return sb.toString();
	}
	
	public static Vector<String> getKeywordsFromString(String st) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
	
		
        Vector<String> res = new Vector<String>();
        boolean check = false;
        
        Connections con = new Connections();
		con.connect();
		
		StringTokenizer str = new StringTokenizer(st,".,?,!,(,),:,_,<,>,%,@,$,^,&,*, ");
		
        while (str.hasMoreElements()) {
        	
        	String tt = (String) str.nextElement();
        	tt = tt.toLowerCase();
        
                //common.Utility.log.debug("Token: "+tt);
        
        
        

		String qry = "SELECT keyword FROM stoplist WHERE keyword LIKE ?";

		PreparedStatement pstmt = con.getCon().prepareStatement(qry);
		pstmt.setString(1, tt);

		check = false;
        		
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			check = true;
		}
		
        pstmt.close();
        
        if (!check) res.add(tt);
        	
        }
        
	
		con.disconnect();
        
	    return res;
		
	}
	
	public static String absoluteURLtoRelative(String url) {
	 
		 String ret = url;
		 
		 if (url.indexOf("www.ourspaces.net") > -1) {
			 ret = "/ourspaces" + url.substring(url.indexOf("www.ourspaces.net")+17);
		 }
	 
		 return ret;
	}
	/**
	 * Returns the link to the page with the details of given resource.
	 * @param type
	 * @param resourceId
	 * @return
	 */
	public static String getDetailPage(String type, String resourceId){
		if(type == null)
			return "";
		String href = "";
		User user = new User();
		OntologyHandler ontology;
		try {
			ontology = new OntologyHandler();
			if (type.equals("http://xmlns.com/foaf/0.1/Person")) {
				href = "/ourspaces/profile.jsp?id="+user.getUserIDFromFOAF(resourceId);
			}
			else if (type.equals("http://www.policygrid.org/provenance-impact.owl#Impact")) {
				href = "/ourspaces/impact.jsp?id="+Utility.getLocalName(resourceId);
			}
			else if (type.equals("http://www.policygrid.org/project.owl#Project")) {
				href = "/ourspaces/project.jsp?id="+Utility.getLocalName(resourceId);
			}
			else if (ontology.getSubclassListFull("all", "http://openprovenance.org/ontology#Node").contains(type) || type.equals("http://openprovenance.org/ontology#Artifact") ) {
				href = "/ourspaces/artifact_new.jsp?id="+Utility.getLocalName(resourceId);
			}
			else if (type.equals("http://rdfs.org/sioc/ns#Post")) {
				Blog b = new Blog();
				java.util.List<BlogBean> l = b.getBlogPostbyRdfId(resourceId);
				if(l.size() > 0){						
					href = "/ourspaces/projectblogposts.jsp?id="+Utility.getLocalName(l.get(0).getContainer());
				}
			}
			return href;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	public static String getImage(String type, String property){
		if(type == null && property == null)
			return "";
		OntologyHandler ontology;
		try {
			ontology = new OntologyHandler();
			OntProperty prop = null;
			if(property != null)
				prop = ontology.getProperty(Utility.getLocalName(property));
			if(type == null && prop != null)
				type = prop.getRange().getURI();
			
			String image = "";
			if(type.startsWith("http://www.policygrid.org/provenance-simulation.owl#"))
				return "/ourspaces/images/spaces/Models_small.png";
			if(type.equals("http://www.geonames.org/ontology#Feature"))
				return "/ourspaces/images/spaces/Maps_small.png";
			String literalType = ResourceUploadBean.getRangeType(type, "all");
			if("http://www.geonames.org/ontology#Feature".equals(type)){
				image = "/ourspaces/icons/maps.png";
			}
			if (literalType != null && literalType.equals("literal")) {
				if(type.equals("http://www.w3.org/2001/XMLSchema#int") || type.equals("http://www.w3.org/2001/XMLSchema#decimal"))
						image = "/ourspaces/icons/numerical.png";
				else
					image = "/ourspaces/icons/textfield.png";
			}
			else if (literalType != null && literalType.equals("date")) {
				image = "/ourspaces/icons/001_44.png";
			}
			else if (type.equals("http://xmlns.com/foaf/0.1/Person") 
					|| type.equals("http://openprovenance.org/ontology#Agent")
					|| type.equals("http://purl.org/net/opmv/ns#Agent")  ) {
				image = "/ourspaces/icons/001_55.png";
			}
			else if (type.equals("http://rdfs.org/sioc/ns#Post")) {
				image = "/ourspaces/icons/001_50.png";
			}
			else if (type.equals("http://www.policygrid.org/project.owl#Project")) {
				image = "/ourspaces/images/spaces/Projects_small.png";
			}
			else if (type.equals("http://www.policygrid.org/provenance-impact.owl#Impact")) {
				image = "/ourspaces/images/spaces/Impact_small.png";
			}
			else if (type.startsWith("http://www.policygrid.org/academic-disciplines#")){
				image = "/ourspaces/icons/001_34.png";
			}
			else if (ontology.getSubclassListFull("all", "http://openprovenance.org/ontology#Artifact").contains(type) 
					|| type.equals("http://openprovenance.org/ontology#Artifact")
					|| type.equals("http://purl.org/net/opmv/ns#Artifact") ) {
				image = "/ourspaces/icons/001_58.png";
			}
			else if (ontology.getSubclassListFull("all", "http://openprovenance.org/ontology#Process").contains(type) 
					|| type.equals("http://openprovenance.org/ontology#Process")
					|| type.equals("http://purl.org/net/opmv/ns#Process")){
				image = "/ourspaces/icons/process.png";
			} 
			else if (type.startsWith("http://www.policygrid.org/provenance-generic.owl#")){
				image = "/ourspaces/icons/001_34.png";
			}  
			else if (type.equals("http://www.w3.org/2002/07/owl#Thing")){
				image = "/ourspaces/icons/001_58.png";
			}
			else if (prop != null){
				if(prop.isObjectProperty())
					image = "/ourspaces/icons/001_58.png";
				else
					image = "/ourspaces/icons/textfield.png";
			}
			return image;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Returns the local name part of the uri.
	 * @param uri
	 * @return
	 */
	public static String getLocalName(String uri) {		 
		 if (uri.contains("#")) {
			 return uri.substring(uri.lastIndexOf("#")+1);
		 }
		 if (uri.contains("/")) {
			 return uri.substring(uri.lastIndexOf("/")+1);
		 }
		 return uri;
	}

	/**
	 * Returns the namespace part of the uri.
	 * @param uri
	 * @return
	 */
	public static String getNamespace(String uri) {	 
		 if (uri.contains("#")) {
			 return uri.substring(0,uri.lastIndexOf("#")-1);
		 }
		 if (uri.contains("/")) {
			 return uri.substring(0,uri.lastIndexOf("/")-1);
		 }
		 return uri;
	}
	

	
}


