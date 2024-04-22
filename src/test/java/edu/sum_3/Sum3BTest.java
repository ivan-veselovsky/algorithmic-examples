package edu.sum_3;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static edu.sum_3.Sum3B.*;

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

    @Test
    void case5() {
        int[] nums = new int[] { -4,-2,-2,-2,0,1,2,2,2,3,3,4,4,6,6 };
        List<List<Integer>> actual = new Sum3B().threeSum(nums);
        then(actual).containsExactly(
                List.of(-4, -2, 6 ),
                List.of(-4, 0, 4),
                List.of(-4,1,3),
                List.of(-4,2,2),
                List.of(-2,-2,4),
                List.of(-2,0,2)
                // [-4,-2,6],[-4,0,4],[],[-4,2,2],[-2,-2,4],[-2,0,2]
        );
    }

    @Test
    void case5_simplified() {
        int[] nums = new int[] { -4,-2,-2,-2,0,1,2,2,2,3 };
        List<List<Integer>> actual = new Sum3B().threeSum(nums);
        then(actual).containsExactly(
                List.of(-4,1,3),
                List.of(-4,2,2),
                List.of(-2,0,2)
        );
    }

    @Test
    void shrink_all() {
        int[] nums = new int[] { -4,-2,-2,-2,0,1,2,2,2,3,3,4,4,6,6 };
        Range r = Range.of(0, nums.length);
        then(r.shrinkRightAll(q -> nums[q]).last()).isEqualTo(12);
        then(r.shrinkLeftAll(q -> nums[q]).first()).isEqualTo(1);

        r = Range.ofInclusive(1, 8);
        then(r.shrinkRightAll(q -> nums[q]).last()).isEqualTo(5);
        then(r.shrinkLeftAll(q -> nums[q]).first()).isEqualTo(4);

        int[] nums1 = new int[] { 1 };
        r = Range.of(0, nums1.length);
        then(r.shrinkRightAll(q -> nums1[q]).length()).isEqualTo(0);
        then(r.shrinkLeftAll(q -> nums1[q]).length()).isEqualTo(0);

        int[] nums2 = new int[] { 1, 1 };
        r = Range.of(0, nums2.length);
        then(r.shrinkRightAll(q -> nums2[q]).length()).isEqualTo(0);
        then(r.shrinkLeftAll(q -> nums2[q]).length()).isEqualTo(0);
    }
}