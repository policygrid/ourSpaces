package lexicon.builder;
import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;


// TODO: Auto-generated Javadoc
/**
 * The Class simpleNLG.
 */
public class simpleNLG {
	
	/** The nlg factory. */
	NLGFactory nlgFactory;
	
	/** The realiser. */
	Realiser realiser ;
	
	/**
	 * Instantiates a new simple nlg.
	 */
	public simpleNLG(){
		Lexicon lexicon = Lexicon.getDefaultLexicon();
        nlgFactory = new NLGFactory(lexicon);
        realiser = new Realiser(lexicon);
        
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		simpleNLG t=new simpleNLG();
		
        System.out.println(t.getPlural("artefact"));
        System.out.println(t.getPlural("activity"));
        
}
	
	/**
	 * Gets the plural.
	 *
	 * @param s the s
	 * @return the plural
	 */
	protected String getPlural(String s){
		
        // this is a work-around to get the plural form of nouns
		//in the absence of a method to get the plural form a noun phrase without realising a sentence
		SPhraseSpec p = nlgFactory.createClause();
        NPPhraseSpec subj=nlgFactory.createNounPhrase("Mary");
        NPPhraseSpec obj=nlgFactory.createNounPhrase(s);
        VPPhraseSpec verb=nlgFactory.createVerbPhrase("use");
        obj.setPlural(true);
        
        p=nlgFactory.createClause(subj, verb,obj);
         
        String  output = realiser.realiseSentence(p); // Realiser created earlier.
        String noun=output.split(" ") [2];
        noun=noun.substring(0, noun.length()-1);
		
        return noun;
		
	}
	
	
}