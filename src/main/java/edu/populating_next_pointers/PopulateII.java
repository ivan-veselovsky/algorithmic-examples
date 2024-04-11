package edu.populating_next_pointers;

import edu.common.Node;

import java.util.Deque;
import java.util.LinkedList;

public class PopulateII {
    private int maxVisitedDepth;

    public Node connect(Node root) {
        if (root != null) {
            bfs(root);
        }
        return root;
    }

    private void bfs(Node root) {
        final Deque<Node> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node node = queue.poll();

            node.next = queue.peek();

            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        maxVisitedDepth = 0;
        invPreOrderDfs(root, 0);
    }

    private void invPreOrderDfs(Node node, int depth) {
        if (node == null) {
            return;
        }
        if (depth > maxVisitedDepth) { // first node on this depth
            assert depth == maxVisitedDepth + 1;
            maxVisitedDepth = depth;
            node.next = null;
        }

        invPreOrderDfs(node.right, depth + 1);
        invPreOrderDfs(node.left, depth + 1);
    }
}
