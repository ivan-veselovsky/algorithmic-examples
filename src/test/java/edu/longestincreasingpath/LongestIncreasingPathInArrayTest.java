package edu.longestincreasingpath;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

class LongestIncreasingPathInArrayTest {

    @ParameterizedTest
    @MethodSource("cases")
    public void should_correctly_find_max_path_length(int[] nums, int expectedLength) {
        int actualLength = new LongestIncreasingPathInArray().lengthOfLIS(nums);
        then(actualLength).isEqualTo(expectedLength);
    }

    static List<Arguments> cases() {
        return List.of(
             Arguments.of(new int[] {1, 7, 8, 5}, 3),
             Arguments.of(new int[] {0, 2, 4, 8, 7, 5, 4, 3, 2, 1, 0}, 4),
             Arguments.of(new int[] {2,4,6,8,10, 1,2,3,4,5,6}, 6),
             Arguments.of(new int[] {10,9,2,5,3,7,101,18}, 4),
             Arguments.of(new int[] {10,9,2,5,3,7,101,18,4,5,0,6}, 5),
             Arguments.of(new int[] {0,9,8,7,6,5,4,3,2,1,0, 4,7,9,11}, 6),
             Arguments.of(new int[] {4,10,4,3,8,9}, 3),
             Arguments.of(new int[] {2,3,2}, 2),
             Arguments.of(new int[] {2,3,1}, 2),
             Arguments.of(new int[] {7,7,7,7,7,7,7}, 1),
             Arguments.of(new int[] {10, 20, 30,  21, 11, 1, 12, 2, 3}, 3)
        );
    }
}