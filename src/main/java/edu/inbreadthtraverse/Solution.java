package edu.inbreadthtraverse;

import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String result = mainImpl(reader);
            System.out.println(result);
        }
    }

    static String mainImpl(BufferedReader reader) throws Exception {
        String numberOfNodesIgnored = reader.readLine().trim();
        String nodesStr = reader.readLine().trim();

        int[] nodes = Arrays
                .stream(nodesStr.split("\\s+"))
                .filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt)
                .toArray();

        final Node root = new Node(nodes[0], null, null);

        int depth = Arrays.stream(nodes).skip(1).map(v -> addNodeGetDepth(root, v)).max().orElse(0);

        Deque<Node> queue = new LinkedList<>();
        queue.addFirst(root);
        StringBuilder sb = new StringBuilder();
        while (true) {
            Node node = queue.pollLast();
            if (node == null) {
                break;
            }

            sb.append(node.value).append(' ');

            if (node.left != null) {
                queue.addFirst(node.left);
            }
            if (node.right != null) {
                queue.addFirst(node.right);
            }
        }

        return sb.toString();
    }

    static class Node {
        final int value;
        Node left;
        Node right;
        Node(int value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    private static int addNodeGetDepth(Node root, int nextValue) {
        Node next = root;
        int depth = 0;
        while (next != null) {
            if (nextValue <= next.value) {
                if (next.left == null) {
                    next.left = new Node(nextValue, null, null);
                    next = null;
                } else {
                    next = next.left;
                }
            } else {
                if (next.right == null) {
                    next.right = new Node(nextValue, null, null);
                    next = null;
                } else {
                    next = next.right;
                }
            }
            depth++;
        }
        return depth;
    }
}