package edu.binary_tree_maximum_path_sum;

import edu.common.TreeNode;

import static java.lang.Math.max;

public class BinTreeMaximumPathSum {

    public int maxPathSum(TreeNode root) {
        OutContext out = postOrderDfs(root);
        return out.getMax();
    }

    record OutContext(
            int maxConnectible,
            int maxDisconnected) {
        int getMax() {
            return max(maxConnectible, maxDisconnected);
        }
    }

    OutContext postOrderDfs(TreeNode node) {
        if (node == null) {
            return null;
        }
        OutContext outLeft = postOrderDfs(node.left);
        OutContext outRight = postOrderDfs(node.right);

        return merge(node.val(), outLeft, outRight);
    }

    OutContext merge(int subrootValue, OutContext left, OutContext right) {
        if (left == null && right == null) {
            return new OutContext(subrootValue, subrootValue); // leaf
        }

        if (left == null) {
            return merge(subrootValue, right);
        } else if (right == null) {
            return merge(subrootValue, left);
        }

        int maxIncludingSubroot = max3(
                subrootValue,
                subrootValue + left.maxConnectible(),
                subrootValue + right.maxConnectible());
        int maxDisconnected = max4(maxIncludingSubroot,
                left.maxDisconnected(),
                right.maxDisconnected(),
                subrootValue + left.maxConnectible() + right.maxConnectible());
        return new OutContext(maxIncludingSubroot, maxDisconnected);
    }

    OutContext merge(int subrootValue, OutContext leftOrRight) {
        int maxIncludingSubroot = max(subrootValue,
                subrootValue + leftOrRight.maxConnectible());
        int maxDisconnected = max(maxIncludingSubroot, leftOrRight.maxDisconnected());
        return new OutContext(maxIncludingSubroot, maxDisconnected);
    }

    private static int max3(int x, int y, int z) {
        return max(max(x,y), z);
    }

    private static int max4(int x, int y, int z, int p) {
        return max(max(x, y), max(z, p));
    }

}
