package edu.is_bin_tree_symmetric;

import edu.common.TreeNode;

public class IsSymmetricRecursive {

    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return dfs(root.left, root.right);
    }

    boolean dfs(TreeNode n1, TreeNode n2) {
        if (!equalNodes(n1, n2)) {
            return false;
        }
        if (n1 != null) {
            return dfs(n1.left(), n2.right()) && dfs(n1.right(), n2.left());
        }
        return true;
    }

    boolean equalNodes(TreeNode n1, TreeNode n2) {
        if (n1 == null || n2 == null) {
            return (n1 == null) && (n2 == null);
        }
        return n1.val() == n2.val();
    }
}
