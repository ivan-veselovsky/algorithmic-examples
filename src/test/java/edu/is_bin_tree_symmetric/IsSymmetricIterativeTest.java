package edu.is_bin_tree_symmetric;

import edu.common.TreeNode;
import edu.common.TreeNodeUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class IsSymmetricIterativeTest {

    @Test
    void isSymmetric() {
        TreeNode root = TreeNodeUtils.buildTreeFromBFS(new Integer[] {
                1,
                2,2,
                3,4, 4,3
        });
        boolean result = new IsSymmetricIterative().isSymmetric(root);
        then(result).isTrue();
    }
}