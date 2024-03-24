package edu.bsfindextobinarytreeindex;

public class SortedArrayToBinaryTree {

    /**
     * An array (length == 2^n) needs to be represented as binary search tree.
     * Given BSF Order index in this tree, find index in the array.
     */
    static double getBinaryTreeIndex(int arraySize, int bsfOrderIndex) {
        int bsfOrderLevel = maxContainedPowerOf2(bsfOrderIndex);
        int denominator = powerOf2(bsfOrderLevel + 1);
        return (double)arraySize * (1 + 2 * bsfOrderIndex) / denominator - arraySize - 1;
    }

    static int powerOf2(int x) {
        return 1 << x;
    }

    static int maxContainedPowerOf2(int x) {
        int cnt = -1;
        while (x != 0) {
            x >>>= 1;
            cnt++;
        }
        return cnt;
    }
}
