package edu.construct_bin_tree_from_preorder_and_inorder_traversals;

import edu.common.TreeNode;

/**
 * pre(T) = reverted_order( inv_post(T) )
 * post(T) = reverted_order( inv_pre(T) )
 *
 * in(T) = reverted_order( inv_in(T) )
 *
 * pre(T) = inv_pre( mirror(T) )
 * post(T) = inv_post( mirror(T) )
 * in(T) = inv_in( mirror(T) )
 */
public class BinTreeFromInorderAndPostorder {

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        reverse(inorder); // this is inverted inorder now (inorder in mirrored tree)
        reverse(postorder); // this is inverted preorder now (preorder in mirrored tree)

        TreeNode inv_tree = new BinTreeFromPreorderAndInorder().buildTree(postorder, inorder);

        mirrorTree(inv_tree); // invert the tree to get non-inverted one

        return inv_tree;
    }

    void mirrorTree(TreeNode node) {
        if (node.right != null) {
            mirrorTree(node.right);
        }
        if (node.left != null) {
            mirrorTree(node.left);
        }
        TreeNode buf = node.right;
        node.right = node.left;
        node.left = buf;
    }

    void reverse(int[] xx) {
        if (xx.length <= 1) {
            return;
        }
        for (int i=0; i< xx.length/2; i++ ) {
            swap(i, xx.length - i - 1, xx);
        }
    }

    void swap(int a, int b, int[] xx) {
        int buf = xx[b];
        xx[b] = xx[a];
        xx[a] = buf;
    }
}
