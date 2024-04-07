package edu.findlongestpath;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static edu.findlongestpath.FindLongestPathInBinaryTree_SolutionWithO1Cache.IntPair.MINUS_ONE_PAIR;

/**
 * Solution that uses O(n) memory and O(n) time.
 * It does not use any additional data structures to store intermediate data,
 * but still uses the stack to traverse the tree, hence O(n) memory.
 */
class FindLongestPathInBinaryTree_SolutionWithO1Cache {

    /** Auxiliary helper class. */
    record IntPair(int leftSubtreeHeight, int rightSubtreeHeight) {
        static final IntPair MINUS_ONE_PAIR = new IntPair(-1, -1);

        private int getSum() {
            return leftSubtreeHeight + rightSubtreeHeight;
        }

        private int getMax() {
            return Math.max(leftSubtreeHeight, rightSubtreeHeight);
        }
    }

    private static class MaxValueAccumulator implements Consumer<Integer> {
        int maxValue;
        @Override
        public void accept(Integer anotherValue) {
            maxValue = Math.max(maxValue, anotherValue);
        }
    }

    public static int findLongestPath(final Node root) {
        final MaxValueAccumulator maxPathLengthAccumulator = new MaxValueAccumulator();

        postOrderTraverse(root,
                () -> MINUS_ONE_PAIR, // just to get (0,0) on leaf nodes.
            (IntPair leftResult, IntPair rightResult)
                -> new IntPair(1 + leftResult.getMax(),
                              1 + rightResult.getMax()),
            (Node node, IntPair result) -> maxPathLengthAccumulator.accept(result.getSum()));

        return maxPathLengthAccumulator.maxValue;
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

        T leftResult = postOrderTraverse(node.left(), nullNodeResultProvider, leftAndRightResultCombiner, resultConsumer);
        T rightResult = postOrderTraverse(node.right(), nullNodeResultProvider, leftAndRightResultCombiner, resultConsumer);

        T combinedResult = leftAndRightResultCombiner.apply(leftResult, rightResult);
        resultConsumer.accept(node, combinedResult);

        return combinedResult;
    }
}
