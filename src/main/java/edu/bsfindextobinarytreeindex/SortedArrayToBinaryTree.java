package edu.bsfindextobinarytreeindex;

import static edu.common.MathUtils.maxContainedPowerOf2;
import static edu.common.MathUtils.powerOf2;

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
}
