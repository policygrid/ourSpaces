package svs.ex1;



import java.io.InputStream;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.JSONTypes;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.io.IOUtils;

public class bla {

        public static void main(String[] args) throws Exception {
//                InputStream is = 
//                        ConvertJSONtoXML.class.getResourceAsStream("/Users/kapila/Documents/repository/json/jsontest.txt");
                //String jsonData ="{'foo':'bar','coolness':2.0,'altitude':39000, 'pilot':{'firstName':'Buzz', 'lastName':'Aldrin'},'mission':'apollo 11',comIDs:[{msgIDs: [{msgID:277},{msgID:274},{msgID:276}]}]}"; //IOUtils.toString(is);
        		String jsonData = "[{'MessageID':277,'Sender':185,'SentDate':'2011-11-08','Content':'Hi Kapila Sending you a real time message from afar for your SICSA demo! Best wishes Kate'},{'MessageID':274,'Sender':196,'SentDate':'2011-11-08','Content':'test'},{'MessageID':276,'Sender':185,'SentDate':'2011-11-08','Content':'OK!'},{'MessageID':275,'Sender':185,'SentDate':'2011-11-08','Content':'OK!'}]";  
                XMLSerializer serializer = new XMLSerializer(); 
                JSON json = JSONSerializer.toJSON( jsonData ); 
                serializer.setRootName("SampleJSON");
                serializer.setTypeHintsEnabled(false);
                String xml = serializer.write( json );  
                System.out.println(xml);   
                
                
                XMLSerializer xmlSerializer = new XMLSerializer(); 
                JSON json2 = xmlSerializer.read( xml );  
                System.out.println( json2.toString(2) );
                      
                
        }
}