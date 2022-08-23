package edu.shortestpathingraph;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

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
                        "6 -1 -1 -1 -1 -1 12 -1 12 \n")
                );
    }
}