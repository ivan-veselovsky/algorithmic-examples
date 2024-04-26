package edu.summary_ranges;

import java.util.ArrayList;
import java.util.List;

public class SummaryRanges {
    int[] nums;

    public List<String> summaryRanges(int[] nums) {
        if (nums == null || nums.length == 0) {
            return List.of();
        }
        this.nums = nums;

        List<String> result = new ArrayList<>(nums.length / 2);
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > 0) {
                assert nums[i] >= nums[i - 1];
                if (nums[i - 1] + 1 == nums[i]) {
                    count++;
                } else {
                    result.add(composeArrowedString(i - count - 1, i - 1));
                    count = 0;
                }
            }
        }
        result.add(composeArrowedString(nums.length - count - 1, nums.length - 1));
        return result;
    }

    String composeArrowedString(int from , int to) {
        StringBuilder sb = new StringBuilder().append(nums[from]);
        if (to > from) {
            sb.append("->").append(nums[to]);
        }
        return sb.toString();
    }

}
