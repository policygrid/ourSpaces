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

/**
 * A  Tag for a RDF literal
 * @author Edoardo Pignotti
 */

public class LiteralTaglib implements Tag
{
  
  //public static String fearlusUri    = "http://www.csd.abdn.ac.uk/research/fearg/ontologies/fearlus.owl#";

  private Tag parent;
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
  public LiteralTaglib()
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
     //pageContext.getOut().write(label+"&nbsp;&nbsp");
	 
     if (optional.equals("false")) pageContext.include("/forms/jsp/literal.jsp?label="+label+"&namespace="+namespace+"&property="+property+"&optional="+optional+"&ukda="+ukda); 
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
