package edu.zigzag_level_order;

import edu.common.RedBallQueue;
import edu.common.TreeNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class ZigZagLevelOrder {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return List.of();
        }
        final RedBallQueue<TreeNode> queue = new RedBallQueue<>(32);
        queue.enqueue(root);
        final List<List<Integer>> result = new LinkedList<>();

        boolean forward = true;

        while (!queue.isEmpty()) {
            if (queue.redBallPosition() == 0) { // we're at level start:
                List<Integer> level = copyAllValues(queue.getDeque(), forward);
                result.add(level);
                forward = !forward;
            }

            TreeNode node = queue.dequeue();

            if (node.left != null) {
                queue.enqueue(node.left);
            }
            if (node.right != null) {
                queue.enqueue(node.right);
            }
        }
        return result;
    }

    List<Integer> copyAllValues(Deque<TreeNode> deque, boolean forward) {
        if (!forward) {
            deque = deque.reversed();
        }
        return deque.stream().map(TreeNode::val).toList();
    }
}
