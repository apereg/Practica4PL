package com.apereg.pl;

import java.io.IOException;

public class Parser {

    private final LexicalAnalyzer lexicalAnalyzer;

    private Yytoken currentToken = null;

    private boolean recoverMode;

    public Parser(LexicalAnalyzer lexicalAnalyzer) {
        this.lexicalAnalyzer = lexicalAnalyzer;
        this.recoverMode = false;
    }

    public TokenType getCurrentTokenType() {
        return currentToken.getTokenType();
    }

    public void getNextToken() {
        Yytoken prevToken = currentToken;
        try {
            this.currentToken = this.lexicalAnalyzer.yylex();
            if (currentToken == null) {
                System.out.println("[Parser] End of file");
                currentToken = new Yytoken("End of file", TokenType.EOF, prevToken.getLine(), prevToken.getColumn());
            }
        } catch (IOException e) {
            currentToken.error("Error getting token next to");
        }
    }

    /*

    Producciones:
    E -> E+T;| E-T; | T;
	T -> T*F | T/F | F
	F -> (E) | int
	------------------------
	Quitamos recursividades a izquierdas y factorizamos para crear una gramatica LL(1):
	S -> E
	E -> TE';
	E'-> +TE'| -TE' | void
	T -> FT'
	T'-> *FT' | /FT' | void
	F -> (E) | int

     */

    public void s() {
        this.e();
        this.match(TokenType.SEMICOLON);
    }

    public void e() {
        this.t();
        this.ePrime();
    }

    public void ePrime() {
        if (this.currentToken.getTokenType().equals(TokenType.PLUS)) {
            this.match(TokenType.PLUS);
            this.t();
            this.ePrime();
        } else if (this.currentToken.getTokenType().equals(TokenType.MINUS)) {
            this.match(TokenType.MINUS);
            this.t();
            this.ePrime();
        }
        // if none of both -> void (epsilon)
    }

    public void t() {
        this.f();
        this.tPrime();
    }

    public void tPrime() {
        if (this.currentToken.getTokenType().equals(TokenType.TIMES)) {
            this.match(TokenType.TIMES);
            this.f();
            this.tPrime();
        } else if (this.currentToken.getTokenType().equals(TokenType.DIV)) {
            this.match(TokenType.DIV);
            this.f();
            this.tPrime();
        }
        // if none of both -> void (epsilon)
    }

    public void f() {
        if (this.currentToken.getTokenType().equals(TokenType.LEFT_BRACKET)) {
            this.match(TokenType.LEFT_BRACKET);
            this.e();
            this.match(TokenType.RIGHT_BRACKET);
        } else if (this.currentToken.getTokenType().equals(TokenType.INTEGER)) {
            this.match(TokenType.INTEGER);
        } else {
            this.match(TokenType.UNKNOWN);
        }
    }


    public void match(TokenType tokenType) {
        if (this.currentToken.getTokenType().equals(tokenType)) {
            if (tokenType.equals(TokenType.SEMICOLON) && this.recoverMode) {
                System.out.println("[Parser] Recovering with token type " + tokenType);
            } else {
                System.out.println("[Parser] Matched token type " + tokenType + " with lexeme " + this.currentToken.getLexeme());
            }
            this.recoverMode = false;
            this.getNextToken();
        } else if (this.currentToken.getTokenType().equals(TokenType.EOF)) {
            System.out.println("[Parser] End of file");
        } else if (this.currentToken.getTokenType().equals(TokenType.SEMICOLON) && !this.recoverMode) {
        } else {
            if (tokenType.equals(TokenType.UNKNOWN) || tokenType.equals(TokenType.SEMICOLON)) {
                this.currentToken.error("Found token");
                this.recoverMode = (this.currentToken.getTokenType().equals(TokenType.SEMICOLON) && tokenType.equals(TokenType.UNKNOWN));
            } else {
                this.currentToken.error("Found token", String.valueOf(tokenType));
            }
            // Ignore tokens until semicolon
            while (!this.currentToken.getTokenType().equals(TokenType.SEMICOLON) && !this.currentToken.getTokenType().equals(TokenType.EOF)) {
                System.out.println("[Parser] Skipping token " + this.currentToken.getTokenType() + " with lexeme " + this.currentToken.getLexeme());
                this.getNextToken();
                this.recoverMode = true;
            }
        }

    }


}

