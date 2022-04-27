package com.apereg.pl;
%%

/* Opciones. */
%class LexicalAnalyzer
%type Yytoken

/* Declaraciones. */
%line
%column

/* Expresiones. */
DIGIT = [0-9]
NUMBER = {DIGIT} {DIGIT}*
PLUS = [+]
MINUS = [-]
TIMES = [*]
DIV = [/]
LEFT_BRACKET = [(]
RIGHT_BRACKET = [)]
SEMICOLON = [;]
BLANK = [\t| |]+
EOL = [\r|\n|\r\n]
%%

/* Reglas y acciones. */

{BLANK} {}

{EOL} {}

{NUMBER} { return new Yytoken(yytext(), TokenType.INTEGER, yyline, yycolumn); }

{PLUS} { return new Yytoken(yytext(), TokenType.PLUS, yyline, yycolumn); }

{MINUS} { return new Yytoken(yytext(), TokenType.MINUS, yyline, yycolumn); }

{TIMES} { return new Yytoken(yytext(), TokenType.TIMES, yyline, yycolumn); }

{DIV} { return new Yytoken(yytext(), TokenType.DIV, yyline, yycolumn); }

{LEFT_BRACKET} { return new Yytoken(yytext(), TokenType.LEFT_BRACKET, yyline, yycolumn); }

{RIGHT_BRACKET} { return new Yytoken(yytext(), TokenType.RIGHT_BRACKET, yyline, yycolumn); }

{SEMICOLON} { return new Yytoken(yytext(), TokenType.SEMICOLON, yyline, yycolumn); }

. {
    return new Yytoken(yytext(), TokenType.UNKNOWN , yyline, yycolumn);
}