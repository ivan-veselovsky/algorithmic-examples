package edu.findlongestpath;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;

class FindLongestPathInBinaryTree_SolutionWithO1CacheTest {

    @ParameterizedTest
    @MethodSource("cases")
    void execute_all_cases(String caseDescription, String tree, int expectedLongestPath) {
        // Given
        Node root = parseTree(tree);
        // When
        int longestPathLength1 = FindLongestPathInBinaryTree_SolutionWithO1Cache.findLongestPath(root);
        //int longestPathLength2 = InterviewSolution.findLongestPath(root);
        // Then
        then(longestPathLength1).describedAs(caseDescription).isEqualTo(expectedLongestPath);
        //then(longestPathLength2).describedAs(caseDescription).isEqualTo(expectedLongestPath);
    }

    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of("Empty tree", "o", 0),
                Arguments.of("One child", "o" +
                                                    "_ o", 1),
                Arguments.of("Two children", "o" +
                                                      "o  o", 2),
                Arguments.of("3 levels, asymmetric", "o" +
                                                              "o  o" +
                                                             "o o", 3),
                Arguments.of("4 levels", "o" +
                                                  "o  o" +
                                                 "_ o _ o" +
                                                "__ o_ __ o_", 6),
                Arguments.of("4 levels, maximum not in the root", "o" +
                                                                           "o  _" +
                                                                         "o o _ _" +
                                                                       "_ o o", 4),
                Arguments.of("5 levels, full tree", "o" +
                                                             "o  o" +
                                                            "o o o o" +
                                                           "oo oo oo oo"+
                                                         "oooo oooo oooo oooo", 8)
        );
    }

    static Node parseTree(String treeAsFigure) {
         int[] array = treeAsFigure
                 .replaceAll("\\s+", "")
                 .chars()
                 .map(x -> (x == 'o') ? 1 : 0)
                 .toArray();
         Node[] nodes = new Node[array.length];
         for (int i = array.length - 1; i >= 0; i--) {
             if (array[i] > 0) {
                 nodes[i] = new Node(/* i, */
                        safeGetNode(nodes, leftChildIndex(i)),
                        safeGetNode(nodes, rightChildIndex(i)));
             }
         }
         return nodes[0]; // the root
    }

    static Node safeGetNode(Node[] nodes, int index) {
        if (index < 0 || index >= nodes.length) {
            return null;
        }
        return nodes[index];
    }

    static int leftChildIndex(int i) {
        return ((i + 1) << 1) - 1;
    }
    static int rightChildIndex(int i) {
        return (i + 1) << 1;
    }

}