package edu.word_matrix;

import lombok.SneakyThrows;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.*;

/**
 * Parses the data from a .lp file.
 * TODO: use dedicated class for matrix.
 */
public class MaxNumberOfIdenticalRows {
   int height;
   int width;
   private char[][] matrix;
   private int k;

   @SneakyThrows
   public int readClasspathResource(String name) {
      URL url = getClass().getClassLoader().getResource(name);
      if (url == null) {
         throw new IllegalArgumentException("Resource [" + name + "] not found.");
      }
      Path path = Paths.get(url.toURI());
      return readPath(path.toString());
   }

   @SneakyThrows
   public int readPath(String path) {
      List<String> lines = Files.readAllLines(Paths.get(path));

      int matrixElementCount = 0;
      for (String line: lines) {
         line = line.trim();

         if (line.startsWith("row(")) {
            height = parseNumber(line);
         } else if (line.startsWith("col(")) {
            width = parseNumber(line);
         } else if (line.startsWith("k(")) {
            k = parseNumber(line);
         } else if (line.startsWith("ch(")) {
            createMatrixIfNeeded();

            matrixElementCount += readAndFillMany(line);
         }
      }

      return matrixElementCount;
   }

   private void createMatrixIfNeeded() {
      if (matrix == null) {
         if (height != 0 && width != 0) {
            matrix = new char[height][width];
         } else {
            throw new IllegalStateException("No dimensions read.");
         }
      }
   }

   int parseNumber(String line) {
      // row(1..40). or k(17).

      final String ending;
      if (line.contains("..")) {
        ending = substringAfter(line, "..");
      } else {
        ending = substringAfter(line, "(");
      }
      String number = substringBefore(ending, ")");
      return Integer.parseInt(number);
   }

   int readAndFillMany(String line) {
      String[] chs = line.split("\\s+");
      int count = 0;
      for (String ch: chs) {
         RowColChar rowColChar = readRowColChar(ch);
         //System.out.println(rowColChar);

         if (matrix[rowColChar.row-1][rowColChar.col-1] != 0) {
            throw new IllegalStateException("Char already set.");
         }
         matrix[rowColChar.row-1][rowColChar.col-1] = rowColChar.val;
         count++;
      }
      return count;
   }

   RowColChar readRowColChar(String ch) {
      // character(1,11,l).
      String data = substringBetween(ch, "(", ")");

      String first = substringBefore(data, ",");
      String middle = substringBetween(data, ",", ",");
      String last = substringAfterLast(data, ",");

      return new RowColChar(
             Integer.parseInt(first),
             Integer.parseInt(middle),
             last.charAt(0));
   }

   public int getK() {
      return k;
   }

   public char[][] getMatrix() {
      return matrix;
   }

   public int getHeight() {
      return height;
   }

   public int getWidth() {
      return width;
   }

   record RowColChar(int row, int col, char val) {
   }
}
