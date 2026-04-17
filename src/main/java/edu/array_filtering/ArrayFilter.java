package edu.array_filtering;

import lombok.val;

public class ArrayFilter {

   /**
    * the array may contain consequent duplicate numbers.
    * need to filter them, so that no number appears twice in a row.
    *
    * @param arr the array to be filtered in-place.
    * @return the new "affective" length of the array.
    */
   public int filter(int[] arr) {
      int x = 0;
      int y = 0;

      while (y < arr.length) {
         if (y > x) {
            arr[x] = arr[y];  // move
         }

         val repeatCount = getRepeatCount(arr, y);
         y += repeatCount;

         x++;
         y++;
      }

      return x;
   }

   /**
    * 0-based repetition count.
    */
   private int getRepeatCount(int[] arr, int from) {
      val x = arr[from];
      int i = from + 1;
      int repetitionCount = 0;
      while (i < arr.length) {
         if (arr[i] == x) {
            repetitionCount++;
            i++;
         } else {
            break;
         }
      }
      return repetitionCount;
   }
}
