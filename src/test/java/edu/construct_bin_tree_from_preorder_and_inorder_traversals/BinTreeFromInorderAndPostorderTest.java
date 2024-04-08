package edu.construct_bin_tree_from_preorder_and_inorder_traversals;

import edu.common.TreeNode;
import org.junit.jupiter.api.Test;

import static edu.common.TreeNodeUtils.asNullFilledBFS;
import static org.assertj.core.api.BDDAssertions.then;

class BinTreeFromInorderAndPostorderTest {

    @Test
    void test() {
        int[] inorder = new int[] {9,3,15,20,7};
        int[] postorder = new int[] {9,15,7,20,3};

        TreeNode root = new BinTreeFromInorderAndPostorder().buildTree(inorder, postorder);

        then(asNullFilledBFS(root)).containsExactly(3,9,20,null,null,15,7);
    }

}