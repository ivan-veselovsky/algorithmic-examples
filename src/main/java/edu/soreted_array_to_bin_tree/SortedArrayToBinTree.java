package edu.soreted_array_to_bin_tree;

import edu.common.TreeNode;

public class SortedArrayToBinTree {
    private int[] nums;

    public TreeNode sortedArrayToBST(int[] nums) {
        this.nums = nums;

        return buildPart(0, nums.length);
    }

    TreeNode buildPart(int startInclusive, int endExclusive) {
        if (startInclusive >= endExclusive || startInclusive >= nums.length) {
            return null;
        }
        int medianIndex = (startInclusive + endExclusive) / 2;
        TreeNode median = new TreeNode(nums[medianIndex]);
        median.left(buildPart(startInclusive, medianIndex));
        median.right(buildPart(medianIndex + 1, endExclusive));
        return median;
    }
}
