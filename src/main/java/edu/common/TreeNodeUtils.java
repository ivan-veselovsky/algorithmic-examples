package edu.common;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;

public class TreeNodeUtils {

    public static <T extends TreeNode> void traverseInBreadth(T root, BiConsumer<T, Integer> consumer) {
        final Deque<Pair<T, Integer>> deque = new LinkedList<>();
        deque.offer(Pair.of(root, 1/*1-based*/));
        while (!deque.isEmpty()) {
            final Pair<T, Integer> pair = deque.poll();
            final T node = pair.getLeft();
            final int oneBasedBfsIndex = pair.getRight();
            if (node.left() != null) {
                deque.offer(Pair.of((T)node.left(), oneBasedBfsIndex << 1));
            }
            if (node.right() != null) {
                deque.offer(Pair.of((T)node.right(), (oneBasedBfsIndex << 1) + 1));
            }
            consumer.accept(node, oneBasedBfsIndex);
        }
    }

    public static Integer[] asNullFilledBFS(TreeNode root) {
        final LinkedList<Pair<TreeNode, Integer>> list = new LinkedList<>();
        traverseInBreadth(root, (x, bfsIndex) -> {
            list.add(Pair.of(x, bfsIndex));
        });
        int maxBfsIndex = list.getLast().getRight();
        Integer[] result = new Integer[maxBfsIndex];
        assert list.size() <= result.length;
        // NB: resultant array contains nulls because we fill only
        // elements that are present in 'list':
        list.forEach(pair -> {
            result[pair.getRight() - 1] = pair.getLeft().val();
        });
        return result;
    }

    record NodeWithBfsIndex(TreeNode node, int index) {
    }

    public static <T extends TreeNode> T buildTreeFromBFS0(final Integer[] nullFilledBFS, final IntFunction<T> creator) {
        if (nullFilledBFS == null || nullFilledBFS.length == 0) {
            return null;
        }
        Preconditions.checkArgument(nullFilledBFS[0] != null, "Null root element.");
        Preconditions.checkArgument(nullFilledBFS[nullFilledBFS.length - 1] != null, "Trailing null at index " + (nullFilledBFS.length - 1));

        final Deque<NodeWithBfsIndex> queue = new ArrayDeque<>();
        final TreeNode root = creator.apply(nullFilledBFS[0]);
        queue.offer(new NodeWithBfsIndex(root, 0)); // root

        for (int i = 0; i<nullFilledBFS.length; i++) {
            final Integer value = nullFilledBFS[i];
            if (value != null) {
                final NodeWithBfsIndex nodeWithIndex = queue.poll();
                final int parentIndex = ((i + 1) >> 1) - 1;
                Preconditions.checkArgument(nodeWithIndex != null
                                && nodeWithIndex.index() == i,
                      "Orphan node detected at index " + i + ", val = "
                              + value + ", parent index = " + parentIndex);

                final TreeNode node = nodeWithIndex.node();
                assert node.val() == value;

                final int leftChildIndex = ((i + 1) << 1) - 1;
                if (leftChildIndex < nullFilledBFS.length) {
                    Integer val = nullFilledBFS[leftChildIndex];
                    if (val != null) {
                        TreeNode left = creator.apply(val);
                        queue.offer(new NodeWithBfsIndex(left, leftChildIndex));
                        node.left(left);
                    }
                }

                final int rightChildIndex = leftChildIndex + 1;
                if (rightChildIndex < nullFilledBFS.length) {
                    Integer val = nullFilledBFS[rightChildIndex];
                    if (val != null) {
                        TreeNode right = creator.apply(val);
                        queue.offer(new NodeWithBfsIndex(right, rightChildIndex));
                        node.right(right);
                    }
                }
            }
        }

        return (T)root;
    }

    public static TreeNode buildTreeFromBFS(Integer[] nullFilledBFS) {
        return buildTreeFromBFS0(nullFilledBFS, TreeNode::new);
    }

    public static int leftBfsChild(int bfsZeroBased) {
        return ((bfsZeroBased + 1) << 1) - 1;
    }

    public static int rightBfsChild(int bfsZeroBased) {
        return (bfsZeroBased + 1) << 1;
    }

    public static int bfsParent(int bfsZeroBased) {
        return (bfsZeroBased + 1) / 2 - 1;
    }


    public static int zeroBasedLevel(int bfsIndex) {
        return MathUtils.maxContainedPowerOf2(bfsIndex);
    }

}
