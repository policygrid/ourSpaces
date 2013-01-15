package bak;


import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;


public class ExecuteRASP {
	
	String f1;
	String f2;
	
	public static void main(String args[]){
		

		
		String f1 = "/Users/kapila/Documents/repository/textdocs/cullinane.txt";
		String f2 = "/Users/kapila/Documents/repository/parsed/cullinanebricksandclicks.txt";
		ExecuteRASP er =new ExecuteRASP(f1,f2);
		er.callRASP();
		
	}
	
	public ExecuteRASP(String fn1,String fn2){
		f1=fn1;
		f2=fn2;
	
	}
		
	 public void callRASP(){    
		 try
	        {            ///home/kapila/rasp3os/scripts/rasp.sh -m < /Users/kapila/Documents/repository/textdocs/cullinanebricksandclicks.txt > /Users/kapila/Documents/repository/parsed/cullinanebricksandclicks.txt
	            Runtime rt = Runtime.getRuntime();
	           Process proc = rt.exec("/home/kapila/rasp3os/scripts/test.sh "+f1+"   "+f2);
	           // Process proc = rt.exec("/home/kapila/rasp3os/scripts/rasp.sh -m < /Users/kapila/Documents/repository/textdocs/cullinane.txt");
	            InputStream stderr = proc.getErrorStream();
	            InputStreamReader isr = new InputStreamReader(stderr);
	            BufferedReader br = new BufferedReader(isr);
	            String line = null;
	            //System.out.println("<ERROR>");
	            while ( (line = br.readLine()) != null)
	                System.out.println(line);
	            //System.out.println("</ERROR>");
	            int exitVal = proc.waitFor();
	            System.out.println("RASP exitValue: " + exitVal);
			 

	            
	        } catch (Throwable t)
	          {
	            t.printStackTrace();
	          }
	
	}
	
}
