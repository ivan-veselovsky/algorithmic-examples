package edu.findpairoffailingtests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;

class FindPairOfFailingTestsTest {

    @ParameterizedTest
    @MethodSource("cases")
    void testCases(int totalTests, int firstIndex, int secondIndex) {
        FindPairOfFailingTests.ArrayRunTestsFunction function = new FindPairOfFailingTests.ArrayRunTestsFunction(
                totalTests, firstIndex, secondIndex);

        int[] actualResult = FindPairOfFailingTests.solve(totalTests, function);
        then(actualResult).containsExactlyInAnyOrder(firstIndex, secondIndex);
    }

    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of(12, 1, 7),
                Arguments.of(12, 3, 4),
                Arguments.of(12, 0, 1),
                Arguments.of(12, 10, 11),
                Arguments.of(12, 0, 11),
                Arguments.of(2, 0, 1),
                Arguments.of(3, 0, 2),
                Arguments.of(3, 1, 2),
                Arguments.of(3, 0, 1)
        );
    }
}