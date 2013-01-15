<%@ page language="java" import="java.io.File, java.util.ArrayList,java.util.Scanner, java.io.BufferedWriter,java.io.*, java.net.*, java.util.Vector, common.*,common.Utility,common.User" contentType="text/html; charset=ISO-8859-1"%>

<jsp:useBean id="dc" class="common.datacollector" scope="session"/>
<jsp:setProperty name="dc" property="*"/> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/ourspaces/javascript/jquery-1.6.2.min.js"></script> 
<script type="text/javascript" src="/ourspaces/javascript/jquery-ui-1.8.16.custom.min.js"></script>
<link rel="stylesheet" type="text/css" type="text/css" href="/ourspaces/css/custom-theme/jquery-ui-1.8.12.custom.css"  />

<style>
	.row{height:20px;width:600px;margin:1px auto; border:solid 1px #ccc;padding-top:4px}
	.cell{float:left;height:20px;width:120px;padding-left:10px;}
	body {background-color:#2e2e2e;}
	#demo-frame > div.demo { padding: 10px !important; }
	#eq span {
		height:120px; float:left; margin:15px
</style>
</head>
<body>
<%
String user="";
if (session.getAttribute( "userid" )==null)
	response.sendRedirect(response.encodeRedirectURL("/ourspaces/Lexicon/error.jsp"));
else
 user=session.getAttribute( "userid" ).toString();

session.setAttribute( "userid", null );

String root="/home/policygrid/apache-tomcat-6.0.18/data/similaritysurvey";
java.io.File file;
java.io.File dir = new java.io.File(root);
   
int id =dir.list().length+1;    //request.getParameter("id") ;

String[] simarr1= request.getParameterValues("sim1");
String[] pair= request.getParameterValues("pair");

System.out.print("i'm here ");
String disc=session.getAttribute( "disc" ).toString();
String english=session.getAttribute( "english" ).toString();
String age=session.getAttribute( "age" ).toString();

BufferedWriter out1;
int part =Integer.parseInt( session.getAttribute( "part" ).toString());

int start=0;
if (part==1)
	start=0;
else if (part==2)
	start=39;
else if (part==3)
	start=78;
try{
  System.out.print("i'm here Try");
	 out1 = new BufferedWriter(new FileWriter("/home/policygrid/apache-tomcat-6.0.18/data/similaritysurvey/respondent"+user+".csv"));
	 //out1.write(disc+System.getProperty("line.separator"));
	 int count=start+1;
	 for (int j=0; j<simarr1.length;j++)	{			
				 	
		 			out1.write(user  +","+disc +","+english+","+age+","+ count+"," +pair[j] + ","+simarr1[j]+System.getProperty("line.separator"));  
				 	//common.Utility.log.debug(resid   +","+ k  + ","+simarr1[j]+System.getProperty("line.separator"));  
					out1.flush();
					count++;
					
	 }
	 
	 out1.close();
	  

}catch (Exception e){//Catch exception if any
	  System.err.println("Error: " + e.getMessage());
	  }

%>
<div class="main" style="width:1000px;height:500px;margin:0px auto;padding: 10px;background-color:#FFFFCC;">
<div style="float:left;height:100px;width:950px;padding:20px">
<h3 style ="width:300px;margin: 0px auto">Evaluation of Word Relatedness</h3>
<div id="res" style="margin-left:auto;margin-right:auto;margin-top:200px;height:80px;width:610px;background-color:#FFFFCC;"><h4>Your evaluation has been submitted successfully. Thank you for participation in this  study.</h4></div>

</div>
</div>

</body>
</html>