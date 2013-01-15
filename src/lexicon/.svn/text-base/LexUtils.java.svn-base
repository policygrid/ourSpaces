package lexicon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Pointer;
import net.didion.jwnl.data.PointerType;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.data.relationship.Relationship;
import net.didion.jwnl.data.relationship.RelationshipFinder;
import net.didion.jwnl.data.relationship.RelationshipList;
import net.didion.jwnl.dictionary.Dictionary;

public class LexUtils {
	public static void init(){
		WNInit.init();
	}
	
	public static Set <String> getSimWords(String word, String poslbl,String btnt){
		Set <String> simwords=new TreeSet<String>();
		
		try{
		IndexWord iw;
		String[] ptypes=btnt.split(",");
		List<String> typeList = Arrays.asList(ptypes);
		List <Object> poss=new ArrayList();
		if (poslbl == null || poslbl.equals(""))
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
					
					
					for (Pointer p:s.getPointers()){
						
						//for (String t:typeList){
						if (btnt.equals("all")){
							Synset syn=p.getTargetSynset();
							for (Word w:syn.getWords()){
								
								simwords.add(w.getLemma());
		        			
							}
						}else if (typeList.contains(p.getType().getKey()))
						{
								
								Synset syn=p.getTargetSynset();
								for (Word w:syn.getWords()){
									
									simwords.add(w.getLemma());
			        			
								}
							
						
						}

							
					}
					
				}
        
			}
		}
		
		}catch (Exception ex){
			
			common.Utility.log.debug(ex.getMessage());
		}
		
		return simwords;
	}
	
	public static void isRelationshipExist(String w1,String w2,String poslbl){
		
		
		try{
		IndexWord iw1,iw2;
	
		List <Object> poss=new ArrayList();
		if (poslbl == null || poslbl.equals(""))
			poss=POS.getAllPOS();
		else
			poss.add(POS.getPOSForLabel(poslbl));
		
		for (Object ob:poss){
			
			POS pos =(POS) ob;
			iw1= Dictionary.getInstance().lookupIndexWord(pos, w1);
			iw2= Dictionary.getInstance().lookupIndexWord(pos, w2);
			for (Synset s1:iw1.getSenses()){
				common.Utility.log.debug("pos");
				for (Synset s2:iw2.getSenses()){
					RelationshipList rl =RelationshipFinder.getInstance().findRelationships(s1, s2, PointerType.HYPONYM);
					
				}
			}
					
				
        
			
		}
		
		}catch (Exception ex){
			
			common.Utility.log.debug(ex.getMessage());
		}
		
		
	}
	public static void main(String[] args){
//		WNInit.init();
//		Set<String> simwords=getSimWords("km", "", "");
//		for (String s:simwords){
//			common.Utility.log.debug(s);
//		}
		
		isRelationshipExist("stream","stream","");
		
	}
	
}
