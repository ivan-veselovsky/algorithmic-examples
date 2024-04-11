package edu.common;

import org.apache.commons.lang3.tuple.Pair;

import java.util.LinkedList;
import java.util.List;

import static edu.common.TreeNodeUtils.buildTreeFromBFS0;
import static edu.common.TreeNodeUtils.traverseInBreadth;

public class NodeUtils {

    /** NB: this is the format used in leetcode to check Node-class trees.
     * Note that this format is not suitable to represent arbitrary tree of Node class!
     * NB2: nulls in the output represent null "next" references of Node class, not
     * missing nodes in BFS traversal! */
    public static Integer[] asHashSeparatedNodeList(Node root) {
        final LinkedList<Pair<Node, Integer>> list = new LinkedList<>();
        traverseInBreadth(root, (x, bfsIndex) -> {
            list.add(Pair.of(x, bfsIndex));
        });

        List<Node> result = new LinkedList<>();
        Node previous = null;
        for (Pair<Node, Integer> pair: list) {
            Node node = pair.getLeft();
            if (previous != null) {
                if (previous.next != node) {
                    throw new IllegalArgumentException("Expected " + previous + " to refer to " + node + ", but that was not the case.");
                }
            }
            result.add(node);
            if (node.next == null) {
                result.add(null); // "hash" symbol in leetcode
                previous = null;
            } else {
                previous = node;
            }
        }

        return result.stream().map(n -> (n == null) ? null : n.val()).toArray(Integer[]::new);
    }

    public static Node buildTreeFromBFS_Node(Integer[] nullFilledBFS) {
        return buildTreeFromBFS0(nullFilledBFS, Node::new);
    }

}
