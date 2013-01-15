package lexicon.builder;


import java.util.List;

public class GR {
	
	private  String head;
	private  String gr;
	private  String dep;
	private  String au;
	
	
	public static void main(String args[]){
//		GR t=new GR("access", "mod","better","");
//		System.out.println(t.toString());
//		String root="/Users/kapila/Documents/repository/parsed/";
//		//String root="D:/repository/parsed/bak";
//		System.out.println(System.currentTimeMillis());
//		Text2Mem mem=new Text2Mem(root,);
//		System.out.println(System.currentTimeMillis());
//		List<GR> l=mem.getAuthorList("dargay");
//		System.out.println(System.currentTimeMillis());
//		double d= t.getMI(l);
//		System.out.println(System.currentTimeMillis());
//		System.out.println(d);
		
	}
	
	
	public GR(String W1,String GR,String W2,String AU ) {
		if (W1!=null)
			head=W1;
		else
			head="";
		
		if (GR!=null)
			gr=GR;
		else
			gr="";
		
		
		if (W2!=null)
			dep=W2;
		else
			dep="";
		if (AU!=null)
			au=AU;
		else
			au="";
		
	}
	
	public boolean isEqual(String lw1, String lgr, String lw2){
		
		return (head.equals(lw1.trim()) && gr.contains(lgr.trim()) && dep.equals(lw2.trim()));
	}
	
	
	
public boolean isHeadEqual(String lw1){
		
		return (head.equals(lw1)) ;
	}

public boolean isDepEqual(String lw2){
	
	return  (dep.equals(lw2));
}

public boolean isGrEqual(String lgr){
	
	return  gr.contains(lgr) ;
}
public boolean isAuthor(String a){
	
	return au.contains(a);
}
public String toString() {
	return head+','+gr+','+dep+','+au;
}

public String getHead(){
	return head;
}
public String getGr(){
	return gr;
}

public String getDep(){
	return dep;
}
public String getAuth(){
	return au;
}
public String obj(String w){
	String rw="";
	if (gr.contains("obj")&&(w.equals(head)))
		rw=dep;
	return rw;
		
}
public String obj_of(String w){
	String lw="";
	if (gr.contains("obj")&&(w.equals(dep)))
		lw=head;
	return lw;
		
}
public String sub(String w){
	String rw="";
	if (gr.contains("sub")&&(w.equals(head)))
		rw=dep;
	return rw;
		
}

public String sub_of(String w){
	String lw="";
	if (gr.contains("sub")&&(w.equals(dep)))
		lw=head;
	return lw;
		
}

public String mod(String w){
	String rw="";
	if (gr.contains("mod")&&(w.equals(head)))
		rw=dep;
	return rw;
		
}

public String mod_of(String w){
	String lw="";
	if (gr.contains("mod")&&(w.equals(dep)))
		lw=head;
	return lw;
		
}

public double getMI(List <GR> list){
	
	int countHead=0;
	int countDep=0;	
	int countR=0;
	int countGR=0;

	for (GR t:list){		
		//System.out.println(t.toString()+"====="+this.toString());
		if (t.isEqual(head,gr,dep))
			countGR++;
	}
	//System.out.println(countGR);
	
	for (GR t:list){		
		if (t.isGrEqual(gr))
			countR++;
			
	}
	//System.out.println(countR);
	//interchange w1 and w2
	for (GR t:list){		
		if (t.isHeadEqual(head)&& t.isGrEqual(gr)){
			//System.out.println(t.toString());
			countHead++;
		}
	}
	//interchange w1 and w2
	for (GR t:list){		
		if (t.isDepEqual(dep)&& t.isGrEqual(gr))
			countDep++;
	}
	
	
	double dividend=countR*countGR;
	double divisor=countHead*countDep;
	
//	System.out.println("R"+countR);
//	System.out.println("GR"+countGR);
//	System.out.println("H"+countHead);
//	System.out.println("D"+countDep);
	double mi=0.0;
	if (divisor!=0)
		mi= Math.log(dividend/divisor);
	
	if (mi>0)
		return mi;
	else
		return 0.0;
			
	
	
	
	
	
	
		
}




}



