package edu.median_of_two_sorted_arrays;

import edu.common.HalfIndex;

import java.util.function.IntFunction;

import static edu.common.MathUtils.bisectFindRoot;
import static edu.common.MathUtils.sign;

public class MedianOfTwoSortedArrays {
    public double findMedianSortedArrays(int[] a0, int[] b0) {
        if (a0.length == 0) {
            return getMedian(b0);
        }
        if (b0.length == 0) {
            return getMedian(a0);
        }
        final int[] a;
        final int[] b;
        if (b0.length > a0.length) {
            a = b0;
            b = a0;
        } else {
            a = a0;
            b = b0;
        }

        final int sumLength = (a.length + b.length) / 2 - 1;
        final int minX = Math.abs(a.length - b.length) / 2;

        final IntFunction<Integer> f = x -> {
            assert x >= minX && x <= sumLength && x < a.length : x;
            int y = sumLength - x;
            assert y >= 0 && y <= sumLength && y < b.length : y;
            return a[x] - b[y];
        };

        for (int i=minX; i <=sumLength; i++) {
            System.out.println("x = " + i + ", f(x) = " + f.apply(i));
        }

        int vLeft = f.apply(minX);
        int vRight = f.apply(sumLength);
        int xStar;
        if (sign(vLeft) == sign(vRight)) {
            if (Math.abs(vLeft) < Math.abs(vRight)) {
                xStar = 0;
            } else {
                xStar = sumLength;
            }
        } else {
            HalfIndex half = bisectFindRoot(f, minX, sumLength + 1);
            xStar = half.index();
            if (half.half()) {
                vLeft = f.apply(half.index());
                vRight = f.apply(half.ceiling());
                if (Math.abs(vRight) < Math.abs(vLeft)) {
                    xStar = half.ceiling();
                }
            }
        }

        final int yStar = sumLength - xStar;

        if ((a.length + b.length) % 2 == 0) {
            return ((double)a[xStar] + b[yStar]) / 2;
        } else {
            // (n + m) is odd
            int middleIndex = (a.length + b.length) / 2;
            assert xStar == middleIndex || yStar == middleIndex;
            return (xStar == middleIndex) ? a[xStar] : b[yStar];
        }
    }

    double getMedian(int[] array) {
        if (array.length % 2 == 0) {
            int v1 = array[array.length / 2];
            int v2 = array[array.length / 2 - 1];
            return ((double)v1 + v2) / 2;
        } else {
            return array[array.length / 2];
        }
    }

}
