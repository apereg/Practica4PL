package com.apereg.pl;

public class Yytoken {

    private String lexeme;

    private TokenType tokenType;

    private int line;

    private int column;

    public Yytoken(String lexeme, TokenType tokenType, int line, int column) {
        this.lexeme = lexeme;
        this.tokenType = tokenType;
        this.line = line;
        this.column = column;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void error(String errorMessage) {
        System.out.println("[Parser] Error: " + errorMessage + " " + this.tokenType.toString() + " at line " + line + ", column " + column + " (expected: other)");
    }

    public void error(String errorMessage, String expected) {
        System.out.println("[Parser] Error: " + errorMessage + " " + this.tokenType.toString() + " at line " + line + ", column " + column + " (expected " + expected + ")");
    }

    @Override
    public String toString() {
        return "Yytoken{" + "lexeme=" + lexeme + ", tokenType=" + tokenType + ", line=" + line + ", column=" + column + '}';
    }

}
