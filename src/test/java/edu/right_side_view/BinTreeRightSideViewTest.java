package edu.right_side_view;

import edu.common.Node;
import edu.common.TreeNode;
import edu.populating_next_pointers.PopulateII_RedBallQueue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static edu.common.NodeUtils.asHashSeparatedNodeList;
import static edu.common.NodeUtils.buildTreeFromBFS_Node;
import static edu.common.TreeNodeUtils.buildTreeFromBFS;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class BinTreeRightSideViewTest {

    @Test
    void rightSideView1() {
        TreeNode root = buildTreeFromBFS(new Integer[] {1,2,3,null,5,null,4});
        List<Integer> actual = new BinTreeRightSideView().rightSideView(root);
        then(actual).containsExactly(1, 3, 4);
    }

    @Test
    void rightSideView2() {
        TreeNode root = buildTreeFromBFS(new Integer[] {1,null,3});
        List<Integer> actual = new BinTreeRightSideView().rightSideView(root);
        then(actual).containsExactly(1, 3);
    }

    @Test
    void rightSideView3() {
        TreeNode root = buildTreeFromBFS(new Integer[] {});
        List<Integer> actual = new BinTreeRightSideView().rightSideView(root);
        then(actual).containsExactly();
    }
}