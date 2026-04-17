package edu.array_rotation_2;

class Solution {
   private int k;

   public void rotate(int[] nums, int k0) {
      k = k0 % nums.length;
      if (k == 0) {
         return; // nothing to do
      }

      final int gcd = gcd_euqlidian(nums.length, k);
      final int times = nums.length / gcd;
      for (int shift=0; shift < gcd; shift++) {
         performOneLoop(shift, times, nums);
      }
   }

   private void performOneLoop(int startIndex, int timesIncrementByK, int[] nums) {
      int indexOfBufferedOldValue = startIndex;
      int bufferedOldValue = nums[startIndex];
      int count = 0;
      while (count < timesIncrementByK) {
         final int newIndex = newIndex(indexOfBufferedOldValue, nums);
         final int valueToBeOverwritten = nums[newIndex]; // save old value

         nums[newIndex] = bufferedOldValue; // overwrite old value with new value;

         bufferedOldValue = valueToBeOverwritten;
         indexOfBufferedOldValue = newIndex;

         count++;
      }
   }

   private int newIndex(int oldIndex, int[] nums) {
      return (oldIndex + k) % nums.length;
   }

   static int gcd_stein(int a, int b) {
      // count common factors of 2
      int shift = Integer.numberOfTrailingZeros(a | b);

      // remove all factors of 2 from a
      a >>= Integer.numberOfTrailingZeros(a);

      while (b != 0) {
         // remove all factors of 2 from b
         b >>= Integer.numberOfTrailingZeros(b);

         // ensure a <= b
         if (a > b) {
            int tmp = a;
            a = b;
            b = tmp;
         }

         b = b - a;
      }

      return a << shift;
   }

   static int gcd_euqlidian(int a, int b) {
      while (b != 0) {
         int t = b;
         b = a % b;
         a = t;
      }
      return a;
   }
}