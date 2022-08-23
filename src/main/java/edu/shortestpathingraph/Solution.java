package edu.shortestpathingraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * https://www.hackerrank.com/challenges/ctci-bfs-shortest-reach/problem
 *
 * Starting from node  and creating a list of distances, for nodes  through  we have .
 *
 * Function Description
 *
 * Define a Graph class with the required methods to return a list of distances.
 *
 * Input Format
 *
 * The first line contains an integer, , the number of queries.
 *
 * Each of the following  sets of lines is as follows:
 *
 * The first line contains two space-separated integers,  and , the number of nodes and the number of edges.
 * Each of the next  lines contains two space-separated integers,  and , describing an edge connecting node  to node .
 * The last line contains a single integer, , the index of the starting node.
 * Constraints
 *
 * Output Format
 *
 * For each of the  queries, print a single line of  space-separated integers denoting the shortest distances to each of the  other nodes from starting position . These distances should be listed sequentially by node number (i.e., ), but should not include node . If some node is unreachable from , print  as the distance to that node.
 *
 * Sample Input
 *
 * 2
 * 4 2
 * 1 2
 * 1 3
 * 1
 * 3 1
 * 2 3
 * 2
 * Sample Output
 *
 * 6 6 -1
 * -1 6
 */
public class Solution {

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String solution = solveAllQueries(reader);
            System.out.println(solution);
        }
    }

    static String solveAllQueries(BufferedReader reader) throws IOException {
        final int numQueries = readOneInt(reader);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numQueries; i++) {
            String solution = solveOneQuery(reader);
            sb.append(solution).append("\n");
        }
        return sb.toString();
    }

    static String solveOneQuery(BufferedReader reader) throws IOException {
        Graph graph = parseGraph(reader);
        return solveGraph(graph);
    }

    static Graph parseGraph(final BufferedReader reader) throws IOException {
        int[] verticesAndEdgesArr = readTwoInts(reader);
        int vertices = verticesAndEdgesArr[0];
        int edges = verticesAndEdgesArr[1];
        final Set<Integer>[] graph = new Set[vertices + 1];
        for (int e=0; e<edges; e++) {
            int[] vertexFromAndTo = readTwoInts(reader);
            assert vertexFromAndTo[0] <= vertices;
            assert vertexFromAndTo[1] <= vertices;
            boolean addedPrimary = addEdge(graph, vertexFromAndTo[0], vertexFromAndTo[1]);
            if (!addedPrimary) {
                System.err.println("Duplicate edge: (" + vertexFromAndTo[0] + ", " + vertexFromAndTo[1] + ")");
            }
            addEdge(graph, vertexFromAndTo[1], vertexFromAndTo[0]);
        }

        int startVertex = readOneInt(reader);

        return new Graph(graph, startVertex);
    }

    static class Graph {
        final Set<Integer>[] edges;
        final int startVertex;

        public Graph(Set<Integer>[] edges, int startVertex) {
            this.edges = edges;
            this.startVertex = startVertex;
        }
    }

    static boolean addEdge(Set<Integer>[] graph, int vertexFrom, int vertexTo) {
        Set<Integer> outgoingFrom = graph[vertexFrom];
        if (outgoingFrom == null) {
            outgoingFrom = new HashSet<>();
            graph[vertexFrom] = outgoingFrom;
        }
        return outgoingFrom.add(vertexTo);
    }

    private static int readOneInt(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        return Integer.parseInt(line);
    }

    private static int[] readTwoInts(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        String[] verticesAndEdgesArr = line.split("\\s+");
        int int1 = Integer.parseInt(verticesAndEdgesArr[0]);
        int int2 = Integer.parseInt(verticesAndEdgesArr[1]);
        return new int[] { int1, int2 };
    }

    private static String solveGraph(Graph graph) {
        return dijkstrasAlgorithm(graph);
    }

    static class Node {
        final int index;
        private int distanceFromStart = Integer.MAX_VALUE;

        public Node(int index) {
            this.index = index;
        }
    }

    private static String dijkstrasAlgorithm(final Graph graph) {
        final int edgeWeight = 6;

        final NavigableSet<Node> nodesByDistance = new TreeSet<>(Comparator
                .comparingInt((Node n) -> n.distanceFromStart)
                .thenComparingInt((Node n) -> n.index)
                .thenComparing((Node n1, Node n2) -> {
                    if (n1 == n2) {
                        return 0;
                    }
                    throw new IllegalStateException("must never go there.");
                })
        );

        final Node[] allNodes = new Node[graph.edges.length];

        for (int i=1; i < graph.edges.length; i++) {
            Set<Integer> set = graph.edges[i];
            if (set == null) {
                // unreachable node:
                Node node = new Node(i);
                node.distanceFromStart = -1;
                allNodes[i] = node;
                if (i == graph.startVertex) {
                    // unreachable start node:
                    node.distanceFromStart = 0;
                    return composeResult(allNodes, graph.startVertex, true);
                }
            } else {
                Node node = new Node(i);
                if (i == graph.startVertex) {
                    node.distanceFromStart = 0; // start
                }
                nodesByDistance.add(node);
                allNodes[i] = node;
            }
        }

        while (!nodesByDistance.isEmpty()) {
            Node node = nodesByDistance.pollFirst(); // start node appears first

            if (node.distanceFromStart != Integer.MAX_VALUE) {
                Set<Integer> adjacentNodesSet = graph.edges[node.index];
                for (int adjacentIdx : adjacentNodesSet) {
                    Node adjNode = allNodes[adjacentIdx];
                    int newDistance = node.distanceFromStart + edgeWeight;
                    if (newDistance < adjNode.distanceFromStart) {
                        boolean rm = nodesByDistance.remove(adjNode); // ***
                        assert rm;
                        adjNode.distanceFromStart = newDistance;
                        boolean added = nodesByDistance.add(adjNode); // *** forces the resorting after distance update
                        assert added;
                    }
                }
            }
        }

        return composeResult(allNodes, graph.startVertex, false);
    }

    private static String composeResult(Node[] allNodes, int startIndex, boolean allUnreachable) {
        StringBuilder sb = new StringBuilder();
        for (int i=1; i< allNodes.length; i++) {
            if (i != startIndex) {
                Node node = allNodes[i];
                sb.append((allUnreachable || node.distanceFromStart == Integer.MAX_VALUE) ? -1 : node.distanceFromStart).append(" ");
            }
        }
        return sb.toString();
    }
}