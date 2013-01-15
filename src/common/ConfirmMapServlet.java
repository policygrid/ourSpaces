package common;

import java.io.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;
import java.io.File;


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
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;
import com.lowagie.text.List;

import org.policygrid.ontologies.*;


public class ConfirmMapServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	
	static final long serialVersionUID = 1L;
	   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
    Connections con = new Connections();
    
    public ConfirmMapServlet() 
	{
		super();
	} 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
    	User user = (User) session.getAttribute("use");
    	
    	String lat = request.getParameter("lat");
    	String lon = request.getParameter("lon");
    	String name = request.getParameter("name");
    	String locationID = "";
        
        try {
			locationID = createMapResource(lat, lon, name);
		} catch (Exception e) {
			e.printStackTrace();
		} 
        
        response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		
		out.println("<message>" + locationID + "</message>");

	
		out.flush();
		
	}

	public String createMapResource(String lat, String lon, String name) throws OpenRDFException, IOException
	{
		String locationID = "http://www.geonames.org/ontology#" + UUID.randomUUID().toString();
		
		Model model = ModelFactory.createDefaultModel();
		
		Resource feature = model.createResource(locationID);
		feature.addProperty(model.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#","type"), model.createResource("http://www.geonames.org/ontology#Feature"));
		feature.addProperty(model.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#", "lat"), lat);
		feature.addProperty(model.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#", "long"), lon);
		feature.addProperty(model.createProperty("http://www.geonames.org/ontology#", "name"), name);
			
		ByteArrayOutputStream outp = new ByteArrayOutputStream();		
		RDFi rdfi = new RDFi();
		model.write(outp);
		InputStream in = new ByteArrayInputStream(outp.toByteArray());
		rdfi.write(in);
		
		model.write(System.out);
		
		return locationID;
	}
}
