// $ANTLR 3.2 Sep 23, 2009 12:02:23 /Users/epignott/Desktop/WebRules.g 2010-03-18 14:45:56

  package org.policygrid.reasoning;
  import java.util.Hashtable;
  

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class WebRulesParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ID", "URL", "INT", "FLOAT", "STRING", "WS", "CMT", "'@prefix'", "':'", "'.'", "'<'", "'>'", "'{'", "'}'", "'=>'", "';'", "'['", "']'", "','", "'?'", "'='"
    };
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int FLOAT=7;
    public static final int INT=6;
    public static final int ID=4;
    public static final int EOF=-1;
    public static final int URL=5;
    public static final int T__19=19;
    public static final int CMT=10;
    public static final int WS=9;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int STRING=8;

    // delegates
    // delegators


        public WebRulesParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public WebRulesParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return WebRulesParser.tokenNames; }
    public String getGrammarFileName() { return "/Users/epignott/Desktop/WebRules.g"; }


      private int index = 0;
      
      public Hashtable<String, String> prefixes = new Hashtable<String, String>();
      
      public ArrayList<STriple> head;
      
      public ArrayList<STriple> body;
      
      String pos = "";
      
      private void addTriple(STriple tr) {
        if (pos.equals("head")) head.add(tr);
        if (pos.equals("body")) body.add(tr);
      }
      
      private static void out(Object obj) {
        common.Utility.log.debug(obj);
      }




    // $ANTLR start "rule"
    // /Users/epignott/Desktop/WebRules.g:73:1: rule : ( prefix )* head ( body )* ;
    public final void rule() throws RecognitionException {
        try {
            // /Users/epignott/Desktop/WebRules.g:73:6: ( ( prefix )* head ( body )* )
            // /Users/epignott/Desktop/WebRules.g:73:9: ( prefix )* head ( body )*
            {
            // /Users/epignott/Desktop/WebRules.g:73:9: ( prefix )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==11) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/epignott/Desktop/WebRules.g:73:9: prefix
            	    {
            	    pushFollow(FOLLOW_prefix_in_rule32);
            	    prefix();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            pushFollow(FOLLOW_head_in_rule35);
            head();

            state._fsp--;

            // /Users/epignott/Desktop/WebRules.g:73:22: ( body )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==18) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /Users/epignott/Desktop/WebRules.g:73:22: body
            	    {
            	    pushFollow(FOLLOW_body_in_rule37);
            	    body();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "rule"


    // $ANTLR start "prefix"
    // /Users/epignott/Desktop/WebRules.g:75:1: prefix : '@prefix' ID ':' u= uri ( '.' )* ;
    public final void prefix() throws RecognitionException {
        Token ID1=null;
        String u = null;


        try {
            // /Users/epignott/Desktop/WebRules.g:76:2: ( '@prefix' ID ':' u= uri ( '.' )* )
            // /Users/epignott/Desktop/WebRules.g:76:4: '@prefix' ID ':' u= uri ( '.' )*
            {
            match(input,11,FOLLOW_11_in_prefix50); 
            ID1=(Token)match(input,ID,FOLLOW_ID_in_prefix52); 
            match(input,12,FOLLOW_12_in_prefix54); 
            pushFollow(FOLLOW_uri_in_prefix59);
            u=uri();

            state._fsp--;

            // /Users/epignott/Desktop/WebRules.g:76:28: ( '.' )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==13) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /Users/epignott/Desktop/WebRules.g:76:28: '.'
            	    {
            	    match(input,13,FOLLOW_13_in_prefix61); 

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            prefixes.put((ID1!=null?ID1.getText():null),u); out("Prefix: "+(ID1!=null?ID1.getText():null)+" "+u);

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "prefix"


    // $ANTLR start "uri"
    // /Users/epignott/Desktop/WebRules.g:79:1: uri returns [String value] : '<' URL '>' ;
    public final String uri() throws RecognitionException {
        String value = null;

        Token URL2=null;

        try {
            // /Users/epignott/Desktop/WebRules.g:80:2: ( '<' URL '>' )
            // /Users/epignott/Desktop/WebRules.g:80:4: '<' URL '>'
            {
            match(input,14,FOLLOW_14_in_uri79); 
            URL2=(Token)match(input,URL,FOLLOW_URL_in_uri81); 
            match(input,15,FOLLOW_15_in_uri83); 
            value = (URL2!=null?URL2.getText():null);

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "uri"


    // $ANTLR start "head"
    // /Users/epignott/Desktop/WebRules.g:83:1: head : '{' ( stat )+ '}' ;
    public final void head() throws RecognitionException {
        head = new ArrayList<STriple>(); pos = "head"; out("HEAD:"); 
        try {
            // /Users/epignott/Desktop/WebRules.g:85:2: ( '{' ( stat )+ '}' )
            // /Users/epignott/Desktop/WebRules.g:85:4: '{' ( stat )+ '}'
            {
            match(input,16,FOLLOW_16_in_head102); 
            // /Users/epignott/Desktop/WebRules.g:85:8: ( stat )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==ID||(LA4_0>=INT && LA4_0<=STRING)||LA4_0==14||LA4_0==23) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /Users/epignott/Desktop/WebRules.g:85:8: stat
            	    {
            	    pushFollow(FOLLOW_stat_in_head104);
            	    stat();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);

            match(input,17,FOLLOW_17_in_head107); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "head"


    // $ANTLR start "body"
    // /Users/epignott/Desktop/WebRules.g:88:1: body : '=>' '{' ( stat )+ '}' ;
    public final void body() throws RecognitionException {
        body = new ArrayList<STriple>(); pos = "body"; out("BODY:");
        try {
            // /Users/epignott/Desktop/WebRules.g:90:3: ( '=>' '{' ( stat )+ '}' )
            // /Users/epignott/Desktop/WebRules.g:90:5: '=>' '{' ( stat )+ '}'
            {
            match(input,18,FOLLOW_18_in_body124); 
            match(input,16,FOLLOW_16_in_body126); 
            // /Users/epignott/Desktop/WebRules.g:90:14: ( stat )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==ID||(LA5_0>=INT && LA5_0<=STRING)||LA5_0==14||LA5_0==23) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /Users/epignott/Desktop/WebRules.g:90:14: stat
            	    {
            	    pushFollow(FOLLOW_stat_in_body128);
            	    stat();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);

            match(input,17,FOLLOW_17_in_body131); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "body"


    // $ANTLR start "stat"
    // /Users/epignott/Desktop/WebRules.g:93:1: stat : triple ( '.' )* ;
    public final void stat() throws RecognitionException {
        try {
            // /Users/epignott/Desktop/WebRules.g:94:2: ( triple ( '.' )* )
            // /Users/epignott/Desktop/WebRules.g:94:4: triple ( '.' )*
            {
            pushFollow(FOLLOW_triple_in_stat143);
            triple();

            state._fsp--;

            // /Users/epignott/Desktop/WebRules.g:94:11: ( '.' )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==13) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /Users/epignott/Desktop/WebRules.g:94:11: '.'
            	    {
            	    match(input,13,FOLLOW_13_in_stat145); 

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "stat"


    // $ANTLR start "triple"
    // /Users/epignott/Desktop/WebRules.g:97:1: triple returns [ArrayList<STriple> triples] : (s= sub p= pre o= obj ( ';' p1= pre o1= obj )* | s= sub p= pre '[' p1= pre o1= obj ']' );
    public final ArrayList<STriple> triple() throws RecognitionException {
        ArrayList<STriple> triples = null;

        String s = null;

        String p = null;

        String o = null;

        String p1 = null;

        String o1 = null;


        try {
            // /Users/epignott/Desktop/WebRules.g:98:9: (s= sub p= pre o= obj ( ';' p1= pre o1= obj )* | s= sub p= pre '[' p1= pre o1= obj ']' )
            int alt8=2;
            alt8 = dfa8.predict(input);
            switch (alt8) {
                case 1 :
                    // /Users/epignott/Desktop/WebRules.g:98:11: s= sub p= pre o= obj ( ';' p1= pre o1= obj )*
                    {
                    pushFollow(FOLLOW_sub_in_triple173);
                    s=sub();

                    state._fsp--;

                    pushFollow(FOLLOW_pre_in_triple177);
                    p=pre();

                    state._fsp--;

                    pushFollow(FOLLOW_obj_in_triple181);
                    o=obj();

                    state._fsp--;

                    // /Users/epignott/Desktop/WebRules.g:98:29: ( ';' p1= pre o1= obj )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==19) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // /Users/epignott/Desktop/WebRules.g:98:30: ';' p1= pre o1= obj
                    	    {
                    	    match(input,19,FOLLOW_19_in_triple184); 
                    	    pushFollow(FOLLOW_pre_in_triple188);
                    	    p1=pre();

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_obj_in_triple192);
                    	    o1=obj();

                    	    state._fsp--;

                    	    addTriple(new STriple(s, p1, o1));

                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);

                    addTriple(new STriple(s, p, o));

                    }
                    break;
                case 2 :
                    // /Users/epignott/Desktop/WebRules.g:99:4: s= sub p= pre '[' p1= pre o1= obj ']'
                    {
                    pushFollow(FOLLOW_sub_in_triple207);
                    s=sub();

                    state._fsp--;

                    pushFollow(FOLLOW_pre_in_triple211);
                    p=pre();

                    state._fsp--;

                    match(input,20,FOLLOW_20_in_triple213); 
                    pushFollow(FOLLOW_pre_in_triple217);
                    p1=pre();

                    state._fsp--;

                    pushFollow(FOLLOW_obj_in_triple221);
                    o1=obj();

                    state._fsp--;

                    match(input,21,FOLLOW_21_in_triple223); 
                    addTriple(new STriple(s, p, "?ref_"+index)); addTriple(new STriple("?ref_"+index, p1, o1)); index++;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return triples;
    }
    // $ANTLR end "triple"


    // $ANTLR start "sub"
    // /Users/epignott/Desktop/WebRules.g:103:1: sub returns [String value] : e= expr ;
    public final String sub() throws RecognitionException {
        String value = null;

        String e = null;


        try {
            // /Users/epignott/Desktop/WebRules.g:104:2: (e= expr )
            // /Users/epignott/Desktop/WebRules.g:104:4: e= expr
            {
            pushFollow(FOLLOW_expr_in_sub246);
            e=expr();

            state._fsp--;

            value = e;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "sub"


    // $ANTLR start "pre"
    // /Users/epignott/Desktop/WebRules.g:107:1: pre returns [String value] : e= expr ;
    public final String pre() throws RecognitionException {
        String value = null;

        String e = null;


        try {
            // /Users/epignott/Desktop/WebRules.g:108:2: (e= expr )
            // /Users/epignott/Desktop/WebRules.g:108:4: e= expr
            {
            pushFollow(FOLLOW_expr_in_pre265);
            e=expr();

            state._fsp--;

            value = e;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "pre"


    // $ANTLR start "obj"
    // /Users/epignott/Desktop/WebRules.g:111:1: obj returns [String value] : e= expr ( ',' e1= expr )* ;
    public final String obj() throws RecognitionException {
        String value = null;

        String e = null;

        String e1 = null;


        try {
            // /Users/epignott/Desktop/WebRules.g:112:2: (e= expr ( ',' e1= expr )* )
            // /Users/epignott/Desktop/WebRules.g:112:4: e= expr ( ',' e1= expr )*
            {
            pushFollow(FOLLOW_expr_in_obj286);
            e=expr();

            state._fsp--;

            // /Users/epignott/Desktop/WebRules.g:112:11: ( ',' e1= expr )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==22) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // /Users/epignott/Desktop/WebRules.g:112:12: ',' e1= expr
            	    {
            	    match(input,22,FOLLOW_22_in_obj289); 
            	    pushFollow(FOLLOW_expr_in_obj293);
            	    e1=expr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            value = e;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "obj"


    // $ANTLR start "expr"
    // /Users/epignott/Desktop/WebRules.g:115:1: expr returns [String value] : ( ID | INT | FLOAT | i= ID ':' i1= ID | '?' ID | STRING | '?' i= ID '=' '?' i1= ID | u= uri );
    public final String expr() throws RecognitionException {
        String value = null;

        Token i=null;
        Token i1=null;
        Token ID3=null;
        Token INT4=null;
        Token FLOAT5=null;
        Token ID6=null;
        Token STRING7=null;
        String u = null;


        try {
            // /Users/epignott/Desktop/WebRules.g:116:5: ( ID | INT | FLOAT | i= ID ':' i1= ID | '?' ID | STRING | '?' i= ID '=' '?' i1= ID | u= uri )
            int alt10=8;
            alt10 = dfa10.predict(input);
            switch (alt10) {
                case 1 :
                    // /Users/epignott/Desktop/WebRules.g:116:9: ID
                    {
                    ID3=(Token)match(input,ID,FOLLOW_ID_in_expr317); 

                         String id = (ID3!=null?ID3.getText():null);
                         if (id.equals("a")) {
                          value = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>";
                         } else {
                           value = (ID3!=null?ID3.getText():null);
                         }  
                        

                    }
                    break;
                case 2 :
                    // /Users/epignott/Desktop/WebRules.g:125:9: INT
                    {
                    INT4=(Token)match(input,INT,FOLLOW_INT_in_expr335); 
                    value = (INT4!=null?INT4.getText():null);

                    }
                    break;
                case 3 :
                    // /Users/epignott/Desktop/WebRules.g:126:9: FLOAT
                    {
                    FLOAT5=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_expr347); 
                    value = (FLOAT5!=null?FLOAT5.getText():null);

                    }
                    break;
                case 4 :
                    // /Users/epignott/Desktop/WebRules.g:127:9: i= ID ':' i1= ID
                    {
                    i=(Token)match(input,ID,FOLLOW_ID_in_expr361); 
                    match(input,12,FOLLOW_12_in_expr363); 
                    i1=(Token)match(input,ID,FOLLOW_ID_in_expr367); 
                    value = "<"+prefixes.get((i!=null?i.getText():null))+(i1!=null?i1.getText():null)+">";

                    }
                    break;
                case 5 :
                    // /Users/epignott/Desktop/WebRules.g:128:9: '?' ID
                    {
                    match(input,23,FOLLOW_23_in_expr379); 
                    ID6=(Token)match(input,ID,FOLLOW_ID_in_expr381); 
                    value = "?"+(ID6!=null?ID6.getText():null);

                    }
                    break;
                case 6 :
                    // /Users/epignott/Desktop/WebRules.g:129:9: STRING
                    {
                    STRING7=(Token)match(input,STRING,FOLLOW_STRING_in_expr393); 
                    value = (STRING7!=null?STRING7.getText():null);

                    }
                    break;
                case 7 :
                    // /Users/epignott/Desktop/WebRules.g:133:9: '?' i= ID '=' '?' i1= ID
                    {
                    match(input,23,FOLLOW_23_in_expr417); 
                    i=(Token)match(input,ID,FOLLOW_ID_in_expr421); 
                    match(input,24,FOLLOW_24_in_expr423); 
                    match(input,23,FOLLOW_23_in_expr425); 
                    i1=(Token)match(input,ID,FOLLOW_ID_in_expr429); 
                    value = "?"+(i!=null?i.getText():null)+"=?"+(i1!=null?i1.getText():null);

                    }
                    break;
                case 8 :
                    // /Users/epignott/Desktop/WebRules.g:134:9: u= uri
                    {
                    pushFollow(FOLLOW_uri_in_expr443);
                    u=uri();

                    state._fsp--;

                    value = u;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "expr"

    // Delegated rules


    protected DFA8 dfa8 = new DFA8(this);
    protected DFA10 dfa10 = new DFA10(this);
    static final String DFA8_eotS =
        "\37\uffff";
    static final String DFA8_eofS =
        "\37\uffff";
    static final String DFA8_minS =
        "\6\4\1\5\6\4\1\5\1\4\1\17\2\4\2\uffff\1\4\1\17\1\27\2\4\1\27\5\4";
    static final String DFA8_maxS =
        "\4\27\1\4\1\27\1\5\1\4\3\27\1\4\1\27\1\5\1\30\1\17\1\27\1\4\2\uffff"+
        "\1\30\1\17\5\27\2\4\2\27";
    static final String DFA8_acceptS =
        "\22\uffff\1\2\1\1\13\uffff";
    static final String DFA8_specialS =
        "\37\uffff}>";
    static final String[] DFA8_transitionS = {
            "\1\1\1\uffff\1\2\1\3\1\5\5\uffff\1\6\10\uffff\1\4",
            "\1\10\1\uffff\1\11\1\12\1\14\3\uffff\1\7\1\uffff\1\15\10\uffff"+
            "\1\13",
            "\1\10\1\uffff\1\11\1\12\1\14\5\uffff\1\15\10\uffff\1\13",
            "\1\10\1\uffff\1\11\1\12\1\14\5\uffff\1\15\10\uffff\1\13",
            "\1\16",
            "\1\10\1\uffff\1\11\1\12\1\14\5\uffff\1\15\10\uffff\1\13",
            "\1\17",
            "\1\20",
            "\1\23\1\uffff\3\23\3\uffff\1\21\1\uffff\1\23\5\uffff\1\22\2"+
            "\uffff\1\23",
            "\1\23\1\uffff\3\23\5\uffff\1\23\5\uffff\1\22\2\uffff\1\23",
            "\1\23\1\uffff\3\23\5\uffff\1\23\5\uffff\1\22\2\uffff\1\23",
            "\1\24",
            "\1\23\1\uffff\3\23\5\uffff\1\23\5\uffff\1\22\2\uffff\1\23",
            "\1\25",
            "\1\10\1\uffff\1\11\1\12\1\14\5\uffff\1\15\10\uffff\1\13\1\26",
            "\1\27",
            "\1\10\1\uffff\1\11\1\12\1\14\5\uffff\1\15\10\uffff\1\13",
            "\1\30",
            "",
            "",
            "\1\23\1\uffff\3\23\5\uffff\1\23\5\uffff\1\22\2\uffff\1\23\1"+
            "\31",
            "\1\32",
            "\1\33",
            "\1\10\1\uffff\1\11\1\12\1\14\5\uffff\1\15\10\uffff\1\13",
            "\1\23\1\uffff\3\23\5\uffff\1\23\5\uffff\1\22\2\uffff\1\23",
            "\1\34",
            "\1\23\1\uffff\3\23\5\uffff\1\23\5\uffff\1\22\2\uffff\1\23",
            "\1\35",
            "\1\36",
            "\1\10\1\uffff\1\11\1\12\1\14\5\uffff\1\15\10\uffff\1\13",
            "\1\23\1\uffff\3\23\5\uffff\1\23\5\uffff\1\22\2\uffff\1\23"
    };

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        public String getDescription() {
            return "97:1: triple returns [ArrayList<STriple> triples] : (s= sub p= pre o= obj ( ';' p1= pre o1= obj )* | s= sub p= pre '[' p1= pre o1= obj ']' );";
        }
    }
    static final String DFA10_eotS =
        "\14\uffff";
    static final String DFA10_eofS =
        "\14\uffff";
    static final String DFA10_minS =
        "\2\4\2\uffff\1\4\4\uffff\1\4\2\uffff";
    static final String DFA10_maxS =
        "\2\27\2\uffff\1\4\4\uffff\1\30\2\uffff";
    static final String DFA10_acceptS =
        "\2\uffff\1\2\1\3\1\uffff\1\6\1\10\1\4\1\1\1\uffff\1\7\1\5";
    static final String DFA10_specialS =
        "\14\uffff}>";
    static final String[] DFA10_transitionS = {
            "\1\1\1\uffff\1\2\1\3\1\5\5\uffff\1\6\10\uffff\1\4",
            "\1\10\1\uffff\3\10\3\uffff\1\7\2\10\2\uffff\1\10\1\uffff\5"+
            "\10",
            "",
            "",
            "\1\11",
            "",
            "",
            "",
            "",
            "\1\13\1\uffff\3\13\4\uffff\2\13\2\uffff\1\13\1\uffff\5\13\1"+
            "\12",
            "",
            ""
    };

    static final short[] DFA10_eot = DFA.unpackEncodedString(DFA10_eotS);
    static final short[] DFA10_eof = DFA.unpackEncodedString(DFA10_eofS);
    static final char[] DFA10_min = DFA.unpackEncodedStringToUnsignedChars(DFA10_minS);
    static final char[] DFA10_max = DFA.unpackEncodedStringToUnsignedChars(DFA10_maxS);
    static final short[] DFA10_accept = DFA.unpackEncodedString(DFA10_acceptS);
    static final short[] DFA10_special = DFA.unpackEncodedString(DFA10_specialS);
    static final short[][] DFA10_transition;

    static {
        int numStates = DFA10_transitionS.length;
        DFA10_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA10_transition[i] = DFA.unpackEncodedString(DFA10_transitionS[i]);
        }
    }

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = DFA10_eot;
            this.eof = DFA10_eof;
            this.min = DFA10_min;
            this.max = DFA10_max;
            this.accept = DFA10_accept;
            this.special = DFA10_special;
            this.transition = DFA10_transition;
        }
        public String getDescription() {
            return "115:1: expr returns [String value] : ( ID | INT | FLOAT | i= ID ':' i1= ID | '?' ID | STRING | '?' i= ID '=' '?' i1= ID | u= uri );";
        }
    }
 

    public static final BitSet FOLLOW_prefix_in_rule32 = new BitSet(new long[]{0x0000000000010800L});
    public static final BitSet FOLLOW_head_in_rule35 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_body_in_rule37 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_11_in_prefix50 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_prefix52 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_prefix54 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_uri_in_prefix59 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_13_in_prefix61 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_14_in_uri79 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_URL_in_uri81 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_uri83 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_head102 = new BitSet(new long[]{0x00000000008041D0L});
    public static final BitSet FOLLOW_stat_in_head104 = new BitSet(new long[]{0x00000000008241D0L});
    public static final BitSet FOLLOW_17_in_head107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_body124 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_body126 = new BitSet(new long[]{0x00000000008041D0L});
    public static final BitSet FOLLOW_stat_in_body128 = new BitSet(new long[]{0x00000000008241D0L});
    public static final BitSet FOLLOW_17_in_body131 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_triple_in_stat143 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_13_in_stat145 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_sub_in_triple173 = new BitSet(new long[]{0x00000000008041D0L});
    public static final BitSet FOLLOW_pre_in_triple177 = new BitSet(new long[]{0x00000000008041D0L});
    public static final BitSet FOLLOW_obj_in_triple181 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_19_in_triple184 = new BitSet(new long[]{0x00000000008041D0L});
    public static final BitSet FOLLOW_pre_in_triple188 = new BitSet(new long[]{0x00000000008041D0L});
    public static final BitSet FOLLOW_obj_in_triple192 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_sub_in_triple207 = new BitSet(new long[]{0x00000000008041D0L});
    public static final BitSet FOLLOW_pre_in_triple211 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_triple213 = new BitSet(new long[]{0x00000000008041D0L});
    public static final BitSet FOLLOW_pre_in_triple217 = new BitSet(new long[]{0x00000000008041D0L});
    public static final BitSet FOLLOW_obj_in_triple221 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_triple223 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_sub246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_pre265 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_obj286 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_22_in_obj289 = new BitSet(new long[]{0x00000000008041D0L});
    public static final BitSet FOLLOW_expr_in_obj293 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_ID_in_expr317 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_expr335 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_expr347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_expr361 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_expr363 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_expr367 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_expr379 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_expr381 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_expr393 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_expr417 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_expr421 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_expr423 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_expr425 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_expr429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_uri_in_expr443 = new BitSet(new long[]{0x0000000000000002L});

}