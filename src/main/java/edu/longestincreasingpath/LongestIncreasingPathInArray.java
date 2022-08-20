package edu.longestincreasingpath;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * https://leetcode.com/problems/longest-increasing-subsequence/
 *
 * Given an integer array nums, return the length of the longest strictly increasing subsequence.
 *
 * A subsequence is a sequence that can be derived from an array by deleting some or no elements without changing the order of the remaining elements. For example, [3,6,2,7] is a subsequence of the array [0,3,1,6,2,2,7].
 *
 *
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
        final NavigableMap<Integer, Integer> map = new TreeMap<>();

        for (final int v : nums) {
            Map.Entry<Integer, Integer> higherEntry = map.higherEntry(v);
            Map.Entry<Integer, Integer> floorEntry = map.floorEntry(v);

            if (higherEntry == null) {
                // add upper entry with "v + 1"
                if (floorEntry == null) {
                    map.put(v, 1);
                } else if (v > floorEntry.getKey()) {
                    map.put(v, floorEntry.getValue() + 1);
                }
            } else {
                // "lower" the higherEntry:
                if (floorEntry == null || floorEntry.getKey() < v) {
                    map.remove(higherEntry.getKey());
                    map.put(v, higherEntry.getValue());
                }
            }
//            map.entrySet().forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue()));
//            System.out.println("---------------");
        }

        return map.lastEntry().getValue();
    }
}
