package common;

import java.io.*;
import java.sql.ResultSet;
import java.sql.Statement;
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

/**
 * Servlet implementation class ResourceUploadServlet
 */
public class ResourceBrowseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public String fileNameToUpload;
	public File fileResource;

	private static void copyfile(String srFile, String dtFile){
		try{
			File f1 = new File(srFile);
			File f2 = new File(dtFile);
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

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ResourceBrowseServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		// the action element that we'll check for
		String cmd;

		cmd = request.getParameter("action");
		if (cmd != null) 
		{
			if (cmd.equals("replace")) 
			{

				String subject = java.net.URLDecoder.decode(request.getParameter("subject"));
				String predicate = java.net.URLDecoder.decode(request.getParameter("predicate"));
				String oldobject = java.net.URLDecoder.decode(request.getParameter("oldobject"));
				String newobject = java.net.URLDecoder.decode(request.getParameter("newobject"));

				RDFi rdfi = new RDFi(); 

				try {
					common.Utility.log.debug("S:"+subject+"P:"+predicate+"OO:"+oldobject+"NO:"+newobject);
					rdfi.replacePropertyValue(subject, predicate, oldobject, newobject);
				} catch (RepositoryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedQueryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (QueryEvaluationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				out = response.getWriter();       			
				out.println("<message>DONE!</message>");
			}
			

			if (cmd.equals("delete")) 
			{

				String subject = java.net.URLDecoder.decode(request.getParameter("subject"));
				String predicate = java.net.URLDecoder.decode(request.getParameter("predicate"));
				String object = java.net.URLDecoder.decode(request.getParameter("object"));

				RDFi rdfi = new RDFi(); 

				try {
					rdfi.deleteProperty(subject, predicate, object);
				} catch (RepositoryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedQueryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (QueryEvaluationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				out = response.getWriter();       			
				out.println("<message>DONE!</message>");
			}
			
			out.flush();
		}
		else
		{
			out.println("Server error.");
		}


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		// the action element that we'll check for
		String cmd;

		cmd = request.getParameter("action");
		if (cmd != null) 
		{
			if (cmd.equals("edit")) 
			{
				try {
					edit(request, response);
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			out.flush();
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
	//public void createRDF(HttpServletRequest request, HttpServletResponse response) throws ParserConfigurationException, SAXException, IOException
	public void edit(HttpServletRequest request, HttpServletResponse response) throws ParserConfigurationException, SAXException, IOException
	{

		common.Utility.log.debug("----------- Resource Browse servlet ----------------");
		boolean privateRes = false;

		String outputURL = "myhome.jsp";

		try {
			PrintWriter out = null;
			String RDFType = "";
			Hashtable formElements = new Hashtable();


			String subject = java.net.URLDecoder.decode(request.getParameter("subject"));

			RDFi rdfi = new RDFi();




			String file = new String("");

			HttpSession session = request.getSession();
			User userSession = (User) session.getAttribute("use");

			Enumeration en = request.getParameterNames();

			while (en.hasMoreElements()) {
				String par = (String)en.nextElement();
				common.Utility.log.debug("----------- Resource Browse servlet -- "+ par + " value : "+ request.getParameter(par));

				if (!par.equals("RDFType") && !par.equals("action") && !par.equals("subject")  && !par.equals("Keywords")) {
					rdfi.addPropertyValue(subject, par, request.getParameter(par));
					common.Utility.log.debug("----------- Resource Browse servlet ADDING:-- SUB: " +subject+ " PRE: "+ par + " OBJ: "+ request.getParameter(par));
				}


			}



		} catch (Exception e) {
			common.Utility.log.debug(e);
			e.printStackTrace();
		};

		response.sendRedirect(outputURL);
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


}
