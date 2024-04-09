package edu.kth_smallest_inbst;

import edu.common.TreeNode;

public class KthSmallestInBst {
    private int k;
    private Integer kthSmallest;
    private int count;

    public int kthSmallest(TreeNode root, int k) {
        this.k = k;

        inorderDfs(root);

        return kthSmallest;
    }

    boolean inorderDfs(TreeNode n) {
        if (n == null) {
            return true; // leaf
        }
        boolean result = inorderDfs(n.left);

        if (result) {
            count++;
            if (count == k) {
                kthSmallest = n.val();
                result = false;
            }
        }

        return result && inorderDfs(n.right);
    }
}
