package com.apereg.pl;

import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        /* Comprobacion de fichero de entrada. */
        if (args.length != 1) {
            System.out.println("Usage: java -jar practica4PL_apereg24.jar <input_file>");
        }

        /* Construccion del analizador lexico. */
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(new FileReader(args[0]));

        /* Construccion del analizador sintactico. */
        Parser asdr = new Parser(lexicalAnalyzer);

        /* Ejecucion del analizador sintactico descendente recursivo. */
        asdr.getNextToken();
        do {
            asdr.s();
        } while (!asdr.getCurrentTokenType().equals(TokenType.EOF));
    }

}

