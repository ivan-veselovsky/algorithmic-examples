package edu.common;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
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

    public static <T extends TreeNode> T buildTreeFromBFS0(final Integer[] nullFilledBFS, final IntFunction<T> creator) {
        if (nullFilledBFS == null || nullFilledBFS.length == 0) {
            return null;
        }
        Preconditions.checkArgument(nullFilledBFS[0] != null, "root element must not be null.");
        Preconditions.checkArgument(nullFilledBFS[nullFilledBFS.length - 1] != null, "Trailing zero at index " + (nullFilledBFS.length - 1));

        final TreeNode[] unlinkedNodeList = new TreeNode[nullFilledBFS.length];
        unlinkedNodeList[0] = creator.apply(nullFilledBFS[0]); // root

        int lastProcessedIndex = 0;
        for (int i = 0; ; i++) {
            final Integer value = nullFilledBFS[i];
            if (value != null) {
                final TreeNode node = unlinkedNodeList[i];
                int parentIndex = ((i + 1) >> 1) - 1;
                Preconditions.checkArgument(node != null,
                      "Orphan node detected at index " + i + ", val = "
                              + value + ", parent index = " + parentIndex);

                int leftChildIndex = ((i + 1) << 1) - 1;
                if (leftChildIndex < unlinkedNodeList.length) {
                    Integer val = nullFilledBFS[leftChildIndex];
                    if (val != null) {
                        TreeNode left = creator.apply(val);
                        unlinkedNodeList[leftChildIndex] = left;
                        node.left(left);
                    }
                    lastProcessedIndex = leftChildIndex;
                } else {
                    break;
                }

                int rightChildIndex = leftChildIndex + 1;
                if (rightChildIndex < unlinkedNodeList.length) {
                    Integer val = nullFilledBFS[rightChildIndex];
                    if (val != null) {
                        TreeNode right = creator.apply(val);
                        unlinkedNodeList[rightChildIndex] = right;
                        node.right(right);
                    }
                    lastProcessedIndex = rightChildIndex;
                } else {
                    break;
                }
            }
        }

        int unconsumedElementCount = nullFilledBFS.length - 1 - lastProcessedIndex;
        Preconditions.checkArgument(unconsumedElementCount == 0,
                unconsumedElementCount + " values are not consumed: "
                        + Arrays.toString(Arrays.copyOfRange(nullFilledBFS, lastProcessedIndex + 1, nullFilledBFS.length)));

        return (T)unlinkedNodeList[0];
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
