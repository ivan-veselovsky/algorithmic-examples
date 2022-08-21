package edu.maxproductsubarray;

import java.util.Arrays;

import static java.lang.Math.max;

/**
 *  https://leetcode.com/problems/maximum-product-subarray/
 *
 *  Given an integer array nums, find a contiguous non-empty subarray within the array that has the largest product, and return the product.
 *
 * The test cases are generated so that the answer will fit in a 32-bit integer.
 *
 * A subarray is a contiguous subsequence of the array.
 *
 * Example 1:
 *
 * Input: nums = [2,3,-2,4]
 * Output: 6
 * Explanation: [2,3] has the largest product 6.
 * Example 2:
 *
 * Input: nums = [-2,0,-1]
 * Output: 0
 * Explanation: The result cannot be 2, because [-2,-1] is not a subarray.
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 2 * 104
 * -10 <= nums[i] <= 10
 * The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
 */
public class Solution {
    //   0) (remove all 1s) -- actually not necessary in O(n) algorithm.
    ///  1) split the array by subarrays on zeroes;
    //   2) just aggregate (multiply) all contiguous positive sequences;
    //   3) same for all *even* contiguous negative sequences;
    //   4) *odd* nagative sequences: max( 1st nagative + all others , all + lastNegative); + additional optimization here:
    //        the negative "middle" between the 1st and the last negative can be calculated only once;
    public static int maxProduct(int[] nums) {
        return maxProductOneFree(nums);
    }

    private static int maxProductOneFree(final int[] nums) {
        int maxCollector = Integer.MIN_VALUE;

        int prevZeroIndex = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                maxCollector = max(maxCollector, 0);
                if (i > prevZeroIndex + 1) {
                    int max = maxProductZeroFree(nums, prevZeroIndex + 1, i);
                    maxCollector = max(maxCollector, max);
                }
                prevZeroIndex = i;
            }
        }

        if (prevZeroIndex < nums.length - 1) {
            // process tail:
            int maxTail = maxProductZeroFree(nums, prevZeroIndex + 1, nums.length);
            maxCollector = max(maxCollector, maxTail);
        }

        return maxCollector;
    }

    private static int maxProductZeroFree(final int[] nums, int startInclusive, int endExclusive) {
        if (startInclusive >= endExclusive) {
            return Integer.MIN_VALUE; // allow empty range
        }
        int negativeCount = (int)Arrays.stream(nums, startInclusive, endExclusive).filter(x -> x < 0).count();
        if (negativeCount % 2 == 0) {
            return maxProductOnlyAbsoluteValues(nums, startInclusive, endExclusive);
        } else {
            // traverse 1st and last negative numbers
            int firstNegative = -1;
            for (int i = startInclusive; i<endExclusive; i++) {
                if (nums[i] < 0) {
                    firstNegative = i;
                    break;
                }
            }
            assert firstNegative >= startInclusive;
            int lastNegative = -1;
            for (int i=endExclusive - 1; i>=firstNegative; i--) {
                if (nums[i] < 0) {
                    lastNegative = i;
                    break;
                }
            }
            assert lastNegative >= firstNegative;

            int maxZeroToLastNegative = maxProductOnlyAbsoluteValues(nums, startInclusive, lastNegative);
            int result1 = max(maxZeroToLastNegative, nums[lastNegative]);

            int maxFirstNegativeToEnd = maxProductOnlyAbsoluteValues(nums, firstNegative + 1, endExclusive);
            int result2 = max(maxFirstNegativeToEnd, nums[firstNegative]);

            return max(result1, result2);
        }
    }

    private static int maxProductOnlyAbsoluteValues(final int[] nums, int startInclusive, int endExclusive) {
        if (startInclusive >= endExclusive) {
            return Integer.MIN_VALUE; // allow empty range
        }
        int product = 1;
        for (int i=startInclusive; i <endExclusive; i++ ) {
            product *= nums[i];
        }
        assert product > 0;
        return product;
    }

//    private static int maxProductOneFree(final int[] nums) {
//        int[] partialProducts = new int[nums.length];
//
//        int maxProduct = Integer.MIN_VALUE;
//
//        for (int i = 0; i<nums.length ; i++) {
//            fillForNextLevel(i, nums, partialProducts);
//
//            int maxProductForLevel = getMaxValue(partialProducts, nums.length - i);
//            if (maxProductForLevel > maxProduct) {
//                maxProduct = maxProductForLevel;
//            }
//
//            //System.out.println(i + " -> " + Arrays.toString(partialProducts) + "   max = " + maxProductForLevel);
//        }
//
//        return maxProduct;
//    }

//    private static int getMaxValue(int[] array, int maxIndexExclusive) {
//        return Arrays.stream(array, 0, maxIndexExclusive).max().getAsInt();
//    }
//
//    private static void fillForNextLevel(int levelIndex, int[] nums, int[] partialProducts) {
//        if (levelIndex == 0) {
//            for (int i=0; i < partialProducts.length; i++) {
//                partialProducts[i] = nums[i];
//            }
//        } else {
//            for (int i=0; i < partialProducts.length - levelIndex; i++) {
//                partialProducts[i] = nums[i] * partialProducts[i + 1];
//            }
//        }
//    }
}
