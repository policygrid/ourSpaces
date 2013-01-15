<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Change Password</title>

<script type="text/javascript">

function changePass()
{

	var email=document.getElementById("email");

		var xmlHttpRequest=init();
	
	  	function init()
	  	{
			if (window.XMLHttpRequest)
			{
		           return new XMLHttpRequest();
		    }
		    else if (window.ActiveXObject) 
		    {  
		           return new ActiveXObject("Microsoft.XMLHTTP");
		    }
		}	

		xmlHttpRequest.open("GET", "ChangePasswordServlet?action=new&email="+ encodeURIComponent(email.value), true);
		xmlHttpRequest.onreadystatechange=processRequest;
		xmlHttpRequest.send(null);
		
		function processRequest()
		{	
			if(xmlHttpRequest.readyState==4)
			{
			   if(xmlHttpRequest.status==200)
			   {
			    
			      processResponse();
			
			   }
			}
		}
		
		function processResponse(){
		
			var xmlMessage=xmlHttpRequest.responseXML;
			
			var valid=xmlMessage.getElementsByTagName("message")[0].firstChild.nodeValue;
			
			if(valid=="true")
				
			{
				document.getElementById('confirm').innerHTML = '<p>An email has been sent to the address given.</p>';

			}
			if(valid=="false")
			{
				document.getElementById('confirm').innerHTML = '<p>Sorry, but we do not have a record of that address.</p>';
			}
		}
}



</script>

</head>
<body>
	<p>Please provide us with your registered email address. A new temporary password will be emailed to you.</p>
	<p>&nbsp;</p>
	<p>Email: <input type="text" id="email" name="email" /></p>
	<p>&nbsp;</p>
	<div id="confirm"></div>
</body>
</html>