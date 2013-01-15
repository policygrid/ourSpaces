function tag(url,target) {
    document.getElementById(target).innerHTML = '<img src="/ourspaces/images/ajax_loader.gif" />'; 
    jQuery.get(url, function(data) {
    	document.getElementById(target).innerHTML = data;
     });      
}    
