package edu.longest_palindromic_substring;

import edu.rabin_karp_substring_search.RabinKarp;

import static edu.rabin_karp_substring_search.RabinKarp.q_26;

// TODO: Currently do for odd palindromicity.
public class LongestPalindromicSubstring {
    private String inputString;
    private int[] textValues;

    public String longestPalindrome(String s) {
        this.inputString = s;
        this.textValues = RabinKarp.makeTextValues(inputString, ch -> ch - 'a');

        int center = textValues.length / 2;
        RabinKarpPair leftPair  = new RabinKarpPair(q_26, 26, textValues).position(center, 1);
        RabinKarpPair rightPair = new RabinKarpPair(q_26, 26, textValues).position(center, 1);

        int halfPalindromicity = 0;
        boolean leftAlive = true;
        boolean rightAlive = true;
        int palindromeCenterIndex = -1;
        while (leftAlive || rightAlive) {
            while (leftAlive && leftPair.halfLength() < halfPalindromicity) {
                if (!leftPair.extend()) {
                    break;
                }
            }
            while (leftAlive && leftPair.isPalindrome()) {
                halfPalindromicity = leftPair.halfLength();
                palindromeCenterIndex = leftPair.center();
                if (!leftPair.extend()) {
                    break;
                }
            }
            if (leftAlive && !leftPair.shiftLeft()) {
                leftAlive = false;
            }
            int max = maxPossibleHalfPalindromicity(leftPair.center());
            if (halfPalindromicity >= max) {
                break;
            }

            while (rightAlive && rightPair.halfLength() < halfPalindromicity) {
                if (!rightPair.extend()) {
                    break;
                }
            }
            while (rightAlive && rightPair.isPalindrome()) {
                halfPalindromicity = rightPair.halfLength();
                palindromeCenterIndex = rightPair.center();
                if (!rightPair.extend()) {
                    break;
                }
            }
            if (rightAlive && !rightPair.shiftRight()) {
                rightAlive = false;
            }
            max = maxPossibleHalfPalindromicity(rightPair.center());
            if (halfPalindromicity >= max) {
                break;
            }
        }

        return getPalindrome(palindromeCenterIndex, halfPalindromicity);
    }

    private String getPalindrome(int center, int palindromicity) {
        int startInclusive = center - palindromicity;
        int endExclusive = center + palindromicity;
        return inputString.substring(startInclusive, endExclusive);
    }

    int maxPossibleHalfPalindromicity(int index) {
        if (index <= inputString.length() / 2) {
            return index;
        } else {
            return inputString.length() - 1 - index;
        }
    }

    static class RabinKarpPair {
        final RabinKarp left;
        final RabinKarp right;
        RabinKarpPair(long modulo, int alphabetSize, int[] values) {
            left = new RabinKarp(false, modulo, alphabetSize, values);
            right = new RabinKarp(true, modulo, alphabetSize, values);
        }
        RabinKarpPair position(int centerPosition, int halfSize) {
            assert halfSize >= 1;
            assert centerPosition >= halfSize;
            // TODO: now only even indices:
            left.compute(centerPosition - halfSize, centerPosition);
            right.compute(centerPosition, centerPosition + halfSize);
            assert invariants();
            return this;
        }
        boolean isPalindrome() {
            return left.maybeMatch(right) == 1;
        }
        boolean shiftLeft() {
            if (!left.shiftToHead()) {
                return false;
            }
            boolean b = right.shiftToHead();
            assert b;
            return true;
        }
        boolean shiftRight() {
            if (!right.shiftToTail()) {
                return false;
            }
            boolean b = left.shiftToTail();
            assert b;
            return true;
        }
        boolean extend() {
            if (left.canExtendHead() && right.extendTail()) {
                boolean b = left.extendHead();
                assert b;
                return true;
            }
            return false;
        }
        int halfLength() {
            assert invariants();
            return left.length();
        }
        int center() {
            assert invariants();
            return left.tailExclusive();
        }
        private boolean invariants() {
            return left.length() == right.length()
                && left.tailExclusive() == right.headInclusive();
        }
    }
}
