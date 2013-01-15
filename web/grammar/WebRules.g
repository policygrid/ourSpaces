grammar WebRules;

@header {
  package org.policygrid.reasoning;
  import java.util.Hashtable;
  }

@members {
  private int index = 0;
  
  
  public class STriple { 
	  public String subject;
	  public String predicate;
	  public String object;
	  public STriple(String subject, String predicate, String object) {
	   this.subject = subject;
	   this.predicate = predicate;
	   this.object = object;
	   out("TRIPLE: "+subject+" "+predicate+" "+object);
	  }
  }
  
  public Hashtable<String, String> prefixes = new Hashtable<String, String>();
  
  public ArrayList<STriple> head;
  
  public ArrayList<STriple> body;
  
  String pos = "";
  
  private void addTriple(STriple tr) {
    if (pos.equals("head")) head.add(tr);
    if (pos.equals("body")) body.add(tr);
  }
  
  private static void out(Object obj) {
    System.out.println(obj);
  }

}


rule :  prefix* head body* ;

prefix 	
	: '@prefix' ID ':'  u=uri '.'* {prefixes.put($ID.text,$u.value); out("Prefix: "+$ID.text+" "+$u.value);}
	;

uri returns [String value]
	:	'<' URL '>' {$value = $URL.text;}
	; 

head 
@init{head = new ArrayList<STriple>(); pos = "head"; out("HEAD:"); }
	:	'{' stat+ '}' 
	;

body
@init{body = new ArrayList<STriple>(); pos = "body"; out("BODY:");}
 	:	'=>' '{' stat+ '}'
	;

stat	
	: triple '.'* 	
	;

triple 	returns [ArrayList<STriple> triples]
        :	s=sub p=pre o=obj (';' p1=pre o1=obj {addTriple(new STriple($s.value, $p1.value, $o1.value));})* {addTriple(new STriple($s.value, $p.value, $o.value));}  
	|	s=sub p=pre '[' p1=pre o1=obj ']' {addTriple(new STriple($s.value, $p.value, "?ref_"+index)); addTriple(new STriple("?ref_"+index, $p1.value, $o1.value)); index++;} 
	;
		

sub returns [String value]
	:	e=expr {$value = $e.value;}
	;

pre returns [String value]
	:	e=expr {$value = $e.value;} 
	;

obj returns [String value] 
	:	e=expr (',' e1=expr)* {$value = $e.value;}
	;

expr returns [String value]
    :   ID  {$value = $ID.text;}
    |   INT {$value = $INT.text;}
    |   FLOAT {$value = $FLOAT.text;}
    |   i=ID ':' i1=ID {$value = "<"+prefixes.get($i.text)+$i1.text+">";}
    |   '?' ID {$value = "?"+$ID.text;}
    |   STRING {$value = $STRING.text;}
   // |   '[' pre obj ']'
   // |   '{' stat '}'
   // |   '(' stat ')'
    |   '?' i=ID '=' '?' i1=ID {$value = "?"+$i.text+"=?"+$i1.text;}
    |   u=uri {$value = $u.value;}
    ;

STRING : '"' .* '"' ;
INT :   '0'..'9'+ ;
ID 	:	('a'..'z'|'A'..'Z') ('_'|'a'..'z'|'A'..'Z'|'0'..'9')* ;
WS  :   (' '|'\n'|'\t')+ {$channel=HIDDEN;} ;
CMT :   '/*' .* '*/'     {$channel=HIDDEN;} ;
URL 	: 'http://' ('_'|'a'..'z'|'A'..'Z'|'0'..'9' | '.'| '#' | '/' | '?' | '&' | '%' | '=' | '!' )* ;
FLOAT: INT '.' INT ;

