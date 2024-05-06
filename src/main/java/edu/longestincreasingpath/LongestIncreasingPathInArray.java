package edu.longestincreasingpath;

import java.util.*;

/**
 * https://leetcode.com/problems/longest-increasing-subsequence/
 * Given an integer array nums, return the length of the longest strictly increasing subsequence.
 * A subsequence is a sequence that can be derived from an array by deleting some or no elements
 * without changing the order of the remaining elements. For example, [3,6,2,7] is a subsequence of the array [0,3,1,6,2,2,7].
 *
 * Example 1:
 *
 * Input: nums = [10,9,2,5,3,7,101,18]
 * Output: 4
 * Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
 * Example 2:
 *
 * Input: nums = [0,1,0,3,2,3]
 * Output: 4
 * Example 3:
 *
 * Input: nums = [7,7,7,7,7,7,7]
 * Output: 1
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 2500
 * -104 <= nums[i] <= 104
 */
public class LongestIncreasingPathInArray {
    public int lengthOfLIS(int[] nums) {
        return arrayVersion(nums);
        //return treeSetVersion(nums);
    }

    /** Very short TreeSet-based version: */
    int treeSetVersion(int[] nums) {
        final NavigableSet<Integer> set = new TreeSet<>();
        for (final int v : nums) {
            if (set.add(v)) {
                Integer higher = set.higher(v); // higher > v
                if (higher != null) {
                    set.remove(higher);
                }
            }
        }
        return set.size();
    }

    /** Very fast array-based version. It implements exactly the same logic, but in a faster way,
     * as it avoids tree re-balancing.
     *  Array maintains ascending sorted numbers and the max element.
     *  The array can only grow.
     *  If a new element is greater than max, it is written to the position after the last, size++.
     *  Otherwise, the new element's position is searched with binary search, and existing
     *  element is replaced with the new one (element is "lowered").
     */
    int arrayVersion(int[] nums) {
        Arr arr = new Arr(nums.length);
        for (final int v : nums) {
            arr.push(v);
        }
        return arr.size();
    }

    static class Arr {
        final int[] array;
        int size;
        int max;
        Arr(int length) {
            array = new int[length];
        }
        void push(int v) {
            if (size == 0 || v > max) {
                array[size] = v;
                size++;
                max = v;
            } else {
                int pos = Arrays.binarySearch(array, 0, size, v);
                if (pos < 0) {
                    pos = - pos - 1;
                }
                if (v < array[pos]) {
                    array[pos] = v;
                    if (pos == size - 1) {
                        max = v;
                    }
                }
            }
        }
        int size() {
            return size;
        }
    }
}
