<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.net.*, java.util.Vector, common.*,  java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%

Random r = new Random();
String memID = request.getParameter("label");

common.Utility.log.debug("label: " + memID);

int next = r.nextInt();
int person = r.nextInt();

%>

			<div style="position:relative; width:450px; float:left; padding-bottom:5px;" id="<%=memID%>con"> <!-- container -->
    
                <div style="position:relative; float:left; width:450px; margin-bottom:5px;" id="<%=memID%>search">
                    <input style="border:1px solid #006699; position:relative; float:left; padding-top:3px; width:300px; height: 17px;" onkeydown="return checkForm(event,'<%=memID%>','http://xmlns.com/foaf/0.1/','Person', <%=person %>);" onclick="clearValue('<%=memID%>in')" value="Search" type="text" id="<%=memID%>in" name="Keywords"/>
                    
                    
                    <div style="position:relative; float:left; width:25px; padding-top:2px; padding-left:10px;">
                        <a href="#" onClick="searchResource('<%=memID%>',document.getElementById('<%=memID%>in').value,'http://xmlns.com/foaf/0.1/','Person', '<%=person %>'); return false;"><img alt="" src="img/blue_search.gif" style="margin-right:5px; position:relative; float:left;"></a>
                    </div>
                    
                    <div style="position:relative; width:30px; display:none; float:left;" id="<%=memID%>gah">
                        <div style="display:none;" id="<%=memID%>l"><img src="images/tick.jpg" /></div>
                        <input type="hidden" id="<%=memID%>r" name="Name"/>
                    </div>
                    
                    

                </div>
                
            
                <div style="width:300px; border:1px solid #cccccc; position:relative; float:left; display:none;" id="<%=memID%>top">
                    <div id="<%=memID%>sea" style="position:relative; float:left; padding:10px; width: 278px; display: none; overflow-x: hidden;overflow-y: scroll; height: 150px;">    
                    </div> 
                </div>
            
                <div id="<%=memID%>sc" style="position: relative; float: left; display:none;">
                     <a href="#" onclick="hideResourceSearch('<%=memID%>'); return false;">[Close search]</a>
                </div>
              
            
            </div> <!-- end of container -->