package edu.recursive_top_to_bottom;

public record Atom(String token) implements Lexema {

    @Override
    public String token() {
        return token;
    }
}
