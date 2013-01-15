
<%@ page language="java"
	import="java.util.Iterator,java.util.*,java.net.*,java.text.SimpleDateFormat,java.util.ArrayList,java.io.*,java.net.*,java.util.Vector,common.*"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="blog" class="common.Blog" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="access" class="common.AccessControl" />

				<div class="widget-head">
					<h3 class="style3">Provenance (graph)</h3>
				</div>
				<div class="widget-content" id="provenanceResource" style="overflow: hidden; height:250px; width: 100%;">
					<!-- TODO - set editable to false -->
					<jsp:include page="../../testProvenance/provenanceOne.jsp" flush="false">
						<jsp:param value="<%=session.getAttribute(\"artifactID\") %>" name="resource"/>
						<jsp:param value="false" name="editable"/>
					</jsp:include>	
					
				</div>