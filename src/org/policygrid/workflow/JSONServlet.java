
package org.policygrid.workflow;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.openrdf.OpenRDFException;
import org.policygrid.workflow.AjaxResponseContent;
import org.policygrid.workflow.AjaxResponseStatus;
import org.policygrid.workflow.OntologyQuery;
import org.policygrid.workflow.ProvenanceGenerator;
import org.policygrid.workflow.RDFQuery;

import common.Resources;


public class JSONServlet extends HttpServlet {

	public JSONServlet(){
		super();
	}
	
	private static final long serialVersionUID = 4339959957277852294L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.setProperty("http.proxyHost", "proxy.abdn.ac.uk");
        System.setProperty("http.proxyPort", "8080");
        
		String method = request.getParameter("method");
		AjaxResponseContent ajaxRes = new AjaxResponseContent();
		
		/*
		 * Retrieve a user's digital artefacts. Users are identified
		 * through their session.
		 */
		if("getResource".equals(method)) {
			common.User user = (common.User) request.getSession().getAttribute("use");
			int userId = user.getID();
			List<Resources> artefacts = RDFQuery.getArtefacts(userId, user);
			ajaxRes.getData().put("resource", artefacts);
		}
		
		/*
		 * Retrieve opm:Processes from the ontology specified.
		 */
		if("getProcess".equals(method)) {
			String ontFilePath = this.getServletContext().getRealPath("ontologies/provenance-generic.owl");
			OntologyQuery ontQuery = new OntologyQuery(ontFilePath);
			ajaxRes.getData().put("processRoot", ontQuery.getProcessTree("http://openprovenance.org/ontology#Process"));
		}
		
		/*
		 * Retrieve relationships/edges and constraints from 
		 * the ontology specified.
		 */
		if("getEdge".equals(method)) {
			String ontFilePath = this.getServletContext().getRealPath("ontologies/provenance-generic.owl");
			OntologyQuery ontQuery = new OntologyQuery(ontFilePath);
			ajaxRes.getData().put("edges", ontQuery.listEdges("http://openprovenance.org/ontology#Edge"));
		}
		
		ajaxRes.setResponseStatus(AjaxResponseStatus.SUCCESS);
		
		response.setContentType("application/json");
		response.getWriter().write(JSONObject.fromObject(ajaxRes).toString());
	}

	/**
	 * Retrieve provenance data in JSON. 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String toServerJSON = request.getParameter("toServerJSON");
		String ontFilePath = this.getServletContext().getRealPath("ontologies/provenance-generic.owl");
		ProvenanceGenerator generator = new ProvenanceGenerator(ontFilePath);		
		try {
			generator.generateRDF(toServerJSON);
		} catch (OpenRDFException e) {
			e.printStackTrace();
		}		
	}
}
