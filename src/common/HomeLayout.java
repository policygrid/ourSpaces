package common;

import java.util.ArrayList;

public class HomeLayout {
	
	public ArrayList left, middle, right;
	
	public HomeLayout(ArrayList left, ArrayList middle, ArrayList right){
		this.left = left;
		this.right = right;
		this.middle = middle;
	}
	
	public ArrayList getLeft(){
		return left;
	}
	
	public ArrayList getMiddle(){
		return middle;
	}
	
	public ArrayList getRight(){
		return right;
	}
	
	public int check(String name, ArrayList temp){
  		int no = 0;
		for(int i =0; i<temp.size(); i++){
			Box box = (Box) temp.get(i);
			if(box.getName().equals(name))
				no = 1;
		}
		return no;
	}
	
	public String getName(String element){
		String name;
		if(element.equals("myresources"))
			name = "My Resources";
		else if(element.equals("mytags"))
			name = "My Tags";
		else if(element.equals("myprojects"))
			name = "My Projects";
		else if(element.equals("myactivities"))
			name = "My Activities";
		else if(element.equals("mygroups"))
			name = "My Groups";
		else if(element.equals("mytools"))
			name = "My Tools";
		else if(element.equals("mycontacts"))
			name = "My Contacts";
		else if(element.equals("ouractivities"))
			name = "Our Activities";
		else if(element.equals("alltags"))
			name = "All Tags";
		else if(element.equals("timeline"))
			name = "Resource Timeline";
		else if(element.equals("myblog"))
			name = "My Blog";
		else
			name = "Our Tags";
		return name;
	}

}
