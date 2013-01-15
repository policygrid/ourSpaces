<%@ page language="java" import="java.io.File, java.util.ArrayList,java.util.Scanner, java.io.*,java.util.Random, java.net.*, java.util.Vector, common.*,common.Utility,common.User" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/ourspaces/javascript/jquery-1.6.2.min.js"></script> 
<script type="text/javascript" src="/ourspaces/javascript/jquery-ui-1.8.16.custom.min.js"></script>
<link rel="stylesheet" type="text/css" type="text/css" href="/ourspaces/css/custom-theme/jquery-ui-1.8.12.custom.css"  />

<style>
	.row{height:20px;width:680px;margin:1px auto; border:solid 1px #ccc;padding-top:1px}
	.cell{float:left;height:20px;width:120px;padding-left:10px;}
	body {background-color:#2e2e2e;}
	#demo-frame > div.demo { padding: 10px !important; }
	#eq span {
		height:120px; float:left; margin:15px
</style>
<!-- 	<script>
	$(function() {
		$( ".slider" ).slider({
			value:0,
			min: 0,
			max: 100,
			step: 5,
			slide: function( event, ui ) {
				$( ".amount" ).val(  ui.value +"%");
			}
		});
		$( ".amount" ).val(  $( ".slider" ).slider( "value" )+"%" );
	});
	</script> -->
	<script>
	$(document).ready(function(){
		
		$("#submit").click(function(){
		$("#survey").submit();
	
		
		});
		$("#clear").click(function(){
			var i=0;
			// setup graphic EQ
			$( ".eqq" ).each(function() {
				// read initial values from markup and remove that
			
				$( this ).slider({
					value: 0,
					min: 0,
					max: 100,
					step: 5,
					animate:true,
					orientation: "horizontal",
					slide: function( event, ui ) {
						$(this).siblings().val(ui.value );
					}
				});
				$(this).siblings().val(  $( this ).slider( "value" ));
			});
		});
		$("input.amount").val("");
	
	
	});
	
	$(function() {
		var i=0;
		// setup graphic EQ
		$( ".eqq" ).each(function() {
			// read initial values from markup and remove that
		
			$( this ).slider({
				value: 0,
				min: 0,
				max: 100,
				step: 5,
				orientation: "horizontal",
				slide: function( event, ui ) {
					$(this).siblings().val(ui.value );
				}
			});
			$(this).siblings().val(  $( this ).slider( "value" ) );
		});
	});
	</script>
</head>

<body>
<form method=post action="success.jsp" id="survey">
<%
String user="";
if (session.getAttribute( "userid" )==null)
	response.sendRedirect(response.encodeRedirectURL("/ourspaces/Lexicon/evaluation/error.jsp"));
else
	 user=session.getAttribute( "userid" ).toString();



%>
<div class="main" style="width:1000px;height:1050px;margin:0px auto;padding: 5px;background-color:#FFFFCC;">
<div style="float:left;width:970px;padding-top:15px">
<h3 style ="width:280px;margin: 0px auto">Evaluation of Word Relatedness</h3>
<%-- <p>Please score the similarity of word pairs. You evaluating part <%=session.getAttribute( "part" ).toString() %></p> --%>
</div>
<div id ="eval" style="float:left;width:100%;background-color:#FFFFCC;">
<div style="margin-left:auto;margin-right:auto;width:680px;background-color:#fff;">
<div class="row" style="font-weight: :bold">
	<div class="cell" style="width:50px">No</div>
	<div class="cell">Word1</div>
	<div class="cell">Word2</div>
	<div class="cell">Similarity Score</div>
	<div class="cell" style="width:220px">(Slide the handle to set the score)</div>
</div>

<%
try{
	BufferedWriter out1;
		 out1 = new BufferedWriter(new FileWriter("/home/policygrid/apache-tomcat-6.0.18/data/similaritysurvey/respondent"+user+".csv"));
}catch (Exception ex){
	
	
}
String disc=request.getParameter("disc");
String english=request.getParameter("english");
String age=request.getParameter("age");

session.setAttribute( "disc", disc );
session.setAttribute( "english", english );
session.setAttribute( "age", age );
String root="/home/policygrid/apache-tomcat-6.0.18/data/similaritysurvey";
java.io.File file;
java.io.File dir = new java.io.File(root);
   
int part =(dir.list().length % 3)+1;    //request.getParameter("id") ;
session.setAttribute( "part", part );


int start=0;
if (part==1)
	start=0;
else if (part==2)
	start=39;
else if (part==3)
	start=78;
%>

<%

 Scanner scanner = new Scanner(new FileReader("/home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/Lexicon/topres.csv"));
try {
	Vector<String> words=new Vector<String>();
	 while ( scanner.hasNextLine()){
	   	
   	  	String aline=scanner.nextLine();
   	  	words.add(aline);
   	  /* 	String w1=aline.split(",")[0];
   		String w2=aline.split(",")[1]; */
    	
	 }
	 common.Utility.log.debug(words.size());
	int count=0;
	 int end=start+39;
	  for (int idx = start; idx < end;idx++){
		  count++;
	  
	     // int randomInt = randomGenerator.nextInt(94);
	      String pair=words.get(idx);
	      String w1=pair.split(",")[0];
	   	 String w2=pair.split(",")[1];
	    
   	%>
   	
   	<div class="row">
   		<div class="cell" style="width:50px"><%=count+"." %></div>
   		<div class="cell" style="font-weight:bold"><%=w1 %><input type="hidden"  name="pair" value="<%=w1+","+w2%>"/></div>
   		<div class="cell" style="font-weight:bold"><%=w2 %></div>
   		
   		<div style="width:200px;float:left;margin-left:70px"><div style="width:250px" class="eqq"></div><input name ="sim1" type="text" class="amount" style="border:0;  font-weight:bold;font-size:medium;margin-left:-50px;margin-top:-15px;width:40px;height:18px" />
   		</div>
   	</div>
   	 
   	<%
	    }
	
	
	
}catch ( Exception Ex){ 
	
	common.Utility.log.debug(Ex.getMessage());
}
%>

<div class="row ui-state-highlight ui-corner-all" style="height:30px">


                         <a id ="submit" class="fg-button ui-state-active fg-button-icon-right ui-corner-all"  style ="color:#fff;padding:2px;float:right;margin-right:30px" >Submit</a>
                         <a id ="clear" class="fg-button ui-state-active fg-button-icon-right ui-corner-all" style ="color:#fff;padding:2px;float:right;margin-right:30px" >Clear</a>
                 
</div>

</div>
</div>


</div>



</form>
</body>
</html>