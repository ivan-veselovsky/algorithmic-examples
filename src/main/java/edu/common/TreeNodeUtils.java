package edu.common;

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

}
