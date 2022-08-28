package edu.depthgofbinarysearchtree;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution {

    public static void main(String[] args) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int result = mainImpl(reader);
            System.out.println(result);
        }
    }

    static int mainImpl(BufferedReader reader) throws Exception {
        String numberOfNodesIgnored = reader.readLine().trim();
        String nodesStr = reader.readLine().trim();

        int[] nodes = Arrays
                .stream(nodesStr.split("\\s+"))
                .filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt)
                .toArray();

        final Node root = new Node(nodes[0], null, null);

        return Arrays.stream(nodes).skip(1).map(v -> addNodeGetDepth(root, v)).max().orElse(0);
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