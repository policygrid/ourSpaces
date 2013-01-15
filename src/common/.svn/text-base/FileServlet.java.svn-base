package common;
import java.io.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

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
import org.zkoss.util.logging.Log;

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;
import org.policygrid.ontologies.*;

/**
 * Servlet implementation class ResourceUploadServlet
 */
public class FileServlet extends HttpServlet {
	public static boolean bcsrch(final char[] chars, final char c) {
	    final int len = chars.length;
	    int base = 0;
	    int last = len - 1; /* Last element in table */
	    int p;

	    while (last >= base) {
	        p = base + ((last - base) >> 1);

	        if (c == chars[p])
	            return true; /* Key found */
	        else if (c < chars[p])
	            last = p - 1;
	        else
	            base = p + 1;
	    }

	    return false; /* Key not found */
	}

	public static String rfc5987_encode(final String s) throws UnsupportedEncodingException {
	    final byte[] s_bytes = s.getBytes("UTF-8");
	    final int len = s_bytes.length;
	    final StringBuilder sb = new StringBuilder(len << 1);
	    final char[] digits = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	    final char[] attr_char = {'!','#','$','&','\'','+','-','.','0','1','2','3','4','5','6','7','8','9',           'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','^','_',                       'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','|', '~'};
	    for (int i = 0; i < len; ++i) {
	        final byte b = s_bytes[i];
	        if (bcsrch(attr_char, (char) b))
	            sb.append((char) b);
	        else {
	            final char[] encoded = {'%', 0, 0};
	            encoded[1] = digits[0x0f & (b >>> 4)];
	            encoded[2] = digits[b & 0x0f];
	            sb.append(encoded);
	        }
	    }

	    return sb.toString();
	}

		/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
    	User userSession = (User) session.getAttribute("use");
    	
    	
    	
    	//TODO: check permission database 
		
		try {
      
        String fileID = request.getPathInfo().substring(1);
        
        common.Utility.log.debug("The full file ID is: "+fileID);
        
        String contentPath = "";
               
        if (fileID.contains("/")) {
        	contentPath = fileID.substring(fileID.indexOf("/")+1);
        	fileID = fileID.substring(0,fileID.indexOf("/"));
        }
        
        common.Utility.log.debug("The file ID is: "+fileID);
        common.Utility.log.debug("The Content path is: "+contentPath);
        //logging
        common.Utility.log.debug("The file ID is DEBUG: "+fileID);   
        
        String fileName = "";
        String mime = "";
        String publicFileID = "NONE";
        String realFileID = "";
        
        //common.Utility.log.debug("File ID: "+fileID);
        
        //Read the database
        Connections con = new Connections();
        con.connect();
        
        Statement st = con.getCon().createStatement();
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select fileID, fileName, mime, publicFileID from files where fileID=\'");
		qry.append(fileID);
		qry.append("\' OR publicFileID=\'"+fileID+"\'");
		ResultSet rs = st.executeQuery(qry.toString());
		
		while(rs.next())
		{  
		   //Locate the file 
		   fileName = rs.getString("fileName");	   
		   publicFileID = rs.getString("publicFileID");	 
		   //get the MIME type from the DB
		   mime = rs.getString("mime");
		   realFileID = rs.getString("fileID");	   
		}
		
		//common.Utility.log.debug(fileName);
		//common.Utility.log.debug(mime);
		
    
		
		
		if (((userSession == null) || (userSession.getID() == 0)) && (!fileID.equals(publicFileID)))
    		response.sendRedirect(response.encodeRedirectURL("/ourspaces/error.jsp"));
		
		
		if  (fileID.equals(publicFileID) && publicFileID != null && !publicFileID.equals("")) {
		 String sql = "DELETE FROM files WHERE publicFileID='"+publicFileID+"'";
		 st.executeUpdate(sql);
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
        //set the response MIME type
	
        
        //Get the file
        
        //Stream the file
        
        //response.sendRedirect("www.google.com");
				
		InputStream fis = null;
	    OutputStream os = null;
		
	    response.setContentType(mime);
	    
		if (!contentPath.equals("")) {
			
			 ZipFile zipFile = new ZipFile(Configuration.getParaemter("file","dataFolder")+fileID);
			 ZipEntry entry = zipFile.getEntry(contentPath);
			 fis = zipFile.getInputStream(entry);
			 String fnam = contentPath.substring(contentPath.lastIndexOf("/")+1);
			 response.addHeader("Content-Disposition", "attachment; filename=\"" + rfc5987_encode(fnam)+"\";filename*=UTF-8\'\'" +rfc5987_encode(fnam));
			 
		} else {
			Utility.log.debug("The real fine id is:"+realFileID);
			File myFile = new File(Configuration.getParaemter("file","dataFolder")+realFileID);
			Utility.log.debug("The path:"+myFile.getAbsolutePath());
				
			fis = new FileInputStream(myFile);
			response.addHeader("Content-Disposition", "attachment; filename=\"" + rfc5987_encode(fileName)+"\";filename*=UTF-8\'\'" +rfc5987_encode(fileName));
		}
	
	
		
		
		int read = 0;
	    byte[] bytes = new byte[1024];
		      
	//First we load the file in our InputStream
	      try {
		         os = response.getOutputStream();

		     	//While there are still bytes in the file, read them and write them to our OutputStream
		     	         while((read = fis.read(bytes)) != -1){
		     	            os.write(bytes,0,read);
		     	         }

		     	//Clean resources
		     	         os.flush();
		     	         os.close();
		     	         
		     	         
		} catch (Exception e) {
			// TODO: handle exception
		}
	         
	        

		} catch (Exception e) {e.printStackTrace();}
	}


}
