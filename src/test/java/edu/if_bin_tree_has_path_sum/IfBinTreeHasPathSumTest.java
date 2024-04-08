package edu.if_bin_tree_has_path_sum;

import edu.common.TreeNode;
import org.junit.jupiter.api.Test;

import static edu.common.TreeNodeUtils.buildTreeFromBFS;
import static org.assertj.core.api.BDDAssertions.then;

class IfBinTreeHasPathSumTest {

    @Test
    void test() {
        TreeNode tree = buildTreeFromBFS(new Integer[] { 5,4,8,11,null,13,4,7,2,null,null,null,1 });
        int targetSum = 22;
        boolean result = new IfBinTreeHasPathSum().hasPathSum(tree, targetSum);
        then(result).isTrue();
    }

    @Test
    void test2() {
        TreeNode tree = buildTreeFromBFS(new Integer[] { 1, 2, 3});
        int targetSum = 5;
        boolean result = new IfBinTreeHasPathSum().hasPathSum(tree, targetSum);
        then(result).isFalse();
    }

}