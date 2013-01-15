/**
 * 
 */
package lexicon;

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
			if (!JWNL.isInitialized())
			JWNL.initialize(TestDefaults.getInputStream());
			
			
		} catch (JWNLException e) {
			e.printStackTrace();
		}
	}

}
