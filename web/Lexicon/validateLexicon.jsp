
<%@page import="lexicon.builder.WordPair"%>
<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.util.Scanner, java.io.*,java.util.Vector,java.util.*,java.text.*, java.net.URLEncoder,common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<jsp:useBean id="resource" class="common.Resources" />
<jsp:useBean id="activity" class="common.Activities" />
<jsp:useBean id="tag" class="common.Tag" />
<jsp:useBean id="project" class="common.ProjectBean" />
<jsp:useBean id="projects" class="common.Project" />
<jsp:useBean id="blog" class="common.Blog" />
<jsp:useBean id="rdf" class="common.RDFi" />
<jsp:useBean id="message" class="common.Message"/>
<jsp:useBean id="msg" class="common.Message" />
 <jsp:include page="/top_head.jsp" />
<jsp:include page="/top_tail.jsp" /> 
<link rel="stylesheet" type="text/css" href="/ourspaces/communications/comms.css" />
<style>
.ui-icon{background-image: url(/ourspaces/css/custom-theme/images/ui-icons_a83300_256x240.png) !important;}

#makeMeDraggable { width: 300px; height: 100px; background: grey; }
#draggableHelper { width: 300px; height: 100px; background: grey; }
#makeMeDroppable { float: right; width: 300px; height: 300px; border: 1px solid #999; }
.term, .relterm{display:block;background-color:#2e2e2e;color:#fff;font-weight: bold;border: solid thin #3e3e3e;border-radius:3px;padding:2px;margin:2px;max-width:200px;float:left}
.msgimg{margin:0px;border:none;float:right;padding:0pxwidth:16px;height:16px;}
.wordpair {border: solid 1px #000000;padding-bottom: 5px; padding-top: 5px; border: thin solid rgb(102, 102, 102); margin-top: 5px;margin:3px}
.objectSortable {border: solid 1px #000000;}
.active{border: dashed medium #000000; border-radius:5px;}
.collector{border: dashed medium #999; border-radius:5px;}
.deleted{border: dashed medium #ff0000; border-radius:5px;}
.hidden {display:none}
.hilite {background-color: #999;}
.artifactcomments{float:left;border-bottom: 1px solid #999;margin-bottom:2px; with:540px}
.blogcomments{float:left;border-bottom: 1px solid #999;margin-bottom:2px; with:540px}
#feedback { font-size: 1.4em; }
	#selectable .ui-selecting { background: #FECA40; }
	#selectable .ui-selected { background: #F39814; color: white; }
	#selectable { list-style-type: none; margin: 0; padding: 0; width: 60%; }
	#selectable li { margin: 3px; padding: 0.4em; font-size: 1.4em; height: 18px; }
	
	.wordpair.ui-draggable-dragging { background: #999; }
	</style>
<script type="text/javascript">
 
$(document).ready(function(){
	var resid = $("#resuri").html();
	var fileuri = $("#fileuri").html();
	var title = $("#title").html();
	if( typeof fileuri != 'undefined' && fileuri != null){
		$("#savebutton").hide();
		loadItems(fileuri,title);
	}else{
		$("#editbutton").hide();
	}
	
	
	
	
	
	$("#cancelbutton").click(function(){
		
		$('.objectSortable').each(function(index) {
			removeItem( this.id,this);
			
		    
		});
		$("#selectedWordstitle").html("Selected Communication Items");
		
	});
	
	
	
	$("#savebutton").click(function(){
		
		var jsn="";
		$('.objectSortable').each(function(index) {
			var s1="";
			var s2="";
			var pair="";
			 $(this).children().each(function() {
			        var child = $(this);
			        
							var rel=child.attr("rel");
							
							if (child.attr("class")=="term"){
								s1="s1:{word:'"+$(child).html().trim()+"',source:'"+rel.split(':')[0]+"',id:'"+rel.split(':')[1]+"'}";
								
							}
							if (child.attr("class")=="relterm"){
								s2="s2:{word:'"+$(child).html().trim()+"',source:'"+rel.split(':')[0]+"',id:'"+rel.split(':')[1]+"'}";
								
							}
						
			 });
			pair="{"+s1+","+s2+"}";
			jsn=jsn+pair+",";
		});
		
	
		jsn=jsn.slice(0,jsn.length-1);
		jsn="["+jsn.trim()+"]";
		//alert(jsn);
		
		$.ajax({
			type: 'GET',
			url: "/ourspaces/plsservlet?type=update&jsn="+jsn , 
			dataType : "html",
			async : false,
			success : function(data, errorThrown) {
				
				 list=data.substring(1,data.length-1);
				 $( "#dialog" ).dialog({ buttons: { "Ok": function() { $(this).dialog("close"); } } });
				 window.location="http://mrt.esc.abdn.ac.uk:8080/ourspaces/myhome.jsp";
				 
				 
			}
		});
		
		
	});
	
$("#editbutton").click(function(){
		
	$.ajax({
		type: 'GET',
		url: "/ourspaces/getrelterms?type=corpus" , 
		dataType : "html",
		async : false,
		success : function(data, errorThrown) {
			
			
			 
			 
		}
	});
	
	});
	
	

	
	
	$(".wordpair").dblclick(function (){
		//alert(this.id);
		 dropItem(null,this,"true");
		
	});
	$(".wordpair").hover(function () {
	      $(this).addClass("hilite");
	    }, function () {
	      $(this).removeClass("hilite");
	});
	$(function() {
		$( "#accordion" ).accordion({
			fillSpace: true
		});
	});
	$(function() {
		$( "#accordionResizer" ).resizable({
			minHeight: 400,
			resize: function() {
				$( "#accordion" ).accordion( "resize" );
			}
		});
	});
    $(".wordpair").draggable({
    		helper:'clone',
    		appendTo: 'body',
    		drag:function(){
    			 $("#selectedWords").removeClass("collector");
    			 $("#selectedWords").addClass("active");
    		},
    		stop:function(){
    			
   			 $("#selectedWords").removeClass("active");
   			 $("#selectedWords").addClass("collector");
   		}
    		
    	});  

    $("#selectedWords").droppable({
            accept: ".wordpair",
            drop: dropItem
        
    });

   
    
});



function dropItem(event,ui,op){
	var el;
	if(typeof ui.draggable != "undefined" && ui.draggable != false){
		el = ui.draggable;
		op="true";
	}
	else // if was clicked onto
	{
		
		el =$(ui);
	}
    console.log("Item was Dropped");
    var clone=$(el).clone();
   
    clone.removeClass("wordpair");
    clone.addClass("objectSortable");
    clone.removeClass("hilite");
    clone.find(".plus").remove();
    if (op==true)
    	clone.append('<span onclick="removeItem('+"'"+clone.attr("id")+"'"+',this.parentNode)" class="removeitem ui-icon ui-icon-circle-minus" style="float: right; display:block;"></span>');
    $("#selectedWords").append(clone);
    $(el).find(".plus").remove();
    $(el).draggable( 'disable' );
  
    
    
}
function getIDs()
{
	var commIDs = "";
	
	
	$('.objectSortable').each(function(index) {
		commIDs=commIDs+ "{"+$(this).attr("id").split("-")[0]+":"+$(this).attr("id").split("-")[1] +"},";
	});
	var newStr = commIDs.substring(0, commIDs.length-1);
	newStr="["+newStr+"]";
	/* var myObject = eval('(' + newStr + ')');
	
	 alert( myObject[0].msgID); */
	 return newStr;
	
}
function removeItem( itemid,element){
	
	
	$( element ).remove();
	 $("#"+itemid).append('<span onclick="dropItem(null,this.parentNode,true)" class="plus ui-icon ui-icon-circle-plus" style="float: right; display:block;"></span>');
	 $("#"+itemid).draggable( 'enable' );
	
}

</script>
	<script>
	$(function() {
		$( "#selectedWords" ).sortable({
			helper:'original'
		});
		$( "#selectedWords" ).disableSelection();
	});
	 
	$(function() {
		$( "#selectable" ).selectable();
	});
	
	
	</script>
<% 
common.User usr= (User) session.getAttribute("use");
if (usr.getID() == 0)
	response.sendRedirect(response.encodeRedirectURL("/error.jsp"));

	

String type="";
String id="";
if (request.getParameter("type") != null)
	type=request.getParameter("type");

if (request.getParameter("id") != null)
	id=request.getParameter("id");



%>
 

    
 <div class="homeStatusContainer" style="color:#FFFFFF; position:relative;" >
 <div id="dialog" title="Basic dialog" style="display:none">
	<p>Your personal lexicon has been updated.</p>
</div>
 <div style ="padding:20px;color:#000;">The following word pairs have been identified as similar words by comparing your personal corpus against the corpus of the PolicyGrid II project. Please review and select the pairs that you wan to include into the personal lexicon.</div>
<div id="columns" style="position:relative;">

<ul id="column2" class="column" style="width: 500px;height:600px">
	<li class="widget color-yellow " id="lex_main">
        <div class="widget-head"><h3 id="selectedWordstitle">Suggested Similar terms pairs</h3></div>
       		 <div class="widget-content" >
				<div style="float:left; height:705px; padding:2px;overflow:auto">
			
<%

String filepath=Configuration.getParaemter("pls","lexsFolder")+usr.getFOAFID().split("#")[1]+"VS"+id;	

//Scanner scanner = new Scanner(new FileReader("/home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/Lexicon/dargay1Vsdargay2.csv"));
Scanner scanner = new Scanner(new FileReader(filepath));


Vector<WordPair> pairs =new Vector<WordPair>();

System.out.println(filepath);
try {
	Vector<String> words=new Vector<String>();
	String prev="";
	int count=0;
	int i=0;
	 while ( scanner.hasNextLine()){
		
   	  	String aline=scanner.nextLine();
   	 System.out.println(aline);
   	  	words.add(aline);
   	   	String w1=aline.split(",")[0];
   	   	String s1="person:"+usr.getFOAFID().split("#")[1];
   		String w2=aline.split(",")[1]; 
   		String s2=type+":"+id;
   		double val=Double.parseDouble( aline.split(",")[2]);
		WordPair wp=new WordPair(w1,s1,w2,s2,val);
		pairs.add(wp);
	 }
	 //List<WordPair> names = Arrays.asList(nameArray);
     Collections.sort(pairs);
	 
	 for (WordPair wp:pairs){
		 double val=wp.sim(); 
	   		if (!prev.equals(wp.w1())&& val>=3){
	   			count=0;
	   			%>
	   				<br/>
	   			<%
	   		}
	   		if ((val>=3 && count <3)&&(!wp.w1().trim().equals(wp.w2().trim()))){
	   			
	   		
	    	
		 
		    
	   	%>
	   	

	   	 <div id=<%="id"+i %>  class ="wordpair" style="float: left;width:440px;  padding-bottom:5px; padding-top:5px; border:solid thin #666; margin-top:5px">
						<span id=<%="lid"+i %> class="term" rel=<%=wp.w1source() %>><%=wp.w1() %></span><span  id=<%="rid"+i %>  class="relterm" rel=<%=wp.w2source() %>><%=wp.w2() %></span>
						<span onclick="dropItem(null,this.parentNode,true)" class="plus ui-icon ui-icon-circle-plus" style="float: right; display:block;"></span>
		</div>
	   	<%
	   		count++;
		    }
	   		prev=wp.w1();
	   		
			i++;
	 }
	
}catch ( Exception Ex){ 
	
	System.out.println(Ex.getMessage());
}
%>

        		
        	
        	 </div> 
        	 <div style="float:left;background-color:#fff;padding:5px;width:450px;margin:5px" class="ui-state-highlight ui-corner-all">
        	 <a id ="selectall" class="fg-button ui-state-active fg-button-icon-right ui-corner-all"  >Select All</a>

        	</div>
        	 </div>
   </li>
                    
</ul>
<ul id="column1" class="column" style="width: 500px;">
 	<li class="widget color-yellow " id="communication_items">
    	<div class="widget-head"><h3 id="selectedWordstitle">Selected Similar Terms</h3></div>
       		<div class="widget-content">
        	 <div id="selectedWords" class="sortable collector" style=" height:705px; margin:2px;padding:2px;overflow:auto"></div>
        	 
        	
        	<div style="float:left;background-color:#fff;padding:5px;width:450px;margin:5px" class="ui-state-highlight ui-corner-all">
        	 
        	 <a id ="savebutton" class="fg-button ui-state-active fg-button-icon-right ui-corner-all"  >Update Personal Lexicon</a>
        	 <a id ="cancelbutton" class="fg-button ui-state-active fg-button-icon-right ui-corner-all"  >Remove All</a>
        	 
        	 </div> 
        	 </div> 
 </li>
     	
</ul>



</div> <!--  end of columns -->

            
            
</div> <!--  End of home status -->

<%--   <jsp:include page="/bottom.jsp" /> --%>
