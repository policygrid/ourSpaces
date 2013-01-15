<%@ page  language="java" import="java.util.*, java.text.*, common.*, java.sql.*"%>

<%


%>

<%!
public int nullIntconv(String inv)
{   
    int conv=0;
        
    try{
        conv=Integer.parseInt(inv);
    }
    catch(Exception e)
    {}
    return conv;
}
%>

<%!



public String getEvents(Connections con, String container, int day, int month, int year) {
	String ret = "";
	try {

String sday = ""+day;
String smonth = ""+(month+1);
String syear = ""+year;

if (sday.length() == 1) sday = "0"+sday;
if (smonth.length() == 1) smonth = "0"+smonth;
if (syear.length() == 1) syear = "0"+syear;
		

String dayStringFrom = syear+"-"+smonth+"-"+sday+" 00:00:00"; 
String dayToFrom = syear+"-"+smonth+"-"+sday+" 23:59:00"; 


Timestamp eventDayFrom = Timestamp.valueOf(dayStringFrom);
Timestamp eventDayTo = Timestamp.valueOf(dayToFrom);

Statement st = con.getCon().createStatement();

StringBuffer qry = new StringBuffer(1024);
qry.append("select * from events ");
qry.append("where  startTime >= " + eventDayFrom.getTime() + " and ");
qry.append("startTime <= " + eventDayTo.getTime()+ " and ");
qry.append("containerID = '" + container +"'");

//common.Utility.log.debug(eventDayFrom +" " + eventDayTo + "    Query "+qry);




ResultSet rs = st.executeQuery(qry.toString());
while(rs.next())
{  
	long startDate = rs.getLong("startTime");
	long endDate = rs.getLong("endTime");
	DateFormat df1 = new SimpleDateFormat( "hh:mm" );
	Timestamp ts = new Timestamp(startDate);
	Timestamp te = new Timestamp(endDate);
	
	ret = ret + "<div style=\"border:1px solid #F0F0F0; background-color:#FF6600; color:#FFF; margin-bottom:5px;\">";
		ret = ret + "<div>";
    		ret = ret + ts.getHours() + ":" + ts.getMinutes() + " - " + te.getHours() + ":" + te.getMinutes();
    	ret = ret + "</div> ";
		ret = ret + "<div>";
   			ret = ret + "<a href = \"/ourspaces/event.jsp?id="+rs.getInt("eventID")+"\">"+rs.getString("title")+"</a>";
    	ret = ret + "</div> ";
    ret = ret + "</div> ";

}

rs.close();
st.close();
	} catch (Exception e) {e.printStackTrace();}
return ret;
}
%>



<%
Connections con = new Connections();
con.connect();
 String container = request.getParameter("container");

 int iYear=nullIntconv(request.getParameter("iYear"));
 int iMonth=nullIntconv(request.getParameter("iMonth"));
 
 common.Utility.log.debug(container + " " + iYear + " " + iMonth);

 Calendar ca = new GregorianCalendar();
 int iTDay=ca.get(Calendar.DATE);
 int iTYear=ca.get(Calendar.YEAR);
 int iTMonth=ca.get(Calendar.MONTH);

 if(iYear==0)
 {
      iYear=iTYear;
      iMonth=iTMonth;
 }

 GregorianCalendar cal = new GregorianCalendar (iYear, iMonth, 1); 

 int days=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
 int weekStartDay=cal.get(Calendar.DAY_OF_WEEK);
 
 cal = new GregorianCalendar (iYear, iMonth, days); 
 int iTotalweeks=cal.get(Calendar.WEEK_OF_MONTH);
  
%>



<table width="620" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td width="25%">&nbsp;</td>
    <td width="45%">&nbsp;</td>
    <td width="30%">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0" >
      <tr>
        <td width="6%">Year&nbsp;</td>
        <td width="7%">
        <select id="iYear" name="iYear" onChange="goTo()">
        <%
        // start year and end year in combo box to change year in calendar
        for(int iy=iTYear-70;iy<=iTYear+70;iy++)
        {
          if(iy==iYear)
          {
            %>
          <option value="<%=iy%>" selected="selected"><%=iy%></option>
          <%
          }
          else
          {
            %>
          <option value="<%=iy%>"><%=iy%></option>
          <%
          }
        }
       %>
        </select></td>
        <td width="73%" align="center" style="color:#000000"><h3 style="color:#000000"><%=new SimpleDateFormat("MMMM").format(new java.util.Date(2008,iMonth,01))%> <%=iYear%></h3></td>
        <td width="6%">Month&nbsp;</td>
        <td width="8%">
        <select id="iMonth" name="iMonth" onChange="goTo()">
        <%
        // print month in combo box to change month in calendar
        for(int im=0;im<=11;im++)
        {
          if(im==iMonth)
          {
         %>
          <option value="<%=im%>" selected="selected"><%=new SimpleDateFormat("MMMM").format(new java.util.Date(2008,im,01))%></option>
          <%
          }
          else
          {
            %>
          <option value="<%=im%>"><%=new SimpleDateFormat("MMMM").format(new java.util.Date(2008,im,01))%></option>
          <%
          }
        }
       %>
        </select></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table align="center" border="0" style="border-top:1px solid #ccc; border-left:1px solid #ccc; margin-top: 5px; background-color: #F7F7F7;" cellpadding="3" cellspacing="0" width="540">
      <tbody>
        <tr>
          <th style="border-right:1px solid #ccc; border-bottom:1px solid #ccc;">Sun</th>
          <th style="border-right:1px solid #ccc; border-bottom:1px solid #ccc;">Mon</th>
          <th style="border-right:1px solid #ccc; border-bottom:1px solid #ccc;">Tue</th>
          <th style="border-right:1px solid #ccc; border-bottom:1px solid #ccc;">Wed</th>
          <th style="border-right:1px solid #ccc; border-bottom:1px solid #ccc;">Thu</th>
          <th style="border-right:1px solid #ccc; border-bottom:1px solid #ccc;">Fri</th>
          <th style="border-right:1px solid #ccc; border-bottom:1px solid #ccc;">Sat</th>
        </tr>
        <%
        int cnt =1;
        for(int i=1;i<=iTotalweeks;i++)
        {
        %>
        <tr>
          <% 
            for(int j=1;j<=7;j++)
            {
                if(cnt<weekStartDay || (cnt-weekStartDay+1)>days)
                {
                 %>
                <td align="center" style="border-bottom:1px solid #ccc; border-right:1px solid #ccc;" height="35" class="dsb">&nbsp;</td>
               <% 
                }
                else
                {
                	String events = getEvents(con,container,(cnt-weekStartDay+1),iMonth,iYear);
                 %>
                <td style="border-bottom:1px solid #ccc; border-right:1px solid #ccc;" height="35" id="day_<%=(cnt-weekStartDay+1)%>"><span style="font-size:9px;"><%=(cnt-weekStartDay+1)%></span><p align="center"><%=events%></p></td>
               <% 
                }
                cnt++;
              }
            %>
        </tr>
        <% 
        }
        con.disconnect();
        %>
      </tbody>
    </table></td>
  </tr>
</table></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp; </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
