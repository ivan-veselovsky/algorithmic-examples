package edu.findlongestpath;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static edu.findlongestpath.TreeUtils.*;
import static org.assertj.core.api.BDDAssertions.then;

class FindLongestPathInBinaryTree_DijkstrasWithO1CacheTest {

    @ParameterizedTest
    @MethodSource("cases")
    void execute_all_cases(String caseDescription, String tree, int expectedLongestPath) {
        // Given
        Node root = parseTree(tree);
        printTree(root); // diagnostic

        // When
        int longestPathLength1 = FindLongestPathInBinaryTree_SolutionWithO1Cache.findLongestPath(root);
        // Then
        then(longestPathLength1).describedAs(caseDescription).isEqualTo(expectedLongestPath);
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

}