package edu.remove_duplicates_sorted;

public class RemoveDuplicatesSortedII {

    public int removeDuplicates(final int[] nums) {
        int writePosition = 0;
        int x = 0;

        while (true) {
            int count = countSame(nums, x);

            if (count == 1) {
                fill(nums, writePosition, 1, nums[x]);
                writePosition++;
            } else {
                // 2 or more:
                fill(nums, writePosition, 2, nums[x]);
                writePosition +=2;
            }

            x += count;

            if (x >= nums.length) {
                return writePosition;
            }
        }
    }

    private int countSame(int[] nums, int fromIndex) {
        int val = nums[fromIndex];
        for (int i=fromIndex; i<nums.length; i++) {
            if (nums[i] != val) {
                return (i - fromIndex);
            }
        }
        return (nums.length - fromIndex);
    }

    private void fill(int[] nums, int from, int count, int value) {
        for (int w=0; w < count; w++) {
            nums[from + w] = value;
        }
    }

}
