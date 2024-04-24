package edu.longest_consequtive_sequence;

import java.util.Arrays;
import java.util.OptionalInt;

public class LongestConsequtiveSequence {
    private int[] nums;

    private static final int[] tenPower = new int[] {
      1, 10, 100, 1_000, 10_000, 100_000, 1_000_000, 10_000_000, 100_000_000, 1_000_000_000
    };
    private int numDigits;

    public int longestConsecutive(final int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        this.nums = nums;
        this.numDigits = maxNumberOfDigits(nums);

        radixSort();

        return findLongestConsequtiveSeqLength();
    }

    private int maxNumberOfDigits(int[] arr) {
        return Arrays.stream(arr).map(this::numberOfDigits).max().getAsInt();
    }

    int numberOfDigits(final int x) {
        if (x == 0) {
            return 0;
        }
        for (int i = 1; i < tenPower.length; i++) {
            int pow = tenPower[i];
            if (x / pow == 0) {
                return i;
            }
        }
        return 10; // int cannot have 11 or more digits
    }

    int[][] computeCountersMatrix() {
        int[][] matrix = new int[numDigits][];
        for (int d=0; d<numDigits; d++) {
            matrix[d] = countersForDigit(d);
            assert sum(matrix[d]) == nums.length;
        }
        return matrix;
    }

    private int sum(int[] arr) {
        return Arrays.stream(arr).sum();
    }

    private int[] countersForDigit(final int digit) {
        int[] counters = new int[10];
        for (int i=0; i<nums.length; i++) {
            int digitValue = getDigit(nums[i], digit);
            counters[digitValue]++;
        }
        return counters;
    }

    void radixSort() {
        // 1. count sizes to allocate appropriate arrays
        int[][] countersMatrix = computeCountersMatrix();

        // 2. create appropriately sized arrays
        int[] source = this.nums;
        int[] target = new int[nums.length];
        for (int digit=0; digit<numDigits; digit++) {
            int[] positionVector = new int[10];
            integratePositions(countersMatrix[digit], positionVector);

            rewriteData(digit, positionVector, source, target);

            int[] tmp = target;
            target = source;
            source = tmp;
        }

        this.nums = sortNegative(source, target);
    }

    int[] sortNegative(int[] source, int[] target) {
        assert source.length == target.length;
        OptionalInt maybeNegative = Arrays.stream(source).filter(x -> x < 0).findAny();
        if (maybeNegative.isEmpty()) {
            return source;
        }
        int positiveWritePosition = target.length - 1;
        int negativeWritePosition = 0;
        for (int i = source.length - 1; i >= 0; i--) {
            int value = source[i];
            if (value < 0) {
                target[negativeWritePosition] = value;
                negativeWritePosition++;
            } else {
                target[positiveWritePosition] = value;
                positiveWritePosition--;
            }
        }
        return target;
    }

    void rewriteData(final int digit, int[] positionVector, int[] source, int[] target) {
        for (int i=0; i<nums.length; i++) {
            int value = source[i];
            int digitValue = getDigit(value, digit);
            int writePosition = positionVector[digitValue];
            target[writePosition] = value;
            positionVector[digitValue]++;
        }
    }

    void integratePositions(int[] counters, int[] target) {
        assert counters.length == target.length;
        for (int i=0; i<counters.length; i++) {
            if (i == 0) {
                target[i] = 0;
            } else {
                target[i] = counters[i - 1] + target[i - 1];
            }
        }
    }

    private int getDigit(int x, int digitIndex) {
        return (Math.abs(x) / tenPower[digitIndex]) % 10;
    }

    int findLongestConsequtiveSeqLength() {
        int nPrev = -1;
        int conseqCount = 1;
        int maxConseq = 1;
        for (int i=0; i<nums.length; i++) {
            int n = nums[i];
            if (i > 0) {
                assert nPrev <= n : "expected sorted array";
                if (n == nPrev + 1) {
                    conseqCount++;
                    if (conseqCount > maxConseq) {
                        maxConseq = conseqCount;
                    }
                } else if (n != nPrev) { // ignore duplicates
                    conseqCount = 1;
                }
            }
            nPrev = n;
        }
        return maxConseq;
    }

}
