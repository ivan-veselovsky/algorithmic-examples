package edu.range_bitwise_and;

import static edu.common.MathUtils.maxContainedPowerOf2;

/**
 Given two integers left and right that represent the range [left, right], return the
 bitwise AND of all numbers in this range, inclusive.

 Example 1:

 Input: left = 5, right = 7
 Output: 4
 Example 2:

 Input: left = 0, right = 0
 Output: 0
 Example 3:

 Input: left = 1, right = 2147483647
 Output: 0

 Constraints:

 0 <= left <= right <= 231 - 1 *
 */
public class BitwiseAndOfNumberRange {
    public int rangeBitwiseAnd(int left, int right) {
        // 0. Shortcut for trivial case
        if (left == right) {
            return left;
        }

        // 1. If the lengths are different, result is zero:
        final int powerLeft = maxContainedPowerOf2(left);
        final int powerRight = maxContainedPowerOf2(right);
        if (powerRight != powerLeft) {
            return 0;
        }

        // 2. Diff length shall be zeroed:
        final int diff = right - left;
        final int powerDiff = maxContainedPowerOf2(diff);
        int result = left;
        for (int i = powerDiff; i >= 0; i--) {
            int mask = 1 << i;
            result &= ~mask;
        }

        // 3. And AND-ed with the high number:
        return result & right;
    }
}
