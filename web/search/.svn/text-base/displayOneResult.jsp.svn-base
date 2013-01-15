<%@ page language="java" import="net.sf.json.*, java.util.Iterator, org.openrdf.*, org.openrdf.query.*, org.openrdf.model.Value, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*, org.policygrid.ontologies.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="contact" class="common.User" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="con" class="common.Connections" />
<jsp:useBean id="ontology" class="common.OntologyHandler" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>




<%
	
	String inputString = "";
	if(request.getParameter("queryString")!=null)
		inputString = request.getParameter("queryString");
  if(request.getParameter("q")!=null)
		inputString = request.getParameter("q");
  //Autocomplete
  if(request.getParameter("term")!=null)
		inputString = request.getParameter("term");
  	
	String queryType = null;
	if(request.getParameter("type") != null)
		queryType = request.getParameter("type").toString();
	
	String spaces = "true";
	if(request.getParameter("spaces") != null)
		spaces = request.getParameter("spaces").toString();
	
	String outputType = "HTML";
	if(request.getParameter("output") != null)
		outputType = request.getParameter("output").toString();

	String addValue = "false";
	if(request.getParameter("addValue")!=null)
		addValue = request.getParameter("addValue");
	
	
	
	if("HTML".equals(outputType)){
		%><ul style="padding: 5px;"><%		
	}
	/*else{
		%>[<%	
	}*/
		
	
	Vector classes = ontology.getSubclassListFull("general",queryType);
		
	
	JSONArray results = new JSONArray();
	JSONObject oneRes;
	StringBuffer qry = new StringBuffer(1024);
	
	qry.append("SELECT DISTINCT ?x ?type ?title ?name ?surname ?blogtitle  ?projectTitle ");
	qry.append("WHERE { ");
	qry.append("?x ?prop ?val. ");
	qry.append("?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?type . ");
	qry.append("FILTER regex(?val, \""+inputString+"\",\"i\"). ");
	qry.append("FILTER NOT EXISTS { ?any <"+ProvenanceGeneric.previousVersion.toString()+"> ?x }. ");
	if(queryType!=null && queryType.length() >0 )
		qry.append("FILTER ( ");
		
		qry.append("regex(str(?type), \""+queryType+"\")  ");
		for (int i = 0; i < classes.size(); i++) {
			common.Utility.log.debug("------------------ CLASSES "+(String)classes.get(i));
			qry.append("|| regex(str(?type), \""+(String)classes.get(i)+"\")  ");
		}	
		
		qry.append(") ");
	
	qry.append("OPTIONAL {?x <http://www.policygrid.org/provenance-generic.owl#title> ?title } ");
	qry.append("OPTIONAL {?x <http://xmlns.com/foaf/0.1/firstName> ?name }  ");
	qry.append("OPTIONAL {?x <http://xmlns.com/foaf/0.1/surname> ?surname }  ");
	qry.append("OPTIONAL {?x <http://rdfs.org/sioc/ns#title> ?blogtitle } ");
	qry.append("OPTIONAL {?x <http://www.policygrid.org/project.owl#projectTitle> ?projectTitle  } ");
	
	
	
	
	/*
	qry.append("OPTIONAL {?x <http://rdfs.org/sioc/ns#has_creator> ?acc .  ");
	qry.append("          ?p <http://xmlns.com/foaf/0.1/holdsAccount> ?acc .  ");
	qry.append("          ?p <http://xmlns.com/foaf/0.1/firstName> ?name  .  ");
	qry.append("          ?p <http://xmlns.com/foaf/0.1/surname> ?surname }  ");
	*/
	
	if("HTML".equals(outputType)){
	qry.append("} ORDER BY(?type)  LIMIT 20");
	} else {
	qry.append("} ORDER BY(?title)");
	}
	common.Utility.log.debug(qry);
	
	String query = qry.toString();
	
	con.repConnect();
	
	TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
	TupleQueryResult result = output.evaluate();
	
	while (result.hasNext()) 
	{
		   BindingSet bindingSet = result.next();
		   String res = bindingSet.getValue("x").stringValue();
		   String type = bindingSet.getValue("type").stringValue();
		   oneRes = new JSONObject();
		   //exceptions
		   
		   if (type.equals("http://www.policygrid.org/ourspacesVRE.owl#BlogContainer")) continue;
		   if (type.equals("http://www.policygrid.org/project.owl#ResearchAim")) continue;
		   if (type.equals("http://www.policygrid.org/ourspacesVRE.owl#OurSpacesAccount")) continue;
		 //  if (type.equals("")) continue;
		 //  if (type.equals("")) continue;
		   //END OF exceptions
		   
		   String cat ="";
		      
		   if (type.startsWith("http://xmlns.com/foaf/0.1/")) { 
			   cat = type.substring(26) ;
		   } else  {   
		    String[] fields = type.split("#");
		    cat = (String)fields[1];
		   }
		   
		   String title = "";
		   String name = "";
		   String surname = "";
		   String blogtitle = "";
		   String projectTitle = "";
		
		   if (bindingSet.getValue("title") != null) title = bindingSet.getValue("title").stringValue();
		   if (bindingSet.getValue("name") != null)  name = bindingSet.getValue("name").stringValue();
		   if (bindingSet.getValue("surname") != null)  surname = bindingSet.getValue("surname").stringValue();
		   if (bindingSet.getValue("blogtitle") != null)  blogtitle = bindingSet.getValue("blogtitle").stringValue();
		   if (bindingSet.getValue("projectTitle") != null)  projectTitle = bindingSet.getValue("projectTitle").stringValue();
		   
		   
		   
		   	
			  String[] fields1 = res.split("#");
			  String rr = (String)fields1[1];
				String displayName = "";
				
				if (!title.equals("")) 
					displayName = title;
							
				String image = "";
				if (type.equals("http://xmlns.com/foaf/0.1/Person")) {
					displayName = name+" "+surname;
					image = "001_55.png";
				}
				else if (type.equals("http://openprovenance.org/ontology#Artifact") ) {
					displayName = title;
					image = "001_58.png";
				}
				else 
				if (type.equals("http://rdfs.org/sioc/ns#Post")) {
					displayName = blogtitle;
					image = "001_50.png";
				}
				else if (type.equals("http://www.policygrid.org/project.owl#Project")) {
					displayName = projectTitle;
					image = "001_34.png";
				}
				else if (type.startsWith("http://www.policygrid.org/provenance-generic.owl#") && (title != "")){
					displayName = title;
					image = "001_34.png";
				}

				   oneRes.put("id", res);
				   if(addValue.equals("true"))
				   		oneRes.put("value", res);
				   
				 
				   if (spaces.equals("true")) 
					   oneRes.put("label", displayName);
				   if (spaces.equals("false")) {
					   String lab = displayName.replaceAll("[^A-Za-z0-9]", "_");
					   if (lab.length() > 40) lab = lab.substring(0, 40)+"_";
					   oneRes.put("label",lab);
				   }
				   
				   oneRes.put("data-class", type);
						
				if("HTML".equals(outputType)){
					%><li>
			   	<div id="<%=res %>" data-class="<%=type %>" title="<%=displayName %>" class="dragElement"> <img src="icons/<%=image %>" align="middle"> <%=displayName %>  </div>
			    </li><%		
				}
				/*else if (!displayName.equals("")){
					%> {"id":"<%=res %>","name":"<%=displayName %>", "data-class":"<%=type %>"},<%
				}*/
				
				if (!displayName.equals("")) results.add(oneRes);
	}
	result.close();
	con.repDisconnect();

	if("HTML".equals(outputType)){
		%></ul><%		
	}
	else{		
		%><%=results.toString() %><%
	}%>





