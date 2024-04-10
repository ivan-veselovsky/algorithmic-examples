package edu.is_bin_tree_symmetric;

import edu.common.TreeNode;

import java.util.Deque;
import java.util.LinkedList;

public class IsSymmetricIterative {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (root.left == null || root.right == null) {
            return (root.left == null && root.right == null);
        }

        return bfs2(root.left, root.right);
    }

    record Wrap(TreeNode node, int bfsIndex) {
        Wrap {
            if (node == null || bfsIndex < 1) {
                throw new IllegalArgumentException();
            }
        }
    }

    Wrap wrapLeftChild(Wrap w, boolean mirror) {
        if (w.node().left == null) {
            return null;
        }
        int bfsIndex = (w.bfsIndex() << 1);
        return new Wrap(w.node().left, mirror ? (bfsIndex + 1) : bfsIndex);
    }

    Wrap wrapRightChild(Wrap w, boolean mirror) {
        if (w.node().right == null) {
            return null;
        }
        int bfsIndex = (w.bfsIndex() << 1) + 1;
        return new Wrap(w.node().right, mirror ? (bfsIndex - 1) : bfsIndex);
    }

    private boolean bfs2(TreeNode n1, TreeNode n2) {
        Deque<Wrap> queue1 = new LinkedList<>();
        queue1.offer(new Wrap(n1, 2));
        Deque<Wrap> queue2 = new LinkedList<>();
        queue2.offer(new Wrap(n2, 2));
        while (!queue1.isEmpty() || !queue2.isEmpty()) {
            Wrap w1 = queue1.poll();
            Wrap w2 = queue2.poll();

            if (!compareTwoWraps(w1, w2)) {
                return false;
            }

            Wrap w = wrapLeftChild(w1, false);
            if (w != null) {
                queue1.offer(w);
            }
            w = wrapRightChild(w1, false);
            if (w != null) {
                queue1.offer(w);
            }

            w = wrapRightChild(w2, true);
            if (w != null) {
                queue2.offer(w);
            }
            w = wrapLeftChild(w2, true);
            if (w != null) {
                queue2.offer(w);
            }
        }
        return true;
    }

    private boolean compareTwoWraps(Wrap w1, Wrap w2) {
        if (w1 == null || w2 == null) {
            return false;
        }
        return (w1.node().val() == w2.node().val()) && (w1.bfsIndex == w2.bfsIndex);
    }

}
