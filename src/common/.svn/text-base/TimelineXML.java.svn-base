package common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.openrdf.OpenRDFException;
import org.xml.sax.SAXException;


import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class TimelineXML extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet 
{
	
	public TimelineXML()
	{
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
        // the action element that we'll check for
        String cmd;

        cmd = request.getParameter("timeline");
        if (cmd != null) 
        {
        	if(cmd.equals("1"))
        	{
                try {
					getTimeline(request, response);
                } catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
        	}
        	else if (cmd.equals("2"))
        	{
        		try {
					getMyProjectsTimeline(request,response);
        		} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
        	}
        	else if (cmd.equals("3"))
        	{
        		try {
					getMyProjectsTimeline(request,response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
        	}
            out.flush();
        }
        else
        {
        	out.println("Server error.");
        }
	}
	
	public void getMyProjectsTimeline(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParserConfigurationException, SAXException, OpenRDFException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
/*		HttpSession session = request.getSession();
    	User user = (User) session.getAttribute("use");
    	String res = user.getRes(user.getID());
    	
    	ProjectRDF rdf = new ProjectRDF();
    	RDFi rdf2 = new RDFi();
		ArrayList projects = (ArrayList) rdf.getAllProjects(res);
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		out.println("<data>");
		
		for(int i = 0; i < projects.size(); i++)
		{
			ArrayList resources = rdf.getResources((String)projects.get(i));
			String name = rdf.getTitle((String)projects.get(i));
			common.Utility.log.debug(name);
			for(int j = 0; j < resources.size(); j++)
			{
				Resources resource = new Resources();
				String curr = (String) resources.get(j);
				common.Utility.log.debug("Got resources");
				String title = rdf2.getResourceTitle(curr);
				
				long timestamp = getLong(rdf2.getResourceTimestamp(curr));
				common.Utility.log.debug("Got timestamp");
				String url = resource.getURL();
				
				String time = formatDate(timestamp);

					out.println("<event " + time + " title=\""+title+"\">");
					out.println("Project: " + name);
					out.println("</event>");

			}
		}
		out.println("</data>");
		*/
	}
	
	public void getTimeline(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParserConfigurationException, SAXException, OpenRDFException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		
		HttpSession session = request.getSession();
    	User user = (User) session.getAttribute("use");
    	String res = user.getRes(user.getID());
    	
    	common.Utility.log.debug("Got user:" + res);
    	
    	RDFi rdf = new RDFi();
    	Vector resources = rdf.getResources(res);
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		out.println("<data>");
		for(int i = 0; i < resources.size(); i++)
		{
			Resources resource = (Resources) resources.get(i);
			common.Utility.log.debug("Got resources");
			String title = resource.getTitle();
			
			long timestamp = getLong(resource.getTimeStamp());
			common.Utility.log.debug("Got timestamp");
			String url = resource.getURL();
			
			
			
			String time = formatDate(timestamp);
			
			
			
			common.Utility.log.debug("<data>");
			common.Utility.log.debug("<event " + time + " title=\""+title+"\">");
				//out.println("Authors:  " + author);
				
			common.Utility.log.debug("</event>");
			common.Utility.log.debug("</data>");
			
				out.println("<event " + time + " title=\""+title+"\">");
					//out.println("Authors:  " + author);
					
				out.println("</event>");
			
			
			common.Utility.log.debug("Printed some xml");
		}
		out.println("</data>");
		common.Utility.log.debug("done");	
	}
	
	public long getLong(double d){
		return (long) d;
	}
	
	public String formatDate(long timestamp)
	{
		Date date = new Date(timestamp);
		String month = "";
		if((String.valueOf(date.getMonth()).equals("0")) || (String.valueOf(date.getMonth()).equals("00")))
		{
			month = "Jan";
		}
		if((String.valueOf(date.getMonth()).equals("1")) || (String.valueOf(date.getMonth()).equals("01")))
		{
			month = "Jan";
		}
		if((String.valueOf(date.getMonth()).equals("2")) || (String.valueOf(date.getMonth()).equals("02")))
		{
			month = "Feb";
		}
		if((String.valueOf(date.getMonth()).equals("3")) || (String.valueOf(date.getMonth()).equals("03")))
		{
			month = "Mar";
		}
		if((String.valueOf(date.getMonth()).equals("4")) || (String.valueOf(date.getMonth()).equals("04")))
		{
			month = "Apr";
		}
		if((String.valueOf(date.getMonth()).equals("5")) || (String.valueOf(date.getMonth()).equals("05")))
		{
			month = "May";
		}
		if((String.valueOf(date.getMonth()).equals("6")) || (String.valueOf(date.getMonth()).equals("06")))
		{
			month = "Jun";
		}
		if((String.valueOf(date.getMonth()).equals("7")) || (String.valueOf(date.getMonth()).equals("07")))
		{
			month = "Jul";
		}
		if((String.valueOf(date.getMonth()).equals("8")) || (String.valueOf(date.getMonth()).equals("08")))
		{
			month = "Aug";
		}
		if((String.valueOf(date.getMonth()).equals("9")) || (String.valueOf(date.getMonth()).equals("09")))
		{
			month = "Sep";
		}
		if((String.valueOf(date.getMonth()).equals("10")))
		{
			month = "Oct";
		}
		if((String.valueOf(date.getMonth()).equals("11")))
		{
			month = "Nov";
		}
		if((String.valueOf(date.getMonth()).equals("12")))
		{
			month = "Dec";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		String day = sdf.format(date);
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
		String year = sdf2.format(date);
		
		String hour = String.valueOf(date.getHours());
		String minute = String.valueOf(date.getMinutes());
		
		String time = "start=\"" + month + " " + day + " " + year + " " + hour + ":" + minute + ":00 GMT\"";
		return time;
	}
	
}
