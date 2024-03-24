package edu.isbalanced;

import edu.common.Node;
import lombok.val;

import java.util.concurrent.atomic.AtomicInteger;

public class IsBinaryTreeBalanced {

    static boolean isBalanced(Node<Void> root) {
        val count = new AtomicInteger();

        TraverseResult<Integer> result = dfs(root, DfsKind.POST_ORDER, (node, r1, r2) -> {
            if (node == null) {
                assert r1 == null && r2 == null;
                return TraverseResult.positiveOf(0);
            } else {
                assert r1 != null && r2 != null;
                int height = 1 + Math.max(r1.data(), r2.data());
                int diff = Math.abs(r1.data() - r2.data());
                System.out.println("Visiting: " + node + ", height = " + height + ", subtrees height diff = " + diff);
                return TraverseResult.of(diff <= 1, height);
            }
        }, count);

        System.out.println("visited: " + count + " nodes.");

        return result.continueTraverse();
    }

    private static int nullSafeInteger(TraverseResult<Integer> r) {
        if (r == null) {
            return 0;
        }
        return r.data;
    }

    enum DfsKind {
        PRE_ORDER, POST_ORDER, IN_ORDER;
    }

    static <T> TraverseResult<T> dfs(Node<Void> node, DfsKind traverseKind, NodeLambda<T> lambda, final AtomicInteger count) {
        return doTraverse(node, traverseKind, (n, r1, r2) -> {
            count.incrementAndGet();
            return lambda.process(n, r1, r2);
        }, null);
    }

    record TraverseResult<T>(boolean continueTraverse, T data) {
        static <T> TraverseResult<T> positiveNull() { return new TraverseResult<>(true, null); }
        static <T> TraverseResult<T> negativeNull() { return new TraverseResult<>(false, null); }
        static <T> TraverseResult<T> of(boolean positive, T data) {
            return new TraverseResult<>(positive, data);
        }

        static <T> TraverseResult<T> positiveOf(T data) {
            return new TraverseResult<>(true, data);
        }
        static <T> TraverseResult<T> negativeOf(T data) {
            return new TraverseResult<>(false, data);
        }
    }

    interface NodeLambda<P> {
        TraverseResult<P> process(Node<?> node, TraverseResult<P> prev1, TraverseResult<P> prev2);
    }

    private static <T> TraverseResult<T> doTraverse(Node<Void> node, DfsKind traverseKind, NodeLambda<T> lambda, TraverseResult<T> previousResult) {
        if (node == null) {
            return lambda.process(null, previousResult, null);
        }
        switch (traverseKind) {
            case PRE_ORDER -> {
                TraverseResult<T> center = lambda.process(node, previousResult, null);
                if (!center.continueTraverse()) {
                    return center;
                }
                TraverseResult<T> left = doTraverse(node.left(), traverseKind, lambda, center);
                if (!left.continueTraverse()) {
                    return left;
                }
                return doTraverse(node.right(), traverseKind, lambda, center);
            }
            case POST_ORDER -> {
                TraverseResult<T> left = doTraverse(node.left(), traverseKind, lambda, null );
                if (!left.continueTraverse()) {
                    return left;
                }
                TraverseResult<T> right = doTraverse(node.right(), traverseKind, lambda, null);
                if (!right.continueTraverse()) {
                    return right;
                }
                return lambda.process(node, left, right);
            }
            case IN_ORDER -> {
                TraverseResult<T> left = doTraverse(node.left(), traverseKind, lambda, null );
                if (!left.continueTraverse()) {
                    return left;
                }
                TraverseResult<T> center = lambda.process(node, left, null);
                if (!center.continueTraverse()) {
                    return center;
                }
                return doTraverse(node.right(), traverseKind, lambda, center);
            }
            default -> throw new IllegalStateException("Unknown kind: " + traverseKind);
        }
    }
}
