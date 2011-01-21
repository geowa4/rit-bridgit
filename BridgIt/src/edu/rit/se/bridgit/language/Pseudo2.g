grammar Pseudo2;

options {
  language = Java;
}

@header {
  package edu.rit.se.bridgit.language;
}

@lexer::header {
  package edu.rit.se.bridgit.language;
}

application
  : 'application' IDENT
    '{'
      setup
      main
    '}'
  ;

setup
  : 'setup' '{'
      constant*
      (variable | function | typeDec)*
    '}'
  ;

main
  : 'main' '{'
      statement*
    '}'
  ;
  
constant
  : 'constant' IDENT ':' type '=' expression
  ;

variable
  : 'var' IDENT (',' IDENT)* ':' type ('=' expression)?
  ;

function
  : 'function' IDENT '(' parameters? ')' (':' type)?
    '{'
      constant*
      variable*
      statement*
      ('return' IDENT)?
    '}'
  ;

arguments
  : expression (',' expression)*
  ;

parameters
  : parameter (',' parameter)*
  ;

parameter
  : IDENT ':' type
  ; 

statement
  : assignment
  | conditional
  | loop
  | functionCall
  ;

assignment
  : IDENT '=' expression
  ;

conditional
  : 'if' expression '{' statement+ '}'
    ('else' 'if' expression '{' statement+ '}')*
    ('else' '{' statement+ '}')?
  ;

loop
  : 'while' expression '{' statement* '}'
  ;

functionCall
  : IDENT '(' arguments? ')'
  ;

type
  : 'Integer'
  | 'Boolean'
  | 'String'
  | 'List'
  | IDENT
  ;

typeDec
  : structType
  | enumType
  ;
  
structType
  : 'struct' IDENT '{' field* '}'
  ;
  
field
  : IDENT ':' type
  ;

enumType
  : 'enum' IDENT '=' '<' IDENT (',' IDENT)* '>'
  ;

term
  : STRING_LITERAL
  | IDENT
  | '(' expression ')'
  | INTEGER
  | IDENT '(' arguments? ')'
  ;

negation
  : 'not'* term
  ;

unary
  : ('+' | '-')* negation
  ;

mult
  : unary (('*' | '/' | 'mod') unary)*
  ;
  
add
  : mult (('+' | '-') mult)*
  ;
  
relation
  : add (('==' | '!=' | '<' | '<=' | '>=' | '>') add)*
  ;
  
expression
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
