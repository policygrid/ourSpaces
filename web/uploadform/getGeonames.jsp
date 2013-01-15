<%@ page language="java" import="net.sf.json.*,com.hp.hpl.jena.ontology.*,java.util.Iterator,java.util.*,java.net.*,java.text.SimpleDateFormat,java.util.ArrayList,java.io.*,java.net.*,java.util.Vector,common.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
URL url;
InputStream is = null;
DataInputStream dis;
String line;
String lon = request.getParameter("lon");
String lat = request.getParameter("lat");
try {
    url = new URL("http://api.geonames.org/findNearbyJSON?lat="+lat+"&lng="+lon+"&username=alan");
    is = url.openStream();  // throws an IOException
    dis = new DataInputStream(new BufferedInputStream(is));

    while ((line = dis.readLine()) != null) {
        %><%=line%><%     
    }
} catch (MalformedURLException mue) {
     mue.printStackTrace();
} catch (IOException ioe) {
     ioe.printStackTrace();
} finally {
    try {
        is.close();
    } catch (IOException ioe) {
        // nothing to see here
    }
}

	%>
