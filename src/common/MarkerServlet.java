package common;
import java.io.*;
import java.awt.*;
import java.awt.image.*;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.UUID;

import javax.imageio.ImageIO;
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
import org.policygrid.ontologies.*;

/**
 * Servlet implementation class ResourceUploadServlet
 */
public class MarkerServlet extends HttpServlet {
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		common.Utility.log.debug("Getting color :"+request.getPathInfo().substring(1));
			
		String col = request.getPathInfo().substring(1);
		col = col.substring(0,col.indexOf('.'));
		
		common.Utility.log.debug("Getting color :"+ col);
		
		int width = 10;
		int height = 10;

		BufferedImage buffer =
		    new BufferedImage(width,
		                      height,
		                      BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.createGraphics();

		//g.setColor(0x00FFFF);
		//g.fillRect(0,0,width,height);

		 int x = 5;
		 int y = 5;
		 int radius = 5;
		 g.setColor(Color.decode("#"+col));
		 g.fillOval(x - radius, y - radius, radius*2, radius*2);
		 


		 response.setContentType("image/png");
		 OutputStream os = response.getOutputStream();
		 

		 ImageIO.write(buffer, "png", os);
		 os.close();

	}


}
