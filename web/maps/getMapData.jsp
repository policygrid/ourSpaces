<%@ page language="java" import="java.util.Iterator,net.sf.json.*,  jxl.*, java.io.File, java.net.*, java.util.ArrayList, java.io.*, org.policygrid.ontologies.*, java.net.*, java.util.Vector, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <% 
 
 String sid = (String)request.getParameter("seriesid");
 Spatial sp = new Spatial();
 MapDataSeries ms = sp.getMapDataSeriesBySeriesID(Integer.parseInt(sid));
 ArrayList features =  ms.getFeatures();
 JSONArray jsonArray = JSONArray.fromObject(features);  
 //common.Utility.log.debug("JSON:" + jsonArray);
%>
<%=jsonArray%>