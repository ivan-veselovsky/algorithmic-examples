package edu.recursive_top_to_bottom;

import java.util.Objects;

public class Node {
    final Lexema lexema;
    Node left;
    Node right;
    Node(Lexema lex) {
        this.lexema = Objects.requireNonNull(lex);
    }
    boolean isAtom() {
        return (left == null) && (right == null) && (lexema instanceof Atom);
    }
    boolean isOperation() {
        return lexema instanceof Operation;
    }
}
