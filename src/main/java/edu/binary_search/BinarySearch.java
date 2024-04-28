package edu.binary_search;

public class BinarySearch {
    public int searchInsert(final int[] nums, final int target) {
        //     int result = Arrays.binarySearch(nums, target);
        //     if (result >= 0) {
        //         return result;
        //     } else {
        //         return - result - 1;
        //     }
        // }

        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (target <= nums[0]) {
            return 0;
        }
        if (target > nums[nums.length - 1]) {
            return nums.length;
        }
        int x = 0;
        int y = nums.length;
        while (true) {
            int middle = (x + y) / 2;
            int median = nums[middle];
            if (median == target) {
                return middle;
            } else if (median < target) {
                if (x == middle) {
                    return x + 1;
                }
                x = middle;
            } else {
                y = middle;
            }
        }
    }

}
