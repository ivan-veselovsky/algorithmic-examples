package edu.word_matrix;

import lombok.val;

import java.util.*;

public class Solution {

   /** Number of characters from a to z: */
   private static final int CHARS = 'z' - 'a' + 1;

   private record CharRepetitionCardinality(char value, int occurrences) {
      CharRepetitionCardinality {
         assert occurrences > 0;
      }
   }
   record RankedCharRepetitionCardinality(char value, int occurrences, int rank) {
      RankedCharRepetitionCardinality {
         assert occurrences > 0;
         assert rank > 0;
      }
   }

   private final Comparator<CharRepetitionCardinality> charRepetitionCardinalityComparator =
          (cardinatity1, cardinality2) -> {
            if (cardinatity1 == cardinality2) {
               return 0;
            }
            int diff = cardinatity1.occurrences() - cardinality2.occurrences();
            if (diff != 0) {
               return -diff; // reversed
            }
            diff = cardinatity1.value() - cardinality2.value();
             if (diff != 0) {
                return diff;
             }
            throw new IllegalStateException("Two identical but not the same objects of CharRepetitionCardinality");
          };

   final Comparator<RankedColumn> rankedColumnComparator = (RankedColumn c1, RankedColumn c2) -> {
      if (c1 == c2) {
         return 0;
      }
      int diff = c1.maxCardinality() - c2.maxCardinality();
      if (diff != 0) {
         return diff;
      }
      diff = c1.originalIndex() - c2.originalIndex();
      if (diff != 0) {
         return diff;
      }
      throw new IllegalStateException();
   };

   void solve(final char[][] matrix) {
      int droppedColumnsCount = 0;

      final List<RankedColumn> rankedColumns = new ArrayList<>();
      for (int i=0; i<matrix[0].length; i++) {
         char[] column = extractColumn(matrix, i);

         RankedColumn rankedColumn = createNormalizedColumn(i, column);
         if (rankedColumn.isAllOnes()) {
            droppedColumnsCount++;
         } else {
            rankedColumns.add(rankedColumn);
         }
      }

      System.out.println("Columns ignored: " + droppedColumnsCount);

      rankedColumns.sort(rankedColumnComparator);

      for (RankedColumn rankedColumn: rankedColumns) {
         System.out.println(rankedColumn);
      }

      System.out.println("=========================");
      val rankedColumnMatrix = new RankedColumnMatrix();
      List<RankedColumnMatrix.Row> rows = rankedColumnMatrix.toRowForm(rankedColumns);
      for (RankedColumnMatrix.Row row: rows) {
         System.out.println(row);
      }

      System.out.println("=========================");
      rankedColumnMatrix.sortRows(rows);
      for (RankedColumnMatrix.Row row: rows) {
         System.out.println(row);
      }
   }

   public interface RankedColumn {
      int originalIndex();
      int numberOfZeroes();
      default boolean isAllOnes() {
         return false;
      }
      int getRank(int index);
      int maxCardinality();
      int length();
   }

   record FullHouseColumn(int originalIndex, int maxCardinality) implements RankedColumn {
      @Override
      public int numberOfZeroes() {
         return 0;
      }
      @Override
      public boolean isAllOnes() {
         return true;
      }
      @Override
      public String toString() {
         return originalIndex + ": 1..1 (full house)";
      }
      @Override
      public int getRank(int index) {
         return 1;
      }

      @Override
      public int length() {
         return maxCardinality;
      }
   }

   record ImmutableRankedColumn(int length, int originalIndex, List<Integer> ranks, int numberOfZeroes, int maxCardinality) implements RankedColumn {
      static ImmutableRankedColumn of(int originalIndex, List<Integer> ranks, int maxCardinality) {
         int numberOfZeroes = (int)ranks.stream().mapToInt(Integer::intValue).filter(r -> r == 0).count();
         return new ImmutableRankedColumn(ranks.size(), originalIndex, ranks, numberOfZeroes, maxCardinality);
      }
      ImmutableRankedColumn {
         assert numberOfZeroes >= 0;
      }
      @Override
      public int numberOfZeroes() {
         return numberOfZeroes;
      }
      @Override
      public int getRank(int index) {
         return ranks.get(index);
      }
      @Override
      public String toString() {
         return originalIndex + ": " + ranks.toString() + "/" + maxCardinality();
      }
   }

   record MutableRankedColumn(int length, int originalIndex, int[] ranks, int numberOfZeroes, int maxCardinality) implements RankedColumn {
      static MutableRankedColumn of(int originalIndex, int[] ranks, int maxOcurrences) {
         int numberOfZeroes = (int)Arrays.stream(ranks).filter(r -> r == 0).count();
         return new MutableRankedColumn(ranks.length, originalIndex, ranks, numberOfZeroes, maxOcurrences);
      }
      MutableRankedColumn {
         assert numberOfZeroes >= 0;
      }
      @Override
      public int numberOfZeroes() {
         return numberOfZeroes;
      }
      @Override
      public int getRank(int index) {
         return ranks[index];
      }
      void swapRows(int i, int j) {
         throw new IllegalStateException();
      }
      @Override
      public String toString() {
         return originalIndex + ": " + Arrays.toString(ranks) + "/" + maxCardinality();
      }
   }

   RankedColumn createNormalizedColumn(int originalIndex, char[] charColumn) {
      Map<Character, RankedCharRepetitionCardinality> rankedCountsByValue = buildRankedCounts(charColumn);

      final int[] rankArray = new int[charColumn.length];
      int maxOccurrences = 0;
      for (int i = 0; i < charColumn.length; i++) {
         final char value = charColumn[i];
         final RankedCharRepetitionCardinality cardinality = rankedCountsByValue.get(value);
         assert cardinality != null;

         final int occurrences = cardinality.occurrences();
         assert cardinality.value() == value;
         assert occurrences > 0;
         assert occurrences <= charColumn.length;

         if (cardinality.occurrences() > maxOccurrences) {
            maxOccurrences = cardinality.occurrences();
         }

         if (occurrences == charColumn.length) {
            return new FullHouseColumn(originalIndex, charColumn.length); // all 1-s.
         } else if (occurrences == 1) {
            rankArray[i] = 0; // unique character in the column.
         } else {
            rankArray[i] = cardinality.rank(); // index it by the rank.
         }
      }
      return MutableRankedColumn.of(originalIndex, rankArray, maxOccurrences);
   }

   char[] extractColumn(char[][] matrix, int colIndex) {
      char[] column = new char[matrix.length];
      for (int i=0; i<matrix.length; i++) {
         column[i] = matrix[i][colIndex];
      }
      return column;
   }

   Map<Character, RankedCharRepetitionCardinality> buildRankedCounts(char[] charColumn) {
      final int[] counts = new int[CHARS];
      for (char c : charColumn) {
         counts[c - 'a']++;
      }

      NavigableSet<CharRepetitionCardinality> cardinalitySortedByRankSet
             = new TreeSet<>(charRepetitionCardinalityComparator);
      for (int i = 0; i < counts.length; i++) {
         if (counts[i] > 0) {
            char value = (char)('a' + i);
            boolean added = cardinalitySortedByRankSet.add(new CharRepetitionCardinality(value, counts[i]));
            assert added;
         }
      }

      final NavigableMap<Character, RankedCharRepetitionCardinality> valueToRankedCardinality = new TreeMap<>();
      int rank = 0; // oder number in
      for (CharRepetitionCardinality cardinality : cardinalitySortedByRankSet) {
         rank++;
         valueToRankedCardinality.put(cardinality.value(),
                new RankedCharRepetitionCardinality(
                       cardinality.value(),
                       cardinality.occurrences(),
                       rank));
      }

      return Map.copyOf(valueToRankedCardinality);
   }


}
