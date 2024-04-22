package edu.sum_3;

import org.junit.jupiter.api.Test;

import static edu.sum_3.Sum3B.*;
import static org.assertj.core.api.BDDAssertions.then;

public class StableBinarySearchTest {
    @Test
    void test_binary_search() {
        int[] nums = new int[] { -10, -8, -5, -3, -2, -1, -1, 0, 2, 4, 6, 11, 100};

        Range range = Range.of(0, nums.length);
        HalfIndexAndCount actual = stableBinarySearch(nums, range, -1);

        then(actual.leftmostKeyIndex()).isEqualTo(5);
        then(actual.keyCount()).isEqualTo(2);

        range = Range.of(0, 2);
        actual = stableBinarySearch(nums, range, -1);
        then(actual.leftmostKeyIndex()).isEqualTo(1);
        then(actual.keyCount()).isEqualTo(0);

        range = Range.of(2, 3);
        actual = stableBinarySearch(nums, range, -4);
        then(actual.leftmostKeyIndex()).isEqualTo(2);
        then(actual.keyCount()).isEqualTo(0);

        range = Range.of(6, 7);
        actual = stableBinarySearch(nums, range, -1);
        then(actual.leftmostKeyIndex()).isEqualTo(6);
        then(actual.keyCount()).isEqualTo(1);

        range = Range.of(5, 7);
        actual = stableBinarySearch(nums, range, -1);
        then(actual.leftmostKeyIndex()).isEqualTo(5);
        then(actual.keyCount()).isEqualTo(2);

        range = Range.of(6, 8);
        actual = stableBinarySearch(nums, range, -1);
        then(actual.leftmostKeyIndex()).isEqualTo(6);
        then(actual.keyCount()).isEqualTo(1);

        range = Range.of(7, 9);
        actual = stableBinarySearch(nums, range, -1);
        then(actual.leftmostKeyIndex()).isEqualTo(6);
        then(actual.keyCount()).isEqualTo(0);
    }
}
