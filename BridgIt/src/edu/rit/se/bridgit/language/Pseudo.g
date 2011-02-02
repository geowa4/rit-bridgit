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
      main  {$eval.add($main.eval);}
    '}'
  ;

setup returns [BlockEvaluator eval]
  : 'setup' '{' {$eval = new BlockEvaluator(false);}
      ( constant {$eval.add($constant.eval);} )*
      
      ( variable {$eval.add($variable.eval);} 
      | function {$eval.add($function.eval);}
      )*
    '}'
  ;

main returns [BlockEvaluator eval]
  : 'main' '{' {$eval = new BlockEvaluator();}
      (statement {$eval.add($statement.eval);})*
    '}'
  ;
  
constant returns [Evaluator eval]
  : 'constant' IDENT ':' type '=' expression ';'
    {$eval = new ConstantEvaluator($IDENT.text, $type.text, $expression.eval);}
  ;

variable returns [Evaluator eval]
  : 'var'IDENT ':' type ('=' expression)? ';'
    {$eval = new VariableEvaluator($IDENT.text, $type.text, $expression.eval);}
  ;

function returns [FunctionEvaluator eval]
  :'function' IDENT '(' parameters? ')' (':' type)? {$eval = new FunctionEvaluator($IDENT.text, $parameters.eval, $type.text, true);}
    '{'				{BlockEvaluator function_block = new BlockEvaluator();}
      (constant {function_block.add($constant.eval);})*		
      (variable {function_block.add($variable.eval);})*
      (statement {function_block.add($statement.eval);})*
      ('return' 
      expression {function_block.add($expression.eval);}
      ';')?
    '}'
  ;

arguments returns [GroupEvaluator eval]
  : {$eval = new GroupEvaluator();}
  firstExp=expression {eval.addItem($firstExp.eval);}
  (',' 
  optionalExp=expression {eval.addItem($optionalExp.eval);} 
  )* 
  ;

parameters returns [GroupEvaluator eval]
  : {$eval = new GroupEvaluator();}
  firstParam=parameter {eval.addItem($firstParam.eval);}
  (',' 
  optionalParam=parameter {eval.addItem($optionalParam.eval);}
  )*
  ;

parameter returns [ParameterEvaluator eval]
  : IDENT ':' type
  {$eval = new ParameterEvaluator($IDENT.text, $type.text);}
  ; 

statement returns [Evaluator eval]
  : assignment   {$eval = $assignment.eval;}
  | conditional  {$eval = $conditional.eval;}
  | loop         {$eval = $loop.eval;}
  | functionCall {$eval = $functionCall.eval;}
  ;

assignment returns [Evaluator eval]
  : IDENT '=' expression ';' {$eval = new VariableEvaluator($IDENT.text, $expression.eval);}
  ;

conditional returns [IfEvaluator eval]
  : {$eval = new IfEvaluator();}
    'if' ifExp=expression
       '{'                            {BlockEvaluator if_block = new BlockEvaluator();} 
       (
         ifStmt=statement             {if_block.add($ifStmt.eval);}
       )+ 
       '}'                            {$eval.addConditional($ifExp.eval, if_block);}
    
    ('else' 'if' elseIfExp=expression 
       '{'                            {BlockEvaluator else_if_block = new BlockEvaluator();}
       (
         elseIfStmt=statement         {else_if_block.add($elseIfStmt.eval);}
       )+ 
       '}'                            {$eval.addConditional($elseIfExp.eval, else_if_block);}
    )*
    ('else' 
       '{'                            {BlockEvaluator else_block = new BlockEvaluator();}
       (
         elseStmt=statement           {else_block.add($elseStmt.eval);}
       )+ 
       '}'                            {$eval.addConditional(new BooleanEvaluator(true), else_block);}
    )?
  ;

loop returns [WhileEvaluator eval]
  : {$eval = new WhileEvaluator();}
    'while' whileExp=expression
       '{'                            {BlockEvaluator while_block = new BlockEvaluator();} 
       (
         whileStmt=statement             {while_block.add($whileStmt.eval);}
       )* 
       '}'                            {$eval.addConditional($whileExp.eval, while_block);}
	;


functionCall returns [FunctionCallEvaluator eval]
  : IDENT '(' arguments? ')' ';'
     {$eval = new FunctionCallEvaluator($IDENT.text, $arguments.eval);}
  ;

type returns [String name]
  : 'Integer' {$name = "Integer";}
  | 'Boolean' {$name = "Boolean";}
  | 'String'  {$name = "String";}
  | 'List'    {$name = "List";}
  | IDENT     {$name = $IDENT.text;}
  ;

term returns [Evaluator eval]
  : STRING_LITERAL           {$eval = new StringEvaluator($STRING_LITERAL.text);}
  | IDENT                    {$eval = new MemberLoadEvaluator($IDENT.text);}
  | '(' expression ')'       {$eval = $expression.eval;}
  | INTEGER                  {$eval = new IntegerEvaluator(Integer.parseInt($INTEGER.text));}
  | bool                     {$eval = $bool.eval;}
  | IDENT '(' arguments? ')' {$eval = new IntegerEvaluator(0);}
  ;

bool returns[Evaluator eval]
  : 'true'  {$eval = new BooleanEvaluator(true);}
  | 'false' {$eval = new BooleanEvaluator(false);}
  ;

negation returns [Evaluator eval]
  : {boolean negated = false;}
    ('not' {negated = !negated;})* 
    term {$eval = $term.eval;}
    {if(negated) $eval = new NegationEvaluator($eval);}
  ;

unary returns [Evaluator eval]
  : {boolean negated = false;}
    ('+' 
    | '-' {negated = !negated;}
    )* negation {$eval = $negation.eval;}
    {if(negated) $eval = new UnaryEvaluator($eval);}
  ;

mult returns [Evaluator eval]
  : op1=unary           {$eval = $op1.eval;}
    ( ( '*' op2=unary   {$eval = new MultEvaluator($eval, $op2.eval);}
      | '/' op2=unary   {$eval = new DivideEvaluator($eval, $op2.eval);}
      | 'mod' op2=unary {$eval = new ModEvaluator($eval, $op2.eval);}
      ) )*
  ;
  
add returns [Evaluator eval]
  : op1=mult         {$eval = $op1.eval;}
    ( '+' op2=mult {$eval = new PlusEvaluator($eval, $op2.eval);}
    | '-' op2=mult {$eval = new MinusEvaluator($eval, $op2.eval);}
    )*
  ;
  
relation returns [Evaluator eval]
  : op1=add        {$eval = $op1.eval;}
    ( '==' op2=add {$eval = new EqualsEvaluator($eval, $op2.eval);}
    | '!=' op2=add {$eval = new NegationEvaluator(new EqualsEvaluator($eval, $op2.eval));}
    | '<'  op2=add {$eval = new LessThanEvaluator($eval, $op2.eval);}
    | '>'  op2=add {$eval = new LessThanEvaluator($eval, $op2.eval);}
    | '<=' op2=add {$eval = new OrEvaluator(
                      new LessThanEvaluator($eval, $op2.eval), 
                      new EqualsEvaluator($eval, $op2.eval)
                   );}
    | '>=' op2=add {$eval = new OrEvaluator(
                     new GreaterThanEvaluator($eval, $op2.eval), 
                     new EqualsEvaluator($eval, $op2.eval)
                   );}
    )*
  ;
  
expression returns [Evaluator eval]
  : op1=relation         {$eval = $op1.eval;}
    ( 'and' op2=relation {$eval = new AndEvaluator($eval, $op2.eval);}
    | 'or'  op2=relation {$eval = new OrEvaluator($eval, $op2.eval);}
    )*
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
COMMENT : '//' .* ('\r' | '\n')         {$channel = HIDDEN;};
MULTILINE_COMMENT : '/*' .* '*/'        {$channel = HIDDEN;};
