package edu.minimum_depth_of_binary_tree;

import edu.common.TreeNode;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
public class MinimumDepthOfBinaryTree {
    private int minDepth = Integer.MAX_VALUE;

    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        dfsPreOrder(root, 1);

        return minDepth;
    }

    private void dfsPreOrder(TreeNode node, int depth) {
        if (depth >= minDepth) {
            return;
        }
        if (isLeaf(node)) {
            maybeUpdateMin(depth);
        } else {
            if (node.left() != null) {
                dfsPreOrder(node.left(), depth + 1);
            }
            if (node.right() != null) {
                dfsPreOrder(node.right(), depth + 1);
            }
        }
    }

    private boolean isLeaf(TreeNode node) {
        return (node.left() == null) && (node.right() == null);
    }

    private void maybeUpdateMin(int depth) {
        if (depth < minDepth) {
            minDepth = depth;
        }
    }
}
