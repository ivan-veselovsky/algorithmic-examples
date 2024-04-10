package edu.populating_next_pointers;

import edu.common.Node;

import java.util.Deque;
import java.util.LinkedList;

public class PopulateII {
    public Node connect(Node root) {
        if (root != null) {
            bfs(root);
        }
        return root;
    }

    record Wrap(Node node, long bfsIndex) {}

    private void bfs(Node root) {
        final Deque<Wrap> queue = new LinkedList<>();
        queue.offer(new Wrap(root, 1L));

        while (!queue.isEmpty()) {
            Wrap wrap = queue.poll();
            Node node = wrap.node();

            Wrap nextWrap = queue.peek();
            if (nextWrap != null) {
                long firstIndexOnTheNextLevel = 1L << (zeroBasedLevel(wrap.bfsIndex()) + 1);
                if (nextWrap.bfsIndex() < firstIndexOnTheNextLevel) {
                    Node nextNode = nextWrap.node();
                    node.next = nextNode;
                }
            }

            if (node.left != null) {
                queue.offer(new Wrap(node.left, wrap.bfsIndex() << 1));
            }
            if (node.right != null) {
                queue.offer(new Wrap(node.right, (wrap.bfsIndex() << 1) + 1));
            }
        }
    }

    int zeroBasedLevel(long x) {
        int cnt = -1;
        while (x != 0) {
            x >>>= 1;
            cnt++;
        }
        return cnt;
    }
}
