<%@ page language="java" import="com.hp.hpl.jena.ontology.OntProperty, common.*, java.net.URLDecoder, java.util.List" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>

<jsp:useBean id="list" class="java.util.ArrayList" scope="request"/>
<%
String label = "";
String id = "";

ParameterHelper parHelp = new ParameterHelper(request, session);
if(request.getParameter("label") != null) {
	label = request.getParameter("label");
}
//Add the nameUpload to the label, in case there is more upload windows opened.
if(request.getParameter("id") != null) {
	id = request.getParameter("id");
	id = URLDecoder.decode(id, "UTF-8");
}
String labelId = Utility.getLocalName(id);

id = URLDecoder.decode(id, "UTF-8");
String remove = (String)parHelp.getParameter("remove","true");
String display = "none";
if("true".equals(remove)) {
	display = "block";
}
String value = (String)request.getParameter("value");
if(value==null)
	value = "";

String labelText = label;
//Transform uppercase to lowercase and a space
labelText = labelText.replaceAll("([A-Z])"," $1");
labelText = labelText.toLowerCase();
//Make first letter uppercase
labelText = labelText.replaceFirst(""+labelText.charAt(0), ""+Character.toUpperCase(labelText.charAt(0)));
//List<String> list = (List<String>)session.getAttribute("values");

RDFi rdf = new RDFi();
String helpText = "";
OntologyHandler o = new OntologyHandler();
OntProperty p = o.getProperty(Utility.getLocalName(id));
String range = p.getRange().getURI();
%><jsp:include page="header.jsp" flush="false">
		<jsp:param value="<%=label %>" name="label"/>
</jsp:include>	
<div id="<%=labelId %>inputDiv" class="resourceInputDiv" style="float: left; width: 100%;">
						<div id="<%=labelId %>inputContent" style="float: left; width: 100%;">
						<%for(String reference: (List<String>)list){
						%><jsp:include page="smallEntity.jsp">
						<jsp:param name="id" value="<%=reference%>"/>
						</jsp:include><%
						}
						%>
						</div>
						<div style="width:100%">
						<input id="<%=labelId %>input" class="resourceInput" style=" border: 1px solid #000000;" id="<%=labelId %>" name="" size="60" type="text" value="">
						</div>
</div>
<jsp:include page="footer.jsp" flush="false">
		<jsp:param value="<%=label %>" name="label"/>
</jsp:include>	
<script>
$(document).ready(function(){

	//attach autocomplete  

		addAutocomplete('<%=labelId%>input', '<%=range%>',
				function(e, ui) { 
		    var name = ui.item.label;
			 //Create formatted resource  
	        var span = $("<span>").text(name);
	        span.attr("id", ui.item.id);  
	        span.addClass("value");  
	        var a = $("<a>").addClass("remove").attr({  
	                href: "#",                
	                title: "Remove " + name  
	      }).text("x").appendTo(span);
	      //Add the resource to the list.  	      
	      $('#<%=labelId %>inputContent').prepend(span);	    
	      
		  //prevent 'to' field being updated and correct position  
		    $('#<%=labelId %>input').val("").css("top", 2);  
	      ui.item.value = "";    
	      ui.item.label = "";  
		}, function() {  
		    //prevent 'to' field being updated and correct position  
		      ui.item.value = "";  
		      ui.item.label = "";  
			    $('#<%=labelId %>input').val("");  
		});
		//add live handler for clicks on remove links  
		$("#<%=labelId %>inputContent .remove", document.getElementById('#<%=labelId %>inputDiv')).live("click", function(){
		    //remove current friend  
		    $(this).parent().remove();  
		    //correct 'to' field position  
		    if($("#<%=labelId %>inputDiv span").length === 0) {  
		        $("#<%=labelId %>input").css("top", 0);  
		    }
		});
});
</script>