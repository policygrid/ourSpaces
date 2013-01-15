package common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.openrdf.OpenRDFException;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.GraphQuery;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryException;
import org.policygrid.ontologies.FOAF;
import org.policygrid.ontologies.ProvenanceGeneric;
import org.policygrid.ontologies.OurSpacesVRE;
import org.policygrid.ontologies.SIOC;
import org.xml.sax.SAXException;


import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;
import com.hp.hpl.jena.vocabulary.*;
import com.hp.hpl.jena.ontology.*;

/**
 * The Project model class.  Provides a means of accessing all relevant
 * information in a Project profile page.
 * 
 * @author Richard Reid
 * @version 1.0
 */

public class Project 
{
    
	String user;
	Connections con = new Connections();
	
	public Project()
	{
		
	}

	
	/**
	 * 
	 * Connect to the database
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void connect() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		con.connect();
	}
	
	/**
	 * 
	 * Disconnecting from the database
	 * 
	 * @throws SQLException
	 */
	public void disconnect() throws SQLException
	{
		con.disconnect();
	}
	
	public String createProject(HttpServletRequest request) throws OpenRDFException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, ServletException
	{
		String title = "";
		String subtitle = "";
		
		HttpSession session = request.getSession();
    	User user2 = (User) session.getAttribute("use");
    	common.Utility.log.debug(user2.getID());
    	int id2 = user2.getID();
		
		Model model = ModelFactory.createDefaultModel();
		RDFi rdf = new RDFi();
		
		String projectID = "http://www.policygrid.org/project.owl#" + UUID.randomUUID().toString();
		String projectContainerID = "http://www.policygrid.org/ourspacesVRE.owl#" + UUID.randomUUID().toString();
		String researchAimsID = "http://www.policygrid.org/project.owl#" + UUID.randomUUID().toString();
		Hashtable memberTable = new Hashtable();
		Hashtable roleTable = new Hashtable();
		
		String startday = "";
		String startmonth = "";
		String startyear = "";
		String endday = "";
		String endmonth = "";
		String endyear = "";
		
		Resource project = model.createResource(projectID);
		project.addProperty(RDF.type, org.policygrid.ontologies.Project.Project);
        
		Resource projectContainer  = model.createResource(projectContainerID);
		projectContainer.addProperty(RDF.type, OurSpacesVRE.ProjectContainer);
		projectContainer.addProperty(SIOC.about, project);
		
		String projectLabel = request.getParameter("title");
		
		Enumeration paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()) 
		{
        	String param = (String)paramNames.nextElement();
        	if(param.equals("projectOf"))
        	{
        		
        		String projects[] = request.getParameterValues(param);
        		String parentProject = projects[0];
        		Resource mainProject = model.createResource(parentProject);
        		common.Utility.log.debug("Subproject of: " + parentProject);
        		mainProject.addProperty(org.policygrid.ontologies.Project.hasSubProject, project);
        		
        		String newTitle = getTitle(parentProject);
        		projectLabel = projectLabel + " --> " + newTitle;
        		
        		//Activities act = new Activities();
        		//act.addActivity(id2, 27, projectID, parentProject);
        	}
        	if(param.equals("startday"))
        	{
        		String dates[] = request.getParameterValues(param);
        		startday = dates[0];
        		
        	}
        	if(param.equals("startmonth"))
        	{
        		String dates[] = request.getParameterValues(param);
        		startmonth = dates[0];
        	}
        	if(param.equals("startyear"))
        	{
        		String dates[] = request.getParameterValues(param);
        		startyear = dates[0];
        	}
        	if(param.equals("endday"))
        	{
        		String dates[] = request.getParameterValues(param);
        		endday = dates[0];
        	}
        	if(param.equals("endmonth"))
        	{
        		String dates[] = request.getParameterValues(param);
        		endmonth = dates[0];
        	}
        	if(param.equals("endyear"))
        	{
        		String dates[] = request.getParameterValues(param);
        		endyear = dates[0];
        	}
        	if(param.equals("title")) 
        	{
        		String title2[] = request.getParameterValues(param);
        		title = title2[0];
        		title = title.replaceAll("'", "\\\\'");
        		project.addProperty(org.policygrid.ontologies.Project.projectTitle, title);
        	}
        	if(param.equals("subtitle")) 
        	{
        		String title2[] = request.getParameterValues(param);
        		subtitle = title2[0];
        		subtitle = subtitle.replaceAll("'", "\\\\'");
        		project.addProperty(org.policygrid.ontologies.Project.projectSubtitle, subtitle);
        	}
        	if(param.equals("aim")) 
        	{
        		String aims[] = request.getParameterValues(param);
        		
        		Resource researchAims = model.createResource(researchAimsID);
    			researchAims.addProperty(RDF.type, org.policygrid.ontologies.Project.ResearchAim);
        		for(int i = 0; i < aims.length; i++)
        		{
        			String aim = (String) aims[i];
        			aim = aim.replaceAll("'", "\\\\'");
        			researchAims.addProperty(org.policygrid.ontologies.Project.hasDescription, aim);
        		}
        		project.addProperty(org.policygrid.ontologies.Project.hasResearchAims, researchAims);
        	}
        	if(param.equals("role")) 
        	{
        		String roles[] = request.getParameterValues(param);
        		for(int i = 0; i < roles.length; i++)
        		{
        			String role = roles[i];
        			String[] fields = role.split("_");
        			roleTable.put(fields[0], fields[1]);
        		}
        	}
        	if(param.equals("Name")) 
        	{
        		String members[] = request.getParameterValues(param);
        		for(int i = 0; i < members.length; i++)
        		{
        			String role = members[i];
        			String[] fields = role.split("_");
        			memberTable.put(fields[0], fields[1]);
        		}
        	}
		}
		
		project.addProperty(org.policygrid.ontologies.Project.startDate, startday + "/" + startmonth + "/" + startyear);
		project.addProperty(org.policygrid.ontologies.Project.endDate, endday + "/" + endmonth + "/" + endyear);
		
		project.addProperty(RDFS.label, projectLabel);
		
		String memberRoles[];
		
		Enumeration en = roleTable.keys();
		while(en.hasMoreElements())
		{
			String projectRoleID = "http://www.policygrid.org/project.owl#" + UUID.randomUUID().toString();
			String param = (String)en.nextElement();
			String finalMem = (String) memberTable.get(param);
			String finalRole = (String) roleTable.get(param);
			
			Resource role = ResourceFactory.createResource(org.policygrid.ontologies.Project.NS + finalRole);
			
			Resource person = model.createResource(finalMem);
			
			Resource projectMemberRole = model.createResource(projectRoleID);
			
			projectMemberRole.addProperty(RDF.type, role);
			projectMemberRole.addProperty(org.policygrid.ontologies.Project.roleOf, person);
			
			project.addProperty(org.policygrid.ontologies.Project.hasMemberRole, projectMemberRole);
			
			
			AccessControl.setUserPermission("view", finalMem, projectID, true);
			AccessControl.setUserPermission("download", finalMem, projectID, true);
			
			AccessControl.setPublic(projectID);
			
		}
        
		
        ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		model.write(out);
		InputStream in = new ByteArrayInputStream(out.toByteArray());
		rdf.write(in);
		
		AccessControl.setUserPermission("view", user2.getFOAFID(), projectID, true);
		AccessControl.setUserPermission("download", user2.getFOAFID(), projectID, true);
		AccessControl.setUserPermission("edit", user2.getFOAFID(), projectID, true);
		AccessControl.setUserPermission("remove", user2.getFOAFID(), projectID, true);
		
		return projectID;
	}
	
	/**
	 * Allows a user to edit a project's information, including title, members and aims.
	 * 
	 * @param request
	 * @return
	 * @throws OpenRDFException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws ServletException
	 */
	public void editProject(HttpServletRequest request) throws OpenRDFException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, ServletException
	{
		String type = request.getParameter("type");
		String projectID = request.getParameter("projectID");
		RDFi rdf = new RDFi();
		
		Connections con = new Connections();
		
		if(type.equals("private"))
		{
			AccessControl.deletePublic(projectID);
		}
		if(type.equals("public"))
		{
			AccessControl.setPublic(projectID);
		}
		
		
		Enumeration paramNames = request.getParameterNames();

		while(paramNames.hasMoreElements()) 
		{
	        String param = (String)paramNames.nextElement();
	        if(param.equals("Name")) 
	        {

	        	String members[] = request.getParameterValues(param);
	        	for(int i = 0; i < members.length; i++)
	        	{

	        		String name = members[i];
	        		String[] fields = name.split("_");
	        		if(fields.length != 1)
	        		{
	        				String foafID = fields[1];
	        				AccessControl.setUserPermission("edit", foafID, projectID, true);
	        		}
	        	}
	        }
	        if(param.equals("aim")) 
	        {
	        	deleteProjectInfo(projectID, org.policygrid.ontologies.Project.hasResearchAims.toString());
	        	
	        	Model model = ModelFactory.createDefaultModel();
	        	Resource project = model.createResource(projectID);
	        	
	        	String aims[] = request.getParameterValues(param);
	        	String researchAimsID = "http://www.policygrid.org/project.owl#" + UUID.randomUUID().toString();
        		Resource researchAims = model.createResource(researchAimsID);
    			researchAims.addProperty(RDF.type, org.policygrid.ontologies.Project.ResearchAim);
        		for(int i = 0; i < aims.length; i++)
        		{
        			String aim = (String) aims[i];
        			aim = aim.replaceAll("'", "\\\\'");
        			if (Utility.isNotNull(aim)) {
        				researchAims.addProperty(org.policygrid.ontologies.Project.hasDescription, aim);
					}
        		}
        		project.addProperty(org.policygrid.ontologies.Project.hasResearchAims, researchAims);
        		
        		ByteArrayOutputStream out = new ByteArrayOutputStream();
        		model.write(out);
        		InputStream in = new ByteArrayInputStream(out.toByteArray());
        		rdf.write(in);
	        }
	        if(param.equals("title")) 
        	{
	        	deleteProjectInfo(projectID,org.policygrid.ontologies.Project.projectTitle.toString());
	        	Model model = ModelFactory.createDefaultModel();
	        	Resource project = model.createResource(projectID);
	        	
        		String[] title2 = request.getParameterValues(param);
        		String title = title2[0];
        		title = title.replaceAll("'", "\\\\'");
        		project.addProperty(org.policygrid.ontologies.Project.projectTitle, title);
        		
        		ByteArrayOutputStream out = new ByteArrayOutputStream();
        		model.write(out);
        		InputStream in = new ByteArrayInputStream(out.toByteArray());
        		rdf.write(in);
        	}
        	if(param.equals("subtitle")) 
        	{
        		deleteProjectInfo(projectID,org.policygrid.ontologies.Project.projectSubtitle.toString());
	        	Model model = ModelFactory.createDefaultModel();
	        	Resource project = model.createResource(projectID);
	        	
        		String[] title2 = request.getParameterValues(param);
        		String title = title2[0];
        		title = title.replaceAll("'", "\\\\'");
        		project.addProperty(org.policygrid.ontologies.Project.projectSubtitle, title);
        		
        		ByteArrayOutputStream out = new ByteArrayOutputStream();
        		model.write(out);
        		InputStream in = new ByteArrayInputStream(out.toByteArray());
        		rdf.write(in);
        	}
        	if(param.equals("role")) 
        	{
        		deleteProjectInfo(projectID, org.policygrid.ontologies.Project.hasMemberRole.toString());
        		String[] roles = request.getParameterValues(param);
        		Model model = ModelFactory.createDefaultModel();
	        	Resource project = model.createResource(projectID);
        		for(int i = 0; i < roles.length; i++)
        		{
        			String temp = roles[i];
        			String[] fields = temp.split("_");
        			String foafID = fields[0];
        			String actualRole = fields[1];

        			String projectRoleID = "http://www.policygrid.org/project.owl#" + UUID.randomUUID().toString();
        			
        			Resource role = ResourceFactory.createResource(org.policygrid.ontologies.Project.NS + actualRole);     
        			Resource person = model.createResource(foafID);        			
        			Resource projectMemberRole = model.createResource(projectRoleID);
        			
        			projectMemberRole.addProperty(RDF.type, role);
        			projectMemberRole.addProperty(org.policygrid.ontologies.Project.roleOf, person);
        			
        			project.addProperty(org.policygrid.ontologies.Project.hasMemberRole, projectMemberRole);
        			
        			ByteArrayOutputStream out = new ByteArrayOutputStream();
            		model.write(out);
            		InputStream in = new ByteArrayInputStream(out.toByteArray());
            		rdf.write(in);
        		}
        	}
		}
	}
	
	/**
	 * Returns a list of ProjectBeans containing all the projects a user
	 * is a member of or has created.
	 * 
	 * @param id
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public ArrayList getAllProjectsAboutUser(String rdfFoafID) throws ParserConfigurationException, SAXException, IOException, ServletException, 
		OpenRDFException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		
		ArrayList projectList = new ArrayList();
		
		StringBuffer qry = new StringBuffer(1024);	
		qry.append("SELECT ?project ?title ?subtitle ?role where { ");
		qry.append("?role <"+org.policygrid.ontologies.Project.roleOf.toString()+"> <"+rdfFoafID+"> . ");
		qry.append("?project <"+org.policygrid.ontologies.Project.hasMemberRole.toString()+"> ?role . ");
		qry.append("?project <"+org.policygrid.ontologies.Project.projectTitle.toString()+"> ?title . ");
		qry.append("?project <"+org.policygrid.ontologies.Project.projectSubtitle.toString()+"> ?subtitle } ");
		
		String project = "";
		String title = "";
		String subtitle = "";
		String role = "";
	
		String query = qry.toString();

		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value1 = bindingSet.getValue("project");
			   Value value2 = bindingSet.getValue("title");
			   Value value3 = bindingSet.getValue("subtitle");
			   Value value4 = bindingSet.getValue("role");
			   project = value1.stringValue();
			   title = value2.stringValue();
			   subtitle = value3.stringValue();
			   role = value4.stringValue();
			   
			   ProjectBean pb = new ProjectBean();
			   String[] roles = splitURI(getProjectRoleOfUser(role));
			   pb.setRole(roles[1]);
			   pb.setSubtitle(subtitle);
			   pb.setTitle(title);
			   pb.setURI(project);
			   
			   String[] fields = splitURI(project);
			   pb.setURL(fields[1]);
			   projectList.add(pb);
		}
		result.close();
		con.repDisconnect();

		return projectList;
	}
	
	/**
	 * Returns an ArrayList of all Projects in ourSpaces
	 * 
	 */
	public ArrayList getAllProjects() throws ParserConfigurationException, SAXException, IOException, ServletException, 
	OpenRDFException, SQLException, InstantiationException, IllegalAccessException
	{
		ArrayList projectList = new ArrayList();
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select ?x ?title ?sub where {?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <"+org.policygrid.ontologies.Project.Project.toString()+"> . ");
		qry.append("?x <"+org.policygrid.ontologies.Project.projectTitle+"> ?title . ");
		qry.append("?x <"+org.policygrid.ontologies.Project.projectSubtitle+"> ?sub . } ");
		
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value1 = bindingSet.getValue("x");
			   Value value2 = bindingSet.getValue("title");
			   Value value3 = bindingSet.getValue("sub");
			   String projectID = value1.stringValue();
			   String projectTitle = value2.stringValue();
			   String projectSubtitle = value3.stringValue();
			   String[] fields = splitURI(projectID);
			   
			   ProjectBean pb = new ProjectBean();
			   pb.setTitle(projectTitle);
			   pb.setSubtitle(projectSubtitle);
			   pb.setURI(projectID);
			   pb.setURL(fields[1]);
			   
			   projectList.add(pb);
		}
		result.close();
		con.repDisconnect();
		
		return projectList;
	}
	
	public ArrayList getAllProjectsWithSub() throws ParserConfigurationException, SAXException, IOException, ServletException, 
	OpenRDFException, SQLException, InstantiationException, IllegalAccessException
	{
		ArrayList projectList = new ArrayList();
		
		StringBuffer qry = new StringBuffer(1024);


		qry.append("select ?id ?title ?subtitle ?subid ?subprojecttitle ");
		qry.append("		{ ");
		qry.append("		?id <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.policygrid.org/project.owl#Project> . ");
		qry.append("		?id <"+org.policygrid.ontologies.Project.projectTitle+"> ?title . ");
		qry.append("		?id <"+org.policygrid.ontologies.Project.projectSubtitle+"> ?subtitle . ");

		qry.append("		OPTIONAL {?x <"+org.policygrid.ontologies.Project.hasSubProject+"> ?id} ");
		qry.append("		FILTER (!bound(?x)) ");

		qry.append("		optional {  ");
		qry.append("				?id <"+org.policygrid.ontologies.Project.hasSubProject+"> ?subid . ");
		qry.append("				?subid <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.policygrid.org/project.owl#Project> . ");
		qry.append("			?subid <"+org.policygrid.ontologies.Project.projectTitle+"> ?subprojecttitle .  ");
		qry.append("			     } ");
		qry.append("		}  ");
		qry.append("		ORDER BY ?title ?subprojecttitle ");
		
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		
		
		ProjectBean pb = new ProjectBean();
		ArrayList subprojects = new ArrayList();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value1 = bindingSet.getValue("id");
			   Value value2 = bindingSet.getValue("title");
			   Value value3 = bindingSet.getValue("subtitle");
			   Value value4 = bindingSet.getValue("subid");
			   Value value5 = bindingSet.getValue("subprojecttitle");
			   
			  
			   String projectID = value1.stringValue();
			   String projectTitle = value2.stringValue();
			   String projectSubtitle = value3.stringValue();
			   String[] fields = splitURI(projectID);
			  
			   if ((!projectID.equals(pb.getURI()))) {
				   pb = new ProjectBean();
				   subprojects = new ArrayList();
				   pb.setTitle(projectTitle);
				   pb.setSubtitle(projectSubtitle);
				   pb.setURI(projectID);
				   pb.setURL(fields[1]);
				   projectList.add(pb);
				   //common.Utility.log.debug("Adding a new project to the List: "+projectTitle);			 
				} 
			     
			   
			   if ((value5 != null) && (value5.stringValue() != null) && (!value5.stringValue().equals(""))) {			   
				   ProjectBean spb = new ProjectBean();
				   spb.setTitle(value5.stringValue());
				   String subProjectID = value4.stringValue();
				   String[] sfields = splitURI(subProjectID);
				   spb.setURI(subProjectID);
				   spb.setURL(sfields[1]);
				   subprojects.add(spb);
				   pb.setSubprojects(subprojects);
			   }
			   
		}
		result.close();
		con.repDisconnect();
		
		/*
		for (int i = 0; i < projectList.size(); i++) {
			ProjectBean pbi = (ProjectBean)projectList.get(i);
			//common.Utility.log.debug("PROJECT: "+pbi.getTitle());
			ArrayList subpi = pbi.getSubprojects();
			
			if ((subpi != null)) {
				for (int j = 0; j < subpi.size(); j++) {
					ProjectBean spbi = (ProjectBean)subpi.get(j);
					common.Utility.log.debug("    SUB-PROJECT: "+spbi.getTitle());
				}
			}
		}
		*/
		
		return projectList;
	}
	
	public ArrayList getAllProjectsWithSub(int offset, int limit) throws ParserConfigurationException, SAXException, IOException, ServletException, 
	OpenRDFException, SQLException, InstantiationException, IllegalAccessException
	{
		ArrayList projectList = new ArrayList();
		
		StringBuffer qry = new StringBuffer(1024);


		qry.append("select ?id ?title ?subtitle ?subid ?subprojecttitle ");
		qry.append("		{ ");
		qry.append("		?id <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.policygrid.org/project.owl#Project> . ");
		qry.append("		?id <"+org.policygrid.ontologies.Project.projectTitle+"> ?title . ");
		qry.append("		?id <"+org.policygrid.ontologies.Project.projectSubtitle+"> ?subtitle . ");

		qry.append("		OPTIONAL {?x <"+org.policygrid.ontologies.Project.hasSubProject+"> ?id} ");
		qry.append("		FILTER (!bound(?x)) ");

		qry.append("		optional {  ");
		qry.append("				?id <"+org.policygrid.ontologies.Project.hasSubProject+"> ?subid . ");
		qry.append("				?subid <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.policygrid.org/project.owl#Project> . ");
		qry.append("			?subid <"+org.policygrid.ontologies.Project.projectTitle+"> ?subprojecttitle .  ");
		qry.append("			     } ");
		qry.append("		}  ");
		qry.append("		ORDER BY ?title ?subprojecttitle ");
		//qry.append("		LIMIT  " + limit + " ");
		//qry.append("		OFFSET  " + offset + " ");
		
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		
		
		ProjectBean pb = new ProjectBean();
		ArrayList subprojects = new ArrayList();
		
		int index = 0;
		
		while (result.hasNext()) 
		{
			   
			   BindingSet bindingSet = result.next();
		
		   
			   
			   Value value1 = bindingSet.getValue("id");
			   Value value2 = bindingSet.getValue("title");
			   Value value3 = bindingSet.getValue("subtitle");
			   Value value4 = bindingSet.getValue("subid");
			   Value value5 = bindingSet.getValue("subprojecttitle");
			   
			  
			   String projectID = value1.stringValue();
			   String projectTitle = value2.stringValue();
			   String projectSubtitle = value3.stringValue();
			   String[] fields = splitURI(projectID);
			  
			   if ((!projectID.equals(pb.getURI()))) {
				   pb = new ProjectBean();
				   subprojects = new ArrayList();
				   pb.setTitle(projectTitle);
				   pb.setSubtitle(projectSubtitle);
				   pb.setURI(projectID);
				   pb.setURL(fields[1]);
				   if ((index >= offset) && (index < (limit + offset))) { 
				   projectList.add(pb);
				   }
				   //common.Utility.log.debug("Adding a new project to the List: "+projectTitle);	
				   index++;
				} 
			     
			   
			   if ((value5 != null) && (value5.stringValue() != null) && (!value5.stringValue().equals(""))) {			   
				   ProjectBean spb = new ProjectBean();
				   spb.setTitle(value5.stringValue());
				   String subProjectID = value4.stringValue();
				   String[] sfields = splitURI(subProjectID);
				   spb.setURI(subProjectID);
				   spb.setURL(sfields[1]);
				   subprojects.add(spb);
				   if ((index >= offset) && (index < (limit + offset))) { 
				   pb.setSubprojects(subprojects);
				   }
			   }
			 
		  }
		result.close();
		con.repDisconnect();
		
		/*
		for (int i = 0; i < projectList.size(); i++) {
			ProjectBean pbi = (ProjectBean)projectList.get(i);
			//common.Utility.log.debug("PROJECT: "+pbi.getTitle());
			ArrayList subpi = pbi.getSubprojects();
			
			if ((subpi != null)) {
				for (int j = 0; j < subpi.size(); j++) {
					ProjectBean spbi = (ProjectBean)subpi.get(j);
					common.Utility.log.debug("    SUB-PROJECT: "+spbi.getTitle());
				}
			}
		}
		*/
		
		return projectList;
	}
	
	
	
	
	
	
	public ArrayList getSubProjects(String projectID) throws OpenRDFException, IOException
	{
		ArrayList projectList = new ArrayList();
	
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select ?sub ?title where {<"+projectID+"> <"+org.policygrid.ontologies.Project.hasSubProject+"> ?sub . ");
		qry.append("?sub <"+org.policygrid.ontologies.Project.projectTitle+"> ?title } ");
		
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value1 = bindingSet.getValue("sub");
			   Value value2 = bindingSet.getValue("title");
			   String subProjectID = value1.stringValue();
			   String subProjectTitle = value2.stringValue();
			   String[] fields = splitURI(subProjectID);
			   
			   ProjectBean pb = new ProjectBean();
			   pb.setTitle(subProjectTitle);
			   pb.setURI(subProjectID);
			   pb.setURL(fields[1]);
			   
			   projectList.add(pb);
		}
		result.close();
		con.repDisconnect();

		return projectList;
	}
	
	
	public String getParentProject(String projectID) throws OpenRDFException, IOException
	{
		
		/*
		select ?x
		{
			?x <http://www.policygrid.org/project.owl#hasSubProject> <http://www.policygrid.org/project.owl#f96e7630-2e3e-478c-a8cd-3638c8994e7f>
			}
		*/
		if(projectID == null || "".equals(projectID))
			return null;
		
		String parProjectID = null;
		
		ArrayList projectList = new ArrayList();
	
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select ?par where { ?par <"+org.policygrid.ontologies.Project.hasSubProject+"> <"+projectID+">");
		qry.append("} ");
		
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value1 = bindingSet.getValue("par");

			   parProjectID = value1.stringValue();
			   
		}
		result.close();
		con.repDisconnect();

		return parProjectID;
	}
	
	public String getProjectRoleOfUser(String roleID) throws OpenRDFException, IOException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select ?y where { <"+roleID+"> <"+RDF.type+"> ?y }");
		
		String role = "";
	
		String query = qry.toString();

		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("y");
			   role = value.stringValue();
		}
		result.close();
		con.repDisconnect();

		return role;
	}
	
	public void deleteProject(String id) throws ServletException, IOException, OpenRDFException
	{
		String query1 = "CONSTRUCT {<" + id + "> ?y ?z.} WHERE {<" + id + "> ?y ?z.}";
		//String query2 = "CONSTRUCT {?y ?z <" + id + ">.} WHERE {?y ?z <" + id + ">.}";
		
		con.repConnect();

		GraphQuery queryA = con.getRepConnection().prepareGraphQuery(QueryLanguage.SPARQL, query1);
		GraphQueryResult result1 = queryA.evaluate();
		con.getRepConnection().remove(result1);
		
		con.repDisconnect();

	}
	
	public void deleteProjectInfo(String projectID, String property) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("construct { ");
		qry.append("<"+projectID+"> <"+property+"> ?a } where {");
		qry.append("<"+projectID+"> <"+property+"> ?a } ");

		
		String query = qry.toString();
		
		con.repConnect();
		
		GraphQuery q = con.getRepConnection().prepareGraphQuery(QueryLanguage.SPARQL, query);
		GraphQueryResult result = q.evaluate();
		con.getRepConnection().remove(result);
		
		con.repDisconnect();
	}
	
	public String[] addProjectMember(HttpServletRequest request) throws OpenRDFException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, ServletException
	{
		String[] params;
		RDFi rdf = new RDFi();
		Model model = ModelFactory.createDefaultModel();
		//Get the project
		String projectID = request.getParameter("projectOf");
		Resource project = model.createResource(projectID);
		
		Hashtable memberTable = new Hashtable();
		Hashtable roleTable = new Hashtable();
		Enumeration paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()) 
		{
			String param = (String)paramNames.nextElement();
			if(param.equals("role")) 
        	{
        		String roles[] = request.getParameterValues(param);
        		for(int i = 0; i < roles.length; i++)
        		{
        			String role = roles[i];
        			String[] fields = role.split("_");
        			roleTable.put(fields[0], fields[1]);
        		}
        	}
        	if(param.equals("Name")) 
        	{
        		String members[] = request.getParameterValues(param);
        		for(int i = 0; i < members.length; i++)
        		{
        			String role = members[i];
        			String[] fields = role.split("_");
        			memberTable.put(fields[0], fields[1]);
        		}
        	}
		}
		String memberRoles[];
		params = new String[roleTable.size()+1]; 
		params[0] = projectID;
		int i = 1;
		Enumeration en = roleTable.keys();
		while(en.hasMoreElements())
		{
			String projectRoleID = "http://www.policygrid.org/project.owl#" + UUID.randomUUID().toString();
			String param = (String)en.nextElement();
			String foafID = (String) memberTable.get(param);
			String finalRole = (String) roleTable.get(param);
			
			Resource role = ResourceFactory.createResource(org.policygrid.ontologies.Project.NS + finalRole);
			
			Resource person = model.createResource(foafID);
			
			Resource projectMemberRole = model.createResource(projectRoleID);
			
			projectMemberRole.addProperty(RDF.type, role);
			projectMemberRole.addProperty(org.policygrid.ontologies.Project.roleOf, person);
			
			project.addProperty(org.policygrid.ontologies.Project.hasMemberRole, projectMemberRole);
			
			
			AccessControl.setUserPermission("view", foafID, projectID, true);
			AccessControl.setUserPermission("download", foafID, projectID, true);
			
			AccessControl.setPublic(projectID);

			params[i] = foafID;
			i++;
		}				
        ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		model.write(out);
		InputStream in = new ByteArrayInputStream(out.toByteArray());
		rdf.write(in);
		return params;
	
	}

	
	/**
	 * Retrieves all the resources related to a particular project.  Firstly,
	 * an ArrayList of resource URIs is received which is then passed as a 
	 * parameter to retrieve a Resource JavaBean Vector.
	 * 
	 * @param resID
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ServletException
	 * @throws OpenRDFException
	 */
	public Vector getResources(String projectID) throws ParserConfigurationException, SAXException, IOException, ServletException, OpenRDFException
	{
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?uri WHERE { ");
		qry.append("?uri <"+ProvenanceGeneric.producedInProject.toString()+"> <");
		qry.append(projectID);
		qry.append(">. ");
		qry.append("  FILTER isURI(?uri). ");
		qry.append("  FILTER NOT EXISTS { ?any <"+ProvenanceGeneric.previousVersion.toString()+"> ?uri }. ");
		qry.append("} ");

		
		ArrayList resources = new ArrayList();
	
		String query = qry.toString();
		
		// Connects to the repository
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		// Any relevant results are added to the resources ArrayList
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   // retrieves the resourceID of the resource
			   Value value = bindingSet.getValue("uri");
			   String url = value.stringValue();
			   resources.add(url);
		}
		result.close();
		con.getRepConnection().close();
		
		// Initialises the Vector
		Vector resourcesBean = new Vector();
		RDFi rdf = new RDFi();
		for(int i = 0; i<resources.size(); i++)
		{
			Resources res = new Resources();
			String resourceID = (String) resources.get(i);
			res.setID(resourceID);
			res.setTitle(rdf.getResourceTitle(resourceID));
			String ver = rdf.getResourceVersion(resourceID);
			if (ver != null) res.setVersionNumber(ver);
			res.setURL(rdf.getResourceURL(resourceID));
			res.setTimeStamp(rdf.getResourceTimestamp(resourceID));
			long time = getLong(rdf.getResourceTimestamp(resourceID));
			Date date = new Date(time);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String fulldate = sdf.format(date);
			res.setDate(fulldate);
			resourcesBean.add(res);
		}
		sort(resourcesBean);
		
		return resourcesBean;

	}
	
	public void sort(Vector<Resources> a)
	 {
	  for(int i=1; i<a.size(); i++)
	     insert(a.get(i),a,i);
	 }

	 private void insert(Resources n,Vector<Resources> a,int i)
	 {
	  for(; i>0&&((Comparable<Double>) a.get(i-1).getTimeStamp()).compareTo(n.getTimeStamp())>0; i--)
	     a.set(i,a.get(i-1));
	  a.set(i,n);
	 }
	
	 /**
	  * Returns the project container.
	  * 
	  * @param projectID
	  * @return
	  * @throws ServletException
	  * @throws IOException
	  * @throws OpenRDFException
	  */
	 public String getProjectContainer(String projectID) throws ServletException, IOException, OpenRDFException
		{
			StringBuffer qry = new StringBuffer(1024);
			qry.append("SELECT ?y WHERE { ?y <"+SIOC.about.toString()+"> <"+projectID+"> } ");
			

			String cont = "";
		
			String query = qry.toString();
			
			con.repConnect();
			
			TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
			TupleQueryResult result = output.evaluate();
			
			while (result.hasNext()) 
			{
				   BindingSet bindingSet = result.next();
				   Value value = bindingSet.getValue("y");
				   cont = value.stringValue();
			}
			result.close();
			con.getRepConnection().close();
			return cont;
		}
	 
	 /**
	  * Returns the ID of a project based on its container.
	  * 
	  * @param containerID
	  * @return
	  * @throws ServletException
	  * @throws IOException
	  * @throws OpenRDFException
	  */
	 public String getProjectFromContainer(String containerID) throws ServletException, IOException, OpenRDFException
	 {
		 StringBuffer qry = new StringBuffer(1024);
		 qry.append("SELECT ?y WHERE { <"+containerID+"> <"+SIOC.about.toString()+"> ?y } ");
			

			String cont = "";
		
			String query = qry.toString();
			
			con.repConnect();
			
			TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
			TupleQueryResult result = output.evaluate();
			
			while (result.hasNext()) 
			{
				   BindingSet bindingSet = result.next();
				   Value value = bindingSet.getValue("y");
				   cont = value.stringValue();
			}
			result.close();
			con.getRepConnection().close();
			return cont;
	 }
	 
	 /**
	  * Returns an arraylist of ProjectBlogBeans, containing the containerID and the title of 
	  * each blog belonging to a project.
	  * @param parentContainerID
	  * @return
	  * @throws RepositoryException
	  * @throws QueryEvaluationException
	  * @throws MalformedQueryException
	  */
	 public ArrayList getProjectBlogContainers(String parentContainerID) throws RepositoryException, QueryEvaluationException, MalformedQueryException
		{
			ArrayList containers = new ArrayList();
			String blogContainerID = "";
			
			StringBuffer qry = new StringBuffer(1024);
			qry.append("SELECT ?y ?title where { ?y a <"+OurSpacesVRE.BlogContainer.toString()+">. ");
			qry.append("<"+parentContainerID+"> <"+SIOC.parent_of.toString()+"> ?y . ");
			qry.append("?y <"+OurSpacesVRE.BlogTitle.toString()+"> ?title } ");
			
			String query = qry.toString();

			con.repConnect();
			
			TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
			TupleQueryResult result = output.evaluate();
			
			while (result.hasNext()) 
			{
				   BindingSet bindingSet = result.next();
				   Value value = bindingSet.getValue("y");
				   Value value2 = bindingSet.getValue("title");
				   ProjectBlogBean pb = new ProjectBlogBean();
				   blogContainerID = value.stringValue();
				   String title = value2.stringValue();
				   pb.setContainer(blogContainerID);
				   pb.setTitle(title);
				   containers.add(pb);
			}
			result.close();
			con.repDisconnect();
			
			return containers;
		}
	 
	 /**
	  * Returns the title of a particular blog Container
	  * @param parentContainerID
	  * @return
	  * @throws RepositoryException
	  * @throws QueryEvaluationException
	  * @throws MalformedQueryException
	  */
	public String getProjectBlogContainerTitle(String blogContainerID) throws RepositoryException, QueryEvaluationException, MalformedQueryException
	{
		String title = "";
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?y where { <"+blogContainerID+"> <"+OurSpacesVRE.BlogTitle.toString()+"> ?y } ");
			
		String query = qry.toString();

		con.repConnect();
			
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
			
		while (result.hasNext()) 
		{
			BindingSet bindingSet = result.next();
			Value value = bindingSet.getValue("y");
			title = value.stringValue();

		}
		result.close();
		con.repDisconnect();
			
		return title;
	}
	 
	/**
	 * Returns the project Title
	 * @param resID
	 * @return String of project title
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public String getTitle(String resourceID) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?title WHERE { <"+resourceID+"> <"+org.policygrid.ontologies.Project.projectTitle.toString()+"> ?title } ");
		

		String title = "";
	
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("title");
			   title = value.stringValue();
		}
		result.close();
		con.getRepConnection().close();
		return title;
	}
	
	/**
	 * Returns the project's subtitle
	 * @param resID
	 * @return String project description
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public String getSubtitle(String resourceID) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?subtitle WHERE { ");
		qry.append("<");
		qry.append(resourceID);
		qry.append("> ");
		qry.append("<http://www.policygrid.org/project.owl#projectSubtitle> ?subtitle");
		qry.append(" } ");

		String subtitle = "";
	
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("subtitle");
			   subtitle = value.stringValue();
		}
		result.close();
		con.getRepConnection().close();
		return subtitle;
	}
	
	/**
	 * Returns the url of a project photo.
	 * 
	 * @param projectID
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 */
	public String getProjectPhoto(String projectID) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?photo WHERE { ");
		qry.append("<");
		qry.append(projectID);
		qry.append("> ");
		qry.append("<"+org.policygrid.ontologies.Project.hasProjectPhoto.toString()+"> ?photo");
		qry.append(" } ");

		String photo = "";
	
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("photo");
			   photo = value.stringValue();
		}
		result.close();
		con.getRepConnection().close();
		return photo;
	}
	
	/**
	 * Updates the project photo
	 * @param foafID
	 * @param photo
	 * @throws OpenRDFException
	 * @throws IOException
	 * @throws ServletException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public void updatePhoto(String projectID, String photo) throws OpenRDFException, IOException, ServletException, ParserConfigurationException, SAXException
	{
		RDFi rdf = new RDFi();
		deletePhoto(projectID);
		
		User user = new User();
		
		Model model = ModelFactory.createDefaultModel();
		
		Resource project = model.createResource(projectID);
		project.addProperty(org.policygrid.ontologies.Project.hasProjectPhoto, photo);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		model.write(out);
		InputStream in = new ByteArrayInputStream(out.toByteArray());
		rdf.write(in);
	}
	
	public void deletePhoto(String projectID) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("construct { ");
		qry.append("<"+projectID+"> <"+org.policygrid.ontologies.Project.hasProjectPhoto.toString()+"> ?a. } where { ");
		qry.append("<"+projectID+"> <"+org.policygrid.ontologies.Project.hasProjectPhoto.toString()+"> ?a. } ");

		
		String query = qry.toString();
		
		con.repConnect();
		
		GraphQuery q = con.getRepConnection().prepareGraphQuery(QueryLanguage.SPARQL, query);
		GraphQueryResult result = q.evaluate();
		con.getRepConnection().remove(result);
		
		con.repDisconnect();
	}
	
	/**
	 * Returns all members of a project in a list of MemberBeans. This contains the name and role of a 
	 * project member.
	 * 
	 * @param projectID
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList getProjectMembers(String projectID) throws ServletException, IOException, OpenRDFException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select ?person ?role ?firstname ?surname where { ");
		qry.append("<"+projectID+"> <"+org.policygrid.ontologies.Project.hasMemberRole.toString()+"> ?r . ");
		qry.append("?r <"+RDF.type+"> ?role . "); 
		qry.append("?r <"+org.policygrid.ontologies.Project.roleOf.toString()+"> ?person .");
		qry.append("?person <"+FOAF.firstName.toString()+"> ?firstname . ");
		qry.append("?person <"+FOAF.surname.toString()+"> ?surname } ");
		
		String query = qry.toString();
		
		con.repConnect();
		
		ArrayList memberList = new ArrayList();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			String personFoafID = "";
			String role = "";
			String firstname = "";
			String surname = "";
			
			BindingSet bindingSet = result.next();
			Value value1 = bindingSet.getValue("person");
			Value value2 = bindingSet.getValue("role");
			Value value3 = bindingSet.getValue("firstname");
			Value value4 = bindingSet.getValue("surname");
			
			personFoafID = value1.stringValue();
			role = value2.stringValue();
			firstname = value3.stringValue();
			surname = value4.stringValue();
			String fullname = firstname + " " + surname;
			String[] roles = splitURI(role);
			
			User user = new User();
			int id = user.getUserIDFromFOAF(personFoafID);
			
			MemberBean mb = new MemberBean();
			mb.setFoafID(personFoafID);
			mb.setName(fullname);
			mb.setRole(roles[1]);
			mb.setUserID(id);
			
			memberList.add(mb);
			
		}
		result.close();
		con.getRepConnection().close();
		
		return memberList;
	}
	
	public ArrayList getProjectAims(String projectID) throws ServletException, IOException, OpenRDFException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select ?aim where { ");
		qry.append("<"+projectID+"> <"+org.policygrid.ontologies.Project.hasResearchAims.toString()+"> ?aimID . ");
		qry.append("?aimID <"+org.policygrid.ontologies.Project.hasDescription.toString()+"> ?aim } ");
		
		String query = qry.toString();
		
		con.repConnect();
		
		ArrayList aims = new ArrayList();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			
			BindingSet bindingSet = result.next();
			Value value1 = bindingSet.getValue("aim");
			
			String aim = value1.stringValue();
			aims.add(aim);
			
		}
		result.close();
		con.getRepConnection().close();
		
		return aims;
	}
	
	public String getStartdate(String projectID) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?startDate WHERE { ");
		qry.append("<");
		qry.append(projectID);
		qry.append("> ");
		qry.append("<http://www.policygrid.org/project.owl#startDate> ?startDate");
		qry.append(" } ");

		String startdate = "";
	
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("startDate");
			   startdate = value.stringValue();
		}
		result.close();
		con.getRepConnection().close();
		return startdate;
	}
	
	public String getEnddate(String projectID) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?endDate WHERE { ");
		qry.append("<");
		qry.append(projectID);
		qry.append("> ");
		qry.append("<http://www.policygrid.org/project.owl#endDate> ?endDate");
		qry.append(" } ");

		String enddate = "";
	
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("endDate");
			   enddate = value.stringValue();
		}
		result.close();
		con.getRepConnection().close();
		return enddate;
	}
	
	public String getRoleIDAboutUser(String projectID, String foafID) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ?x WHERE { <"+projectID+"> <"+org.policygrid.ontologies.Project.hasMemberRole.toString()+"> ?x . ");
		qry.append("?x <"+org.policygrid.ontologies.Project.roleOf.toString()+"> <"+foafID+"> } ");
		
		String roleID = "";
	
		String query = qry.toString();
		
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   Value value = bindingSet.getValue("x");
			   roleID = value.stringValue();
		}
		result.close();
		con.getRepConnection().close();
		return roleID;
	}
	
	/**
	 * Returns a Vector containing multiple Tag JavaBean Objects.  Project Tags
	 * are retrieved based on all the resources related to a particular project.
	 * 
	 * Firstly, the ArrayList of projects is retrieved then passed to a method
	 * in the Tag class to retrieve the Vector.
	 * 
	 * @param resID
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws OpenRDFException
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public Vector getTags(String resID) throws ServletException, IOException, OpenRDFException, SQLException, 
		InstantiationException, IllegalAccessException, ClassNotFoundException
		{
		// Initialising the relevant classes
		Vector tags = new Vector();
		ProjectRDF rdf = new ProjectRDF();
		Tag tag = new Tag();
		
		// The list of resources linked with the specified project
		ArrayList resources = rdf.getResources(resID);
		
		// Checks if there are any resources available
		if( resources.size() > 0)
			// If so, retrieve the tags for those resources into one Vector
			tags = tag.getResourceTags(resources);
		
		return tags;
	}
	
	public Vector getAllProjectTags() throws ServletException, IOException, OpenRDFException, SQLException, 
	InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		Vector tags = new Vector();
		ProjectRDF rdf = new ProjectRDF();
		Tag tag = new Tag();
		ArrayList resources = new ArrayList();
		
		ArrayList projects = rdf.getProjects();
		
		//common.Utility.log.debug("------------ZZZZZZZZZZZ----------- Projects :"+projects.size());
		int resNum = 0;
		
		for(int i = 0; i < projects.size(); i++) 
		{
			String url = (String) projects.get(i);
			resources.addAll(rdf.getResources(url));
			//common.Utility.log.debug("------------ZZZZZZZZZZZ----------- Resources :"+resources.size());

		}
		common.Utility.log.debug("------------ZZZZZZZZZZZ----------- Resources :"+resources.size());
		
		if(resources.size() > 0) tags = tag.getResourceTags(resources, 50);
		
		return tags;
	}
	
	
	public ArrayList getResourcesFromStage(String stage, String projectID) throws ServletException, IOException, OpenRDFException, SQLException, 
	InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		ArrayList list = new ArrayList();
		ProjectRDF rdf = new ProjectRDF();
		ArrayList resources = rdf.getResourcesFromStage(stage, projectID);
		RDFi rdfi = new RDFi();
		for(int i = 0; i < resources.size(); i++)
		{
			String resource = (String) resources.get(i);
			Resources res = new Resources();
			res.setTitle(rdfi.getResourceTitle(resource));
			res.setURL(rdfi.getResourceURL(resource));
			res.setTimeStamp(rdfi.getResourceTimestamp(resource));
			long time = getLong(rdfi.getResourceTimestamp(resource));
			Date date = new Date(time);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String fulldate = sdf.format(date);
			res.setDate(fulldate);
			list.add(res);
		}
		sort(list);
		return list;
	}
	
	public long getLong(double d){
		return (long) d;
	}
	
	public void sort(ArrayList<Resources> a)
	 {
	  for(int i=1; i<a.size(); i++)
	     insert(a.get(i),a,i);
	 }

	 private void insert(Resources n,ArrayList<Resources> a,int i)
	 {
	  for(; i>0&&((Comparable<Double>) a.get(i-1).getTimeStamp()).compareTo(n.getTimeStamp())>0; i--)
	     a.set(i,a.get(i-1));
	  a.set(i,n);
	 }
	 
	 public void sort2(Vector<Resources> a)
	 {
	  for(int i=1; i<a.size(); i++)
	     insert2(a.get(i),a,i);
	 }

	 private void insert2(Resources n,Vector<Resources> a,int i)
	 {
	  for(; i>0&&((Comparable<Double>) a.get(i-1).getTimeStamp()).compareTo(n.getTimeStamp())>0; i--)
	     a.set(i,a.get(i-1));
	  a.set(i,n);
	 }
	 
	 public String[] splitURI(String resourceID)
	 {
		 String[] fields = resourceID.split("#");
		 return fields;
	 }
}

