package taglibs;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import java.io.*;
import java.util.*;
import java.net.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.rdf.*;
import com.hp.hpl.jena.vocabulary.*;
import com.hp.hpl.jena.db.*;
import javax.servlet.http.HttpSession;
import javax.servlet.http.*;
import common.XML;
import common.User;

/**
 * A  Tag for a RDF literal
 * @author Edoardo Pignotti
 */

public class UserTaglib implements Tag
{
  
  private Tag parent;
  private PageContext pageContext;
  
   
  private String namespace = "";
 
  public void setNamespace(String namespace) 
  {
   this.namespace = namespace;
  }
  
  private String property = "";
  
   public void setProperty(String property) 
  {
   this.property = property;
  }
  
   private String optional = "";
   
   public void setOptional(String optional) 
  {
   this.optional = optional;
  }
  
   private String ukda = "";
   
   public void setUkda(String ukda) 
  {
   this.ukda = ukda;
  }
   
  /**
   * Constructor
   */
  public UserTaglib()
  {
    super();
  }

  /**
   * Method called by the Container to set the PageContext
   */
   public void setPageContext(PageContext pageContext)
   {
    this.pageContext=pageContext;
    
   }

  /** Method used by the JSP container to set the parent of the Tag
   * @param parent, the parent Tag
   */
  public void setParent(final javax.servlet.jsp.tagext.Tag parent)
  {
    this.parent=parent;
  }


  /**
   * Method called at start of tag
   * @return either a EVAL_BODY_TAG or a SKIP_BODY
   */
  public int doStartTag() throws JspTagException
  {           
	  return SKIP_BODY;
  }



  /**
   * Method Called at end of tag
   * @return either EVAL_PAGE or SKIP_PAGE
   */
  public int doEndTag() throws JspTagException
  {

   try    
   {        
      HttpSession session = pageContext.getSession();      User user = (User) session.getAttribute("use");      common.Utility.log.debug(user.getID());      String id = Integer.toString(user.getID());            Integer userid = Integer.parseInt(id);      XML xml = new XML();      String resourceID = xml.getResourceID(userid);
   	
     //pageContext.getOut().write(label+"&nbsp;&nbsp");
     pageContext.include("<INPUT type=\"hidden\" name=\""+namespace+"#"+property+"\" value=\""+resourceID+"\""); 
   }        
   catch(Exception e)       
   {           
     throw new JspTagException("IO Error: " + e);       
   }        
   return EVAL_PAGE;
  
  }

  /**
   * Method called to releases all resources
   */
  public void release() {}

  /** Method for retrieving the parent
   * @return the parent
   */
  public javax.servlet.jsp.tagext.Tag getParent()
  {
    return parent;
  }
}
