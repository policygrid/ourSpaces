package common;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;

import org.openrdf.OpenRDFException;
import org.policygrid.ontologies.FOAF;
import org.xml.sax.SAXException;

/**
 * @author EP
 * @author RR
 * @version 1.0
 */
public class Logs {

	public static void addLog(int user, String page, String activity, String note) throws ServletException, IOException, OpenRDFException, ParserConfigurationException, SAXException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		Connections con = new Connections();
		con.connect();

		String qry = "INSERT INTO logs(userID,timestamp,page,activity,note) values (?,?,?,?,?)";

		PreparedStatement pstmt = con.getCon().prepareStatement(qry);
		pstmt.setInt(1, user);
		pstmt.setLong(2, System.currentTimeMillis());
		pstmt.setString(3, page);
		pstmt.setString(4, activity);
		pstmt.setString(5, note);

		int  rs = pstmt.executeUpdate();

		pstmt.close();
		con.disconnect();
		
	}
	
	public static void addLogRequest(int user, String remoteIP, String protocol, String parameters, String mainPage, String requestURL, String sid, String session) throws ServletException, IOException, OpenRDFException, ParserConfigurationException, SAXException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		Connections con = new Connections();
		con.connect();

		String qry = "INSERT INTO logRequests (timestamp,uid,remoteIP,protocol,parameters,mainPage, requestURL,sid,session) VALUES (?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = con.getCon().prepareStatement(qry);
		pstmt.setLong(1, System.currentTimeMillis());
		pstmt.setInt(2, user);
		pstmt.setString(3, remoteIP);
		pstmt.setString(4, protocol);
		pstmt.setString(5, parameters);
		pstmt.setString(6, mainPage);
		pstmt.setString(7, requestURL);
		pstmt.setString(8, sid);
		pstmt.setString(9, session);

		int  rs = pstmt.executeUpdate();

		pstmt.close();
		con.disconnect();
		
	}
	
	public static void addLogPolicy(String psid, String action, String policy, long time, String inferences) throws ServletException, IOException, OpenRDFException, ParserConfigurationException, SAXException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		Connections con = new Connections();
		con.connect();

		String qry = "INSERT INTO logPolicyReasoner (timestamp,psid,action,policy,time,inferences) VALUES (?,?,?,?,?,?)";
		PreparedStatement pstmt = con.getCon().prepareStatement(qry);
		pstmt.setLong(1, System.currentTimeMillis());
		pstmt.setString(2, psid);
		pstmt.setString(3, action);
		pstmt.setString(4, policy);
		pstmt.setLong(5, time);
		pstmt.setString(6, inferences);
		int  rs = pstmt.executeUpdate();

		pstmt.close();
		con.disconnect();
		
	}
	
	public static void addLogUpload(int uid, String sid, String resourceID, String resourceType, long time) throws ServletException, IOException, OpenRDFException, ParserConfigurationException, SAXException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		Connections con = new Connections();
		con.connect();

		String qry = "INSERT INTO logUploadForm (timestamp,uid,sid,resourceID,resourceType,time) VALUES (?,?,?,?,?,?)";
		PreparedStatement pstmt = con.getCon().prepareStatement(qry);
		pstmt.setLong(1, System.currentTimeMillis());
		pstmt.setInt(2, uid);
		pstmt.setString(3, sid);
		pstmt.setString(4, resourceID);
		pstmt.setString(5, resourceType);
		pstmt.setLong(6, time);
		int  rs = pstmt.executeUpdate();

		pstmt.close();
		con.disconnect();
		
	}
	

}
