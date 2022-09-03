package edu.findbendedword;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;

class FindWordOnCharacterGridTest {

    static final char[][] grid0 = {
           // 0    1    2    3    4    5
            {'c', 'c', 't', 'n', 'a', 'x'}, // 0
            {'c', 'c', 'a', 't', 'n', 't'}, // 1
            {'a', 'c', 'n', 'n', 't', 't'}, // 2
            {'t', 'n', 'i', 'i', 'p', 'p'}, // 3
            {'o', 'o', 'o', 'o', 'a', 'a'}, // 4
            {'s', 'a', 'a', 'a', 'o', 'o'}, // 5
            {'k', 'a', 'i', 'o', 'm', 'i'}, // 6
            {'k', 'z', 'i', 'o', 'x', 'i'}, // 7
            {'k', 'a', 'i', 'o', 'u', 'i'}, // 8
            {'k', 'b', 'i', 'o', 'k', 'p'}, // 9
            {'k', 'b', 'i', 'o', 'k', 'i'}, // 10
    };

    @ParameterizedTest
    @MethodSource("cases")
    void execute_all_cases(String caseDescription, String word, List<String> expectedPaths) {
        // given
        FindWordOnCharacterGrid findWordOnCharacterGrid = new FindWordOnCharacterGrid(grid0);
        // when
        List<FindWordOnCharacterGrid.Point> lastPoints = findWordOnCharacterGrid.findWordPositions(word);
        List<String> actualResult = lastPoints.stream().map(FindWordOnCharacterGrid.Point::deepToString).toList();
        // then
        then(actualResult).describedAs(caseDescription).containsExactlyInAnyOrderElementsOf(expectedPaths);
    }

    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of("Generic case, one result", "catnip",
                        List.of("(4, 3) -> (3, 3) -> (3, 2) -> (3, 1) -> (2, 1) -> (1, 1)")),
                Arguments.of("Same character repeated, two results", "cccc",
                        List.of(
                        "(1, 2) -> (1, 1) -> (0, 1) -> (0, 0)",
                        "(1, 2) -> (1, 1) -> (1, 0) -> (0, 0)")),
                Arguments.of("length 1, one result", "s", List.of("(0, 5)")),
                Arguments.of("generic sample, two overlapping results", "ant",
                        List.of("(4, 2) -> (4, 1) -> (4, 0)", "(5, 1) -> (4, 1) -> (4, 0)")),
                Arguments.of("One vertical match", "aoi", List.of(
                        "(5, 6) -> (5, 5) -> (5, 4)")),
                Arguments.of("Horizontal match in the right lower corner", "ki",
                        List.of("(5, 10) -> (4, 10)")),
                Arguments.of("Two couples  of repeating character", "aaoo", List.of(
                        "(5, 5) -> (4, 5) -> (3, 5) -> (2, 5)",
                        "(3, 7) -> (3, 6) -> (3, 5) -> (2, 5)")),
                Arguments.of("Same character repeated, several overlapping matches, both horizontal & vertical",
                        "ooo",
                        List.of(
                            "(2, 4) -> (1, 4) -> (0, 4)",
                            "(3, 4) -> (2, 4) -> (1, 4)",
                            "(3, 8) -> (3, 7) -> (3, 6)",
                            "(3, 9) -> (3, 8) -> (3, 7)",
                            "(3, 10) -> (3, 9) -> (3, 8)"))
        );
    }

}