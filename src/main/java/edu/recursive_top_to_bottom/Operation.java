package edu.recursive_top_to_bottom;

public enum Operation implements Lexema {
    multiplication("*"),
    division("/"),
    sum("+"),
    minus("-");

    private final String lexema;
    Operation(String lex) {
        this.lexema = lex;
    }

    @Override
    public String token() {
        return lexema;
    }
}
