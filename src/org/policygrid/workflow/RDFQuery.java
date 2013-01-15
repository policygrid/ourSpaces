package org.policygrid.workflow;


import java.util.List;

import common.*;

public class RDFQuery {
	
	public static List<Resources> getArtefacts(int userId, User user) {		
		List<Resources> data;
		try {
			data = user.getResources(userId);
			return data;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
