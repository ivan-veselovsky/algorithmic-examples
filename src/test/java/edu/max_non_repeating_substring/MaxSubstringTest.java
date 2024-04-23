package edu.max_non_repeating_substring;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class MaxSubstringTest {

    @Test
    void case1() {
        String s = "abcabcbb";
        then(new MaxSubstring().lengthOfLongestSubstring(s)).isEqualTo(3);
    }

    @Test
    void case2() {
        String s = "bbbbb";
        then(new MaxSubstring().lengthOfLongestSubstring(s)).isEqualTo(1);
    }

    @Test
    void case3() {
        String s = "pwwkew";
        then(new MaxSubstring().lengthOfLongestSubstring(s)).isEqualTo(3);
    }

}