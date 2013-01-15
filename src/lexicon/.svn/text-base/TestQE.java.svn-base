package lexicon;

import java.io.FileReader;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


import net.didion.jwnl.data.POS;

public class TestQE {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			WNInit.init();
		    try {
		      //first use a Scanner to get each line
		    	Scanner scanner = new Scanner(new FileReader("/Users/kapila/Documents/repository/experiment2/results/tobeanalysed/cullinane1Vsdargay2M3.csv"));
				int sameCount=0;
				int simCount=0;
				int i=0;
		    	while (scanner.hasNextLine()&&(i<=20)){
		    		i++;
		    		String aLine=scanner.nextLine();
		    		String w1=aLine.split(",")[0].trim();
		    		String w2=aLine.split(",")[1].trim();
		    		Set <String> words=LexUtils.getSimWords(w2,"","+,~" );
		    		if (w1.equals(w2)){
		    			sameCount++;
		    			
		    		}
		    		else if (words.contains(w1)){
		    			simCount++;
		    			//common.Utility.log.debug(aLine);
		    		}
		    	
		    	}
		    	common.Utility.log.debug("Same word:"+sameCount);
		    	common.Utility.log.debug("Sim word:"+simCount);
		    }catch (Exception ex){
		    	
		    }
		
		
	}

}
