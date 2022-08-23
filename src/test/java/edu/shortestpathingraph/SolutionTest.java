package edu.shortestpathingraph;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * Last example scheme: <img src="doc-files/Fig-11.jpg"/>
 */
class SolutionTest {

    @ParameterizedTest
    @MethodSource("cases")
    void main(String input, String expectedOutput) throws Exception {
        try (BufferedReader reader = new BufferedReader(new StringReader(input))) {
            String actual = Solution.solveAllQueries(reader);
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
                        "6 6 -1 \n" +
                                "-1 6 \n"),
                Arguments.of("1\n" +
                                "6 4\n" +
                                "1 2\n" +
                                "2 3\n" +
                                "3 4\n" +
                                "1 5\n" +
                                "1\n",
                        "6 12 18 6 -1 \n"),
                Arguments.of("1\n" +
                                "7 4\n" +
                                "1 2\n" +
                                "1 3\n" +
                                "3 4\n" +
                                "2 5\n" +
                                "2",
                        "6 12 18 6 -1 -1 \n"),
                Arguments.of("1\n" +
                                "4 2\n" +
                                "1 2\n" +
                                "1 3\n" +
                                "4\n",
                        "-1 -1 -1 \n"),
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
                        "6 -1 -1 -1 -1 -1 12 -1 12 \n"),
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
                        "4 12 19 21 11 9 8 14 \n")
                );
    }
}