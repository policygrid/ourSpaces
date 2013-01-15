package common;

import java.util.*;
import java.io.*;
import java.lang.Thread;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload; 

public class Postings extends HttpServlet implements Servlet
{
	public static final String USER = "wysiwym_user";
	public static final String RESOURCE = "wysiwym_resource";
	
	/*	Receives the user id from ourSpaces (or any other posts that may happen
	 *	in future)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		common.Utility.log.debug("called 'doGet'");
		doPost(request,response);
	}
	
	/*	Receives the user id from ourSpaces (or any other posts that may happen
	 *	in future)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			common.Utility.log.debug("Called doPost");
			PrintWriter out = response.getWriter();
			String cmd = request.getParameter("action");
			if (cmd != null) 
        	{
        	    if (cmd.equals("login")) 
        	    {
        	    	storeID(request, response);	
       	    		response.sendRedirect("http://roc.csd.abdn.ac.uk:8091/edit");	
       	    	//	response.sendRedirect("http://canvasback.csd.abdn.ac.uk:8080/edit");
            	}
            	else if (cmd.equals("upload"))
            		getFile(request, response);
            	else if (cmd.equals("query"))
            	{	
            		storeID(request, response);	//create new query session for this user   
            		response.sendRedirect("http://roc.csd.abdn.ac.uk:8091/query");	
            	//	response.sendRedirect("http://canvasback.csd.abdn.ac.uk:8080/query"); 
            	}
            	else if (cmd.equals("browse"))
            	{
            		storeID(request, response);
            		String resourceID = request.getParameter("resourceid");
            		common.Utility.log.debug("GOT RESOURCE ID FROM OURSPACES: " + resourceID);
            		Cookie c = new Cookie(RESOURCE, resourceID);
            		response.addCookie(c);
            		response.sendRedirect("http://roc.csd.abdn.ac.uk:8091/browse");	
            	//	response.sendRedirect("http://canvasback.csd.abdn.ac.uk:8080/browse");
            	}	
            	else
            		out.flush();
			}
			else
				common.Utility.log.debug("doPost without command specified");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/*	When the user creates a query, the same actions are required as for login,
	 *	really? We get the user id and set a cookie.
	 */
	private void storeID(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String userID = request.getParameter("userid"); 	//get the user id and set a cookie
		common.Utility.log.debug("GOT	USER ID FROM OURSPACES: " + userID);
        Cookie koekje = new Cookie(USER, userID);
       	response.addCookie(koekje);
	}

	/*	Removes all characters that Fedora might not accept from the filename
	 */
	private String checkFileName(String name)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < name.length(); i++)
		{
			Character c = name.charAt(i);
			if (c.isLetterOrDigit(c) || c.equals('.'))
				sb.append(c);
		}
		return sb.toString();
	}
	
	/*	Takes the posted file and puts it in Fedora.
	 */
	private void getFile(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		common.Utility.log.debug("UPLOADING FILE!!!!");
		response.setContentType("text/plain");    	
   		FileItemFactory factory = new DiskFileItemFactory();
    	ServletFileUpload upload = new ServletFileUpload(factory);

    	try 
    	{
    		List items = upload.parseRequest(request);
    		String user = null;
    		String filename = null;
    		File file = null;
    		
      		for (Iterator it = items.iterator(); it.hasNext(); ) 
      		{
        		FileItem item = (FileItem) it.next();
        		if (!item.isFormField()) //&& "upload".equals(item.getFieldName())) 
			    {  	//PUT THE FILE INTO FEDORA	From FileItem we can use get() or getInputStream()
			    	filename = checkFileName(item.getName());
			    	file = new File (/*"deletable/" + */filename);
			    	item.write(file);
			    	item.delete();
			   	}
				else 		//user id!!!
			   		user = item.getFieldName();
	      	}
	      	
	      	if ((user != null) && (file != null))
	      	{
	      		response.getWriter().write(storeFileInFedora(user, file, filename));	
	      		if (!file.delete())
           			common.Utility.log.debug("file not deleted");
   	      		return;
	      	}
    	} 
    	catch (FileUploadException e) 
    	{
      		e.printStackTrace();
    	}
    	
    	response.getWriter().write("Sorry, I could not upload your file.");
  	}
  	
  	private void setParameters(ClientHttpRequest httpClient) throws Exception
  	{
  		httpClient.setParameter("title","digitalObject"); 
		httpClient.setParameter("creator","PolicyGrid");
		httpClient.setParameter("description","archive");
		httpClient.setParameter("publisher","PolicyGrid"); 
		httpClient.setParameter("date", "today"); 
		httpClient.setParameter("format","whoknows"); 
		httpClient.setParameter("rights","protected"); 
  	}
  	  	
  	public String storeFileInFedora(String userID, File file, String filename) throws Exception
  	{
  		String digObjPID = new String();
		try 
		{
  			ClientHttpRequest httpClient = new ClientHttpRequest("http://roc.csd.abdn.ac.uk:8092/fedoraupload/fedoraCreateObjectServlet");
  			setParameters(httpClient);           
			InputStream serverInput = httpClient.post();
			for(int i = serverInput.read(); i != -1; i = serverInput.read()) 
			     digObjPID += (char) i;  		
			digObjPID = normalise(digObjPID);	         
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		
		try 
		{	//Note: avoid the use of spaces and strange characters in the file name. The file name becomes the name of the datastream in fedora. Sometimes fedora complains if the name of the datastream contains invalid characters.
			ClientHttpRequest httpClient = new ClientHttpRequest("http://roc.csd.abdn.ac.uk:8092/fedoraupload/fedoraUploadServlet");
			httpClient.setParameter("username", "user");
			httpClient.setParameter("pid", digObjPID);	 //the one returned from step 1
			httpClient.setParameter("file", file);
		
			int idx = filename.indexOf(".");
			if (idx > 0)
			{
				String extension = new Extensions().get(filename.substring(idx + 1));
				if (extension == null)
					extension = "text/plain";
				httpClient.setParameter("mimeType", extension);
				//	httpClient.setMimeType(filename.substring(idx + 1));
			}			
			
			InputStream serverInput = httpClient.post();
			String result = new String();
			for (int i = serverInput.read(); i != -1; i = serverInput.read())
            	result += (char) i;	//The buffer return a success/error message from the servlet
            common.Utility.log.debug("FILENAME: " + result);	
    		return result;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return("Upload failed");
		}		
  	}
  	
  	private String normalise(String str)
  	{
  		str = str.replace("\\r", "");
  		str = str.replace("\\n", "");
  		str = str.replace("\\", "");
  		str = str.replace("\r", "");
  		str = str.replace("\n", "");
  		return str;
  	}
}