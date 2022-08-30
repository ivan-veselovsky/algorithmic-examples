package edu.stringpalindrome;

import org.junit.jupiter.api.Test;

import static edu.stringpalindrome.NumberOfSwapsToBuildStringPalindrome.minSwapsRequired;
import static org.assertj.core.api.BDDAssertions.then;

class NumberOfSwapsToBuildStringPalindromeTest {

    static String removeUnderscores(String s) {
        return s.replaceAll("_", "");
    }

    @Test
    void should_return_minus_one_on_impossible() {
        int min = minSwapsRequired(removeUnderscores("10_00"));
        then(min).isEqualTo(-1);
        min = minSwapsRequired(removeUnderscores("10_11"));
        then(min).isEqualTo(-1);
    }

    @Test
    void should_return_zero_on_palindrome() {
        int min = minSwapsRequired(removeUnderscores("00_00"));
        then(min).isEqualTo(0);
        min = minSwapsRequired(removeUnderscores("111_111"));
        then(min).isEqualTo(0);
    }

    @Test
    void even_bit_diff() {
        int min = minSwapsRequired(removeUnderscores("101_000"));
        then(min).isEqualTo(1);
    }

    @Test
    void odd_bits_odd_diff_count() {
        int min = minSwapsRequired(removeUnderscores("1_0_0"));
        then(min).isEqualTo(1);
        min = minSwapsRequired(removeUnderscores("00_0_10"));
        then(min).isEqualTo(1);
        min = minSwapsRequired(removeUnderscores("00_1_10")); // odd, use middle
        then(min).isEqualTo(1);
        min = minSwapsRequired(removeUnderscores("000_0_111"));
        then(min).isEqualTo(2);
        min = minSwapsRequired(removeUnderscores("010_0_101"));
        then(min).isEqualTo(2);
        min = minSwapsRequired(removeUnderscores("1010_0_1010"));
        then(min).isEqualTo(2);
        min = minSwapsRequired(removeUnderscores("0000_0_1111"));
        then(min).isEqualTo(2);
        min = minSwapsRequired(removeUnderscores("00000_0_11111"));
        then(min).isEqualTo(3);
    }

    @Test
    void odd_bits_even_bits_count() {
        int min = minSwapsRequired(removeUnderscores("00_1_11"));
        then(min).isEqualTo(1);
        min = minSwapsRequired(removeUnderscores("00_0_11"));
        then(min).isEqualTo(1);
        min = minSwapsRequired(removeUnderscores("10_1_10"));
        then(min).isEqualTo(1);
    }
}