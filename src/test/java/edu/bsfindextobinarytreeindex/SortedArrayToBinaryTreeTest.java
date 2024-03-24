package edu.bsfindextobinarytreeindex;

import org.junit.jupiter.api.Test;

import static edu.bsfindextobinarytreeindex.SortedArrayToBinaryTree.*;
import static org.assertj.core.api.BDDAssertions.then;

class SortedArrayToBinaryTreeTest {

    @Test
    void test() {
        then(powerOf2(0)).isEqualTo(1);
        then(powerOf2(1)).isEqualTo(2);
        then(powerOf2(2)).isEqualTo(4);
        then(powerOf2(3)).isEqualTo(8);

        then(maxContainedPowerOf2(13)).isEqualTo(3);
        then(maxContainedPowerOf2(1)).isEqualTo(0);
        then(maxContainedPowerOf2(2)).isEqualTo(1);

        // level 0, root:
        then(getBinaryTreeIndex(16, 1)).isEqualTo(7);

        // level 1:
        then(getBinaryTreeIndex(16, 2)).isEqualTo(3);
        then(getBinaryTreeIndex(16, 3)).isEqualTo(11);

        // level 2:
        then(getBinaryTreeIndex(16, 4)).isEqualTo(1);
        then(getBinaryTreeIndex(16, 5)).isEqualTo(5);
        then(getBinaryTreeIndex(16, 6)).isEqualTo(9);
        then(getBinaryTreeIndex(16, 7)).isEqualTo(13);

        // level 3:
        then(getBinaryTreeIndex(16, 8)).isEqualTo(0);
        then(getBinaryTreeIndex(16, 9)).isEqualTo(2);
        then(getBinaryTreeIndex(16, 10)).isEqualTo(4);
        then(getBinaryTreeIndex(16, 11)).isEqualTo(6);
        then(getBinaryTreeIndex(16, 12)).isEqualTo(8);
        then(getBinaryTreeIndex(16, 13)).isEqualTo(10);
        then(getBinaryTreeIndex(16, 14)).isEqualTo(12);
        then(getBinaryTreeIndex(16, 15)).isEqualTo(14);

        // level 4:
        then(getBinaryTreeIndex(16, 31)).isEqualTo(14.5); // actually, needs to be rounded up
    }
}