package edu.right_side_view;

import edu.common.TreeNode;

import java.util.LinkedList;
import java.util.List;

public class RightSideView2 {
    private final List<Integer> result = new LinkedList<>();
    private int maxProcessedDepth = -1;

    public List<Integer> rightSideView(TreeNode root) {
        if (root == null) {
            return List.of();
        }

        dfs(root, 0);

        return result;
    }

    void dfs(TreeNode node, int depth) {
        if (depth > maxProcessedDepth) {
            result.add(node.val());
            maxProcessedDepth = depth;
        }

        if (node.right != null) { // inverse pre-order
            dfs(node.right, depth + 1);
        }
        if (node.left != null) {
            dfs(node.left, depth + 1);
        }
    }
}
