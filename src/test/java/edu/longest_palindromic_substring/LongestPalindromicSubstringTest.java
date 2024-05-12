package edu.longest_palindromic_substring;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class LongestPalindromicSubstringTest {

    @Disabled // TODO: support odd palindromity also.
    @Test
    void longestPalindrome0() {
        String s = "babad";
        String longestPalindrome = new LongestPalindromicSubstring().longestPalindrome(s);
        then(longestPalindrome).isEqualTo("bab");
    }

    @Test
    void longestPalindrome1() {
        String s = "aebbefehnq";
        String longestPalindrome = new LongestPalindromicSubstring().longestPalindrome(s);
        then(longestPalindrome).isEqualTo("ebbe");
    }

    @Test
    void longestPalindrome2() {
        String s = "aebbefehnqqnhp";
        String longestPalindrome = new LongestPalindromicSubstring().longestPalindrome(s);
        then(longestPalindrome).isEqualTo("hnqqnh");
    }

}