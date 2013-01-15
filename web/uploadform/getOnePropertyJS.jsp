<%@ page language="java" import="net.sf.json.*,com.hp.hpl.jena.ontology.*,java.util.Iterator,java.util.*,java.net.*,java.text.SimpleDateFormat,java.util.ArrayList,java.io.*,java.net.*,java.util.Vector,common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	JSONObject j = (JSONObject)session.getAttribute("json");
	JSONArray values = j.getJSONArray("value");
	%>

				property = new Property();	
				property.idProperty = '<%=j.get("idProperty") %>';
			  property.property = '<%=j.get("property") %>';
				property.value = [];
			  property.restriction = null;
			  property.el = null;
			  property.type = '<%=j.get("type") %>';
			  property.range = '<%=j.get("range") %>';
			  <%
			  if(values.size()>0){
				  for(Object s : values){
					  //Skip the system processes.
					  if(s.toString().startsWith("http://www.policygrid.org/ourspacesVRE.owl#"))
						  continue;
					  if("geoMultiple".equals(j.get("type"))){
					  		%>property.value.push(<%=s %>);<%						  
					  }
					  else{
				  		%>property.value.push('<%=s %>');<%
					  }
				  }
			  }
			  //Adding subproperties for nested properties.
			  if(j.containsKey("subProperties")){
					JSONArray subProperties = j.getJSONArray("subProperties");
			    %>property.subProperties = [];
			    var propTemp = property;<%
			    int count = 0;
				  for(Object s : subProperties){
					  JSONObject j2 = (JSONObject)s;
					  j2.put("location","propTemp.subProperties");
					  j2.put("count",count);
						session.setAttribute("json", s);
						count++;
						%>
						<jsp:include page="getOnePropertyJS.jsp"/>
				  	<% //	roperty.subProperties.push('<%=s ');
				  }
				  %>
				  property = propTemp;
				  <%
			  }

			  if(j.containsKey("location")&&j.containsKey("count")){
			  %>  
					<%=j.get("location") %>.push(property);
				<%
				}
				%>