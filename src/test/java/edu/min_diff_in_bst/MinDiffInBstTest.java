package edu.min_diff_in_bst;

import edu.common.TreeNode;
import edu.common.TreeNodeUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class MinDiffInBstTest {

    @Test
    void getMinimumDifference1() {
        TreeNode tree = TreeNodeUtils.buildTreeFromBFS(new Integer[] { 4,2,6,1,3 });
        int minDiff = new MinDiffInBst().getMinimumDifference(tree);
        then(minDiff).isEqualTo(1);
    }

    @Test
    void getMinimumDifference2() {
        TreeNode tree = TreeNodeUtils.buildTreeFromBFS(new Integer[] { 1,0,48,null,null,12,49 });
        int minDiff = new MinDiffInBst().getMinimumDifference(tree);
        then(minDiff).isEqualTo(1);
    }
}