package edu.min_missing_number;

public class MinMissingNumber {
    public int firstMissingPositive(final int[] nums) {
        // 1.
        boolean oneEncountered = false;
        for (int i=0; i<nums.length; i++) {
            final int v = nums[i];
            if (v < 0 || v > nums.length) {
                nums[i] = 0;
            } else if (v == 1) {
                oneEncountered = true;
            }
        }
        if (!oneEncountered) {
            return 1;
        }
        // 2.
        for (int i=0; i<nums.length; i++) {
            final int v = nums[i];
            if (v != 0) {
                int index = Math.abs(v) - 1;
                int v2 = nums[index];
                if (v2 == 0) {
                    nums[index] = -1;
                } else if (v2 > 0) {
                    nums[index] = -v2; // invert the number using it as a counter
                }
            }
        }
        // 3.
        for (int i=0; i<nums.length; i++) {
            final int v = nums[i];
            if (v >= 0) {
                return i + 1;
            }
        }
        return nums.length + 1;
    }

}
