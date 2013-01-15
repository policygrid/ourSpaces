<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="projects" class="common.Project" />
<jsp:useBean id="rdf" class="common.RDFi" />
<%
			User user = (User) session.getAttribute("use");
			if (user.getID() == 0)
				response.sendRedirect(response.encodeRedirectURL("/error.jsp"));
			Vector tags = user.getTags(user.getID());
			
		
			Tag tag = new Tag();
			ArrayList<String> list  = new ArrayList();
			// The list of communication resources lfor the user
			//Vector myComres = user.getResourcesByType(user.getID() ,"http://www.policygrid.org/provenance-generic.owl#Communication" );
			Vector <common.Resources>  si = user.getResourcesByType(user.getID(),"http://www.policygrid.org/provenance-simulation.owl#SimulationInput");
			Vector <common.Resources> smi = user.getResourcesByType(user.getID(),"http://www.policygrid.org/provenance-simulation.owl#SimulationModelInstance");
			Vector <common.Resources> sm = user.getResourcesByType(user.getID(),"http://www.policygrid.org/provenance-simulation.owl#SimulationModel");
			Vector <common.Resources> all = new Vector <common.Resources>();
			 all.addAll(si);
			 all.addAll(sm);
			 all.addAll(smi); 
			for (int i=all.size()-1;i>=0;i--){
				Resources res= (Resources) all.get(i);        	
        		list.add(res.getID());
        		common.Utility.log.debug(res.getID());
			}
			
			
			if( list.size() > 0)
				tags = tag.getResourceTags( list);
			
			session.setAttribute("tags", tags);
			%>
			<jsp:include page="../boxes/tags.jsp"></jsp:include>
			<%
			session.setAttribute("tags", null); 
			%>
			
			