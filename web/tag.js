// JavaScript Document

function tag(url,target) {
    // native XMLHttpRequest object
    document.getElementById(target).innerHTML = 'Retrieving Tags...';
    if (window.XMLHttpRequest) {
        req = new XMLHttpRequest();
        req.onreadystatechange = function() {tagDone(target);};
        req.open("GET", url, true);
        req.send(null);
    // IE/Windows ActiveX version
    } else if (window.ActiveXObject) {
        req = new ActiveXObject("Microsoft.XMLHTTP");
        if (req) {
            req.onreadystatechange = function() {tagDone(target);};
            req.open("GET", url, true);
            req.send();
        }
    }
}    

function tagDone(target) {
    // only if req is "loaded"
    if (req.readyState == 4) {
        // only if "OK"
        if (req.status == 200) {
            results = req.responseText;
            document.getElementById(target).innerHTML = results;
        } else {
            document.getElementById(target).innerHTML="tag error:\n" +
                req.statusText;
        }
    }
}