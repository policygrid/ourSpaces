package common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;
import java.util.Vector;
import java.util.zip.*;

import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;

import org.openrdf.OpenRDFException;
import org.openrdf.query.GraphQuery;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.repository.RepositoryException;
import org.policygrid.ontologies.FOAF;
import org.policygrid.ontologies.OurSpacesVRE;
import org.policygrid.ontologies.SIOC;
import org.xml.sax.SAXException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;


/**
 * 
 * @author Edoardo Pignotti
 * @version 1.0
 * 
 * User JavaBean Class
 * 
 * All requests from the servlet and JSP pages related to generating
 * user information is initialised in this class.
 *
 */

public class FileUtils
{
	
	Connections con = new Connections();
	
	public FileUtils()
	{
		super();
	}
	
	/**
	 * Returns the username of the user based on their userID
	 * @param id - the id of the user
	 * @return username
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList getFoldersByContainer(String containerID) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
	{
		// Database Connection
		con.connect();
		Statement st = con.getCon().createStatement();
		
		
		ArrayList res = new ArrayList();
		// SQL Query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select path from files where containerID=\'");
		qry.append(containerID);
		qry.append("\' group by path order by path");
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		
		while(rs.next()){
			res.add(rs.getString("path"));
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return res;	
	}
	
	
	public String getFileIDFromRDF(String resourceID) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
	{
		// Database Connection
		con.connect();
		Statement st = con.getCon().createStatement();
		
		// SQL Query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select fileID from files where resourceID=\""+resourceID+"\"");
			
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		String res = "";
		while(rs.next()){
			res = rs.getString("fileID");
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return res;	
	}
	
	
	public ArrayList getFoldersByUserID(int userID) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
	{
		// Database Connection
		con.connect();
		Statement st = con.getCon().createStatement();
		
		
		ArrayList res = new ArrayList();
		// SQL Query
		StringBuffer qry = new StringBuffer(2024);
	    qry.append("SELECT  DISTINCT toD.path AS path ");
	    qry.append("FROM ourspaces2.files AS fil LEFT JOIN ourspaces2.files AS toD "); 
		qry.append("    ON toD.containerID = fil.containerID " );
		qry.append("WHERE fil.userid = "+userID);
		qry.append(" ORDER BY path ");
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		
		while(rs.next()){
			res.add(rs.getString("path"));
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return res;	
	}
	
	
	
	
	public Vector listZipFile(String fileID) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
	{
		// Database Connection
		con.connect();
		Statement st = con.getCon().createStatement();
		
		Vector res = null;
		boolean isZip = false;
		
		// SQL Query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select fileName from files where (publicFileID = '' OR publicFileID IS NULL) AND fileID=\'");
		qry.append(fileID);
		qry.append("\'");
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		
		while(rs.next()){
			 String name = rs.getString("fileName");
			 if (name.endsWith(".zip")) isZip = true;
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		if (!isZip) return null;		
		
		res = new Vector();
		try {
            ZipFile zipFile = new ZipFile(Configuration.getParaemter("file","dataFolder")+fileID);
            
            Enumeration zipEntries = zipFile.entries();
            
            while (zipEntries.hasMoreElements()) {
                
                //Process the name, here we just print it out
              String name =  ((ZipEntry)zipEntries.nextElement()).getName();
              if (name.contains("/.") || (name.startsWith("__MACOSX"))) {
              
              } else {
              res.add(name);
              }
                
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		
		
		return res;	
	}
	
	
	public boolean isZipFile(String fileID) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
	{
		// Database Connection
		con.connect();
		Statement st = con.getCon().createStatement();
		
		
		boolean isZip = false;
		
		// SQL Query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select fileName from files where (publicFileID = '' OR publicFileID IS NULL) AND fileID=\'");
		qry.append(fileID);
		qry.append("\'");
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		
		while(rs.next()){
			 String name = rs.getString("fileName");
			 if (name.endsWith(".zip")) isZip = true;
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return isZip;	
	}
	
	public String getFilePath(String fileID) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
	{
		// Database Connection
		con.connect();
		Statement st = con.getCon().createStatement();
		
		String path = "";
		
		
		// SQL Query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select path from files where (publicFileID = '' OR publicFileID IS NULL) AND fileID=\'");
		qry.append(fileID);
		qry.append("\'");
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		
		while(rs.next()){
			path = rs.getString("path");
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return path;	
	}
	
	
	public String getContainerIDofFile(String fileID) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
	{
		// Database Connection
		con.connect();
		Statement st = con.getCon().createStatement();
		
		String containerID = "";
		
		
		// SQL Query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select containerID from files where (publicFileID = '' OR publicFileID IS NULL) AND fileID=\'");
		qry.append(fileID);
		qry.append("\'");
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		
		while(rs.next()){
			containerID = rs.getString("containerID");
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		if (containerID == null) containerID = "";
		
		return containerID;	
	}
	
	public String getContainerIDofFolder(int userID, String path) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
	{
		// Database Connection
		con.connect();
		Statement st = con.getCon().createStatement();
		
		String containerID = "";
		
		
		// SQL Query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select containerID from files where (publicFileID = '' OR publicFileID IS NULL) AND userid = "+userID+" AND path=\'");
		qry.append(path);
		qry.append("\'");
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		
		while(rs.next()){
			containerID = rs.getString("containerID");
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		if (containerID == null) containerID = "";
		
		return containerID;	
	}
	
	public String getFileName(String fileID) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
	{
		// Database Connection
		con.connect();
		Statement st = con.getCon().createStatement();
		
		String fileName = "";
		
		
		// SQL Query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select fileName from files where (publicFileID = '' OR publicFileID IS NULL) AND fileID=\'");
		qry.append(fileID);
		qry.append("\'");
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		
		while(rs.next()){
			fileName = rs.getString("fileName");
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return fileName;	
	}
	
	public void moveFilePath(String fileID, String path) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
	{
		// Database Connection
		con.connect();
		Statement st = con.getCon().createStatement();
	
		
		
		// SQL Query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("update files set path = '"+path+"' where (publicFileID = '' OR publicFileID IS NULL) AND fileID=\'");
		qry.append(fileID);
		qry.append("\'");
		
		st.executeUpdate(qry.toString());
		
		
	
		st.close();
		con.disconnect();
	}
	
	public void removeFile(String fileID) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
	{
		// Database Connection
		con.connect();
		Statement st = con.getCon().createStatement();
	
		// SQL Query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("delete from files where fileID=\'");
		qry.append(fileID);
		qry.append("\'");
		
		st.executeUpdate(qry.toString());
		
		
	
		st.close();
		con.disconnect();
	}
	
	public void newFolder(String projectID, String path) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
	{
		// Database Connection
		con.connect();
		Statement st = con.getCon().createStatement();
	
		
		
		// SQL Query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("insert into files (containerID, path) values ( '"+projectID+"','"+path+"') ");
		
		st.executeUpdate(qry.toString());
		
		
	
		st.close();
		con.disconnect();
	}
	
	public void deleteFolder(String projectID, String path) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
	{
		// Database Connection
		con.connect();
		Statement st = con.getCon().createStatement();
	
		
		
		// SQL Query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("delete from files where fileID IS NULL AND containerID = '"+projectID+"' AND path = '"+path+"'");
		
		st.executeUpdate(qry.toString());
		
		
	
		st.close();
		con.disconnect();
	}
	
}
