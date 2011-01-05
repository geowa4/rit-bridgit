grammar Pseudo;

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
    constant*
    (variable | function | procedure | typeDec)*
    'begin'
    statement*
    'end' IDENT?
  ;
  
constant
  : 'constant' IDENT ':' type ':=' expression
  ;

variable
  : 'var' IDENT (',' IDENT)* ':' type (':=' expression)?
  ;

procedure
  : 'procedure' IDENT '(' parameters* ')'
    (constant | variable)*
    'begin'
      statement*
    'end' IDENT
  ;

function
  : 'function' IDENT '(' parameters ')' ':' type
    (constant | variable)*
    'begin'
      statement*
      'return' IDENT
    'end' IDENT
  ;
  
procedureCall
  : IDENT '(' arguments? ')'
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
  | procedureCall
  ;

assignment
  : IDENT ':=' expression
  ;

conditional
  : 'if' expression 'then' statement+
    ('elsif' expression 'then' statement+)*
    ('else' statement+)?
    'fi'
  ;

loop
  : 'while' expression 'do' statement* 'done'
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
  : 'struct' IDENT 'def' field* 'fed'
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
  : add (('=' | '/=' | '<' | '<=' | '>=' | '>') add)*
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
