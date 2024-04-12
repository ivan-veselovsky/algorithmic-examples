package edu.average_of_levels_in_binary_tree;

import edu.common.TreeNode;
import edu.common.TreeNodeUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class AverageOfLevelsInBinaryTreeTest {

    @Test
    void averageOfLevels() {
        TreeNode root = TreeNodeUtils.buildTreeFromBFS(new Integer[] {3,9,20,15,7});

        List<Double> list = new AverageOfLevelsInBinaryTree().averageOfLevels(root);

        then(list).containsExactly(3., 14.5, 11.);
    }
}