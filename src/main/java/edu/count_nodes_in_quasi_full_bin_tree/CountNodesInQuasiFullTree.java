package edu.count_nodes_in_quasi_full_bin_tree;

import edu.common.TreeNode;

/** Solution of O(log^2(N)). */
public class CountNodesInQuasiFullTree {
    public int countNodes(final TreeNode root) {
        if (root == null) {
            return 0;
        }

        final int depthLeft = sideDepth(root, true);
        final int depthRight = sideDepth(root, false);

        if (depthLeft == depthRight) {
            return pow2(depthLeft + 1) - 1;  // last level if full
        }
        assert depthRight + 1 == depthLeft;

        int x = 0; // left path
        int y = pow2(depthRight + 1) - 1; // right path

        while (x + 1 < y) {
            int middlePath = (x + y) >> 1;
            int depthMiddle = depth(root, middlePath, depthRight);
            if (depthLeft == depthMiddle) {
                x = middlePath; // -->
            } else {
                y = middlePath; // <--
            }
        }

        return pow2(depthLeft) + x;
    }

    private int depth(TreeNode root, final int bitPath, int numberOfBits) {
        int bitMask = (1 << numberOfBits);
        int depthCount = 0;
        while (true) {
            boolean left = ((bitPath & bitMask) == 0);
            if (left) {
                root = root.left;
            } else {
                root = root.right;
            }
            if (root == null) {
                return depthCount;
            }
            depthCount++;
            bitMask >>>= 1;
        }
    }

    private int sideDepth(final TreeNode root, final boolean left) {
        int depthCount = 0;
        TreeNode node = root;
        while (true) {
            if (left) {
                node = node.left;
            } else {
                node = node.right;
            }
            if (node == null) {
                return depthCount;
            }
            depthCount++;
        }
    }

    public static int pow2(int pow) {
        return (1 << pow);
    }

}
