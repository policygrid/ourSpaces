package common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.openrdf.OpenRDFException;
import org.openrdf.query.GraphQuery;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.QueryLanguage;
import org.policygrid.ontologies.AcademicDiscipline;
import org.policygrid.ontologies.FOAF;
import org.policygrid.ontologies.OurSpacesVRE;
import org.xml.sax.SAXException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

public class PersonCreator {
	
	Connections con = new Connections();
	
	public PersonCreator()
	{
		
	}
	
	/**
	 * Creates a new user in ourSpaces in regards to RDF. A FOAF person is created
	 * containing a name and email, and that holds an ourSpaces User account. This
	 * ourSpaces user account is equivalent to a SIOC user.
	 * 
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @throws OpenRDFException
	 * @throws IOException
	 * @throws ServletException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public String createPerson(String firstname, String lastname, String email) throws OpenRDFException, IOException, ServletException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParserConfigurationException, SAXException
	{
		// Create empty Ontology Model
		Model model = ModelFactory.createDefaultModel();
		RDFi rdf = new RDFi();
		
		String foafResourceID = "http://xmlns.com/foaf/0.1/#" + UUID.randomUUID().toString();
		String ourSpacesAccountID = "http://www.policygrid.org/ourspacesVRE.owl#" + UUID.randomUUID().toString();
		String addressID = "http://www.policygrid.org/utility.owl#" + UUID.randomUUID().toString();
		
		/*
		 * Checks if a user has been described previously. If so, the profiles are merged.
		 */
		String potentialRegisteredID = rdf.getResourceIDFromRDF(email, FOAF.mbox.toString(), FOAF.Person.toString());
		if(!potentialRegisteredID.equals("")) 
		{
			foafResourceID = potentialRegisteredID;
			deleteUserInfo(potentialRegisteredID,FOAF.firstName.toString());
			deleteUserInfo(potentialRegisteredID,FOAF.surname.toString());
			deleteUserInfo(potentialRegisteredID,RDFS.label.toString());	
		}
		
		Resource address = model.createResource(addressID);
		address.addProperty(RDF.type, org.policygrid.ontologies.Utility.Address);
		
		Resource ourSpacesUser = model.createResource(ourSpacesAccountID);
		ourSpacesUser.addProperty(RDF.type, OurSpacesVRE.OurSpacesAccount);
        
		Resource newAccount  = model.createResource(foafResourceID);
		newAccount.addProperty(RDF.type, FOAF.Person);       
        newAccount.addProperty(FOAF.firstName,firstname);
        newAccount.addProperty(FOAF.surname,lastname);
        newAccount.addProperty(FOAF.mbox,email);
        newAccount.addProperty(FOAF.holdsAccount,ourSpacesUser);
        newAccount.addProperty(org.policygrid.ontologies.Utility.hasAddress, address);
        newAccount.addProperty(RDFS.label, firstname + " " + lastname + " : " + email);
        
   		Resource disciplineInfo = AcademicDiscipline.getNewDisciplineInfo(model);
   		newAccount.addProperty(OurSpacesVRE.hasDisciplineInfo, disciplineInfo);
		
        ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		model.write(out);
		InputStream in = new ByteArrayInputStream(out.toByteArray());
		rdf.write(in);
		
		/*
		 * Create user container by default, then create a blog container
		 * and add it to user container.
		 * 
		 * Every user has a personal blog by default.
		 */
		Container container = new Container();
		String containerID = container.createUserContainer(ourSpacesAccountID);
		String blogContainer = container.createBlogContainer(containerID);
		
		Tag tag = new Tag();
		tag.addTag(firstname, "", foafResourceID);
		tag.addTag(lastname, "", foafResourceID);
		
		
		return ourSpacesAccountID;
	}
	
	public static String encrypt(String s) throws NoSuchAlgorithmException
	  {;
		MessageDigest m=MessageDigest.getInstance("MD5");
		 m.update(s.getBytes(),0,s.length());
		 String code = new BigInteger(1,m.digest()).toString(16);
		 return code;
	  }
	
	/**
	 * Adds a new username and password into the Users database table.
	 * The ID of the newly created user is returned in a seperate query.
	 * 
	 * @param username
	 * @param password
	 * @return Newly created userid
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException 
	 * @throws OpenRDFException 
	 * @throws NoSuchAlgorithmException 
	 */
	public int addUser(String username, String password, String rdf2) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, OpenRDFException, IOException, NoSuchAlgorithmException
	{
		con.connect();
		Statement st = con.getCon().createStatement();
		
		RDFi rdf = new RDFi();
		
		String foaf = rdf.getFOAFIDFromOurSpacesUser(rdf2);
		String authKey = UUID.randomUUID().toString();
		String newPass = encrypt(password);
		
		String sql = "INSERT INTO user (email, password, rdf, foaf, authKey, confirmed, terms_and_conditions) VALUES('"+username+"','"+newPass+"', '"+rdf2+"','"+foaf+"', '"+authKey+"', false, true)";
		st.executeUpdate(sql);
		
		Email.send(username, "ourSpaces Email Confirmation", "To activate your account, please click the link below. \n\n http://www.ourspaces.net/authUser/"+authKey);
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("select userid from user where email=\'");
		qry.append(username);
		qry.append("\'");
		
		ResultSet rs = st.executeQuery(qry.toString());
		int i = 0;
		while(rs.next())
		{
			i = rs.getInt("userid");
		}
		
		rs.close();
		st.close();
		con.disconnect();
		return i;
	}
	
	public void editProfilef(HttpServletRequest request) throws OpenRDFException, IOException, ServletException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParserConfigurationException, SAXException
	{
		RDFi rdf = new RDFi();
		
		HttpSession session = request.getSession();
    	User user = (User) session.getAttribute("use");
    	int userid = user.getID();
    	String foafID = user.getFOAFID();
    	String addressID = rdf.getAddressID(foafID);
    	
    	Model model = ModelFactory.createDefaultModel();
    	Resource address = model.createResource(addressID);
		Resource person = model.createResource(foafID);

		// Create discipline info if user doesn't already have one (as previously created accounts don't have one): 
		String disciplineInfoId = user.getPropertyValue(userid, OurSpacesVRE.hasDisciplineInfo.toString());
		Resource disciplineInfo = null;
    	if (disciplineInfoId == null || "".equals(disciplineInfoId)) {
    		disciplineInfo = AcademicDiscipline.getNewDisciplineInfo(model);
    		person.addProperty(OurSpacesVRE.hasDisciplineInfo, disciplineInfo);
			disciplineInfoId = disciplineInfo.getURI();
		} else {
			disciplineInfo = model.createResource(disciplineInfoId);
		}
		
		Enumeration paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()) 
		{
        	String param = (String)paramNames.nextElement();
        	String[] paramValues = request.getParameterValues(param);
			if(param.equals("name"))
        	{
        		if(paramValues.length != 0)
        		{
	        		String fields[] = paramValues;
	        		String name = fields[0];
	        		name = name.replaceAll("'", "\\\\'");
	        		String[] names = name.split(" ");
	        		deleteUserInfo(foafID, FOAF.firstName.toString());
	        		
	        		person.addProperty(FOAF.firstName, names[0]);
	        		if(names.length > 1) {
	        			deleteUserInfo(foafID, FOAF.surname.toString());
	        			person.addProperty(FOAF.surname, names[1]);
	        		}
        		}
        	}
        	if (param.equals("gender")) {
        		updateLiteralPropertyValue(person, FOAF.gender, getFirstValue(paramValues));
        	}
        	if (param.equals("researchInterest")) {
        		updateLiteralPropertyValue(person, OurSpacesVRE.hasResearchInterest, getFirstValue(paramValues));
        	}
        	if (param.equals("website")) {
        		updateLiteralPropertyValue(person, OurSpacesVRE.hasWebsite, getFirstValue(paramValues));
        	}
        	if (param.equals("title")) {
        		updateLiteralPropertyValue(person, OurSpacesVRE.hasJobTitle, getFirstValue(paramValues));
        	}
        	if (param.equals("academicDiscipline")) {
        		updateObjectPropertyValue(disciplineInfo, AcademicDiscipline.hasAcademicDiscipline, getFirstValue(paramValues));
        	}
        	if (param.equals("disciplineTag")) {
        		updateLiteralPropertyValue(disciplineInfo, AcademicDiscipline.hasDisciplineTag, getFirstValue(paramValues));
        	}
        	if (param.equals("number")) {
        		updateLiteralPropertyValue(address, org.policygrid.ontologies.Utility.houseNumber, getFirstValue(paramValues));
        	}
        	if (param.equals("street")) {
        		updateLiteralPropertyValue(address, org.policygrid.ontologies.Utility.street, getFirstValue(paramValues));
        	}
        	if(param.equals("town")) {
        		updateLiteralPropertyValue(address, org.policygrid.ontologies.Utility.place, getFirstValue(paramValues));
        	}
        	if (param.equals("country")) {
        		updateLiteralPropertyValue(address, org.policygrid.ontologies.Utility.country, getFirstValue(paramValues));
        	}
        	if (param.equals("postcode")) {
        		updateLiteralPropertyValue(address, org.policygrid.ontologies.Utility.postcode, getFirstValue(paramValues));
        	}
        	if (param.equals("skypeID")) {
        		String fields[] = paramValues;
        		//UPDATE t1 SET col1 = col1 + 1;
        		con.connect();
        		Statement st = con.getCon().createStatement();
        		String sql = "UPDATE user SET skypeID = \""+fields[0]+"\" WHERE userid = "+userid;
        		st.executeUpdate(sql);
        		st.close();
        		con.disconnect();
        	}
        	        	
		}
		
        ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		model.write(out);
		InputStream in = new ByteArrayInputStream(out.toByteArray());
		rdf.write(in);
		
		// Creates an instance of the Activity JavaBean
		Activities act = new Activities();
		act.addActivity(userid, 12, "", ""); // adds the update to the activities log
	}

	private void updateLiteralPropertyValue(Resource resource, Property property,
			String propertyValue) throws ServletException, IOException,
			OpenRDFException {
		deleteUserInfo(resource.getURI(), property.toString());
		if (Utility.isNotNull(propertyValue)) {
			resource.addProperty(property, propertyValue);
		}
	}
	
	private void updateObjectPropertyValue(Resource resource, Property property,
			String propertyValue) throws ServletException, IOException,
			OpenRDFException {
		deleteUserInfo(resource.getURI(), property.toString());
		if (Utility.isNotNull(propertyValue)) {
			resource.addProperty(property, resource.getModel().createResource(propertyValue));
		}
	}

	/**
	 * Get the first String of a list and format it to be stored in the repository 
	 * @param paramValues list of Strings
	 * @return formated first element
	 */
	private String getFirstValue(String[] paramValues) {
		if(paramValues.length != 0)
		{
			String fields[] = paramValues;
			String firstParam = fields[0];
			if(!firstParam.equals(""))
			{
				firstParam = firstParam.replaceAll("'", "\\\\'");
				return firstParam;
			}
		}
		return null;
	}
	
	public void deleteUserInfo(String resourceID, String property) throws ServletException, IOException, OpenRDFException
	{
		StringBuffer qry = new StringBuffer(1024);
		qry.append("construct { ");
		qry.append("<"+resourceID+"> <"+property+"> ?a } where {");
		qry.append("<"+resourceID+"> <"+property+"> ?a } ");

		
		String query = qry.toString();
		
		con.repConnect();
		
		GraphQuery q = con.getRepConnection().prepareGraphQuery(QueryLanguage.SPARQL, query);
		GraphQueryResult result = q.evaluate();
		con.getRepConnection().remove(result);
		
		con.repDisconnect();
	}
	
}
