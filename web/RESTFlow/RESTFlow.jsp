<jsp:include page="/topslim.jsp" />





<style type="text/css">/* demo */
._jsPlumb_connector { z-index:4; }
._jsPlumb_endpoint { z-index:5; }
</style>




<style type="text/css">
.window { border:1px solid #346789; 
box-shadow: 2px 2px 19px #aaa;
-o-box-shadow: 2px 2px 19px #aaa;
-webkit-box-shadow: 2px 2px 19px #aaa;
-moz-box-shadow: 2px 2px 19px #aaa;
padding-bottom: 10px;
padding-left: 0px;
padding-right: 0px;
padding-top: 10px;
vertical-align: top;
opacity:0.8; 
filter:alpha(opacity=80); 

z-index:20; position:absolute; 
background-color:#eeeeef;
color:black;
font-family:helvetica;padding:0.5em; 
font-size:0.9em;}
.window:hover {
border:1px solid #123456;
box-shadow: 2px 2px 19px #444;
   -o-box-shadow: 2px 2px 19px #444;
   -webkit-box-shadow: 2px 2px 19px #444;
   -moz-box-shadow: 2px 2px 19px #444;
    opacity:0.6; 
filter:alpha(opacity=60); 
}
.hl { border:3px solid red; }
p { padding-top:5px; padding-bottom:5px;}
#debug { position:absolute; background-color:black; color:red; z-index:5000 }
</style>


<script type="text/javascript" src="/ourspaces/javascript/excanvas.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/jquery-ui-1.8.5.min.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/jquery.jsPlumb-1.2.3-all.js"></script>
<script src="/ourspaces/javascript/jquery.quickflip.source.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript" src="RESTFlow.js"></script>

<script type="text/javascript">
$(function() {
    $('.quickFlip').quickFlip();
    
    $('.quickFlip3').quickFlip({
        vertical : true
    });
    
    $('.quickFlip2').quickFlip();

    $('.window').resizable();
});
</script>


     
<div id="main" style="position: fixed; z-index:25; margin-top: 100px">
<h3>Workflow Catalogue</h3>
<div id="catalog">
			<ul>
				<li>Add</li>
				<li>Constant</li>
				<li>Visualise</li>
			</ul>
</div>    
</div>
     

<!--  
        <div class="window quickFlip drag ui-widget-content" id="window1a" >
          <div class="blackPanel">
           <a href="#"  class="quickFlipCta"><img style="float:right;" src="/ourspaces/images/settings.jpg"/></a>
           <p>Workflow Activity 1</p>
          </div>
          <div class="redPanel">
           <a href="#" class="quickFlipCta"><img style="float:right; " src="/ourspaces/images/settings1.jpg"/></a>
			        
			        <form action="test.jsp" method="post" style="width: 280px;">
						<label for="p1">Parameter 1 :</label><input type="text" name="p1" id="p1" size="10" maxlength="20" /><br />
						<label for="p1">Parameter 2 :</label><input type="text" name="p1" id="p1" size="10" maxlength="20" /><br />
						<label for="p1">Parameter 3 :</label><input type="text" name="p1" id="p1" size="10" maxlength="20" /><br />           
			        </form> 
          </div>
        </div>
        
        
		<div class="window quickFlip drag ui-widget-content" id="window2a"><div class="blackPanel"><h3 class="first quickFlipCta">2</h3></div><div class="redPanel">Settings</div></div>
	    <div class="window quickFlip  drag ui-widget-content" id="window3a"><div class="blackPanel"><h3 class="first quickFlipCta">3</h3></div><div class="redPanel">Settings</div></div>
	    <div class="window quickFlip  drag ui-widget-content" id="window4a"><div class="blackPanel"><h3 class="first quickFlipCta">4</h3></div><div class="redPanel">Settings</div></div>
	    <div class="window quickFlip  drag ui-widget-content" id="window5a"><div class="blackPanel"><h3 class="first quickFlipCta">5</h3></div><div class="redPanel">Settings</div></div>
	    <div class="window quickFlip  drag ui-widget-content" id="window6a"><div class="blackPanel"><h3 class="first quickFlipCta">6</h3></div><div class="redPanel">Settings</div></div>
-->









