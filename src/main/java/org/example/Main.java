package org.example;

import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

import org.example.TParser.a_return;

import java.io.IOException;


/**
 * Test driver program for the ANTLR3 Maven Architype demo
 *
 * @author Jim Idle (jimi@temporal-wave.com)
 */
class Main {

    /**
     * Just a simple test driver for the ASP parser
     * to show how to call it.
     */

    public static void main(String[] args) throws Exception {

        TLexer lexer = new TLexer();

        String source = args[0];

        System.out.println("source: " + source);

        lexer.setCharStream(new ANTLRStringStream(source));

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        Tree tree = parseSource(tokens);

        printTree(tree, 0);

    }

    private static void printTree(Tree tree, int depth) {
        System.out.println("  ".repeat(depth) + TTree.tokenNames[tree.getType()]);
        for (int i = 0; i < tree.getChildCount(); i++) {
            Tree child = tree.getChild(i);
            printTree(child, depth + 1);
        }
    }


    public static Tree parseSource(CommonTokenStream tokens) throws Exception {
        // Now we need an instance of our parser
        //
        TParser parser = new TParser(tokens);


        // Provide some user feedback
        //
        System.out.println("    Lexer Start");
        long start = System.currentTimeMillis();

        // Force token load and lex (don't do this normally,
        // it is just for timing the lexer)
        //
        tokens.LT(1);
        long lexerStop = System.currentTimeMillis();
        System.out.println("      lexed in " + (lexerStop - start) + "ms.");

        // And now we merely invoke the start rule for the parser
        //
        System.out.println("    Parser Start");
        long pStart = System.currentTimeMillis();
        a_return psrReturn = parser.a();
        long stop = System.currentTimeMillis();
        System.out.println("      Parsed in " + (stop - pStart) + "ms.");

        // Pick up the generic tree
        //
        Tree t = (Tree) psrReturn.getTree();
        return t;
    }

}
