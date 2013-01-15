<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<% User user = new User(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>ourSpaces</title>
<link rel="stylesheet" type="text/css" href="style.css" />

<script type="text/javascript" src="javascript/tab.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/jquery-1.6.2.min.js"></script> 
<script type="text/javascript" src="/ourspaces/javascript/jquery-ui-1.8.16.custom.min.js"></script>
<link href="facebox.css" media="screen" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" type="/ourspaces/text/css" href="/ourspaces/css/custom-theme/jquery-ui-1.8.12.custom.css"  />
<script src="facebox.js" type="text/javascript"></script> 

<script type="text/javascript" language="javascript">

</script>

<script language="javascript" type="text/javascript">
function stopRKey(evt) {
   var evt = (evt) ? evt : ((event) ? event : null);
   var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
   if ((evt.keyCode == 13) && (node.type=="text")) {return false;}
}
document.onkeypress = stopRKey;



jQuery(document).ready(function($) {		

	 $("#forgot").click(function() {
		    var $link = $(this);
			var $dialog = $('<div></div>')
				.load($link.attr('href'))
				.dialog({
					autoOpen: false,
					title: $link.attr('title'),
					modal: true,
					height: 'auto',
		            width: 'auto' ,
		            position: [(($(window).width()/2)-300),50],
		            open: function(event, ui) {
		            
		            },
				    buttons: {
				        "Request new Password": function() {
				        	changePass()
				  	    }
				    }//END OF BUTTONS
				
				});   			    
			$dialog.dialog('open');
			return false;
	});
	
	 $("#register").click(function() {
		    var $link = $(this);
			var $dialog = $('<div></div>')
				.load($link.attr('href'))
				.dialog({
					autoOpen: false,
					title: $link.attr('title'),
					modal: true,
					height: 'auto',
		            width: 'auto' ,
		            position: [(($(window).width()/2)-300),50],
		            open: function(event, ui) {
		            
		            },
				    buttons: {
				        "Register": function() {
				            $("#target").submit();
				  	    }
				    }//END OF BUTTONS
				
				});   			    
			$dialog.dialog('open');
			return false;
	}); 
	 
	 
});


</script>

<link rel="stylesheet" type="text/css" href="style.css" />

</head>

<body>

<div class="header">
	<div class="headerIndexLogo"></div>
</div>
<div class="indexBG">
	<div class="indexCenterPanel">
    	<div class="indexCenterPanelText">
        	<p style="margin-bottom:10px; color:#990000;">The ourSpaces virtual research environment is designed to allow researchers to manage their digital resources through a series of dynamic and collaborative workspaces.</p>
        	<p style="margin-bottom:30px;">Users within ourSpaces form part of a large social network which facilitates functionality such as resource uploading, tagging, blogging and more.</p>
            <p style="margin-bottom:10px; color:#FF6600; font-size:26px;">Login</p>
            <div class="loginForm">
                <form name="login" method="post" action="Controller?action=login">
                	<p><span style="margin-left:30px;">Email</span> <span style="margin-left:90px;">Password</span></p>
                    <input name="username" type="text" style="border:1px solid #999999; padding:1px; margin-left:30px;" />
                    <input name="password" type="password" style="border:1px solid #999999; padding:1px;"/>
                    <input type="image" src="images/login.png" style="margin-left:137px; margin-top:10px;" />
                </form>
                <p style="margin-top:30px; margin-left:85px;">
                	<a id="register" title="Register with ourSpaces" href="register.jsp" ><img src="images/register2.png" style="border:none;" /></a>
                </p>
                <p style="margin-top: 10px;"><a id="forgot" title="Forgotten your passwsord?" href="changePassword.jsp">Forgotten your password?</a></p>
            </div>
    	</div>
    </div>
</div>




</body>
</html>
