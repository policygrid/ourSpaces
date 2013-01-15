package svs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GR {
	
	private  String head;
	private  String gr;
	private  String dep;
	private  String au;
	
	
	public static void main(String args[]){
		GR t=new GR("show", "sub","analysis","");
		System.out.println(t.toString());
		Text2Mem mem=new Text2Mem("D:/repository/parsed/bak");
		List<GR> l=mem.getAuthorList("cullinane");
		double d= t.getMINoun(l);
		System.out.println(d);
	}
	
	
	public GR(String W1,String GR,String W2,String AU ) {
		if (W1!=null)
			head=W1.trim();
		else
			head="";
		
		if (GR!=null)
			gr=GR.trim();
		else
			gr="";
		
		
		if (W2!=null)
			dep=W2.trim();
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
	
	
	
public boolean isLeftEqual(String lw1){
		
		return (head.equals(lw1.trim())) ;
	}

public boolean isRightEqual(String lw2){
	
	return  (dep.equals(lw2.trim()));
}

public boolean isGrEqual(String lgr){
	
	return  gr.contains(lgr.trim()) ;
}
public boolean isAuthor(String a){
	
	return au.equals(a);
}
public String toString() {
	return head+','+gr+','+dep;
}

public String getLeft(){
	return head;
}
public String getGr(){
	return gr;
}

public String getRight(){
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

public double getMINoun(List <GR> list){
	
	int countLeft=0;
	int countRight=0;
	
	int countR=0;
	int countGR=0;
	
	Iterator<GR> iterator = list.iterator();
	while (iterator.hasNext()) {
		GR t=iterator.next();
		//System.out.println(t.toString()+"====="+this.toString());
		if (t.isEqual(head,gr,dep))
			countGR++;
	}
	//System.out.println(countGR);
	
	iterator = list.iterator();
	while (iterator.hasNext()) {
		GR t=iterator.next();
		if (t.isGrEqual(gr))
			countR++;
			
	}
	//System.out.println(countR);
	//interchange w1 and w2
	iterator = list.iterator();
	while (iterator.hasNext()) {
		GR t=iterator.next();
		
		if (t.isLeftEqual(head)&& t.isGrEqual(gr)){
			
			countLeft++;
		}
	}
	//interchange w1 and w2
	iterator = list.iterator();
	while (iterator.hasNext()) {
		GR t=iterator.next();
		if (t.isRightEqual(dep)&& t.isGrEqual(gr))
			countRight++;
	}
	
	
	double top=countR*countGR;
	double bottom=countLeft*countRight;
	
//	System.out.println("count GR "+countGR);
//	System.out.println("count R "+countR);
//	System.out.println("count Left "+countLeft);
//	System.out.println("count Right "+countRight);
	
	if ((bottom!=0)&&(top!=0))
		return -Math.log(top/bottom);
	else
		return 0;
	
	
	
	
	
	
		
}


public double getMIVerb(List <GR> list){
	
	int countLeft=0;
	int countRight=0;
	
	int countR=0;
	int countGR=0;
	
	Iterator<GR> iterator = list.iterator();
	while (iterator.hasNext()) {
		GR t=iterator.next();
		//System.out.println(t.toString()+"====="+this.toString());
		if (t.isEqual(head,gr,dep))
			countGR++;
	}
	//System.out.println(countGR);
	
	iterator = list.iterator();
	while (iterator.hasNext()) {
		GR t=iterator.next();
		if (t.isGrEqual(gr))
			countR++;
			
	}
	//System.out.println(countR);
	//interchange w1 and w2
	iterator = list.iterator();
	while (iterator.hasNext()) {
		GR t=iterator.next();
		
		if (t.isLeftEqual(head)&& t.isGrEqual(gr)){
			
			countLeft++;
		}
	}
	//interchange w1 and w2
	iterator = list.iterator();
	while (iterator.hasNext()) {
		GR t=iterator.next();
		if (t.isRightEqual(dep)&& t.isGrEqual(gr))
			countRight++;
	}
	
	
	double top=countR*countGR;
	double bottom=countLeft*countRight;
	
//	System.out.println("count GR "+countGR);
//	System.out.println("count R "+countR);
//	System.out.println("count Left "+countLeft);
//	System.out.println("count Right "+countRight);
	
	if ((bottom!=0)&&(top!=0))
		return -Math.log(top/bottom);
	else
		return 0;
	
	
	
	
	
	
		
}



}



