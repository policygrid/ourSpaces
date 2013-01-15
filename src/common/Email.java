package common;

import java.util.*;
import java.io.*;


public class Email {
	
	/**
	* Using this function u can send mail
	* @param smtpHost: u have to specify u'r smtp host here
	* @param smtpPort: the smtp port number for gmail its 587
	* @param from: from address
	* @param urPassword: password of the sender for authentication
	* @param to: to address
	* @param subject: subject of the mail
	* @param content: the message content
	* @throws AddressException
	* @throws MessagingException test
	*/
	public static void send(String to, String subject, String message ) {
		
    try {
    	        Process p = Runtime.getRuntime().exec(new String[]{
    			"/usr/sbin/sendmail",
    			"-t"
    			});
    			OutputStreamWriter os = new OutputStreamWriter(p.getOutputStream(), "8859_1");
    			os.write("To: ");
    			os.write(to);
    			os.write("\n");
    			os.write("From: no-reply@ourspaces.net\n");
    			os.write("Subject: "+subject+" \n");
    			os.write(message);
    			os.close();
    			p.waitFor();
    } catch (Exception e) {common.Utility.log.debug(e);}
	}	

}
