package edu.bst_iterator;

import edu.common.TreeNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Consumer;

class BSTIterator {
    private final IterativeInOrderSimple iterativeInOrderSimple = new IterativeInOrderSimple();
    private Direction direction = Direction.left_down;
    private Integer value;

    public BSTIterator(TreeNode root) {
        iterativeInOrderSimple.setRoot(root);
    }

    public int next() {
        if (value == null) {
            tryGetNextValue();
        }
        if (value == null) {
            throw new java.util.NoSuchElementException();
        }
        final Integer result = value;
        value = null; // null value to force next value finding in the next step.
        return result;
    }

    public boolean hasNext() {
        if (value == null) {
            tryGetNextValue();
        }
        return (value != null);
    }

    private void tryGetNextValue() {
        assert value == null;
        while (direction != null) {
            direction = iterativeInOrderSimple.next(direction, x -> {}, x -> {
                value = x.treeNode().val();
            }, x -> {});
            if (value != null) {
                break; // value found
            }
        }
    }
}

record Ctx(TreeNode treeNode, Direction direction) {
    Ctx {
        assert treeNode != null;
    }
    static Ctx createLeftChildCtx(Ctx ctx) {
        return new Ctx(ctx.treeNode().left, Direction.left_down);
    }
    static Ctx createRightChildCtx(Ctx ctx) {
        return new Ctx(ctx.treeNode().right, Direction.right_down);
    }
    boolean hasLeft() {
        return treeNode.left != null;
    }
    boolean hasRight() {
        return treeNode.right != null;
    }
}

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

class IterativeInOrderSimple {

    private final Deque<Ctx> stack = new LinkedList<>();

    public void setRoot(TreeNode root) {
        assert stack.isEmpty();
        Ctx ctx = new Ctx(root, null);
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
                        return null;
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
        consumer.accept(ctx);
        return ctx;
    }
}