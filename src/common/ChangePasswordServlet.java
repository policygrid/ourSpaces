package common;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.repository.RepositoryException;

import static java.lang.Math.round;
import static java.lang.Math.random;
import static java.lang.Math.pow;
import static java.lang.Math.abs;
import static java.lang.Math.min;
import static org.apache.commons.lang.StringUtils.leftPad;

public class ChangePasswordServlet extends HttpServlet {
	
	public ChangePasswordServlet()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
        
		String cmd = request.getParameter("action");
		if (cmd != null) 
		{
			if (cmd.equals("new")) 
			{

				try {
					String email = request.getParameter("email");
				
					Login login = new Login();
					if(login.checkEmail(email) != 0)
					{
						out = response.getWriter();       			
						out.println("<message>true</message>");
						String password = gen(8);
						String newPass = PersonCreator.encrypt(password);
						changePass(newPass, email);
						Email.send(email, "ourSpaces password change", "Your password has been reset on your ourSpaces account. As a result, a temporary password has been automatically created for you. Please log into ourSpaces using this password. \n\n You can change your password under the Edit page on your account. \n\n New password: "+password);
					
					}
				
					else {
						out = response.getWriter();       							
						out.println("<message>false</message>");
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			

			if (cmd.equals("update")) 
			{

				try {
					String password = request.getParameter("password");
					String newPass = PersonCreator.encrypt(password);
					HttpSession session = request.getSession();
					User userSession = (User) session.getAttribute("use");
					if(userSession.getID() != 0) 
					{
						String email = userSession.getEmail(userSession.getID());
						changePass (newPass, email);
						out = response.getWriter();       			
						out.println("<message>true</message>");
					}
					else 
					{
						out = response.getWriter();       			
						out.println("<message>false</message>");
					}
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				
			}
			
			out.flush();
		}
		else
		{
			out.println("Server error.");
		}
	}
	
	public void changePass(String hashPass, String email) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		Connections con = new Connections();
		con.connect();
		Statement st = con.getCon().createStatement();

		// SQL query
		StringBuffer qry = new StringBuffer(1024);
		qry.append("update user set password=\""+hashPass+"\" where email=\"");
		qry.append(email);
		qry.append("\"");

		st.executeUpdate(qry.toString());
		
		st.close();
		con.disconnect();
	}
	

	  public String gen(int length) {
	    StringBuffer sb = new StringBuffer();
	    for (int i = length; i > 0; i -= 12) {
	      int n = min(12, abs(i));
	      sb.append(leftPad(Long.toString(round(random() * pow(36, n)), 36), n, '0'));
	    }
	    return sb.toString();
	  }

	
}
