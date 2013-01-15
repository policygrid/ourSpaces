        <script src="/ourspaces/jqueryFileTree.js" type="text/javascript"></script>

		
		
		<script type="text/javascript">
			
			$(document).ready( function() {
				
				$('#fileTreeProject').fileTree({ root: '/', script: 'jqueryFileTree.jsp' }, function(file) { 

	              jQuery.get("/ourspaces/browseInfo.jsp?resource="+file, function(data) {
	            	  document.getElementById("resInfo").innerHTML = data;
	            	  initClasses();
// 	            	  var $dialog = $(data).dialog({
// 							autoOpen: false,
// 							title: 'Resource Details',
// 							modal: true,
// 							height: 'auto',
// 				            width: 'auto' ,
// 				            position: [(($(window).width()/2)-300),50],
// 				            open: function(event, ui) {
// 				            	$('#post1').ourSpacesProvenanceTagging();
// 				            },
// 						    buttons: {
// 						    }//END OF BUTTONS
						
// 						}); 
// 	            	  	$dialog.dialog('open');
				    });
						
				});
				
				
			});
		 </script>
		 
		 <div id="fileTreeProject" class="demo"></div>	 