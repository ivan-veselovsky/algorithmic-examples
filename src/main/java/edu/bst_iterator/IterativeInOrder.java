package edu.bst_iterator;

import edu.common.TreeNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Consumer;

import static edu.common.TreeNodeUtils.leftBfsChild;
import static edu.common.TreeNodeUtils.rightBfsChild;

public class IterativeInOrder {

    private final Deque<Ctx> stack = new LinkedList<>();

    enum Direction {
        left_down,
        right_down,
        left_up,
        right_up;

        Direction revert() {
            return switch (this) {
                case left_up -> right_down;
                case right_down -> left_up;
                case left_down -> right_up;
                case right_up -> left_down;
            };
        }
    }

    record Ctx(TreeNode treeNode, Direction direction, int bfsIndex) {
        Ctx {
            assert treeNode != null;
        }
        static Ctx createLeftChildCtx(Ctx ctx) {
            int bfsIndex = leftBfsChild(ctx.bfsIndex());
            return new Ctx(ctx.treeNode().left(), Direction.left_down, bfsIndex);
        }
        static Ctx createRightChildCtx(Ctx ctx) {
            int bfsIndex = rightBfsChild(ctx.bfsIndex());
            return new Ctx(ctx.treeNode().right(), Direction.right_down, bfsIndex);
        }
        boolean hasLeft() {
            return treeNode.left != null;
        }
        boolean hasRight() {
            return treeNode.right != null;
        }
    }

    public void setRoot(TreeNode root) {
        assert stack.isEmpty();
        Ctx ctx = new Ctx(root, null, 0);
        stack.push(ctx);
    }

    /**
     * Makes a step of in-order traversal;
     * @return Direction for next step, or null if the traversal is finished.
     */
    public Direction next(Direction directionFromPreviousStep,
             Consumer<Ctx> preProcessingConsumer,
             Consumer<Ctx> middleProcessingConsumer,
             Consumer<Ctx> postProcessingConsumer) {
        if (stack.isEmpty()) {
            return null; // end.
        }
        final Ctx x = stack.peek();
        switch (directionFromPreviousStep) {
            case left_down -> {
                if (x.hasLeft()) {
                    push(Ctx.createLeftChildCtx(x), preProcessingConsumer);
                    return Direction.left_down;
                } else {
                    return Direction.right_down;
                }
            }
            case right_down -> {
                middleProcessingConsumer.accept(x); // MIDDLE: from left subtree we go to the right subtree
                if (x.hasRight()) {
                    push(Ctx.createRightChildCtx(x), preProcessingConsumer);
                    return Direction.left_down;
                } else {
                    if (x.direction() == null) {
                        return null; // root
                    }
                    return x.direction().revert();
                }
            }
            case right_up -> {
                pop(postProcessingConsumer);
                if (stack.isEmpty()) {
                    return null; // enf of traversal
                } else {
                    return Direction.right_down;
                }
            }
            case left_up -> {
                pop(postProcessingConsumer);
                if (stack.isEmpty()
                    || stack.peek().direction() == null) {
                    return null; // enf of traversal
                } else {
                    return stack.peek().direction().revert();
                }
            }
        }
        throw new IllegalStateException("never go here");
    }

    void push(Ctx ctx, Consumer<Ctx> consumer) {
        consumer.accept(ctx);
        stack.push(ctx);
    }

    Ctx pop(Consumer<Ctx> consumer) {
        Ctx ctx = stack.pop();
        assert ctx != null;
        consumer.accept(ctx);
        return ctx;
    }
}
