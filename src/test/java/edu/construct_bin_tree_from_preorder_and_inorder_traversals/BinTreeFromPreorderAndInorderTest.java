package edu.construct_bin_tree_from_preorder_and_inorder_traversals;

import edu.common.TreeNode;
import org.junit.jupiter.api.Test;

import static edu.common.TreeNodeUtils.asNullFilledBFS;
import static org.assertj.core.api.BDDAssertions.then;

class BinTreeFromPreorderAndInorderTest {


    @Test
    void test() {
        int[] preorder = {3,9,20,15,7};
        int[] inorder = {9,3,15,20,7};

        TreeNode root = new BinTreeFromPreorderAndInorder().buildTree(preorder, inorder);

        then(asNullFilledBFS(root)).containsExactly(3,9,20,null,null,15,7);
    }


}