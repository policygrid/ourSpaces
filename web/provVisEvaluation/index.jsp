<%@ page language="java" import="java.io.File, java.util.ArrayList,java.util.Scanner, java.io.*,java.util.Random, java.net.*, java.util.Vector, common.*,common.Utility,common.User" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:include page="head.jsp" />

<body>
<form method=post action="evaluation.jsp" id="survey">
<%

session.invalidate();
session = request.getSession(true); 

Random randomGenerator = new Random();
int randomInt = randomGenerator.nextInt(4);
session.setAttribute( "type", randomInt );
session.setAttribute( "user", session.hashCode() );
session.setAttribute("completedGraph", false);
session.setAttribute("completedNLG", false);
session.setAttribute("completedCombination", false);

Login login = new Login();
int i = 0; // Integer for retrieving user ID
// A value greater than 0 will be returned if the user exists
i = (int) login.checkDetails("test", "aaa");
// Create a User JavaBean to identify each user
User user = new User();
user.setID(session.hashCode());
user.setRDFID(login.getRDFID(i));
user.setFOAFID(user.getFOAFID(i));
session.setAttribute("use", user);

%>
  
<div class="main" style="">
<div style="padding:60px">
<h1 style ="    text-align: center;">Evaluation of provenance visualisation techniques</h1>
<p>We have developed two different approaches to visualise meta-data: one
using graphs and the other, automatically generated natural language
descriptions.<br>

This experiment will evaluate how good these methods are in presenting
certain kinds of information to users.<br>

<h2>The evaluation procedure</h2>
The experiment requires you to watch a short video, before trialling one
of the visualisation methods, and then using the tool to find information
to help you answer a number of questions. In the second part of the
experiment, you will watch another video, before using a hybrid approach which
combines both of the visualisation methods.<br><br><br>

We will record the answers you provide, as well as the time required to
answer the questions.


</p>
<!-- <h2>The evaluation procedure</h2><p >
<ul ><li>First, you will be presented with a short introductory video of one of the visualisation tools (Graphical or NLG). On the next page, you will have two examples, which you can explore using the same visualisation tool.
 <br></li><li>
Then, a provenance of another artefact will be presented to you and you will be asked to answer three questions about the provenance record.
You can find the answers using the visualisation tool. We will record the answers you provided, as well as the time required to answer the questions.
<br></li><li>
In the next section, you will be again presented with a video about the other visualisation tool and two examples, too.
On the following page, we will ask you to answer another set of questions about a provenance record. Now, you can use either or both visualisation tools to find the necessary information.
<br></li><li>
Finally, we will ask you to complete a set of overall feedback questions.
</li></ul></p> -->
<p ><b>Please note that the graphical visualisation supports only modern browsers: Firefox 6.0+, Chrome, IE 9.0+, Opera 11.0+, Safari 6.0+.</b></p>
</div>
       
	<a style="    color: white;display: block; left: 10px; position: relative; width: 55px;;" href="evaluation.jsp"><span style="background-color: darkblue; border-radius: 5px 5px 5px 5px; display: block; padding: 10px; position: relative;">Next</span></a>     
</div>
</form>
</body>
</html>