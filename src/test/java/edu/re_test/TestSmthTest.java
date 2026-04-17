package edu.re_test;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class TestSmthTest {

    @Test
    void solve() {
        int[] nums = new int[] {1,2,3,1};
        then(new TestSmth().solve(nums, 3)).isTrue();

        nums = new int[] {1,2,3,1,2,3};
        then(new TestSmth().solve(nums, 2)).isFalse();
    }
}