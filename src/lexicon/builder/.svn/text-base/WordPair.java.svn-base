package lexicon.builder;


import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class WordPair.
 */
public class WordPair implements Comparable<WordPair> {
    
    /** The w2source. */
    private final String  w1,w1source,w2,w2source;
    
    /** The sim. */
    private final Double sim;
    
    /**
     * Instantiates a new word pair.
     *
     * @param w1 the w1
     * @param w1source the w1source
     * @param w2 the w2
     * @param w2source the w2source
     * @param s the s
     */
    public WordPair(String w1,String w1source, String w2,String w2source, Double s) {
        if (w2 == null || w1 == null)
            throw new NullPointerException();
        
        this.w1 = w1;
        this.w1source=w1source;
        this.w2 = w2;
        this.w2source=w2source;
        this.sim=s;
    }

    /**
     * W1.
     *
     * @return the string
     */
    public String w1() { return w1; }
    
    /**
     * W2.
     *
     * @return the string
     */
    public String w2()  { return w2;  }
    
    /**
     * W1source.
     *
     * @return the string
     */
    public String w1source()  { return w1source;  }
    
    /**
     * W2source.
     *
     * @return the string
     */
    public String w2source()  { return w2source;  }
    
    /**
     * Sim.
     *
     * @return the double
     */
    public Double sim() {return sim;}
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
        if (!(o instanceof WordPair))
            return false;
        WordPair n = (WordPair) o;
        return n.w2.equals(w2) && n.w1.equals(w1);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return 31*w2.hashCode() + w1.hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
	return w2 + " " + w1 +" "+sim;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(WordPair n) {
        int lastCmp = w1.compareTo(n.w1);
        return (lastCmp != 0 ? lastCmp : (sim >n.sim ? -1:(sim ==n.sim? 0:1) ));
    }
}