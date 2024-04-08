package edu.common;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.BiConsumer;

public class TreeNodeUtils {

    public static void traverseInBreadth(TreeNode root, BiConsumer<TreeNode, Integer> consumer) {
        final Deque<Pair<TreeNode, Integer>> deque = new LinkedList<>();
        deque.offer(Pair.of(root, 1));
        while (!deque.isEmpty()) {
            final Pair<TreeNode, Integer> pair = deque.poll();
            final TreeNode node = pair.getLeft();
            final int oneBasedIndex = pair.getRight();
            if (node.left() != null) {
                deque.offer(Pair.of(node.left(), oneBasedIndex << 1));
            }
            if (node.right() != null) {
                deque.offer(Pair.of(node.right(), (oneBasedIndex << 1) + 1));
            }
            consumer.accept(node, oneBasedIndex);
        }
    }

    public static Integer[] asNullFilledBFS(TreeNode root) {
        LinkedList<Pair<TreeNode, Integer>> list = new LinkedList<>();
        traverseInBreadth(root, (x, bfsIndex) -> {
            list.add(Pair.of(x, bfsIndex));
        });
        int maxBfsIndex = list.getLast().getRight();
        Integer[] result = new Integer[maxBfsIndex];
        list.forEach(p -> {
            result[p.getRight() - 1] = p.getLeft().val();
        });
        return result;
    }

    public static TreeNode buildTreeFromBFS(Integer[] nullFilledBFS) {
        if (nullFilledBFS == null || nullFilledBFS.length == 0) {
            return null;
        }
        Preconditions.checkArgument(nullFilledBFS[0] != null);

        final TreeNode[] unlinkedNodeList = new TreeNode[nullFilledBFS.length];
        for (int i = 0; i < nullFilledBFS.length; i++) {
            Integer val = nullFilledBFS[i];
            if (val != null) {
                unlinkedNodeList[i] = new TreeNode(val);
            }
        }

        for (int i = 0; i < unlinkedNodeList.length; i++) {
            final TreeNode node = unlinkedNodeList[i];
            if (node != null ) {
                int leftChildIndex = ((i + 1) << 1) - 1;
                if (leftChildIndex < unlinkedNodeList.length) {
                    TreeNode left = unlinkedNodeList[leftChildIndex];
                    if (left != null) {
                        node.left(left);
                    }
                } else {
                    break;
                }

                int rightChildIndex = ((i + 1) << 1);
                if (rightChildIndex < unlinkedNodeList.length) {
                    TreeNode right = unlinkedNodeList[rightChildIndex];
                    if (right != null) {
                        node.right(right);
                    }
                } else {
                    break;
                }
            }
        }

        return unlinkedNodeList[0];
    }

}
