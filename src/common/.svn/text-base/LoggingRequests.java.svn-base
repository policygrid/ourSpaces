package common;

import java.io.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.antlr.runtime.*;
import org.apache.commons.collections.iterators.IteratorEnumeration;

import org.jvyamlb.util.Base64Coder;
import org.openrdf.OpenRDFException;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryException;
import org.xml.sax.SAXException;
import org.zkoss.web.servlet.http.HttpBufferedResponse;

import common.Connections;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import common.*;

public class LoggingRequests implements Filter {

	private FilterConfig filterConfig;
	public int uid;
	public String sid;

    
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		
	
		String url = ((HttpServletRequest)request).getRequestURL().toString();
		
		//Logging Exceptions
		if (url.contains("/images") || url.contains("/icons") || url.contains("/img") || url.endsWith(".js") || url.endsWith(".js")) {
			chain.doFilter(request, response);
			return;
		}
		//End of exceptions
		
		
		String queryString = ((HttpServletRequest)request).getQueryString();
		String remoteAddr = request.getRemoteAddr();
		String remoteHost = request.getRemoteHost();
		
		String protocol = request.getProtocol();		
		
		HttpSession session = ((HttpServletRequest)request).getSession();
		
		
		String mainPage = ""+session.getAttribute("mainpage");
		
	    User user = (User) session.getAttribute("use");
		   
	    int userid = -1; 
	    
	    if (user != null) userid = user.getID();
	    
		    
		
	    String pars = "";
		Map parameters = request.getParameterMap();
		Enumeration enumeration = new IteratorEnumeration(parameters.keySet().iterator());
		while (enumeration.hasMoreElements()) {
			String parameter = (String)enumeration.nextElement();
			String value = request.getParameter(parameter);
			
			//Logging Exceptions
			if (parameter.equals("action") && value.equals("chatheartbeat")) {
				chain.doFilter(request, response);
				return;
			}
			//End of exceptions
					
		 if (parameter.equals("password")) {
			 value = "***";
			}
		 pars += "("+parameter+"="+value+")";
		}
		
		String sst = "";
		
		Enumeration satt = session.getAttributeNames();
		while (satt.hasMoreElements()) {
			String attr = (String)satt.nextElement();
			if (session.getAttribute(attr) instanceof String) {
				sst += "("+attr+"="+session.getAttribute(attr) +")";
			} else {
				sst += "("+attr+"=OBJ)";
			}
			
		}
		
		String sid = session.getId();
		//Should be in a database
		
		try {
			Logs.addLogRequest(userid, remoteAddr, protocol, pars, mainPage, url, sid, sst);
		    this.uid = userid;
		    this.sid = sid;
		} catch (OpenRDFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//common.Utility.log.debug("------ UID: "+userid+" RM:"+remoteAddr+","+remoteHost+" Prot:"+protocol+" request:"+url+" pars:"+pars+" SID:"+sid+" ssn:"+sst);
		
		
		chain.doFilter(request, response);
		
	}
	

	

	/**
	 * Take this filter out of service.
	 */
	public void destroy() {
	}

	public FilterConfig getFilterConfig() {
		return this.filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;

	}

}
