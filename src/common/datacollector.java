package common;

public class datacollector {


    int rid ;
    String disc;
    int sim1;
    

    public void setid( int id  )
    {
        rid = id;
    }

    public void setDisc( String value )
    {
    	disc = value;
    }

   

    public int getId() { return rid; }

    public String getDisc() { return disc; }

    public void setSim1( int val  ) {sim1 = val;}
    public int getSim1(   ) {return sim1 ;}
    
    
}