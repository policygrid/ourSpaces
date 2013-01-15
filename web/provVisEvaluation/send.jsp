<%@ page language="java" import="com.hp.hpl.jena.ontology.OntProperty, common.*, java.net.URLDecoder, java.util.List, net.sf.json.*" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%
	String json = (String)request.getParameter("data");
/*java.util.Map m = request.getParameterMap();
for(Object s : m.keySet()){
	Object o1 = m.get(s);
	System.out.print(s.toString() + ": "+o1.toString());
}*/
	/*JSONObject j = (JSONObject) JSONSerializer.toJSON(json);
	String a1 = "";
	if(j.containsKey("q1"))
		a1=j.getString("q1");
	String a2 = "";
	if(j.containsKey("q2"))
		a2=j.getString("q2");
	String a3 = "";
	if(j.containsKey("q3"))
		a3=j.getString("q3");
	String a4 = "";
	if(j.containsKey("q4"))
		a4=j.getString("q4");
	String dataset = "";
	if(j.containsKey("dataset"))
		dataset=j.getString("dataset");
	String vis = "";
	if(j.containsKey("id"))
		vis=j.getString("id");
	String time = "";
	if(j.containsKey("time"))
		time=j.getString("time");

	java.net.InetAddress yip2=java.net.InetAddress.getLocalHost();	
	String yip=yip2.getHostAddress();

	Integer type = (Integer)session.getAttribute("type");
	Integer user = (Integer)session.getAttribute("user");*/

	String a1 = request.getParameter("q1");
	String a2 = request.getParameter("q2");
	String a3 = request.getParameter("q3");
	String a4 = request.getParameter("q4");
	if(a1 == null)
		a1 = "none";
	if(a2 == null)
		a2 = "none";
	if(a3 == null)
		a3 = "none";
	if(a4 == null)
		a4 = "none";
	
	String dataset = request.getParameter("dataset");
	String vis = request.getParameter("id");
	String time = request.getParameter("time");

	String yip = ""+request.getRemoteAddr() +"--"+request.getRemoteHost() +"--"+request.getHeader("x-forwarded-for");

	Integer type = (Integer)session.getAttribute("type");
	Integer user = (Integer)session.getAttribute("user");
	System.out.println("EVAL:"+user+","+type+",'"+vis+"','"+a1+"','"+a2+"','"+a3+"','"+a4+"','"+dataset+"',"+time+",'"+yip);

	LoggerSearch l = new LoggerSearch();
	l.addLog("log_provviseval(userid,type,vis,a1,a2,a3,a4,dataset,time, ip) ",user+","+type+",'"+vis+"','"+a1+"','"+a2+"','"+a3+"','"+a4+"','"+dataset+"',"+time+",'"+yip+"'");
	if(dataset.equals("video")){
		if(vis.equals("nlgintro"))
			response.sendRedirect("NLGExamples.jsp");
		if(vis.equals("graphintro"))
			response.sendRedirect("GraphExamples.jsp");
	}
	else{
		response.sendRedirect("Task.jsp");
	}
%>