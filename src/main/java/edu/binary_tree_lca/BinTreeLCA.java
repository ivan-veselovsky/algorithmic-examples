package edu.binary_tree_lca;

import edu.common.TreeNode;

public class BinTreeLCA {

    private int foundCount;
    private TreeNode result;

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        preOrderDfs(root, null, p, q);

        return result;
    }

    boolean preOrderDfs(TreeNode node, TreeNode parent, TreeNode t1, TreeNode t2) {
        if (node == t1 || node == t2) {
            foundCount++;
            if (foundCount == 1) {
                result = node;
            } else {
                assert foundCount == 2;
                return false; // stop traverse.
            }
        }

        boolean continueTraverse = true;
        if (node.left != null) {
            continueTraverse = preOrderDfs(node.left, node, t1, t2);
        }
        if (node.right != null) {
            continueTraverse = continueTraverse && preOrderDfs(node.right, node, t1, t2);
        }

        if (continueTraverse) {
            if (foundCount == 1 && node == result && parent != null) {
                // we didn't find the 2nd target in this subtree, so move result candidate up:
                result = parent;
            }
        }
        return continueTraverse;
    }
}
