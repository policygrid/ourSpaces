<%@ page language="java" import="net.sf.json.*,com.hp.hpl.jena.ontology.*,java.util.Iterator,java.util.*,java.net.*,java.text.SimpleDateFormat,java.util.ArrayList,java.io.*,java.net.*,java.util.Vector,common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="ontology" class="common.OntologyHandler" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="resourceBean" class="common.ResourceUploadBean" />

<% 
ParameterHelper parHelp = new ParameterHelper(request, session);
	String idProperty = (String)parHelp.getParameter("idProperty",  "");
	String liStyle = (String)parHelp.getParameter("liStyle",  "");
	String liClass = (String)parHelp.getParameter("liClass",  "");
	String type = (String)parHelp.getParameter("type",  "");
	String dataRange = (String)parHelp.getParameter("data-range",  "");
		String image = Utility.getImage(dataRange, idProperty);
	image = (String)parHelp.getParameter("image",  image);	
	String onclick = (String)parHelp.getParameter("onclick",  "activeDialog.dropProperty(null, this.parentNode);");	
	String label = (String)parHelp.getParameter("label",  "");		
	String helpText = "";
	OntologyHandler o = new OntologyHandler();
	OntProperty p = o.getProperty(Utility.getLocalName(idProperty));
	helpText = p.getComment(null);		
	String noTitle = "";
	if(helpText == null || helpText.length() == 0){
		noTitle = "noTitle";
		helpText = "";
	}
%>
<li id="<%=idProperty %>" style="<%=liStyle%>" data-range="<%=dataRange%>" title="<%=helpText%>" class="<%=liClass%> <%=noTitle %>"  rel="<%=type %>"><span style="float:left; margin-right:5px;background-position: 0 0;background-image:url('<%=image%>');background-size:12px 12px;" class="ui-icon ui-icon-info"></span><a href="#" style="float:left;" onclick="<%=onclick%>" ><%=label %></a><span class="restriction" style="color:red"></span></br></li>