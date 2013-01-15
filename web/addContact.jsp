<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*,  java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="user" class="common.User" />

<%
user = (User) session.getAttribute("use");
Random r = new Random();
int d = r.nextInt();
%>

<form method="post" id="target" action="Controller?action=addContact">    
    <div style="position:relative; float:left; width: 450px; margin-bottom:5px;">
     
     	<div style="position:relative; float:left; width:150px;">
            Search Contacts:<br />
        </div>
        
        <div id="members" style="position:relative; width:300px; float:left;">
        
<!--        Add autocomplete field to search for persons (also used in addProjectMember.jsp. Centralise somewhere?) -->
			<script language="javascript" type="text/javascript">
					addAutocomplete('membersinput', 'http://xmlns.com/foaf/0.1/Person', /*select */function(e, ui) { 
					            var resource = ui.item.label; 
					   		 			//Create formatted resource  
					            var span = $("<span>").text(resource);
					            span.css("width", "85%");  
					            span.attr("id", ui.item.id);
					            var random = Math.floor(Math.random()*10000);
					            var input = $("<input>").attr("type","hidden").attr("name","Name").val(random+"_"+ui.item.id)
					            .appendTo(span);
					            
					            var a = $("<a>").addClass("remove").attr({  
					                    href: "#",                
					                    title: "Remove " + name  
					          	}).text("x").appendTo(span); 
						    		
					          //Add the resource to the list.  
					          span.insertBefore('#membersinput');  
					          //Empty the value so that the edit box isn't updated with the URI
					          ui.item.value = "";
					        },  
					       /* change:*/ function() {  
					            //prevent 'to' field being updated and correct position  
					            $('#membersinput').css("top", 2);  
					        
					        }
					       );
					        //add live handler for clicks on remove links  
					        $(".remove", document.getElementById('#membersinputinputDiv')).live("click", function(){
					            //remove current friend  
					            $(this).parent().remove();  
					            //correct 'to' field position  
					            if($("#membersinputinputDiv span").length === 0) {  
					                $("#membersinputinput").css("top", 0);  
					            }  
					        });
			</script>

            <div id="membersinputDiv" class="resourceInputDiv" style="float: left;width:100%;">
				<input id="membersinput" class="resourceInput" style="padding: 2px;  border: 0px solid #000000;" size="40" type="text" value="">
			</div>
    
        </div><!-- end of members -->
 	</div>
    
    <input type="hidden" name="userid" value="<%=user.getID() %>"></input>
         
</form>

