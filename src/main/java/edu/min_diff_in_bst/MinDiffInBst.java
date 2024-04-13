package edu.min_diff_in_bst;

import edu.common.TreeNode;

public class MinDiffInBst {
    private final MinAbsDiffReceiver minAbsDiffReceiver = new MinAbsDiffReceiver();

    public int getMinimumDifference(TreeNode root) {
        dfs(root);

        return minAbsDiffReceiver.getMinAbsDiff();
    }

    boolean dfs(TreeNode node) {
        boolean result = true;
        if (node.left != null) {
            result = dfs(node.left);
        }

        result = result && minAbsDiffReceiver.receive(node.val());

        if (node.right != null) {
            result = result && dfs(node.right);
        }
        return result;
    }

    static class MinAbsDiffReceiver {
        private int minDiff = Integer.MAX_VALUE;
        private int previousValue;
        private int count;
        boolean receive(int val) {
            assert val >= 0;
            if (count > 0) {
                assert previousValue < val;
                int diff = val - previousValue;
                assert diff > 0;
                if (diff < minDiff) {
                    minDiff = diff;
                }
            }
            previousValue = val;
            count++;
            return (minDiff > 1);
        }

        int getMinAbsDiff() {
            return minDiff;
        }
    }
}
