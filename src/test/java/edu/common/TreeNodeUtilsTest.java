package edu.common;

import org.junit.jupiter.api.Test;

import static edu.common.TreeNodeUtils.asNullFilledBFS;
import static edu.common.TreeNodeUtils.buildTreeFromBFS;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

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

    @Test
    void null_root() {
        Integer[] tree = new Integer[] { null,
                2,3,
                4,     null, null,          7,
                8, null, null, null, 51, 52, null, null,
                16, null};
        thenThrownBy( () -> buildTreeFromBFS(tree))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Null root element.");
    }

    @Test
    void trailing_null_1() {
        Integer[] tree = new Integer[] { 1,
                2,3,
                4,     null, null,          7,
                8, null, null, null, 51, 52, null, null,
                16, null};
        thenThrownBy( () -> buildTreeFromBFS(tree))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Trailing null at index 16");
    }

    @Test
    void orphan_in_the_middle() {
        Integer[] tree = new Integer[] { 1,
                   2,3,
                4,     null, null,          7,
                8, null, null, null, 51, 52, null, null,
                16};
        thenThrownBy( () -> buildTreeFromBFS(tree))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Orphan node detected at index 11, val = 51, parent index = 5");
    }

    @Test
    void orphan_in_the_end() {
        Integer[] tree = new Integer[] { 1,
                2,3,
                4,     null, null,          7,
                8, null, null, null, null, null, null, null,
                16, null, null, 88};
        thenThrownBy( () -> buildTreeFromBFS(tree))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Orphan node detected at index 18, val = 88, parent index = 8");
    }
}