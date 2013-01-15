package common;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.xml.sax.SAXException;

import provenanceService.JSONProvider;
import provenanceService.Properties;
import provenanceService.ProvenanceService;
import provenanceService.RDFProvider;
import NLGService.WYSIWYM.NLGProperties;

public final class OurSpacesContextServlet
    implements ServletContextListener {

	
  private ServletContext context = null;

  public OurSpacesContextServlet() {}

  //This method is invoked when the Web Application
  //has been removed and is no longer able to accept
  //requests

  public void contextDestroyed(ServletContextEvent event)
  {

    //Output a simple message to the server's console
    common.Utility.log.debug("The ourSpaces Scheduler. Has Been Removed");
    this.context = null;

   
  }


  //This method is invoked when the Web Application
  //is ready to service requests

  public void contextInitialized(ServletContextEvent event)
  {
    this.context = event.getServletContext();

    common.Utility.log.debug("Initialising configuration class");
    Configuration.init(event.getServletContext().getRealPath("/"));
    Properties.setBaseFolder(event.getServletContext().getRealPath("/"));
    NLGProperties.setBaseFolder(event.getServletContext().getRealPath("/"));
	common.Utility.log.debug("Initialising Provenance Service");
	ProvenanceService.initProvenance();
	 
	
    try {
		OntologyHandler onto = new OntologyHandler();
		new ResourceUploadBean();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SAXException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    //Output a simple message to the server's console
    common.Utility.log.debug("The ourSpaces Scheduler thread. Is Running");
    try{
		new OurSpacesScheduler(5);
		}catch (Exception ex){
			ex.printStackTrace();
		}
  }
}
