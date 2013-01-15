package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;

import com.hp.hpl.jena.ontology.OntModel;

/**
 * Handles all connections to various repositories, including 
 * databases, RDF repositories and Digital Object respositories.
 * 
 * @author Richard Reid
 * @version 1.0
 */

public class Connections {
	
	public static final String baseURI = Configuration.getParaemter("repository","url");
	public static final String repository = Configuration.getParaemter("repository","repository");
	@Deprecated public static final String testRepository = "ourSpacesVRE2";
	
	@Deprecated String utilityOntology = "/home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/Utility.xml";
	@Deprecated String resourceOntology = "/home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/ontologies/Resource.owl";
	@Deprecated String vreOntology = "/home/policygrid/apache-tomcat-6.0.18/webapps/ourspaces/ontologies/OurSpacesVRE.owl";
	
	Connection con;
	RepositoryConnection connection;
	
	
	public Connections()
	{
		
	}
	
	/**
	 * 
	 * Connect to the database
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	//added zeroDateTimeBehavior=convertToNull by kapila 12/09/11
	public void connect() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		Class.forName(Configuration.getParaemter("database","driver")).newInstance();
		con = DriverManager.getConnection(Configuration.getParaemter("database","url"),Configuration.getParaemter("database","username"),Configuration.getParaemter("database","password"));
	}
	
	/**
	 * 
	 * Disconnecting from the database
	 * 
	 * @throws SQLException
	 */
	public void disconnect() throws SQLException
	{
		con.close();
	}
	
	/**
	 * Returns the database connection variable
	 * 
	 * @return
	 */
	public Connection getCon()
	{
		return con;
	}
	
	/**
	 * Connect to the RDF repository
	 * 
	 * @throws RepositoryException
	 */
	public void repConnect() throws RepositoryException
	{
		Repository rep = new HTTPRepository(Configuration.getParaemter("repository","url"), repository);
		rep.initialize();
		connection = rep.getConnection();
	}
	
	/**
	 * Connect to the TEST RDF repository
	 * 
	 * @throws RepositoryException
	 */
	@Deprecated public void repTestConnect() throws RepositoryException
	{
		Repository rep = new HTTPRepository(baseURI, testRepository);
		rep.initialize();
		connection = rep.getConnection();
	}
	
	/**
	 * Disconnecting from the repository
	 * 
	 * @throws RepositoryException
	 */
	public void repDisconnect() throws RepositoryException
	{
		connection.close();
	}
	
	public RepositoryConnection getRepConnection()
	{
		return connection;
	}
	
	public String getBaseURI()
	{
		return baseURI;
	}
	
	public String getRepository()
	{
		return repository;
	}
	
	@Deprecated public String getTestRepository()
	{
		return testRepository;
	}
	
	@Deprecated public String getUtilityOntology()
	{
		return utilityOntology;
	}
	
	@Deprecated  public String getResourceOntology()
	{
		return resourceOntology;
	}
	
	@Deprecated  public String getVREOntology()
	{
		return vreOntology;
	}
	
}
