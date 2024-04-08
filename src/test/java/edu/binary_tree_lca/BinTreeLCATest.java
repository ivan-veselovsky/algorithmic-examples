package edu.binary_tree_lca;

import edu.common.TreeNode;
import org.junit.jupiter.api.Test;

import static edu.common.TreeNodeUtils.buildTreeFromBFS;
import static org.assertj.core.api.BDDAssertions.then;

class BinTreeLCATest {

    @Test
    void test1() {
        TreeNode root = buildTreeFromBFS(new Integer[] { 3,5,1,6,2,0,8,null,null,7,4});

        TreeNode n5 = root.left();
        TreeNode n1 = root.right();
        TreeNode lca = new BinTreeLCA().lowestCommonAncestor(root, n5, n1);
        then(lca).isSameAs(root);
    }

    @Test
    void test2() {
        TreeNode root = buildTreeFromBFS(new Integer[] { 3,5,1,6,2,0,8,null,null,7,4});

        TreeNode n5 = root.left();
        TreeNode n4 = n5.right().right();
        TreeNode lca = new BinTreeLCA().lowestCommonAncestor(root, n5, n4);
        then(lca).isSameAs(n5);
    }

    @Test
    void test3() {
        TreeNode root = buildTreeFromBFS(new Integer[] { 3,5,1,6,2,0,8,null,null,7,4});

        TreeNode n5 = root.left();
        TreeNode n6 = n5.left();
        TreeNode n4 = n5.right().right();

        TreeNode lca = new BinTreeLCA().lowestCommonAncestor(root, n6, n4);
        then(lca).isSameAs(n5);

        lca = new BinTreeLCA().lowestCommonAncestor(root, n4, n6);
        then(lca).isSameAs(n5);
    }
}