package edu.binary_search;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class BinarySearchTest {

    @Test
    void case1() {
        int[] array = {1,3,5,6};
        then(new BinarySearch().searchInsert(array, 5)).isEqualTo(2);
    }
    @Test
    void case1a() {
        int[] array = {1,3,5,6};
        then(new BinarySearch().searchInsert(array, 1)).isEqualTo(0);
    }
    @Test
    void case2() {
        int[] array = {1,3,5,6};
        then(new BinarySearch().searchInsert(array, 2)).isEqualTo(1);
    }
    @Test
    void case2a() {
        int[] array = {1,3,5,6};
        then(new BinarySearch().searchInsert(array, 0)).isEqualTo(0);
    }
    @Test
    void case3() {
        int[] array = {1,3,5,6};
        then(new BinarySearch().searchInsert(array, 7)).isEqualTo(4);
    }
    @Test
    void case4() {
        int[] array = {1,3,5,6};
        then(new BinarySearch().searchInsert(array, 6)).isEqualTo(3);
    }
}