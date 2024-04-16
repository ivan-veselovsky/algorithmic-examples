package edu.majority_element_moore_voting_algorithm;

import java.util.HashMap;
import java.util.Map;

public class MajorityElement_NaiveHashTable {
    public int majorityElement(final int[] nums) {
        final Map<Integer, int[]> map = new HashMap<>(nums.length / 2);
        final Integer[] majorityContainer = new Integer[]{null};
        final int n2 = nums.length / 2;
        for (int i = 0; i < nums.length; i++) {
            int val = nums[i];
            map.compute(val, (k, v) -> {
                if (v == null) {
                    v = new int[]{1};
                } else {
                    v[0]++;
                }
                if (v[0] > n2) {
                    majorityContainer[0] = k;
                }
                return v;
            });
            if (majorityContainer[0] != null) {
                return majorityContainer[0];
            }
        }
        return majorityContainer[0];
    }
}
