
<%@ page language="java"
	import="java.util.Iterator,java.util.*,java.net.*,java.text.SimpleDateFormat,java.util.ArrayList,java.io.*,java.net.*,java.util.Vector,common.*"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="blog" class="common.Blog" />
<jsp:useBean id="user" class="common.User" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="access" class="common.AccessControl" />
<div class="widget-head">
					<h3 class="style3">Provenance (text)</h3>
				</div>
				<div class="widget-content" class="style3">
				<div id="nlgProv" style="padding: 10px;"></div>
				<script type="text/javascript">
					$.ajax({
						type: 'GET',
						url: "/ourspaces/LiberRestServlet?resourceID=" + "<%=URLEncoder.encode(session.getAttribute("artifactID").toString(),"UTF-8")%>&Property=provenance", 
						dataType : "html",
						async : false,
						success : function(html, errorThrown) {
							$("div#nlgProv").html(html);
						}
					});
				</script>
				</div>
				