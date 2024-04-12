package edu.populating_next_pointers;

import edu.common.Node;
import edu.common.RedBallArrayDeque;

public class PopulateII_ArrayQueueWithMarkedPosition {

    public Node connect(Node root) {
        if (root != null) {
            bfs(root);
        }
        return root;
    }

    private void bfs(Node root) {
        RedBallArrayDeque<Node> queue = new RedBallArrayDeque<>(32);
        queue.enqueue(root);

        while (true) {
            Node node = queue.dequeue();
            if (node == null) {
                break;
            }

            if (queue.getRedBallPosition() > 0) {
                node.next = queue.peek();
            }

            if (node.left != null) {
                queue.enqueue(node.left());
            }
            if (node.right != null) {
                queue.enqueue(node.right());
            }
        }
    }

}
