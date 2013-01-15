package taglibs;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

//import org.zkoss.web.servlet.xel.PageContext;

import java.io.*;
import java.util.*;
import java.net.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.rdf.*;
import com.hp.hpl.jena.vocabulary.*;
import com.hp.hpl.jena.db.*;

/**
 * A  Tag for a RDF literal
 * @author Edoardo Pignotti
 */

public class OurSpacesFormTaglib implements BodyTag
{
  
  //public static String fearlusUri    = "http://www.csd.abdn.ac.uk/research/fearg/ontologies/fearlus.owl#";

  private Tag parent;
  private BodyContent bodyContent;
  private PageContext pageContext;
  
  private String label = "";
  
  public void setLabel(String label) 
  {
   this.label = label;
  }
  
  
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
  
   private String type = "";
   
   public void setType(String type) 
  {
   this.type = type;
  }
  
  /**
   * Constructor
   */
  public OurSpacesFormTaglib()
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
      return EVAL_BODY_TAG;
	  //return SKIP_BODY;
  }

  /**
   * Method called by the Container to set the BodyContent
   */
   public void setBodyContent(BodyContent bodyContent)
   {
    this.bodyContent=bodyContent;
   }

  /**
   * Method Called before the first time the body is evaluated
   */
  public void doInitBody() throws JspTagException{}

  /**
   * Method called after each evaluation of the body
   * @return either EVAL_BODY_TAG or SKIP_BODY
   */
  public int doAfterBody() throws JspTagException
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
	   
	 if (type.equals("upload")){
		 pageContext.include("/forms/jsp/formUpload.jsp"); 
	 }
	 
	 if (type.equals("resource")){
		 pageContext.include("/forms/jsp/formResource.jsp"); 
	 }
	 
	 if (type.equals("browse")){
		 pageContext.include("/forms/jsp/formResourceBrowse.jsp"); 
	 }
   
     pageContext.getOut().write(bodyContent.getString()); 
     
     
     if (type.equals("upload")){
    	 pageContext.include("/forms/jsp/formBottom.jsp"); 
	 }
	 
	 if (type.equals("resource")){
		 pageContext.include("/forms/jsp/formBottomResource.jsp"); 
	 }
	 if (type.equals("browse")){
		 pageContext.include("/forms/jsp/formBottomResourceBrowse.jsp"); 
	 }
     
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
