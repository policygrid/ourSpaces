package common;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.openrdf.OpenRDFException;

/**
 * Login Class designed to check if users have valid accounts
 * within ourSpaces and if not, add them during registration.
 * 
 * @author Richard Reid
 * @version 1.0
 */

public class Login implements Serializable 
{
	
	String user;
	Connections con = new Connections();
	int i = 0;
	
	/**
	 * @return String username
	 */
	public String getUsername()
	{
		return this.user;
	}
	
	/**
	 * Checks if a username and password combination is correct.  An integer will be
	 * returned if a match is found (the integer being the userid).  A 0 will be 
	 * returned if the username or password (or both) is incorrect.
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws NoSuchAlgorithmException 
	 */
	public int checkDetails(String username, String password) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NoSuchAlgorithmException 
	{
		
		con.connect();
		Statement st = con.getCon().createStatement();
		
		String newPass = PersonCreator.encrypt(password);
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select userid, terms_and_conditions from user where confirmed=true AND email=\'");
		qry.append(username);
		qry.append("\'");
		qry.append("and password=\'");
		qry.append(newPass);
		qry.append("\'");
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		while(rs.next())
		{
			i = rs.getInt("userid");
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return i;
	
	}
	
	public boolean checkTC(String username) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NoSuchAlgorithmException 
	{
		boolean tc = false;
		con.connect();
		Statement st = con.getCon().createStatement();
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select userid, terms_and_conditions from user where email=\'");
		qry.append(username);
		qry.append("\'");
		

		
		ResultSet rs = st.executeQuery(qry.toString());
		
		while(rs.next())
		{
			tc = rs.getBoolean("terms_and_conditions");
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return tc;
	
	}
	
	
	/**
	 * returns the RDF ID of a user, stored in the user database table.
	 * @param username
	 * @param password
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String getRDFID(int userid) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
	{
		
		con.connect();
		Statement st = con.getCon().createStatement();
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select rdf from user where userid=");
		qry.append(userid);
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		String id = "";
		
		while(rs.next())
		{
			id = rs.getString("rdf");
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		return id;
	
	}
	
	/**
	 * Returns the userID of a user based on their email. a userid of 0 indicates the email has
	 * not been registered.
	 * @param email
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public int checkEmail(String email) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		con.connect();
		Statement st = con.getCon().createStatement();
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select userid from user where email=\'");
		qry.append(email);
		qry.append("\'");
		
		ResultSet rs = st.executeQuery(qry.toString());
		
		String id = "";
		int userid = 0;
		
		while(rs.next())
		{
			id = rs.getString("userid");
		}
		
		rs.close();
		st.close();
		con.disconnect();
		
		if(id.equals("") || id == null)
		{
			return userid;
		}
		else
		{
			userid = Integer.parseInt(id);
			return userid;
		}
	}
	
}

