package edu.longest_palindromic_substring;

import edu.rabin_karp_substring_search.RabinKarp;

import java.util.IntSummaryStatistics;
import java.util.function.Supplier;

import static edu.rabin_karp_substring_search.RabinKarp.q_62;

public class LongestPalindromicSubstring {

    public static final int maxAlphabetSize = 62; // lower/uppercase English letters + numbers

    public String longestPalindrome(String inputString) {
        if (inputString.length() <= 1) {
            return inputString;
        }
        if (inputString.length() == 2) {
            if (inputString.charAt(0) == inputString.charAt(1)) {
                return inputString;
            } else {
                return inputString.substring(0, 1);
            }
        }

        IntSummaryStatistics stat = inputString.chars().summaryStatistics();
        final int alphabetSize = stat.getMax() - stat.getMin() + 1;
        assert alphabetSize > 0 && alphabetSize <= maxAlphabetSize;
        final int min = stat.getMin();
        final int[] textValues = RabinKarp.makeTextValues(inputString, ch -> ch - min);

        PalindromeInfo longestPalindrome = process("even", textValues, 0, 0,
                () -> createEvenPair(q_62, alphabetSize, textValues));

        if (longestPalindrome.fullLength() < textValues.length) {
            int startHalfPalindromity = (longestPalindrome.halfLength() < 1) ? 0 : longestPalindrome.halfLength() - 1;
            PalindromeInfo longestOddPalindrome = process("odd", textValues, startHalfPalindromity,
                    1, () -> createOddPair(q_62, alphabetSize, textValues));
            if (longestOddPalindrome.fullLength() > longestPalindrome.fullLength()) {
                longestPalindrome = longestOddPalindrome;
            }
        }

        return getPalindrome(inputString, longestPalindrome);
    }

    record PalindromeInfo(int center, int halfLength, int fullLength) {
        PalindromeInfo {
            assert fullLength / 2 == halfLength;
        }
        int startInclusive() {
            return center - halfLength;
        }
        int endExclusive() {
            return startInclusive() + fullLength;
        }
    }

    private PalindromeInfo process(final String name, int[] textValues,
                                   final int startHalfPalindromity, final int palindromeLengthAddon,
                                   Supplier<RabinKarpPair> supplier) {
        final int center = textValues.length / 2;
        final RabinKarpPair leftPair  = supplier.get().position(center);
        final RabinKarpPair rightPair = supplier.get().position(center);

        int halfPalindromity = startHalfPalindromity;
        int foundPalindromeCenterIndex = -1;
        boolean leftAlive = true;
        boolean rightAlive = true;
        while (leftAlive || rightAlive) {
            if (leftAlive) {
                // 0. extend to the Palindromity found so far + 1,
                // because shorter palindromes are not of our interest anymore:
                while (leftPair.halfLength() <= halfPalindromity) {
                    if (!leftPair.extend()) {
                        leftAlive = false;
                        break;
                    }
                }
                // 1. extend while it is a palindrome, extend once again to grab next size:
                while (leftAlive && leftPair.isPalindrome()) {
                    halfPalindromity = leftPair.halfLength();
                    foundPalindromeCenterIndex = leftPair.center();
                    System.out.println(name + " left: p = " + halfPalindromity + ", c = " + foundPalindromeCenterIndex);
                    if (!leftPair.extend()) {
                        leftAlive = false;
                        break;
                    }
                }
                // 2. shift to the left
                if (leftAlive && !leftPair.shiftLeft()) {
                    leftAlive = false; // stop, this pair is done.
                }
            }

            // same with right pair:
            if (rightAlive) {
                while (rightPair.halfLength() <= halfPalindromity) {
                    if (!rightPair.extend()) {
                        rightAlive = false;
                        break;
                    }
                }
                while (rightAlive && rightPair.isPalindrome()) {
                    halfPalindromity = rightPair.halfLength();
                    foundPalindromeCenterIndex = rightPair.center();
                    System.out.println(name + " right: p = " + halfPalindromity + ", c = " + foundPalindromeCenterIndex);
                    if (!rightPair.extend()) {
                        rightAlive = false;
                        break;
                    }
                }
                if (rightAlive && !rightPair.shiftRight()) {
                    rightAlive = false;
                }
            }
        }

        if (foundPalindromeCenterIndex < 0) {
            return new PalindromeInfo(0, 0, palindromeLengthAddon); // not found
        } else {
            return new PalindromeInfo(foundPalindromeCenterIndex, halfPalindromity,
                    palindromeLengthAddon + halfPalindromity * 2);
        }
    }

    private String getPalindrome(String inputString, PalindromeInfo palindromeInfo) {
        return inputString.substring(palindromeInfo.startInclusive(), palindromeInfo.endExclusive());
    }

    static RabinKarpPair createEvenPair(long modulo, int alphabetSize, int[] values) {
        return new RabinKarpPair(
             new RabinKarp(false, modulo, alphabetSize, values),
             new RabinKarp(true, modulo, alphabetSize, values),
             0, values);
    }
    static RabinKarpPair createOddPair(long modulo, int alphabetSize, int[] values) {
        return new RabinKarpPair(
                new RabinKarp(false, modulo, alphabetSize, values),
                new RabinKarp(true, modulo, alphabetSize, values),
                1, values);
    }

    static class RabinKarpPair {
        final RabinKarp left;
        final RabinKarp right;
        final int oddMiddleLength; // 0 for Even, 1 for Odd.
        final int[] values;
        RabinKarpPair(RabinKarp left, RabinKarp right, int oddMiddleLength, int[] values) {
            this.left = left;
            this.right = right;
            this.oddMiddleLength = oddMiddleLength;
            this.values = values;
        }
        RabinKarpPair position(int centerPosition) {
            assert centerPosition >= 0;
            left.compute(centerPosition, centerPosition);
            right.compute(centerPosition + oddMiddleLength, centerPosition + oddMiddleLength);
            assert invariants();
            return this;
        }
        boolean isPalindromeBruteForce() {
            int center = left.tailExclusive();
            int length = left.length();
            for (int i = 1; i <= length; i++) {
                if (values[center - i] != values[center + oddMiddleLength + i - 1]) {
                    return false;
                }
            }
            return true;
        }
        final boolean invariants() {
            return left.length() == right.length()
                    && left.tailExclusive() + oddMiddleLength == right.headInclusive();
        }
        final boolean isPalindrome() {
            boolean p0 = (left.maybeMatch(right) == 1);
            boolean p1 = isPalindromeBruteForce();
            assert p0 == p1;
            return p0;
        }
        final boolean shiftLeft() {
            if (!left.shiftToHead()) {
                return false;
            }
            boolean b = right.shiftToHead();
            assert b;
            return true;
        }
        final boolean shiftRight() {
            if (!right.shiftToTail()) {
                return false;
            }
            boolean b = left.shiftToTail();
            assert b;
            return true;
        }
        final boolean extend() {
            if (left.canExtendHead() && right.extendTail()) {
                boolean b = left.extendHead();
                assert b;
                return true;
            }
            return false;
        }
        /** real fullLength of OddPair == (2 * halfLength() + 1) */
        final int halfLength() {
            assert invariants();
            return left.length();
        }
        final int center() {
            assert invariants();
            return left.tailExclusive();
        }
    }
}
