package edu.sum_3;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

class Sum3BTest {

    @Test
    void case1() {
        int[] nums = new int[] { -1, 0, 1, 2, -1, -4 };
        List<List<Integer>> actual = new Sum3B().threeSum(nums);
        then(actual).containsExactly(
                List.of(-1, -1, 2),
                List.of(-1, 0, 1));
    }

    @Test
    void case2() {
        int[] nums = new int[] { -4, -2, -1, 0, 1, 1, 5 };
        List<List<Integer>> actual = new Sum3B().threeSum(nums);
        then(actual).containsExactly(
                List.of(-4, -1, 5),
                List.of(-2, 1, 1),
                List.of(-1, 0, 1)
        );
    }

    @Test
    void case_zeroes() {
        int[] nums = new int[] { 0, 0, 0 };
        List<List<Integer>> actual = new Sum3B().threeSum(nums);
        then(actual).containsExactly(
                List.of(0, 0, 0));
    }

    @Test
    void case_zeroes2() {
        int[] nums = new int[] { -1, -2, 0, 0, 0, 8, 7};
        List<List<Integer>> actual = new Sum3B().threeSum(nums);
        then(actual).containsExactly(
                List.of(0, 0, 0));
    }

    @Test
    void case4() {
        int[] nums = new int[] { -10, -8, -5, -3, -2, -1, -1, 0, 2, 4, 6, 11, 100 };
        List<List<Integer>> actual = new Sum3B().threeSum(nums);
        then(actual).containsExactly(
                List.of(-10, -1, 11),
                List.of(-10, 4, 6),
                List.of(-8, -3, 11),
                List.of(-8, 2, 6),
                List.of(-5, -1, 6),
                List.of(-3, -1, 4),
                List.of(-2, 0, 2),
                List.of(-1, -1, 2)
        );
    }

    @Test
    void case4_simplified() {
        int[] nums = new int[] {-18, -3, -2, -1, -1, 0, 2, 4, 128};
        List<List<Integer>> actual = new Sum3B().threeSum(nums);
        then(actual).containsExactly(
                List.of(-3, -1, 4),
                List.of(-2, 0, 2),
                List.of(-1, -1, 2)
        );
    }

    @Test
    void case4_simplified_2() {
        int[] nums = new int[] { -5, -3, -2, -1, -1, 0, 2, 4 };
        List<List<Integer>> actual = new Sum3B().threeSum(nums);
        then(actual).containsExactly(
                List.of(-3, -1, 4),
                List.of(-2, 0, 2),
                List.of(-1, -1, 2)
        );
    }
}