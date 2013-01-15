package common;

import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

public class Communications {
	public Communications(){
		
	}
	
	
	
	
	
	public String getSavedComm(int userid, String artifactid){
		 String msgstr="";
		try {
			
			
			
			common.Utility.log.debug(artifactid);
			String fileoutput =Utility.readTextFile(artifactid);
			common.Utility.log.debug(fileoutput);
			XMLSerializer xmlSerializer = new XMLSerializer(); 
            JSON json2 = xmlSerializer.read( fileoutput.trim() ); 
            common.Utility.log.debug("JSON from XML: "+ json2 );
           
            if (json2.isArray()){
            	JSONArray jsnarr=(JSONArray) json2;
            	common.Utility.log.debug( jsnarr.get(0).toString() );
                
                for (int i =0; i<jsnarr.size();i++){
                	JSONObject msgobj=(JSONObject) jsnarr.get(i);
                	
                	msgstr=createJson(msgobj);
                }
                
            }
            else {
            	JSONObject  msgobj=(JSONObject) ((JSONObject) json2).get("e");
            	common.Utility.log.debug( "JSON OBJ "+msgobj.get("e") );
            	msgstr=createJson(msgobj);
            	
            }
            
            
            
            msgstr=msgstr.substring(0,msgstr.length()-1);
            msgstr="["+msgstr+"]";
            common.Utility.log.debug(msgstr);
			
			
		}catch (Exception ex){
			common.Utility.log.debug(ex.getMessage());
		}
		return msgstr;
	}
	private String createJson(JSONObject jo){
		String str="";
		if (jo.containsKey("MessageID"))
			str=str+"{messageid:"+jo.getString("MessageID")+"},";

    	else if (jo.containsKey("ImID"))
    		str=str+"{imid:"+jo.getString("ImID")+"},";
    	else if (jo.containsKey("PostID"))
    		str=str+"{postid:"+jo.getString("PostID")+"},";
    	else if (jo.containsKey("CommentID"))
    		str=str+"{commentid:"+jo.getString("CommentID")+"},";
    	else if (jo.containsKey("ArtefactcommID"))
    		str=str+"{artefactcommentid:"+jo.getString("ArtefactcommID")+"},";
		return str;
	
	}
}
