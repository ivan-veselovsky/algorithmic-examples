package edu.populating_next_pointers;

import edu.common.ArrayQueueWithSeparator;
import edu.common.Node;

public class PopulateII_ArrayQueueWithMarkedPosition {

    public Node connect(Node root) {
        if (root != null) {
            bfs(root);
        }
        return root;
    }

    private void bfs(Node root) {
        ArrayQueueWithSeparator<Node> queue = new ArrayQueueWithSeparator<>(64, Node[]::new);
        queue.enqueueTail(root);

        while (true) {
            Node node = queue.dequeueHead();
            if (node == null) {
                break;
            }

            if (queue.markedPosition() != 0) {
                node.next = queue.peek();
            }

            if (node.left != null) {
                queue.enqueueTail(node.left());
            }
            if (node.right != null) {
                queue.enqueueTail(node.right());
            }
        }
    }

}
