package edu.shortestpathingraph;

import lombok.*;

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
 * The first line contains an integer, the number of queries.
 *
 * Each of the following  sets of lines is as follows:
 *
 * The first line contains two space-separated integers,  and , the number of nodes and the number of edges.
 * Each of the next lines contains two space-separated integers,  and , describing an edge connecting node to node.
 * The last line contains a single integer, the index of the starting node.
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
public class Dijkstras {
    static final int defaultEdgeWeight = 6;

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
        final int vertices = verticesAndEdgesArr[0];
        final int edges = verticesAndEdgesArr[1];
        final Set<Edge>[] graph = new Set[vertices];
        for (int e=0; e<edges; e++) {
            int[] vertexFromAndTo = readTwoInts(reader);
            subtractOne(vertexFromAndTo);
            assert vertexFromAndTo[0] < vertices;
            assert vertexFromAndTo[1] < vertices;
            int weight = (vertexFromAndTo.length > 2) ? vertexFromAndTo[2] : defaultEdgeWeight;
            boolean addedPrimary = addEdge(graph, vertexFromAndTo[0], vertexFromAndTo[1], weight);
            if (!addedPrimary) {
                System.err.println("Duplicate edge: (" + vertexFromAndTo[0] + ", " + vertexFromAndTo[1] + ")");
            }
            addEdge(graph, vertexFromAndTo[1], vertexFromAndTo[0], weight);
        }

        int startVertex = readOneInt(reader) - 1;
        assert startVertex < vertices;

        return new Graph(graph, startVertex);
    }

    private static void subtractOne(int[] xy) {
        xy[0] -= 1;
        xy[1] -= 1;
    }

    @RequiredArgsConstructor
    @Getter
    static class Graph {
        // TODO: replace array with immutable List
        private final Set<Edge>[] edges; // 0-based

        private final List<Node> nodes; // 0-based
        private final int startVertex;
        int getNumberOfEdges() {
            return Arrays.stream(edges).filter(Objects::nonNull).mapToInt(Set::size).sum();
        }
        int getMaxEdgeWeight() {
            return Arrays.stream(edges).filter(Objects::nonNull).flatMap(Set::stream).mapToInt(e -> e.weight).max().getAsInt();
        }
        int numberOfVertices() {
            return edges.length;
        }
        Graph(Set<Edge>[] edges, int startVertex) {
            this.edges = edges;
            this.nodes = createNodes();
            this.startVertex = startVertex;
        }
        private List<Node> createNodes() {
            final Node[] nodes = new Node[edges.length];
            for (int i=0; i<edges.length; i++) {
                nodes[i] = new Node(i);

                Set<Edge> adjacencySet = edges[i];
                if (adjacencySet != null && !adjacencySet.isEmpty()) {
                    for (Edge edge: adjacencySet) {
                        assert edge.toNode < edges.length;
//                        if (nodes[edge.toNode] == null) {
//                            nodes[edge.toNode] = new Node(edge.toNode); // to Node
//                        }
                    }
                }
            }
            return List.of(nodes);
        }
    }

    static boolean addEdge(Set<Edge>[] graph, int vertexFrom, int vertexTo, int weight) {
        Set<Edge> edges = graph[vertexFrom];
        if (edges == null) {
            edges = new HashSet<>();
            graph[vertexFrom] = edges;
        }
        boolean added = edges.add(new Edge(vertexTo, weight));
        return added;
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

        if (verticesAndEdgesArr.length > 2) {
            int weight = Integer.parseInt(verticesAndEdgesArr[2]);
            return new int[] { int1, int2, weight };
        } else {
            return new int[] { int1, int2 };
        }
    }

    private static String solveGraph(Graph graph) {
        return dijkstrasAlgorithm(graph);
    }

    @EqualsAndHashCode
    @RequiredArgsConstructor
    static class Edge {
        final int toNode;
        @EqualsAndHashCode.Exclude
        final int weight;

        @Override
        public String toString() {
            return String.valueOf(toNode);
        }
    }

    @RequiredArgsConstructor
    @Getter
    static class Node {
        final int index;
        private Integer distanceFromStart;
        private int relaxCount;

        boolean canRelaxTo(int newDistance) {
            return distanceFromStart == null || newDistance < distanceFromStart;
        }

        void relax(int newDistance) {
            assert distanceFromStart == null || newDistance < distanceFromStart;
            System.out.println("    rlx " + index + " " + distanceFromStart + " -> " + newDistance);
            if (distanceFromStart != null) {
                relaxCount++;
            }
            distanceFromStart = newDistance;
        }

        @Override
        public String toString() {
            if (distanceFromStart == null) {
                return String.valueOf(index);
            } else {
                return index + " d=" + distanceFromStart + " rlxCnt=" + relaxCount;
            }
        }
    }

    private static int compareTwoNullables(Integer x, Integer y) {
        if (x == null || y == null) {
            if (x == null && y == null) {
                return 0;
            }
            return (x == null) ? 1 : -1;
        } else {
            return x.compareTo(y);
        }
    }

    static String dijkstrasAlgorithm(final Graph graph) {
        val maxEdgeWeight = graph.getMaxEdgeWeight();

        System.out.println("max edge weight = " + maxEdgeWeight);

        final Comparator<Node> cmp = (@NonNull Node n1, @NonNull Node n2) -> {
            if (n1 == n2) {
                return 0;
            }
            int cmp1 = compareTwoNullables(n1.distanceFromStart, n2.distanceFromStart);
            if (cmp1 != 0) {
                return cmp1;
            }
            int cmp2 = compareTwoNullables(n1.index, n2.index);
            if (cmp2 != 0) {
                return cmp2;
            }
            throw new IllegalArgumentException();
        };
        final NavigableSet<Node> nodesByDistance = new TreeSet<>(cmp);

        Node startNode = graph.nodes().get(graph.startVertex());
        startNode.distanceFromStart = 0; // start
        nodesByDistance.add(startNode); // enqueue Start node
        if (graph.edges[graph.startVertex()] == null || graph.edges[graph.startVertex()].isEmpty()) {
            return composeResult(graph.nodes(), graph.startVertex, true);
        }

        while (!nodesByDistance.isEmpty()) {
            System.out.println("Queue: " + nodesByDistance);
            final Node node = nodesByDistance.pollFirst(); // start node appears first

            Set<Edge> outgoingEdges = graph.edges[node.index];
            for (Edge edge: outgoingEdges) {
                Node adjacentNode = graph.nodes().get(edge.toNode);
                int newDistance = node.distanceFromStart + edge.weight;
                if (adjacentNode.canRelaxTo(newDistance)) {
                    boolean removed = nodesByDistance.remove(adjacentNode);
                    if (removed) {
                        //System.out.println("***** already contained in queue: " + adjacentNode);
                    }
                    checkQueue(maxEdgeWeight, nodesByDistance);

                    adjacentNode.relax(newDistance);

                    boolean added = nodesByDistance.add(adjacentNode); // *** forces the resorting after distance update
                    assert added : "Was not added: " + adjacentNode + ", queue: " + nodesByDistance;
                    checkQueue(maxEdgeWeight, nodesByDistance);
                }
            }
        }

        return composeResult(graph.nodes(), graph.startVertex, false);
    }

    private static void checkQueue(int maxEdgeWeight, SortedSet<Node> queue) {
        if (queue.size() > 1) {
            Node first = queue.first();
            Node last = queue.last();
            int diff = last.distanceFromStart() - first.distanceFromStart();
            assert diff <= maxEdgeWeight;
        }
    }

    private static String composeResult(List<Node> allNodes, int startIndex, boolean allUnreachable) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i< allNodes.size(); i++) {
                Node node = allNodes.get(i);
                assert node != null;
                String valueToDisplay = (allUnreachable || node.distanceFromStart == null)
                        ? "_" : String.valueOf(node.distanceFromStart);
                sb.append(valueToDisplay).append(" ");
        }
        return sb.toString();
    }
}