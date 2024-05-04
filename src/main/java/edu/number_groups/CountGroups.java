package edu.number_groups;

import java.util.*;

class CountGroups {

    public static int countGroups(List<String> related) {
        final int numNodes = related.get(0).length();

        // 1. make matrix
        matrix = new boolean[related.size()][];
        Iterator<String> it = related.iterator();
        int index = 0;
        while (it.hasNext()) {
            String bin = it.next();
            matrix[index] = toBoolArray(index, bin);
            index++;
        }

        // 2. make it symmetric:
        for (int y=0; y<matrix.length; y++) {
            boolean[] row = matrix[y];
            for (int x = 0; x<row.length; x++) {
                if (row[x]) {
                    matrix[x][y] = true;
                }
            }
        }

        // 3. count groups
        visitedNodes = new boolean[numNodes];
        int groupCount = 0;
        int summaryNodeCount = 0;
        for (int i=0; i<numNodes; i++) {
            int count = bfs(i);
            if (count > 0) {
                groupCount++;
            }
            summaryNodeCount += count;
            if (summaryNodeCount == numNodes) {
                break;
            }
        }
        return groupCount;
    }

    static boolean[][] matrix;
    static boolean[] visitedNodes;

    static int bfs(int startNode) {
        if (visitedNodes[startNode]) {
            return 0;
        }
        Deque<Integer> queue = new ArrayDeque<>();
        int count = 0;
        queue.offer(startNode);
        while (!queue.isEmpty()) {
            Integer x = queue.poll();
            if (!visitedNodes[x]) {
                visitedNodes[x] = true; // mark

                count++;

                // add children:
                boolean[] row = matrix[x];
                for (int i=0; i<row.length; i++) {
                    if (row[i]) {
                        queue.offer(i);
                    }
                }
            }
        }
        return count;
    }

    static boolean[] toBoolArray(int selfIndex, String bin) {
        boolean[] bb = new boolean[bin.length()];
        char[] cc = bin.toCharArray();
        for (int i=0; i<cc.length; i++) {
            if (cc[i] == '1' && i != selfIndex) {
                bb[i] = true;
            }
        }
        return bb;
    }
}
