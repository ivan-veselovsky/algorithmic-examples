package edu.invert_binary_tree;

import edu.common.TreeNode;

import java.util.function.Consumer;

public class InvertBinaryTree {
    public TreeNode invertTree(TreeNode root) {
        if (root != null) {
            dfsPostOrder(root, this::swapChildren);
        }
        return root;
    }

    void swapChildren(TreeNode x) {
        TreeNode right = x.right();
        x.right(x.left());
        x.left(right);
    }

    void dfsPostOrder(TreeNode node, Consumer<TreeNode> func) {
        if (node.left() != null) {
            dfsPostOrder(node.left(), func);
        }
        if (node.right() != null) {
            dfsPostOrder(node.right(), func);
        }

        func.accept(node);
    }

}
