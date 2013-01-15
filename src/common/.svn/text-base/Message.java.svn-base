package common;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Vector;

/**
 * A JavaBean class used to store messages and their status.
 * 
 * @author Richard
 * @version 1.0
 */

public class Message
{
	
	public int sender;
	public int id;
	public Vector recipients;
	public Vector recipientIds;
	public int threadid;
	public int idmin;
	public int idmax;
	public String subject;
	public String content;
	public Date sentdate;
	public Time senttime;
	public String unread;
	public boolean delstatus;
	
	Connections con = new Connections();
	
	public Message()
	{
		
	}
	
	/**
	 * Sets the sender of the message
	 * @param sender
	 */
	public void setSender(int sender)
	{
		this.sender = sender;
	}
	/**
	 * Sets the recipients of the message
	 * @param sender
	 */
	public void setRecipients(Vector recipients)
	{
		this.recipients = recipients;
	}
	/**
	 * Sets the recipients of the message
	 * @param sender
	 */
	public void setRecipientIds(Vector recipientIds)
	{
		this.recipientIds = recipientIds;
	}
	
	/**
	 * Sets the message's ID
	 * @param id
	 */
	public void setID(int id)
	{
		this.id = id;
	}
	
	/**
	 * Sets the message's thradID (added by Kapila 11/09/11)
	 * @param id
	 */
	public void setThreadID(int threadid)
	{
		this.threadid = threadid;
	}
	/**
	 * Sets the message's ID
	 * @param id
	 */
	public void setIDmin(int idmin)
	{
		this.idmin = idmin;
	}
	/**
	 * Sets the message's ID
	 * @param id
	 */
	public void setIDmax(int idmax)
	{
		this.idmax = idmax;
	}
	/**
	 * Sets the message's subject
	 * @param subject
	 */
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	
	/**
	 * Sets the message's content
	 * @param content
	 */
	public void setContent(String content)
	{
		this.content = content;
	}
	/**
	 * Sets the message's sent (date and time) (added by Kapila 11/09/11)
	 * @param sentd
	 */
	public void setSent(Date sentd)
	{
		this.sentdate = sentd;
	}
	public void setSentTime(Time sentt)
	{
		this.senttime = sentt;
	}
	/**
	 * Sets the message's status, whether it is unread or not
	 * @param unread
	 */
	public void setUnread(String unread)
	{
		this.unread = unread;
	}
	
	/**
	 * Sets the message's status, whether it is unread or not
	 * @param unread
	 */
	public void setdelStatus(boolean deleted)
	{
		this.delstatus = deleted;
	}
	
	/**
	 * @return message threadid (added by Kapila 11/09/11)
	 */
	public int getThreadID()
	{
		return threadid;
	}
	
	/**
	 * @return message id
	 */
	public int getID()
	{
		return id;
	}
	/**
	 * @return recipients
	 */
	public Vector getRecipients()
	{
		return recipients;
	}
	/**
	 * @return recipients ids
	 */
	public Vector getRecipientIds()
	{
		return recipientIds;
	}
	/**
	 * @return message id
	 */
	public int getIDmin()
	{
		return idmin;
	}
	
	/**
	 * @return message id
	 */
	public int getIDmax()
	{
		return idmax;
	}
	
	/**
	 * 
	 * @return message sender
	 */
	public int getSender()
	{
		return sender;
	}
	
	/**
	 * 
	 * @return message subject
	 */
	public String getSubject()
	{
		return subject;
	}
	
	/**
	 * 
	 * @return message sent (date and time)
	 */
	public Date getSent()
	{
		return sentdate;
	}
	public Time getSentTime()
	{
		return senttime;
	}
	
	/**
	 * 
	 * @return message content
	 */
	public String getContent()
	{
		return content;
	}
	
	/**
	 * 
	 * @return Unread status
	 */
	public String getUnread()
	{
		return unread;
	}
	
	
	/**
	 * 
	 * @return Unread status
	 */
	public boolean getDelStatus()
	{
		return delstatus;
	}
	public boolean deleteMessage(int userid,int msgid) throws InstantiationException,
	IllegalAccessException, ClassNotFoundException, SQLException 
	{
		
	// Connects to the database
	con.connect();
	Statement st = con.getCon().createStatement();

	// SQL Query

	
String qry ="update messagerecipients set hide_received=true where id="+msgid+" and recipient="+userid;
common.Utility.log.debug(qry);
	boolean status=false;
	if (st.executeUpdate(qry)>0)
		status=true;

	

	
	st.close();
	con.disconnect();
		return status;
	}
	
	
	public boolean deleteMessagePerm(int userid,int msgid) throws InstantiationException,
	IllegalAccessException, ClassNotFoundException, SQLException 
	{
		
	// Connects to the database
	con.connect();
	Statement st = con.getCon().createStatement();

	// SQL Query

	
String qry ="delete from messagerecipients  where  id="+msgid+" and recipient="+userid;
common.Utility.log.debug(qry);
	boolean status=false;
	if (st.executeUpdate(qry)>0)
		status=true;

	

	
	st.close();
	con.disconnect();
		return status;
	}
	
	
	public boolean updatehideSent(int userid,int msgid) throws InstantiationException,
	IllegalAccessException, ClassNotFoundException, SQLException 
	{
		
	// Connects to the database
	con.connect();
	Statement st = con.getCon().createStatement();

	// SQL Query

	
String qry ="update messages set hide_sent=true where id="+msgid+" and sender="+userid;
common.Utility.log.debug(qry);
	boolean status=false;
	if (st.executeUpdate(qry)>0)
		status=true;

	

	
	st.close();
	con.disconnect();
		return status;
	}
	
	public boolean updateReadStatus(int userid,int msgid, String readdatetime) throws InstantiationException,
	IllegalAccessException, ClassNotFoundException, SQLException 
	{
		
	// Connects to the database
	con.connect();
	Statement st = con.getCon().createStatement();

	// SQL Query

	
String qry ="update messagerecipients set unread='R',  read_datetime='"+readdatetime+"' where id="+msgid+" and recipient="+userid +" and unread !='R'" ;
common.Utility.log.debug(qry);
	boolean status=false;
	if (st.executeUpdate(qry)>0)
		status=true;

	

	
	st.close();
	con.disconnect();
		return status;
	}
}
