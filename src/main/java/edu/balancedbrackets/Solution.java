package edu.balancedbrackets;

import java.io.*;
import java.util.*;
import java.util.stream.*;

class Result {

    public static String isBalanced(String s) {
        return isBalancedBool(s) ? "YES" : "NO";
    }

    static boolean isBalancedBool(String s) {
        final EnumMap<Brace, BitSet> stackMap = new EnumMap<>(Brace.class);

        BitSet roundStack = new BitSet();
        stackMap.put(Brace.ROUND, roundStack);
        stackMap.put(Brace.ROUND_CL, roundStack);

        BitSet squareStack = new BitSet();
        stackMap.put(Brace.SQUARE, squareStack);
        stackMap.put(Brace.SQUARE_CL, squareStack);

        BitSet curlyStack = new BitSet();
        stackMap.put(Brace.CURLY, curlyStack);
        stackMap.put(Brace.CURLY_CL, curlyStack);

        int depth = 0;
        for (char ch : s.toCharArray()) {
            final Brace brace = Brace.forChar(ch);
            if (!brace.isOpening) {
                depth--;
            }
            if (depth < 0) {
                return false;
            }

            BitSet stack = stackMap.get(brace);
            if (brace.isOpening) {
                assert !stack.get(depth);
                stack.set(depth);
            } else {
                if (!stack.get(depth)) {
                    return false; // mismatch detected.
                }
                stack.clear(depth);
            }

            if (brace.isOpening) {
                depth++;
            }
        }

        return roundStack.isEmpty() && curlyStack.isEmpty() && squareStack.isEmpty();
    }
}

enum Brace {
    ROUND('(', true),
    SQUARE('[', true),
    CURLY('{', true),

    ROUND_CL(')', false),
    SQUARE_CL(']', false),
    CURLY_CL('}', false);

    final char ch;
    final boolean isOpening;

    Brace(char c, boolean op) {
        ch = c;
        isOpening = op;
    }

    public static Brace forChar(char c) {
        return switch (c) {
            case '(' -> Brace.ROUND;
            case '[' -> Brace.SQUARE;
            case '{' -> Brace.CURLY;
            case ')' -> Brace.ROUND_CL;
            case ']' -> Brace.SQUARE_CL;
            case '}' -> Brace.CURLY_CL;
            default -> throw new IllegalArgumentException("char: " + c);
        };
    }
}


public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                String s = bufferedReader.readLine();

                String result = Result.isBalanced(s);

                bufferedWriter.write(result);
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
