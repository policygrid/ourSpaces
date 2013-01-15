package wn;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Pointer;
import net.didion.jwnl.data.PointerType;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.data.relationship.RelationshipFinder;
import net.didion.jwnl.data.relationship.RelationshipList;
import net.didion.jwnl.dictionary.Dictionary;

public class Utility {
	public static void init(){
		WNInit.init();
	}
	
	public static Set <String> getSimWords(String word, String poslbl,String btnt){
		Set <String> simwords=new TreeSet<String>();
		
		try{
		IndexWord iw;
		List <Object> poss=new ArrayList();
		if (poslbl.isEmpty())
			poss=POS.getAllPOS();
		else
			poss.add(POS.getPOSForLabel(poslbl));
		
		for (Object ob:poss){
			POS pos =(POS) ob;
			iw= Dictionary.getInstance().lookupIndexWord(pos, word);
			if (iw!=null){
				
				for (Synset s:iw.getSenses()){
					for (Word w:s.getWords()){
						
						simwords.add(w.getLemma());
        			
					}
					String[] ptypes=btnt.split(",");
					for (String t:ptypes){
						for (Pointer p:s.getPointers()){
							//if (p.getType().getKey().equals(t)){
								
								Synset syn=p.getTargetSynset();
								for (Word w:syn.getWords()){
									
									simwords.add(w.getLemma());
			        			
								}
							//}
						}


							
					}
					
				}
        
			}
		}
		
		}catch (Exception ex){
			
			System.out.println(ex.getMessage());
		}
		
		return simwords;
	}
	
	public static void main(String[] args){
		WNInit.init();
		Set<String> simwords=getSimWords("stream", "noun", "+,~");
		for (String s:simwords){
			System.out.println(s);
		}
	}
	
}
