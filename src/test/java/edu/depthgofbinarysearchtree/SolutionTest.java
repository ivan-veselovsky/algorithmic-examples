package edu.depthgofbinarysearchtree;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;

class SolutionTest {

    @ParameterizedTest
    @MethodSource("cases")
    void testCases(String input, int expectedDepth) throws Exception {
        try (BufferedReader reader = new BufferedReader(new StringReader(input))) {
            int depth = Solution.mainImpl(reader);
            then(depth).isEqualTo(expectedDepth);
        }
    }

    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of( "7\n" +
                        "3 5 2 1 4 6 7" ,  3),
                Arguments.of( "7\n" +
                        "4 2 6 1 3 5 7" ,  2)
                );
    }


}