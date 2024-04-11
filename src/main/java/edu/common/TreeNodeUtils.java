package edu.common;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.tuple.Pair;

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

    public static <T extends TreeNode> T buildTreeFromBFS0(Integer[] nullFilledBFS, IntFunction<T> creator) {
        if (nullFilledBFS == null || nullFilledBFS.length == 0) {
            return null;
        }
        Preconditions.checkArgument(nullFilledBFS[0] != null);

        final TreeNode[] unlinkedNodeList = new TreeNode[nullFilledBFS.length];
        for (int i = 0; i < nullFilledBFS.length; i++) {
            Integer val = nullFilledBFS[i];
            if (val != null) {
                unlinkedNodeList[i] = creator.apply(val);
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
