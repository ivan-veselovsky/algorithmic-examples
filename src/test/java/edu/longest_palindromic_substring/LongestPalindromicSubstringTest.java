package edu.longest_palindromic_substring;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class LongestPalindromicSubstringTest {

    @Test
    void longestPalindrome0() {
        String s = "babad";
        String longestPalindrome = new LongestPalindromicSubstring().longestPalindrome(s);
        then(longestPalindrome).isEqualTo("aba");
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

    @Test
    void longestPalindrome3() {
        String s = "xxxxxxxxzxx";
        String longestPalindrome = new LongestPalindromicSubstring().longestPalindrome(s);
        then(longestPalindrome).isEqualTo("xxxxxxxx");
    }
    @Test
    void longestPalindrome3_right() {
        String s = "xzxxxxxxxx";
        String longestPalindrome = new LongestPalindromicSubstring().longestPalindrome(s);
        then(longestPalindrome).isEqualTo("xxxxxxxx");
    }

    @Test
    void longestPalindrome4() {
        String s = new StringBuilder().repeat('a', 50).toString();
        String longestPalindrome = new LongestPalindromicSubstring().longestPalindrome(s);
        then(longestPalindrome).isEqualTo(s);
    }
    @Test
    void longestPalindrome4_right() {
        String s = "axzxxxx";
        String longestPalindrome = new LongestPalindromicSubstring().longestPalindrome(s);
        then(longestPalindrome).isEqualTo("xxxx");
    }
    @Test
    void case_5() {
        String s = "xxxxaxz";
        String longestPalindrome = new LongestPalindromicSubstring().longestPalindrome(s);
        then(longestPalindrome).isEqualTo("xxxx");
    }
    @Test
    void case_6() {
        String s = "abxx";
        String longestPalindrome = new LongestPalindromicSubstring().longestPalindrome(s);
        then(longestPalindrome).isEqualTo("xx");
    }
    @Test
    void case_7() {
        String s = "xxab";
        String longestPalindrome = new LongestPalindromicSubstring().longestPalindrome(s);
        then(longestPalindrome).isEqualTo("xx");
    }
    @Test
    void case_8() {
        String s = "axxb";
        String longestPalindrome = new LongestPalindromicSubstring().longestPalindrome(s);
        then(longestPalindrome).isEqualTo("xx");
    }

    @Test
    void case_9() {
        String s = "axxxb";
        String longestPalindrome = new LongestPalindromicSubstring().longestPalindrome(s);
        then(longestPalindrome).isEqualTo("xxx");
    }
    @Test
    void case_10() {
        String s = "abxxx";
        String longestPalindrome = new LongestPalindromicSubstring().longestPalindrome(s);
        then(longestPalindrome).isEqualTo("xxx");
    }
    @Test
    void case_11() {
        String s = "xxxab";
        String longestPalindrome = new LongestPalindromicSubstring().longestPalindrome(s);
        then(longestPalindrome).isEqualTo("xxx");
    }
    @Test
    void case_12() {
        String s = "xxxabxxxxx";
        String longestPalindrome = new LongestPalindromicSubstring().longestPalindrome(s);
        then(longestPalindrome).isEqualTo("xxxxx");
    }
    @Test
    void case_13() {
        String s = "xxabxxxxx";
        String longestPalindrome = new LongestPalindromicSubstring().longestPalindrome(s);
        then(longestPalindrome).isEqualTo("xxxxx");
    }
    @Test
    void case_14() {
        String s = "xxabxxxxxz";
        String longestPalindrome = new LongestPalindromicSubstring().longestPalindrome(s);
        then(longestPalindrome).isEqualTo("xxxxx");
    }

    @Test
    void case_15() {
        String s = "abcda";
        String longestPalindrome = new LongestPalindromicSubstring().longestPalindrome(s);
        then(longestPalindrome).isEqualTo("a");
    }

    @Disabled("TODO: fix it!")
    @Test
    void case_16() {
        String s = "321012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210123210012321001232100123210123";
        String longestPalindrome = new LongestPalindromicSubstring().longestPalindrome(s);
        String expected = "321012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210123";
        then(longestPalindrome).hasSize(expected.length());
        then(longestPalindrome).isEqualTo(expected);
    }
}