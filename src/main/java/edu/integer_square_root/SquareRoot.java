package edu.integer_square_root;

import static edu.common.MathUtils.maxContainedPowerOf2;
import static edu.common.MathUtils.powerOf2;

/**
 Given a non-negative integer x, return the square root of x rounded down to the nearest integer.
 The returned integer should be non-negative as well.
 You must not use any built-in exponent function or operator.
 For example, do not use pow(x, 0.5) in c++ or x ** 0.5 in python.

 Example 1:
 Input: x = 4
 Output: 2
 Explanation: The square root of 4 is 2, so we return 2.

 Example 2:
 Input: x = 8
  Output: 2
 Explanation: The square root of 8 is 2.82842..., and since we round it down to the nearest integer, 2 is returned.

 Constraints:
     0 <= x <= 231 - 1 *
 */
public class SquareRoot {
    public int mySqrt(final int x) {
        if (x == 0) {
            return 0;
        }
        int lowerPowerOf2 = maxContainedPowerOf2(x);
        int higherPowerOf2 = lowerPowerOf2 + 1;
        //assert powerOf2(lowerPowerOf2) <= x;
        //assert powerOf2(higherPowerOf2) > x;

        long lowRoot  = powerOf2(lowerPowerOf2 / 2);
        long highRoot;
        if (higherPowerOf2 % 2 == 0) {
            highRoot = powerOf2(higherPowerOf2 / 2);
        } else {
            highRoot = powerOf2((higherPowerOf2 + 1) / 2);
        }
        //assert highRoot > lowRoot;

        while (true) {
            long middle = (lowRoot + highRoot) / 2;
            long square = middle * middle;
            if (square > x) {
                highRoot = middle;
            } else {
                lowRoot = middle;
            }
            if (lowRoot + 1 == highRoot) {
                return (int)lowRoot;
            }
        }
    }

//    static int maxContainedPowerOf2(int x) {
//        int p = -1;
//        while (x != 0) {
//            x >>>= 1;
//            p++;
//        }
//        return p;
//    }
//
//    static long powerOf2(int x) {
//        return 1L << x;
//    }
}
