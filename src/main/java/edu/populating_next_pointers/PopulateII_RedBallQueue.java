package edu.populating_next_pointers;

import edu.common.Node;
import edu.common.RedBallQueue;

public class PopulateII_RedBallQueue {

    public Node connect(Node root) {
        if (root != null) {
            bfs(root);
        }
        return root;
    }

    private void bfs(Node root) {
        RedBallQueue<Node> queue = new RedBallQueue<>(32);
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            Node node = queue.dequeue();

            if (queue.redBallPosition() > 0) {
                node.next = queue.peek(); // link all except the last
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
