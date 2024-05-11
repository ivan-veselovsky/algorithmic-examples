package edu.longest_palindromic_substring;

//import edu.rabin_karp_substring_search.RabinKarp;
//
//import static java.lang.Math.max;
//
//// Currently do for odd palindromicity.
//public class LongestPalindromicSubstring {
//    private String inputString;
//    private char[] chars;
//    private RabinKarp rkA;
//    private RabinKarp rkB;
//    private RabinKarp rkC;
//    private RabinKarp rkD;
//    private int provenHalfPalindromicity = 1;
//    private int longestPalindromIndex = 0;
//
//    public String longestPalindrome(String s) {
//        this.inputString = s;
//        this.chars = s.toCharArray();
//
//        int left = chars.length / 2;
//        int right = chars.length / 2 + 1;
//        while (true) {
//            int stepCount = 0;
//            if (left > 0 && provenHalfPalindromicity < maxPossibleHalfPalindromicity(left)) {
//                left = tryIncreasePalindromicity(left, rkA, rkB);
//                stepCount++;
//            }
//            if (right < chars.length && provenHalfPalindromicity < maxPossibleHalfPalindromicity(right)) {
//                right = tryIncreasePalindromicity(right, rkC, rkD);
//                stepCount++;
//            }
//            if (stepCount == 0) {
//                break; // we did not do any progress in this loop
//            }
//        }
//
//        return getPalindrome();
//    }
//
//    private String getPalindrome() {
//        int startInclusive = longestPalindromIndex - provenHalfPalindromicity;
//        int endExclusive = longestPalindromIndex + provenHalfPalindromicity + 1;
//        return inputString.substring(startInclusive, endExclusive);
//    }
//
//    int maxPossibleHalfPalindromicity(int index) {
//        if (index <= inputString.length() / 2) {
//            return index;
//        } else {
//            return inputString.length() - 1 - index;
//        }
//    }
//
//    private boolean tryIncreasePalindromicity(int centerIndex, RabinKarpNumber left, RabinKarpNumber right) {
//        // 0. Check fast negative
//        char firstLeft = chars[centerIndex - provenHalfPalindromicity];
//        char firstRight = chars[centerIndex + provenHalfPalindromicity];
//        if (firstLeft != firstRight) {
//            left.shiftLeft();
//            right.shiftLeft();
//            return false; // short false.
//        }
//
//        // increase RK
//        left.addHighDigit(firstLeft);
//        right.addHighDigit(firstRight);
//
//
//    }
//}
