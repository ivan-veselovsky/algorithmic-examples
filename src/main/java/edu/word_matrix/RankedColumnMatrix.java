package edu.word_matrix;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RankedColumnMatrix {

   public record Row(int originalIndex, List<Integer> rowData, int numberOfZeroes, int numberOfOnes) {
      @Override
      public String toString() {
         return rowData + "/ 0s = " + numberOfZeroes + " / 1s = " + numberOfOnes + (isFullHouse() ? " ==" : "");
      }
      boolean isFullHouse() {
         return (numberOfOnes == rowData().size());
      }
   }

   public List<Row> toRowForm(List<Solution.RankedColumn> rankedColumns) {
      final int columnLength = rankedColumns.getFirst().length();
      final List<Row> rows = new ArrayList<>(columnLength);

      for (int rowIndex = 0; rowIndex < columnLength; rowIndex++) {
         List<Integer> rowData = new ArrayList<>(rankedColumns.size());
         int numZeroes = 0;
         int numOnes = 0;
         for (Solution.RankedColumn column: rankedColumns) {
            int value = column.getRank(rowIndex);
            if (value == 0) {
               numZeroes++;
            } else if (value == 1) {
               numOnes++;
            }
            rowData.add(value);
         }
         Row row = new Row(rowIndex, List.copyOf(rowData), numZeroes, numOnes);
         rows.add(row);
      }
      return rows;
   }

   final Comparator<Row> byNumberOfZeroesRowComparator = (r1, r2) -> {
      if (r1 == r2) {
         return 0;
      }
      int diff = r1.numberOfZeroes() - r2.numberOfZeroes();
      if (diff != 0) {
         return -diff; // let 1-s be in the bottom
      }
      diff = r1.originalIndex() - r2.originalIndex();
      if (diff != 0) {
         return diff;
      }
      throw new IllegalStateException(r1 + " == " + r2);
   };

   final Comparator<Row> byNumberOfOnesRowComparator = (r1, r2) -> {
      if (r1 == r2) {
         return 0;
      }
      int diff = r1.numberOfOnes() - r2.numberOfOnes();
      if (diff != 0) {
         return diff; // let 1-s be in the bottom
      }
      diff = r1.originalIndex() - r2.originalIndex();
      if (diff != 0) {
         return diff;
      }
      throw new IllegalStateException(r1 + " == " + r2);
   };

   public void sortRows(List<Row> list) {
      list.sort(byNumberOfOnesRowComparator);
   }
}
