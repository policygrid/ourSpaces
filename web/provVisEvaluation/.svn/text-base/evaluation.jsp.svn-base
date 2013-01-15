<%

if(request.getParameter("dataset") != null){
	//String json = (String)request.getParameter("data");
/*java.util.Map m = request.getParameterMap();
for(Object s : m.keySet()){
	Object o1 = m.get(s);
	System.out.print(s.toString() + ": "+o1.toString());
}*/
	String a1 = request.getParameter("q1");
	String a2 = request.getParameter("q2");
	String a3 = request.getParameter("q3");
	String a4 = request.getParameter("q4");
	if(a1 == null)
		a1 = "none";
	if(a2 == null)
		a2 = "none";
	if(a3 == null)
		a3 = "none";
	if(a4 == null)
		a4 = "none";
	
	String dataset = request.getParameter("dataset");
	String vis = request.getParameter("id");
	String time = request.getParameter("time");

	java.net.InetAddress yip2=java.net.InetAddress.getLocalHost();	
	String yip=yip2.getHostAddress();

	Integer type = (Integer)session.getAttribute("type");
	Integer user = (Integer)session.getAttribute("user");
	common.LoggerSearch l = new common.LoggerSearch();
	l.addLog("log_provviseval(userid,type,vis,a1,a2,a3,a4,dataset,time, ip) ",user+","+type+",'"+vis+"','"+a1+"','"+a2+"','"+a3+"','"+a4+"','"+dataset+"',"+time+",'"+yip+"'");
}





Integer type =(Integer)session.getAttribute("type");
if(request.getParameter("Graph") != null){
	session.setAttribute("completedGraph", Boolean.parseBoolean(request.getParameter("Graph")));
}
if(request.getParameter("NLG") != null){
	session.setAttribute("completedNLG", Boolean.parseBoolean(request.getParameter("NLG")));
}
if(request.getParameter("Combination") != null){
	session.setAttribute("completedCombination", Boolean.parseBoolean(request.getParameter("Combination")));
}
Boolean complC =(Boolean)session.getAttribute("completedCombination");
Boolean complG =(Boolean)session.getAttribute("completedGraph");
Boolean complN =(Boolean)session.getAttribute("completedNLG");
	//NLG first
	if(type==0 || type == 1){
		if(!complN){
			response.sendRedirect("NLGIntro.jsp");
		}
		//Second combined
		else if(!complC){
			response.sendRedirect("GraphIntro.jsp");
		}
		else 
			//redirect to questionnaire
			response.sendRedirect("questionnaire.jsp");
	}
	//Graph first
	else if(type==2 || type == 3){
		if(!complG){
			response.sendRedirect("GraphIntro.jsp");
		}
		//Second combined
		else if(!complC){
			response.sendRedirect("NLGIntro.jsp");
		}
		else 
			//redirect to questionnaire
			response.sendRedirect("questionnaire.jsp");
	}
%>