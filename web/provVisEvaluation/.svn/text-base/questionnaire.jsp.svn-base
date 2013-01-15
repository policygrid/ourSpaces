<%@ page language="java" import="java.io.File, java.util.ArrayList,java.util.Scanner, java.io.*,java.util.Random, java.net.*, java.util.Vector, common.*,common.Utility,common.User" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:include page="head.jsp" />
<body>

<div class="main" style="">
<h1 style="    text-align: center;;">Thank you!</h1>
<p class="box">Thank you for participating in our study. Please complete this final questionnaire to help us interpret the results correctly.
	</p>
	<form ACTION="sendQuestionnaire.jsp"  METHOD="POST">
	
	<div style=";;;width:100%"> 
	  <div id="questions" style="width:98%">
		<div id = "q1body" class="questionBody" style="">		
			<p >For the following statements, please mark on scale 1-5 how you agree with the statement, 1 meaning not at all, 5 meaning completely.</p>
			
		<table style="width:100%">
		<tr>
			<td> </td><td style="text-align: center;">1</td><td style="text-align: center;">2</td><td style="text-align: center;">3</td><td style="text-align: center;">4</td><td style="text-align: center;">5</td>
			
		</tr>
		<tr><td style="width: 82%;">The graphical visualisation was clear, I could instantly see what information it represents.</td><td><label for="q11"><input type="radio" name="q1" class="value" value="1" id="q11"></label></td><td><label for="q12"><input type="radio" class="value" name="q1" value="2" id="q12"></label></td><td><label for="q13"><input type="radio" class="value" name="q1" value="3" id="q13"></label></td><td><label for="q14"><input type="radio" class="value" name="q1" value="4" id="q14"></label></td><td><label for="q15"><input type="radio" class="value" name="q1" value="5" id="q15"></label></td></tr>
		<tr><td style="width: 82%;">The natural language representation was clear, I could instantly see what information it represents.</td><td><label for="q21"><input type="radio" name="q2" class="value" value="1" id="q21"></label></td><td><label for="q22"><input type="radio" class="value" name="q2" value="2" id="q22"></label></td><td><label for="q23"><input type="radio" class="value" name="q2" value="3" id="q23"></label></td><td><label for="q24"><input type="radio" class="value" name="q2" value="4" id="q24"></label></td><td><label for="q25"><input type="radio" class="value" name="q2" value="5" id="q25"></label></td></tr>
		<!-- <tr><td style="width: 82%;">I would rather use natural language representation than graphical representation.</td><td><label for="q31"><input type="radio" name="q3" class="value" value="1" id="q31"></label></td><td><label for="q32"><input type="radio" class="value" name="q3" value="2" id="q32"></label></td><td><label for="q33"><input type="radio" class="value" name="q3" value="3" id="q33"></label></td><td><label for="q34"><input type="radio" class="value" name="q3" value="4" id="q34"></label></td><td><label for="q35"><input type="radio" class="value" name="q3" value="5" id="q35"></label></td></tr>-->
		<tr><td style="width: 82%;">It was easier to find the information with both visualisations on the same page - I liked some aspects of one but also some aspects of the other.</td><td><label for="q41"><input type="radio" name="q4" class="value" value="1" id="q41"></label></td><td><label for="q42"><input type="radio" class="value" name="q4" value="2" id="q42"></label></td><td><label for="q43"><input type="radio" class="value" name="q4" value="3" id="q43"></label></td><td><label for="q44"><input type="radio" class="value" name="q4" value="4" id="q44"></label></td><td><label for="q45"><input type="radio" class="value" name="q4" value="5" id="q45"></label></td></tr>
		<tr><td style="width: 82%;">Without the tutorial video and examples, I wouldn't know how to use graphical representation.</td><td><label for="q51"><input type="radio" name="q5" class="value" value="1" id="q51"></label></td><td><label for="q52"><input type="radio" class="value" name="q5" value="2" id="q52"></label></td><td><label for="q53"><input type="radio" class="value" name="q5" value="3" id="q53"></label></td><td><label for="q54"><input type="radio" class="value" name="q5" value="4" id="q54"></label></td><td><label for="q55"><input type="radio" class="value" name="q5" value="5" id="q55"></label></td></tr>
		<tr><td style="width: 82%;">Without the tutorial video and examples, I wouldn't know how to use natural language representation.</td><td><label for="q61"><input type="radio" name="q6" class="value" value="1" id="q61"></label></td><td><label for="q62"><input type="radio" class="value" name="q6" value="2" id="q62"></label></td><td><label for="q63"><input type="radio" class="value" name="q6" value="3" id="q63"></label></td><td><label for="q64"><input type="radio" class="value" name="q6" value="4" id="q64"></label></td><td><label for="q65"><input type="radio" class="value" name="q6" value="5" id="q65"></label></td></tr>
		<tr><td style="width: 82%;">Using the combination of visualisations was easier, because I already learned something from the first task using only one visualisation tool.</td><td><label for="q71"><input type="radio" name="q7" class="value" value="1" id="q71"></label></td><td><label for="q72"><input type="radio" class="value" name="q7" value="2" id="q72"></label></td><td><label for="q73"><input type="radio" class="value" name="q7" value="3" id="q73"></label></td><td><label for="q74"><input type="radio" class="value" name="q7" value="4" id="q74"></label></td><td><label for="q75"><input type="radio" class="value" name="q7" value="5" id="q75"></label></td></tr>		
		</table>
		<!-- 	<style>
			.label{    display: inline-block;
    position: relative;
    width: 21px;}
    .value{
    width:13px
    }</style>
			<p style="float:right; margin-right: 0; position: relative;width:105px; "><span class="label">1</span><span class="label">2</span><span class="label">3</span><span class="label">4</span><span class="label">5</span></p>
			<div style="    display: block;
    float: left;
    height: 1px;
    position: relative;
    width: 100%;"></div>
			<div style="width:350px;    float: left;"></div><div style="width:112px;    float: right;"></div>
			
			 -->
		</div>	  	
		
		<div id = "q3body" style="" class="questionBody">		
			<h4 style="text-align: left;">Which of the visualisation tools did you prefer?</h4>
			<label for=q81><input id="q81" style="margin:5px;" type="radio" name="q8" value="1">Natural Language Generation</label><br/>
			<label for=q82><input id="q82" style="margin:5px;" type="radio" name="q8" value="2">Graphical Visualisation</label><br/>
			<label for=q83><input id="q83" style="margin:5px;" type="radio" name="q8" value="3">Don't care</label><br/>
			<label for=q9>Please include the reason why:</label><br/>
			<textarea id=q9 style="margin:5px;" cols="50"  name="q9"></textarea><br/>
		</div>
		<div id = "q3body" style="" class="questionBody">		
			<h4 style="text-align: left;">What is your main academic discipline?</h4>
			<input type="text" id=q10 style="margin:5px;width:300px" name="q10"></input><br/>
			<h4 style="text-align: left;">Select your age group:</h4>
			<select name=q11 style="width: 80"><option value=20-29>20-29</option><option value=30-39>30-39</option><option value=40-49>40-49</option><option value=50-59>50-59</option><option value=over60>over 60</option></select> 

		</div>	
		<div id = "q3body" style="" class="questionBody">		
			<h4 style="text-align: left;">Were you satisfied with the provenance information visualisations in general? Do you have any comments, feedback, improvements, suggestions?</h4>
			<textarea id=q12 style="margin:5px;" cols="50" rows="5" name="q12"></textarea><br/>
		</div>
	  </div>    
	</div>			
	<INPUT style="cursor: pointer;text-decoration: underline;background-color: darkblue;border: 0 none;border-radius: 5px 5px 5px 5px;color: white;float: right;font-family: times;font-size: 14px;padding: 10px;" TYPE="SUBMIT" VALUE="Submit">
	</form>
	
	<!-- <a onclick="sendResults();return true;" id="submit" style="color:white;" href="end.jsp" ><span style="border-radius:5px;display: block;    margin-left: 5px;padding: 10px;width: 50px;;background-color:darkblue;">Submit</span></a>   -->      
</div>    

<script>
function sendResults(){
	var json = new Object();
	json.q1 = $('input:radio[name=q1]:checked').val();
	json.q2 = $('input:radio[name=q2]:checked').val();
	json.q3 = $('input:radio[name=q3]:checked').val();
	json.q4 = $('input:radio[name=q4]:checked').val();
	json.q5 = $('input:radio[name=q5]:checked').val();
	json.q6 = $('input:radio[name=q6]:checked').val();
	json.q7 = $('input:radio[name=q7]:checked').val();

	json.q8 = $('input:radio[name=q8]:checked').val();
	json.q9 = $('#q9').val();
	json.q10= $('#q10').val();
	json.q11 = $('select[name=q11] option:selected').val();
	json.q12 = $('#q12').val();
	var jsonString = JSON.stringify(json);
	$.post("/ourspaces/provVisEvaluation/sendQuestionnaire.jsp",{json:jsonString},
			  function(data2) {
			}
	);
	//alert('Thank you very much for your efforts!');
	//window.location.href = 'http://www.ourspaces.net';
	return true;

}
</script> 
</body>
</html>