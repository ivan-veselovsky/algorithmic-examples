package edu.word_matrix;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class MaxNumberOfIdenticalRowsTest {

   @Test
   void read_data() {
      final MaxNumberOfIdenticalRows max = new MaxNumberOfIdenticalRows();

      val count = max.readClasspathResource("example_1.lp");

      then(max.getHeight()).isEqualTo(40);
      then(max.getWidth()).isEqualTo(60);

      then(max.getK()).isEqualTo(17);

      then(count).isEqualTo(max.getHeight() * max.getWidth());

      for (int y=0; y<max.getHeight(); y++) {
         for (int x=0; x<max.getWidth(); x++) {
            char ch = max.getMatrix()[y][x];
            System.out.print(ch + " ");
         }
         System.out.println();
      }
   }

   @Test
   void same_cardinalities_in_column() {
      char[] charColumn = "abcabcabcde".toCharArray();
      Solution.RankedColumn rankedColumn = new Solution().createNormalizedColumn(15, charColumn);

      then(rankedColumn.isAllOnes()).isFalse();
      then(rankedColumn.numberOfZeroes()).isEqualTo(2);
      then(rankedColumn.originalIndex()).isEqualTo(15);
      then(rankedColumn.maxCardinality()).isEqualTo(3);

      int[] expectedRanks = new int[] { 1, 2, 3,   1, 2, 3,  1, 2, 3,   0, 0 };
      for (int i = 0; i < charColumn.length ; i++) {
         then(rankedColumn.getRank(i)).isEqualTo(expectedRanks[i]);
      }
   }

   @Test
   void solve_example_0() {
      // TODO: investigational test, add assertions
      final MaxNumberOfIdenticalRows max = new MaxNumberOfIdenticalRows();
      val count = max.readClasspathResource("example_0.lp");
      System.out.println("k = " + max.getK());

      for (int y=0; y<max.getHeight(); y++) {
         for (int x=0; x<max.getWidth(); x++) {
            char ch = max.getMatrix()[y][x];
            System.out.print(ch + " ");
         }
         System.out.println();
      }
      System.out.println("==============================");

      Solution solution = new Solution();
      solution.solve(max.getMatrix());
   }

   @Test
   void solve_example_1() {
      // TODO: investigational test, add assertions
      final MaxNumberOfIdenticalRows max = new MaxNumberOfIdenticalRows();
      val count = max.readClasspathResource("example_1.lp");

      Solution solution = new Solution();
      solution.solve(max.getMatrix());
   }
}