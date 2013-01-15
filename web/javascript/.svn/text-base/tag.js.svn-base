function tag(url,target) {
    // native XMLHttpRequest object
    document.getElementById(target).innerHTML = 'Refreshing page...';
    jQuery.get(url, function(data) {
    	document.getElementById(target).innerHTML = data;
     });
}    