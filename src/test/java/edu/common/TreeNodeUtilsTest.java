package edu.common;

import org.junit.jupiter.api.Test;

import static edu.common.TreeNodeUtils.asNullFilledBFS;
import static edu.common.TreeNodeUtils.buildTreeFromBFS;
import static org.assertj.core.api.BDDAssertions.then;

class TreeNodeUtilsTest {

    @Test
    void test() {
        Integer[] tree = new Integer[] { 3,5,1,6,2,0,8,null,null,7,4 };
        TreeNode root = buildTreeFromBFS(tree);

        then(root).isNotNull();
        then(root.val()).isEqualTo(3);
        then(root.left().val()).isEqualTo(5);

        Integer[] dumpBack = asNullFilledBFS(root);

        then(dumpBack).contains(tree);
    }

    @Test
    void test2() {
        Integer[] tree = new Integer[] { 3, 5,1, null,2,0,null,  null,null, 7, 4, 19 };
        TreeNode root = buildTreeFromBFS(tree);

        then(root).isNotNull();
        then(root.val()).isEqualTo(3);
        then(root.left().val()).isEqualTo(5);

        Integer[] dumpBack = asNullFilledBFS(root);

        then(dumpBack).contains(tree);
    }

}