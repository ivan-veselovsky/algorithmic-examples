package edu.zigzag_level_order;

import edu.common.TreeNode;
import edu.common.TreeNodeUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class ZigZagLevelOrderTest {

    @Test
    void zigzagLevelOrder() {
        TreeNode root = TreeNodeUtils.buildTreeFromBFS(new Integer[] {
                3,9,20,null,null,15,7
        });
        List<List<Integer>> list = new ZigZagLevelOrder().zigzagLevelOrder(root);

        then(list).containsExactly(List.of(3), List.of(20,9), List.of(15,7));
    }
}