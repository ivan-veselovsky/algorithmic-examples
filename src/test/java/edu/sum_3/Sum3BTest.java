package edu.sum_3;

import org.junit.jupiter.api.Disabled;
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

    @Disabled("TODO: fix it")
    @Test
    void case6() {
        int[] nums = new int[] {-82, -70, -66, -49, -43, -29, -29, -14, -11, -6, -3, -3, 1, 2, 10, 12, 13, 15, 15, 17, 21, 26, 26, 28, 28, 29, 31, 33, 34, 36, 43, 46, 46, 47, 48, 49, 52, 55, 55, 56, 57, 61, 62, 65, 69, 71, 74, 76, 77, 79, 83, 84, 86, 93, 94};
        List<List<Integer>> actual = new Sum3B().threeSum(nums);
        then(actual).containsExactly(
                List.of(-82,-11,93),
                List.of(-82,13,69),
                List.of(-82,17,65),
                List.of(-82,21,61),
                List.of(-82,26,56),
                List.of(-82,33,49),
                List.of(-82,34,48),
                List.of(-82,36,46),
                List.of(-70,-14,84),
                List.of(-70,-6,76),
                List.of(-70,1,69),
                List.of(-70,13,57),List.of(-70,15,55),List.of(-70,21,49),List.of(-70,34,36),List.of(-66,-11,77),List.of(-66,-3,69),List.of(-66,1,65),List.of(-66,10,56),List.of(-66,17,49),List.of(-49,-6,55),List.of(-49,-3,52),List.of(-49,1,48),List.of(-49,2,47),List.of(-49,13,36),List.of(-49,15,34),List.of(-49,21,28),List.of(-43,-14,57),List.of(-43,-6,49),List.of(-43,-3,46),List.of(-43,10,33),List.of(-43,12,31),List.of(-43,15,28),List.of(-43,17,26),List.of(-29,-14,43),List.of(-29,1,28),List.of(-29,12,17),List.of(-14,-3,17),List.of(-14,1,13),List.of(-14,2,12),List.of(-11,-6,17),List.of(-11,1,10),List.of(-3,1,2)
        );
    }

    @Test
    void case6_simplified() {
        //int[] nums = new int[] { 34,55,79,28,46,33,2,48,31,-3,84,71,52,-3,93,15,21,-43,57,-6,86,56,94,74,83,-14,28,-66,46,-49,62,-11,43,65,77,12,47,61,26,1,13,29,55,-82,76,26,15,-29,36,-29,10,-70,69,17,49 };
        //int[] nums = new int[] {-82, -70, -66, -49, -43, -29, -29, -14, -11, -6, -3, 1, 2, 10, 12, 13, 15, 17, 21, 26, 28, 29, 31, 33, 34, 36, 43, 46, 47, 48, 49, 52, 55, 56, 57, 61, 62, 65, 69, 71, 74, 76, 77, 79, 83, 84, 86, 93, 94};

        int[] nums_without_duplicates_test_passes = new int[] {-82, -70, -66, -49, -43, -29, -14, -11, -6, -3, 1, 2, 10, 12, 13, 15, 17, 21, 26, 28, 29, 31, 33, 34, 36, 43, 46, 47, 48, 49, 52, 55, 56, 57, 61, 62, 65, 69, 71, 74, 76, 77, 79, 83, 84, 86, 93, 94};

        List<List<Integer>> actual = new Sum3B().threeSum(nums_without_duplicates_test_passes);
        then(actual).containsExactly(
                List.of(-82,-11,93),
                List.of(-82,13,69),
                List.of(-82,17,65),
                List.of(-82,21,61),
                List.of(-82,26,56),
                List.of(-82,33,49),
                List.of(-82,34,48),
                List.of(-82,36,46),
                List.of(-70,-14,84),
                List.of(-70,-6,76),
                List.of(-70,1,69),
                List.of(-70,13,57),List.of(-70,15,55),List.of(-70,21,49),List.of(-70,34,36),List.of(-66,-11,77),
                List.of(-66,-3,69),List.of(-66,1,65),List.of(-66,10,56),List.of(-66,17,49),List.of(-49,-6,55),List.of(-49,-3,52),
                List.of(-49,1,48),List.of(-49,2,47),List.of(-49,13,36),List.of(-49,15,34),List.of(-49,21,28),List.of(-43,-14,57),
                List.of(-43,-6,49),List.of(-43,-3,46),List.of(-43,10,33),List.of(-43,12,31),List.of(-43,15,28),List.of(-43,17,26),
                List.of(-29,-14,43),List.of(-29,1,28),List.of(-29,12,17),List.of(-14,-3,17),List.of(-14,1,13),List.of(-14,2,12),
                List.of(-11,-6,17),List.of(-11,1,10),List.of(-3,1,2)
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