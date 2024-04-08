package edu.if_bin_tree_has_path_sum;

import edu.common.TreeNode;

public class IfBinTreeHasPathSum {
    private int targetSum;
    private boolean result;

    public boolean hasPathSum(TreeNode root, int targetSum) {
        this.targetSum = targetSum;

        if (root == null) {
            return false;
        }

        dfs(root, 0);

        return result;
    }

    private boolean dfs(TreeNode node, int parentSum) {
        int sum = parentSum + node.val(); // NB: values can be negative!
        if (isLeaf(node) && sum == targetSum) {
            result = true;
            return false;
        }
        boolean continueTraverse = true;
        if (node.left != null) {
            continueTraverse = dfs(node.left, sum);
        }
        if (continueTraverse && node.right != null) {
            continueTraverse = dfs(node.right, sum);
        }
        return continueTraverse;
    }

    private boolean isLeaf(TreeNode n) {
        return n.left == null && n.right == null;
    }

}
