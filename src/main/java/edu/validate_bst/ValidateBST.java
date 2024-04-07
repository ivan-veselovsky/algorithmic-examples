package edu.validate_bst;

import edu.common.TreeNode;

class ValidateBST {
    private int count = 0;
    private int previousValue;

    public boolean isValidBST(TreeNode root) {
        return dfs(root);
    }

    boolean dfs(TreeNode node) {
        boolean result = true;
        if (node.left() != null) {
            result = dfs(node.left());
        }

        result = result && checkValue(node.val());

        if (node.right() != null) {
            result = result && dfs(node.right());
        }
        return result;
    }

    boolean checkValue(int v) {
        if (!(count == 0 || v > previousValue)) {
            return false;
        }
        count++;
        previousValue = v;
        return true;
    }
}