package edu.majority_element_moore_voting_algorithm;

/**
 * Given an array nums of size n, return the majority element.
 *
 * The majority element is the element that appears more than ⌊n / 2⌋ times.
 * You may assume that the majority element always exists in the array.
 */
public class MajorityElement {
    // Moore's Voting Algorithm:
    public int majorityElement(final int[] nums) {
        int count = 0;
        int candidate = nums[0];
        for (int val: nums) {
            if (candidate == val) {
                count++;
            } else {
                count--;
                if (count == 0) {
                    candidate = val;
                    count = 1;
                }
            }
        }
        return candidate;
    }
}
