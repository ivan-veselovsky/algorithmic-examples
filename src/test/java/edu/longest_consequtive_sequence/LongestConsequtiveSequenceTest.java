package edu.longest_consequtive_sequence;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class LongestConsequtiveSequenceTest {

    @Test
    void case1() {
        int[] nums = new int[] { 100,4,200,1,3,2 };
        int result = new LongestConsequtiveSequence().longestConsecutive(nums);
        then(result).isEqualTo(4);
    }

    @Test
    void case2() {
        int[] nums = new int[] { 0,3,7,2,5,8,4,6,0,1 };
        int result = new LongestConsequtiveSequence().longestConsecutive(nums);
        then(result).isEqualTo(9);
    }

    @Test
    void case_nagative() {
        int[] nums = new int[] { 0, -1 };
        int result = new LongestConsequtiveSequence().longestConsecutive(nums);
        then(result).isEqualTo(2 );
    }

    @Test
    void case_nagative_2() {
        int[] nums = new int[] { 0, -1 , 1, -2, 3, -4, 5, -6};
        int result = new LongestConsequtiveSequence().longestConsecutive(nums);
        then(result).isEqualTo(4);
    }

    @Test
    void repeated_value() {
        int[] nums = new int[] { 1, 2, 0 ,1 };
        int result = new LongestConsequtiveSequence().longestConsecutive(nums);
        then(result).isEqualTo(3);
    }

    @Test
    void boundary_value() {
        int[] nums = new int[] { -1_000_000_000, 1, 2, 3, 9, 1_000_000_000 };
        int result = new LongestConsequtiveSequence().longestConsecutive(nums);
        then(result).isEqualTo(3);
    }

    @Test
    void test1() {
        then(new LongestConsequtiveSequence().numberOfDigits(0)).isEqualTo(0);
        then(new LongestConsequtiveSequence().numberOfDigits(5)).isEqualTo(1);
        then(new LongestConsequtiveSequence().numberOfDigits(15)).isEqualTo(2);
    }

    @Test
    void test2() {
        then(new LongestConsequtiveSequence().numberOfDigits(1_000_000_000)).isEqualTo(10);
        then(new LongestConsequtiveSequence().numberOfDigits(-1_000_000_000)).isEqualTo(10);
    }
}