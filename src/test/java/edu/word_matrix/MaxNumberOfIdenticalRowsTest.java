package edu.word_matrix;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class MaxNumberOfIdenticalRowsTest {

   @Test
   void read_data() {
      final MaxNumberOfIdenticalRows max = new MaxNumberOfIdenticalRows();

      val count = max.read("/media/ivan/ext4-data/olesya/example_instance (1).lp");

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
   void solve() {
      final MaxNumberOfIdenticalRows max = new MaxNumberOfIdenticalRows();
      val count = max.read("/media/ivan/ext4-data/olesya/example_instance (1).lp");

      Solution solution = new Solution();
      solution.solve(max.getMatrix());
   }
}