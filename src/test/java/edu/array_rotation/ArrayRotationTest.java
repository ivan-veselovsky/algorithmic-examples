package edu.array_rotation;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.BDDAssertions.then;

class ArrayRotationTest {

    @Test
    void test0() {
        int[] arr = new int[] {1,2,3,4,5,6,7};
        new ArrayRotation().rotate(arr, 3);
        then(arr).containsExactly(5,6,7, 1,2,3,4);
    }

    @Test
    void test2() {
        int[] arr = new int[] { -1, -100, 3, 99};
        new ArrayRotation().rotate(arr, 2);
        then(arr).containsExactly(3, 99, -1, -100);
    }

    @Test
    void test3() {
        int[] arr = new int[] { -1, -100, 3, 99};
        new ArrayRotation().rotate(arr, 3);
        then(arr).containsExactly(-100, 3, 99, -1);
    }

    @Test
    void test4() {
        int[] arr = new int[] { 1,2,3,4,5,6 };
        new ArrayRotation().rotate(arr, 4);
        then(arr).containsExactly(3,4,5,6, 1,2);
    }

    @Test
    void test5() {
        int[] arr0 = new int[] { 1,2,3,4,5,6 };

        int[] arr = Arrays.copyOf(arr0, 6);
        new ArrayRotation().rotate(arr, 0);
        then(arr).containsExactly(arr);

        arr = Arrays.copyOf(arr0, 6);
        new ArrayRotation().rotate(arr, 1);
        then(arr).containsExactly(6, 1, 2,3,4,5);

        arr = Arrays.copyOf(arr0, 6);
        new ArrayRotation().rotate(arr, 2);
        then(arr).containsExactly(5, 6, 1, 2, 3, 4);

        arr = Arrays.copyOf(arr0, 6);
        new ArrayRotation().rotate(arr, 3);
        then(arr).containsExactly(4,5,6, 1,2,3);

        arr = Arrays.copyOf(arr0, 6);
        new ArrayRotation().rotate(arr, 4);
        then(arr).containsExactly(3, 4, 5,6,1,2);

        arr = Arrays.copyOf(arr0, 6);
        new ArrayRotation().rotate(arr, 5);
        then(arr).containsExactly(2,3,4,5, 6, 1);

        arr = Arrays.copyOf(arr0, 6);
        new ArrayRotation().rotate(arr, 6);
        then(arr).containsExactly(arr0);
    }

    @Test
    void test7() {
        int[] arr = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54};
        new ArrayRotation().rotate(arr, 45);
        then(arr).containsExactly(10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,1,2,3,4,5,6,7,8,9);
    }
}