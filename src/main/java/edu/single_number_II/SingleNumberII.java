package edu.single_number_II;

/**
 Given an integer array nums where every element appears three times except for one, which appears exactly once.
 Find the single element and return it.
 You must implement a solution with a linear runtime complexity and use only constant extra space.

 Example 1:
 Input: nums = [2,2,3,2]
 Output: 3

 Example 2:
Input: nums = [0,1,0,1,0,1,99]
 Output: 99

 Constraints:
 1 <= nums.length <= 3 * 104
 -231 <= nums[i] <= 231 - 1
 Each element in nums appears exactly three times except for one element which appears once. *
 */
public class SingleNumberII {
    private final int[] counts = new int[32];

    public int singleNumber(int[] nums) {
        for (int n : nums) {
            appendCounts(n);
        }
        int result = 0;
        for (int i=0; i<32; i++) {
            counts[i] %= 3;
            assert counts[i] != 2;
            if (counts[i] != 0) {
                result |= (1 << i);
            }
        }
        return result;
    }

    void appendCounts(int n) {
        for (int i=0; i<32; i++) {
            if (getBit(n, i)) {
                counts[i]++;
            }
        }
    }

    boolean getBit(int n, int bit) {
        return (n & (1 << bit)) != 0;
    }
}
