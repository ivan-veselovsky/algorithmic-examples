package edu.merge_sorted_arrays;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class MergeSortedArraysTest {

    @Test
    void merge() {
        int[] nums1 = new int[] { 2,0 };
        int m = 1;
        int[] nums2 = new int[] { 1 };
        int n = 1;
        new MergeSortedArrays().merge(nums1, m, nums2, n);

        then(nums1).containsExactly(1, 2);
    }
}