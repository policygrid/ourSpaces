package common;

import java.util.ArrayList;

public class DirectoryNode {
public String directoryName;
public ArrayList subDirectories;
public int level;
public String path;

public DirectoryNode(String dir, String path,  int level) {
	this.directoryName = dir;
	this.subDirectories = new ArrayList();
	this.level = level;
	this.path = path;
}

public static DirectoryNode containsDirectory(ArrayList list, String directory) {
	DirectoryNode ret = null;
	
	for (int i = 0; i < list.size(); i++) {
		DirectoryNode tmp = (DirectoryNode)list.get(i);
		if (tmp.directoryName.equals(directory))  ret = tmp;
	}
	
	return ret;
}

}
