<%@ page language="java" import="java.io.File, java.util.ArrayList,java.util.Scanner, java.io.*,java.util.Random, java.net.*, java.util.Vector, common.*,common.Utility,common.User" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/ourspaces/javascript/jquery-1.6.2.min.js"></script> 
<script type="text/javascript" src="/ourspaces/javascript/jquery-ui-1.8.16.custom.min.js"></script>
<link rel="stylesheet" type="text/css" type="text/css" href="/ourspaces/css/custom-theme/jquery-ui-1.8.12.custom.css"  />

<style>
	.row{height:20px;width:680px;margin:1px auto; border:solid 1px #ccc;padding-top:4px}
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
		$("input").val("");
	
	
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
<form method=post action="evaluation.jsp" id="survey">
<%
Random randomGenerator = new Random();
int randomInt = randomGenerator.nextInt(99999);
session.setAttribute( "userid", randomInt );

%>
  
<div class="main" style="width:900px;height:560px;margin:0px auto;padding: 5px;background-color:#FFFFCC;">
<div style="float:left;padding:60px">
<h3 style ="width:280px;margin: 0px auto">Evaluation of Word Relatedness</h3>
<p>In order to investigate the vocabulary differences of users, we have carried out an experiment using two text collections from two authors, in which similar word pairs are extracted from corresponding collections using natural language processing (NLP) algorithms.</p>
<p>In the next page you will see 39 unique word pairs identified by 3 NLP algorithms as "similar" words (including some artificially inserted word pairs as controls). If you think that words within the pair can be used <b>interchangeably in some context (information searching, describing something, etc)</b> give them a relative score based on your judgment of similarity of the word pair. The similarity score can be varied from 0 to 100 where 0 indicates no similarity at all and 100 indicates that words are completely interchangeable.  </p>
<div style="border:1px solid #ccc;padding:10px">Ex:<br/>"Pre heat", "turn on" do not mean the same. But in the context of "........ the oven", we could be talking about the same action.</div>
<p>(Note that we do not store any identity information.)</p>
<div style="width:400px;margin:2px auto;">
<span style="width:350px;display:block;float:left;padding:5px">What is your discipline? : <input id ="dec" type="text" name="disc" /></span>
<span style="width:300px;display:block;float:left;padding:5px">Is English your first language? <select name = english style = "Width: 50"><option value = yes>yes</option><option value = no>no</option></select></span>
<span style="width:300px;display:block;float:left;padding:5px">Select your age group : <select name = age style = "Width: 80"><option value = 20-29>20-29</option><option value = 30-39>30-39</option><option value = 40-49>40-49</option><option value = 50-59>50-59</option><option value = over60>over60</option></select></span> 
</div>


<div class="row ui-state-highlight ui-corner-all" style="float:left;height:30px;width:100%">


                         <a id ="submit" class="fg-button ui-state-active fg-button-icon-right ui-corner-all"  style ="color:#fff;padding:2px;float:right;margin-right:30px" >Next</a>
                         <a id ="clear" class="fg-button ui-state-active fg-button-icon-right ui-corner-all" style ="color:#fff;padding:2px;float:right;margin-right:30px" >Clear</a>
                 
</div>
</div>



</div>



</form>
</body>
</html>