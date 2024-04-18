package edu.product_except_self;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class ProductExceptSelfTest {

    @Test
    void case0() {
        int[] nums = new int[] {1,2,0,4};
        int[] result = new ProductExceptSelf().productExceptSelf(nums);
        then(result).containsExactly(0,0,8,0);
    }

    @Test
    void case00() {
        int[] nums = new int[] {1,2,0,4, 555, 8, 0};
        int[] result = new ProductExceptSelf().productExceptSelf(nums);
        then(result).containsExactly(0,0,0,0, 0,0,0);
    }

    @Test
    void case1() {
        int[] nums = new int[] {1,2,3,4};
        int[] result = new ProductExceptSelf().productExceptSelf(nums);
        then(result).containsExactly(24,12,8,6);
    }

    @Test
    void case2() {
        int[] nums = new int[] {1,2,3,4, 5,6,7,8};
        int[] result = new ProductExceptSelf().productExceptSelf(nums);
        then(result).containsExactly(getExpectedResultUsingDivision(nums));
    }

    @Test
    void case7() {
        int[] nums = new int[] {1,2,3,4, 5,6,7};
        int[] result = new ProductExceptSelf().productExceptSelf(nums);
        then(result).containsExactly(getExpectedResultUsingDivision(nums));
    }

    private int[] getExpectedResultUsingDivision(int[] nums) {
        int[] expected = new int[nums.length];

        int prod = 1;
        for (int num : nums) {
            prod *= num;
        }

        for (int i=0; i<nums.length; i++) {
            expected[i] = prod / nums[i];
        }
        return expected;
    }
}