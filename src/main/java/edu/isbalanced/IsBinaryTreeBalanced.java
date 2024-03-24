package edu.isbalanced;

import com.google.common.base.Preconditions;
import edu.common.Node;
import lombok.val;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class IsBinaryTreeBalanced {

    record LeftRight(int left, int right) {
        static LeftRight of(int x, int y) {
            return new LeftRight(x, y);
        }
    }
    
    static boolean isBalanced(Node<AtomicReference<LeftRight>> root) {
        val count = new AtomicInteger();

        boolean result = dfs(root, DfsKind.POST_ORDER, node -> {
            LeftRight pair = getSubtreeHeightPair(node);
            node.payload().set(pair);
            int diff = Math.abs(pair.left() - pair.right());
            System.out.println("Visiting: " + node + ", subtrees height diff = " + diff);
            return (diff <= 1);
        }, count);

        System.out.println("visited: " + count + " nodes.");

        return result;
    }

    private static LeftRight getSubtreeHeightPair(Node<AtomicReference<LeftRight>> node) {
        return LeftRight.of(
                getSubtreeHeight(node.left()),
                getSubtreeHeight(node.right()));
    }

    private static Integer getSubtreeHeight(Node<AtomicReference<LeftRight>> node) {
        if (node == null) {
            return 0;
        }
        LeftRight pair = node.payload().get();
        Preconditions.checkState(pair != null, "node = " + node);
        // Max of the heights of left and right subtrees:
        return 1 + Math.max(pair.left(), pair.right());
    }

    enum DfsKind {
        PRE_ORDER, POST_ORDER, IN_ORDER;
    }

    static <T> boolean dfs(Node<T> node, DfsKind traverseKind, Predicate<Node<T>> lambda, final AtomicInteger count) {
        return doTraverse(node, traverseKind, x -> {
            count.incrementAndGet();
            return lambda.test(x);
        });
    }

    private static <T> boolean doTraverse(Node<T> node, DfsKind traverseKind, Predicate<Node<T>> lambda) {
        if (node == null) {
            return true;
        }
        final boolean response;
        switch (traverseKind) {
            case PRE_ORDER -> {
                response = lambda.test(node)
                    && doTraverse(node.left(), traverseKind, lambda)
                    && doTraverse(node.right(), traverseKind, lambda);
            }
            case POST_ORDER -> {
                response = doTraverse(node.left(), traverseKind, lambda)
                    && doTraverse(node.right(), traverseKind, lambda)
                    && lambda.test(node);
            }
            case IN_ORDER -> {
                response = doTraverse(node.left(), traverseKind, lambda)
                    && lambda.test(node)
                    && doTraverse(node.right(), traverseKind, lambda);
            }
            default -> throw new IllegalStateException("Unknown kind: " + traverseKind);
        }
        return response;
    }
}
