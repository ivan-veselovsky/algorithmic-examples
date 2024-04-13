package edu.right_side_view;

import edu.common.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static edu.common.TreeNodeUtils.buildTreeFromBFS;
import static org.assertj.core.api.BDDAssertions.then;

class BinTreeRightSideView2Test {

    @Test
    void rightSideView1() {
        TreeNode root = buildTreeFromBFS(new Integer[] {1,2,3,null,5,null,4});
        List<Integer> actual = new RightSideView2().rightSideView(root);
        then(actual).containsExactly(1, 3, 4);
    }

    @Test
    void rightSideView2() {
        TreeNode root = buildTreeFromBFS(new Integer[] {1,null,3});
        List<Integer> actual = new RightSideView2().rightSideView(root);
        then(actual).containsExactly(1, 3);
    }

    @Test
    void rightSideView3() {
        TreeNode root = buildTreeFromBFS(new Integer[] {});
        List<Integer> actual = new RightSideView2().rightSideView(root);
        then(actual).containsExactly();
    }

    @Test
    void case4() {
        TreeNode root = buildTreeFromBFS(new Integer[] {1,
                2,3,
                4,5,null,7,
                8,9, null, null,  null,null,null,null,
                16, null, 18,19, null,null,null,null, null,null,null,null, null,null,null,null,
                32, //null,null,null,null, null,null, 66, //null, null, null, 44,
            });
        List<Integer> actual = new RightSideView2().rightSideView(root);
        then(actual).containsExactly(1,3,7,9,19,32);
    }
}