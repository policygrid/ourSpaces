/**
 * 
 */
package wn;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;

/**
 * @author kapila
 *
 */
public class WNInit {
	//initialises the word net dictionery service
	public static void init(){
		try {  
			JWNL.initialize(TestDefaults.getInputStream());
			
			
		} catch (JWNLException e) {
			e.printStackTrace();
		}
	}

}
