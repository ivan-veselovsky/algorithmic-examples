package edu.shortestpathingraph;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.*;

import edu.shortestpathingraph.Dijkstras.*;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * Last example scheme: <img src="doc-files/Fig-11.jpg"/>
 */
class DijkstrasTest {

    @ParameterizedTest
    @MethodSource("cases")
    void main(String input, String expectedOutput) throws Exception {
        try (BufferedReader reader = new BufferedReader(new StringReader(input))) {
            String actual = Dijkstras.solveAllQueries(reader);
            then(actual).isEqualTo(expectedOutput);
        }
    }

    static List<Arguments> cases() {
        return List.of(
                Arguments.of("2\n" +
                                "4 2\n" +
                                "1 2\n" +
                                "1 3\n" +
                                "1\n" +
                                "3 1\n" +
                                "2 3\n" +
                                "2",
                        "0 6 6 _ \n" +
                                "_ 0 6 \n"),
                Arguments.of("1\n" +
                                "6 4\n" +
                                "1 2\n" +
                                "2 3\n" +
                                "3 4\n" +
                                "1 5\n" +
                                "1\n",
                        "0 6 12 18 6 _ \n"),
                Arguments.of("1\n" +
                                "7 4\n" +
                                "1 2\n" +
                                "1 3\n" +
                                "3 4\n" +
                                "2 5\n" +
                                "2",
                        "6 0 12 18 6 _ _ \n"),
                Arguments.of("1\n" +
                                "4 2\n" +
                                "1 2\n" +
                                "1 3\n" +
                                "4\n",
                        "_ _ _ _ \n"),
                // NB: 2 duplicated edges left there, as they appear in original testcase.
                Arguments.of("1\n" +
                        "10 6\n" +
                        "3 1\n" +
                        "10 1\n" +
                        "10 1\n" +
                        "3 1\n" +
                        "1 8\n" +
                        "5 2\n" +
                        "3",
                        "6 _ 0 _ _ _ _ 12 _ 12 \n"),
                // Example from https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/ :
                Arguments.of("1\n" +
                                "9 14\n" +
                                "1 2 4\n" +
                                "1 8 8\n" +
                                "2 8 11\n" +
                                "2 3 8\n" +
                                "3 4 7\n" +
                                "3 6 4\n" +
                                "3 9 2\n" +
                                "4 6 14\n" +
                                "4 5 9\n" +
                                "5 6 10\n" +
                                "6 7 2\n" +
                                "7 9 6\n" +
                                "7 8 1\n" +
                                "8 9 7\n" +
                                "1",
                        "0 4 12 19 21 11 9 8 14 \n")
                );
    }

    @Test
    void random_matrix() {
        val numberOfVertices = 100;
        val random = new Random(12345L);

        Set<Dijkstras.Edge>[] edges = new Set[numberOfVertices];
        for (int i = 0; i<numberOfVertices; i++) {
            edges[i] = getRandomEdgeSet(i, numberOfVertices, random,
             numberOfVertices, 1);
        }

        Dijkstras.Graph graph = new Dijkstras.Graph(edges, 0);
        System.out.println("num vertices = " + numberOfVertices);
        System.out.println("num edges = " + graph.getNumberOfEdges());

        String result = Dijkstras.dijkstrasAlgorithm(graph);

        printRelaxCounts(graph);

        System.out.println(result);
    }

    private void printRelaxCounts(Graph graph) {
        SortedSet<Node> histogram = new TreeSet<>((Node n1, Node n2) -> {
            if (n1 == n2) {
                return 0;
            }
            int diff = n1.getRelaxCount() - n2.getRelaxCount();
            if (diff != 0) {
                return diff;
            }
            diff = n1.getIndex() - n2.getIndex();
            if (diff != 0) {
                return diff;
            }
            throw new IllegalStateException();
        });
        histogram.addAll(graph.getNodes());
        System.out.println(" ======= Relax Counts: ");
        histogram.stream().forEachOrdered(n -> {
            System.out.println(n + "    rlx cnt = " + n.getRelaxCount());
            assert n.getRelaxCount() <= 1;
        });
    }

    private Set<Dijkstras.Edge> getRandomEdgeSet(int fromNode, int numVertices, Random random,
                                                 int heavyWeight, int lightWeight) {
        Set<Dijkstras.Edge> set = new HashSet<>();
        for (int i = 1; i < numVertices; i++) {
            if (i != fromNode) { // let it be nonreflexive
                boolean connectsToI = (random.nextInt(100) < 7); // 21
                boolean heavyEdge = (random.nextInt(100) < 90);
                if (connectsToI) {
                    int weight1 = heavyEdge ? heavyWeight : lightWeight;
                    set.add(new Dijkstras.Edge(i, weight1));
                }
            }
        }
        System.out.println(fromNode + " -> " + set);
        return Set.copyOf(set);
    }
}