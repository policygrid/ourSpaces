package common;

public class Folder  {

	String Name;
	String ID;
	
	public Folder(String name, String iD) {
		super();
		Name = name;
		ID = iD;
	}
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	
	public boolean equals(Folder fol) {
		
		if ((fol.getName().equals(this.getName())) && (fol.getID().equals(this.getID()))) {
			return true;
		} else {
			return false;
		}
	}
	
}
