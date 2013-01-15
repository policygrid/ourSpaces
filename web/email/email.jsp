<%@ page import="java.io.*, java.net.*" %>


<jsp:useBean id="email" class="common.Email" />


 <% 

email.send("e.pignotti@abdn.ac.uk, r.reid@abdn.ac.uk","Another test","Body of the message \n another line");


%> 