package edu.populating_next_pointers;

import edu.common.Node;

import java.util.Deque;
import java.util.LinkedList;

public class PopulateIIBiQueue {

    public Node connect(Node root) {
        if (root != null) {
            bfs(root);
        }
        return root;
    }

    private void bfs(Node root) {
        final Deque<Node> inQueue = new LinkedList<>();
        final Deque<Node> outQueue = new LinkedList<>();
        inQueue.offer(root);

        while (true) {
            Node node = outQueue.poll();
            if (node == null) {
                moveAll(inQueue, outQueue);

                node = outQueue.poll();

                if (node == null) {
                    break;
                }
            }

            if (outQueue.isEmpty()) {
                //System.out.println("process: " + node + " **** end of level.");
            } else {
                node.next = outQueue.peek();
                //System.out.println("Linked: " + node);
            }

            if (node.left != null) {
                inQueue.offer(node.left());
            }
            if (node.right != null) {
                inQueue.offer(node.right());
            }
        }
    }

    <T> void moveAll(Deque<T> src, Deque<T> dst) {
        while (true) {
            T t = src.poll();
            if (t == null) {
                return;
            }
            dst.offer(t);
        }
    }
}
