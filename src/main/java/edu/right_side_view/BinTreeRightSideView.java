package edu.right_side_view;

import edu.common.RedBallQueue;
import edu.common.TreeNode;

import java.util.LinkedList;
import java.util.List;

public class BinTreeRightSideView {
    public List<Integer> rightSideView(TreeNode root) {
        if (root == null) {
            return List.of();
        }
        final RedBallQueue<TreeNode> queue = new RedBallQueue<>(32);
        queue.enqueue(root);
        final List<Integer> result = new LinkedList<>();
        while (!queue.isEmpty()) {
            TreeNode node = queue.dequeue();

            if (queue.redBallPosition() == 0) { // level processing finished:
                result.add(node.val());
            }

            if (node.left != null) {
                queue.enqueue(node.left);
            }
            if (node.right != null) {
                queue.enqueue(node.right);
            }
        }
        return result;
    }
}
