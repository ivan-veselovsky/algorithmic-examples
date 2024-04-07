package edu.findlongestpath;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.BiFunction;

import static edu.findlongestpath.FindLongestPathInBinaryTree_SolutionWithO1Cache.postOrderTraverse;

public class TreeUtils {

    public static Node parseTree(String treeAsFigure) {
        final int[] array = treeAsFigure
                .replaceAll("\\s+", "")
                .chars()
                .map(x -> (x == 'o') ? 1 : 0)
                .toArray();
        long expectedNodeCount = Arrays.stream(array).filter(i -> i > 0).count();
        final Node[] nodes = new Node[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] > 0) {
                nodes[i] = new Node(i + 1,
                        safeGetNode(nodes, leftChildIndex(i)),
                        safeGetNode(nodes, rightChildIndex(i)));
            }
        }

        int actualNodeCount = countNodes(nodes[0]);
        if (expectedNodeCount != actualNodeCount) {
            throw new IllegalStateException("Node count mismatch: expected "
                    + expectedNodeCount + " != actual " + actualNodeCount);
        }

        return nodes[0]; // the root
    }

    private static Node safeGetNode(Node[] nodes, int index) {
        if (index < 0 || index >= nodes.length) {
            return null;
        }
        return nodes[index];
    }

    /**
     * @return Zero based index of left child
     */
    static int leftChildIndex(int zeroBasedIndex) {
        return ((zeroBasedIndex + 1) << 1) - 1;
    }

    /**
     * @return Zero based index of right child
     */
    static int rightChildIndex(int zeroBasedIndex) {
        return (zeroBasedIndex + 1) << 1;
    }

    public static int containedPowerOf2(final int numNodes) {
        int numShifts = 0;
        int x = numNodes;
        while (x > 1) {
            x >>>= 1;
            numShifts++;
        }
        return numShifts;
    }

    public static int majoratingPowerOf2(final int numNodes) {
        int numShifts = -1;
        int x = numNodes;
        while (x > 0) {
            x >>>= 1;
            numShifts++;
        }
        if (numNodes > (1 << numShifts)) {
            numShifts++;
        }
        return Math.max(0, numShifts);
    }

    public static int countNodes(Node root) {
        return postOrderTraverse(root, () -> 0, (x, y) -> x + y + 1 , (node, x) -> {});
    }

    public static int depth(Node root) {
        return postOrderTraverse(root, () -> -1, (x, y) -> 1 + Math.max(x, y), (node, x) -> {});
    }

    public static void printTree(final Node root) {
        final int spacerOnLowerLevel = 2;
        final int nodePrintWidth = 1;

        final int treeDepth = depth(root);
        final int nodesOnLowermostLevel = 1 << treeDepth;
        final int lowermostLineLength = nodesOnLowermostLevel * nodePrintWidth + (nodesOnLowermostLevel - 1) * spacerOnLowerLevel;

        final int[] previousHorizontalPosition = new int[] { 0 };
        final int[] previousVerticalPosition = new int[] { 0 };

        traverseInBreadth(root, (node, oneBasedIndex) -> {
            final int zeroBasedLevel = containedPowerOf2(oneBasedIndex);
            final int totalNodesOnThisLevel = 1 << zeroBasedLevel;
            final int startingOneBasedIndexOnThisLevel = (1 << zeroBasedLevel);
            final boolean isLastLevel = (zeroBasedLevel == treeDepth);

            int denominator = isLastLevel ? (totalNodesOnThisLevel - 1) : totalNodesOnThisLevel;

            double delta = ((double)(lowermostLineLength - totalNodesOnThisLevel * nodePrintWidth)) / denominator;
            int firstOffset = isLastLevel ? 0 : (int)(delta / 2);

            int horizontalPosition = (int)(firstOffset
                    + (delta + nodePrintWidth) * (oneBasedIndex - startingOneBasedIndexOnThisLevel)
                    + nodePrintWidth);
            int verticalPosition = zeroBasedLevel;

            if (verticalPosition > previousVerticalPosition[0]) {
                System.out.println();
                previousHorizontalPosition[0] = 0;
            }

            int offset = horizontalPosition - previousHorizontalPosition[0];
            System.out.print(generateSpacer(offset - nodePrintWidth));

            //System.out.print("(" + node + ")");
            System.out.print("O");

            previousVerticalPosition[0] = verticalPosition;
            previousHorizontalPosition[0] = horizontalPosition;

            return null;
        });
        System.out.println();
    }

    private static String generateSpacer(int length) {
        if (length < 0) {
            return ""; // TODO: debug and avoid such cases.
        }
        StringBuilder sb = new StringBuilder(length);
        for (int i=0; i<length; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }

    public static <R> void traverseInBreadth(Node root, BiFunction<Node, Integer, R> processingFunction) {
        final Deque<Pair<Integer, Node>> deque = new LinkedList<>();
        deque.offerFirst(Pair.of(1, root));
        while (!deque.isEmpty()) {
            final Pair<Integer, Node> pair = deque.pollLast();
            final Node node = pair.getRight();
            final int oneBasedIndex = pair.getLeft();
            if (node.left() != null) {
                deque.offerFirst(Pair.of(oneBasedIndex << 1, node.left()));
            }
            if (node.right() != null) {
                deque.offerFirst(Pair.of((oneBasedIndex << 1) + 1, node.right()));
            }
            R result = processingFunction.apply(node, oneBasedIndex);
        }
    }

}
