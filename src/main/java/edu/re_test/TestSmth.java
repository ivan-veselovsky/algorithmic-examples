package edu.re_test;

import java.util.HashSet;
import java.util.Set;

public class TestSmth {
//    Engineering Int
//    Given an integer array nums and an integer k,
//    return true if there are two distinct indices i and j in the array such that
//    nums[i] == nums[j] and abs(i - j) <= k.
//    Input: nums = [1,2,3,1], k = 3
//    Output: true
//    Input: nums = [1,2,3,1,2,3], k = 2
//    Output: false

    // Runtime: O(n)
    // Memory:  O(k)
    public boolean solve(int[] nums, int k) {
        final Set<Integer> window = new HashSet<>(k);
        for (int i = -k; i < (nums.length - k); i++) {
            if (!window.add(nums[i + k])) {
                return true;
            }
            if (i >= 0) {
                boolean rm = window.remove(nums[i]);
                assert rm;
            }
        }
        return false;
    }
}
