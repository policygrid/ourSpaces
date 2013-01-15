package org.policygrid.extraction;

import java.io.IOException;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import common.*;

public class LoadDocument {
	
	public static String document;
	public int id;
	
	public LoadDocument(int id)
	{
		this.id = id;
		try {
			getDocumentFromDatabase();
			extractXML();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally {
			//
		}
	}

	
	public void getDocumentFromDatabase() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		Connections con = new Connections();
		con.connect();
		Statement st = con.getCon().createStatement();
		String sql = "Select content from documents where id = " + id;
		ResultSet rs = st.executeQuery(sql);
		con.disconnect();
		
		while(rs.next())
		{
			document = rs.getString("content");
		}
		
	}
	
	public void extractXML() throws SAXException, IOException
	{
		DOMParser parser = new DOMParser();
		parser.parse(new InputSource(new StringReader(document)));
		Document doc = parser.getDocument();	
	}
	
	
	
}
