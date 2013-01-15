<%@ page language="java" import="com.hp.hpl.jena.ontology.*,java.util.Iterator,java.util.*,java.net.*,java.text.SimpleDateFormat,java.util.ArrayList,java.io.*,java.net.*,java.util.Vector,common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="ontology" class="common.OntologyHandler" />
<jsp:useBean id="rdf" class="common.RDFi" />
<% 
ParameterHelper parHelp = new ParameterHelper(request, session);
	String localName = (String)parHelp.getParameter("property",  "");
	String min = (String)parHelp.getParameter("min",  "");
	String max = (String)parHelp.getParameter("max",  "");
	String exact = (String)parHelp.getParameter("exact",  "");
	String count = (String)parHelp.getParameter("count",  "");
	%>
			restr = new Restriction();
			restr.min = <%=min%>;
			restr.max = <%=max%>;
			restr.exact = <%=exact%>;
			//Finding the right property for this restriction. Also making back-link.
			var x;
			for (x in uploadForm.properties)
			{
				var p = uploadForm.properties[x];
				if(p.property == '<%=localName %>'){
					restr.property = p;
					p.restriction = restr;
					break;
				}				
			}
			for (x in uploadForm.commonProperties)
			{
				var p = uploadForm.commonProperties[x];
				if(p.property == '<%=localName %>'){
					restr.property = p;
					p.restriction = restr;
					break;
				}
			}
				
			uploadForm.restrictions[<%=count%>] = restr;