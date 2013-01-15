package common;

import java.util.ArrayList;


/**
 * Class used to represent a project in a JavaBean
 * 
 * @author Richard Reid
 * @version 1.0
 * 
 */

public class ProjectBean 
{
	public String url;
	public String role;
	public String uri;
	public String title;
	public String subtitle;
	public String parent;
	public ArrayList subprojects;
	
	
	public ProjectBean()
	{
		
	}
	
	/**
	 * 
	 * @return Project ID
	 */
	public String getURL()
	{
		return url;
	}
	
	public String getRole()
	{
		return role;
	}
	
	public void setRole(String role)
	{
		this.role = role;
	}
	
	public ArrayList getSubprojects()
	{
		return subprojects;
	}
	
	public void setSubprojects(ArrayList subprojects)
	{
		this.subprojects = subprojects;
	}
	
	/**
	 * 
	 * @return project URI
	 */
	public String getURI()
	{
		return uri;
	}
	
	/**
	 * 
	 * @return project Title
	 */
	public String getTitle()
	{
		return title;
	}
	
	public String getSubtitle()
	{
		return subtitle;
	}
	
	public String getParent()
	{
		return parent;
	}
	
	
	/**
	 * Sets project ID
	 * @param id
	 */
	public void setURL(String url)
	{
		this.url = url;
	}
	
	/**
	 * Sets project URI
	 * @param uri
	 */
	public void setURI(String uri)
	{
		this.uri = uri;
	}
	
	/**
	 * Sets project Title
	 * @param title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void setSubtitle(String subtitle)
	{
		this.subtitle = subtitle;
	}
	
	public void setParent(String parent)
	{
		this.parent = parent;
	}
}
