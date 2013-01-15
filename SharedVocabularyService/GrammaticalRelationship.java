package vocabulary;

public class GrammaticalRelationship {
	
	private  String w1;
	private  String gr;
	private  String w2;
	
	
	
	
	public GrammaticalRelationship(String lw1, String lgr, String lw2) {
		w1=lw1;
		gr=lgr;
		w2=lw2;
	}
	
	public boolean isEqual(String lw1, String lgr, String lw2){
		
		return (w1.trim()==lw1.trim()) && (gr.trim()==lgr.trim()) && (w2.trim()==lw2.trim());
	}
	
public boolean isLeftEqual(String lw1){
		
		return (w1.trim()==lw1.trim()) ;
	}

public boolean isRightEqual(String lw2){
	
	return  (w2.trim()==lw2.trim());
}

public boolean isGrEqual(String lgr){
	
	return  (gr.trim()==lgr.trim()) ;
}

public String toString() {
	return w1+','+gr+','+w2;
}

public String getLeft(){
	return w1.trim();
}
public String getGr(){
	return gr.trim();
}
public String getRight(){
	return w2.trim();
}

}
