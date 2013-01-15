package common;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
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

public class LoggerSearch extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	static final long serialVersionUID = 1L;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	Connections con = new Connections();

	public LoggerSearch() {
		super();
	} 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try {
		       String table = request.getParameter("table");
		       String insert  = request.getParameter("insert");
		       if(table == null || table.equals(""))
		    	   return;
		       if(insert == null || insert.equals(""))
		    	   return;
		       addLog(table, insert);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public void addLog(String table, String insert) {

		String qry = "INSERT INTO "+table+" values ("+insert+")";
		common.Utility.log.debug(qry);
		PreparedStatement pstmt;
		Connections con = new Connections();
		try {
			con.connect();
			pstmt = con.getCon().prepareStatement(qry);
			int  rs = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				con.disconnect();
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
		
	}
	
}
