grammar Pseudo;

options {
  language = Java;
}

@header {
  package edu.rit.se.bridgit.language;
  import edu.rit.se.bridgit.language.evaluator.*;
  import edu.rit.se.bridgit.language.evaluator.bool.*;
  import edu.rit.se.bridgit.language.evaluator.term.*;
  import edu.rit.se.bridgit.language.evaluator.arithmetic.*;
}

@lexer::header {
  package edu.rit.se.bridgit.language;
}

application returns [BlockEvaluator eval]
  : 'application' IDENT {$eval = new BlockEvaluator();}
    '{'
      setup {$eval.add($setup.eval);}
      main 
    '}'
  ;

setup returns [BlockEvaluator eval]
  : 'setup' '{' {$eval = new BlockEvaluator();}
      ( constant {$eval.add($constant.eval);} )*
      ( variable {$eval.add($variable.eval);} 
      | function {$eval.add($function.eval);}
      )*
    '}'
  ;

main returns [BlockEvaluator eval]
  : 'main' '{' {$eval = new BlockEvaluator();}
      statement*
    '}'
  ;
  
constant returns [Evaluator eval]
  : 'constant' IDENT ':' type '=' expression
  ;

variable returns [Evaluator eval]
  : 'var'IDENT ':' type ('=' expression)?
    {$eval = new VariableEvaluator($IDENT.text, $type.text, $expression.eval);}
  ;

function returns [Evaluator eval]
  : 'function' IDENT '(' parameters? ')' (':' type)?
    '{'
      constant*
      variable*
      statement*
      ('return' IDENT)?
    '}'
  ;

arguments returns [Evaluator eval]
  : expression (',' expression)*
  ;

parameters returns [Evaluator eval]
  : parameter (',' parameter)*
  ;

parameter returns [Evaluator eval]
  : IDENT ':' type
  ; 

statement returns [Evaluator eval]
  : assignment
  | conditional
  | loop
  | functionCall
  ;

assignment returns [Evaluator eval]
  : IDENT '=' expression
  ;

conditional returns [Evaluator eval]
  : 'if' expression '{' statement+ '}'
    ('else' 'if' expression '{' statement+ '}')*
    ('else' '{' statement+ '}')?
  ;

loop returns [Evaluator eval]
  : 'while' expression '{' statement* '}'
  ;

functionCall returns [Evaluator eval]
  : IDENT '(' arguments? ')'
  ;

type returns [String name]
  : 'Integer' {$name = "Integer";}
  | 'Boolean' {$name = "Boolean";}
  | 'String'  {$name = "String";}
  | 'List'    {$name = "List";}
  | IDENT     {$name = $IDENT.text;}
  ;

term returns [Evaluator eval]
  : STRING_LITERAL
  | IDENT
  | '(' expression ')'
  | INTEGER
  | IDENT '(' arguments? ')'
  ;

negation returns [Evaluator eval]
  : 'not'* term
  ;

unary returns [Evaluator eval]
  : ('+' | '-')* negation
  ;

mult returns [Evaluator eval]
  : unary (('*' | '/' | 'mod') unary)*
  ;
  
add returns [Evaluator eval]
  : mult (('+' | '-') mult)*
  ;
  
relation returns [Evaluator eval]
  : add (('==' | '!=' | '<' | '<=' | '>=' | '>') add)*
  ;
  
expression returns [Evaluator eval]
  : relation (('and' | 'or') relation)*
  ;

fragment LETTER : ('a'..'z' | 'A'..'Z');
fragment DIGIT : '0'..'9';
STRING_LITERAL
  : '"'
    ( '\\' '"'
    | ~('"' | '\r' | '\n')
    )*
    '"'
  ;
INTEGER : DIGIT+;
IDENT : LETTER(LETTER | DIGIT)*;
WS : (' ' | '\t' | '\n' | '\r' | '\f')+ {$channel = HIDDEN;};
COMMENT : '//' .* ('\r' | '\n') {$channel = HIDDEN;};
MULTILINE_COMMENT : '/*' .* '*/' {$channel = HIDDEN;};
