package edu.product_except_self;

import static edu.common.MathUtils.maxContainedPowerOf2;
import static edu.common.MathUtils.power2;

public class ProductExceptSelf {
    private int[] array;

    public int[] productExceptSelf(final int[] nums) {
        this.array = nums;
        final int[] result = new int[nums.length];
        int zeroIndex = firstIndexOf(0, 0, array);
        if (zeroIndex > -1) {
            int secondZeroIndex = firstIndexOf(0, zeroIndex + 1, array);
            if (secondZeroIndex == -1) {
                result[zeroIndex] = productOfAllExcept(zeroIndex);
            }
        } else {
            // idx   period,    shift
            // ---------------------------
            // 0:    2          0
            // 1:    2          1
            // ---------------------------
            // 2:    4          0
            // 3:    4          1
            // 4:    4          2
            // 5:    4          3
            // ----------------------------
            // 6:    8          0
            // 7:    8          1
            // 8:    8          2
            // 9:    8          3
            // 10:   8          4
            // 11:   8          5
            // 12:   8          6
            // 13:   8          7
            // -----------------------------
            // 14:
            // ..............
            final int partialProductsLength = getPartialProductsLength(nums.length);
            final int[] partialProducts = new int[partialProductsLength];
            final int maxPeriod = period(partialProducts.length - 1);
            computePartialProducts(maxPeriod, partialProducts);

            for (int i = 0; i < result.length; i++) {
                result[i] = computeFromPartialProducts(i, maxPeriod, partialProducts);
            }
        }
        return result;
    }

    int getPartialProductsLength(int arrayLength) {
        int maxPow2 = maxContainedPowerOf2(arrayLength - 1);
        return power2(maxPow2 + 1) - 2;
    }

    int period(int index) {
        int p = maxContainedPowerOf2(index + 2);
        return power2(p);
    }

    int shift(int index) {
        int p = period(index);
        return index + 2 - p;
    }

    int partialProductIndex(int shift, int period) {
        return period - 2 + shift;
    }

    private static int firstIndexOf(int val, int startIndex, int[] a) {
        for (int i = startIndex; i < a.length; i++) {
            if (a[i] == val) {
                return i;
            }
        }
        return -1;
    }

    private int productOfAllExcept(int indexToSkip) {
        int prod = 1;
        for (int i = 0; i < array.length; i++) {
            if (i != indexToSkip) {
                prod *= array[i];
            }
        }
        return prod;
    }

    private int computePartialProductDirect(final int shift, final int period) {
        int prod = 1;
        for (int i = shift; i < array.length; i += period) {
            prod *= array[i];
        }
        return prod;
    }

    private int computePartialProductReuse(final int shift, final int period, int[] partialProducts) {
        int higherPeriod = period << 1;
        int shift2 = shift + period;

        int v1 = getPartialProductValue(shift, higherPeriod, partialProducts);
        assert v1 != 0;
        int v2 = getPartialProductValue(shift2, higherPeriod, partialProducts);
        assert v2 != 0;

        return v1 * v2;
    }

    int getPartialProductValue(int shift, int period, int[] partialProducts) {
        int index = partialProductIndex(shift, period);
        if (index < partialProducts.length) {
            return partialProducts[index];
        } else if (shift < array.length) {
            return array[shift];
        } else {
            return 1;
        }
    }

    private void computePartialProducts(int maxPeriod, int[] partialProducts) {
        for (int i=partialProducts.length - 1; i >= 0; i--) {
            int shift = shift(i);
            int period = period(i);
            if (period == maxPeriod) {
                partialProducts[i] = computePartialProductDirect(shift, period);
            } else {
                partialProducts[i] = computePartialProductReuse(shift, period, partialProducts);
            }
        }
        assert firstIndexOf(0, 0, partialProducts) < 0;
    }

    private int computeFromPartialProducts(final int i, final int maxPeriod, int[] partialProducts) {
        int prod = 1;
        for (int baseShift = 1; ; baseShift <<= 1) {
            int period = baseShift << 1;
            int shift = (i + baseShift) % period;
            int value = getPartialProductValue(shift, period, partialProducts);
            assert value != 0;
            prod *= value;
            if (period > maxPeriod) {
                break;
            }
        }
        return prod;
    }
}
