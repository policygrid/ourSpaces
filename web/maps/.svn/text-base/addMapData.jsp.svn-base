<%@ page language="java" import="java.util.Iterator,net.sf.json.*,  jxl.*, java.io.File, java.net.*, java.util.ArrayList, java.io.*, org.policygrid.ontologies.*, java.net.*, java.util.Vector, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <% 
 Workbook workbook = Workbook.getWorkbook(new File("/home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/testdata/Spatialdata.xls")); 
 Sheet sheet = workbook.getSheet(0);
 
 Spatial sp = new Spatial();
 
  
 int cattleID = sp.addSpatialSeries("Cattle points","http://openprovenance.org/ontology#5827cef8-c11a-4ea3-8dc9-d86d6b6e14d6","P","N");
 //int sheepID = sp.addSpatialSeries("Distribution of Sheeps in Grampian 1","http://openprovenance.org/ontology#5827cef8-c11a-4ea3-8dc9-d86d6b6e14d6","S","N");
 
 	
 
for (int i = 1; i <  sheet.getRows(); i++){
 
 	double easting =  Double.parseDouble(sheet.getCell(0,i).getContents());
 	double northing =  Double.parseDouble(sheet.getCell(1,i).getContents());
 	double cattle =  Double.parseDouble(sheet.getCell(2,i).getContents());
 	double sheep =  Double.parseDouble(sheet.getCell(3,i).getContents());
 
	int cattleFeatureID = sp.addSpatialFeature(cattleID,"Datum"+i,new Double(cattle),null);
	//int sheepFeatureID = sp.addSpatialFeature(sheepID,"Datum"+i,new Double(sheep),null);
	
	sp.addGeoreferenceNE(cattleFeatureID,"",easting,northing);
	//sp.addGeoreferenceNE(cattleFeatureID,"",easting+2000,northing);
	//sp.addGeoreferenceNE(cattleFeatureID,"",easting+2000,northing-2000);
	//sp.addGeoreferenceNE(cattleFeatureID,"",easting,northing-2000);
	
	//sp.addGeoreferenceNE(sheepFeatureID,"",easting,northing);
	//sp.addGeoreferenceNE(sheepFeatureID,"",easting+2000,northing);
	//sp.addGeoreferenceNE(sheepFeatureID,"",easting+2000,northing-2000);
	//sp.addGeoreferenceNE(sheepFeatureID,"",easting,northing-2000);
}	
 %>
