package lexicon.builder;
import java.io.FileInputStream;
import java.io.InputStream;

public class LexDefaults {
 
	    /**
	     * The location of the configuration file. 
	     */
	    //public static String CONFIG_PATH = "/Users/kapila/Documents/repository/lexicon/config/";
	    public static String CONFIG_PATH = "/home/policygrid/apache-tomcat-6.0.18/data/lexicons/config/";
	    
	    
	    
	    /**
	     * The name of the file configuration. 
	     */
	    public static String FILE_CONFIG_NAME = "properties.xml";
	   
	    /**
	     * Gets the input stream based on the type. 
	     * @return input stream
	     */
	    public static InputStream getInputStream() {
	        try {
	        		System.out.println(CONFIG_PATH + FILE_CONFIG_NAME);
	                return new FileInputStream(CONFIG_PATH + FILE_CONFIG_NAME);
	           
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	    
	    public static void main(String[] args){
	    	PersonalLexicon pl =new PersonalLexicon();
	    	pl.init(LexDefaults.getInputStream(),"");
	    	
	    }
}
