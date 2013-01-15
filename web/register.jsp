

<script language=javascript type=text/javascript>
function stopRKey(evt) {
   var evt = (evt) ? evt : ((event) ? event : null);
   var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
   if ((evt.keyCode == 13) && (node.type=="text")) {return false;}
}

document.onkeypress = stopRKey;
</script>



  <script type="text/javascript">

function checkUser(){

	var username=document.getElementById("email");

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

		xmlHttpRequest.open("GET", "Checker?username="+ encodeURIComponent(username.value), true);
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
			
			var valid=xmlMessage.getElementsByTagName("valid")[0].firstChild.nodeValue;
			
			if(valid=="true")
				
			{
				document.getElementById('emailConf').style.display = 'block';
				document.getElementById('rightUser').style.display = 'none';
				document.getElementById('submitForm').enabled=true;
			}
			if(valid=="false")
			{
				document.getElementById('rightUser').style.display = 'block';
				document.getElementById('submitForm').disabled=true;
				document.getElementById('emailConf').style.display = 'none';
			}
		}
}



</script>

<script language="javascript">

  
  function checkPass() {
	var pass1 = document.getElementById('password2').value;
	var pass2 = document.getElementById('passconfirm2').value;
	
	if(pass1 == pass2) {
		document.getElementById('passConf').style.display = 'block';
		document.getElementById('submitForm').disabled=false;
		document.getElementById('rightPass').style.display = 'none';
	}
	else {
		document.getElementById('rightPass').style.display = 'block';
		document.getElementById('submitForm').disabled=true;
		document.getElementById('passConf').style.display = 'none';
	}
  }



function checkform()
{
	if (!document.getElementById('agree').checked)
	{
		alert('You must accept the Terms and Condition before registering.');
		return false;
	} else if (document.getElementById('email').value == '') {
		alert('You must complete the form before registering.');
		return false;
	} else if (document.getElementById('password2').value == '') {
		alert('You must complete the form before registering.');
		return false;
	} else if (document.getElementById('passconfirm2').value == '') {
		alert('You must complete the form before registering.');
		return false;
	} else if (document.getElementById('firstname').value == '') {
		alert('You must complete the form before registering.');
		return false;
	} else if (document.getElementById('lastname').value == '') {
		alert('You must complete the form before registering.');
		return false;
	}

	return true;
}

</script>
  <style type="text/css">
<!--
.style1 {
	color: #999999;
	font-style: italic;
}
-->
  </style>
  
<p style="color:#999; font-size:24px; margin: 0 auto;" align="center">Register with ourSpaces</p>

<div style="position:relative;  width: 500px;">

	
	<div id="terms" style="position: relative; float:left; margin-bottom:10px;">
	
		<div style="width:480px; position: relative; float:left; padding:10px;  margin-top:10px;">
		<p>Terms and Conditions:</p>
			
			<textarea style="width:480px; height:150px;">
ourSpaces is a Virtual Research Environment (VRE) developed by the University of Aberdeen as part of the PolicyGrid II research project. PolicyGrid is supported by the Economic and Social Research Council, project number: RES-149-25-1075.

PolicyGrid II is a research project examining interdisciplinary research and developing computational tools to support interdisciplinary research teams. As such we will be exploring the ways in which interdisciplinary research teams communicate and co-operate via ourSpaces. 
				
We make use of the data generated by users (for example; meta-tagging, user interactions) only to support the academic research of PolicyGrid II. Selected elements of this research project may be subject to additional approval; we will seek consent from associated participants as appropriate. 
				
Data that you upload into ourSpaces is stored securely; our servers are located in a secure environment and server administration is handled only by members of University of Aberdeen staff working on the PolicyGrid II project. We strongly recommend that you keep alternative copies of your data as we will not be liable for loss or damage to data while it is stored within our systems. 
				
Data resources which you upload can be made private or public; private resources are inaccessible to all but the owner and any additional members who the owner permits access.
				
ourSpaces must not be used for any commercial purpose whatsoever or for any illegal or unauthorised purpose. When you use ourSpaces you must comply with all applicable UK laws relating to online conduct (including, without limitation, content which can be posted online) and with any applicable international laws, including the local laws in your country of residence.
				
All user profiles are public on ourSpaces and therefore can be viewed by any registered member. Any person can become a registered member simply by completing the registration process and agreeing to these terms and conditions.
				
You may remove your profile and content at any time however it is important to be aware that we may be unable to delete certain elements related to uploaded content, for example commentary relating to a document cannot be removed but will instead be anonymised.  
				
This service complies with the University of Aberdeen - Policy on Data Protection (http://www.abdn.ac.uk/hr/uploads/files/data%20protection.pdf) and is governed by the University of Aberdeen - Conditions for using IT Facilities (http://www.abdn.ac.uk/dit/documents/cond-IT.pdf).

Note: please contact info@ourspaces.net if you wish to use ourSpaces but do not wish to be included in our research.
			</textarea>
			
			
				
		</div>
	</div>
	

	
<div id="terms" style="position: relative; float:left; margin-bottom:10px;">
<div style="width:480px; position: relative; float:left; padding:10px;">		
		
	<p style="margin-bottom:10px; font-size:10px;">
		Please contact us if you have any queries about our terms or our privacy policy. 
	</p>  
	<p style="margin-bottom:10px; color: #FF6600;">
		<input type="checkbox" name="agree" id="agree"> I agree to the Terms and Conditions <br><br>
	</p>
    	
    <form id="target" method="post" style="float:left; margin-top:5px;" action="Controller?action=register" onSubmit="return checkform()">
    <p style="font-size:9px; color: red;">Please enter a valid email address. An email will be sent to your supplied address in order to confirm your profile.</p>
    <br />
              <div class="regLeftCont">
                <div class="regLeftNames"><p>Email:</p></div>
            	<input type="text" name="email" id="email" onblur="checkUser()" class="input" />
            	<div id="emailConf" style="position: relative; float: left; width:16px; margin-left:10px; display:none;"><img src="images/tick.jpg" style="position: relative; float: left; margin:0px; padding: 0px;"></img></div>
              </div>
              <div class="regLeftCont" id="rightUser" style="display:none;">
                <p align="center" style="font-size:9px; color: red;">That email has already been registered.</p>
              </div>
              <div class="regLeftCont">
                <div class="regLeftNames"><p>Password:</p></div>
            	<input type="password" name="password" id="password2" class="input" />
              </div>
              <div class="regLeftCont">
                <div class="regLeftNames"><p>Confirm Password:</p></div>
            	<input type="password" name="passconfirm" id="passconfirm2" onblur="checkPass()" class="input" />
            	<div id="passConf" style="position: relative; float: left; width:16px; margin-left:10px; display:none;"><img src="images/tick.jpg" style="position: relative; float: left; margin:0px; padding: 0px;"></img></div>
              </div>
              <div class="regLeftCont" id="rightPass" style="display:none;">
                <p align="center" style="font-size:9px; color: red;">Your passwords do not match.</p>
              </div>
              <div class="regLeftCont">
                <div class="regLeftNames"><p>First Name:</p></div>
            	<input type="text" name="firstname" id="firstname" class="input" />
              </div>
              <div class="regLeftCont">
                <div class="regLeftNames"><p>Last Name:</p></div>
            	<input type="text" name="lastname" id="lastname" onblur="checkName()" class="input" />
              </div>

              <div class="regLeftCont">
              	<input type="submit" value="Register" id="submitForm" />
                <input type="reset" value="Clear" />
             </div>
       </form>
			

  </div>         
</div>

</div>
   
  