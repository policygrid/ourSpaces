<%@ page language="java" import="java.util.Iterator,net.sf.json.*,  jxl.*, java.io.File, java.net.*, java.util.ArrayList, java.io.*, org.policygrid.ontologies.*, java.net.*, java.util.Vector, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <% 
 Workbook workbook = Workbook.getWorkbook(new File("/home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/testdata/Spatialdata.xls")); 
 Sheet sheet = workbook.getSheet(0);
 ArrayList features = new ArrayList();
//for (int i = 1; i <  sheet.getRows(); i++)
for (int i = 1; i <  sheet.getRows(); i++){
 
 	double easting =  Double.parseDouble(sheet.getCell(0,i).getContents());
 	double northing =  Double.parseDouble(sheet.getCell(1,i).getContents());
 	double sheep =  Double.parseDouble(sheet.getCell(3,i).getContents());
 	
 	MapFeature m = new MapFeature();
 	m.setName("Feature"+i);
 	m.setResourceID("http://openprovenance.org/ontology#test1");
 	
 	double ct = (double)sheep/1378;
 	
 	m.setNumericalValue(ct);
 	ArrayList points = new ArrayList();
 	points.add(new MapPoint(easting, northing));
 	points.add(new MapPoint(easting+2000, northing));
 	points.add(new MapPoint(easting+2000, northing-2000));
 	points.add(new MapPoint(easting, northing-2000));
 	m.setPoints(points);
 	features.add(m);
 %>
 
<%
}
 
 	//JSONObject jsonObject = JSONObject.fromObject(features);  
 	JSONArray jsonArray = JSONArray.fromObject( features );  
 	
 	//double ct = (double)cattle/731;
 
 	common.Utility.log.debug("JSON:" + jsonArray);
%>
<%=jsonArray%>