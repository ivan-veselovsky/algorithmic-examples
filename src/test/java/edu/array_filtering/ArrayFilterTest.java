package edu.array_filtering;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.copyOfRange;
import static org.assertj.core.api.BDDAssertions.then;

class ArrayFilterTest {

   @ParameterizedTest
   @CsvSource({
          "1,                  1",
          "3 3 4 5 7 7 7,      3 4 5 7",
          "1 1 1 1,            1",
          "2 2 3 4 8 7 7,      2 3 4 8 7",
          "0 0 0 0 0 7 7 7,    0 7",
          "0 7 7 7 7,          0 7",
          "1 1 1 1 8,          1 8",
          "1 2 3 4 5 6 7,      1 2 3 4 5 6 7",
   })   void filter(String inputArrayStr, String expectedArrayStr) {
      int[] inputArray = parseArray(inputArrayStr);
      Integer[] expectedArray = parseArrayBoxed(expectedArrayStr);

      int newLength = new ArrayFilter().filter(inputArray);

      then(newLength).isEqualTo(expectedArray.length);
      int[] actualArray = copyOfRange(inputArray, 0, newLength);
      List<Integer> actualList = Arrays.stream(actualArray).boxed().toList();
      then(actualList).containsExactly(expectedArray);
   }


   private int[] parseArray(String arrayStr) {
      return Arrays.stream(arrayStr.split("\\s+")).mapToInt(Integer::parseInt).toArray();
   }

   private Integer[] parseArrayBoxed(String arrayStr) {
      return Arrays.stream(arrayStr.split("\\s+")).map(Integer::parseInt).toArray(Integer[]::new);
   }

}