// $ANTLR 3.2 Sep 23, 2009 12:02:23 /Users/epignott/Desktop/WebRules.g 2010-03-18 14:45:56
package org.policygrid.reasoning;
import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class WebRulesLexer extends Lexer {
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

    	public Token nextToken() {
    		while (true) {
    			state.token = null;
    			state.channel = Token.DEFAULT_CHANNEL;
    			state.tokenStartCharIndex = input.index();
    			state.tokenStartCharPositionInLine = input.getCharPositionInLine();
    			state.tokenStartLine = input.getLine();
    			state.text = null;
    			if ( input.LA(1)==CharStream.EOF ) {
    				return Token.EOF_TOKEN;
    			}
    			try {
    				mTokens();
    				if ( state.token==null ) {
    					emit();
    				}
    				else if ( state.token==Token.SKIP_TOKEN ) {
    					continue;
    				}
    				return state.token;
    			}
    			catch (RecognitionException re) {
    				reportError(re);
    				throw new RuntimeException("Bailing out!"); // or throw Error
    			}
    		}
    	}


    // delegates
    // delegators

    public WebRulesLexer() {;} 
    public WebRulesLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public WebRulesLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "/Users/epignott/Desktop/WebRules.g"; }

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/epignott/Desktop/WebRules.g:33:7: ( '@prefix' )
            // /Users/epignott/Desktop/WebRules.g:33:9: '@prefix'
            {
            match("@prefix"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__11"

    // $ANTLR start "T__12"
    public final void mT__12() throws RecognitionException {
        try {
            int _type = T__12;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/epignott/Desktop/WebRules.g:34:7: ( ':' )
            // /Users/epignott/Desktop/WebRules.g:34:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__12"

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/epignott/Desktop/WebRules.g:35:7: ( '.' )
            // /Users/epignott/Desktop/WebRules.g:35:9: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/epignott/Desktop/WebRules.g:36:7: ( '<' )
            // /Users/epignott/Desktop/WebRules.g:36:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/epignott/Desktop/WebRules.g:37:7: ( '>' )
            // /Users/epignott/Desktop/WebRules.g:37:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/epignott/Desktop/WebRules.g:38:7: ( '{' )
            // /Users/epignott/Desktop/WebRules.g:38:9: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/epignott/Desktop/WebRules.g:39:7: ( '}' )
            // /Users/epignott/Desktop/WebRules.g:39:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/epignott/Desktop/WebRules.g:40:7: ( '=>' )
            // /Users/epignott/Desktop/WebRules.g:40:9: '=>'
            {
            match("=>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/epignott/Desktop/WebRules.g:41:7: ( ';' )
            // /Users/epignott/Desktop/WebRules.g:41:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/epignott/Desktop/WebRules.g:42:7: ( '[' )
            // /Users/epignott/Desktop/WebRules.g:42:9: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/epignott/Desktop/WebRules.g:43:7: ( ']' )
            // /Users/epignott/Desktop/WebRules.g:43:9: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/epignott/Desktop/WebRules.g:44:7: ( ',' )
            // /Users/epignott/Desktop/WebRules.g:44:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/epignott/Desktop/WebRules.g:45:7: ( '?' )
            // /Users/epignott/Desktop/WebRules.g:45:9: '?'
            {
            match('?'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/epignott/Desktop/WebRules.g:46:7: ( '=' )
            // /Users/epignott/Desktop/WebRules.g:46:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/epignott/Desktop/WebRules.g:137:8: ( '\"' ( . )* '\"' )
            // /Users/epignott/Desktop/WebRules.g:137:10: '\"' ( . )* '\"'
            {
            match('\"'); 
            // /Users/epignott/Desktop/WebRules.g:137:14: ( . )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='\"') ) {
                    alt1=2;
                }
                else if ( ((LA1_0>='\u0000' && LA1_0<='!')||(LA1_0>='#' && LA1_0<='\uFFFF')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/epignott/Desktop/WebRules.g:137:14: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/epignott/Desktop/WebRules.g:138:5: ( ( '0' .. '9' )+ )
            // /Users/epignott/Desktop/WebRules.g:138:9: ( '0' .. '9' )+
            {
            // /Users/epignott/Desktop/WebRules.g:138:9: ( '0' .. '9' )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='0' && LA2_0<='9')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /Users/epignott/Desktop/WebRules.g:138:9: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/epignott/Desktop/WebRules.g:139:5: ( ( 'a' .. 'z' | 'A' .. 'Z' ) ( '_' | 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )* )
            // /Users/epignott/Desktop/WebRules.g:139:7: ( 'a' .. 'z' | 'A' .. 'Z' ) ( '_' | 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // /Users/epignott/Desktop/WebRules.g:139:27: ( '_' | 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='0' && LA3_0<='9')||(LA3_0>='A' && LA3_0<='Z')||LA3_0=='_'||(LA3_0>='a' && LA3_0<='z')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /Users/epignott/Desktop/WebRules.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/epignott/Desktop/WebRules.g:140:5: ( ( ' ' | '\\n' | '\\t' )+ )
            // /Users/epignott/Desktop/WebRules.g:140:9: ( ' ' | '\\n' | '\\t' )+
            {
            // /Users/epignott/Desktop/WebRules.g:140:9: ( ' ' | '\\n' | '\\t' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='\t' && LA4_0<='\n')||LA4_0==' ') ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /Users/epignott/Desktop/WebRules.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


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

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "CMT"
    public final void mCMT() throws RecognitionException {
        try {
            int _type = CMT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/epignott/Desktop/WebRules.g:141:5: ( '/*' ( . )* '*/' )
            // /Users/epignott/Desktop/WebRules.g:141:9: '/*' ( . )* '*/'
            {
            match("/*"); 

            // /Users/epignott/Desktop/WebRules.g:141:14: ( . )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0=='*') ) {
                    int LA5_1 = input.LA(2);

                    if ( (LA5_1=='/') ) {
                        alt5=2;
                    }
                    else if ( ((LA5_1>='\u0000' && LA5_1<='.')||(LA5_1>='0' && LA5_1<='\uFFFF')) ) {
                        alt5=1;
                    }


                }
                else if ( ((LA5_0>='\u0000' && LA5_0<=')')||(LA5_0>='+' && LA5_0<='\uFFFF')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /Users/epignott/Desktop/WebRules.g:141:14: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            match("*/"); 

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CMT"

    // $ANTLR start "URL"
    public final void mURL() throws RecognitionException {
        try {
            int _type = URL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/epignott/Desktop/WebRules.g:142:6: ( 'http://' ( '_' | 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '.' | '#' | '/' | '?' | '&' | '%' | '=' | '!' | '-' )* )
            // /Users/epignott/Desktop/WebRules.g:142:8: 'http://' ( '_' | 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '.' | '#' | '/' | '?' | '&' | '%' | '=' | '!' | '-' )*
            {
            match("http://"); 

            // /Users/epignott/Desktop/WebRules.g:142:18: ( '_' | 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '.' | '#' | '/' | '?' | '&' | '%' | '=' | '!' | '-' )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0=='!'||LA6_0=='#'||(LA6_0>='%' && LA6_0<='&')||(LA6_0>='-' && LA6_0<='9')||LA6_0=='='||LA6_0=='?'||(LA6_0>='A' && LA6_0<='Z')||LA6_0=='_'||(LA6_0>='a' && LA6_0<='z')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /Users/epignott/Desktop/WebRules.g:
            	    {
            	    if ( input.LA(1)=='!'||input.LA(1)=='#'||(input.LA(1)>='%' && input.LA(1)<='&')||(input.LA(1)>='-' && input.LA(1)<='9')||input.LA(1)=='='||input.LA(1)=='?'||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "URL"

    // $ANTLR start "FLOAT"
    public final void mFLOAT() throws RecognitionException {
        try {
            int _type = FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/epignott/Desktop/WebRules.g:143:6: ( INT '.' INT )
            // /Users/epignott/Desktop/WebRules.g:143:8: INT '.' INT
            {
            mINT(); 
            match('.'); 
            mINT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FLOAT"

    public void mTokens() throws RecognitionException {
        // /Users/epignott/Desktop/WebRules.g:1:8: ( T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | STRING | INT | ID | WS | CMT | URL | FLOAT )
        int alt7=21;
        alt7 = dfa7.predict(input);
        switch (alt7) {
            case 1 :
                // /Users/epignott/Desktop/WebRules.g:1:10: T__11
                {
                mT__11(); 

                }
                break;
            case 2 :
                // /Users/epignott/Desktop/WebRules.g:1:16: T__12
                {
                mT__12(); 

                }
                break;
            case 3 :
                // /Users/epignott/Desktop/WebRules.g:1:22: T__13
                {
                mT__13(); 

                }
                break;
            case 4 :
                // /Users/epignott/Desktop/WebRules.g:1:28: T__14
                {
                mT__14(); 

                }
                break;
            case 5 :
                // /Users/epignott/Desktop/WebRules.g:1:34: T__15
                {
                mT__15(); 

                }
                break;
            case 6 :
                // /Users/epignott/Desktop/WebRules.g:1:40: T__16
                {
                mT__16(); 

                }
                break;
            case 7 :
                // /Users/epignott/Desktop/WebRules.g:1:46: T__17
                {
                mT__17(); 

                }
                break;
            case 8 :
                // /Users/epignott/Desktop/WebRules.g:1:52: T__18
                {
                mT__18(); 

                }
                break;
            case 9 :
                // /Users/epignott/Desktop/WebRules.g:1:58: T__19
                {
                mT__19(); 

                }
                break;
            case 10 :
                // /Users/epignott/Desktop/WebRules.g:1:64: T__20
                {
                mT__20(); 

                }
                break;
            case 11 :
                // /Users/epignott/Desktop/WebRules.g:1:70: T__21
                {
                mT__21(); 

                }
                break;
            case 12 :
                // /Users/epignott/Desktop/WebRules.g:1:76: T__22
                {
                mT__22(); 

                }
                break;
            case 13 :
                // /Users/epignott/Desktop/WebRules.g:1:82: T__23
                {
                mT__23(); 

                }
                break;
            case 14 :
                // /Users/epignott/Desktop/WebRules.g:1:88: T__24
                {
                mT__24(); 

                }
                break;
            case 15 :
                // /Users/epignott/Desktop/WebRules.g:1:94: STRING
                {
                mSTRING(); 

                }
                break;
            case 16 :
                // /Users/epignott/Desktop/WebRules.g:1:101: INT
                {
                mINT(); 

                }
                break;
            case 17 :
                // /Users/epignott/Desktop/WebRules.g:1:105: ID
                {
                mID(); 

                }
                break;
            case 18 :
                // /Users/epignott/Desktop/WebRules.g:1:108: WS
                {
                mWS(); 

                }
                break;
            case 19 :
                // /Users/epignott/Desktop/WebRules.g:1:111: CMT
                {
                mCMT(); 

                }
                break;
            case 20 :
                // /Users/epignott/Desktop/WebRules.g:1:115: URL
                {
                mURL(); 

                }
                break;
            case 21 :
                // /Users/epignott/Desktop/WebRules.g:1:119: FLOAT
                {
                mFLOAT(); 

                }
                break;

        }

    }


    protected DFA7 dfa7 = new DFA7(this);
    static final String DFA7_eotS =
        "\10\uffff\1\25\6\uffff\1\26\1\23\7\uffff\3\23\1\uffff";
    static final String DFA7_eofS =
        "\34\uffff";
    static final String DFA7_minS =
        "\1\11\7\uffff\1\76\6\uffff\1\56\1\164\7\uffff\1\164\1\160\1\72\1"+
        "\uffff";
    static final String DFA7_maxS =
        "\1\175\7\uffff\1\76\6\uffff\1\71\1\164\7\uffff\1\164\1\160\1\72"+
        "\1\uffff";
    static final String DFA7_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\uffff\1\11\1\12\1\13\1\14"+
        "\1\15\1\17\2\uffff\1\22\1\23\1\21\1\10\1\16\1\20\1\25\3\uffff\1"+
        "\24";
    static final String DFA7_specialS =
        "\34\uffff}>";
    static final String[] DFA7_transitionS = {
            "\2\21\25\uffff\1\21\1\uffff\1\16\11\uffff\1\14\1\uffff\1\3\1"+
            "\22\12\17\1\2\1\11\1\4\1\10\1\5\1\15\1\1\32\23\1\12\1\uffff"+
            "\1\13\3\uffff\7\23\1\20\22\23\1\6\1\uffff\1\7",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\24",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\27\1\uffff\12\17",
            "\1\30",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\31",
            "\1\32",
            "\1\33",
            ""
    };

    static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
    static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
    static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
    static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
    static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
    static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
    static final short[][] DFA7_transition;

    static {
        int numStates = DFA7_transitionS.length;
        DFA7_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
        }
    }

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = DFA7_eot;
            this.eof = DFA7_eof;
            this.min = DFA7_min;
            this.max = DFA7_max;
            this.accept = DFA7_accept;
            this.special = DFA7_special;
            this.transition = DFA7_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | STRING | INT | ID | WS | CMT | URL | FLOAT );";
        }
    }
 

}