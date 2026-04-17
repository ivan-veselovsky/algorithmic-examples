package edu.median_of_two_sorted_arrays;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class MedianOfTwoSortedArraysTest {

    @Test
    void case0() {
        int[] a = {1, 2, 3, 4, 6, 8};
        int[] b = {5, 7, 9, 10, 11, 12};
        then(new MedianOfTwoSortedArrays().findMedianSortedArrays(a, b)).isEqualTo(6.5);
    }
    @Test
    void case1() {
        int[] a = {1, 3};
        int[] b = {2};
        then(new MedianOfTwoSortedArrays().findMedianSortedArrays(a, b)).isEqualTo(2.);
    }
    @Test
    void case2() {
        int[] a = {1, 2};
        int[] b = {3, 4};
        then(new MedianOfTwoSortedArrays().findMedianSortedArrays(a, b)).isEqualTo(2.5);
    }
    @Test
    void case3() {
        int[] a = {1, 3};
        int[] b = {};
        then(new MedianOfTwoSortedArrays().findMedianSortedArrays(a, b)).isEqualTo(2.);
    }
    @Test
    void case4() {
        int[] a = {};
        int[] b = {1, 3};
        then(new MedianOfTwoSortedArrays().findMedianSortedArrays(a, b)).isEqualTo(2.);
    }
    @Test
    void case5() {
        int[] a = {1, 3};
        int[] b = {2, 7};
        then(new MedianOfTwoSortedArrays().findMedianSortedArrays(a, b)).isEqualTo(2.5);
    }

    @Test
    void case6() {
        int[] a = {0,0,0,0,0};
        int[] b = {-1,0,0,0,0,0,1};
        then(new MedianOfTwoSortedArrays().findMedianSortedArrays(a, b)).isEqualTo(0.);
    }

    @Test
    void case7() {
        int[] a = {3};
        int[] b = {-2, -1};
        then(new MedianOfTwoSortedArrays().findMedianSortedArrays(a, b)).isEqualTo(-1.);
    }
}