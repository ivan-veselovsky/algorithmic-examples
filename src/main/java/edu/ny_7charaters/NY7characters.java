package edu.ny_7charaters;

import java.util.List;

public class NY7characters {

    List<String> findWords(final String seven) {
        final char[] template = seven.toCharArray();
        assert template.length == 7;

        for (int length = 4; length <= 5; length++) {
            SevenWord sevenWord = new SevenWord(length);
            while (!sevenWord.isMaximum()) {
                sevenWord.increment();

                char[] maybeWord = sevenWord.composeMaybeWord(template);
                System.out.println(new String(maybeWord));
            }
        }

        return List.of();
    }

    public static void main(String[] args) {
        new NY7characters().findWords("0123456");
    }

    static class SevenWord {
        final int digits;
        final int maxNUmber;
        int data;

        SevenWord(int digits) {
            this.digits = digits;
            this.maxNUmber = powerOf7(digits) - 1;
        }

        void increment() {
            data++;
            if (data > maxNUmber) {
                data = 0;
            }
        }

        int getDigit(int digitNumber) {
            int sevenPowerDigitNumber = powerOf7(digitNumber);
            return (data / sevenPowerDigitNumber) % 7;
        }

        private int powerOf7(int x) {
            int prod = 1;
            for (int i=0; i<x; i++) {
                prod *= 7;
            }
            return prod;
        }

        boolean isMaximum() {
            return data == maxNUmber;
        }

        char[] composeMaybeWord(char[] template) {
            assert template.length == 7;
            char[] result = new char[digits];
            for (int d = 0; d < digits; d++) {
                int index = getDigit(d);
                result[d] = template[index];
            }
            return result;
        }
    }
}
