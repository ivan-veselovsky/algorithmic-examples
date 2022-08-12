package edu.findlongestpath;


import lombok.AllArgsConstructor;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * Solution that uses O(1) additional memory and O(n) time.
 */
class FindLongestPathInBinaryTree_SolutionWithO1Cache {

    /** Auxiliary helper class. */
    @AllArgsConstructor
    static class MutablePair {
        int leftSubtreeHeight;
        int rightSubtreeHeight;

        private int getSum() {
            return leftSubtreeHeight + rightSubtreeHeight;
        }
    }

    public static int findLongestPath(final Node root) {
        final int[] maxPathLength = new int[1];

        postOrderTraverse(root,
                () -> new MutablePair(-1, -1), // just to get (0,0) on leaf nodes.
            (MutablePair leftResult, MutablePair rightResult)
                -> new MutablePair(1 + Math.max(leftResult.leftSubtreeHeight, leftResult.rightSubtreeHeight),
                           1 + Math.max(rightResult.leftSubtreeHeight, rightResult.rightSubtreeHeight)),
            (Node node, MutablePair result) -> {
                final int length = result.getSum();
                maxPathLength[0] = Math.max(length, maxPathLength[0]);
            });

        return maxPathLength[0];
    }

    /**
     * Generic post-order traversal recursive function with custom handlers of the visited Node-s.
     * @param node The node to start traversal.
     * @param nullNodeResultProvider function that gives "result" for children of leaves (null nodes).
     * @param leftAndRightResultCombiner function that joins results from the left and right children.
     * @param resultConsumer optional consumer of intermediate results. It will be called for each visited Node with the result for that Node.
     * @return result for the subtree rooted in 'node' Node.
     * @param <T> The type of the result.
     */
    static <T> T postOrderTraverse(Node node,
                                           Supplier<T> nullNodeResultProvider,
                                           BiFunction<T, T, T> leftAndRightResultCombiner,
                                           BiConsumer<Node, T> resultConsumer
                                         ) {
        if (node == null) {
            return nullNodeResultProvider.get();
        }

        T leftResult = postOrderTraverse(node.getLeft(), nullNodeResultProvider, leftAndRightResultCombiner, resultConsumer);
        T rightResult = postOrderTraverse(node.getRight(), nullNodeResultProvider, leftAndRightResultCombiner, resultConsumer);

        T combinedResult = leftAndRightResultCombiner.apply(leftResult, rightResult);
        resultConsumer.accept(node, combinedResult);

        return combinedResult;
    }
}
