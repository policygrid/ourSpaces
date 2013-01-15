function sendToServlet(string, userid)
	{
		var xmlHttpRequest=init();
	
		function init(){
			if (window.XMLHttpRequest) {
			   return new XMLHttpRequest();
			} else if (window.ActiveXObject) {      
			   return new ActiveXObject("Microsoft.XMLHTTP");
			}
		}
	
		
		xmlHttpRequest.open("GET", "Save?string="+ encodeURIComponent(string) + "&userid="+ encodeURIComponent(userid), true);
		xmlHttpRequest.onreadystatechange=processRequest;
		xmlHttpRequest.send(null);
	
		function processRequest(){
	
			if(xmlHttpRequest.readyState==4){
				if(xmlHttpRequest.status==200){
					// Do nothing
				}
			}
		}
	}