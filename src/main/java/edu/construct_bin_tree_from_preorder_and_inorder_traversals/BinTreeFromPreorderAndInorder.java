package edu.construct_bin_tree_from_preorder_and_inorder_traversals;

import edu.common.TreeNode;

public class BinTreeFromPreorderAndInorder {
    private int[] preorder;
    private int[] inorder;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null ||
                preorder.length == 0 || preorder.length != inorder.length) {
            return null;
        }
        this.preorder = preorder;
        this.inorder = inorder;

        return buildSubtree(0, 0, preorder.length);
    }

    private TreeNode buildSubtree(int preOrderStartIndex, int inOrderStartIndex, int subtreeSize) {
        final int rootVal = preorder[preOrderStartIndex];
        int inOrderRootIdx = indexOf(inorder, inOrderStartIndex, subtreeSize, rootVal);
        int leftSubtreeSize = inOrderRootIdx - inOrderStartIndex;

        final TreeNode root = create(rootVal);

        if (leftSubtreeSize > 0) {
            TreeNode leftSubtree = buildSubtree(
                    preOrderStartIndex + 1,
                    inOrderStartIndex,
                    leftSubtreeSize);
            root.left(leftSubtree);
        }

        int rightSubtreeSize = subtreeSize - leftSubtreeSize - 1;
        if (rightSubtreeSize > 0) {
            TreeNode rightSubtree = buildSubtree(
                    preOrderStartIndex + leftSubtreeSize + 1,
                    inOrderRootIdx + 1,
                    rightSubtreeSize);
            root.right(rightSubtree);
        }

        return root;
    }

    private int indexOf(int[] arr, int startIndexInclusive, int length, int val) {
        for (int i = startIndexInclusive; i < (startIndexInclusive + length); i++) {
            if (arr[i] == val) {
                return i;
            }
        }
        throw new IllegalArgumentException("Value " + val + " not found.");
    }

    private TreeNode create(int val) {
        return new TreeNode(val);
    }

}
