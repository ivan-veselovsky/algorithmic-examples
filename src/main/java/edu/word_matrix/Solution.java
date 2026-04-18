package edu.word_matrix;

import java.util.*;

public class Solution {

   void solve(char[][] matrix) {
      int negCount = 0;
      for (int i=0; i<matrix[0].length; i++) {
         char[] column = extractColumn(matrix, i);

         int[] normalizedCol = createNormalizedColumn(column);
         if (normalizedCol[0] < 0) {
            negCount++;
         } else {
            System.out.println(i + ": " + Arrays.toString(normalizedCol));
         }
      }

      System.out.println("Columns to remove: " + negCount);
   }

   int[] createNormalizedColumn(char[] charColumn) {
      int[] counts = new int[26];
      for (char c : charColumn) {
         counts[c - 'a']++;
      }

      NavigableMap<Integer,Integer> rankMap = new TreeMap<>(Comparator.comparing(Integer::intValue).reversed());
      for (int count : counts) {
         if (count > 0) {
            // FIXME: bug here: identical counts will clash.
            //  Comparator should be changed to compare characters in case of identical counts.
            rankMap.put(count, 0);
         }
      }

      int rank = 0;
      for (Map.Entry<Integer, Integer> e: rankMap.entrySet()) {
         rank++;
         e.setValue(rank);
      }

      int[] result = new int[charColumn.length];
      for (int i = 0; i < charColumn.length; i++) {
         char c = charColumn[i];
         int cardinality = counts[c - 'a'];
         if (cardinality == charColumn.length) {
            result[i] = -1; // all line consists of identical chars, make it boolean-like.
         } else if (cardinality > 1) {
            result[i] = rankMap.get(cardinality);
         }
      }
      return result;
   }

   char[] extractColumn(char[][] matrix, int colIndex) {
      char[] column = new char[matrix.length];
      for (int i=0; i<matrix.length; i++) {
         column[i] = matrix[i][colIndex];
      }
      return column;
   }
}
