package edu.can_jump_II_min_number_of_jumps;

public class MinNumberOfJumps {

    public int jump(int[] nums) {
        if (nums.length <= 1) {
            return 0;
        }
        int numberOfSteps = 1;
        int m1 = -1;
        int m2 = 0;
        for (int i=0; i<nums.length; i++) {
            int max = maxPositionReachableInOneStep((m1 + 1), m2, nums);
            if (max >= (nums.length - 1)) {
                return numberOfSteps;
            }
            numberOfSteps++;
            m1 = m2;
            m2 = max;
        }
        return -1;
    }

    static int maxPositionReachableInOneStep(int from, int to, int[] values) {
        assert from <= to;
        int max = 0;
        for (int i=from; i<=to; i++) {
            max = Math.max(max, i + values[i]);
        }
        return max;
    }
}
