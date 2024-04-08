package edu.count_nodes_in_quasi_full_bin_tree;

import edu.common.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.function.UnaryOperator;

import static edu.common.TreeNodeUtils.buildTreeFromBFS;
import static org.assertj.core.api.BDDAssertions.then;

class CountNodesInQuasiFullTreeTest {

    @Test
    void test6() {
        TreeNode tree = buildTreeFromBFS(new Integer[] { 1,2,3,4,5,6 });
        int result = new CountNodesInQuasiFullTree().countNodes(tree);
        then(result).isEqualTo(6);
    }


    @Test
    void test3() {
        TreeNode tree = buildTreeFromBFS(new Integer[] { 1,2,3 });
        int result = new CountNodesInQuasiFullTree().countNodes(tree);
        then(result).isEqualTo(3);
    }

    @Test
    void test4() {
        TreeNode tree = buildTreeFromBFS(new Integer[] { 1,2,3,4 });
        int result = new CountNodesInQuasiFullTree().countNodes(tree);
        then(result).isEqualTo(4);
    }

    @Test
    void test5() {
        TreeNode tree = buildTreeFromBFS(new Integer[] { 1,2,3,4,5 });
        int result = new CountNodesInQuasiFullTree().countNodes(tree);
        then(result).isEqualTo(5);
    }

    @Test
    void testN() {
        for (int i = 0; i < 51; i++) {
            Integer[] nodes = new Integer[i];
            fillArray(nodes, x -> x);

            TreeNode tree = buildTreeFromBFS(nodes);
            int result = new CountNodesInQuasiFullTree().countNodes(tree);
            then(result).isEqualTo(i);
        }
    }

    public static void fillArray(Integer[] arr, UnaryOperator<Integer> fillFunction) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = fillFunction.apply(i);
        }
    }
}