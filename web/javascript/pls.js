$(document).ready(function() 
				{
	annotateTerms();

				});
		

function annotateTerms(){
	
	var list="";
	$.ajax({
		type: 'GET',
		url: "/ourspaces/plsservlet?type=list" , 
		dataType : "html",
		async : false,
		success : function(data, errorThrown) {
			
			 list=data.substring(1,data.length-1);
			 
			 
		}
	});
	//alert (list);
	var arr=list.split(", ");
	$('.plscontent').each(function(index){
	var text=$(this).text();
	var x=0;
	var txtarr=text.split(' ');
	//alert(txtarr);
	for (x =0; x<txtarr.length;x++){
		var word =txtarr[x];
		word=word.toLowerCase();
		word=$.trim(word);
		word =word.replace(".","");
		//alert(jQuery.inArray(word, arr));
		if((jQuery.inArray(word, arr) !=-1)&&(word.length!=0)){
			//alert(txtarr[x]);
			var regword= new RegExp(word, "gi");

			var repword='<span  class ="pls" style ="cursor:pointer">'+word+'</span>';
			$(this).html($(this).html().replace(regword, repword));
			
		}
	}
	
	
	
	
	});
	$('.pls').qtip(
			   {
				   hide: { when: 'mouseout', fixed: true ,delay: 1000},
			      content: 'Default content for the tooltip', // Give it some content, in this case a simple string ,
			      style: {
			           padding: '10px', // Give it some extra padding
			           background: '#ffffcc',
			           tip: { color: 'black' } 
			        },
			        
			   	  api: {
			    	         // Retrieve the content when tooltip is first rendered
			    	         onRender: function()
			    	         {
			    	            var self = this;
			    	            $.ajax({
			    					type: 'GET',
			    					url: "/ourspaces/plsservlet?type=rt&word="+self.elements.target.text() , 
			    					dataType : "html",
			    					async : false,
			    					success : function(newStr, errorThrown) {
			    						
			    						self.updateContent(newStr);
			    						
			    						log('log_plsUsage(userid,resid,term_viewed)',userID+',\''+document.URL+'\',\''+self.elements.target.text()+'\'');
			    						
			    					}
			    				});
			    	               // Update the tooltip with the retrieved translation
			    	               
			    	            
			    	         }
			   	}
			   });
}


function getObjectName(objtype,objid){
	var name="";
	$.ajax({
		type: 'GET',
		url: "/ourspaces/getObjName?type="+objtype+"&id="+objid , 
		dataType : "html",
		async : false,
		success : function(jsonstr, errorThrown) {
		
			name=jsonstr;
			
			
		}
	});
	return name;
}