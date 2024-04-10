package edu.populating_next_nodes;

import edu.common.Node;

import java.util.Deque;
import java.util.LinkedList;

public class PopulatingNextNodes {
    public Node connect(Node root) {
        if (root != null) {
            bfs(root);
        }
        return root;
    }

    private void bfs(Node root) {
        Deque<Node> queue = new LinkedList<>();
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
    }

}
