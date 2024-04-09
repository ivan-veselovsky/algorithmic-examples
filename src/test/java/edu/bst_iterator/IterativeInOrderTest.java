package edu.bst_iterator;

import edu.common.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import static edu.common.TreeNodeUtils.buildTreeFromBFS;
import static org.assertj.core.api.BDDAssertions.then;

class IterativeInOrderTest {

    @Test
    void testNext() {
        TreeNode root = buildTreeFromBFS(new Integer[] {
                0,
                1,2,
                3,4,5, null,
                null, null, 9, 10, null, null, null, null,
                null, null, null, null, 19});
        final IterativeInOrder iterativeInOrder = new IterativeInOrder();
        iterativeInOrder.setRoot(root);

        Consumer<IterativeInOrder.Ctx> preConsumer = ctx -> {};

        final List<Integer> valList = new LinkedList<>();
        Consumer<IterativeInOrder.Ctx> middleConsumer = ctx -> {
            System.out.println("Middle: "
                    + ctx.treeNode() + ", idx = " + ctx.bfsIndex() + ", direction = " + ctx.direction());
            valList.add(ctx.treeNode().val());
        };
        Consumer<IterativeInOrder.Ctx> postConsumer = ctx -> {
            System.out.println("           Post: "
                + ctx.treeNode() + ", idx = " + ctx.bfsIndex() + ", direction = " + ctx.direction());
        };

        IterativeInOrder.Direction direction = IterativeInOrder.Direction.left_down;
        do {
            direction = iterativeInOrder.next(direction, preConsumer, middleConsumer, postConsumer);
        } while (direction != null);

        then(valList).containsExactly(3, 1, 19, 9, 4, 10, 0, 5, 2);
    }
}