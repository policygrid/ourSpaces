<%@ page language="java" import="java.util.Iterator, java.util.ArrayList, java.io.*, java.awt.*, java.awt.image.*, javax.imageio.ImageIO, org.policygrid.ontologies.*, java.net.*, java.util.Vector, common.*, java.util.Random" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<%
String col = "FF00FF";

if (request.getParameter("color") != null) {
 col = (String)request.getParameter("color");
}

int width = 10;
int height = 10;

BufferedImage buffer =
    new BufferedImage(width,
                      height,
                      BufferedImage.TYPE_INT_ARGB);
Graphics g = buffer.createGraphics();

//g.setColor(0x00FFFF);
//g.fillRect(0,0,width,height);

 int x = 5;
 int y = 5;
 int radius = 5;
 g.setColor(Color.decode("#"+col));
 g.fillOval(x - radius, y - radius, radius*2, radius*2);
 


 response.setContentType("image/png");
 OutputStream os = response.getOutputStream();
 

 ImageIO.write(buffer, "png", os);
 os.close();

%>