package common;

import java.awt.Toolkit;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;

import lexicon.builder.LexiconUpdater;

import org.openrdf.OpenRDFException;
import org.xml.sax.SAXException;

/**
 * Simple demo that uses java.util.Timer to schedule a task to execute once 5
 * seconds have passed.
 */

public class OurSpacesScheduler {
	Toolkit toolkit;
	Connections con = new Connections();

	Timer timer;

	
	public OurSpacesScheduler(int seconds) throws ParseException {
		
		timer = new Timer();
        Date date = new Date();
		date.setSeconds(date.getSeconds()+5);
		//first executes after 10 seconds of starting the server and then every hour
		timer.schedule(new OurSpacesSchedulerTask(),date, 60*60*1000);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String string = dateFormat.format(date) +" 19:00:00";
		Date firstexec = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(string);
		
		//first executes 19hrs and then every 24hrs
		timer.schedule(new LexiconBuilderTask(),firstexec,3*24*60*60*1000);
		
		date.setSeconds(date.getSeconds()+30);
		timer.schedule(new LexiconBuilderTask(),date,3*24*60*60*1000);
	}
	
	class LexiconBuilderTask extends TimerTask{
		public void run() {
			System.out.println("Lexicon Builder executed ......");
					
			try{
				(new Thread(new LexiconUpdater())).start();
			}catch (Exception ex){
				ex.printStackTrace();
			}
		}
	}
	class OurSpacesSchedulerTask extends TimerTask {
		public void run() {
			//common.Utility.log.debug("OurSpaces Scheduler invoked...");
			// check database for users with daily digest
			System.out.println("Our Spaces Schedular task executed ......");
			try {
				con.connect();

				Statement st = con.getCon().createStatement();

				// SQL query
				StringBuffer qry = new StringBuffer(1024);
				qry.append("select userid, last_digest, email from user where daily_digest = True");

				ResultSet rs = st.executeQuery(qry.toString());
				
				int i = 0;
				while (rs.next()) {
					i = rs.getInt("userid");
					//common.Utility.log.debug("Last digest = "+ rs.getString("last_digest"));
					
				    String lastDigest = "";
				    if (rs.getString("last_digest") != null) {
				    	lastDigest = rs.getString("last_digest");
				    }
					Calendar c = Calendar.getInstance();
					int day1 = c.get(Calendar.DATE);
					int month1 = c.get(Calendar.MONTH) +1;
					int year1 = c.get(Calendar.YEAR);
					
					if (!lastDigest.equals(day1+"/"+month1+"/"+year1)) {
					//common.Utility.log.debug("The user: " + rs.getString("email") + " needs to be notified.");
					User user = new User();
					//common.Utility.log.debug("Printing report");
					user.sendDailyDigest(rs.getInt("userid"));
					} else {
					  //common.Utility.log.debug("The user: " + rs.getString("email") + " is up to date.");	
					}
				}

				rs.close();
				st.close();
				con.disconnect();
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
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OpenRDFException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try{
				//new OurSpacesScheduler(6000);
				}catch (Exception ex){
					ex.printStackTrace();
				}
			
		}
	}
}