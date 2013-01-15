package wn;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Pointer;
import net.didion.jwnl.data.PointerType;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.data.relationship.RelationshipFinder;
import net.didion.jwnl.data.relationship.RelationshipList;
import net.didion.jwnl.dictionary.Dictionary;

public class WNtest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {  
			//[conclude, square]+++++++[employ, implication, present, produce]
			
			JWNL.initialize(TestDefaults.getInputStream());
			
			System.out.println(System.nanoTime());
			IndexWord iw = Dictionary.getInstance().lookupIndexWord(POS.NOUN, "stream");
			//IndexWord iw2 = Dictionary.getInstance().lookupIndexWord(POS.VERB, "method");
			System.out.println(System.nanoTime());
			System.out.println(iw.getSenseCount());
		
			for (Synset s:iw.getSenses()){
		
				System.out.println(s.toString());
				Pointer[] pa=s.getPointers();
				
				for (Pointer p:pa){
					
					System.out.println(p.getType()+"="+p.getTargetSynset());
					System.out.println();
				}
				
//				for (Word w:s.getWords()){
//				System.out.println(w.getLemma());
//				}
			}
//			for (Synset s:iw2.getSenses()){
//				
//				
//				for (Word w:s.getWords()){
//				System.out.println(w.getLemma());
//				}
//			}
			
		} catch (JWNLException e) {
			e.printStackTrace();
		}
	}

}
