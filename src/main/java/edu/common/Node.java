package edu.common;

public class Node extends TreeNode {
    public Node next;

    public Node(int _val) {
        super(_val);
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        super(_val, _left, _right);
        next = _next;
    }

    @Override
    public String toString() {
        return val() + "->" + ((next == null) ? "null" : next.val());
    }

    public Node left() {
        return (Node)left;
    }
    public Node right() {
        return (Node)right;
    }
}
