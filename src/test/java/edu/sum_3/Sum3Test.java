//package edu.sum_3;
//
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static edu.sum_3.Sum3.stableBinarySearch;
//import static org.assertj.core.api.BDDAssertions.then;
//
//class Sum3Test {
//
//    @Test
//    void test_binary_search() {
//        int[] nums = new int[] { -10, -8, -5, -3, -2, -1, -1, 0, 2, 4, 6, 11, 100};
//
//        Sum3.IndexAndCount index_of_key_y = stableBinarySearch(nums, 0, nums.length, -1);
//
//        then(index_of_key_y.leftmostKeyIndex()).isEqualTo(5);
//        then(index_of_key_y.keyCount()).isEqualTo(2);
//
//        index_of_key_y = stableBinarySearch(nums, 0, 2, -1);
//        then(index_of_key_y.realOrAssumedIndex()).isEqualTo(2);
//        then(index_of_key_y.leftmostKeyIndex()).isEqualTo(-3);
//        then(index_of_key_y.keyCount()).isEqualTo(0);
//
//        index_of_key_y = stableBinarySearch(nums, 2, 3, -4);
//        then(index_of_key_y.realOrAssumedIndex()).isEqualTo(3);
//        then(index_of_key_y.leftmostKeyIndex()).isEqualTo(-4);
//        then(index_of_key_y.keyCount()).isEqualTo(0);
//
//        index_of_key_y = stableBinarySearch(nums, 6, 7, -1);
//        then(index_of_key_y.realOrAssumedIndex()).isEqualTo(6);
//        then(index_of_key_y.leftmostKeyIndex()).isEqualTo(6);
//        then(index_of_key_y.keyCount()).isEqualTo(1);
//
//        index_of_key_y = stableBinarySearch(nums, 5, 7, -1);
//        then(index_of_key_y.realOrAssumedIndex()).isEqualTo(5);
//        then(index_of_key_y.leftmostKeyIndex()).isEqualTo(5);
//        then(index_of_key_y.keyCount()).isEqualTo(2);
//
//        index_of_key_y = stableBinarySearch(nums, 6, 8, -1);
//        then(index_of_key_y.realOrAssumedIndex()).isEqualTo(6);
//        then(index_of_key_y.leftmostKeyIndex()).isEqualTo(6);
//        then(index_of_key_y.keyCount()).isEqualTo(1);
//
//        index_of_key_y = stableBinarySearch(nums, 7, 9, -1);
//        then(index_of_key_y.realOrAssumedIndex()).isEqualTo(7);
//        then(index_of_key_y.leftmostKeyIndex()).isEqualTo(-8);
//        then(index_of_key_y.keyCount()).isEqualTo(0);
//    }
//
//    @Test
//    void case1() {
//        int[] nums = new int[] { -1, 0, 1, 2, -1, -4 };
//        List<List<Integer>> actual = new Sum3().threeSum(nums);
//        then(actual).containsExactly(
//                List.of(-1, -1, 2),
//                List.of(-1, 0, 1));
//    }
//
//    @Test
//    void case_zeroes() {
//        int[] nums = new int[] { 0, 0, 0 };
//        List<List<Integer>> actual = new Sum3().threeSum(nums);
//        then(actual).containsExactly(
//                List.of(-1, -1, 2),
//                List.of(-1, 0, 1));
//    }
//
//    @Test
//    void case_zeroes2() {
//        int[] nums = new int[] { -1, -2, 0, 0, 0 , 8, 7};
//        List<List<Integer>> actual = new Sum3().threeSum(nums);
//        then(actual).containsExactly(
//                List.of(-1, -1, 2),
//                List.of(-1, 0, 1));
//    }
//
//    @Test
//    void case4() {
//        int[] nums = new int[] { -10, -8, -5, -3, -2, -1, -1, 0, 2, 4, 6, 11, 100};
//        List<List<Integer>> actual = new Sum3().threeSum(nums);
//        then(actual).containsExactly(
//                List.of(-10, -1, 11),
//                List.of(-10, 4, 6),
//                List.of(-8, -3, 11),
//                List.of(-8, 2, 6),
//                List.of(-5, -1, 6),
//                List.of(-3, -1, 4),
//                List.of(-2, 0, 2),
//                List.of(-1, -1, 2)
//        );
//    }
//
//}