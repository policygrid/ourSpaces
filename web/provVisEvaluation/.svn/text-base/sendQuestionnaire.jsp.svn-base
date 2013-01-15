<%@ page language="java" import="com.hp.hpl.jena.ontology.OntProperty, common.*, java.net.URLDecoder, java.util.List, net.sf.json.*" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%
String a1 = (String)request.getParameter("q1");
String a2 = (String)request.getParameter("q2");
String a3 = (String)request.getParameter("q3");
String a4 = (String)request.getParameter("q4");
String a5 = (String)request.getParameter("q5");
String a6 = (String)request.getParameter("q6");
String a7 = (String)request.getParameter("q7");
String a8 = (String)request.getParameter("q8");
String a9 = (String)request.getParameter("q9");
String a10 = (String)request.getParameter("q10");
String a11 = (String)request.getParameter("q11");
String a12 = (String)request.getParameter("q12");
Integer type = (Integer)session.getAttribute("type");
Integer user = (Integer)session.getAttribute("user");
try{
	if(a1!= null)
		a1 = a1.replaceAll("'", "");
	if(a2!= null)
		a2 = a2.replaceAll("'", "");
	if(a3!= null)
		a3 = a3.replaceAll("'", "");
	if(a4!= null)
		a4 = a4.replaceAll("'", "");
	if(a5!= null)
		a5 = a5.replaceAll("'", "");
	if(a6!= null)
		a6 = a6.replaceAll("'", "");
	if(a7!= null)
		a7 = a7.replaceAll("'", "");
	if(a8!= null)
		a8 = a8.replaceAll("'", "");
	if(a9!= null)
		a9 = a9.replaceAll("'", "");
	if(a10!= null)
		a10 = a10.replaceAll("'", "");
	if(a11!= null)
		a11 = a11.replaceAll("'", "");
	if(a12!= null)
		a12 = a12.replaceAll("'", "");
	String yip = ""+request.getRemoteAddr() +"--"+request.getRemoteHost() +"--"+request.getHeader("x-forwarded-for");
	
	System.out.println(type+",'"+a1+"','"+a2+"','"+a3+"','"+a4+"','"+a5+"','"+a6+"','"+a7+"','"+a8+"','"+a9+"','"+a10+"','"+a11+"','"+a12+"','"+yip);
	//java.net.InetAddress yip2=java.net.InetAddress.getLocalHost();	
	//String yip=yip2.getHostAddress();
		
	LoggerSearch l = new LoggerSearch();
	l.addLog("log_provvisquestionnaire(userid,type,a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12, ip)",user+","+type+",'"+a1+"','"+a2+"','"+a3+"','"+a4+"','"+a5+"','"+a6+"','"+a7+"','"+a8+"','"+a9+"','"+a10+"','"+a11+"','"+a12+"','"+yip+"'");
}
catch(Exception e){
	System.out.println(type+",'"+a1+"','"+a2+"','"+a3+"','"+a4+"','"+a5+"','"+a6+"','"+a7+"','"+a8+"','"+a9+"','"+a10+"','"+a11+"','"+a12+"','");

}
	response.sendRedirect("end.jsp");
%>