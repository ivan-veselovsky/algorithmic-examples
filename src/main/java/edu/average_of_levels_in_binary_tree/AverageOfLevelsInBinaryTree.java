package edu.average_of_levels_in_binary_tree;

import edu.common.RedBallArrayDeque;
import edu.common.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class AverageOfLevelsInBinaryTree  {
    public List<Double> averageOfLevels(TreeNode root) {
        final RedBallArrayDeque<TreeNode> queue = new RedBallArrayDeque<>(32);
        queue.enqueue(root);
        final List<Double> result = new ArrayList<>();
        final Accumulator accumulator = new Accumulator();
        while (!queue.isEmpty()) {
            TreeNode node = queue.dequeue();

            accumulator.add(node.val());

            if (queue.getRedBallPosition() == 0) { // level processing finished:
                result.add(accumulator.getAverage());
                accumulator.reset();
            }

            if (node.left != null) {
                queue.enqueue(node.left);
            }
            if (node.right != null) {
                queue.enqueue(node.right);
            }
        }
        return result;
    }

    static class Accumulator {
        long sum;
        int count;
        void add(int value) {
            sum += value;
            count++;
        }
        void reset() {
            sum = 0L;
            count = 0;
        }
        double getAverage() {
            return ((double)sum) / count;
        }
        int count() {
            return count;
        }
    }
}

