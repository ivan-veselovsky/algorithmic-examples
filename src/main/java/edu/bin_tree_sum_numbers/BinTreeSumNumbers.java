package edu.bin_tree_sum_numbers;

import edu.common.TreeNode;

public class BinTreeSumNumbers {

    public int sumNumbers(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return dfs(root, 0);
    }

    int dfs(TreeNode node, int parentAccum) {
        int accum = parentAccum * 10 + node.val();

        int sumInSubtrees = 0;
        if (node.left != null) {
            sumInSubtrees += dfs(node.left, accum);
        }
        if (node.right != null) {
            sumInSubtrees += dfs(node.right, accum);
        }

        if (isLeaf(node)) { // leaf:
            return accum;
        }
        return sumInSubtrees;
    }

    private boolean isLeaf(TreeNode n) {
        return n.left == null && n.right == null;
    }
}
