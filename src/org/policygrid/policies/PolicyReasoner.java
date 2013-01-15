package org.policygrid.policies;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;

import org.topbraid.spin.constraints.ConstraintViolation;
import org.topbraid.spin.constraints.SPINConstraints;
import org.topbraid.spin.inference.SPINInferences;
import org.topbraid.spin.system.SPINLabels;
import org.topbraid.spin.system.SPINModuleRegistry;
import org.xml.sax.SAXException;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.shared.ReificationStyle;

import org.openjena.jenasesame.JenaSesame;

import org.openrdf.OpenRDFException;
import org.openrdf.query.TupleQuery;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.sail.memory.MemoryStore;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.sparql.util.QueryExecUtils;

import common.Configuration;
import common.Connections;
import common.Logs;
import common.OntologyBean;
import common.OntologyHandler;
import common.Utility;

/**
 * @author epignott
 *
 * Singleton class for reasoning about provenance policies 
 * 
 */
public class PolicyReasoner {

	//Base model containing all necessary ontologies and instances
	public static OntModel baseModel = null;
	
	//Sesame model linking to sesame repository
	private static Model sesameModel = null;
	
	//variable used to determine if the reasoner has been initialised
	private static boolean initialised = false;

	/**
	 * Method used to initialise the policy resoner
	 */
	public static synchronized void init() {

		long start = System.currentTimeMillis();
		
		if (!initialised) {

			//Register the spin regostry
			SPINModuleRegistry.get().init();

			//Get proxy information from the configuration
			System.setProperty("http.proxyHost",
					Configuration.getParaemter("proxy", "host"));
			System.setProperty("http.proxyPort",
					Configuration.getParaemter("proxy", "port"));

			//Create the base model
			if (baseModel == null) {
				baseModel = ModelFactory
						.createOntologyModel(OntModelSpec.OWL_MEM);

		    //Link sesameModel with sesame repository
				Connections con = new Connections();

				try {
					con.repConnect();
				} catch (RepositoryException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {

					sesameModel = JenaSesame.createModel(con.getRepConnection());

					
				} catch (Exception ex) {
					common.Utility.log.debug(ex);
				}
				
				baseModel.add(sesameModel);

				
				
			   // Initialise the ontology handler

				try {
					common.OntologyHandler ont1 = new common.OntologyHandler();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (SAXException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				
				//Link all the ontologies with the baseModel
				Enumeration e = common.OntologyHandler.hashtable.elements();
				while (e.hasMoreElements()) {
					OntModel mod = (OntModel) e.nextElement();

					baseModel.add(mod);

					common.Utility.log.debug("Loading the model :" + mod.size());

				}

			}

		}

		initialised = true;
		PolicyReasoner.class.notifyAll();

		long end = System.currentTimeMillis();
		
		try {
			Logs.addLogPolicy("", "Init Reasoner", "", end-start,"");
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OpenRDFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create a new policy session used by the policy resoner
	 * 
	 * @return a new policy session
	 */
	public synchronized static PolicySession createSession() {
		if (!initialised) init();
		Model inferences = ModelFactory
				.createDefaultModel(ReificationStyle.Minimal);
		baseModel.addSubModel(inferences);

		OntModel policies = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM);
		baseModel.addSubModel(policies);

		Model context = ModelFactory
				.createDefaultModel(ReificationStyle.Minimal);
		baseModel.addSubModel(context);
			
		PolicySession session = new PolicySession(policies, inferences, context);
		return session;
	}

	/**
	 * Close a policy session
	 * 
	 * @param session the policy session to close
	 */
	public synchronized static void closeSession(PolicySession session) {
		baseModel.removeSubModel(session.policies);
		baseModel.removeSubModel(session.inferences);
		baseModel.removeSubModel(session.context);
	}

	
	
	/**
	 * Commit the inferences contained in a policy session to the sesame repository
	 * 
	 * @param session a policy session
	 */
	public synchronized static void commitSessionInferences(
			PolicySession session) {
		// TODO
	}

	/**
	 * Commit the context contained in a policy session to the sesame repository
	 * @param session a policy session
	 */
	public synchronized static void commitSessionContext(PolicySession session) {
		// TODO
	}

	
	/**
	 * Load a policy from the policy folder to a policy session
	 * 
	 * @param policy the name of the policy ontology
	 * @param session the policy session
	 */
	public synchronized static void loadPolicy(String policy, PolicySession session) {
		if (!initialised) init();
		
		long start = System.currentTimeMillis();
		OntModel pm = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		String bpath = Configuration.getParaemter("policies", "policiesFolder")
				+ policy;
		FileInputStream bin = null;
		try {
			bin = new FileInputStream(bpath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		pm.read(bin, "");
		session.policies.add(pm);

		common.Utility.log.debug("Policy Loaded ...  " + bpath);
		
		long end = System.currentTimeMillis();
		
	    try {
			Logs.addLogPolicy(Integer.toHexString(System.identityHashCode(session)), "Load Policy", policy, (end - start),"");
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OpenRDFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	/**
	 * Load all the policies the policy folder to a policy session
	 * 
	 * @param policy the name of the policy ontology
	 * @param session the policy session
	 */
	public synchronized static void loadAllPolicies(PolicySession session) {
		if (!initialised) init();
		
		// Directory path here
		  String path = Configuration.getParaemter("policies", "policiesFolder");; 
		 
		  String files;
		  File folder = new File(path);
		  File[] listOfFiles = folder.listFiles(); 
		 
		  for (int i = 0; i < listOfFiles.length; i++) 
		  {
		 
		  
		   if (listOfFiles[i].isFile()) 
		   {
			   
		   files = listOfFiles[i].getName();
		       if (files.endsWith(".owl"))
		       {
		          common.Utility.log.debug(files);
		          
  	            long start = System.currentTimeMillis(); 
		          
		      	OntModel pm = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
				String bpath = Configuration.getParaemter("policies", "policiesFolder") + files;
				
				FileInputStream bin = null;
				try {
					bin = new FileInputStream(bpath);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				pm.read(bin, "");
				session.policies.add(pm);

				common.Utility.log.debug("Policy Loaded ...  " + bpath);
				long end = System.currentTimeMillis();
         
				try {
					Logs.addLogPolicy(Integer.toHexString(System.identityHashCode(session)), "Load Policy", files, (end - start),"");
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OpenRDFException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		        }
		     }
		   
		  }		
	
	}
	
	/**
	 * Load all the policies the policy folder to a policy session
	 * 
	 * @param policy the name of the policy ontology
	 * @param session the policy session
	 */
	public synchronized static void loadAllPolicies(PolicySession session, String prefix) {
		if (!initialised) init();
		
		// Directory path here
		  String path = Configuration.getParaemter("policies", "policiesFolder");; 
		 
		  String files;
		  File folder = new File(path);
		  File[] listOfFiles = folder.listFiles(); 
		 
		  for (int i = 0; i < listOfFiles.length; i++) 
		  {
		 
		  
		   if (listOfFiles[i].isFile()) 
		   {
			   
		   files = listOfFiles[i].getName();
		       if (files.endsWith(".owl") && files.startsWith(prefix))
		       {
		          common.Utility.log.debug(files);
		          
  	            long start = System.currentTimeMillis(); 
		          
		      	OntModel pm = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
				String bpath = Configuration.getParaemter("policies", "policiesFolder") + files;
				
				FileInputStream bin = null;
				try {
					bin = new FileInputStream(bpath);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				pm.read(bin, "");
				session.policies.add(pm);

				common.Utility.log.debug("Policy Loaded ...  " + bpath);
				long end = System.currentTimeMillis();
         
				try {
					Logs.addLogPolicy(Integer.toHexString(System.identityHashCode(session)), "Load Policy", files, (end - start),"");
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OpenRDFException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		        }
		     }
		   
		  }		
	
	}

	/**
	 * Run the reasoner based on a specific session
	 * 
	 * @param session a policy session
	 */
	public synchronized static void run(PolicySession session) {
		if (!initialised) init();
		
		// Initialize system functions and templates

		long start = System.currentTimeMillis();

		// SPINModuleRegistry.get().registerAll(ontModel);
		SPINModuleRegistry.get().registerAll(baseModel);

		// Run all inferences
   	    SPINInferences.run(baseModel, session.inferences, null, null, false, null);
		common.Utility.log.debug("Inferred triples: " + session.inferences.size());

		ByteArrayOutputStream sos = new ByteArrayOutputStream();
		session.inferences.write(System.out, "N-TRIPLE");
		session.inferences.write(sos);
		
		long end = System.currentTimeMillis();

		common.Utility.log.debug("The reasoner took :" + (end - start) + " millisecond(s)");
		
		try {
			Logs.addLogPolicy(Integer.toHexString(System.identityHashCode(session)), "Run Policy Reasoner", "", (end - start),sos.toString());
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OpenRDFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
