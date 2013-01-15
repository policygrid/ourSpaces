<%@ page language="java" import="com.hp.hpl.jena.ontology.*,java.util.Iterator,java.util.*,java.net.*,java.text.SimpleDateFormat,java.util.ArrayList,java.io.*,java.net.*,java.util.Vector,common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="ontology" class="common.OntologyHandler" />
<jsp:useBean id="rdf" class="common.RDFi" />
<% 
ParameterHelper parHelp = new ParameterHelper(request, session);
	String format = (String)parHelp.getParameter("format",  "HTML");
	String className = (String)parHelp.getParameter("className",  "");
	
	/**Contains properties and description of the restriction on the property.*/
	HashMap<String,String> restrictions = new HashMap<String,String>();	

	//No restrictions in HTML
	if(format.equals("HTML")){			
	}
	else{
		%>function reloadRestrictions(uploadForm){
		var restr;
		//Reset the restrictions for empty array.
		uploadForm.restrictions = [];
		<%
		//Load the restrictions into hashmap.
			Iterator<Restriction> it = ontology.getRestrictionsOnClass("all", className);
			int count = 0;	
			while (it != null && it.hasNext()) {
					Restriction rest = it.next();
					int min = -1, max = -1, exact = -1;
					String style = "";
					String append = "";
					if(rest.isMinCardinalityRestriction()){
						min = (rest.asMinCardinalityRestriction()).getMinCardinality();
						if(!"".equals(append))
							append += ", ";
						append = ">="+min;
					}
					if(rest.isMaxCardinalityRestriction()){
						max = (rest.asMaxCardinalityRestriction()).getMaxCardinality();
						if(!"".equals(append))
							append += ", ";
						append = "<="+max;
					}
					if(rest.isCardinalityRestriction()){
						exact = (rest.asCardinalityRestriction()).getCardinality();
						if(!"".equals(append))
							append += ", ";
						append = "="+exact;
					}
					String property = rest.getOnProperty().getLocalName();
					//We don't want system properties manipulated by the user
					if(property.equals("type") ||
							property.equals("hasURI") ||
							property.equals("depositedBy") ||
								property.equals("dateOfDeposit") ||
								property.equals("versionNumber")||
							  property.equals("wasUsedByInfer")  ||
								property.equals("controlledInfer") 
							)							
						continue;
					restrictions.put(property, append);
					%>
					<jsp:include page="getOneRestrictionJS.jsp">
						<jsp:param value="<%=count %>" name="count"/>
						<jsp:param value="<%=property %>" name="property"/>							
						<jsp:param value="<%=min %>" name="min"/>
						<jsp:param value="<%=max %>" name="max"/>
						<jsp:param value="<%=exact %>" name="exact"/>
					</jsp:include><%
				count++;
			} 
			//End of reloadRestrictions
			%>}
			<%
	}%>