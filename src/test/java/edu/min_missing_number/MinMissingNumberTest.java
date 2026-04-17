package edu.min_missing_number;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class MinMissingNumberTest {

    @Test
    void case1() {
        int actual = new MinMissingNumber().firstMissingPositive(new int[] {3,4,-1,1});
        then(actual).isEqualTo(2);
    }

    @Test
    void case2() {
        int actual = new MinMissingNumber().firstMissingPositive(new int[] {7,8,9,11,12});
        then(actual).isEqualTo(1);
    }

    @Test
    void case3() {
        int actual = new MinMissingNumber().firstMissingPositive(new int[] {1});
        then(actual).isEqualTo(2);
    }

    @Test
    void case4() {
        int actual = new MinMissingNumber().firstMissingPositive(new int[] {0,1,2});
        then(actual).isEqualTo(3);
    }

    @Test
    void case5() {
        int actual = new MinMissingNumber().firstMissingPositive(new int[] {3,4,0,2});
        then(actual).isEqualTo(1);
    }
}