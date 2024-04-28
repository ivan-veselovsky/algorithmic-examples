package edu.balancedbrackets;

import java.util.ArrayDeque;
import java.util.Deque;

public class BalancedBrackets {

    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>(s.length() / 2);
        for (char c: s.toCharArray()) {
            switch (c) {
                case '(', '{', '[' -> stack.push(c);
                case ')' -> {
                    Character ch = stack.poll();
                    if (ch == null || ch != '(') {
                        return false;
                    }
                }
                case '}' -> {
                    Character ch = stack.poll();
                    if (ch == null || ch != '{') {
                        return false;
                    }
                }
                case ']' -> {
                    Character ch = stack.poll();
                    if (ch == null || ch != '[') {
                        return false;
                    }
                }
                default -> throw new IllegalArgumentException("Unknown character: " + c);
            }
        }
        return stack.isEmpty();
    }
}
