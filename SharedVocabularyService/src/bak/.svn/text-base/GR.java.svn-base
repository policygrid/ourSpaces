package bak;

public class GR {
	
	private  String w1;
	private  String gr;
	private  String w2;
	private  String au;
	
	
	
	public GR(String W1,String GR,String W2,String AU ) {
		if (W1!=null)
			w1=W1;
		else
			w1="";
		
		if (GR!=null)
			gr=GR;
		else
			gr="";
		
		
		if (W2!=null)
			w2=W2;
		else
			w2="";
		if (AU!=null)
			au=AU;
		else
			au="";
		
	}
	
	public boolean isEqual(String lw1, String lgr, String lw2){
		
		return (w1.equals(lw1) && gr.contains(lgr) && w2.equals(lw2));
	}
	
	
	
public boolean isLeftEqual(String lw1){
		
		return (w1.equals(lw1)) ;
	}

public boolean isRightEqual(String lw2){
	
	return  (w2.equals(lw2));
}

public boolean isGrEqual(String lgr){
	
	return  gr.trim().contains(lgr) ;
}
public boolean isAuthor(String a){
	
	return au.equals(a);
}
public String toString() {
	return w1+','+gr+','+w2;
}

public String getLeft(){
	return w1;
}
public String getGr(){
	return gr;
}

public String getRight(){
	return w2;
}
public String getAuth(){
	return au;
}
public String obj(String w){
	String rw="";
	if (gr.contains("obj")&&(w.equals(w1)))
		rw=w2;
	return rw;
		
}
public String obj_of(String w){
	String lw="";
	if (gr.contains("obj")&&(w.equals(w2)))
		lw=w1;
	return lw;
		
}
public String sub(String w){
	String rw="";
	if (gr.contains("sub")&&(w.equals(w1)))
		rw=w2;
	return rw;
		
}

public String sub_of(String w){
	String lw="";
	if (gr.contains("sub")&&(w.equals(w2)))
		lw=w1;
	return lw;
		
}

public String mod(String w){
	String rw="";
	if (gr.contains("mod")&&(w.equals(w1)))
		rw=w2;
	return rw;
		
}

public String mod_of(String w){
	String lw="";
	if (gr.contains("mod")&&(w.equals(w2)))
		lw=w1;
	return lw;
		
}

}



