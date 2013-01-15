package org.policygrid.reasoning;

import java.io.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.antlr.runtime.*;

import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryException;
import org.zkoss.web.servlet.http.HttpBufferedResponse;

import common.Connections;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import common.*;

public class N3RulesServlet implements Filter {

	private FilterConfig filterConfig;

	private Vector<RjspBlock> blocks;

	private void parseContent(String con) {
		blocks = new Vector<RjspBlock>();

		String bl = "";
		String type = "";
		RjspBlock mybl = new RjspBlock();
		;
		int i = 0;

		StringTokenizer st = new StringTokenizer(con);
		while (st.hasMoreTokens()) {
			String token = st.nextToken();

			if (token.indexOf("<?q=") != -1) {
				String var = token.substring(token.indexOf("<?q=") + 4);
				//common.Utility.log.debug("NEEDING VAR :" + var);
				token = st.nextToken();
				//common.Utility.log.debug("token :" + token);
				if (!token.equals("?>"))
					common.Utility.log.debug("ERROR end of variable missing");
				if (mybl != null)
					mybl.addVariable(var);
				bl = bl + " " + var;

			} else if (token.equals("<?q")) {
				// begin block
				if (!bl.equals("")) {
					// save the previous block
					mybl.setContent(bl);
					mybl.setType(type);
					mybl.setId(i++);
					blocks.add(mybl);
					bl = "";
				}

				type = "QUERY";
				mybl = new RjspBlock();
				mybl.setType(type);
			} else if (token.equals("?>")) {
				// end block
				if (!bl.equals("")) {
					// save the previous block
					mybl.setContent(bl);
					mybl.setId(i++);
					blocks.add(mybl);
					bl = "";
				}
				type = "IN HTML OF " + mybl.getId();
				mybl = new RjspBlock();
			} else if (token.equals("<eq?>")) {
				if (!bl.equals("")) {
					// save the previous block
					mybl.setContent(bl);
					mybl.setType(type);
					mybl.setId(i++);
					blocks.add(mybl);
					bl = "";
				}

				type = "";
				mybl = new RjspBlock();

			} else {
				bl = bl + " " + token;
				if (type.equals(""))
					type = "HTML";
			}

		}

		if (!bl.equals("")) {
			// save the previous block
			mybl = new RjspBlock();
			mybl.setContent(bl);
			mybl.setType(type);
			mybl.setId(i++);
			blocks.add(mybl);
			bl = "";
		}

	}
	
	public Hashtable<String, ArrayList<String>> runQuery(Hashtable<String, String> prefixes, ArrayList<STriple> head) throws RepositoryException, MalformedQueryException, QueryEvaluationException {
		Hashtable<String, ArrayList<String>>  res = new Hashtable<String, ArrayList<String>> ();
		Connections con = new Connections();
		
		ArrayList<String> variables = new ArrayList<String>();
	    
		for (int i = 0; i < head.size(); i++){
			String subject = head.get(i).getSubject();
			String predicate = head.get(i).getPredicate();
			String object = head.get(i).getObject();
			
			if (subject.startsWith("?")) {
				if (variables.indexOf(subject) > -1) {
					variables.remove(variables.indexOf(subject));
				}
				variables.add(subject);
			}
			if (predicate.startsWith("?")) {
				if (variables.indexOf(predicate) > -1) {
					variables.remove(variables.indexOf(predicate));
				}
				variables.add(predicate);
			}
			if (object.startsWith("?")) {
				if (variables.indexOf(object) > -1) {
					variables.remove(variables.indexOf(object));
				}
				variables.add(object);
			}
			
		}
		
	//	for (int i = 0; i < variables.size(); i++){
		//	common.Utility.log.debug("Var: "+ variables.get(i));
		//}
		
		
		StringBuffer qry = new StringBuffer(1024);
		qry.append("SELECT ");
		//list of variables
		for (int i = 0; i < variables.size(); i++){
			qry.append(" "+ variables.get(i));
		}
		qry.append(" where { ");
		//list of statements
		for (int i = 0; i < head.size(); i++){
			String subject = head.get(i).getSubject();
			String predicate = head.get(i).getPredicate();
			String object = head.get(i).getObject();
			qry.append(subject + " " + predicate + " " + object + " . ");
		}
		qry.append(" } ");
		
	
		String query = qry.toString();
		//common.Utility.log.debug("SPARQL : " + query );
		
		//query = "SELECT  ?x  where { ?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.policygrid.org/opm-resource.owl#Paper> .  }";
       
		con.repConnect();
		
		TupleQuery output = con.getRepConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
		TupleQueryResult result = output.evaluate();
		
		//common.Utility.log.debug("SPARQL query done! ");
		
		while (result.hasNext()) 
		{
			   BindingSet bindingSet = result.next();
			   
			
			   for (int i = 0; i < variables.size(); i++){
				   
				String vname = variables.get(i).substring(1);
				//common.Utility.log.debug("Var name  :" + vname);
			    Value value = bindingSet.getValue(vname);
			    String val = value.stringValue();
			    
			    if (res.get(variables.get(i)) == null) {
			    	ArrayList<String> arr = new ArrayList<String>();
			    	arr.add(val);
			    	res.put(variables.get(i), arr);
			    } else {
			    	ArrayList<String> arr = res.get(variables.get(i));
			    	arr.add(val);
			    	res.put(variables.get(i), arr);
			    }
			   
			   }
			   
			   
		}
		result.close();
		con.repDisconnect();
		
		return res;
	}

	 
	public String replaceVar(String source, String var, String value) {
		
		//common.Utility.log.debug("Replacing "+ var +" witn " + value);
		String res = "";
		res = source.replaceAll("\\"+var, value);
		return res;
	}
	
	protected void process(HttpServletRequest request,
			HttpServletResponse response, String content) {
		try {
			//common.Utility.log.debug("TXT : " + content);
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

			// parse content
			parseContent(content);

			for (int i = 0; i < blocks.size(); i++) {

				RjspBlock bl = blocks.get(i);

				//common.Utility.log.debug("-------------------- " + bl.getType() + "("
					//	+ bl.getId() + ") -------------------- ");

				//common.Utility.log.debug();
				//common.Utility.log.debug("->" + bl.getContent() + "<-");
				
				if (bl.getType().equals("HTML")) out.println(bl.getContent());

				if (bl.getType().equals("QUERY")) {
					 String ct = bl.getContent().substring(1);
					 ANTLRStringStream cs = new ANTLRStringStream(ct);
					 WebRulesLexer lex = new WebRulesLexer(cs);
					CommonTokenStream tokens = new CommonTokenStream(lex);
					WebRulesParser g = new WebRulesParser(tokens);
					g.rule();
					Hashtable<String, ArrayList<String>> res = runQuery(g.prefixes, g.head);
					
					Enumeration k1 = res.keys();
					RjspBlock inner_bl = blocks.get(i+1);
		
					//calculate the size of the query
					int size = 0;
					String key1 = (String) k1.nextElement();
					ArrayList<String> vec = res.get(key1);
					
					size = vec.size();
					
					//output the blocks of html
					String block = inner_bl.getContent();
					
					
					Vector<String> var = inner_bl.getVariables();
					
					
					for (int k = 0; k < size; k++) {
					
				    String bk =	block;
				    
					for (int j = 0; j < var.size(); j++) {
						ArrayList<String> line = res.get(var.get(j));
						String value = line.get(k);
						//common.Utility.log.debug("   VAR: " + var.get(j));
						bk = replaceVar(bk, var.get(j), value);
						
					}
					
					out.println(bk);
					}

				}

				Vector<String> var = bl.getVariables();
				for (int j = 0; j < var.size(); j++) {
					//common.Utility.log.debug("   VAR: " + var.get(j));
				}

				//common.Utility.log.debug("-------------------- END " + bl.getType()
				//		+ "-------------------- ");

			}

		} catch (Exception e) {
			common.Utility.log.debug(e);
		}

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		final StringWriter sw = new StringWriter(4096 * 2);
		final HttpServletResponse hres = (HttpServletResponse) response;
		final HttpBufferedResponse hbufres = (HttpBufferedResponse) HttpBufferedResponse
				.getInstance(hres, sw);
		chain.doFilter(request, hbufres);

		final StringWriter out = new StringWriter(4096 * 2);

		// Bug 1673839: servlet might redirect
		if (!hbufres.isSendRedirect())
			process((HttpServletRequest) request, hres, sw.getBuffer()
					.toString());
	}

	/*
	 * public void doFilter(ServletRequest request, ServletResponse response,
	 * 
	 * FilterChain chain) throws IOException, ServletException {
	 * 
	 * long startTime = System.currentTimeMillis(); chain.doFilter(request,
	 * response); long stopTime = System.currentTimeMillis();
	 * common.Utility.log.debug("Time to execute request: " + (stopTime - startTime) +
	 * " milliseconds");
	 * 
	 * 
	 * 
	 * Enumeration en = request.getAttributeNames();
	 * 
	 * while (en.hasMoreElements()) { common.Utility.log.debug("Par :" +
	 * en.nextElement()); }
	 * 
	 * BufferedReader br = request.getReader();
	 * 
	 * String line = ""; String resultStr = ""; do { try { line = (String)
	 * br.readLine(); } catch (IOException e) {
	 * common.Utility.log.debug("IO Exception on Buffered Read"); } resultStr += line
	 * + "\r\n"; } while (line != null);
	 * 
	 * common.Utility.log.debug("TXT: ");
	 * 
	 * }
	 */

	/**
	 * Take this filter out of service.
	 */
	public void destroy() {
	}

	public FilterConfig getFilterConfig() {
		return this.filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;

	}

}
