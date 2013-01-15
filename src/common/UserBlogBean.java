package common;

/**
 * JavaBean class intended to represent an instance of a user's personal blog.
 * 
 * @author Richard Reid
 * @version 1.0
 *
 */

public class UserBlogBean 
{
	
	public int id;
	public String content;
	public String title;
	public String date;
	
	public UserBlogBean()
	{
		
	}
	
	/**
	 * returns the blog's ID
	 * @return
	 */
	public int getID()
	{
		return id;
	}
	
	/**
	 * Returns the blog's content
	 * @return
	 */
	public String getContent()
	{
		return content;
	}
	
	/**
	 * Returns the blog's title
	 * @return
	 */
	public String getTitle()
	{
		return title;
	}
	
	/**
	 * Returns the blog's date
	 * @return
	 */
	public String getDate()
	{
		return date;
	}
	
	/**
	 * Sets the blog's ID
	 * @param id
	 */
	public void setID(int id)
	{
		this.id = id;
	}
	
	/**
	 * Sets the blog's content
	 * @param content
	 */
	public void setContent(String content)
	{
		this.content = content;
	}
	
	/**
	 * Sets the blog's title
	 * @param title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	/**
	 * Sets the blog's date.
	 * @param date
	 */
	public void setDate(String date)
	{
		this.date = date;
	}
}
