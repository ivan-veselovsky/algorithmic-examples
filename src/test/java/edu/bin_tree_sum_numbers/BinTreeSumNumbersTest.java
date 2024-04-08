package edu.bin_tree_sum_numbers;

import edu.common.TreeNode;
import org.junit.jupiter.api.Test;

import static edu.common.TreeNodeUtils.buildTreeFromBFS;
import static org.assertj.core.api.BDDAssertions.then;

class BinTreeSumNumbersTest {

    @Test
    void test1() {
        // [1,2,3]
        //Output: 25
        TreeNode root = buildTreeFromBFS(new Integer[] { 1,2,3 });
        then(new BinTreeSumNumbers().sumNumbers(root)).isEqualTo(25);
    }

    @Test
    void test2() {
        // root = [4,9,0,5,1]
        //Output: 1026
        TreeNode root = buildTreeFromBFS(new Integer[] { 4,9,0,5,1 });
        then(new BinTreeSumNumbers().sumNumbers(root)).isEqualTo(1026);
    }

}