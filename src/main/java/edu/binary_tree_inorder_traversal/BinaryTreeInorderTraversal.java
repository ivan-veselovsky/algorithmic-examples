package edu.binary_tree_inorder_traversal;

import edu.common.TreeNode;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class BinaryTreeInorderTraversal {

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        if (root == null) {
            return result;
        }
        dfsInOrder(root, n -> result.add(n.val()));
        return result;
    }

    void dfsInOrder(TreeNode node, Consumer<TreeNode> func) {
        if (node.left() != null) {
            dfsInOrder(node.left(), func);
        }

        func.accept(node);

        if (node.right() != null) {
            dfsInOrder(node.right(), func);
        }
    }
}
