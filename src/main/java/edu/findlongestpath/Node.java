package edu.findlongestpath;

import lombok.RequiredArgsConstructor;

import java.util.StringJoiner;

@RequiredArgsConstructor
class Node {
//    private final int val;
    private final Node left;
    private final Node right;

//    Integer leftSubtreeHeight;
//    Integer rightSubtreeHeight;

//    public Node(int val) {
//        this.val = val;
//    }

//    public int getVal() {
//        return val;
//    }

    public Node getLeft() {
        return left;
    }

//    public void setLeft(Node left) {
//        this.left = left;
//    }

    public Node getRight() {
        return right;
    }

//    public void setRight(Node right) {
//        this.right = right;
//    }

//    @Override
//    public String toString() {
//        return new StringJoiner(", ", Node.class.getSimpleName() + "[", "]")
//                .add("val=" + val)
//                .add("left=" + (left == null ? "null" : left.val))
//                .add("right=" + (right == null ? "null" : right.val))
//                .toString();
//    }
}
